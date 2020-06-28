package com.supermap.imobilelite.data;

import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.SpatialQueryMode;

/**
 * <p>
 * 数据集几何查询参数类。
 * </p>
 * <p>
 * 几何查询就是查询与指定几何对象符合一定空间关系的矢量要素。该类主要用于设置对数据集进行几何查询的参数，包括几何对象、空间关系、要查询的数据集集合等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFeaturesByGeometryParameters extends GetFeaturesParametersBase {
    private static final long serialVersionUID = -7957653204339117732L;

    /**
    * <p>
    * 几何查询属性过滤条件，相当于 SQL 语句中的 WHERE 子句。
    * 其格式为：WHERE 条件表达式，attributeFilter 就是其中的“条件表达式”。该字段的用法为 attributeFilter = "过滤条件"。
    * 例如，要查询字段 fieldValue 小于100的记录，设置 attributeFilter = "fieldValue &lt; 100"；要查询字段 name 的值为“酒店”的记录，设置 attributeFilter = "name like '%酒店%'"，等等。
    * 用户在设置该字段时，仅需要输入“条件表达式”即可，如：fieldValue &lt; 100。
    * 若不设置该属性则返回与指定几何对象符合某种空间关系的所有要素。
    * </p>
    */
    public String attributeFilter;

    /**
     * <p>
     * 设置结果返回字段。
     * 当指定了返回结果字段后，则 GetFeaturesResult 中的 features 的属性字段只包含所指定的字段。不设置即返回全部字段。
     * </p>
     */
    public String[] fields;

    /**
     * <p>
     * 用于几何查询的几何对象，必设参数。
     * </p>
     */
    public Geometry geometry;

    /**
     * <p>
     * 空间查询模式常量，必设参数。 默认为CONTAIN。
     * </p>
     */
    public SpatialQueryMode spatialQueryMode = SpatialQueryMode.CONTAIN;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GetFeaturesByGeometryParameters() {
        super();
    }
}
