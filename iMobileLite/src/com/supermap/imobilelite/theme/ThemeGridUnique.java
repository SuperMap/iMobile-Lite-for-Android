package com.supermap.imobilelite.theme;

import com.supermap.imobilelite.serverType.ServerColor;

/**
 * <p>
 * 栅格单值专题图。
 * </p>
 * <p>
 * 栅格单值专题图，是将单元格值相同的归为一类，为每一类设定一种颜色，从而用来区分不同的类别。栅格单值专题图适用于离散栅格数据和部分连续栅格数据，对于单元格值各不相同的那些连续栅格数据，使用栅格单值专题图不具有任何意义。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGridUnique extends Theme {
    private static final long serialVersionUID = -4002065877498817643L;
    /**
     * <p>
     * 栅格单值专题图的默认颜色。
     * </p>
     */
    public ServerColor defaultcolor;
    /**
     * <p>
     * 栅格单值专题图子项类数组。
     * </p>
     */
    public ThemeGridUniqueItem[] items;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGridUnique() {
        super();
        type = ThemeType.GRIDUNIQUE;
    }

}
