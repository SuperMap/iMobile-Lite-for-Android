package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Recordset;

/**
 * <p>
 * 数据集缓冲区分析服务结果数据类。
 * </p>
 * <p>
 * 该类中包含了缓冲区分析的结果记录集。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class DatasetBufferAnalystResult extends SpatialAnalystResult {
    private static final long serialVersionUID = 5445939129656574511L;

    /**
     * <p>
     * 数据集缓冲区分析的结果数据集标识。
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 数据集缓冲区分析的结果记录集。 
     * </p>
     */
    public Recordset recordset;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public DatasetBufferAnalystResult() {
        super();
    }
}
