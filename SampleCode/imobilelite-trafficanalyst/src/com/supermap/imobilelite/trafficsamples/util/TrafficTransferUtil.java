package com.supermap.imobilelite.trafficsamples.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.trafficTransferAnalyst.StopQueryParameters;
import com.supermap.imobilelite.trafficTransferAnalyst.StopQueryResult;
import com.supermap.imobilelite.trafficTransferAnalyst.StopQueryService;
import com.supermap.imobilelite.trafficTransferAnalyst.StopQueryService.StopQueryEventListener;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferGuideItem;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferLine;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferLines;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferPathParameters;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferPathResult;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferPathService;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferPathService.TransferPathEventListener;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferSolution;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferSolutionParameters;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferSolutionResult;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferSolutionService;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferSolutionService.TransferSolutionEventListener;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferStopInfo;
import com.supermap.imobilelite.trafficTransferAnalyst.TransferTactic;

/**
 * <p>
 * 封装交通换乘分析工具类，通过该类方法，可以得到交通换乘的换乘信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TrafficTransferUtil {
    /**
     * <p>
     * 执行交通换乘方案分析，返回方案结果。
     * </p>
     * @param url 交通换乘服务根地址
     * @param transferTatic 换乘策略
     * @param startText 出发站
     * @param endText 终点站
     * @return
     */
    public static SolutionsResult excuteTransferAnalyst(String url, TransferTactic transferTatic, String startText, String endText) {
        SolutionsResult solutionsResult = new SolutionsResult();
        if (url == null || "".equals(url)) {
            return null;
        }
        Log.d("iserver", "Tansfer url:" + url);
        int start = -1;
        int end = -1;
        if (startText != null && !"".equals(startText)) {
            start = new Integer(queryStopId(url, startText));
        }
        if (endText != null && !"".equals(endText)) {
            end = new Integer(queryStopId(url, endText));
        }
        TransferSolutionResult result = new TransferSolutionResult();
        if (start != -1 && end != -1) {
            TransferSolutionService tss = new TransferSolutionService(url);
            TransferSolutionParameters params = new TransferSolutionParameters();
            Integer[] ids = { start, end };
            params.points = ids;
            if (transferTatic != null) {
                params.transferTactic = transferTatic;
            }

            MyTransferSolutionEventListener listener = new MyTransferSolutionEventListener();
            tss.process(params, listener);
            try {
                listener.waitUntilProcessed();
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = listener.getLastResult();
            solutionsResult = solutionAnalyst(url, result, start, end);
            solutionsResult.startStopName = startText;
            solutionsResult.endStopName = endText;
        }
        return solutionsResult;

    }

    /**
     * <p>
     * 根据输入的关键字查询对应的公交站点id
     * </p>
     * @param url 交通换乘服务根地址
     * @param keyword 公交站点名
     * @return
     */
    private static String queryStopId(String url, String keyword) {
        TransferStopInfo[] tfs = queryStop(url, keyword);
        if (tfs != null && tfs.length > 0) {
            return tfs[0].stopID;
        } else {
            return "-1";
        }
    }

    /**
     * <p>
     * 根据输入的关键字查询匹配的公交站点名称集合
     * </p>
     * @param url 交通换乘服务根地址
     * @param keyword 公交站点名
     * @return
     */
    public static List<String> queryStopNames(String url, String keyword) {
        List<String> stopInfos = new ArrayList<String>();
        TransferStopInfo[] tfs = queryStop(url, keyword);
        if (tfs != null && tfs.length > 0) {
            for (int i = 0; i < tfs.length; i++) {
                stopInfos.add(tfs[i].name);
            }
        }
        return stopInfos;
    }

    /**
     * <p>
     *  根据输入的关键字查询匹配的公交站点信息 对象集合
     * </p>
     * @param url 交通换乘服务根地址
     * @param keyword 公交站点名
     * @return
     */
    private static TransferStopInfo[] queryStop(String url, String keyword) {
        StopQueryService sqs = new StopQueryService(url);
        StopQueryParameters params = new StopQueryParameters();
        params.keyWord = keyword;
        params.returnPosition = true;
        MyStopQueryEventListener listener = new MyStopQueryEventListener();
        sqs.process(params, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StopQueryResult queryResult = listener.getLastResult();
        if (queryResult == null) {
            return null;
        }
        return queryResult.transferStopInfos;
    }

    /**
     * <p>
     * 
     * </p>
     * @param url 交通换乘服务根地址
     * @param transferResult 交通换乘方案查询结果对象
     * @param start 出发站id
     * @param end 终点站id
     * @return
     */
    private static SolutionsResult solutionAnalyst(String url, TransferSolutionResult transferResult, int start, int end) {
        SolutionsResult solutionsResult = new SolutionsResult();
        if (transferResult == null || (transferResult != null && transferResult.solutionItems == null)) {
            return solutionsResult;
        }
        List<String> groupList = new ArrayList<String>();
        Map<String, PathsResult> pathsResults = new HashMap<String, PathsResult>();
        TransferSolution[] transferSolutions = transferResult.solutionItems;
        for (int i = 0; i < transferSolutions.length; i++) {
            TransferSolution solution = transferSolutions[i];
            TransferLines[] linesItems = solution.linesItems;
            if (linesItems == null) {
                return solutionsResult;
            }
            int transferCount = solution.transferCount;
            // 一个换乘方案的大标题名，即每条乘车路线名称组合字符串
            String tempLinesName = "";
            TransferLine[] transferlineArray = new TransferLine[linesItems.length];
            // 一个换乘方案包含多个换乘分段，但是分段可能是并列的即transferCount为0，分段可能是串行(需要换乘)即transferCount大于0
            for (int j = 0; j < linesItems.length; j++) {
                TransferLines transferlines = linesItems[j];
                if (transferlines.lineItems.length > 0) {
                    // 目前只选择 换乘分段内可乘车的路线集合的第一个路线
                    transferlineArray[j] = transferlines.lineItems[0];
                }
                if (j > 0) {
                    if (transferCount == 0) {
                        // 不换乘，换乘方案的大标题名包含或的符号
                        tempLinesName += "/";
                    } else if (transferCount > 0) {
                        // 换乘，换乘方案的大标题名包含换乘的符号
                        tempLinesName += " —— ";
                    }
                }
                if (transferlineArray[j] != null) {
                    tempLinesName += transferlineArray[j].lineName;
                }
            }
            // 换乘方案的大标题名集合
            groupList.add(tempLinesName);
            Log.d("iserver", "groupName:" + tempLinesName);
            // 换乘方案对应的具体分段线路描述信息结果集合
            pathsResults.put(tempLinesName, excuteTransferPath(url, transferlineArray, tempLinesName, transferCount, start, end));
        }
        solutionsResult.groupNames = groupList;
        solutionsResult.pathsResults = pathsResults;
        return solutionsResult;
    }

    /**
     * <p>
     * 执行交通换乘路径分析
     * </p>
     * @param url 交通换乘服务根地址
     * @param transferLines 换乘路线信息数组
     * @param groupName 换乘方案路线名
     * @param transferCount 一个方案的路线换乘次数
     * @param start 出发站id
     * @param end 终点站id
     * @return
     */
    private static PathsResult excuteTransferPath(String url, TransferLine[] transferLines, String groupName, int transferCount, int start, int end) {
        // Log.d("iserver", JsonConverter.toJson(transferLines));
        if (transferLines == null || (transferLines != null && transferLines.length == 0)) {
            return null;
        }
        TransferPathService tps = new TransferPathService(url);
        // points=[175,164]&transferLines=[{"lineID":27,"startStopIndex":7,"endStopIndex":9}]
        TransferPathParameters params = new TransferPathParameters();
        Integer[] ids = { start, end };
        params.points = ids;
        if (transferCount == 0) {
            TransferLine[] tempTransferLines = new TransferLine[] { transferLines[0] };
            params.transferLines = tempTransferLines;
        } else {
            params.transferLines = transferLines;
        }
        MyTransferPathEventListener listener = new MyTransferPathEventListener();
        tps.process(params, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TransferPathResult pathResult = listener.getResult();
        // Log.d("iserver", JsonConverter.toJson(pathResult));
        return displayPathResult(pathResult, groupName, transferCount);

    }

    // 显示路径结果
    /**
     * <p>
     * 提取交通换乘路径查询结果信息封装成具体线路导引描述信息结果对象
     * </p>
     * @param pathResult 交通换乘路径查询结果
     * @param groupName 换乘方案路线名
     * @param transferCount 一个方案的路线换乘次数
     * @return
     */
    private static PathsResult displayPathResult(TransferPathResult pathResult, String groupName, int transferCount) {
        PathsResult pathsResult = new PathsResult();
        if (pathResult != null && pathResult.transferGuide != null && pathResult.transferGuide.items != null) {
            List<String> childItems = new ArrayList<String>();
            List<Point2D> points = new ArrayList<Point2D>();
            TransferGuideItem[] transferGuideItems = pathResult.transferGuide.items;
            // Log.d("iserver", JsonConverter.toJson(transferGuideItems));
            TransferGuideItem transferGuideItem = null;
            String item;
            Log.d("iserver", "transferGuideItems.length:" + transferGuideItems.length);
            for (int m = 0; m < transferGuideItems.length; m++) {
                transferGuideItem = transferGuideItems[m];
                if (transferGuideItem.isWalking) {
                    item = "isWalk=true&path=" + "步行" + (int) transferGuideItem.distance + "米，至" + transferGuideItem.endStopName;
                } else {
                    item = "isWalk=fasle&path=" + "乘坐" + transferGuideItem.lineName + "经" + transferGuideItem.passStopCount + "站，在"
                            + transferGuideItem.endStopName + "下车";
                }
                // Log.d("path", item);
                childItems.add(item);
                if (m == 0) {
                    // 第一条线路的所有坐标都加入列表里
                    for (int i = 0; i < transferGuideItem.route.points.length; i++) {
                        points.add(new Point2D(transferGuideItem.route.points[i].x, transferGuideItem.route.points[i].y));

                    }
                } else {
                    // 不是第一条线路从第二个点起都加入列表里，因上一条路线的终点就是当前的起点，无需重复加入
                    for (int i = 1; i < transferGuideItem.route.points.length; i++) {
                        points.add(new Point2D(transferGuideItem.route.points[i].x, transferGuideItem.route.points[i].y));

                    }
                }
            }

            pathsResult.paths = childItems;
            pathsResult.points = points;
        }
        return pathsResult;
    }

    /**
     * <p>
     * 实现 处理线路查询结果的监听器，自己实现处理结果接口
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static class MyTransferPathEventListener extends TransferPathEventListener {
        private TransferPathResult pathResult;

        public MyTransferPathEventListener() {
            super();
            // TODO Auto-generated constructor stub
        }

        public TransferPathResult getResult() {
            return pathResult;
        }

        @Override
        public void onTransferPathStatusChanged(Object sourceObject, EventStatus status) {
            if (sourceObject instanceof TransferPathResult) {
                pathResult = (TransferPathResult) sourceObject;
            }
        }

    }

    /**
     * <p>
     * 实现 处理乘车方案结果的监听器，自己实现处理结果接口
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static class MyTransferSolutionEventListener extends TransferSolutionEventListener {
        private TransferSolutionResult lastResult;

        public MyTransferSolutionEventListener() {
            super();
        }

        public TransferSolutionResult getLastResult() {
            // 发送请求返回结果
            return lastResult;
        }

        @Override
        public void onTransferSolutionStatusChanged(Object sourceObject, EventStatus status) {
            if (sourceObject instanceof TransferSolutionResult) {
                lastResult = (TransferSolutionResult) sourceObject;
            }
        }

    }

    /**
     * <p>
     * 实现 处理站点查询结果的监听器，自己实现处理结果接口
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static class MyStopQueryEventListener extends StopQueryEventListener {
        private StopQueryResult result;

        public MyStopQueryEventListener() {
            super();
        }

        public StopQueryResult getLastResult() {
            // 发送请求返回结果
            return result;
        }

        @Override
        public void onStopQueryStatusChanged(Object sourceObject, EventStatus status) {
            if (sourceObject instanceof StopQueryResult) {
                result = (StopQueryResult) sourceObject;
            }
        }
    }

    /**
     * <p>
     * 一个路线方案的具体线路描述信息结果封装类
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static class PathsResult implements Serializable {
        private static final long serialVersionUID = -3784610110045741350L;
        /**
         * <p>
         * 存储一个路线方案的详细线路描述信息，key为路线方案名
         * </p>
         */
        public List paths = new ArrayList();
        /**
         * <p>
         * 存储一个路线方案的详细线路站点的坐标集合，key为路线方案名
         * </p>
         */
        public List points = new ArrayList();
    }

    /**
     * <p>
     * 封装路线方案集合信息类
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static class SolutionsResult implements Serializable {
        private static final long serialVersionUID = 5429602863043905912L;
        /**
         * <p>
         * 起始站点名称
         * </p>
         */
        public String startStopName;
        /**
         * <p>
         * 终点站点名称
         * </p>
         */
        public String endStopName;

        /**
         * <p>
         * 路线方案集合
         * </p>
         */
        public List<String> groupNames = new ArrayList<String>();

        /**
         * <p>
         * 路线方案的详细路线信息集合
         * </p>
         */
        public Map<String, PathsResult> pathsResults = new HashMap<String, PathsResult>();

    }
}
