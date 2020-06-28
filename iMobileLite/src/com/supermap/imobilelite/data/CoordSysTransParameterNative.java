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
 * @作者 zhangjinan
 * @version 2.0
 */
class CoordSysTransParameterNative {
    private CoordSysTransParameterNative() {
    }

    public native static long jni_New();

    public native static void jni_Delete(long instatnce);

    public native static long jni_Clone(long instance);

    public native static boolean jni_FromXML(long instance, String xml);

    public native static String jni_ToXML(long instance);

    public native static double jni_GetTranslateX(long instance);

    public native static void jni_SetTranslateX(long instance, double value);

    public native static double jni_GetTranslateY(long instance);

    public native static void jni_SetTranslateY(long instance, double value);
    
    public native static double jni_GetTranslateZ(long instance);

    public native static void jni_SetTranslateZ(long instance, double value);

    public native static double jni_GetRotateX(long instance);

    public native static void jni_SetRotateX(long instance, double value);

    public native static double jni_GetRotateY(long instance);
    
    public native static void jni_SetRotateZ(long instance, double value);

    public native static double jni_GetRotateZ(long instance);

    public native static void jni_SetRotateY(long instance, double value);

    public native static double jni_GetScaleDifference(long instance);

    public native static void jni_SetScaleDifference(long instance,
            double value);

}
