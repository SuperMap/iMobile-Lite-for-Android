package com.supermap.imobilelite.networkAnalyst;

/**
 * <p>
 * 资源供给中心点类型枚举类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum SupplyCenterType {

    /**
     * <p>
     * 固定中心点。固定中心是指网络中已经存在的、已建成的服务设施（扮演资源供给角色）。
     * </p>
     */
    FIXEDCENTER,

    /**
     * <p>
     * 非中心点。非中心点在分析时不予考虑，在实际中可能是不允许建立这项设置或者已经存在了其他设施的点。
     * </p>
     */
    NULL,

    /**
     * <p>
     * 可选中心点。可选中心点是指可以建立服务设施的资源供给中心，即待建服务设施将在这些可选中心间选址。
     * </p>
     */
    OPTIONALCENTER;

}
