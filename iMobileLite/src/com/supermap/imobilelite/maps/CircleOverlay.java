package com.supermap.imobilelite.maps;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * <p>
 * 圆型覆盖物图层。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
public class CircleOverlay extends Overlay {
    private static final String LOG_TAG = "com.supermap.android.maps.circleoverlay";
    private Point2D center;
    private int radiusPixels;
    private double radiusMeters;
    private Paint paint;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param center 圆形覆盖物的中心点，地理位置（经纬度）。
     * @param radius 圆形覆盖物的半径，单位是像素。
     * @param paint 绘图画笔。
     */
    public CircleOverlay(Point2D center, int radius, Paint paint) {
        this.center = center;
        this.radiusPixels = radius;
        this.paint = paint;
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param center 圆形覆盖物的中心点，地理位置（经纬度）。
     * @param radius 圆形覆盖物的半径，单位是米。
     * @param paint 绘图画笔。
     */
    public CircleOverlay(Point2D center, double radius, Paint paint) {
        this.center = center;
        this.radiusMeters = radius;
        this.paint = paint;
    }

    /**
     * <p>
     * 设置绘制的中心点。
     * </p>
     * @param center 中心点。
     */
    public void setCenter(Point2D center) {
        this.center = center;
    }

    /**
     * <p>
     * 设置绘图画笔。
     * </p>
     * @param paint 绘图画笔。
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * <p>
     * 设置绘制的半径，单位是像素。
     * </p>
     * @param radius 以像素为单位的半径。
     */
    public void setRadiusPixels(int radius) {
        this.radiusPixels = radius;
        this.radiusMeters = 0.0D;
    }

    /**
     * <p>
     * 设置绘制的半径，单位是米。
     * </p>
     * @param radius 以米为单位的半径。
     */
    public void setRadiusMeters(double radius) {
        this.radiusMeters = radius;
        this.radiusPixels = 0;
    }

    /**
     * <p>
     * 绘图。
     * </p>
     * @param canvas
     * @param mapView 地图视图。
     * @param shadow
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        int radius = 0;
        Projection projection = mapView.getProjection();

        Point screen = projection.toPixels(this.center, null);

        int x = screen.x;
        int y = screen.y;

        Rect bounds = canvas.getClipBounds();

        if (this.radiusMeters > 0.0D)
            radius = (int) mapView.getProjection().metersToEquatorPixels((float) this.radiusMeters);
        else {
            radius = this.radiusPixels;
        }

        Rect imageRegion = new Rect(x - radius, y - radius, x + radius, y + radius);

        int pad = (int) this.paint.getStrokeWidth() / 2;
        imageRegion.inset(-pad, -pad);

        if (imageRegion.intersect(bounds))
            canvas.drawCircle(x, y, radius, this.paint);
    }

    public boolean onTap(Point2D p, MapView mapView) {
        if (this.tapListener != null) {
            Point pt = mapView.getProjection().toPixels(p, null);
            if (contains(pt, mapView)) {
                this.tapListener.onTap(p, mapView);
                return true;
            }
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent evt, MapView mapView) {
        if (this.touchListener != null) {
            Point pt = new Point((int) evt.getX(), (int) evt.getY());
            if (contains(pt, mapView)) {
                this.touchListener.onTouch(evt, mapView);
                return true;
            }
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent evt, MapView mapView) {
        if (this.trackballListener != null) {
            Point pt = new Point((int) evt.getX(), (int) evt.getY());
            if (contains(pt, mapView)) {
                this.trackballListener.onTrackballEvent(evt, mapView);
                return true;
            }
        }
        return false;
    }

    private boolean contains(Point pt, MapView mapView) {
        Point center = mapView.getProjection().toPixels(this.center, null);

        int radius = 0;
        if (this.radiusMeters > 0.0D)
            radius = (int) mapView.getProjection().metersToEquatorPixels((float) this.radiusMeters);
        else {
            radius = this.radiusPixels;
        }

        int dx = center.x - pt.x;
        int dy = center.y - pt.y;

        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= radius;
    }

    public void destroy() {
        this.paint = null;
    }
}