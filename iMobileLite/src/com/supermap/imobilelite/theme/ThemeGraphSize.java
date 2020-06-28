package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 统计专题图符号尺寸设置类。
 * </p>
 * <p>
 * 通过该类可以设置统计专题图符号最小和最大的基准尺寸。专题图表的尺寸大小与基准值、分级方式及专题字段值的大小都有紧密联系。它是利用指定的分级方式，最大基准尺寸、最小基准值以及字段的最大值和最小值计算统计图中各个值对应的图表尺寸的大小。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeGraphSize implements Serializable {
    private static final long serialVersionUID = -5934418200476682333L;
    /**
     * <p>
     * 获取或设置统计图中显示的最大图表尺寸基准值，默认为0像素。
     * </p>
     */
    public double maxGraphSize;
    /**
     * <p>
     * 统计图中显示的最小图表尺寸基准值，默认为0像素。
     * </p>
     */
    public double minGraphSize;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeGraphSize() {
        super();
    }

}
