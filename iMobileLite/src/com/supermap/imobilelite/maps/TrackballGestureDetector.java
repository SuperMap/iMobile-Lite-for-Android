package com.supermap.imobilelite.maps;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;


class TrackballGestureDetector {
    private static final String LOG_TAG = "com.supermap.android.maps.trackballgesturedetector";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private static final int LONG_PRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout() * 2;
    private static final int EVENT_EXPIRY = (LONG_PRESS_TIMEOUT + DOUBLE_TAP_TIMEOUT + TAP_TIMEOUT) * 2;
    private float scrollX;
    private float scrollY;
    private float currentDownX;
    private float currentDownY;
    private float firstDownX;
    private float firstDownY;
    private Runnable longPressRunnable = null;
    private Runnable tapRunnable = null;
    EventDispatcher eventDispatcher;
    private boolean longPress = false;
    private boolean tap = false;
    private boolean doubleTap = false;
    private boolean scroll = false;

    private long lastKeyDownTime = 0L;
    private long lastKeyUpTime = 0L;
    private static final int LONG_PRESS = 0;
    private static final int TAP = 1;
    private static final int RESET = 2;

    public TrackballGestureDetector() {
        this.eventDispatcher = new EventDispatcher();
    }

    public void analyze(MotionEvent ev) {
        int action = ev.getAction();

        float currentX = ev.getX() * 100.0F;
        float currentY = ev.getY() * 100.0F;

        this.eventDispatcher.removeMessages(2);
        switch (action) {
        case 0:
            this.currentDownX = currentX;
            this.currentDownY = currentY;

            this.lastKeyDownTime = ev.getDownTime();
            if (Math.abs(ev.getDownTime() - this.lastKeyUpTime) < DOUBLE_TAP_TIMEOUT) {
                this.eventDispatcher.removeMessages(0);
                this.eventDispatcher.removeMessages(1);
                this.tap = false;
                this.doubleTap = true;
                this.scroll = false;
                this.longPress = false;
            } else {
                this.eventDispatcher.removeMessages(0);
                this.eventDispatcher.sendEmptyMessageAtTime(0, ev.getDownTime() + TAP_TIMEOUT + LONG_PRESS_TIMEOUT);

                this.firstDownX = currentX;
                this.firstDownY = currentY;
            }
            break;
        case 2:
            if ((this.doubleTap) || (this.tap) || (this.longPress)) {
                this.doubleTap = false;
                this.tap = false;
            } else {
                this.scrollX = currentX;
                this.scrollY = currentY;
                if ((Math.abs(this.scrollX) < 1.0F) && (Math.abs(this.scrollY) < 1.0F))
                    break;
                this.eventDispatcher.removeMessages(0);
                this.scroll = true;
                this.tap = false;
                this.longPress = false;
            }
            break;
        case 1:
            if ((this.lastKeyUpTime == 0L) || (Math.abs(this.lastKeyDownTime - this.lastKeyUpTime) > DOUBLE_TAP_TIMEOUT)) {
                if (Math.abs(this.lastKeyDownTime - ev.getEventTime()) < TAP_TIMEOUT) {
                    this.eventDispatcher.removeMessages(0);
                    this.eventDispatcher.sendEmptyMessageDelayed(1, DOUBLE_TAP_TIMEOUT);
                    this.tap = true;
                }
            }
            this.doubleTap = false;
            this.scroll = false;
            this.longPress = false;
            this.lastKeyUpTime = ev.getEventTime();
            break;
        case 3:
            this.eventDispatcher.removeMessages(0);
            this.tap = false;
        }

        this.eventDispatcher.sendEmptyMessageDelayed(2, EVENT_EXPIRY);
    }

    public void registerLongPressCallback(Runnable runnable) {
        this.longPressRunnable = runnable;
    }

    public void registerTapCallback(Runnable runnable) {
        this.tapRunnable = runnable;
    }

    public float getCurrentDownX() {
        return this.currentDownX;
    }

    public float getCurrentDownY() {
        return this.currentDownY;
    }

    public float getFirstDownX() {
        return this.firstDownX;
    }

    public float getFirstDownY() {
        return this.firstDownY;
    }

    public float scrollX() {
        return this.scrollX;
    }

    public float scrollY() {
        return this.scrollY;
    }

    public boolean isDoubleTap() {
        return this.doubleTap;
    }

    public boolean isScroll() {
        return this.scroll;
    }

    public boolean isTap() {
        return this.tap;
    }

    private void reset() {
        this.tap = false;
        this.doubleTap = false;
        this.scroll = false;
        this.longPress = false;
        this.scrollX = 0.0F;
        this.scrollY = 0.0F;
        this.currentDownX = 0.0F;
        this.currentDownY = 0.0F;
        this.firstDownY = 0.0F;
        this.firstDownY = 0.0F;
        this.lastKeyUpTime = 0L;
    }

    private class EventDispatcher extends Handler {
        private EventDispatcher() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 0:
                // TrackballGestureDetector.access$102(TrackballGestureDetector.this,
                // true);
                longPress = true; // added by zhouxu
                if (TrackballGestureDetector.this.longPressRunnable == null)
                    break;
                new Thread(TrackballGestureDetector.this.longPressRunnable).start();
                break;
            case 1:
                // TrackballGestureDetector.access$302(TrackballGestureDetector.this,
                // false);
                doubleTap = false; // added by zhouxu
                Log.d(LOG_TAG, resource.getMessage(MapCommon.TRACKBALLGESTUREDETECTOR_RECEVIED_EVENT));
                if (TrackballGestureDetector.this.tapRunnable == null)
                    break;
                new Thread(TrackballGestureDetector.this.tapRunnable).start();
                break;
            case 2:
                TrackballGestureDetector.this.reset();
            }
        }
    }
}