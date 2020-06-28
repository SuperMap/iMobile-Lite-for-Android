package com.supermap.imobilelite.spatialAnalyst;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.restlet.data.MediaType;

import android.util.Log;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.imobilelite.maps.Constants;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.SpatialAnalystCommon;
import com.supermap.services.rest.encoders.JsonEncoder;
import com.supermap.services.rest.util.JsonConverter;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 数据集邻近分析服务类。
 * </p>
 * <p>
 * 该类负责将客户设置的数据集邻近分析服务参数传递给服务端，并接收服务端返回的数据集邻近分析结果数据。<br>
 * 邻近分析是 GIS 领域里一个最为基础的分析功能之一，邻近分析是用来发现事物之间的某种邻近关系。
 * 进行邻近分析的方法是实现泰森多边形的建立，即根据所提供的点数据建立泰森多边形，从而获得点之间的邻近关系。
 * 泰森多边形的建立如下所示： <br>
 * <img src="../../../../resources/SpatialAnalyst/createThiessen.png"><br>
 * 1.对待建立泰森多边形的点数据进行由左向右，由上到下的扫描，如果某个点距离之前刚刚扫描过的点的距离小于给定的邻近容限值，那么分析时将忽略该点；<br>
 * 2.基于扫描检查后符合要求的所有点建立不规则三角网，即构建 Delaunay 三角网；<br>
 * 3.画出每个三角形边的中垂线，由这些中垂线构成泰森多边形的边，而中垂线的交点是相应的泰森多边形的顶点；<br>
 * 4.用于建立泰森多边形的点的点位将成为相应的泰森多边形的锚点。<br>
 * </p>
 * <p>
 * 以上建立的泰森多边形有如下特点：<br>
 * 1.每个泰森多边形内仅含有一个离散点数据；<br>
 * 2.泰森多边形内的点到相应离散点的距离最近；<br>
 * 3.位于泰森多边形边上的点到其两边的离散点的距离相等。<br>
 * 泰森多边形可用于定性分析、统计分析、邻近分析等。例如，可以用离散点的性质来描述泰森多边形区域的性质；可用离散点的数据来计算泰森多边形区域的数据；判断一个离散点与其它哪些离散点相邻时，可根据泰森多边形直接得出，且若泰森多边形是n边形，则就与n个离散点相邻；当某一数据点落入某一泰森多边形中时，它与相应的离散点最邻近，无需计算距离。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class DatasetThiessenAnalystService {
    private ExecutorService executors = Executors.newFixedThreadPool(5);
    private static final String LOG_TAG = "com.supermap.imobilelite.data.DatasetThiessenAnalystService";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.SpatialAnalystCommon");
    private ThiessenAnalystResult lastResult;
    private String baseUrl;
    private int timeout = -1; // 代表使用默认超时时间，5秒

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 数据集邻近区分析服务地址。如 http://ServerIP:8090/iserver/services/spatialanalyst-sample/restjsr/spatialanalyst
     */
    public DatasetThiessenAnalystService(String url) {
        super();
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new ThiessenAnalystResult();
    }

    /**
     * <p>
     * 根据数据集邻近区与服务端完成异步通讯，即发送分析参数，并通过实现DatasetThiessenAnalystEventListener监听器处理分析结果。
     * </p>
     * @param params 数据集邻近区参数信息。
     * @param listener 处理分析结果的DatasetThiessenAnalystEventListener监听器。
     */
    public void process(DatasetThiessenAnalystParameters params, DatasetThiessenAnalystEventListener listener) {
        if (StringUtils.isEmpty(baseUrl) || params == null) {
            return;
        }
        if (StringUtils.isEmpty(params.dataset)) {
            return;
        }

        Future<?> future = this.executors.submit(new DoDatasetThiessenAnalystTask(params, listener));
        listener.setProcessFuture(future);
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

    /**
     * <p>
     * 重新组装请求的地址，发送请求并处理请求。
     * </p>
     * @param params 数据集邻近区分析参数信息。
     * @return 返回数据集邻近区分析结果。
     * @throws IOException
     */
    private ThiessenAnalystResult doDatasetThiessenAnalyst(DatasetThiessenAnalystParameters params, DatasetThiessenAnalystEventListener listener)
            throws IOException {
        // 对数据集名称编码
        String datasetStr = params.dataset;
        datasetStr = URLEncoder.encode(datasetStr, Constants.UTF8);
        // 请求体参数
        HashMap<String, Object> queryEntity = new HashMap<String, Object>();
        queryEntity.put("filterQueryParameter", params.filterQueryParameter);
        queryEntity.put("clipRegion", params.clipRegion);
        queryEntity.put("createResultDataset", params.createResultDataset);
        queryEntity.put("resultDatasetName", params.resultDatasetName);
        queryEntity.put("resultDatasourceName", params.resultDatasourceName);
        queryEntity.put("returnResultRegion", params.returnResultRegion);
        JsonEncoder encoder = new JsonEncoder();
        String queryText = encoder.toRepresentation(MediaType.APPLICATION_JSON, queryEntity).getText();
        // URI参数
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("asynchronousReturn", "false"));
        paramList.add(new BasicNameValuePair("returnContent", "true"));
        if (Credential.CREDENTIAL != null) {
            paramList.add(new BasicNameValuePair(Credential.CREDENTIAL.name, Credential.CREDENTIAL.value));
        }
        String serviceUrl = baseUrl + "/datasets/" + datasetStr + "/thiessenpolygon.json?" + URLEncodedUtils.format(paramList, HTTP.UTF_8);// 参数编码
        try {
            String resultStr = Util.post(serviceUrl, Util.newJsonUTF8StringEntity(queryText), this.timeout);
            // 请求返回成功，则解析结果。请求失败返回null，则lastResult直接用new的空对象
            if (!StringUtils.isEmpty(resultStr)) {
                JsonConverter jsConverer = new JsonConverter();
                lastResult = jsConverer.to(resultStr, ThiessenAnalystResult.class);
            }
            listener.onDatasetThiessenAnalystStatusChanged(lastResult, EventStatus.PROCESS_COMPLETE);
        } catch (Exception e) {
            listener.onDatasetThiessenAnalystStatusChanged(lastResult, EventStatus.PROCESS_FAILED);
            Log.w(LOG_TAG, resource.getMessage(this.getClass().getSimpleName(), SpatialAnalystCommon.SPATIALANALYST_EXCEPTION, e.getMessage()));
        }

        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 返回数据集邻近区分析结果。
     * </p>
     * @return 分析结果。
     */
    public ThiessenAnalystResult getLastResult() {
        // 发送请求返回结果
        return lastResult;
    }

    /**
     * <p>
     * 处理数据集邻近区分析结果的监听器抽象类。
     * 提供了等待 监听器执行完毕的接口。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract class DatasetThiessenAnalystEventListener {
        private AtomicBoolean processed = new AtomicBoolean(false);
        private Future<?> future;

        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onDatasetThiessenAnalystStatusChanged(Object sourceObject, EventStatus status);

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

    class DoDatasetThiessenAnalystTask implements Runnable {
        private DatasetThiessenAnalystParameters params;
        private DatasetThiessenAnalystEventListener listener;

        DoDatasetThiessenAnalystTask(DatasetThiessenAnalystParameters params, DatasetThiessenAnalystEventListener listener) {
            this.params = params;
            this.listener = listener;
        }

        public void run() {
            try {
                doDatasetThiessenAnalyst(params, listener);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
