package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

import com.supermap.imobilelite.maps.Point2D;
import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 交通换乘导引子项类。
 * 交通换乘导引记录了从换乘分析起始站点到终止站点需要换乘或者步行的线路，其中每一换乘或步行线路就是一个交通换乘导引子项。
 * 利用该类可以返回交通换乘导引对象的子项信息，诸如交通换乘导引子项的起始站点信息、终止站点信息、公交线路信息等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TransferGuideItem implements Serializable {
    private static final long serialVersionUID = 3746309654290554758L;
    /**
     * <p>
     * 线路类型,0代表公交车，1代表地铁，2代表轻轨。
     * </p>
     */
    public int lineType;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示的一段换乘或者步行线路的距离。
     * </p>
     */
    public double distance;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示的一段换乘线路的终止站点在其完整的公交线路中处在第几个站点位置。
     * </p>
     */
    public int endIndex;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示的一段换乘线路的起始站点在其完整的公交线路中处在第几个站点位置。
     * </p>
     */
    public int startIndex;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示是步行线路还是乘车线路。
     * </p>
     */
    public boolean isWalking;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示的一段换乘线路信息。
     * </p>
     */
    public Geometry route;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示的一段换乘线路所经过的站点个数。
     * </p>
     */
    public int passStopCount;
    /**
     * <p>
     * 该 TransferGuideItem 对象所表示的一段换乘线路名称。
     * </p>
     */
    public String lineName;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示的一段换乘或者步行线路的起始站点的位置坐标。
     * </p>
     */
    public Point2D startPosition;
    /**
     * <p>
     * 该 TransferGuideItem 对象所表示的一段换乘或者步行线路的终止站点位置坐标。
     * </p>
     */
    public Point2D endPosition;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示的一段换乘线路的起始站点的名称。
     * </p>
     */
    public String startStopName;
    /**
     * <p>
     * 返回该 TransferGuideItem 对象所表示的一段换乘线路的终点站点的名称。
     * </p>
     */
    public String endStopName;
    /**
     * <p>
     * 上车站点别名。
     * </p>
     */
    public String startStopAliasName;
    /**
     * <p>
     * 乘车路线别名。
     * </p>
     */
    public String lineAliasName;
    /**
     * <p>
     * 下车站点别名。
     * </p>
     */
    public String endStopAliasName;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferGuideItem() {
        super();
    }

}
