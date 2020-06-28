package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * <p>
 * 关联信息类。
 * </p>
 * 
 * <p>
 * 用于矢量数据集与外部表的关联。外部表是另一个数据集（其中纯属性数据集中没有空间几何信息）中的 DBMS 表，矢量数据集与外部表可以属于不同的数据源，但数据源类型目前只支持SQL Server和Oracle类型。使用LinkItem时，空间数据和属性数据必须满足关联条件，即主空间数据集与外部属性表之间存在关联字段。
 * </p>
 * <p>
 * 表之间的联系的建立有两种方式，一种是连接（join），一种是关联（link）。连接的相关设置是通过JoinItem类实现的，关联的相关设置是通过LinkItem类实现的。JoinItem目前支持左连接和内连接，不支持全连接和右连接；LinkItem 只支持左连接。另外，用于建立连接的两个表必须在同一个数据源下，而用于建立关联关系的两个表可以不在同一个数据源下。
 * </p>
 * <p>
 * 下面通过查询的例子来说明连接和关联的区别，假设用来进行查询的表（即主空间数据集中的表），为TableA，被关联或者连接的表为TableB，现通过建立TableA与TableB的连接或关联关系来查询TableA中满足查询条件的记录:
 * </p>
 * <p>
 * 1.连接（join）
 * </p>
 * <p>
 * 设置将TableB连接TableA的连接信息，即建立JoinItem 类并设置其属性，当执行TableA的查询操作时，系统将根据连接条件及查询条件，将满足条件的TableA中的内容与满足条件的TableB中的内容构成一个查询结果表，并这个查询表保存在内存中，需要获取结果时，再从内存中取出相应的内容。
 * </p>
 * <p>
 * 2.关联（link）
 * </p>
 * <p>
 * 设置将TableB关联到TableA的关联信息，即建立LinkItem类并设置其属性，TableA与TableB是通过主表（TableA）的外键（LinkItem类的 ForeignKey 属性）和副表（TableB）的主键（LinkItem类的 PrimaryKey 属性）实现关联的，当执行TableA的查询操作时，系统将根据关联信息中的过滤条件及查询条件，分别查询TableA 与TableB中满足条件的内容，TableA的查询结果与TableB的查询结果分别作为独立的两个结果表保存在内存中，当需要获取结果时，SuperMap将对两个结果进行拼接并返回，因此，进行关联查询时，查询参数中的返回字段一定要有关联条件中的外键，否则无法根据外键的值获取副表中的关联字段值，副表中的字段值将返回 null。在应用层看来，连接和关联操作很相似。
 * </p>
 * <p>
 * 3.LinkItem 只支持左连接，不支持 SDB、UDB 等文件型数据源，只支持 Oracle 和 SQL 数据库型数据源。
 * </p>
 * <p>
 * 4.JoinItem目前支持左连接和内连接，不支持全连接和右连接；
 * </p>
 * <p>
 * 5.使用 LinkItem 的约束条件：空间数据和属性数据必须有关联条件，即主空间数据集与外部属性表之间存在关联字段。主空间数据集：用来与外部表进行关联的数据集。外部属性表：用户通过 Oracle 或者 SQL Server 创建的数据表，或者是另一个矢量数据集所对应的 DBMS 表。
 * </p>
 * @author ${Author}
 * @version ${Version}
 */
// from UGO的描述，SDB、UDB、PostgreSQL 数据源暂不支持 LinkItem。对应：http://jira.com:8090/browse/ISJ-1839
public class LinkItem implements Serializable {
    private static final long serialVersionUID = -2774933500997207312L;

    /**
     * <p>
     * 获取或设置关联的外部数据源信息 DatasourceConnectionInfo 。
     * </p>
     */
    public DatasourceConnectionInfo datasourceConnectionInfo;

    /**
     * <p>
     * 获取或设置主空间数据集的外键。
     * </p>
     */
    public String[] foreignKeys;

    /**
     * <p>
     * 关联的外部属性表的名称，目前仅支持 Supermap 管理的表，即另一个矢量数据集所对应的 DBMS 表。
     * </p>
     */
    public String foreignTable;

    /**
     * <p>
     * 欲保留的外部属性表的字段。
     * </p>
     * 
     * <p>
     * 如果不设置字段或者设置的字段在外部属性表中不存在的话则不返回任何外部属性表的属性信息。如果欲保留的外部表字段与主表字段存在同名，则还需要指定一个不存在字段名作为外部表的字段别名。
     * </p>
     */
    public String[] linkFields;

    /**
     * <p>
     * 与外部属性表的连接查询条件。
     * </p>
     */
    public String linkFilter;

    /**
     * <p>
     * 获取或设置此关联信息对象的名称。
     * </p>
     */
    public String name;

    /**
     * <p>
     * 外部属性表的主键。
     * </p>
     * 
     */
    public String[] primaryKeys;

    public LinkItem() {
    }

    public LinkItem(LinkItem linkItem) {
        if (linkItem != null) {
            this.datasourceConnectionInfo = new DatasourceConnectionInfo(linkItem.datasourceConnectionInfo);
            this.foreignKeys = linkItem.foreignKeys;
            this.foreignTable = linkItem.foreignTable;
            this.linkFields = linkItem.linkFields;
            this.linkFilter = linkItem.linkFilter;
            this.name = linkItem.name;
            this.primaryKeys = linkItem.primaryKeys;
        }
    }

    /**
     * <p>
     * 获取关联信息对象的哈希码。
     * </p>
     * 
     * @return 哈希码值。
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(55, 57).append(datasourceConnectionInfo).append(foreignKeys).append(foreignTable).append(linkFields).append(linkFilter)
                .append(name).append(primaryKeys).toHashCode();
    }

    /**
     * <p>
     * 比较指定对象与当前 {@link LinkItem} 对象是否相等。
     * </p>
     * 
     * @param obj 与当前 {@link LinkItem} 对象进行比较的对象。
     * @return 如果指定对象跟此关联信息对象相等，则返回 true，否则返回 false。
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LinkItem)) {
            return false;
        }
        LinkItem rhs = (LinkItem) obj;
        return new EqualsBuilder().append(datasourceConnectionInfo, rhs.datasourceConnectionInfo).append(foreignKeys, rhs.foreignKeys)
                .append(foreignTable, rhs.foreignTable).append(linkFields, rhs.linkFields).append(linkFilter, rhs.linkFilter).append(name, rhs.name)
                .append(primaryKeys, rhs.primaryKeys).isEquals();
    }
}
