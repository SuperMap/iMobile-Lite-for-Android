package com.supermap.imobilelite.spatialAnalyst;

/**
 * <p>
 * 缓冲区端点枚举类型。
 * </p>
 * <p>
 * 线对象缓冲区分析时的端点类型，分为圆头缓冲和平头缓冲。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum BufferEndType {
    /**
     * <p>
     * 平头缓冲。平头缓冲区是在生成缓冲区时，在线段的端点处做线段的垂线。暂不支持平头缓冲。
     * </p>
     * <p>
     * <img src="../../../../resources/SpatialAnalyst/Buffer_flat.png">
     * </p>
     */
    FLAT,
    /**
     * <p>
     * 圆头缓冲。圆头缓冲区是在生成缓冲区时，在线段的端点处做半圆弧处理。
     * </p>
     * <p>
     * <img src="../../../../resources/SpatialAnalyst/Buffer_round.png">
     * </p>
     */
    ROUND
}
