package com.supermap.imobilelite.maps;

import android.graphics.Point;

class ZoomAnimator extends Animator {
    private int startZoom;
    private int endZoom;
    // private Point2D geoPoint;
    private Point centerPoint;
    private float initialScale = 1.0F;
    private float currentScale = 1.0F;
    private float finalScale = 1.0F;
    private float deltaScale;
    private long startTime = 0L;
    private static final int DURATION = 200;

    public ZoomAnimator(MapView mapView) {
        super(mapView);
    }

    public ZoomAnimator(MapView mapView, Runnable runnable) {
        super(mapView, runnable);
    }

    public ZoomAnimator(MapView mapView, int startZoom, int endZoom, float currentScale, Point centerPoint) {
        this(mapView);
        this.startZoom = startZoom;
        this.endZoom = endZoom;
        this.initialScale = currentScale;
        this.currentScale = currentScale;
        this.centerPoint = centerPoint;
    }

    public void animate(float scale, Point center) {
        this.currentScale = this.mapView.currentScale;
        this.finalScale *= scale;
        setDuration(getDuration() + DURATION);
    }

    public void preAnimation() {
        if (getDuration() == 0) {
            setDuration(DURATION);
        }
        if (!this.mapView.scaling) {
            mapView.getEventDispatcher().sendEmptyMessage(11);
        }

        if ((this.centerPoint.x != this.mapView.getFocalPoint().x) || (this.centerPoint.y != this.mapView.getFocalPoint().y)) {
            // this.geoPoint = this.mapView.getProjection().fromPixels(this.centerPoint.x, this.centerPoint.y);
            mapView.centerGeoPoint = this.mapView.getProjection().fromPixels(this.centerPoint.x, this.centerPoint.y);
        }
        this.finalScale = (float) Math.pow(2.0D, this.endZoom - this.startZoom);
        this.deltaScale = (this.finalScale - this.initialScale);

        this.startTime = System.currentTimeMillis();
    }

    public boolean doAnimation() {
        float elapsed = (float) (System.currentTimeMillis() - this.startTime);

        if ((elapsed > getDuration()) && (Math.abs(this.currentScale - this.finalScale) > 0.0000001))
            elapsed = getDuration();
        else if (elapsed > getDuration()) {
            return false;
        }
        this.currentScale = (this.initialScale + elapsed / getDuration() * this.deltaScale); 
        this.mapView.setScaleByZoom(this.currentScale, this.centerPoint.x, this.centerPoint.y);
//        // Log.d("ZoomAnimator", "doAnimation currentScale:" + currentScale);
//        this.mapView.setScale(this.currentScale, this.currentScale, this.centerPoint.x, this.centerPoint.y);
        return true;
    }

    public void postAnimation() {
//        this.mapView.currentScale = this.currentScale;
        this.mapView.isZoomScale = false;
        this.mapView.currentScale = 1.0f;
        this.mapView.setZoomLevel(this.endZoom);
        // if (this.geoPoint != null) {
        // this.mapView.centerGeoPoint = this.geoPoint;
        //
        // Point focalPoint = this.mapView.getFocalPoint();
        // int newCenterX = focalPoint.x + (focalPoint.x - this.centerPoint.x);
        // int newCenterY = focalPoint.y + (focalPoint.y - this.centerPoint.y);
        // this.mapView.centerGeoPoint = this.mapView.getProjection().fromPixels(newCenterX, newCenterY);
        // Log.e("ZoomAnimator", "进来了");
        // this.geoPoint = null;
        // }
        // Log.d("ZoomAnimator", "postAnimation currentScale:"+currentScale);
        this.mapView.zoomEnd();
        mapView.getEventDispatcher().sendEmptyMessage(12);
        // 更新比例尺控件
        mapView.updateScaleBar();
    }
}