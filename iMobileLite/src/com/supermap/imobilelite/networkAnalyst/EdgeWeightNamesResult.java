package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 耗费字段分析服务结果类。
 * </p>
 * <p>
 * 该类用于存储获取耗费字段服务执行结果，即服务端返回的所有耗费字段。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class EdgeWeightNamesResult implements Serializable {
    private static final long serialVersionUID = -8864054317619907135L;
    /**
     * <p>
     * 获取耗费字段列表。
     * </p>
     */
    public String[] edgeWeightNames;

    public EdgeWeightNamesResult() {
        super();
    }
}
