package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.QueryParameter;

/**
 * <p>
 * 数据集叠加分析参数类。
 * </p>
 * <p>
 * 该类主要提供进行叠加分析时，输出结果数据集的字段选择。在矢量叠加分析中至少涉及到三个数据集，其中一个数据集被称作源数据集，即被操作的数据集（在 SuperMap GIS 中称作第一数据集）；另一个数据集被称作叠加数据集，即操作数据集（在 SuperMap GIS 中称作第二数据集）；还有一个数据集就是叠加结果数据集，包含叠加后数据的几何信息和属性信息。叠加结果数据集中的属性信息来自于第一数据集和第二数据集的属性表，在进行叠加分析的时候，用户可以根据自己的需要在这两个数据集的属性表中选择需要保留的属性字段。
 * </p>
 * <p>
 * 叠加分析是 GIS 中的一项非常重要的空间分析功能。是指在统一空间参考系统下，通过对两个数据集或两个几何对象进行的一系列集合运算，产生新数据集或几何对象的过程。叠加分析广泛应用于资源管理、城市建设评估、国土管理、农林牧业、统计等领域。因此，通过此叠加分析类可实现对空间数据的加工和分析，提取用户需要的新的空间几何信息，并且对数据的属性信息进行处理。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class DatasetOverlayAnalystParameters extends OverlayAnalystParameters {
    private static final long serialVersionUID = 655839012598153802L;
    /**
     * <p>
     * 节点容限值。叠加分析后，若两个节点之间的距离小于此值，则将这两个节点合并。
     * </p>
     */
    public double tolerance;
    /**
     * <p>
     * 叠加分析中操作数据集的名称。必设字段。 
     * </p>
     */
    public String operateDataset;

    /**
     * <p>
     * 叠加分析中操作数据集保留在结果数据集中的字段名列表。当该参数为空时，默认返回所有字段。 
     * </p>
     */
    public String[] operateDatasetFields;

    /**
     * <p>
     * 设置操作数据集中空间对象的过滤条件。
     * 设置完过滤条件后，操作数据集中仅有满足条件的对象才参与叠加分析。若该参数为空，则表示操作数据集中的所有对象均参与叠加分析。
     * </p>
     */
    public QueryParameter operateDatasetFilter;

    /**
     * <p>
     * 操作面对象集合，表示与这些面对象进行叠加分析。与 operateDataset 参数互斥，冲突时以operateDataset 为准。
     * </p>
     */
    public Geometry[] operateRegions;

    /**
     * <p>
     * 分析结果参数。
     * </p>
     */
    public DataReturnOption resutlSetting;

    /**
     * <p>
     * 叠加分析中源数据集（即被操作数据集）的名称。必设字段。 
     * </p>
     */
    public String sourceDataset;

    /**
     * <p>
     * 叠加分析中源数据集保留在结果数据集中的字段名列表。当该参数为空时，默认返回所有字段。 
     * </p>
     */
    public String[] sourceDatasetFields;

    /**
     * <p>
     * 置源数据集中空间对象过滤条件 。
     * 设置完过滤条件后，源数据集中仅有满足条件的对象才参与叠加分析。若该参数为空，则表示操作数据集中的所有对象均参与叠加分析。
     * </p>
     */
    public QueryParameter sourceDatasetFilter;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public DatasetOverlayAnalystParameters() {
        super();
    }
}
