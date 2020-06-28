package com.supermap.imobilelite.spatialAnalyst;

/**
 * <p>
 * 克吕金插值分析参数类。
 * </p>
 * <p>
 * 通过该类可以设置克吕金插值分析所需的参数。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class InterpolationKrigingAnalystParameters extends InterpolationAnalystParameters {
    private static final long serialVersionUID = 1921581124345936527L;

    /**
     * <p>
     * 克吕金算法中旋转角度值。默认值为0。
     * </p>
     */
    public double angle;

    /**
     * <p>
     * 【固定点数查找】方式下，设置待查找的点数，默认为12；【定长查找】方式下，设置查找的最小点数，默认为12。
     * </p>
     */
    public int expectedCount = 12;

    /**
     * <p>
     * 【泛克吕金】类型下，用于插值的样点数据中趋势面方程的阶数，可选值为exp1、exp2，默认为exp1。
     * </p>
     */
    public Exponent exponent = Exponent.EXP1;

    /**
     * <p>
     * 【块查找】方式下，设置最多参与插值的点数。默认为200。
     * </p>
     */
    public int maxPointCountForInterpolation = 200;

    /**
     * <p>
     * 【块查找】方式下，设置单个块内最多参与运算点数。默认为50。
     * </p>
     */
    public int maxPointCountInNode = 50;

    /**
     * <p>
     * 【简单克吕金】类型下,插值字段的平均值。
     * </p>
     */
    public double mean;

    /**
     * <p>
     * 克吕金算法中块金效应值。默认值为0。
     * </p>
     */
    public double nugget;

    /**
     * <p>
     * 克吕金算法中自相关阈值，单位与原数据集单位相同。默认值为0。
     * </p>
     */
    public double range;

    /**
     * <p>
     * 插值运算时，查找参与运算点的方式，有固定点数查找、定长查找、块查找。必输参数。
     * </p>
     */
    public SearchMode searchMode;

    /**
     * <p>
     * 克吕金算法中基台值。默认值为0。
     * </p>
     */
    public double sill;

    /**
     * <p>
     * 克吕金插值的类型。必输参数。
     * </p>
     */
    public InterpolationAlgorithmType type;

    /**
     * <p>
     * 克吕金插值时的半变函数类型，包括指数型（EXPONENTIAL）、球型（SPHERICAL）、高斯型（GAUSSIAN），默认为球型（SPHERICAL）。
     * </p>
     */
    public VariogramMode variogramMode = VariogramMode.SPHERICAL;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public InterpolationKrigingAnalystParameters() {
        super();
    }

}
