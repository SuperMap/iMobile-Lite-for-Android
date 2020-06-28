package com.supermap.imobilelite.spatialsamples.util;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;

import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.maps.BoundingBox;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.spatialAnalyst.BufferDistance;
import com.supermap.imobilelite.spatialAnalyst.BufferEndType;
import com.supermap.imobilelite.spatialAnalyst.BufferSetting;
import com.supermap.imobilelite.spatialAnalyst.DataReturnMode;
import com.supermap.imobilelite.spatialAnalyst.DataReturnOption;
import com.supermap.imobilelite.spatialAnalyst.DatasetBufferAnalystParameters;
import com.supermap.imobilelite.spatialAnalyst.DatasetBufferAnalystResult;
import com.supermap.imobilelite.spatialAnalyst.DatasetBufferAnalystService;
import com.supermap.imobilelite.spatialAnalyst.DatasetBufferAnalystService.DatasetBufferAnalystEventListener;
import com.supermap.imobilelite.spatialAnalyst.DatasetOverlayAnalystParameters;
import com.supermap.imobilelite.spatialAnalyst.DatasetOverlayAnalystResult;
import com.supermap.imobilelite.spatialAnalyst.DatasetOverlayAnalystService;
import com.supermap.imobilelite.spatialAnalyst.DatasetOverlayAnalystService.DatasetOverlayAnalystEventListener;
import com.supermap.imobilelite.spatialAnalyst.DatasetSurfaceAnalystParameters;
import com.supermap.imobilelite.spatialAnalyst.GeometryOverlayAnalystParameters;
import com.supermap.imobilelite.spatialAnalyst.GeometryOverlayAnalystResult;
import com.supermap.imobilelite.spatialAnalyst.GeometryOverlayAnalystService;
import com.supermap.imobilelite.spatialAnalyst.GeometryOverlayAnalystService.GeometryOverlayAnalystEventListener;
import com.supermap.imobilelite.spatialAnalyst.OverlayOperationType;
import com.supermap.imobilelite.spatialAnalyst.RouteCalculateMeasureParameters;
import com.supermap.imobilelite.spatialAnalyst.RouteCalculateMeasureResult;
import com.supermap.imobilelite.spatialAnalyst.RouteCalculateMeasureService;
import com.supermap.imobilelite.spatialAnalyst.RouteCalculateMeasureService.RouteCalculateMeasureEventListener;
import com.supermap.imobilelite.spatialAnalyst.RouteLocatorParameters;
import com.supermap.imobilelite.spatialAnalyst.RouteLocatorResult;
import com.supermap.imobilelite.spatialAnalyst.RouteLocatorService;
import com.supermap.imobilelite.spatialAnalyst.RouteLocatorService.RouteLocatorEventListener;
import com.supermap.imobilelite.spatialAnalyst.SmoothMethod;
import com.supermap.imobilelite.spatialAnalyst.SurfaceAnalystParametersSetting;
import com.supermap.imobilelite.spatialAnalyst.SurfaceAnalystResult;
import com.supermap.imobilelite.spatialAnalyst.SurfaceAnalystService;
import com.supermap.imobilelite.spatialAnalyst.SurfaceAnalystService.SurfaceAnalystEventListener;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.GeometryType;
import com.supermap.services.components.commontypes.QueryParameter;
import com.supermap.services.components.commontypes.Route;
import com.supermap.services.rest.commontypes.LocateType;
import com.supermap.services.rest.util.JsonConverter;

/**
 * <p>
 * 空间分析工具类。通过该类方法，可以得到空间分析结果。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
/**
 * @author admin
 *
 */
/**
 * @author admin
 *
 */
public class SpatialAnalystUtil {

    /**
     * <p>
     * 执行线数据集缓冲区分析
     * </p>
     * @param url
     * @return
     */
    public static DatasetBufferAnalystResult excuteBuffer(String url) {
        // 构造分析参数
        DatasetBufferAnalystParameters parameters = new DatasetBufferAnalystParameters();
        parameters.dataset = "RoadLine2@Changchun";
        QueryParameter filterparameter = new QueryParameter();
        filterparameter.attributeFilter = "NAME='团结路'";
        parameters.filterQueryParameter = filterparameter;
        // 设置分析结果参数
        DataReturnOption resultSet = new DataReturnOption();
        resultSet.dataReturnMode = DataReturnMode.RECORDSET_ONLY;
        parameters.resultSetting = resultSet;
        // 设置缓冲区分析参数：缓冲端点类型、左右缓冲半径一致
        double value = 50.0;
        BufferSetting bufferSet = new BufferSetting();
        bufferSet.endType = BufferEndType.ROUND;
        BufferDistance buffervalue = new BufferDistance();
        buffervalue.value = value;
        bufferSet.leftDistance = buffervalue;
        bufferSet.rightDistance = buffervalue;
        parameters.buffersetting = bufferSet;
        DatasetBufferAnalystService service = new DatasetBufferAnalystService(url);
        MyDatasetBufferAnalystEventListener listener = new MyDatasetBufferAnalystEventListener();
        service.process(parameters, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DatasetBufferAnalystResult result = listener.getResult();
        return result;
    }

    /**
     * <p>
     * 执行数据集叠加分析--求并
     * </p>
     * @param url
     * @return
     */
    public static DatasetOverlayAnalystResult excuteOverlay(String url) {
        // 设置叠加分析参数
        DatasetOverlayAnalystParameters parameters = new DatasetOverlayAnalystParameters();
        parameters.sourceDataset = "BaseMap_R@Jingjin";
        QueryParameter filter1 = new QueryParameter();
        filter1.attributeFilter = "SMID=7";
        parameters.sourceDatasetFilter = filter1;
        parameters.operateDataset = "Neighbor_R@Jingjin";
        QueryParameter filter2 = new QueryParameter();
        filter2.attributeFilter = "SMID=10";
        parameters.operateDatasetFilter = filter2;
        parameters.operation = OverlayOperationType.UNION;
        // 设置分析结果参数
        DataReturnOption resultSet = new DataReturnOption();
        resultSet.dataReturnMode = DataReturnMode.RECORDSET_ONLY;
        parameters.resutlSetting = resultSet;
        parameters.tolerance = 0;
        DatasetOverlayAnalystService service = new DatasetOverlayAnalystService(url);
        MyDatasetOverlayAnalystEventListener listener = new MyDatasetOverlayAnalystEventListener();
        service.process(parameters, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DatasetOverlayAnalystResult result = listener.getResult();
        return result;
    }

    /**
     * <p>
     * 几何对象叠加分析--求交
     * </p>
     * @param url
     * @return
     */
    public static GeometryOverlayAnalystResult excuteGeoOverlay(String url) {
        // 设置叠加分析参数
        GeometryOverlayAnalystParameters parameters = new GeometryOverlayAnalystParameters();
        // 构造几何对象
        Geometry geo = new Geometry();
        com.supermap.services.components.commontypes.Point2D[] points = { new com.supermap.services.components.commontypes.Point2D(117.0, 40.01),
                new com.supermap.services.components.commontypes.Point2D(117.61, 40.06),
                new com.supermap.services.components.commontypes.Point2D(117.66, 39.54),
                new com.supermap.services.components.commontypes.Point2D(116.9, 39.57), new com.supermap.services.components.commontypes.Point2D(117.0, 40.01) };
        geo.points = points;
        geo.type = GeometryType.REGION;
        parameters.sourceGeometry = geo;
        Geometry geo2 = new Geometry();
        com.supermap.services.components.commontypes.Point2D[] points2 = { new com.supermap.services.components.commontypes.Point2D(117.34, 39.84),
                new com.supermap.services.components.commontypes.Point2D(117.9, 39.81),
                new com.supermap.services.components.commontypes.Point2D(117.81, 39.26),
                new com.supermap.services.components.commontypes.Point2D(117.22, 39.24),
                new com.supermap.services.components.commontypes.Point2D(117.34, 39.84) };
        geo2.points = points2;
        geo2.type = GeometryType.REGION;
        parameters.operateGeometry = geo2;
        parameters.operation = OverlayOperationType.CLIP;

        GeometryOverlayAnalystService service = new GeometryOverlayAnalystService(url);
        MyGeometryOverlayAnalystEventListener listener = new MyGeometryOverlayAnalystEventListener();
        service.process(parameters, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GeometryOverlayAnalystResult result = listener.getResult();
        return result;

    }

    /**
     * <p>
     * 提取等值线。
     * </p>
     * @param url 提取等值线分析url
     * @return 分析结果
     */
    public static SurfaceAnalystResult excuteIsoline(String url) {
        // 构建裁剪面对象
        com.supermap.services.components.commontypes.Point2D[] points = new com.supermap.services.components.commontypes.Point2D[] {
                new com.supermap.services.components.commontypes.Point2D(0, 4010338),
                new com.supermap.services.components.commontypes.Point2D(1063524, 4010338),
                new com.supermap.services.components.commontypes.Point2D(1063524, 3150322),
                new com.supermap.services.components.commontypes.Point2D(0, 3150322) };
        Geometry region = new Geometry();
        region.points = points;
        region.type = GeometryType.REGION;
        // 设置表面分析通用参数
        SurfaceAnalystParametersSetting paramSetting = new SurfaceAnalystParametersSetting();
        paramSetting.datumValue = 0;
        paramSetting.interval = 2;
        paramSetting.resampleTolerance = 0;
        paramSetting.smoothMethod = SmoothMethod.BSPLINE;
        paramSetting.smoothness = 1.2;
        paramSetting.clipRegion = region;
        // 设置分析结果参数
        DataReturnOption resultSet = new DataReturnOption();
        resultSet.expectCount = 100;
        // 设置数据集表面分析参数
        DatasetSurfaceAnalystParameters surfaceParams = new DatasetSurfaceAnalystParameters();
        surfaceParams.dataset = "SamplesP@Interpolation";
        surfaceParams.resolution = 3000;
        surfaceParams.zValueFieldName = "AVG_TMP";
        surfaceParams.extractParameter = paramSetting;
        surfaceParams.resultSetting = resultSet;

        SurfaceAnalystService service = new SurfaceAnalystService(url);
        MySurfaceAnalystEventListener listener = new MySurfaceAnalystEventListener();
        service.process(surfaceParams, listener);
        service.setTimeout(0);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SurfaceAnalystResult result = listener.getReult();
        return result;

    }

    /**
     * <p>
     * 点定里程
     * </p>
     * @param url 空间分析url
     * @param routeObj 路由对象
     * @param qureyPoint 指定点
     * @return
     */
    public static RouteCalculateMeasureResult RouteCalculateMeasure(String url, Geometry routeObj, Point2D qureyPoint) {
        com.supermap.services.components.commontypes.Point2D point = new com.supermap.services.components.commontypes.Point2D(qureyPoint.x, qureyPoint.y);
        Route sourceRoute = new Route();
        sourceRoute.type = GeometryType.LINEM;
        sourceRoute.points = routeObj.points;
        sourceRoute.parts = routeObj.parts;
        // 设置路由对象定位空间对象参数
        RouteCalculateMeasureParameters params = new RouteCalculateMeasureParameters();
        Log.d("com.supermap.android.samples.util.SpatialAnalystUtil", JsonConverter.toJson(sourceRoute));
        params.sourceRoute = sourceRoute;
        params.isIgnoreGap = false;
        params.point = point;
        params.tolerance = 10;

        // 与服务端交互
        RouteCalculateMeasureService service = new RouteCalculateMeasureService(url);
        MyRouteCalculateMeasureEventListener listener = new MyRouteCalculateMeasureEventListener();
        Log.d("com.supermap.android.samples.util.SpatialAnalystUtil", JsonConverter.toJson(params));
        service.process(params, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RouteCalculateMeasureResult result = listener.getReult();
        return result;

    }

    /**
     * <p>
     * 里程定点
     * </p>
     * @param url 空间分析url
     * @param routeObj 路由对象
     * @param measure 指定里程点
     * @return
     */
    public static RouteLocatorResult RouteLocatorPoint(String url, Geometry routeObj, double measure) {
        // 设置路由对象定位空间对象参数
        RouteLocatorParameters params = new RouteLocatorParameters();
        Route sourceRoute = new Route();
        sourceRoute.type = GeometryType.LINEM;
        sourceRoute.parts = routeObj.parts;
        // 其routeObj.points中点对象不是Point2D对象而是PointWithMeasure对象，包含x、y和measure三个值
        sourceRoute.points = routeObj.points;
        params.sourceRoute = sourceRoute;
        params.type = LocateType.POINT;
        params.measure = measure;

        // 与服务端交互
        RouteLocatorService service = new RouteLocatorService(url);
        MyRouteLocatorEventListener listener = new MyRouteLocatorEventListener();
        service.process(params, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RouteLocatorResult result = listener.getReult();
        return result;

    }

    /**
     * <p>
     * 里程定线
     * </p>
     * @param url 空间分析url
     * @param routeObj 路由对象
     * @param startMeasure 起始里程点
     * @param endMeasure 终止里程点
     * @return
     */
    public static RouteLocatorResult RouteLocatorLine(String url, Geometry routeObj, double startMeasure, double endMeasure) {
        // 设置路由对象定位空间对象参数
        RouteLocatorParameters params = new RouteLocatorParameters();
        Route sourceRoute = new Route();
        sourceRoute.type = GeometryType.LINEM;
        sourceRoute.parts = routeObj.parts;
        // 其routeObj.points中点对象不是Point2D对象而是PointWithMeasure对象，包含x、y和measure三个值
        sourceRoute.points = routeObj.points;
        params.sourceRoute = sourceRoute;
        params.type = LocateType.LINEM;
        params.startMeasure = startMeasure;
        params.endMeasure = endMeasure;
        // 与服务端交互
        RouteLocatorService service = new RouteLocatorService(url);
        MyRouteLocatorEventListener listener = new MyRouteLocatorEventListener();
        service.process(params, listener);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RouteLocatorResult result = listener.getReult();
        return result;

    }

    /**
     * <p>
     * 提取构成地物的点集合
     * </p>
     * @param geometry
     * @return
     */
    public static List<Point2D> getPiontsFromGeometry(Geometry geometry) {
        List<Point2D> geoPoints = new ArrayList<Point2D>();
        com.supermap.services.components.commontypes.Point2D[] points = geometry.points;
        for (com.supermap.services.components.commontypes.Point2D point : points) {
            Point2D geoPoint = new Point2D(point.x, point.y);
            geoPoints.add(geoPoint);
        }
        return geoPoints;
    }
    
    /**
     *绘点风格
     * @return
     */
    public static Paint getPointPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        return paint;
    }
    
    
    /**
     * 绘线风格，设置线为蓝色
     * @return 
     */
    public static Paint getLinePaintBlue() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        return paint;
    }
    
    /**
     * 绘线风格，设置线为红色
     * @return
     */
    public static Paint getLinePaintRed() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        return paint;
    }
    
    /**
     * 绘面风格 ，设置面为蓝色
     * @return
     */
    public static Paint getPolygonPaintBlue() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);
        return paint;
    }
    
    /**
     * 绘面风格，设置面为红色
     * @return
     */
    public static Paint getPolygonPaintRed() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);
        return paint;
    }
      

    /**
     * <p>
     * 数据集缓冲区分析监控器类
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    static class MyDatasetBufferAnalystEventListener extends DatasetBufferAnalystEventListener {
        private DatasetBufferAnalystResult lastResult;

        public MyDatasetBufferAnalystEventListener() {
            super();
        }

        public DatasetBufferAnalystResult getResult() {
            return lastResult;
        }

        @Override
        public void onDatasetBufferAnalystStatusChanged(Object sourceObject, EventStatus status) {
            // 分析结果
            lastResult = (DatasetBufferAnalystResult) sourceObject;
        }
    }

    /**
     * <p>
     * 数据集叠加分析监控器类
     * </p>
     */
    static class MyDatasetOverlayAnalystEventListener extends DatasetOverlayAnalystEventListener {
        private DatasetOverlayAnalystResult lastResult;

        public MyDatasetOverlayAnalystEventListener() {
            super();
        }

        public DatasetOverlayAnalystResult getResult() {
            return lastResult;
        }

        @Override
        public void onDatasetOverlayAnalystStatusChanged(Object sourceObject, EventStatus status) {
            // 返回分析结果
            lastResult = (DatasetOverlayAnalystResult) sourceObject;
        }
    }

    /**
     * <p>
     * 几何对象叠加分析的监控器类
     * </p>
     * @author ${Author}
     * @version ${Version}
     *
     */
    static class MyGeometryOverlayAnalystEventListener extends GeometryOverlayAnalystEventListener {
        private GeometryOverlayAnalystResult lastResult;

        public MyGeometryOverlayAnalystEventListener() {
            super();
        }

        public GeometryOverlayAnalystResult getResult() {
            return lastResult;
        }

        @Override
        public void onGeometryOverlayAnalystStatusChanged(Object sourceObject, EventStatus status) {
            // 返回分析结果
            lastResult = (GeometryOverlayAnalystResult) sourceObject;
        }
    }

    /**
     * <p>
     * 实现表面分析结果的监听器，自己实现处理结果接口
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    static class MySurfaceAnalystEventListener extends SurfaceAnalystEventListener {
        private SurfaceAnalystResult lastResult;

        public MySurfaceAnalystEventListener() {
            super();
            // TODO Auto-generated constructor stub
        }

        public SurfaceAnalystResult getReult() {
            return lastResult;
        }

        @Override
        public void onSurfaceAnalystStatusChanged(Object sourceObject, EventStatus status) {
            if (sourceObject instanceof SurfaceAnalystResult) {
                lastResult = (SurfaceAnalystResult) sourceObject;
            }
        }
    }

    /**
     * <p>
     * 实现点定里程监控器类，自己实现处理结果接口
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    static class MyRouteCalculateMeasureEventListener extends RouteCalculateMeasureEventListener {
        private RouteCalculateMeasureResult lastResult;

        public MyRouteCalculateMeasureEventListener() {
            super();
            // TODO Auto-generated constructor stub
        }

        public RouteCalculateMeasureResult getReult() {
            return lastResult;
        }

        @Override
        public void onRouteCalculateMeasureStatusChanged(Object sourceObject, EventStatus status) {
            // 分析结果
            lastResult = (RouteCalculateMeasureResult) sourceObject;
        }
    }

    /**
     * <p>
     * 实现里程定点/线的监控器类，自己实现处理结果接口
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    static class MyRouteLocatorEventListener extends RouteLocatorEventListener {
        private RouteLocatorResult lastResult;

        public MyRouteLocatorEventListener() {
            super();
            // TODO Auto-generated constructor stub
        }

        public RouteLocatorResult getReult() {
            return lastResult;
        }

        @Override
        public void onRouteLocatorStatusChanged(Object sourceObject, EventStatus status) {
            // 分析结果
            lastResult = (RouteLocatorResult) sourceObject;
        }
    }
    
    /**
     * <p>
     * 计算当前点串的中心点
     * </p>
     * @param point2Ds 点串数组。
     * @return 返回当前点串数组的中心点。
     */
    public static Point2D calculateCenter(List<Point2D> point2Ds) {
        Point2D point =null;
        double minX = 0;
        double maxX = 0;
        double minY = 0;
        double maxY = 0;

        if ((point2Ds != null) && (point2Ds.size() > 0)) {
            Point2D first = (Point2D) point2Ds.get(0);
            minY = maxY = first.getY();
            minX = maxX = first.getX();
            for (Point2D gp : point2Ds) {
                if (gp.getY() > maxY)
                    maxY = gp.getY();
                else if (gp.getY() < minY) {
                    minY = gp.getY();
                }
                if (gp.getX() < minX)
                    minX = gp.getX();
                else if (gp.getX() > maxX) {
                    maxX = gp.getX();
                }
            }
        }
        point = new Point2D((minX + maxX) / 2, (minY + maxY) / 2);
        return point;
    }
    
    
    /**
     * 计算查询路由对象的X值范围
     * @param point2Ds
     * @return 查询路由对象的最大X值，保留小数点后两位
     */
    public static double calculatemaxM(List<Point2D> point2Ds) {
        double sum = 0;
        double x;
        double x1;
        double y;
        double y1;
        
        if ((point2Ds != null) && (point2Ds.size() > 0)) {
           for(int i=0;i<point2Ds.size()-1;i++){
               x1=point2Ds.get(i+1).getX();
               x=point2Ds.get(i).getX();
               y1=point2Ds.get(i+1).getY();
               y =point2Ds.get(i).getY();
               sum +=Math.sqrt(Math.abs(x1-x)*Math.abs(x1-x)+Math.abs(y1-y)*Math.abs(y1-y));
                                    
           }           
    }
        return Math.round(sum*100)/100.0;
    }
    
}
