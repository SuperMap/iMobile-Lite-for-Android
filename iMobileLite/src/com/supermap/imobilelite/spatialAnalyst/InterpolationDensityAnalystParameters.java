package com.supermap.imobilelite.spatialAnalyst;

/**
 * <p>
 * 点密度插值分析参数类。
 * </P>
 * <p>
 * 通过该类可以设置点密度插值分析所需的参数。对点数据集进行点密度插值分析后，可以得到反映数据分布密度的栅格数据集。不同于其他插值方法的是，插值字段的含义为每个插值点在插值过程中的权重，插值结果反映的是原数据集数据点的分布密度。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class InterpolationDensityAnalystParameters extends InterpolationAnalystParameters {
    private static final long serialVersionUID = -3933757093668333949L;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public InterpolationDensityAnalystParameters() {
        super();
    }
}
