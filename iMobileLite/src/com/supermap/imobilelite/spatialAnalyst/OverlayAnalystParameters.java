package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 叠加分析参数基类。
 * </p>
 * <p>
 * 叠加分析是 GIS 中的一项非常重要的空间分析功能。是指在统一空间参考系统下，通过对两个数据集或两个几何对象进行的一系列集合运算，产生新数据集或几何对象的过程。叠加分析广泛应用于资源管理、城市建设评估、国土管理、农林牧业、统计等领域。因此，通过此叠加分析类可实现对空间数据的加工和分析，提取用户需要的新的空间几何信息，并且对数据的属性信息进行处理。
 * 可以对数据集或某个几何对象进行叠加分析，这两类叠加分析功能的参数类（DatasetsOverlayAnalystParameters 和 GeometryOverlayAnalystParameters）均继承了该基类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class OverlayAnalystParameters implements Serializable {
    private static final long serialVersionUID = 8452553219387154654L;
    /**
     * <p>
     * 叠加操作类型，由 OverlayOperationType 类定义。
     * 叠加操作有：裁剪（Clip）、擦除（Erase）、合并（Union）、相交（Intersect）、同一（Identity）、对称差（XOR）和更新（Update）。默认为 OverlayOperationType.UNION。
     * </p>
     */
    public OverlayOperationType operation = OverlayOperationType.UNION;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public OverlayAnalystParameters() {
        super();
    }
}
