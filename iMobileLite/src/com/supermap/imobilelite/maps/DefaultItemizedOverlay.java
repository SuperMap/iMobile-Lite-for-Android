package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

/**
 * <p>
 * DefaultItemizedOverlay是ItemizedOverlay的一个默认实现类。
 * </p>
 * <p>
 * 用于绘制、创建平移边界、为每个点绘制标记点，和维护一个焦点点中的item， 同时负责把屏幕点击匹配到item上去，分发焦点改变事件给备选的监听器。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class DefaultItemizedOverlay extends ItemizedOverlay<OverlayItem> {
    private List<OverlayItem> items = new ArrayList();
    private Drawable defaultMarker;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param defaultMarker 覆盖物标识。
     */
    public DefaultItemizedOverlay(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
        this.defaultMarker = defaultMarker;
    }

    /**
     * <p>
     * 创建实体item。
     * </p>
     * @param i 索引值。
     * @return item对象。
     */
    protected OverlayItem createItem(int i) {
        return (OverlayItem) this.items.get(i);
    }

    /**
     * <p>
     * 返回overlay的item的数目。
     * </p>
     * @return item的数目。
     */
    public int size() {
        return this.items.size();
    }

    /**
     * <p>
     * 添加一个OverlayItem，并显示该item实例。
     * </p>
     * @param item 覆盖物item对象。
     */
    public void addItem(OverlayItem item) {
        if (item.getMarker(item.getState()) == null) {
            item.setMarker(this.defaultMarker);
        } else {
            boundCenterBottom(item.getMarker(item.getState()));
        }
        this.items.add(item);
        populate();
    }
    
    /**
     * <p>
     * 删除给定的OverlayItem实例。
     * </p>
     * @param item 覆盖物item对象。
     */
    public void removeItem(OverlayItem item) {        
        this.items.remove(item);
        populate();
    }

    /**
     * <p>
     * 返回给定索引对应item的序号。默认情况下，item按照由上至下、由左至右排序。子类可以覆盖这个方法以改变绘制顺序。
     * </p>
     * @param drawingOrder 按绘制排序的索引。
     * @return 给定索引对应的item序号。
     */
    protected int getIndexToDraw(int drawingOrder) {
        return drawingOrder;
    }

    /**
     * <p>
     * 清空overlay中所有的item。
     * </p>
     */
    public void clear() {
        super.clear();
        this.items.clear();
    }

    /**
     * <p>
     * 销毁当前overlay。
     * </p>
     */
    public void destroy() {
        clear();
    }

    /**
     * <p>
     * 判断是否发生点击overlay中索引为index的item的事件。
     * </p>
     * @param index 索引值。
     * @return true表示发生点击事件，反之没有。
     */
    protected boolean onTap(int index) {
        return true;
    }

    /**
     * <p>
     * 判断是否发生点击overlay的事件。
     * </p>
     * @param p 点击的坐标位置。
     * @param mapView 点击的地图视图。
     * @return true表示发生点击事件，反之没有。
     */
    public boolean onTap(Point2D p, MapView mapView) {
        boolean test = super.onTap(p, mapView);
        if ((test) && (this.tapListener != null)) {
            this.tapListener.onTap(p, mapView);
            return true;
        }
        return false;
    }

    /*
 *设置标示符中心点显示（默认为标示符下边框点覆盖显示）
 */
    public void setCenterOverlay(boolean b){
        center=b;
    }


}