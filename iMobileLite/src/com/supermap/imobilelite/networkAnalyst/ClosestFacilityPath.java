package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Path;

/**
 * <p>
 * 最近设施分析结果路径类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ClosestFacilityPath extends Path implements Serializable {
    private static final long serialVersionUID = 6416661640489179348L;
    /**
    * <p>
    * 最近设施点。
    * 当设置最近设施分析参数时，如果指定设施点时使用的是 ID号，则返回结果也为 ID号；如果使用的是坐标值，则返回结果也为坐标值。
    * </p>
    */
    public Object facility;
    /**
     * <p>
     * 该路径所到达的最近设施点在候选设施点序列（FindClosestFacilitiesParameters.facilities）中的索引号。
     * </p>
     */
    public int facilityIndex;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ClosestFacilityPath() {
        super();
    }

}
