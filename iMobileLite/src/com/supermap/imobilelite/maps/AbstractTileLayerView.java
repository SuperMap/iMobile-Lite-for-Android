package com.supermap.imobilelite.maps;

import org.apache.commons.lang3.StringUtils;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * <p>
 * 抽象图层视图，作为地图视图的子项添加到 {@link MapView} 中显示。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
public abstract class AbstractTileLayerView extends View {
    RMGLCanvas rmglCanvas=null;
    public double bottom,left,right,top;
    private static final String LOG_TAG = "com.supermap.android.maps.abstractTileLayerView";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    /**
     * <p>
     * 默认瓦片画笔风格。
     * </p>
     */
    private Paint defaultTilePaint = new Paint(1);
    /**
     * <p>
     * 自定义瓦片画笔风格。
     * </p>
     */
    private Paint customTilePaint = new Paint(1);
    /**
     * <p>
     * Activity的上下文。
     * </p>
     */
    protected Context context;
    private Rect rotRect = new Rect();
    /**
     * <p>
     * 瓦片数量总和。
     * </p>
     */
    protected int totalTileCount = 0;
    /**
     * <p>
     * 瓦片数量。
     * </p>
     */
    protected int tileCount = 0;
    /**
     * <p>
     * 图层是否可见。默认为true，图层可见。
     * </p>
     */
    protected boolean visible = true;
    /**
     * <p>
     * 图层是否初始化完成。默认为false。
     * </p>
     */
    protected boolean isLayerInited = false;
    /**
     * <p>
     * 图层是否为地理坐标系图层。默认为true。
     * </p>
     */
    protected boolean isGCSLayer = true;
    /**
     * <p>
     * 动态图层的发布地址url，即地图服务根地址，动态图层必设。
     * </p>
     */
    protected String curMapUrl = "";
    /**
     * <p>
     * 图层的名字标签。
     * </p>
     */
    protected String layerName = "layerName";
    /**
     * <p>
     * 用户设置图层的缓存文件名称，用于sd卡缓存。
     * </p>
     */
    protected String layerCacheFileName;
    /**
     * <p>
     * 当图层加入到mapView中时初始化。
     * </p>
     */
    protected MapView mapView = null;
    /**
     * <p>
     * 用户设置固定比例尺数组。
     * </p>
     */
    protected double[] visibleScales;
    /**
     * <p>
     * 用户设置固定分辨率数组。
     * </p>
     */
    protected double[] resolutions;
    /**
     * <p>
     * 用户设置坐标参考系。
     * </p>
     */
    protected CoordinateReferenceSystem crs;
    /**
     * <p>
     * 用户设置图层的地理范围。
     * </p>
     */
    protected BoundingBox layerBounds;
    // protected double defScale = 0;
    // protected double defResolution = 0;
    /**
     * <p>
     * 用户设置地图缩放级别。
     * </p>
     */
    protected int zoomLevel = -1;
    /**
     * <p>
     * 工具类负责屏幕像素坐标与地理坐标的相互转化。
     * </p>
     */
    protected Projection projection;
    /**
     * <p>
     * 地图默认分辨率（米每像素）和比例尺的乘积，跟dpi相关的一个运算值。
     * </p>
     */
    protected double dpi = -1;
    /**
     * <p>
     * 瓦片是否使用网络下载，默认为true，离线图层可以设置为false，暂时不对外公开
     * </p>
     */
    protected boolean addToNetworkDownload = true;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     */
    public AbstractTileLayerView(Context context) {
        super(context);
        initialize(context);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param attrs 属性信息。
     */
    public AbstractTileLayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param attrs 属性信息。
     * @param defStyle 风格标识。
     */
    public AbstractTileLayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context 为空。");
        }
        this.context = context;
        this.defaultTilePaint.setDither(true);
        this.defaultTilePaint.setFilterBitmap(true);
        this.customTilePaint.setDither(true);
        this.customTilePaint.setFilterBitmap(true);
    }

    /**
     * <p>
     * 初始化投影转换和坐标转化工具类Projection。
     * 其中ProjectionUtil 对象需要 mapView 的范围信息，所以添加初始化完成的图层后调用。
     * </p>
     */
    protected void initProjection() {
        this.projection = new Projection(this);
    }

    /**
     * <p>
     * 主绘制方法。
     * </p>
     * @param canvas 待绘制的画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Log.d(LOG_TAG, "layerView  onDraw");
        if (!this.visible) {
            return;
        }
        if (!this.isLayerInited) {
            return;
        }
        if (getProjection() == null)
            return;
        if ((getHeight() == 0) || (getWidth() == 0)) {
            return;
        }
        if (getResolutionIndex() == -1) {
            return;
        }
        if (canvas == null) {
            return;
        }

        try {
            if (this.mapView.currentScale != 1.0F) {
                // canvas.save();
                // Point out = this.getProjection().mapPoint(this.mapView.scalePoint.x, this.mapView.scalePoint.y, null);
                // this.getProjection().offsetToFocalPoint(out.x, out.y, out);
                // canvas.scale(1, 1, out.x, out.y);
                int zoom = this.mapView.scaling ? this.mapView.getZoomLevel() : (int) Math.round(this.mapView.getZoomLevel()
                        - Util.log2(this.mapView.currentScale));
                Point2D point = this.mapView.centerGeoPoint;
                // if ((this.mapView.scalePoint.x != this.mapView.focalPoint.x) || (this.mapView.scalePoint.y != this.mapView.focalPoint.y)) {
                // if (validateZoomLevel(zoom)) {
                // int tempZoomLevel = this.mapView.getZoomLevel();
                // Point2D tempCenterGeoPoint = this.mapView.centerGeoPoint;
                // this.mapView.centerGeoPoint = getProjection().fromPixels(this.mapView.scalePoint.x, this.mapView.scalePoint.y);
                // this.mapView.setZoomLevel(zoom);
                // int newCenterX = this.mapView.focalPoint.x + (this.mapView.focalPoint.x - this.mapView.scalePoint.x);
                // int newCenterY = this.mapView.focalPoint.y + (this.mapView.focalPoint.y - this.mapView.scalePoint.y);
                // point = getProjection().fromPixels(newCenterX, newCenterY);
                // this.mapView.setZoomLevel(tempZoomLevel);
                // this.mapView.centerGeoPoint = tempCenterGeoPoint;
                // }
                // }
                // Log.d(LOG_TAG, "layername:"+this.layerName+",mapView.centerGeoPoint,x:"+mapView.centerGeoPoint.x+",y:"+mapView.centerGeoPoint.y);
                drawTiles(canvas, point, zoom, true);
                // canvas.restore();
            }
            // preLoad();
            if ((!this.mapView.scaling) || (this.mapView.currentScale == 1.0F)) {
                // Log.d(LOG_TAG, "layername:"+this.layerName+",mapView.scaling:"+mapView.scaling+",currentScale:"+mapView.currentScale);
                boolean drawLoadingTile = this.mapView.currentScale == 1.0F;
                // if (this.mapView.currentScale != 1.0F) {
                // preLoad();
                // }
                // 双击和缩放按钮的缩放结束时，会设置scaling为false和currentScale为2或是0.5，此处修正currentScale为1，这样才不会出现用下一级别的瓦片再进行拉伸的现象。
                // this.mapView.currentScale = 1.0F;
                // Log.d(LOG_TAG, "layerView  onDraw drawTiles开始!!!,中心点为:\t" + this.mapView.centerGeoPoint.toString());
                // Log.d(LOG_TAG, "scaling mapView.centerGeoPoint,x:"+mapView.centerGeoPoint.x+",y:"+mapView.centerGeoPoint.y);
                int tileCount = drawTiles(canvas, this.mapView.centerGeoPoint, this.mapView.getZoomLevel(), drawLoadingTile);
                // Log.d(LOG_TAG, "layerView  onDraw drawTiles结束!!!");
                if ((tileCount >= 0.5 * this.totalTileCount) && (this.mapView.currentScale != 1.0F)) {
                    // Log.i(LOG_TAG, "layerView  setScale!!!");
                    setScale(1.0F, 1.0F, this.mapView.focalPoint.x, this.mapView.focalPoint.y);
                }
            }
            preLoad();
        } catch (Exception ex) {
            Log.e(LOG_TAG, resource.getMessage(MapCommon.LAYERVUEW_EXCEPTION, ex));
        }
    }

    private int drawTiles(Canvas canvas, Point2D geoPoint, int zoom, boolean drawLoadingTile) {
        // long start = System.currentTimeMillis();
        if ((getWidth() == 0) || (getHeight() == 0))
            return 0;
        if (this.getTileCacher() == null)
            return 0;
        if (zoom < 0) {
            return 0;
        }
        if (getResolutionIndex() == -1) {
            return 0;
        }
        int zoomLevel = this.mapView.getZoomLevel();
        Point2D centerGeoPoint = this.mapView.centerGeoPoint;

        if ((zoom != this.mapView.getZoomLevel()) && (zoom >= 0)) {
            this.mapView.setZoomLevel(zoom);
        }
        if (geoPoint != this.mapView.centerGeoPoint) {
            this.mapView.centerGeoPoint = geoPoint;
        }

        this.zoomLevel = this.mapView.getZoomLevel();

        this.tileCount = 0;
        this.totalTileCount = 0;
        try {
            iterateTiles(this.zoomLevel, TileType.MAP, false, canvas, drawLoadingTile);
            // Log.d(LOG_TAG, "drawTiles绘制一次所需的时间："+(System.currentTimeMillis()-start)+" ms");
            int i = this.tileCount;
            return i;
        } finally {
            if (zoomLevel != this.mapView.getZoomLevel())
                this.mapView.setZoomLevel(zoomLevel);
            if (centerGeoPoint != this.mapView.centerGeoPoint)
                this.mapView.centerGeoPoint = centerGeoPoint;
        }
    }

    void preLoad() {
        if (!this.isLayerInited) {// 没有初始化完成，不去迭代计算需要下载的瓦片（因未初始化完会发生异常）
            return;
        }
        if ((getMapWidth() == 0) || (getMapHeight() == 0))
            return;
        if (this.getTileCacher() == null)
            return;
        if (getZoomLevel() < 0) {
            return;
        }
        if (getResolutionIndex() == -1) {
            return;
        }

        this.getTileProvider().beginQueue();// 会清空下载队列
        try {
            iterateTiles(getZoomLevel(), TileType.MAP, true, null, false);
        } finally {
            this.getTileProvider().endQueue();
        }
    }

    /**
     * <p>
     * 获取当前层级的分辨率索引。
     * </p>
     * @return 当前层级的分辨率索引。
     */
    protected int getResolutionIndex() {
        double[] resolutions = getResolutions();
        int index = -1;
        if (resolutions != null && resolutions.length > 0) {
            double resolution = this.mapView.getRealResolution();
            for (int i = 0; i < resolutions.length; i++) {
                double ratio = resolution - resolutions[i];
                if (Math.abs(ratio) < Math.abs(resolution) / 100000.0) {
                    index = i;
                    break;
                }
            }
        } else {
            index = mapView.getZoomLevel();
        }
        // Log.i(LOG_TAG, "get layerView resolutionIndex:" + index);
        return index;
    }

    void setScale(float scaleX, float scaleY, float focusX, float focusY) {
        this.mapView.currentScale = scaleX;
        this.mapView.scalePoint.x = (int) focusX;
        this.mapView.scalePoint.y = (int) focusY;
        mapView.moved();
    }

    private void iterateTiles(int zoom, TileType type, boolean queueTile, Canvas canvas, boolean drawLoadingTile) {
        // long start = System.currentTimeMillis();
        if ((getWidth() == 0) || (getHeight() == 0)) {
            return;
        }
        if (this.getTileCacher() == null) {
            return;
        }
        if (zoom < 0 || getZoomLevel() < 0) {
            return;
        }

        int midY = this.mapView.focalPoint.y;
        int midX = this.mapView.focalPoint.x;
        int tileSize = (int) Math.round(256 * mapView.currentScale * mapView.getDensity());

        boolean positiveYFinished = false;
        boolean negativeYFinished = false;
        Point offset = new Point();

        this.rotRect.set(0, 0, getWidth(), getHeight());
        if (this.mapView.getMapRotation() != 0.0F) {
            this.getProjection().rotateMapRect(this.rotRect);
        }

        int i = 0;
        for (int startY = midY; (!positiveYFinished) || (!negativeYFinished); i++) {
            boolean positiveXFinished = false;
            boolean negativeXFinished = false;
            Tile t = null;
            int i_sign = i % 2 == 0 ? -tileSize : tileSize;
            startY = midY + (i / 2 + i % 2) * i_sign;

            int j = 0;
            for (int startX = midX; (!positiveXFinished) || (!negativeXFinished); j++) {
                int j_sign = j % 2 == 0 ? -tileSize : tileSize;
                startX = midX + (j / 2 + j % 2) * j_sign;
                // Log.d(LOG_TAG, "startX  startY " + startX + "," + startY);
                offset = this.getProjection().offsetFromFocalPoint(startX, startY, offset);
                // 仅用于返回瓦片信息，没有对范围有效性进行控制
                t = this.buildTile(offset.x, offset.y, zoom);
                if (t == null) {
                    break;
                }
                this.getProjection().offsetToFocalPoint(t.getRect());
                Rect imageSize = this.getProjection().getProjectionUtil().getMapImageSize();
                // imageSize.bottom = Math.round(imageSize.bottom /this.mapView.currentScale);
                // imageSize.left = Math.round(imageSize.left /this.mapView.currentScale);
                // imageSize.right = Math.round(imageSize.right /this.mapView.currentScale);
                // imageSize.top = Math.round(imageSize.top /this.mapView.currentScale);
                Rect scaleRect = new Rect(this.rotRect);
                // if (mapView.currentScale < 1.0 && mapView.isMultiTouchScale) {
                // // 压缩时且是多点缩小(动画缩放过程无需扩宽，以便快速出图)，当前屏幕需要更多的瓦片来填充，所以扩宽原有下载瓦片的屏幕，保证下载更多的瓦片来压缩
                // scaleRect.inset((int) Math.round(getWidth() * (1 - 1 / mapView.currentScale)),
                // (int) Math.round(getHeight() * (1 - 1 / mapView.currentScale)));
                // mapView.isMultiTouchScale = false;
                // }
                if (Rect.intersects(scaleRect, t.getRect()) && imageSize != null) {
                    boolean needDraw = t.getPixelX() > imageSize.left - 256 && t.getPixelX() < imageSize.right && t.getPixelY() > imageSize.top - 256
                            && t.getPixelY() < imageSize.bottom;
//                    boolean needDraw = t.getPixelX() > imageSize.left - 256 || t.getPixelX() < imageSize.right || t.getPixelY() > imageSize.top - 256
//                            || t.getPixelY() < imageSize.bottom;
                    // boolean noDraw = (t.getPixelX() < imageSize.left - 256) || (t.getPixelX() >= imageSize.right) || (t.getPixelY() <= imageSize.top - 256)
                    // || (t.getPixelY() >= imageSize.bottom);
                    //if (imageSize != null && needDraw) {
                    if (needDraw) {
                        if (queueTile) {
                            // Log.d(LOG_TAG, "queueTile开始");
                            queueTile(t);
                        } else {
                            // Log.d(LOG_TAG, "drawTile开始");
                            drawTile(t, canvas, drawLoadingTile);
                        }
                    }
                }

                if (j % 2 != 0) {
                    if (negativeXFinished) {
                        j++;
                    }
                } else if (positiveXFinished) {
                    j++;
                }
                // mapView.currentScale用来修正横轴X出图瓦片的个数，适应任意比例尺出图
                if (t.getRect().left < this.rotRect.left - 256) {
                    negativeXFinished = true;
                }
                if (t.getRect().right <= this.rotRect.right + 256) {
                    continue;
                }
                positiveXFinished = true;
            }

            if (t == null) {
                if (i % 2 == 0)
                    negativeYFinished = true;
                else
                    positiveYFinished = true;
            } else {
                // currentScale用来修正竖轴Y出图瓦片的个数，适应任意比例尺出图
                if (t.getRect().top < this.rotRect.top - 256)
                    negativeYFinished = true;
                if (t.getRect().bottom > this.rotRect.bottom + 256)
                    positiveYFinished = true;
            }

            if (i % 2 != 0) {
                if (negativeYFinished) {
                    i++;
                }
            } else if (positiveYFinished)
                i++;
        }
        // 初始化完所需的瓦片后，调用异步读取离线缓存接口，读取完毕刷新地图
        asyncGetTilesFromCache();
        // if (!queueTile) {
        // Log.d(LOG_TAG, "iterateTiles绘制一次所需的时间："+(System.currentTimeMillis()-start)+" ms");
        // }
    }

    void queueTile(Tile tile) {
        this.totalTileCount += 1;
        // 瓦片已经有内容了，无需加入到下载队列中去
        if (tile != null && tile.getBitmap() != null) {
            return;
        }
        if (!addToNetworkDownload) {
            return;
        }
        ITileCache mCache = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY);
        if (mCache != null) {
            Tile ct = mCache.getTile(tile);
            if (ct == null) {
                this.getTileProvider().queueTile(tile);
            }
        }
    }

    void drawTile(Tile tile, Canvas canvas, boolean drawLoadingTile) {
        if (tile == null || tile.getZoomLevel() != this.mapView.getZoomLevel()) {
            return;
        }
        this.totalTileCount += 1;

        Bitmap bitmap = null;
        // 瓦片已经有内容了，无需到内存缓存中去取
        if (tile.getBitmap() != null) {
            bitmap = tile.getBitmap();
            this.tileCount += 1;
        } else {
            // Log.i(LOG_TAG, "MEMORY getTile");
            Tile t = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY).getTile(tile);
            if (t != null) {
                bitmap = t.getBitmap();
                if ((bitmap == null) || (bitmap.isRecycled())) {
                    this.getTileCacher().getCache(TileCacher.CacheType.MEMORY).removeTile(tile);
                    return;
                }
                this.tileCount += 1;
            }
//             else if ((drawLoadingTile)) {
//             bitmap = getLoadingTile();
//             }
        }
        Rect tileRect = tile.getRect();
        if (tileRect == null) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_DRAWTILE_UNVISIBLE, tile.toString()));
            return;
        }
        // long start = System.currentTimeMillis();
        if ((canvas != null) && (bitmap != null)) {
            // if (this.mapView.currentScale != 1.0F) {
            // int left = Math.round((tileRect.left - this.mapView.scalePoint.x) * mapView.currentScale) + this.mapView.scalePoint.x;
            // int top = Math.round((tileRect.top - this.mapView.scalePoint.y) * mapView.currentScale) + this.mapView.scalePoint.y;
            // int right = left + Math.round(256 * mapView.currentScale) + 1;
            // int bottom = top + Math.round(256 * mapView.currentScale) + 1;
            // canvas.drawBitmap(bitmap, null, new Rect(left, top, right, bottom), this.customTilePaint);
            canvas.drawBitmap(bitmap, null, new Rect(tileRect.left, tileRect.top, tileRect.right, tileRect.bottom), this.customTilePaint);
            // canvas.drawBitmap(bitmap, null, new Rect(Math.round((tileRect.left * mapView.currentScale)), Math.round((tileRect.top * mapView.currentScale)),
            // Math.round(((tileRect.left + 256) * mapView.currentScale)), Math.round(((tileRect.top + 256) * mapView.currentScale))),
            // this.customTilePaint);
            // canvas.drawBitmap(bitmap, null, new RectF(tileRect.left * mapView.currentScale, tileRect.top * mapView.currentScale, (tileRect.left + 256)
            // * mapView.currentScale, (tileRect.top + 256) * mapView.currentScale), this.customTilePaint);
            // Matrix m = new Matrix();
            // m.postScale(mapView.currentScale, mapView.currentScale);
            // Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, 256, 256, m, true);
            // canvas.drawBitmap(bm, tileRect.left* mapView.currentScale, tileRect.top* mapView.currentScale, this.customTilePaint);
            // } else {
            // canvas.drawBitmap(bitmap, tileRect.left, tileRect.top, this.customTilePaint);
            // }
            // canvas.drawBitmap(bitmap, null, new RectF(tileRect.left * 1.0F, tileRect.top * 1.0F, (tileRect.left + 258) * 1.0F, (tileRect.top + 258) * 1.0F),
            // this.customTilePaint);
            // Log.d(LOG_TAG, "drawTile绘制一次所需的时间："+(System.currentTimeMillis()-start)+" ms");
        }
    }

    private int getMapWidth() {
        return this.mapView.getMapWidth() == 0 ? getWidth() : this.mapView.getMapWidth();
    }

    private int getMapHeight() {
        return this.mapView.getMapHeight() == 0 ? getHeight() : this.mapView.getMapHeight();
    }

    TileDownloader getTileProvider() {
        return this.mapView.getTileProvider();
    }

    /**
     * <p>
     * 获取图层的缩放级别。
     * </p>
     * @return 图层的缩放级别。
     */
    protected int getZoomLevel() {
        if (mapView != null) {
            return this.mapView.getZoomLevel();
        }
        return -1;
    }

    /**
     * <p>
     * 获取android端瓦片的缓存。
     * </p>
     * @return android端瓦片的缓存。
     */
    protected TileCacher getTileCacher() {
        if (this.mapView != null) {
            return this.mapView.getTileCacher();
        }
        return null;
    }

    /**
     * <p>
     * 构造瓦片对象。
     * </p>
     * @param x 相对于屏幕中心点为原点的x坐标。
     * @param y 相对于屏幕中心点为原点的y坐标。
     * @param zoom 当前缩放层级。
     * @return
     */
    protected Tile buildTile(int x, int y, int zoom) {
        if (zoom < 0) {
            return null;
        }
        // Log.d(LOG_TAG,
        // resource.getMessage(MapCommon.RESTMAPTILEFACTORY_BUILDTILE_START, new String[] { String.valueOf(x), String.valueOf(y), String.valueOf(zoom) }));
        Point globalPoint = this.getProjection().getProjectionUtil().getGlobalFromScreen(x, y, null);
        if (globalPoint != null) {
            Rect imageSize = this.getProjection().getProjectionUtil().getMapImageSize();
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
            // 以下修改可以避免[-255,255]范围的值计算出来的x和y都是0，不会有两次的0
            double scaleTileSize = 256.0 * mapView.currentScale * mapView.getDensity();
            int tileX = (int) Math.floor((globalPoint.x - imageSize.left) / scaleTileSize);
            int tileY = (int) Math.floor((globalPoint.y - imageSize.top) / scaleTileSize);
            int tileGlobalX = (int) Math.round(tileX * scaleTileSize) + imageSize.left;
            int tileGlobalY = (int) Math.round(tileY * scaleTileSize) + imageSize.top;
            x += tileGlobalX - globalPoint.x;
            y += tileGlobalY - globalPoint.y;
            // Log.d(LOG_TAG, "tileX:" + tileX + "tileY:" + tileY+"tileGlobalX:" + tileGlobalX + "tileGlobalY:" + tileGlobalY+"X:" + x + "Y:" + y+"currentScale:"+mapView.currentScale+"zoom"+zoom);
            // Log.d(LOG_TAG,
            // resource.getMessage(MapCommon.RESTMAPTILEFACTORY_TILEGLOBAL, new String[] { String.valueOf(tileGlobalX), String.valueOf(tileGlobalY) }));
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.RESTMAPTILEFACTORY_XY, new String[] { String.valueOf(x), String.valueOf(y) }));
            int scaleSize = (int) Math.round(scaleTileSize) + 1;// 所有的瓦片都多画一个像素
            Rect rect = new Rect(x, y, x + scaleSize, y + scaleSize);
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.RESTMAPTILEFACTORY_NEWTILE, String.valueOf(type)));

            Tile tile = new Tile(tileX, tileY, tileGlobalX, tileGlobalY, zoom, "rest-map", this.getLayerCacheFileName());
            // 瓦片做缓存时，考虑投影参数
            // if (this.getCRS() != null && this.getCRS().wkid > 0) {
            // tile.setEpsgCodes(this.getCRS().wkid);
            // }
            // tile.setUrl(getTileURL(tile));
            // tile.setUrl(getTileURL(tile.getX(), tile.getY(), tile.getZoomLevel()));
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.RESTMAPTILEFACTORY_GETTILEURL, tile.getUrl()));
            tile.setRect(rect);
            initTileContext(tile);
            return tile;
        }
        return null;
    }

    /**
     * <p>
     * 获取瓦片的url，具体继承类实现该接口。
     * </p>
     * @param tile 通过构造函数构建的瓦片对象，其初始化好了瓦片的列号x、行号y、缩放层级，地图图层名以及瓦片左上对应的像素坐标(px,py)。
     * @return 瓦片的url。
     */
    // abstract public String getTileURL(Tile tile);

    /**
     * <p>
     * 初始化tile瓦片，具体继承类实现该接口。即可以根据需要给瓦片设置请求瓦片的url、设置透明值、设置比例尺、设置瓦片所属的坐标系的epsgcode、设置瓦片内容的字节数组、设置瓦片内容的Bitmap对象。
     * </p>
     * @param tile 通过构造函数构建的瓦片对象，其初始化好了瓦片的列号x、行号y、缩放层级，地图图层名以及瓦片左上对应的像素坐标(px,py)。
     */
    abstract public void initTileContext(Tile tile);

    /**
     * <p>
     * 异步去读取缓存数据，读取完毕刷新地图。使用场景：当初始化完当前屏幕所需的瓦片后，根据已知瓦片，去异步读取离线数据缓存，保证不阻塞主线程
     * 子类根据需要扩展实现，达到预期的效果，结合initTileContext(在其中保存所有初始化完成的瓦片list)接口使用，asyncGetTilesFromCache根据list异步读取瓦片缓存
     * </p>
     * @since 6.1.3
     */
    public void asyncGetTilesFromCache() {
    }

    /**
     * <p>
     * 清除客户端本地缓存。
     * </p>
     * @param clearServerCache
     */
    public void clearCache(boolean clearServerCache) {
    }

    /**
     * <p>
     * 销毁当前 LayerView 对象，退出前调用。
     * </p>
     */
    public void destroy() {
    }

    /**
     * <p>
     * 设置当前地图的 url，注意 url 不能进行任何编码。
     * </p>
     * @param mapUrl 地图服务的 url。
     */
    public void setURL(String mapUrl) {
        this.curMapUrl = mapUrl;
    }

    /**
     * 设置坐标参考系类对象，用于支持动态投影。
     * <p>
     * 具体实现类最好重写该接口，如动态图层需要发送新请求获取投影后的地图状态。
     * 而云图层就不支持动态投影即不支持设置，该接口需要抛出异常警告。
     * </p>
     * @param crs
     */
    public void setCRS(CoordinateReferenceSystem crs) {

    }

    /**
     * 获取MapView中当前图层的 url 地址。
     * @return 返回当前图层的 url 地址。
     */
    public String getURL() {
        return this.curMapUrl;
    }

    /**
     * <p>
     * 获取图层显示时的固定比例尺数组。
     * </p>
     * @return 图层显示时的固定比例尺数组。
     */
    public double[] getScales() {
        return visibleScales;
    }

    /**
     * <p>
     * 获取图层的分辨率数组。
     * </p>
     * @return 图层的分辨率数组。
     */
    public double[] getResolutions() {
        return this.resolutions;
    }

    /**
     * <p>
     * 设置当前图层是否可见。
     * </p>
     * @param visible 当前图层是否可见。
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * <p>
     * 返回当前图层是否可见。
     * </p>
     * @return 当前图层是否可见。
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * <p>
     * 获取当前图层的坐标参考系。
     * </p>
     * @return 当前图层的坐标参考系。
     */
    public CoordinateReferenceSystem getCRS() {
        /*if (!this.isLayerInited) {
            throw new IllegalStateException("图层没有初始化。");
        }*/
        return this.crs;
    }

    String getLayerName() {
        return this.layerName;
    }

    /**
     * <p>
     * 获取图层的缓存文件名称，作为sd卡缓存的文件夹名
     * </p>
     * @return
     */
    String getLayerCacheFileName() {
        if (StringUtils.isEmpty(this.layerCacheFileName)) {
            return this.getLayerName();
        } else {
            return this.layerCacheFileName;
        }
    }

    /**
     * <p>
     * 设置图层的缓存文件名称，用于在sd卡中看到该图层的缓存文件夹
     * </p>
     * @param cacheFileName
     */
    public void setLayerCacheFileName(String cacheFileName) {
        this.layerCacheFileName = cacheFileName;
    }

    /**
     * <p>
     * 检查当前图层是否已经初始化完成。
     * </p>
     * @return 如果地图已经初始化则返回 true，否则返回 false。
     */
    public boolean isInitialized() {
        return this.isLayerInited;
    }

    Projection getProjection() {
        if (projection == null && isLayerInited) {
            this.projection = new Projection(this);
        }
        return this.projection;
    }

    void onRestoreInstanceState(Bundle state) {
        if ((state.containsKey("STATE_CENTER_LAT")) && (state.containsKey("STATE_CENTER_LNG"))) {
            int latE6 = state.getInt("STATE_CENTER_LAT");
            int longE6 = state.getInt("STATE_CENTER_LNG");
            this.mapView.centerGeoPoint = new Point2D(longE6, latE6);
        }

        if (state.containsKey("STATE_ZOOM_LEVEL")) {
            int zoom = state.getInt("STATE_ZOOM_LEVEL");
            if (validateZoomLevel(zoom))
                this.mapView.setZoomLevel(zoom);
        }
    }

    void onSaveInstanceState(Bundle state) {
        if (this.mapView.centerGeoPoint != null) {
            state.putDouble("STATE_CENTER_LAT", this.mapView.centerGeoPoint.getY());
            state.putDouble("STATE_CENTER_LNG", this.mapView.centerGeoPoint.getX());
        }
        state.putInt("STATE_ZOOM_LEVEL", this.mapView.getZoomLevel());
    }

    /**
     * <p>
     * 判断图层缩放级别是否合法。
     * </p>
     * @param zoom 缩放级别。
     * @return true为合法，false为非法。
     */
    protected boolean validateZoomLevel(int zoom) {
        if (this.getMapView().getMaxZoomLevel() >= zoom) {
            return true;
            // return isScaleValid(this.getMapView().getScales()[zoom]);// 最好保存有效层级，这样只需要判断int，提高显示速度
        } else {
            return false;
        }
    }

    /**
     * <p>
     * 获取MapView容器。
     * </p>
     * @return MapView容器。
     */
    protected MapView getMapView() {
        if (this.mapView != null) {
            return this.mapView;
        } else {
            throw new IllegalStateException(resource.getMessage(MapCommon.LAYERVIEW_GETMAPVIEW_FAIL));
        }
    }

    /**
     * <p>
     * 返回是否为地理坐标系图层。
     * </p>
     * @return
     */
    protected boolean isGCSLayer() {
        return this.isGCSLayer;
    }

    /**
     * <p>
     * 返回图层的地理范围。
     * </p>
     * @return 图层的地理范围。
     */
    public BoundingBox getBounds() {
        return this.layerBounds != null ? new BoundingBox(this.layerBounds) : null;
    }

    /**
     * <p>
     * 返回图层当前分辨率。
     * </p>
     * @return 图层当前分辨率。
     */
    // public double getResolution() {
    // return getMapView().getResolution();
    // }

    /**
     * <p>
     * 返回图层当前比例尺。
     * </p>
     * @return 图层当前比例尺。
     */
    // public double getScale() {
    // return getMapView().getScale();
    // }


//设置图层透明度
     public void setOpaqueRate(int PaintAlpha) { 
    	Paint paint = new Paint();
    	PaintAlpha = (int) (PaintAlpha*(255/100.0));
    	paint.setAlpha(PaintAlpha);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        this.customTilePaint = paint;
    }

}
