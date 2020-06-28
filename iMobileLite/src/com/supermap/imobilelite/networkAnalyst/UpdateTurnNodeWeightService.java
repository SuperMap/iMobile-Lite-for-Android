package com.supermap.imobilelite.networkAnalyst;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.entity.StringEntity;

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
 * 更新转向结点的权值服务类。
 * </p>
 * <p>
 * 用于更新转向结点（TurnNodeWeight）的权值。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class UpdateTurnNodeWeightService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.updateTurnNodeWeightService";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private UpadateWeightResult lastResult;
    private String basicUrl;

    /**
     * <p>
     * 构造函数。
     * 使用转向权重字段资源地址初始化 UpdateTurnNodeWeightService 类的新实例。
     * </p>
     * @param url 转向权重字段资源地址。"http://localhost:8090/iserver/services/components-rest/rest/networkanalyst/RoadNet@Changchun"。
     */
    public UpdateTurnNodeWeightService(String url) {
        super();
        basicUrl = ServicesUtil.getFormatUrl(url);// 对网络数据名进行编码
        lastResult = new UpadateWeightResult();
    }

    /**
     * <p>
     * 根据更新转向结点的权值服务与服务端完成异步通讯，即发送相关参数，并通过实现UpdateTurnNodeWeightEventListener监听器处理更新结果。
     * </p>
     * @param parameter
     * @param listener 处理分析结果的GetEdgeWeightNamesEventListener监听器。
     */
    public <T> void process(TurnNodeWeightParameters parameter, UpdateTurnNodeWeightEventListener listener) {
        if (parameter != null && listener != null) {
            Future<?> future = this.executors.submit(new DoUpdateTurnNodeWeightTask(parameter, listener));
            listener.setProcessFuture(future);
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param params
     * @return 返回更新权值服务结果。
     */
    private <T> UpadateWeightResult doUpdateTurnNodeWeight(TurnNodeWeightParameters params, UpdateTurnNodeWeightEventListener listener) {
        if (params != null && listener != null) {
            // networkanalyst/{networkDataName}/turnnodeweight/{nodeID}/fromedge/{fromEdgeID}/toedge/{toEdgeID}/weightfield/{weightField}
            // networkanalyst/RoadNet@Changchun/turnnodeweight/106/fromedge/6508/toedge/6504/weightfield/TurnCost.json
            String weightfield = "TurnCost"; // 因为 weightfield 目前只有一种，所以这边没有判断其他的
            String serviceUrl = basicUrl + "/turnnodeweight/" + params.nodeID + "/fromedge/" + params.fromEdgeID + "/toedge/" + params.toEdgeID
                    + "/weightfield/" + weightfield + ".json";
            if (Credential.CREDENTIAL != null) {
                serviceUrl = serviceUrl + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
            }
            try {
                String resultStr = Util.put(serviceUrl, new StringEntity(String.valueOf(params.weight), "UTF-8"));
                if (resultStr != null) {
                    JsonConverter jsConverer = new JsonConverter();
                    lastResult = jsConverer.to(resultStr, UpadateWeightResult.class);
                }
                listener.onUpdateTurnNodeWeightStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
            } catch (Exception e) {
                listener.onUpdateTurnNodeWeightStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
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
     * 处理更新转向结点的权值服务的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class UpdateTurnNodeWeightEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onUpdateTurnNodeWeightStatusChanged(Object sourceObject, EventStatus status);

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

    class DoUpdateTurnNodeWeightTask<T> implements Runnable {
        private UpdateTurnNodeWeightEventListener listener;
        private TurnNodeWeightParameters params;

        DoUpdateTurnNodeWeightTask(TurnNodeWeightParameters parameter, UpdateTurnNodeWeightEventListener listener) {
            this.listener = listener;
            this.params = parameter;
        }

        public void run() {
            doUpdateTurnNodeWeight(params, listener);
        }
    }
}
