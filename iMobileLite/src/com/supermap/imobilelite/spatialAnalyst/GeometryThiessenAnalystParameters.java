package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Point2D;

/**
 * <p>
 * 几何对象邻近分析参数类。
 * <p>
 * <p>
 * 通过该类可以为几何对象邻近分析服务提供参数信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class GeometryThiessenAnalystParameters extends ThiessenAnalystParameters {
    private static final long serialVersionUID = -7983205846123973831L;
    /**
     * <p>
     * 进行几何对象邻近分析使用的点数组，Point2D类型
     * </p>
     */
    public Point2D[] points;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GeometryThiessenAnalystParameters() {
        super();
    }

}
