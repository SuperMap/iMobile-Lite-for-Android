package com.supermap.imobilelite.maps;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.io.ChunkedInputStream;
import org.apache.http.impl.io.ContentLengthInputStream;
import org.apache.http.impl.io.HttpRequestWriter;
import org.apache.http.impl.io.HttpResponseParser;
import org.apache.http.impl.io.SocketInputBuffer;
import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.BasicLineParser;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 缓存提供类，负责从SD卡中提取缓存或者从网络下载图片
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
class ThreadBasedTileDownloader implements TileDownloader {
    private static final String LOG_TAG = "com.supermap.maps.downloader";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private Map<String, Tile> queue;
    private MapView mapView;
    private TileCacher tileCacher;
    private int maxRunningDownloads = 3;
    Set<String> runningDownloads = Collections.synchronizedSet(new HashSet<String>());// 记住当前正在下载的瓦片（存储瓦片的cacheKey）
    HttpConnectionPool httpConnectionPool = null;
    private ArrayList<TileDownloadThread> threads = null;
    private boolean networkAvailable = true;
    private NetworkListener listener = new NetworkListener();

    public ThreadBasedTileDownloader(MapView mapView, TileCacher tileCacher) {
        this.mapView = mapView;
        this.tileCacher = tileCacher;
        this.queue = new LinkedHashMap<String, Tile>(30);
        this.threads = new ArrayList<TileDownloadThread>();
        this.networkAvailable = NetworkConnectivityListener.getLastKnownNetworkState();
        mapView.getEventDispatcher().registerHandler(this.listener);
        for (int i = 0; i < this.maxRunningDownloads; i++) {
            TileDownloadThread t = new TileDownloadThread();// TileDownloadThread();//HttpPipelinerThread
            this.threads.add(t);
            t.start();
        }
    }

    /*private void setThreadPriority(int priority) {
        for (Iterator it = this.threads.iterator(); it.hasNext();) {
            TileDownloadThread t = (TileDownloadThread) it.next();
            if (t.isAlive())
                t.setPriority(priority);
        }
    }*/

    private HttpConnectionPool getHttpConnectionPoolInstance() {
        if (this.httpConnectionPool == null) {
            synchronized (this) {
                if (this.httpConnectionPool == null) {
                    this.httpConnectionPool = new HttpConnectionPool(this.maxRunningDownloads, this.maxRunningDownloads, 3000, 5000);
                }
            }
        }
        return this.httpConnectionPool;
    }

    public void destroy() {
        this.queue.clear();
        this.runningDownloads.clear();
        for (TileDownloadThread t : this.threads) {
            t.shutdown();
        }
        this.threads.clear();
        mapView.getEventDispatcher().removeHandler(this.listener);
        this.tileCacher = null;
        if (this.httpConnectionPool != null)
            this.httpConnectionPool.shutdown();
        this.mapView = null;
        System.gc();
    }

    public void beginQueue() {
        this.queue.clear();
    }

    public void queueTile(Tile tile) {
        String key = tile.buildCacheKey();
        this.queue.put(key, tile);
    }

    public void endQueue() {
        int intersectionCount = 0;
        int runningSize = this.runningDownloads.size();
        int queueSize = this.queue.size();
        if (queueSize == 0) {
            return;
        }
        // Log.d(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_ENDQUEUE_QUEUESIZE, queueSize));
        // Log.d(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_ENDQUEUE_RUNNINGSIZE, runningSize));

        for (Iterator<Entry<String, Tile>> it = this.queue.entrySet().iterator(); it.hasNext();) {
            Tile tile = (Tile) ((Entry<String, Tile>) it.next()).getValue();
            if (this.runningDownloads.contains(tile.buildCacheKey())) {
                intersectionCount++;
                // Log.d(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_BUILDCACHEKEY_INTHREAD, tile.buildCacheKey()));
            }
        }
        int numberOfTilesToProcess = queueSize - intersectionCount + (runningSize - intersectionCount);

        if (numberOfTilesToProcess > (int) (queueSize * 1.25D)) {
            for (Iterator<TileDownloadThread> it = this.threads.iterator(); it.hasNext();) {
                TileDownloadThread t = (TileDownloadThread) it.next();
                if (t.isAlive()) {
                    t.shutdown();
                }
                it.remove();
            }
            this.runningDownloads.clear();
        }

        int numberOfThreadsToCreate = this.maxRunningDownloads;
        for (Iterator<TileDownloadThread> it = this.threads.iterator(); it.hasNext();) {
            TileDownloadThread t = (TileDownloadThread) it.next();

            if (t.isAlive())
                numberOfThreadsToCreate--;
            else {
                it.remove();
            }
            t.clearQueue();
        }

        if (numberOfThreadsToCreate > 0) {
            for (int i = 0; i < numberOfThreadsToCreate; i++) {
                TileDownloadThread t = new TileDownloadThread();// HttpPipelinerThread()
                this.threads.add(t);
                t.start();
            }
        }

        int i = 0;
        for (Iterator<Entry<String, Tile>> it = this.queue.entrySet().iterator(); it.hasNext(); i++) {
            Tile tile = (Tile) ((Entry<String, Tile>) it.next()).getValue();
            ((TileDownloadThread) this.threads.get(i % this.maxRunningDownloads)).addToQueue(tile);
            it.remove();
        }
        for (Iterator<TileDownloadThread> it = this.threads.iterator(); it.hasNext();) {
            TileDownloadThread t = (TileDownloadThread) it.next();
            t.endQueue();
        }
    }

    private void addToRunning(Tile tile) {
        this.runningDownloads.add(tile.buildCacheKey());
    }

    private void removeFromRunning(Tile tile) {
        this.runningDownloads.remove(tile.buildCacheKey());
    }

    public void clearQueue() {
        beginQueue();
    }

    public void finishedDownload(Tile tile) {
        removeFromRunning(tile);
        if (!tile.isValid()) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_NOBYTESFORTILE, tile.getUrl()));
        } else if (this.mapView != null) {
            this.mapView.addTile(tile);
        }
    }

    private static class BlockQueue<E> implements Queue<E> {
        private volatile boolean blocked = false;
        private Queue<E> queue;

        public BlockQueue(Queue<E> queue) {
            this.queue = queue;
        }

        public void block() {
            this.blocked = true;
        }

        /*public boolean isBlocked() {
            return this.blocked;
        }*/

        public void throwExceptionIfBlocked() {
            if (this.blocked)
                throw new BlockedQueueException("Queue instance is blocked and cannot be used further");
        }

        public E element() {
            throwExceptionIfBlocked();
            return this.queue.element();
        }

        public boolean offer(E o) {
            throwExceptionIfBlocked();
            return this.queue.offer(o);
        }

        public E peek() {
            throwExceptionIfBlocked();
            return this.queue.peek();
        }

        public E poll() {
            throwExceptionIfBlocked();
            return this.queue.poll();
        }

        public E remove() {
            throwExceptionIfBlocked();
            return this.queue.remove();
        }

        public boolean add(E object) {
            throwExceptionIfBlocked();
            return this.queue.add(object);
        }

        public boolean addAll(Collection<? extends E> collection) {
            throwExceptionIfBlocked();
            return this.queue.addAll(collection);
        }

        public void clear() {
            this.queue.clear();
        }

        public boolean contains(Object object) {
            throwExceptionIfBlocked();
            return this.queue.contains(object);
        }

        public boolean containsAll(Collection<?> collection) {
            throwExceptionIfBlocked();
            return this.queue.containsAll(collection);
        }

        public boolean isEmpty() {
            throwExceptionIfBlocked();
            return this.queue.isEmpty();
        }

        public Iterator<E> iterator() {
            throwExceptionIfBlocked();
            return this.queue.iterator();
        }

        public boolean remove(Object object) {
            throwExceptionIfBlocked();
            return this.queue.remove(object);
        }

        public boolean removeAll(Collection<?> collection) {
            throwExceptionIfBlocked();
            return this.queue.removeAll(collection);
        }

        public boolean retainAll(Collection<?> collection) {
            throwExceptionIfBlocked();
            return this.queue.removeAll(collection);
        }

        public int size() {
            return this.queue.size();
        }

        public Object[] toArray() {
            throwExceptionIfBlocked();
            return this.queue.toArray();
        }

        public <T> T[] toArray(T[] array) {
            throwExceptionIfBlocked();
            return this.queue.toArray(array);
        }
    }

    private static class BlockedQueueException extends IllegalStateException {
        private static final long serialVersionUID = 1L;

        /*public BlockedQueueException() {
        }
        public BlockedQueueException(String message, Throwable cause) {
            super(cause);
        }
        public BlockedQueueException(Throwable cause) {
            super();
        }*/

        public BlockedQueueException(String detailMessage) {
            super();
        }
    }

    private class HttpPipelinerThread extends TileDownloadThread {
        /*final int CONNECTION_TIME_OUT = 5000;
        final int DO_PROCESS = 1;
        final int CLOSE_SOCKET = 2;
        final int FORCE_CLOSE_SOCKET = 3;
        final int STALE_CONNECTION_TIME_OUT = 30000;*/
        private Socket socket;
        private String host = "";
        private int port;
        private int keepAliveTimeout = Constants.NETWORK_TIME_OUT;
        private HttpParams params;
        private SocketInputBuffer socketIn;
        private SocketOutputBuffer socketOut;
        private HttpRequestWriter requestWriter = null;
        private HttpResponseParser responseParser = null;
        private BlockQueue<Tile> inProcessQueue = new BlockQueue<Tile>(new LinkedList<Tile>());
        private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private Handler handler;
        private Looper looper;

        public HttpPipelinerThread() {
            super();
            this.params = new BasicHttpParams();
            // this.params = new BasicHttpParams();
            HttpProtocolParams.setVersion(this.params, HttpVersion.HTTP_1_1);

            HttpProtocolParams.setUserAgent(this.params, "supermap_android/1.1");
        }

        private void connect(String host, int port) throws IOException {
            port = port == -1 ? 80 : port;
            if ((!host.equals(this.host)) && (port != this.port)) {
                close();
            }

            if (this.socket == null) {
                this.host = host;
                this.port = port;
                this.socket = new Socket(host, port);

                if (this.handler != null) {
                    this.handler.sendEmptyMessageDelayed(3, 30000L);
                }
                this.socket.setSoLinger(false, 0);
                this.socket.setSoTimeout(keepAliveTimeout);
                this.socketIn = new SocketInputBuffer(this.socket, 1500, this.params);
                this.socketOut = new SocketOutputBuffer(this.socket, 1500, this.params);
                this.requestWriter = new HttpRequestWriter(this.socketOut, new BasicLineFormatter(), this.params);
                this.responseParser = new HttpResponseParser(this.socketIn, new BasicLineParser(), new DefaultHttpResponseFactory(), this.params);
            }
        }

        private void sendTileRequest(Tile tile) throws IOException, HttpException, Exception {
            URI uri = new URI(tile.getUrl());
            // Log.d(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_GETURL, tile.getUrl()));
            try {
                connect(uri.getHost(), uri.getPort());
            } catch (IOException e) {
                Log.w(LOG_TAG,
                        resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_SOCKET_FAIL,
                                new String[] { tile.getUrl(), String.valueOf(ThreadBasedTileDownloader.this.networkAvailable) }));
                throw e;
            }
            // modify by xuzw
            // 处理中文地图问题，中文地图在传入时会进行编码，但是uri.getPath()会进行解码（参加api文档），
            // 直接采用tile.getUrl()即可
            // String uriPath = ;
            // String s = uri.getQuery();
            // if (s != null && !"".equals(s)) {
            // uriPath = uriPath + "?" + s;
            // }
            // BasicHttpRequest request = new BasicHttpRequest("GET",
            // uriPath);
            BasicHttpRequest request = new BasicHttpRequest("GET", tile.getUrl());
            String hostHeader = uri.getHost();
            if (uri.getPort() > 0)
                hostHeader = hostHeader + ':' + uri.getPort();
            request.addHeader("Host", hostHeader);
            request.addHeader("Connection", "keep-alive");
            this.requestWriter.write(request);
        }

        private byte[] readTileResponse(HttpResponseParser responseParser) throws IOException, HttpException {
            HttpMessage message = responseParser.parse();
            HttpResponse httpResponse = (HttpResponse) message;
            BasicHttpEntity entity = new BasicHttpEntity();

            Header header = message.getFirstHeader("Content-Encoding");
            if (header != null) {
                entity.setContentEncoding(header);
            }

            header = message.getFirstHeader("Content-Type");
            if (header != null) {
                entity.setContentType(header);
            }

            header = message.getFirstHeader("Content-Length");
            if (header != null) {
                long length = Long.parseLong(header.getValue());
                entity.setContentLength(length);
                entity.setContent(new ContentLengthInputStream(this.socketIn, length));
            }

            header = message.getFirstHeader("Transfer-Encoding");
            if ((header != null) && (header.getValue().indexOf("chunked") >= 0)) {
                entity.setChunked(true);
                entity.setContent(new ChunkedInputStream(this.socketIn));
            }

            HeaderElementIterator it = new BasicHeaderElementIterator(httpResponse.headerIterator("Keep-Alive"));

            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if ((value != null) && (param.equalsIgnoreCase("timeout"))) {
                    try {
                        this.keepAliveTimeout = (Integer.parseInt(value) * 1000);
                        if (this.keepAliveTimeout > 5000)
                            this.keepAliveTimeout = 5000;
                    } catch (NumberFormatException ignore) {
                    }
                }
            }
            httpResponse.setEntity(entity);
            this.buffer.reset();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity.writeTo(this.buffer);

                this.buffer.flush();

                this.buffer.close();
                return this.buffer.toByteArray();
            }
            if (httpResponse.getStatusLine().getStatusCode() == 302) {// 支持转发，获取转发的url重新设置给tile并加到请求瓦片的队列中去（去掉原来的在加入）
                if (httpResponse.getFirstHeader("Location") != null) {
                    Tile tile = this.inProcessQueue.poll();// 获取队头元素并去除该元素
                    // Log.d(LOG_TAG, "getStatusCode 302");
//                    String s = httpResponse.getFirstHeader("Location").getClass().getName();
                    // Log.d(LOG_TAG, " httpResponse.getFirstHeader('Location')"+s);
                    String url = httpResponse.getFirstHeader("Location").getValue();// 从消息头Location获取
                    tile.setUrl(url);
                    this.inProcessQueue.add(tile);
                }
            }
            entity.writeTo(this.buffer);

            return null;
        }

        private void flushSocket() throws IOException {
            if (this.socketOut != null)
                this.socketOut.flush();
        }

        public void run() {
            try {
                Looper.prepare();
                this.looper = Looper.myLooper();
                this.handler = new DownloadHandler();
                if (this.queue.size() > 0) {
                    this.handler.sendEmptyMessage(1);
                }
                Looper.loop();
            } catch (Exception e) {
                Log.e(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_LOOP_EXITED, e.getMessage()));
            }
            this.inProcessQueue.clear();
            close();
        }

        private void close() {
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_CLOSESOCKET_ERROR, e.getMessage()));
                }
            }
            this.socket = null;
            this.socketIn = null;
            this.socketOut = null;
            this.requestWriter = null;
            this.responseParser = null;
        }

        public void shutdown() {
            this.stop = true;
            this.queue.block();
            this.queue.clear();
            this.inProcessQueue.block();
            if (this.handler != null) {
                this.handler.removeMessages(1);
                this.handler.removeMessages(2);
                this.handler.removeMessages(3);
                this.handler = null;
            }

            if (this.looper != null)
                this.looper.quit();
        }

        public void endQueue() {
            if (this.handler != null)
                this.handler.sendEmptyMessage(1);
        }

        class DownloadHandler extends Handler {
            // final String TAG = "com.supermap.android.maps.downloader_" + Thread.currentThread().getId();//这种方式无法在实体机上分析日志
            final MapView mapView = ThreadBasedTileDownloader.this.mapView;

            DownloadHandler() {
            }

            // added by zhouxu
            // this$1 ThreadBasedTileDownloader.HttpPipelinerThread.this
            // this$1.this$0 ThreadBasedTileDownloader.this
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case 1:// 队列中有Tile
                    Tile tile = null;
                    try {
                        boolean messageRemoved = false;
                        HttpPipelinerThread.this.inProcessQueue.clear();
                        while ((tile = ((Tile) HttpPipelinerThread.this.queue.poll())) != null) {
                            // ThreadBasedTileDownloader.access$400(this$1.this$0,tile);
                            ThreadBasedTileDownloader.this.addToRunning(tile);
                            Tile t = HttpPipelinerThread.this.fetchTileFromCache(tile);
                            if ((null == t) || (!t.isValid())) {
                                if (ThreadBasedTileDownloader.this.networkAvailable) {
                                    HttpPipelinerThread.this.inProcessQueue.add(tile);
                                    // ThreadBasedTileDownloader.HttpPipelinerThread.access$800(this$1, tile);
                                    HttpPipelinerThread.this.sendTileRequest(tile);
                                    if (!messageRemoved) {
                                        removeMessages(2);
                                        messageRemoved = true;
                                    }
                                }
                            } else {
                                tile = t;
                                HttpPipelinerThread.this.constructTileBitMap(tile);
                                HttpPipelinerThread.this.addToMemoryCache(tile);
                                ThreadBasedTileDownloader.this.finishedDownload(tile);
                            }
                        }
                        // Thread.sleep(keepAliveTimeout);
                        // ThreadBasedTileDownloader.HttpPipelinerThread.access$900(this$1);
                        HttpPipelinerThread.this.flushSocket();
                        tile = null;
                        while ((tile = ((Tile) HttpPipelinerThread.this.inProcessQueue.peek())) != null) {
                            // tile.setBytes(ThreadBasedTileDownloader.HttpPipelinerThread.access$1000(this$1, this$1.responseParser));
                            tile.setBytes(HttpPipelinerThread.this
                                    .readTileResponse(HttpPipelinerThread.this.responseParser));
                            HttpPipelinerThread.this.constructTileBitMap(tile);// to bitmap
                            HttpPipelinerThread.this.addToMemoryCache(tile);
                            HttpPipelinerThread.this.addToDiskCache(tile);
                            ThreadBasedTileDownloader.this.finishedDownload(tile);
                            HttpPipelinerThread.this.inProcessQueue.poll();
                        }
                        // sendEmptyMessageDelayed(2,(long) ThreadBasedTileDownloader.HttpPipelinerThread.access$1100(this$1));
                        sendEmptyMessageDelayed(2, (long) HttpPipelinerThread.this.keepAliveTimeout);
                    } catch (BlockedQueueException bqe) {
                        Log.e(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_BlOCKEDQUEUEEXCEPTION_HTTPREQUEST, bqe.getMessage()));
                        HttpPipelinerThread.this.inProcessQueue.clear();
                        if (0 < HttpPipelinerThread.this.inProcessQueue.size()) {
                            Tile tileRemove = null;
                            try {
                                while ((tileRemove = ((Tile) HttpPipelinerThread.this.inProcessQueue.poll()))!= null) {
                                    // ThreadBasedTileDownloader.access$600(this$1.this$0, tile);
                                    ThreadBasedTileDownloader.this.removeFromRunning(tileRemove);
                                }
                            } catch (BlockedQueueException bqe1) { // bqe -> bqe1
                                Log.e(LOG_TAG, new StringBuilder().append("BlockedQueueException while removeFromRunning ：").append(bqe1.getMessage())
                                        .toString());
                            }
                            mapView.preLoadDelayed(5000L);
                        }
                    } catch (SocketTimeoutException e) {
                        Log.w(LOG_TAG,
                                new StringBuilder().append("SocketTimeoutException while processing http request,message:").append(e.getMessage())
                                        .append("; isNetworkAvailable: ").append(ThreadBasedTileDownloader.this.networkAvailable)
                                        .append(tile == null ? ";" : ";" + tile.getUrl()).toString());
                        HttpPipelinerThread.this.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG,
                                new StringBuilder().append("IO Error while processing http request ").append(e.getMessage()).append("; isNetworkAvailable: ")
                                        .append(ThreadBasedTileDownloader.this.networkAvailable).append(tile == null ? ";" : ";" + tile.getUrl()).toString());
                        HttpPipelinerThread.this.close();
                    } catch (HttpException e) {
                        Log.e(LOG_TAG,
                                new StringBuilder().append("Http Error while processing http request").append(e.getMessage())
                                        .append(tile == null ? ";" : ";" + tile.getUrl()).toString());
                        HttpPipelinerThread.this.close();
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Fatal Error while processing http request, Tile:" + (tile == null ? ";" : ";" + tile.getUrl()), e);
                        HttpPipelinerThread.this.close();
                    } finally {
                        if (0 < HttpPipelinerThread.this.inProcessQueue.size()) {
                            Tile tileRemove = null;
                            try {
                                while ((tileRemove = ((Tile) HttpPipelinerThread.this.inProcessQueue.poll())) != null) {
                                    // ThreadBasedTileDownloader.access$600(this$1.this$0, tile);
                                    ThreadBasedTileDownloader.this.removeFromRunning(tileRemove);
                                }
                            } catch (BlockedQueueException bqe) {
                                Log.e(LOG_TAG, "exception curse in finnal:" + bqe.getMessage());
                            }
                            mapView.preLoadDelayed(5000L);
                        }
                    }
                    break;
                case 2:
                case 3:
                    HttpPipelinerThread.this.handler.removeMessages(2);
                    HttpPipelinerThread.this.handler.removeMessages(3);
                    HttpPipelinerThread.this.close();
                    break;
                }
                super.handleMessage(msg);
            }
        }
    }

    private class TileDownloadThread extends Thread implements Comparator<Tile> {
        BlockQueue<Tile> queue = new BlockQueue<Tile>(new PriorityBlockingQueue<Tile>(50, this));
        volatile boolean stop = false;

        private TileDownloadThread() {
        }

        void addToQueue(Tile tile) {
            this.queue.add(tile);
        }

        public void clearQueue() {
            this.queue.clear();
        }

        void shutdown() {
            this.queue.clear();
            this.stop = true;
            interrupt();
        }

        public int compare(Tile tile1, Tile tile2) {
            ITileCache cache = ThreadBasedTileDownloader.this.tileCacher;

            if (cache != null && cache.contains(tile2)) {
                return 1;
            }
            return -1;
        }

        /**
         * <p>
         * 构建Tile的BitMap用于内存缓存存储
         * </p>
         * @param tile
         */
        void constructTileBitMap(Tile tile) {
            if ((ThreadBasedTileDownloader.this.tileCacher != null) && (tile.getBytes() != null) && (tile.getBitmap() == null)) {
                // long start = System.currentTimeMillis();
                byte[] b = tile.getBytes();
                if (b == null || b.length < 1) {
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_CONSTRUCTTILEBITMAP));
                    return;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                // options.inPreferredConfig = Config.ARGB_8888;
                options.inDither = false;
                options.inPurgeable = true;
                options.inInputShareable = true;
                options.inTempStorage = new byte[32 * 1024];
                Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length, options);
                // Log.d(LOG_TAG, "bm.getByteCount():"+(bm.getRowBytes()*bm.getHeight()));
                tile.setBitMap(bm);
                // Log.d(LOG_TAG, "constructTileBitMap一次长度为"+b.length+"所需的时间："+(System.currentTimeMillis()-start)+" ms");
            }
        }

        /**
         * <p>
         * 从缓存系统中获取Tile
         * </p>
         * @param tile
         * @return
         */
        Tile fetchTileFromCache(Tile tile) {
            if (ThreadBasedTileDownloader.this.tileCacher != null) {
                tile = ThreadBasedTileDownloader.this.tileCacher.getCache(TileCacher.CacheType.ALL).getTile(tile);
            }

            return tile;
        }

        /**
         * <p>
         * 从网络下载Tile
         * </p>
         * @param tile
         * @return
         */
        Tile fetchTileFromNetwork(Tile tile) {
            if (tile.getUrl() == null || "".equals(tile.getUrl())) {
                return tile;
            }
            byte[] b = ThreadBasedTileDownloader.this.getHttpConnectionPoolInstance().get(tile.getUrl());
            tile.setBytes(b);

            // tile.setBytes(b);
            return tile;
        }

        /**
         * <p>
         * 先从缓存系统中找到Tile，没有找到则从网络下载后得到Tile
         * </p>
         * @param tile
         * @return
         */
        Tile fetchTile(Tile tile) {
            Tile t = fetchTileFromCache(tile);
            if ((t == null) || (!t.isValid())) {
                t = fetchTileFromNetwork(tile);
            }
            return t;
        }

        void addToMemoryCache(Tile tile) {
            if (tile.getBitmap() != null) {
                if (ThreadBasedTileDownloader.this.tileCacher != null) {
                    ITileCache cache = ThreadBasedTileDownloader.this.tileCacher.getCache(TileCacher.CacheType.MEMORY);
                    if (cache != null) {
                        cache.addTile(tile);
                    }
                }
            }
        }

        void addToDiskCache(Tile tile) {
            if ((ThreadBasedTileDownloader.this.tileCacher != null) && (tile != null) && (tile.getBytes() != null)) {
                ITileCache cache = ThreadBasedTileDownloader.this.tileCacher.getCache(TileCacher.CacheType.DB);
                if ((cache != null) && (!cache.contains(tile))) {
                    cache.addTile(tile);
                }
                tile.setBytes(null);
            }
        }

        public void processTile(Tile tile) {
            if (tile != null) {
                ThreadBasedTileDownloader.this.addToRunning(tile);
                try {
                    if (ThreadBasedTileDownloader.this.tileCacher == null)
                        return;
                    fetchTile(tile);// 从内存或者网络中获取
                    constructTileBitMap(tile);// 如果bitMap为空则构建bitMap
                    addToMemoryCache(tile);// 加入内存缓存
                    addToDiskCache(tile);// 加入文件卡缓存
                    ThreadBasedTileDownloader.this.finishedDownload(tile);
                } catch (OutOfMemoryError e) {
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_OUTOFMEMORY, e.getMessage()));
                    System.gc();
                } catch (Exception e) {
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_OUTOFMEMORY_ERROR, e.getMessage()));
                } finally {
                    // if (!tile.isValid()) {
                    // ThreadBasedTileDownloader.this.mapView.preLoadDelayed(5000L);
                    // Log.d(LOG_TAG, "processTile mapView.preLoadDelayed(5000L)");
                    // }
                    ThreadBasedTileDownloader.this.removeFromRunning(tile);
                }
            }
        }

        public void run() {
            while (!this.stop) {
                Tile tile = null;
                try {
                    tile = (Tile) this.queue.poll();
                    processTile(tile);
                    Thread.sleep(400L);// 必须的，避免cpu空转，占用cpu
                } catch (InterruptedException e1) {
                    String msg = "InterruptedException occurs :";
                    if (e1.getMessage() != null) {
                        msg += e1.getMessage();
                    }
                    Log.d(LOG_TAG, resource.getMessage(MapCommon.THREADBASEDTILEDOWNLOADER_INTERRUPTEDEXCEPTION, msg));
                }
            }
        }

        public void endQueue() {
        }
    }

    private class NetworkListener extends Handler {
        private NetworkListener() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 61:
                // ThreadBasedTileDownloader.access$102(ThreadBasedTileDownloader.this,
                // true);
                networkAvailable = true; // added by zhouxu
                break;
            case 62:
                // ThreadBasedTileDownloader.access$102(ThreadBasedTileDownloader.this,
                // false);
                networkAvailable = false; // added by zhouxu
                break;
            }

            super.handleMessage(msg);
        }
    }
}