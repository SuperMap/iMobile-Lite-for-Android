package com.supermap.imobilelite.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @作者 not attributable
 * @version 2.0
 */

public class GeoSpatialRefType extends Enum {
    private GeoSpatialRefType(int value, int ugcValue) {
        super(value, ugcValue);
    }
    public static final GeoSpatialRefType SPATIALREF_NONEARTH = new GeoSpatialRefType(0,0);
    public static final GeoSpatialRefType SPATIALREF_EARTH_LONGITUDE_LATITUDE = new GeoSpatialRefType(1,1);
    public static final GeoSpatialRefType SPATIALREF_EARTH_PROJECTION = new GeoSpatialRefType(2,2);
}
