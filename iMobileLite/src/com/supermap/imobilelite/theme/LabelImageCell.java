package com.supermap.imobilelite.theme;

/**
 * <p>
 * 图片类型的矩阵标签元素类。
 * </p>
 * <p>
 * 该类型的对象可作为矩阵标签对象中的一个矩阵标签元素。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */

public class LabelImageCell extends LabelMatrixCell {
    private static final long serialVersionUID = -2628878671125853606L;

    /**
     * <p>
     * 图片的高度，默认为10，单位为毫米。
     * </p>
     */
    public double height = 10;

    /**
     * <p>
     * 记录了图片类型的矩阵标签元素所使用图片的路径的字段名称。
     * </p>
     */
    public String pathField;

    /**
     * <p>
     * 图片的旋转角度。
     * </p>
     */
    public double rotation;
    /**
     * <p>
     * 图片的大小是否固定。默认为false，表示不固定。
     * </p>
     */
    public Boolean sizeFixed = false;
    /**
     * <p>
     * 返回图片的宽度，默认为10，单位为毫米。
     * </p>
     */
    public double width = 10;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public LabelImageCell() {
        super();
    }

}
