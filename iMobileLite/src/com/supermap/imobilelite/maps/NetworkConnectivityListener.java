package com.supermap.imobilelite.maps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;


final class NetworkConnectivityListener {
    private static final String LOG_TAG = "com.supermap.android.maps.networkconnectivitylistener";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private Context context;
    private boolean listening;
    private ConnectivityBroadcastReceiver receiver;
    private static boolean lastKnownNetworkState = true;
    private MapView mapView;
    NetworkConnectivityListener(Context context, MapView mapView) {
        this.context = context;
        this.mapView = mapView;
        this.receiver = new ConnectivityBroadcastReceiver();
    }

    public static synchronized boolean getLastKnownNetworkState() {
        return lastKnownNetworkState;
    }
    
    public static synchronized void setLastKnownNetworkState(boolean state) {
         lastKnownNetworkState = state;
    }

    public void startListening() {
        if (!this.listening)
            try {
                IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                this.context.registerReceiver(this.receiver, intentFilter);
                this.listening = true;
            } catch (Exception e) {
                Log.d(LOG_TAG, resource.getMessage(MapCommon.NETWORKCONNECTIVITYLISTENER_REGISTER_FAILED));
            }
    }

    public void stopListening() {
        if (this.listening) {
            try {
                this.context.unregisterReceiver(this.receiver);
                // ((Activity)this.context).finish();
            } catch (Exception e) {
                Log.d(LOG_TAG, resource.getMessage(MapCommon.NETWORKCONNECTIVITYLISTENER_UNREGISTER_FAILED));
            }
            this.listening = false;
        }
    }

    private class ConnectivityBroadcastReceiver extends BroadcastReceiver {
        private ConnectivityBroadcastReceiver() {
        }

        public void onReceive(Context content, Intent intent) {

            NetworkInfo ni = (NetworkInfo) intent.getParcelableExtra("networkInfo");

            if (ni != null) {
                if (ni.isConnected()) {
                    // NetworkConnectivityListener.access$102(true);
//                    lastKnownNetworkState = true; // added by zhouxu
                    NetworkConnectivityListener.setLastKnownNetworkState(true);
                    mapView.getEventDispatcher().sendEmptyMessage(61);
                } else {
                    // NetworkConnectivityListener.access$102(false);
//                    lastKnownNetworkState = false; // added by zhouxu
                    NetworkConnectivityListener.setLastKnownNetworkState(false);
                    mapView.getEventDispatcher().sendEmptyMessage(62);
                }
            } else {
                try {
                    ConnectivityManager connectivityManager = (ConnectivityManager) NetworkConnectivityListener.this.context.getSystemService("connectivity");

                    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetInfo.isConnected()) {
                        // NetworkConnectivityListener.access$102(true);
//                        lastKnownNetworkState = true; // added by zhouxu
                        NetworkConnectivityListener.setLastKnownNetworkState(true);
                        mapView.getEventDispatcher().sendEmptyMessage(61);
                    } else {
                        // NetworkConnectivityListener.access$102(false);
//                        lastKnownNetworkState = false; // added by zhouxu
                        NetworkConnectivityListener.setLastKnownNetworkState(false);
                        mapView.getEventDispatcher().sendEmptyMessage(62);
                    }
                } catch (Exception e) {
                    Log.d(LOG_TAG, resource.getMessage(MapCommon.NETWORKCONNECTIVITYLISTENER_RECEIVENETWORKSTATE_FAILED));
                }
            }
        }
    }
}