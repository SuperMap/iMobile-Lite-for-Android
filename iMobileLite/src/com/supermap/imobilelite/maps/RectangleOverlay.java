package com.supermap.imobilelite.maps;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;


/**
 * <p>
 * RectangleOverlay是Overlay的一个实现类，它表示可以显示在地图上方的 矩形 覆盖物图层。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
class RectangleOverlay extends Overlay {
    private static final String LOG_TAG = "com.supermap.android.maps.rectangleoverlay";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private BoundingBox boundingBox;
    private Paint paint;
    private Path path;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param boundingBox 边界框。
     * @param paint 画笔。
     */
    public RectangleOverlay(BoundingBox boundingBox, Paint paint) {
        this.boundingBox = boundingBox;
        this.paint = paint;
        this.path = new Path();
    }

    /**
     * <p>
     * 设置矩形覆盖物的边界框。
     * </p>
     * @param boundingBox
     */
    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * <p>
     * 设置画笔。
     * </p>
     * @param paint 画笔。
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * <p>
     * 绘制矩形覆盖物。
     * </p>
     * @param canvas 画布。
     * @param mapView 绘制的地图视图。
     * @param shadow 是否采用阴影效果。
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Log.d(LOG_TAG, resource.getMessage(MapCommon.RECTANGLEOVERLAY_DRAW));
        Projection projection = mapView.getProjection();
        this.path.reset();
        Point pt1 = projection.toPixels(this.boundingBox.leftTop, null);
        Point pt2 = projection.toPixels(new Point2D(this.boundingBox.rightBottom.getX(), this.boundingBox.leftTop.getY()), null);
        Point pt3 = projection.toPixels(this.boundingBox.rightBottom, null);
        Point pt4 = projection.toPixels(new Point2D(this.boundingBox.leftTop.getX(), this.boundingBox.rightBottom.getY()), null);
        this.path.moveTo(pt1.x, pt1.y);
        this.path.lineTo(pt2.x, pt2.y);
        this.path.lineTo(pt3.x, pt3.y);
        this.path.lineTo(pt4.x, pt4.y);
        this.path.lineTo(pt1.x, pt1.y);
        canvas.drawPath(this.path, this.paint);
    }

    /**
     * <p>
     * 判断是否发生点击事件。
     * </p>
     * @param p 点击的坐标位置。
     * @param mapView 点击的地图视图。
     * @return True表示发生点击事件，反之没有发生。
     */
    public boolean onTap(Point2D p, MapView mapView) {
        if ((this.tapListener != null) && (contains(mapView, p))) {
            this.tapListener.onTap(p, mapView);
            return true;
        }
        return false;
    }

    /**
     * <p>
     * 判断是否发生触屏操作。
     * </p>
     * @param evt 触屏操作对象。
     * @param mapView 操作所在的地图视图。
     * @return True表示有触屏操作，False表示没有。
     */
    public boolean onTouchEvent(MotionEvent evt, MapView mapView) {
        if (this.touchListener != null) {
            Point2D gp = mapView.getProjection().fromPixels((int) evt.getX(), (int) evt.getY());
            if (contains(mapView, gp)) {
                this.touchListener.onTouch(evt, mapView);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 判断是否存在跟踪球事件发生。
     * </p>
     * @param evt 触屏操作对象。
     * @param mapView 操作所在的地图视图。
     * @return True表示有跟踪球事件，False表示没有。
     */
    public boolean onTrackballEvent(MotionEvent evt, MapView mapView) {
        if (this.trackballListener != null) {
            Point2D gp = mapView.getProjection().fromPixels((int) evt.getX(), (int) evt.getY());
            if (contains(mapView, gp)) {
                this.trackballListener.onTrackballEvent(evt, mapView);
                return true;
            }
        }
        return false;
    }

    private boolean contains(MapView mapView, Point2D gp) {
        return this.boundingBox.contains(gp);
    }

    /**
     * <p>
     * 销毁矩形覆盖物。
     * </p>
     */
    public void destroy() {
        this.paint = null;
        this.boundingBox = null;
    }
}