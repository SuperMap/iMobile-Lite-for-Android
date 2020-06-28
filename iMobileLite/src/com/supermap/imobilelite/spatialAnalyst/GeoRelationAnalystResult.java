package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.GeoRelationResult;

/**
 * <p>
 * 空间关系分析操作结果数据类。
 * </p>
 * <p> 
 * 记录空间关系分析服务返回的结果信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GeoRelationAnalystResult implements Serializable {
    private static final long serialVersionUID = -4381462480409263015L;
    /**
     * <p>
     * 空间关系分析的结果信息。
     * </p>
     */
    public GeoRelationResult[] geoRelationResults;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GeoRelationAnalystResult() {
        super();
    }
}
