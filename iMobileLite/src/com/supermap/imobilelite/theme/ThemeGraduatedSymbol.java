package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 等级符号专题图。
 * </p>
 * <p>
 * 等级符号专题图是利用符号的大小来表示图层的数值型的某一字段或字段表达式的值。等级符号专题图多用于具有数量特征的地图，比如表示不同地区的粮食产量、GDP、人口等的分级。</br>
 * 例如，在下图中，通过符号的大小即可直观地看出黄河流经各省高校的相对多少：</br>
 * <img src="../../../../resources/Theme/ThemeGraduatedSymbol.png">
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ThemeGraduatedSymbol extends Theme {
    private static final long serialVersionUID = -3518076268864384879L;
    /**
     * <p>
     * 等级符号专题图的基准值，单位同expression所指定字段对应的值经过分级计算之后的值。
     * </p>
     */
    public double baseValue;
    /**
     * <p>
     * 用于创建等级符号专题图的字段或字段表达式，字段或字段表达式为数值型。必设字段。
     * </p>
     */
    public String expression;
    /**
     * <p>
     * 等级符号专题图符号流动显示与牵引线设置类。通过该字段可以设置等级符号是否流动显示和牵引线风格。
     * </p>
     */
    public ThemeFlow flow;
    /**
     * <p>
     * 等级符号专题图分级模式。
     * </p>
     */
    public GraduatedMode graduatedMode;
    /**
     * <p>
     * 用于设置等级符号图相对于要素内点的偏移量。
     * </p>
     */
    public ThemeOffset offset;
    /**
     * <p>
     * 用于设置等级符号图正负和零值显示风格。
     * </p>
     */
    public ThemeGraduatedSymbolStyle style;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGraduatedSymbol() {
        super();
        type = ThemeType.GRADUATEDSYMBOL;
    }

}
