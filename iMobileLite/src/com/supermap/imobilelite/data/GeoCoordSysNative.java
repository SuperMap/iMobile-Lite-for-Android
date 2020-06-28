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
class GeoCoordSysNative {
    private GeoCoordSysNative() {
    }

    public native static long jni_New();

    public native static long jni_New2(int type, int spatialRefType);

    public native static long jni_New3(long geoDatum, long geoPrimeMeridian,
                                       int spatialRefType, int unit,
                                       String name);
    public native static void jni_Reset(long instance);

    public native static void jni_Delete(long instance);

    public native static long jni_Clone(long instance);

    public native static String jni_GetName(long instance);

    public native static void jni_SetName(long instance, String value);

    public native static long jni_GetGeoDatum(long instance);

    public native static void jni_SetGeoDatum(long instance, long value);

    public native static long jni_GetGeoPrimeMeridian(long instance);

    public native static void jni_SetGeoPrimeMeridian(long instance, long value);

    public native static int jni_GetCoordUnit(long instance);

    public native static void jni_SetCoordUnit(long instance,int value);

    public native static int jni_GetType(long instance);

    public native static void jni_SetType(long instance, int value);

    public native static int jni_GetGeoSpatialRefType(long instance);

    public native static void jni_SetGeoSpatialRefType(long instance, int value);

    public native static boolean jni_FromXML(long instance, String xml);

    public native static String jni_ToXML(long instance);
}
