package com.supermap.imobilelite.maps;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.os.Handler;
import android.os.Message;

class EventDispatcher {
//    private static final int MAP = 0;
//    private static final int ZOOM = 10;
//    private static final int MOVE = 20;
//    private static final int ROTATION = 30;
//    private static final int OVERLAY = 40;
//    private static final int TRAFFIC = 50;
//    private static final int NETWORK = 60;
//    private static final int ERROR = 70;
//    public static final int MAP_LOADED = 1;
//    public static final int MAP_PROVIDER_CHANGE = 2;
//    public static final int MAP_TOUCH = 3;
//    public static final int MAP_LONG_TOUCH = 4;
//    public static final int RESIZED = 5;
//    public static final int TRACKBALL_ZOOM_TOGGLED = 6;
//    public static final int ZOOM_START = 11;
//    public static final int ZOOM_END = 12;
//    public static final int MOVE_START = 21;
//    public static final int MOVING = 22;
//    public static final int MOVE_END = 23;
//    public static final int ROTATION_START = 31;
//    public static final int ROTATING = 32;
//    public static final int ROTATION_END = 33;
//    public static final int OVERLAY_ADDED = 41;
//    public static final int OVERLAY_REMOVED = 42;
//    public static final int TRAFFIC_ENABLED = 51;
//    public static final int TRAFFIC_DISABLED = 52;
//    public static final int NETWORK_CONNECTED = 61;
//    public static final int NETWORK_DISCONNECTED = 62;
//    public static final EventDispatcher instance = new EventDispatcher();
    private List<Handler> handlers;

    public EventDispatcher() {
        this.handlers = new CopyOnWriteArrayList();
    }

    public void registerHandler(Handler handler) {
        if (handler != null)
            handlers.add(handler);
    }

    public void removeHandler(Handler handler) {
        if (handler != null)
            handlers.remove(handler);
    }

    public void removeAllHandlers() {
        handlers.clear();
    }

    public void sendEmptyMessage(int what) {
        for (Handler handler : handlers)
            if (Util.checkIfSameThread(handler)) {
                Message m = Message.obtain();
                m.what = what;
                handler.dispatchMessage(m);
            } else {
                handler.sendEmptyMessage(what);
            }
    }

    public void sendEmptyMessageDelayed(int what, long millis) {
        for (Handler handler : handlers)
            handler.sendEmptyMessageDelayed(what, millis);
    }

    public void sendMessage(Message message) {
        for (Handler handler : handlers)
            if (Util.checkIfSameThread(handler))
                handler.dispatchMessage(Message.obtain(message));
            else
                handler.sendMessage(Message.obtain(message));
    }

    public void removeMessages(int what) {
        for (Handler handler : handlers)
            handler.removeMessages(what);
    }
}