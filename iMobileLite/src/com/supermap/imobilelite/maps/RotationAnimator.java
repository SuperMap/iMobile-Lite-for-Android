package com.supermap.imobilelite.maps;

class RotationAnimator extends Animator {
    private static final int ANIMATION_DURATION = 900;
    private static final String tag = "com.supermap.android.maps.rotationanimator";
    private float toDeg = 0.0F;

    private float currentDeg = 0.0F;

    private float deltaDeg = 0.0F;

    private long startTime = 0L;

    public RotationAnimator(MapView mapView) {
        super(mapView);
    }

    public RotationAnimator(MapView mapView, Runnable runnable) {
        super(mapView, runnable);
    }

    public RotationAnimator(MapView mapView, float toDeg) {
        this(mapView);
        this.toDeg = toDeg;
    }

    public RotationAnimator(MapView mapView, Runnable runnable, float toDeg) {
        this(mapView, runnable);
        this.toDeg = toDeg;
    }

    public void preAnimation() {
        if (getDuration() == 0) {
            setDuration(900);
        }

        this.currentDeg = this.mapView.getMapRotation();
        this.toDeg %= 360.0F;
        this.startTime = System.currentTimeMillis();
        mapView.getEventDispatcher().sendEmptyMessage(31);
    }

    public boolean doAnimation() {
        long elapsed = System.currentTimeMillis() - this.startTime;

        if ((elapsed >= getDuration()) && (Math.abs(this.currentDeg - this.toDeg) >= 0.01D)) {
            elapsed = getDuration();
        }

        if (Math.abs(this.currentDeg - this.toDeg) < 0.000001) {
            return false;
        }
        if (elapsed <= getDuration()) {
            this.deltaDeg = (this.toDeg - this.currentDeg);
            if (Math.abs(this.deltaDeg) > 180.0F) {
                this.deltaDeg = (360.0F - Math.abs(this.deltaDeg));
                if (this.toDeg > this.currentDeg) {
                    this.deltaDeg *= -1.0F;
                    this.currentDeg += 360.0F;
                }
            }
            this.currentDeg = ((this.currentDeg + (float) elapsed * 1.0F / getDuration() * this.deltaDeg) % 360.0F);
            this.mapView.setMapRotation(this.currentDeg);
            mapView.getEventDispatcher().sendEmptyMessage(32);
            return true;
        }
        return false;
    }

    public void postAnimation() {
        mapView.getEventDispatcher().sendEmptyMessage(33);
    }
}