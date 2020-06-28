package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 矩形标签元素抽象类。
 * </p>
 * <p>
 * 矩形标签专题图是标签专题图（Themelabel）的一种，其中矩阵标签中的填充元素又可分为图片类型（LabelImageCell）、符号类型（LabelSymbolCell）、专题图类型（LabelThemeCell）三种，该类是这三种类型的矩阵标签元素的基类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class LabelMatrixCell implements Serializable {
    private static final long serialVersionUID = -7544538717715602319L;
    /**
     * <p>
     * 获取或设置矩阵标签元素类型。由LabelMatrixCellType类定义，包括图片、符号、专题图三种。
     * </p>
     */
    public LabelMatrixCellType type = LabelMatrixCellType.THEME;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public LabelMatrixCell() {
        super();
    }

}
