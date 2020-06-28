package com.supermap.imobilelite.mapsamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PointOverlay;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.dialog.ReadmeDialog;
import com.supermap.imobilelite.samples.service.PreferencesService;

/**
 * 绘制线和面要素的demo范例，绘制线或面都需要2个设置：1 绘制风格Paint对象(若不设置，有个默认值，可以不设置)；2 绘制所需的点集合
 * 注意 选择绘制完一个对象的结束标志事件，这里以 点选 结束 菜单项来结束
 * 绘制点时，无需设置结束事件，触屏点击就绘制
 * 绘制点线面要素时，不启用滑动事件触发平移地图，当选择浏览和清除 菜单项时启用
 * @author huangqinghua
 * 
 */
public class DrawOverlayDemo extends SimpleDemo {
    // 存放绘制线或面的点集合
    private List<Point2D> geoPoints = new ArrayList<Point2D>();
    // 存放所需绘制的线对象集合
    private List<LineOverlay> lineOverlays = new ArrayList<LineOverlay>();
    // 默认的绘制线对象
    // private LineOverlay firstLineOverlay;
    // 存放所需绘制的面对象集合
    private List<PolygonOverlay> polygonOverlays = new ArrayList<PolygonOverlay>();
    // 默认的绘制面对象
    // private PolygonOverlay firstPolygonOverlay;
    // 触屏Overlay,用来获取点坐标
    private TouchOverlay touchOverlay;
    // 绘制要素状态
    private int drawStatic = 1;// 1代表绘制线，2表示绘制面，0代表绘制点
    // 单个绘制对象绘制完成的标志
    private boolean addOverlayOver = false;
    private boolean useDraw = true;
    private static final int README_DIALOG = 9;
    // 触屏的x坐标
    private int touchX;

    // 触屏的y坐标
    private int touchY;
    private PreferencesService service;

    // private long previousTouchTime = 0;
    // private boolean isDoubleTouch = false;
//    private boolean isMove = false;
    private boolean isMultiTouch = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
        mapView.getController().setZoom(6);
        // mapView.getController().setCenter(new Point2D(116.391468, 39.904491));

        mapView.setBuiltInZoomControls(false);
        // mapView.addMapViewEventListener(new MapViewEventAdapter());
        // 初始化各个overlayer
        // firstLineOverlay = new LineOverlay(paint);
        // firstPolygonOverlay = new PolygonOverlay(paint);
        touchOverlay = new TouchOverlay();

        service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("DrawLineDemo");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
        helpBtn.setVisibility(View.VISIBLE);
        helpBtn.setOnClickListener(new View.OnClickListener() {
                
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(README_DIALOG);
               }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, R.string.drawpoint);
        menu.add(0, 2, 0, R.string.drawline);
        menu.add(0, 3, 0, R.string.drawpolygon);
        menu.add(0, 4, 0, R.string.clear);
        menu.add(0, 5, 0, R.string.view);
        menu.add(0, 6, 0, R.string.enddraw);
        return true;
    }
    
    /**
     * 绘线风格
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
     * 绘面风格
     * @return
     */
    public static Paint getPolygonPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(200, 10, 230, 250));
        paint.setStyle(Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        return paint;
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
            // 添加PointOverlay绘点，地图刷新
            geoPoints.clear();
            if (!mapView.getOverlays().contains(touchOverlay)) {
                mapView.getOverlays().add(touchOverlay);
            }
            drawStatic = 0;
            // 不启用双击事件触发放大地图
            mapView.setUseDoubleTapEvent(false);
            break;
        case 2:
            // 添加LineOverlay绘线，地图刷新
            geoPoints.clear();
            if (!mapView.getOverlays().contains(touchOverlay)) {
                mapView.getOverlays().add(touchOverlay);
            }
            addLineOverLayer();
            drawStatic = 1;
            mapView.setUseDoubleTapEvent(false);
            break;
        case 3:
            // 添加PolygonOverlay绘面，地图刷新
            geoPoints.clear();
            if (!mapView.getOverlays().contains(touchOverlay)) {
                mapView.getOverlays().add(touchOverlay);
            }
            addPolygonOverLayer();
            drawStatic = 2;
            mapView.setUseDoubleTapEvent(false);
            break;
        case 4:
            // 清空Overlay，地图刷新
            // if (lineOverlays.size() > 0) {
            // mapView.getOverlays().removeAll(lineOverlays);
            // }
            mapView.getOverlays().clear();
            lineOverlays.clear();
            polygonOverlays.clear();
            geoPoints.clear();
            mapView.setUseDoubleTapEvent(true);
            mapView.invalidate();
            break;
        case 5:
            // 切换 浏览与绘制状态，浏览时保持先前的绘制情况，处于绘制操作时，才能绘制点线面
            // mapView.getOverlays().remove(touchOverlay);
            if (useDraw) {
                mapView.setUseDoubleTapEvent(true);
                useDraw = false;
                item.setTitle("绘制");
            } else {
                mapView.setUseDoubleTapEvent(false);
                useDraw = true;
                item.setTitle("浏览");
            }
            break;
        case 6:
            // 结束绘制一个线或面要素，开始绘制另一个线或面要素
            if (drawStatic == 1 || drawStatic == 2) {
                addOverlayOver = true;
            }
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addPolygonOverLayer() {
        PolygonOverlay polygonOverlay = new PolygonOverlay(getPolygonPaint());
        mapView.getOverlays().add(polygonOverlay);
        polygonOverlays.add(polygonOverlay);
    }

    private void addLineOverLayer() {
        LineOverlay lineOverlay = new LineOverlay(getLinePaint());
        mapView.getOverlays().add(lineOverlay);
        lineOverlays.add(lineOverlay);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

    @Override
    protected Dialog onCreateDialog(int id) {
        // switch是android示范程序中的标准写法
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "DrawLineDemo");
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
            readmeDialog.setReadmeText(getResources().getString(R.string.drawoverlaydemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 触屏Overlay
     */
    class TouchOverlay extends Overlay {
        private List<Point> movePoints = new ArrayList<Point>();
        @Override
        public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
            if (useDraw) {
                if (event.getPointerCount() > 1) {// 发生多点触碰
                    isMultiTouch = true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (movePoints.size() > 1) {// 如果发生平移，不绘制
                        double distance = 0;
                        for (int i = movePoints.size() - 1; i > 0; i--) {
                            double ds = Math.pow((movePoints.get(i).x - movePoints.get(i - 1).x), 2)
                                    + Math.pow((movePoints.get(i).y - movePoints.get(i - 1).y), 2);
                            if (ds > distance) {
                                distance = ds;
                            }
                        }
                        if(distance>16){
                            return false; 
                        }
                    }
                    if (isMultiTouch) {// 如果发生多点触碰，不绘制
                        isMultiTouch = false;
                        return false;
                    }
                    // if(isDoubleTouch){//如果发生双击，只绘制第一次的点
                    // isDoubleTouch = false;
                    // return true;
                    // }
                    touchX = Math.round(event.getX());
                    touchY = Math.round(event.getY());
                    // 记录点击位置
                    Point2D touchGeoPoint = mapView.getProjection().fromPixels(touchX, touchY);

                    // 当addOverlayOver为true时，当前对象绘制结束，根据绘制状态，选择创建新的绘制对象加入绘制对象集合中，并置 addOverlayOver为false
                    if (addOverlayOver) {
                        // Log.i("iserver", "onTouchEvent");
                        if (drawStatic == 1) {
                            LineOverlay lineOverlay = new LineOverlay(getLinePaint());
                            mapView.getOverlays().add(lineOverlay);
                            // Log.i("iserver", "lineOverlays前个数：" + lineOverlays.size());
                            lineOverlays.add(lineOverlay);
                            // Log.i("iserver", "lineOverlays后个数：" + lineOverlays.size());
                        } else if (drawStatic == 2) {
                            PolygonOverlay polygonOverlay = new PolygonOverlay(getPolygonPaint());
                            mapView.getOverlays().add(polygonOverlay);
                            polygonOverlays.add(polygonOverlay);
                        }
                        geoPoints.clear();
                        addOverlayOver = false;
                    }

                    if (drawStatic == 1 || drawStatic == 2) {
                        if (!geoPoints.contains(touchGeoPoint)) {
                            // Log.i("iserver", "touchGeoPoint");
                            geoPoints.add(touchGeoPoint);
                        }
                    }

                    if (drawStatic == 0) {// 0代表绘制点
                        mapView.getOverlays().add(new PointOverlay(touchGeoPoint, DrawOverlayDemo.this));
                        mapView.invalidate();
                    }

                    if (drawStatic == 1) {// 1代表绘制线
                        setOverlayData(lineOverlays.get(lineOverlays.size() - 1));
                        // mapView.invalidate();
                    } else if (drawStatic == 2) {// 2表示绘制面
                        setOverlayData(polygonOverlays.get(polygonOverlays.size() - 1));
                        // mapView.invalidate();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    movePoints.clear();
                    // int distX = Math.round(event.getX() - touchX);
                    // int distY = Math.round(event.getY() - touchY);
                    // if (System.currentTimeMillis() - previousTouchTime < 1000 && (distX * distX + distY * distY) < 9) {
                    // isDoubleTouch = true;// 发生双击
                    // }
                    // previousTouchTime = System.currentTimeMillis();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // 根据绘制状态，给当前绘制对象设置点集合 数据，当前绘制对象在 绘制对象集合中的最后一个，即新加入的绘制对象
                    // if (!addOverlayOver) {
                    // List<Point2D> geoPointList = new ArrayList<Point2D>();
                    // if (geoPoints.size() > 0) {
                    // copyList(geoPoints, geoPointList);
                    // }
                    // int x = Math.round(event.getX());
                    // int y = Math.round(event.getY());
                    // Point2D touchGeoPoint = mapView.getProjection().fromPixels(x, y);
                    // updatePoint(geoPointList, touchGeoPoint);
                    // if (drawStatic == 1) {// 1代表绘制线
                    // setOverlayData(lineOverlays.get(lineOverlays.size() - 1), geoPointList);
                    // } else if (drawStatic == 2) {
                    // setOverlayData(polygonOverlays.get(polygonOverlays.size() - 1), geoPointList);
                    // }
                    // mapView.invalidate();
                    // }
                    movePoints.add(new Point(Math.round(event.getX()),Math.round(event.getY())));
//                    isMove = true;
                }
            }
            return false;
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

    /**
     * MapView事件处理适配器，提供长按事件的实现
     */
    class MapViewEventAdapter implements MapView.MapViewEventListener {

        @Override
        public void moveStart(MapView paramMapView) {

        }

        @Override
        public void move(MapView paramMapView) {

        }

        @Override
        public void moveEnd(MapView paramMapView) {

        }

        @Override
        public void touch(MapView paramMapView) {

        }

        @Override
        public void longTouch(MapView paramMapView) {
            // Log.i("iserver", "longTouch");
            // 以 长按事件 作为绘制当前线面对象结束的标志
            // if (drawStatic == 1 || drawStatic == 2) {
            // addOverlayOver = true;
            // }
        }

        @Override
        public void zoomStart(MapView paramMapView) {

        }

        @Override
        public void zoomEnd(MapView paramMapView) {

        }

        @Override
        public void mapLoaded(MapView paramMapView) {

        }
    }
    // class MyOverlayTouchEventListener implements Overlay.OverlayTouchEventListener{
    //
    // @Override
    // public void onTouch(MotionEvent event, MapView paramMapView) {
    // touchX = Math.round(event.getX());
    // touchY = Math.round(event.getY());
    // // 记录点击位置
    // GeoPoint touchGeoPoint = mapView.getProjection().fromPixels(touchX, touchY);
    // geoPoints.add(touchGeoPoint);
    // }
    //
    // }

}
