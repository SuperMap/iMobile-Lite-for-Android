package com.supermap.imobilelite.spatialAnalyst;

/**
 * <p>
 * 数据返回模式枚举类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum DataReturnMode {
    /**
     * <p>
     * 返回结果数据集标识(数据集名称@数据源名称)和记录集（RecordSet）。 
     * </p>
     */
    DATASET_AND_RECORDSET,
    /**
     * <p>
     * 只返回数据集标识（数据集名称@数据源名称）。 
     * </p>
     */
    DATASET_ONLY,
    /**
     * <p>
     * 只返回记录集（RecordSet）。 
     * </p>
     */
    RECORDSET_ONLY
}
