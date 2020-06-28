package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 几何对象叠加分析服务结果数据类。
 * </p>
 * <p>
 * 该类中包含了叠加分析的结果几何对象。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GeometryOverlayAnalystResult extends SpatialAnalystResult {
    private static final long serialVersionUID = -8404887800973942775L;
    /**
     * <p>
     * 叠加分析的结果几何对象。 
     * </p>
     */
    public Geometry resultGeometry;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GeometryOverlayAnalystResult() {
        super();
    }
}
