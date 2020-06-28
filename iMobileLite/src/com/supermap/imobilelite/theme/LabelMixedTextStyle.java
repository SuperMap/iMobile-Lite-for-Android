package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ServerTextStyle;

/**
 * <p>
 * 文本复合风格类。
 * </p>
 * <p>
 * 该类主要用于对标签专题图中标签的文本内容进行风格设置。通过该类用户可以使标签的文字显示不同的风格，比如文本 “喜马拉雅山”，通过本类可以将前三个字用红色显示，后两个字用蓝色显示。<br>
 * 对同一文本设置不同的风格实质上是对文本的字符进行分段，同一分段内的字符具有相同的显示风格。对字符分段有两种方式，一种是利用分隔符对文本进行分段；另一种是根据分段索引值进行分段。<br>
 * 1. 利用分隔符对文本进行分段 比如用“&”作分隔符，它将文本“5&109”分为“5”和“109”两部分，在显示时，“5”和分隔符“&”使用同一个风格，字符串“109”使用相同的风格。<br>
 * 2. 利用分段索引值进行分段 文本中字符的索引值是以0开始的整数，比如文本“珠穆朗玛峰”，第一个字符（“珠”）的索引值为0，第二个字符（“穆”）的索引值为1，以此类推；当设置分段索引值为1，3，4，9时，字符分段范围相应的就是(-∞，1)，[1，3)，[3，4)，[4，9)，[9，+∞)，可以看出索引号为0的字符（即“珠” ）在第一个分段内，索引号为1，2的字符（即“穆”、“朗”）位于第二个分段内，索引号为3的字符（“玛”）在第三个分段内，索引号为4的字符（“峰”）在第四个分段内，其余分段中没有字符。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class LabelMixedTextStyle implements Serializable {
    private static final long serialVersionUID = -6789344491924563633L;

    /**
     * <p>
     * 默认的文本复合风格。
     * </p>
     */
    public ServerTextStyle defaultStyle;

    /**
     * <p>
     * 文本的分隔符，分隔符的风格采用默认风格，并且分隔符只能设置一个字符。
     * </p>
     */
    public String separator;

    /**
     * <p>
     * 文本的分隔符是否有效。
     * </p>
     */
    public Boolean separatorEnabled;

    /**
     * <p>
     * 分段索引值，分段索引值用来对文本中的字符进行分段。
     * </p>
     */
    public int[] splitIndexes;

    /**
     * <p>
     * 文本样式集合。文本样式集合中的样式用于不同分段内的字符。
     * </p>
     */
    public ServerTextStyle[] styles;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public LabelMixedTextStyle() {
        super();
    }

}
