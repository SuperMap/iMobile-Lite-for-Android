package com.supermap.imobilelite.maps.ogc.wmts;

import java.util.List;

/**
 * <p>
 * WMTS 服务图层信息类，该类用于封装 WMTS 服务中的可操作图层信息,包括图层坐标系、地理范围、样式等等。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class WMTSLayerInfo {

    /**
    * <p>
    * 图层地理范围
    * </p>
    * @since 7.0.0
    */
    public BoundsWithCRS bounds;
    /**
    * <p>
    * 图层wGS84地理范围
    * </p>
    * @since 7.0.0
    */
    public BoundsWithCRS wgs84BoundingBox;
    /**
    * <p>
    * 当前图层默认使用的图片格式
    * </p>
    * @since 7.0.0
    */
    public String imageFormat;
    /**
    * <p>
    * 图层唯一标符
    * </p>
    * @since 7.0.0
    */
    public String name;
    /**
    * <p>
    * 图层样式
    * </p>
    * @since 7.0.0
    */
    public String style;
    /**
    * <p>
    * 图层标准比例尺集名称集合
    * </p>
    * @since 7.0.0
    */
    public List<String> tileMatrixSetLinks;
    /**
     * <p>
     * 当前图层支持的图片格式集合
     * </p>
     * @since 7.0.0
     */
    public String[] formats;
    /**
     * <p>
     * 当前图层在不同坐标系下的Bounds
     * </p>
     * @since 7.0.0
     */
    public List<BoundsWithCRS> boundingBoxes;

    public WMTSLayerInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public WMTSLayerInfo(BoundsWithCRS bounds, String imageFormat, String name, String style, List<String> tileMatrixSetLinks) {
        super();
        this.bounds = bounds;
        this.imageFormat = imageFormat;
        this.name = name;
        this.style = style;
        this.tileMatrixSetLinks = tileMatrixSetLinks;
    }

    public WMTSLayerInfo(BoundsWithCRS bounds, BoundsWithCRS wGS84BoundingBox, String imageFormat, String name, String style, List<String> tileMatrixSetLinks) {
        super();
        this.bounds = bounds;
        this.wgs84BoundingBox = wGS84BoundingBox;
        this.imageFormat = imageFormat;
        this.name = name;
        this.style = style;
        this.tileMatrixSetLinks = tileMatrixSetLinks;
    }

}
