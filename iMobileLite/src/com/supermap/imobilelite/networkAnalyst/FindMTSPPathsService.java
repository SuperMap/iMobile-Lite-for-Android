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
 * 多旅行商分析服务类。
 * </p>
 * <p> 
 * 多旅行商分析属于网络分析的一种，也称为物流配送，是指在网络数据集中，给定 M 个配送中心点和 N 个配送目的地（M，N 为大于零的整数），查找经济有效的配送路径，并给出相应的行走路线。物流配送功能就是解决如何合理分配配送次序和送货路线，使配送总花费达到最小或每个配送中心的花费达到最小。
 * 例如：现在有50个报刊零售地（配送目的地），和4个报刊供应地（配送中心），现寻求这4个供应地向报刊零售地发送报纸的最优路线，属物流配送问题。下面的示意图展示了这个例子的情况以及进行多旅行商分析后的简图。
 * 如下图所示，左图中粉色大圆点代表4个报刊供应地（配送中心），而其他小圆点代表报刊零售地（配送目的地），共有50个；每一类颜色代表一个配送中心的配送方案，包括它所负责的配送目的地、配送次序以及配送线路。
 * 右图为左图中矩形框圈出的第2号配送中心的配送方案：蓝色的标有数字的小圆点是2号配送中心所负责的配送目的地（共有18个），2号配送中心将按照配送目的地上标有数字的顺序依次发送报纸，即先送1号报刊零售地，再送2号报刊零售地，依次类推，并且沿着分析得出的蓝色线路完成配送，回到配送中心。
 * 该类负责将客户端指定的多旅行商分析参数传递给服务端，并接收服务端返回的结果数据，将最终结果存放于 FindMTSPPathsResult 类中。
 * </p>
 * <p> 
 * <img src="../../../../resources/NetworkAnalyst/MTSPathsL.png">
 * <img src="../../../../resources/NetworkAnalyst/MTSPathsR.png">
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindMTSPPathsService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.findmtsppathsservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private FindMTSPPathsResult lastResult;
    private String baseUrl;

    /**
     * <p>
     * 获取多旅行商分析结果数据。
     * </p>
     * @return 多旅行商分析结果。
     */
    public FindMTSPPathsResult getlastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 多旅行商分析服务地址 
     */
    public FindMTSPPathsService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);// 对换乘网络数据名进行编码
        lastResult = new FindMTSPPathsResult();
    }

    /**
     * <p>
     * 根据多旅行商分析服务地址与服务端完成异步通讯，即发送分析参数，并通过实现FindMTSPPathsEventListener监听器处理查询结果。
     * </p>
     * @param parameters 多旅行商分析参数信息。
     * @param listener 处理查询结果的FindMTSPPathsEventListener监听器。
     */
    public <T> void process(FindMTSPPathsParameters<T> parameters, FindMTSPPathsEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }

        if (parameters.centers == null || parameters.centers.length <= 0 || parameters.nodes == null || parameters.nodes.length <= 0) {
            return;
        }
        Future<?> future = this.executors.submit(new doFindMTSPPathsTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 处理多旅行商分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class FindMTSPPathsEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onFindMTSPPathsStatusChanged(Object sourceObject, EventStatus status);

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

    class doFindMTSPPathsTask<T> implements Runnable {
        private FindMTSPPathsParameters<T> params;
        private FindMTSPPathsEventListener listener;

        doFindMTSPPathsTask(FindMTSPPathsParameters<T> parameters, FindMTSPPathsEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            doFindMTSPPaths(params, listener);
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param parameters 多旅行商分析参数信息。
     * @return 多旅行商分析结果。
     */
    private <T> FindMTSPPathsResult doFindMTSPPaths(FindMTSPPathsParameters<T> parameters, FindMTSPPathsEventListener listener) {
        String centersJosnStr = JsonConverter.toJson(parameters.centers);
        String nodesJosnStr = JsonConverter.toJson(parameters.nodes);
        String costStr = String.valueOf(parameters.hasLeastTotalCost);
        String parametersJosnStr = JsonConverter.toJson(parameters.parameter);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("centers", centersJosnStr));
        paramList.add(new BasicNameValuePair("nodes", nodesJosnStr));
        paramList.add(new BasicNameValuePair("hasLeastTotalCost", costStr));
        paramList.add(new BasicNameValuePair("parameter", parametersJosnStr));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = baseUrl + "/mtsppath.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            lastResult = Util.get(serviceUrl, FindMTSPPathsResult.class);
            listener.onFindMTSPPathsStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onFindMTSPPathsStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
        }
        // 发送请求返回结果
        return lastResult;
    }
}
