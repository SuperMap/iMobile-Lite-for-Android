package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 服务区分析参数类。
 * </p>
 * <p>
 * 该类用于设置进行服务区分析所需的参数。如：服务中心点（可多选）、中心点输入类型、中心点服务半径等信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindServiceAreasParameters<T> implements Serializable {
    private static final long serialVersionUID = -8647544093599075143L;

    /**
     * <p>
     * 获取或设置服务站中心点集合。必设字段。
     * </p>
     */
    public T[] centers;

    /**
     * <p>
     * 获取或设置服务中心点的服务半径集合。必设字段。
     * </p>
     */
    public double[] weights;

    /**
     * <p>
     * 获取或设置是否从中心点开始分析。默认为false表示不从中心点开始分析。
     * </p>
     */
    public Boolean isFromCenter;

    /**
     * <p>
     * 获取或设置是否对分析结果服务区进行互斥处理，可选参数，默认为false表示不进行互斥处理。
     * </p>
     */
    public Boolean isCenterMutuallyExclusive;

    /**
     * <p>
     * 获取或设置交通网络分析通用参数。通过本类可以设置障碍边、障碍点、权值字段信息的名称标识、转向权值字段等信息。
     * </p>
     */
    public TransportationAnalystParameter parameter;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindServiceAreasParameters() {
        super();
    }

}
