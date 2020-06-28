package com.supermap.imobilelite.data;

import java.io.Serializable;

/**
 * <p>
 * 数据集字段查询结果类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class GetFieldsResult implements Serializable {
    private static final long serialVersionUID = -4778785240447792438L;

    /**
     * <p>
     * 数据集字段查询成功后获得的字段信息数组。 
     * </p>
     */
    public String[] fieldNames;

    /**
     * <p>
     * 数据集字段查询成功后获得的字段访问路径信息数组。
     * 由字段资源的 url组成的数组， 通过单个字段资源可以获取字段的名称、类型、别名等字段详细信息。
     * </p>
     */
    public String[] childUriList;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public GetFieldsResult() {
        super();
    }
}
