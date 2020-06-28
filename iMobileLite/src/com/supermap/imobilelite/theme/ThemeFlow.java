package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 专题图中渲染符号流动显示和牵引线风格设置类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ThemeFlow implements Serializable {
    private static final long serialVersionUID = -78452136813936261L;

    /**
     * <p>
     * 是否流动显示专题图中的渲染符号,默认为false，表示不流动显示。
     * </p>
     */
    public Boolean flowEnabled = false;

    /**
     * <p>
     * 是否显示渲染符号与其标注对象之间的牵引线。默认为false。
     * </p>
     */
    public Boolean leaderLineDisplayed = false;
    /**
     * <p>
     * 渲染符号与其标注对象之间牵引线的风格。
     * </p>
     */
    public ServerStyle leaderLineStyle;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeFlow() {
        super();
    }

}
