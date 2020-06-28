package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerColor;

/**
 * <p>
 * 栅格单值专题图子项类。
 * </p>
 * <p>
 * 栅格单值专题图是将值相同的单元格归为一类，每一类是一个专题图子项。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGridUniqueItem implements Serializable {
    private static final long serialVersionUID = -607372529801810695L;
    /**
     * <p>
     * 栅格单值专题图子项的名称。
     * </p>
     */
    public String caption;
    /**
     * <p>
     * 栅格单值专题图子项的显示颜色。
     * </p>
     */
    public ServerColor color;
    /**
     * <p>
     * 栅格单值专题图子项的专题值，即单元格的值，值相同的单元格位于一个子项内。
     * </p>
     */
    public double unique;
    /**
     * <p>
     * 栅格单值专题图子项是否可见。默认为true，表示可见。
     * </p>
     */
    public Boolean visible = true;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGridUniqueItem() {
        super();
    }

}
