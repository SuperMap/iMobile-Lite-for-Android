package com.supermap.imobilelite.data;
//jni类
class GeoCurveNative {
	private GeoCurveNative() {

	}

	public native static long jni_New();

	public native static long jni_Clone(long instance);

	public static native long jni_New2(double[] xs, double[] ys);

	public static native double jni_GetLength(long instance);

	public static native void jni_Delete(long instance);

	public static native void jni_GetControlPoints(long instance, double[] xs,
			double[] ys);

	public static native void jni_SetControlPoints(long instance, double[] xs,
			double[] ys);

	public static native int jni_GetPartPointCount(long instance);

	public static native long jni_ConvertToLine(long instance,
			int pointCountPerSegment);

	public static native boolean jni_IsEmpty(long instance) ;

	public static native void jni_SetEmpty(long instance);

}
