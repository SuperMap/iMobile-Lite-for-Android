package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 范围分段专题图子项类。
 * </p>
 * <p>
 * 在分段专题图中，字段值按照某种分段模式被分成多个范围段，每个范围段即为一个子项，同一范围段的要素属于同一个分段专题图子项。每个子项都有其分段起始值、终止值、名称和风格等。每个分段所表示的范围为[start，end)。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeRangeItem implements Serializable {
    private static final long serialVersionUID = 6323322432717543586L;
    /**
     * <p>
     * 范围分段专题图子项的标题。
     * </p>
     */
    public String caption;
    /**
     * <p>
     * 范围分段专题图子项的终止值。
     * </p>
     */
    public double end;
    /**
     * <p>
     * 范围分段专题图子项的起始值。
     * </p>
     */
    public double start;
    /**
     * <p>
     * 范围分段专题图子项的显示风格。
     * </p>
     */
    public ServerStyle style;
    /**
     * <p>
     * 范围分段专题图子项的可见性。默认为true，表示可见。
     * </p>
     */
    public Boolean visible;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeRangeItem() {
        super();
    }

}
