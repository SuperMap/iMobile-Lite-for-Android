package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 获取转向权重字段服务结果类。
 * </p>
 * <p>
 * 该类用于存储获取转向权重字段服务执行结果，即服务端发布的所有转向权重字段
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class TurnNodeWeightNamesResult implements Serializable {
    private static final long serialVersionUID = 8028018859562428652L;
    /**
     * <p>
     * 转向权值字段（string）列表
     * </p>
     */
    public String[] turnNodeWeightNames;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TurnNodeWeightNamesResult() {
        super();
    }

}
