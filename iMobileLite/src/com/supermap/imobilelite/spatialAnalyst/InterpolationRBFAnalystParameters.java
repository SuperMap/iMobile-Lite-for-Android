package com.supermap.imobilelite.spatialAnalyst;

/**
 * <p>
 * 样条插值（径向基函数插值法）分析参数类。
 * </p>
 * <p>
 * 通过该类可以设置样条插值分析所需的参数。样条插值方法假设变化是平滑的，它有两个特点： 1、表面必须精确通过采样点； 2、表面必须有最小曲率。样条插值在利用大量采样点创建有视觉要求的平滑表面方面具有优势，但难以对误差进行估计，如样点在较短的水平距离内表面值发生急剧变化，或存在测量误差及具有不确定性时，不适合使用此算法。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class InterpolationRBFAnalystParameters extends InterpolationAnalystParameters {
    private static final long serialVersionUID = -6694182623335985010L;

    /**
     * <p>
     * 【固定点数查找】方式下，设置参与差值运算的点数。
     * </p>
     */
    public int expectedCount;

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
     * 插值运算时，查找参与运算点的方式，有固定点数查找、定长查找、块查找。必输参数。
     * </p>
     */
    public SearchMode searchMode;

    /**
     * <p>
     * 光滑系数，该值表示插值函数曲线与点的逼近程度，值域为 0到1，默认值约为0.1。
     * </p>
     */
    public double smooth = 0.1;

    /**
     * <p>
     * 张力系数，用于调整结果栅格数据表面的特性，默认为40。
     * </p>
     */
    public double tension = 40;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public InterpolationRBFAnalystParameters() {
        super();
    }

}
