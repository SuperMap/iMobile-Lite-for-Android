package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.supermap.imobilelite.serverType.ServerStyle;
import com.supermap.services.components.commontypes.DatasetType;
import com.supermap.services.components.commontypes.PixelGeometry;
import com.supermap.services.components.commontypes.PixelGeometryText;
import com.supermap.services.components.commontypes.TextAlignment;
import com.supermap.services.components.commontypes.TextStyle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

/**
 * <p>
 * SVTiles图层即离线矢量图层，如果sdcard中没有离线矢量缓存SVTiles则不出图。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class TileVectorLayer extends AbstractTileLayerView {
    private static final String LOG_TAG = "com.supermap.android.maps.TiledVectorLayer";
    private String svtilesPath = "";
    private MBTilesUtil mbtilesHelper;
    private MBTilesMetadata metadata;
    private int tileSize = 256;
    private Handler layersInfoHandler;
    private Map<String, LayerStyle> LayersInfoMap = new HashMap<String, LayerStyle>();
    private boolean layersInfoInitialized = false;
    private VectorTileCacher vectorTileCacher;
    // 存储当前所需的不在内存缓存中的所有的瓦片信息
    private List<SVTileMessage> tileList = new ArrayList<SVTileMessage>();
    private GetSVTilesTask getSVTilesTask;
    private boolean getSVTilesTaskOver = false;
    private RefreshHandler refreshHandler;
    private double[] metaResolutions;
    private boolean sleep = true;
    // 读取图片的线程睡眠的时间，单位ms
    private long sleepTime = 400L;
    // 是否是第一次出图，第一次就不让读取图片的线程睡眠
    private boolean firstOutputTile = true;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文
     */
    public TileVectorLayer(Context context) {
        super(context);
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文
     * @param attrs 属性信息
     */
    public TileVectorLayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文
     * @param attrs 属性信息
     * @param defStyle 风格标识
     */
    public TileVectorLayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文
     * @param svtilesPath 设置SVTiles所在的路径，该路径相对于sdcard的路径，如"supermap/SVTiles/*.svtiles"
     */
    public TileVectorLayer(Context context, String svtilesPath) {
        super(context);
        setSVTilesPath(svtilesPath);
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文
     * @param svtilesPath 设置SVTiles所在的路径，该路径相对于sdcard的路径，如"supermap/SVTiles/*.svtiles"
     * @param mapUrl 地图服务的url地址
     */
    public TileVectorLayer(Context context, String svtilesPath, String mapUrl) {
        super(context);
        this.curMapUrl = mapUrl;
        setSVTilesPath(svtilesPath);
        initialize();
    }

    /**
     * <p>
     * 设置SVTiles所在的路径，该路径相对于sdcard的路径，如"supermap/SVTiles/*.svtiles"
     * 并读取SVTiles的源信息初始化图层状态
     * </p>
     * @param value
     */
    public void setSVTilesPath(String value) {
        svtilesPath = value;
        mbtilesHelper = new MBTilesUtil(svtilesPath);
        if (mbtilesHelper.open()) {
            metadata = mbtilesHelper.readMBTilesMetadata();
            // 设置图层元数据信息
            if (metadata != null) {
                this.layerBounds = metadata.bounds;
                // 解决切图范围的左上不是切图源点时，出图错误的问题
                if (metadata.axis_origin != null) {
                    this.layerBounds.leftTop = new Point2D(metadata.axis_origin);
                }
                this.resolutions = metadata.resolutions;
                if (resolutions != null) {
                    this.metaResolutions = this.resolutions.clone();
                }
                this.visibleScales = metadata.scales;
                // this.origin=new Point2D(this.layerBounds.getLeft(), this.layerBounds.getTop());
                // this.compatible = metadata.compatible;
                this.tileSize = metadata.tileSize;
                // this.imageFormat = metadata.format;
                this.crs = new CoordinateReferenceSystem();
                this.crs.wkid = metadata.crs_wkid;
                this.crs.unit = metadata.unit;
                isGCSLayer = Util.isGCSCoordSys(this.crs);
                if (resolutions != null) {
                    if (layerBounds != null && resolutions.length > 0) {
                        isLayerInited = true;// 不用发送请求，因为初始化了layerBounds、resolutions就可以了
                    }
                    if (isGCSLayer) {
                        // 分辨率统一以米为单位，SVTiles中地图投影为4326那么resolutions则是以度为单位，此处把度转换成米
                        double radius = this.crs.datumAxis > 1d ? this.crs.datumAxis : Constants.DEFAULT_AXIS;
                        for (int i = 0; i < resolutions.length; i++) {
                            resolutions[i] = resolutions[i] * Math.PI * radius / 180.0;
                        }
                    }
                }
            } else {
                Log.w(LOG_TAG, "metadata in SVTiles is null!");
            }
        } else {
            Log.w(LOG_TAG, "SVTiles is not existed or opened!");
            Toast.makeText(context, "SVTilesPath:" + svtilesPath + " isn't existed,please check!", Toast.LENGTH_LONG);
        }
    }

    private void initialize() {
        // todo 异步发送"/layers.json"获取矢量风格,初始化LayersStatus
        // JsonConverter.addDecoderResolver(new VectorTileJsonDecoderResolver());
        layersInfoInitialized = false;
        layersInfoHandler = new GetlayersInfoHandler();
        new getLayersInfoThread().start();

        layerName = "SVTilesLayer_";// 清除sd卡缓存需要
        if (!StringUtils.isEmpty(svtilesPath)) {
            layerName += svtilesPath.substring(svtilesPath.lastIndexOf("/") + 1, svtilesPath.lastIndexOf("."));
        }
        // curMapUrl = "http://support.supermap.com.cn:8090/iserver/services/mapInstance/rest/maps/" + layerName;// sd卡缓存需要SuperMapCloud这个名称
        // this.dpi = 0.0254 / 96.0;
        vectorTileCacher = new VectorTileCacher(context);
        // vectorTileProvider = new VectorTileDownloader(this, vectorTileCacher);
        this.addToNetworkDownload = false;
        refreshHandler = new RefreshHandler();
    }

    @Override
    String getLayerCacheFileName() {
        return super.getLayerCacheFileName() + "_VTL";
    }

    // @Override
    // TileDownloader getTileProvider() {
    // return vectorTileProvider;
    // }

    @Override
    protected TileCacher getTileCacher() {
        return vectorTileCacher;
    }

    @Override
    void drawTile(Tile tile, Canvas canvas, boolean drawLoadingTile) {
        // TODO 需要判断图层风格layersInfoInitialized是否已经初始化了
        if (!layersInfoInitialized) {
            // Log.d(LOG_TAG, "layersInfoInitialized=false");
            return;
        }
        if (tile.getZoomLevel() != this.mapView.getZoomLevel()) {
            return;
        }
        this.totalTileCount += 1;
        this.tileCount += 1;
        Log.d(LOG_TAG, "drawTile x=" + tile.getX() + ",y=" + tile.getY());
        ITileCache mc = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY);
        if (mc instanceof MemoryVectorTileCache) {
            MemoryVectorTileCache mvtc = (MemoryVectorTileCache) mc;
            List<VectorGeometryData> vectorTileResult = mvtc.getVectorTile(tile);
            Rect tileRect = tile.getRect();
            if (vectorTileResult != null && vectorTileResult.size() > 0) {
                drawVectorTile(vectorTileResult, canvas, tileRect);
            } else {
                Log.d(LOG_TAG, "MemoryVectorTileCache is null");
            }
            // else {
            // ITileCache sdCache = this.getTileCacher().getCache(TileCacher.CacheType.DB);
            // if (sdCache != null && sdCache.contains(tile)) {
            // Tile ct = sdCache.getTile(tile);
            // if (ct != null && ct.getBytes() != null && ct.getBytes().length > 0) {
            // try {
            // vectorTileResult = JsonConverter.parseJson(new String(ct.getBytes(), Charset.forName("utf-8")), VectorTileData.class);
            // } catch (JSONException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            // drawVectorTile(vectorTileResult, canvas, tileRect);
            // }
            // }
            // }
        }
    }

    private void drawVectorTile(List<VectorGeometryData> vectorTileResult, Canvas canvas, Rect tileRect) {
        Log.d(LOG_TAG, "drawVectorTile");
        // 解析绘制
        if (tileRect == null || canvas == null) {
            return;
        }
        int len = vectorTileResult.size();
        for (int i = 0; i < len; i++) {
            VectorGeometryData vgd = vectorTileResult.get(i);
            String layerName = vgd.layer;
            LayerStyle layersStyle = this.getLayerStyle(layerName);
            if (layersStyle == null) {
                return;
            }
            DatasetType type = layersStyle.type;
            // 将json格式的style转换为ServerStyle,先用服务端的Style
            ServerStyle serverStyle = layersStyle.style;
            // 设置绘制风格
            // setCanvasStyle(paint,serverStyle,type);
            // 分不同类型进行绘制
            if (type == DatasetType.TEXT) {
                // Log.d(LOG_TAG, "TEXT");
                // long t = System.currentTimeMillis();
                // PixelGeometryText geometryText = JsonConverter.parseJson(vgd.geometry_data, PixelGeometryText.class);
                // Log.d(LOG_TAG, "解析文本一次耗时:" + (System.currentTimeMillis() - t));
                drawVectorText(vgd.geometry_data, canvas, tileRect);
            } else if (type == DatasetType.POINT) {
                // long t = System.currentTimeMillis();
                // PixelGeometry geometry = JsonConverter.parseJson(vgd.geometry_data, PixelGeometry.class);
                // Log.d(LOG_TAG, "解析点一次耗时:" + (System.currentTimeMillis() - t));
                drawVectorPoint(serverStyle, vgd.geometry_data, canvas, tileRect);
            } else if (type == DatasetType.LINE || type == DatasetType.REGION) {
                // long t = System.currentTimeMillis();
                // PixelGeometry geometry = JsonConverter.parseJson(vgd.geometry_data, PixelGeometry.class);
                // Log.d(LOG_TAG, "解析线面一次耗时:" + (System.currentTimeMillis() - t));
                drawVectorRegionOrLine(serverStyle, vgd.geometry_data, type, false, canvas, tileRect);
            }
        }
    }

    @Override
    void preLoad() {
        // Log.d(LOG_TAG, "TiledVectorLayer preLoad");
    }

    @Override
    void queueTile(Tile tile) {
        this.totalTileCount += 1;
    }

    // void endTile(Tile tile) {
    // this.mapView.postInvalidate();
    // }

    /**
     * <p>
     * 初始化瓦片的内容
     * 首先判断内存缓存对象是否存在，1.存在内存缓存对象且包含当前瓦片则读取缓存无需查询数据库获取直接返回，增强效率；
     * 2.存在内存缓存对象但不包含当前瓦片 或 不存在内存缓存对象; 则判断SDCard缓存对象是否存在，存在且包含瓦片则把当前瓦片保持到内存缓存中后返回，增强效率；
     * 3.不存在任何缓存对象则查询数据库获取瓦片，并保持于缓存起来(包含内存缓存和SDCard缓存)
     * </p>
     * @param tile
     * @return
     */
    @Override
    public void initTileContext(Tile tile) {
        if (tile == null || tile.getX() < 0 || tile.getY() < 0) {
            return;
        }
        // 首先判断内存缓存对象是否存在
        ITileCache mCache = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY);
        // 存在内存缓存对象且包含当前瓦片直接返回
        if (mCache != null && mCache.contains(tile)) {
            // Log.d(LOG_TAG, "MEMORY Cache");
            return;
        }
        // ITileCache sdCache = this.getTileCacher().getCache(TileCacher.CacheType.DB);
        // // 存在SDCard缓存对象且包含当前瓦片则把当前瓦片保持到内存缓存中后返回
        // if (sdCache != null && sdCache.contains(tile)) {
        // Tile ct = sdCache.getTile(tile);
        // if (ct != null && ct.getBytes() != null && ct.getBytes().length > 0) {
        // // 把当前瓦片保持到内存缓存中
        // if (mCache != null) {
        // // Log.d(LOG_TAG, "sdCache");
        // mCache.addTile(ct);
        // }
        // return;
        // }
        // }
        // 不包含当前瓦片则查询数据库获取瓦片并存储到 缓存中
        int index = getResolutionIndex();
        if (StringUtils.isEmpty(svtilesPath) || mbtilesHelper == null || !mbtilesHelper.isOpen() || index < 0 || this.tileSize != 256) {
            return;
        }
        // 加上最小级别，换算成从左上角点出图,SVtiles是从左 下 角切图（supermap iServer切的SVtiles缓存也遵循此标准）
        double resolution = this.metaResolutions[index];
        // if (isGCSLayer) {
        // // SVtiles中地图投影为4326那么resolutions则是以度为单位，此处把之前转换成以米为单位的resoltion值还原成以度为单位
        // double radius = this.crs.datumAxis > 1d ? this.crs.datumAxis : Constants.DEFAULT_AXIS;
        // resolution = resolution / (Math.PI * radius / 180.0);
        // }
        // 存储当前所需的不在内存缓存中的所有的瓦片信息
        tileList.add(new SVTileMessage(tile, resolution));
    }

    @Override
    public void asyncGetTilesFromCache() {
        // 异步去读取缓存数据tileList
        if (getSVTilesTask == null) {
            getSVTilesTask = new GetSVTilesTask();
            getSVTilesTask.start();
        }
        if (!getSVTilesTask.isAlive()) {
            getSVTilesTask.start();
        }
        SyncTask task = buildSyncTask();
        if (!"".equals(task.sql)) {
            // 根据需要不断同步修改需要执行的同步任务
            getSVTilesTask.setSyncTask(task);
        }
    }

    private SyncTask buildSyncTask() {
        List<Tile> tiles = new ArrayList<Tile>();
        int len = tileList.size();
        // String sql = "";
        StringBuffer sqlBuf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            SVTileMessage tileMes = tileList.get(i);
            tiles.add(tileMes.tile);
            sqlBuf.append("(tile_column=" + tileMes.tile.getX() + " AND tile_row=" + tileMes.tile.getY() + " AND resolution=" + tileMes.resolution + ")");
            // sql = sql + "(tile_column=" + tileMes.tile.getX() + " AND tile_row=" + tileMes.tile.getY() + " AND resolution=" + tileMes.resolution + ")";
            if (i != len - 1) {
                sqlBuf.append(" OR ");
                // sql += " OR ";
            }
        }
        // sql = sqlBuf.toString();
        // 运行完一次，必须清空tileList，用来存储下次屏幕所需的瓦片
        tileList.clear();
        SyncTask task = new SyncTask(tiles, sqlBuf.toString());
        return task;
    }

    void refresh() {
        this.mapView.invalidate();
    }

    class SyncTask {
        public List<Tile> tiles;
        public String sql;

        public SyncTask(List<Tile> tiles, String sql) {
            super();
            this.tiles = tiles;
            this.sql = sql;
        }

    }

    class SVTileMessage {
        public Tile tile;
        public double resolution;

        public SVTileMessage(Tile tile, double resolution) {
            super();
            this.tile = tile;
            this.resolution = resolution;
        }
    }

    /**
     * <p>
     * 读取SVTiles的任务线程，运行一次读取当前屏幕所需的不在内存缓存的所有瓦片，即tileList存储的瓦片
     * </p>
     * @author ${huangqh}
     * @version ${Version}
     * @since 6.1.3
     * 
     */
    class GetSVTilesTask extends Thread {
        private static final String ROOT_SQL = "SELECT tile_id FROM tiles WHERE (";
        private SyncTask task = null;// 任务修改需要同步，保证最后加入的任务得以执行而且覆盖以往的等待任务

        @Override
        public void run() {
            while (!getSVTilesTaskOver) {
                List<String> argus = new ArrayList<String>();
                // 根据sql语句和sql参数执行查询返回瓦片二进制数组，并做缓存
                Map<String, List<VectorGeometryData>> byteMap = null;
                try {
                    // 异步读取同步任务，非空执行任务
                    SyncTask st = getSyncTask();
                    if (st != null && !"".equals(st.sql)) {
                        String mySql = st.sql;
                        mySql = ROOT_SQL + mySql + ");";
                        Log.d(LOG_TAG, "sql:" + mySql);
                        byteMap = mbtilesHelper.getVectorTiles(mySql, argus);
//                        if (byteMap != null) {
//                            Log.d(LOG_TAG, "tileFromMBTiles byteMap size:" + byteMap.size());
//                        }
                        if (getTileCacher() != null) {// 判断的原因是主线程停止会置空TileCacher，而子线程还没有立即结束
                            ITileCache mCache = getTileCacher().getCache(TileCacher.CacheType.MEMORY);
                            Log.d(LOG_TAG, "st.tiles.size():" + st.tiles.size());
                            if (byteMap != null && byteMap.size() > 0) {
                                String rs = getResolutionStr(byteMap);
                                // int index = getResolutionIndex();
                                // if (index >= 0 && index < TiledVectorLayer.this.metaResolutions.length) {
                                if (rs != null) {
                                    // double r = TiledVectorLayer.this.metaResolutions[index];
                                    for (int i = 0; i < st.tiles.size(); i++) {
                                        Tile mTile = st.tiles.get(i);
                                        // getResolutionString(r)避开科学技术法的double值
                                        // String key = MBTilesUtil.getResolutionString(r) + "_" + mTile.getX() + "_" + mTile.getY();
                                        String key = rs + "_" + mTile.getX() + "_" + mTile.getY();
                                        Log.d(LOG_TAG, "key:" + key);
                                        List<VectorGeometryData> vgds = byteMap.get(key);
                                        if (vgds != null && vgds.size() > 0) {
                                            // todo
                                            // mTile.setBytes(bs.getBytes(Charset.forName("utf-8")));
                                            Log.d(LOG_TAG, "List<VectorGeometryData> size:" + vgds.size());
                                            if (mCache != null && mCache instanceof MemoryVectorTileCache) {
                                                ((MemoryVectorTileCache) mCache).addTile(mTile, vgds);
                                            }
                                        } else {
                                            Log.d(LOG_TAG, "List<VectorGeometryData> is null");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 刷新地图
                    if (byteMap != null && byteMap.size() > 0) {
                        firstOutputTile = false;
                        // refresh();
                        // 子线程不能直接调用UI相关控件，所以只能通过把结果以消息的方式告知UI主线程展示结果
                        refreshHandler.sendEmptyMessage(1);
                    } else {
                        if (sleep && !firstOutputTile) {
                            Thread.sleep(sleepTime);
                        }
                    }
                } catch (InterruptedException e) {
                    Log.d(LOG_TAG, "InterruptedException occurs:" + e.getMessage());
                }
            }
            // Log.d(LOG_TAG, "成功退出读取离线缓存的子线程");
        }

        private String getResolutionStr(Map<String, List<VectorGeometryData>> byteMap) {
            Set<String> set = byteMap.keySet();
            Iterator<String> it = set.iterator();
            if (it.hasNext()) {
                String s = it.next();
                s = s.substring(0, s.indexOf("_"));
                return s;
            }
            return null;
        }

        /**
         * <p>
         * 同步修改需要执行的同步任务
         * </p>
         * @param task
         * @since 6.1.3
         */
        public synchronized void setSyncTask(SyncTask task) {
            this.task = task;
        }

        /**
         * <p>
         * 同步读取需要执行的同步任务后并把同步任务置空，置空保证循环执行有意义的任务
         * </p>
         * @return
         * @since 6.1.3
         */
        public synchronized SyncTask getSyncTask() {
            if (task == null) {
                return null;
            }
            SyncTask st = new SyncTask(task.tiles, task.sql);
            task = null;
            return st;
        }
    }

    private class RefreshHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1:
                // 刷新地图
                refresh();
                break;
            default:
                break;
            }
        }
    }

    public void destroy() {
        if (getSVTilesTask != null) {
            getSVTilesTask.interrupt();// 存在sleep，中断无效，所以增加getSVTilesTaskOver标志
            getSVTilesTaskOver = true;
            getSVTilesTask = null;
        }
        if (mbtilesHelper != null) {
            mbtilesHelper.close();
        }
        if (this.vectorTileCacher != null) {
            this.vectorTileCacher.destroy();
            this.vectorTileCacher = null;
        }
    }

    class GetlayersInfoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 200:
                Log.d(LOG_TAG, "GetlayersInfoHandler完成，刷新地图");
                TileVectorLayer.this.mapView.postInvalidate();
                break;
            default:
                break;
            }
        }
    }

    class getLayersInfoThread extends Thread {
        public void run() {
            initLayersInfo();
        }
    }

    void initLayersInfo() {
        this.layersInfoInitialized = false;
        // if (this.context.getMainLooper().getThread() == Thread.currentThread()) {
        // // Log.w(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_USEWEB));
        // // return;
        // }
        String mapUrl = getURL();
        Log.d(LOG_TAG, "mapUrl:" + mapUrl);
        if (StringUtils.isEmpty(mapUrl)) {
            return;
        }
        try {
            String getLayersInfoURL = mapUrl + "/layers.json";
            Log.d(LOG_TAG, "getLayersInfoURL:" + getLayersInfoURL);
            String layersInfo = Util.getJsonStr(getLayersInfoURL);
            Log.d(LOG_TAG, "layersInfo:" + layersInfo);
            // List<Layer> layerList= JsonConverter.parseJsonToList(Util.getJSON(getLayersInfoURL).toString(), Layer.class);
            if (!StringUtils.isEmpty(layersInfo)) {
                initLayerInfoMap(layersInfo);
                // Layer layer = layers[0];
                // LayerCollection lc = layer.subLayers;
                // if (lc != null && lc.size() > 0) {
                // for (int i = 0; i < lc.size(); i++) {
                // Layer subLayer = lc.get(i);
                // if (subLayer instanceof UGCVectorLayer) {
                //
                // UGCVectorLayer ugcvl = (UGCVectorLayer) subLayer;
                // ServerStyle ss = getServerStyle(ugcvl.style);
                // LayerInfo layerInfo = new LayerInfo(ss, ugcvl.datasetInfo.type);
                // Log.d(LOG_TAG, "layername:"+ugcvl.name);
                // LayersInfoMap.put(ugcvl.name, layerInfo);
                // }else if(subLayer instanceof UGCGridLayer){
                //
                // }else if(subLayer instanceof UGCThemeLayer){
                //
                // }
                // }
                // }
                this.layersInfoInitialized = true;
                Message message = Message.obtain();
                message.what = 200;
                message.getData().putString("description", "getLayersInfo success!");
                sendMes(message);
            }
        } catch (Exception e) {
            this.layersInfoInitialized = false;
            Log.w(LOG_TAG, "getLayersInfo failed!" + e.getMessage());
        }
    }

    private void initLayerInfoMap(String layersInfoStr) {
        if (StringUtils.isEmpty(layersInfoStr)) {
            return;
        }
        try {
            JSONArray layersInfo = new JSONArray(layersInfoStr);
            JSONObject layerInfo = layersInfo.getJSONObject(0);
            if (layerInfo == null) {
                return;
            }
            JSONObject subLayers = layerInfo.getJSONObject("subLayers");
            if (subLayers == null) {
                return;
            }
            JSONArray layers = subLayers.getJSONArray("layers");
            if (layers != null && layers.length() > 0) {
                for (int i = 0; i < layers.length(); i++) {
                    JSONObject layer = layers.getJSONObject(i);
                    String ugcLayerType = layer.getString("ugcLayerType");
                    if ("VECTOR".equalsIgnoreCase(ugcLayerType)) {
                        String name = layer.getString("name");
                        ServerStyle style = JSON.parseObject(layer.getJSONObject("style").toString(), ServerStyle.class);
                        String type = layer.getJSONObject("datasetInfo").getString("type");
                        if ("TEXT".equalsIgnoreCase(type)) {
                            Log.d(LOG_TAG, "TEXT:" + name);
                        }
                        // DatasetInfo datasetInfo = JSON.parseObject(jo2.getJSONObject("datasetInfo").toString(), DatasetInfo.class);
                        LayerStyle layerStyle = new LayerStyle(style, DatasetType.valueOf(type));
                        LayersInfoMap.put(name, layerStyle);
                    }
                }
            }
            Log.d(LOG_TAG, "LayersInfoMap.size():" + LayersInfoMap.size());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private LayerStyle getLayerStyle(String layerName) {
        if (!StringUtils.isEmpty(layerName) && LayersInfoMap != null) {
            return LayersInfoMap.get(layerName);
        }
        return null;
    }

    private void sendMes(Message message) {
        if (this.layersInfoHandler != null) {
            if (Util.checkIfSameThread(this.layersInfoHandler)) {
                this.layersInfoHandler.dispatchMessage(message);
            } else {
                this.layersInfoHandler.sendMessage(message);
            }
        }
    }

    // private void drawVectorByRecordSet(VectorRecordSet recordSet, Canvas canvas, Rect tileRect) {
    // String layerName = recordSet.layerName;
    // LayerStyle layersStyle = this.getLayerStyle(layerName);
    // if (layersStyle == null) {
    // return;
    // }
    // DatasetType type = layersStyle.type;
    // // 将json格式的style转换为ServerStyle,先用服务端的Style
    // ServerStyle serverStyle = layersStyle.style;
    // // 设置绘制风格
    // // setCanvasStyle(paint,serverStyle,type);
    // // 分不同类型进行绘制
    // for (int i = 0; i < recordSet.features.length; i++) {
    // // PixelGeometry geometry = recordSets.geometries[i];
    // if (type == DatasetType.TEXT) {
    // // Log.d(LOG_TAG, "TEXT");
    // PixelGeometryText geometryText = (PixelGeometryText) recordSet.features[i].geometry;
    // drawVectorText(geometryText, canvas, tileRect);
    // } else if (type == DatasetType.POINT) {
    // PixelGeometry geometry = (PixelGeometry) recordSet.features[i].geometry;
    // drawVectorPoint(serverStyle, geometry, canvas, tileRect);
    // } else if (type == DatasetType.LINE || type == DatasetType.REGION) {
    // PixelGeometry geometry = (PixelGeometry) recordSet.features[i].geometry;
    // drawVectorRegionOrLine(serverStyle, geometry, type, false, canvas, tileRect);
    // }
    // }
    // }

    private void drawVectorRegionOrLine(ServerStyle serverStyle, PixelGeometry geometry, DatasetType type, boolean forSelectGeometry, Canvas canvas,
            Rect tileRect) {
        int startIndex = 0;
        long s = System.currentTimeMillis();
        // 在多面的情况下，只要设置一次paint即可
        Paint paint = new Paint();
        if (type == DatasetType.REGION) {
            // 设置绘制面填充风格
            paint.setStrokeWidth(0);
            paint.setStyle(Paint.Style.FILL);// 默认是填充
            paint.setColor(Color.rgb(serverStyle.fillForeColor.red, serverStyle.fillForeColor.green, serverStyle.fillForeColor.blue));
        }
        if (type == DatasetType.LINE) {
            // 设置线宽
            float lineWidth = (float) this.getPixelByMillMeter(serverStyle.lineWidth);
            paint.setStrokeWidth(lineWidth);
            paint.setAntiAlias(true);// 影响性能
            paint.setStyle(Paint.Style.STROKE);
            // 设置线的颜色
            paint.setColor(Color.rgb(serverStyle.lineColor.red, serverStyle.lineColor.green, serverStyle.lineColor.blue));
        }
        for (int i = 0; i < geometry.parts.length; i++) {
            int part = geometry.parts[i];
            if (part < 2 || (type == DatasetType.REGION && part < 3)) {
                startIndex += part;
                continue;
            }
            Path path = new Path();
            if (this.mapView.currentScale != 1.0F) {
                path.moveTo(tileRect.left + geometry.points[startIndex * 2 + 0] * mapView.getDensity() * mapView.currentScale, tileRect.top
                        + geometry.points[startIndex * 2 + 1] * mapView.getDensity() * mapView.currentScale);
            } else {
                path.moveTo(tileRect.left + geometry.points[startIndex * 2 + 0] * mapView.getDensity(), tileRect.top + geometry.points[startIndex * 2 + 1]
                        * mapView.getDensity());
            }
            for (int j = 1; j < part; j++) {
                int ptIndex = startIndex + j;
                if (this.mapView.currentScale != 1.0F) {
                    path.lineTo(tileRect.left + geometry.points[ptIndex * 2] * mapView.getDensity() * mapView.currentScale, tileRect.top
                            + geometry.points[ptIndex * 2 + 1] * mapView.getDensity() * mapView.currentScale);
                } else {
                    path.lineTo(tileRect.left + geometry.points[ptIndex * 2] * mapView.getDensity(),
                            tileRect.top + geometry.points[ptIndex * 2 + 1] * mapView.getDensity());
                }
            }
            // path.close();
            canvas.drawPath(path, paint);
            // Paint paint = new Paint();
            // if (type == DatasetType.REGION) {
            // // 设置绘制面填充风格
            // paint.setStrokeWidth(0);
            // paint.setStyle(android.graphics.Paint.Style.FILL);// 默认是填充
            // paint.setColor(Color.rgb(serverStyle.fillForeColor.red, serverStyle.fillForeColor.green, serverStyle.fillForeColor.blue));
            // canvas.drawPath(path, paint);
            // // 设置面边框风格
            // // paint.setStyle(android.graphics.Paint.Style.STROKE);
            // // // 设置线宽
            // // float lineWidth = (float) this.getPixelByMillMeter(serverStyle.lineWidth);
            // // paint.setStrokeWidth(lineWidth);
            // // // 设置线的颜色
            // // paint.setColor(Color.rgb(serverStyle.lineColor.red, serverStyle.lineColor.green, serverStyle.lineColor.blue));
            // // canvas.drawPath(path, paint);
            // }
            // if (type == DatasetType.LINE) {
            // // 设置线宽
            // float lineWidth = (float) this.getPixelByMillMeter(serverStyle.lineWidth);
            // paint.setStrokeWidth(lineWidth);
            // paint.setAntiAlias(true);// 影响性能
            // paint.setStyle(android.graphics.Paint.Style.STROKE);
            // // 设置线的颜色
            // paint.setColor(Color.rgb(serverStyle.lineColor.red, serverStyle.lineColor.green, serverStyle.lineColor.blue));
            // canvas.drawPath(path, paint);
            // }
            startIndex += part;
        }
        Log.d(LOG_TAG, "drawVectorRegionOrLine times:" + (System.currentTimeMillis() - s) + "ms");
    }

    private void drawVectorPoint(ServerStyle serverStyle, PixelGeometry geometry, Canvas canvas, Rect tileRect) {
        float markerSize = (float) (this.getPixelByMillMeter(serverStyle.markerSize));
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        // paint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        paint.setAntiAlias(true);// 设置了才有圆圈
        paint.setStrokeWidth(markerSize);// markerSize好像太大
        paint.setColor(Color.rgb(serverStyle.lineColor.red, serverStyle.lineColor.green, serverStyle.lineColor.blue));
        if (this.mapView.currentScale != 1.0F) {
            canvas.drawPoint(tileRect.left + geometry.points[0] * mapView.currentScale * mapView.getDensity(), tileRect.top + geometry.points[1]
                    * mapView.currentScale * mapView.getDensity(), paint);
        } else {
            canvas.drawPoint(tileRect.left + geometry.points[0] * mapView.getDensity(), tileRect.top + geometry.points[1] * mapView.getDensity(), paint);
        }
    }

    private void drawVectorText(PixelGeometry geometry, Canvas canvas, Rect tileRect) {
        PixelGeometryText geometryText = null;
        long s = System.currentTimeMillis();
        if (geometry instanceof PixelGeometryText) {
            // Log.d(LOG_TAG, "PixelGeometryText");
            geometryText = (PixelGeometryText) geometry;
        }
        if (geometryText == null || geometryText.texts == null) {
            // Log.w(LOG_TAG, "PixelGeometryText null");
            return;
        }
        // 显示文字有问题，先不显示
        int startIndex = 0;
        // 一个geometry里面可能有多个文本，但style必定都是一样的
        Paint paint = new Paint();
        // TextStyle textStyle = new TextStyle();
        TextStyle textStyle = geometryText.textStyle;
        if (textStyle == null) {
            textStyle = new TextStyle();
        }
        for (int i = 0; i < geometry.parts.length; i++) {
            int part = geometry.parts[i];
            // 获取文本
            String text = geometryText.texts[i];
            Log.d(LOG_TAG, "text:" + text);
            // 设置文本是否倾斜
            if (textStyle.italic) {
                Typeface font = Typeface.create("", Typeface.ITALIC);
                paint.setTypeface(font);
            }
            // 设置文本是否使用粗体
            paint.setFakeBoldText(textStyle.bold);
            // 设置文本的尺寸（对应fontHeight属性）和行高，行高iserver不支持，默认5像素
            // 固定大小的时候单位是毫米
            if (textStyle.sizeFixed) {
                double textSizeD = getPixelByMillMeter(textStyle.fontHeight);
                float textSize = (float) textSizeD;
                Log.d(LOG_TAG, "textSize:" + textSize);
                paint.setTextSize(textSize);
            } else {
                double sizeD = getPixelByGeography(textStyle.fontHeight);
                float size = (float) sizeD;
                paint.setTextSize(size);
            }

            // 根据对齐方式对文字进行排版
            float width = paint.measureText(text);
            // Log.d(LOG_TAG, "measureText:" + width);
            double fontHeight = getPixelByMillMeter(textStyle.fontHeight);
            float height = (float) fontHeight;
            float x = geometry.points[startIndex * 2 + 0] * mapView.getDensity() + tileRect.left;
            float y = geometry.points[startIndex * 2 + 1] * mapView.getDensity() + tileRect.top;
            if (this.mapView.currentScale != 1.0F) {
                x = geometry.points[startIndex * 2 + 0] * mapView.getDensity() * mapView.currentScale + tileRect.left;
                y = geometry.points[startIndex * 2 + 1] * mapView.getDensity() * mapView.currentScale + tileRect.top;
            }
            // Log.d(LOG_TAG, "修改前x:" + x + ",y:" + y);
//            if (textStyle.align == TextAlignment.TOPLEFT) {
//                // paint.setTextAlign(Align.LEFT);
//            } else 
            if (textStyle.align == TextAlignment.TOPCENTER) {
                // paint.setTextAlign(Align.CENTER);
                x = x - width / 2;
            } else if (textStyle.align == TextAlignment.TOPRIGHT) {
                // paint.setTextAlign(Align.RIGHT);
                x = x - width;
            } else if (textStyle.align == TextAlignment.BOTTOMLEFT) {
                // paint.setTextAlign(Align.LEFT);
                y = y - height;
            } else if (textStyle.align == TextAlignment.BOTTOMCENTER) {
                // paint.setTextAlign(Align.CENTER);
                x = x - width / 2;
                y = y - height;
            } else if (textStyle.align == TextAlignment.BOTTOMRIGHT) {
                // paint.setTextAlign(Align.RIGHT);
                x = x - width;
                y = y - height;
            } else if (textStyle.align == TextAlignment.MIDDLELEFT) {
                // paint.setTextAlign(Align.LEFT);
                y = y - height / 2;
            } else if (textStyle.align == TextAlignment.MIDDLECENTER) {
                // paint.setTextAlign(Align.CENTER);
                x = x - width / 2;
                y = y - height / 2;
            } else if (textStyle.align == TextAlignment.MIDDLERIGHT) {
                // paint.setTextAlign(Align.RIGHT);
                x = x - width;
                y = y - height / 2;
            } else if (textStyle.align == TextAlignment.BASELINELEFT) {
                // paint.setTextAlign(Align.LEFT);
                y = y - height * 2 / 3;
            } else if (textStyle.align == TextAlignment.BASELINECENTER) {
                // paint.setTextAlign(Align.LEFT);
                x = x - width / 2;
                y = y - height * 2 / 3;
            } else if (textStyle.align == TextAlignment.BASELINERIGHT) {
                // paint.setTextAlign(Align.LEFT);
                x = x - width;
                y = y - height * 2 / 3;
            } 
//            else {
//                // 默认为左上
//                // paint.setTextAlign(Align.LEFT);
//            }

            // 首先判定是否需要绘制阴影，如果需要绘制，阴影应该在最下面
            if (textStyle.shadow) {
                // 先绘制阴影，字体的前景色无所谓，反正是在最底下，会被遮盖
                paint.setARGB(255, textStyle.foreColor.getRed(), textStyle.foreColor.getGreen(), textStyle.foreColor.getBlue());
                // 颜色取一个灰色，调成半透明
                int color = Color.argb(127, 50, 50, 50);
                // 绘制阴影层,设置阴影角度为0，阴影在x，y轴的距离默认为3个像素
                paint.setShadowLayer(0, 3, 3, color);
                canvas.drawText(text, x, y, paint);
                // 绘制完毕后清除阴影层
                paint.clearShadowLayer();
            }

            // 是否按照轮廓来显示背景,轮廓的实现是向四周8个方向平移绘制一遍实现的，轮廓这一层需要在阴影上面，字体下面
            if (textStyle.outline) {
                // 设置字体的轮廓，轮廓颜色使用背景色，桌面里面是这样定义的
                paint.setARGB(255, textStyle.backColor.getRed(), textStyle.backColor.getGreen(), textStyle.backColor.getBlue());
                int offset = 1;
                // Log.d(LOG_TAG, "修改后x:" + x + ",y:" + y);
                canvas.drawText(text, x + offset, y, paint);
                canvas.drawText(text, x, y + offset, paint);
                canvas.drawText(text, x + offset, y + offset, paint);
                canvas.drawText(text, x - offset, y + offset, paint);
                canvas.drawText(text, x - offset, y, paint);
                canvas.drawText(text, x, y - offset, paint);
                canvas.drawText(text, x - offset, y - offset, paint);
                canvas.drawText(text, x + offset, y - offset, paint);
                // Log.d(LOG_TAG, "canvas.drawText");
            }

            // 设置字体前景色(填充色)
            paint.setColor(Color.rgb(textStyle.foreColor.getRed(), textStyle.foreColor.getGreen(), textStyle.foreColor.getBlue()));
            canvas.drawText(text, x, y, paint);
            startIndex += part;
        }
        Log.d(LOG_TAG, "drawVectorText times:" + (System.currentTimeMillis() - s) + "ms");
    }

    private double getPixelByGeography(double ge) {
        double res = this.mapView.getResolution();
        return ge / res;
    }

    private double getPixelByMillMeter(double mm) {
        double dpi = 96.0;
        if (this.dpi != -1) {
            dpi = this.dpi;
        }
        return mm * dpi / 25.4;
    }

    class LayerStyle {
        public ServerStyle style;
        public DatasetType type;

        public LayerStyle(ServerStyle style, DatasetType type) {
            this.style = style;
            this.type = type;
        }
    }

    /**
     * <p>
     * 设置缓存大小，单位是张，指明最多缓存多少瓦片
     * </p>
     * @param size 缓存张数
     * @since 7.0.0
     */
    public void setCacheSize(int size) {
        if (this.vectorTileCacher != null) {
            this.vectorTileCacher.setCacheSize(size);
        }
    }

    // public void setSleep(boolean sleep, long time) {
    // this.sleep = sleep;
    // this.sleepTime = time;
    // }
}
