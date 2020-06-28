package com.supermap.imobilelite.maps;

import java.io.InputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.view.MotionEvent;

/**
 * <p>
 * PointOverlay是Overlay的一个实现类，它表示可以显示在地图上方的点覆盖物图层。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class PointOverlay extends Overlay {
    private Point2D data;
    private Paint pointPaint;
    private Bitmap bm;
    private int distDP = 6;    
    /**
     * <p>
     * 当前点Overlay是否处于手势选中的状态。
     * </p>
     */
    public boolean selectedFlag = false;
    /**
     * <p>
     * 记录上一次选中当前点Overlay的时刻，用于判断是否触发了双击。
     * </p>
     */
    public long previousTouchTime;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public PointOverlay() {
        super();
        initDefLinePaint();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param pPaint 画笔。
     */
    public PointOverlay(Paint pPaint) {
        pointPaint = pPaint;
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param point 点坐标。
     */
    public PointOverlay(Point2D point) {
        this.data = point;
        initDefLinePaint();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param point 点坐标。
     * @param context 上下文信息。
     */
    public PointOverlay(Point2D point, Context context) {
        this.data = point;
        InputStream is = context.getClass().getResourceAsStream("/com/supermap/android/maps/pointBitMap.png");
        bm = BitmapFactory.decodeStream(is);
        initDefLinePaint();
    }

    private void initDefLinePaint() {
        if (pointPaint == null) {
            pointPaint = new Paint();
            pointPaint.setColor(Color.argb(200, 255, 0, 0));
            pointPaint.setStyle(Style.STROKE);
            pointPaint.setStrokeCap(Paint.Cap.ROUND);
            pointPaint.setStrokeWidth(6);
            pointPaint.setAntiAlias(true);
        }
    }

    /**
     * <p>
     * 设置绘制点覆盖物的点数据。
     * </p>
     * @param data 点数据。
     */
    public void setData(Point2D data) {
        this.data = data;
    }

    /**
     * <p>
     * 获取绘制点覆盖物的点数据。
     * </p>
     * @return 点数据。
     */
    public Point2D getData() {
        return data;
    }

    /**
     * <p>
     * 设置点画笔。
     * </p>
     * @param paint 画笔。
     */
    public void setPointPaint(Paint paint) {
        this.pointPaint = paint;
    }

    /**
     * <p>
     * 设置点图片。
     * </p>
     * @param bitmap 图片。
     */
    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            this.bm = bitmap;
        }
    }

    /**
     * <p>
     * 绘制点覆盖物。
     * </p>
     * @param canvas 画布。
     * @param mapView 所在的地图视图。
     * @param shadow 是否有阴影。
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (data == null) {
            return;
        }
        Rect bounds = canvas.getClipBounds();
        Projection projection = mapView.getProjection();
        Point point = new Point();
        Point s = projection.toPixels(this.data, point);
        // 过滤掉不在屏幕范围内的点，保证每次值绘制屏幕内的点，增强渲染效率
        if (!bounds.contains(s.x, s.y)) {
            return;
        }
        if (bm != null) {
            // 图片的中心点坐落在绘制的点上
            canvas.drawBitmap(bm, s.x - bm.getWidth() / 2, s.y - bm.getHeight() / 2, null);
        } else {
            canvas.drawPoint(s.x, s.y, pointPaint);
        }
    }

    /**
     * <p>
     * 判断是否发生点击事件。
     * </p>
     * @param gp 点击的坐标位置。
     * @param mapView 点击的地图视图。
     * @return true表示发生点击事件，反之没有发生。
     */
    public boolean onTap(Point2D gp, MapView mapView) {
        if (this.tapListener != null) {
            Point touchPoint = mapView.getProjection().toPixels(gp, new Point());
            if (isNearPoint(touchPoint, mapView)) {
                this.tapListener.onTap(gp, this, mapView);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 判断是否发生触屏操作。
     * </p>
     * @param evt 触屏操作对象。
     * @param mapView 操作所在的地图视图。
     * @return true表示有触屏操作，false表示没有。
     */
    public boolean onTouchEvent(MotionEvent evt, MapView mapView) {
        if (this.touchListener != null) {
            Point touchPoint = new Point((int) evt.getX(), (int) evt.getY());
            boolean selected = isNearPoint(touchPoint, mapView);
            switch (evt.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 判断是否选中pointOverlay
                selectedFlag = selected;
                if (selectedFlag) {
                    this.touchListener.onTouch(evt, this, mapView);
                }
                break;
            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                // 如果pointOverlay被选中，则响应平移事件来实现点的拖拉
                if (selectedFlag) {
                    this.touchListener.onTouch(evt, this, mapView);
                }
                break;
            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                if (selectedFlag) {
                    this.touchListener.onTouch(evt, this, mapView);
                }
                // 触碰抬起时，取消点被选中状态
                selectedFlag = false;
                break;
            }
            return selected;
        }
        return false;
    }

    /**
     * <p>
     * 判断是否选中点，即判断触碰点是否在点overlay的附近，误差像素为5，因为手指一般触碰的范围大一些。
     * </p>
     * @param point 触碰点对象。
     * @param mapView 操作所在的地图视图。
     * @return true表示选中，false表示未选中。
     */
    public boolean isNearPoint(Point point, MapView mapView) {
        if (this.data == null) {
            return false;
        }
//        Log.d("", "isNearPoint data x:"+data.x+",Y:"+data.y);
        Point dataPoint = mapView.getProjection().toPixels(this.data, new Point());// toPixels
        int distX = Math.abs(point.x - dataPoint.x);
        int distY = Math.abs(point.y - dataPoint.y);
//        Log.e("", "isNearPoint distX:"+distX+",distY:"+distY);
        if (bm != null) {
            int distWidth = bm.getWidth() / 2;
            int distHeight = bm.getHeight() / 2;
            return (distX <= distWidth) && (distY <= distHeight);
        } else {
            int distPx = Math.round((distDP * mapView.getDensity()));
            return (distX <= distPx) && (distY <= distPx);
        }
    }

    /**
     * <p>
     * 判断是否存在跟踪球事件发生。
     * </p>
     * @param evt 触屏操作对象。
     * @param mapView 操作所在的地图视图。
     * @return true表示有跟踪球事件，false表示没有。
     */
    public boolean onTrackballEvent(MotionEvent evt, MapView mapView) {
        if (this.trackballListener != null) {
            this.trackballListener.onTrackballEvent(evt, mapView);
            return true;
        }
        return false;
    }

    /**
     * <p>
     * 销毁点覆盖物。
     * </p>
     */
    public void destroy() {
        this.data = null;
        this.pointPaint = null;
    }

    /**
     * <p>
     * 设置点选是否选中的允许误差dp值
     * </p>
     * @param distDP dp值
     * @since 7.0.0
     */
    public void setDistDP(int distDP) {
        this.distDP = distDP;
    }

}
