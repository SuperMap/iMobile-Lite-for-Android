package com.supermap.imobilelite.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * 通过wifi定位到当前位置，并返回当前的地理坐标，速度快但误差大一些
 * @author huangqinghua
 * 
 */
class WiFiLocation {
//    public static Context act;
//
//    public WiFiLocation() {
//    }
//
//    public WiFiLocation(Context activity) {
//        act = activity;
//    }

    public static JSONObject wifiLocation(Context act) throws JSONException {
        if (act == null) {
            return null;
        }
        JSONObject location = new JSONObject();
        try {
            location = doWifiPost(act);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public static JSONObject doWifiPost(Context act) throws Exception {
        return transResponse(execute(doWifi(act)));
    }

    // 返回数据和地理位置信息之间的转换
    private static JSONObject transResponse(HttpResponse response) {
        JSONObject lca = new JSONObject();
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(entity.getContent()));
                StringBuffer sb = new StringBuffer();
                String result = br.readLine();
                while (result != null) {
                    sb.append(result);
                    result = br.readLine();
                }
                JSONObject json = new JSONObject(sb.toString());
                lca = json.getJSONObject("location");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return lca;
    }

    // 得到json数据
    private static JSONObject doWifi(Context act) throws Exception {
        JSONObject holder = new JSONObject();
        holder.put("version", "1.1.0");
        holder.put("host", "maps.google.com");
        holder.put("address_language", "zh_CN");
        holder.put("request_address", true);
        WifiManager wifiManager = (WifiManager) act.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.getConnectionInfo().getBSSID() == null) {
            throw new RuntimeException("bssid is null");
        }

        JSONArray array = new JSONArray();
        JSONObject data = new JSONObject();
        data.put("mac_address", wifiManager.getConnectionInfo().getBSSID());
        data.put("signal_strength", 8);
        data.put("age", 0);
        array.put(data);
        holder.put("wifi_towers", array);

        return holder;
    }

    // 通过向google发送请求，得到返回字符串
    public static HttpResponse execute(JSONObject params) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();

        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 20 * 1000);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 20 * 1000);

        HttpPost post = new HttpPost("http://74.125.71.147/loc/json");

        StringEntity se = new StringEntity(params.toString());
        post.setEntity(se);
        HttpResponse response = httpClient.execute(post);
        return response;
    }

    public static boolean isWiFiActive(Context act) {
//        Context context = act.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
