package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 交通换乘方案查询结果类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class TransferSolutionResult implements Serializable {
    private static final long serialVersionUID = -3536218878422611737L;
    /**
     * <p>
     * 默认的乘车方案及相关信息。
     * </p>
     */
    public TransferGuide defaultGuide;
    /**
     * <p>
     * 返回的乘车方案集合。
     * </p>
     */
    public TransferSolution[] solutionItems;
    /**
     * <p>
     * 是否建议步行。
     * </p>
     * @since 7.0.0
     */
    public boolean suggestWalking = false;

    /**
     * <p>
     * 构造函数。
     * </p>
     */ 
    public TransferSolutionResult() {
        super();
    }

}
