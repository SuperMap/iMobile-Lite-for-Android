package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerTextStyle;

/**
 * <p>
 * 分段标签专题图子项。
 * </p>
 * <p>
 * 标签专题图用标签表达式（ ThemeLabel.labelExpression）的值对点、线、面等对象做标注。还允许划分标签专题图子项，不同专题图子项中的要素的标注用不同的风格显示。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeLabelItem implements Serializable {
    private static final long serialVersionUID = -8052917331540748506L;
    /**
     * <p>
     * 标签专题图子项的标题。
     * </p>
     */
    public String caption;
    /**
     * <p>
     * 标签专题图子项的终止值。
     * </p>
     */
    public double end;
    /**
     * <p>
     * 标签专题图子项的分段起始值。
     * </p>
     */
    public double start;
    /**
     * <p>
     * 标签专题图子项文本的显示风格。
     * </p>
     */
    public ServerTextStyle style;
    /**
     * <p>
     * 标签专题图子项是否可见。默认值为true，表示可见。
     * </p>
     */
    public Boolean visible = true;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeLabelItem() {
        super();
    }

}
