package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 交通换乘方案类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TransferSolution implements Serializable {
    private static final long serialVersionUID = -322273242436986513L;

    /**
     * <p>
     * 换乘方案对应的换乘次数。
     * </p>
     */
    public int transferCount;

    /**
     * <p>
     * 换乘分段数组。
     * </p>
     */
    public TransferLines[] linesItems;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferSolution() {
        super();
    }

}
