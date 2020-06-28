package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * MBTiles图层即离线图层，如果sdcard中没有离线缓存MBTiles则也不发送请求即不出图。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
public class MBTilesLayerView extends AbstractTileLayerView {
    private static final String LOG_TAG = "MBTilesLayerView";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private String mbtilesPath = "";
    private MBTilesUtil mbtilesHelper;
    private MBTilesMetadata metadata;
    // private boolean compatible;
    private int tileSize = 256;
    // private AsyncGetMBTiles asyncGetMBTiles;
    // 存储当前所需的不在内存缓存中的所有的瓦片信息
    private List<MBTileMessage> tileList = new ArrayList<MBTileMessage>();
    private GetMBTilesTask getMBTilesTask;
    private RefreshHandler refreshHandler;
    private double[] metaResolutions;
    private boolean getMBTilesTaskOver = false;
    private boolean sleep = true;
    // 读取图片的线程睡眠的时间，单位ms
    private long sleepTime = 400L;
    // private String imageFormat = "png";
    // 是否是第一次出图，第一次就不让读取图片的线程睡眠
    private boolean firstOutputTile = true;
    // 标识离线缓存是标准的MBTiles还是iserver扩展的SMTiles，标准的为true。
    private boolean compatible;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文
     */
    public MBTilesLayerView(Context context) {
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
    public MBTilesLayerView(Context context, AttributeSet attrs) {
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
    public MBTilesLayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文
     * @param mbtilesPath 设置Mbtiles所在的路径，该路径可以是绝对路径（/mnt/sdcard/supermap/*.mbtiles） 也可以是相对路径（相对于内置sdcard的路径，如"supermap/*.mbtiles"),建议使用绝对路径
     */
    public MBTilesLayerView(Context context, String mbtilesPath) {
        super(context);
        setMbtilesPath(mbtilesPath);
        initialize();
    }

    /**
     * <p>
     * 设置Mbtiles所在的路径，该路径相对于sdcard的路径，如"supermap/Mbtiles/*.mbtiles"
     * 并读取Mbtiles的源信息初始化图层状态
     * </p>
     * @param value
     */
    public void setMbtilesPath(String value) {
        mbtilesPath = value;
        mbtilesHelper = new MBTilesUtil(mbtilesPath);
        if (!mbtilesHelper.open()) {
            Toast.makeText(context, "MBTilesPath:" + mbtilesPath + " isn't existed,please check!", Toast.LENGTH_LONG);
            return;
        }
        metadata = mbtilesHelper.readMBTilesMetadata();
        if (metadata == null) {
            Log.w(LOG_TAG, "metadata in MBTiles is null!");
            return;
        }
        // 设置图层元数据信息
        this.compatible = metadata.compatible;
        if (!this.compatible) {
            this.layerBounds = metadata.bounds;
            // 解决切图范围的左上不是切图源点时，出图错误的问题
            if (metadata.axis_origin != null) {
                this.layerBounds.leftTop = new Point2D(metadata.axis_origin);
            }
            this.resolutions = metadata.resolutions;
            this.metaResolutions = metadata.resolutions.clone();
            this.visibleScales = metadata.scales;
            this.tileSize = metadata.tileSize;
            // this.imageFormat = metadata.format;
            this.crs = new CoordinateReferenceSystem();
            this.crs.wkid = metadata.crs_wkid;
            this.crs.unit = metadata.unit;
            isGCSLayer = Util.isGCSCoordSys(this.crs);
            if (isGCSLayer) {
                // 分辨率统一以米为单位，Mbtiles中地图投影为4326那么resolutions则是以度为单位，此处把度转换成米
                double radius = this.crs.datumAxis > 1d ? this.crs.datumAxis : Constants.DEFAULT_AXIS;
                for (int i = 0; i < resolutions.length; i++) {
                    resolutions[i] = resolutions[i] * Math.PI * radius / 180.0;
                }
            }
        } else {
            this.isGCSLayer = false;
            this.crs = new CoordinateReferenceSystem();
            this.crs.unit = "meter";
            this.crs.wkid = 3857;
            this.resolutions = mbtilesHelper.getResolutions();
            this.layerBounds = metadata.bounds;
        }
        if (layerBounds != null && resolutions != null && resolutions.length > 0) {
            isLayerInited = true;// 不用发送请求，因为初始化了layerBounds、resolutions就可以了
        }
    }

    private void initialize() {
        layerName = "MBTilesLayer_";// 清除sd卡缓存需要
        if (!StringUtils.isEmpty(mbtilesPath)) {
            layerName += mbtilesPath.substring(mbtilesPath.lastIndexOf("/") + 1, mbtilesPath.lastIndexOf("."));
        }
        curMapUrl = "http://support.supermap.com.cn:8090/iserver/services/mapInstance/rest/maps/" + layerName;// sd卡缓存需要SuperMapCloud这个名称
        // this.dpi = 0.0254 / 96.0;
        this.addToNetworkDownload = false;
        refreshHandler = new RefreshHandler();
    }

    @Override
    String getLayerCacheFileName() {
        return super.getLayerCacheFileName() + "_MBTL";
    }

    /**
     * <p>
     * 初始化瓦片的内容
     * </p>
     * @param tile
     * @return
     */
    @Override
    public void initTileContext(Tile tile) {
        // 瓦片大小不是256*256，不出图。
        if (mbtilesHelper == null || !mbtilesHelper.isOpen() || this.tileSize != 256) {
            return;
        }
        int index = getResolutionIndex();
        if (index == -1) {
            return;
        }
        // Log.d(LOG_TAG, "getResolutionIndex:" + index);
        // 首先判断内存缓存对象是否存在，1.存在内存缓存对象且包含当前瓦片则读取缓存无需查询数据库获取，增强效率；2.存在内存缓存对象但不包含当前瓦片则查询数据库获取瓦片并存储到内存缓存；3.不存在内存缓存对象则查询数据库获取瓦片
        ITileCache mCache = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY);
        // 存在内存缓存对象
        if (mCache != null) {
            Tile ct = mCache.getTile(tile);
            if (ct != null && ct.getBitmap() != null) {
                // 包含当前瓦片则读取缓存无需查询数据库获取
                tile.setBitMap(ct.getBitmap());
            } else {
                // 不包含当前瓦片则查询数据库获取瓦片并存储到内存缓存
                initTileBitMap(tile, index);
            }
        } else {
            // 不存在内存缓存对象则查询数据库获取瓦片
            initTileBitMap(tile, index);
        }

        tile.setUrl(null);
    }

    /**
     * <p>
     * 查询数据库获取瓦片并存储到内存缓存
     * </p>
     * @param tile 瓦片对象
     * @param mCache 内存缓存对象
     * @param index 缩放级别
     */
    private void initTileBitMap(Tile tile, int index) {
        // 改换异步读取MBTiles数据库，这样不会导致主线程卡顿
        if (StringUtils.isEmpty(mbtilesPath) || mbtilesHelper == null || !mbtilesHelper.isOpen() || index < 0) {
            return;
        }
        // 加上最小级别，换算成从左上角点出图,MBTiles是从左 下 角切图（supermap iServer切的MBTiles缓存也遵循此标准）
        double resolution = 0.0f;
        if (metaResolutions != null && index < metaResolutions.length) {
            resolution = this.metaResolutions[index];
        }
        // if (isGCSLayer) {
        // //
        // Mbtiles中地图投影为4326那么resolutions则是以度为单位，此处把之前转换成以米为单位的resoltion值还原成以度为单位
        // double radius = this.crs.datumAxis > 1d ? this.crs.datumAxis :
        // Constants.DEFAULT_AXIS;
        // resolution = resolution / (Math.PI * radius / 180.0);
        // }
        tileList.add(new MBTileMessage(tile, resolution));
    }

    @Override
    public void asyncGetTilesFromCache() {
        // 异步去读取缓存数据tileList
        if (getMBTilesTask == null) {
            getMBTilesTask = new GetMBTilesTask();
            getMBTilesTask.start();
        }
        if (!getMBTilesTask.isAlive()) {
            getMBTilesTask.start();
        }
        SyncTask task = buildSyncTask();
        if (!"".equals(task.sql)) {
            // 根据需要不断同步修改需要执行的同步任务
            getMBTilesTask.setSyncTask(task);
        }
    }

    private SyncTask buildSyncTask() {
        List<Tile> tiles = new ArrayList<Tile>();
        int len = tileList.size();
        // String sql = "";
        StringBuilder sb = new StringBuilder();
        int zoom = getResolutionIndex();
        for (int i = 0; i < len; i++) {
            MBTileMessage mm = tileList.get(i);
            tiles.add(mm.tile);
            if (compatible) {
                // 构建标准mbtiles缓存的查询sql语句
                int y = MBTilesUtil.displaceY(zoom, mm.tile.getY());
                sb.append("(tile_column=").append(mm.tile.getX()).append(" AND tile_row=").append(y).append(" AND zoom_level=").append(zoom).append(")");
                // sql = sql + "(tile_column=" + mm.tile.getX() +
                // " AND tile_row=" + y + " AND zoom_level=" + zoom + ")";
            } else {
                // 构建扩展mbtiles的SMTlies缓存查询sql语句
                sb.append("(tile_column=").append(mm.tile.getX()).append(" AND tile_row=").append(mm.tile.getY()).append(" AND resolution=")
                        .append(mm.resolution).append(")");
                // sql = sql + "(tile_column=" + mm.tile.getX() +
                // " AND tile_row=" + mm.tile.getY() + " AND resolution=" +
                // mm.resolution + ")";
            }
            if (i != len - 1) {
                sb.append(" OR ");
                // sql += " OR ";
            }
        }
        // 运行完一次，必须清空tileList，用来存储下次屏幕所需的瓦片
        tileList.clear();
        SyncTask task = new SyncTask(tiles, sb.toString());
        return task;
    }

    /**
     * <p>
     * 资源释放，特别是关闭缓存数据库和终止子线程，建议推出应用时直接调用或是调用mapview的销毁
     * </p>
     * @since 7.0.0
     */
    public void destroy() {
        if (mbtilesHelper != null) {
            mbtilesHelper.close();
        }
        if (getMBTilesTask != null) {
            getMBTilesTask.interrupt();// 存在sleep，中断无效，所以增加getMBTilesTaskOver标志
            getMBTilesTaskOver = true;
            getMBTilesTask = null;
        }
    }

    void refresh() {
        Log.d(LOG_TAG, "刷新地图");
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

    class MBTileMessage {
        public Tile tile;
        public double resolution;

        public MBTileMessage(Tile tile, double resolution) {
            super();
            this.tile = tile;
            this.resolution = resolution;
        }
    }

    /**
     * <p>
     * 读取MBTiles的任务线程，运行一次读取当前屏幕所需的不在内存缓存的所有瓦片，即tileList存储的瓦片
     * </p>
     * @author ${huangqh}
     * @version ${Version}
     * @since 6.1.3
     * 
     */
    class GetMBTilesTask extends Thread {
        private static final String ROOT_SQL = "SELECT tile_column,tile_row,tile_data FROM tiles WHERE (";
        private static final String CONDITION_SQL = "(tile_column=? AND tile_row=? AND resolution=?)";
        // private static final String CONDITION_SQL = "(tile_column=? AND tile_row=? AND resolution>? and resolution<?)";
        private static final double RESOLUTION_PRECESION = 1.0E-6;
        private SyncTask task = null;

        @Override
        public void run() {
            while (!getMBTilesTaskOver) {
                // String sql = "";
                // // 初始化组装sql语句和sql参数
                // Log.d(LOG_TAG, "tileList size:" + tileList.size());
                // double r = 0;
                // int len = tileList.size();
                // for (int i = 0; i < len; i++) {
                // MBTileMessage mm = tileList.get(i);
                // // argus.add(String.valueOf(mm.tile.getX()));
                // // argus.add(String.valueOf(mm.tile.getY()));
                // // argus.add(String.valueOf(mm.resolution));
                // // argus.add(String.valueOf(mm.resolution- RESOLUTION_PRECESION));
                // // argus.add(String.valueOf(mm.resolution+ RESOLUTION_PRECESION));
                // // sql = sql + CONDITION_SQL;
                // sql = sql + "(tile_column=" + mm.tile.getX() + " AND tile_row=" + mm.tile.getY() + " AND resolution=" + mm.resolution + ")";
                // if (i != len - 1) {
                // sql += " OR ";
                // } else {
                // r = mm.resolution;
                // }
                // }
                // // 运行完一次。必须清空tileList，用来存储下次屏幕所需的瓦片
                // tileList.clear();
                // Log.d(LOG_TAG, "resolution:" + r);
                // 根据sql语句和sql参数执行查询返回瓦片二进制数组，并做缓存
                Map<String, byte[]> byteMap = null;
                try {
                    // 异步读取同步任务，非空执行任务
                    SyncTask st = getSyncTask();
                    if (st != null && !"".equals(st.sql)) {
                        List<String> argus = new ArrayList<String>();
                        String mySql = st.sql;
                        mySql = ROOT_SQL + mySql + ");";
                        Log.d(LOG_TAG, "sql:" + mySql);
                        byteMap = mbtilesHelper.getTiles(mySql, argus);
//                        if (byteMap != null) {
//                            Log.d(LOG_TAG, "tileFromMBTiles byteMap size:" + byteMap.size());
//                        }
                        ITileCache mCache = getTileCacher().getCache(TileCacher.CacheType.MEMORY);
                        Log.d(LOG_TAG, "st.tiles.size():" + st.tiles.size());
                        if (byteMap != null && byteMap.size() > 0) {
                            for (int i = 0; i < st.tiles.size(); i++) {
                                Tile mTile = st.tiles.get(i);
                                String key = null;
                                if (compatible) {
                                    key = mTile.getX() + "_" + MBTilesUtil.displaceY(getResolutionIndex(), mTile.getY());
                                } else {
                                    key = mTile.getX() + "_" + mTile.getY();
                                }
                                byte[] bs = byteMap.get(key);
                                if (bs != null && bs.length > 0) {
                                    mTile.setBytes(bs);
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inDither = false;
                                    options.inPurgeable = true;
                                    options.inInputShareable = true;
                                    options.inTempStorage = new byte[32 * 1024];
                                    Bitmap bm = BitmapFactory.decodeByteArray(bs, 0, bs.length, options);
                                    mTile.setBitMap(bm);
                                    if (mCache != null) {
                                        mCache.addTile(mTile);
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
                        // Log.d(LOG_TAG, "byteMap null");
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

    // public void setSleep(boolean sleep, long time) {
    // this.sleep = sleep;
    // this.sleepTime = time;
    // }

}
