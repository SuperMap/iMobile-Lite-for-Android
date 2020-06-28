package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 标签沿线标注样式类。
 * </p>
 * <p>
 * 通过该类可以设置标签是否沿线标注以及沿线标注的样式。沿线标注属性只适用于线数据集专题图。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeLabelAlongLine implements Serializable {
    private static final long serialVersionUID = 2375835568261204980L;
    /**
     * <p>
     * 是否沿线显示文本。true表示沿线显示文本，false表示正常显示文本，默认为true。
     * </p>
     */
    public Boolean alongLine = true;
    /**
     * <p>
     * 标签沿线标注方向。默认为AlongLineDirection.LEFT_BOTTOM_TO_RIGHT_TOP。
     * </p>
     */
    public AlongLineDirection alongLineDirection = AlongLineDirection.LEFT_BOTTOM_TO_RIGHT_TOP;
    /**
     * <p>
     * 当沿线显示文本时，是否将文本角度固定，默认为false。
     * </p>
     */
    public Boolean angleFixed = false;
    /**
     * <p>
     * 沿线标注时是否进行循环标注，默认为false，表示不循环标注。
     * </p>
     */
    public Boolean isLabelRepeated = false;
    /**
     * <p>
     * 沿线且循环标记时标签之间的间隔，默认值为0，单位为地图单位。
     * </p>
     */
    public double labelRepeatInterval;
    /**
     * <p>
     * 沿线循环标注时是否避免标签重复标注，默认为false，表示不避免重复标注。
     * </p>
     */
    public Boolean repeatedLabelAvoided = false;
    /**
     * <p>
     * 循环标注间隔是否固定，默认为false。
     * </p>
     */
    public Boolean repeatIntervalFixed = false;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeLabelAlongLine() {
        super();
    }

}
