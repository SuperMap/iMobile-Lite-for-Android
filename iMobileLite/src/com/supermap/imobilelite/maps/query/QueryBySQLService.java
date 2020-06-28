package com.supermap.imobilelite.maps.query;

import android.util.Log;

import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.services.rest.util.JsonConverter;

/**
 * <p>
 * SQL 查询服务类。
 * </p>
 * <p>
 * SQL 查询是指在一个或多个指定的图层上查询符合 SQL 条件的空间地物信息。其中 SQL 条件通过 QueryBySQLParameters.filterParameters 属性设置。该类负责将 SQL 查询所需参数（QueryBySQLParameters）传递至服务端，并获取服务端的返回结果。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryBySQLService extends QueryService {
    /**
     * <p>
     * 构造函数。使用服地址 URL 参数实例化 QueryBySQlService 对象。
     * </p>
     * @param url
     */
    public QueryBySQLService(String url) {
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new QueryResult();
    }

    @Override
    protected String getVariablesJson(QueryParameters parameters) {
        if (parameters instanceof QueryBySQLParameters) {
            ServerQueryParameters sqp = new ServerQueryParameters();
            sqp.queryMode = QueryMode.SqlQuery;
            sqp.queryParameters = getQueryParameterSet(parameters);
            String entityJosnStr = JsonConverter.toJson(sqp);
            return entityJosnStr;
        } else {
            Log.w(LOG_TAG, "参数parameters不是QueryBySQLParameters对象");
            return null;
        }
    }

}