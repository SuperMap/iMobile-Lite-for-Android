package com.supermap.imobilelite.networkAnalyst;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.entity.StringEntity;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.NetworkAnalystCommon;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.util.ResourceManager;
import android.util.Log;

/**
 * <p>
 * 更新弧度权值服务类。
 * </p>
 * <p>
 * 用于更新弧度权值（EdgeWeight）的权值。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class UpdateEdgeWeightService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.updateEdgeWeightService";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private UpadateWeightResult lastResult;
    private String basicUrl;

    /**
     * <p>
     * 构造函数。
     * 使用服务地址 url 初始化 UpdateEdgeWeightService 类的新实例。
     * </p>
     * @param url 弧度权值资源地址。例如:"http://localhost:8090/iserver/services/components-rest/rest/networkanalyst/RoadNet@Changchun"。 
     */
    public UpdateEdgeWeightService(String url) {
        super();
        basicUrl = ServicesUtil.getFormatUrl(url);// 对网络数据名进行编码
        lastResult = new UpadateWeightResult();
    }

    /**
     * <p>
     * 根据更新弧度权值服务与服务端完成异步通讯，即发送相关参数，并通过实现UpdateEdgeWeightEventListener监听器处理更新结果。
     * </p>
     * @param listener 处理分析结果的UpdateEdgeWeightEventListener监听器。
     */
    public <T> void process(EdgeWeightParameters parameter, UpdateEdgeWeightEventListener listener) {
        if (parameter != null && listener != null) {
            Future<?> future = this.executors.submit(new DoUpdateEdgeWeightTask(parameter, listener));
            listener.setProcessFuture(future);
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param params 弧段权值参数对象
     * @param listener 
     * @return 返回更新权值服务结果。
     */
    private <T> UpadateWeightResult doUpdateEdgeWeight(EdgeWeightParameters params, UpdateEdgeWeightEventListener listener) {
        if (params != null && listener != null) {
            // networkanalyst/{networkDataName}/edgeweight/{edgeID}/fromnode/{fromNodeID}/tonode/{toNodeID}/weightfield/{weightField}
            // /networkanalyst/RoadNet@Changchun/edgeweight/20/fromnode/26/tonode/109/weightfield/time.json
            String weightfield = "time";
            if (EdgeWeightFieldType.LENGTH.equals(params.weightField)) {
                weightfield = "length";
            }
            String serviceUrl = basicUrl + "/edgeweight/" + params.edgeID + "/fromnode/" + params.fromNodeID + "/tonode/" + params.toNodeID + "/weightfield/"
                    + weightfield + ".json";
            if (Credential.CREDENTIAL != null) {
                serviceUrl = serviceUrl + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
            }
            try {
                String resultStr = Util.put(serviceUrl, new StringEntity(String.valueOf(params.weight), "UTF-8"));
                if (resultStr != null) {
                    JsonConverter jsConverer = new JsonConverter();
                    lastResult = jsConverer.to(resultStr, UpadateWeightResult.class);
                }
                listener.onUpdateEdgeWeightStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
            } catch (Exception e) {
                listener.onUpdateEdgeWeightStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
                Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
            }
        }
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回更新权值服务结果。
     * </p>
     * @return 更新权值服务结果。
     */
    public UpadateWeightResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理获更新弧度权值服务的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class UpdateEdgeWeightEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onUpdateEdgeWeightStatusChanged(Object sourceObject, EventStatus status);

        private boolean isProcessed() {
            return processed.get();
        }

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

    class DoUpdateEdgeWeightTask<T> implements Runnable {
        private UpdateEdgeWeightEventListener listener;
        private EdgeWeightParameters params;

        DoUpdateEdgeWeightTask(EdgeWeightParameters parameter, UpdateEdgeWeightEventListener listener) {
            this.listener = listener;
            this.params = parameter;
        }

        public void run() {
            doUpdateEdgeWeight(params, listener);
        }
    }
}
