package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 交通换乘分段类，记录了本分段中可乘坐的路线信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TransferLines implements Serializable {
    private static final long serialVersionUID = 8308921786816702925L;
    /**
     * <p>
     * 本换乘分段内可乘车的路线集合。
     * </p>
     */
    public TransferLine[] lineItems;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferLines() {
        super();
    }
    
}
