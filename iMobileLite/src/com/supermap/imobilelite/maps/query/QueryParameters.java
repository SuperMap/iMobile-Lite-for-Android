package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

/**
 * <p>
 * 查询参数基类。
 * </p>
 * <p>
 * 距离查询、SQL 查询、几何查询等各自的参数类都继承该类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QueryParameters implements Serializable {
    private static final long serialVersionUID = 8435782405244787006L;

    /**
     * <p>
     * 自定义参数，供扩展使用。
     * 如果用户输入geometry=null，则返回的查询结果中geometry=null。
     * </p>
     */
    public String customParams;

    /**
     * <p>
     * 查询结果中期望返回的结果记录数，该值大于0，默认返回 1000000 .
     * </p>
     * <p>
     * 当查询条件为距离查询，且查询结果数大于期望返回的结果记录数（expectCount）时，距离查询结果为从查询总记录中随机抽取的expectCount个地物。
     * <p/>
     * <p>
     * 当查询条件为最近地物查找，且查询结果数大于期望返回的结果记录数（expectCount）时，最近地物查找结果为查询总记录中距离中心最近的expectCount个地物，这expectCount个地物按无序排列。
     * <p>
     * ${services_components_commontypes_QueryParameterSet_attribute_expectCount_Description}
     * </p>
     */
    public int expectCount = 1000000;
    /**
     * <p>
     * 网络数据集对应的查询类型，分为点和线两种类型，默认为线几何对象类型，即{@link GeometryType#LINE}。
     * </p>
     */
    public ServerGeometryType networkType = ServerGeometryType.LINE;
    /**
     * <p>
     * 查询结果选项对象，用于指定查询结果中包含的内容。
     * 默认为发返回属性和几何实体，即 {@link QueryOption#ATTRIBUTEANDGEOMETRY}。
     * </p>
     */
    public QueryOption queryOption = QueryOption.ATTRIBUTEANDGEOMETRY;

    /**
     * <p>
     * 获取或设置查询过滤参数（FilterParameter）数组，必设属性。
     * 【注意】距离查询（QueryByDistanceService）、范围查询（QueryByBoundsService）只支持属性条件过滤(attributesFilter)，对于其它条件，
     * 如分组(groupBy)，排序(orderBy)，关联/链接(joinItem/linkItem)等都不支持）。
     * </p>
     */
    public FilterParameter []filterParameters;

    /**
     * <p>
     * 获取或设置查询起始记录位置，默认值为 0。【注意】对于最近地物查找设置该属性无效。
     * </p>
     */
    public int startRecord = 0;

    /**
     * <p>
     * 获取或设置是返回查询结果记录集 recordsets，还是返回查询结果的资源 resourceInfo。默认为 true，表示返回 recordsets.
     * </p>
     */
    public boolean returnContent = true;

    /**
     * <p>
     * 获取或设置资源在服务端保存的时间。默认为 10 （分钟）。
     * </p>
     */
    public int holdTime = 10;

    public QueryParameters() {
    }

}