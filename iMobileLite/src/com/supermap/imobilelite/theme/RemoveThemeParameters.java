package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 移除专题图参数类。
 * </p>
 * <p>
 * 移除专题图就是通过资源的ID号移除在服务端对应的资源。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class RemoveThemeParameters implements Serializable {
    private static final long serialVersionUID = -5664676807927568483L;
    /**
     * <p>
     * 要移除的专题图资源的ID号。这个资源ID号再生成专题图的结果类ThemeResult中可以获取。
     * </p>
     */
    public String newResourceID;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public RemoveThemeParameters() {
        super();
    }

}
