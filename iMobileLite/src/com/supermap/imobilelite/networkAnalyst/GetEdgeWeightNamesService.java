package com.supermap.imobilelite.networkAnalyst;

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
import com.supermap.imobilelite.resources.NetworkAnalystCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 获取耗费字段服务类。
 * </p>
 * <p>
 * 用于网络分析的耗费字段由服务端发布，以资源的形式存储在服务器端。客户端通过耗费字段资源的URL就能获取到当前可用的所有耗费字段，然后从中选择本次网络分析需要的耗费字段即可。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetEdgeWeightNamesService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.getEdgeweightnamesservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private EdgeWeightNamesResult lastResult;
    private String basicUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 获取耗费字段服务地址。
     */
    public GetEdgeWeightNamesService(String url) {
        super();
        basicUrl = ServicesUtil.getFormatUrl(url);// 对网络数据名进行编码
        lastResult = new EdgeWeightNamesResult();
    }

    /**
     * <p>
     * 根据耗费字段服务与服务端完成异步通讯，即发送分析参数，并通过实现GetEdgeWeightNamesEventListener监听器处理分析结果。
     * </p>
     * @param listener 处理分析结果的GetEdgeWeightNamesEventListener监听器。
     */
    public <T> void process(GetEdgeWeightNamesEventListener listener) {

        Future<?> future = this.executors.submit(new DoGetEdgeWeightNamesTask(listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @return 返回耗费字段列表。
     */
    private <T> EdgeWeightNamesResult doGetEdgeWeightNames(GetEdgeWeightNamesEventListener listener) {
        String serviceUrl = basicUrl + "/edgeweightnames.json";
        if (Credential.CREDENTIAL != null) {
            serviceUrl = serviceUrl + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        try {
            String[] weightNames = Util.get(serviceUrl, String[].class);
            lastResult.edgeWeightNames = weightNames;
            listener.onGetEdgeWeightNamesStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onGetEdgeWeightNamesStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回耗费字段列表。
     * </p>
     * @return 耗费字段列表。
     */
    public EdgeWeightNamesResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理获取耗费字段结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class GetEdgeWeightNamesEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onGetEdgeWeightNamesStatusChanged(Object sourceObject, EventStatus status);

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

    class DoGetEdgeWeightNamesTask<T> implements Runnable {
        private GetEdgeWeightNamesEventListener listener;

        DoGetEdgeWeightNamesTask(GetEdgeWeightNamesEventListener listener) {
            this.listener = listener;
        }

        public void run() {
            doGetEdgeWeightNames(listener);
        }
    }

}
