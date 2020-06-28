package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.imobilelite.serverType.ColorGradientType;
import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 单值专题图。
 * </p>
 * <p>
 * 是将专题值相同的要素归为一类，为每一类设定一种渲染风格，如颜色或符号等，专题值相同的要素采用相同的渲染风格，从而区分不同的类别。 例如，在表示土地的面数据中表示土地利用类型的字段中有草地，林地，居民地，耕地等值，使用单值专题图进行渲染时，每种类型的土地利用类型被赋予一种颜色或填充风格，从而可以看出每种类型的土地利用的分布区域和范围。可用于地质图、地貌图、植被图、土地利用图、政治行政区划图、自然区划图、经济区划图等。
 * </p>
 * <p>
 * 单值专题图着重表示现象质的差别，一般不表示数量的特征。尤其是有交叉或重叠现象时，此类不推荐使用，例如：民族分布区等。
 * </p>
 * <p>
 * 下图为山东省行政区划单值专题图，下图中每一种颜色代表一个行政区：<br>
 * <img src="../../../../resources/Theme/ThemeUnique_iServer6.bmp">
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeUnique extends Theme {
    private static final long serialVersionUID = -5912253190973275177L;

    /**
     * <p>
     * 颜色渐变类型。默认为ColorGradientType.YELLOWBLUE,表示由黄到绿进行渐变。
     * </p>
     */
    public ColorGradientType colorGradientType = ColorGradientType.YELLOWBLUE;

    /**
     * <p>
     * ServerStyle对象，单值专题图的默认风格。
     * </p>
     */
    public ServerStyle defaultStyle;

    /**
     * <p>
     * 单值专题图子项类数组。
     * </p>
     */
    public ThemeUniqueItem[] items;

    /**
     * <p>
     * 单值专题图字段表达式。
     * </p>
     */
    public String uniqueExpression;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeUnique() {
        super();
        type = ThemeType.UNIQUE;
    }

}
