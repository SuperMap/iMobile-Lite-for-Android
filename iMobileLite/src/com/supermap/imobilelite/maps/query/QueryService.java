package com.supermap.imobilelite.maps.query;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.http.entity.StringEntity;
import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.maps.Util;
import com.supermap.services.rest.util.JsonConverter;

/**
 * <p>
 * 查询服务基类。
 * </p>
 * <p>
 * 范围、距离、几何、SQL 等查询服务类均继承了该类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public abstract class QueryService {
    ExecutorService executors = Executors.newFixedThreadPool(5);
    static final String LOG_TAG = "com.supermap.imobilelite.measure.QueryService";
    private int timeout = -1; // 代表使用默认超时时间，5秒
    /**
     * <p>
     * 服务地址。
     * </p>
     */ 
    protected String baseUrl;
    QueryResult lastResult;

    /**
     * <p>
     * 根据地图服务地址与服务端完成异步通讯，即发送查询参数，并通过实现QueryEventListener监听器处理查询结果。
     * </p>
     * @param parameters 查询参数
     * @param listener 处理查询结果的QueryEventListener监听器。
     */
    public void process(QueryParameters parameters, QueryEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }
        Future<?> future = this.executors.submit(new DoQueryTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param parameters 查询参数
     * @param listener 处理查询结果的QueryEventListener监听器。
     * @return
     */
    private QueryResult doQuery(QueryParameters parameters, QueryEventListener listener) {
        String url = baseUrl + "/queryResults.json?returnContent=" + parameters.returnContent;
        if (Credential.CREDENTIAL != null) {
            url = url + "&" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        // Log.d(LOG_TAG, "Query url:" + url);
        String entityJosnStr = getVariablesJson(parameters);
        // Log.d(LOG_TAG, "Query entityJosnStr:" + entityJosnStr);
        if (entityJosnStr != null) {
            try {
                String resultStr = Util.post(url, new StringEntity(entityJosnStr, "UTF-8"), timeout);
                if (resultStr != null) {
                    // Log.d(LOG_TAG, "resultStr:" + resultStr);
                    JsonConverter jsConverer = new JsonConverter();
                    if (parameters.returnContent) {
                        lastResult.quertyResultInfo = jsConverer.to(resultStr, QuertyResultInfo.class);
                    } else {
                        lastResult.resourceInfo = jsConverer.to(resultStr, ResourceInfo.class);
                    }
                    listener.onQueryStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
                } else {
                    // Log.d(LOG_TAG, "resultStr null");
                    listener.onQueryStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
                }
            } catch (Exception e) {
                listener.onQueryStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
                Log.w(LOG_TAG, e.getMessage());
            }
        }
        // 发送请求返回结果
        return lastResult;
    }
    
    /**
     * <p>
     * 用户自定义超时时间。
     * </p>
     * @param timeout 用户自定义超时时间。若用户不设置，则使用默认超时间为5秒。0代表无限，即代表不设置超时限制。单位默认为秒。
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    protected abstract String getVariablesJson(QueryParameters parameters);

    protected QueryParameterSet getQueryParameterSet(QueryParameters parameters) {
        QueryParameterSet queryParameters = new QueryParameterSet();
        if (parameters != null) {
            queryParameters.customParams = parameters.customParams;
            queryParameters.expectCount = parameters.expectCount;
            queryParameters.networkType = parameters.networkType;
            queryParameters.queryOption = parameters.queryOption;
            queryParameters.queryParams = parameters.filterParameters;
            queryParameters.startRecord = parameters.startRecord;
        }
        return queryParameters;
    }

    class DoQueryTask implements Runnable {
        private QueryParameters params;
        private QueryEventListener listener;

        DoQueryTask(QueryParameters parameters, QueryEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            doQuery(params, listener);
        }
    }
}