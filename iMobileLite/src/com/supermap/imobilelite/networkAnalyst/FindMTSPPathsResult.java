package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.MTSPPath;

/**
 * <p>
 * 多旅行商分析服务结果类。
 * </p>
 * <p>
 * 该类用于存储多旅行商分析的结果，即从配送中心点依次向各个配送目标点配送物资的最佳路径。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindMTSPPathsResult implements Serializable {
    private static final long serialVersionUID = -9160976576017220205L;
    /**
     * <p>
     * 获取多旅行商分析结果路径数组。
     * 该属性为多维数组，其中包含每个配送中心点依次向所负责的配送目标点配送物资的最佳路径（MTSPPath）。数组大小取决配送中心点的个数。
     * </p>
     */
    public com.supermap.services.components.commontypes.MTSPPath[] pathList;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindMTSPPathsResult() {
        super();
    }
}
