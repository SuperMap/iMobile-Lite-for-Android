package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

/**
 * <p>
 * 查询参数集合类，不同的查询都需要的参数，共同的参数部分，不公开，内部使用
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
class QueryParameterSet implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * <p>
     * 获取或设置查询过滤参数（queryParams）数组，必设属性。
     * 【注意】距离查询（QueryByDistanceService）、范围查询（QueryByBoundsService）只支持属性条件过滤(attributesFilter)，对于其它条件，
     * 如分组(groupBy)，排序(orderBy)，关联/链接(joinItem/linkItem)等都不支持）。
     * </p>
     */
    public FilterParameter[] queryParams;
    /**
     * <p>
     * 获取或设置查询起始记录位置，默认值为 0。【注意】对于最近地物查找设置该属性无效。
     * </p>
     */
    public int startRecord = 0;
    /**
     * <p>
     * 查询结果中期望返回的结果记录数，该值大于0。
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
     * 自定义参数，供扩展使用。
     * 如果用户输入geometry=null，则返回的查询结果中geometry=null。
     * </p>
     */
    public String customParams;
    /**
     * <p>
     * 查询结果选项对象，用于指定查询结果中包含的内容。
     * 默认为发返回属性和几何实体，即 {@link QueryOption#ATTRIBUTEANDGEOMETRY}。
     * </p>
     */
    public QueryOption queryOption = QueryOption.ATTRIBUTEANDGEOMETRY;
    /**
     * <p>
     * 查询每个要素期望的重采样后返回的二维坐标对的数目，默认值为-1，表示不设置。
     * 实际进行重采样，如果能得到小于该值的结果，则二维坐标对的数目等于该结果；
     * 如果无法得到小于该值的结果，即重采样后的最小值仍大于该值，则二维坐标对的数目等于重采样后的最小值。
     * </p>
     */
    public int resampleExpectCount = -1;
    /**
     * <p>
     * 查询结果中是否返回准确的totalCount
     * 多图层查询时，如果部分图层的查询结果个数达到{@link QueryParameterSet#expectCount}要求后，为计算{@link QueryResult#totalCount}值，会对剩余的其他图层继续进行查询 ，如果该字段设置为false ,则不继续进行查询。
     * 默认为false。
     * </p>
     */
    public boolean ignoreTotalCount = false;

    public QueryParameterSet() {
        // ? FeatureType.LINE
        this.networkType = ServerGeometryType.LINE;
        // 控制在数组长度容限（15000000）内
        this.expectCount = -1;
        this.queryOption = QueryOption.ATTRIBUTEANDGEOMETRY;
    }
}
