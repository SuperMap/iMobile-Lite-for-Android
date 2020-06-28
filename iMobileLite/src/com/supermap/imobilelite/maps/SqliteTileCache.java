package com.supermap.imobilelite.maps;

import java.io.File;

import android.os.Environment;

import com.supermap.services.util.ResourceManager;

/**
 * Sqlite数据库缓存，默认只支持读取，不支持写入(readonly)，因为这里使用的缓存是iserver生成的mbtiles缓存；因而只需实现getTile接口即可。
 * @author huangqinghua
 * 
 */
class SqliteTileCache implements ITileCache {
    private static final String LOG_TAG = "com.supermap.android.maps.sqlitetilecache";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");

    public SqliteTileCache() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public Tile getTile(Tile tile) {
        // Log.d(LOG_TAG, resource.getMessage(MapCommon.SQLITETILECACHE_GETTILE));
        if (tile == null) {
            return null;
        }
        if (SqliteTileSourceFactory.getInstance().openSQLiteDatabase(tile.getLayerNameCache())) {
            byte[] bs = SqliteTileSourceFactory.getInstance().getTileBytes(tile);
            if (bs != null) {
                // Log.i(LOG_TAG, resource.getMessage(MapCommon.SQLITETILECACHE_GETTILE_SUCCESS, tile.toString()));
                tile.setBytes(bs);
            }
        }
        return tile;
    }

    @Override
    public void addTile(Tile paramTile) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeTile(Tile paramTile) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean contains(Tile paramTile) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    public boolean clearBySQLiteDBName(String dbName) {
        File tileFile = new File(Environment.getExternalStorageDirectory(), SqliteTileSourceFactory.SQLITE_CACHE_DIRECTORY + "/" + dbName + ".mbtiles");
        if (!tileFile.exists()) {
            return true;
        }
        if (tileFile.isFile()) {
            return tileFile.delete();
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
