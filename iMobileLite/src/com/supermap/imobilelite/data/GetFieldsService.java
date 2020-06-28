package com.supermap.imobilelite.data;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Constants;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.DataCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 数据集字段查询服务类。
 * </p>
 * <p>
 * 数据集字段查询是指查询数据集中所包含的字段信息。
 * 该类负责与服务端完成异步通信：将客户端指定的数据集字段查询参数传递给服务端，并获取服务端返回的结果信息，从而实现数据集字段查询的操作。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFieldsService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.getfieldsservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.DataCommon");

    private GetFieldsResult lastResult;
    private String baseUrl;

    /**
    * <p>
    * 构造函数。
    * </p>
    * @param url 数据服务地址。如 http://ServerIP:8090/iserver/services/data-world/rest/data。
    */
    public GetFieldsService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new GetFieldsResult();
    }

    /**
     * <p>
     * 根据数据集字段查询服务地址与服务端完成异步通讯，即发送分析参数，并通过实现GetFieldsEventListener监听器处理查询结果。
     * </p>
     * @param parameters 数据集字段查询参数信息。
     * @param listener 处理查询结果的GetFieldsEventListener监听器。
     */
    public void process(FieldParameters parameters, GetFieldsEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }
        if ("".equals(parameters.field) || "".equals(parameters.statisticMode)) {
            return;
        }

        Future<?> future = this.executors.submit(new DoGetFieldsTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 处理数据集字段查询结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class GetFieldsEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onGetFieldsStatusChanged(Object sourceObject, EventStatus status);

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

    class DoGetFieldsTask implements Runnable {
        private FieldParameters params;
        private GetFieldsEventListener listener;

        DoGetFieldsTask(FieldParameters parameters, GetFieldsEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            try {
                doGetFields(params, listener);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param parameters 数据集字段查询参数信息。
     * @return 数据集字段查询结果。
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    private GetFieldsResult doGetFields(FieldParameters parameters, GetFieldsEventListener listener) throws ClientProtocolException, IOException {
        // 查询参数
        String datasourceStr = String.valueOf(parameters.datasource);
        datasourceStr = URLEncoder.encode(datasourceStr, Constants.UTF8); // 对数据源名称编码
        String datasetStr = String.valueOf(parameters.dataset);
        datasetStr = URLEncoder.encode(datasetStr, Constants.UTF8); // 对数据集名称编码
        String url = baseUrl + "/datasources/name/" + datasourceStr + "/datasets/name/" + datasetStr + "/fields.json";
        if (Credential.CREDENTIAL != null) {
            url = url + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        try {
            lastResult = Util.get(url, GetFieldsResult.class);
            listener.onGetFieldsStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onGetFieldsStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), DataCommon.DATA_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回数据集字段查询结果。
     * </p>
     * @return 数据集字段查询结果。
     */
    public GetFieldsResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

}
