package com.supermap.imobilelite.maps;

import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

class DefaultTrackBallHandler {
    private static final String LOG_TAG = "com.supermap.android.maps.defaulttrackballhandler";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private MapView mapView;
    private boolean trackballZoomMode = false;

    public DefaultTrackBallHandler(MapView mapView) {
        this.mapView = mapView;
    }

    public boolean handleTrackballEvent(MotionEvent event) {
        int action = event.getAction() & 0xFF;
        if (action == 0) {
            this.trackballZoomMode = (!this.trackballZoomMode);
            Message message = Message.obtain();
            message.what = 6;
            message.getData().putBoolean("state", this.trackballZoomMode);
            this.mapView.getEventDispatcher().sendMessage(message);
        } else if (action == 2) {
            if (this.trackballZoomMode) {
                Log.d(LOG_TAG, resource.getMessage(MapCommon.DEFAULTTRACKBALLHANDLER_ZOOMMODE, event.getY()));
                double y = event.getY();
                if (y == 0.0D) {
                    return true;
                }
                if (0.0F < event.getY())
                    this.mapView.getController().zoomOut();
                else
                    this.mapView.getController().zoomIn();
            } else {
                float diffX = -(event.getX() * 100.0F);
                float diffY = -(event.getY() * 100.0F);
                this.mapView.getController().scrollBy((int) diffX, (int) diffY);
            }

        }

        return true;
    }
}