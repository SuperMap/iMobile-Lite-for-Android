package com.supermap.imobilelite.theme;

import com.supermap.imobilelite.serverType.ServerStyle;

/**
 * <p>
 * 点密度专题图。
 * </p>
 * <p>
 * 点密度专题图使用点的个数或密集程度来反映一个区域或范围某一专题数据的值，因此只有面数据才能制作点密度专题图。<br>
 * 点密度专题图的一个点代表了一定数值，则一个区域内点的个数乘以一个点所表示的数值就是此区域对应的专题数据的值。例如指定一个点代表1000，则若一个区域的人口为1000000，则该区域点的个数为1000个。影响点密度专题图显示风格和效果的参数主要是：点代表的值和点风格。<br>
 * 如下图所示，中部六省地图中，对1990年人口数量使用点密度来表示，设置基准值为10万人（即一个点代表10万人）：<br>
 * <img src="../../../../resources/Theme/ThemeDotDensity.png">
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeDotDensity extends Theme {
    private static final long serialVersionUID = -2804930817470771581L;

    /**
     * <p>
     * 用于创建点密度专题图的字段或字段表达式，字段或字段表达式应为数值型。必设参数。
     * </p>
     */
    public String dotExpression;

    /**
     * <p>
     * 定义点密度专题图中用于渲染的符号显示样式。
     * </p>
     */
    public ServerStyle style;

    /**
     * <p>
     * 专题图中每一个点所代表的数值，即基准值。单位同dotExpression属性，默认为200。
     * </p>
     */
    public double value = 200;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeDotDensity() {
        super();
        type = ThemeType.DOTDENSITY;
    }

}
