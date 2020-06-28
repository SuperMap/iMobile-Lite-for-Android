package com.supermap.imobilelite.maps.ogc.wmts;

/**
 * <p>
 * 瓦片矩阵信息类。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class TileMatrix {
    /**
     * <p>
     * 瓦片矩阵名称。
     * </p>
     * @since 7.0.0
     */
    public String id;
    /**
     * <p>
     * 当前瓦片矩阵的比列尺。
     * </p>
     * @since 7.0.0
     */
    public double scaleDenominator;
    /**
     * <p>
     * 瓦片矩阵左上角地理坐标点。
     * </p>
     * @since 7.0.0
     */
    public String topLeftCorner;
    /**
     * <p>
     * 瓦片矩阵所包含的单个瓦片高度，单位：像素。
     * </p>
     * @since 7.0.0
     */
    public int tileHeight;
    /**
     * <p>
     * 瓦片矩阵所包含的单个瓦片宽度，单位：像素。
     * </p>
     * @since 7.0.0
     */
    public int tileWidth;
    /**
     * <p>
     * 瓦片矩阵行数
     * </p>
     * @since 7.0.0
     */
    public int matrixHeight;
    /**
     * <p>
     * 瓦片矩阵列数
     * </p>
     * @since 7.0.0
     */
    public int matrixWidth;

    public TileMatrix() {
        super();
    }

    public TileMatrix(String id, double scaleDenominator, String topLeftCorner, int tileHeight, int tileWidth, int matrixHeight, int matrixWidth) {
        super();
        this.id = id;
        this.scaleDenominator = scaleDenominator;
        this.topLeftCorner = topLeftCorner;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.matrixHeight = matrixHeight;
        this.matrixWidth = matrixWidth;
    }

}
