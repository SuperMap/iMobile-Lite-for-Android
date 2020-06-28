package com.supermap.imobilelite.maps;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

class FSTileCache implements ITileCache {
    private static final String LOG_TAG = "com.supermap.android.maps.fstilecache";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private static final long MILISECONDS_IN_A_DAY = 86400000L;
    private static final int INTERNAL_CACHE_SIZE = 10485760;
    private static final int EXTERNAL_CACHE_SIZE = 104857600;
    static final String INTERNAL_CACHE_DIRECTORY = "tiles";
    static final String EXTERNAL_CACHE_DIRECTORY = "supermap/tiles";
    private File file;
    private Context context;
    private BroadcastReceiver mExternalStorageReceiver;
    boolean mExternalStorageAvailable = false;
    boolean mExternalStorageWriteable = false;

    private static int VERSION = 1;
    // 监控缓存的大小和存储有限期的线程和处理器，sdcard的缓存大小不超过100M,data/data下的缓存大小不超过10M，存储有限期30天。
    HandlerThread handlerThread;
    CacheHandler cacheHandler;

    public FSTileCache(Context ctx, boolean initHandlers) {
        this.context = ctx;
        startWatchingExternalStorage();

        if (initHandlers) {
            this.handlerThread = new HandlerThread("cache", 1);
            this.handlerThread.start();
            this.cacheHandler = new CacheHandler(this.handlerThread.getLooper());
//            this.cacheHandler.removeMessages(1);
//            this.cacheHandler.sendEmptyMessageDelayed(1, 500L);
        }
        Log.d(LOG_TAG, resource.getMessage(MapCommon.FSTILECACHE_CACHELOCATION, this.file.getAbsolutePath()));
    }

    private File getTileDirectory(Tile tile) {
        File tileDir = new File(this.file, tile.getProvider() + "_" + VERSION);
        if (tile.getLayerNameCache() != null && !"".equals(tile.getLayerNameCache())) {
            tileDir = new File(tileDir, tile.getLayerNameCache());// sd卡缓存目录结构考虑图层名
        }
        if (!tileDir.exists()) {
            tileDir.mkdirs();
        }
        return tileDir;
    }

    public void addTile(Tile tile) {
        if ((!tile.isValid()) || (tile.getBytes() == null))
            return;
        if (tile.getBytes() == null)
            return;
        // this.cacheHandler.removeMessages(1);
        File tileFile = new File(getTileDirectory(tile), tile.buildCacheKey());
        if (tileFile.exists() && tileFile.length() > 0) {
            return;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tileFile);
            fos.write(tile.getBytes());
            fos.flush();
//            fos.close();
//            fos = null;
            // this.cacheHandler.sendEmptyMessageDelayed(1, 500L);
        } catch (FileNotFoundException e) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.FSTILECACHE_ADDTILE_NOTFOUND), e);
        } catch (IOException e) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.FSTILECACHE_ADDTILE_IOEXCEPTION), e);
        } finally {
            if (fos != null)
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                }
        }
    }

    public Tile getTile(Tile tile) {
        File tileFile = new File(getTileDirectory(tile), tile.buildCacheKey());

        if (!tileFile.exists()) {
            // tile.setId(-1L);
            return tile;
        }

        FileInputStream fis = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int i = -1;
        try {
            fis = new FileInputStream(tileFile);
            byte[] b = new byte[8192];
            i = -1;
            while ((i = fis.read(b)) != -1) {
                buffer.write(b, 0, i);
            }
            tile.setBytes(buffer.toByteArray());
            fis.close();
            fis = null;
        } catch (FileNotFoundException e) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.FSTILECACHE_GETTILE_NOTFOUND), e);
            return null;
        } catch (IOException e) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.FSTILECACHE_GETTILE_IOEXCEPTION), e);
            return null;
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                }
        }
        return tile;
    }

    public void removeTile(Tile tile) {
        new File(getTileDirectory(tile), tile.buildCacheKey()).delete();
    }

    public void clear() {
        this.cacheHandler.sendEmptyMessage(0);
        // try {
        // FileUtils.deleteDirectory(new File(Environment.getExternalStorageDirectory(), EXTERNAL_CACHE_DIRECTORY));
        // } catch (IOException e) {
        // Log.w(LOG_TAG, "Clear SDCard cache exception:" + e.getMessage());
        // }
        // try {
        // FileUtils.deleteDirectory(context.getDir("tiles", 2));
        // } catch (IOException e) {
        // Log.w(LOG_TAG, "Clear Data cache exception:" + e.getMessage());
        // }
    }

    public void clearByDirName(String directoryName) {// directoryName包含Provider的值/图层名，如rest-map/world
        if (directoryName.contains(File.separator)) {// 把rest-map/world变成rest-map_1/world
            directoryName = directoryName.substring(0, directoryName.indexOf(File.separator)) + "_" + VERSION
                    + directoryName.substring(directoryName.indexOf(File.separator));
        }
        File tileDirectory = new File(this.file, directoryName);
        deleteDir(tileDirectory);
    }

    private boolean deleteDir(File dir) {
        if (dir == null || !dir.exists()) {
            return true;
        }
        boolean flag = true;
        File[] tempFiles = dir.listFiles();
        if (tempFiles != null) {
            for (int i = 0; i < tempFiles.length; i++) {
                if (tempFiles[i].isDirectory()) {
                    flag &= deleteDir(tempFiles[i]);
                } else if (tempFiles[i].isFile()) {
                    flag &= tempFiles[i].delete();
                } else {
                    flag = false;
                }
            }
        }
        flag &= dir.delete();
        return flag;
    }

    public boolean contains(Tile tile) {
        return new File(getTileDirectory(tile), tile.buildCacheKey()).exists();
    }

    public void destroy() {
        stopWatchingExternalStorage();
        if (this.cacheHandler != null) {
            this.cacheHandler.removeMessages(0);
            this.cacheHandler.removeMessages(1);
            this.cacheHandler.removeMessages(3);
            this.cacheHandler.removeMessages(2);
        }
        if (this.handlerThread != null) {
            Looper looper = this.handlerThread.getLooper();
            if (looper != null) {
                looper.quit();
            }
        }
        this.file = null;
    }

    public int size() {
        int size = 0;
        size = size(this.context.getDir("tiles", 2));
        if ((this.mExternalStorageAvailable) && (this.mExternalStorageWriteable)) {
            size += size(new File(Environment.getExternalStorageDirectory(), EXTERNAL_CACHE_DIRECTORY));
        }
        return size;
    }

    private int size(File directory) {
        if (directory == null || !directory.isDirectory())
            return 0;
        int size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file != null) {
                    if (file.isDirectory()) {
                        size += size(file);
                    } else {
                        size = (int) (size + file.length());
                    }
                }
            }
        }
        return size;
    }

    public IFileCallback getCallback(FileCallbackType type) {
        IFileCallback callback = null;
        switch (type.ordinal()) {
        case 0:
            callback = new IFileCallback() {
                public void process(File file) {
                    file.delete();
                }
            };
            break;
        case 3:
            callback = new IFileCallback() {
                int days = 30;

                public void process(File file) {
                    long oldest = 86400000L * this.days;

                    long diff = System.currentTimeMillis() - file.lastModified();
                    if (diff > oldest)
                        file.delete();
                }
            };
            break;
        case 1:
            callback = createPurgeCallback(104857600);
            break;
        case 2:
            callback = createPurgeCallback(10485760);
            break;
        }

        return callback;
    }

    private IFileCallback createPurgeCallback(final int size) {
        IFileCallback callback = new IFileCallback() {
            int max_size = size;
            int total_size;

            public void process(File file) {
                if (this.total_size + file.length() > this.max_size)
                    file.delete();
                else
                    this.total_size = (int) (this.total_size + file.length());
            }
        };
        return callback;
    }

    public void iterateDirectory(File root, final int order, IFileCallback callback) {
        if (root == null || !root.isDirectory())
            return;
        File[] files = root.listFiles();
        if(files == null){
            return;
        }
        if (order != 0) {
            Arrays.sort(files, new Comparator<File>() {
                public int compare(File file1, File file2) {
                    return Long.valueOf(file1.lastModified()).compareTo(Long.valueOf(file2.lastModified())) * order;
                }
            });
        }
        for (File file : files) {
            if (file.isDirectory()) {
                iterateDirectory(file, order, callback);
            }
            callback.process(file);
        }
    }

    void updateExternalStorageState(Context context) {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            this.mExternalStorageAvailable = (this.mExternalStorageWriteable = true);
        } else if ("mounted_ro".equals(state)) {
            this.mExternalStorageAvailable = true;
            this.mExternalStorageWriteable = false;
        } else {
            this.mExternalStorageAvailable = (this.mExternalStorageWriteable = false);
        }
        handleExternalStorageState(context, this.mExternalStorageAvailable, this.mExternalStorageWriteable);
    }

    private void handleExternalStorageState(Context context, boolean mExternalStorageAvailable2, boolean mExternalStorageWriteable2) {
        if ((mExternalStorageAvailable2) && (mExternalStorageWriteable2)) {
            this.file = new File(Environment.getExternalStorageDirectory(), EXTERNAL_CACHE_DIRECTORY);
            if (!this.file.exists())
                this.file.mkdirs();
        } else {
            this.file = context.getDir("tiles", 2);
        }
    }

    void startWatchingExternalStorage() {
        this.mExternalStorageReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                FSTileCache.this.updateExternalStorageState(context);
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MEDIA_MOUNTED");
        filter.addAction("android.intent.action.MEDIA_REMOVED");
        this.context.registerReceiver(this.mExternalStorageReceiver, filter);
        updateExternalStorageState(this.context);
    }

    void stopWatchingExternalStorage() {
        try {
            this.context.unregisterReceiver(this.mExternalStorageReceiver);
        } catch (Exception e) {
            Log.w(LOG_TAG, "stopWatchingExternalStorage异常");
        }
    }

    private class CacheHandler extends Handler {
        static final int EMPTY_CACHE = 0;
        static final int ENSURE_CACHE = 1;
        static final int ENSURE_CACHE_BASED_ON_SIZE = 2;
        static final int ENSURE_CACHE_BASED_ON_EXPIRY = 3;

        public CacheHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 0:
                IFileCallback callback = FSTileCache.this.getCallback(FileCallbackType.EMPTY_CACHE);
                FSTileCache.this.iterateDirectory(FSTileCache.this.context.getDir("tiles", 2), 0, callback);

                if ((FSTileCache.this.mExternalStorageAvailable) && (FSTileCache.this.mExternalStorageWriteable)) {
                    FSTileCache.this.iterateDirectory(new File(Environment.getExternalStorageDirectory(), EXTERNAL_CACHE_DIRECTORY), 0, callback);
                }

                removeMessages(0);
                break;
            case 1:
                sendEmptyMessage(3);
                sendEmptyMessage(2);
                break;
            case 3:
                IFileCallback callback1 = FSTileCache.this.getCallback(FileCallbackType.PURGE_CACHE_BASED_ON_EXPIRY);
                FSTileCache.this.iterateDirectory(FSTileCache.this.context.getDir("tiles", 2), 0, callback1);

                if ((FSTileCache.this.mExternalStorageAvailable) && (FSTileCache.this.mExternalStorageWriteable)) {
                    FSTileCache.this.iterateDirectory(new File(Environment.getExternalStorageDirectory(), EXTERNAL_CACHE_DIRECTORY), 0, callback1);
                }

                removeMessages(3);
                break;
            case 2:
                try {
                    IFileCallback callbackExternal = FSTileCache.this.getCallback(FileCallbackType.PURGE_EXTERNAL_CACHE_BASED_ON_SIZE);

                    if ((FSTileCache.this.mExternalStorageAvailable) && (FSTileCache.this.mExternalStorageWriteable)) {
                        FSTileCache.this.iterateDirectory(new File(Environment.getExternalStorageDirectory(), EXTERNAL_CACHE_DIRECTORY), -1, callbackExternal);
                    }

                    IFileCallback callbackInternal = FSTileCache.this.getCallback(FileCallbackType.PURGE_INTERNAL_CACHE_BASED_ON_SIZE);
                    FSTileCache.this.iterateDirectory(FSTileCache.this.context.getDir("tiles", 2), -1, callbackInternal);

                    removeMessages(2);
                } catch (Exception e) {
                    Log.e(LOG_TAG, resource.getMessage(MapCommon.FSTILECACHE_HANDLEMESSAGE_EXCEPTION) + e.getMessage());
                    sendEmptyMessageDelayed(2, 5000L);
                }
                break;
            }

            super.handleMessage(msg);
        }
    }

    static abstract interface IFileCallback {
        public abstract void process(File paramFile);
    }

    static enum FileCallbackType {
        EMPTY_CACHE, PURGE_EXTERNAL_CACHE_BASED_ON_SIZE, PURGE_INTERNAL_CACHE_BASED_ON_SIZE, PURGE_CACHE_BASED_ON_EXPIRY;
    }
}