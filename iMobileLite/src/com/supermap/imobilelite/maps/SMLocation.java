package com.supermap.imobilelite.maps;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;

/**
 * <p>
 * 通过gps或wifi定位到当前位置，gps定位优先，并返回当前的地理坐标
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
class SMLocation {
    String errorString = "";
    public Context act;
    public SMLocation(Context activity) {
        act = activity;
    }

    private String errorFun(String str) {
        if ("".equals(errorString)) {
            errorString = errorString + str;
        } else {
            errorString = errorString + ";" + str;
        }
        return errorString;
    }

    public JSONObject locationInfo(int time) throws Exception {
        if (act == null) {
            return null;
        }
        JSONObject fileInfo = new JSONObject();
        Location locationInfo = null;
        double lon = 0;
        double lat = 0;
//        GpsLocation.activity = act;
        if (GpsLocation.isGpsActive(act)) {
            locationInfo = GpsLocation.gpsLocation(act, time);
            if (locationInfo != null) {
                lon = locationInfo.getLongitude();
                lat = locationInfo.getLatitude();
            } else {
                String GPSerrorString = "GPS定位失败，将使用wifi定位";
                errorString = errorFun(GPSerrorString);
            }
        } else {
            String GPSerrorString = "GPS未打开，将使用wifi定位";
            errorString = errorFun(GPSerrorString);
        }

        if (locationInfo == null) {
            JSONObject locationWifiInfo = new JSONObject();
//            WiFiLocation.act = act;
            if (WiFiLocation.isWiFiActive(act)) {
                locationWifiInfo = WiFiLocation.wifiLocation(act);
                if (locationWifiInfo == null) {
                    String WifierrorString = "wifi定位失败";
                    errorString = errorFun(WifierrorString);
                } else {
                    lat = locationWifiInfo.getDouble("latitude");
                    lon = locationWifiInfo.getDouble("longitude");
                }
            } else {
                String WifierrorString = "未连接网络，无法使用wifi定位";
                errorString = errorFun(WifierrorString);
            }
        }
        fileInfo.put("lon", lon);
        fileInfo.put("lat", lat);
        fileInfo.put("errorString", errorString);
        return fileInfo;
    }

    public static final String ACTION = "Location";
}
