package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 最佳路径分析参数类。
 * </p>
 * <p>
 * 该类用于设置进行最佳路径分析时所需的参数，如路径途经的结点、分析结果类型等信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindPathParameters<T> implements Serializable {
    private static final long serialVersionUID = 4795769281234050126L;

    /**
     * <p>
     * 获取或设置是否返回弧段数最少的路径。默认为 false。
     * true 表示结果路径由最少弧段组成。由于弧段数少并不代表弧段阻力最小，所以此时查出的结果可能不是最佳路径。
     * </p>
     */
    public boolean hasLeastEdgeCount = false;

    /**
     * <p>
     * 获取或设置进行最佳路径分析的结点，必设字段。
     * 可以通过两种方式赋予：分析结点ID号集合和分析结点坐标（Point2D类型）集合。
     * </p>
     */
    public T[] nodes;

    /**
     * <p>
     * 获取或设置交通网络分析通用参数。用于设置障碍边、障碍点、权重字段、转向权重字段、分析结果内容等信息。 
     * </p>
     */
    public TransportationAnalystParameter parameter;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindPathParameters() {
        super();
    }

}
