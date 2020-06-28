package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 换乘路线信息类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TransferLine implements Serializable {
    private static final long serialVersionUID = -5215642581678721957L;
    /**
     * <p>
     * 乘车路线名称。
     * </p>
     */
    public int lineID;
    /**
     * <p>
     * 乘车路线名称。
     * </p>
     */
    public String lineName;
    /**
     * <p>
     * 上车站点在本公交路线中的索引。
     * </p>
     */
    public int startStopIndex;
    /**
     * <p>
     * 上车站点名称。
     * </p>
     */
    public String startStopName;
    /**
     * <p>
     * 下车站点在本公交路线中的索引。
     * </p>
     */
    public int endStopIndex;
    /**
     * <p>
     * 下车站点名称。
     * </p>
     */
    public String endStopName;    
    /**
     * <p>
     * 上车站点别名。
     * </p>
     */
    public String startStopAliasName;
    /**
     * <p>
     * 乘车路线别名。
     * </p>
     */
    public String lineAliasName;
    /**
     * <p>
     * 下车站点别名。
     * </p>
     */
    public String endStopAliasName;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferLine() {
        super();
    }

}
