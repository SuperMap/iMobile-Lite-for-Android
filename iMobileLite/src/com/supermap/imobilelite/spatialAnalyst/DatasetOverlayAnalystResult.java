package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Recordset;

/**
 * <p>
 * 数据集叠加分析服务结果数据类。
 * </p>
 * <p>
 * 该类中包含了数据集叠加分析的结果记录集。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class DatasetOverlayAnalystResult extends SpatialAnalystResult {
    private static final long serialVersionUID = 896631384014790354L;

    /**
     * <p>
     * 数据集叠加分析的结果数据集标识。
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 数据集叠加分析的结果记录集。 
     * </p>
     */
    public Recordset recordset;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public DatasetOverlayAnalystResult() {
        super();
    }
}
