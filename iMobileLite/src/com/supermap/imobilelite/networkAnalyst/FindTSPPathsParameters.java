package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 旅行商分析参数类。
 * </p>
 * <p>
 * 该类用于设置旅行商分析所需的参数，包括分析站点、交通网络通用参数（TransportationAnalystParameter）等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindTSPPathsParameters<T> implements Serializable {
    private static final long serialVersionUID = -9209838432012522353L;

    /**
     * <p>
     * 获取或设置是否指定终止点，默认为false
     * </p>
     */
    public Boolean endNodeAssigned;

    /**
     * <p>
     * 获取或设置旅行商分析途经站点数组，必设字段
     * </p>
     */
    public T[] nodes;

    /**
     * <p>
     * 获取或设置交通网络分析通用参数。用于设置障碍边、障碍点、权重字段、转向权重字段、分析结果内容等信息。
     * </p>
     */
    public TransportationAnalystParameter parameter;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindTSPPathsParameters() {
        super();
    }

}
