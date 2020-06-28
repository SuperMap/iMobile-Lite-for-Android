package com.supermap.imobilelite.maps;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import com.supermap.services.components.commontypes.PixelGeometry;
import com.supermap.services.components.commontypes.PixelGeometryText;
import com.supermap.services.rest.util.JsonConverter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

/**
 * <p>
 * MBTiles图块信息管理工具,负责图层信息的读取和编辑（暂时不支持编辑）
 * 通过db文件路径来获取数据信息,dbFilePath设置数据库文件路径，使用前要调用open(),连接成功后才能进行下一步操作，如果不再需要连接可以调用close()关闭
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
/**
 * <p>
 * 读取MBTiles、smTiles和svTiles文件的工具类。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
class MBTilesUtil {
    private static final String LOG_TAG = "MBTilesUtil";
    private String dbFilePath = "";
    // private boolean canUseSDCard = false;
    // private File cacheFile;
    // 二进制文件来源，是AS写入的还是其他，控制查询参数
    // public static Boolean bytefromIsAS = false;
    private SQLiteDatabase db;
    private boolean open = false;
    private static Map<Integer, Double> resolutions = new HashMap<Integer, Double>();
    private static final double RESOLUTION_PRECESION = 1.0E-6;// 分辨率精度，在该精度范围内，认为两个分辨率相等。
    private static final String SELECT_TILE_BYRES_SQL = "SELECT tile_data FROM tiles WHERE tile_column=? AND tile_row=? AND resolution>? and resolution<?;";
    private static final String SELECT_TILE_BYLEVEL = "SELECT tile_data FROM tiles WHERE tile_column=? AND tile_row=? AND zoom_level=?;";

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param dbFilePath
     */
    public MBTilesUtil(String dbFilePath) {
        super();
        this.dbFilePath = dbFilePath;
    }

    /**
     * <p>
     * 连接数据库，返回true表示成功
     * </p>
     * @return
     */
    public boolean open() {
        String state = Environment.getExternalStorageState();
        File cacheFile = null;
        if ("mounted".equals(state) || "mounted_ro".equals(state)) {
            // canUseSDCard = true;
            cacheFile = new File(Environment.getExternalStorageDirectory(), dbFilePath);
            Log.d(LOG_TAG, "dbFilePath:" + cacheFile.getAbsolutePath());
            if (!openFile(cacheFile)) {
                cacheFile = new File(dbFilePath);
                Log.d(LOG_TAG, "dbFilePath:" + dbFilePath);
                return openFile(cacheFile);
            } else {
                return true;
            }
        } else {
            cacheFile = new File(dbFilePath);
            Log.d(LOG_TAG, "dbFilePath:" + dbFilePath);
            return openFile(cacheFile);
        }
    }

    private boolean openFile(File file) {
        if (file.exists()) {
            String path = file.getAbsolutePath();
            db = SQLiteDatabase.openDatabase(path, null, 0);
            if (db != null && db.isOpen()) {
                this.open = true;
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 数据库是否已经打开
     * </p>
     * @return
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * <p>
     * 断开数据库连接，操作成功返回true
     * </p>
     * @return
     */
    public boolean close() {
        boolean isClose = false;
        if (db != null && db.isOpen()) {
            db.close();
            isClose = !db.isOpen();
            db = null;
        }
        return isClose;
    }

    /**
     * <p>
     * 获取瓦片的内容
     * </p>
     * @param x 瓦片的列号
     * @param y 瓦片的行号
     * @param resolution 瓦片所处的分辨率
     * @return
     */
    public byte[] getTile(int x, int y, double resolution) {
        if (!this.open || db == null) {
            return null;
        }
        int level = getLevel(resolution, "3857");
        Cursor result = null;
        byte[] resultBytes = null;
        try {
            if (level > -1) {
                y = (int) displaceY(level, y);
                String argus[] = { String.valueOf(x), String.valueOf(y), String.valueOf(level) };
                result = db.rawQuery(SELECT_TILE_BYLEVEL, argus);
                result.moveToFirst();
                resultBytes = result.getBlob(0);
                return resultBytes;
            } else {
                String argus[] = { String.valueOf(x), String.valueOf(y), String.valueOf(resolution - RESOLUTION_PRECESION),
                        String.valueOf(resolution + RESOLUTION_PRECESION) };
                // long s = System.currentTimeMillis();
                result = db.rawQuery(SELECT_TILE_BYRES_SQL, argus);
                // Log.d(LOG_TAG, "Query times:" + (System.currentTimeMillis() - s) + "ms");
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

    /**
     * <p>
     * 获取指定SQL查询的瓦片内容
     * </p>
     * @param sql 瓦片查询sql语句
     * @param argus 查询语句所需参数
     * @return 结果集hashmap
     * @since 7.0.0
     */
    public Map<String, byte[]> getTiles(String sql, List<String> argus) {
        if (!this.open || db == null) {
            return null;
        }
        Cursor result = null;
        Map<String, byte[]> resultBytesMap = new HashMap<String, byte[]>();
        if (sql != null && !"".equals(sql)) {
            String[] arguStrs = null;
            if (argus != null && argus.size() > 0) {
                arguStrs = new String[argus.size()];
                arguStrs = argus.toArray(arguStrs);
            }
            try {
                long s = System.currentTimeMillis();
                // 该接口组装sql语句时可能会出现参数为double值时小数点后面数字缺失，使用=查不出结果，必须使用范围查询，所以最好不用argus传参
                result = db.rawQuery(sql, arguStrs);
                Log.d(LOG_TAG, "Query Tiles times:" + (System.currentTimeMillis() - s) + "ms");
                if (result != null) {
                    Log.d(LOG_TAG, "Tile Cursor Count:" + result.getCount());
                    for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                        String x = result.getString(0);
                        String y = result.getString(1);
                        byte[] resultBytes = result.getBlob(2);
                        resultBytesMap.put(x + "_" + y, resultBytes);
                    }
                }
                return resultBytesMap;
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
     * <p>
     * 读取当前的tiles数据库元数据信息，以供出图过程中使用 关于Metadata的使用，请查看MetaDataObj的相关属性
     * </p>
     * 
     * @return
     */
    public MBTilesMetadata readMBTilesMetadata() {
        if (!this.open || db == null) {
            return null;
        }
        Cursor cursor = db.rawQuery("SELECT value from metadata WHERE name ='compatible'", null);
        Cursor result = db.rawQuery("select * from metadata", null);

        MBTilesMetadata metadata = new MBTilesMetadata();
        if (result == null || cursor == null) {
            return metadata;
        }
        try {
            boolean isMBTiles = !cursor.moveToFirst() || !cursor.getString(0).equals("false");
            if (isMBTiles) {
                metadata.compatible = true;
                for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                    String key = result.getString(0);
                    String value = result.getString(1);
                    if ("bounds".equalsIgnoreCase(key)) {
                        metadata.bounds = metadata.getLayerBounds(value);
                    } else if ("name".equalsIgnoreCase(key)) {
                        metadata.name = value;
                    } else if ("type".equalsIgnoreCase(key)) {
                        metadata.type = value;
                    } else if ("version".equalsIgnoreCase(key)) {
                        metadata.version = value;
                    } else if ("description".equalsIgnoreCase(key)) {
                        metadata.description = value;
                    } else if ("format".equalsIgnoreCase(key)) {
                        metadata.format = value;
                    }
                }
                result.close();
                return metadata;
            }
            // 根据查询信息，构建metadata对象
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                String key = result.getString(0);
                String value = result.getString(1);
                if ("bounds".equalsIgnoreCase(key)) {
                    metadata.bounds = metadata.getLayerBounds(value);
                } else if ("name".equalsIgnoreCase(key)) {
                    metadata.name = value;
                } else if ("type".equalsIgnoreCase(key)) {
                    metadata.type = value;
                } else if ("version".equalsIgnoreCase(key)) {
                    metadata.version = value;
                } else if ("description".equalsIgnoreCase(key)) {
                    metadata.description = value;
                } else if ("format".equalsIgnoreCase(key)) {
                    metadata.format = value;
                } else if ("resolutions".equalsIgnoreCase(key)) {
                    metadata.resolutions = metadata.getResolutionsFromStr(value);
                } else if ("scales".equalsIgnoreCase(key)) {
                    metadata.scales = metadata.getScalesFromStr(value);
                } else if ("crs_wkid".equalsIgnoreCase(key)) {
                    metadata.crs_wkid = Integer.valueOf(value);
                } else if ("compatible".equalsIgnoreCase(key)) {
                    metadata.compatible = Boolean.valueOf(value);
                } else if ("transparent".equalsIgnoreCase(key)) {
                    metadata.transparent = Boolean.valueOf(value);
                } else if ("tile_height".equalsIgnoreCase(key)) {
                    metadata.tileSize = Integer.valueOf(value);
                } else if ("crs_wkt".equalsIgnoreCase(key)) {
                    if (value.contains("UNIT")) {
                        String unitStr = value.substring(value.lastIndexOf("UNIT"));
                        metadata.unit = comfirmUnit(unitStr);
                    }
                } else if ("axis_origin".equalsIgnoreCase(key)) {
                    metadata.axis_origin = getOrigin(value);
                }
            }
        } finally {
            //if (result != null) {
                result.close();
            //}
            //if (cursor != null) {
                cursor.close();
            //}
        }
        return metadata;
    }

    /**
     * <p>
     * 读取切图源点
     * </p>
     * @param origin 切图源点字符串
     * @return
     * @since 7.0.0
     */
    private Point2D getOrigin(String origin) {
        if (StringUtils.isEmpty(origin)) {
            return null;
        }
        String[] originArr = origin.split(",");
        if (originArr != null && originArr.length == 2) {
            return new Point2D(Double.valueOf(originArr[0]), Double.valueOf(originArr[1]));
        } else {
            return null;
        }
    }

    /**
     * 根据分辨率，epsgcode获取级别，若投影不是3857或分辨率不是MBTiles规范分辨率返回-1
     * @param resolution
     * @param epsgCode
     * @return
     */
    protected static int getLevel(double resolution, String epsgCode) {
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

    private static void initLODInfos() {
        double resolution = 156543.033928;
        for (int i = 0; i < 23; i++) {
            resolutions.put(i, resolution);
            resolution /= 2.0;
        }
    }

    /**
     * 为规范的MBTiles时,获取固定3857比例尺
     */
    public double[] getResolutions() {
        String sql = "SELECT max(zoom_level) FROM tiles";
        Cursor cursor = db.rawQuery(sql, null);
        int maxZoomLevel = -1;
        if (cursor != null && cursor.getCount() == 1 && cursor.moveToFirst()) {
            maxZoomLevel = cursor.getInt(0);
        }
        if (maxZoomLevel < 0) {
            return null;
        }
        double[] resolutions = new double[maxZoomLevel + 1];
        resolutions[0] = 156543.033928;
        for (int i = 0; i <= maxZoomLevel; i++) {
            resolutions[i] = resolutions[0] * Math.pow(Double.valueOf(2), -i);
        }
        return resolutions;
    }

    /**
     * <p>
     * 把由原点为左上角计算出的行号转成符合MBTiles规范的行号
     * </p>
     * <p>
     * MBTiles切片符合TMS规范，投影为WebMercator，其切片原点为(0,0)
     * </p>
     * @param zoom 级别
     * @param y 行号
     * @return
     */
    protected static int displaceY(int zoom, int y) {
        if (zoom < 0) {
            return y;
        }
        return (1 << zoom) - y - 1;
    }

    private String comfirmUnit(String unitStr) {
        String unit = "degree";
        if (StringUtils.isEmpty(unitStr)) {
            return unit;
        }
        if (unitStr.indexOf("METER") > -1) {
            unit = "meter";
        } else if (unitStr.indexOf("DEGREE") > -1) {
            unit = "degree";
        } else if (unitStr.indexOf("DECIMAL_DEGREE") > -1) {
            unit = "decimal_degree";
        } else if (unitStr.indexOf("CENTIMETER") > -1) {
            unit = "centimeter";
        } else if (unitStr.indexOf("DECIMETER") > -1) {
            unit = "decimeter";
        } else if (unitStr.indexOf("FOOT") > -1) {
            unit = "foot";
        } else if (unitStr.indexOf("INCH") > -1) {
            unit = "inch";
        } else if (unitStr.indexOf("KILOMETER") > -1) {
            unit = "kilometer";
        } else if (unitStr.indexOf("MILE") > -1) {
            unit = "mile";
        } else if (unitStr.indexOf("MILIMETER") > -1) {
            unit = "milimeter";
        } else if (unitStr.indexOf("MINUTE") > -1) {
            unit = "minute";
        } else if (unitStr.indexOf("RADIAN") > -1) {
            unit = "radian";
        } else if (unitStr.indexOf("SECOND") > -1) {
            unit = "second";
        } else if (unitStr.indexOf("YARD") > -1) {
            unit = "yard";
        }
        return unit;
    }

    /**
     * <p>
     * 获取指定SQL查询的矢量瓦片内容，并封装查询结果
     * </p>
     * @param sql 查询矢量瓦片的sql语句
     * @param argus 查询语句所需参数
     * @return 结果封装对象集hashmap
     * @since 7.0.0
     */
    public Map<String, List<VectorGeometryData>> getVectorTiles(String sql, List<String> argus) {
        if (!this.open || db == null) {
            return null;
        }
        Cursor result = null;
        Cursor queryresult = null;
        Map<String, List<VectorGeometryData>> resultBytesMap = new HashMap<String, List<VectorGeometryData>>();
        if (sql != null && !"".equals(sql)) {
            String[] arguStrs = null;
            if (argus != null && argus.size() > 0) {
                arguStrs = new String[argus.size()];
                arguStrs = argus.toArray(arguStrs);
            }
            try {
                long s = System.currentTimeMillis();
                // 该接口组装sql语句时可能会出现参数为double值时小数点后面数字缺失，使用=查不出结果，必须使用范围查询，所以最好不用argus传参
                result = db.rawQuery(sql, arguStrs);
                Log.d(LOG_TAG, "Query VectorTile ids times:" + (System.currentTimeMillis() - s) + "ms");
                List<String> tileids = new ArrayList<String>();
                if (result != null) {
                    // Log.d(LOG_TAG, "Tile Cursor Count:" + result.getCount());
                    for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                        // String x = result.getString(0);
                        // String y = result.getString(1);
                        String tileid = result.getString(0);
                        tileids.add(tileid);
                    }
                }
                int len = tileids.size();
                if (len > 0) {
                    // String queySql = "SELECT tile_id,fid,layer,geometry_data FROM geometries WHERE (";
                    StringBuilder sb = new StringBuilder();
                    sb.append("SELECT tile_id,fid,layer,geometry_data FROM geometries WHERE (");
                    for (int i = 0; i < len; i++) {
                        // queySql = queySql + "tile_id='" + tileids.get(i) + "'";
                        sb.append("tile_id='").append(tileids.get(i)).append("'");
                        if (i != len - 1) {
                            // queySql += " OR ";
                            sb.append(" OR ");
                        }
                    }
                    // queySql += ")";
                    sb.append(")");
                    Log.d(LOG_TAG, "queySql:" + sb.toString());
                    long s1 = System.currentTimeMillis();
                    queryresult = db.rawQuery(sb.toString(), null);
                    Log.d(LOG_TAG, "Query VectorTiles data times:" + (System.currentTimeMillis() - s1) + "ms");
                    if (queryresult != null) {
                        // Log.d(LOG_TAG, "Tile Cursor Count:" + queryresult.getCount());
                        for (queryresult.moveToFirst(); !queryresult.isAfterLast(); queryresult.moveToNext()) {
                            String tile_id = queryresult.getString(0);
                            String fid = queryresult.getString(1);
                            String layer = queryresult.getString(2);
                            String geometry_data = queryresult.getString(3);
                            PixelGeometry pg = getPixelGeometry(geometry_data);
                            VectorGeometryData vgd = new VectorGeometryData(Integer.parseInt(fid), tile_id, layer, pg);
                            if (resultBytesMap.containsKey(tile_id) && resultBytesMap.get(tile_id) != null) {
                                resultBytesMap.get(tile_id).add(vgd);
                            } else {
                                List<VectorGeometryData> dataList = new ArrayList<VectorGeometryData>();
                                dataList.add(vgd);
                                resultBytesMap.put(tile_id, dataList);
                            }
                        }
                    }
                }
                return resultBytesMap;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (result != null) {
                    result.close();
                }
                if (queryresult != null) {
                    queryresult.close();
                }
            }
        }
        return null;
    }

    /**
     * <p>
     * 通过矢量数据获取矢量对象
     * </p>
     * @param geometry_data 矢量数据字符串
     * @return
     * @since 7.0.0
     */
    private PixelGeometry getPixelGeometry(String geometry_data) {
        if (geometry_data == null) {
            return null;
        }
        try {
            if (geometry_data.contains("\"type\":\"TEXT\"")) {// 文本解析
                long t = System.currentTimeMillis();
                PixelGeometryText geometryText = JsonConverter.parseJson(geometry_data, PixelGeometryText.class);
                Log.d(LOG_TAG, "解析文本一次耗时:" + (System.currentTimeMillis() - t));
                return geometryText;
            } else {// 点线面的解析
                long t = System.currentTimeMillis();
                PixelGeometry geometry = JsonConverter.parseJson(geometry_data, PixelGeometry.class);
                Log.d(LOG_TAG, "解析线面一次耗时:" + (System.currentTimeMillis() - t));
                return geometry;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>
     * 生成分辨率double对应的不包含科学技术法的字符串
     * </p>
     * @param resolution
     * @return
     * @since 7.0.0
     */
    public static String getResolutionString(double resolution) {
        int figer = (int) Math.log10(resolution);
        if (resolution < 1.0) {
            figer--;
        }
        figer = -figer;
        int scale = 11 + figer - 1;
        return BigDecimal.valueOf(resolution).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }
}
