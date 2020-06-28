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
 * @作者 SuperMap Objects Java Team
 * @version 2.0
 */
class GeoTextNative {
    private GeoTextNative() {

    }

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static long jni_Clone(long instance);

    public native static String jni_GetContent(long instance);

    public native static int jni_GetPartCount(long instance);

    public native static long jni_GetTextStyle(long instance);

    public native static void jni_SetTextStyle(long instance, long textStyle);

    public native static int jni_AddPart(long instance, long subHandle,
                                         double x,
                                         double y);

    public native static boolean jni_InsertPart(long instance, int index,
                                                long subHandle, double x,
                                                double y);

    public native static boolean jni_RemovePart(long instance, int index);

    public native static boolean jni_SetPart(long instance, int index,
                                             long subHandle,
                                             double x, double y);

  
    public native static double jni_GetSubRotation(long geoTextInstance, int index);

    public native static void jni_SetSubRotation(long geoTextInstance,
                                              double rotation, int index);

    public native static String jni_GetSubText(long geoTextInstance, int index);

    public native static void jni_SetSubText(long geoTextInstance, String text,
                                          int index);

    public native static long jni_GetSubHandle(long geoTextInstance,int index);

}
