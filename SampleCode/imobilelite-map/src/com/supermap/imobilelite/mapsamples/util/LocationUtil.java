package com.supermap.imobilelite.mapsamples.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.supermap.imobilelite.maps.DefaultItemizedOverlay;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.ItemizedOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

/**
 * 通过gps或wifi定位到当前位置，默认wifi定位优先，并返回当前的地理坐标
 * 
 * @author huangqinghua
 * 
 */
public class LocationUtil {
    private static MapView mapView;
    private static Context context;
    private static LocationClient mLocationClient;
    private static LocationClientOption option;
    private static DefaultItemizedOverlay overlay;
    private static boolean isStarted = false;
    private static OverlayItem overlayItem;
    private static int oneTime = 1;

    public static void initLocationClient(MapView view, Drawable drawable) {
        if (view == null) {
            return;
        }
        mapView = view;
        context = view.getContext().getApplicationContext();
        if (context == null) {
            return;
        }
        overlay = new DefaultItemizedOverlay(drawable);
        overlay.setOnFocusChangeListener(new ItemizedOverlay.OnFocusChangeListener() {
            @Override
            public void onFocusChanged(ItemizedOverlay overlay, OverlayItem item) {
                // 地图中心漫游至当前OverlayItem
                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        if (!mapView.getOverlays().contains(overlay)) {
            mapView.getOverlays().add(overlay);
        }
        mLocationClient = new LocationClient(context);
        if (option == null) {
            option = initDefLocationClientOption();
        }
        mLocationClient.setLocOption(option);
        oneTime = 1;
        mLocationClient.registerLocationListener(new MyBDLocationListener());
    }

    public static LocationClient getLocationClient() {
        return mLocationClient;
    }

    public static LocationClientOption initDefLocationClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("gcj02"); // 设置坐标类型为gcj02
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
        option.setProdName("SuperMapLoction"); // 设置产品线名称
        option.setScanSpan(10000); // 定时定位，每隔10秒钟定位一次。
        return option;
    }

    public static void setOption(boolean isOpenGps, CoorType coorType, Priority priority, int scanSpanTime) {
        option = new LocationClientOption();
        option.setOpenGps(isOpenGps); // 是否打开gps
        if (CoorType.bd0911.equals(coorType)) {
            option.setCoorType(coorType.toString()); // 设置坐标类型为bd09ll
        } else if (CoorType.bd09.equals(coorType)) {
            option.setCoorType(coorType.toString());
        } else if (CoorType.gcj02.equals(coorType)) {
            option.setCoorType(coorType.toString());
        } else if (CoorType.gps.equals(coorType)) {
            option.setCoorType(coorType.toString());
        }
        if (Priority.NetWorkFirst.equals(priority)) {
            option.setPriority(2); // 设置网络优先
        } else if (Priority.GpsFirst.equals(priority)) {
            option.setPriority(1); // 设置Gps优先
        } else if (Priority.MIN_SCAN_SPAN.equals(priority)) {
            option.setPriority(1000); // 设置最短的扫描时间优先
        }
        option.setProdName("SuperMapLoction"); // 设置产品线名称
        option.setScanSpan(scanSpanTime * 1000); // 定时定位，每隔scanSpanTime秒钟定位一次。
    }

    public static void stopLoction() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            isStarted = false;
        }
    }

    public static void disposeLoction() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            isStarted = false;
            mLocationClient = null;
        }
    }

    public static void startLoction() {
        if (mLocationClient != null) {
            mLocationClient.start();
            isStarted = true;
            oneTime = 1;
        }
    }

    /**
     * 坐标系类型
     * 
     * @author huangqinghua
     * 
     */
    public static enum CoorType {
        gcj02, bd09, bd0911, gps
    }

    /**
     * 使用何种方式定位的优先级设置
     * 
     * @author huangqinghua
     * 
     */
    public static enum Priority {
        NetWorkFirst, GpsFirst, MIN_SCAN_SPAN
    }

    public static boolean isStarted() {
        return isStarted;
    }

    /**
     * 定位监听器
     */
    static class MyBDLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            // sb.append("\nmessage code : ");
            // sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
            }
            // sb.append("\nsdk version : ");
            // sb.append(loc.getVersion());
            // Toast.makeText(act, sb.toString(),
            // Toast.LENGTH_SHORT).show();
            if (oneTime < 2) {
                Point2D gp = null;
                String title = "";
                try {
                    gp = new Point2D(location.getLongitude(), location.getLatitude());
                    title = sb.toString();
                    overlayItem = new OverlayItem(gp, title, title);
                    overlay.clear();
                    overlay.addItem(overlayItem);
                    mapView.getController().animateTo(gp);
                    mapView.getController().setZoom(10);
                    // 重新onDraw一次
                    mapView.invalidate();
                    Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                oneTime++;
            }
        }

        public void onReceivePoi(BDLocation location) {
            // return ;
        }
    }
}
