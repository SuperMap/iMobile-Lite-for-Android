package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 选址分区分析服务结果数据类。
 * </p>
 * <p>
 * 该类用于存放选址分区分析结果。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindLocationResult implements Serializable {
    private static final long serialVersionUID = -6032472342204091504L;

    /**
    * <p>
    * 获取需求结果对象数组。
    * </p>
    */
    public DemandResult[] demandResults;

    /**
     * <p>
     * 获取资源供给结果对象数组。
     * </p>
     */
    public SupplyResult[] supplyResults;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindLocationResult() {
        super();
    }
}
