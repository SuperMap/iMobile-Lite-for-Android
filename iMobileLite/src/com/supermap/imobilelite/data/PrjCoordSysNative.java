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
class PrjCoordSysNative {
    private PrjCoordSysNative() {
    }

    public native static long jni_New();

    public native static long jni_New2(int type);

    public native static long jni_New3(long geoCoordSys, long projection,
                                       long prjParameter, String name);

    public native static void jni_Reset(long instance);

    public native static void jni_Delete(long instance);

    public native static long jni_Clone(long instance);

    public native static String jni_GetName(long instance);

    public native static void jni_SetName(long instance, String value);

    public native static long jni_GetGeoCoordSys(long instance);

    public native static void jni_SetGeoCoordSys(long instance, long value);

    public native static long jni_GetProjection(long instance);

    public native static void jni_SetProjection(long instance, long value);

    public native static long jni_GetPrjParameter(long instance);

    public native static void jni_SetPrjParameter(long instance, long value);

    public native static int jni_GetCoordUnit(long instance);

    public native static void jni_SetCoordUnit(long instance, int value);

    public native static int jni_GetDistUnit(long instance);

    public native static void jni_SetDistUnit(long instance, int value);

    public native static int jni_GetType(long instance);

    public native static void jni_SetType(long instance, int value);

    public native static boolean jni_FromXML(long instance, String xml);

    public native static String jni_ToXML(long instance);
    
    public native static int jni_ToEPSGCode(long instance);
    
    public native static boolean jni_FromEPSGCode(long instance,int value);
    
    public native static boolean jni_ToFile(long instance, String path, int prjFileVersion);
    
    public native static boolean jni_FromFile(long instance, String path, int prjFileType);
}
