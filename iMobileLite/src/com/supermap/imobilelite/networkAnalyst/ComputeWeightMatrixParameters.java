package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 耗费矩阵分析参数类。
 * </p>
 * <p> 
 * 该类用于设置耗费矩阵分析（ComputeWeightMatrixService）所需的参数。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ComputeWeightMatrixParameters<T> implements Serializable {
    private static final long serialVersionUID = 3719242066694064727L;

    /**
     * <p>
     * 获取或设置要计算耗费矩阵的点数组，必设字段。   
     * 可以通过两种方式赋予：点ID号集合和点坐标（Point2D类型）集合。 
     * </p>
     */
    public T[] nodes;

    /**
     * <p>
     * 获取或设置交通网络分析通用参数。设置耗费字段、障碍边、障碍点等。
     * </p>
     */
    public TransportationAnalystParameter parameter;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ComputeWeightMatrixParameters() {
        super();
    }
}
