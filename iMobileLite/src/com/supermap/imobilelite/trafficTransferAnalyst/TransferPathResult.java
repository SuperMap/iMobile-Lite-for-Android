package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 交通换乘路线查询结果类。 
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class TransferPathResult implements Serializable {
    private static final long serialVersionUID = -3727604999860154729L;
    /**
     * <p>
     * 交通换乘导引对象，记录了从换乘分析起始站点到终止站点的交通换乘导引方案，通过 此对象可以获取交通换乘导引对象中子项的个数，根据序号获取交通换乘导引的子项对象，导引总距离以及总花费等。
     * </p>
     */
    public TransferGuide transferGuide;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferPathResult() {
        super();
    }

}
