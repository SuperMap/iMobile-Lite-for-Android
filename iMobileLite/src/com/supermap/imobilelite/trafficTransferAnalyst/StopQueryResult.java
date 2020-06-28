package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 站点查询结果类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class StopQueryResult implements Serializable {
    private static final long serialVersionUID = 8400108193899034695L;
    /**
     * <p>
     * 公交站点信息，包括所属的数据集、SmID、ID、名称以及别名。
     * </p>
     */
    public TransferStopInfo[] transferStopInfos;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public StopQueryResult() {
        super();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param transferStopInfos 公交站点信息集合。
     */
    public StopQueryResult(TransferStopInfo[] transferStopInfos) {
        super();
        if (transferStopInfos != null) {
            int len = transferStopInfos.length;
            if (len <= 0) {
                return;
            }
            transferStopInfos = new TransferStopInfo[len];
            for (int i = 0; i < len; i++) {
                this.transferStopInfos[i] = transferStopInfos[i];
            }
        }
    }

}
