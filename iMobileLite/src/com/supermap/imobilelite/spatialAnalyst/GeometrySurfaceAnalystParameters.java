package com.supermap.imobilelite.spatialAnalyst;

import com.supermap.services.components.commontypes.Point2D;

/**
 * <p>
 * 几何对象表面分析参数类。
 * </p>
 * <p>
 * 通过该类可以设置表面分析方法的参数。如用于等值线或面提取的点集合、第三维数据，及其它参数。
 * 从一个点集合中提取等值线的实现原理是先利用点集合中存储的第三维信息（高程或者温度等）， 也就是除了点的坐标信息的数据，对点数据进行插值分析，得到栅格数据集（中间结果数据集），接着从栅格数据集中提取等值线。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class GeometrySurfaceAnalystParameters extends SurfaceAnalystParameters {
    private static final long serialVersionUID = -2715222683001847570L;

    /**
     * <p>
     * 获取或设置用于提取等值线/面的点（Point2D 类型）集合。必设属性。
     * </p>
     */
    public Point2D[] points;

    /**
     * <p>
     * 获取或设置第三维数据值集合，该集合中的数值与点集合 Points 中的点对象一一对应，代表点对象的第三维数据，如：高程、温度等。必设属性。
     * </p>
     */
    public double[] zValues;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GeometrySurfaceAnalystParameters() {
        super();
    }

}
