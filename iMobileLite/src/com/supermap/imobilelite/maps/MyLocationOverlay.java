package com.supermap.imobilelite.maps;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;


class MyLocationOverlay extends Overlay implements SensorListener, LocationListener {
    private static final String LOG_TAG = "com.supermap.android.maps.mylocationoverlay";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private static final int GPS_TIME_OUT = 10000;
    private MapView mapView;
    int minFingerSize;
    private SensorManager sensorManager = null;
    private LocationManager locationManager = null;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float bearing = 0.0F;
    private boolean enableCompass = false;

    private boolean enableMyLocation = false;
    private Runnable runnable;
    private Location lastFix = null;
    private Point2D lastFixGeoPoint = null;

    private Location lastNetworkFix = null;
    private long lastGpsFixTime = 0L;
    private boolean networkAvailable;
    private Drawable marker = null;

    private Rect rect = new Rect();

    private Paint paint = null;

    private Point reusable = new Point();
    private CompassView cv;
    private int HALO_COLOR = -11460275;

    private int markerAlignment = 3;
    int animating;
    long startTime;
    int animationFrequency = 2000;
    double animationLength = 500.0D;
    double animationRadius = 50.0D;
    private Paint beaconAnimationOutlinePaint;
    private Paint beaconAnimationFillPaint;
    private boolean userFollowingFlag = false;
    private boolean followingFlag = false;

    private MapView.MapViewEventListener listener = null;

    private final SensorEventListener mListener = new SensorEventListener() {
        float[] gravity;
        float[] geomagnetic;
        float[] r = new float[16];

        private void reset(float[] a) {
            if (a != null)
                for (int i = 0; i < a.length; i++)
                    a[i] = 0.0F;
        }

        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == 1) {
                if ((this.gravity == null) || (event.values.length != this.gravity.length)) {
                    this.gravity = new float[event.values.length];
                }
                System.arraycopy(event.values, 0, this.gravity, 0, this.gravity.length);
            }
            if (event.sensor.getType() == 2) {
                this.geomagnetic = ((float[]) event.values.clone());
                if ((this.geomagnetic == null) || (event.values.length != this.geomagnetic.length)) {
                    this.geomagnetic = new float[event.values.length];
                }
                System.arraycopy(event.values, 0, this.geomagnetic, 0, this.geomagnetic.length);
            }
            if ((this.gravity != null) && (this.geomagnetic != null)) {
                reset(this.r);
                if (SensorManager.getRotationMatrix(this.r, null, this.gravity, this.geomagnetic)) {
                    float[] orientation = new float[3];
                    SensorManager.getOrientation(this.r, orientation);
                    orientation[0] = (float) Math.toDegrees(orientation[0]);
                    orientation[1] = (float) Math.toDegrees(orientation[1]);
                    orientation[2] = (float) Math.toDegrees(orientation[2]);

                    MyLocationOverlay.this.onSensorChanged(3, orientation);
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            MyLocationOverlay.this.onAccuracyChanged(3, accuracy);
        }
    };

    private MapView.MapViewEventListener defaultListener = new MapView.MapViewEventListener() {
        public void zoomStart(MapView mapView) {
        }

        public void zoomEnd(MapView mapView) {
        }

        public void touch(MapView mapView) {
        }

        public void mapLoaded(MapView mapView) {
        }

        public void moveStart(MapView mapView) {
//            if (MyLocationOverlay.this.lastFix != null && MyLocationOverlay.this.lastFix.getAccuracy() <= 32.0F) {
//            }
            // MyLocationOverlay.access$102(MyLocationOverlay.this, false);
            enableMyLocation = false; // added by zhouxu
        }

        public void moveEnd(MapView mapView) {
            if ((MyLocationOverlay.this.userFollowingFlag) && (!MyLocationOverlay.this.followingFlag)) {
//                Float dist = getChangeDistance(mapView);
//                if (dist.floatValue() <= 15.0F) {
//                }
                // MyLocationOverlay.access$102(MyLocationOverlay.this, true);
                enableMyLocation = true; // added by zhouxu
            }
        }

//        private Float getChangeDistance(MapView mapView) {
//            if (MyLocationOverlay.this.lastFixGeoPoint == null)
//                return Float.valueOf(0.0F);
//            Projection p = mapView.getProjection();
//            Float dist = Float.valueOf(Util.distance(p.toPixels(mapView.getCenter(), new Point()),
//                    p.toPixels(MyLocationOverlay.this.lastFixGeoPoint, new Point())));
//
//            return dist;
//        }

        public void move(MapView mapView) {
        }

        public void longTouch(MapView mapView) {
        }
    };

    public MyLocationOverlay(Context context, MapView mapView) {
        this.mapView = mapView;
        this.minFingerSize = (int) (context.getResources().getDisplayMetrics().density * 10.0F + 0.5F);

        this.paint = new Paint(1);
        this.paint.setFilterBitmap(true);
        this.paint.setDither(true);

        this.sensorManager = ((SensorManager) context.getSystemService("sensor"));
        this.accelerometer = this.sensorManager.getDefaultSensor(1);
        this.magnetometer = this.sensorManager.getDefaultSensor(2);
        this.locationManager = ((LocationManager) context.getSystemService("location"));

        this.marker = Util.getDrawable(context, "location_marker_purple");

        this.beaconAnimationFillPaint = new Paint();
        int haloColor = this.HALO_COLOR;
        this.beaconAnimationFillPaint.setColor(haloColor);
        this.beaconAnimationFillPaint.setAlpha(30);
        this.beaconAnimationFillPaint.setAntiAlias(true);
        this.beaconAnimationFillPaint.setStyle(Paint.Style.FILL);

        this.beaconAnimationOutlinePaint = new Paint(this.beaconAnimationFillPaint);
        this.beaconAnimationOutlinePaint.setAlpha(60);
        this.beaconAnimationOutlinePaint.setStrokeWidth(2.0F);
        this.beaconAnimationOutlinePaint.setStyle(Paint.Style.STROKE);

        this.cv = new CompassView(mapView.getContext());
        this.cv.setVisibility(4);

        MapView.LayoutParams lp = new MapView.LayoutParams(-2, -2, 40, 40, 3);

        mapView.addView(this.cv, lp);

        this.animating = 1;
        this.startTime = new Date().getTime();
    }

    protected MapView.MapViewEventListener setupMapViewEventListener() {
        return this.defaultListener;
    }

    public boolean isFollowing() {
        return this.userFollowingFlag;
    }

    public void setFollowing(boolean following) {
        this.userFollowingFlag = following;
        this.followingFlag = true;
        if (following) {
            this.listener = setupMapViewEventListener();
            if (this.listener != null)
                this.mapView.addMapViewEventListener(this.listener);
            this.mapView.getController().animateTo(this.lastFixGeoPoint);
        } else {
            this.mapView.removeMapViewEventListener(this.listener);
        }
    }

    public void destroy() {
        if (this.enableMyLocation) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.MYLOCATIONOVERLAY_DESTROY));
            disableMyLocation();
            this.enableMyLocation = false;
        }
        if (this.enableCompass) {
            disableCompass();
            this.enableCompass = false;
        }

        this.locationManager = null;
        this.sensorManager = null;
        this.accelerometer = null;
        this.magnetometer = null;
        this.lastFix = null;
        this.lastFixGeoPoint = null;
        if (this.cv != null) {
            this.cv.destroyDrawingCache();
            this.cv = null;
        }
        this.mapView = null;
    }

    public boolean isCompassEnabled() {
        return this.enableCompass;
    }

    public boolean enableCompass() {
        if (!this.enableCompass) {
            this.enableCompass = true;
            this.sensorManager.registerListener(this.mListener, this.accelerometer, 3);

            this.sensorManager.registerListener(this.mListener, this.magnetometer, 3);
        }

        return this.enableCompass;
    }

    public void disableCompass() {
        if (this.enableCompass) {
            this.cv.setVisibility(4);
            this.sensorManager.unregisterListener(this);
            this.sensorManager.unregisterListener(this.mListener);
        }
    }

    private boolean registerProviders() {
        boolean enabled = false;
        List providers = this.locationManager.getAllProviders();
        if (providers.contains("gps")) {
            if (this.locationManager.isProviderEnabled("gps")) {
                enabled = true;
            }
            this.locationManager.requestLocationUpdates("gps", 0L, 0.0F, this);
        }
        if (providers.contains("network")) {
            if (this.locationManager.isProviderEnabled("network")) {
                enabled = true;
            }
            this.locationManager.requestLocationUpdates("network", 0L, 0.0F, this);
        }
        return enabled;
    }

    public boolean enableMyLocation() {
        this.enableMyLocation = true;
        return registerProviders();
    }

    public void disableMyLocation() {
        this.locationManager.removeUpdates(this);
        this.enableMyLocation = false;
    }

    protected boolean dispatchTap() {
        return false;
    }

    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
        if (this.enableCompass) {
            drawCompass(canvas, this.bearing);
        }

        if ((this.enableMyLocation) && (this.lastFix != null)) {
            boolean returnedValue = animateBeacon(canvas, mapView, shadow, when);
            drawMyLocation(canvas, mapView, this.lastFix, this.lastFixGeoPoint, when);
            if ((!returnedValue) && (this.enableCompass == true)) {
                return true;
            }
            return returnedValue;
        }

        return true;
    }

    protected boolean animateBeacon(Canvas canvas, MapView mapView, boolean shadow, long when) {
        if ((this.beaconAnimationFillPaint == null) && (this.beaconAnimationOutlinePaint == null))
            return false;

        if (this.animating == 1) {
            this.startTime = when;
            this.animating = 2;
        }

        long ellapsed = (when - this.startTime) % this.animationFrequency;

        if ((this.animating == 2) && (ellapsed < this.animationLength)) {
            Double radius = Double.valueOf(ellapsed / this.animationLength * this.animationRadius);

            if (radius.doubleValue() < 5.0D)
                return false;

            Point point = mapView.getProjection().toPixels(this.lastFixGeoPoint, this.reusable);
            Double alpha = Double.valueOf(255.0D - ellapsed / this.animationLength * 255.0D);
            if (this.beaconAnimationFillPaint != null) {
                this.beaconAnimationFillPaint.setAlpha(alpha.intValue());
                canvas.drawCircle(point.x, point.y, (float) radius.longValue(), this.beaconAnimationFillPaint);
            }
            if (this.beaconAnimationOutlinePaint != null) {
                this.beaconAnimationOutlinePaint.setAlpha(alpha.intValue());
                canvas.drawCircle(point.x, point.y, (float) radius.longValue(), this.beaconAnimationOutlinePaint);
            }
            int radiusAsInt = Double.valueOf(radius.doubleValue() + 3.0D).intValue();
            Rect dirtyRect = new Rect(point.x - radiusAsInt, point.y - radiusAsInt, point.x + radiusAsInt, point.y + radiusAsInt);

            mapView.invalidate(dirtyRect);

            return true;
        }

        draw(canvas, mapView, shadow);
        return false;
    }

    protected void drawCompass(Canvas canvas, float bearing) {
        if (this.cv.getVisibility() != 0) {
            this.cv.setVisibility(0);
            this.cv.invalidate();
        }
    }

    protected void drawMyLocation(Canvas canvas, MapView mapView, Location lastFix, Point2D myLocation, long when) {
        if (this.marker != null) {
            Point point = mapView.getProjection().toPixels(this.lastFixGeoPoint, this.reusable);
            setAlignment(this.marker, this.markerAlignment);
            this.rect.set(this.marker.getBounds());
            this.rect.offset(point.x, point.y);
            this.rect.inset(-this.minFingerSize >> 1, -this.minFingerSize >> 1);
            drawAt(canvas, this.marker, point.x, point.y, false);

            float pix = mapView.getProjection().metersToEquatorPixels(lastFix.getAccuracy());
            if (pix > 10.0F) {
                canvas.drawCircle(point.x, point.y, pix, this.beaconAnimationFillPaint);
                canvas.drawCircle(point.x, point.y, pix, this.beaconAnimationOutlinePaint);
            }
        }
    }

    public Location getLastFix() {
        return this.lastFix;
    }

    public Point2D getMyLocation() {
        return this.lastFixGeoPoint;
    }

    public float getOrientation() {
        return this.bearing;
    }

    public boolean isMyLocationEnabled() {
        return this.enableMyLocation;
    }

    public void onSensorChanged(int sensor, float[] values) {
        float[] vs = values.clone();
        if (vs[0] == this.bearing)
            return;
        this.bearing = vs[0];
        this.cv.setRotation(-this.bearing);
    }

    public void onAccuracyChanged(int sensor, int accuracy) {
    }

    public void onLocationChanged(Location location) {
        long now = System.currentTimeMillis();
        boolean useLocation = false;
        String provider = location.getProvider();

        if ("gps".equals(provider)) {
            this.lastGpsFixTime = System.currentTimeMillis();
            useLocation = true;
        } else if ("network".equals(provider)) {
            useLocation = now - this.lastGpsFixTime > 10000L;
            if (this.lastNetworkFix == null)
                this.lastNetworkFix = new Location(location);
            else {
                this.lastNetworkFix.set(location);
            }

            this.lastGpsFixTime = 0L;
        }
        if (useLocation) {
            this.lastFixGeoPoint = new Point2D(Util.to1E6(location.getLongitude()), Util.to1E6(location.getLatitude()));
            if (this.lastFix == null) {
                this.lastFix = new Location(location);
                if (this.runnable != null) {
                    new Thread(this.runnable).start();
                    this.runnable = null;
                }
            }
            this.lastFix.set(location);
        }

        checkMapCenter();

        this.mapView.invalidate();
    }

    private void checkMapCenter() {
        if ((this.userFollowingFlag) && (this.followingFlag))
            this.mapView.getController().animateTo(this.lastFixGeoPoint);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        if ("gps".equals(provider)) {
            switch (status) {
            case 0:
            case 1:
                if ((this.lastNetworkFix == null) || (!this.networkAvailable))
                    break;
                this.lastGpsFixTime = 0L;
                onLocationChanged(this.lastNetworkFix);
            }

        } else if ("network".equals(provider))
            switch (status) {
            case 2:
                this.networkAvailable = true;
                break;
            case 0:
            case 1:
                this.networkAvailable = false;
                break;
            }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public boolean runOnFirstFix(Runnable runnable) {
        if (this.lastFix != null) {
            Thread t = new Thread(runnable);
            t.start();
            return true;
        }
        this.runnable = runnable;
        return false;
    }

    public boolean onSnapToItem(int x, int y, Point snapPoint, MapView mapView) {
        return false;
    }

    public boolean onTap(Point2D p, MapView map) {
        Point point = map.getProjection().toPixels(p, this.reusable);
        if (this.rect.contains(point.x, point.y)) {
            return dispatchTap();
        }
        return false;
    }

    public void shouldAnimate(boolean shouldAnimate) {
        if (shouldAnimate)
            this.animating = 1;
        else
            this.animating = 0;
    }

    public void setBeaconAnimationOutlinePaint(Paint animationPaint) {
        this.beaconAnimationOutlinePaint = animationPaint;
    }

    public void setBeaconAnimationFillPaint(Paint beaconAnimationFillPaint) {
        this.beaconAnimationFillPaint = beaconAnimationFillPaint;
    }

    public void setMarker(Drawable marker, int markerAlignment) {
        this.marker = marker;
        if (markerAlignment != 0)
            this.markerAlignment = markerAlignment;
    }
}