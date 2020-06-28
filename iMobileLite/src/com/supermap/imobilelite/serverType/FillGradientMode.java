package com.supermap.imobilelite.serverType;

/**
 * <p>
 * 渐变填充风格的渐变类型枚举类。
 * </p>
 * <p>
 * 该类提供了五种渐变填充类型，所有渐变类型都是两种颜色之间的渐变，即从渐变起始色到渐变终止色之间的渐变。渐变填充范围的计算都是以填充区域的边界矩形，即最小外接矩形作为基础的。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum FillGradientMode {
    /**
     * <p>
     * 圆锥渐变填充。
     * </p>
     */
    CONICAL,
    /**
     * <p>
     * 线性渐变填充。
     * </p>
     */
    LINEAR,
    /**
     * <p>
     * 无渐变。
     * </p>
     */
    NONE,
    /**
     * <p>
     * 辐射渐变填充。
     * </p>
     */
    RADIAL,
    /**
     * <p>
     * 四角渐变填充。
     * </p>
     */
    SQUARE;

}
