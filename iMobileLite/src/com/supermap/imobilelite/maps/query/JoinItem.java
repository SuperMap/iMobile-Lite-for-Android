package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

/**
 * <p>
 * 连接信息类。
 * </p>
 * <p>
 * 用于矢量数据集与外部表的连接。外部表可以为另一个矢量数据集（其中纯属性数据集中没有空间几何信息）所对应的 DBMS 表，也可以是用户自建的业务表。需要注意的是，矢量数据集与外部表必须属于同一数据源。表之间的联系的建立有两种方式，一种是连接（join），一种是关联（link）。连接，实际上是依据相同的字段将一个外部表追加到指定的表；而关联是基于一个相同的字段定义了两个表格之间的联系，但不是实际的追加。用于连接两个表的字段的名称不一定相同，但类型必须一致。当两个表格之间建立了连接，通过对主表进行操作，可以对外部表进行查询，制作专题图以及分析等。当两个表格之间是一对一或多对一的关系时，可以使用 join 连接。当为多对一的关系时，允许指定多个字段之间的关联。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class JoinItem implements Serializable {
    private static final long serialVersionUID = -462779456591466414L;
    /**
     * <p>
     * 获取或设置外部表的名称.
     * </p>
     */
    public String foreignTableName;
    /**
     * <p>
     * 获取或设置矢量数据集与外部表之间的连接表达式.
     * </p>
     */
    public String joinFilter;
    /**
     * <p>
     * 获取或设置两个表之间的连接类型，JoinType 常量.
     * </p>
     */
    public JoinType joinType;

    public JoinItem() {
        this.joinType = JoinType.INNERJOIN;
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param foreignTableName 外部表的名称。
     * @param joinFilter 与外部表之间的连接表达式，即设定两个表之间关联的字段。
     * @param joinType 两个表之间连接的类型。可以是内连接或者是外连接。
     */
    public JoinItem(String foreignTableName, String joinFilter, JoinType joinType) {
        this.foreignTableName = foreignTableName;
        this.joinFilter = joinFilter;
        this.joinType = joinType;
    }

    public JoinItem(String foreignTableName, String joinFilter) {
        this.foreignTableName = foreignTableName;
        this.joinFilter = joinFilter;
        this.joinType = JoinType.INNERJOIN;
    }

    public JoinItem(JoinItem joinItem) {
        if (joinItem == null) {
            return;
        }
        this.foreignTableName = joinItem.foreignTableName;
        this.joinFilter = joinItem.joinFilter;
        this.joinType = joinItem.joinType;
    }
}