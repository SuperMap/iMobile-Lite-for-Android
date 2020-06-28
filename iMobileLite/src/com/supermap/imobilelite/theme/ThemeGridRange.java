package com.supermap.imobilelite.theme;

import com.supermap.imobilelite.serverType.ColorGradientType;

/**
 * <p>
 * 栅格分段专题图。
 * </p>
 * <p>
 * 栅格分段专题图，是将所有单元格的值按照某种分段方式分成多个范围段，值在同一个范围段中的单元格使用相同的颜色进行显示。栅格分段专题图一般用来反映连续分布现象的数量或程度特征。比如某年的全国降水量分布图，将各气象站点的观测值经过内插之后生成的栅格数据进行分段显示。该类类似于分段专题图类，不同点在于分段专题图的操作对象是矢量数据，而栅格分段专题图的操作对象是栅格数据。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGridRange extends Theme {
    private static final long serialVersionUID = -8114910929472948620L;
    /**
     * <p>
     * 获取或设置专题图的颜色渐变方案。
     * </p>
     */
    public ColorGradientType colorGradientType;
    /**
     * <p>
     * 栅格分段专题图子项数组。
     * </p>
     */
    public ThemeGridRangeItem[] items;
    /**
     * <p>
     * 分段专题图的分段方式。
     * </p>
     */
    public RangeMode rangeMode;
    /**
     * <p>
     * 分段参数。
     * </p>
     */
    public double rangeParameter;
    /**
     * <p>
     * 是否对分段专题图中分段的颜色风格进行反序显示。默认为false，即不对颜色风格进行反序显示。
     * </p>
     */
    public Boolean reverseColor;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGridRange() {
        super();
        type = ThemeType.GRIDRANGE;
    }

}
