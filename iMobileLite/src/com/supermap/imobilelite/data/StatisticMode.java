package com.supermap.imobilelite.data;

/**
 * <p>
 * 数据集字段统计类型枚举类。 
 * </p>
 * <p>
 * 字段统计类型包括六种：平均值、最大值、最小值、标准差、总和、方差。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum StatisticMode {
    /**
     * <p>
     * 统计所选字段的平均值。 
     * </p>
     */
    AVERAGE,
    /**
     * <p>
     * 统计所选字段的最大值。 
     * </p>
     */
    MAX,
    /**
     * <p>
     * 统计所选字段的最小值。 
     * </p>
     */
    MIN,
    /**
     * <p>
     * 统计所选字段的标准差。 
     * </p>
     */
    STDDEVIATION,
    /**
     * <p>
     * 统计所选字段的总和。 
     * </p>
     */
    SUM,
    /**
     * <p>
     * 统计所选字段的方差。 
     * </p>
     */
    VARIANCE
}
