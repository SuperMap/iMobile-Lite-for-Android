package com.supermap.imobilelite.maps;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

class TouchEventHandler implements MapTouchEventHandler {
    private static final String LOG_TAG = "com.supermap.android.maps.toucheventhandler";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private MapView mapView;
    private SimpleMultiTouchDetector multiTouchDetector;
    private GestureDetector gestureDetector;
    private boolean firedMoveStart = false;
    private boolean firedTouch = false;

    static final int ACTION = 0;

    MultiTouchDoubleTapHandler multiTouchDoubleTapHandler = null;

    public TouchEventHandler(MapView mapView) {
        this.mapView = mapView;
        this.multiTouchDetector = new SimpleMultiTouchDetector();

        this.gestureDetector = new GestureDetector(new GestureListener());
        this.multiTouchDoubleTapHandler = new MultiTouchDoubleTapHandler(mapView);

        disableMultitouchRotation();
    }

    public boolean handleTouchEvent(MotionEvent event) {
        if (this.multiTouchDetector.onTouchEvent(event)) {
            return true;
        }
        int action = event.getAction();
        int actionCode = action & this.multiTouchDetector.actionMask;

        if (actionCode == 1) {
            if (this.firedMoveStart) {
                mapView.getEventDispatcher().sendEmptyMessage(23);
                this.firedMoveStart = false;
            }
            this.firedTouch = false;
        }

        if (this.gestureDetector != null) {
            return this.gestureDetector.onTouchEvent(event);
        }
        return false;
    }

    public void destroy() {
    }

    protected void enableMultitouchRotation() {
        this.multiTouchDetector.removeHandlers();
        this.multiTouchDetector.registerHandler(this.multiTouchDoubleTapHandler);
        this.multiTouchDetector.registerHandler(new RotationPinchHandler(this.mapView));
    }

    private void disableMultitouchRotation() {
        this.multiTouchDetector.removeHandlers();
        this.multiTouchDetector.registerHandler(this.multiTouchDoubleTapHandler);
        this.multiTouchDetector.registerHandler(new PinchHandler(this.mapView));
    }

    protected void fireZoomEndEvent() {
        mapView.getEventDispatcher().sendEmptyMessage(12);
    }

    protected void fireZoomStartEvent() {
        mapView.getEventDispatcher().sendEmptyMessage(11);
    }

    private class SimpleMultiTouchDetector {
        static final int MAXIMUM_SUPPORTED_POINTER_COUNT = 2;
        boolean inProgress = false;
        boolean isMoveAfterPinch = false;
        float scaleFactor;
        boolean multiTouchSupported = true;

        private int actionPointerDown = 0;
        private int actionPointerUp = 0;
        protected int actionMask = 65535;

        ArrayList<MultiTouchHandler> handlers = new ArrayList();

        public SimpleMultiTouchDetector() {
            this.multiTouchSupported = checkMultiTouchSupport();
            if (this.multiTouchSupported) {
                try {
                    this.actionPointerDown = MotionEvent.class.getField("ACTION_POINTER_DOWN").getInt(null);

                    this.actionPointerUp = MotionEvent.class.getField("ACTION_POINTER_UP").getInt(null);
                    this.actionMask = MotionEvent.class.getField("ACTION_MASK").getInt(null);
                } catch (Exception localException) {
                }
            }
        }

        private void registerHandler(MultiTouchHandler mth) {
            this.handlers.add(mth);
        }

        private void removeHandler(MultiTouchHandler mth) {
            this.handlers.remove(mth);
        }

        public void removeHandlers() {
            this.handlers.clear();
        }

        public boolean isInProgress() {
            return (this.inProgress) || (this.isMoveAfterPinch);
        }

        private boolean checkMethodExists(Class<MotionEvent> clss, String method, Class[] parameterTypes) {
            try {
                clss.getMethod(method, parameterTypes);
                return true;
            } catch (Exception e) {
            }
            return false;
        }

        private boolean checkMultiTouchSupport() {
            boolean checkGetPointerCountMethod = checkMethodExists(MotionEvent.class, "getPointerCount", null);

            boolean checkGetXIndexMethod = checkMethodExists(MotionEvent.class, "getX", new Class[] { Integer.TYPE });

            boolean checkGetYIndexMethod = checkMethodExists(MotionEvent.class, "getY", new Class[] { Integer.TYPE });

            return (checkGetPointerCountMethod) && (checkGetXIndexMethod) && (checkGetYIndexMethod);
        }

        public Object invoke(Object o, String methodName, Class[] parameterTypes, Object[] args) throws Exception {
            Method method = o.getClass().getMethod(methodName, parameterTypes);
            return method.invoke(o, args);
        }

        private float getX(MotionEvent event, int i) throws Exception {
            return ((Float) invoke(event, "getX", new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(i) })).floatValue();
        }

        private float getY(MotionEvent event, int i) throws Exception {
            return ((Float) invoke(event, "getY", new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(i) })).floatValue();
        }

        private int getPointerCount(MotionEvent event) throws Exception {
            return ((Integer) invoke(event, "getPointerCount", null, null)).intValue();
        }

        public int getActionPointerDown() {
            return this.actionPointerDown;
        }

        public int getActionPointerUp() {
            return this.actionPointerUp;
        }

        public int getActionMask() {
            return this.actionMask;
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (!this.multiTouchSupported) {
                return false;
            }
            try {
                int action = event.getAction();
                int actionCode = action & this.actionMask;

                if (actionCode == 0) {
                    this.isMoveAfterPinch = false;
                }

                if (getPointerCount(event) <= 1) {
                    return false;
                }
                this.inProgress = true;
                this.isMoveAfterPinch = true;
                PointF[] points = new PointF[getPointerCount(event)];
                for (int i = 0; i < getPointerCount(event); i++) {
                    points[i] = new PointF();
                    points[i].x = getX(event, i);
                    points[i].y = getY(event, i);
                }
                if ((actionCode == this.actionPointerDown) || (actionCode == 2)) {
                    for (MultiTouchHandler mth : this.handlers) {
                        if (mth.onTouch(event, this, points)) {
                            break;
                        }

                    }

                } else if (actionCode == this.actionPointerUp) {
                    for (MultiTouchHandler mth : this.handlers) {
                        mth.onTouchEnd(event, this, points);
                    }
                    this.inProgress = false;
                }

                return true;
            } catch (Exception e) {
                Log.d(LOG_TAG, resource.getMessage(MapCommon.TOUCHEVENTHANDLER_EXCEPTION, e.getMessage()));
            }
            return false;
        }
    }

    private class PinchHandler extends AbstractMultiTouchHandler {
        ArrayList<PointF> previousTouchPoints = new ArrayList<PointF>();
        private MapView mapView;
        // PointF center = null;
        // Point2D centerPoint2D = null;
        private float scaleFactor = 1.0F;
        private float lastSuccessScaleFactor = 1.0F;
        private boolean zoomStarted = false;

        public PinchHandler(MapView mapView) {
            super(mapView);
            this.mapView = mapView;
        }

        public boolean onTouchEnd(MotionEvent me, SimpleMultiTouchDetector smtd, PointF[] points) {
            this.previousTouchPoints.clear();
            // 结束一次多点触碰缩放
            mapView.isMultiTouchScale = false;
            mapView.realScale = 1.0F;
            // 支持固定比例尺缩放，多点缩放结束后缩放到固定级别，而不是任意比例尺缩放的可视效果
            if (mapView.fixedLevelsEnabled && mapView.currentScale != 1.0f) {
                // 支持固定比例尺缩放，缩放过程中已经找到最接近的比例尺了，此处只要把可视化的缩放因子还原成1.0f即可。
                mapView.currentScale = 1.0f;
                // 每次多点触碰的缩放因子数值不能累计，每一次停止后都要重头开始
                this.scaleFactor = 1.0F;
                this.lastSuccessScaleFactor = 1.0F;
                // 刷新地图
                mapView.invalidate();
            }
            // if (this.zoomStarted) {
            // int startZoom = this.mapView.getZoomLevel();
            // // int endZoom = (int) Math.round(startZoom + Util.log2(this.scaleFactor));
            // int endZoom = startZoom;
            // if (this.scaleFactor > 1.0f) {
            // endZoom = (int) Math.round(startZoom + Util.log2(this.scaleFactor) + 0.49999999);
            // } else if (this.scaleFactor < 1.0f) {
            // endZoom = (int) Math.round(startZoom + Util.log2(this.scaleFactor) - 0.49999999);
            // }
            // // Log.d(LOG_TAG, "PinchHandler onTouchEnd!");
            // this.mapView.getController().getMapAnimator()
            // .animateZoomScaler(startZoom, endZoom, this.scaleFactor, new Point((int) this.center.x, (int) this.center.y), true);

            // this.zoomStarted = false;
            // }
            // this.scaleFactor = 1.0F;
            // this.lastSuccessScaleFactor = 1.0F;
            // 因为双击和缩放按钮的缩放会导致scaling为false，这样就无法触发setScale缩放过程的效果，所以多点触碰结束置zoomStarted为false
            this.zoomStarted = false;
            return true;
        }

        public boolean onTouch(MotionEvent me, SimpleMultiTouchDetector smtd, PointF[] points) {
            boolean handled = true;
            if (this.previousTouchPoints.size() != 0) {
                int actionCode = me.getAction() & smtd.getActionMask();
                // Log.d(LOG_TAG, "PinchHandler onTouch actionCode:"+actionCode+"me.getAction():"+me.getAction()+"smtd.getActionMask():"+smtd.getActionMask());
                if (actionCode == 2) {
                    float ed3 = Util.distance((PointF) this.previousTouchPoints.get(0), (PointF) this.previousTouchPoints.get(1));
                    float ed4 = Util.distance(points[0], points[1]);
                    // 支持多点平移，先发生平移再考虑缩放，以第二个点为参考点，先考虑该点的平移，再以平移后的点作为参考点进行缩放
                    // 考虑所有触碰点中发生最大位移的点为参考点
                    float detaX = points[1].x - this.previousTouchPoints.get(1).x;
                    float detaY = points[1].y - this.previousTouchPoints.get(1).y;
                    Projection p = this.mapView.getProjection();
                    int x = this.mapView.focalPoint.x - (int) detaX;
                    int y = this.mapView.focalPoint.y - (int) detaY;
                    Point2D lastCenterGeoPoint = mapView.centerGeoPoint;
                    this.mapView.centerGeoPoint = p.fromPixels(x, y);
                    Point2D focusPoint2D = p.fromPixels((int) points[1].x, (int) points[1].y);
                    boolean isMapFresh = true;
                    // 多点缩放产生的距离误差允许为8个像素，超过8个像素代表发生了缩放
                    if (Math.abs(ed4 - ed3) >= 8.0f) {
                        float factor = ed4 / ed3;
                        // 其实scaleFactor应该考虑mapview的currentScale值，更合理的是scaleFactor = mapView.currentScale * factor;
                        this.scaleFactor *= factor;
                        // this.scaleFactor = mapView.currentScale * factor;
                        mapView.realScale = factor;// 记录真实缩放比例
                        int startLevel = this.mapView.getZoomLevel();
                        int endLevel = (int) Math.round(startLevel + Util.log2(this.scaleFactor));
                        if (this.mapView.validateZoomLevel(endLevel)) {
                            if (!this.zoomStarted) {
                                TouchEventHandler.this.fireZoomStartEvent();
                                this.zoomStarted = true;
                            }
                            // 因为双击和缩放按钮的缩放会导致scaling为false，这样就无法触发setScale缩放过程的效果
                            // if (!this.mapView.scaling) {
                            // TouchEventHandler.this.fireZoomStartEvent();
                            // }
                            // 触发多点缩放
                            mapView.isMultiTouchScale = true;
                            // 多点缩放地图，设置zoomInChanged的值
                            if (factor > 1) {
                                mapView.zoomInChanged = true;
                            } else {
                                mapView.zoomInChanged = false;
                            }
                            if (startLevel != endLevel) {
                                // 设置固定比例尺时
                                if (mapView.getResolutions() != null && mapView.getResolutions().length > 0) {
                                    // 当前真正的分辨率
                                    double curResolution = mapView.getRealResolution();
                                    // 缩放后的分辨率
                                    double zoomR = curResolution / scaleFactor;
                                    // 用当前真正的分辨率与缩放后的分辨率的差初始化最小的分辨率差，遍历找到最靠近缩放后的分辨率的固定分辨率且不是当前真正的分辨率
                                    double minDetaR = Math.abs(curResolution - zoomR);
                                    for (int i = 0; i < mapView.getResolutions().length; i++) {
                                        if (Math.abs(mapView.getResolutions()[i] - zoomR) < minDetaR) {
                                            minDetaR = Math.abs(mapView.getResolutions()[i] - zoomR);
                                            // 找到最靠近的分辨率的下标作为最终的缩放层级
                                            endLevel = i;
                                        }
                                    }
                                    if (minDetaR < Math.abs(curResolution - zoomR)) {
                                        // 找到最靠近缩放后的分辨率的固定分辨率且不是当前真正的分辨率，修正缩放的程度scaleFactor值
                                        this.scaleFactor = (float) (mapView.getResolutions()[endLevel] / zoomR);
                                    } else {
                                        // 没有找到，说明还是当前比例尺的拉伸和压缩来实现缩放 ，无需修正缩放的程度scaleFactor值
                                        endLevel = startLevel;
                                    }
                                } else {
                                    // 没有设置固定比例尺时，直接修正缩放的程度scaleFactor值
                                    this.scaleFactor = (float) (this.scaleFactor / Math.pow(2, (endLevel - startLevel)));
                                }
                                this.mapView.setZoomLevel(endLevel);                               
                            }
                            // 修正屏幕中心点对应的piont2D，即修正mapView.centerGeoPoint
                            // if (center != null && centerPoint2D != null) {
                            // mapView.centerGeoPoint = this.mapView.getProjection().getFocusPiont2D(new Point((int) center.x, (int) center.y), centerPoint2D,
                            // scaleFactor);
                            // }
                            mapView.centerGeoPoint = p.getFocusPiont2D(new Point((int) points[1].x, (int) points[1].y), focusPoint2D, scaleFactor);
                            this.mapView.setScale(this.scaleFactor, this.scaleFactor, points[1].x, points[1].y);
                            // this.mapView.setScale(this.scaleFactor, this.scaleFactor, this.center.x, this.center.y);
                            // 除了setZoomLevel，setScale接口也触发了缩放，所以在此做一次通知，而且保证此刻的地图中心点也是最新的
                            mapView.getEventDispatcher().sendEmptyMessage(12);// 通知监听器MapViewEventListener的zoomEnd接口生效
                            this.lastSuccessScaleFactor = this.scaleFactor;
                        } else {// 没有发生平移也没有发生缩放，还原参数值
                            this.scaleFactor = this.lastSuccessScaleFactor;
                            mapView.realScale = 1.0f;
                            mapView.centerGeoPoint = lastCenterGeoPoint;// 中心点保持不变，还原
                            isMapFresh = false;// 没有发生平移也没有发生缩放所以地图不做刷新
                        }
                    }
                    if (isMapFresh) {
                        this.mapView.invalidate();
                        // 更新比例尺控件
                        mapView.updateScaleBar();
                    }
                    copy(this.previousTouchPoints, points);
                    // 每次更新所有触碰点的中心点及对应的地理坐标
                    // this.center = getCenterPoint(this.previousTouchPoints);
                    // centerPoint2D = this.mapView.getProjection().fromPixels((int) this.center.x, (int) this.center.y);
                }
            } else {
                copy(this.previousTouchPoints, points);
                // 考虑this.scaleFactor = this.mapView.currentScale;
                // this.scaleFactor *= this.mapView.currentScale;
                this.lastSuccessScaleFactor = this.scaleFactor;
                // this.center = getCenterPoint(this.previousTouchPoints);
                // centerPoint2D = this.mapView.getProjection().fromPixels((int) this.center.x, (int) this.center.y);
            }

            return handled;
        }

        public boolean reset() {
            this.previousTouchPoints.clear();
            this.scaleFactor = 1.0F;
            return false;
        }
    }

    private class RotationPinchHandler extends AbstractMultiTouchHandler {
        ArrayList<PointF> previousTouchPoints = new ArrayList();
        private MapView mapView;
        private PointF center = null;
        private float exAngle;
        private float scaleFactor = 1.0F;
        private float exScaleFactor = 1.0F;
        private boolean zoomStarted = false;
        private boolean rotationStarted = false;
        private static final float MINIMUM_SCALE_FACTOR = 0.03F;
        private static final float MINIMUM_ANGLE = 0.5F;

        public RotationPinchHandler(MapView mapView) {
            super(mapView);
            this.mapView = mapView;
        }

        public boolean onTouchEnd(MotionEvent me, SimpleMultiTouchDetector smtd, PointF[] points) {
            this.previousTouchPoints.clear();
            if (this.zoomStarted) {
                int startZoom = this.mapView.getZoomLevel();
                int endZoom = (int) Math.round(startZoom + Util.log2(this.scaleFactor));

                this.mapView.getController().getMapAnimator()
                        .animateZoomScaler(startZoom, endZoom, this.scaleFactor, new Point((int) this.center.x, (int) this.center.y), true);
            }

            if (this.rotationStarted) {
                mapView.getEventDispatcher().sendEmptyMessage(33);
            }

            this.rotationStarted = false;
            this.zoomStarted = false;
            this.scaleFactor = 1.0F;
            this.exScaleFactor = 1.0F;
            return true;
        }

        public float angle(PointF center, PointF point) {
            float radius = Util.hypotenuse(point.y - center.y, point.x - center.x);

            PointF pointNorth = new PointF(center.x, center.y - radius);

            return angle(center, pointNorth, point);
        }

        public float angle(PointF center, PointF p1, PointF p2) {
            float x1 = p1.x - center.x;
            float x2 = p2.x - center.x;
            float y1 = center.y - p1.y;
            float y2 = center.y - p2.y;

            double angle = 2.0D * Math.atan2(y1 - y2, x1 - x2);
            return (float) Math.toDegrees(angle);
        }

        public boolean onTouch(MotionEvent me, SimpleMultiTouchDetector smtd, PointF[] points) {
            boolean handled = false;
            if (this.previousTouchPoints.size() != 0) {
                float maxAngle = 0.0F;
                float maxDistance = 0.0F;
                for (int i = 0; i < this.previousTouchPoints.size(); i++) {
                    float angle1 = angle(this.center, (PointF) this.previousTouchPoints.get(i));
                    float angle2 = angle(this.center, points[i]);
                    float angle = angle1 - angle2;
                    float distance1 = Util.distance((PointF) this.previousTouchPoints.get(i), this.center);
                    float distance2 = Util.distance(points[1], this.center);
                    float distance = distance1 - distance2;
                    if (Math.abs(maxAngle) < Math.abs(angle)) {
                        maxAngle = angle;
                    }
                    if (maxDistance < Math.abs(distance)) {
                        maxDistance = Math.abs(distance);
                    }
                }

                float ed3 = Util.distance((PointF) this.previousTouchPoints.get(0), (PointF) this.previousTouchPoints.get(1));

                float ed4 = Util.distance(points[0], points[1]);

                this.scaleFactor *= ed4 / ed3;
                if (Math.abs(this.scaleFactor - this.exScaleFactor) / 0.03F > Math.abs(maxAngle - this.exAngle) / 0.5F) {
                    handled = true;
                    if (!this.zoomStarted) {
                        this.zoomStarted = true;
                        TouchEventHandler.this.fireZoomStartEvent();
                    }

                    this.mapView.setScale(this.scaleFactor, this.scaleFactor, this.center.x, this.center.y);

                    this.mapView.invalidate();
                } else if (Math.abs(maxAngle - this.exAngle) >= 0.3F) {
                    handled = true;
                    if (!this.rotationStarted) {
                        mapView.getEventDispatcher().sendEmptyMessage(31);
                        this.rotationStarted = true;
                    }
                    float finalAngle = (360.0F + (this.mapView.getMapRotation() + maxAngle)) % 360.0F;

                    this.mapView.setMapRotation(finalAngle);
                    this.mapView.invalidate();
                }
                if (handled) {
                    this.exScaleFactor = this.scaleFactor;
                    this.exAngle = maxAngle;
                    copy(this.previousTouchPoints, points);
                }
            } else {
                copy(this.previousTouchPoints, points);
                this.scaleFactor *= this.mapView.currentScale;
                this.center = getCenterPoint(this.previousTouchPoints);
                handled = true;
            }

            return handled;
        }

        public boolean reset() {
            return false;
        }
    }

    private class MultiTouchDoubleTapHandler extends AbstractMultiTouchHandler {
        private MapView mapView;
        private boolean doZoom = false;

        private long previousTouchTime = -1L;

        public MultiTouchDoubleTapHandler(MapView mapView) {
            super(mapView);
            this.mapView = mapView;
        }

        public boolean onTouch(MotionEvent me, SimpleMultiTouchDetector smtd, PointF[] points) {
            int actionCode = me.getAction() & smtd.getActionMask();
            // Log.d(LOG_TAG, "MultiTouchDoubleTapHandler onTouch actionCode:"+actionCode+"me.getAction():"+me.getAction()+"smtd.getActionMask():"+smtd.getActionMask());
            if (actionCode == 2) {
                this.previousTouchTime = -1L;
                return false;
            }
            if (actionCode == smtd.getActionPointerDown()) {
                if (this.previousTouchTime == -1L) {
                    this.previousTouchTime = System.currentTimeMillis();
                    return false;
                }
                if ((System.currentTimeMillis() - this.previousTouchTime < 1000L) && (points.length == 2)) {
                    ArrayList touchPoints = new ArrayList();
                    copy(touchPoints, points);
                    PointF point = getCenterPoint(touchPoints);
                    this.mapView.getController().zoomOutFixing((int) point.x, (int) point.y);
//                    Log.e(LOG_TAG, "zoomOutFixing执行了!");
                }
                this.previousTouchTime = System.currentTimeMillis();
            }

            return false;
        }

        public boolean isInProgress() {
//            Log.e(LOG_TAG, "isInProgress:"+previousTouchTime);
            return System.currentTimeMillis() - this.previousTouchTime < 1000L;
        }

        public boolean onTouchEnd(MotionEvent me, SimpleMultiTouchDetector smtd, PointF[] points) {
            return false;
        }

        public boolean reset() {
            return false;
        }
    }

    private abstract class AbstractMultiTouchHandler implements MultiTouchHandler {
        public AbstractMultiTouchHandler(MapView mapView) {
        }

        protected void copy(ArrayList<PointF> touchPoints, PointF[] points) {
            PointF[] pts = points.clone();
            for (int i = touchPoints.size(); i < pts.length; i++) {
                touchPoints.add(new PointF());
            }
            for (int i = 0; i < pts.length; i++) {
                ((PointF) touchPoints.get(i)).x = pts[i].x;
                ((PointF) touchPoints.get(i)).y = pts[i].y;
            }
            for (int i = touchPoints.size() - 1; i >= pts.length; i--)
                touchPoints.remove(i);
        }

        public PointF getCenterPoint(ArrayList<PointF> points) {
            float minX = 0.0F;
            float maxX = 0.0F;
            float minY = 0.0F;
            float maxY = 0.0F;
            for (PointF point : points) {
                if ((minX > point.x) || (minX == 0.0F)) {
                    minX = point.x;
                }
                if ((maxX < point.x) || (maxX == 0.0F)) {
                    maxX = point.x;
                }
                if ((minY > point.y) || (minY == 0.0F)) {
                    minY = point.y;
                }
                if ((maxY < point.y) || (maxY == 0.0F)) {
                    maxY = point.y;
                }
            }
            float midX = (maxX + minX) / 2.0F;
            float midY = (maxY + minY) / 2.0F;
            return new PointF(midX, midY);
        }
    }

    private static abstract interface MultiTouchHandler {
        public abstract boolean reset();

        public abstract boolean onTouch(MotionEvent paramMotionEvent, SimpleMultiTouchDetector paramSimpleMultiTouchDetector,
                                        PointF[] paramArrayOfPointF);

        public abstract boolean onTouchEnd(MotionEvent paramMotionEvent, SimpleMultiTouchDetector paramSimpleMultiTouchDetector,
                                           PointF[] paramArrayOfPointF);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private long previousTime = -1L;

        private GestureListener() {
        }

        public void onLongPress(MotionEvent me) {
            if (TouchEventHandler.this.multiTouchDetector.isInProgress())
                return;

            Message message = Message.obtain();
            message.what = 4;
            message.getData().putParcelable("data", MotionEvent.obtain(me));
            mapView.getEventDispatcher().sendMessage(message);
            super.onLongPress(me);
        }

        public boolean onDown(MotionEvent event) {
            TouchEventHandler.this.mapView.getController().stopPanning();
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (previousTime == -1 || (System.currentTimeMillis() - this.previousTime) > 300) {
                this.previousTime = System.currentTimeMillis();
                if (!TouchEventHandler.this.multiTouchDoubleTapHandler.isInProgress() && mapView.isUseDoubleTapEvent()) {
                    TouchEventHandler.this.mapView.getController().zoomInFixing((int) e.getX(), (int) e.getY());
                    // 双击放大地图时不触发缩放过程动画的实现代码，注释掉先预留着
                    // Projection p = TouchEventHandler.this.mapView.getProjection();
                    // int x = (int) e.getX();
                    // int y = (int) e.getY();
                    // TouchEventHandler.this.mapView.centerGeoPoint = p.fromPixels(x, y);
                    // TouchEventHandler.this.mapView.zoomIn();
                    return true;
                }
            }
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            boolean result = TouchEventHandler.this.multiTouchDetector.onTouchEvent(e2);

            if (TouchEventHandler.this.multiTouchDetector.isInProgress()) {
                return true;
            }
            if (mapView.isUseScrollEvent()) {
                Projection p = TouchEventHandler.this.mapView.getProjection();
                int x = TouchEventHandler.this.mapView.focalPoint.x + (int) distanceX;
                int y = TouchEventHandler.this.mapView.focalPoint.y + (int) distanceY;

                TouchEventHandler.this.mapView.centerGeoPoint = p.fromPixels(x, y);
                // Log.d(LOG_TAG, resource.getMessage(MapCommon.TOUCHEVENTHANDLER_CENTERGEOPOINT, TouchEventHandler.this.mapView.centerGeoPoint.toString()));

                // double geoScreenWidth = mapView.getResolution() * mapView.getMapWidth()/2;
                // double geoScreenHeight = mapView.getResolution() * mapView.getMapHeight()/2;
                // LayerView baseLayerView = mapView.getBaseLayer();
                // if (baseLayerView != null && baseLayerView.isGCSLayer()) {
                // double radius = baseLayerView.getCRS().datumAxis > 1d ? baseLayerView.getCRS().datumAxis : 6378137d;
                // geoScreenWidth = geoScreenWidth * 180 / radius / Math.PI;
                // geoScreenHeight = geoScreenHeight * 180 / radius / Math.PI;
                // }
                // BoundingBox boundingBox = mapView.getIndexBounds();
                // if (boundingBox != null && Math.abs((mapView.centerGeoPoint.getY() - boundingBox.getBottom())) > geoScreenHeight
                // && Math.abs((mapView.centerGeoPoint.getY() - boundingBox.getTop())) > geoScreenHeight
                // && Math.abs((mapView.centerGeoPoint.getX() - boundingBox.getLeft())) > geoScreenWidth
                // && Math.abs((mapView.centerGeoPoint.getX() - boundingBox.getRight())) > geoScreenWidth) {
                // mapView.zoomInChanged = true;
                // } else {
                mapView.zoomInChanged = false;// 不是放大地图，而是平移地图，置为false
                // }

                TouchEventHandler.this.mapView.invalidate();
                // 考虑纬度变化，所以平移的时候也要更新比例尺控件
                TouchEventHandler.this.mapView.updateScaleBar();
                if (!TouchEventHandler.this.firedMoveStart) {
                    mapView.getEventDispatcher().sendEmptyMessage(21);
                    // TouchEventHandler.access$302(TouchEventHandler.this, true);
                    firedTouch = true; // added by zhouxu

                } else {
                    mapView.getEventDispatcher().sendEmptyMessage(22);
                }
            }
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (TouchEventHandler.this.multiTouchDetector.isInProgress())
                return false;
            if (mapView.isUseScrollEvent() && e1 != null) {
                Point point = new Point((int) e1.getX(), (int) e1.getY());
                TouchEventHandler.this.mapView.getController().getMapAnimator().animateFlick(point, velocityX, velocityY);
            }
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            Projection p = TouchEventHandler.this.mapView.getProjection();
            if (p == null) {
                return true;
            }
            Point2D gp = p.fromPixels((int) e.getX(), (int) e.getY());
            if (!TouchEventHandler.this.mapView.onTap(gp)) {
                mapView.getEventDispatcher().sendEmptyMessage(3);
                return false;
            }
            return true;
        }
    }
}