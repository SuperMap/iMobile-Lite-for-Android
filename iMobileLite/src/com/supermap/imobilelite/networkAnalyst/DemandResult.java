package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Feature;

/**
 * <p>
 * 选址分区分析中的需求对象类。
 * </p>
 * <p> 
 * 该类对应于选址分区分析中的资源需求对象（弧段或结点），其中包括需求结点或弧段的 ID、资源供给中心 ID、实际被分配的资源量以及需求结果是弧段还是结点信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class DemandResult extends Feature implements Serializable {
    private static final long serialVersionUID = 2271096090262386918L;

    /**
     * <p>
     * 获取需求对象实际被分配的资源量。
     * </p>
     */
    public long actualResourceValue;

    /**
     * <p>
     * 获取需求结果对应的结点或弧段的 ID。
     * 当该类中 isEdge 字段为 true 时，该需求对象为弧段的 ID，当 isEdge 为 false 时，为需求结点的 ID。
     * </p>
     */
    public int demandID;

    /**
     * <p>
     * 获取要素是否是弧段。true 表明需求结果对应的要素是弧段，false 表明需求结果对应的要素是结点。 
     * </p>
     */
    public boolean isEdge;

    /**
     * <p>
     * 获取该需求结果对象对应的资源供给中心。
     * </p>
     */
    public SupplyCenter supplyCenter;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public DemandResult() {
        super();
    }
}
