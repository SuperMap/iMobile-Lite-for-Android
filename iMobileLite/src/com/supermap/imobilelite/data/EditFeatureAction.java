package com.supermap.imobilelite.data;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PointOverlay;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.maps.Util;
import com.supermap.imobilelite.resources.DataCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 支持操作 编辑地物 交互的封装类，支持地物端点的拖拉，地物面的整体平移，支持在地物边缘线上增加一个节点
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
public class EditFeatureAction {
    private static final String LOG_TAG = "com.supermap.imobilelite.data.editfeatureaction";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.DataCommon");
    private MapView mapView;
    private List<Overlay> overlayList = new ArrayList<Overlay>();
    // 创建点 Overlay对象的处理触碰事件的监听器对象
    private PointOverlayTouchEventListener potel;
    // 创建线 Overlay对象的处理触碰事件的监听器对象
    private LineOverlayTouchEventListener lotel;
    // 创建面 Overlay对象的处理触碰事件的监听器对象
    private PolygonOverlayTouchEventListener pnotel;
    private Paint polygonPaint;
    // private Paint pointPaint;
    private Paint linePaint;

    public EditFeatureAction(MapView mapView) {
        super();
        this.mapView = mapView;
        polygonPaint = getPolygonPaint();
        // pointPaint = getPointPaint();
        linePaint = getLinePaint();
    }

    /**
     * <p>
     * 使地物处于可编辑状态，构造地物的高亮点、线、面对象的Overlay对象，并给这些对象分别设置一个处理触碰事件的监听器
     * </p>
     * @param pointList 构成地物的点集合
     */
    public void doEditFeature(List<Point2D> pointList) {
        resetEditPoints(pointList);
        // 以下是测试用的
        // List<Point2D> pointList = new ArrayList<Point2D>();
        // Point2D firstPoint2D = new Point2D(3803.0, -3218.0);
        // pointList.add(firstPoint2D);
        // pointList.add(new Point2D(5099.0, -3879.0));
        // pointList.add(new Point2D(3733.0, -4819.0));
        // pointList.add(firstPoint2D);

        // 创建点 Overlay对象的处理触碰事件的监听器对象
        potel = new PointOverlayTouchEventListener(pointList);
        // 创建线 Overlay对象的处理触碰事件的监听器对象
        lotel = new LineOverlayTouchEventListener(pointList);
        // 创建面 Overlay对象的处理触碰事件的监听器对象
        pnotel = new PolygonOverlayTouchEventListener();
        // 创建组成高亮地物的点线面Overlay对象，并设置处理触碰事件的监听器对象
        initEditOverlays(pointList, mapView);
        mapView.invalidate();
    }

    /**
     * <p>
     * 修正构成地物的点集合，保证头尾的点对象是一个，而不仅仅是坐标相等
     * </p>
     * @param pointList
     */
    private void resetEditPoints(List<Point2D> pointList) {
        if (pointList == null) {
            return;
        }
        int size = pointList.size();
        Point2D first = pointList.get(0);
        Point2D last = pointList.get(size - 1);
        if (first != last) {
            if (first.x == last.x && first.y == last.y) {
                pointList.remove(size - 1);
                pointList.add(first);
            } else {
                pointList.add(first);
            }
        }
    }

    /**
     * <p>
     * 创建组成高亮地物的点线面Overlay对象，并设置处理触碰事件的监听器对象
     * </p>
     * @param pointList
     * @param mapView
     */
    private void initEditOverlays(List<Point2D> pointList, MapView mapView) {
        if (pointList == null) {
            return;
        }
        List<Overlay> overlays = mapView.getOverlays();
        for (int i = 0; i < pointList.size() - 1; i++) {
            PointOverlay pointOverlay = new PointOverlay(pointList.get(i), mapView.getContext());
            // pointOverlay.setPointPaint(pointPaint);
            overlays.add(pointOverlay);
            overlayList.add(pointOverlay);
            // 给点 Overlay对象设置 处理触碰事件的监听器对象
            pointOverlay.setTouchEventListener(potel);
            List<Point2D> linePoints = new ArrayList<Point2D>();
            linePoints.add(pointList.get(i));
            linePoints.add(pointList.get(i + 1));
            LineOverlay lineOverlay = new LineOverlay(linePaint);
            overlays.add(lineOverlay);
            overlayList.add(lineOverlay);
            lineOverlay.setData(linePoints);
            // 给线 Overlay对象设置 处理触碰事件的监听器对象
            lineOverlay.setTouchEventListener(lotel);
        }
        // 创建组成地物的一个 Overlay面对像
        PolygonOverlay polygonOverlay = new PolygonOverlay(polygonPaint);
        overlays.add(polygonOverlay);
        overlayList.add(polygonOverlay);
        polygonOverlay.setData(pointList);
        // 给面 Overlay对象设置 处理触碰事件的监听器对象
        polygonOverlay.setTouchEventListener(pnotel);
    }

    /**
     * <p>
     * 停止或退出编辑状态，移除构成地物的高亮点线面Overlay对象，刷新地图
     * </p>
     */
    public void stopEditFeature() {
        removeEditOverlays();
        if (potel != null) {
            potel = null;
        }
        if (lotel != null) {
            lotel = null;
        }
        if (pnotel != null) {
            pnotel = null;
        }
        mapView.invalidate();
    }

    /**
     * <p>
     * 移除构成地物的高亮点线面Overlay对象
     * </p>
     */
    private void removeEditOverlays() {
        for (int i = 0; i < overlayList.size(); i++) {
            mapView.getOverlays().remove(overlayList.get(i));
        }
        overlayList.clear();
    }

    /**
     * <p>
     * 设置绘制高亮地物面的画笔风格，保证有填充色
     * </p>
     * @return
     */
    private Paint getPolygonPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);

        return paint;
    }

    private Paint getPointPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(200, 255, 0, 0));
        paint.setStyle(Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(16);
        paint.setAntiAlias(true);

        return paint;
    }

    private Paint getLinePaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(200, 10, 230, 250));
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        return paint;
    }

    /**
     * <p>
     * 处理 点 Overlay对象的触碰事件监听器
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    private class PointOverlayTouchEventListener implements Overlay.OverlayTouchEventListener {
        private List<Point2D> pointList = new ArrayList<Point2D>();

        public PointOverlayTouchEventListener(List<Point2D> point2dList) {
            super();
            pointList = point2dList;
        }

        @Override
        public void onTouch(MotionEvent event, MapView mapView) {
            onTouch(event, null, mapView);
        }

        @Override
        public void onTouch(MotionEvent event, Overlay overlay, MapView mapView) {
            if (!(overlay instanceof PointOverlay)) {
                return;
            }
            PointOverlay pointOverlay = (PointOverlay) overlay;
            // mapView.setUseScrollEvent(false);
            Point2D gp = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                Point touchPoint = new Point((int) event.getX(), (int) event.getY());
                // 判断是否选中pointOverlay
                pointOverlay.selectedFlag = pointOverlay.isNearPoint(touchPoint, mapView);
                break;
            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                // 如果pointOverlay被选中，则响应平移事件来实现点的拖拉
                if (pointOverlay.selectedFlag) {
                    updateOverlay(overlay, gp);
                    mapView.invalidate();
                }
                break;
            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                // if (selectedFlag) {
                // updateOverlay(overlay, gp);
                // mapView.invalidate();
                // }
                // 支持双击删除一个节点，即删除一个点overlay对象
                if (pointOverlay.selectedFlag) {
                    long disttime = System.currentTimeMillis() - pointOverlay.previousTouchTime;
                    pointOverlay.previousTouchTime = System.currentTimeMillis();
                    if (disttime < 1200L) {
                        Log.d(LOG_TAG, resource.getMessage(DataCommon.EDITFEATUREACTION_DOUBLECLICK));
                        deletePointOverlay(pointOverlay, mapView);
                    }
                }
                // 触碰抬起时，取消点被选中状态
                pointOverlay.selectedFlag = false;
                // mapView.setUseScrollEvent(true);
                break;
            }
        }

        /**
         * <p>
         * 响应双击，删除当前的点overlay对象
         * </p>
         * @param overlay
         * @param mapView
         */
        private void deletePointOverlay(Overlay overlay, MapView mapView) {
            if (overlay instanceof PointOverlay) {
                PointOverlay po = (PointOverlay) overlay;
                Point2D data = po.getData();
                if (data == null) {
                    return;
                }
                List<Point2D> newPoint2DList = new ArrayList<Point2D>();
                // 是否是删除地物的首节点
                boolean isDeleteFirstPoint = false;
                // 头尾点是同一个对象，所以只判断size()-1个点的位置
                for (int i = 0; i < pointList.size() - 1; i++) {
                    Point2D pt = pointList.get(i);
                    if (pt.x == data.x && pt.y == data.y) {
                        if (i == 0) {
                            isDeleteFirstPoint = true;
                        }
                    } else {
                        newPoint2DList.add(pt);
                    }
                }
                // 添加地物的尾节点
                if (isDeleteFirstPoint) {
                    // 如果删除了首节点就没有必要加入跟首节点相同的尾节点，只要添加当前首节点到地物的尾部，实现闭合的地物面
                    newPoint2DList.add(newPoint2DList.get(0));
                } else {
                    // 如果不是删除了首节点就加入尾节点
                    newPoint2DList.add(pointList.get(pointList.size() - 1));
                }

                // 修改构成地物的点集合
                pointList.clear();
                for (int i = 0; i < newPoint2DList.size(); i++) {
                    pointList.add(newPoint2DList.get(i));
                }
                // pointList = newPoint2DList;
                // Log.d(TAG, "pointList:" + pointList.size());
                // todo使用point2DList重新构造点线面overlay，先mapView线移除所有overlay，在重新添加最后刷新mapView
                // mapView.getOverlays().clear();
                removeEditOverlays();
                initEditOverlays(newPoint2DList, mapView);
                mapView.invalidate();
            }
        }

        /**
         * <p>
         * 平移时实现点的拖拉，改变点的坐标
         * </p>
         * @param overlay
         * @param gp
         */
        private void updateOverlay(Overlay overlay, Point2D gp) {
            if (overlay instanceof PointOverlay) {
                PointOverlay po = (PointOverlay) overlay;
                po.getData().x = gp.x;
                po.getData().y = gp.y;
            }
        }

    }

    /**
     * <p>
     * 处理 线 Overlay对象的触碰事件监听器
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    private class LineOverlayTouchEventListener implements Overlay.OverlayTouchEventListener {

        private List<Point2D> point2DList = new ArrayList<Point2D>();

        public LineOverlayTouchEventListener(List<Point2D> point2dList) {
            super();
            point2DList = point2dList;
        }

        @Override
        public void onTouch(MotionEvent event, MapView mapView) {
            onTouch(event, null, mapView);
        }

        @Override
        public void onTouch(MotionEvent event, Overlay overlay, MapView mapView) {
            if (!(overlay instanceof LineOverlay)) {
                return;
            }
            // mapView.setUseScrollEvent(false);
            Point2D gp = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 点击线overlay并按下点不靠近地物点集合中任何一个点，则地物增加一个端点，重新构造处于编辑状态的高亮地物
                boolean isadd = true;
                Point touchPoint = new Point((int) event.getX(), (int) event.getY());
                for (int i = 0; i < point2DList.size(); i++) {
                    Point2D point2D = point2DList.get(i);
                    Point pt = mapView.getProjection().toPixels(point2D, null);
                    if (Util.distance(touchPoint, pt) < 6) {
                        isadd = false;
                        break;
                    }
                }
                if (isadd) {
                    updateOverlay(overlay, gp, mapView);
                    mapView.invalidate();
                }
                break;
            //case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                // todo 更新点overlay的点坐标
                // touchPoint2D.x = gp.x;
                // touchPoint2D.y = gp.y;
                // mapView.invalidate();
               // break;
            //case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                // mapView.setUseScrollEvent(true);
              //  break;
            }
        }

        /**
         * <p>
         * 地物增加一个端点，并重新 构造处于编辑状态的高亮地物
         * </p>
         * @param overlay
         * @param gp
         * @param mapView
         */
        private void updateOverlay(Overlay overlay, Point2D gp, MapView mapView) {
            if (overlay instanceof LineOverlay) {
                LineOverlay lo = (LineOverlay) overlay;
                List<Point2D> datas = lo.getData();
                if (datas == null || datas.size() < 1) {
                    return;
                }
                Point2D closestGP = Util.closestPoint(gp, datas);
                List<Point2D> newPoint2DList = new ArrayList<Point2D>();
                // 保证只添加一次
                boolean isAdd = false;
                // 头尾点是同一个对象，所以只判断size()-1个点的位置，不然可能会多加一次closestGP点对象
                for (int i = 0; i < point2DList.size() - 1; i++) {
                    Point2D pt = point2DList.get(i);
                    if (pt.x == datas.get(0).x && pt.y == datas.get(0).y && !isAdd) {
                        newPoint2DList.add(pt);
                        newPoint2DList.add(closestGP);
                        isAdd = true;
                    } else {
                        newPoint2DList.add(pt);
                    }
                }
                newPoint2DList.add(point2DList.get(point2DList.size() - 1));
                // 修改构成地物的点集合
                point2DList.clear();
                for (int i = 0; i < newPoint2DList.size(); i++) {
                    point2DList.add(newPoint2DList.get(i));
                }
                // Log.d(TAG, "pointList:" + point2DList.size());
                // todo使用point2DList重新构造点线面overlay，先mapView线移除所有overlay，在重新添加最后刷新mapView
                // mapView.getOverlays().clear();
                removeEditOverlays();
                initEditOverlays(newPoint2DList, mapView);
                mapView.invalidate();
            }
        }
    }

    /**
     * <p>
     * 处理 面 Overlay对象的触碰事件监听器
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    private class PolygonOverlayTouchEventListener implements Overlay.OverlayTouchEventListener {
        // 记录按下事件点
        private double mTouchX;
        private double mTouchY;

        @Override
        public void onTouch(MotionEvent event, MapView mapView) {
            onTouch(event, null, mapView);
        }

        @Override
        public void onTouch(MotionEvent event, Overlay overlay, MapView mapView) {
            if (!(overlay instanceof PolygonOverlay)) {
                return;
            }
            // mapView.setUseScrollEvent(false);
            Point2D gp = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 记录按下事件点
                mTouchX = gp.x;
                mTouchY = gp.y;
                break;
            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                // 实现地物面overlay的平移
                updateOverlay(overlay, gp);
                mapView.invalidate();
                break;
            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                // updateOverlay(overlay, gp);
                // mapView.invalidate();
                // mapView.setUseScrollEvent(true);
                break;
            }
        }

        /**
         * <p>
         * 实现地物面overlay的平移
         * </p>
         * @param overlay
         * @param gp
         */
        private void updateOverlay(Overlay overlay, Point2D gp) {
            double distX = gp.x - mTouchX;
            double distY = gp.y - mTouchY;
            if (overlay instanceof PolygonOverlay) {
                PolygonOverlay po = (PolygonOverlay) overlay;
                List<Point2D> datas = po.getData();
                // 头尾点是同一个对象，所以只修改size()-1个点的位置，不然会使第一个点对象被修改两次
                for (int i = 0; i < datas.size() - 1; i++) {
                    Point2D pt = datas.get(i);
                    pt.x += distX;
                    pt.y += distY;
                }
            }
            mTouchX = gp.x;
            mTouchY = gp.y;
        }
    }
}
