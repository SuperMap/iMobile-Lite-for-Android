package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 多旅行商分析参数类。
 * </p>
 * <p>
 * 该类用于设置多旅行商分析（FindMTSPPathsService）所需的参数，包括：配送中心点集合、配送目标点集合、配送模式、交通网络通用参数等信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindMTSPPathsParameters<T> implements Serializable {
    private static final long serialVersionUID = -7870317212617476244L;

    /**
     * <p>
     * 获取或设置配送中心集合，必设属性。
     * 可以通过两种方式赋予：配送中心点ID号集合和配送中心点坐标（Point2D类型）集合。
     * </p>
     */
    public T[] centers;

    /**
     * <p>
     * 获取或设置配送模式是否为总耗费最小方案。默认为 false。
     * 若为 true，则按照总花费最小的模式进行配送，此时可能会出现某几个配送中心点配送的花费较多而其他配送中心点的花费很少的情况。若为 false，则为局部最优，此方案会控制每个配送中心点的花费，使各个中心点花费相对平均，此时总花费不一定最小。
     * </p>
     */
    public boolean hasLeastTotalCost = false;

    /**
     * <p>
     * 获取或设置配送目标点集合，必设属性。
     * 可以通过两种方式赋予：配送目标点ID号集合和配送目标点坐标（Point2D类型）集合。
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
    public FindMTSPPathsParameters() {
        super();
    }

}
