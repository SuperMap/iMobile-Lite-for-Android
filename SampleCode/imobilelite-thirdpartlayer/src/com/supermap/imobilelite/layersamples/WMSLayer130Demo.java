package com.supermap.imobilelite.layersamples;

import com.supermap.imobilelite.maps.WMSLayerView;

public class WMSLayer130Demo extends WMSLayerDemo {
    @Override
    public String getWMSLayerUrl() {
        return serviceUrl + "/map-world/wms130/世界地图_Day";
    }

    @Override
    public WMSLayerView getWMSLayer() {
        // http://192.168.120.9:8090/iserver/services/map-world/wms130/世界地图_Day
        return new WMSLayerView(this, getWMSLayerUrl(), "1.3.0", "0");//0.11
    }

}
