package com.supermap.imobilelite.datasamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.supermap.imobilelite.data.GetFeaturesResult;
import com.supermap.imobilelite.maps.DefaultItemizedOverlay;
import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.OverlayItem;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.datasamples.R;
import com.supermap.imobilelite.datasamples.util.DataUtil;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.GeometryType;

public class DataQueryDemo extends Activity {
    protected static final String TAG = "com.supermap.android.samples.edit.DataQueryDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-world/rest/maps/World";
    protected static final String DEFAULT_DATA_URL = "/data-world/rest/data";
    private static final int DATAQUERY_DIALOG = 0;
    private static final int README_DIALOG = 9;
    private static final int QUERY_SUCCESS = 0;
    private static final int QUERY_FAILED = 1;
    private Handler handler;
    private DataQueryDialog dataQueryDialog;
    protected Dialog progressDialog;
    protected MapView mapView;
    private LayerView baseLayerView;
    private PreferencesService service;
    private String dataServiceUrl;
    private Bundle bundle;
    private Drawable pointMarker;
    // 默认的绘制面对象
    private PolygonOverlay polygonOverlay;
    // 默认的绘制线对象
    private LineOverlay lineOverlay;
    // 点查询触屏Overlay，用来获取点坐标
    private PointTouchOverlay pointTouchOverlay;
    // 线面查询触屏Overlay，用来获取点坐标
    private TouchOverlay touchOverlay;
    // 存放绘制线或面的点集合
    private List<Point2D> geoPoints = new ArrayList<Point2D>();
    // 绘制要素状态
    private int drawStatic = -1;// 0代表绘制线，1表示绘制面
    private int queryStatic = -1;// 0代表几何查询，1代表缓冲查询，-1无实际意义
    // 触屏的x坐标
    private int touchX;
    // 触屏的y坐标
    private int touchY;
    private List<PolygonOverlay> polygonOverlays = new ArrayList<PolygonOverlay>();
    private List<DefaultItemizedOverlay> overlays = new ArrayList<DefaultItemizedOverlay>();
    protected int titleBarHeight;
    private Button helpBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        bundle = this.getIntent().getExtras();
        // 设置访问地图的URL和数据编辑的URL
        String serviceUrl = bundle.getString("service_url");
        String mapUrl = "";
        if (serviceUrl == null || "".equals(serviceUrl)) {
            mapUrl = DEFAULT_URL + DEFAULT_MAP_URL;
            dataServiceUrl = DEFAULT_URL + DEFAULT_DATA_URL;
        } else {
            mapUrl = serviceUrl + DEFAULT_MAP_URL;
            dataServiceUrl = serviceUrl + DEFAULT_DATA_URL;
        }
        baseLayerView = new LayerView(this, mapUrl);
        mapView.addLayer(baseLayerView);
        mapView.getController().setZoom(2);
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        // 获取标题栏高度
        mapView.post(new Runnable() {
            public void run() {
                titleBarHeight = initHeight();

            }
        });
        helpBtn = (Button) findViewById(R.id.button_help);
        helpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(README_DIALOG);
            }
        });

        // 创建数据查询操作提示窗口对象
        dataQueryDialog = new DataQueryDialog(this, R.style.dialogTheme);
        dataQueryDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        polygonOverlay = new PolygonOverlay(getPolygonPaint());
        lineOverlay = new LineOverlay(getLinePaint());
        pointTouchOverlay = new PointTouchOverlay();
        touchOverlay = new TouchOverlay();
        pointMarker = getResources().getDrawable(R.drawable.point_marker);
        service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("dataquery");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
        handler = new QueryFinished();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 计算标题栏的高度
     * @return
     */
    private int initHeight() {
        Rect rect = new Rect();
        Window window = getWindow();
        mapView.getWindowVisibleDisplayFrame(rect);
        // 状态栏的高度
        int statusBarHight = rect.top;
        // 标题栏跟状态栏的总体高度
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // 标题栏的高度
        int titleBarHeight = contentViewTop - statusBarHight;
        return titleBarHeight;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 创建数据集查询菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.idquery_text);
        menu.add(0, 2, 0, R.string.geometryquery_text);
        menu.add(0, 3, 0, R.string.bufferquery_text);
        menu.add(0, 4, 0, R.string.sqlquery_text);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击数据集查询菜单触发事件
        case 1:// ID查询
            queryStatic = -1;
            showProgressDialog();
            clearAllTouchOverlay();
            clear();
            new Thread(new QueryRunnable(DataUtil.excute_idsQuery(dataServiceUrl))).start();
            break;
        case 2:// 几何查询
            queryStatic = 0;
            clearAllTouchOverlay();
            clear();
            showDialog(DATAQUERY_DIALOG);
            dataQueryDialog.setReadmeText(R.string.geometryquery_text);
            break;
        case 3:// 缓冲区查询
            queryStatic = 1;
            clearAllTouchOverlay();
            clear();
            showDialog(DATAQUERY_DIALOG);
            dataQueryDialog.setReadmeText(R.string.bufferquery_text);
            break;
        case 4:// SQL查询
            queryStatic = -1;
            showProgressDialog();
            clearAllTouchOverlay();
            clear();
            new Thread(new QueryRunnable(DataUtil.excute_geoSQL(dataServiceUrl))).start();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 点查询
    public void pointQuery() {
        drawStatic = -1;
        if (!mapView.getOverlays().contains(pointTouchOverlay)) {
            mapView.getOverlays().add(pointTouchOverlay);
        }
        if (mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().remove(touchOverlay);
        }
        mapView.setUseScrollEvent(true);
        mapView.invalidate();

    }

    // 线查询
    public void lineQuery() {
        mapView.setUseScrollEvent(false);
        lineOverlay.setData(new ArrayList<Point2D>());
        polygonOverlay.setData(new ArrayList<Point2D>());
        geoPoints.clear();
        drawStatic = 0;
        // 添加LineOverlay绘线，地图刷新
        if (!mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().add(touchOverlay);
        }
        if (mapView.getOverlays().contains(pointTouchOverlay)) {
            mapView.getOverlays().remove(pointTouchOverlay);
        }
        mapView.invalidate();

    }

    // 面查询
    public void regionQuery() {
        mapView.setUseScrollEvent(false);
        lineOverlay.setData(new ArrayList<Point2D>());
        polygonOverlay.setData(new ArrayList<Point2D>());
        geoPoints.clear();
        drawStatic = 1;
        // 添加PolygonOverlay绘面，地图刷新
        if (!mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().add(touchOverlay);
        }
        if (mapView.getOverlays().contains(pointTouchOverlay)) {
            mapView.getOverlays().remove(pointTouchOverlay);
        }
        mapView.invalidate();

    }

    // 清除
    public void clear() {
        clearAllTouchOverlay();
        lineOverlay.setData(new ArrayList<Point2D>());
        polygonOverlay.setData(new ArrayList<Point2D>());
        geoPoints.clear();
        if (polygonOverlays.size() != 0) {
            mapView.getOverlays().remove(polygonOverlays);
            polygonOverlays.clear();
        }
        if (overlays.size() != 0) {
            for (int i = 0; i < overlays.size(); i++) {
                // overlays.get(i).clear();
                mapView.getOverlays().remove(overlays.get(i));
            }
            overlays.clear();
        }
        mapView.invalidate();
    }

    // 清除所有的touchOverlay
    public void clearAllTouchOverlay() {
        if (mapView.getOverlays().contains(pointTouchOverlay)) {
            mapView.getOverlays().remove(pointTouchOverlay);
        }
        if (mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().remove(touchOverlay);
        }
        mapView.setUseScrollEvent(true);
    }

    /**
     *  弹出进度条dialog
     */
    void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(DataQueryDemo.this, getResources().getString(R.string.treating), getResources().getString(R.string.querying),
                    true);
        } else {
            progressDialog.show();
        }
    }

    /**
     * 启动IDs查询或SQL查询的Runnable接口
     * 
     */
    class QueryRunnable implements Runnable {
        GetFeaturesResult queryResult = null;

        public QueryRunnable(GetFeaturesResult result) {
            this.queryResult = result;

        }

        @Override
        public void run() {
            Message msg = new Message();
            if (queryResult != null) {
                msg.obj = queryResult;
                msg.what = QUERY_SUCCESS;
            } else {
                msg.what = QUERY_FAILED;
            }
            handler.sendMessage(msg);
        }

    }

    /**
     * 启动点查询的Runnable接口
     *
     */
    class PointQueryRunnable implements Runnable {
        GetFeaturesResult queryResult = null;
        Point2D touchPoint;

        public PointQueryRunnable(Point2D touchPoint) {
            this.touchPoint = touchPoint;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            Geometry geometry = new Geometry();
            com.supermap.services.components.commontypes.Point2D[] points = new com.supermap.services.components.commontypes.Point2D[] { new com.supermap.services.components.commontypes.Point2D(
                    touchPoint.x, touchPoint.y) };
            geometry.points = points;
            geometry.type = GeometryType.POINT;
            if (queryStatic == 0) {
                queryResult = DataUtil.excute_geometryQuery(dataServiceUrl, geometry);
            } else if (queryStatic == 1) {
                queryResult = DataUtil.excute_bufferQuery(dataServiceUrl, geometry);

            }
            Message msg = new Message();
            if (queryResult != null) {
                msg.obj = queryResult;
                msg.what = QUERY_SUCCESS;
            } else {
                msg.what = QUERY_FAILED;
            }
            handler.sendMessage(msg);
        }

    }

    /**
     * 启动线查询或面查询的Runnable接口
     *
     */
    public class DrawQueryRunnable implements Runnable {
        GetFeaturesResult queryResult = null;

        public DrawQueryRunnable() {

        }

        @Override
        public void run() {
            // 提交线查询事件请求
            clearAllTouchOverlay();
            if (drawStatic == 0) {
                lineOverlay.setData(new ArrayList<Point2D>());
                // mapView.invalidate();
                if (geoPoints.size() >= 2) {
                    com.supermap.services.components.commontypes.Point2D[] pts = new com.supermap.services.components.commontypes.Point2D[geoPoints.size()];
                    for (int j = 0; j < geoPoints.size(); j++) {
                        pts[j] = new com.supermap.services.components.commontypes.Point2D(geoPoints.get(j).x, geoPoints.get(j).y);
                    }
                    Geometry geometry = new Geometry();
                    geometry.points = pts;
                    geometry.parts = new int[] { pts.length };
                    geometry.type = GeometryType.LINE;
                    if (queryStatic == 0) {// 0代表几何查询
                        Log.d("iserver", "excuteGeometryQuery");
                        queryResult = DataUtil.excute_geometryQuery(dataServiceUrl, geometry);
                    } else if (queryStatic == 1) {
                        Log.d("iserver", "excuteBufferQuery");
                        queryResult = DataUtil.excute_bufferQuery(dataServiceUrl, geometry);
                    }

                } else {
                    Toast.makeText(DataQueryDemo.this, "绘制线至少包含两个点!", Toast.LENGTH_LONG).show();
                }

            } else if (drawStatic == 1) {
                // 提交面缓冲区查询事件请求
                polygonOverlay.setData(new ArrayList<Point2D>());
                // mapView.invalidate();
                if (geoPoints.size() > 2) {
                    geoPoints.add(geoPoints.get(0));
                    com.supermap.services.components.commontypes.Point2D[] pts = new com.supermap.services.components.commontypes.Point2D[geoPoints.size()];
                    for (int j = 0; j < geoPoints.size(); j++) {
                        pts[j] = new com.supermap.services.components.commontypes.Point2D(geoPoints.get(j).x, geoPoints.get(j).y);
                    }
                    Geometry geometry = new Geometry();
                    geometry.points = pts;
                    geometry.parts = new int[] { pts.length };
                    geometry.type = GeometryType.REGION;
                    if (queryStatic == 0) {// 0代表几何查询
                        Log.d("iserver", "excuteGeometryQuery");
                        queryResult = DataUtil.excute_geometryQuery(dataServiceUrl, geometry);
                    } else if (queryStatic == 1) {
                        Log.d("iserver", "excuteBufferQuery");
                        queryResult = DataUtil.excute_bufferQuery(dataServiceUrl, geometry);
                    }

                } else {
                    Toast.makeText(DataQueryDemo.this, "绘制面至少包含三个点!", Toast.LENGTH_LONG).show();
                }
            }
            Message msg = new Message();
            if (queryResult != null) {
                msg.obj = queryResult;
                msg.what = QUERY_SUCCESS;
            } else {
                msg.what = QUERY_FAILED;
            }
            handler.sendMessage(msg);
        }

    }

    /**
     * 展示查询结果的handler接口，查询结果高亮显示后，关闭进度条
     *
     */
    class QueryFinished extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case QUERY_SUCCESS:
                GetFeaturesResult queryResult = (GetFeaturesResult) msg.obj;
                if (queryStatic == -1 || queryStatic == 0) {// 0代表几何查询
                    Log.d("iserver", "excuteGeometryQuery");
                    showQueryResult(queryResult);
                } else if (queryStatic == 1) {
                    Log.d("iserver", "excuteBufferQuery");
                    showBufferQueryResult(queryResult);
                }
                progressDialog.dismiss();
                break;
            case QUERY_FAILED:
                progressDialog.dismiss();
                Toast.makeText(DataQueryDemo.this, R.string.query_failed, Toast.LENGTH_LONG).show();
                break;
            default:
                progressDialog.dismiss();
                break;
            }
        }
    }

    /**
     * 调用DrawQueryRunnable的线程
     * @return
     */
    public Thread drawQueryThread() {
        Thread thread = new Thread(new DrawQueryRunnable());
        return thread;
    }

    /**
     * buffer查询结果显示
     */
    public void showBufferQueryResult(GetFeaturesResult result) {
        if (result == null || result.features == null || result.features.length < 1) {
            Toast.makeText(this, "查询结果为空!", Toast.LENGTH_LONG).show();
            return;
        }
        DefaultItemizedOverlay overlay = new DefaultItemizedOverlay(pointMarker);
        // 存储查询记录的所有点对象
        for (int i = 0; i < result.features.length; i++) {
            Feature feature = result.features[i];
            Geometry geometry = feature.geometry;
            List<Point2D> geoPoints = DataUtil.getPiontsFromGeometry(geometry);
            if (geoPoints != null && geoPoints.size() > 0) {
                OverlayItem overlayItem = new OverlayItem(geoPoints.get(0), null, null);
                overlay.addItem(overlayItem);
            }
        }
        mapView.getOverlays().add(overlay);
        overlays.add(overlay);
        clearAllTouchOverlay();
        mapView.invalidate();
        progressDialog.dismiss();

    }

    /**
     * 查询结果的地物高亮显示
     */
    public void showQueryResult(GetFeaturesResult result) {
        if (result == null || result.features == null) {
            Toast.makeText(this, "查询结果为空!", Toast.LENGTH_LONG).show();
            return;
        }
        // 存储查询记录的几何对象的点
        List<List<Point2D>> pointsLists = new ArrayList<List<Point2D>>();
        for (int i = 0; i < result.features.length; i++) {
            Feature feature = result.features[i];
            Geometry geometry = feature.geometry;
            Log.d("Test info ", "feature.geometry points =" + geometry.points.length);
            List<Point2D> geoPoints = DataUtil.getPiontsFromGeometry(geometry);
            if (geometry.parts.length > 1) {
                int num = 0;
                for (int j = 0; j < geometry.parts.length; j++) {
                    int count = geometry.parts[j];
                    List<Point2D> partList = geoPoints.subList(num, num + count);
                    pointsLists.add(partList);
                    num = num + count;
                }
            } else {
                pointsLists.add(geoPoints);
            }
        }
        // 把所有查询的几何对象都高亮显示
        for (int m = 0; m < pointsLists.size(); m++) {
            List<Point2D> geoPointList = pointsLists.get(m);
            PolygonOverlay polygonOverlay = new PolygonOverlay(getPolygonPaint());
            mapView.getOverlays().add(polygonOverlay);
            polygonOverlays.add(polygonOverlay);
            polygonOverlay.setData(geoPointList);
        }
        clearAllTouchOverlay();
        this.mapView.invalidate();

    }

    // 绘面风格
    private Paint getPolygonPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);
        return paint;
    }

    // 绘线风格
    private Paint getLinePaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        return paint;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATAQUERY_DIALOG:
            if (dataQueryDialog != null) {
                return dataQueryDialog;
            }
            break;
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "dataquery");
            return dialog;
        default:
            break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
        case DATAQUERY_DIALOG:
            if (dataQueryDialog != null) {
                Log.d("iserver", "DataQueryDemo onPrepareDialog!");
            }
            break;
        case README_DIALOG:
            ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
            readmeDialog.setReadmeText(getResources().getString(R.string.dataquerydemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

    /**
     * 点地物触屏Overlay
     */
    class PointTouchOverlay extends Overlay {
        @Override
        public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                touchX = Math.round(event.getX());
                touchY = Math.round(event.getY());
                // 记录点击位置
                Point2D touchPoint = mapView.getProjection().fromPixels(touchX, touchY);
                showProgressDialog();
                new Thread(new PointQueryRunnable(touchPoint)).start();
            }

            return true;
        }
    }

    /**
     * 触屏Overlay
     */
    class TouchOverlay extends Overlay {
        @Override
        public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                touchX = Math.round(event.getX());
                touchY = Math.round(event.getY());
                // 记录点击位置
                Point2D touchGeoPoint = mapView.getProjection().fromPixels(touchX, touchY);
                if (drawStatic == 0 || drawStatic == 1) {
                    if (!geoPoints.contains(touchGeoPoint)) {
                        // Log.i("iserver", "touchGeoPoint");
                        geoPoints.add(touchGeoPoint);
                    }
                }
                if (drawStatic == 0) {
                    mapView.getOverlays().add(lineOverlay);
                    setOverlayData(lineOverlay);
                } else if (drawStatic == 1) {
                    mapView.getOverlays().add(polygonOverlay);
                    setOverlayData(polygonOverlay);
                }

            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // 根据绘制状态，给当前绘制对象设置点集合 数据，当前绘制对象在 绘制对象集合中的最后一个，即新加入的绘制对象
                // 给当前绘制面对象设置点集合 数据
                List<Point2D> geoPointList = new ArrayList<Point2D>();
                if (geoPoints.size() > 0) {
                    copyList(geoPoints, geoPointList);
                }
                int x = Math.round(event.getX());
                int y = Math.round(event.getY());
                Point2D touchGeoPoint = mapView.getProjection().fromPixels(x, y);
                updatePoint(geoPointList, touchGeoPoint);
                if (drawStatic == 0) {// 1代表绘制线
                    setOverlayData(lineOverlay, geoPointList);
                } else if (drawStatic == 1) {
                    setOverlayData(polygonOverlay, geoPointList);
                }
                mapView.invalidate();
            }

            return true;
        }

        /**
         * 给Overlay设置点集合，开始绘制对象，并刷新地图
         * 
         * @param overlay
         * @param gps
         */
        private void setOverlayData(Overlay overlay, List<Point2D> gps) {
            if (overlay == null) {
                return;
            }
            List<Point2D> geoPointList = new ArrayList<Point2D>();
            if (gps != null && gps.size() > 0) {
                copyList(gps, geoPointList);
            } else if (geoPoints.size() > 0) {
                copyList(geoPoints, geoPointList);
            }
            if (geoPointList.size() > 0) {
                if (overlay instanceof LineOverlay) {
                    ((LineOverlay) overlay).setData(geoPointList);
                } else if (overlay instanceof PolygonOverlay) {
                    ((PolygonOverlay) overlay).setData(geoPointList);
                }
                mapView.invalidate();
            }
        }

        private void copyList(List<Point2D> sourcegps, List<Point2D> targetgps) {
            for (int i = 0; i < sourcegps.size(); i++) {
                targetgps.add(new Point2D(sourcegps.get(i)));
            }
        }

        private void setOverlayData(Overlay overlay) {
            setOverlayData(overlay, null);
        }

        private void updatePoint(List<Point2D> geoPointList, Point2D touchGeoPoint) {
            if (geoPointList.size() == geoPoints.size()) {
                geoPointList.add(touchGeoPoint);
            } else if (geoPointList.size() > geoPoints.size()) {
                geoPointList.remove(geoPointList.size() - 1);
                geoPointList.add(touchGeoPoint);
            }
        }
    }

    @Override
    protected void onDestroy() {
        mapView.stopClearCacheTimer();// 停止和销毁 清除运行时服务器中缓存瓦片的定时器。
        mapView.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // 重写onBackPressed，将字段editFeatureDialog必须置为null，以保证消除之前的引用
        dataQueryDialog.dismiss();
        dataQueryDialog = null;
        super.onBackPressed();
    }

}
