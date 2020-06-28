package com.supermap.imobilelite.maps;

import android.app.Activity;
import android.content.Context;
import android.view.Display;

class VectorTileCacher extends TileCacher {
    // 多少屏幕瓦片的内存缓存，目前设置16个。
    private static final int SCREENCOUNT = 16;
    private ITileCache memoryVT;
    private ITileCache dbVT;
    private int total = 0;

    public VectorTileCacher(Context ctx) {
        super(null);
        if (ctx != null) {
            Display display = ((Activity) ctx).getWindowManager().getDefaultDisplay();
            checkMemCacheSize(display.getHeight(), display.getWidth());
            this.dbVT = new FSTileCache(ctx, true);
        }
    }

    private void checkMemCacheSize(int height, int width) {
        int t = SCREENCOUNT * (width / 256 + 2) * (height / 256 + 2);
        if (t > this.total)
            synchronized (this) {
                this.total = t;
                if (this.memoryVT != null)
                    this.memoryVT.destroy();
                this.memoryVT = new MemoryVectorTileCache(this.total);
            }
    }

    @Override
    public Tile getTile(Tile tile) {
        Tile t = this.memoryVT.getTile(tile);
        if (t == null || t.getBytes() == null) {
            t = this.dbVT.getTile(tile);
        }
        return t;
    }

    @Override
    public void addTile(Tile tile) {
        this.memoryVT.addTile(tile);
        this.dbVT.addTile(tile);
    }

    @Override
    public void removeTile(Tile tile) {
        getCache(CacheType.MEMORY).removeTile(tile);
        getCache(CacheType.DB).removeTile(tile);
    }

    @Override
    public boolean contains(Tile tile) {
        if (getCache(CacheType.MEMORY).contains(tile)) {
            return true;
        }
        return getCache(CacheType.DB).contains(tile);
    }

    @Override
    public void clear() {
        getCache(CacheType.MEMORY).clear();
        getCache(CacheType.DB).clear();
    }

    @Override
    public int size() {
        return getCache(CacheType.DB).size();
    }

    @Override
    public void destroy() {
        this.dbVT.destroy();
        this.dbVT = null;
        this.memoryVT.destroy();
        this.memoryVT = null;
    }

    public ITileCache getCache(CacheType type) {
        if (CacheType.DB == type)
            return this.dbVT;
        if (CacheType.MEMORY == type) {
            return this.memoryVT;
        }
        if (CacheType.ALL == type) {
            return this;
        }
        return null;
    }
    
    @Override
    public void setCacheSize(int size) {
        // Log.d(LOG_TAG, "total:" + total);
        // 至少保证一个屏幕数量的瓦片缓存
        if (size < (total / SCREENCOUNT)) {
            size = total / SCREENCOUNT * 2;
        }
        //if (this.memoryVT instanceof MemoryVectorTileCache) {
            ((MemoryVectorTileCache) memoryVT).setCacheSize(size);
        //}
    }
}
