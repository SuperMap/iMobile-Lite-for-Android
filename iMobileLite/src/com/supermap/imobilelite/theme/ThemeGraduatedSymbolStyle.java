package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 等级符号专题图正负零值显示风格类。
 * </p>
 * <p>
 * 通过该类可以设置正值、零值、负值对应的等级符号显示风格，以及是否显示零值或负值对应的等级符号。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGraduatedSymbolStyle implements Serializable {
    private static final long serialVersionUID = 1107410476425218655L;
    /**
     * <p>
     * 是否显示负值。默认为false。
     * </p>
     */
    public Boolean negativeDisplayed = false;
    /**
     * <p>
     * 负值的等级符号风格。
     * </p>
     */
    public ServerStyle negativeStyle;
    /**
     * <p>
     * 正值的等级符号风格。
     * </p>
     */
    public ServerStyle positiveStyle;
    /**
     * <p>
     * 是否显示0值的等级符号。默认为false。
     * </p>
     */
    public Boolean zeroDisplayed;
    /**
     * <p>
     * 0值的等级符号风格。
     * </p>
     */
    public ServerStyle zeroStyle;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGraduatedSymbolStyle() {
        super();
    }

}
