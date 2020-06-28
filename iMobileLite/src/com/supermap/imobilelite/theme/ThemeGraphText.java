package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerTextStyle;

/**
 * <p>
 * 统计图文字标注风格。
 * </p>
 * <p>
 * 通过该类可以设置统计图表中文字可见性以及标注风格等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGraphText implements Serializable {
    private static final long serialVersionUID = 2000226430184513886L;
    /**
     * <p>
     * 是否显示统计图上的文字标注。默认为false，即不显示。
     * </p>
     */
    public Boolean graphTextDisplayed = false;
    /**
     * <p>
     * 获取或设置统计专题图文本显示样式。
     * </p>
     */
    public ThemeGraphTextFormat graphTextFormat;
    /**
     * <p>
     * 用于设置统计图上的文字标注风格。
     * </p>
     */
    public ServerTextStyle graphTextStyle;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGraphText() {
        super();
    }

}
