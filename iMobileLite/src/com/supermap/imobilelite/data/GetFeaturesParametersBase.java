package com.supermap.imobilelite.data;

import java.io.Serializable;

/**
 * <p>
 * 数据集查询参数基类。
 * </p>
 * <p>
 * 缓冲区查询、几何查询、ID 查询、SQL查询参数类均继承于该类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFeaturesParametersBase implements Serializable {
    private static final long serialVersionUID = 9167701401434535351L;

    /**
     * <p>
     * 数据集名称数组，必设参数。对于数据集 SQL 查询（GetFeaturesBySQLService），若在查询参数类 GetFeaturesBySQLParameters 中设置了 datasetNames属性，GetFeaturesBySQLParameters::queryParameter中的 name 属性无效。 
     * 数据集名称由数据源名和数据集名构成，例如 world 数据源下的 Ocean 数据集，这里的数据集名称就为 "world:Ocean"，注意不要漏掉双引号。
     * </p>
     */
    public String[] datasetNames;

    /**
     * <p>
     * 获取或设置返回对象的起始索引值。默认值为 0，表示从第一个对象开始返回。
     * 例如有 10 个对象符合查询条件，若该属性值为 2，则返回索引值属于 [2, toIndex] 的对象。
     * </p>
     */
    public int fromIndex;

    /**
     * <p>
     * 获取或设置返回对象的终止索引值。默认值为 -1，表示返回全部对象。
     * 例如有 10 个对象符合查询条件，若该属性值为 5，则返回索引值属于 [fromIndex, 5] 的对象。
     * </p>
     */
    public int toIndex = -1;

    /**
     * <p>
     * 是否立即返回新创建资源的表述还是返回新资源的URI。 
     * 如果为 true，则直接返回新创建资源，即查询结果的表述。 如果为 false，则返回的是查询结果资源的 URI。默认为 true。
     * </p>
     */
    public boolean returnContent = true;

    public GetFeaturesParametersBase() {
        super();
    }
}
