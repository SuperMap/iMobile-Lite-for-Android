package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 缓冲区分析通用参数设置类。
 * </p>
 * <p> 
 * 通过该类可以设置缓冲区分析的左缓冲距离、右缓冲距离、端点类型、圆头缓冲圆弧处线段的个数。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class BufferSetting implements Serializable {
    private static final long serialVersionUID = 4517931426223980641L;

    /**
     * <p>
     * 线对象缓冲区分析时的端点类型。 分为平头和圆头两种，默认为圆头缓冲，即 BufferEndType.ROUND，暂不支持平头缓冲。 对于面和点对象的缓冲区分析，该字段值必需为 BufferEndType.ROUND 类型。
     * </p>
     */
    public BufferEndType endType = BufferEndType.ROUND;

    /**
     * <p>
     * 左侧缓冲距离。由 BufferDistance 类定义。
     * 在 BufferDistance 类中，提供数值型和字符串两种类型定义该参数：
     * 1.当该参数为数值型时（默认为 100 数据集单位）：
     * 对于对点数据和面数据进行缓冲区分析时，该参数代表缓冲区的距离；
     * 对于对线数据进行缓冲区分析时，该参数代表线对象的左缓冲距离。
     * 2.当该参数为字符串类型时（只对数据缓冲区分析 DatasetBufferAnalystService 有效）：
     * 对于对点数据集和面数据集进行缓冲区分析时，该参数代表缓冲距离的字段表达式；
     * 对于对线数据集进行缓冲区分析时，该参数代表线对象的左缓冲距离的字段表达式。
     * </p>
     */
    public BufferDistance leftDistance;

    /**
     * <p>
     * 右侧缓冲距离。由 BufferDistance 类定义。
     * 在 BufferDistance 类中，提供数值型和字符串两种类型定义该参数：
     * 1.当该参数为数值型时（默认为 100 数据集单位）：
     * 对于对点数据和面数据进行缓冲区分析时，该参数无效；
     * 对于对线数据进行缓冲区分析时，该参数代表线对象的右缓冲距离。
     * 2.当该参数为字符串类型时（只对数据集缓冲区分析 DatasetBufferAnalystService 有效）：
     * 对于对点数据和面数据进行缓冲区分析时，该参数无效；
     * 对于对线数据集进行缓冲区分析时，该参数代表线对象的右缓冲距离的字段表达式。
     * </p>
     */
    public BufferDistance rightDistance;

    /**
     * <p>
     * 圆头缓冲圆弧处线段的个数，即用多少个线段来模拟一个半圆，默认值为4。当属性 endType 值为 BufferEndType.ROUND 时有效。
     * </p>
     */
    public int semicircleLineSegment = 4;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public BufferSetting() {
        super();
    }
}
