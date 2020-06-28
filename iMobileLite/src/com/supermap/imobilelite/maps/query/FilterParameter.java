package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * 下面的代码显示如何构建一个图层的普通查询参数对象，对应数据信息在范例数据世界地图中：
 * </p>
 * <p><blockquote><pre>
 * QueryParameter queryParam = new QueryParameter();
 * // 查询所有人口大于1000万的首都
 * // 指定要查询的图层名称
 * queryParam.name = "Capitals@World";
 * // 对图层要素进行sql查询条件设置
 * queryParam.attributeFilter = "Cap_Pop > 10000000";
 * // 设置返回字段信息
 * queryParam.fields = new String[]{"Cap_pop"};
 * // 根据下面的字段进行分组
 * // queryParam.groupBy = "SMID";
 * // 根据SMID字段进行匹配查询,可以和attributeFilter进行组合使用
 * // queryParam.ids = new int[]{1,2,3,4};
 * // 根据SMID字段进行排序
 * // queryParam.orderBy = "SMID";
 * queryParameterSet.queryParams[0] = queryParam;
 * </pre></blockquote>
 * <p>
 * 下面的代码显示如何构建一个关联外表的查询参数对象，对应数据信息在范例数据世界地图中：
 * </p>
 * <p><blockquote><pre>
 * QueryParameter queryParam = new QueryParameter();
 * // 指定要查询的图层名称
 * queryParam.name = "Capitals@World";
 * // 设置返回字段信息,以下设置为关联外表的字段
 * queryParam.fields = new String[]{"Pop_1994"};
 * 
 * // 关联项设置
 * JoinItem joinItem = new JoinItem();
 * // 关联外表表名
 * joinItem.foreignTableName = "Countries";
 * // 关联查询语句
 * joinItem.joinFilter = "Capitals.Country=Countries.Country";
 * // 关联类型
 * joinItem.joinType = JoinType.INNERJOIN;
 * queryParam.joinItems = new JoinItem[]{joinItem};
 * </pre></blockquote>
 * @see QueryParameterSet
 * @see DatasetInfo
 * 
 * @author ${Author}
 * @version ${Version}
 */
/**
 * <p>
 * 过滤条件参数类。
 * </p>
 * <p>
 * 该类用于设置查询服务（queryServices）或空间分析服务（spatialAnalystServices）中所使用到的过滤参数。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FilterParameter implements Serializable {
    private static final long serialVersionUID = 4585376175796402214L;
    /**
     * <p>
     * 数据集名称，或者图层名称，根据实际的功能而定。
     * </p>
     * 
     * <p>
     * 一般情况下该字段为数据集名称，但在进行与地图相关功能的操作时，需要设置为图层名称（图层名称格式：数据集名称@数据源别名）。
     * 因为一个地图的图层可能是来自于不同数据源的数据集，而不同的数据源中可能存在同名的数据集，使用数据集名称不能唯一的确定数据集， 所以在进行与地图相关功能的操作时，该值需要设置为图层名称。
     * </p>
     * <p>
     * 暂不支持对 CAD 数据集和 CAD 图层进行查询。
     * </p>
     */
    public String name;

    /**
     * <p>
     * 关联查询项数组。进行 SQL 查询时有效。
     * </p>
     */
    public JoinItem[] joinItems;

    /**
     * <p>
     * 与外部表的关联信息。 进行 SQL 查询时有效。
     * </p>
     */
    public LinkItem[] linkItems;

    /**
     * <p>
     * 获取或设置查询 id 数组，即属性表中的 SmID 值。
     * </p>
     */
    public int[] ids;

    /**
     * <p>
     * 属性过滤条件。
     * </p>
     * 
     * <p>
     * SQL 语句中的 WHERE 子句的格式为：WHERE &lt;条件表达式&gt;，attributeFilter 就是其中的“条件表达式”。
     * </p>
     * 
     * <p>
     * 该字段的用法为 attributeFilter = "过滤条件"。
     * </p>
     * 
     * <p>
     * 例如，要查询字段 fieldValue 小于100的记录，设置 attributeFilter = "fieldValue &lt; 100"； 要查询字段值为“酒店”的记录，设置 attributeFilter = "name like '%酒店%'"，等等。
     * </p>
     */
    public String attributeFilter;

    /**
     * <p>
     * SQL 查询和空间查询中结果排序的字段。用于排序的字段必须为数值型。
     * </p>
     * 
     * <p>
     * SQL 语句中的 ORDER BY 子句的格式为：ORDER BY &lt;列名&gt;，列名即属性表中每一列的名称，列又可称为属性， 在 SuperMap 中又称为字段，orderBy 设置为字段名。
     * </p>
     * 
     * <p>
     * 对单个字段排序时，该字段的用法为 orderBy = "字段名"； 对多个字段排序时，字段之间以英文逗号进行分割，用法为 orderBy = "字段名1, 字段名2"。
     * </p>
     * 
     * <p>
     * 例如，在一个国家数据集中，有两个字段，字段名分别为“SmArea”和“pop_1994”，分别表示国家的面积和1994年的各国的人口数量，如果要按照各国人口数量对记录进行排序，可以设置 orderBy = "pop_1994"；如果要以面积和人口进行排序， 设置 orderBy = "SmArea, pop_1994"。
     * </p>
     * 
     * <p>
     * 进行 SQL 查询及空间查询时有效。
     * </p>
     * 
     */
    public String orderBy;

    /**
     * <p>
     * SQL 查询中结果分组条件的字段。
     * </p>
     * 
     * <p>
     * SQL 语句中的 GROUP BY 子句的格式为：GROUP BY &lt;列名&gt;，列名即属性表中每一列的名称，列又可称为属性， 在 SuperMap 中又称为字段，groupBy 设置为字段名。
     * </p>
     * 
     * <p>
     * 对单个字段分组时，该字段的用法为 groupBy = "字段名"； 对多个字段分组时，字段之间以英文逗号进行分割，用法为 groupBy = "字段名1, 字段名2"。
     * </p>
     * 
     * <p>
     * 例如，有一个全球城市数据集，该数据集有两个字段，字段名分别为“Continent”和“Country”，分别表示某个城市所属的洲和国家， 如果要按照国家对全球的城市进行分组，可以设置 groupBy = "Country"； 如果以洲和国家对城市进行分组，设置 groupBy = "Continent, Country"。
     * </p>
     * 
     * <p>
     * 进行 SQL 查询时有效。注意：空间查询不支持 groupBy字段，否则可能导致空间查询的结果不正确。
     * </p>
     */
    public String groupBy;

    /**
     * <p>
     * 获取或设置查询结果的字段数组，如果不设置则使用系统返回的所有字段。
     * </p>
     */
    public String[] fields;

    public FilterParameter() {

    }

    public FilterParameter(FilterParameter queryParam) {
        if (queryParam == null) {
            return;
        }
        this.name = queryParam.name;
        if (queryParam.joinItems != null) {
            this.joinItems = new JoinItem[queryParam.joinItems.length];
            for (int i = 0; i < queryParam.joinItems.length; i++) {
                if (queryParam.joinItems[i] != null) {
                    this.joinItems[i] = new JoinItem(queryParam.joinItems[i]);
                }
            }
        }
        if (queryParam.linkItems != null) {
            this.linkItems = new LinkItem[queryParam.linkItems.length];
            for (int i = 0; i < queryParam.linkItems.length; i++) {
                if (queryParam.linkItems[i] != null) {
                    this.linkItems[i] = new LinkItem(queryParam.linkItems[i]);
                }
            }
        }
        if (queryParam.fields != null) {
            this.fields = queryParam.fields.clone();
        }
        if (queryParam.ids != null) {
            this.ids = queryParam.ids.clone();
        }
        this.groupBy = queryParam.groupBy;
        this.orderBy = queryParam.orderBy;
        this.attributeFilter = queryParam.attributeFilter;
    }

    public FilterParameter(String name) {
        this.name = name;
    }

    public FilterParameter(String name, String attributeFilter) {
        this.name = name;
        if (attributeFilter != null) {
            this.attributeFilter = attributeFilter;
        }
    }

    /**
     * <p>
     * 获取 {@link QueryParameter} 对象的哈希码。
     * </p>
     * 
     * @return 查询参数对象的哈希码值。
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(83, 85).append(name).append(joinItems).append(linkItems).append(ids).append(attributeFilter).append(orderBy).append(groupBy)
                .append(fields).toHashCode();
    }

    /**
     * <p>
     * 比较指定对象与当前 对象是否相等。
     * </p>
     * 
     * @param obj 与当前 {@link QueryParameter} 进行比较的对象。
     * @return 如果指定对象跟此查询参数对象相等，则返回 true，否则，返回 false。
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FilterParameter)) {
            return false;
        }
        FilterParameter rhs = (FilterParameter) obj;
        return new EqualsBuilder().append(name, rhs.name).append(joinItems, rhs.joinItems).append(linkItems, rhs.linkItems).append(ids, rhs.ids)
                .append(attributeFilter, rhs.attributeFilter).append(orderBy, rhs.orderBy).append(groupBy, rhs.groupBy).append(fields, rhs.fields).isEquals();
    }
}
