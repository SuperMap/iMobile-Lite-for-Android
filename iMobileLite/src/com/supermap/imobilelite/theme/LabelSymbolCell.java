package com.supermap.imobilelite.theme;

import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 符号类型的矩阵标签元素类。
 * </p>
 * <p>
 * 该类型的对象可作为矩阵标签对象中的一个矩阵标签元素。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class LabelSymbolCell extends LabelMatrixCell {
    private static final long serialVersionUID = 2955423032175136000L;
    /**
     * <p>
     * 所使用符号的样式。这里 Style 中的符号编码，即符号风格 ID 的设置不起作用，符号风格 ID 由 symbolIDField 指定。
     * </p>
     */
    public ServerStyle style;
    /**
     * <p>
     * 符号 ID 或 记录符号 ID 的字段名称,必设属性。
     * </p>
     */
    public String symbolIDField;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public LabelSymbolCell() {
        super();
    }
}
