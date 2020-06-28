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
 * 交通换乘方案查询服务类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TransferSolutionService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.transfersolutionservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.TrafficTransferAnalystCommon");

    private TransferSolutionResult lastResult;
    private String queryUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 乘车方案查询服务地址。
     */
    public TransferSolutionService(String url) {
        super();
        queryUrl = ServicesUtil.getFormatUrl(url);// 对换乘网络数据名进行编码
        lastResult = new TransferSolutionResult();
    }

    /**
     * <p>
     * 根据交通换乘方案查询与服务端完成异步通讯，即发送分析参数，并通过实现TransferSolutionEventListener监听器处理查询结果。
     * </p>
     * @param <T>
     * @param params 乘车方案参数信息。
     * @param listener 处理查询结果的TransferSolutionEventListener监听器。
     */
    public <T> void process(TransferSolutionParameters<T> params, TransferSolutionEventListener listener) {
        if (queryUrl == null || "".equals(queryUrl) || params == null) {
            return;
        }
        if (params.points == null || params.points.length <= 0) {
            return;
        }
        Future<?> future = this.executors.submit(new DoTransferSolutionTask(params, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param <T>
     * @param params 乘车方案参数信息。
     * @return 返回乘车方案。
     */
    private <T> TransferSolutionResult doTransferSolution(TransferSolutionParameters<T> params, TransferSolutionEventListener listener) {
        String pointsJosnStr = JsonConverter.toJson(params.points);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("solutionCount", String.valueOf(params.solutionCount)));
        paramList.add(new BasicNameValuePair("transferTactic", params.transferTactic.toString()));
        paramList.add(new BasicNameValuePair("transferPreference", params.transferPreference.toString()));
        paramList.add(new BasicNameValuePair("walkingRatio", String.valueOf(params.walkingRatio)));
        paramList.add(new BasicNameValuePair("points", pointsJosnStr));
        
        if (params.evadeLines != null && params.evadeLines.length > 0) {
            String elJosnStr = JsonConverter.toJson(params.evadeLines);
            paramList.add(new BasicNameValuePair("evadeLines", elJosnStr));
        }
        if (params.evadeStops != null && params.evadeStops.length > 0) {
            String esJosnStr = JsonConverter.toJson(params.evadeStops);
            paramList.add(new BasicNameValuePair("evadeStops", esJosnStr));
        }
        if (params.priorLines != null && params.priorLines.length > 0) {
            String plJosnStr = JsonConverter.toJson(params.priorLines);
            paramList.add(new BasicNameValuePair("priorLines", plJosnStr));
        }
        if (params.priorStops != null && params.priorStops.length > 0) {
            String psJosnStr = JsonConverter.toJson(params.priorStops);
            paramList.add(new BasicNameValuePair("priorStops", psJosnStr));
        }
        // String elJosnStr = JsonConverter.toJson(params.evadeLines);
        // String esJosnStr = JsonConverter.toJson(params.evadeStops);
        // String plJosnStr = JsonConverter.toJson(params.priorLines);
        // String psJosnStr = JsonConverter.toJson(params.priorStops);
        // paramList.add(new BasicNameValuePair("evadeLines", elJosnStr));
        // paramList.add(new BasicNameValuePair("evadeStops", esJosnStr));
        // paramList.add(new BasicNameValuePair("priorLines", plJosnStr));
        // paramList.add(new BasicNameValuePair("priorStops", psJosnStr));
        
        if (params.travelTime != null && !"".equals(params.travelTime)) {
            paramList.add(new BasicNameValuePair("travelTime", params.travelTime));
        }
        
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String transferSolutionUrl = queryUrl + "/solutions.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            lastResult = Util.get(transferSolutionUrl, TransferSolutionResult.class);
            listener.onTransferSolutionStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onTransferSolutionStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), TrafficTransferAnalystCommon.TRAFFICTRANSFERANALYST_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回乘车方案查询结果。
     * </p>
     * @return 乘车方案。
     */
    public TransferSolutionResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理乘车方案查询结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class TransferSolutionEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onTransferSolutionStatusChanged(Object sourceObject, EventStatus status);

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

    class DoTransferSolutionTask<T> implements Runnable {
        private TransferSolutionParameters<T> params;
        private TransferSolutionEventListener listener;

        DoTransferSolutionTask(TransferSolutionParameters<T> params, TransferSolutionEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            doTransferSolution(params, listener);
        }
    }
}
