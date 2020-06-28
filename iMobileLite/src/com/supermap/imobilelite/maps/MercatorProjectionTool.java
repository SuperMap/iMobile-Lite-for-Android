package com.supermap.imobilelite.maps;


//import com.supermap.services.components.commontypes.Geometry;
//import com.supermap.services.components.commontypes.GeometryType;

class MercatorProjectionTool {
    private static final double GlobeTop = 20037508.34;

    static double mercatorY2lat(double y) {
        double result = y / GlobeTop * 180;
        result = 180 / Math.PI * (2 * Math.atan(Math.exp(result * Math.PI / 180)) - Math.PI / 2);
        return result;
    }

    static double lat2mercatorY(double lat) {
        double result = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
        result = result * GlobeTop / 180;
        return result;
    }

    static double mercatorX2lon(double x) {
        double result = x / GlobeTop * 180;
        return result;
    }

    static double lon2mercatorX(double lon) {
        double result = lon * GlobeTop / 180;
        return result;
    }

    /**
     * 将墨卡托投影的坐标转换成经纬度坐标
     * @param point
     * @return
     */
    // public static Point2D mercator2lonlat(com.supermap.services.components.commontypes.Point2D point){
    // double lon = mercatorX2lon(point.x);
    // double lat = mercatorY2lat(point.y);
    // return new Point2D(lon, lat);
    // }

    /**
     * 将经纬度坐标转换成墨卡托投影坐标
     * @param geoPoint
     * @return
     */
    // public static com.supermap.services.components.commontypes.Point2D lonlat2mercator(Point2D geoPoint) {
    // double x = lon2mercatorX(geoPoint.getX());
    // double y = lat2mercatorY(geoPoint.getY());
    // return new com.supermap.services.components.commontypes.Point2D(x, y);
    // }

    /**
     * 将墨卡托投影的坐标转换成经纬度坐标
     * @param geometry
     * @return
     */
    // public static List<Point2D> mercator2lonlat(Geometry geometry){
    // List< Point2D> geoPoints = new ArrayList<Point2D>();
    // com.supermap.services.components.commontypes.Point2D[] points = geometry.points;
    // for(com.supermap.services.components.commontypes.Point2D point : points){
    // Point2D geoPoint = mercator2lonlat(point);
    // geoPoints.add(geoPoint);
    // }
    // return geoPoints;
    // }

    /**
     * 将经纬度坐标转换成墨卡托投影坐标
     * @param geoPoint
     * @return
     */
    // public static Geometry lonlat2mercator(List<Point2D> geoPoints) {
    // Geometry retGeo = null;
    // List<com.supermap.services.components.commontypes.Point2D> pts = new ArrayList<com.supermap.services.components.commontypes.Point2D>();
    // com.supermap.services.components.commontypes.Point2D[] ptArray = null;
    // for (Point2D gp : geoPoints) {
    // pts.add(lonlat2mercator(gp));
    // }
    // ptArray = new com.supermap.services.components.commontypes.Point2D[pts.size()];
    // ptArray = pts.toArray(ptArray);
    // if (pts.size() == 1) {
    // retGeo = Geometry.fromPoint2Ds(ptArray, GeometryType.POINT);
    // } else if (pts.size() > 1 && !pts.get(0).equals(pts.get(pts.size() - 1))) {
    // retGeo = Geometry.fromPoint2Ds(ptArray, GeometryType.LINE);
    // } else if (pts.size() > 2 && pts.get(0).equals(pts.get(pts.size() - 1))) {
    // retGeo = Geometry.fromPoint2Ds(ptArray, GeometryType.REGION);
    // }
    //
    // return retGeo;
    // }
}
