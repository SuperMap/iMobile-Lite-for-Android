package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Point3D;
import com.supermap.services.components.commontypes.QueryParameter;
import com.supermap.services.components.commontypes.Rectangle2D;

/**
 * <p>
 * 插值分析参数基类 。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class InterpolationAnalystParameters implements Serializable {
    private static final long serialVersionUID = 4506012457058025245L;

    /**
     * <p>
     * 用于确定结果栅格数据集的范围。
     * </p>
     */
    public Rectangle2D bounds;

    /**
     * <p>
     * 对插值分析结果进行裁剪的参数。
     * </p>
     */
    public ClipParameter clipParam;

    /**
     * <p>
     * 对数据集进行插值分析时用来设置数据集名称，格式为“数据集名称@数据源名称”。例如：dataset = “SamplesP@Interpolation”。
     * </p>
     */
    public String dataset;

    /**
     * <p>
     * 属性过滤条件。对数据集中的点进行过滤，只有满足条件的点对象才参与分析。
     * </p>
     */
    public QueryParameter filterQueryParameter;

    /**
     * <p>
     * 用于 Geometry 插值分析的离散点数组。
     * </p>
     */
    public Point3D[] inputPoints;

    /**
     * <p>
     * 表示插值分析类型，“dataset”表示对数据集进行插值分析，“geometry”表示对离散点数组进行插值分析，默认值为“dataset”。
     * </p>
     */
    public String InterpolationAnalystType = "dataset";

    /**
     * <p>
     * 指定结果数据集的名称。此名称后面不需要跟“@数据源名称”。
     * </p>
     */
    public String outputDatasetName;

    /**
     * <p>
     * 插值分析结果数据源的名称。
     * </p>
     */
    public String outputDatasourceName;

    /**
     * <p>
     * 栅格数据类型，由 PixelFormat 枚举类定义。默认值为PixelFormat.BIT16。
     * 支持存储的像素格式有 BIT16、BIT32、BIT64、DOUBLE、SINGLE、UBIT1、UBIT24、UBIT32、UBIT4、UBIT8。
     * </p>
     */
    public PixelFormat pixelFormat = PixelFormat.BIT16;

    /**
     * <p>
     * 插值结果栅格数据集的分辨率，即一个像元所代表的实地距离，与点数据集单位相同。该值不能超过待分析数据集的范围边长，且该值设置时，应该考虑点数据集范围大小来取值。
     * </p>
     */
    public double resolution;

    /**
     * <p>
     * 【定长查找】 方式下的查找半径，即参与运算点的查找范围，与点数据集单位相同。计算某个位置的Z 值时，会以该位置为圆心，以查找范围的值为半径，落在这个范围内的采样点都将参与运算。该值需要根据待插值点数据的分布状况和点数据集范围进行设置。
     * </p>
     */
    public double searchRadius;

    /**
     * <p>
     * 存储用于进行插值分析的字段名称，插值分析不支持文本类型的字段。含义为每个插值点在插值过程中的权重，可以将所有点此字段值设置为1，即所有点在整体插值中权重相同。
     * </p>
     */
    public String zValueFieldName;

    /**
     * <p>
     * 用于进行插值分析值的缩放比率，默认为1。
     * </p>
     */
    public double zValueScale = 1;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public InterpolationAnalystParameters() {
        super();
    }

}
