package com.supermap.imobilelite.theme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.ThemeCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 移除专题图服务类。
 * </p>
 * <p>
 * 该类负责将专题图资源ID号传递给服务端以此删除指定专题图资源，并获取服务端返回的结果信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class RemoveThemeService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.theme.RemoveThemeService";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.ThemeCommon");
    private RemoveThemeResult lastResult;
    private String baseUrl;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 专题图服务地址。如： http://ServerIP:8090/iserver/services/map-china400/rest/maps/China
     */
    public RemoveThemeService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new RemoveThemeResult();
    }

    /**
     * <p>
     * 根据专题图与服务端完成异步通讯，即发送移除专题图参数，并通过实现RemoveThemeServiceEventListener监听器处理专题图结果。
     * </p>
     * @param <T>
     * @param params 移除专题图参数信息。
     * @param listener 处理移除专题图结果的RemoveThemeServiceEventListener监听器。
     */
    public <T> void process(RemoveThemeParameters params, RemoveThemeServiceEventListener listener) {
        if (StringUtils.isEmpty(baseUrl) || params == null) {
            return;
        }
        if (StringUtils.isEmpty(params.newResourceID)) {
            return;
        }

        Future<?> future = this.executors.submit(new DoRemoveThemeServiceTask(params, listener));
        listener.setProcessFuture(future);
    }

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param params 移除专题图参数信息。
     * @return 返回移除专题图结果。
     * @throws IOException
     */
    private RemoveThemeResult doRemoveThemeService(RemoveThemeParameters params, RemoveThemeServiceEventListener listener) throws IOException {
        String layersID = params.newResourceID;
        // URI参数
        List<NameValuePair> paramListuri = new ArrayList<NameValuePair>();
        paramListuri.add(new BasicNameValuePair("_method", "DELETE"));
        if (Credential.CREDENTIAL != null) {
            paramListuri.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = baseUrl + "/tempLayersSet/" + layersID + ".json?" + URLEncodedUtils.format(paramListuri, HTTP.UTF_8);
        try {
            lastResult = Util.get(serviceUrl, RemoveThemeResult.class);
            listener.onRemoveThemeServiceStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onRemoveThemeServiceStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), ThemeCommon.THEME_EXCEPTION, e.getMessage()));
        }
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回移除专题图结果。
     * </p>
     * @return 分析结果。
     */
    public RemoveThemeResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理专题图结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class RemoveThemeServiceEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onRemoveThemeServiceStatusChanged(Object sourceObject, EventStatus status);

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

    class DoRemoveThemeServiceTask implements Runnable {
        private RemoveThemeParameters params;
        private RemoveThemeServiceEventListener listener;

        DoRemoveThemeServiceTask(RemoveThemeParameters params, RemoveThemeServiceEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            try {
                doRemoveThemeService(params, listener);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
