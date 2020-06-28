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
 * 最近设施分析服务类。
 * </p>
 * <p>
 * 最近设施分析是指在网络上给定一个事件点和一组设施点，查找从事件点到设施点(或从设施点到事件点)以最小耗费能到达的最佳路径。设施点一般为学校、超市、加油站等服务设施；事件点为需要服务设施的事件位置。例如事件发生点是一起交通事故，要求查找在10分钟内能到达的最近医院，超过10分钟能到达的都不予考虑。此例中，事故发生地即是一个事件点，周边的医院则是设施点。最近设施查找实际上也是一种路径分析，因此对路径分析起作用的障碍边、障碍点、转向表、耗费等属性在最近设施分析时同样可设置。
 * </p>
 * <p>
 * 最近设施查找属于网络分析的一种。网络分析是针对网络数据集的应用。网络数据集为具有拓扑关系的包含了一个网络结点子数据集的线图层，因此网络数据集拥有两个属性表，一个是记录弧段信息的属性表，一个是记录结点信息的属性表。
 * </p>
 * <p>
 * 该类负责将客户端指定的最近设施分析参数传递给服务端，并接收服务端返回的结果数据，将最终结果存放于 FindClosestFacilitiesResult 中。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindClosestFacilitiesService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.findclosestfacilitiesservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private FindClosestFacilitiesResult lastResult;
    private String baseUrl;

    /**
     * <p>
     * 获取最近设施分析结果数据。
     * </p>
     * @return 最近设施分析结果。
     */
    public FindClosestFacilitiesResult getlastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 最近设施分析服务地址 
     */
    public FindClosestFacilitiesService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);// 对换乘网络数据名进行编码
        lastResult = new FindClosestFacilitiesResult();
    }

    /**
     * <p>
     * 根据最近设施分析服务地址与服务端完成异步通讯，即发送分析参数，并通过实现FindClosestFacilitiesEventListener监听器处理查询结果。
     * </p>
     * @param parameters 最近设施分析参数信息。
     * @param listener 处理查询结果的FindClosestFacilitiesEventListener监听器。
     */
    public <T> void process(FindClosestFacilitiesParameters<T> parameters, FindClosestFacilitiesEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }

        if (parameters.facilities == null || parameters.facilities.length <= 0 || parameters.event == null) {
            return;
        }
        Future<?> future = this.executors.submit(new doFindClosestFacilitiesTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 处理最近设施分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class FindClosestFacilitiesEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onFindClosestFacilitiesStatusChanged(Object sourceObject, EventStatus status);

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

    class doFindClosestFacilitiesTask<T> implements Runnable {
        private FindClosestFacilitiesParameters<T> params;
        private FindClosestFacilitiesEventListener listener;

        doFindClosestFacilitiesTask(FindClosestFacilitiesParameters<T> parameters, FindClosestFacilitiesEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            doFindClosestFacilities(params, listener);
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param parameters 最近设施分析参数信息。
     * @return 最近设施分析结果。
     */
    private <T> FindClosestFacilitiesResult doFindClosestFacilities(FindClosestFacilitiesParameters<T> parameters, FindClosestFacilitiesEventListener listener) {
        String eventJosnStr = JsonConverter.toJson(parameters.event);
        String facilitiesJosnStr = JsonConverter.toJson(parameters.facilities);
        String countStr = String.valueOf(parameters.expectFacilityCount);
        String fromEventStr = String.valueOf(parameters.fromEvent);
        String maxWeightStr = String.valueOf(parameters.maxWeight);
        String parameterJosnStr = JsonConverter.toJson(parameters.parameter);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("event", eventJosnStr));
        paramList.add(new BasicNameValuePair("facilities", facilitiesJosnStr));
        paramList.add(new BasicNameValuePair("expectFacilityCount", countStr));
        paramList.add(new BasicNameValuePair("fromEvent", fromEventStr));
        paramList.add(new BasicNameValuePair("maxWeight", maxWeightStr));
        paramList.add(new BasicNameValuePair("parameter", parameterJosnStr));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = baseUrl + "/closestfacility.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            lastResult = Util.get(serviceUrl, FindClosestFacilitiesResult.class);
            listener.onFindClosestFacilitiesStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onFindClosestFacilitiesStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
        }
        // 发送请求返回结果
        return lastResult;
    }
}
