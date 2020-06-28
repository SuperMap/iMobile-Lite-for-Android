package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 最近设施分析服务结果类。
 * </p>
 * <p>
 * 该类用于存储最近设施分析结果，即事件点到最近设施点之间的路径。
 * </p>
 * <p>
 * 注意：最近设施分析结果可以有多条路径，这取决于 FindClosestFacilitiesParameters.expectFacilityCount 属性。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindClosestFacilitiesResult implements Serializable {
    private static final long serialVersionUID = 4386182385857973244L;
    /**
     * <p>
     * 获取最近设施分析结果路径集合。
     * 集合中的每个对象即为一条路径（ClosestFacilityPath），对象个数取决于 FindClosestFacilitiesParameters.expectFacilityCount 属性。
     * </p>
     */
    public ClosestFacilityPath[] facilityPathList;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindClosestFacilitiesResult() {
        super();
    }
}
