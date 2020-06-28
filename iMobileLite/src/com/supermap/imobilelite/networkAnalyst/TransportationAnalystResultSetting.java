package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 *  交通网络分析结果参数类。
 *  </p>
 * <p>
 *  通过该类设置交通网络分析的结果参数，包括是否返回弧段、是否返回结点、是否返回行驶导引等
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class TransportationAnalystResultSetting implements Serializable {
    private static final long serialVersionUID = -6806030969340646098L;

    /**
     * <p>
     * 获取或设置分析结果中是否包含弧段要素集合
     * </p>
     */
    public Boolean returnEdgeFeatures;

    /**
     * <p>
     * 获取或设置分析结果的弧段集合中是否包含各弧段的几何信息
     * </p>
     */
    public Boolean returnEdgeGeometry;

    /**
     * <p>
     * 获取或设置分析结果中是否包含途经弧段的ID集合
     * </p>
     */
    public Boolean returnEdgeIDs;

    /**
     * <p>
     * 获取或设置分析结果中是否包含结点要素集合
     * </p>
     */
    public Boolean returnNodeFeatures;

    /**
     * <p>
     * 获取或设置分析结果的结点要素集合中是否包含各结点的几何信息
     * </p>
     */
    public Boolean returnNodeGeometry;

    /**
     * <p>
     * 获取或设置分析结果中是否包含途经结点的ID集合
     * </p>
     */
    public Boolean returnNodeIDs;

    /**
     * <p>
     * 获取或设置分析结果中是否包含行驶导引（PathGuideItem）集合
     * </p>
     */
    public Boolean returnPathGuides;

    /**
     * <p>
     * 获取或设置分析结果中是否包含路由对象（Route）的集合
     * </p>
     */
    public Boolean returnRoutes;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransportationAnalystResultSetting() {
        super();
    }

}
