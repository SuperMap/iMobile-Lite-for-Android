package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 几何对象叠加分析参数类。
 * </p>
 * <p>
 * 几何对象叠加分析是指对指定的某两个几何对象做叠加分析。通过该类可以指定要做叠加分析的几何对象、叠加操作类型（继承于 OverlayAnalystParameters 类）。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GeometryOverlayAnalystParameters extends OverlayAnalystParameters {
    private static final long serialVersionUID = -2755520872602914631L;

    /**
     * <p>
     * 叠加分析的操作几何对象。必设字段。 
     * </p>
     */
    public Geometry operateGeometry;

    /**
     * <p>
     * 叠加分析的源（被操作）几何对象。必设字段。 
     * </p>
     */
    public Geometry sourceGeometry;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GeometryOverlayAnalystParameters() {
        super();
    }
}
