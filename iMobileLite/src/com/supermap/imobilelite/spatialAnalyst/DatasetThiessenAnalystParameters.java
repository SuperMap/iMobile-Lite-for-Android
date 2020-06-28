package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.QueryParameter;

/**
 * <p>
 * 数据集邻近分析参数类。
 * </p>
 * <p>
 * 通过该类可以为数据集邻近分析提供参数信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class DatasetThiessenAnalystParameters extends ThiessenAnalystParameters {
    private static final long serialVersionUID = -1421658722831307638L;

    /**
     * <p>
     * 待分析的数据集名称，形如"数据集名称@数据源别名"。例如：Country@World
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 对待分析数据集中的点进行过滤，不设置时默认为null，即对数据集中的所有点进行分析
     * </p>
     */
    public QueryParameter filterQueryParameter;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public DatasetThiessenAnalystParameters() {
        super();
    }

}
