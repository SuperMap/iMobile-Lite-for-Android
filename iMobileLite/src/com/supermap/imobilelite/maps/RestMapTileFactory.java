package com.supermap.imobilelite.maps;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 目前被遗弃不用了，该类就是生产瓦片而已，把这个集成到图层类去了
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
class RestMapTileFactory implements TileFactory {
    private static final String LOG_TAG = "com.supermap.android.maps.restmaptilefactory";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");

    private static final int TILE_SIZE = 256;
    LayerView layerView;
    ProjectionUtil projection;
    private TileType type = TileType.MAP;
    Point reuse = new Point();
    protected String baseUrl = "";

    public RestMapTileFactory(LayerView layerView) {
        this.layerView = layerView;
        this.projection = new ProjectionUtil(layerView);
    }

    /**
     * 重新设置投影
     */
    void setProjection(ProjectionUtil prj) {
        this.projection = prj;
    }

    public MapProvider getMapProvider() {
        return MapProvider.REST;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }

    protected String getTileURL(Tile tile) {
        // Log.d(LOG_TAG, resource.getMessage(MapCommon.RESTMAPTILEFACTORY_GETTILEURL_START));
        int index = layerView.getResolutionIndex();
        if (index == -1) {
            return "";
        }
        double scale = layerView.dpi / layerView.mapView.getRealResolution();
        double[] resolutions = layerView.getResolutions();
        if (resolutions != null && index < resolutions.length) {
            // 因为layer的resolution和map的resolution存在可允许的误差，但是计算出图比例尺使用layer的resolution
            scale = layerView.dpi / resolutions[index];
        }
        // double scale = layerView.dpi/layerView.mapView.getResolution();
        String result = "";
        tile.setScale(scale);
        boolean transparent = layerView.isTransparent();
        tile.setTransparent(transparent);
        result = getURL() + "/tileImage.png?width=256&height=256&layersID=&transparent=" + String.valueOf(transparent) + "&cacheEnabled="
                + layerView.isCacheEnabled() + "&scale=" + scale + "&x=" + tile.getX() + "&y=" + tile.getY();
        CoordinateReferenceSystem crs = layerView.getCRS();
        if (crs != null && crs.wkid > 0) {
            result += "&prjCoordSys=%7B%22epsgCode%22%3A" + crs.wkid + "%7D";
        }
        return result;
    }

    public Tile buildTile(Tile tile, int zoom, TileType type) {
        // tile = tile.copy();
        tile.setUrl(getTileURL(tile));
        return tile;
    }

    public Tile buildTile(int x, int y, int zoom, TileType type) {
        if (zoom < 0) {
            // Log.e(LOG_TAG, resource.getMessage(MapCommon.RESTMAPTILEFACTORY_ZOOM));
            return null;
        }
        // Log.d(LOG_TAG,
        // resource.getMessage(MapCommon.RESTMAPTILEFACTORY_BUILDTILE_START, new String[] { String.valueOf(x), String.valueOf(y), String.valueOf(zoom) }));
        Point globalPoint = this.projection.getGlobalFromScreen(x, y, this.reuse);
        //if (globalPoint != null) {
            Rect imageSize = this.projection.getMapImageSize();
            // 防止迭代器中出现死循环，此处逻辑放到迭代器内部处理
            // 如果超出边界128像素，则返回空
            /*int offsetPixel = 128;
            if (globalPoint.x < imageSize.left - 256 - offsetPixel) {
                return null;
            }
            if (globalPoint.x > imageSize.right + offsetPixel) {
                return null;
            }
            if (globalPoint.y > imageSize.bottom + offsetPixel) {
                return null;
            }
            if (globalPoint.y < imageSize.top - 256 - offsetPixel) {
                return null;
            }*/

            int tileX = (globalPoint.x - imageSize.left) / 256;
            int tileY = (globalPoint.y - imageSize.top) / 256;
            int tileGlobalX = tileX * 256 + imageSize.left;
            int tileGlobalY = tileY * 256 + imageSize.top;

            x += tileGlobalX - globalPoint.x;
            y += tileGlobalY - globalPoint.y;
            // Log.d(LOG_TAG,
            // resource.getMessage(MapCommon.RESTMAPTILEFACTORY_TILEGLOBAL, new String[] { String.valueOf(tileGlobalX), String.valueOf(tileGlobalY) }));
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.RESTMAPTILEFACTORY_XY, new String[] { String.valueOf(x), String.valueOf(y) }));
            Rect rect = new Rect(x, y, x + 256, y + 256);
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.RESTMAPTILEFACTORY_NEWTILE, String.valueOf(type)));

            Tile tile = new Tile(tileX, tileY, tileGlobalX, tileGlobalY, zoom, getProvider(), layerView.getLayerCacheFileName());
            // 瓦片做缓存时，考虑投影参数
            if (layerView.getCRS() != null && layerView.getCRS().wkid > 0) {
                tile.setEpsgCodes(layerView.getCRS().wkid);
            }
            tile.setUrl(getTileURL(tile));
            Log.d(LOG_TAG, resource.getMessage(MapCommon.RESTMAPTILEFACTORY_GETTILEURL, tile.getUrl()));
            tile.setRect(rect);
            return tile;
        //}
        //return null;
    }

    public Tile buildTile(Point2D gp, int zoom, TileType type) {
        Point point = this.projection.toGlobalPixels(gp, null);
        int x = point.x >> 8;
        int y = point.y >> 8;
        Tile tile = new Tile(x, y, x * 256, y * 256, zoom, getProvider(), null);
        tile.setUrl(getTileURL(tile));
        return tile;
    }

    protected String getURL() {
        return baseUrl;
    }

    protected String getProvider() {
        return "rest-map";
    }

    public ProjectionUtil getProjection() {
        return this.projection;
    }

    public TileType getTileType() {
        return this.type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isSupportedTileType(TileType tileType) {
        return tileType == TileType.MAP;
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.trim().equals("")) {
            return;
        }
        this.baseUrl = baseUrl;
    }
}