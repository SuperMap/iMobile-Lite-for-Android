package com.supermap.imobilelite.maps;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

/**
 * 通过gps定位到当前位置，并返回当前的地理坐标，速度慢但误差小一些
 * @author huangqinghua
 * 
 */
class GpsLocation {
    private static boolean TIME_OUT = false;
    // 设置gps定位当前位置的时间，gps定位比较久
    private static long TIME_DURATION = 5000;
    private static LocationManager locationManager;
//    public static Context activity;
    private static final String TAG = "om.supermap.android.maps.gpsLocation";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
//
//    public GpsLocation() {
//    }
//
//    public GpsLocation(Context activityPar) {
//        activity = activityPar;
//    }

    public static Location gpsLocation(Context activity,int timeOut) {
        if (activity == null) {
            return null;
        }
        TIME_DURATION = timeOut;
        Location location = null;
//        String context = Context.LOCATION_SERVICE;
//        locationManager = (LocationManager) activity.getSystemService(context);
        getInstance(activity);
        boolean GPS_status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (GPS_status) {
            // 为获取地理位置信息时设置查询条件
            // String bestProvider = locationManager.getBestProvider(getCriteria(), true);
            // 获取位置信息
            // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
            // location = locationManager.getLastKnownLocation(bestProvider);
            String provider = LocationManager.GPS_PROVIDER;
            location = locationManager.getLastKnownLocation(provider);

            locationManager.requestLocationUpdates(provider, 1000, 10, new LocationListener() {

                @Override
                /** * 位置信息变化时触发 */
                public void onLocationChanged(Location location) {
                    Log.i(TAG, resource.getMessage(MapCommon.GPSLOCATION_GPSLOCATION_TIME, location.getTime()));
                    Log.i(TAG, resource.getMessage(MapCommon.GPSLOCATION_GPSLOCATION_LONGITUDE, location.getLongitude()));
                    Log.i(TAG, resource.getMessage(MapCommon.GPSLOCATION_GPSLOCATION_LATITUDE, location.getLatitude()));
                    Log.i(TAG, resource.getMessage(MapCommon.GPSLOCATION_GPSLOCATION_ALTITUDE, location.getAltitude()));
                }

                /** * GPS状态变化时触发 */
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    switch (status) {
                    // GPS状态为可见时
                    case LocationProvider.AVAILABLE:
                        Log.i(TAG, resource.getMessage(MapCommon.GPSLOCATION_GPSLOCATION_AVAILABLE));
                        break;
                    // GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        Log.i(TAG, resource.getMessage(MapCommon.GPSLOCATION_GPSLOCATION_OUT_OF_SERVICE));
                        break; // GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.i(TAG, resource.getMessage(MapCommon.GPSLOCATION_GPSLOCATION_TEMPORARILY_UNAVAILABLE));
                        break;
                    }
                }

                /** * GPS开启时触发 */
                public void onProviderEnabled(String provider) {
                    // Location location = locationManager.getLastKnownLocation(provider);
                }

                @Override
                public void onProviderDisabled(String arg0) {
                    // TODO Auto-generated method stub
                }
            });
            TimeExecution();
            location = updateWithNewLocation(location);
            if (location == null) {
                Log.d(TAG, resource.getMessage(MapCommon.GPSLOCATION_GETLOCATION_FAIL));
            }
        }
        return location;
    }
    
    private static synchronized LocationManager getInstance(Context activity) {
        if (locationManager != null) {
            return locationManager;
        }
        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) activity.getSystemService(context);
        return locationManager;
    }

    public static void TimeExecution() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TIME_OUT = true;
            }
        }, TIME_DURATION);
    }

    private static Location updateWithNewLocation(Location location) {
        Location locationInfo = null;
        while (!TIME_OUT) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                return location;
            }
        }
        if (location != null) {
            locationInfo = location;
        }
        return locationInfo;
    }

    public static boolean isGpsActive(Context activity) {
        getInstance(activity);
        boolean GPS_status = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (GPS_status) {
            return true;
        }
        return false;
    }

    /** * 返回查询条件 * @return */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }
}
