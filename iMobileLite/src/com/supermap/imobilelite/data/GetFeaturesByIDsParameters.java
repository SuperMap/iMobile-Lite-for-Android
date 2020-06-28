package com.supermap.imobilelite.data;

/**
 * <p>
 * 数据集 ID 查询参数类。
 * </p>
 * <p>
 * 数据集 ID 查询就是在数据集集合中查找与指定 ID 号对应的矢量要素。该类用于设置数据集 ID 查询参数，包括要查询的数据集集合和 ID 号集合。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFeaturesByIDsParameters extends GetFeaturesParametersBase {
    private static final long serialVersionUID = 2811048939771539693L;

    /**
     * <p>
     * 设置结果返回字段。
     * 当指定了返回结果字段后，则 GetFeaturesResult 中的 features 的属性字段只包含所指定的字段。不设置即返回全部字段。
     * </p>
     */
    public String[] fields;

    /**
     * <p>
     * 要查询的矢量要素的 ID 号集合，必设参数。 
     * </p>
     */
    public int[] IDs;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GetFeaturesByIDsParameters() {
        super();
    }
}
