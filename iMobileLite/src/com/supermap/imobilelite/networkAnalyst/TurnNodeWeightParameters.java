package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 更新转向结点权值参数类。
 * 通过本类可以设置目标结点 ID、目标转向结点的起始弧段  ID、目标转向结点的终止弧段 ID、转向权值字段的名称，以及指定的最新转向结点的权值。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class TurnNodeWeightParameters implements Serializable {
    private static final long serialVersionUID = -962497173962834933L;
    /**
     * <p>
     * 目标结点 ID.
     * </p>
     */
    public int nodeID;
    /**
     * <p>
     * 目标转向结点的起始弧段 ID.
     * </p>
     */
    public int fromEdgeID;
    /**
     * <p>
     * 目标转向结点的终止弧段 ID.
     * </p>
     */
    public int toEdgeID;
    /**
     * <p>
     * 转向权值字段的名称.
     * </p>
     */
    public TurnNodeWeightFieldType weightField = TurnNodeWeightFieldType.TURNCOST;
    /**
     * <p>
     * 指定的最新转向结点的权值.
     * </p>
     */
    public int weight;

    public TurnNodeWeightParameters() {
        super();
    }
}
