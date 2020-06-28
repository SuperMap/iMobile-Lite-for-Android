package com.supermap.imobilelite.maps;

import java.util.Arrays;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 瓦片信息封装类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class Tile implements Comparable<Tile> {
    private static final String LOG_TAG = "com.supermap.maps.tile";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private String url;
    private final int zoomLevel;
    // private long id;
    // private Date createdOn;
    private final int pixelX;
    private final int pixelY;
    private final int x;
    private final int y;
    private int priority;
    private Bitmap bitmap;
    private byte[] bytes;
    private final String provider;
    private Rect rect;
    private String key;
    private String layerNameCache;
    private double scale;
    private boolean transparent = false;
    private String format = "png";
    private int tileSize = 256;
    private int epsgCode = -1;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param x 瓦片的列号。
     * @param y 瓦片的行号。
     * @param pX 瓦片左上对应的x像素坐标。
     * @param pY 瓦片左上对应的y像素坐标。
     * @param zoomLevel 缩放层级。
     * @param provider 服务类型，默认都是"rest-map"。
     * @param currentURL 瓦片对应的图层名。
     */
    public Tile(int x, int y, int pX, int pY, int zoomLevel, String provider, String layerName) {
        this.x = x;
        this.y = y;
        this.pixelX = pX;
        this.pixelY = pY;
        this.zoomLevel = zoomLevel;
        this.provider = provider;
        // if (currentURL != null) {
        // if (currentURL.contains("/")) {
        // try {
        // this.layerNameCache = URLDecoder.decode(currentURL.substring(currentURL.lastIndexOf("/") + 1), "utf-8");
        // } catch (UnsupportedEncodingException e) {
        // this.layerNameCache = currentURL.substring(currentURL.lastIndexOf("/") + 1);
        // }
        // } else {
        // this.layerNameCache = String.valueOf(currentURL.hashCode());
        // }
        // }
        this.layerNameCache = layerName;
        this.key = cacheKey();
    }

    /**
     * <p>
     * 获取瓦片的url。
     * </p>
     * @return 瓦片的url。
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * <p>
     * 设置瓦片的url。
     * </p>
     * @param url 瓦片的url。
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>
     * 获取瓦片左上对应的x像素坐标。
     * </p>
     * @return 左上对应的x像素坐标。
     */
    public int getPixelX() {
        return this.pixelX;
    }

    /**
     * <p>
     * 获取瓦片左上对应的y像素坐标。
     * </p>
     * @return 左上对应的y像素坐标。
     */
    public int getPixelY() {
        return this.pixelY;
    }

    /**
     * <p>
     * 获取瓦片所处的层级。
     * </p>
     * @return 瓦片层级。
     */
    public int getZoomLevel() {
        return this.zoomLevel;
    }

    /**
     * <p>
     * 获取瓦片的列号。
     * </p>
     * @return 瓦片的列号。
     */
    public int getX() {
        return this.x;
    }

    /**
     * <p>
     * 获取瓦片的行号。
     * </p>
     * @return 瓦片的行号。
     */
    public int getY() {
        return this.y;
    }

    // public TileType getTileType() {
    // return this.tileType;
    // }

    /**
     * <p>
     * 比较tile瓦片。
     * </p>
     * @param tile 待比较的瓦片。
     * @return 
     */
    public int compareTo(Tile tile) {
        return this.priority - tile.priority;
    }

    /**
     * <p>
     * 获取瓦片的服务类型。
     * </p>
     * @return 瓦片的服务类型。
     */
    public String getProvider() {
        return this.provider;
    }

    /**
     * <p>
     * 设置瓦片的bytes值。
     * </p>
     * @param bytes
     */
    public void setBytes(byte[] bytes) {
        if (bytes != null) {
            this.bytes = Arrays.copyOf(bytes, bytes.length);
        }
    }

    /**
     * <p>
     * 获取瓦片的bytes值。
     * </p>
     * @return 瓦片的bytes值。
     */
    public byte[] getBytes() {
        return this.bytes;
    }

    /**
     * <p>
     * 获取瓦片的矩形。
     * </p>
     * @return 瓦片的矩形。
     */
    public Rect getRect() {
        return this.rect;
    }

    /**
     * <p>
     * 设置瓦片的矩形。
     * </p>
     * @param rect 矩形对象。
     */
    public void setRect(Rect rect) {
        this.rect = rect;
    }

    /**
     * <p>
     * 获取瓦片对应的地图名。
     * </p>
     * @return 瓦片对应的地图名。
     */
    public String getLayerNameCache() {
        return this.layerNameCache;
    }

    /**
     * <p>
     * 生成瓦片的唯一标识。
     * </p>
     * @return
     */
    private String cacheKey() {
        StringBuilder key = new StringBuilder(24);
        // key.append(getProvider()).append("_");
        // key.append(getTileType()).append("_");
        // 瓦片做缓存时，考虑投影参数
        if (this.epsgCode > 0) {
            key.append(epsgCode).append("_");
        }
        // 设置固定比例尺和不设置的时候，层级不能唯一确认一个scale，level只代表比例尺数组的下标而已，需使用scale来唯一标识
        if (this.scale > 0) {
            key.append(Math.round(1 / scale)).append("_");
        } else {
            key.append(getZoomLevel()).append("_");
        }
        key.append(getX()).append("_");
        key.append(getY()).append("_");
        key.append(getLayerNameCache());// 考虑url的hashCode作为瓦片缓存键值的一部分
        if (this.transparent) {
            key.append("_t");
        }
        return key.toString();
    }

    /**
     * <p>
     * 获取瓦片的唯一标识。
     * </p>
     * @return 瓦片的唯一标识。
     */
    public String buildCacheKey() {
        return this.key;
    }

    /**
     * <p>
     * 设置瓦片的id号，暂时不公开。
     * </p>
     * @param id
     */
    // public void setId(long id) {
    // this.id = id;
    // }

    /**
     * <p>
     * 获取瓦片的id号，暂时不公开。
     * </p>
     * @return
     */
    // public long getId() {
    // return this.id;
    // }

    /**
     * <p>
     * 设置瓦片的创建日期，暂时不公开。
     * </p>
     * @param createdOn
     */
    // public void setCreatedOn(Date createdOn) {
    // this.createdOn = createdOn;
    // }

    /**
     * <p>
     * 获取瓦片的创建日期，暂时不公开。
     * </p>
     * @return
     */
    // public Date getCreatedOn() {
    // return this.createdOn;
    // }

    /**
     * <p>
     * 判断瓦片是否有效。
     * </p>
     * @return true表示有效，false表示无效。
     */
    public boolean isValid() {
        if ((getBitmap() != null) && (!getBitmap().isRecycled())) {
            return true;
        }
        return (getBytes() != null) && (getBytes().length > 0);
    }

    /**
     * <p>
     * 获取瓦片的Bitmap。
     * </p>
     * @return 瓦片的Bitmap。
     */
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    /**
     * <p>
     * 设置瓦片的Bitmap。
     * </p>
     * @param bitmap
     */
    public void setBitMap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * <p>
     * 获取瓦片所处的比例尺。
     * </p>
     * @return 瓦片的比例尺。
     */
    public double getScale() {
        return scale;
    }

    /**
     * <p>
     * 设置瓦片所处的比例尺。
     * </p>
     * @param scale 比例尺。
     */
    public void setScale(double scale) {
        this.scale = scale;
        // if(!this.key.contains(String.valueOf(Math.round(1/scale)))){
        this.key = cacheKey();
        // }
    }

    /**
     * <p>
     * 设置瓦片所属的坐标系的epsgcode。
     * </p>
     * @param epsgcode
     */
    public void setEpsgCodes(int epsgcode) {
        this.epsgCode = epsgcode;
        if (!this.key.contains(String.valueOf(epsgcode))) {
            this.key = cacheKey();
        }
    }

    /**
     * <p>
     * 获取瓦片是否透明。
     * </p>
     * @return true表示透明，false表示不透明。
     */
    public boolean isTransparent() {
        return transparent;
    }

    /**
     * <p>
     * 设置瓦片是否透明。
     * </p>
     * @param transparent 是否透明。
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
        if ((this.key.contains("_t") && !transparent) || (!this.key.contains("_t") && transparent)) {
            this.key = cacheKey();
        }
    }

    /**
     * <p>
     * 获取瓦片的图片格式类型，暂时不公开。
     * </p>
     * @return
     */
    // public String getFormat() {
    // return format;
    // }

    /**
     * <p>
     * 设置瓦片的图片格式类型，暂时不公开。
     * </p>
     * @param format
     */
    // public void setFormat(String format) {
    // this.format = format;
    // }

    /**
     * <p>
     * 获取瓦片的大小，暂时不公开。
     * </p>
     * @return
     */
    // public int getTileSize() {
    // return tileSize;
    // }

    /**
     * <p>
     * 设置瓦片的大小，暂时不公开。
     * </p>
     * @param tileSize
     */
    // public void setTileSize(int tileSize) {
    // this.tileSize = tileSize;
    // }

    // public Tile copy() {
    // return new Tile(this.x, this.y, this.pixelX, this.pixelY, this.zoomLevel, this.provider);
    // }

    /**
     * <p>
     * 生成hashCode。
     * </p>
     * @return hashCode。
     */
    public int hashCode() {
        return buildCacheKey().hashCode();
    }

    /**
     * <p>
     * 比较是否相等。
     * </p>
     * @param obj 待比较对象。
     * @return true表示相等，false表示不相等。
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        return buildCacheKey().equals(other.buildCacheKey());
    }

    /**
     * <p>
     * 瓦片的整体描述字符串。
     * </p>
     * @return 瓦片的描述字符串。
     */
    public String toString() {
        return "Tile [layerNameCache=" + this.layerNameCache + ", pixelX=" + this.pixelX + ", pixelY=" + this.pixelY + ", url=" + this.url + ", x=" + this.x
                + ", y=" + this.y + ", zoomLevel=" + this.zoomLevel + "]";
    }
}