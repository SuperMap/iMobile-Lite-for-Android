package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * PolygonOverlay是Overlay的一个实现类，它表示可以显示在地图上方的多部分组成的面(即多面地物)覆盖物图层。专门针支持对 内部面包含岛洞的geoRegion格式数据的绘制。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class MultiPolygonOverlay extends Overlay {
    private static final String LOG_TAG = "com.supermap.android.maps.multiPolygonOverlay";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");

    // private List<Point2D> data;
    private MultiPolygon data;
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
    public MultiPolygonOverlay() {
        this.path = new Path();
        initDefLinePaint();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param linePaint 线画笔。
     */
    public MultiPolygonOverlay(Paint linePaint) {
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
     * 设置多个多边形组成的多面对象和边界框。
     * </p>
     * @param data 多面对象。
     * @param bbox 边界框。
     */
    public void setData(MultiPolygon data, BoundingBox bbox) {
        this.data = data;
        this.boundingBox = bbox;
        // validateData();
    }

    /**
     * <p>
     * 设置多个多边形组成的多面对象，并设置是否据此重新计算边界框。
     * </p>
     * @param data 多面对象。
     * @param recomputeBoundingBox 是否重新计算边界框。
     */
    public void setData(MultiPolygon data, boolean recomputeBoundingBox) {
        this.data = data;
        if (recomputeBoundingBox) {
            this.boundingBox = BoundingBox.calculateBoundingBoxGeoPoint(data.getPoints());
        }
        // validateData();
    }

    /**
     * <p>
     * 设置多个多边形组成的多面对象。
     * </p>
     * @param data 多面对象。
     */
    public void setData(MultiPolygon data) {
        this.data = data;
        this.boundingBox = BoundingBox.calculateBoundingBoxGeoPoint(data.getPoints());
        // validateData();
    }

    /**
     * <p>
     * 返回组成多面对象
     * </p>
     * @return 多面对象
     */
    public MultiPolygon getData() {
        MultiPolygon mp = new MultiPolygon();
        List<Point2D> list = new ArrayList<Point2D>();
        Iterator<Point2D> it = data.getPoints().iterator();
        while (it.hasNext()) {
            Point2D p = it.next();
            list.add(new Point2D(p.x, p.y));
        }
        mp.setPoints(list);
        mp.setParts(data.getParts());
        return mp;
    }

    private void validateData(List<Point2D> data) {
        if ((data != null) && (data.size() > 1)) {
            Point2D first = (Point2D) data.get(0);
            Point2D last = (Point2D) data.get(data.size() - 1);

            if (!first.equals(last))
                data.add(new Point2D(first.getX(), first.getY()));
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
     * 设置多面覆盖物的边界框。
     * </p>
     * @param boundingBox
     */
    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * <p>
     * 绘制多面覆盖物。
     * </p>
     * @param canvas 画布。
     * @param mapView 绘图的地图视图。
     * @param shadow 是否采用阴影效果。
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (data == null || data.getPoints() == null || data.getPoints().size() < 1) {
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
            // Point s1 = projection.toPixels((Point2D) this.data.get(0), point);
            // float x1 = s1.x;
            // float y1 = s1.y;
            List<List<Point2D>> pss = getPartPoints(this.data);
            int[] result = checkPolygonHole(pss);
            int len = result.length;
            for (int j = 0; j < pss.size(); j++) {
                int id = -1;
                if (j < len) {
                    id = result[j];
                }
                List<Point2D> data = pss.get(j);
                validateData(data);
                if (id != -1) {// 倒序绘制
                    List<Point2D> reverseData = new ArrayList<Point2D>();
                    for (int k = data.size() - 1; k >= 0; k--) {
                        reverseData.add(data.get(k));
                    }
                    setPath(reverseData, projection, canvas);
                } else {
                    setPath(data, projection, canvas);
                }
            }

            long end = System.currentTimeMillis();
            float proTime = (float) (end - start) / 1000.0F;
            if (this.debug)
//                Log.d(LOG_TAG,
//                        resource.getMessage(MapCommon.POLYGONOVERLAY_PROCESS_SHAPEPOINTS,
//                                new String[] { String.valueOf(proTime), String.valueOf(data.getPoints().size()) }));
            canvas.drawPath(this.path, this.linePaint);
            float distTime = (float) (System.currentTimeMillis() - end) / 1000.0F;
//            if (this.debug)
//                Log.d(LOG_TAG,
//                        resource.getMessage(MapCommon.POLYGONOVERLAY_DRAW_SHAPEPOINTS,
//                                new String[] { String.valueOf(distTime), String.valueOf(data.getPoints().size()) }));
        }
    }

    private void setPath(List<Point2D> data, Projection projection, Canvas canvas) {
        int i = 0;
        Point point = new Point();
        for (Point2D gp : data) {
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
    }

    private List<List<Point2D>> getPartPoints(MultiPolygon data) {
        if (data == null || data.getPoints() == null || data.getParts() == null || data.getPoints().size() < 1 || data.getParts().length < 1) {
            return null;
        }
        List<List<Point2D>> pss = new ArrayList<List<Point2D>>();
        int len = data.getParts().length;
        int start = 0;
        for (int i = 0; i < len; i++) {
            int count = data.getParts()[i];
            List<Point2D> ps = data.getPoints().subList(start, start + count);
            pss.add(ps);
            start += count;
        }
        return pss;
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
    private boolean isSelectedPolygon(MotionEvent event, Overlay overlay, MapView mapView) {
        MultiPolygonOverlay mpo = (MultiPolygonOverlay) overlay;
        if (mpo == null || mpo.getData() == null || mpo.getData().getPoints() == null) {
            return false;
        }
        List<Point2D> datas = mpo.getData().getPoints();
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
        if (this.data == null || this.data.getPoints() == null) {
            return false;
        }
        List<Point2D> pointList = this.data.getPoints();
        int[] parts = this.data.getParts();
        boolean isContains = false;
        if (pointList != null && pointList.size() > 0 && parts != null && parts.length > 0) {
            int fromIndex = 0;
            for (int i = 0; i < parts.length; i++) {
                int len = parts[i];
                // List ps = new ArrayList<Point2D>();
                List<Point2D> ps = pointList.subList(fromIndex, fromIndex + len);
                fromIndex += len;
                if (Util.contians(gp, ps)) {// 多面中有一个面包含点就是包含点，结束
                    isContains = true;
                    break;
                }

            }
        }
        return isContains;
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

    /**
     * <p>
     * 用于检测一个geoRegion内部面的岛洞包含关系，支持对 geoRegion格式数据输入
     * </p>
     * @param parts 需要进行检测的 Polygon中所有面的点集合
     * @return 返回一组int值的岛洞包含的检测结果，如果为-1，表示当前为岛。否则表示为洞，对应值记录其被包含的岛的索引。
     * @since 1.0.0
     */
    private int[] checkPolygonHole(List<List<Point2D>> parts) {
        if (parts == null || parts.size() < 1) {
            return null;
        }
        // 该算法假定服务端传输回来的part具有互不相交特性（要么包含，要么不相交），所以多边形的包含检测通过其中一个点的包含关系即可判断出来
        // 1、首先对面积做排序，基于大面积的对象肯定不可能被小面积的对象包含的逻辑,
        // 2、针对面积由大到小检测，最大的即为岛，不被其他对象包含的也就是岛。被岛所包含则为洞，被洞包含则为岛。.....
        PartArea[] boundsArray = new PartArea[parts.size()];

        for (int i = 0; i < parts.size(); i++) {
            BoundingBox bounds = BoundingBox.calculateBoundingBoxGeoPoint(parts.get(i));
            double area = bounds.getWidth() * bounds.getHeight();
            boundsArray[i] = new PartArea(i, area);
        }
        Arrays.sort(boundsArray);

        int[] resultArray = new int[parts.size()];
        // 面积最大的为岛
        if (boundsArray[0].id > 0 && boundsArray[0].id < parts.size()) {
            resultArray[boundsArray[0].id] = -1;
        }

        for (int m = 1; m < boundsArray.length; m++) {
            List<Point2D> innerLinearRing = parts.get(boundsArray[m].id);
            if (innerLinearRing.size() > 0) {
                // 遍历比当前面积大的part,查找其直接包含项
                for (int n = m - 1; n >= 0; n--) {
                    List<Point2D> outerLinearRing = parts.get(boundsArray[n].id);
                    // 如果包含，则
                    if (Util.pointInPolygon(new Point2D(innerLinearRing.get(0)), outerLinearRing)) {
                        // 外围是岛，当前是洞
                        if (-1 == resultArray[boundsArray[n].id]) {
                            resultArray[boundsArray[m].id] = boundsArray[n].id;
                        }
                        // 外围不是岛，当前则为岛
                        else {
                            resultArray[boundsArray[m].id] = -1;
                        }
                        break;
                    }
                    // 如果没有被任何大面积对象包含，则为顶级岛
                    if (n == 0) {
                        resultArray[boundsArray[m].id] = -1;
                    }
                }
            } else {
                // 面对像无效，暂定为岛
                resultArray[boundsArray[m].id] = -1;
            }
        }
        return resultArray;
    }

    static class PartArea implements Comparable<PartArea> {
        public int id;
        public double area;

        public PartArea(int id, double area) {
            super();
            this.id = id;
            this.area = area;
        }

        @Override
        public int compareTo(PartArea partArea) {
            double deta = partArea.area - this.area;// 倒序，大的在前
            int result = 0;
            if (deta > 0) {
                result = 1;
            } else if (deta < 0) {
                result = -1;
            }
            return result;
        }

    }

    // public static BoundingBox calcBounds(List<Point2D> points) {
    // if (points == null || points.size() < 1) {
    // return null;
    // }
    // double left = points.get(0).x;
    // double right = points.get(0).x;
    // double top = points.get(0).y;
    // double bottom = points.get(0).y;
    // int pointLen = points.size();
    //
    // for (int i = 0; i < pointLen; i++) {
    // Point2D point2D = points.get(i);
    // left = point2D.x < left ? point2D.x : left;
    // right = point2D.x > right ? point2D.x : right;
    // bottom = point2D.y < bottom ? point2D.y : bottom;
    // top = point2D.y > top ? point2D.y : top;
    // }
    // return new BoundingBox(new Point2D(left, top), new Point2D(right, bottom));
    // }

}
