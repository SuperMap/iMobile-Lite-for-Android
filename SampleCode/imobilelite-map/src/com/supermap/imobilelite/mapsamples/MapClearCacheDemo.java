package com.supermap.imobilelite.mapsamples;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.supermap.imobilelite.maps.BoundingBox;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.mapsamples.util.Constants;

public class MapClearCacheDemo extends AddLayersDemo {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String mapUrl = data.getStringExtra("map_url");
            LayerView layerView = new LayerView(this);

            // 设置访问地图的URL
            layerView.setURL(mapUrl);
            mapView.addLayer(layerView);
            // 设置访问地图的比例尺
            // int level = 0;
            // // 计算当前添加的图层的中心点
            // double centerX = layerView.getBounds().getLeft() + layerView.getBounds().getRight();
            // double centerY = layerView.getBounds().getBottom() + layerView.getBounds().getTop();
            // Point2D gp = new Point2D(centerX / 2, centerY / 2);
            // Log.d("iserver", "gp.getCenter:" + gp.getY() + "," + gp.getX());
            // // 计算当前添加的图层的最适合可见层级
            // BoundingBox bb = new BoundingBox(layerView.getBounds());
            // level = mapView.getProjection().calculateZoomLevel(bb);
            // Log.d(Constants.ISERVER_TAG, "calculate to ZoomLevel by layer bounds:" + level);

            // 新添加的 地图 开启并设定1分钟清除一次缓存，为了更新瓦片信息，关键代码行
            layerView.startClearCacheTimer(1);
            // mapView.getController().setZoom(level);
            // // 设置中心点
            // mapView.getController().setCenter(gp);// 39.904491, 116.391468 0.0d, 0.0d

            mapView.invalidate();
        }
    }

    @Override
    protected void onDestroy() {
        mapView.stopClearCacheTimer();// 停止和销毁 清除运行时服务器中缓存瓦片的定时器。
        super.onDestroy();
    }

}
