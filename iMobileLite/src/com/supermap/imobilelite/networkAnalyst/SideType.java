package com.supermap.imobilelite.networkAnalyst;

/**
 * <p>
 * 行驶位置枚举。
 * </p>
 * <p>
 * 表示行驶在路的左边、右边或者路上的枚举，用于行驶引导子项类（PathGuideItem）类中。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum SideType {

    /**
     * <p>
     * 路的左侧
     * </p>
     */
    LEFT,

    /**
     * <p>
     * 在路上（即路的中间）
     * </p>
     */
    MIDDLE,

    /**
     * <p>
     * 无效值
     * </p>
     */
    NONE,

    /**
     * <p>
     * 路的右侧
     * </p>
     */
    RIGHT;
}
