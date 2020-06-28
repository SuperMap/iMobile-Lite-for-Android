package com.supermap.imobilelite.querysamples;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;

import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.maps.DefaultItemizedOverlay;
import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.OverlayItem;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.maps.query.QueryEventListener;
import com.supermap.imobilelite.maps.query.QueryResult;
import com.supermap.imobilelite.querysamples.R;
import com.supermap.imobilelite.service.PreferencesService;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.GeometryType;

/**
 * <p>
 * 地图查询类，用于展示地图和可视化查询结果，作为具体查询的父类
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryDemo extends Activity {
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services/map-world/rest/maps/World";
    private static final int QUERY_SUCCESS = 0;
    private static final int QUERY_FAILED = 1;
    protected MapView mapView;
    protected LayerView baseLayerView;
    protected String mapUrl;
    protected Button helpBtn;
    public Drawable drawablePiontMarker;
    public DefaultItemizedOverlay overlay;
    public List<LineOverlay> lineOverlays;
    public List<PolygonOverlay> polygonOverlays;
    private Handler handler;
    public PreferencesService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapquery);
        mapView = (MapView) this.findViewById(R.id.mapview);
        baseLayerView = new LayerView(this);
        Bundle bundle = this.getIntent().getExtras();
        // 设置访问地图的URL
        mapUrl = bundle.getString("map_url");
        if (mapUrl != null && !mapUrl.equals("")) {
            baseLayerView.setURL(mapUrl);
        } else {
            baseLayerView.setURL(DEFAULT_URL);
        }
        mapView.addLayer(baseLayerView);
        mapView.getController().setCenter(new Point2D(116.391468, 39.904491));
        mapView.getController().setZoom(3);
        mapView.setBuiltInZoomControls(true);
        helpBtn = (Button) findViewById(R.id.button_help);
        drawablePiontMarker = getResources().getDrawable(R.drawable.pointmarker);
        service = new PreferencesService(this);
        handler = new ShowQueryResultHandler();
    }

    @Override
    protected void onDestroy() {
        // todo 善后处理
         if (mapView != null) {
         mapView.destroy();
         }
        super.onDestroy();
    }

    public class MyQueryEventListener extends QueryEventListener {

        /**
         * <p>
         * 查询完成回调该接口，用户根据需要处理结果sourceObject
         * </p>
         * @param sourceObject 查询结果
         * @param status 查询结果状态
         */
        @Override
        public void onQueryStatusChanged(Object sourceObject, EventStatus status) {
            Message msg = new Message();
            if (sourceObject instanceof QueryResult && status.equals(EventStatus.PROCESS_COMPLETE)) {
                QueryResult qr = (QueryResult) sourceObject;
                System.out.println("QueryResult totalCount:" + qr.quertyResultInfo.totalCount + ",currentCount:" + qr.quertyResultInfo.currentCount);
                msg.obj = qr;
                msg.what = QUERY_SUCCESS;
            } else {
                msg.what = QUERY_FAILED;
            }
            // 子线程不能直接调用UI相关控件，所以只能通过把结果以消息的方式告知UI主线程展示结果
            handler.sendMessage(msg);
        }
    }

    /**
     * <p>
     * 可视化查询结果的处理器
     * </p>
     * @author ${Author}
     * @version ${Version}
     *
     */
    class ShowQueryResultHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case QUERY_SUCCESS:
                Toast.makeText(QueryDemo.this, "查询成功", Toast.LENGTH_SHORT).show();
                QueryResult queryResult = (QueryResult) msg.obj;
                showQueryResult(queryResult);
                break;
            case QUERY_FAILED:
                Toast.makeText(QueryDemo.this, "查询失败，请检查参数是否正确", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
            }
        }
    }

    /**
     * <p>
     * 可视化查询结果
     * </p>
     * @param qr
     */
    public void showQueryResult(QueryResult qr) {
        clean();
        if (qr != null && qr.quertyResultInfo != null && qr.quertyResultInfo.recordsets != null) {
            for (int i = 0; i < qr.quertyResultInfo.recordsets.length; i++) {
                Feature[] features = qr.quertyResultInfo.recordsets[i].features;
                if (features != null) {
                    for (int j = 0; j < features.length; j++) {
                        Feature feature = features[j];
                        if (feature != null && feature.geometry != null) {
                            Geometry geometry = feature.geometry;
                            GeometryType type = geometry.type;
                            int[] parts = geometry.parts;
                            List<Point2D> points = getPiontsFromGeometry(geometry);// 把iserver的点集合转换成客户端的点集合
                            if (type.equals(GeometryType.POINT)) {
                                showPointOverlay(points);
                            } else if (type.equals(GeometryType.LINE)) {
                                showLineOverlay(points, parts);
                            } else if (type.equals(GeometryType.REGION)) {
                                long s = System.currentTimeMillis();
                                showPolygonOverlay(points, parts);
                                System.out.println("可视化耗时：" + (System.currentTimeMillis() - s) + "ms");
                            }
                        }
                    }
                }
            }
            this.mapView.invalidate();
        }
    }

    /**
     * <p>
     * 可视化点对象
     * </p>
     * @param points
     */
    protected void showPointOverlay(List<Point2D> points) {
        if (overlay == null) {
            overlay = new DefaultItemizedOverlay(drawablePiontMarker);
            mapView.getOverlays().add(overlay);
        }
        for (int k = 0; k < points.size(); k++) {
            OverlayItem overlayItem = new OverlayItem(points.get(k), "", "");
            overlay.addItem(overlayItem);
        }
    }

    /**
     * <p>
     * 可视化线对象
     * </p>
     * @param points
     * @param parts
     */
    void showLineOverlay(List<Point2D> points, int[] parts) {
        // 存储查询记录的几何对象的点
        List<List<Point2D>> pointsLists = getAllPartPionts(points, parts);
        if (pointsLists.size() > 0) {
            if (lineOverlays == null) {
                lineOverlays = new ArrayList<LineOverlay>();
            }
            for (int m = 0; m < pointsLists.size(); m++) {
                List<Point2D> geoPointList = pointsLists.get(m);
                LineOverlay lineOverlay = new LineOverlay(getLinePaint());
                lineOverlay.setData(geoPointList);
                mapView.getOverlays().add(lineOverlay);
                lineOverlays.add(lineOverlay);
            }
        }
    }

    /**
     * <p>
     * 可视化面对像
     * </p>
     * @param points
     * @param parts
     */
    void showPolygonOverlay(List<Point2D> points, int[] parts) {
        List<List<Point2D>> pointsLists = getAllPartPionts(points, parts);
        if (pointsLists.size() > 0) {
            if (polygonOverlays == null) {
                polygonOverlays = new ArrayList<PolygonOverlay>();
            }
            for (int m = 0; m < pointsLists.size(); m++) {
                List<Point2D> geoPointList = pointsLists.get(m);
                PolygonOverlay polygonOverlay = new PolygonOverlay(getPolygonPaint());
                polygonOverlay.setData(geoPointList);
                mapView.getOverlays().add(polygonOverlay);
                polygonOverlays.add(polygonOverlay);
            }
        }
    }

    /**
     * <p>
     * 清除结果的可视化
     * </p>
     */
    public void clean() {
        if (overlay != null) {
            mapView.getOverlays().remove(overlay);
            overlay.clear();
        }
        if (lineOverlays != null) {
            mapView.getOverlays().removeAll(lineOverlays);
            lineOverlays.clear();
        }
        if (polygonOverlays != null) {
            mapView.getOverlays().removeAll(polygonOverlays);
            polygonOverlays.clear();
        }
        this.mapView.invalidate();
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
     * <p>
     * 获取组成地物各个部分的点集合，最后把 所有的点集合 保存在集合中
     * </p>
     * @param points
     * @param parts
     * @return
     */
    public List<List<Point2D>> getAllPartPionts(List<Point2D> points, int[] parts) {
        List<List<Point2D>> pointsLists = new ArrayList<List<Point2D>>();
        if (parts.length > 1) {
            int num = 0;
            for (int m = 0; m < parts.length; m++) {
                int count = parts[m];
                List<Point2D> partList = points.subList(num, num + count);
                pointsLists.add(partList);
                num = num + count;
            }
        } else {
            pointsLists.add(points);
        }
        return pointsLists;
    }

    /**
     * <p>
     * 自定义绘线风格
     * </p>
     * @return
     */
    public static Paint getLinePaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(200, 10, 230, 250));
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        return paint;

    }

    /**
     * <p>
     * 自定义绘面风格
     * </p>
     * @return
     */
    public Paint getPolygonPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);
        return paint;
    }

    public Paint getDefPolygonPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);
        return paint;
    }
}
