package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 标签专题图。
 * </p>
 * <p>
 * 标签专题图用专题值对点、线、面等对象做标注（也叫标签），多用字符或数值型字段，如标注地名、道路名称、河流等级、宽度等信息。这里需要注意的是地图上一般还会出现图例说明，图名，比例尺等等，这些都是制图元素，不属于标签专题图标注的范畴。
 * </p>
 * <p>
 * 在标签专题图中，可以统一对标签的显示风格和位置进行设置，也可以通过分段的方式（利用分段表达式，即 rangeExpression），对单个或每个分段内的标签的风格单独进行设置。
 * </p>
 * <p>
 * 下图为一幅标签专题图的示意图：<br>
 * <img src="../../../../resources/Theme/ThemeLabel_iServer6.bmp">
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeLabel extends Theme {
    private static final long serialVersionUID = 6520838678773174203L;
    /**
     * <p>
     * 用于定义标签沿线标注的样式。
     * </p>
     */
    public ThemeLabelAlongLine alongLine;
    /**
     * <p>
     * 标签专题图中标签的背景显示样式。通过该字段可以设置标签的背景形状和颜色等。
     * </p>
     */
    public ThemeLabelBackground background;
    /**
     * <p>
     * 用于定义标签专题图中标签是否流动显示及牵引线显示样式。
     * </p>
     */
    public ThemeFlow flow;
    /**
     * <p>
     * 设置分段标签专题图子项数组。
     * </p>
     */
    public ThemeLabelItem[] items;
    /**
     * <p>
     * 标注字段表达式。系统将labelExpression对应的字段或字段表达式的值以标签的形式显示在图层中。必设字段。
     * </p>
     */
    public String labelExpression;
    /**
     * <p>
     * 专题图中超长标签的处理模式，默认为LabelOverLengthMode.NONE。
     * </p>
     */
    public LabelOverLengthMode labelOverLengthMode = LabelOverLengthMode.NONE;
    /**
     * <p>
     * 矩阵标签元素数组。
     * </p>
     */
    public LabelMatrixCell[][] matrixCells;
    /**
     * <p>
     * 标签在每一行显示的最大长度，单位为字符，默认为256。
     * </p>
     */
    public double maxLabelLength = 256;
    /**
     * <p>
     * 如果现实的标签内容为数字，通过该字段设置其显示的精度。
     * </p>
     */
    public int numericPrecision;
    /**
     * <p>
     * 用于定义专题图中标签的偏移量。
     * </p>
     */
    public ThemeOffset offset;
    /**
     * <p>
     * 是否允许以文本避让方式显示文本。默认值为false，即不进行自动避让。
     * </p>
     */
    public Boolean overlapAvoided = false;
    /**
     * <p>
     * 制作分段标签专题的分段字段或字段表达式。
     * </p>
     */
    public String rangeExpression;
    /**
     * <p>
     * 是否显示长度大于被标注对象本身长度的标签，默认为false，即不显示。
     * </p>
     */
    public Boolean smallGeometryLabeled = false;
    /**
     * <p>
     * 标签中的文本风格。
     * </p>
     */
    public ThemeLabelText text;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeLabel() {
        super();
        type = ThemeType.LABEL;
    }

}
