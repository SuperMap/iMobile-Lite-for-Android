package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 用于裁剪的参数。优先使用用户指定的裁剪区域多边形进行裁剪，也可以通过指定数据源和数据集名，从而使用指定数据集的边界多边形进行裁剪。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ClipParameter implements Serializable {
    private static final long serialVersionUID = -8342165975661096177L;

    /**
     * <p>
     * 用于裁剪的数据集名，当clipRegion不设置时起作用。
     * </p>
     */
    public String clipDatasetName;

    /**
     * <p>
     * 用于裁剪的数据集所在数据源的名字。 
     * </p>
     */
    public String clipDatasourceName;

    /**
     * <p>
     * 用户指定的裁剪区域，优先使用。 
     * </p>
     */
    public Geometry clipRegion;

    /**
     * <p>
     * 是否对裁剪区内的数据集进行裁剪。 
     * </p>
     */
    public boolean isClipInRegion;

    /**
     * <p>
     * 是否使用精确裁剪。 
     * </p>
     */
    public boolean isExactClip;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public ClipParameter() {
        super();
    }
}
