package com.supermap.imobilelite.maps.ogc.wmts;

import java.util.List;

/**
 * <p>
 * WMTS 服务瓦片矩阵集信息类,该类用于封装 WMTS 服务中的瓦片矩阵集信息。
 * 图层、瓦片矩阵集、瓦片矩阵三者之间的关系：图层由多个瓦片矩阵集组成，瓦片矩阵集由多个瓦片矩阵组成，地图分块后的瓦片分别存储于各个瓦片矩阵中。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class WMTSTileMatrixSetInfo {
    /**
     * <p>
     * 瓦片矩阵集的唯一标识符。
     * </p>
     * @since 7.0.0
     */
    public String name;
    /**
     * <p>
     * 坐标参考系。
     * </p>
     * @since 7.0.0
     */
    public String supportedCRS;
    /**
     * <p>
     * 通用比例尺集，SuperMap iServer 目前支持 GlobalCRS84Scale、Degree_GlobalCRS84Pixel、Meter_GlobalCRS84Pixel、GoogleCRS84Quad 和 GoogleMapsCompatible 五种类型的通用比例尺集。
     * </p>
     * @since 7.0.0
     */
    public String wellKnownScaleSet;
    /**
     * <p>
     * 瓦片矩阵集合。该集合中的每个元素表示一个瓦片矩阵（TileMatrix 类型），地图分块后的瓦片分别存储于各个矩阵中。
     * </p>
     * @since 7.0.0
     */
    public List<TileMatrix> tileMatrixs;

    public WMTSTileMatrixSetInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public WMTSTileMatrixSetInfo(String name, String supportedCRS, String wellKnownScaleSet, List<TileMatrix> tileMatrixs) {
        super();
        this.name = name;
        this.supportedCRS = supportedCRS;
        this.wellKnownScaleSet = wellKnownScaleSet;
        this.tileMatrixs = tileMatrixs;
    }

}
