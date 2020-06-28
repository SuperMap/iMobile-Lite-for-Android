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
 * 最佳路径分析服务类。
 * </p>
 * <p>
 * 最佳路径是在网络数据集中指定一些结点，按照顺序访问结点从而求解起止点之间阻抗最小的路径。例如如果要顺序访问1、2、3、4四个结点，则需要分别找到1、2结点间的最佳路径 R1—2，2、3 间的最佳路径 R2—3 和 3、4结点间的最佳路径 R3—4，顺序访问1、2、3、4四个结点的最佳路径就是 R= R1—2 + R2—3 + R3—4。
 * 阻抗就是指从一点到另一点的耗费，在实际应用中我们可以将距离、时间、花费等作为阻抗条件。阻抗最小也就可以理解为从一点到另一点距离最短、时间最少、花费最低等。当两点间距离最短时为最短路径，它是最佳路径问题的一个特例。阻抗值通过 TransportationAnalystParameter.weightFieldName 设置。
 * 计算最佳路径除了受阻抗影响外，还受转向字段的影响。转向值通过 TransportationAnalystParameter.turnWeightField 设置。
 * </p>
 * <p>
 * 最佳路径属于网络分析的一种。网络分析是针对网络数据集的应用。网络数据集为具有拓扑关系的包含了一个网络结点子数据集的线图层，因此网络数据集拥有两个属性表，一个是记录弧段信息的属性表，一个是记录结点信息的属性表。
 * 最佳路径分析和最佳路径分析都是在网络中寻找遍历所有指定结点的最经济的路径，区别在于遍历所有结点的过程中对结点访问顺序不同。最佳路径分析必须按照指定顺序对站点进行访问，而最佳路径分析是无序的路径分析。
 * </p>
 * <p>
 * 该类负责将客户端指定的最佳路径分析参数传递给服务端，并接收服务端返回的结果数据，将最终结果存储于 FindPathResult 中。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindPathService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.findpathservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private FindPathResult lastResult;
    private String baseUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 最佳路径分析服务地址。
     */
    public FindPathService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);// 对网络数据名进行编码
        lastResult = new FindPathResult();
    }

    /**
     * <p>
     * 根据最佳路径分析与服务端完成异步通讯，即发送分析参数，并通过实现FindPathEventListener监听器处理分析结果。
     * </p>
     * @param <T>
     * @param params 最佳路径分析参数信息。
     * @param listener 处理分析结果的FindPathEventListener监听器。
     */
    public <T> void process(FindPathParameters<T> params, FindPathEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || params == null) {
            return;
        }
        if (params.nodes == null || params.nodes.length <= 0) {
            return;
        }
        Future<?> future = this.executors.submit(new DoFindPathTask(params, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param <T>
     * @param params 最佳路径分析参数信息。
     * @return 返回最佳路径分析结果。
     */
    private <T> FindPathResult doFindPath(FindPathParameters<T> params, FindPathEventListener listener) {
        String nodesJosnStr = JsonConverter.toJson(params.nodes);
        String parametersJosnStr = JsonConverter.toJson(params.parameter);
        String countStr = String.valueOf(params.hasLeastEdgeCount);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("nodes", nodesJosnStr));
        paramList.add(new BasicNameValuePair("hasLeastEdgeCount", countStr));
        paramList.add(new BasicNameValuePair("parameter", parametersJosnStr));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = baseUrl + "/path.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            lastResult = Util.get(serviceUrl, FindPathResult.class);
            listener.onFindPathStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onFindPathStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回最佳路径分析结果。
     * </p>
     * @return 分析结果。
     */
    public FindPathResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理最佳路径分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class FindPathEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onFindPathStatusChanged(Object sourceObject, EventStatus status);

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

    class DoFindPathTask<T> implements Runnable {
        private FindPathParameters<T> params;
        private FindPathEventListener listener;

        DoFindPathTask(FindPathParameters<T> params, FindPathEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            doFindPath(params, listener);
        }
    }

}
