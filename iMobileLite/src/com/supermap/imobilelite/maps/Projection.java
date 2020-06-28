package com.supermap.imobilelite.maps;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * <p>
 * 投影工具类。
 * </p>
 * <p>
 * 负责屏幕像素坐标与地理坐标的相互转化。
 * </p>
 * @author map
 * 
 */
public class Projection {
    // private static final String TAG = "com.supermap.android.maps.projection";
    private ProjectionUtil projectionUtil;
    private AbstractTileLayerView layerView;
    private Matrix rotateMatrix;
    private Matrix reverseRotateMatrix;
    private float[] mapPointOut = new float[2];
    private float[] reverseMapPointOut = new float[2];

    Point focalPointOnMap = null;

    private RectF rectF = new RectF();

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param layerView 图层视图对象。
     * @param projectionUtil 投影工具对象。
     */
    Projection(AbstractTileLayerView layerView, ProjectionUtil projectionUtil) {
        this.layerView = layerView;
        this.projectionUtil = projectionUtil;

        this.rotateMatrix = new Matrix();
        this.reverseRotateMatrix = new Matrix();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param layerView 图层视图对象。
     */
    public Projection(AbstractTileLayerView layerView) {
        this(layerView, new ProjectionUtil(layerView));
    }

    void setRotate(float rotateDegrees, int pivotX, int pivotY) {
        Matrix rotateMatrix = this.rotateMatrix;
        Matrix reverseRotateMatrix = this.reverseRotateMatrix;

        if (rotateDegrees == 0.0F) {
            rotateMatrix.reset();
            reverseRotateMatrix.reset();
            this.focalPointOnMap = null;
            return;
        }

        reverseRotateMatrix.reset();
        reverseRotateMatrix.setRotate(-rotateDegrees, pivotX, pivotY);
        rotateMatrix.reset();
        reverseRotateMatrix.invert(rotateMatrix);
    }

    private void arrayToPoint(float[] pt, Point p) {
        p.set((int) pt[0], (int) pt[1]);
    }

    void rotateMapRect(Rect rect) {
        this.rectF.set(rect);
        this.reverseRotateMatrix.mapRect(this.rectF);
        this.rectF.round(rect);
    }

    Point mapPoint(int x, int y, Point out) {
        if (out == null) {
            out = new Point(x, y);
        } else {
            out.set(x, y);
        }
        if (this.layerView.mapView.getMapRotation() != 0.0F) {
            this.mapPointOut[0] = x;
            this.mapPointOut[1] = y;
            this.reverseRotateMatrix.mapPoints(this.mapPointOut);
            arrayToPoint(this.mapPointOut, out);
        }
        out = offsetFromFocalPoint(out.x, out.y, out);
        return out;
    }

    Point reverseMapPoint(int x, int y, Point out) {
        if (out == null)
            out = new Point(x, y);

        out = offsetToFocalPoint(x, y, out);
        if (this.layerView.mapView.getMapRotation() != 0.0F) {
            this.reverseMapPointOut[0] = out.x;
            this.reverseMapPointOut[1] = out.y;
            this.rotateMatrix.mapPoints(this.reverseMapPointOut);
            arrayToPoint(this.reverseMapPointOut, out);
        }
        return out;
    }

    Point offsetFromFocalPoint(int x, int y, Point out) {
        if (out == null)
            out = new Point(x, y);
        else {
            out.set(x, y);
        }
        // LayerView layerView = this.layerView;
        Point focalPoint = layerView.mapView.focalPoint;

        if (focalPoint == null)
            return out;
        out.x = (x + ((layerView.mapView.getMapWidth() >> 1) - focalPoint.x));
        out.y = (y + ((layerView.mapView.getMapHeight() >> 1) - focalPoint.y));
        return out;
    }

    Point offsetToFocalPoint(int x, int y, Point out) {
        if (out == null)
            out = new Point(x, y);
        else {
            out.set(x, y);
        }
        // LayerView layerView = this.layerView;
        Point focalPoint = layerView.mapView.focalPoint;
        if (focalPoint == null)
            return out;

        out.x = (x + (focalPoint.x - (layerView.mapView.getMapWidth() >> 1)));
        out.y = (y + (focalPoint.y - (layerView.mapView.getMapHeight() >> 1)));
        return out;
    }

    Rect offsetToFocalPoint(Rect rect) {
        // LayerView layerView = this.layerView;
        Point focalPoint = layerView.mapView.focalPoint;
        if (focalPoint == null)
            return rect;

        int dx = focalPoint.x - (layerView.mapView.getMapWidth() >> 1);
        int dy = focalPoint.y - (layerView.mapView.getMapHeight() >> 1);
        rect.offset(dx, dy);
        return rect;
    }

    Point deScalePoint(int x, int y, Point out) {
        if (out == null)
            out = new Point(x, y);
        else
            out.set(x, y);
        Point scalePoint = this.layerView.mapView.scalePoint;
        out.x = (int) (scalePoint.x + (x - scalePoint.x) / this.layerView.mapView.currentScale + 0.5F);
        out.y = (int) (scalePoint.y + (y - scalePoint.y) / this.layerView.mapView.currentScale + 0.5F);

        return out;
    }

    Point scalePoint(int x, int y, Point out) {
        if (out == null) {
            out = new Point(x, y);
        } else {
            out.set(x, y);
        }
        Point scalePoint = this.layerView.mapView.scalePoint;
        out.x = (int) (scalePoint.x + (x - scalePoint.x) * this.layerView.mapView.currentScale + 0.5F);
        out.y = (int) (scalePoint.y + (y - scalePoint.y) * this.layerView.mapView.currentScale + 0.5F);

        return out;
    }

    int getScaleFactor(int zoom) {
        return this.projectionUtil.getScaleFactor(zoom);
    }

    /**
     * <p>
     * 计算显示指定矩形区域范围适合的层级。
     * </p>
     * @param bbox 指定矩形区域范围。
     * @return 显示该区域适合的层级。
     */
    public int calculateZoomLevel(BoundingBox bbox) {
        return this.projectionUtil.calculateZoomLevel(bbox);
    }

    int calculateZoomLevel(BoundingBox bbox, int horizontalMargin, int verticalMargin) {
        return this.projectionUtil.calculateZoomLevel(bbox, horizontalMargin, verticalMargin);
    }

    /**
     * <p>
     * 将地理像素坐标转化为屏幕上的像素坐标。
     * </p>
     * @param in 地理像素坐标点。
     * @return 屏幕上的像素坐标。
     */
    Point getScreenFromGlobal(Point in) {
        Point out = this.projectionUtil.getScreenFromGlobal(in);
        out = reverseMapPoint(out.x, out.y, out);
        return out;
    }

    /**
     * 将屏幕上的像素坐标点转化为对应的地理坐标。
     * @param x 像素坐标 x 。
     * @param y 像素坐标 y 。
     * @return 对应的地理坐标。
     */
    public Point2D fromPixels(int x, int y) {
        Point out = new Point(x, y);

        out = mapPoint(out.x, out.y, out);
        Point2D gp = this.projectionUtil.fromPixels(out.x, out.y);
        return gp;
    }

    float metersToEquatorPixels(float meters) {
        return this.projectionUtil.metersToEquatorPixels(meters);
    }

    /**
     * <p>
     * 将地理坐标转化为屏幕上的像素坐标。
     * </p>
     * @param in 地理坐标点。
     * @param out 屏幕上的像素坐标点参数对象。
     * @return 屏幕上的像素坐标点。
     */
    public Point toPixels(Point2D in, Point out) {
        out = this.projectionUtil.toPixels(in, out);
        out = reverseMapPoint(out.x, out.y, out);

        return out;
    }

    ProjectionUtil getProjectionUtil() {
        return this.projectionUtil;
    }

    Point2D getFocusPiont2D(Point point, Point2D point2D, float scaleFactor) {
        Point out = new Point(point.x, point.y);
        out = mapPoint(out.x, out.y, out);
        Point2D gp = this.projectionUtil.getFocusPiont2D(out, point2D, scaleFactor);
        return gp;
    }
}