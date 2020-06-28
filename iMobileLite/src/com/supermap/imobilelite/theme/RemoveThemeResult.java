package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 服务端返回的移除专题图结果数据。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class RemoveThemeResult implements Serializable {
    private static final long serialVersionUID = 7973121635818692399L;
    /**
     * <p>
     * 删除专题图是否成功，true表示成功删除。
     * </p>
     */
    public Boolean succeed;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public RemoveThemeResult() {
        super();
    }

}
