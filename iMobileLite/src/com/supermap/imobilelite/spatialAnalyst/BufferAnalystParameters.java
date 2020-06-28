package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 缓冲区分析参数基类。
 * </p>
 * <p> 
 * 缓冲区分析是 GIS 中基本的空间分析，缓冲区实际上是在基本空间要素周围建立的具有一定宽度的邻近区域。缓冲区分析可以有以下应用：确定街道拓宽的范围，确定放射源影响的范围等等。
 * 对于面对象而言，在做缓冲区分析前最好先经过拓扑检查，排除面内相交的情况，所谓面内相交，指的是面对象自身相交，如图所示，图中数字代表面对象的节点顺序。
 * <p> 
 * <img src="../../../../resources/SpatialAnalyst/Buffer.png">
 * </p>
 * <p> 
 * 可以对数据集或某个几何对象做缓冲区分析，这两类缓冲区分析功能的参数类（DatasetsBufferAnalystParameters 和 GeometryBufferAnalystParameters）继承了该基类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class BufferAnalystParameters implements Serializable {
    private static final long serialVersionUID = 7017217765588024408L;
    /**
     * <p>
     * 获取或设置缓冲区分析通用参数。
     * 为缓冲区分析提供必要的参数信息，包括左缓冲距离、右缓冲距离、端点类型、圆头缓冲圆弧处线段的个数信息。
     * 对线进行缓冲区分析时左右缓冲距离必须相等。
     * </p>
     */
    public BufferSetting buffersetting;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public BufferAnalystParameters() {
        super();
    }

}
