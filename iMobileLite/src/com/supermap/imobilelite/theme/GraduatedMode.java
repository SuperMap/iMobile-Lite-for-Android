package com.supermap.imobilelite.theme;

/**
 * <p>
 * 专题图分级模式。
 * </p>
 * <p>
 * 分级主要是为了减少制作专题图时数据大小之间的差异。如果数据之间差距较大，则可以采用对数或者平方根的分级方式来进行，这样就减少了数据之间的绝对大小的差异，使得专题图的视觉效果比较好，同时不同类别之间的比较也还是有意义的。有三种分级模式：常数、对数和平方根，对于有值为负数的字段，不可以采用对数和平方根的分级方式。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum GraduatedMode {
    /**
     * <p>
     * 常量分级模式。
     * </p>
     */
    CONSTANT,
    /**
     * <p>
     * 对数分级模式。
     * </p>
     */
    LOGARITHM,
    /**
     * <p>
     * 平方根分级模式。
     * </p>
     */
    SQUAREROOT;

}
