package com.supermap.imobilelite.theme;

import java.io.Serializable;

/**
 * <p>
 * 专题图基类
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public abstract class Theme implements Serializable {
    private static final long serialVersionUID = 7441786869464764438L;

    /**
     * <p>
     * 专题图内存数据。
     * </p>
     * <p>
     * 制作专题图时，如果设置了内存数据，则会制作基于内存数据的专题图。具体实现为：在使用专题值制作专题图后，会用外部值代替专题值来制作相应的专题图。
     * 第一个参数代表专题值，是数据集中用来做专题图的字段或表达式的值；第二个参数代表外部值。
     * </p>
     */
    public ThemeMemoryData themeMemoryData;

    /**
     * <p>
     * 专题图类型,子类中已指定默认值。
     * </p>
     */
    public ThemeType type;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public Theme() {
        super();
    }

}
