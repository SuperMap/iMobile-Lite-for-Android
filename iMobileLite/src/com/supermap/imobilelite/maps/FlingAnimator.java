package com.supermap.imobilelite.maps;

import android.graphics.Point;
import android.widget.Scroller;

class FlingAnimator extends Animator {
    Scroller scroller;
    private int startX;
    private int startY;
    private static final float SCROLL_FRICTION = 0.25F;
    private static final int DURATION = 600;
    private Point2D point;

    public FlingAnimator(MapView mapView) {
        super(mapView);
        this.scroller = new Scroller(mapView.getContext());
    }

    public FlingAnimator(MapView mapView, Runnable runnable) {
        super(mapView, runnable);
    }

    public void animate(int toX, int toY) {
        super.startAnimation();
        this.startX = toX;
        this.startY = toY;
        int endX = this.mapView.getFocalPoint().x;
        int endY = this.mapView.getFocalPoint().y;

        if (!this.scroller.isFinished())
            this.scroller.forceFinished(false);
        else
            this.scroller.startScroll(this.startX, this.startY, endX - this.startX, endY - this.startY, DURATION);
    }

    public void animate(Point2D point) {
        Projection p = this.mapView.getProjection();
        if (p == null)
            return;
        Point end = p.toPixels(point, null);
        this.point = point;
        animate(end.x, end.y);
    }

    public void animate(Point point, float velocityX, float velocityY) {
        super.startAnimation();
        this.point = null;
        this.startX = point.x;
        this.startY = point.y;
        if (!this.scroller.isFinished()) {
            this.scroller.forceFinished(true);
        }
        this.scroller.fling(point.x, point.y, (int) (velocityX * SCROLL_FRICTION), (int) (velocityY * SCROLL_FRICTION), -2147483648, 2147483647, -2147483648, 2147483647);
    }

    public void preAnimation() {
        mapView.getEventDispatcher().sendEmptyMessage(21);
    }

    /**
     * 地图移动时调用
     */
    private void move() {
        Projection p = this.mapView.getProjection();
        int x = this.startX + (this.mapView.getFocalPoint().x - this.scroller.getCurrX());
        int y = this.startY + (this.mapView.getFocalPoint().y - this.scroller.getCurrY());

        this.startX = this.scroller.getCurrX();
        this.startY = this.scroller.getCurrY();
        Point2D newCenter = p.fromPixels(x, y);
        this.mapView.centerGeoPoint = newCenter;
        mapView.getEventDispatcher().sendEmptyMessage(22);
    }

    /**
     * 地图移动时调用
     */
    public boolean doAnimation() {
        if (this.scroller.computeScrollOffset()) {
            move();
            return true;
        }
        move();
        return false;
    }

    public void postAnimation() {
        if (this.point != null) {
            this.mapView.centerGeoPoint = this.point;
            this.mapView.invalidate();
        }
        this.point = null;
        finishPan();
    }

    public void stopAnimation(boolean jumpToFinish) {
        super.stopAnimation(jumpToFinish);
        if (jumpToFinish)
            this.scroller.abortAnimation();
        else
            this.scroller.forceFinished(true);
    }

    public void finishPan() {
        mapView.getEventDispatcher().sendEmptyMessage(23);
    }
}