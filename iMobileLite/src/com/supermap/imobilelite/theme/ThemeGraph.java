package com.supermap.imobilelite.theme;

/**
 * <p>
 * 统计专题图。
 * </p>
 * <p>
 * 统计专题图通过为每个要素或记录绘制统计图来反映其对应的专题值的大小。统计专题图可以基于多个变量，反映多种属性，即可以将多个专题变量的值绘制在一个统计图上。通过统计专题图可以在区域本身与各区域之间形成横向和纵向的对比。多用于具有相关数量特征的地图上，比如表示不同地区多年的粮食产量、GDP、人口等，不同时段客运量、地铁流量等。
 * </p>
 * <p>
 * 下图为一幅渤海地区2000年城乡人口比例的统计专题图：<br>
 * <img src="../../../../resources/Theme/themeGraph_iServer6.bmp">
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGraph extends Theme {
    private static final long serialVersionUID = 7670765518706277017L;
    /**
     * <p>
     * 柱状专题图中每一个柱的宽度。使用地图坐标单位，默认值为0。
     * </p>
     */
    public double barWidth;
    /**
     * <p>
     * 通过该字段可以设置统计符号是否流动显示和牵引线风格。
     * </p>
     */
    public ThemeFlow flow;
    /**
     * <p>
     * 统计图中地理要素的值与图表尺寸间的映射关系（常数、对数、平方根），即分级方式。默认值为GraduatedMode.CONSTANT
     * </p>
     */
    public GraduatedMode graduatedMode = GraduatedMode.CONSTANT;
    /**
     * <p>
     * 用于设置统计图中坐标轴样式相关信息，如坐标轴颜色、是否显示、坐标文本样式等。
     * </p>
     */
    public ThemeGraphAxes graphAxes;
    /**
     * <p>
     * 用于设置统计符号的最大最小尺寸。
     * </p>
     */
    public ThemeGraphSize graphSize;
    /**
     * <p>
     * 缩放地图时统计图符号是否固定大小。默认值为false，即统计图符号将随地图缩放。
     * </p>
     */
    public Boolean graphSizeFixed = false;
    /**
     * <p>
     * 用于设置统计图上的文字是否可见、文本类型、文本显示风格。
     * </p>
     */
    public ThemeGraphText graphText;
    /**
     * <p>
     * 统计专题图渲染类型。
     * </p>
     */
    public ThemeGraphType graphType;
    /**
     * <p>
     * 统计专题图子项（ThemeGraphItem）集合。必设字段。
     * </p>
     */
    public ThemeGraphItem[] items;
    /**
     * <p>
     * 指定需要制作专题图的对象ID数组，如SMID号
     * </p>
     */
    public int[] memoryKeys;
    /**
     * <p>
     * 专题图是否显示属性为负值的数据。默认为true表示显示。
     * </p>
     */
    public Boolean negativeDisplayed = true;
    /**
     * <p>
     * 用于设置统计图的偏移量。
     * </p>
     */
    public ThemeOffset offset;
    /**
     * <p>
     * 统计图是否采用避让方式显示。
     * </p>
     */
    public Boolean overlapAvoided;
    /**
     * <p>
     * 统计图中玫瑰图或三维玫瑰图用于等分的角度，默认为0度，精确到0.1度。
     * </p>
     */
    public double roseAngle;
    /**
     * <p>
     * 饼状统计图扇形的起始角。默认为0度，精确到0.1度。
     * </p>
     */
    public double startAngle;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGraph() {
        super();
        type = ThemeType.GRAPH;
    }

}
