package com.supermap.imobilelite.data;

import java.io.Serializable;

/**
 * <p>
 * 数据集编辑结果类。
 * </p>
 * <p>
 * 该类包含了编辑数据是否成功、编辑后的数据服务资源的地址。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class EditFeaturesResult implements Serializable {
    private static final long serialVersionUID = -2336869168537690925L;

    /**
     * <p>
     * 返回已新增地物的 SmID 数组。通过该字段可知道新增地物的 SmID。当删除或更新地物时，该字段为 null。 
     * </p>
     */
    public int[] IDs;

    /**
     * <p>
     * 返回资源是否成功。
     * </p>
     */
    public boolean succeed;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public EditFeaturesResult() {
        super();
    }
}
