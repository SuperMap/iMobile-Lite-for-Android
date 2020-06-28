package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.commons.ResourceInfo;

/**
 * <p>
 * 服务端返回的专题图结果类。
 * </p>
 * <p>
 * 该类中包含了服务端生成的专题图资源信息（ResourceInfo）。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeResult implements Serializable {
    private static final long serialVersionUID = -7377581555574393960L;
    /**
     * <p>
     * 初始化ThemeResult的新实例。
     * </p>
     */
    public ResourceInfo resourceInfo;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeResult() {
        super();
    }

}
