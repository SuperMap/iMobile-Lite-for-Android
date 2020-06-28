package com.supermap.imobilelite.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

final class HttpUtil {
    private static final String LOG_TAG = "com.supermap.android.maps.httputil";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 3000;

    public static String runPost(String url, Map<String, String> params) {
        InputStream instream = executePostAsStream(url, params);

        if (instream != null) {
            String result = convertStreamToString(instream);
            try {
                instream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, resource.getMessage(MapCommon.HTTPUTIL_RUNPOST_EXCEPTION, e.getMessage()));
            }
            return result;
        }
        return null;
    }

    public static String runRequest(String url, Map<String, String> headers) {
        InputStream instream = executeAsStream(url, headers);

        if (instream != null) {
            String result = convertStreamToString(instream);
            try {
                instream.close();
            } catch (IOException e) {
            	Log.e(LOG_TAG, resource.getMessage(MapCommon.HTTPUTIL_RUNPOST_EXCEPTION, e.getMessage()));
            }
            return result;
        }
        return null;
    }

    public static String runRequest(String url) {
        return runRequest(url, null);
    }

    private static HttpParams getHttpConnectionParams(int connectionTimeout, int readTimeout) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, "utf-8");
        HttpProtocolParams.setUseExpectContinue(httpParams, false);

        HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout > 0 ? connectionTimeout : CONNECTION_TIMEOUT);

        HttpConnectionParams.setSoTimeout(httpParams, readTimeout > 0 ? readTimeout : READ_TIMEOUT);

        return httpParams;
    }

    public static InputStream executePostAsStream(String url, Map<String, String> params) {
        HttpClient client = new DefaultHttpClient(getHttpConnectionParams(CONNECTION_TIMEOUT, READ_TIMEOUT));
        HttpPost postRequest = new HttpPost(url);

        InputStream instream = null;
        try {
            List<BasicNameValuePair> postParams = new ArrayList<BasicNameValuePair>(params.size());
            for (Entry<String,String> entry : params.entrySet()) {
                if (entry != null) {
                    postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            postRequest.setEntity(new UrlEncodedFormEntity(postParams));

            HttpResponse response = client.execute(postRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            Log.d(LOG_TAG, resource.getMessage(MapCommon.HTTPUTIL_EXECUTEPOSTASSTREAM_STATUSCODE, statusCode));
            if ((statusCode != HttpStatus.SC_OK) && (statusCode != HttpStatus.SC_NO_CONTENT)) {
                Log.w(LOG_TAG, resource.getMessage(MapCommon.HTTPUTIL_EXECUTEPOSTASSTREAM_ERROR, new String[]{String.valueOf(statusCode), url}));
                return null;
            }

            if (statusCode == HttpStatus.SC_NO_CONTENT) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                instream = entity.getContent();
                InputStream localInputStream1 = instream;
                return localInputStream1;
            }
        } catch (Exception e) {
            postRequest.abort();
            Log.w(LOG_TAG, "Error while retrieving response from " + url + ", " + e.getMessage());
        } 
//        finally {
//        }

        return instream;
    }

    public static InputStream executeAsStream(String url) {
        return executeAsStream(url, null);
    }

    public static InputStream executeAsStream(String url, int timeout) {
        return executeAsStream(url, null, timeout);
    }

    public static InputStream executeAsStream(String url, Map<String, String> headers) {
        return executeAsStream(url, headers, CONNECTION_TIMEOUT);
    }

    public static InputStream executeAsStream(String url, Map<String, String> headers, int timeout) {
        HttpClient client = new DefaultHttpClient(getHttpConnectionParams(timeout, timeout));

        HttpGet getRequest = new HttpGet(url);

        InputStream instream = null;
        try {
            if (headers != null) {
                Iterator<Entry<String, String>> it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Entry<String, String> en = it.next();
                    if (en != null) {
                        String name = en.getKey();
                        String value = en.getValue();
                        getRequest.addHeader(name, value);
                    }
                }
            }
            HttpResponse response = client.execute(getRequest);
            HttpEntity entity = response.getEntity();
            if (entity != null)
                instream = entity.getContent();
        } catch (Exception e) {
            getRequest.abort();
            Log.w(LOG_TAG, resource.getMessage(MapCommon.HTTPUTIL_EXECUTEASSTREAM_ERROR, new String[]{url, e.getMessage()}));
            instream = null;
        }
        return instream;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null)
                sb.append(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}