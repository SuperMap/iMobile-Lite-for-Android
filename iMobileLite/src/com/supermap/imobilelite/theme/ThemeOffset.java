package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 专题图中的渲染符号的偏移量设置类。
 * </p>
 * <p>
 * 通过该类可以设置专题图中渲染符号的偏移量以及偏移量是否随地图缩放而改变。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ThemeOffset implements Serializable {
    private static final long serialVersionUID = -7656859750804749535L;
    /**
     * <p>
     * 当前专题图是否固定渲染符号的偏移量。
     * </p>
     */
    public Boolean offsetFixed;
    /**
     * <p>
     * 专题图中渲染符号的水平偏移量，当ThemeOffset.offsetFixed =true时，该属性单位为像素，反之为地图单位。
     * </p>
     */
    public String offsetX;
    /**
     * <p>
     * 专题图中渲染符号垂直偏移量。当ThemeOffset.offsetFixed =true时，该属性单位为像素，反之为地图单位。
     * </p>
     */
    public String offsetY;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeOffset() {
        super();
    }

}
