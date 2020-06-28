package com.supermap.imobilelite.trafficTransferAnalyst;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.TrafficTransferAnalystCommon;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 交通换乘线路查询服务类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TransferPathService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.transferpathservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.TrafficTransferAnalystCommon");

    private TransferPathResult lastResult;
    private String queryUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 线路查询服务地址。
     */
    public TransferPathService(String url) {
        super();
        queryUrl = ServicesUtil.getFormatUrl(url);// 对换乘网络数据名进行编码
        lastResult = new TransferPathResult();
    }

    /**
     * <p>
     * 根据线路查询服务地址与服务端完成异步通讯，即发送分析参数，并通过实现TransferPathEventListener监听器处理查询结果。
     * </p>
     * @param parameters 线路查询参数信息。
     * @param listener 处理查询结果的TransferPathEventListener监听器。
     */
    public <T> void process(TransferPathParameters<T> parameters, TransferPathEventListener listener) {
        if (queryUrl == null || "".equals(queryUrl) || parameters == null) {
            return;
        }

        if (parameters.points == null || parameters.points.length <= 0 || parameters.transferLines == null || parameters.transferLines.length <= 0) {
            return;
        }
        Future<?> future = this.executors.submit(new DoTransferPathTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 处理线路查询结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class TransferPathEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onTransferPathStatusChanged(Object sourceObject, EventStatus status);

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

    class DoTransferPathTask<T> implements Runnable {
        private TransferPathParameters<T> params;
        private TransferPathEventListener listener;

        DoTransferPathTask(TransferPathParameters<T> parameters, TransferPathEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            doTransferPath(params, listener);
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param parameters 线路查询参数信息。
     * @return 线路查询结果。
     */
    private <T> TransferPathResult doTransferPath(TransferPathParameters<T> parameters, TransferPathEventListener listener) {
        String pointsJosnStr = JsonConverter.toJson(parameters.points);// [1,2] ,[{"x":32,"y":43}]
        String linesJosnStr = JsonConverter.toJson(parameters.transferLines);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("points", pointsJosnStr));
        paramList.add(new BasicNameValuePair("transferLines", linesJosnStr));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String lineQueryUrl = queryUrl + "/path.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            TransferGuide guide = Util.get(lineQueryUrl, TransferGuide.class);
            lastResult.transferGuide = guide;
            listener.onTransferPathStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onTransferPathStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), TrafficTransferAnalystCommon.TRAFFICTRANSFERANALYST_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回线路查询结果。
     * </p>
     * @return 线路查询结果。
     */
    public TransferPathResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }
}
