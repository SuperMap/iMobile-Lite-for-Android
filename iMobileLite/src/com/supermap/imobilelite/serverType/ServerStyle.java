package com.supermap.imobilelite.serverType;

import java.io.Serializable;

/**
 * <p>
 * 服务端矢量要素风格类。
 * </p>
 * <p>
 * 该类用于定义点状符号、线状符号、填充符号风格及其相关属性。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ServerStyle implements Serializable {
    private static final long serialVersionUID = 4597769545721635818L;
    /**
     * <p>
     * 获取或设置填充背景颜色。当填充模式为渐变填充时，该颜色为填充终止色。默认为白色。
     * </p>
     */
    public ServerColor fillBackColor;
    /**
     * <p>
     * 获取或设置背景是否不透明，默认值为false，表示透明。
     * </p>
     */
    public Boolean fillBackOpaque;
    /**
     * <p>
     * 获取或设置填充颜色。当填充模式为渐变填充 时，该颜色为填充起始颜色。默认为红色。
     * </p>
     */
    public ServerColor fillForeColor;
    /**
     * <p>
     * 获取或设置渐变填充的旋转角度。单位为度，精确到0.1度，默认为0。
     * </p>
     */
    public double fillGradientAngle;
    /**
     * <p>
     * 获取或设置矢量要素的渐变填充风格的渐变类型，FillGradientMode类常量。
     * </p>
     */
    public FillGradientMode fillGradientMode;
    /**
     * <p>
     * 获取或设置渐变填充中心点相对于填充区域范围中心点的水平偏移百分比。
     * </p>
     */
    public double fillGradientOffsetRatioX;
    /**
     * <p>
     * 获取或设置渐变填充中心点相对于填充区域范围中心点的垂直偏移百分比。
     * </p>
     */
    public double fillGradientOffsetRatioY;
    /**
     * <p>
     * 获取或设置填充不透明度，合法值为0-100的数值。默认值为100。
     * </p>
     */
    public int fillOpaqueRate = 100;
    /**
     * <p>
     * 获取或设置填充符号的编码，即在填充库中填充风格的ID。
     * </p>
     */
    public int fillSymbolID;
    /**
     * <p>
     * 获取或设置矢量要素的边线颜色，默认为黑色。当设置对象为点状符号时，该属性用于设置点状符号的颜色。
     * </p>
     */
    public ServerColor lineColor;
    /**
     * <p>
     * 获取或设置现状符号的编码，即线性库中线性的ID。
     * </p>
     */
    public int lineSymbolID;
    /**
     * <p>
     * 获取或设置边线的宽度，单位为毫米，精确到0.1，默认值为1。
     * </p>
     */
    public double lineWidth;
    /**
     * <p>
     * 获取或设置点状符号的旋转角度，以度为单位，精确到0.1度，逆时针方向为正方向，默认值为0。
     * </p>
     */
    public double markerAngle;
    /**
     * <p>
     * 获取或设置点状符号的大小，单位为毫米，精确到0.1，默认值为1。
     * </p>
     */
    public double markerSize = 1;
    /**
     * <p>
     * 获取或设置点状符号的编码。
     * </p>
     */
    public int markerSymbolID;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ServerStyle() {
        super();
    }

}
