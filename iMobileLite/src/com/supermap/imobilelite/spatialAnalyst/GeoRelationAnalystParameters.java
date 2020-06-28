package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.QueryParameter;

/**
 * <p>
 * 空间关系分析操作参数类。
 * </p>
 * <p>
 * 通过该类可以为空间关系分析提供参数信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GeoRelationAnalystParameters implements Serializable {
    private static final long serialVersionUID = 5891494453516573957L;

    /**
     * <p>
     * 源数据集名称,即被操作数据集名称，规则为"数据集名称@数据源别名"。例如：Country@World。
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 空间关系分析期望返回结果记录数，默认为500条，如果实际不足500条结果则返回所有分析结果。 
     * </p>
     */
    public int expectCount = 500;

    /**
     * <p>
     * 边界处理方式，即位于面边线上的点是否被面包含。此参数仅用于空间关系为包含或被包含的情况。  
     * </p>
     */
    public boolean isBorderInside;

    /**
     * <p>
     * 空间关系分析中的参考数据集查询参数。仅 name, ids, attributeFilter 和 fields 字段有效。
     * 参考数据集即操作数据集。例如：要设置关系"面包含点"，则空间关系类型为"包含"，参考数据集为相应的面数据集，源数据集为相应的点数据集；再如，"点被面包含"，则空间关系类型为"被包含"，参考数据集为相应的点数据集，源数据集为相应的面数据集。
     * </p>
     */
    public QueryParameter referenceFilter;

    /**
     * <p>
     * 是否返回Feature信息。
     * </p>
     */
    public boolean returnFeature;

    /**
     * <p>
     * 是否仅返回满足指定空间关系的空间对象，默认为 True。
     * 若设置为false，则在源数据集中与操作数据集中的指定对象不满足指定空间关系类型的对象也会返回。
     * </p>
     */
    public boolean returnGeoRelatedOnly = true;

    /**
     * <p>
     * 空间关系分析中的源数据集查询参数。仅 ids、attributeFilter 和 fields 字段有效。 
     * </p>
     */
    public QueryParameter sourceFilter;

    /**
     * <p>
     * 指定的空间关系类型,参见 SpatialRelationType 枚举类。
     * </p>
     */
    public SpatialRelationType spatialRelationType;

    /**
     * <p>
     * 分析结果起始记录位置，默认为0。
     * </p>
     */
    public int startRecord;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GeoRelationAnalystParameters() {
        super();
    }
}
