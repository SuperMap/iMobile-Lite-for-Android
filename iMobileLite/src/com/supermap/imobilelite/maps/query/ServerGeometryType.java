package com.supermap.imobilelite.maps.query;

/**
 * <p>
 * 服务端几何对象类型枚举类。
 * </p>
 * <p>
 * 该类定义了服务端一系列几何对象类型。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum ServerGeometryType {
    /**
     * <p>
     * 未定义。
     * </p>
     */
    UNKNOWN,

    /**
     * <p>
     * 点。
     * </p>
     */
    POINT,

    /**
     * <p>
     * 线。
     * </p>
     */
    LINE,

    /**
     * <p>
     * 面。
     * </p>
     */
    REGION,

    /**
     * <p>
     * 文本。
     * </p>
     *
     */
    TEXT,

    /**
     * <p>
     * 路由对象（Route），是一组线类型的地物对象，线对象的每个节点除了有地理坐标值，还有一个M坐标值，代表该节点到起点的距离。
     * </p>
     */
    LINEM,

    /**
     * <p>
     * 三维点。
     * </p>
     */
    POINT3D, /**
     * <p>
     * 三维线。
     * </p>
     */
    LINE3D, /**
     * <p>
     * 三维面。
     * </p>
     */
    REGION3D,
    // 为支持CAD数据集扩展的几何类型

    /**
     * 圆弧。
     */
    ARC,

    /**
     * 二次B样条曲线。
     */
    BSPLINE,

    /**
     * 二维Cardinal样条曲线。
     */
    CARDINAL,

    /**
     * 弓形。
     */
    CHORD,

    /**
     * 圆。
     */
    CIRCLE,

    /**
     * 二维曲线。
     */
    CURVE,

    /**
     * 椭圆。
     */
    ELLIPSE,

    /**
     * 椭圆弧。
     */
    ELLIPTICARC,

    /**
     * 扇面。
     */
    PIE,

    /**
     * 矩形。
     */
    RECTANGLE,

    /**
     * 圆角矩形。
     */
    ROUNDRECTANGLE

}
