package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.TSPPath;

/**
 * <p>
 * 旅行商分析服务结果类。
 * </p>
 * <p>
 * 该类用于存储旅行商分析结果，即旅行最佳路径。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindTSPPathsResult implements Serializable {
    private static final long serialVersionUID = 1929776960358864590L;
    /**
     * <p>
     * 获取旅行商分析结果路径（TSPPath）数组。
     * </p>
     */
    public TSPPath[] tspPathList;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindTSPPathsResult() {
        super();
    }

}
