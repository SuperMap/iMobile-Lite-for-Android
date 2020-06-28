package com.supermap.imobilelite.maps;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

/**
 * <p>
 * DrawableOverlay是Overlay的一个实现类，它表示可以显示在地图上方的图片覆盖物图层。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class DrawableOverlay extends Overlay {
    private static final String LOG_TAG = "com.supermap.android.maps.drawableoverlay";
    private Drawable drawable;
    private BoundingBox boundingBox;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public DrawableOverlay() {
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param drawable 绘图对象。
     * @param bbox 边界框。
     */
    public DrawableOverlay(Drawable drawable, BoundingBox bbox) {
        this();
        this.drawable = drawable;
        this.boundingBox = bbox;
    }

    /**
     * <p>
     * 在地图mapView上方绘制覆盖物。
     * </p>
     * @param canvas 绘图画布。
     * @param mapView 绘图的地图视图。
     * @param shadow 是否有阴影。
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if ((this.boundingBox == null) || (this.drawable == null) || (shadow)) {
            return;
        }
        try {
            Rect bounds = canvas.getClipBounds();
            canvas.save();
            if (mapView.getMapRotation() != 0.0F) {
                boolean clipScreen = false;
                if ((bounds.width() != mapView.getWidth()) || (bounds.height() != mapView.getHeight())) {
                    clipScreen = true;
                    canvas.save(2);
                    canvas.clipRect(0.0F, 0.0F, mapView.getWidth(), mapView.getHeight(), Op.REPLACE);
                }

                canvas.rotate(mapView.getMapRotation(), mapView.focalPoint.x, mapView.focalPoint.y);

                if (clipScreen) {
                    canvas.restore();
                }

            }

            Rect image_region = Util.createOriginRectFromBoundingBox(this.boundingBox, mapView);
            Projection rp = (Projection) mapView.getProjection();
            rp.offsetToFocalPoint(image_region);
            if (Rect.intersects(image_region, bounds)) {
                this.drawable.setBounds(image_region);
                this.drawable.draw(canvas);
            }
        } finally {
            canvas.restore();
        }
    }

    /**
     * <p>
     * 设置覆盖物的绘图对象和绘制边界框。
     * </p>
     * @param drawable 绘图对象。
     * @param bbox 边界框。
     */
    public void setDrawable(Drawable drawable, BoundingBox bbox) {
        if ((this.drawable != null) && ((this.drawable instanceof BitmapDrawable))) {
            ((BitmapDrawable) this.drawable).getBitmap().recycle();
            this.drawable = null;
        }

        if (drawable != null) {
            drawable.setDither(true);
            drawable.setFilterBitmap(true);
            this.drawable = drawable;
        }

        this.boundingBox = bbox;
    }

    /**
     * <p>
     * 返回覆盖物的绘图对象。
     * </p>
     * @return 覆盖物的绘图对象。
     */
    public Drawable getDrawable() {
        return this.drawable;
    }

    /**
     * <p>
     * 返回覆盖物在地图上绘制的边界框。
     * </p>
     * @return 覆盖物在地图上绘制的边界框。
     */
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    boolean onTap(MapView mapView) {
        return false;
    }

    /**
     * <p>
     * 判断是否发生点击事件。
     * </p>
     * @param gp 点击的坐标点。
     * @param mapView 点击的地图视图。
     * @return true表示有点击，反之没有。
     */
    public boolean onTap(Point2D gp, MapView mapView) {
        if ((this.boundingBox != null) && (this.boundingBox.contains(gp))) {
            boolean result = false;
            if (this.tapListener != null) {
                this.tapListener.onTap(gp, mapView);
                result = true;
            }

            result = (result) || (onTap(mapView));
            return result;
        }

        return false;
    }

    /**
     * <p>
     * 判断是否发生触碰事件。
     * </p>
     * @param evt 触屏操作对象。
     * @param mapView 操作所在的地图视图。
     * @return true表示有触屏操作，false表示没有。
     */
    public boolean onTouchEvent(MotionEvent evt, MapView mapView) {
        if ((this.touchListener != null) && (this.boundingBox != null)) {
            Point2D gp = mapView.getProjection().fromPixels((int) evt.getX(), (int) evt.getY());
            if (this.boundingBox.contains(gp)) {
                this.touchListener.onTouch(evt, mapView);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 销毁当前overlay。
     * </p>
     */
    public void destroy() {
        if ((this.drawable != null) && ((this.drawable instanceof BitmapDrawable))) {
            ((BitmapDrawable) this.drawable).getBitmap().recycle();
        }
        this.drawable = null;
        this.boundingBox = null;
    }
}