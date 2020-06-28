package com.supermap.imobilelite.theme;

/**
 * <p>
 * 标签沿线标注方向枚举类。
 * </p>
 * <p>
 * 通过该类可以设置标签沿线放置的位置。规定，路线与水平方向的锐角夹角在60度以上用上下方向来表示，60度以下用左右方向来表示。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum AlongLineDirection {

    /**
     * <p>
     * 沿线的法线方向放置标签。
     * </p>
     */
    ALONG_LINE_NORMAL,

    /**
     * <p>
     * 从下到上，从左到右放置。
     * </p>
     */
    LEFT_BOTTOM_TO_RIGHT_TOP,

    /**
     * <p>
     * 从上到下，从左到右放置。
     * </p>
     */
    LEFT_TOP_TO_RIGHT_BOTTOM,

    /**
     * <p>
     * 从下到上，从右到左放置。
     * </p>
     */
    RIGHT_BOTTOM_TO_LEFT_TOP,

    /**
     * <p>
     * 从上到下，从右到左放置。
     * </p>
     */
    RIGHT_TOP_TO_LEFT_BOTTOM;
}
