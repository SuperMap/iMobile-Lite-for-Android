package com.supermap.imobilelite.layersamples;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.layersamples.R;
import com.supermap.imobilelite.thirdpartlayers.TDTLayerView;

public class TDTLayerDemo extends Activity {
    protected MapView mapView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        TDTLayerView tdtLayerview = new TDTLayerView(this);// 矢量图层
        TDTLayerView tdtLayerview1 = new TDTLayerView(this, "vec", true, "4326");// 矢量标签图层
        mapView.addLayer(tdtLayerview);
        mapView.addLayer(tdtLayerview1);
        // 设置缩放级别
        mapView.getController().setZoom(3);
        // 设置中心点
        mapView.getController().setCenter(new Point2D(116.391468, 39.904491));// 39.904491, 116.391468 0.0d, 0.0d

        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        Log.d("TDTLayerDemo", "TDTLayerDemo onCreate over!");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.destroy();
        }
        super.onDestroy();
    }
}