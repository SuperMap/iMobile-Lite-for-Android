package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 统计专题图子项类。
 * </p>
 * <p>
 * 该类用来设置统计专题图子项的名称，专题变量，显示风格和分段风格。统计专题图通过为每个要素或记录绘制统计图来反映其对应的专题值的大小。统计专题图可以基于多个变量，反映多种属性，即可以将多个专题变量的值绘制在一个统计图上。每一个专题变量对应的统计图即为一个专题图子项。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGraphItem implements Serializable {
    private static final long serialVersionUID = -5775140917186859923L;
    /**
     * <p>
     * 获取或设置专题图子项的标题。
     * </p>
     */
    public String caption;
    /**
     * <p>
     * 获取或设置统计专题图的专题变量。
     * </p>
     */
    public String graphExpression;
    /**
     * <p>
     * 内存数组方式制作专题图时的值数组。
     * </p>
     */
    public double[] memoryDoubleValues;
    /**
     * <p>
     * 获取或设置统计专题图子项的显示风格。
     * </p>
     */
    public ServerStyle uniformStyle;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGraphItem() {
        super();
    }

}
