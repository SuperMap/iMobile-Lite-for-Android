package com.supermap.imobilelite.mapsamples;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.supermap.imobilelite.maps.CoordinateReferenceSystem;
import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.util.Constants;

public class SimpleDemo extends Activity {

    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services/map-china400/rest/maps/China";

    protected MapView mapView;
    protected LayerView baseLayerView;
    protected Button locationButton;
    protected String mapUrl;
    protected Button helpBtn;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        baseLayerView = new LayerView(this);
        Bundle bundle = this.getIntent().getExtras();
        // 设置访问地图的URL
        mapUrl = bundle.getString("map_url");
        if (mapUrl != null && mapUrl.equals("")) {
            baseLayerView.setURL(DEFAULT_URL);
        } else {
            baseLayerView.setURL(mapUrl);
        }
        CoordinateReferenceSystem crs = new CoordinateReferenceSystem();
        crs.wkid = 4326;
        baseLayerView.setCRS(crs);
        // 即可以设置地图范围也可以不设置，不设置则自动从服务端获取
        // baseLayerView.setBounds(-2.0037508342789244E7, -2.003750834278914E7, 2.0037508342789244E7, 2.0037508342789095E7);
        mapView.addLayer(baseLayerView);
        // 清除缓存
        // baseLayerView.clearCache(false);
        // 设置缩放级别
        mapView.getController().setZoom(2);
        // 设置中心点
        // Point2D gp = new Point2D(0.0d, 0.0d);
        mapView.getController().setCenter(new Point2D(116.391468, 39.904491));// 39.904491, 116.391468 0.0d, 0.0d

        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);        
        helpBtn =(Button)findViewById(R.id.button_help);
        Log.d(Constants.ISERVER_TAG, "SimpleDemo onCreate over!!!");

        // 定位到当前位置，并显示当前的地理坐标
        /*   暂时忽略定位功能按钮的响应，待投影坐标转换功能完成后打开注释     
         * Drawable drawableBlue = getResources().getDrawable(R.drawable.blue_pin);
        LocationUtil.initLocationClient(mapView, drawableBlue);
        
      locationButton = (Button) this.findViewById(R.id.location);
        locationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (LocationUtil.isStarted()) {
                    LocationUtil.stopLoction();
                } else {
                    LocationUtil.startLoction();
                }
            }
        });*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        // LocationUtil.disposeLoction();
        if (mapView != null) {
            mapView.destroy();
        }
        super.onDestroy();
    }
}