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
class GeoStyleNative {
    private GeoStyleNative() {
    }

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static int jni_GetFillBackColor(long instance);

    public native static void jni_SetFillBackColor(long instance, int value);

    public native static boolean jni_GetIsBackTransparent(long instance);

    public native static void jni_SetIsBackTransparent(long instance,
            boolean value);

    public native static int jni_GetFillForeColor(long instance);

    public native static void jni_SetFillForeColor(long instance, int value);

    public native static double jni_GetFillAngle(long instance);

    public native static void jni_SetFillAngle(long instance, double value);

    public native static double jni_GetFillCenterOffsetX(long instance);

    public native static void jni_SetFillCenterOffsetX(long instance,
            double value);

    public native static double jni_GetFillCenterOffsetY(long instance);

    public native static void jni_SetFillCenterOffsetY(long instance,
            double value);

    public native static int jni_GetFillGradientType(long instance);

    public native static void jni_SetFillGradientType(long instance, int value);

    public native static int jni_GetFillOpaqueRate(long instance);

    public native static void jni_SetFillOpaqueRate(long instance, int value);

    public native static int jni_GetFillStyle(long instance);

    public native static void jni_SetFillStyle(long instance, int value);

    public native static int jni_GetLineColor(long instance);

    public native static void jni_SetLineColor(long instance, int value);

    public native static int jni_GetLineStyle(long instance);

    public native static void jni_SetLineStyle(long instance, int value);

    public native static double jni_GetLineWidth(long instance);

    public native static void jni_SetLineWidth(long instance, double value);


    public native static double jni_GetMarkerAngle(long instance);

    public native static void jni_SetMarkerAngle(long instance, double value);

    public native static void jni_GetMarkerSize(long instance,double []values);

    public native static void jni_SetMarkerSize(long instance, double width,double height);

    public native static int jni_GetMarkerStyle(long instance);

    public native static void jni_SetMarkerStyle(long instance, int value);

    public native static long jni_Clone(long instance);

    public native static void jni_Reset(long insance);
    
    public native static long jni_GetSymbolMarker(long instance);
    
    public native static void jni_SetSymbolMarker(long instance, long handle);
    
    public native static long jni_GetSymbolLine(long instance);
    
    public native static void jni_SetSymbolLine(long instance, long handle);
    
    public native static long jni_GetSymbolFill(long instance);
    
    public native static void jni_SetSymbolFill(long instance, long handle);
    
    public native static boolean jni_FromXML(long instance, String xml);
    
    public native static String jni_ToXML(long instance);

}
