package com.supermap.imobilelite.theme;

/**
 * <p>
 * 标签专题图中超长标签的处理模式枚举类。
 * </p>
 * <p>
 * SuperMap 提供三种超长标签的处理方式来控制超长标签的显示行为，即换行显示、对超长标签不进行处理、省略超出部分。<br>
 * 对于标签的长度超过设置的标签最大长度的标签称为超长标签，标签的最大长度可以通过 ThemeLabel.maxLabelLength 来设置。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum LabelOverLengthMode {
    
    /**
     * <p>
     * 换行显示。此模式将超长标签中超出指定的标签最大长度的部分换行显示，即用多行来显示超长标签。
     * </p>
     */
    NEWLINE,
    
    /**
     * <p>
     * 对超长标签不进行处理。此为默认模式，即在一行中全部显示此超长标签。
     * </p>
     */
    NONE,
    
    /**
     * <p>
     * 省略超出部分。此模式将超长标签中超出指定的标签最大长度（MaxLabelLength）的部分用省略号表示。
     * </p>
     */
    OMIT;

}
