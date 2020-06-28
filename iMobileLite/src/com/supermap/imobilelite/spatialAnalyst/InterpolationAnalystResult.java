package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Recordset;

/**
 * <p>
 * 插值分析结果类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class InterpolationAnalystResult extends SpatialAnalystResult {
    private static final long serialVersionUID = -8604546236260184182L;

    /**
     * <p>
     * 结果数据集标识
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 结果记录集，用于存放空间对象信息。
     * </p>
     */
    public Recordset recordset;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public InterpolationAnalystResult() {
        super();
    }

}
