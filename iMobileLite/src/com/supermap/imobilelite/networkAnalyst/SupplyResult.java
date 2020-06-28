package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 选址分区的资源供给中心对象。
 * </p>
 * <p>
 * 该类对应于选址分区分析中的资源提供者（DemandResult为资源需求者），其中包括资源供给中心的类型、ID、最大阻值、资源量等信息
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class SupplyResult implements Serializable {
    private static final long serialVersionUID = 5917201992234151544L;

    /**
     * <p>
     * 资源供给中心实际提供的资源量。
     * </p>
     */
    public double actualResourceValue;

    /**
     * <p>
     * 从本资源供给中心到每个需求点的平均耗费（阻值）。
     * </p>
     */
    public double averageWeight;

    /**
     * <p>
     * 所服务的需求点（弧段）的数量。
     * </p>
     */
    public int demandCount;

    /**
     * <p>
     * 各个需求对象到资源供给中心的最大耗费（阻值）。
     * </p>
     */
    public double maxWeight;

    /**
     * <p>
     * 资源供给中心点的结点ID。
     * </p>
     */
    public int nodeID;

    /**
     * <p>
     * 资源供给中心的资源量。
     * </p>
     */
    public double resourceValue;

    /**
     * <p>
     * 从本资源供给中心到所有需求点的总耗费（阻值）。
     * </p>
     */
    public double totalWeights;

    /**
     * <p>
     * 资源供给中心点的类型。
     * </p>
     */
    public SupplyCenterType type;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public SupplyResult() {

    }

}
