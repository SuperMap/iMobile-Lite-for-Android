package com.supermap.imobilelite.data;

import java.io.Serializable;

/**
 * <p>
 * 字段查询统计服务结果类。
 * </p>
 * <p>
 * 存储字段查询统计的结果信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class FieldStatisticResult implements Serializable {
    private static final long serialVersionUID = 5964158956257458084L;

    /**
     * <p>
     * 数据集字段信息统计成功后返回的统计类型。
     * </p>
     */
    public String mode;

    /**
     * <p>
     * 数据集字段信息统计成功后获得的统计结果值。 
     * </p>
     */
    public double result;

    public FieldStatisticResult() {
        super();
    }
}
