package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * <p>
 * ItemizedOverlay是Overlay的一个基类，ItemizedOverlay包含了一个OverlayItem列表。
 * </p>
 * <p>
 * 用于绘制、创建平移边界、为每个点绘制标记点，和维护一个焦点点中的item，同时负责把屏幕点击匹配到item上去，分发焦点改变事件给备选的监听器。 添加覆盖物的一般流程：<br>
 * 1.用OverlayItem准备Overlay数据信息；<br>
 * 2.继承ItemizedOverlay重写createItem()和size()方法；<br>
 * 3.调用Mapview.getOverlays().add()添加overlay到mapview中；<br>
 * 4.调用Mapview.refresh()使overlay生效。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 * @param <Item>
 */
public abstract class ItemizedOverlay<Item extends OverlayItem> extends Overlay implements Overlay.Snappable {
    public boolean center=false;
    private static final String LOG_TAG = "com.supermap.android.maps.itemizedoverlay";
    private Drawable defaultMarker;
    private int defaultAlignment = 33;

    ArrayList<OverlayItem> items = new ArrayList();
    private double latSpanE6;
    private double lngSpanE6;
    private BoundingBox boundingBox = new BoundingBox();

    private int focusedIndex = -1;
    private int selectedIndex = -1;
    private int pressedIndex = -1;

    private boolean drawFocusedItem = true;
    private GestureDetector gestureDetector;
    private GestureListener gestureListener;
    private int fingerSize = ViewConfiguration.getTouchSlop();
    private OnFocusChangeListener listener;
    private OnClickListener clickListener;
    private TrackballGestureDetector trackballGestureDetector = new TrackballGestureDetector();

    private Integer[] rankIndex = null;

    Rect bounds = new Rect();

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param marker 覆盖物标记。
     */
    public ItemizedOverlay(Drawable marker) {
        this.defaultMarker = marker;
        this.gestureListener = new GestureListener();
        this.gestureDetector = new GestureDetector(this.gestureListener);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param marker 覆盖物标记。
     * @param alignment
     */
    public ItemizedOverlay(Drawable marker, int alignment) {
        this(marker);
        this.defaultAlignment = alignment;
        Overlay.setAlignment(marker, this.defaultAlignment);
    }

    private Drawable getMarker(OverlayItem item) {
        Drawable marker = item.getMarker(item.getState());
        if (marker == null) {
            marker = this.defaultMarker;
            OverlayItem.setState(marker, item.getState());
        }
        return marker;
    }

    void drawItem(Canvas canvas, OverlayItem item, Point point, Drawable marker, boolean shadow) {
        this.bounds.set(marker.getBounds());
        this.bounds.offset(point.x, point.y);
        Rect bounds = canvas.getClipBounds();
        if (Rect.intersects(this.bounds, bounds)) {
            drawAt(canvas, marker, point.x, point.y, shadow);
        }
        // else {
        // Log.d(LOG_TAG, "被过滤的item:"+point.x+","+point.y);
        // }
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
        Projection projection = mapView.getProjection();

        Rect bounds = canvas.getClipBounds();
        BoundingBox bbox = getBoundingBox();

        BoundingBox screenBox = Util.createBoundingBoxFromRect(bounds, mapView);

        if (BoundingBox.intersect(bbox, screenBox)) {
            Point point = new Point();
            int size = this.items.size();

            for (int i = size - 1; i >= 0; i--) {
                if (this.focusedIndex == i) {
                    continue;
                }
                OverlayItem item = getItem(i);

                Drawable marker = getMarker(item);

                if (item.getAlignment() != 0) {
                    Overlay.setAlignment(marker, item.getAlignment());
                }

                projection.toPixels(item.getPoint(), point);

                if (center)
                point.y=point.y-marker.getBounds().centerY();

                drawItem(canvas, item, point, marker, shadow);
            }
            if ((this.drawFocusedItem) && (this.focusedIndex != -1)) {
                OverlayItem item = getItem(this.focusedIndex);
                projection.toPixels(item.getPoint(), point);
                drawItem(canvas, getItem(this.focusedIndex), point, getMarker(item), shadow);
            }
        }
    }

    /**
     * <p>
     * 子类通过该方法创建实体item。
     * </p>
     * @param paramInt
     * @return
     */
    protected abstract Item createItem(int paramInt);

    /**
     * <p>
     * 返回overlay的item的数目。
     * </p>
     * @return
     */
    public abstract int size();

    /**
     * <p>
     * 根据索引获取要绘制的item。
     * </p>
     * @param position 索引位置。
     * @return 要绘制的item。
     */
    public final OverlayItem getItem(int position) {
        if (getIndexToDraw(position) < items.size()) {
            return (OverlayItem) this.items.get(getIndexToDraw(position));
        } else {
            return null;
        }

    }

    /**
     * <p>
     * 清除所有覆盖物。
     * </p>
     */
    public void clear() {
        this.items.clear();
    }

    /**
     * <p>
     * 获取中心点。
     * </p>
     * @return 中心点。
     */
    public Point2D getCenter() {
        Point2D point = null;
        if (this.items.size() > 0) {
            point = getItem(0).getPoint();
        }
        return point;
    }

    /**
     * <p>
     * 获取边界框。
     * </p>
     * @return 边界框。
     */
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    /**
     * <p>
     * 销毁覆盖物。
     * </p>
     */
    public void destroy() {
        clear();
    }

    static Drawable boundCenter(Drawable balloon) {
        return Overlay.setAlignment(balloon, 3);
    }

    static Drawable boundCenterBottom(Drawable balloon) {
        return Overlay.setAlignment(balloon, 33);
    }

    /**
     * <p>
     * 设置绘制的焦点item对象。
     * </p>
     * @param drawFocusedItem 绘制的焦点item对象。
     */
    public void setDrawFocusedItem(boolean drawFocusedItem) {
        this.drawFocusedItem = drawFocusedItem;
    }

    /**
     * <p>
     * 获取上一个关注的焦点。
     * </p>
     * @return 上一个关注的焦点的索引。
     */
    public final int getLastFocusedIndex() {
        return this.focusedIndex;
    }

    /**
     * <p>
     * 获取关注的焦点。
     * </p>
     * @return 选中的覆盖物构件。
     */
    public OverlayItem getFocus() {
        if (this.focusedIndex < 0 || this.focusedIndex >= this.items.size()) {
            return null;
        }
        return this.items.get(this.focusedIndex);
    }

    double getLatSpanE6() {
        return this.latSpanE6;
    }

    double getLonSpanE6() {
        return this.lngSpanE6;
    }

    OverlayItem nextFocus(boolean forwards) {
        int tempFocusedIndex = this.focusedIndex;
        if (forwards)
            this.focusedIndex += 1;
        else {
            this.focusedIndex -= 1;
        }
        if ((this.focusedIndex < 0) || (this.focusedIndex >= this.items.size())) {
            this.focusedIndex = tempFocusedIndex;
            return null;
        }
        return (OverlayItem) this.items.get(this.focusedIndex);
    }

    int getIndexToDraw(int drawingOrder) {
        if (this.rankIndex == null)
            return drawingOrder;
        return this.rankIndex[drawingOrder].intValue();
    }

    final void populate() {
        int size = size();
        if (size <= 0) {
            return;
        }
        this.items.clear();
        this.items.ensureCapacity(size);
        double maxlat = -90.000000;
        double minlat = 90.000000;
        double maxlng = -180.000000;
        double minlng = 180.000000;

        if (size > 0) {
            OverlayItem firstItem = createItem(0);
            Point2D first = firstItem.getPoint();
            minlat = maxlat = first.getY();
            minlng = maxlng = first.getX();
            for (int i = 0; i < size; i++) {
                OverlayItem item = createItem(i);
                Point2D p = item.getPoint();
                if (p.getY() > maxlat) {
                    maxlat = p.getY();
                }
                if (p.getY() < minlat) {
                    minlat = p.getY();
                }
                if (p.getX() > maxlng) {
                    maxlng = p.getX();
                }
                if (p.getX() < minlng) {
                    minlng = p.getX();
                }

                item.getMarker(0);
                item.setState(0);
                this.items.add(item);
            }
        }

        this.boundingBox.leftTop = new Point2D(minlng, maxlat);
        this.boundingBox.rightBottom = new Point2D(maxlng, minlat);

        this.latSpanE6 = (maxlat - minlat);
        this.lngSpanE6 = (maxlng - minlng);

        this.focusedIndex = -1;
        this.selectedIndex = -1;
        this.pressedIndex = -1;

        this.rankIndex = new Integer[size];
        for (int i = 0; i < size; i++) {
            this.rankIndex[i] = Integer.valueOf(i);
        }
        Arrays.sort(this.rankIndex, new Comparator<Integer>() {
            public int compare(Integer index1, Integer index2) {
                return Double.valueOf(((OverlayItem) ItemizedOverlay.this.items.get(index1.intValue())).getPoint().getY()).compareTo(
                        Double.valueOf(((OverlayItem) ItemizedOverlay.this.items.get(index2.intValue())).getPoint().getY()));
            }
        });
    }

    private void focus(int newIndex) {
        int tempFocusIndex = this.focusedIndex;
        this.focusedIndex = changeState(this.focusedIndex, newIndex, 4);
        if ((this.listener != null) && (tempFocusIndex != this.focusedIndex) && focusedIndex < items.size())
            this.listener.onFocusChanged(this, (OverlayItem) this.items.get(this.focusedIndex));
    }

    private void click(int newIndex) {
        if (this.clickListener != null && (newIndex > -1) && newIndex < this.items.size())
            this.clickListener.onClicked(this, (OverlayItem) this.items.get(newIndex));
    }

    private void select(int newIndex) {
        this.selectedIndex = changeState(this.selectedIndex, newIndex, 1);
    }

    private void press(int newIndex) {
        this.pressedIndex = changeState(this.pressedIndex, newIndex, 2);
    }

    private int changeState(int currentIndex, int newIndex, int stateBitset) {
        if (currentIndex != newIndex) {
            if ((newIndex > -1) && (newIndex < this.items.size())) {
                if ((currentIndex > -1) && (currentIndex < this.items.size())) {
                    OverlayItem currentFocusItem = getItem(currentIndex);
                    currentFocusItem.setState(removeStateBit(currentFocusItem.getState(), stateBitset));
                }

                OverlayItem newFocusItem = getItem(newIndex);
                newFocusItem.setState(setStateBit(newFocusItem.getState(), stateBitset));
                currentIndex = newIndex;
            }
        }

        return currentIndex;
    }

    private int setStateBit(int state, int stateBitset) {
        // return state |= stateBitset;
        return state | stateBitset;
    }

    private int removeStateBit(int state, int stateBitset) {
        return (stateBitset ^ 0xFFFFFF) & state;
    }

    /**
     * <p>
     * 
     * </p>
     * @param mapView
     * @param x 屏幕对应的可视的像素坐标x
     * @param y 屏幕对应的可视的像素坐标y
     * @return
     */
    private int findItem(MapView mapView, int x, int y) {
        Projection projection = mapView.getProjection();
        Point p = new Point();
        int size = this.items.size();
        if (this.focusedIndex != -1 && this.focusedIndex < size) {
            OverlayItem item = getItem(this.focusedIndex);
            p = projection.toPixels(item.getPoint(), p);// toPixels
            if (hitTest(item, getMarker(item), x - p.x, y - p.y)) {
                return this.focusedIndex;
            }
        }
        for (int i = 0; i < size; i++) {
            OverlayItem item = getItem(i);
            p = projection.toPixels(item.getPoint(), p);

            if (hitTest(item, getMarker(item), x - p.x, y - p.y)) {
                return i;
            }
        }
        return -1;
    }

    boolean hitTest(OverlayItem item, Drawable marker, int hitX, int hitY) {
        Rect fingerBounds = marker.copyBounds();
        int m = this.fingerSize >> 1;
        fingerBounds.inset(-m, -m);
        return fingerBounds.contains(hitX, hitY);
    }

    boolean onTap(int index) {
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
        if ((keyCode == 66) || (keyCode == 23)) {
            if ((this.focusedIndex > -1) && (this.focusedIndex < this.items.size())) {
                return onTap(this.focusedIndex);
            }
        }
        return super.onKeyUp(keyCode, event, mapView);
    }

    /**
     * <p>
     * 判断是否捕捉到了item。
     * </p>
     * @param x
     * @param y
     * @param snapPoint 捕捉点的屏幕像素坐标。
     * @param mapView 捕捉点所在地图视图。
     * @return True表示捕捉到了，反之没有。
     */
    public boolean onSnapToItem(int x, int y, Point snapPoint, MapView mapView) {
        return false;
    }

    /**
     * <p>
     * 设置绘图焦点。
     * </p>
     * @param item
     */
    public void setFocus(Item item) {
        int index = 0;
        for (int i = 0; i < size(); i++) {
            OverlayItem it = getItem(i);
            if (it == item) {
                index = i;
                break;
            }
        }

        focus(index);
    }

    /**
     * <p>
     * 设置最近（或当前）焦点选中的item的索引，如果没有item被选中则设置为-1。
     * </p>
     * @param lastFocusedIndex 给定的最近（或当前）焦点选中的item的索引。   
     */
    protected void setLastFocusedIndex(int lastFocusedIndex) {
        this.focusedIndex = lastFocusedIndex;
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
        Point point = mapView.getProjection().toPixels(p, null);// toPixels
        int index = findItem(mapView, point.x, point.y);
        if (index > -1) {
            focus(index);
            // click(index);
            select(index);
            return onTap(index);
        }
        return super.onTap(p, mapView);
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
        this.gestureListener.setMapView(mapView);
        return this.gestureDetector.onTouchEvent(evt);
    }

    /**
     * <p>
     * 响应点击OverlayItem事件。
     * </p>
     * @param evt
     * @param mapView
     */
    private void onClick(MotionEvent evt, MapView mapView) {
        if (mapView.getProjection() == null) {
            return;
        }
        int index = findItem(mapView, (int) evt.getX(), (int) evt.getY());
        if (index > -1) {
            click(index);
        }
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
        this.trackballGestureDetector.analyze(evt);
        if (this.trackballGestureDetector.isScroll()) {
            if (this.trackballGestureDetector.scrollX() < 0.0F)
                focus(this.focusedIndex - 1);
            else
                focus(this.focusedIndex + 1);
        } else if (this.trackballGestureDetector.isTap()) {
            return onTap(this.focusedIndex);
        }
        return super.onTrackballEvent(evt, mapView);
    }

    /**
     * <p>
     * 设置焦点变化监听器。
     * </p>
     * @param l 焦点变化监听器。
     */
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        this.listener = l;
    }

    /**
     * <p>
     * 设置点击Overlay监听器。
     * </p>
     * @param l 点击Overlay监听器。
     */
    public void setOnClickListener(OnClickListener l) {
        this.clickListener = l;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private MapView mapView;

        private GestureListener() {
        }

        void setMapView(MapView mapView) {
            this.mapView = mapView;
        }

        public boolean onDown(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            int index = ItemizedOverlay.this.findItem(this.mapView, (int) x, (int) y);
            if (index > -1) {
                ItemizedOverlay.this.press(index);
            }

            return super.onDown(e);
        }

        public boolean onSingleTapUp(MotionEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            if (this.mapView.getProjection() == null) {
                return false;
            }
            onClick(e, mapView);
            Point2D geoPoint = this.mapView.getProjection().fromPixels(x, y);
            return ItemizedOverlay.this.onTap(geoPoint, this.mapView);
        }
    }

    /**
     * <p>
     * 焦点在Overlay发生变化监听事件。
     * </p>
     * <p>
     * 该接口监听聚焦在Overlay上不同OverlayItem的处理事件，用户可以根据需要实现该接口以处理相应事件。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static abstract interface OnFocusChangeListener {
        /**
         * <p>
         * 处理聚焦在Overlay上不同OverlayItem的事件，用户需要实现该接口以处理相应事件。
         * </p>
         * @param paramItemizedOverlay
         * @param paramOverlayItem
         */
        public abstract void onFocusChanged(ItemizedOverlay paramItemizedOverlay, OverlayItem paramOverlayItem);
    }

    /**
     * <p>
     * 点击Overlay监听事件。
     * </p>
     * <p>
     * 该接口监听点击在Overlay上的任意OverlayItem的处理事件，用户可以根据需要实现该接口以处理相应事件。
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    public static interface OnClickListener {
        /**
         * <p>
         * 处理点击在Overlay上的任意OverlayItem的事件，用户需要实现该接口以处理相应事件。
         * </p>
         * @param paramItemizedOverlay
         * @param paramOverlayItem
         */
        public void onClicked(ItemizedOverlay paramItemizedOverlay, OverlayItem paramOverlayItem);
    }
}