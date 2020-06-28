package com.supermap.imobilelite.maps;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * <p>
 * Overlay是一个基类，它表示可以显示在地图上方的覆盖物图层。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public abstract class Overlay {
    /**
     * <p>
     * 点击事件监听器。
     * </p>
     */
    protected OverlayTapListener tapListener;
    /**
     * <p>
     * 触碰事件监听器。
     * </p>
     */
    protected OverlayTouchEventListener touchListener;
    /**
     * <p>
     * 跟踪球事件监听器。
     * </p>
     */
    protected OverlayTrackballEventListener trackballListener;
    private String key;
    private int zIndex;

    // protected static final float SHADOW_X_SKEW = -0.9F;
    // protected static final float SHADOW_Y_SCALE = 0.5F;
    // public static final int CENTER_HORIZONTAL = 1;
    // public static final int CENTER_VERTICAL = 2;
    // public static final int CENTER = 3;
    // public static final int LEFT = 4;
    // public static final int RIGHT = 8;
    // public static final int TOP = 16;
    // public static final int BOTTOM = 32;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public Overlay() {
        this.key = "";
        this.zIndex = 0;
    }

    /**
     * <p>
     * 设置绘图对象的大小。
     * </p>
     * @param drawable 可绘制对象。
     * @param bitset 绘图对象位置。
     * @return 绘图对象，大小、位置等。
     */
    static Drawable setAlignment(Drawable drawable, int bitset) {
        if (drawable != null) {
            int w = drawable.getIntrinsicWidth();
            int h = drawable.getIntrinsicHeight();

            int l = -(w >> 1);
            int r = l + w;
            int t = -(h >> 1);
            int b = t + h;
            int count = 0;
            while ((bitset != 0) && (count++ < 3)) {
                if ((bitset & 0x1) > 0) {
                    l = -(w >> 1);
                    r = l + w;
                    bitset ^= 1;
                    continue;
                }
                if ((bitset & 0x2) > 0) {
                    t = -(h >> 1);
                    b = t + h;
                    bitset ^= 2;
                    continue;
                }
                if ((bitset & 0x20) > 0) {
                    t = -h;
                    b = 0;
                    bitset ^= 32;
                    continue;
                }
                if ((bitset & 0x10) > 0) {
                    t = 0;
                    b = h;
                    bitset ^= 16;
                    continue;
                }
                if ((bitset & 0x8) > 0) {
                    r = 0;
                    l = -w;
                    bitset ^= 8;
                    continue;
                }
                if ((bitset & 0x4) > 0) {
                    l = 0;
                    r = w;
                    bitset ^= 4;
                }
            }

            drawable.setBounds(l, t, r, b);
        }
        return drawable;
    }

    /**
     * <p>
     * 在某个偏移位置画一个Drawable的便捷方法。
     * </p>
     * 
     * @param canvas 画布。
     * @param drawable 绘制的Drawable。
     * @param x 像素坐标x。
     * @param y 像素坐标y。
     * @param shadow 是否带有阴影。
     */
    protected static void drawAt(Canvas canvas, Drawable drawable, int x, int y, boolean shadow) {
        try {
            canvas.save();
            canvas.translate(x, y);
            if (shadow) {
                drawable.setColorFilter(2130706432, Mode.SRC_IN);
                canvas.skew(-0.9F, 0.0F);
                canvas.scale(1.0F, 0.5F);
            }
            drawable.draw(canvas);
            if (shadow)
                drawable.clearColorFilter();
        } finally {
            canvas.restore();
        }
    }

    /**
     * <p>
     * 设置点击事件的监听器。
     * </p>
     * @param overlayTapListener 点击事件的监听器。
     */
    public void setTapListener(OverlayTapListener overlayTapListener) {
        this.tapListener = overlayTapListener;
    }

    /**
     * <p>
     * 设置触屏事件的监听器。
     * </p>
     * @param touchListener 触屏事件的监听器。
     */
    public void setTouchEventListener(OverlayTouchEventListener touchListener) {
        this.touchListener = touchListener;
    }

    /**
     * <p>
     * 设置跟踪球（移动）事件监听器。
     * </p>
     * @param trackballListener 跟踪球（移动）事件监听器。
     */
    public void setTrackballEventListener(OverlayTrackballEventListener trackballListener) {
        this.trackballListener = trackballListener;
    }

    /**
     * <p>
     * 绘制覆盖物。
     * </p>
     * @param canvas 画布。
     * @param mapView 指定绘制的地图视图。
     * @param shadow 是否带有阴影。
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
    }

    /**
     * <p>
     * 绘制覆盖物，返回是否绘制成功。。
     * </p>
     * @param canvas 画布
     * @param mapView 指定绘制的地图视图。
     * @param shadow 是否带有阴影。
     * @param when 绘制时间。
     * @return 是否绘制成功。
     */
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
        draw(canvas, mapView, shadow);
        return false;
    }

    /**
     * <p>
     * 判断是否有相应按键按下的操作。
     * </p>
     * @param keyCode 按键编码。
     * @param event 操作事件。
     * @param mapView 操作所在的地图视图。
     * @return True表示按键按下，False表示没有。
     */
    public boolean onKeyDown(int keyCode, KeyEvent event, MapView mapView) {
        return false;
    }

    /**
     * <p>
     * 判断是否有相应按键抬起的操作。
     * </p>
     * @param keyCode 按键编码。
     * @param event 操作事件。
     * @param mapView 操作所在的地图视图。
     * @return True表示按键抬起，False表示没有。
     */
    public boolean onKeyUp(int keyCode, KeyEvent event, MapView mapView) {
        return false;
    }

    /**
     * <p>
     * 判断是否具有点击操作。
     * </p>
     * @param p 点击的位置坐标。
     * @param mapView 操作所在的地图视图。
     * @return True表示有点击，False表示没有。
     */
    public boolean onTap(Point2D p, MapView mapView) {
        return false;
    }

    /**
     * <p>
     * 判断是否存在触屏操作。
     * </p>
     * @param evt 触屏操作对象。
     * @param mapView 操作所在的地图视图。
     * @return True表示有触屏操作，False表示没有。
     */
    public boolean onTouchEvent(MotionEvent evt, MapView mapView) {
        return false;
    }

    /**
     * <p>
     * 判断是否存在跟踪球事件发生。
     * </p>
     * @param evt 触屏操作对象。
     * @param mapView 操作所在的地图视图。
     * @return True表示有触屏操作，False表示没有。
     */
    public boolean onTrackballEvent(MotionEvent evt, MapView mapView) {
        return false;
    }

    /**
     * <p>
     * 销毁当前绘制的对象。
     * </p>
     */
    public void destroy() {
    }

    /**
     * <p>
     * 获取覆盖物的序号，用于排序。
     * </p>
     * @return 覆盖物的序号。
     */
    public int getZIndex() {
        return this.zIndex;
    }

    /**
     * <p>
     * 设置覆盖物的序号，用于排序。
     * </p>
     * @param zIndex 覆盖物的序号。
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * <p>
     * 获取覆盖物的Key，即唯一标识。
     * </p>
     * @return Key值。
     */
    public String getKey() {
        return this.key;
    }

    /**
     * <p>
     * 设置覆盖物的Key，即唯一标识。
     * </p>
     * @param key 唯一标识。
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * <p>
     * 跟踪球事件监听器。
     * </p>
     * <p>
     * 该接口监听跟踪球事件响应，用户可以根据需要实现该接口以处理相应事件。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract interface OverlayTrackballEventListener {
        /**
         * <p>
         * 处理跟踪球事件，用户需要实现该接口以处理相应事件。
         * </p>
         * @param paramMotionEvent 触屏操作对象。
         * @param paramMapView 操作所在的地图视图。
         */
        public abstract void onTrackballEvent(MotionEvent paramMotionEvent, MapView paramMapView);
    }

    /**
     * <p>
     * 触碰事件监听器。
     * </p>
     * <p>
     * 该接口监听触碰的事件响应，用户可以根据需要实现该接口以处理相应事件。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract interface OverlayTouchEventListener {
        /**
         * <p>
         * 处理触碰事件，用户需要实现该接口以处理相应事件。
         * </p>
         * @param paramMotionEvent 触屏操作对象。
         * @param paramMapView 操作所在的地图视图。
         */
        public abstract void onTouch(MotionEvent paramMotionEvent, MapView paramMapView);

        /**
         * <p>
         * 处理触碰事件，用户需要实现该接口以处理相应事件。
         * </p>
         * @param paramMotionEvent 触屏操作对象。
         * @param overlay 覆盖物图层。
         * @param paramMapView 操作所在的地图视图。
         */
        public abstract void onTouch(MotionEvent paramMotionEvent, Overlay overlay, MapView paramMapView);
    }

    /**
     * <p>
     * 点击事件监听器。
     * </p>
     * <p>
     * 该接口监听点击的事件响应，用户可以根据需要实现该接口以处理相应事件。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract interface OverlayTapListener {
        /**
         * <p>
         * 处理点击事件，用户需要实现该接口以处理相应事件。
         * </p>
         * @param paramGeoPoint 点击的点对象。
         * @param paramMapView  操作所在的地图视图。
         */
        public abstract void onTap(Point2D paramGeoPoint, MapView paramMapView);
        
        /**
         * <p>
         * 处理点击事件，用户需要实现该接口以处理相应事件。
         * </p>
         * @param paramGeoPoint 点击的点对象。
         * @param overlay 覆盖物图层。
         * @param paramMapView  操作所在的地图视图。
         */
        public abstract void onTap(Point2D paramGeoPoint, Overlay overlay, MapView paramMapView);
    }

    /**
     * <p>
     * 
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    static abstract interface Snappable {
        public abstract boolean onSnapToItem(int paramInt1, int paramInt2, Point paramPoint, MapView paramMapView);
    }
}