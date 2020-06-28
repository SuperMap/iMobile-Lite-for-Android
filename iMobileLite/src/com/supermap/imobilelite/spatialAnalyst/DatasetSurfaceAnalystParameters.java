package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.QueryParameter;

/**
 * <p>
 * 数据集表面分析参数类。
 * </p>
 * <p>
 * 该类对数据集等值线/面提取的表面分析所用到的参数进行设置。
 * 从点数据集中提取等值面的实现原理是先对点数据集进行插值分析，得到栅格数据集（方法实现的中间结果），接着从栅格数据集中提取等值线，最终由等值线构成等值面。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class DatasetSurfaceAnalystParameters extends SurfaceAnalystParameters {
    private static final long serialVersionUID = -7423393803042943636L;

    /**
     * <p>
     * 获取或设置数据集的标识。该标识形如"数据集名称@数据源别名"，例如：Country@World。必设属性。
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 获取或设置对待插值点进行筛选的过滤参数——QueryParameter对象.
     * </p>
     */
    public QueryParameter filterQueryParameter;

    /**
     * <p>
     * 获取或设置用于提取等值线/面的字段名称。
     * </p>
     */
    public String zValueFieldName;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public DatasetSurfaceAnalystParameters() {
        super();
    }

}
