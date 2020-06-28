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
 * 服务区分析服务类。
 * </p>
 * <p>
 * 服务区分析属于网络分析的一种，是以指定服务站点为中心，在一定服务范围内查找网络上服务站点能够提供服务的区域范围。例如：计算某快餐店能够在30分钟内送达快餐的区域。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindServiceAreasService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.findserviceareasservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.NetworkAnalystCommon");

    private FindServiceAreasResult lastResult;
    private String basicUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 服务区分析服务地址。
     */
    public FindServiceAreasService(String url) {
        super();
        basicUrl = ServicesUtil.getFormatUrl(url);// 对网络数据名进行编码
        lastResult = new FindServiceAreasResult();
    }

    /**
     * <p>
     * 根据服务区分析与服务端完成异步通讯，即发送分析参数，并通过实现FindServiceAreasEventListener监听器处理分析结果。
     * </p>
     * @param <T>
     * @param params 服务区分析参数信息。
     * @param listener 处理分析结果的FindServiceAreasEventListener监听器。
     */
    public <T> void process(FindServiceAreasParameters<T> params, FindServiceAreasEventListener listener) {
        if (basicUrl == null || "".equals(basicUrl) || params == null) {
            return;
        }
        if (params.centers == null || params.centers.length <= 0) {
            return;
        }
        Future<?> future = this.executors.submit(new DoFindServiceAreasTask(params, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param <T>
     * @param params 服务区分析参数信息。
     * @return 返回服务区分析结果。
     */
    private <T> FindServiceAreasResult doFindServiceAreas(FindServiceAreasParameters<T> params, FindServiceAreasEventListener listener) {
        String centersJosnStr = JsonConverter.toJson(params.centers);
        String parameter = JsonConverter.toJson(params.parameter);
        String weightsJosnStr = JsonConverter.toJson(params.weights);
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("weights", weightsJosnStr));
        paramList.add(new BasicNameValuePair("centers", centersJosnStr));
        paramList.add(new BasicNameValuePair("isFromCenter", String.valueOf(params.isFromCenter)));
        paramList.add(new BasicNameValuePair("isCenterMutuallyExclusive", String.valueOf(params.isCenterMutuallyExclusive)));
        paramList.add(new BasicNameValuePair("parameter", parameter));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = basicUrl + "/servicearea.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            lastResult = Util.get(serviceUrl, FindServiceAreasResult.class);
            listener.onFindServiceAreasStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onFindServiceAreasStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), NetworkAnalystCommon.NETWORKANALYST_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回服务区分析结果。
     * </p>
     * @return 分析结果。
     */
    public FindServiceAreasResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理服务区分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class FindServiceAreasEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onFindServiceAreasStatusChanged(Object sourceObject, EventStatus status);

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

    class DoFindServiceAreasTask<T> implements Runnable {
        private FindServiceAreasParameters<T> params;
        private FindServiceAreasEventListener listener;

        DoFindServiceAreasTask(FindServiceAreasParameters<T> params, FindServiceAreasEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            doFindServiceAreas(params, listener);
        }
    }

}
