package com.supermap.imobilelite.maps;

import android.os.Message;

abstract class Animator {
    protected static final String tag = "com.supermap.android.maps.animator";
    protected boolean animating = true;
    private boolean finishIt;
    private int duration;
    protected MapView mapView;
    protected Runnable runnable;
    protected boolean started = false;
    protected Message message;

    public Animator(MapView mapView) {
        this.mapView = mapView;
    }

    public Animator(MapView mapView, Runnable runnable) {
        this.mapView = mapView;
        this.runnable = runnable;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void startAnimation() {
        this.animating = true;
        this.message = null;
        this.finishIt = false;
        this.runnable = null;
        this.started = false;
    }

    public void stopAnimation() {
        this.animating = false;
    }

    public void stopAnimation(boolean jumpToFinish) {
        this.finishIt = true;
        stopAnimation();
    }

    public boolean isAnimating() {
        return this.animating;
    }

    public boolean animate() {
        if (!this.started) {
            this.started = true;
            preAnimation();
        }
        if ((this.animating) && (doAnimation()))
            return true;
        if ((this.animating) || (this.finishIt)) {
            postAnimation();
        }
        this.started = false;
        this.animating = false;

        if (this.runnable != null) {
            this.mapView.post(this.runnable);
        }
        if ((this.message != null) && (this.message.getTarget() != null)) {
            this.message.sendToTarget();
        }
        return false;
    }

    abstract void preAnimation();

    abstract boolean doAnimation();

    abstract void postAnimation();
}