package com.supermap.imobilelite.serverType;

import java.io.Serializable;

/**
 * <p>
 * 文本对象的风格。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ServerTextStyle implements Serializable {
    private static final long serialVersionUID = -2456543743873618142L;
    /**
     * <p>
     * 注记文本的对齐方式，默认为TextAlignment.BASELINECENTER。
     * </p>
     */
    public TextAlignment align = TextAlignment.BASELINECENTER;
    /**
     * <p>
     * 注记文本的背景色。默认值为蓝色。
     * </p>
     */
    public ServerColor backColor;
    /**
     * <p>
     * 注记背景是否透明。默认为false，即透明。
     * </p>
     */
    public Boolean backOpaque = false;
    /**
     * <p>
     * 注记对象是否为粗体字。默认为false，表示非粗体。
     * </p>
     */
    public Boolean bold = false;
    /**
     * <p>
     * 文本字体的高度，默认值为10，单位与sizeFixed有关。
     * </p>
     */
    public double fontHeight = 10;
    /**
     * <p>
     * 文本字体的名称。
     * </p>
     */
    public String fontName;
    /**
     * <p>
     * 文本字体的缩放比例。
     * </p>
     */
    public double fontScale;
    /**
     * <p>
     * 文本字体的磅数，表示粗体的具体数值。<br>
     * 取值范围为从0－900之间的整百数，如400表示正常显示，700表示为粗体，可参见微软 MSDN 帮助中关于 LOGFONT 类的介绍。 默认值为400。
     * </p>
     */
    public int fontWeight = 400;
    /**
     * <p>
     * 文本字体的宽度。
     * </p>
     */
    public double fontWidth;
    /**
     * <p>
     * 文本的前景色。
     * </p>
     */
    public ServerColor foreColor;
    /**
     * <p>
     * 文本是否采用斜体，默认为false，表示不采用斜体。
     * </p>
     */
    public Boolean italic = false;
    /**
     * <p>
     * 字体倾斜角度，正负度之间，以度为单位，精确到0.1度。
     * </p>
     */
    public double italicAngle;
    /**
     * <p>
     * 注记文字的不透明度，只对三维字体有效。
     * </p>
     */
    public int opaqueRate;
    /**
     * <p>
     * 是否以轮廓的方式来显示文本的背景，默认为false
     * </p>
     */
    public Boolean outline = false;
    /**
     * <p>
     * 文本旋转的角度。
     * </p>
     */
    public double rotation;
    /**
     * <p>
     * 文本是否有阴影，默认为false
     * </p>
     */
    public Boolean shadow = false;
    /**
     * <p>
     * 文本大小是否固定，默认为true
     * </p>
     */
    public Boolean sizeFixed = true;
    /**
     * <p>
     * 文本字体是否加删除线，默认为false
     * </p>
     */
    public Boolean strikeout = false;
    /**
     * <p>
     * 文本字体是否加下划线，默认为false
     * </p>
     */
    public Boolean underline = false;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ServerTextStyle() {
        super();
    }

}
