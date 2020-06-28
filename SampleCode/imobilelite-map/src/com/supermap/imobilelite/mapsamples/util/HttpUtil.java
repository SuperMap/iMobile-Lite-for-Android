package com.supermap.imobilelite.mapsamples.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

public final class HttpUtil {

	private static final String ISERVER_TAG = "iserver";

	// 默认坐标系为经纬坐标系
	private static final String DEFAULT_PRJCOORDSYS_TYPE = "PCS_EARTH_LONGITUDE_LATITUDE";

	// 普通平面坐标系
	private static final String PCS_NON_EARTH = "PCS_NON_EARTH";

	// 设置请求超时时间10秒钟
	private static final int REQUEST_TIMEOUT = 5 * 1000;

	// 设置等待数据超时时间10秒钟
	private static final int SO_TIMEOUT = 5 * 1000;

	/**
	 * 获取地图投影坐标系的JSON对象
	 * 
	 * @param baseUrl
	 * @return
	 */
	public static final JSONObject getPrjCoordSysJSON(String baseUrl) {
		HttpClient httpClient = getHttpClient();
		String prjCoordsysJsonUrl = baseUrl + "/prjCoordSys.json";
		HttpGet request = new HttpGet(prjCoordsysJsonUrl);
		JSONObject result = null;
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = new JSONObject(EntityUtils.toString(response
						.getEntity()));
			}
		} catch (Exception e) {
		    request.abort();
			Log.w(ISERVER_TAG, "Get prjcoordsys json error", e);
		}
		return result;
	}

	/**
	 * 获取全幅地图的JSON对象
	 * 
	 * @param baseUrl
	 *            服务地址，形如http://192.168.1.1:8090/iserver/services/map-China400/
	 *            rest/maps/China方法中自动补充/entireImage.json
	 * @return
	 */
	public static final JSONObject getEntireImageJSON(String baseUrl) {
		JSONObject prjcoordsys = getPrjCoordSysJSON(baseUrl);
		String type = JSONUtil.getPrjCoordSysFromJSON(prjcoordsys);

		JSONObject result = null;
		String entireImageJsonUrl = "";
		if (type != null && !type.equals(PCS_NON_EARTH)) {
			entireImageJsonUrl = baseUrl
					+ "/entireImage.json?prjCoordSys=%7B%22epsgCode%22%3A3857%7D";
		} else {
			entireImageJsonUrl = baseUrl + "/entireImage.json";
		}
		try {
		    return getJSON(entireImageJsonUrl);
		} catch (Exception e) {
			Log.w(ISERVER_TAG, "Get entire image json error", e);
		}

		return result;
	}
	
    public static final JSONObject getJSON(String baseUrl) {
        JSONObject result = null;
        HttpClient httpClient = getHttpClient();
        HttpGet request = new HttpGet(baseUrl);
        try {
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = new JSONObject(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            request.abort();
            e.printStackTrace();
        }
        return result;
    }

	/**
	 * 获取一个具备请求超时和等待数据超时的HttpClient
	 * 
	 * @return
	 */
	public static final HttpClient getHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}

	public static final JSONObject getMapJSON(String PCSType, String baseUrl) {
		HttpClient httpClient = getHttpClient();
		HttpGet request = null;
		if (PCSType != null && !PCSType.equals(PCS_NON_EARTH)) {
			request = new HttpGet(baseUrl + ".json?prjCoordSys=%7B%22epsgCode%22%3A3857%7D");
		} else {
			request = new HttpGet(baseUrl + ".json");
		}
		JSONObject result = null;
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = new JSONObject(EntityUtils.toString(response
						.getEntity()));
			}
		} catch (Exception e) {
		    request.abort();
			Log.w(ISERVER_TAG, "Get prjcoordsys json error", e);
		}
		return result;
	}

    public static final JSONObject getMapJSON(String baseUrl, boolean isMercatorProj) {
        HttpClient httpClient = getHttpClient();
        HttpGet request = null;
        if (!isMercatorProj) {
            request = new HttpGet(baseUrl + ".json?prjCoordSys=%7B%22epsgCode%22%3A3857%7D");
        } else {
            request = new HttpGet(baseUrl + ".json");
        }
        JSONObject result = null;
        try {
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = new JSONObject(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            request.abort();
            Log.w(ISERVER_TAG, "Get prjcoordsys json error", e);
        }
        return result;
    }
    
	public static final JSONArray getServicesJSONArray(String iserverUrl) {
		HttpClient httpClient = getHttpClient();
		HttpGet request = new HttpGet(iserverUrl + "/services.json");
		JSONArray result = null;
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = new JSONArray(EntityUtils.toString(response
						.getEntity()));
			}
		} catch (Exception e) {
		    request.abort();
			Log.w(ISERVER_TAG, "Get prjcoordsys json error", e);
		}
		return result;
	}
	
	public static final JSONArray getMapsJSONArray(String mapsUrl) {
		HttpClient httpClient = getHttpClient();
		HttpGet request = new HttpGet(mapsUrl);
		JSONArray result = null;
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = new JSONArray(EntityUtils.toString(response
						.getEntity()));
			}
		} catch (Exception e) {
		    request.abort();
			Log.w(ISERVER_TAG, "Get maps json error", e);
		}
		return result;
	}
	
	public static final JSONObject getLayersJSONObject(String currentMapUrl) {
		HttpClient httpClient = getHttpClient();
		String mapName = Uri.parse(currentMapUrl).getLastPathSegment(); 
		String layersUrl = "";
		try {
			layersUrl = currentMapUrl + "/layers/" + URLEncoder.encode(mapName, Constants.UTF8) + ".json";
		} catch (UnsupportedEncodingException e) {
			Log.w(ISERVER_TAG, "Get layers json error", e);
		}
		HttpGet request = new HttpGet(layersUrl);
		JSONObject result = null;
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = new JSONObject(EntityUtils.toString(response.getEntity()));
			}
		} catch (Exception e) {
		    request.abort();
			Log.w(ISERVER_TAG, "Get layers json error", e);
		}
		return result;
	}
}
