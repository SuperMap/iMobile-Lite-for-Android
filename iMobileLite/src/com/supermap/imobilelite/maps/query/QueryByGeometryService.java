package com.supermap.imobilelite.maps.query;

import android.util.Log;

import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.services.rest.util.JsonConverter;

/**
 * <p>
 * 几何查询服务类。
 * </p>
 * <p>
 * 几何查询即查找与指定的几何对象符合查询条件和某种空间查询模式（SpatialQueryMode）的地物。该类负责将距离查询所需参数（QueryByGeometryParameters）传递至服务端，并获取服务端的返回结果。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryByGeometryService extends QueryService {

    /**
     * <p>
     * 构造函数。使用服务地址 URL 参数实例化 QueryByGeometryService 对象。
     * </p>
     * @param url
     */
    public QueryByGeometryService(String url) {
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new QueryResult();
    }

    @Override
    protected String getVariablesJson(QueryParameters parameters) {
        if (parameters instanceof QueryByGeometryParameters) {
            QueryByGeometryParameters queryParameters = (QueryByGeometryParameters)parameters;
            ServerQueryParameters sqp = new ServerQueryParameters();
            sqp.geometry = queryParameters.geometry;
            sqp.spatialQueryMode = queryParameters.spatialQueryMode;
            sqp.queryMode = QueryMode.SpatialQuery;
            sqp.queryParameters = getQueryParameterSet(parameters);
            String entityJosnStr = JsonConverter.toJson(sqp);
            return entityJosnStr;
        } else {
            Log.w(LOG_TAG, "参数parameters不是QueryByGeometryParameters对象");
            return null;
        }
    }

}