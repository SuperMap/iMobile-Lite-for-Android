package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 乘车方案查询参数类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class TransferSolutionParameters<T> implements Serializable {
    private static final long serialVersionUID = -6576154290631798544L;
    /**
     * <p>
     * 交通换乘策略类型，包括时间最短、距离最短、最少换乘、最少步行四种选择。
     * </p>
     */
    public TransferTactic transferTactic = TransferTactic.LESS_TIME;
    /**
     * <p>
     * 乘车偏好类型，包括公交汽车优先、无乘车偏好、不乘地铁、地铁优先四种类型。
     * </p>
     */
    public TransferPreference transferPreference = TransferPreference.NONE;
    /**
     * <p>
     * 步行与公交的权重比，默认值为10.此值越大，则步行因素对于方案选择的影响越大。
     * </p>
     */
    public double walkingRatio = 10;
    /**
     * <p>
     * 乘车方案的数量，默认为6。
     * </p>
     */
    public int solutionCount = 5;
    /**
     * <p>
     * 起止点的ID或坐标。
     * 两种查询方式：1、按照公交站点的起点ID进行查询，则points参数的类型为Integer[],形如：[起点ID、终点ID]，公交站点的ID对应服务提供者配置中的站点ID字段；2、按照起止点的坐标进行查询，则points参数的类型为Point2D[],形如：[{"x":44,"y":39},{"x":45,"y":40}]
     * </p>
     */
    public T[] points;
    /**
     * 避让线路的线路 ID 
     */
    public long[] evadeLines;
    /**
     * 避让站点的站点 ID 
     */
    public long[] evadeStops;
    /**
     * 优先线路的线路 ID 
     * 
     */
    public long[] priorLines;
    /**
     *  优先站点的站点 ID 
     */
    public long[] priorStops;
    
    /**
     * 出行时间，如:早上八点半--"8:30"。设置了该参数，分析时，会考虑线路的首末班车时间的限制。过滤掉运行时间不包含出行时间的线路。
     */
    public String travelTime;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferSolutionParameters() {
        super();
    }

}
