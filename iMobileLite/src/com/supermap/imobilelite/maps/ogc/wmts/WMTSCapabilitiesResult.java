package com.supermap.imobilelite.maps.ogc.wmts;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * WMTS 服务元数据信息类，该类用于封装 WMTS 服务元数据信息。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class WMTSCapabilitiesResult {
    /**
     * <p>
     * 图层信息集合。该集合中的每个元素类型为 WMTSLayerInfo
     * </p>
     * @since 7.0.0
     */
    public List<WMTSLayerInfo> layerInfos;
    /**
     * <p>
     * 当前 WMTS 服务版本号
     * </p>
     * @since 7.0.0
     */
    public String version = "1.0.0";
    /**
     * <p>
     * 瓦片矩阵集信息集合。该集合中的每个元素表示一个矩阵集（WMTSTileMatrixSetInfo 类型），矩阵集中又包含多个矩阵，地图分块后的瓦片分别存储于各个矩阵中。
     * </p>
     * @since 7.0.0
     */
    public List<WMTSTileMatrixSetInfo> tileMatrixSetInfos;

    public WMTSCapabilitiesResult() {
        super();
        layerInfos = new ArrayList<WMTSLayerInfo>();
        tileMatrixSetInfos = new ArrayList<WMTSTileMatrixSetInfo>();
    }

    public WMTSCapabilitiesResult(List<WMTSLayerInfo> layerInfos, String version, List<WMTSTileMatrixSetInfo> tileMatrixSetInfos) {
        super();
        this.layerInfos = layerInfos;
        this.version = version;
        this.tileMatrixSetInfos = tileMatrixSetInfos;
    }

    public WMTSCapabilitiesResult(List<WMTSLayerInfo> layerInfos, List<WMTSTileMatrixSetInfo> tileMatrixSetInfos) {
        super();
        this.layerInfos = layerInfos;
        this.tileMatrixSetInfos = tileMatrixSetInfos;
    }

}
