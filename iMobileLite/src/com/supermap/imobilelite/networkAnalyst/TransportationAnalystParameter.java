package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

import com.supermap.imobilelite.maps.Point2D;

/**
 * <p>
 * 交通网络分析通用参数。
 * </p>
 * <p>
 * 该类主要用来提供交通网络分析所需的通用参数。通过本类可以设置障碍边、障碍点、权值字段信息的名称标识、转向权值字段信息，以及分析结果参数
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class TransportationAnalystParameter implements Serializable {
    private static final long serialVersionUID = -2653622062098528613L;

    /**
     * <p>
     * 障碍弧段 ID 列表。弧段设置为障碍边之后，表示双向都不通。
     * </p>
     */
    public int[] barrierEdgeIDs;

    /**
     * <p>
     * 障碍点的ID列表。结点设置为障碍点之后，标识任何方向都不能通过此结点。当各网络分析参数类中的isAnalyzeById属性设置为true时，该属性才生效。
     * </p>
     */
    public int[] barrierNodeIDs;

    /**
     * <p>
     *  障碍坐标数组，以坐标的形式设置障碍。障碍点表示任何方向都不能通过此点。当各网络分析参数类中的isAnalyzeById属性设置为false时，该属性才生效。
     * </p>
     */
    public Point2D[] barrierPoints;

    /**
     * <p>
     * TransportationAnalystResultSetting对象，设置分析结果的返回内容。
     * </p>
     */
    public TransportationAnalystResultSetting resultSetting;

    /**
     * <p>
     * 转向权重字段的名称
     * </p>
     */
    public String turnWeightField;

    /**
     * <p>
     * 权重字段（也称作耗费字段、阻力字段）名称
     * </p>
     */
    public String weightFieldName;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransportationAnalystParameter() {
        super();
    }

}
