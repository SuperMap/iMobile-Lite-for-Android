package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.Rectangle2D;

/**
 * <p>
 * 查询参数类，跟iserver对接的查询参数类（请求体内容的封装类），不公开，内部使用
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
class ServerQueryParameters implements Serializable {
    private static final long serialVersionUID = 84357823244787006L;
    /**
     * <p>
     * 查询的模式，有 SQL 查询、距离查询、空间查询、最近地物查找、范围查询.
     * </p>
     */
    public QueryMode queryMode;
    
    /**
     * <p>
     * 查询参数集合类，不同的查询都需要的参数，共同的参数部分
     * </p>
     */
    public QueryParameterSet queryParameters;
    public Geometry geometry;
    public Rectangle2D bounds;
    public SpatialQueryMode spatialQueryMode;
    public double distance;
    public ServerQueryParameters() {
        queryParameters = new QueryParameterSet();
    }

}
