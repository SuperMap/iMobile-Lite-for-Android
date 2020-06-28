package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 单值专题图子项。
 * </p>
 * <p>
 * 单值专题图是将专题值相同的要素归为一类，为每一类设定一种渲染风格，其中每一类就是一个专题图子项。比如，利用单值专题图制作行政区划图，Name字段代表省/直辖市名，该字段用来做专题变量，如果该字段的字段值总共有5种不同值，则该行政区划图有5个专题图子项，其中每一个子项内的要素 Name 字段值都相同。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeUniqueItem implements Serializable {
    private static final long serialVersionUID = 4024505389816569799L;

    /**
     * <p>
     * 单值专题图子项的名称。
     * </p>
     */
    public String caption;

    /**
     * <p>
     * 单值专题图子项的显示风格。
     * </p>
     */
    public ServerStyle style;

    /**
     * <p>
     * 单值专题图子项的单值。
     * </p>
     */
    public String unique;

    /**
     * <p>
     * 单值专题图子项是否可见。
     * </p>
     */
    public Boolean visible;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeUniqueItem() {
        super();
    }

}
