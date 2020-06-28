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
 * @作者 Objects Java Team
 * @version 2.0
 */
class TextPartNative {
    private TextPartNative() {
    }

    public native static long jni_New();

    public native static void jni_Delete(long instance);

    public native static double jni_GetRotation(long instance);

    public native static void jni_SetRotation(long instance, double rotation);

    public native static String jni_GetText(long instance);

    public native static void jni_SetText(long instance, String text);

    public native static long jni_Clone(long instance);

    public native static void jni_GetSubAnchor(long geoTextInstance, double pt[],
                                               int index);

    public native static void jni_SetSubAnchor(long geoTextInstance, double x,
                                               double y, int index);
}
