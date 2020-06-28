package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 站点查询参数类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class StopQueryParameters implements Serializable {
    private static final long serialVersionUID = 1095486796124300437L;
    /**
     * <p>
     * 站点名称关键字。
     * </p>
     */
    public String keyWord;
    /**
     * <p>
     * 是否返回站点坐标信息。
     * </p>
     */
    public boolean returnPosition;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public StopQueryParameters() {
        super();
    }
}
