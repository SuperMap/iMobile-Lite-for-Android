package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

import com.supermap.imobilelite.maps.Point2D;

/**
 * <p>
 * 公交站点信息类。该类用于描述公交站点的信息，包括所属的数据集、SmID、ID、名称以及别名。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TransferStopInfo implements Serializable {
    private static final long serialVersionUID = -1584947050388931915L;
    /**
     * <p>
     * 公交站点别名。
     * </p>
     */
    public String alias;
    /**
     * <p>
     * 公交站点的 SMID。
     * </p>
     */
    public int id;
    /**
     * <p>
     * 公交站点名称。
     * </p>
     */
    public String name;
    /**
     * <p>
     * 公交站点坐标。
     * </p>
     */
    public Point2D position;
    /**
     * <p>
     * 公交站点ID。
     * </p>
     */
    public String stopID;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferStopInfo() {
        super();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param alias 公交站点别名。
     * @param id 公交站点的SMID。
     * @param name 公交站点名称。
     * @param position 公交站点坐标。
     * @param stopID 公交站点的ID。
     */
    public TransferStopInfo(String alias, int id, String name, Point2D position, String stopID) {
        super();
        this.alias = alias;
        this.id = id;
        this.name = name;
        this.position = position;
        this.stopID = stopID;
    }

}
