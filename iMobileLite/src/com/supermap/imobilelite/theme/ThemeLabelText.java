package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerTextStyle;

/**
 * <p>
 * 标签专题图中文本风格类。
 * </p>
 * <p>
 * 通过该类可以设置专题图中标签的文字字体大小和显示风格。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeLabelText implements Serializable {
    private static final long serialVersionUID = -8514895382852413332L;
    /**
     * <p>
     * 标签中文本的最大高度。
     * </p>
     */
    public double maxTextHeight;
    /**
     * <p>
     * 标签中文本的最大宽度。
     * </p>
     */
    public double maxTextWidth;
    /**
     * <p>
     * 标签中文本的最小高度。
     * </p>
     */
    public double minTextHeight;
    /**
     * <p>
     * 标签中文本的最小宽度。
     * </p>
     */
    public double minTextWidth;
    /**
     * <p>
     * 标签专题图统一的文本组合风格。
     * </p>
     */
    public LabelMixedTextStyle uniformMixedStyle;
    /**
     * <p>
     * 统一文本风格。
     * </p>
     */
    public ServerTextStyle uniformStyle;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeLabelText() {
        super();
    }

}
