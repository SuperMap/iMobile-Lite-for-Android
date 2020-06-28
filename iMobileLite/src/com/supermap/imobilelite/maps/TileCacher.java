package com.supermap.imobilelite.maps;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Display;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

class TileCacher implements ITileCache {
    private static final String LOG_TAG = "com.supermap.android.maps.tilecacher";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    // 多少屏幕瓦片的内存缓存，目前设置8个。
    private static final int SCREENCOUNT = 8;
    private ITileCache memory;
    private ITileCache db;
    private ITileCache sqliteTileCache;
    private int total = 0;

    public TileCacher(Context ctx) {
        if (ctx != null) {
            Display display = ((Activity) ctx).getWindowManager().getDefaultDisplay();
            checkCacheSize(display.getHeight(), display.getWidth());
            this.db = new FSTileCache(ctx, true);
            sqliteTileCache = new SqliteTileCache();
        }
    }

    public ITileCache getCache(CacheType type) {
        if (CacheType.DB == type)
            return this.db;
        if (CacheType.MEMORY == type) {
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.TILECACHER_CACHETYPE_MEMORY));
            return this.memory;
        }
        if (CacheType.SQLITE == type) {
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.TILECACHER_CACHETYPE_SQLITE));
            return this.sqliteTileCache;
        }
        if (CacheType.ALL == type) {
            return this;
        }
        return null;
    }

    public void addTile(Tile tile) {
        this.memory.addTile(tile);
        this.db.addTile(tile);
    }

    public Tile getTile(Tile tile) {
        Tile t = this.memory.getTile(tile);
        if (t == null || (t.getBitmap() == null && t.getBytes() == null)) {
            t = this.db.getTile(tile);
        }
        if (t == null || (t.getBitmap() == null && t.getBytes() == null)) {
            t = this.sqliteTileCache.getTile(tile);
        }
        return t;
    }

    public void removeTile(Tile tile) {
        getCache(CacheType.MEMORY).removeTile(tile);
        getCache(CacheType.DB).removeTile(tile);
        if ((tile.getBitmap() != null) && (!tile.getBitmap().isRecycled()))
            tile.getBitmap().recycle();
    }

    public boolean contains(Tile tile) {
        if (getCache(CacheType.MEMORY).contains(tile)) {
            return true;
        }
        return getCache(CacheType.DB).contains(tile);
    }

    public void clear() {
        getCache(CacheType.MEMORY).clear();
        getCache(CacheType.DB).clear();
    }

    public int size() {
        return getCache(CacheType.DB).size();
    }

    public void destroy() {
        this.db.destroy();
        this.db = null;

        this.memory.destroy();
        this.memory = null;
    }

    public void checkCacheSize(int height, int width) {
        int t = SCREENCOUNT * (width / 256 + 2) * (height / 256 + 2);
        if (t > this.total)
            synchronized (this) {
                Log.d(LOG_TAG, resource.getMessage(MapCommon.TILECACHER_CHECKCACHESIZE, t));
                this.total = t;
                if (this.memory != null)
                    this.memory.destroy();
                this.memory = new MemoryTileCache(this.total);
            }
    }

    public static enum CacheType {
        DB, MEMORY, SQLITE, ALL;
    }

    /**
     * <p>
     * 设置内存缓存大小，单位是张，指明最多缓存多少瓦片
     * </p>
     * @param size
     * @since 7.0.0
     */
    public void setCacheSize(int size) {
        // Log.d(LOG_TAG, "total:" + total);
        // 至少保证一个屏幕数量的瓦片缓存
        if (size < (total / SCREENCOUNT)) {
            size = total / SCREENCOUNT * 2;
        }
        //if (this.memory instanceof MemoryTileCache) {
            ((MemoryTileCache) memory).setCacheSize(size);
        //}
    }
}