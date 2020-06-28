package com.supermap.imobilelite.maps;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 内存缓存类
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 6.1.3
 * 
 */
class MemoryTileCache implements ITileCache {
    private static final String LOG_TAG = "com.supermap.android.maps.memorytilecache";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");

    private Cache tileCache;
    // private Map<String, Bitmap> synTileCache;
    // private boolean bumped;
    RemoveHandler removeHandler = null;

    public MemoryTileCache(int cacheSize) {
        this.tileCache = new Cache(cacheSize);
        // this.synTileCache = Collections.synchronizedMap(this.tileCache);
        this.removeHandler = new RemoveHandler(cacheSize, Looper.getMainLooper());
    }

    public Tile getTile(Tile tile) {
        // Log.d(LOG_TAG, resource.getMessage(MapCommon.ITileCache_GETTILE));
        if (tile == null) {
            return null;
        }
        String key = tile.buildCacheKey();
        if (key == null) {
            return null;
        }
        synchronized (this.tileCache) {
            Bitmap bm = (Bitmap) this.tileCache.get(key);
            if (bm == null) {
                return null;
            }
            tile.setBitMap(bm);
        }
        return tile;
    }

    public void addTile(Tile tile) {
        if (!tile.isValid()) {
            return;
        }
        String key = tile.buildCacheKey();
        if (this.tileCache.containsKey(key)) {
            return;
        }
        if (tile.getBitmap() != null)
            synchronized (this.tileCache) {
                // if ((tile.getTileType() == TileType.HYB) && (!this.bumped)) {
                // this.tileCache.capacity *= 2;
                // this.bumped = true;
                // }

                if (this.tileCache.containsKey(key))
                    return;
                this.tileCache.put(key, tile.getBitmap());
            }
    }

    public boolean contains(Tile tile) {
        return this.tileCache.containsKey(tile.buildCacheKey());
    }

    public void removeTile(Tile tile) {
        synchronized (this.tileCache) {
            Bitmap bm = (Bitmap) this.tileCache.remove(tile.buildCacheKey());
            this.removeHandler.removeBitmap(bm);
        }
    }

    /**
     * <p>
     * 根据图层名清除该图层的内存缓存
     * </p>
     * @param name 图层名
     */
    public void removeTilesByName(String name) {
        synchronized (this.tileCache) {
            Iterator<Entry<String, Bitmap>> it = this.tileCache.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, Bitmap> e = it.next();
                if (e.getKey().contains(name)) {
                    Bitmap bm = e.getValue();
                    it.remove();
                    if (bm != null)
                        bm.recycle();
                }
            }
        }
    }

    public void clear() {
        synchronized (this.tileCache) {
            for (Bitmap b : this.tileCache.values()) {
                if (b != null)
                    b.recycle();
            }
            this.tileCache.clear();
        }
    }

    public int size() {
        return this.tileCache.size() * 256 * 256 * 2;
    }
    
    /**
     * <p>
     * 设置内存缓存大小，单位是张，指明最多缓存多少瓦片
     * </p>
     * @param size
     * @since 7.0.0
     */
    public void setCacheSize(int size) {
        if (this.tileCache != null) {
            this.tileCache.capacity = size;
        }
    }

    public void destroy() {
        clear();
        System.gc();
    }

    private class Cache extends LinkedHashMap<String, Bitmap> {
        private static final long serialVersionUID = 1L;
        protected int capacity = 32;
        private static final float load = 0.75F;

        public Cache(int size) {
            super(16, load, true);
            this.capacity = size;
        }

        protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
            if (size() > this.capacity) {
                MemoryTileCache.this.removeHandler.removeBitmap((Bitmap) eldest.getValue());
                return true;
            }
            return false;
        }
    }

    private class RemoveHandler extends Handler {
        static final int REMOVE_BITMAP = 0;
        ConcurrentLinkedQueue<Bitmap> removalQueue = null;

        public RemoveHandler(int queueSize, Looper mainLooper) {
            super();
            this.removalQueue = new ConcurrentLinkedQueue<Bitmap>();
        }

        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                while (!this.removalQueue.isEmpty()) {
                    Bitmap bitmap = (Bitmap) this.removalQueue.poll();
                    if ((bitmap != null) && (!bitmap.isRecycled())) {
                        bitmap.recycle();
                    }
                }
            }
            super.handleMessage(msg);
        }

        public void removeBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                this.removalQueue.add(bitmap);
                sendEmptyMessage(0);
            }
        }
    }
}