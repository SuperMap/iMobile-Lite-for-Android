package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 服务区分析结果类。
 * </p>
 * <p>
 * 该类用于存储服务区分析结果。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindServiceAreasResult implements Serializable {
    private static final long serialVersionUID = -6966314439030751253L;
    /**
     * <p>
     * 获取服务区对象数组。
     * </p>
     */
    public ServiceAreaResult[] serviceAreaList;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindServiceAreasResult() {
        super();
    }

}
