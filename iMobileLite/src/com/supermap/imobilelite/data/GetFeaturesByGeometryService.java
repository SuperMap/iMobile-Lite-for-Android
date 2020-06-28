package com.supermap.imobilelite.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.restlet.data.MediaType;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.DataCommon;
import com.supermap.services.components.commontypes.QueryParameter;
import com.supermap.services.rest.encoders.JsonEncoder;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 数据集查询服务类。
 * </p>
 * <p>
 * 几何查询就是查询与指定几何对象符合一定空间关系的矢量要素。
 * 该类负责将客户端指定的数据集查询参数传递给服务端，并接收服务端返回的结果数据。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFeaturesByGeometryService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.getfeaturesbygeometryservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.DataCommon");

    private GetFeaturesResult lastResult;
    private String baseUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 数据集查询服务地址。如 http://ServerIP:8090/iserver/services/data-world/rest/data。
     */
    public GetFeaturesByGeometryService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new GetFeaturesResult();
    }

    /**
     * <p>
     * 根据数据集查询服务地址与服务端完成异步通讯，即发送分析参数，并通过实现GetFeaturesEventListener监听器处理查询结果。
     * </p>
     * @param parameters 数据集查询参数信息。
     * @param listener 处理查询结果的GetFeaturesEventListener监听器。
     */
    public void process(GetFeaturesByGeometryParameters parameters, GetFeaturesEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }

        if (parameters.geometry == null) {
            return;
        }
        Future<?> future = this.executors.submit(new DoGetFeaturesTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 处理数据集查询结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class GetFeaturesEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onGetFeaturesStatusChanged(Object sourceObject, EventStatus status);

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

    class DoGetFeaturesTask implements Runnable {
        private GetFeaturesByGeometryParameters params;
        private GetFeaturesEventListener listener;

        DoGetFeaturesTask(GetFeaturesByGeometryParameters parameters, GetFeaturesEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            try {
                doGetFeatures(params, listener);
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
     * @param parameters 数据集几何查询参数信息。
     * @return 数据集查询结果。
     * @throws IOException 
     */
    private GetFeaturesResult doGetFeatures(GetFeaturesByGeometryParameters parameters, GetFeaturesEventListener listener) throws IOException {
        String fromIndexStr = String.valueOf(parameters.fromIndex);
        String toIndexStr = String.valueOf(parameters.toIndex);
        String returnContentStr = String.valueOf(parameters.returnContent);
        // 请求体参数
        HashMap<String, Object> queryEntity = new HashMap<String, Object>();
        queryEntity.put("datasetNames", parameters.datasetNames);
        QueryParameter queryParameter = new QueryParameter();
        queryParameter.fields = parameters.fields;
        queryEntity.put("queryParameter", queryParameter);
        // queryEntity.put("fields", parameters.fields);
        queryEntity.put("geometry", parameters.geometry);
        queryEntity.put("attributeFilter", parameters.attributeFilter);
        queryEntity.put("spatialQueryMode", parameters.spatialQueryMode);
        if (parameters.attributeFilter == null || "".equals(parameters.attributeFilter)) {
            queryEntity.put("getFeatureMode", "SPATIAL");
        } else {
            queryEntity.put("getFeatureMode", "SPATIAL_ATTRIBUTEFILTER");
        }
        JsonEncoder encoder = new JsonEncoder();
        String queryText = encoder.toRepresentation(MediaType.APPLICATION_JSON, queryEntity).getText();

        // URI参数
        List<NameValuePair> paramListuri = new ArrayList<NameValuePair>();
        paramListuri.add(new BasicNameValuePair("returnContent", returnContentStr));
        paramListuri.add(new BasicNameValuePair("fromIndex", fromIndexStr));
        paramListuri.add(new BasicNameValuePair("toIndex", toIndexStr));
        paramListuri.add(new BasicNameValuePair("_method", "POST"));
        if (Credential.CREDENTIAL != null) {
            paramListuri.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String url = baseUrl + "/featureResults.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
        try {
            String resultStr = Util.post(url, new StringEntity(queryText, "UTF-8"));
            JsonConverter jsConverer = new JsonConverter();
            lastResult = jsConverer.to(resultStr, GetFeaturesResult.class);
            listener.onGetFeaturesStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onGetFeaturesStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), DataCommon.DATA_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回数据集查询结果。
     * </p>
     * @return 数据集查询结果。
     */
    public GetFeaturesResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }
}
