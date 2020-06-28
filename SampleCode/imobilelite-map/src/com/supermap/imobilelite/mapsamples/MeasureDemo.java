package com.supermap.imobilelite.mapsamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.maps.measure.MeasureResult;
import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.dialog.ReadmeDialog;
import com.supermap.imobilelite.mapsamples.util.MeasureUtil;
import com.supermap.imobilelite.samples.service.PreferencesService;

public class MeasureDemo extends Activity {
    private static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services/map-china400/rest/maps/China";
    private MapView mapView;
    private LayerView baseLayerView;
    private String mapUrl;
    // 显示量算结果的编辑文本控件
    private EditText text;
    // 线面触屏Overlay，用来获取点坐标
    private TouchOverlay touchOverlay;
    // 存放绘制线或面的点集合
    private List<Point2D> geoPoints = new ArrayList<Point2D>();
    // 绘制要素状态
    private int drawStatic = -1;// 0代表绘制线，1表示绘制面
    // 默认的绘制面对象
    private PolygonOverlay polygonOverlay;
    // 默认的绘制线对象
    private LineOverlay lineOverlay;
    // 是否发生多点触碰
    public boolean isMultiTouch = false;
    private Button helpBtn;
    private static final int README_DIALOG = 9;
    private PreferencesService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        baseLayerView = new LayerView(this);
        Bundle bundle = this.getIntent().getExtras();
        // 设置访问地图的URL
        mapUrl = bundle.getString("map_url");
        if (mapUrl != null && mapUrl.equals("")) {
            baseLayerView.setURL(DEFAULT_URL);
        } else {
            baseLayerView.setURL(mapUrl);
        }
        mapView.addLayer(baseLayerView);
        // 设置缩放级别
        mapView.getController().setZoom(3);
        mapView.getController().setCenter(Util.lonLat2Mercator(116.391468, 39.904491));// new Point2D(116.391468, 39.904491)
        mapView.setBuiltInZoomControls(true);
        text = (EditText) this.findViewById(R.id.editText);
        touchOverlay = new TouchOverlay();
        lineOverlay = new LineOverlay(getLinePaint());
        mapView.getOverlays().add(lineOverlay);
        polygonOverlay = new PolygonOverlay(getPolygonPaint());
        mapView.getOverlays().add(polygonOverlay);
        service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("MeasureDemo");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
        helpBtn = (Button) findViewById(R.id.button_help);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(README_DIALOG);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, R.string.distance);
        menu.add(0, 2, 0, R.string.area);
        menu.add(0, 3, 0, R.string.clear);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击数据集查询菜单触发事件
        case 1:// 距离量算
            showDiatanceMeasure();
            break;
        case 2:// 面积量算
            showAreaMeasure();
            break;
        case 3:// 清除
            clear();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <p>
     * 展示距离量算
     * </p>
     */
    public void showDiatanceMeasure() {
        clean();
        drawStatic = 0;
        // 添加LineOverlay绘线，地图刷新
        if (!mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().add(touchOverlay);
        }
        mapView.invalidate();
    }

    /**
     * <p>
     * 展示面积量算
     * </p>
     */
    public void showAreaMeasure() {
        clean();
        drawStatic = 1;
        // 添加PolygonOverlay绘面，地图刷新
        if (!mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().add(touchOverlay);
        }
        mapView.invalidate();
    }

    // 清除
    public void clear() {
        if (mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().remove(touchOverlay);
        }
        drawStatic = -1;
        clean();
        text.setVisibility(View.INVISIBLE);
        mapView.invalidate();
    }

    private void clean() {
        lineOverlay.setData(new ArrayList<Point2D>());
        polygonOverlay.setData(new ArrayList<Point2D>());
        geoPoints.clear();
        text.setText("");
    }

    // 绘面风格
    private Paint getPolygonPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
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

    /**
     * 触屏Overlay
     */
    class TouchOverlay extends Overlay {
        // 触屏的x坐标
        private int touchX;
        // 触屏的y坐标
        private int touchY;
        private List<Point> movePoints = new ArrayList<Point>();

        @Override
        public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
            if (event.getPointerCount() > 1) {// 发生多点触碰
                isMultiTouch = true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (movePoints.size() > 1) {// 如果发生平移，不绘制
                    double distance = 0;
                    for (int i = movePoints.size() - 1; i > 0; i--) {
                        double ds = Math.pow((movePoints.get(i).x - movePoints.get(i - 1).x), 2) + Math.pow((movePoints.get(i).y - movePoints.get(i - 1).y), 2);
                        if (ds > distance) {
                            distance = ds;
                        }
                    }
                    if (distance > 16) {
                        return false;
                    }
                }
                if (isMultiTouch) {// 如果发生多点触碰，不绘制
                    isMultiTouch = false;
                    return false;
                }
                text.setVisibility(View.VISIBLE);
                text.setTextSize(15);
                text.setText("");
                touchX = Math.round(event.getX());
                touchY = Math.round(event.getY());
                // 记录点击位置
                Point2D touchGeoPoint = mapView.getProjection().fromPixels(touchX, touchY);
                geoPoints.add(touchGeoPoint);
                if (drawStatic == 0) {
                    setOverlayData(lineOverlay);
                    if (geoPoints.size() >= 2) {
                        com.supermap.services.components.commontypes.Point2D[] pts = changePiont2D(geoPoints);
                        MeasureResult result = MeasureUtil.distanceMeasure(mapUrl, pts);
                        if (result != null) {
                            text.setText("量算结果：" + result.distance + "米");
                            Log.d("MeasureDemo", "量算结果" + text);
                        }
                    }

                } else if (drawStatic == 1) {
                    setOverlayData(polygonOverlay);
                    if (geoPoints.size() >= 3) {
                        com.supermap.services.components.commontypes.Point2D[] pts = changePiont2D(geoPoints);
                        MeasureResult result = MeasureUtil.areaMeasure(mapUrl, pts);
                        if (result != null) {
                            text.setText("量算结果：" + result.area + "平方千米");
                            Log.d("MeasureDemo", "量算结果" + text);
                        }
                    }
                }
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                movePoints.clear();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                movePoints.add(new Point(Math.round(event.getX()), Math.round(event.getY())));
            }
            return false;
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

        private com.supermap.services.components.commontypes.Point2D[] changePiont2D(List<Point2D> gps) {
            com.supermap.services.components.commontypes.Point2D[] pts = new com.supermap.services.components.commontypes.Point2D[gps.size()];
            for (int j = 0; j < gps.size(); j++) {
                pts[j] = new com.supermap.services.components.commontypes.Point2D(gps.get(j).x, gps.get(j).y);
            }
            return pts;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "MeasureDemo");
            return dialog;
        default:
            break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
        case README_DIALOG:
            ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
            readmeDialog.setReadmeText(getResources().getString(R.string.measuredemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }
    
    @Override
    protected void onDestroy() {
        // LocationUtil.disposeLoction();
        if (mapView != null) {
            mapView.destroy();
        }
        super.onDestroy();
    }
}
