package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.Rectangle2D;

/**
 * <p>
 * 行驶导引子项类。
 * </p>
 * <p>
 * 行驶导引记录了如何一步步从起点行驶到终点，其中每一步就是一个行驶导引子项，包括行驶过程中经过的点和弧段， 这些点可以是分析时选取的站点，也可以是分析结果途经的网络结点；弧段可以是网络边，也可能是一条网络边的一部分 （如果分析的站点不在网络结点上）。 利用该类可以对行驶导引对象的子项进行一些设置， 诸如返回子项的 ID、名称、序号、权值等，可以判断子项是点还是弧段，还可以返回行驶方向、转弯方向等等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class PathGuideItem implements Serializable {
    private static final long serialVersionUID = -709290486358261976L;

    /**
     * <p>
     * 行驶导引的范围，对弧段而言，为弧段的外接矩形；对点而言，为点本身。
     * </p>
     */
    public Rectangle2D bounds;

    /**
     * <p>
     * 行驶导引子项的描述信息。
     * </p>
     */
    public String description;

    /**
     * <p>
     * 行驶的方向。
     * </p>
     */
    public DirectionType directionType;

    /**
     * <p>
     * 站点到弧段的距离。
     * </p>
     */
    public double distance;

    /**
     * <p>
     * 行驶导引项所对应的地物对象。
     * </p>
     */
    public Geometry geometry;

    /**
     * <p>
     * 行驶导引对象子项的 ID。
     * </p>
     */
    public int id;

    /**
     * <p>
     *  行驶导引对象子项序号。
     * </p>
     */
    public int index;

    /**
     * <p>
     * 判断本行驶导引子项是否是弧段。
     * </p>
     */
    public Boolean isEdge;

    /**
     * <p>
     * 判断本行驶导引子项是否是站点，即用户输入的用于做路径分析的点， 站点可能与网络结点重合，也可能不在网络上。
     * </p>
     */
    public Boolean isStop;

    /**
     * <p>
     *   弧段的长度（行驶导引对象子项为弧段时）。
     * </p>
     */
    public double length;

    /**
     * <p>
     *   行驶导引对象子项的名称。
     * </p>
     */
    public String name;

    /**
     * <p>
     * 行驶位置，是在路的左侧、右侧还是在路上。
     * </p>
     */
    public SideType sideType;

    /**
     * <p>
     * 转弯的角度。
     * </p>
     */
    public double turnAngle;

    /**
     * <p>
     * 转弯的方向。
     * </p>
     */
    public TurnType turnType;

    /**
     * <p>
     *  行驶导引对象子项的权值，即行使导引子项的花费
     * </p>
     */
    public double weight;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public PathGuideItem() {
        super();
    }

}
