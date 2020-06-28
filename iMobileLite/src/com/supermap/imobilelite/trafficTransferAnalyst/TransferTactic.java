package com.supermap.imobilelite.trafficTransferAnalyst;

/**
 * 
 * <p>
 * 交通换乘策略枚举类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 */
public enum TransferTactic {
    /**
     * 
     * <p>
     * 最少时间。
     * </p>
     */
    LESS_TIME,
    /**
     * 
     * <p>
     * 最少换乘。
     * </p>
     */
    LESS_TRANSFER,
    /**
     * 
     * <p>
     * 最少步行。
     * </p>
     */
    LESS_WALK,
    /**
     * 
     * <p>
     * 最短距离。
     * </p>
     */
    MIN_DISTANCE;
}
