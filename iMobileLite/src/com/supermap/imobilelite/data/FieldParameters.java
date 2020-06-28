package com.supermap.imobilelite.data;

import java.io.Serializable;

/**
 * <p>
 * 数据集字段统计参数类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FieldParameters implements Serializable {
    private static final long serialVersionUID = -5896653137301164578L;
    /**
    * <p>
    * 查询的数据集名称。必设字段。
    * </p>
    */
    public String dataset;
    /**
    * <p>
    * 查询的数据源名称。必设字段。
    * </p>
    */
    public String datasource;
    /**
    * <p>
    * 查询的字段名称。字段查询时的必设字段。
    * </p>
    */
    public String field;
    /**
     * <p>
     * 查询的统计类型。字段查询时的必设字段。
     * </p>
     */
    public String statisticMode;

    public FieldParameters() {
        super();
    }
}
