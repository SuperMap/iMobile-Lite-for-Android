package com.supermap.imobilelite.maps.query;
import java.io.Serializable;
/**
 * <p>
 * 海图查询过滤参数类。
 * </p>
 * <p>
 * 用于设置海图查询的过滤参数。包括：物标代码、物标可应用对象的选择（是否查询点、线或面）、属性字段过滤条件。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ChartQueryFilterParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * <p>
     * 是否查询点。
     * </p>
     */
    public boolean isQueryPoint;
    /**
     * <p>
     * 是否查询线。
     * </p>
     */
    public boolean isQueryLine;
    /**
     * <p>
     * 是否查询面。
     * </p>
     */
    public boolean isQueryRegion;
    /**
     * <p>
     * 属性字段过滤条件。
     * </p>
     */
    public String attributeFilter;
    /**
     * <p>
     * 查询的物标代号。
     * </p>
     */
    public int chartFeatureInfoSpecCode;

    public ChartQueryFilterParameter() {
        super();
    }

    public String toJson() {
        String json = "";
        json += "\"isQueryPoint\":" + this.isQueryPoint + ",";
        json += "\"isQueryLine\":" + this.isQueryLine + ",";
        json += "\"isQueryRegion\":" + this.isQueryRegion + ",";
        if ("".equals(this.attributeFilter))
            json += "\"attributeFilter\": \"" + this.attributeFilter + "\",";
        json += "\"chartFeatureInfoSpecCode\":" + this.chartFeatureInfoSpecCode;
        json = "{" + json + "}";
        return json;
    }

}