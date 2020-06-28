package com.supermap.imobilelite.maps;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author not attributable
 * @version 2.0
 */
class GLCacheFileNative {
    private GLCacheFileNative() {
    }

    public native static long jni_New();

    public native static void jni_Delete(long instatnce);
    
   // public native static String jni_GetCacheFile(long instance);

    public native static boolean jni_FromConfig(long instance, String path);
    
  //  public native static double jni_FindScale(long instance, double value);

    public native static String jni_GetTilePath(long instance, int level, int row, int col);

    public native static String jni_GetMVTTilePath(long instance, int level, int row, int col);

}
