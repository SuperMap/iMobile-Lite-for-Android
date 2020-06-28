package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 最近设施分析参数类。
 * </p>
 * <p>
 * 该类用于设置最近设施分析（FindClosestFacilitiesService）所需的参数。如：事件点、设施点、交通网络通用参数、分析半径等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindClosestFacilitiesParameters<T> implements Serializable {
    private static final long serialVersionUID = 7158742115206140428L;

    /**
     * <p>
     * 获取或设置事件点，一般为需要获得服务的事件位置，必设字段。
     * 可以通过两种方式赋予：事件点 ID号和事件点坐标（Point2D类型）。
     * </p>
     */
    public T event;

    /**
     * <p>
     * 获取或设置期望返回的最近设施个数。默认值为 1。
     * </p>
     */
    public int expectFacilityCount = 1;

    /**
     * <p>
     * 获取或设置设施点集合，一般为提供服务的服务设施位置，必设字段。
     * 可以通过两种方式赋予：设施施点ID号集合和设施点坐标（Point2D类型）集合。
     * </p>
     */
    public T[] facilities;

    /**
     * <p>
     * 获取或设置是否从事件点到设施点进行查找，默认值为 false。
     * 最近设施分析主要是通过设施点和事件点之间最优的路线来分析在一定范围内哪个或哪些设施与事件点有最优路线的关系。这个行走线路是通过网络图层进行网络分析算法计算出来的两点间的最优路线。由于存在从 A 点到 B 点与从 B 点到 A 点的耗费不一样的情况，因此起止点不同可能会得到不同的最优路线。因此在进行最近设施分析之前，需要设置获取的最优路线的方向，即是以事件点作为起点到最近设施点的方向分析，还是以最近设施点为起点到事件点的方向分析。如果需要以事件点作为起点到设施点方向进行查找，设置该字段值为 true；默认为 false，表示从设施点到事件点进行查找。
     * </p>
     */
    public boolean fromEvent = false;

    /**
     * <p>
     * 获取或设置查找半径。单位与该类中 parameter 字段（交通网络分析通用参数）中设置的权重字段一致。默认值为0，表示查找全网络。
     * 例如事件发生点是一起交通事故，要求查找在10分钟内能到达的最近医院，超过10分钟能到达的都不予考虑。那么需要将属中 parameter 的 weightFieldName 设置为表示时间的权重字段，然后设置查找范围的半径值为 10。
     * </p>
     */
    public double maxWeight = 0;

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
    public FindClosestFacilitiesParameters() {
        super();
    }
}
