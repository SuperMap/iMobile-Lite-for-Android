package com.supermap.imobilelite.layersamples;

import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.WMSLayerView;
import com.supermap.imobilelite.layersamples.R;

import android.app.Activity;
import android.os.Bundle;

public class WMSLayerDemo extends Activity {
    protected static final String TAG = "com.supermap.imobilelite.layersamples.WMSLayerDemo";
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
        WMSLayerView wmsLayerView = getWMSLayer();
        mapView.addLayer(wmsLayerView);
        mapView.getController().setZoom(1);
        // 设置中心点
        mapView.getController().setCenter(new Point2D(116.391468, 39.904491));// 39.904491, 116.391468 0.0d, 0.0d
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
    }

    public String getWMSLayerUrl() {
        return serviceUrl + "/maps/wms111/世界地图_Day";
    }

    public WMSLayerView getWMSLayer() {
        // "http://192.168.120.9:8090/iserver/services/maps/wms111/世界地图_Day"
        return new WMSLayerView(this, getWMSLayerUrl(), "1.1.1", "0.12");
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.destroy();
        }
        super.onDestroy();
    }
}
