package com.supermap.imobilelite.layersamples;

import com.supermap.imobilelite.maps.WMSLayerView;

public class WMSLayer111Demo extends WMSLayerDemo {
    @Override
    public String getWMSLayerUrl() {
        return serviceUrl + "/maps/wms111/世界地图_Day";
    }

    @Override
    public WMSLayerView getWMSLayer() {
        // "http://192.168.120.9:8090/iserver/services/maps/wms111/世界地图_Day"
        return new WMSLayerView(this, getWMSLayerUrl(), "1.1.1", "0");//0.12
    }

}
