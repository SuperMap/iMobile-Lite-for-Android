package com.supermap.imobilelite.layersamples;

import android.app.Activity;
import android.os.Bundle;

import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.WMTSLayerView;
import com.supermap.imobilelite.layersamples.R;

public class WMTSLayerDemo extends Activity {
    protected static final String TAG = "com.supermap.imobilelite.layersamples.WMTSLayerDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected MapView mapView;
    protected String serviceUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        Bundle bundle = this.getIntent().getExtras();
        // 设置访问地图的URL和数据编辑的URL
        serviceUrl = bundle.getString("service_url");
        if (serviceUrl == null || "".equals(serviceUrl)) {
            serviceUrl = DEFAULT_URL;
        }
        WMTSLayerView wmtsLayerView = getWMTSLayer();
        mapView.addLayer(wmtsLayerView);
        mapView.getController().setZoom(1);
        // 设置中心点
        mapView.getController().setCenter(new Point2D(116.391468, 39.904491));// 39.904491, 116.391468 0.0d, 0.0d
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
    }

    public String getWMTSLayerUrl() {
        return serviceUrl + "/map-world/wmts100";
    }

    public WMTSLayerView getWMTSLayer() {
        // "http://192.168.120.9:8090/iserver/services/map-world/wmts100"
        return new WMTSLayerView(this, getWMTSLayerUrl(), "世界地图_Day", "GlobalCRS84Scale_世界地图_Day");
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.destroy();
        }
        super.onDestroy();
    }
}
