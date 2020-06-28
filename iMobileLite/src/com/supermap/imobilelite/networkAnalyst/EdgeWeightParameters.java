package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 弧段权值参数类。
 * </p>
 * <p>
 * 通过本类可以设置目标弧段 ID、目标弧段的起始结点 ID、目标弧段的终止结点 ID、目标弧段对应的权值信息的名称，以及指定的最新弧段的权值。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class EdgeWeightParameters implements Serializable {
    private static final long serialVersionUID = 8670396952367165915L;
    /**
     * <p>
     * 目标弧段 ID.
     * </p>
     */
    public int edgeID;
    /**
     * <p>
     * 目标弧段的起始结点 ID.
     * </p>
     */
    public int fromNodeID;
    /**
     * <p>
     * 目标弧段的终止结点 ID.
     * </p>
     */
    public int toNodeID;
    /**
     * <p>
     * 目标弧段对应的权值信息的名称.
     * </p>
     */
    public EdgeWeightFieldType weightField = EdgeWeightFieldType.TIME;
    /**
     * <p>
     * 指定的最新弧段的权值.
     * </p>
     */
    public int weight;

    public EdgeWeightParameters() {
        super();
    }

}
