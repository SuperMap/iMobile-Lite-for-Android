package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ColorGradientType;

/**
 * <p>
 * 范围分段专题图。
 * </p>
 * <p>
 * 按照提供的分段方法对字段的属性值进行分段，并根据每个属性值所在的分段范围赋予相应对象的显示风格。
 * </p>
 * <p>
 * 在分段专题图中，专题值按照某种分段方式被分成多个范围段，要素根据各自的专题值被分配到其中一个范围段中，在同一个范围段中的要素使用相同的颜色，填充，符号等风格进行显示。分段专题图所基于的专题变量必须为数值型，分段专题图一般用来反映连续分布现象的数量或程度特征，如降水量的分布，土壤侵蚀强度的分布等。
 * </p>
 * <p>
 * 下图为一幅2000年中国各省人均 GDP 情况的分段专题图：<br>
 * <img src="../../../../resources/Theme/ThemeRange_iServer6.bmp">
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeRange extends Theme {
    private static final long serialVersionUID = 6228755529200130617L;
    /**
     * <p>
     * 获取或设置范围分段专题图的颜色渐变方案。默认值为黄蓝。
     * </p>
     */
    public ColorGradientType colorGradientType = ColorGradientType.YELLOWBLUE;
    /**
     * <p>
     * 范围分段专题图子项类数组，必设字段。
     * </p>
     */
    public ThemeRangeItem[] items;
    /**
     * <p>
     * 用于制作范围分段专题图的字段或字段表达式。必设字段。
     * </p>
     */
    public String rangeExpression;
    /**
     * <p>
     * 范围分段模式，RangeMode枚举类常量。默认值为RangeMode.EQUALINTERVAL(等距离分段)。
     * </p>
     */
    public RangeMode rangeMode = RangeMode.EQUALINTERVAL;
    /**
     * <p>
     * 分段参数。
     * </p>
     */
    public double rangeParameter;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeRange() {
        super();
        type = ThemeType.RANGE;
    }

}
