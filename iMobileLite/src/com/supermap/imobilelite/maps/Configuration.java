package com.supermap.imobilelite.maps;

class Configuration {
    private MapView mapView;
    // modify by xuzw
    private boolean satelliteLabeled = false;
    private boolean satellite = false;
    private String apiKey = null;
    private String platformApiKey = null;
    private int trafficMinimumZoomLevel = 5;

    private String trafficURL = "http://www.supermap.com";

    Configuration(MapView mapView) {
        this.mapView = mapView;
    }

    String getApiKey() {
        return this.apiKey;
    }

    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    boolean isSatelliteLabeled() {
        return this.satelliteLabeled;
    }

    // void setSatelliteLabeled(boolean satelliteLabeled) {
    // if (this.satelliteLabeled == satelliteLabeled)
    // return;
    // this.satelliteLabeled = satelliteLabeled;
    // this.mapView.setSatellite(this.satellite, this.satelliteLabeled);
    // }

    boolean isSatellite() {
        return this.satellite;
    }

    // void setSatellite(boolean satellite) {
    // this.satellite = satellite;
    // this.mapView.setSatellite(this.satellite, this.satelliteLabeled);
    // }

    int getTrafficMinimumZoomLevel() {
        return this.trafficMinimumZoomLevel;
    }

    void setTrafficMinimumZoomLevel(int trafficMinimumZoomLevel) {
        this.trafficMinimumZoomLevel = trafficMinimumZoomLevel;
    }

    String getTrafficURL() {
        return this.trafficURL;
    }

    void setTrafficURL(String trafficURL) {
        this.trafficURL = trafficURL;
    }

    String getPlatformApiKey() {
        if (this.platformApiKey == null)
            return getApiKey();
        return this.platformApiKey;
    }

    void setPlatformApiKey(String platformApiKey) {
        this.platformApiKey = platformApiKey;
    }
}