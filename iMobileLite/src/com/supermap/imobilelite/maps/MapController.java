package com.supermap.imobilelite.maps;

import java.util.Queue;

import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 地图控件类，响应地图的基本操作事件（缩放，平移等）。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public final class MapController implements View.OnKeyListener {
    private static final String LOG_TAG = "com.supermap.android.maps.mapcontroller";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private MapView mapView;
    private MapAnimator mapAnimator;
    private MapHandler handler;
    private long zoomClickTime;
    private static final long ZOOM_DELAY = 250;

    MapController(MapView mapView) {
        if (mapView == null) {
            Log.e(LOG_TAG, resource.getMessage(MapCommon.MAPCONTROLLER_CONSTRUCT_NOTNULL));
            throw new IllegalArgumentException("不能用空对象构造MapController");
        }
        this.mapView = mapView;
        this.mapAnimator = new MapAnimator(mapView);
        this.handler = new MapHandler(mapView);
    }

    /**
     * <p>
     * 对已给定的点Point2D，开始动画显示地图。
     * </p>
     * @param point 指定的二维点对象。
     */
    public void animateTo(Point2D point) {
        this.mapAnimator.animateTo(point);
    }

    /**
     * <p>
     * 对已给定的点Point2D，开始动画显示地图。如果动画自然结束，则分发给定的消息。如果动画中途被放弃，则不分发给定的消息。
     * </p>
     * @param point 指定的二维点对象。
     * @param message 给定的消息。
     */
    public void animateTo(Point2D point, Message message) {
        this.mapAnimator.animateTo(point, message);
    }

    public void animateTo(Point2D point, Runnable runnable) {
        this.mapAnimator.animateTo(point, runnable);
    }

    /**
     * <p>
     * 按照给定的旋转角度，开始动画显示地图。
     * </p>
     * @param rotationDegrees 旋转角度。
     */
    public void animateRotation(float rotationDegrees) {
        rotationDegrees = (rotationDegrees + 360.0F) % 360.0F;
        getMapAnimator().animateRotation(rotationDegrees);
    }

    /**
     * <p>
     * 处理按键事件，把事件变换为适度的地图平移。在View.onkeyListener中定义。
     * </p>
     * @param view 按键被分发的视图。
     * @param code 按下的物理按键的代码。
     * @param event 包含按键事件所有信息的KeyEvent实例。
     * @return 一个布尔值，true表示事件被处理，false表示事件未被处理。
     */
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * <p>
     * 按照给定的像素数据量滚动。
     * </p>
     * @param x 水平滚动的像素值。
     * @param y 垂直滚动的像素值。
     */
    public void scrollBy(int x, int y) {
        Projection p = this.mapView.getProjection();
        this.handler.sendSetCenter(p.fromPixels(this.mapView.getFocalPoint().x + x, this.mapView.getFocalPoint().y + y));
    }

    /**
     * <p>
     * 在给定的中心点Point2D上设置地图视图。
     * </p>
     * @param point 指定的二维点对象。
     */
    public void setCenter(Point2D point) {
        this.handler.sendSetCenter(point);
    }

    /**
     * <p>
     * 设置地图的缩放级别。这个值的取值范围是[0,17]。
     * </p>
     * @param zoomLevel 缩放级别，取值范围是[0,17]。
     */
    public void setZoom(int zoomLevel) {
        if (zoomLevel == mapView.getZoomLevel() || zoomLevel < 0 || zoomLevel > mapView.getMaxZoomLevel()) {
            return;
        }
        this.handler.sendSetZoom(zoomLevel);
    }

    /**
     * <p>
     * 按照给定的旋转角度进行地图旋转。
     * </p>
     * @param rotationDegrees 旋转角度。
     */
    public void setMapRotation(float rotationDegrees) {
        this.handler.sendSetMapRotation(rotationDegrees);
    }

    /**
     * <p>
     * 设置是否停止动画。
     * </p>
     * @param jumpToFinish 地图动画是否停止。
     */
    public void stopAnimation(boolean jumpToFinish) {
        Queue<Animator> animators = this.mapView.animators;
        while (!animators.isEmpty()) {
            Animator animator = (Animator) animators.element();
            if (animator.isAnimating())
                animator.stopAnimation(jumpToFinish);
        }
    }

    /**
     * <p>
     * 停止平移。
     * </p>
     */
    public void stopPanning() {
        this.mapAnimator.stopSpanning(false);
    }

    /**
     * <p>
     * 放大一个级别。
     * </p>
     * @return 如果放大成功，返回true；如果达到最大极限，返回false。
     */
    public boolean zoomIn() {       
        return zoomInFixing(this.mapView.getFocalPoint().x, this.mapView.getFocalPoint().y);
    }

    /**
     * <p>
     * 放大一个级别。这个放大会平移地图使之保持在屏幕的一个固定点上。通过像素坐标来设定固定点。
     * </p>
     * @param xPixel 地图左边固定点缩放的偏移量。
     * @param yPixel 地图上方固定点缩放的偏移量。
     * @return 如果放大成功，返回true；如果达到最大极限，返回false。
     */
    public boolean zoomInFixing(int xPixel, int yPixel) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - zoomClickTime < ZOOM_DELAY) {
            return false;
        }
        zoomClickTime = currentTime;
        // int startZoom = (int) Math.round(this.mapView.getZoomLevel() - Util.log2(this.mapView.currentScale));
        int startZoom = this.mapView.getZoomLevel();
        Point center = new Point(xPixel, yPixel);
        if (this.mapView.validateZoomLevel(this.mapView.getZoomLevel() + 1)) {
            this.mapAnimator.animateZoomScaler(startZoom, startZoom + 1, 1.0f, center, false);
            // this.mapAnimator.animateZoomScaler(startZoom, this.mapView.getZoomLevel() + 1, this.mapView.currentScale, center, false);
            mapView.zoomInChanged = true;// 是放大地图，置为true
            return true;
        }
        return false;
    }

    /**
     * <p>
     * 缩小一个级别。
     * </p>
     * @return 如果缩小成功，返回true；如果达到最小极限，返回false。
     */
    public boolean zoomOut() {        
        return zoomOutFixing(this.mapView.getFocalPoint().x, this.mapView.getFocalPoint().y);
    }

    /**
     * <p>
     * 缩小一个级别。 这个缩小会平移地图使之保持在屏幕的一个固定点上。通过像素坐标来设定固定点。
     * </p>
     * @param xPixel 地图左边固定点缩放的偏移量。
     * @param yPixel 地图上方固定点缩放的偏移量。
     * @return 如果缩小成功，返回true；如果达到最小极限，返回false。
     */
    public boolean zoomOutFixing(int xPixel, int yPixel) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - zoomClickTime < ZOOM_DELAY) {
            return false;
        }
        zoomClickTime = currentTime;
        // int startZoom = (int) Math.round(this.mapView.getZoomLevel() - Util.log2(this.mapView.currentScale));
        int startZoom = this.mapView.getZoomLevel();
        Point center = new Point(xPixel, yPixel);
        if (this.mapView.validateZoomLevel(this.mapView.getZoomLevel() - 1)) {
            this.mapAnimator.animateZoomScaler(startZoom, startZoom - 1, 1.0f, center, false);
            // this.mapAnimator.animateZoomScaler(startZoom, this.mapView.getZoomLevel() - 1, this.mapView.currentScale, center, false);
            mapView.zoomInChanged = false;// 不是放大地图，而是缩小地图，置为false
            return true;
        }
        return false;
    }

    /**
     * <p>
     * 按照指定的距离平移地图。
     * </p>
     * @param latSpanE6 水平方向的平移量，单位为坐标单位。
     * @param lonSpanE6 垂直方向的平移量，单位为坐标单位。
     */
    // 暂时没有用到，先不开放此接口。
    private void zoomToSpan(int latSpanE6, int lonSpanE6) {
        this.handler.sendZoomToSpan(latSpanE6, lonSpanE6);
    }

    /**
     * <p>
     * 按照指定的矩形区域平移地图。
     * </p>
     * @param boundingBox 指定的矩形区域对象。
     */
    // 暂时没有用到，先不开放此接口。
    private void zoomToSpan(BoundingBox boundingBox) {
        this.handler.sendZoomToSpan(boundingBox);
    }

    MapAnimator getMapAnimator() {
        return this.mapAnimator;
    }

    void destroy() {
        this.mapView = null;
        this.mapAnimator = null;
    }

    class MapHandler extends Handler {
        static final int MSG_CENTER = 0;
        static final int MSG_ZOOM = 1;
        static final int MSG_ZOOM_TO_SPAN = 2;
        static final int MSG_ZOOM_TO_SPAN_BBOX = 3;
        static final int MSG_ROTATION = 4;
        static final String KEY_LATITUDE = "latitude";
        static final String KEY_LONGITUDE = "longitude";
        static final String KEY_UL_LATITUDE = "ul_latitude";
        static final String KEY_UL_LONGITUDE = "ul_longitude";
        static final String KEY_LR_LATITUDE = "lr_latitude";
        static final String KEY_LR_LONGITUDE = "lr_longitude";
        static final String KEY_ZOOM_LEVEL = "zoom_level";
        static final String KEY_ZOOM_TO_SPAN = "scale";
        static final String KEY_MAP_ROTATION = "scale";
        static final String KEY_SCALE_POINT_X = "scale_point_x";
        static final String KEY_SCALE_POINT_Y = "scale_point_x";
        MapView mapView;

        private MapHandler(MapView mapView) {
            this.mapView = mapView;
        }

        public void handleMessage(Message msg) {
            double lat = 0d;
            double lng = 0d;
            switch (msg.what) {
            case 0:
                mapView.getEventDispatcher().sendEmptyMessage(21);
                lat = msg.getData().getDouble("latitude");
                lng = msg.getData().getDouble("longitude");
                Point2D gp = new Point2D(lng, lat);
//                this.mapView.currentScale = 1.0f;// 直接放大或缩小时，置缩放比为1
                this.mapView.setMapCenter(gp, this.mapView.getZoomLevel());
                this.mapView.invalidate();
                mapView.getEventDispatcher().sendEmptyMessage(23);
                break;
            case 1:         
                mapView.getEventDispatcher().sendEmptyMessage(11);  
                int zoom = msg.getData().getInt("zoom_level");
                this.mapView.currentScale = 1.0f;// 直接放大或缩小时，置缩放比为1
                this.mapView.setZoomLevel(zoom);
                this.mapView.invalidate();
                mapView.getEventDispatcher().sendEmptyMessage(12);
                break;
            case 2:
                // TODO 验证是否正确
                lat = msg.getData().getDouble("latitude");
                lng = msg.getData().getDouble("longitude");
                this.mapView.zoomToSpan(lat, lng);
                /* int latE6 = msg.getData().getInt("latitude");
                 int lngE6 = msg.getData().getInt("longitude");
                this.mapView.zoomToSpan(latE6, lngE6);*/
                this.mapView.invalidate();
                break;
            case 3:
                // TODO 确认整型？
                int ulLatE6 = msg.getData().getInt("ul_latitude");
                int ulLngE6 = msg.getData().getInt("ul_longitude");
                int lrLatE6 = msg.getData().getInt("lr_latitude");
                int lrLngE6 = msg.getData().getInt("lr_longitude");
                Point2D ul = new Point2D(ulLngE6, ulLatE6);
                Point2D lr = new Point2D(lrLngE6, lrLatE6);
                BoundingBox bbox = new BoundingBox(ul, lr);
                this.mapView.zoomToSpan(bbox, true);
                this.mapView.invalidate();
                break;
            case 4:
                mapView.getEventDispatcher().sendEmptyMessage(31);
                float rotation = msg.getData().getFloat("scale");
                this.mapView.setMapRotation(rotation);
                mapView.getEventDispatcher().sendEmptyMessage(32);
                this.mapView.invalidate();
                mapView.getEventDispatcher().sendEmptyMessage(33);
                break;
            }
        }

        public void sendSetCenter(Point2D gp) {
            Message msg = Message.obtain();
            msg.getData().putDouble("latitude", gp.getY());
            msg.getData().putDouble("longitude", gp.getX());
            msg.what = 0;
            dispatchOrSend(msg);
        }

        public void sendSetZoom(int zoom) {
            Message msg = Message.obtain();
            msg.getData().putInt("zoom_level", zoom);
            msg.what = 1;
            dispatchOrSend(msg);
        }

        public void sendSetMapRotation(float rotation) {
            Message msg = Message.obtain();
            msg.getData().putFloat("scale", rotation);
            msg.what = 4;
            dispatchOrSend(msg);
        }

        public void sendZoomToSpan(int latE6, int lngE6) {
            Message msg = Message.obtain();
            msg.getData().putInt("latitude", latE6);
            msg.getData().putInt("longitude", lngE6);
            msg.what = 2;
            dispatchOrSend(msg);
        }

        public void sendZoomToSpan(BoundingBox bbox) {
            Message msg = Message.obtain();
            msg.getData().putDouble("ul_latitude", bbox.leftTop.getY());
            msg.getData().putDouble("ul_longitude", bbox.leftTop.getX());
            msg.getData().putDouble("lr_latitude", bbox.rightBottom.getY());
            msg.getData().putDouble("lr_longitude", bbox.rightBottom.getX());
            msg.what = 3;
            dispatchOrSend(msg);
        }

        private void dispatchOrSend(Message msg) {
            if (Util.checkIfSameThread(this))
                dispatchMessage(msg);
            else
                sendMessage(msg);
        }
    }
}