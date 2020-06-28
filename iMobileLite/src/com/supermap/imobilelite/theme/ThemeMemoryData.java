package com.supermap.imobilelite.theme;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 专题图内存数据类。
 * </p>
 * <p>
 * 目前 iClient Flex 提供的所有专题图类型均支持内存数组专题图，该类主要为制作内存数组专题图（除统计专题图，因为它有自己的一套内存数据设置逻辑）提供内存数据信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeMemoryData implements Serializable {
    private static final long serialVersionUID = -8797535037529427347L;
    /**
     * <p>
     * 专题图内存数据。<br>
     * 制作专题图时，如果设置了内存数据，则会制作基于内存数据的专题图。具体实现为：在使用专题值制作专题图后，会用外部值代替专题值来制作相应的专题图。
     * 第一个参数代表专题值，是数据集中用来做专题图的字段或表达式的值；第二个参数代表外部值。
     * </p>
     */
    public Map<String, String> memoryData;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeMemoryData() {
        super();
    }

}
