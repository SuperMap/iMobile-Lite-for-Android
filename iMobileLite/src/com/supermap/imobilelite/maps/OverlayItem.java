package com.supermap.imobilelite.maps;

import android.graphics.drawable.Drawable;

/**
 * <p>
 * ItemizedOverlay的基本组件，存储单个的overlay数据。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class OverlayItem {
    // public static final int ITEM_STATE_FOCUSED_MASK = 4;
    // public static final int ITEM_STATE_SELECTED_MASK = 1;
    // public static final int ITEM_STATE_PRESSED_MASK = 2;
    /**
     * <p>
     * 该item的标记点。
     * </p>
     */
    protected Drawable mMarker;
    /**
     * <p>
     * 该item的位置。
     * </p>
     */
    protected Point2D mPoint;
    /**
     * <p>
     * 该item的标题文本。
     * </p>
     */
    protected String mTitle;
    /**
     * <p>
     * 该item的文字片段。
     * </p>
     */
    protected String mSnippet;
    private int mAlignment;
    private int state;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param point 坐标点。
     * @param title 名称。
     * @param snippet 描述片段。
     */
    public OverlayItem(Point2D point, String title, String snippet) {
        this.mPoint = point;
        this.mTitle = title;
        this.mSnippet = snippet;
    }

    /**
     * <p>
     * 返回标记点，该标记点在地图上绘制该item时使用。
     * </p>
     * @param stateBitSet 状态标识。
     * @return 标记点对象。
     */
    public Drawable getMarker(int stateBitSet) {
        if (this.mMarker != null) {
            setState(this.mMarker, stateBitSet);
        }
        return this.mMarker;
    }

    /**
     * <p>
     * 返回该item的经纬度坐标，以Point2D形式。
     * </p>
     * @return 坐标点对象。
     */
    public Point2D getPoint() {
        return this.mPoint;
    }
    
    /**
     * <p>
     * 设置该item的经纬度坐标，以Point2D形式。
     * </p>
     * @return 坐标点对象。
     */
    public void setPoint(Point2D point) {
        this.mPoint = point;
    }

    /**
     * <p>
     * 返回该item的文字描述片段。
     * </p>
     * @return 文字描述片段。
     */
    public String getSnippet() {
        return this.mSnippet;
    }

    /**
     * <p>
     * 设置该item的文字片段。
     * </p>
     * @param snippet 描述文字片段。
     */
    public void setSnippet(String snippet) {
        this.mSnippet = snippet;
    }

    /**
     * <p>
     * 返回该item的标题文本。
     * </p>
     * @return 标题文本。
     */
    public String getTitle() {
        return this.mTitle;
    }

    /**
     * <p>
     * 设置该item的标题文本。
     * </p>
     * @param title item的标题文本。
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * <p>
     * 设置overlay标记。
     * </p>
     * @param marker overlay标记。
     */
    public void setMarker(Drawable marker) {
        if (this.mMarker != null) {
            marker.setBounds(this.mMarker.getBounds());
        }
        this.mMarker = marker;
    }

    /**
     * <p>
     * 设置状态。
     * </p>
     * @param marker 绘图标识。
     * @param stateBitset 状态标识。
     */
    public static void setState(Drawable marker, int stateBitset) {
        int[] states = new int[3];
        int index = 0;
        if ((stateBitset & 0x2) > 0)
            states[(index++)] = 16842919;// state_pressed
        if ((stateBitset & 0x1) > 0)
            states[(index++)] = 16842913;// state_selected
        if ((stateBitset & 0x4) > 0) {
            states[(index++)] = 16842908;// state_focused
        }
        marker.setState(states);
    }

    int getState() {
        return this.state;
    }

    void setState(int state) {
        this.state = state;
    }

    /**
     * <p>
     * 设置绘图对象的大小。
     * </p>
     * @param bitset 绘图对象的大小。
     */
    public void setAlignment(int bitset) {
        this.mAlignment = bitset;
    }

    /**
     * <p>
     * 获取绘图对象的大小。
     * </p>
     * @return 绘图对象的大小。
     */
    public int getAlignment() {
        return this.mAlignment;
    }

    /**
     * <p>
     * 路由位址。
     * </p>
     * @return 路由位址字符串。
     */
    public String routableAddress() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.mPoint.getY()).append(",").append(this.mPoint.getX());
        return builder.toString();
    }
}