package com.supermap.imobilelite.networkAnalyst;

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
import com.supermap.imobilelite.resources.NetworkAnalystCommon;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 旅行商分析服务类。
 * </p>
 * <p>
 * 该类负责将客户端指定的旅行商分析参数传递给服务端，并接收服务端返回的结果数据。
 * </p>
 * <p>
 * 旅行商分析是路径分析的一种，它从起点开始（默认为用户指定的第一点）查找能够遍历所有途经点且花费最小的路径。旅行商分析也可以指定到达的终点，这时查找从起点能够遍历所有途经点最后到达终点，且花费最小的路径。
 * </p>
 * <p>
 * 旅行商分析和最佳路径分析都是在网络中寻找遍历所有站点的最经济的路径，区别是在遍历网络所有站点的过程中对结点访问顺序不同。最佳路径分析必须按照指定顺序对站点进行访问，而旅行商分析是无序的路径分析。
 * </p>
 * <p>
 * 该类负责将客户端指定的旅行商分析参数传递给服务端，并接收服务端返回的结果数据，将最终结果存放于 FindTSPPathsResult 类中。用户若需获取服务端返回的原始结果，需监听 FindTSPPathsEvent.PROCESS_COMPLETE 事件，该事件中即存有原始结果，又存有可在客户端显示的最终结果 FindMTSPPathsResult。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindTSPPathsService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.findtsppathsservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private FindTSPPathsResult lastResult;
    private String basicUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 旅行商分析服务地址。
     */
    public FindTSPPathsService(String url) {
        super();
        basicUrl = ServicesUtil.getFormatUrl(url);// 对网络数据名进行编码
        lastResult = new FindTSPPathsResult();
    }

    /**
     * <p>
     * 根据旅行商分析与服务端完成异步通讯，即发送分析参数，并通过实现FindTSPPathsEventListener监听器处理分析结果。
     * </p>
     * @param <T>
     * @param params 旅行商分析参数信息。
     * @param listener 处理分析结果的FindTSPPathsEventListener监听器。
     */
    public <T> void process(FindTSPPathsParameters<T> params, FindTSPPathsEventListener listener) {
        if (basicUrl == null || "".equals(basicUrl) || params == null) {
            return;
        }
        if (params.nodes == null || params.nodes.length <= 0) {
            return;
        }
        Future<?> future = this.executors.submit(new DoFindTSPPathsTask(params, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param <T>
     * @param params 旅行商分析参数信息。
     * @return 返回旅行商分析结果。
     */
    private <T> FindTSPPathsResult doFindTSPPaths(FindTSPPathsParameters<T> params, FindTSPPathsEventListener listener) {
        String nodesJosnStr = JsonConverter.toJson(params.nodes);
        String parameter = JsonConverter.toJson(params.parameter);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("endNodeAssigned", String.valueOf(params.endNodeAssigned)));
        paramList.add(new BasicNameValuePair("nodes", nodesJosnStr));
        paramList.add(new BasicNameValuePair("parameter", parameter));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = basicUrl + "/tsppath.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            lastResult = Util.get(serviceUrl, FindTSPPathsResult.class);
            listener.onFindTSPPathsStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onFindTSPPathsStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回旅行商分析结果。
     * </p>
     * @return 分析结果。
     */
    public FindTSPPathsResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理旅行商分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class FindTSPPathsEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onFindTSPPathsStatusChanged(Object sourceObject, EventStatus status);

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

    class DoFindTSPPathsTask<T> implements Runnable {
        private FindTSPPathsParameters<T> params;
        private FindTSPPathsEventListener listener;

        DoFindTSPPathsTask(FindTSPPathsParameters<T> params, FindTSPPathsEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            doFindTSPPaths(params, listener);
        }
    }

}
