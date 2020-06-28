package com.supermap.imobilelite.networkAnalyst;

/**
 * <p>
 * 转弯方向枚举类。
 * </p>
 * <p>
 * 该常量用于行驶导引子项类中
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum TurnType {

    /**
     * <p>
     * 向前直行
     * </p>
     */
    AHEAD,

    /**
     * <p>
     * 掉头
     * </p>
     */
    BACK,

    /**
     * <p>
     * 终点，不拐弯
     * </p>
     */
    END,

    /**
     * <p>
     * 左转弯
     * </p>
     */
    LEFT,

    /**
     * <p>
     * 无效值
     * </p>
     */
    NONE,

    /**
     * <p>
     * 右转弯
     * </p>
     */
    RIGHT;

}
