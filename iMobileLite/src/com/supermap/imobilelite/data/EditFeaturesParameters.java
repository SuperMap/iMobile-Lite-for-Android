package com.supermap.imobilelite.data;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Feature;
/**
 * <p>
 * 数据集编辑参数类。
 * </p>
 * <p>
 * 数据集编辑是指对数据集中的矢量要素进行编辑，包括对要素进行增加、删除、修改的三种操作。通过字段 features 设置要增加或修改的矢量要素，对编辑的矢量要素还可以赋予几何信息和属性信息，服务器能将所编辑的矢量要素的几何信息和属性信息添加至或更新要素所在的数据集。如果要删除某些矢量要素，通过字段 IDs 直接指定要删除的要素的 ID 号即可。
该类则用于设置进行数据集编辑时所需的参数，如：要添加或修改的矢量要素对象 Feature 集合；要删除的矢量要素 ID 集合等。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class EditFeaturesParameters implements Serializable{
    private static final long serialVersionUID = 6706142958544441228L;

    /**
     * <p>
     * 对矢量要素进行编辑操作的类型，包括增加、删除和修改三个操作。默认为 EditType.ADD。 
     * </p>
     */
    public EditType editType = EditType.ADD;

    /**
     * <p>
     * 要增加或修改的地物要素集合。  
     * 当新增矢量要素时该字段必设，而字段 IDs 无需设置。当修改矢量要素时该属性中的 ID 值（存储在服务端的要被修改的要素 ID 号）与 features 中的矢量要素（编辑后的要素，存放在客户端，用来替换服务端对应的要素）是一一对应的，此时两者均为必设属性 。
     * </p>
     */
    public Feature[] features;

    /**
     * <p>
     * 要删除或修改的矢量要素 ID 号集合。
     * 即矢量要素 attributes 中的 SmID。 当 editType 为删除时（EditType.DELETE），该属性为必设，features 属性无需设置。当 editType 为修改时（EditType.UPDATE），该属性中的 ID 值（存储在服务端的要被修改的要素 ID 号）与 features 中的矢量要素（编辑后的要素，存放在客户端，用来替换服务端对应的要素）是一一对应的，此时两者均为必设属性 。
     * </p>
     */
    public int[] IDs;

    /**
     * <p>
     * 设置是否使用批量添加要素功能，要素添加时有效。默认为false。
     * 批量添加能够提高要素编辑效率。true 表示批量添加；false 表示不使用批量添加。
     * </p>
     */
    public boolean isUseBatch = false;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public EditFeaturesParameters() {
        super();
    }
}
