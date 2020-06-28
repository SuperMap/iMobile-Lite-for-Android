package com.supermap.imobilelite.maps.query;

import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 几何查询参数类。
 * </p>
 * <p>
 * 该类用于设置几何查询时的相关查询参数。几何查询就是查询与指定几何对象符合某种空间关系和查询条件的地物。<br>
 * 必设属性有：FilterParameters（继承于 QueryParameters）、Geometry 和 SpatialQueryMode。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryByGeometryParameters extends QueryParameters {
    private static final long serialVersionUID = -2006702330328762378L;
    /**
     * <p>
     * 获取或设置空间查询模式（ SpatialQueryMode ），必设属性
     * </p>
     */
    public SpatialQueryMode spatialQueryMode = SpatialQueryMode.INTERSECT;
    /**
     * <p>
     * 获取或设置用于查询的几何对象（点/线/面），必设属性。
     * </p>
     */
    public Geometry geometry;
    public QueryByGeometryParameters() {

    }

}