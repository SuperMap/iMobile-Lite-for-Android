package com.supermap.imobilelite.maps.query;

/**
 * <p>
 * 海图查询模式类型枚举类。
 * </p>
 * <p>
 * SuperMap iClient for Android对海图支持两种查询方式：海图属性查询和海图范围查询。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum ChartQueryMode {

    /**
     * <p>
     * 海图属性查询
     * </p>
     */
    ChartAttributeQuery, // "ChartAttributeQuery";

    /**
     * <p>
     * 海图bounds查询
     * </p>
     */
    ChartBoundsQuery // "ChartBoundsQuery";
}
