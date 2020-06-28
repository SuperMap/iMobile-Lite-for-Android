package com.supermap.imobilelite.spatialAnalyst;

/**
 * <p>
 * 反距离加权插值分析参数类。
 * </P>
 * <p>
 * 通过该类可以设置反距离加权插值分析所需的参数。反距离加权插值方法通过计算附近区域离散点群的平均值来估算单元格的值，生成栅格数据集。 这是一种简单有效的数据内插方法，运算速度相对较快。反距离加权算法假设离预测点越近的值对预测点的影响越大，即预测某点的值时，其周围点的权值与距离预测点的距离成反比。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class InterpolationIDWAnalystParameters extends InterpolationAnalystParameters {
    private static final long serialVersionUID = 3754190819541392211L;

    /**
     * <p>
     * 【固定点数查找】方式下，设置待查找的点数，即参与差值运算的点数。默认为12。
     * </p>
     */
    public int expectedCount = 12;

    /**
     * <p>
     * 距离权重计算的幂次。幂次越大，随距离的增大权值下降越快。默认值为2。
     * </p>
     */
    public int power = 2;

    /**
     * <p>
     * 插值运算时，查找参与运算点的方式，支持固定点数查找、定长查找。必输参数。
     * </p>
     */
    public SearchMode searchMode;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public InterpolationIDWAnalystParameters() {
        super();
    }

}
