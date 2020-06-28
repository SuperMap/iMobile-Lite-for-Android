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
 * 选址分区分析服务类。
 * </p>
 * <p>
 * 选址分区分析是为了确定一个或多个待建设施的最佳或最优位置，使得设施可以用一种最经济有效的方式为需求方提供服务或者商品。选址分区不仅仅是一个选址过程，还要将需求点的需求分配到相应的新建设施的服务区中，因此称之为选址与分区。
 * 在分析过程中使用的需求点都为网络结点，即除了各种类型的中心点所对应的网络结点， 所有网络结点都作为资源需求点参与选址分区分析，如果要排除某部分结点，可以将其设置为障碍点。
 * 例子：如下图所示，某个区域还没有邮局，现在想在这个区域内建立邮局，有15个待选地点（如左图所示，蓝色方框代表 15个候选地点），将在这些待选点中选择7个最佳地点建立邮局。最佳选址要满足，居民点中的居民步行去邮局办理业务的 步行时间要在30分钟以内，同时每个邮局能够服务的居民总人数有限，在同时满足这两个条件的基础上，选址分区分析会 给出以个最佳的选址位置，并且圈出每个邮局的服务区域（如右图所示，红色点表示最后选出的7个建立邮局的最佳位置）。备注：下面两幅中的网络数据集的所有网络结点被看做是该区域的居民点全部参与选址分区分析，居民点中的居民数目即为该居民点所需服务的数量。
 * </p>
 * <p>
 * <img src="../../../../resources/NetworkAnalyst/LocationL.png">
 * <img src="../../../../resources/NetworkAnalyst/LocationR.png">
 * </p>
 * <p> 
 * 该类负责将选址分区分析所需的参数传递至服务端，并获取服务端返回的结果，将其存储于 FindLocationResult 类中。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindLocationService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.findlocationservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private FindLocationResult lastResult;
    private String baseUrl;

    /**
     * <p>
     * 获取选址分区分析结果数据。
     * </p>
     * @return 选址分区分析结果。
     */
    public FindLocationResult getlastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 选址分区分析服务地址 
     */
    public FindLocationService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);// 对换乘网络数据名进行编码
        lastResult = new FindLocationResult();
    }

    /**
     * <p>
     * 根据选址分区分析服务地址与服务端完成异步通讯，即发送分析参数，并通过实现FindLocationEventListener监听器处理查询结果。
     * </p>
     * @param parameters 选址分区分析参数信息。
     * @param listener 处理查询结果的FindLocationEventListener监听器。
     */
    public <T> void process(FindLocationParameters parameters, FindLocationEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }

        if (parameters.supplyCenters == null || parameters.supplyCenters.length <= 0) {
            return;
        }

        // if (parameters.supplyCenters == null || parameters.supplyCenters.length <= 0 || "".equals(parameters.weightName)) {
        // return;
        // }
        Future<?> future = this.executors.submit(new doFindLocationTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 处理选址分区分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class FindLocationEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onFindLocationStatusChanged(Object sourceObject, EventStatus status);

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

    class doFindLocationTask implements Runnable {
        private FindLocationParameters params;
        private FindLocationEventListener listener;

        doFindLocationTask(FindLocationParameters parameters, FindLocationEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            doFindLocation(params, listener);
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param parameters 选址分区分析参数信息。
     * @return 选址分区分析结果。
     */
    private <T> FindLocationResult doFindLocation(FindLocationParameters parameters, FindLocationEventListener listener) {
        String supplyCentersJosnStr = JsonConverter.toJson(parameters.supplyCenters);
        String countStr = String.valueOf(parameters.expectedSupplyCenterCount);
        String fromCenterStr = String.valueOf(parameters.isFromCenter);
        String edgeFeatureStr = String.valueOf(parameters.returnEdgeFeature);
        String edgeGeometryStr = String.valueOf(parameters.returnEdgeGeometry);
        String nodeFeatureStr = String.valueOf(parameters.returnNodeFeature);
        String weightFieldStr = String.valueOf(parameters.turnWeightField);
        String weightNameStr = String.valueOf(parameters.weightName);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("supplyCenters", supplyCentersJosnStr));
        paramList.add(new BasicNameValuePair("expectedSupplyCenterCount", countStr));
        paramList.add(new BasicNameValuePair("isFromCenter", fromCenterStr));
        paramList.add(new BasicNameValuePair("returnEdgeFeature", edgeFeatureStr));
        paramList.add(new BasicNameValuePair("returnEdgeGeometry", edgeGeometryStr));
        paramList.add(new BasicNameValuePair("returnNodeFeature", nodeFeatureStr));
        paramList.add(new BasicNameValuePair("turnWeightField", weightFieldStr));
        paramList.add(new BasicNameValuePair("weightName", weightNameStr));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = baseUrl + "/location.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            lastResult = Util.get(serviceUrl, FindLocationResult.class);
            listener.onFindLocationStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onFindLocationStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
        }
        // 发送请求返回结果
        return lastResult;
    }
}
