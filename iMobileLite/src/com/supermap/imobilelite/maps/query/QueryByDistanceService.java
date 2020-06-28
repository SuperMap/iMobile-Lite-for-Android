package com.supermap.imobilelite.maps.query;

import android.util.Log;

import com.supermap.imobilelite.commons.utils.ServicesUtil;
import com.supermap.services.rest.util.JsonConverter;

/**
 * <p>
 * 距离查询服务类。
 * </p>
 * <p>
 * 距离查询就是查询距离几何对象一定范围内符合指定条件的地物。对于点几何对象，则查询以该点为圆心，距离为半径的圆内地物；对于线和面几何对象，则查询距离对像边界上一定范围内的地物。<br>
 * 该类负责将距离查询所需参数（QueryByDistanceParameters）传递至服务端，并获取服务端的返回结果。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryByDistanceService extends QueryService {
    /**
     * <p>
     * 构造函数。使用服务器的 URL 参数实例化 QueryByDistanceService 对象。
     * </p>
     * @param url
     */
    public QueryByDistanceService(String url) {
        baseUrl = ServicesUtil.getFormatUrl(url);
        lastResult = new QueryResult();
    }

    @Override
    protected String getVariablesJson(QueryParameters parameters) {
        if (parameters instanceof QueryByDistanceParameters) {
            QueryByDistanceParameters queryParameters = (QueryByDistanceParameters) parameters;
            ServerQueryParameters sqp = new ServerQueryParameters();
            sqp.distance = queryParameters.distance;
            sqp.geometry = queryParameters.geometry;
            if (queryParameters.isNearest) {
                sqp.queryMode = QueryMode.FindNearest;
            } else {
                sqp.queryMode = QueryMode.DistanceQuery;
            }
            sqp.queryParameters = getQueryParameterSet(parameters);
            String entityJosnStr = JsonConverter.toJson(sqp);
            return entityJosnStr;
        } else {
            Log.w(LOG_TAG, "参数parameters不是QueryByDistanceParameters对象");
            return null;
        }
    }

}
