package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 选址分区分析参数类。
 * </p>
 * <p>
 * 该类用于设置选址分区分析所需参数。包括资源供给中心点、返回结果内容、耗费字段、转向字段等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindLocationParameters implements Serializable {
    private static final long serialVersionUID = 2276284742941300040L;

    /**
     * <p>
     * 获取或设置期望选址分区分析结果中包含的资源供给点个数，默认值为 1。
     * 即通过分析，期望最终选择的资源供给点数量。当输入值为0时，最终选择的资源供给点数量默认为覆盖分析区域内所需的最少供给中心点数
     * </p>
     */
    public int expectedSupplyCenterCount = 1;

    /**
     * <p>
     * 获取或设置是否从中心点开始分配资源。默认为 false，表示从需求点向资源供给中心点分配。
     * 由于网路数据中的弧段具有正反阻力，即弧段的正向阻力值与其反向阻力值可能不同，因此，在进行分析时，从资源供给中心开始分配资源到需求点与从需求点向资源供给中心分配这两种分配形式下，所得的分析结果会不同。
     * 下面例子说明了在实际应用中该字段的用处：
     *  * 从中心点开始分配（供给到需求）的例子：电能是从电站产生，并通过电网传送到客户那里去的。在这里，电站就是网络模型中的中心，因为它可以提供电力供应。电能的客户沿电网的线路（网络模型中的弧段）分布，他们产生了“需求”。在这种情况下，资源是通过网络由供方传输到需要来实现资源分配的。
     *  * 不从中心点开始分配（需求到供给）的例子：学校与学生的关系也构成一种在网络中供需分配关系。学校是资源提供方，它负责提供名额供适龄儿童入学。适龄儿童是资源的需求方，他们要求入学。作为需求方的适龄儿童沿街道网络分布，他们产生了对作为供给方的学校的资源--学生名额的需求。
     * </p>
     */
    public boolean isFromCenter = false;

    /**
     * <p>
     * 获取或设置是否在分析结果中包含弧段要素集合。
     * 弧段要素包括弧段的空间信息和属性信息。其中返回的弧段要素是否包含空间信息可通过 returnEdgeGeometry 字段设置。默 认为 false。
     * </p>
     */
    public boolean returnEdgeFeature = false;

    /**
     * <p>
     * 获取或设置分析结果中是否包含弧段的几何信息。默认为 false，表示不包含。
     * 当 returnEdgeFeature 属性值为 true 时，该属性有效。
     * </p>
     */
    public boolean returnEdgeGeometry = false;

    /**
     * <p>
     * 获取或设置是否在分析结果中包含结点要素集合。结点要素包括结点的空间信息和属性信息。默认为 false。
     * </p>
     */
    public boolean returnNodeFeature = true;

    /**
     * <p>
     * 获取或设置资源供给中心点（SupplyCenter）集合，必设参数。
     * 资源供给中心是提供资源和服务的设施，对应于网络结点，资源供给中心的相关信息包括资源量、最大阻力值、资源供给中心类型，资源供给中心在网络中所处结点的 ID 等，以便在进行选址分区分析时使用。
     * </p>
     */
    public SupplyCenter[] supplyCenters;

    /**
     * <p>
     * 获取或设置转向权值字段的名称。可以通过 GetTurnNodeWeightNamesService 类获取服务端发布的所有转向权重字段。
     * </p>
     */
    public String turnWeightField;

    /**
     * <p>
     * 获取或设置阻力字段的名称。必设参数。    
     * 标识了进行网络分析时所使用的阻力字段，例如表示时间、长度等的字段都可以用作阻力字段。可以通过 GetEdgeWeightNamesService 类获取服务端发布的所有阻力字段。
     * </p>
     */
    public String weightName;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindLocationParameters() {

    }

}
