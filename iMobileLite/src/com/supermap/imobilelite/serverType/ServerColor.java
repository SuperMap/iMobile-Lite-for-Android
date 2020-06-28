package com.supermap.imobilelite.serverType;

import java.io.Serializable;

/**
 * <p>
 * 颜色类。
 * </p>
 * <p>
 * 该类使用三原色（RGB）来表达颜色。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ServerColor implements Serializable {
    private static final long serialVersionUID = 6835700165213326178L;
    /**
     * <p>
     * 获取或设置红色默认值，默认值为255。
     * </p>
     */
    public int red = 255;
    /**
     * <p>
     * 获取或设置绿色值，默认值为0。
     * </p>
     */
    public int green;

    /**
     * <p>
     * 获取或设置蓝色值，默认为0。
     * </p>
     */
    public int blue;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ServerColor() {
        super();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param red
     * @param green
     * @param blue
     */
    public ServerColor(int red, int green, int blue) {
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

}
