package com.supermap.imobilelite.networkAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.Route;

/**
 * <p>
 * 服务区对象。
 * </p>
 * <p>
 * 该类用于表示一个服务中心点对应的服务区数据，包括：服务范围内的相关结点、弧段、服务区域等。该类中所包含的结果内容与TransportationAnalystResultSetting类的设置相关。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ServiceAreaResult implements Serializable {
    private static final long serialVersionUID = -1849758763927026632L;

    /**
     * <p>
     * 本服务区包含的网络弧段要素集合。
     * </p>
     */
    public Feature[] edgeFeatures;

    /**
     * <p>
     * 本服务区包含的网络弧段的ID集合。
     * </p>
     */
    public int[] edgeIDs;

    /**
     * <p>
     * 本服务区包含的网络结点要素集合。
     * </p>
     */
    public Feature[] nodeFeatures;

    /**
     * <p>
     * 本服务区包含的网络节点的ID集合。
     * </p>
     */
    public int[] nodeIDs;

    /**
     * <p>
     * 本服务区的路由对象集合。
     * </p>
     */
    public Route[] routes;

    /**
     * <p>
     * 本服务区对应的面对象，即服务范围。
     * </p>
     */
    public Geometry serviceRegion;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ServiceAreaResult() {
        super();
    }

}
