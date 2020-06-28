package com.supermap.imobilelite.maps;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import android.util.Log;

class MemoryVectorTileCache implements ITileCache {
    private static final String LOG_TAG = "com.supermap.android.maps.memoryVectortilecache";

    private Cache tileCache;

    // private Map<String, List<VectorGeometryData>> synTileCache;
    // RemoveHandler removeHandler = null;

    public MemoryVectorTileCache(int cacheSize) {
        this.tileCache = new Cache(cacheSize);
        // this.synTileCache = Collections.synchronizedMap(this.tileCache);
        // this.removeHandler = new RemoveHandler(cacheSize, Looper.getMainLooper());
        // JsonConverter.addDecoderResolver(new VectorTileJsonDecoderResolver());
    }

    public Tile getTile(Tile tile) {
        if (tile == null) {
            return null;
        }
        String key = tile.buildCacheKey();
        if (key == null) {
            return null;
        }
        synchronized (this.tileCache) {
            List<VectorGeometryData> vt = (List<VectorGeometryData>) this.tileCache.get(key);
            if (vt == null || vt.isEmpty()) {
                return null;
            }
        }
        return tile;
    }

    public List<VectorGeometryData> getVectorTile(Tile tile) {
        if (tile == null) {
            return null;
        }
        String key = tile.buildCacheKey();
        if (key == null) {
            return null;
        }
        List<VectorGeometryData> vt = null;
        synchronized (this.tileCache) {
            vt = (List<VectorGeometryData>) this.tileCache.get(key);
        }
        return vt;
    }

    public void addTile(Tile tile) {
        Log.w(LOG_TAG, "tile is null,MemoryVectorTileCache addTile failed!");
        // if (!tile.isValid()) {
        // return;
        // }
        // String key = tile.buildCacheKey();
        // if (this.tileCache.containsKey(key)) {
        // return;
        // }
        // if (tile.getBytes() != null)
        // synchronized (this.tileCache) {
        // if (this.tileCache.containsKey(key))
        // return;
        // try {
        // long t = System.currentTimeMillis();
        // VectorTileData vt = JsonConverter.parseJson(new String(tile.getBytes(), "utf-8"), VectorTileData.class);
        // Log.w(LOG_TAG, "解析tileFeature一次耗时:" + (System.currentTimeMillis() - t));
        // this.tileCache.put(key, vt);
        // } catch (Exception e) {
        // Log.w(LOG_TAG, "MemoryVectorTileCache addTile failed!");
        // }
        // }
    }

    public boolean contains(Tile tile) {
        return this.tileCache.containsKey(tile.buildCacheKey());
    }

    public void removeTile(Tile tile) {
        synchronized (this.tileCache) {
            this.tileCache.remove(tile.buildCacheKey());
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
            Iterator<Entry<String, List<VectorGeometryData>>> it = this.tileCache.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, List<VectorGeometryData>> e = it.next();
                if (e.getKey().contains(name)) {
                    it.remove();
                }
            }
        }
    }

    public void clear() {
        synchronized (this.tileCache) {
            this.tileCache.clear();
        }
    }

    public int size() {
        return this.tileCache.size();
    }

    public void destroy() {
        clear();
        System.gc();
    }

    private class Cache extends LinkedHashMap<String, List<VectorGeometryData>> {
        private static final long serialVersionUID = 1L;
        protected int capacity = 16;
        private static final float load = 0.75F;

        public Cache(int capacity) {
            super(16, load, true);
            this.capacity = capacity;
        }

        protected boolean removeEldestEntry(Entry<String, List<VectorGeometryData>> eldest) {
            if (size() > this.capacity) {
                // MemoryVectorTileCache.this.removeHandler.removeVectorTile((List<VectorGeometryData>) eldest.getValue());
                return true;
            }
            return false;
        }
    }

    // private class RemoveHandler extends Handler {
    // static final int REMOVE_BITMAP = 0;
    // ConcurrentLinkedQueue<List<VectorGeometryData>> removalQueue = null;
    //
    // public RemoveHandler(int queueSize, Looper mainLooper) {
    // super();
    // this.removalQueue = new ConcurrentLinkedQueue<List<VectorGeometryData>>();
    // }
    //
    // public void handleMessage(Message msg) {
    // if (msg.what == 0) {
    // while (!this.removalQueue.isEmpty()) {
    // List<VectorGeometryData> vt = (List<VectorGeometryData>) this.removalQueue.poll();
    // if (vt != null) {
    // vt.clear();
    // vt = null;
    // }
    // }
    // }
    // super.handleMessage(msg);
    // }
    //
    // public void removeVectorTile(List<VectorGeometryData> vt) {
    // if (vt != null) {
    // this.removalQueue.add(vt);
    // sendEmptyMessage(0);
    // }
    // }
    // }

    public void addTile(Tile tile, List<VectorGeometryData> vgds) {
        String key = tile.buildCacheKey();
        if (this.tileCache.containsKey(key)) {
            return;
        }
        if (vgds != null && vgds.size() > 0) {
            // List<PixelGeometry> geoList = new ArrayList<PixelGeometry>();
            // for (int i = 0; i < vgds.size(); i++) {
            // VectorGeometryData vgd = vgds.get(i);
            // if (vgd != null && vgd.geometry_data != null) {
            // try {
            // if (vgd.geometry_data.contains("\"type\":\"TEXT\"")) {// 文本解析
            // long t = System.currentTimeMillis();
            // PixelGeometryText geometryText = JsonConverter.parseJson(vgd.geometry_data, PixelGeometryText.class);
            // Log.d(LOG_TAG, "解析文本一次耗时:" + (System.currentTimeMillis() - t));
            // geoList.add(geometryText);
            // } else {// 点线面的解析
            // long t = System.currentTimeMillis();
            // PixelGeometry geometry = JsonConverter.parseJson(vgd.geometry_data, PixelGeometry.class);
            // Log.d(LOG_TAG, "解析线面一次耗时:" + (System.currentTimeMillis() - t));
            // geoList.add(geometry);
            // }
            // } catch (JSONException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            // }
            // }
            synchronized (this.tileCache) {
                if (this.tileCache.containsKey(key))
                    return;
                try {
                    this.tileCache.put(key, vgds);
                } catch (Exception e) {
                    Log.w(LOG_TAG, "MemoryVectorTileCache addTile failed!");
                }
            }
        }
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

}
