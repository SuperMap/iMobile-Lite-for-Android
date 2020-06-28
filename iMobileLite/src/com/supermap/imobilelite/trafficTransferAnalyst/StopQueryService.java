package com.supermap.imobilelite.trafficTransferAnalyst;

import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.TrafficTransferAnalystCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 站点查询服务类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class StopQueryService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.stopqueryservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.TrafficTransferAnalystCommon");

    private StopQueryResult lastResult;
    private String queryUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 站点查询服务地址。
     */
    public StopQueryService(String url) {
        super();
        queryUrl = ServicesUtil.getFormatUrl(url);// 对换乘网络数据名进行编码
        lastResult = new StopQueryResult();
    }

    /**
     * <p>
     * 根据站点查询服务地址与服务端完成异步通讯，即发送分析参数，并通过实现StopQueryEventListener监听器处理查询结果。
     * </p>
     * @param params 站点查询参数信息。
     * @param listener 处理查询结果的StopQueryEventListener监听器。
     */
    public void process(StopQueryParameters params, StopQueryEventListener listener) {
        if (queryUrl == null || "".equals(queryUrl) || params == null) {
            return;
        }
        if (params.keyWord == null || "".equals(params.keyWord)) {
            return;
        }
        Future<?> future = this.executors.submit(new DoStopQueryTask(params, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param params 站点查询参数信息。
     * @param listener 处理查询结果的StopQueryEventListener监听器。
     * @return 站点查询结果。
     */
    private StopQueryResult doStopQuery(StopQueryParameters params, StopQueryEventListener listener) {
        try {
            String keyword = URLEncoder.encode(params.keyWord, "utf-8");// 对keyWord名进行编码
            String stopQueryUrl = queryUrl + "/stops/keyword/" + keyword + ".json?returnPosition=" + params.returnPosition;
            if (Credential.CREDENTIAL != null) {
                stopQueryUrl = stopQueryUrl + "&" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
            }
            TransferStopInfo[] infos = Util.get(stopQueryUrl, TransferStopInfo[].class);
            lastResult.transferStopInfos = infos;
            listener.onStopQueryStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onStopQueryStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            // TODO 添加日志
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), TrafficTransferAnalystCommon.TRAFFICTRANSFERANALYST_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回站点查询结果。
     * </p>
     * @return 站点查询结果。
     */
    public StopQueryResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理站点查询结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class StopQueryEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onStopQueryStatusChanged(Object sourceObject, EventStatus status);

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

    class DoStopQueryTask implements Runnable {
        private StopQueryParameters params;
        private StopQueryEventListener listener;

        DoStopQueryTask(StopQueryParameters params, StopQueryEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            doStopQuery(params, listener);
        }
    }
}
