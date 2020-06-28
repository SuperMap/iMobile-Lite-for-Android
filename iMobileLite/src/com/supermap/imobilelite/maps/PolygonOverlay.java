package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * PolygonOverlay是Overlay的一个实现类，它表示可以显示在地图上方的闭合多边形覆盖物图层。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class PolygonOverlay extends Overlay {
    private static final String LOG_TAG = "com.supermap.android.maps.polygonoverlay";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");

    private List<Point2D> data;
    private BoundingBox boundingBox;
    private Paint linePaint;
    private Paint pointPaint;
    private Path path;
    private boolean showPoints = true;

    private boolean debug = false;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public PolygonOverlay() {
        this.path = new Path();
        initDefLinePaint();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param linePaint 线画笔。
     */
    public PolygonOverlay(Paint linePaint) {
        this.linePaint = linePaint;
        this.path = new Path();
    }

    private void initDefLinePaint() {
        if (linePaint == null) {
            linePaint = new Paint(1);
            linePaint.setColor(Color.argb(200, 10, 230, 250));
            linePaint.setStyle(Style.FILL_AND_STROKE);
            linePaint.setStrokeWidth(2);
            linePaint.setAntiAlias(true);
        }
    }

    /**
     * <p>
     * 设置组成多边形的点数组和边界框。
     * </p>
     * @param data 点数组。
     * @param bbox 边界框。
     */
    public void setData(List<Point2D> data, BoundingBox bbox) {
        this.data = data;
        this.boundingBox = bbox;
        validateData();
    }

    /**
     * <p>
     * 设置组成多边形的点数组，并设置是否据此重新计算边界框。
     * </p>
     * @param data 组成多边形的点数组。
     * @param recomputeBoundingBox 是否重新计算边界框。
     */
    public void setData(List<Point2D> data, boolean recomputeBoundingBox) {
        this.data = data;
        if (recomputeBoundingBox) {
            this.boundingBox = BoundingBox.calculateBoundingBoxGeoPoint(data);
        }
        validateData();
    }

    /**
     * <p>
     * 设置组成多边形的点数组。
     * </p>
     * @param data 组成多边形的点数组。
     */
    public void setData(List<Point2D> data) {
        this.data = data;
        this.boundingBox = BoundingBox.calculateBoundingBoxGeoPoint(data);
        validateData();
    }

    /**
     * <p>
     * 返回组成多边形的点数组
     * </p>
     * @return 组成多边形的点数组
     */
    public List<Point2D> getData() {
        List<Point2D> list = new ArrayList<Point2D>();
        Iterator<Point2D> it = data.iterator();
        while (it.hasNext()) {
            Point2D p = it.next();
            list.add(new Point2D(p.x, p.y));
        }
        return list;
    }

    private void validateData() {
        if ((this.data != null) && (this.data.size() > 1)) {
            Point2D first = (Point2D) this.data.get(0);
            Point2D last = (Point2D) this.data.get(this.data.size() - 1);

            if (!first.equals(last))
                this.data.add(new Point2D(first.getX(), first.getY()));
        }
    }

    /**
     * <p>
     * 设置线画笔。
     * </p>
     * @param paint 画笔。
     */
    public void setLinePaint(Paint paint) {
        this.linePaint = paint;
    }

    /**
     * <p>
     * 设置面覆盖物上显示的点，高亮效果。
     * </p>
     * @param showPoints
     * @param pointPaint
     */
    void setShowPoints(boolean showPoints, Paint pointPaint) {
        this.showPoints = showPoints;
        this.pointPaint = pointPaint;
    }

    /**
     * <p>
     * 设置是否显示面覆盖物上的节点
     * </p>
     * @param showPoints 是否显示
     */
    public void setShowPoints(boolean showPoints) {
        this.showPoints = showPoints;
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
     * 设置多边形覆盖物的边界框。
     * </p>
     * @param boundingBox
     */
    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * <p>
     * 绘制多边形覆盖物。
     * </p>
     * @param canvas 画布。
     * @param mapView 绘图的地图视图。
     * @param shadow 是否采用阴影效果。
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (data == null || data.size() < 1) {
            return;
        }
        if (this.debug) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.POLYGONOVERLAY_DRAW));
        }
        Projection projection = mapView.getProjection();

        Rect bounds = canvas.getClipBounds();

        Rect imageRegion = Util.createRectFromBoundingBox(this.boundingBox, mapView);

        int pad = (int) this.linePaint.getStrokeWidth() / 2;
        imageRegion.inset(-pad, -pad);

        Rect intersect = new Rect();
        boolean intersects = intersect.setIntersect(imageRegion, bounds);

        if (intersects) {
            long start = System.currentTimeMillis();
            this.path.reset();

            Point point = new Point();
            // Point s1 = projection.toPixels((Point2D) this.data.get(0), point);
            // float x1 = s1.x;
            // float y1 = s1.y;

            int i = 0;
            for (Point2D gp : this.data) {
                Point s = projection.toPixels(gp, point);
                float x2 = s.x;
                float y2 = s.y;

                if (i == 0) {
                    this.path.moveTo(x2, y2);
                } else {
                    this.path.lineTo(x2, y2);
                }

                if (this.showPoints) {
                    if (this.pointPaint == null)
                        this.pointPaint = createPointPaint();
                    canvas.drawCircle(x2, y2, (int) this.pointPaint.getStrokeWidth(), this.pointPaint);
                }

                i++;
            }
            long end = System.currentTimeMillis();
            float proTime = (float) (end - start) / 1000.0F;
            if (this.debug)
                Log.d(LOG_TAG, resource.getMessage(MapCommon.POLYGONOVERLAY_PROCESS_SHAPEPOINTS,
                        new String[] { String.valueOf(proTime), String.valueOf(data.size()) }));
            canvas.drawPath(this.path, this.linePaint);
            float distTime = (float) (System.currentTimeMillis() - end) / 1000.0F;
            if (this.debug)
                Log.d(LOG_TAG,
                        resource.getMessage(MapCommon.POLYGONOVERLAY_DRAW_SHAPEPOINTS, new String[] { String.valueOf(distTime), String.valueOf(data.size()) }));
        }
    }

    private Paint createPointPaint() {
        if (this.pointPaint == null) {
            Paint paint = new Paint(1);
            paint.setColor(this.linePaint.getColor());
            paint.setAlpha(this.linePaint.getAlpha());
            paint.setStrokeWidth(this.linePaint.getStrokeWidth());
            return paint;
        }
        return this.pointPaint;
    }

    /**
     * <p>
     * 判断是否发生点击事件。
     * </p>
     * @param gp 点击的坐标点。
     * @param mapView 点击的地图视图。
     * @return True表示有点击，反之没有。
     */
    public boolean onTap(Point2D gp, MapView mapView) {
        if ((this.tapListener != null) && (contains(gp))) {
            this.tapListener.onTap(gp, this, mapView);
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
            if (contains(gp) && isSelectedPolygon(evt, this, mapView)) {
                this.touchListener.onTouch(evt, this, mapView);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 选中点优先，即排除选中端点，选中多边形而且选中端点时，相对于没有选中多边形即不触发选中多边形的平移事件，选中点返回false
     * </p>
     * @param event
     * @param overlay
     * @param mapView
     * @return
     */
    public boolean isSelectedPolygon(MotionEvent event, Overlay overlay, MapView mapView) {
        PolygonOverlay po = (PolygonOverlay) overlay;
        List<Point2D> datas = po.getData();
        Point touchPoint = new Point((int) event.getX(), (int) event.getY());
        for (int i = 0; i < datas.size(); i++) {
            Point2D gp = datas.get(i);
            Point pt = mapView.getProjection().toPixels(gp, null);
            if (Util.distance(touchPoint, pt) < 8) {
                return false;
            }
        }
        return true;
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

    private boolean contains(Point2D gp) {
        int j = 0;
        int N = this.data.size() - 1;
        boolean oddNodes = false;// 是否是奇数个点
        double x = gp.getX();
        double y = gp.getY();
        for (int i = 0; i < N; i++) {
            j++;
            if (j == N) {
                j = 0;
            }
            // 判断多边形任意一条边的两个端点分别在该点的上下方(异侧)才不执行continue
            if (((((Point2D) this.data.get(i)).getY() >= y) || (((Point2D) this.data.get(j)).getY() < y))
                    && ((((Point2D) this.data.get(j)).getY() >= y) || (((Point2D) this.data.get(i)).getY() < y)))
                continue;
            // 满足x1+(y-y1)/(y2-y1)*(x2-x1)>=x即执行continue
            if (((Point2D) this.data.get(i)).getX() + (y - ((Point2D) this.data.get(i)).getY())
                    / (((Point2D) this.data.get(j)).getY() - ((Point2D) this.data.get(i)).getY())
                    * (((Point2D) this.data.get(j)).getX() - ((Point2D) this.data.get(i)).getX()) >= x) {
                continue;
            }
            oddNodes = !oddNodes;
        }

        return oddNodes;
    }

    /**
     * <p>
     * 销毁多边形覆盖物。
     * </p>
     */
    public void destroy() {
        this.data = null;
        this.boundingBox = null;
        this.path = null;
        this.linePaint = null;
        this.pointPaint = null;
    }

}