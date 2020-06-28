package com.supermap.imobilelite.maps.query;

import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 距离查询参数类。
 * </p>
 * <p>
 * 该类用于设置距离查询时的相关查询参数。必设属性有：FilterParameters 和 Geometry。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryByDistanceParameters extends QueryParameters {
    private static final long serialVersionUID = 1980854846305352417L;
    /**
     * <p>
     * 获取或设置查询距离，单位与所查询图层对应的数据集单位相同。无论 isNearest 属性为 true 或 false 时，该属性均为必设。
     * </p>
     */
    public double distance;
    /**
     * <p>
     * 获取或设置用于查询的几何对象，必设属性。
     * </p>
     */
    public Geometry geometry;
    /**
     * <p>
     * 获取或设置是否为最近距离查询，默认为 false，即根据距离查找地物.
     * </p>
     */
    public boolean isNearest = false;
    public QueryByDistanceParameters() {

    }

}