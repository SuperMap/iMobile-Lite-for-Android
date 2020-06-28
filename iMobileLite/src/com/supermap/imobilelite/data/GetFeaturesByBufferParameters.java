package com.supermap.imobilelite.data;


import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 数据集缓冲区查询参数类。
 * </p>
 * <p>
 * 数据集缓冲区查询就是对指定的几何对象进行一定距离缓冲，从指定数据集集合中查询出与缓冲区区域相交的矢量要素。该类主要用于设置对数据集进行缓冲区查询时所需的参数，包括缓冲区对象、缓冲距离、要查询的数据集集合等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFeaturesByBufferParameters extends GetFeaturesParametersBase {
    private static final long serialVersionUID = 4891793626866374245L;
    /**
     * <p>
     * 缓冲区查询属性过滤条件，相当于 SQL 语句中的 WHERE 子句。
     * 其格式为：WHERE 条件表达式，attributeFilter 就是其中的“条件表达式”。该字段的用法为 attributeFilter = "过滤条件"。
     * 例如，要查询字段 fieldValue 小于100的记录，设置 attributeFilter = "fieldValue &lt; 100"；要查询字段 name 的值为“酒店”的记录，设置 attributeFilter = "name like '%酒店%'"，等等。
     * 用户在设置该字段时，仅需要输入“条件表达式”即可，如：fieldValue &lt; 100。
     * 若不设置该属性则返回与缓冲区相交的所有矢量要素。
     * </p>
     */
    public String attributeFilter;
    /**
     * <p>
     * 缓冲半径，单位与所操作的数据集单位相同，必设参数。 
     * </p>
     */
    public double bufferDistance = 0.0;
    /**
     * <p>
     * 设置结果返回字段。
     * 当指定了返回结果字段后，则 GetFeaturesResult 中的 features 的属性字段只包含所指定的字段。不设置即返回全部字段。
     * </p>
     */
    public String[] fields;
    /**
     * <p>
     * 用于进行缓冲区查询的几何对象，必设属性。
     * </p>
     */
    public Geometry geometry;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GetFeaturesByBufferParameters() {
        super();
    }
}
