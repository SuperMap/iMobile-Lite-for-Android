package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerColor;

/**
 * <p>
 * 栅格分段专题图子项类。
 * </p>
 * <p>
 * 在栅格分段专题图中，将栅格值按照某种分段模式被分成多个范围段。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGridRangeItem implements Serializable {
    private static final long serialVersionUID = 7801043541224594466L;
    /**
     * <p>
     * 栅格分段专题图中子项的名称。
     * </p>
     */
    public String caption;
    /**
     * <p>
     * 栅格分段专题图中每一个分段专题图子项的对应的颜色。
     * </p>
     */
    public ServerColor color;
    /**
     * <p>
     * 栅格分段专题图子项的终止值。
     * </p>
     */
    public double end;
    /**
     * <p>
     * 栅格分段专题图子项的起始值。
     * </p>
     */
    public double start;
    /**
     * <p>
     * 栅格分段专题图中的子项是否可见。默认为true。即栅格分段专题图中的子项可见。
     * </p>
     */
    public Boolean visible = true;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGridRangeItem() {
        super();
    }

}
