package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Path;

/**
 * <p>
 * 最佳路径分析结果类。
 * </p>
 * <p>
 * 该类用于存储从服务端获取的最佳路径分析结果，从中可以获取一条或多条结果路径（Path）。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FindPathResult implements Serializable {
    private static final long serialVersionUID = -2732270291608855162L;
    /**
     * <p>
     * 获取交通网络分析结果路径数组。
     * 目前分析结果中只有一条路径（Path），其中包含路径途经的结点、弧段、该路径的路由、行驶引导、耗费等信息。
     * </p>
     */
    public Path[] pathList;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public FindPathResult() {
        super();
    }

}
