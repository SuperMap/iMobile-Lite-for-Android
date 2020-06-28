package com.supermap.imobilelite.maps.query;

import com.supermap.services.components.commontypes.Rectangle2D;

/**
 * <p>
 * 范围查询参数类. 该类用于设置范围查询时的相关查询参数。
 * </p>
 * <p>
 * 范围查询是指查找包含于指定范围内，以及与指定范围边界相交的所有符合查询条件的地物。<br>
 * 需要注意的是：该范围只能是矩形，不可为多边形。<br>
 * 必设属性有：FilterParameter（继承于 QueryParameters）、bounds。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryByBoundsParameters extends QueryParameters {
    private static final long serialVersionUID = 1760886719104917979L;
    /**
     * <p>
     * 查询范围（Rectangle2D 类型），必设属性。
     * </p>
     */
    public Rectangle2D bounds;
    public QueryByBoundsParameters() {

    }
    
}