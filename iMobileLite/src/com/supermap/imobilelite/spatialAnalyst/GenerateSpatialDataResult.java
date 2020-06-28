package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Recordset;

/**
 * <p>
 * 动态分段操作结果数据类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GenerateSpatialDataResult extends SpatialAnalystResult {
    private static final long serialVersionUID = -5020596388142610014L;

    /**
     * <p>
     * 动态分段操作结果数据集。 
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 结果记录集，用于存放动态分段操作结果信息。 
     * </p>
     */
    public Recordset recordset;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GenerateSpatialDataResult() {
        super();
    }
}
