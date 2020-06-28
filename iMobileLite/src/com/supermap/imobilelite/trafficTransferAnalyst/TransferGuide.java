package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 交通换乘导引类。
 * 交通换乘导引记录了从换乘分析起始站点到终止站点的交通换乘导引方案。
 * 交通换乘导引由交通换乘导引子项（TransferGuideItem 类型对象）构成，每一个导引子项可以表示一段换乘或者步行线路。
 * 通过本类型可以返回交通换乘导引对象中子项的个数，根据序返回交通换乘导引的子项对象，导引总距离以及总花费等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TransferGuide implements Serializable {
    private static final long serialVersionUID = 2063192381089230401L;
    /**
     * <p>
     * 返回交通换乘导引对象中子项的个数。
     * </p>
     */
    public int count;
    /**
     * <p>
     * 根据指定的序号返回交通换乘导引中的子项对象。
     * </p>
     */
    public TransferGuideItem[] items;
    /**
     * <p>
     * 返回交通换乘导引的总距离，即当前换乘方案的总距离。
     * </p>
     */
    public double totalDistance;
    /**
     * <p>
     * 返回交通换乘次数，因为中途可能有步行的子项，所以交通换乘次数不能根据 TransferGuide.getCount() 来简单计算。
     * </p>
     */
    public int transferCount;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferGuide() {
        super();
    }

}
