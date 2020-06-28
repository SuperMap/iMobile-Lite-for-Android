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
 * 耗费矩阵分析服务类。
 * </p>
 * <p> 
 * 耗费矩阵是根据交通网络分析通用参数（TransportationAnalystParameter）来计算一个二维数组，用来存储任意两点间的资源消耗。
 * </p>
 * <p> 
 * 该类负责将耗费矩阵分析参数传递到服务端，并获取服务端返回的分析结果，将其存放于 ComputeWeightMatrixResult 类中。用户若需获取服务端返回的原始结果，需监听 ComputeWeightMatrixEvent.PROCESS_COMPLETE 事件，该事件中即存有原始结果，又存有最终结果（ComputeWeightMatrixResult）。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ComputeWeightMatrixService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.computeweightmatrixservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private ComputeWeightMatrixResult lastResult;
    private String baseUrl;

    /**
     * <p>
     * 获取耗费矩阵分析结果数据。
     * </p>
     * @return 耗费矩阵分析结果。
     */
    public ComputeWeightMatrixResult getlastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 耗费矩阵分析服务地址 
     */
    public ComputeWeightMatrixService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);// 对换乘网络数据名进行编码
        lastResult = new ComputeWeightMatrixResult();
    }

    /**
     * <p>
     * 根据耗费矩阵分析服务地址与服务端完成异步通讯，即发送分析参数，并通过实现ComputeWeightMatrixEventListener监听器处理查询结果。
     * </p>
     * @param parameters 耗费矩阵分析参数信息。
     * @param listener 处理查询结果的ComputeWeightMatrixEventListener监听器。
     */
    public <T> void process(ComputeWeightMatrixParameters<T> parameters, ComputeWeightMatrixEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }

        if (parameters.nodes == null || parameters.parameter == null) {
            return;
        }
        Future<?> future = this.executors.submit(new doComputeWeightMatrixTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 处理耗费矩阵分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class ComputeWeightMatrixEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onComputeWeightMatrixStatusChanged(Object sourceObject, EventStatus status);

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

    class doComputeWeightMatrixTask<T> implements Runnable {
        private ComputeWeightMatrixParameters<T> params;
        private ComputeWeightMatrixEventListener listener;

        doComputeWeightMatrixTask(ComputeWeightMatrixParameters<T> parameters, ComputeWeightMatrixEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            doComputeWeightMatrix(params, listener);
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param parameters 耗费矩阵分析参数信息。
     * @return 耗费矩阵分析结果。
     */
    private <T> ComputeWeightMatrixResult doComputeWeightMatrix(ComputeWeightMatrixParameters<T> parameters, ComputeWeightMatrixEventListener listener) {
        String pointsJosnStr = JsonConverter.toJson(parameters.nodes);
        String parameterJosnStr = JsonConverter.toJson(parameters.parameter);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("nodes", pointsJosnStr));
        paramList.add(new BasicNameValuePair("parameter", parameterJosnStr));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = baseUrl + "/weightmatrix.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            double[][] weightMatrix = Util.get(serviceUrl, double[][].class);
            lastResult.weightMatrix = weightMatrix;
            listener.onComputeWeightMatrixStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onComputeWeightMatrixStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
        }
        // 发送请求返回结果
        return lastResult;
    }

}
