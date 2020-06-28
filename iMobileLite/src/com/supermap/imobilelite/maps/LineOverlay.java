package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * LineOverlay是Overlay的一个实现类，它表示可以显示在地图上方的 线形 覆盖物图层。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class LineOverlay extends Overlay {
    private static final String LOG_TAG = "com.supermap.android.maps.lineoverlay";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private static final int TOUCH_TOLERANCE = 5;
    private static final int EPSILON = 9;
    private int epsilon = 9;
    private List<Point2D> data;
    private ArrayList<Point> points;
    private volatile List<Point2D> simplified;
    private BoundingBox boundingBox;
    private Paint linePaint;
    private Paint pointPaint;
    private Path path;
    private boolean showPoints = true;

    private boolean simplify = false;// 原本默认为true,会导致simplify(..)方法数组越界异常

    private boolean debug = false;

    private MapView.MapViewEventListener listener = null;

    private HandlerThread simplifierThread = null;

    private SimplifierHandler simplifierHandler = null;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public LineOverlay() {
        this.path = new Path();
        initDefLinePaint();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param linePaint 线画笔。
     */
    public LineOverlay(Paint linePaint) {
        this();
        this.linePaint = linePaint;
    }

    private void initDefLinePaint() {
        if (linePaint == null) {
            linePaint = new Paint(1);
            linePaint.setColor(Color.argb(200, 10, 230, 250));
            linePaint.setStyle(Style.STROKE);
            linePaint.setStrokeWidth(2);
            linePaint.setAntiAlias(true);
        }
    }

    /**
     * <p>
     * 设置组成线覆盖物的点数组和边界框。
     * </p>
     * @param data 组成线覆盖物的点数组。
     * @param bbox 边界框。
     */
    public void setData(List<Point2D> data, BoundingBox bbox) {
        this.data = data;
        this.boundingBox = bbox;
        this.points = new ArrayList(data.size());
        this.simplified = null;
    }

    /**
     * <p>
     * 设置组成线覆盖物的点数组，并设置是否据此重新计算边界框。
     * </p>
     * @param data 组成线覆盖物的点数组。
     * @param recomputeBoundingBox 是否重新计算边界框。
     */
    public void setData(List<Point2D> data, boolean recomputeBoundingBox) {
        BoundingBox bbox = recomputeBoundingBox ? BoundingBox.calculateBoundingBoxGeoPoint(data) : this.boundingBox;
        setData(data, bbox);
    }

    /**
     * <p>
     * 设置组成线覆盖物的点数组。
     * </p>
     * @param data 点数组。
     */
    public void setData(List<Point2D> data) {
        setData(data, true);
    }

    /**
     * <p>
     * 返回组成线覆盖物的点数组
     * </p>
     * @return 组成线覆盖物的点数组
     */
    public List<Point2D> getData() {
        return this.data;
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
     * 设置线覆盖物上显示的点，高亮传入的点。
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
     * 设置是否显示线覆盖物上的节点
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
     * 设置绘制线覆盖物的边界框。
     * </p>
     * @param boundingBox 线覆盖物的边界框。
     */
    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * <p>
     * 是否简化。
     * </p>
     * @return True，简化；False，不简化。
     */
    public boolean isSimplify() {
        return this.simplify;
    }

    void setSimplify(boolean simplify, int tolerance) {
        if (tolerance > -1)
            this.epsilon = tolerance;
        else {
            this.epsilon = 9;
        }
        if (this.simplify == simplify)
            return;
        if (this.simplify) {
            this.points.clear();
            quitSimplifier();
            this.simplified = null;
        }
        this.simplify = simplify;
    }

    void setSimplify(boolean simplify) {
        setSimplify(simplify, -2);
    }

    /**
     * <p>
     * 绘制线覆盖物。
     * </p>
     * @param canvas 画布。
     * @param mapView 绘图的地图视图。
     * @param shadow 是否有阴影。
     */
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (this.debug) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.LINEOVERLAY_DRAW));
        }
//        Log.d(LOG_TAG, resource.getMessage(MapCommon.LINEOVERLAY_DRAW));
        if (this.listener == null) {
            this.listener = new EventListener();
            mapView.addMapViewEventListener(this.listener);
        }

        if (this.data == null || this.data.size() < 1) {
//            Log.d(LOG_TAG, "data null");
            return;
        }

        if (this.boundingBox == null) {
//            Log.d(LOG_TAG, "boundingBox null");
            this.boundingBox = BoundingBox.calculateBoundingBoxGeoPoint(data);
        }

        Projection projection = mapView.getProjection();

        Rect bounds = canvas.getClipBounds();

        Rect imageRegion = Util.createRectFromBoundingBox(this.boundingBox, mapView);
//        if(boundingBox.leftTop!=null&&boundingBox.rightBottom!=null){
//            Log.d(LOG_TAG, "boundingBox left:"+boundingBox.getLeft()+",top:"+boundingBox.getTop()+",right:"+boundingBox.getRight()+",bottom:"+boundingBox.getBottom());
//        }else{
//            Log.d(LOG_TAG, "boundingBox leftTop null");
//        }
//        Log.d(LOG_TAG, "imageRegion left:"+imageRegion.left+",top:"+imageRegion.top+",right:"+imageRegion.right+",bottom:"+imageRegion.bottom);
        int pad = (int) this.linePaint.getStrokeWidth() / 2;
        imageRegion.inset(-pad, -pad);

        boolean intersects = Rect.intersects(imageRegion, bounds);

        bounds.inset(-50, -50);

        BoundingBox screenBox = Util.createBoundingBoxFromRect(bounds, mapView);

        List<Point2D> data = this.data;

        if (this.simplify) {
            if ((this.simplifierHandler == null) || (this.simplifierThread == null)) {
                this.simplifierThread = new HandlerThread("simplifier", 1);
                this.simplifierThread.start();
                this.simplifierHandler = new SimplifierHandler(mapView, this.simplifierThread.getLooper());
            }
            if (this.simplified == null) {
                this.simplified = new ArrayList();
                mapView.post(new Simplifier(projection));
            } else if (this.simplified.size() != 0) {
                data = this.simplified;
            }
        }
//        Log.d(LOG_TAG, "intersects:"+intersects);
        if (intersects) {
            long start = System.currentTimeMillis();
            this.path.reset();

            Point point = new Point();
            Point s1 = projection.toPixels((Point2D) data.get(0), point);
            float x1 = s1.x;
            float y1 = s1.y;

            boolean inside = false;
            Point2D gpPrev = (Point2D) data.get(0);
            int i = 0;

            // for (GeoPoint gp : data) {//换成以下的迭代遍历不会有数组越界异常
            for (int c = 0; c < data.size(); c++) {
                Point2D gp = data.get(c);
                if (!screenBox.contains(gp)) {
                    if (inside) {
                        Point s = projection.toPixels(gp, point);
                        float x2 = s.x;
                        float y2 = s.y;
                        if(x2>mapView.getWidth()){
                            y2 = ((y2-y1)*mapView.getWidth()-x1*y2+x2*y1)/(x2-x1);
                            x2 = mapView.getWidth();
                        }
                        if(x2<0){
                            y2 = (-x1*y2+x2*y1)/(x2-x1);
                            x2 = 0;
                        }
                        if(y2>mapView.getHeight()){
                            x2=((x1-x2)*mapView.getHeight()+x2*y1-x1*y2)/(y1-y2);
                            y2=mapView.getHeight();
                        }
                        if(y2<0){
                            x2=(x2*y1-x1*y2)/(y1-y2);
                            y2=0;
                        }
                        this.path.lineTo(x2, y2);
                        x1 = x2;
                        y1 = y2;
                    } else if (c > 0) {
                        //线上任意相邻两点都不在屏幕内但是他们组成的线段跟屏幕有交点(即线段部分在屏幕内)需要绘制
                        Point2D gpLast = data.get(c - 1);
                        if (isLineIntersectToRect(gpLast, gp, screenBox)) {
                            Point s = projection.toPixels(gp, point);
                            Point sLast = projection.toPixels(gpLast, null);
                            if(sLast.x<0){
                                sLast.y = (s.x*sLast.y-sLast.x*s.y)/(s.x-sLast.x);
                                sLast.x = 0;
                            }
                            if(sLast.x>mapView.getWidth()){
                                sLast.y = (s.x*sLast.y-sLast.x*s.y+(s.y-sLast.y)*mapView.getWidth())/(s.x-sLast.x);
                                sLast.x = mapView.getWidth();
                            }
                            if (sLast.y<0){
                                sLast.x=(s.x*sLast.y-sLast.x*s.y)/(sLast.y-s.y);
                                sLast.y=0;
                            }
                            if (sLast.y>mapView.getHeight()){
                                sLast.x=((sLast.x-s.x)*mapView.getHeight()+s.x*sLast.y-sLast.x*s.y);
                                sLast.y=mapView.getHeight();
                            }
                            if(s.x>mapView.getWidth()){
                                s.y = ((s.y-sLast.y)*mapView.getWidth()-sLast.x*s.y+s.x*sLast.y)/(s.x-sLast.x);
                                s.x = mapView.getWidth();
                            }
                            if(s.x<0){
                                s.y = (-sLast.x*s.y+s.x*sLast.y)/(s.x-sLast.x);
                                s.x = 0;
                            }
                            if(s.y<0){
                                s.x=(s.x*sLast.y-sLast.x*s.y)/(sLast.y-s.y);
                                s.y=0;
                            }
                            if(s.y>mapView.getHeight()){
                                s.x=((sLast.x-s.x)*mapView.getHeight()+s.x*sLast.y-sLast.x*s.y)/(sLast.y-s.y);
                                s.y=mapView.getHeight();
                            }
                            this.path.moveTo(sLast.x, sLast.y);
                            this.path.lineTo(s.x, s.y);
                            x1 = s.x;
                            y1 = s.y;
                        }
                    }
                    inside = false;
                } else {
                    Point s = projection.toPixels(gp, point);
                    float x2 = s.x;
                    float y2 = s.y;
                    if (!inside) {
                        Point point1 = new Point();
                        Point prev = projection.toPixels(gpPrev, point1);
                        x1 = prev.x;
                        y1 = prev.y;
                        if(x1<0){
                            y1 = (x2*y1-x1*y2)/(x2-x1);
                            x1 = 0;
                        }
                        if(x1>mapView.getWidth()){
                            y1 = (x2*y1-x1*y2+(y2-y1)*mapView.getWidth())/(x2-x1);
                            x1 = mapView.getWidth();
                        }
                        if(y1<0){
                            x1=(x2*y1-x1*y2)/(y1-y2);
                            y1=0;
                        }
                        if(y1>mapView.getHeight()){
                            x1=((x1-x2)*mapView.getHeight()+x2*y1-x1*y2)/(y1-y2);
                            y1=mapView.getHeight();
                        }
                        this.path.moveTo(x1, y1);
                    }
                    if(x2>mapView.getWidth()){
                        y2 = ((y2-y1)*mapView.getWidth()-x1*y2+x2*y1)/(x2-x1);
                        x2 = mapView.getWidth();
                    }
                    if(x2<0){
                        y2 = (-x1*y2+x2*y1)/(x2-x1);
                        x2 = 0;
                    }
                    if(y2<0){
                        x2=(x2*y1-x1*y2)/(y1-y2);
                        y2=0;
                    }
                    if(y2>mapView.getHeight()){
                        x2=((x1-x2)*mapView.getHeight()+x2*y1-x1*y2)/(y1-y2);
                        y2=mapView.getHeight();
                    }
                    this.path.lineTo(x2, y2);
                    inside = true;
                    if (this.showPoints) {
                        if (this.pointPaint == null)
                            this.pointPaint = createPointPaint();
                        canvas.drawCircle(x2, y2, (int) this.pointPaint.getStrokeWidth(), this.pointPaint);
                    }
                    x1 = x2;
                    y1 = y2;
                    i++;
                }
                gpPrev = gp;
            }
            // }
            // 如果有点在屏幕内，但是有些点组成的线段在屏幕内，以下这样做就不能保证这些线段会被绘制
            // 没有一个点在屏幕内而且组成线的点大于一个时,虽然所有点都不在屏幕内，但是存在线上任意相邻两点的线段跟屏幕有交点(即线段部分在屏幕内)需要绘制
//            if (i == 0 && data.size() > 1) {
//                Log.d(LOG_TAG, "线上的所有点都不在屏幕内！");
//                for (int c = 1; c < data.size(); c++) {
//                    Point2D gp = data.get(c);
//                    Point2D gpLast = data.get(c - 1);
//                    Point s = projection.toPixels(gp, point);
//                    if (isLineIntersectToRect(gpLast, gp, screenBox)) {
//                        this.path.moveTo(x1, y1);
//                        this.path.lineTo(s.x, s.y);
//                    }
//                    x1 = s.x;
//                    y1 = s.y;
//                }
//            }

            long end = System.currentTimeMillis();
            float proTime = (float) (end - start) / 1000.0F;
            if (this.debug)
                Log.d(LOG_TAG,
                        resource.getMessage(MapCommon.LINEOVERLAY_PROCESS_SHAPEPOINTS, new Object[] { String.valueOf(proTime), String.valueOf(data.size()) }));

            canvas.drawPath(this.path, this.linePaint);
            float distTime = (float) (System.currentTimeMillis() - end) / 1000.0F;
            if (this.debug)
                Log.d(LOG_TAG,
                        resource.getMessage(MapCommon.LINEOVERLAY_DRAW_SHAPEPOINTS, new Object[] { String.valueOf(distTime), String.valueOf(data.size()) }));
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
     * @param gp 点击的坐标位置。
     * @param mapView 点击的地图视图。
     * @return True表示发生点击事件，反之没有发生。
     */
    public boolean onTap(Point2D gp, MapView mapView) {
        if ((this.tapListener != null) && (isNearLine(gp, mapView))) {
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
            if (isNearLine(gp, mapView) && isSelectedLine(evt, this, mapView)) {
                this.touchListener.onTouch(evt, this, mapView);
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
            if (isNearLine(gp, mapView)) {
                this.trackballListener.onTrackballEvent(evt, mapView);
                return true;
            }
        }
        return false;
    }

    private boolean isNearLine(Point2D gp, MapView mapView) {
        Point2D closestGP = Util.closestPoint(gp, this.data);
        Point closestPt = mapView.getProjection().toPixels(closestGP, null);
        Point pt = mapView.getProjection().toPixels(gp, null);

        float dist = Util.distance(pt, closestPt);

        return dist < 5.0F;
    }

    /**
     * <p>
     * 选中点优先，即选中线而且选中端点时，相对于没有选中线即不触发选中线的点击增加节点事件,选中点返回false
     * </p>
     * @param event
     * @param overlay
     * @param mapView
     * @return
     */
    public boolean isSelectedLine(MotionEvent event, Overlay overlay, MapView mapView) {
        LineOverlay lo = (LineOverlay) overlay;
        List<Point2D> datas = lo.getData();
        Point touchPoint = new Point((int) event.getX(), (int) event.getY());
        for (int i = 0; i < datas.size(); i++) {
            Point2D gp = datas.get(i);
            Point pt = mapView.getProjection().toPixels(gp, null);// toPixels
            if (Util.distance(touchPoint, pt) < 12) {
                return false;
            }
        }
        return true;
    }

    private boolean isLineIntersectToRect(Point2D gp1, Point2D gp2, BoundingBox screenBox) {
        boolean flag = false;
        if (gp1 != null && gp2 != null && screenBox != null) {
            Point2D lt = screenBox.leftTop;
            Point2D lb = new Point2D(screenBox.getLeft(), screenBox.getBottom());
            Point2D rb = screenBox.rightBottom;
            Point2D rt = new Point2D(screenBox.getRight(), screenBox.getTop());
            flag = Util.isIntersect(gp1.x, gp1.y, gp2.x, gp2.y, lt.x, lt.y, lb.x, lb.y);
            if (!flag) {
                flag = Util.isIntersect(gp1.x, gp1.y, gp2.x, gp2.y, lb.x, lb.y, rb.x, rb.y);
            }
            if (!flag) {
                flag = Util.isIntersect(gp1.x, gp1.y, gp2.x, gp2.y, rb.x, rb.y, rt.x, rt.y);
            }
            if (!flag) {
                flag = Util.isIntersect(gp1.x, gp1.y, gp2.x, gp2.y, rt.x, rt.y, lt.x, lt.y);
            }
        }
        return flag;
    }

    private void quitSimplifier() {
        if (this.simplifierHandler != null) {
            this.simplifierHandler.removeMessages(0);
            this.simplifierHandler.reuse.clear();
            this.simplifierHandler = null;
        }
        if (this.simplifierThread != null) {
            Looper looper = this.simplifierThread.getLooper();
            if (looper != null) {
                looper.quit();
            }
            this.simplifierThread = null;
        }
    }

    /**
     * <p>
     * 销毁线覆盖物。
     * </p>
     */
    public void destroy() {
        this.data = null;
        this.points = null;
        this.simplified = null;
        this.boundingBox = null;
        this.path = null;
        this.linePaint = null;
        this.pointPaint = null;
        this.simplify = false;
        quitSimplifier();
    }

    // 暂不用，先不公开
    private void addPoint(Point2D geoPoint, int maxPoints) {
        if (this.data == null)
            this.data = new LinkedList();
        this.data.add(geoPoint);
        if (maxPoints > 0) {
            while (this.data.size() > maxPoints)
                this.data.remove(0);
        }
        this.boundingBox = BoundingBox.calculateBoundingBoxGeoPoint(this.data);
    }

    private class Simplifier implements Runnable {
        Projection projection;

        private Simplifier(Projection projection) {
            this.projection = projection;
        }

        public void run() {
            if (LineOverlay.this.simplify) {
                int size = LineOverlay.this.data.size();
                LineOverlay.this.points.ensureCapacity(size);
                int p_size = LineOverlay.this.points.size();
                if (p_size < size) {
                    while (p_size++ < size) {
                        LineOverlay.this.points.add(new Point());
                    }
                }
                for (int i = 0; i < size; i++) {
                    Point p = (Point) LineOverlay.this.points.get(i);
                    this.projection.toPixels((Point2D) LineOverlay.this.data.get(i), p);
                }
                LineOverlay.this.simplifierHandler.sendEmptyMessage(0);
            }
        }
    }

    private class SimplifierHandler extends Handler {
        static final int SIMPLIFY = 0;
        Stack<int[]> reuse = new Stack();
        private MapView mapView;

        public SimplifierHandler(MapView mapView, Looper looper) {
            super();
            this.mapView = mapView;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ArrayList output = new ArrayList();
                    simplify(LineOverlay.this.points, LineOverlay.this.data, output, 0, LineOverlay.this.data.size() - 1);
                    // LineOverlay(LineOverlay.this, output);
                    simplified = output; // added by zhouxu
                    this.mapView.postInvalidate();
                    break;
            }

            super.handleMessage(msg);
        }

        private int[] getIndices(int start, int end) {
            int[] indices = null;
            if (this.reuse.isEmpty()) {
                indices = new int[] { start, end };
            } else {
                indices = (int[]) this.reuse.pop();
                indices[0] = start;
                indices[1] = end;
            }
            return indices;
        }

        private void simplify(ArrayList<Point> points, List<Point2D> data, List<Point2D> output, int start, int end) {
            Stack stack = new Stack();
            int[] startEnd = null;

            stack.push(getIndices(start, end));

            List indices = new ArrayList();
            indices.add(Integer.valueOf(start));
            indices.add(Integer.valueOf(end));
            Point out = new Point();
            while (!stack.isEmpty()) {
                startEnd = (int[]) stack.pop();
                start = startEnd[0];
                end = startEnd[1];

                this.reuse.push(startEnd);

                if (start + 1 >= end) {
                    continue;
                }
                int maxDistance = 0;
                int farthestIndex = 0;

                Point startPoint = (Point) points.get(start);
                Point endPoint = (Point) points.get(end);

                for (int i = start + 1; i < end; i++) {
                    Point p = (Point) points.get(i);
                    Util.closestPoint(p, startPoint, endPoint, out);
                    int dist = Util.distanceSquared(p.x, p.y, out.x, out.y);
                    if (dist > maxDistance) {
                        maxDistance = dist;
                        farthestIndex = i;
                    }
                }

                if (maxDistance > LineOverlay.this.epsilon) {
                    indices.add(Integer.valueOf(farthestIndex));
                    int[] indices1 = getIndices(start, farthestIndex);
                    int[] indices2 = getIndices(farthestIndex, end);

                    stack.push(indices1);
                    stack.push(indices2);
                }
            }

            Collections.sort(indices);
            int previous = -1;
            for (Iterator it = indices.iterator(); it.hasNext();) {
                int i = ((Integer) it.next()).intValue();
                if (i != previous) {
                    output.add(data.get(i));
                    previous = i;
                }
            }
        }
    }

    private class EventListener implements MapView.MapViewEventListener {
        private EventListener() {
        }

        public void longTouch(MapView mapView) {
        }

        public void move(MapView mapView) {
        }

        public void moveEnd(MapView mapView) {
        }

        public void moveStart(MapView mapView) {
        }

        public void touch(MapView mapView) {
        }

        public void zoomEnd(MapView mapView) {
            if ((LineOverlay.this.simplify) && (LineOverlay.this.simplifierHandler != null)) {
                LineOverlay.this.simplifierHandler.removeMessages(0);
                mapView.post(new Simplifier(mapView.getProjection()));
            }
        }

        public void zoomStart(MapView mapView) {
        }

        public void mapLoaded(MapView mapView) {
        }
    }
}