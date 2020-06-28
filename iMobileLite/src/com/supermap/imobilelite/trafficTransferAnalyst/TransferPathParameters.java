package com.supermap.imobilelite.trafficTransferAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 交通换乘线路查询参数类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class TransferPathParameters<T> implements Serializable {
    private static final long serialVersionUID = -1314245686972733424L;
    /**
     * <p>
     * 起止点的ID或坐标。
     * 两种查询方式： 1. 按照公交站点的起止ID进行查询，则points参数的类型为Integer[]，形如：[起点ID、终点ID]， 公交站点的ID对应服务提供者配置中的站点ID字段； 2. 按照起止点的坐标进行查询，则points参数的类型为Point2D[]，形如：[{“x”:44,”y”:39},{“x”:45,”y”:40}]。
     * </p>
     */
    public T[] points;
    /**
     * <p>
     * 本换乘分段内可乘车的路线集合。
     * </p>
     */
    public TransferLine[] transferLines;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public TransferPathParameters() {
        super();
    }

}
