package com.supermap.imobilelite.maps.measure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.components.commontypes.util.ResourceManager;
import com.supermap.services.rest.util.JsonConverter;

/**
 * <p>
 * 量算服务类. 
 * </P>
 * 该类负责将客户端创建的 MeasureParameters 对象传入服务器端进行距离/面积量算，并将服务器端返回的结果保存在 MeasureResult 对象中。
 */
public class MeasureService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.measure.measureService";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.MeasureCommon");
    private MeasureResult lastResult;
    private String baseUrl;
    private String url;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 地图服务地址。如 http://ServerIP:port/iserver/services/map-world/rest/maps/世界地图。  
     */
    public MeasureService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new MeasureResult();
    }

    /**
     * <p>
     * 根据量算参数进行距离或面积量算与服务端完成异步通讯，即发送量算参数，并通过实现MeasureEventListener监听器处理量算结果。
     * </p>
     * @param parameters 量算参数信息。
     * @param listener 处理量算结果的MeasureEventListener监听器。
     * @param measureMode 量算模式， 如果为null，则默认为距离量算。
     */
    public void process(MeasureParameters parameters, MeasureEventListener listener, MeasureMode measureMode) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }
        if (parameters.point2Ds == null) {
            return;
        }
        if (measureMode == null) {
            measureMode = MeasureMode.DISTANCE;
        }

        Future<?> future = this.executors.submit(new DoMeasureTask(parameters, listener, measureMode));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 处理量算结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class MeasureEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理量算结果的接口。
         * </p>
         * @param sourceObject 量算结果。
         * @param status 量算结果的状态。
         */
        public abstract void onMeasureStatusChanged(Object sourceObject, EventStatus status);

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

    class DoMeasureTask implements Runnable {
        private MeasureParameters params;
        private MeasureEventListener listener;
        private MeasureMode measureMode;

        DoMeasureTask(MeasureParameters parameters, MeasureEventListener listener, MeasureMode measureMode) {
            this.params = parameters;
            this.listener = listener;
            this.measureMode = measureMode;
        }

        public void run() {
            try {
                doMeasure(params, listener, measureMode);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param parameters 量算参数信息。
     * @param listener 处理量算结果的MeasureEventListener监听器。
     * @param measureMode 量算模式。
     * @return 量算结果。
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    private MeasureResult doMeasure(MeasureParameters parameters, MeasureEventListener listener, MeasureMode measureMode) throws ClientProtocolException,
            IOException {
        // 量算参数
        String pointsStr = JsonConverter.toJson(parameters.point2Ds);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("point2Ds", pointsStr));
        paramList.add(new BasicNameValuePair("unit", String.valueOf(parameters.unit)));
        if (parameters.prjCoordSys != null) {
            String prjCoordSysStr = JsonConverter.toJson(parameters.prjCoordSys);
            String prjCoordSysTemp = "{" + "epsgCode" + prjCoordSysStr.substring(prjCoordSysStr.indexOf(":"), prjCoordSysStr.length()) + "}";
            paramList.add(new BasicNameValuePair("prjCoordSys", prjCoordSysTemp));

        }
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        if (measureMode == MeasureMode.AREA) {
            url = baseUrl + "/area.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码;
        } else {
            url = baseUrl + "/distance.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码;
        }
        try {
            lastResult = Util.get(url, MeasureResult.class);
            listener.onMeasureStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onMeasureStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), MapCommon.MEASURE_EXCEPTION, e.getMessage()));
        }
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回量算结果。
     * </p>
     * @return 量算结果。
     */
    public MeasureResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

}
