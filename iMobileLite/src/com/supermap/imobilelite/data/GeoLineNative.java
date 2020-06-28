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
 * @作者 jiangwh
 * @version 6.0
 */
class GeoLineNative {
	public native static long jni_New();

    public native static void jni_Delete(long instance);

    public static native int jni_GetPartCount(long instance);

    public static native double jni_GetLength(long instance);

    public static native int jni_AddPart(long instance, double[] xs,
                                         double[] ys);

    public static native boolean jni_RemovePart(long instance, int index);

    public static native void jni_FindPointOnLineByDistance(long instance,
            double distance, double[] point);

    public static native int jni_GetPartPointCount(long instance, int index);

    public static native void jni_GetPart(long instance, int index, double[] xs,
                                          double[] ys);

    public static native boolean jni_InsertPart(long instance, int index,
                                                double[] xs, double[] ys);

    public static native boolean jni_SetPart(long instance, int index,
                                             double[] xs, double[] ys);

    public static native long jni_Clone(long instance);

}
