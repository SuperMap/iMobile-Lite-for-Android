package com.supermap.imobilelite.spatialAnalyst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.restlet.data.MediaType;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.SpatialAnalystCommon;
import com.supermap.services.rest.encoders.JsonEncoder;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 插值分析服务类。
 * </p>
 * <p>
 * 插值分析是用于对离散的点数据进行插值得到栅格数据集。插值分析可以将有限的采样点数据，通过插值对采样点周围的数值情况进行预测，从而掌握研究区域内数据的总体分布状况，而使采样的离散点不仅仅反映其所在位置的数值情况，而且可以反映区域的数值分布。
 * 由于地理空间要素之间存在着空间关联性，即相互邻近的事物总是趋于同质，也就是具有相同或者相似的特征，举个例子，街道的一遍下雨了，那么街道的另一边在大多数情况下也一定在下雨，如果在更大的区域范围，一个乡镇的气候应当与其接壤的另一的乡镇的气候相同，等等，基于这样的推理，我们就可以利用已知地点的信息来间接获取与其相邻的其他地点的信息，而插值分析就是基于这样的思想产生的，也是插值重要的应用价值之一。
 * </p>
 * <p>
 * 将某个区域的采样点数据插值生成栅格数据，实际上是将研究区域按照给定的格网尺寸（分辨率）进行栅格化，栅格数据中每一个栅格单元对应一块区域，栅格单元的值由其邻近的采样点的数值通过某种插值方法计算得到，因此，就可以预测采样点周围的数值情况，进而了解整个区域的数值分布情况。其中，插值方法主要有距离反比权值插值法、克吕金（Kriging）内插法、径向基函数RBF（Radial Basis Function）插值法等。<br>
 * 利用插值分析功能能够预测任何地理点数据的未知值，如高程、降雨量、化学物浓度、噪声级等等。
 * </p>
 * <p>
 * 下面几幅图，就是利用采样点的高程数据进行插值分析得到栅格数据的示意图，其所使用的插值方法都是距离反比权值插值法，但所使用的分辨率不同。<br>
 * 如图一为插值使用的采样点数据，数值为高程值，利用距离反比权值插值法，分辨率为3000米所得的插值结果如图二所示，每个栅格单元中的数值为栅格单元的值，这些值都是由采样点的数值插值得出的，由此可以大致地了解这块区域地形的高低状况。当设置更高的分辨率后，地形的起伏趋势将更加清晰化，图三为1000米分辨率，图四为30米分辨率，因此设置合理的分辨率值以及合适的插值算法，就可以从有限的采样点数据中，挖据出更多的信息。<br>
 * <img src="../../../../resources/SpatialAnalyst/spatialAlyPoint.png"> <br>
 * <img src="../../../../resources/SpatialAnalyst/spatialAlyGridPoint.png"><br>
 * <img src="../../../../resources/SpatialAnalyst/spatialAlyInterpolated.png"><br>
 * <img src="../../../../resources/SpatialAnalyst/spatialAlyInterpolated2.png"><br>
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class InterpolationAnalystService {

    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.spatialAnalyst.InterpolationAnalystService";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.SpatialAnalystCommon");
    private InterpolationAnalystResult lastResult;
    private String baseUrl;
    private String url;
    private int timeout = -1; // 代表使用默认超时时间，5秒

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 插值分析服务地址。
     */
    public InterpolationAnalystService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);// 对数据名进行编码
        lastResult = new InterpolationAnalystResult();
    }

    /**
     * <p>
     * 根据插值分析与服务端完成异步通讯，即发送分析参数，并通过实现InterpolationAnalystEventListener监听器处理分析结果。
     * </p>
     * @param <T>
     * @param params 插值分析参数信息。
     * @param listener 处理分析结果的InterpolationAnalystEventListener监听器。
     */
    public void process(InterpolationAnalystParameters params, InterpolationAnalystEventListener listener) {
        if (StringUtils.isEmpty(baseUrl) || params == null) {
            return;
        }
        Future<?> future = this.executors.submit(new DoInterpolationAnalystTask(params, listener));
        listener.setProcessFuture(future);
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
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param params 插值分析参数信息。
     * @return 返回插值分析结果。
     * @throws IOException
     */
    private InterpolationAnalystResult doInterpolationAnalyst(InterpolationAnalystParameters params, InterpolationAnalystEventListener listener)
            throws IOException {
        // URI参数
        List<NameValuePair> paramListuri = new ArrayList<NameValuePair>();
        paramListuri.add(new BasicNameValuePair("asynchronousReturn", "false"));
        paramListuri.add(new BasicNameValuePair("returnContent", "true"));
        if (Credential.CREDENTIAL != null) {
            paramListuri.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        // 请求体参数
        HashMap<String, Object> interpolationEntity = new HashMap<String, Object>();
        interpolationEntity.put("resolution", params.resolution);
        interpolationEntity.put("outputDatasetName", params.outputDatasetName);
        interpolationEntity.put("outputDatasourceName", params.outputDatasourceName);
        interpolationEntity.put("searchRadius", params.searchRadius);
        interpolationEntity.put("pixelFormat", params.pixelFormat);
        interpolationEntity.put("dataset", params.dataset);
        interpolationEntity.put("zValueFieldName", params.zValueFieldName);
        interpolationEntity.put("zValueScale", params.zValueScale);
        if (params.bounds != null) {
            interpolationEntity.put("bounds", params.bounds);
        }
        if (params.inputPoints != null) {
            interpolationEntity.put("inputPoints", params.inputPoints);
        }
        if (params.filterQueryParameter != null) {
            interpolationEntity.put("filterQueryParameter", params.filterQueryParameter);
        }
        if (params.clipParam != null) {
            interpolationEntity.put("clipParam", params.clipParam);
        }

        if (params instanceof InterpolationDensityAnalystParameters) {
            if ("geometry".equals(params.InterpolationAnalystType)) {
                url = baseUrl + "/geometry/interpolation/density.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
            } else {
                url = baseUrl + "/datasets/" + params.dataset + "/interpolation/density.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
            }

        } else if (params instanceof InterpolationIDWAnalystParameters) {
            InterpolationIDWAnalystParameters interpolationIDWAnalystParameters = (InterpolationIDWAnalystParameters) params;
            if ("geometry".equals(params.InterpolationAnalystType)) {
                url = baseUrl + "/geometry/interpolation/idw.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
            } else {
                url = baseUrl + "/datasets/" + params.dataset + "/interpolation/idw.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
            }
            interpolationEntity.put("expectedCount", interpolationIDWAnalystParameters.expectedCount);
            interpolationEntity.put("power", interpolationIDWAnalystParameters.power);
            interpolationEntity.put("searchMode", interpolationIDWAnalystParameters.searchMode);

        } else if (params instanceof InterpolationKrigingAnalystParameters) {
            InterpolationKrigingAnalystParameters interpolationKrigingAnalystParameters = (InterpolationKrigingAnalystParameters) params;
            if ("geometry".equals(params.InterpolationAnalystType)) {
                url = baseUrl + "/geometry/interpolation/kriging.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
            } else {
                url = baseUrl + "/datasets/" + params.dataset + "/interpolation/kriging.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
            }
            if (interpolationKrigingAnalystParameters.type == InterpolationAlgorithmType.UniversalKriging) {
                interpolationEntity.put("exponent", interpolationKrigingAnalystParameters.exponent);
            } else if (interpolationKrigingAnalystParameters.type == InterpolationAlgorithmType.SimpleKriging) {
                interpolationEntity.put("mean", interpolationKrigingAnalystParameters.mean);
            }
            if (interpolationKrigingAnalystParameters.searchMode == SearchMode.QUADTREE) {
                interpolationEntity.put("maxPointCountForInterpolation", interpolationKrigingAnalystParameters.maxPointCountForInterpolation);
                interpolationEntity.put("maxPointCountInNode", interpolationKrigingAnalystParameters.maxPointCountInNode);
            }
            interpolationEntity.put("angle", interpolationKrigingAnalystParameters.angle);
            interpolationEntity.put("expectedCount", interpolationKrigingAnalystParameters.expectedCount);
            interpolationEntity.put("nugget", interpolationKrigingAnalystParameters.nugget);
            interpolationEntity.put("range", interpolationKrigingAnalystParameters.range);
            interpolationEntity.put("searchMode", interpolationKrigingAnalystParameters.searchMode);
            interpolationEntity.put("sill", interpolationKrigingAnalystParameters.sill);
            interpolationEntity.put("type", interpolationKrigingAnalystParameters.type);
            interpolationEntity.put("variogramMode", interpolationKrigingAnalystParameters.variogramMode);
        } else if (params instanceof InterpolationRBFAnalystParameters) {
            InterpolationRBFAnalystParameters interpolationRBFAnalystParameters = (InterpolationRBFAnalystParameters) params;
            if ("geometry".equals(params.InterpolationAnalystType)) {
                url = baseUrl + "/geometry/interpolation/rbf.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
            } else {
                url = baseUrl + "/datasets/" + params.dataset + "/interpolation/rbf.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
            }
            interpolationEntity.put("expectedCount", interpolationRBFAnalystParameters.expectedCount);
            interpolationEntity.put("maxPointCountForInterpolation", interpolationRBFAnalystParameters.maxPointCountForInterpolation);
            interpolationEntity.put("maxPointCountInNode", interpolationRBFAnalystParameters.maxPointCountInNode);
            interpolationEntity.put("searchMode", interpolationRBFAnalystParameters.searchMode);
            interpolationEntity.put("smooth", interpolationRBFAnalystParameters.smooth);
            interpolationEntity.put("tension", interpolationRBFAnalystParameters.tension);
        }

        JsonEncoder encoder = new JsonEncoder();
        String interpolationText = encoder.toRepresentation(MediaType.APPLICATION_JSON, interpolationEntity).getText();

        try {
            String resultStr = Util.post(url, Util.newJsonUTF8StringEntity(interpolationText), this.timeout);
            if (!StringUtils.isEmpty(resultStr)) {
                JsonConverter jsConverer = new JsonConverter();
                lastResult = jsConverer.to(resultStr, InterpolationAnalystResult.class);
            }
            listener.onInterpolationAnalystStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            // e.printStackTrace();
            listener.onInterpolationAnalystStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), SpatialAnalystCommon.SPATIALANALYST_EXCEPTION, e.getMessage()));

        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回插值分析结果。
     * </p>
     * @return 分析结果。
     */
    public InterpolationAnalystResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理插值分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class InterpolationAnalystEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onInterpolationAnalystStatusChanged(Object sourceObject, EventStatus status);

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

    class DoInterpolationAnalystTask implements Runnable {
        private InterpolationAnalystParameters params;
        private InterpolationAnalystEventListener listener;

        DoInterpolationAnalystTask(InterpolationAnalystParameters params, InterpolationAnalystEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            try {
                doInterpolationAnalyst(params, listener);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

}
