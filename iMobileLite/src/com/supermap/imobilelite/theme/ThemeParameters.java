package com.supermap.imobilelite.theme;

import java.io.Serializable;

import com.supermap.services.components.commontypes.JoinItem;

/**
 * <p>
 * 专题图基数类。
 * </p>
 * <p>
 * 该类存储了制作专题图时所需的参数，包括数据源名称、数据集名称以及专题图数组。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ThemeParameters implements Serializable {
    private static final long serialVersionUID = -270512301272137548L;
    /**
     * <p>
     * 获取或设置制作专题图的数据集名称集合。必设。
     * </p>
     */
    public String[] datasetNames;
    /**
     * <p>
     * 获取或设置制作专题图的数据源名称集合。必设。
     * </p>
     */
    public String[] dataSourceNames;
    /**
     * <p>
     * 专题图过滤条件数组。
     * </p>
     */
    public String[] displayFilters;

    /**
     * <p>
     * 专题图对象列表。该参数为实例化的各类专题图对象的集合。
     * </p>
     */
    public Theme[] themes;

    /**
     * <p>
     * 设置与外部表的连接信息JionItem数组。使用此属性可以制作与外部表连接的专题图。
     * </p>
     */
    public JoinItem[] joinItems;

    /**
     * <p>
     * 专题图对象生成符号叠加次序排序字段。
     * </p>
     */
    public String[] displayOrderBy;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ThemeParameters() {
        super();
    }

}
