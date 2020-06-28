package com.supermap.imobilelite.data;

import com.supermap.services.components.commontypes.QueryParameter;

/**
 * <p>
 * 数据集 SQL查询参数类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFeaturesBySQLParameters extends GetFeaturesParametersBase {
    private static final long serialVersionUID = -3765015976693190369L;

    /**
     * <p>
     * SQL查询过滤条件，QueryParameter对象。必设参数。
     * 若设置了 datasetNames属性,则queryParameter中的name属性设置无效。 
     * </p>
     */
    public QueryParameter queryParameter;

    /**
     * <p>
     * 设置服务端返回查询结果条目的数量，默认为1000。
     * </p>
     */
    public int maxFeatures = 1000;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GetFeaturesBySQLParameters() {
        super();
    }
}
