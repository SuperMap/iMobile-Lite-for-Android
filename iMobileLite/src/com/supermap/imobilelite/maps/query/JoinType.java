package com.supermap.imobilelite.maps.query;

/**
 * <p>
 * 关联查询时的关联类型常量。
 * </p>
 * <p>
 * 该类定义了两个表之间的连接类型常量，决定了对两个表之间进行连接查询时，查询结果中得到的记录的情况。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum JoinType {
    
    /**
     * <p>
     * 内连接。只有两个表中都有相关的记录才加入查询结果集。
     * </p>
     */
    INNERJOIN,

    /**
     * <p>
     * 左连接。左边表中所有相关记录进入查询结果集，右边表中无相关的记录字段显示为空。
     * </p>
     */
    LEFTJOIN
}