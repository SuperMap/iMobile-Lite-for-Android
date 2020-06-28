package com.supermap.imobilelite.data;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: SuperMap GIS Technologies Inc.
 * </p>
 * 
 * @author ������
 * @version 2.0
 */
class TextStyleNative {
	private TextStyleNative() {
	}

	public native static long jni_New();

	public native static void jni_Delete(long instance);
	
	public native static void jni_SetBackTransparency(long instance,int value);
	
	public native static int jni_GetBackTransparency(long instance);

	public native static long jni_Clone(long instance);

	public native static int jni_GetTextAlignment(long instance);

	public native static void jni_SetTextAlignment(long instance, int value);

	public native static int jni_GetBackColor(long instance);

	public native static void jni_SetBackColor(long instance, int value);

	public native static boolean jni_GetBold(long instance);

	public native static void jni_SetBold(long instance, boolean value);

	public native static int jni_GetForeColor(long instance);

	public native static void jni_SetForeColor(long instance, int value);

	public native static boolean jni_GetIsSizeFixed(long instance);

	public native static void jni_SetSizeFixed(long instance, boolean value);

	public native static double jni_GetFixedTextSize(long instance);

	public native static void jni_SetFixedTextSize(long instance, double value);

	public native static double jni_GetFontHeight(long instance);

	public native static void jni_SetFontHeight(long instance, double value);

	public native static String jni_GetFontName(long instance);

	public native static void jni_SetFontName(long instance, String value);

	public native static double jni_GetFontWidth(long instance);

	public native static void jni_SetFontWidth(long instance, double value);

	public native static boolean jni_GetItalic(long instance);

	public native static void jni_SetItalic(long instance, boolean value);

	public native static double jni_GetItalicAngle(long instance);

	public native static void jni_SetItalicAngle(long instance, double value);

	public native static boolean jni_GetOutline(long instance);

	public native static void jni_SetOutline(long instance, boolean value);

	public native static double jni_GetRotation(long instance);

	public native static void jni_SetRotation(long instance, double value);

	public native static boolean jni_GetShadow(long instance);

	public native static void jni_SetShadow(long instance, boolean value);

	public native static boolean jni_GetStrikeout(long instanc);

	public native static void jni_SetStrikeout(long instance, boolean value);

	public native static boolean jni_GetBackOpaque(long instance);

	public native static void jni_SetBackOpaque(long instance, boolean value);

	public native static boolean jni_GetUnderLine(long instance);

	public native static void jni_SetUnderLine(long instance, boolean value);

	public native static int jni_GetWeight(long instance);

	public native static void jni_setWeight(long instance, int value);

	public native static void jni_Reset(long instance);

	public native static double jni_GetFontScale(long instance);

	public native static void jni_SetFontScale(long instance, double value);
}
