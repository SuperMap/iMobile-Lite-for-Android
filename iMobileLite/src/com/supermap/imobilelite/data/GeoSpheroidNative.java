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
class GeoSpheroidNative {
    private GeoSpheroidNative() {
    }
    public native static long jni_New();
    public native static long jni_New2(int type);
    public native static long jni_New3(double axis,double flatten,String name);

    public native static void jni_Delete(long instatnce);

    public native static long jni_Clone(long instance);

    public native static double jni_GetAxis(long instance);
    public native static void jni_SetAxis(long instance,double value);

    public native static double jni_GetFlatten(long instance);
    public native static void jni_SetFlatten(long instance,double value);

    public native static String jni_GetName(long instance);
    public native static void jni_SetName(long instance,String value);

    public native static int jni_GetType(long instance);
    public native static void jni_SetType(long instance,int value);

    public native static boolean jni_FromXML(long instance, String xml);
    public native static String jni_ToXML(long instance);

}
