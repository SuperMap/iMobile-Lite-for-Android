package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.QueryParameter;

/**
 * <p>
 * 数据集缓冲区分析参数类。
 * </p>
 * <p>
 * 对数据集中满足条件的要素做缓冲区分析。通过该类可以指定要做缓冲区分析的数据集、过滤条件等参数。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class DatasetBufferAnalystParameters extends BufferAnalystParameters {
    private static final long serialVersionUID = -5626361080146545268L;

    /**
     * <p>
     * 获取或设置数据集的标识。该标识形如"数据集名称@数据源别名"，例如：Country@World。必设字段。 
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 设置数据集中几何对象的过滤条件，由 QueryParameter 类定义，只有满足此条件的几何对象才参与缓冲区分析。
     * 若该参数为空，则表示对数据集中的所有对象进行缓冲区分析。
     * </p>
     */
    public QueryParameter filterQueryParameter;

    /**
     * <p>
     * 是否保留进行缓冲区分析的对象的字段属性，默认为 true，表示保留。
     * 当 isUnion 字段为 false 时，即合并缓冲区及缓冲对象时，该字段有效。
     * </p>
     */
    public boolean isAttributeRetained = true;

    /**
     * <p>
     * 是否将缓冲区与源记录集中的缓冲对象合并后返回。对于面对象而言，要求源数据集中的面对象不相交。默认为 false，表示不合并。 
     * </p>
     */
    public boolean isUnion;

    /**
     * <p>
     * 结果返回信息类型。DataReturnOption 类型。 
     * </p>
     */
    public DataReturnOption resultSetting;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public DatasetBufferAnalystParameters() {
        super();
    }

}
