package com.supermap.imobilelite.maps;

import android.graphics.Point;
import android.os.Message;

class MapAnimator {
    private static final String TAG = "com.supermap.android.maps.mapanimator";
    private MapView mapView;
    FlingAnimator flingAnimator;

    public MapAnimator(MapView mapView) {
        this.mapView = mapView;
        this.flingAnimator = new FlingAnimator(mapView);
    }

    public void animateZoomScaler(int startZoom, int endZoom, float currentScale, Point centerPoint, boolean snappable) {
        if ((snappable) && (startZoom < endZoom)) {
            applySnappable(centerPoint.x, centerPoint.y, centerPoint);
        }

        this.mapView.queueAnimator(new ZoomAnimator(this.mapView, startZoom, endZoom, currentScale, centerPoint));
    }

    private void applySnappable(int x, int y, Point snapPoint) {
        for (Overlay o : this.mapView.getOverlays())
            if ((o instanceof Overlay.Snappable)) {
                Overlay.Snappable os = (Overlay.Snappable) o;
                if (os.onSnapToItem(x, y, snapPoint, this.mapView))
                    break;
            }
    }

    public void animatePan(int endX, int endY) {
        this.flingAnimator.animate(endX, endY);
        this.mapView.queueAnimator(this.flingAnimator);
    }

    public void animateTo(Point2D geoPoint) {
        if (geoPoint != null)
            animateTo(geoPoint, (Runnable) null);
    }

    public void animateTo(Point2D geoPoint, int zoomLevel) {
        if (geoPoint != null) {
            animateTo(geoPoint);
            this.mapView.queueAnimator(new ZoomAnimator(this.mapView, this.mapView.getZoomLevel(), zoomLevel, 1.0F, new Point(this.mapView.getFocalPoint().x,
                    this.mapView.getFocalPoint().y)));
        }
    }

    public void animateTo(Point2D geoPoint, Runnable runnable) {
        if (geoPoint != null) {
            this.flingAnimator.animate(geoPoint);
            this.flingAnimator.setRunnable(runnable);
            this.mapView.queueAnimator(this.flingAnimator);
        }
    }

    public void animateTo(Point2D geoPoint, Message message) {
        if (geoPoint != null) {
            this.flingAnimator.animate(geoPoint);
            this.flingAnimator.setMessage(message);
            this.mapView.queueAnimator(this.flingAnimator);
        }
    }

    public void animateFlick(Point point, float velocityX, float velocityY) {
        if (point != null) {
            this.flingAnimator.animate(point, velocityX, velocityY);
            this.mapView.queueAnimator(this.flingAnimator);
        }
    }

    public void animateRotation(float rotateDegrees) {
        this.mapView.queueAnimator(new RotationAnimator(this.mapView, rotateDegrees));
    }

    public void stopSpanning() {
        stopSpanning(false);
    }

    public void stopSpanning(boolean jumpToFinish) {
        this.flingAnimator.stopAnimation(jumpToFinish);
    }
}