package com.supermap.imobilelite.maps;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * EllipseOverlay是Overlay的一个实现类，它表示可以显示在地图上方的 椭圆形状 覆盖物图层。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
class EllipseOverlay extends Overlay {
    private static final String LOG_TAG = "com.supermap.android.maps.ellipseoverlay";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private BoundingBox boundingBox;
    private Paint paint;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param boundingBox 边界框。
     * @param paint 画笔。
     */
    public EllipseOverlay(BoundingBox boundingBox, Paint paint) {
        this.boundingBox = boundingBox;
        this.paint = paint;
    }

    /**
     * <p>
     * 设置椭圆覆盖物的边界框。
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
     * @param paint
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * <p>
     * 绘制椭圆覆盖物。
     * </p>
     * @param canvas 画布。
     * @param mapView 绘制的地图视图。
     * @param shadow 是否采用阴影效果。
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Log.d(LOG_TAG, resource.getMessage(MapCommon.ELLIPSEOVERLAY_DRAW));
        Projection projection = mapView.getProjection();

        Point ul = projection.toPixels(this.boundingBox.leftTop, null);
        Point lr = projection.toPixels(this.boundingBox.rightBottom, null);

        RectF screenBox = new RectF();
        screenBox.set(ul.x, ul.y, lr.x, lr.y);

        RectF bounds = new RectF();
        bounds.set(canvas.getClipBounds());

        if (RectF.intersects(screenBox, bounds))
            canvas.drawOval(screenBox, this.paint);
    }

    /**
     * <p>
     * 判断是否发生点击事件。
     * </p>
     * @param p 点击的坐标点。
     * @param mapView 点击的地图视图。
     * @return True表示有点击，反之没有。
     */
    public boolean onTap(Point2D p, MapView mapView) {
        if ((this.tapListener != null) && (contains(p))) {
            this.tapListener.onTap(p, mapView);
            return true;
        }

        return false;
    }

    /**
     * <p>
     * 判断是否发生触碰事件。
     * </p>
     * @param evt 触屏操作对象。
     * @param mapView 操作所在的地图视图。
     * @return True表示有触屏操作，False表示没有。
     */
    public boolean onTouchEvent(MotionEvent evt, MapView mapView) {
        if (this.touchListener != null) {
            Point2D gp = mapView.getProjection().fromPixels((int) evt.getX(), (int) evt.getY());
            if (contains(gp)) {
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
            if (contains(gp)) {
                this.trackballListener.onTrackballEvent(evt, mapView);
                return true;
            }
        }
        return false;
    }

    private boolean contains(Point2D pt) {
        return this.boundingBox.contains(pt);
    }

    /**
     * <p>
     * 销毁椭圆覆盖物。
     * </p>
     */
    public void destroy() {
        this.paint = null;
        this.boundingBox = null;
    }
}