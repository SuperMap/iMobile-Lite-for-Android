package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 返回结果参数设置类。
 * </p>
 * <p>
 * 通过该类可以设置返回的最大记录。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class DataReturnOption implements Serializable {
    private static final long serialVersionUID = 7114863127753636203L;

    /**
     * <p>
     * 分析结果返回模式,默认为DataReturnMode.RECORDSET_ONLY
     * </p>
     */
    public DataReturnMode dataReturnMode = DataReturnMode.RECORDSET_ONLY;

    /**
     * <p>
     * 设置结果数据集名称，当 dataReturnMode 属性值不等于 DataReturnMode.RECORDSET_ONLY 时，该属性有效。
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 如果用户命名的结果数据集名称（dataset 属性值）与已有的数据集重名，是否覆盖已有的数据集。默认为 false，表示不覆盖，在这种情况下如果重名，系统内部会报错，返回“与已有数据集重名”的信息。
     * </p>
     */
    public boolean deleteExistResultDataset = false;

    /**
     * <p>
     * 获取或设置返回的最大记录数，小于或者等于0时表示返回所有记录集。默认值为0。
     * </p>
     */
    public int expectCount = 0;

    public DataReturnOption() {
        super();
    }
}
