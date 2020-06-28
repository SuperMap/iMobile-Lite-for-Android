package com.supermap.imobilelite.spatialAnalyst;

/**
 * <p>
 * 插值分析的算法的类型枚举类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum InterpolationAlgorithmType {
    /**
     * <p>
     * 普通克吕金插值法。
     * </p>
     */
    KRIGING,

    /**
     * <p>
     * 简单克吕金插值法。
     * </p>
     */
    SimpleKriging,

    /**
     * <p>
     * 泛克吕金插值法。
     * </p>
     */
    UniversalKriging;

}
