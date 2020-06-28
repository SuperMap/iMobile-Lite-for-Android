package com.supermap.imobilelite.maps;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

final class HttpConnectionPool {
    private static final String LOG_TAG = "com.supermap.maps.httpConnectionPool";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    ThreadSafeClientConnManager manager = null;
    DefaultHttpClient httpClient = null;
    public static final int CONNECTION_TIMEOUT = 3000;
    public static final int KEEP_ALIVE = 5000;
    public static final int READ_TIMEOUT = 5000;
    private IdleConnectionMonitorThread monitor = null;

    public HttpConnectionPool(int maxConnections, int maxConnectionPerHost, int connectionTimeout, int readTimeout) {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
        HttpConnectionParams.setSoTimeout(params, readTimeout);
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpConnectionParams.setSocketBufferSize(params, 8192);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "utf-8");
        HttpProtocolParams.setUseExpectContinue(params, false);

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        registry.register(new Scheme("https", sslSocketFactory, 443));

        ConnPerRoute connPerRoute = new ConnPerRouteBean(maxConnectionPerHost);
        ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
        ConnManagerParams.setMaxTotalConnections(params, maxConnections);
        ConnManagerParams.setTimeout(params, 3000L);
        this.manager = new ThreadSafeClientConnManager(params, registry);

        this.httpClient = new DefaultHttpClient(this.manager, params);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        this.monitor = new IdleConnectionMonitorThread(this.manager);
        this.monitor.start();

        httpclient.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));

                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if ((value != null) && (param.equalsIgnoreCase("timeout")))
                        try {
                            return Long.parseLong(value) * 1000L;
                        } catch (NumberFormatException ignore) {
                        }
                }
                return 5000L;
            }
        });
    }

    public byte[] get(String url) {
        HttpGet request = new HttpGet(url);
        request.addHeader("Connection", "keep-alive");
        try {
            HttpResponse response = this.httpClient.execute(request);
            // 增加判断，只有在返回200的时候获取图片内容，因为状态错误时，错误信息也会被写入成图片的缓存，导致某些瓦片一直出白图
            if (response.getStatusLine().getStatusCode() == 200) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent(), 8192);

                byte[] b = new byte[8192];
                int i = -1;
                while ((i = bis.read(b)) != -1) {
                    buffer.write(b, 0, i);
                }
                buffer.flush();

                bis.close();
                buffer.close();
                b = buffer.toByteArray();
                return b;
            }else{
                Log.w(LOG_TAG, "get tile bytes failed:" + url);
            }
        } catch (ClientProtocolException e) {
            Log.w(LOG_TAG, resource.getMessage(MapCommon.HTTPCONNECTIONPOOL_CLIENTPROTOCOLEXCEPTION, e.getMessage()));
            request.abort();
        } catch (IOException e) {
            Log.w(LOG_TAG, resource.getMessage(MapCommon.HTTPCONNECTIONPOOL_IOEXCEPTION, e.getMessage()));
            request.abort();
        }

        return null;
    }

    public void shutdown() {
        if (this.httpClient != null) {
            this.monitor.shutdown();
            this.manager.shutdown();
            this.monitor = null;
            this.manager = null;
            this.httpClient = null;
        }
    }

    private static class IdleConnectionMonitorThread extends Thread {
        private final ClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(ClientConnectionManager connMgr) {
            this.connMgr = connMgr;
        }

        public void run() {
            try {
                while (!this.shutdown)
                    synchronized (this) {
                        wait(3000L);
                        if (!this.shutdown) {
                            this.connMgr.closeExpiredConnections();

                            this.connMgr.closeIdleConnections(5000L, TimeUnit.MILLISECONDS);
                        }
                    }
            } catch (InterruptedException ex) {
            }
        }

        public void shutdown() {
            this.shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }
}