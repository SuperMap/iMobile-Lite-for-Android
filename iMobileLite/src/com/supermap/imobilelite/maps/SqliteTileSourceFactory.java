package com.supermap.imobilelite.maps;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;


/**
 * 管理Sqlite数据库的工厂工具类，只允许一个实例，这里数据库文件存放在SD卡中，方便用户手动管理。
 * @author huangqinghua
 * 
 */
class SqliteTileSourceFactory {
    private static final String LOG_TAG = "com.supermap.android.maps.sqlitetilesourcefactory";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private static boolean canUseSDCard = false;
    // private static final String SELECT_TILE_BYRES_SQL = "SELECT tile_data FROM tiles WHERE tile_column=%d AND tile_row=%d AND resolution>%d and resolution<%d;";
    private static final String SELECT_TILE_BYRES_SQL = "SELECT tile_data FROM tiles WHERE tile_column=? AND tile_row=? AND resolution>? and resolution<?;";
    private static final String SELECT_TILE_BYLEVEL = "SELECT tile_data FROM tiles WHERE tile_column=? AND tile_row=? AND zoom_level=?;";
    private static final double RESOLUTION_PRECESION = 1.0E-6;// 分辨率精度，在该精度范围内，认为两个分辨率相等。
    private static Map<Integer, Double> resolutions = new HashMap<Integer, Double>();
    static final String SQLITE_CACHE_DIRECTORY = "supermap/sqlite";
    private static File cacheFile;

    static {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state) || "mounted_ro".equals(state)) {
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.SQLITETILESOURCEFACTORY_SDCARD_STATE, state));
            canUseSDCard = true;
            cacheFile = new File(Environment.getExternalStorageDirectory(), SQLITE_CACHE_DIRECTORY);
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
        }
    }

    private Map<String, SQLiteDatabase> layerDatabaseMap = new ConcurrentHashMap<String, SQLiteDatabase>();
    private Map<String, TileResolutionInfo> mapResolutionInfoPair = new ConcurrentHashMap<String, TileResolutionInfo>();
    private static final SqliteTileSourceFactory instance = new SqliteTileSourceFactory();

    private SqliteTileSourceFactory() {
        super();
    }

    public static SqliteTileSourceFactory getInstance() {
        return instance;
    }

    public boolean openSQLiteDatabase(String layerName) {
        if (!canUseSDCard) {
            return false;
        }
        if (layerDatabaseMap.containsKey(layerName) && layerDatabaseMap.get(layerName) != null && layerDatabaseMap.get(layerName).isOpen()) {
            return true;
        }
        try {
            File file = new File(cacheFile, layerName + ".mbtiles");
            if (!file.exists()) {
                file = new File(cacheFile, layerName + ".smtiles");
                if (!file.exists()) {
                    return false;
                }
            }
            String path = file.getAbsolutePath();// "/mnt/sdcard/supermap/" + name;// 默认值应该是这个
            Log.i(LOG_TAG, resource.getMessage(MapCommon.SQLITETILESOURCEFACTORY_DATABASE_INFO, new String[] { layerName, path }));
            SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, 0);
            if (db != null && db.isOpen()) {
                layerDatabaseMap.put(layerName, db);
                return true;
            }
        } catch (SQLiteException e) {
            return false;
        }
        return false;
    }

    public byte[] getTileBytes(Tile tile) {
        if (tile == null) {
            return null;
        }
        String layerName = tile.getLayerNameCache();
        SQLiteDatabase db = layerDatabaseMap.get(layerName);
        if (db != null) {
            double resolution = getResolutionFromScale(layerName, tile.getScale());
            if (Math.abs(resolution) < 1.0E-6 && Math.abs(resolution) > -1.0E-6) {
                return null;
            }
            // String queryString = String.format(SELECT_TILE_BYRES_SQL, tile.getY(), tile.getX(), resolution - RESOLUTION_PRECESION, resolution
            // + RESOLUTION_PRECESION);
            // int y = getDBY(tile, resolution);
            int y = tile.getY();
            int level = getLevel(resolution, "3857");
            Cursor result = null;
            byte[] resultBytes = null;
            try {
                if (level > -1) {
                    y = (int) displaceY(level, y);
                    String argus[] = { String.valueOf(tile.getX()), String.valueOf(y), String.valueOf(level) };
                    result = db.rawQuery(SELECT_TILE_BYLEVEL, argus);
                    result.moveToFirst();
                    resultBytes = result.getBlob(0);
                    return resultBytes;
                } else {
                    String argus[] = { String.valueOf(tile.getX()), String.valueOf(y), String.valueOf(resolution - RESOLUTION_PRECESION),
                            String.valueOf(resolution + RESOLUTION_PRECESION) };
                    result = db.rawQuery(SELECT_TILE_BYRES_SQL, argus);
                    result.moveToFirst();
                    resultBytes = result.getBlob(0);
                    return resultBytes;
                }
            } catch (RuntimeException e) {
                return null;
            } finally {
                if (result != null) {
                    result.close();
                }
            }
        }
        return null;
    }

    /**
     * 设置地图分辨率
     */
    public void setLayerResolutionInfo(String layNameCache, double scale, double resolution) {
        if (!canUseSDCard) {
            return;
        }
        /*SQLiteDatabase db = layerDatabaseMap.get(layNameCache);
        if (db == null) {
            return;
        }*/
        TileResolutionInfo resolutionInfo = new TileResolutionInfo();
        resolutionInfo.scale = scale;
        resolutionInfo.resolution = resolution;
        mapResolutionInfoPair.put(layNameCache, resolutionInfo);
    }

    /**
     * 获取当前比例尺下的地图分辨率
     */
    private strictfp double getResolutionFromScale(String layName, double scale) {
        TileResolutionInfo resolutionInfo = this.mapResolutionInfoPair.get(layName);
        if (resolutionInfo != null) {
            return verifyResolution(resolutionInfo.scale * resolutionInfo.resolution / scale);
        } else {
            Log.i(LOG_TAG, resource.getMessage(MapCommon.SQLITETILESOURCEFACTORY_RESOLUTION_INFO, layName));
            return 0d;// 判断是否可以计算分辨率，按照默认96 dpi
        }
    }

    public boolean dispose() {
        if (layerDatabaseMap.size() > 0) {
            Iterator<Entry<String, SQLiteDatabase>> it = layerDatabaseMap.entrySet().iterator();
            Log.i(LOG_TAG, resource.getMessage(MapCommon.SQLITETILESOURCEFACTORY_OPENED_SQLITEDATABASE, layerDatabaseMap.size()));
            while (it.hasNext()) {
                Entry<String, SQLiteDatabase> entry = it.next();
                Log.i(LOG_TAG, resource.getMessage(MapCommon.SQLITETILESOURCEFACTORY_CLOSE_SQLITEDATABASE, entry.getKey()));
                entry.getValue().close();
            }
            layerDatabaseMap.clear();
        }
        return true;
    }

    // 得到数据库中实际存储的Y
    /*private int getDBY(Tile tile, double resolution) {
        // double resolution = 0d;// = getResolutionFromScale();
        int z = getLevel(resolution, "3857");
        int y = tile.getY();
        if (z >= 0) {
            y = (int) displaceY(z, y);
        }
        return y;
    }*/

    private static void initLODInfos() {
        double resolution = 156543.033928;
        for (int i = 0; i < 23; i++) {
            resolutions.put(i, resolution);
            resolution /= 2.0;
        }
    }

    /**
     * 根据分辨率，epsgcode获取级别，若投影不是3857或分辨率不是MBTiles规范分辨率返回-1
     * @param resolution
     * @param epsgCode
     * @return
     */
    private int getLevel(double resolution, String epsgCode) {
        if (!epsgCode.equals("3857")) {
            return -1;
        }
        if (resolutions.isEmpty()) {
            initLODInfos();
        }
        Iterator<Entry<Integer, Double>> iter = resolutions.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Integer, Double> entry = iter.next();
            if (Math.abs(resolution - entry.getValue()) < RESOLUTION_PRECESION) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * <p>把由原点为左上角计算出的行号转成符合MBTiles规范的行号</p>
     * <p>MBTiles切片符合TMS规范，投影为WebMercator，其切片原点为(0,0)</p>
     * @param zoom 级别
     * @param y 行号
     * @return
     */
    private long displaceY(long zoom, long y) {
        if (zoom < 0) {
            return y;
        }
        return (1 << zoom) - y - 1;
    }

    private class TileResolutionInfo {
        public double scale;
        public double resolution;
    }

    /**
     * <p>
     * 使用与mbtiles中计算resolution的算法来修正当前的resolution
     * </p>
     * @param resolution
     * @return
     */
    private double verifyResolution(double resolution) {
        int n = (int) Math.log10(resolution);
        int precision = 11;
        if (resolution < 1.0) {
            n--;
        }
        int scale = precision - 1 - n;
        return BigDecimal.valueOf(resolution).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
