package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 动态分段操作参数类。 
 * </p>
 * <p>
 * 通过该类可以为动态分段提供参数信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GenerateSpatialDataParameters implements Serializable {
    private static final long serialVersionUID = -3880727901649776216L;

    /**
     * <p>
     * 结果返回设置。注意：事件表数据集若已经关联的结果空间数据集，不能重复创建。
     * </p>
     */
    public DataReturnOption dataReturnOption;

    /**
     * <p>
     * 错误信息字段，设置后直接写入原事件表，用于描述事件未能生成对应的点或线时的错误信息。可选参数。 
     * </p>
     */
    public String errorInfoField;

    /**
     * <p>
     * 获取或设置参与动态分段的事件表中的路由标识字段。
     * </p>
     */
    public String eventRouteIDField;

    /**
     * <p>
     * 获取或设置参与动态分段的事件表表名，形如"数据集名称@数据源别名"。例如：Country@World。
     * </p>
     */
    public String eventTable;

    /**
     * <p>
     * 获取或设置参与动态分段的事件表的终止刻度字段，只有当事件为线事件的时候该属性才有意义。目前支持的字段类型为双精度。
     * </p>
     */
    public String measureEndField;

    /**
     * <p>
     * 获取或设置参与动态分段的事件表的刻度字段，只有当事件为点事件的时候该属性才有意义。目前支持的字段类型为双精度。
     * </p>
     */
    public String measureField;

    /**
     * <p>
     * 刻度偏移量字段。可选参数。
     * </p>
     */
    public String measureOffsetField;

    /**
     * <p>
     * 获取或设置参与动态分段的事件表的起始刻度字段，只有当事件为线事件的时候该属性才有意义。目前支持的字段类型为双精度。
     * </p>
     */
    public String measureStartField;

    /**
     * <p>
     * 获取或设置参与动态分段的路由数据集中的路由标识字段。
     * </p>
     */
    public String routeIDField;

    /**
     * <p>
     * 获取或设置参与动态分段的路由数据集名称，形如"数据集名称@数据源别名"。例如：Country@World。 
     * </p>
     */
    public String routeTable;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GenerateSpatialDataParameters() {
        super();
    }
}
