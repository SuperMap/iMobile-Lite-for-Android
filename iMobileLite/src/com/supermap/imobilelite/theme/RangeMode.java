package com.supermap.imobilelite.theme;

/**
 * <p>
 * 范围分段专题图分段方式枚举类。
 * </p>
 * <p>
 * 该类列举出了六种用于分段方法：等距离、平方根、标准差、对数、等计数、自定义。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum RangeMode {
    /**
     * <p>
     * 自定义分段法。
     * </p>
     */
    CUSTOMINTERVAL,
    /**
     * <p>
     * 等距离分段法。
     * </p>
     */
    EQUALINTERVAL,
    /**
     * <p>
     * 对数分段法。
     * </p>
     */
    LOGARITHM,
    /**
     * <p>
     * 等计数分段法。
     * </p>
     */
    QUANTILE,
    /**
     * <p>
     * 平方根分段法。
     * </p>
     */
    SQUAREROOT,
    /**
     * <p>
     * 标准差分段法。
     * </p>
     */
    STDDEVIATION;

}
