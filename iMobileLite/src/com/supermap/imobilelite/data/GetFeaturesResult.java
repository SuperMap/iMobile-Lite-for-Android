package com.supermap.imobilelite.data;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Feature;

/**
 * <p>
 * 数据集查询结果类。
 * </p>
 * <p>
 * 该类包含了从服务端返回的查询结果，包括查询到的要素的个数和要素集合。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFeaturesResult implements Serializable {
    private static final long serialVersionUID = -8012016193293155431L;

    /**
     * <p>
     * 返回查询到的要素总个数，即符合条件的所有要素数目。
     * </p>
     */
    public int featureCount;

    /**
     * <p>
     * 返回查询到的矢量要素（Feature）集合。
     * 从矢量要素的 geometry 字段可以得到该要素的几何信息，从 attribute 字段可以获得该矢量要素的属性信息（包括 ID、名称等）。
     * 注意：矢量要素的 style 属性为 null，若需在客户端显示用户需首先定义 syle。
     * </p>
     */
    public Feature[] features;

    public GetFeaturesResult() {
        super();
    }
}
