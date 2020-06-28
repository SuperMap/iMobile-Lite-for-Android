package com.supermap.imobilelite.maps;

import android.graphics.Point;
import android.graphics.Rect;

import com.supermap.services.util.ResourceManager;

/**
 * 投影工具类。
 * @author map
 * 
 */
class ProjectionUtil {
    private static final String LOG_TAG = "com.supermap.android.maps.projectionutil";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");

    private int zoomLevel;
    private AbstractTileLayerView layerView;
    private int centerXPix;// 设备屏幕中心点对应的像素坐标X
    private int centerYPix;// 设备屏幕中心点对应的像素坐标Y
    private double centerLat = Double.MIN_VALUE;
    private double centerLng = Double.MIN_VALUE;
    private static final int[] SCALE_FACTORS = { 0, 0, 110936008, 55468004, 27734002, 13867001, 6933500, 3466750, 1733375, 866687, 433343, 216671, 108335,
            54167, 27083, 13541, 6770, 3385, 1692 };
    // 地图范围的bounds
    private double mapBoundsLeft = -180;
    private double mapBoundsRight = 180;
    private double mapBoundsTop = 85.051128779806589;
    private double mapBoundsBottom = -85.051128779806589;

    // 图层范围的bounds
    private double boundsLeft = -180;
    private double boundsRight = 180;
    private double boundsTop = 85.051128779806589;
    private double boundsBottom = -85.051128779806589;

    private double leftPadRate = 0;
    private double rightPadRate = 1;
    private double topPadRate = 0;
    private double bottomPadRate = 1;

    private double prjCoordSysRadio = 1.0d;
    private double resolution;

    // private int halfScreenWidth = -1;// 设备屏幕宽度的一半
    // private int halfScreenHeight = -1;// 设备屏幕高度的一半

    public ProjectionUtil(AbstractTileLayerView layerView) {
        super();
        this.layerView = layerView;
        // 给地图范围的bounds动态赋值
        if (layerView.mapView != null && layerView.mapView.getIndexBounds().getWidth() != 0) {
            mapBoundsRight = layerView.mapView.getIndexBounds().rightBottom.getX();
            mapBoundsTop = layerView.mapView.getIndexBounds().leftTop.getY();
            mapBoundsBottom = layerView.mapView.getIndexBounds().rightBottom.getY();
            mapBoundsLeft = layerView.mapView.getIndexBounds().leftTop.getX();
        } 
//        else if (!layerView.isGCSLayer()) {
//            Log.w(LOG_TAG, resource.getMessage(MapCommon.PROJECTIONUTIL_INIT_FAIL));
//            throw new IllegalStateException(resource.getMessage(MapCommon.PROJECTIONUTIL_MAPVIEW_NULL));
//        }
        // 给图层范围的bounds动态赋值
        if (Double.compare(layerView.getBounds().getLeft(), layerView.getBounds().getRight()) != 0) {
            boundsRight = layerView.getBounds().getRight();
            boundsTop = layerView.getBounds().getTop();
            boundsBottom = layerView.getBounds().getBottom();
            boundsLeft = layerView.getBounds().getLeft();
        }
        leftPadRate = (boundsLeft - mapBoundsLeft) / (mapBoundsRight - mapBoundsLeft);
        rightPadRate = (boundsRight - mapBoundsLeft) / (mapBoundsRight - mapBoundsLeft);
        topPadRate = (mapBoundsTop - boundsTop) / (mapBoundsTop - mapBoundsBottom);
        bottomPadRate = (mapBoundsTop - boundsBottom) / (mapBoundsTop - mapBoundsBottom);
        // TODO 屏幕宽高变化时是否需要响应
        // halfScreenWidth = this.layerView.mapView.getMapWidth() >> 1;
        // halfScreenHeight = this.layerView.mapView.getMapHeight() >> 1;
    }

    /**
     * 将屏幕上的像素坐标点转化为对应的地理坐标
     * @param x
     * @param y
     * @return
     */
    public Point2D fromPixels(int x, int y) {
        check();
        // int pixelX = x + (this.centerXPix - halfScreenWidth);
        // int pixelY = x + (this.centerYPix - halfScreenHeight);
        int pixelX = x + (this.centerXPix - (this.layerView.mapView.getWidth() >> 1));// 有问题时考虑mapView.getMapWidth()?
        int pixelY = y + (this.centerYPix - (this.layerView.mapView.getHeight() >> 1));
        // int pixelX = this.centerXPix + Math.round((x - (this.layerView.mapView.getWidth() >> 1))/layerView.mapView.currentScale);
        // int pixelY = this.centerYPix + Math.round((y - (this.layerView.mapView.getHeight() >> 1))/layerView.mapView.currentScale);
        // int realX = Math.round((x - this.layerView.mapView.scalePoint.x) / layerView.mapView.currentScale) + this.layerView.mapView.scalePoint.x;
        // int realY = Math.round((y - this.layerView.mapView.scalePoint.y) / layerView.mapView.currentScale) + this.layerView.mapView.scalePoint.y;
        // int realCenterX = Math.round(((this.layerView.mapView.getWidth() >> 1) - this.layerView.mapView.scalePoint.x) / layerView.mapView.currentScale)
        // + this.layerView.mapView.scalePoint.x;
        // int realCenterY = Math.round(((this.layerView.mapView.getHeight() >> 1) - this.layerView.mapView.scalePoint.y) / layerView.mapView.currentScale)
        // + this.layerView.mapView.scalePoint.y;
        // int pixelX = this.centerXPix + realX - realCenterX;
        // int pixelY = this.centerYPix + realY - realCenterY;
        // int leftTopPixel_X = this.centerXPix - (this.layerView.mapView.getMapWidth() >> 1);
        // int leftTopPixel_Y = this.centerYPix - (this.layerView.mapView.getMapHeight() >> 1);
        // return fromGlobalPixels(x + leftTopPixel_X, y + leftTopPixel_Y);
        return fromGlobalPixels(pixelX, pixelY);
    }

    // public Point2D fromRealPixels(int x, int y) {
    // check();
    // int pixelX = this.centerXPix + x - (this.layerView.mapView.getWidth() >> 1);
    // int pixelY = this.centerYPix + y - (this.layerView.mapView.getHeight() >> 1);
    // return fromGlobalPixels(pixelX, pixelY);
    // }

    /**
     * 计算指定长度在当前视角地图上对应的像素值。
     * @param meters
     * @return
     */
    public float metersToEquatorPixels(float meters) {
        float dist = metersPerPixel(this.layerView.mapView.getCenter().getY(), this.getZoomLevel());
        return meters / dist;
    }

    /**
     * <p>
     * 将地理坐标转化为屏幕上的像素坐标
     * </p>
     * @param in
     * @param out
     * @return
     */
    public Point toPixels(Point2D in, Point out) {
        if (out == null)
            out = new Point();
        check();
        toGlobalPixels(in, out);

        // int pixelX = Math.round((out.x - this.centerXPix) * layerView.mapView.currentScale);
        // int pixelY = Math.round((out.y - this.centerYPix) * layerView.mapView.currentScale);
        int pixelX = out.x - this.centerXPix;
        int pixelY = out.y - this.centerYPix;
        out.x = (this.layerView.mapView.getWidth() / 2 + pixelX);
        out.y = (this.layerView.mapView.getHeight() / 2 + pixelY);

        // int pixelX = this.layerView.mapView.getWidth() / 2 +(out.x - this.centerXPix);
        // int pixelY = this.layerView.mapView.getHeight() / 2 +(out.y - this.centerYPix);
        // out.x = Math.round((pixelX-layerView.mapView.scalePoint.x)* layerView.mapView.currentScale)+layerView.mapView.scalePoint.x;
        // out.y = Math.round((pixelY-layerView.mapView.scalePoint.y)* layerView.mapView.currentScale)+layerView.mapView.scalePoint.y;
        return out;
    }

    /**
     * <p>
     * 将地理坐标转化为屏幕上的像素坐标,内部绘制的时候使用
     * </p>
     * @param in
     * @param out
     * @return
     */
    // protected Point toRealPixels(Point2D in, Point out) {
    // if (out == null)
    // out = new Point();
    // check();
    // toGlobalPixels(in, out);
    //
    // int pixelX = out.x - this.centerXPix;
    // int pixelY = out.y - this.centerYPix;
    //
    // out.x = (this.layerView.mapView.getWidth() / 2 + pixelX);
    // out.y = (this.layerView.mapView.getHeight() / 2 + pixelY);
    // return out;
    // }

    // 将地理像素坐标转化为屏幕上的像素坐标
    public Point getScreenFromGlobal(Point in) {
        check();
        int offsetX = in.x - this.centerXPix;
        int offsetY = in.y - this.centerYPix;

        return new Point(this.layerView.mapView.getMapWidth() / 2 + offsetX, this.layerView.mapView.getMapHeight() / 2 + offsetY);
    }

    public int getScaleFactor(int zoom) {
        return SCALE_FACTORS[zoom];
    }

    public int calculateZoomLevel(BoundingBox bbox) {
        return calculateZoomLevel(bbox, 0, 0);
    }

    // 待重新实现，该实现只适用于全球范围的18级别的比例尺换算
    public int calculateZoomLevel(BoundingBox bbox, int horizontalMargin, int verticalMargin) {
        int zoom = this.getZoomLevel();
        try {
            Point ul = toPixels(bbox.leftTop, null);
            Point lr = toPixels(bbox.rightBottom, null);
            double width = Math.abs(ul.x - lr.x);
            double height = Math.abs(ul.y - lr.y);
            // Log.d(LOG_TAG,
            // resource.getMessage(MapCommon.PROJECTIONUTIL_CALCULATEZOOMLEVEL,
            // new String[] { String.valueOf(ul), String.valueOf(lr), String.valueOf(width), String.valueOf(height) }));
            double ratio = Math.max(width / (this.layerView.mapView.getMapWidth() - horizontalMargin), height
                    / (this.layerView.mapView.getMapHeight() - verticalMargin));
            double ratioLog = Math.log(ratio) / Math.log(2.0D);            
            int deltaZoom = (int) Math.ceil(ratioLog);//ratio > 1.0D ? (int) Math.ceil(ratioLog) : (int) Math.ceil(ratioLog);
            zoom = this.getZoomLevel() - deltaZoom < 0 ? 0 : this.getZoomLevel() - deltaZoom;
        } catch (Exception ex) {
        }

        return zoom;
    }

    /**
     * 校正，主要是让工具里面保存的状态（层级、地图中心点）和当前实际地图状态保持一致。
     */
    private void check() {
        boolean reset = false;
        if (this.getZoomLevel() != this.zoomLevel) {
            reset = true;
        }
        Point2D c = this.layerView.mapView.centerGeoPoint;

        if (c == null)
            return;
        if (c.getY() != this.centerLat)
            reset = true;
        if (c.getX() != this.centerLng) {
            reset = true;
        }
        if (layerView.mapView.currentScale != 1.0)// 缩放动画效果需要
            reset = true;
        // 解决当缩放层级和中心点以及缩放因子currentScale不变的情况下，分辨率数组发生变化时重新计算
        if (!reset && resolution != layerView.mapView.getResolution()) {// 保证分辨率发生变化重新计算结果
            reset = true;
        }
        if (!reset) {
            return;
        }
        synchronized (this) {
            this.zoomLevel = this.getZoomLevel();
            this.centerLat = c.getY();
            this.centerLng = c.getX();
            this.resolution = layerView.mapView.getResolution();
            Point p = toGlobalPixels(c, new Point());
            this.centerXPix = p.x;
            this.centerYPix = p.y;
        }
    }

    /**
     * 将地理坐标点转化为地理像素坐标点
     * @param in
     * @param out
     * @return
     */
    protected Point toGlobalPixels(Point2D in, Point out) {
        if (out == null) {
            out = new Point();
        }
        initGCSRadius();
        double res = this.layerView.mapView.getResolution() / layerView.mapView.getDensity();
        double longitude = in.getX();
        double latitude = in.getY();
        out.x = (int) Math.round((longitude - mapBoundsLeft) * prjCoordSysRadio / res);
        out.y = (int) Math.round((mapBoundsTop - latitude) * prjCoordSysRadio / res);

        return out;
    }

    /**
     * 将地理像素坐标转化为地理坐标
     * @param x
     * @param y
     * @return
     */
    private Point2D fromGlobalPixels(int x, int y) {
        initGCSRadius();
        double res = this.layerView.mapView.getResolution() / layerView.mapView.getDensity();
        // int totalX = (int) Math.round(this.layerView.mapView.getIndexBounds().getWidth() * prjCoordSysRadio / res);
        // int totalY = (int) Math.round(this.layerView.mapView.getIndexBounds().getHeight() * prjCoordSysRadio / res);
        // // x = (totalX + x) % totalX;
        // // y = (totalY + y) % totalY;
        // // double latitude = ((totalY + y) % totalY) / (totalY * 1.0D) * this.layerView.mapView.getBounds().getHeight();
        // // double longitude = ((totalX + x) % totalX) / (totalX * 1.0D) * this.layerView.mapView.getBounds().getWidth();
        // double latitude = (y / (totalY * 1D)) * this.layerView.mapView.getIndexBounds().getHeight();
        // double longitude = (x / (totalX * 1D)) * this.layerView.mapView.getIndexBounds().getWidth();
        double latitude = y * res / prjCoordSysRadio;
        double longitude = x * res / prjCoordSysRadio;

        return new Point2D(longitude + mapBoundsLeft, mapBoundsTop - latitude);
    }

    /**
     * 计算在特定层级上 指定纬度每个像素的地理长度。
     * @param latitude
     * @param zoom
     * @return
     */
    private float metersPerPixel(double latitude, int zoom) {
        // TODO 重构该接口，支持任意坐标系，减少重复计算引起的误差
//        check();
//        initGCSRadius();
//        int totalY = (int) Math.round(this.layerView.mapView.getIndexBounds().getHeight() * prjCoordSysRadio / this.layerView.mapView.getResolution());
//        // TODO: 暂时没有功能用到该接口
//        // return (float) (Math.cos(latitude * 3.141592653589793D / 180.0D) * 2.0D * 3.141592653589793D * 6378137.0D / (TILE_SIZE << zoom));
//        double scale = Math.cos(latitude * Math.PI / 180.0D);// 在该纬度上和在赤道上每个像素所代表的长度之比
//        double meterPerPixelAtRadius = 2.0D * Math.PI * 6378137 / totalY;
//        return (float) (scale * meterPerPixelAtRadius);
        double meterPerPixel = this.layerView.mapView.getResolution() / layerView.mapView.getDensity();
        // if (layerView.isGCSLayer) {
        // double scale = Math.cos(latitude * Math.PI / 180.0D);// 在该纬度上和在赤道上每个像素所代表的长度之比
        // meterPerPixel = scale * meterPerPixel;// * Math.PI * 6378137 / 180.0;
        // }
        return (float) meterPerPixel;
    }

    /**
     * 得到当前屏幕像素坐标对应的地图像素坐标
     * @param x
     * @param y
     * @param out
     * @return
     */
    protected Point getGlobalFromScreen(int x, int y, Point out) {
        check();
        initGCSRadius();
        // int zoom = this.layerView.getZoomLevel();
        // int totalX = (int) Math.round(this.layerView.mapView.getBounds().getWidth() * prjCoordSysRadio / this.layerView.mapView.getResolutions()[zoom]);
        // int totalY = (int) Math.round(this.layerView.mapView.getBounds().getHeight() * prjCoordSysRadio / this.layerView.mapView.getResolutions()[zoom]);
        int globalX = this.centerXPix + (x - (this.layerView.mapView.getMapWidth() >> 1));
        int globalY = this.centerYPix + (y - (this.layerView.mapView.getMapHeight() >> 1));

        /*if ((globalY < 0) || (globalY > totalY)) {
            return null;
        }*/
        if (out == null)
            out = new Point();
        out.set(globalX, globalY);
        // 支持固定比例尺，直接返回
        // if (layerView.isVisibleScalesEnabled() && layerView.getVisibleScales() != null && layerView.getVisibleScales().length >= layerView.getZoomLevel()) {
        // return out;
        // }
        /*// 通常情况下以全球范围出图，需过滤不在当前范围的图片后返回
        int entireTop = (int) (totalY * topPadRate) - 256;
        int entireLeft = (int) (totalX * leftPadRate) - 256;
        int entireBottom = (int) (totalY * bottomPadRate) + 256;
        int entireRight = (int) (totalX * rightPadRate) + 256;
        if (!(globalX >= entireLeft && globalX <= entireRight && globalY >= entireTop && globalY <= entireBottom)) {
            // if(layerView.getCurrentMapURL().contains("jingjin")) {
            // Log.d(LOG_TAG, "filted:"+globalX+","+globalY+","+entireLeft+","+entireRight+","+entireTop+","+entireBottom);
            // }
            // 这块的算法返回null后会使外部迭代时该绘制的地方没有绘制
            return null;
        }*/
        // if(layerView.getCurrentMapURL().contains("jingjin")) {
        // Log.d(LOG_TAG, "remained:"+globalX+","+globalY+","+entireLeft+","+entireRight+","+entireTop+","+entireBottom);
        // }
        return out;
    }

    /**
     * <p>
     * 根据当前分辨率最接近的分辨率(分辨率数组中的一个)获取当前屏幕像素坐标对应的地图像素坐标，只用于计算瓦片的行列。
     * </p>
     * @param x
     * @param y
     * @param out
     * @return
     */
    protected Point getNearGlobalFromScreen(int x, int y, Point out) {
        initGCSRadius();
        double res = this.layerView.mapView.getRealResolution() / layerView.mapView.getDensity();
        Point point = new Point();
        point.x = (int) Math.round((layerView.mapView.centerGeoPoint.getX() - mapBoundsLeft) * prjCoordSysRadio / res);
        point.y = (int) Math.round((mapBoundsTop - layerView.mapView.centerGeoPoint.getY()) * prjCoordSysRadio / res);
        int globalX = point.x + (x - (this.layerView.mapView.getMapWidth() >> 1));
        int globalY = point.y + (y - (this.layerView.mapView.getMapHeight() >> 1));
        if (out == null)
            out = new Point();
        out.set(globalX, globalY);
        return out;
    }

    void initGCSRadius() {
        if (this.layerView.isGCSLayer()) {
            double radius = this.layerView.getCRS().datumAxis > 1d ? layerView.getCRS().datumAxis : Constants.DEFAULT_AXIS;
            prjCoordSysRadio = Math.PI * radius / 180.0;
        } else {
            prjCoordSysRadio = 1d;
        }
    }

    /**
     * 得到当前地图图层对应的地理像素坐标范围（小于等于整幅地图地理像素坐标）
     * @return
     */
    Rect getMapImageSize() {
        /*if (layerView.isPCSNonEarth()) {
            // 平面坐标系的返回个左上角在0,0的就可以了，别的无所谓。
            return new Rect(0, 0, 256, 256);
        } else {
            initGCSRadius();
        }*/
        initGCSRadius();

        // int zoom = this.getZoomLevel();
        double res = this.layerView.mapView.getResolution() / layerView.mapView.getDensity();// 除于mapView.currentScale
        int totalX = (int) Math.round(this.layerView.mapView.getIndexBounds().getWidth() * prjCoordSysRadio / res);
        int totalY = (int) Math.round(this.layerView.mapView.getIndexBounds().getHeight() * prjCoordSysRadio / res);
        // int totalX = (int) Math.round((this.layerView.getBoundsRight() - this.layerView.getBoundsLeft()) * prjCoordSysRadio
        // / this.layerView.mapView.getResolutions()[zoom]);
        // int totalY = (int) Math.round((this.layerView.getBoundsTop() - this.layerView.getBoundsBottom()) * prjCoordSysRadio
        // / this.layerView.mapView.getResolutions()[zoom]);
        return new Rect((int) (totalX * leftPadRate), (int) (totalY * topPadRate), (int) (totalX * rightPadRate), (int) (totalY * bottomPadRate));
    }

    private int getZoomLevel() {
        if (this.layerView.mapView != null) {
            return this.layerView.mapView.getZoomLevel();
        } else {
            throw new IllegalStateException("获取层级错误。");
        }
    }

    Point2D getFocusPiont2D(Point point, Point2D point2D, float scaleFactor) {
        initGCSRadius();
        double res = this.layerView.mapView.getRealResolution() / scaleFactor / layerView.mapView.getDensity();
        // Point out = new Point();
        // double longitude = point2D.getX();
        // double latitude = point2D.getY();
        // out.x = (int) Math.round((longitude - mapBoundsLeft) * prjCoordSysRadio / res);
        // out.y = (int) Math.round((mapBoundsTop - latitude) * prjCoordSysRadio / res);
        // int pixelX = out.x + (this.layerView.mapView.getWidth() >> 1) - point.x;
        // int pixelY = out.y + (this.layerView.mapView.getHeight() >> 1) - point.y;
        // double lat = pixelY * res / prjCoordSysRadio;
        // double lon = pixelX * res / prjCoordSysRadio;

        double lon = point2D.x + ((this.layerView.mapView.getWidth() >> 1) - point.x) * res / prjCoordSysRadio;
        // 屏幕上方的point2D的Y值越大，所以这部是减
        double lat = point2D.y - ((this.layerView.mapView.getHeight() >> 1) - point.y) * res / prjCoordSysRadio;
        return new Point2D(lon, lat);
        // return new Point2D(lon + mapBoundsLeft, mapBoundsTop - lat);
    }
}
