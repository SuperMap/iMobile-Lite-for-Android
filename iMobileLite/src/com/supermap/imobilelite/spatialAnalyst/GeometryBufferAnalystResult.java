package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 几何对象缓冲区分析服务结果数据类。
 * </p>
 * <p>
 * 该类中包含了缓冲区分析的结果几何对象。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GeometryBufferAnalystResult extends SpatialAnalystResult {
    private static final long serialVersionUID = -4589688809502899170L;
    /**
     * <p>
     * 缓冲区分析的结果几何对象。 
     * </p>
     */
    public Geometry resultGeometry;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GeometryBufferAnalystResult() {
        super();
    }
}
