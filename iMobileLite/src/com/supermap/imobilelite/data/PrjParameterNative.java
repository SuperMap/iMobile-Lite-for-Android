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
class PrjParameterNative {
    private PrjParameterNative() {
    }

    public native static long jni_New();

    public native static void jni_Delete(long instatnce);

    public native static long jni_Clone(long instance);

    public native static boolean jni_FromXML(long instance, String xml);

    public native static String jni_ToXML(long instance);

    public native static double jni_GetFalseEasting(long instance);

    public native static void jni_SetFalseEasting(long instance, double value);

    public native static double jni_GetFalseNorthing(long instance);

    public native static void jni_SetFalseNorthing(long instance, double value);

    public native static double jni_GetCentralMeridian(long instance);

    public native static void jni_SetCentralMeridian(long instance,
            double value);

    public native static double jni_GetCentralParallel(long instance);

    public native static void jni_SetCentralParallel(long instance,
            double value);

    public native static double jni_GetStandardParallel1(long instance);

    public native static void jni_SetStandardParallel1(long instance,
            double value);

    public native static double jni_GetStandardParallel2(long instance);

    public native static void jni_SetStandardParallel2(long instance,
            double value);

    public native static double jni_GetScaleFactor(long instance);

    public native static void jni_SetScaleFactor(long instance, double value);

    public native static double jni_GetAzimuth(long instance);

    public native static void jni_SetAzimuth(long instance, double value);

    public native static double jni_GetFirstPointLongitude(long instance);

    public native static void jni_SetFirstPointLongitude(long instance,
            double value);

    public native static double jni_GetSecondPointLongitude(long instance);

    public native static void jni_SetSecondPointLongitude(long instance,
            double value);
}
