package com.supermap.imobilelite.maps;

import com.supermap.services.components.commontypes.PixelGeometry;

/**
 * <p>
 * 矢量地物信息封装类，用于保存从读取的矢量地物信息
 * </p>
 * @author ${Author}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
class VectorGeometryData {
    public int fid; // 地物的id
    public String tile_id;// r_x_y
    public String layer;// 图层名
    public PixelGeometry geometry_data;// 地物数据信息

    public VectorGeometryData(int fid, String tile_id, String layer, PixelGeometry geometry_data) {
        super();
        this.fid = fid;
        this.tile_id = tile_id;
        this.layer = layer;
        this.geometry_data = geometry_data;
    }

    public VectorGeometryData() {
        super();
    }
}
