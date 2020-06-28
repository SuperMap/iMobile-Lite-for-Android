package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 更新权值服务结果类.
 * 该类用于返回更新弧段权值和转向权重字段服务执行结果，即返回权值更新成功与否，更新失败的错误代码等内容。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class UpadateWeightResult implements Serializable {
    private static final long serialVersionUID = 7211876594578378180L;
    /**
     * <p>
     * 更新权值服务结果成功与否
     * </p>
     */
    public boolean succeed;
    /**
     * <p>
     * 更新权值服务结果状态码
     * </p>
     */
    public int code;
    /**
     * <p>
     * 更新权值服务结果详细信息
     * </p>
     */
    public String details;

    public UpadateWeightResult() {
        super();
    }

}
