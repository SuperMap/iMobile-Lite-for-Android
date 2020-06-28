package com.supermap.imobilelite.maps.query;

import android.util.Log;

import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.services.rest.util.JsonConverter;

/**
 * <p>
 * 范围查询服务类。
 * </p>
 * <p>
 * 范围查询是指查找包含于指定范围内，以及与指定范围边界相交的所有符合查询条件的地物。<br>
 * 需要注意的是：该范围只能是矩形，不可为多边形。<br>
 * 该类负责将范围查询所需参数（QueryByBoundsParameters）传递至服务端，并获取服务端的返回结果。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryByBoundsService extends QueryService {
    /**
     * <p>
     * 构造函数。使用服务地址 URL 参数实例化 QueryByBoundsService 对象。
     * </p>
     * @param url
     */
    public QueryByBoundsService(String url) {
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new QueryResult();
    }

    @Override
    protected String getVariablesJson(QueryParameters parameters) {
        if (parameters instanceof QueryByBoundsParameters) {
            QueryByBoundsParameters queryParameters = (QueryByBoundsParameters)parameters;
            ServerQueryParameters sqp = new ServerQueryParameters();
            sqp.bounds = queryParameters.bounds;
            sqp.queryMode = QueryMode.BoundsQuery;
            sqp.queryParameters = getQueryParameterSet(parameters);
            String entityJosnStr = JsonConverter.toJson(sqp);
            return entityJosnStr;
        } else {
            Log.w(LOG_TAG, "参数parameters不是QueryByBoundsParameters对象");
            return null;
        }
    }
 
}