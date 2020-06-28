/**
 * 
 */
package com.supermap.imobilelite.data;

/**
 * @作者 konglingliang
 * 
 */
class GeoRegion3DNative {
	private GeoRegion3DNative() {
	}

	public native static long jni_New();

	public native static void jni_Delete(long instance);

	public native static long jni_Clone(long instance);

	public native static double jni_GetArea(long instance);

	public native static int jni_GetPartCount(long instance);

	public native static double jni_GetPerimeter(long instance);

	public native static int jni_AddPart(long instance, double[] xs,
			double[] ys, double[] zs);

	public native static long jni_ConvertToLine(long instance);

	public native static void jni_GetPart(long instance, int index,
			double[] xs, double[] ys, double[] zs);

	public native static boolean jni_InsertPart(long instance, int index,
			double[] xs, double[] ys, double[] zs);

	public native static boolean jni_RemovePart(long instance, int index);

	public native static boolean jni_SetPart(long instance, int index,
			double[] xs, double[] ys, double[] zs);

	public native static int jni_GetPartPointCount(long instance, int index);

	public native static void jni_Clear(long instance);
}
