package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 耗费矩阵分析服务结果类。
 * </p>
 * <p> 
 * 该类用于存储了指定的耗费矩阵结点数组（ComputeWeightMatrixParameters.nodes）中任意两点间的资源耗费。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ComputeWeightMatrixResult implements Serializable {
    private static final long serialVersionUID = 1464049633667786322L;
    /**
     * <p>
     * 获取耗费矩阵数组。
     * 数组中每个元素代表矩阵中的一行。矩阵为 n*n 维，n 为 ComputeWeightMatrixParameters.nodes 中结点的个数。
     * </p>
     */
    public double[][] weightMatrix;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ComputeWeightMatrixResult() {
        super();
    }

}
