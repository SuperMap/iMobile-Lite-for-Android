package com.supermap.imobilelite.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.DataCommon;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.util.ResourceManager;


/**
 * <p>
 * imobile移植类
 * </p>
 */

public class EditFeaturesService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.editfeatureservice";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.DataCommon");

    private EditFeaturesResult lastResult;
    private String baseUrl;

    public EditFeaturesService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new EditFeaturesResult();
    }

    public void process(EditFeaturesParameters parameters, EditFeaturesEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl) || parameters == null) {
            return;
        }

        Future<?> future = this.executors.submit(new DoEditFeaturesTask(parameters, listener));
        listener.setProcessFuture(future);
    }

    public static abstract class EditFeaturesEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        public abstract void onEditFeaturesStatusChanged(Object sourceObject, EventStatus status);

        private boolean isProcessed() {
            return processed.get();
        }

        protected void setProcessFuture(Future<?> future) {
            this.future = future;
        }

        public void waitUntilProcessed() throws InterruptedException, ExecutionException {
            if (future == null) {
                return;
            }
            future.get();
        }
    }

    class DoEditFeaturesTask implements Runnable {
        private EditFeaturesParameters params;
        private EditFeaturesEventListener listener;

        DoEditFeaturesTask(EditFeaturesParameters parameters, EditFeaturesEventListener listener) {
            this.params = parameters;
            this.listener = listener;
        }

        public void run() {
            try {
                doEditFeatures(params, listener);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private EditFeaturesResult doEditFeatures(EditFeaturesParameters parameters, EditFeaturesEventListener listener) throws ClientProtocolException,
            IOException {
        String entityjosnStr = "";
        List<NameValuePair> paramListuri = new ArrayList<NameValuePair>();
        if (parameters.editType == EditType.DELETE) {
            paramListuri.add(new BasicNameValuePair("_method", "DELETE"));
            entityjosnStr = JsonConverter.toJson(parameters.IDs);
        } else if (parameters.editType == EditType.UPDATE) {
            paramListuri.add(new BasicNameValuePair("_method", "PUT"));
            entityjosnStr = JsonConverter.toJson(parameters.features);
        } else {
            paramListuri.add(new BasicNameValuePair("_method", "POST"));
            paramListuri.add(new BasicNameValuePair("isUseBatch", String.valueOf(parameters.isUseBatch)));
            paramListuri.add(new BasicNameValuePair("returnContent", "true"));
            entityjosnStr = JsonConverter.toJson(parameters.features);
        }
        if (Credential.CREDENTIAL != null) {
            paramListuri.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String url = baseUrl + "/features.json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
        try {
            String resultStr = Util.post(url, new StringEntity(entityjosnStr, "UTF-8"));
            if (resultStr != null) {
                JsonConverter jsConverer = new JsonConverter();
                if (parameters.editType == EditType.ADD) {
                    int[] ids = jsConverer.to(resultStr, int[].class);
                    lastResult.IDs = ids;
                    lastResult.succeed = true;
                } else {
                    lastResult = jsConverer.to(resultStr, EditFeaturesResult.class);
                }
            }
            listener.onEditFeaturesStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onEditFeaturesStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), DataCommon.DATA_EXCEPTION, e.getMessage()));
        }
        return lastResult;
    }

    public EditFeaturesResult getLastResult() {
        return lastResult;
    }

}
