package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 路由点类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class PointWithMeasure implements Serializable {
    private static final long serialVersionUID = -2772805279829011450L;
    /**
     * <p>
     * 获取路由点的度量值。
     * </p>
     */
    public double measure;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public PointWithMeasure() {
        super();
    }

}
