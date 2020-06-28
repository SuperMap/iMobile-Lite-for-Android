package com.supermap.imobilelite.theme;

/**
 * <p>
 * 统计专题图文本显示样式。
 * </p>
 * <p>
 * 在统计专题图中，可以设置各子项文本的显示形式，包括百分数PERCENT、真实数值VALUE、标题CAPTION、标题+百分数CAPTION_PERCENT、标题+真实数值CAPTION_VALUE五种形式。以下图示均以三维柱状图为例。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum ThemeGraphTextFormat {
    /**
     * <p>
     * 标题，以各子项的标题来进行标注。
     * </p>
     */
    CAPTION,
    /**
     * <p>
     * 标题+百分数。以各子项的标题和所占的百分比来进行标注。
     * </p>
     */
    CAPTION_PERCENT,
    /**
     * <p>
     * 标题+实际数值。以各子项的标题和真实数值来进行标注。
     * </p>
     */
    CAPTION_VALUE,
    /**
     * <p>
     * 百分数。以各子项所占的百分比来进行标注。
     * </p>
     */
    PERCENT,
    /**
     * <p>
     * 实际数值。以各子项的真实数值来进行标注。
     * </p>
     */
    VALUE;

}
