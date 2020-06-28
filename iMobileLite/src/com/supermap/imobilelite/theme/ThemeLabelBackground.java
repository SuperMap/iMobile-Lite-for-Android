package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 标签背景风格类。
 * </p>
 * <p>
 * 通过该类可以设置标签的背景形状和风格。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ThemeLabelBackground implements Serializable {
    private static final long serialVersionUID = -7040867403194630445L;
    /**
     * <p>
     * 用于获取或设置标签专题图中标签背景风格。
     * </p>
     */
    public ServerStyle backStyle;
    /**
     * <p>
     * 标签专题图中标签背景的形状，默认为LabelBackShape.NONE即不使用任何的形状作为标签的背景。
     * </p>
     */
    public LabelBackShape labelBackShape = LabelBackShape.NONE;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeLabelBackground() {
        super();
    }

}
