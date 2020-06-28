package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerColor;
import com.supermap.imobilelite.serverType.ServerTextStyle;

/**
 * <p>
 * 统计专题图坐标轴样式类。
 * </p>
 * <p>
 * 该类用于设置统计图中坐标轴样式相关信息，如坐标轴颜色、是否显示、坐标文本样式等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGraphAxes implements Serializable {
    private static final long serialVersionUID = -7889186933342755690L;
    /**
     * <p>
     * 坐标轴颜色，默认为黑色。当axesDisplayed =true时有效。
     * </p>
     */
    public ServerColor axesColor;
    /**
     * <p>
     * 是否显示坐标轴，默认为false，即不显示。
     * </p>
     */
    public Boolean axesDisplayed = false;
    /**
     * <p>
     * 是否在统计图坐标轴上显示网格。默认为false，即不显示。当axesDisplayed =true时有效。
     * </p>
     */
    public Boolean axesGridDisplayed = false;
    /**
     * <p>
     * 是否显示坐标轴的文本标注。默认为false，即不显示。当axesDisplayed =true时有效。
     * </p>
     */
    public Boolean axesTextDisplayed = false;
    /**
     * <p>
     * 获取或设置坐标轴文本显示风格。当axesTextDisplayed =true时有效。
     * </p>
     */
    public ServerTextStyle axesTextStyle;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGraphAxes() {
        super();
    }

}
