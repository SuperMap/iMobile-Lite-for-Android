package com.supermap.imobilelite.maps.query;

/**
 * <p>
 * 查询类型。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum QueryMode {
    /**
     * <p>
     * 范围查询。查询指定范围内的几何对象。
     * </p>
     */
    BoundsQuery,

    /**
     * <p>
     * 空间查询。查询与指定的多边形符合某种空间关系和查询条件的几何对象。
     * </p>
     */
    SpatialQuery,
    /**
     * <p>
     * 距离查询。查找距离指定的点一定范围内的几何对象。
     * </p>
     */
    DistanceQuery,

    /**
     * <p>
     * 最近地物查询。查找距离指定几何对象一定距离内最近的几何对象。从多个数据集查找时，也只返回所有图层中最近的期望数量的地物。
     * </p>
     */
    FindNearest,
    /**
     * <p>
     * SQL 查询。根据 SQL 查询条件查找满足条件的记录。
     * </p>
     */
    SqlQuery
}
