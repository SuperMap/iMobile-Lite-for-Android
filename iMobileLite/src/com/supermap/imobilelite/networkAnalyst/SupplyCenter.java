package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 资源供给中心点。
 * </p>
 * <p>
 * 资源供给中心点，在选址分区分析（FindLacationService）中使用。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class SupplyCenter implements Serializable {
    private static final long serialVersionUID = 3721027017262919135L;

    /**
     * <p>
     * 资源供给中心的最大耗费值，必设参数。
     * </p>
     */
    public double maxWeight;

    /**
     * <p>
     * 资源供给中心点的结点ID号，必设参数。资源供给中心必须是结点。
     * </p>
     */
    public int nodeID;

    /**
     * <p>
     * 资源供给中心能提供的最大服务量或商品数量，必设参数。
     * </p>
     */
    public double resourceValue;

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
    public SupplyCenter() {
        super();
    }

    public SupplyCenter(double maxWeight, int nodeID, double resourceValue, SupplyCenterType type) {
        super();
        this.maxWeight = maxWeight;
        this.nodeID = nodeID;
        this.resourceValue = resourceValue;
        this.type = type;
    }

}
