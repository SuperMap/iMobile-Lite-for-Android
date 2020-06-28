package com.supermap.imobilelite.maps;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;


class RotatableIconOverlay extends Overlay {
    private static final String LOG_TAG = "com.supermap.android.maps.rotatableiconoverlay";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");

    public static final int TOUCH_TOLERANCE = 10;
    public static final int ANCHOR_CENTER = 0;
    public static final int ANCHOR_CENTER_LEFT = 1;
    public static final int ANCHOR_CENTER_RIGHT = 2;
    public static final int ANCHOR_TOP_CENTER = 3;
    public static final int ANCHOR_BOTTOM_CENTER = 4;
    private Point2D point;
    private Drawable drawable;
    private float rotation;
    private boolean rotateWithMap;
    private Paint paint;
    private int alignment;
    private boolean DEBUG = false;
    private Matrix matrix;
    private RectF imageRegion = new RectF();
    private RectF clipBounds = new RectF();

    public RotatableIconOverlay(Point2D point, Drawable drawable) {
        this(point, drawable, 35);
    }

    public RotatableIconOverlay(Point2D point, Drawable drawable, int alignment) {
        this.point = point;
        this.drawable = drawable;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.paint.setFilterBitmap(true);
        this.alignment = alignment;
        this.matrix = new Matrix();
        Overlay.setAlignment(drawable, alignment);
    }

    public Point2D getPoint() {
        return this.point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public Drawable getDrawable() {
        return this.drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        Overlay.setAlignment(drawable, this.alignment);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        this.matrix.setRotate(rotation);
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
        if (this.drawable != null)
            Overlay.setAlignment(this.drawable, alignment);
    }

    public int getAligment() {
        return this.alignment;
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (this.DEBUG)
            Log.d(LOG_TAG, resource.getMessage(MapCommon.ROTATABLEICONOVERLAY_DRAW));
        Point screenPt = mapView.getProjection().toPixels(this.point, null);

        this.imageRegion.set(this.drawable.getBounds());
        this.imageRegion.offset(screenPt.x, screenPt.y);

        this.clipBounds.set(canvas.getClipBounds());
        if (RectF.intersects(this.imageRegion, this.clipBounds)) {
            try {
                canvas.save();
                float degrees = this.rotation % 360.0F;
                if (this.rotateWithMap) {
                    degrees += mapView.getMapRotation();
                    degrees %= 360.0F;
                }
                canvas.translate(screenPt.x, screenPt.y);
                if (degrees != 0.0F) {
                    canvas.rotate(degrees);
                }
                this.drawable.draw(canvas);
            } finally {
                canvas.restore();
            }

        } else if (this.DEBUG)
            Log.d(LOG_TAG, resource.getMessage(MapCommon.ROTATABLEICONOVERLAY_DRAW_SKIP));
    }

    public boolean onTap(Point2D point, MapView mapView) {
        if ((this.tapListener != null) && (intersects(point, mapView))) {
            if (this.DEBUG)
                Log.d(LOG_TAG, resource.getMessage(MapCommon.ROTATABLEICONOVERLAY_ONTAP_HANDLED));
            this.tapListener.onTap(point, mapView);
            return true;
        }
        if (this.DEBUG)
            Log.d(LOG_TAG, resource.getMessage(MapCommon.ROTATABLEICONOVERLAY_ONTAP_NOTHANDLED));
        return false;
    }

    public boolean onTouchEvent(MotionEvent evt, MapView mapView) {
        if (this.touchListener != null) {
            Point2D gp = mapView.getProjection().fromPixels((int) evt.getX(), (int) evt.getY());
            if (intersects(gp, mapView)) {
                this.touchListener.onTouch(evt, mapView);
                return true;
            }
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent evt, MapView mapView) {
        if (this.trackballListener != null) {
            Point2D gp = mapView.getProjection().fromPixels((int) evt.getX(), (int) evt.getY());
            if (intersects(gp, mapView)) {
                this.trackballListener.onTrackballEvent(evt, mapView);
                return true;
            }
        }
        return false;
    }

    private boolean intersects(Point2D point, MapView mapView) {
        Point screenPt = mapView.getProjection().toPixels(this.point, null);
        Point touchPt = mapView.getProjection().toPixels(point, null);

        this.imageRegion.set(this.drawable.getBounds());
        this.matrix.mapRect(this.imageRegion);
        this.imageRegion.offset(screenPt.x, screenPt.y);

        return this.imageRegion.contains(touchPt.x, touchPt.y);
    }

    public void destroy() {
        this.point = null;
        this.drawable = null;
    }

    public boolean isRotateWithMap() {
        return this.rotateWithMap;
    }

    public void setRotateWithMap(boolean rotateWithMap) {
        this.rotateWithMap = rotateWithMap;
    }
}