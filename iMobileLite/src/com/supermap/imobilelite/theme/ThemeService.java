package com.supermap.imobilelite.theme;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.ResourceInfo;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Constants;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.ThemeCommon;
import com.supermap.imobilelite.serverType.ServerStyle;
import com.supermap.imobilelite.serverType.ServerTextStyle;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 专题图服务类。
 * </p>
 * <p>
 * 该类负责将客户端制作专题图的参数传递给服务端，在服务端会生成一个临时图层来制作相应的专题图，这个专题图在服务端就是一个资源（ResourceInfo），它具有资源地址url和资源ID号。客户端获取到这个资源ID号以后将其赋值给动态图层的layersID属性就能显示出相应的专题图。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.theme.ThemeService";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.ThemeCommon");
    private ThemeResult lastResult;
    private String baseUrl;
    private int timeout = -1; // 代表使用默认超时时间，5秒

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 专题图服务地址。如： http://ServerIP:8090/iserver/services/map-china400/rest/maps/China
     */
    public ThemeService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new ThemeResult();
    }

    /**
     * <p>
     * 用户自定义超时时间。
     * </p>
     * @param timeout 用户自定义超时时间。若用户不设置，则使用默认超时间为5秒。0代表无限，即代表不设置超时限制。单位默认为秒。
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * <p>
     * 根据专题图与服务端完成异步通讯，即发送专题图参数，并通过实现ThemeServiceEventListener监听器处理专题图结果。
     * </p>
     * @param <T>
     * @param params 专题图参数信息。
     * @param listener 处理专题图结果的ThemeServiceEventListener监听器。
     */
    public <T> void process(ThemeParameters params, ThemeServiceEventListener listener) {
        if (StringUtils.isEmpty(baseUrl) || params == null) {
            return;
        }
        if (params.datasetNames == null || params.datasetNames.length < 1 || params.dataSourceNames == null || params.dataSourceNames.length < 1) {
            return;
        }

        Future<?> future = this.executors.submit(new DoThemeServiceTask(params, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 获取地图名。
     * </p>
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getServiceMapName() throws UnsupportedEncodingException {
        String[] array = baseUrl.split("/");
        String mapName = array[array.length - 1];
        mapName = URLDecoder.decode(mapName, Constants.UTF8);
        Log.d("name", mapName);
        return mapName;

    }

    /**
     * <p>
     * 返回专题图参数。
     * </p>
     * @return
     * @throws IOException
     */
    public HashMap<String, Object[]> getJsonParameters(ThemeParameters params) throws IOException {
        HashMap<String, Object[]> subLayersEntity = new HashMap<String, Object[]>();
        HashMap<String, Object> LayerEntity = new HashMap<String, Object>();
        HashMap<String, Object> themeEntity = new HashMap<String, Object>();
        if (params.themes != null && params.themes.length > 0) {

            for (int i = 0; i < params.themes.length; i++) {
                Theme item = params.themes[i];
                themeEntity.put("type", item.type);
                if (item instanceof ThemeUnique) {
                    ThemeUnique themeUnique = (ThemeUnique) item;
                    themeEntity.put("items", themeUnique.items);
                    themeEntity.put("colorGradientType", themeUnique.colorGradientType);
                    themeEntity.put("defaultStyle", themeUnique.defaultStyle);
                    themeEntity.put("uniqueExpression", themeUnique.uniqueExpression);
                    themeEntity.put("memoryData", themeUnique.themeMemoryData);
                } else if (item instanceof ThemeLabel) {
                    ThemeLabel themeLabel = (ThemeLabel) item;
                    if (themeLabel.alongLine == null) {
                        themeLabel.alongLine = new ThemeLabelAlongLine();
                    }
                    themeEntity.put("alongLine", themeLabel.alongLine.alongLine);
                    themeEntity.put("alongLineDirection", themeLabel.alongLine.alongLineDirection);
                    themeEntity.put("angleFixed", themeLabel.alongLine.angleFixed);
                    themeEntity.put("isLabelRepeated", themeLabel.alongLine.isLabelRepeated);
                    themeEntity.put("repeatedLabelAvoided", themeLabel.alongLine.repeatedLabelAvoided);
                    themeEntity.put("labelRepeatInterval", themeLabel.alongLine.labelRepeatInterval);
                    themeEntity.put("repeatIntervalFixed", themeLabel.alongLine.repeatIntervalFixed);
                    if (themeLabel.background == null) {
                        themeLabel.background = new ThemeLabelBackground();
                    }
                    themeEntity.put("labelBackShape", themeLabel.background.labelBackShape);
                    if (themeLabel.background.backStyle == null) {
                        themeLabel.background.backStyle = new ServerStyle();
                    }
                    themeEntity.put("backStyle", themeLabel.background.backStyle);
                    if (themeLabel.flow == null) {
                        themeLabel.flow = new ThemeFlow();
                    }
                    themeEntity.put("flowEnabled", themeLabel.flow.flowEnabled);
                    themeEntity.put("leaderLineDisplayed", themeLabel.flow.leaderLineDisplayed);
                    if (themeLabel.flow.leaderLineStyle == null) {
                        themeLabel.flow.leaderLineStyle = new ServerStyle();
                    }
                    themeEntity.put("leaderLineStyle", themeLabel.flow.leaderLineStyle);
                    if (themeLabel.offset == null) {
                        themeLabel.offset = new ThemeOffset();
                    }
                    themeEntity.put("offsetFixed", themeLabel.offset.offsetFixed);
                    themeEntity.put("offsetX", themeLabel.offset.offsetX);
                    themeEntity.put("offsetY", themeLabel.offset.offsetY);
                    themeEntity.put("items", themeLabel.items);
                    themeEntity.put("labelExpression", themeLabel.labelExpression);
                    themeEntity.put("labelOverLengthMode", themeLabel.labelOverLengthMode);
                    themeEntity.put("overlapAvoided", themeLabel.overlapAvoided);
                    themeEntity.put("matrixCells", themeLabel.matrixCells);
                    themeEntity.put("maxLabelLength", themeLabel.maxLabelLength);
                    themeEntity.put("numericPrecision", themeLabel.numericPrecision);
                    themeEntity.put("rangeExpression", themeLabel.rangeExpression);
                    themeEntity.put("smallGeometryLabeled", themeLabel.smallGeometryLabeled);
                    if (themeLabel.text == null) {
                        themeLabel.text = new ThemeLabelText();
                    }
                    themeEntity.put("maxTextHeight", themeLabel.text.maxTextHeight);
                    themeEntity.put("minTextHeight", themeLabel.text.minTextHeight);
                    themeEntity.put("maxTextWidth", themeLabel.text.maxTextWidth);
                    themeEntity.put("minTextWidth", themeLabel.text.minTextWidth);
                    if (themeLabel.text.uniformStyle == null) {
                        themeLabel.text.uniformStyle = new ServerTextStyle();
                    }
                    themeEntity.put("uniformStyle", themeLabel.text.uniformStyle);
                    if (themeLabel.text.uniformMixedStyle == null) {
                        themeLabel.text.uniformMixedStyle = new LabelMixedTextStyle();
                    }
                    themeEntity.put("uniformMixedStyle", themeLabel.text.uniformMixedStyle);
                    themeEntity.put("memoryData", themeLabel.themeMemoryData);
                } else if (item instanceof ThemeGridUnique) {
                    ThemeGridUnique themeGridUnique = (ThemeGridUnique) item;
                    themeEntity.put("defaultcolor", themeGridUnique.defaultcolor);
                    themeEntity.put("items", themeGridUnique.items);
                    themeEntity.put("memoryData", themeGridUnique.themeMemoryData);
                } else if (item instanceof ThemeDotDensity) {
                    ThemeDotDensity themeDotDensity = (ThemeDotDensity) item;
                    themeEntity.put("dotExpression", themeDotDensity.dotExpression);
                    themeEntity.put("style", themeDotDensity.style);
                    themeEntity.put("memoryData", themeDotDensity.themeMemoryData);
                    themeEntity.put("value", themeDotDensity.value);
                } else if (item instanceof ThemeGraduatedSymbol) {
                    ThemeGraduatedSymbol themeGraduatedSymbol = (ThemeGraduatedSymbol) item;
                    themeEntity.put("baseValue", themeGraduatedSymbol.baseValue);
                    themeEntity.put("expression", themeGraduatedSymbol.expression);
                    if (themeGraduatedSymbol.flow == null) {
                        themeGraduatedSymbol.flow = new ThemeFlow();
                    }
                    themeEntity.put("flowEnabled", themeGraduatedSymbol.flow.flowEnabled);
                    themeEntity.put("leaderLineDisplayed", themeGraduatedSymbol.flow.leaderLineDisplayed);
                    if (themeGraduatedSymbol.flow.leaderLineStyle == null) {
                        themeGraduatedSymbol.flow.leaderLineStyle = new ServerStyle();
                    }
                    themeEntity.put("leaderLineStyle", themeGraduatedSymbol.flow.leaderLineStyle);
                    themeEntity.put("graduatedMode", themeGraduatedSymbol.graduatedMode);
                    if (themeGraduatedSymbol.offset == null) {
                        themeGraduatedSymbol.offset = new ThemeOffset();
                    }
                    themeEntity.put("offsetFixed", themeGraduatedSymbol.offset.offsetFixed);
                    themeEntity.put("offsetX", themeGraduatedSymbol.offset.offsetX);
                    themeEntity.put("offsetY", themeGraduatedSymbol.offset.offsetY);
                    if (themeGraduatedSymbol.style == null) {
                        themeGraduatedSymbol.style = new ThemeGraduatedSymbolStyle();
                    }
                    if (themeGraduatedSymbol.style.negativeStyle == null) {
                        themeGraduatedSymbol.style.negativeStyle = new ServerStyle();
                    }
                    themeEntity.put("negativeStyle", themeGraduatedSymbol.style.negativeStyle);
                    themeEntity.put("negativeDisplayed", themeGraduatedSymbol.style.negativeDisplayed);
                    if (themeGraduatedSymbol.style.positiveStyle == null) {
                        themeGraduatedSymbol.style.positiveStyle = new ServerStyle();
                    }
                    themeEntity.put("positiveStyle", themeGraduatedSymbol.style.positiveStyle);
                    if (themeGraduatedSymbol.style.zeroStyle == null) {
                        themeGraduatedSymbol.style.zeroStyle = new ServerStyle();
                    }
                    themeEntity.put("zeroStyle", themeGraduatedSymbol.style.zeroStyle);
                    themeEntity.put("zeroDisplayed", themeGraduatedSymbol.style.zeroDisplayed);
                    themeEntity.put("memoryData", themeGraduatedSymbol.themeMemoryData);
                } else if (item instanceof ThemeRange) {
                    ThemeRange themeRange = (ThemeRange) item;
                    themeEntity.put("colorGradientType", themeRange.colorGradientType);
                    themeEntity.put("items", themeRange.items);
                    themeEntity.put("rangeExpression", themeRange.rangeExpression);
                    themeEntity.put("rangeMode", themeRange.rangeMode);
                    themeEntity.put("rangeParameter", themeRange.rangeParameter);
                    themeEntity.put("memoryData", themeRange.themeMemoryData);
                } else if (item instanceof ThemeGridRange) {
                    ThemeGridRange themeGridRange = (ThemeGridRange) item;
                    themeEntity.put("colorGradientType", themeGridRange.colorGradientType);
                    themeEntity.put("items", themeGridRange.items);
                    themeEntity.put("rangeMode", themeGridRange.rangeMode);
                    themeEntity.put("rangeParameter", themeGridRange.rangeParameter);
                    themeEntity.put("reverseColor", themeGridRange.reverseColor);
                    themeEntity.put("memoryData", themeGridRange.themeMemoryData);
                } else if (item instanceof ThemeGraph) {
                    ThemeGraph themeGraph = (ThemeGraph) item;
                    themeEntity.put("barWidth", themeGraph.barWidth);
                    if (themeGraph.flow == null) {
                        themeGraph.flow = new ThemeFlow();
                    }
                    themeEntity.put("flowEnabled", themeGraph.flow.flowEnabled);
                    themeEntity.put("leaderLineDisplayed", themeGraph.flow.leaderLineDisplayed);
                    if (themeGraph.flow.leaderLineStyle == null) {
                        themeGraph.flow.leaderLineStyle = new ServerStyle();
                    }
                    themeEntity.put("leaderLineStyle", themeGraph.flow.leaderLineStyle);
                    if (themeGraph.offset == null) {
                        themeGraph.offset = new ThemeOffset();
                    }
                    themeEntity.put("offsetFixed", themeGraph.offset.offsetFixed);
                    themeEntity.put("offsetX", themeGraph.offset.offsetX);
                    themeEntity.put("offsetY", themeGraph.offset.offsetY);
                    themeEntity.put("graduatedMode", themeGraph.graduatedMode);
                    if (themeGraph.graphAxes == null) {
                        themeGraph.graphAxes = new ThemeGraphAxes();
                    }
                    themeEntity.put("axesColor", themeGraph.graphAxes.axesColor);
                    themeEntity.put("axesDisplayed", themeGraph.graphAxes.axesDisplayed);
                    themeEntity.put("axesTextDisplayed", themeGraph.graphAxes.axesTextDisplayed);
                    themeEntity.put("axesGridDisplayed", themeGraph.graphAxes.axesGridDisplayed);
                    if (themeGraph.graphAxes.axesTextStyle == null) {
                        themeGraph.graphAxes.axesTextStyle = new ServerTextStyle();
                    }
                    themeEntity.put("axesTextStyle", themeGraph.graphAxes.axesTextStyle);
                    if (themeGraph.graphSize == null) {
                        themeGraph.graphSize = new ThemeGraphSize();
                    }
                    themeEntity.put("maxGraphSize", themeGraph.graphSize.maxGraphSize);
                    themeEntity.put("minGraphSize", themeGraph.graphSize.minGraphSize);
                    themeEntity.put("graphSizeFixed", themeGraph.graphSizeFixed);
                    if (themeGraph.graphText == null) {
                        themeGraph.graphText = new ThemeGraphText();
                    }

                    themeEntity.put("graphTextDisplayed", themeGraph.graphText.graphTextDisplayed);
                    themeEntity.put("graphTextFormat", themeGraph.graphText.graphTextFormat);
                    if (themeGraph.graphText.graphTextStyle == null) {
                        themeGraph.graphText.graphTextStyle = new ServerTextStyle();
                    }

                    themeEntity.put("graphTextStyle", themeGraph.graphText.graphTextStyle);
                    themeEntity.put("graphType", themeGraph.graphType);
                    themeEntity.put("items", themeGraph.items);
                    themeEntity.put("memoryKeys", themeGraph.memoryKeys);
                    themeEntity.put("negativeDisplayed", themeGraph.negativeDisplayed);
                    themeEntity.put("overlapAvoided", themeGraph.overlapAvoided);
                    themeEntity.put("roseAngle", themeGraph.roseAngle);
                    themeEntity.put("startAngle", themeGraph.startAngle);
                    themeEntity.put("memoryData", themeGraph.themeMemoryData);
                }
                LayerEntity.put("theme", themeEntity);
                LayerEntity.put("type", "UGC");
                LayerEntity.put("ugcLayerType", "THEME");
                if (params.displayFilters != null && params.displayFilters.length > 0) {
                    LayerEntity.put("displayFilters", params.displayFilters[i]);
                }
                HashMap<String, Object> datasetInfoEntity = new HashMap<String, Object>();
                if (params.datasetNames != null && params.datasetNames.length > 0) {
                    datasetInfoEntity.put("name", params.datasetNames[i]);
                }
                if (params.dataSourceNames != null && params.dataSourceNames.length > 0) {
                    datasetInfoEntity.put("dataSourceName", params.dataSourceNames[i]);
                }

                LayerEntity.put("datasetInfo", datasetInfoEntity);
                if (params.joinItems != null && params.joinItems.length > 0) {
                    LayerEntity.put("joinItems", params.joinItems[i]);
                }
                HashMap<String, Object>[] LayerEntitys = new HashMap[1];
                LayerEntitys[0] = LayerEntity;
                subLayersEntity.put("layers", LayerEntitys);
            }
        }

        return subLayersEntity;

    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param params 专题图参数信息。
     * @return 返回专题图结果。
     * @throws IOException
     */
    private ThemeResult doThemeService(ThemeParameters params, ThemeServiceEventListener listener) throws IOException {
        String serviceUrl = baseUrl + "/tempLayersSet.json";
        if (Credential.CREDENTIAL != null) {
            serviceUrl = serviceUrl + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        // 请求体参数
        HashMap<String, Object> themeEntity = new HashMap<String, Object>();
        themeEntity.put("type", "UGC");
        themeEntity.put("subLayers", this.getJsonParameters(params));
        themeEntity.put("name", this.getServiceMapName());
        String themeText = JsonConverter.toJson(themeEntity);
        themeText = "[" + themeText + "]";
        Log.d("theme", themeText);
        try {
            String resultStr = Util.post(serviceUrl, Util.newJsonUTF8StringEntity(themeText), this.timeout); // new StringEntity(themeText, "UTF-8")
            Log.d("result", resultStr);
            JsonConverter jsConverer = new JsonConverter();
            ResourceInfo resourceInfo = jsConverer.to(resultStr, ResourceInfo.class);
            lastResult.resourceInfo = resourceInfo;
            listener.onThemeServiceStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onThemeServiceStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), ThemeCommon.THEME_EXCEPTION, e.getMessage()));
        }
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回专题图结果。
     * </p>
     * @return 分析结果。
     */
    public ThemeResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理专题图结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class ThemeServiceEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onThemeServiceStatusChanged(Object sourceObject, EventStatus status);

        private boolean isProcessed() {
            return processed.get();
        }

        /**
         * <p>
         * 设置异步操作处理。
         * </p>
         * @param future Future对象。
         */
        protected void setProcessFuture(Future<?> future) {
            this.future = future;
        }

        /**
         * <p>
         * 等待监听器执行完毕。
         * </p>
         * @throws InterruptedException
         * @throws ExecutionException
         */
        public void waitUntilProcessed() throws InterruptedException, ExecutionException {
            if (future == null) {
                return;
            }
            future.get();
        }
    }

    class DoThemeServiceTask<T> implements Runnable {
        private ThemeParameters params;
        private ThemeServiceEventListener listener;

        DoThemeServiceTask(ThemeParameters params, ThemeServiceEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            try {
                doThemeService(params, listener);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
