package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 几何对象缓冲区分析参数类。
 * </p>
 * <p>
 * 几何对象缓冲区分析是指对指定的某个几何对象做缓冲区分析。通过该类可以指定要做缓冲区分析的几何对象、缓冲区参数等。
 * 缓冲区分析是 GIS 中基本的空间分析，缓冲区实际上是在基本空间要素周围建立的具有一定宽度的邻近区域。缓冲区分析可以有以下应用，比如确定街道拓宽的范围，确定放射源影响的范围等等。
 * 对于面对象而言，在做缓冲区分析前最好先经过拓扑检查，排除面内相交的情况，所谓面内相交，指的是面对象自身相交，如图所示，图中数字代表面对象的节点顺序。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GeometryBufferAnalystParameters extends BufferAnalystParameters {
    private static final long serialVersionUID = -7098085010023998547L;
    /**
     * <p>
     * 要做缓冲区分析的几何对象。必设字段。 
     * </p>
     */
    public Geometry sourceGeometry;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GeometryBufferAnalystParameters() {
        super();
    }
}
