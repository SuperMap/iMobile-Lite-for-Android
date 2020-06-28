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
class CoordSysTranslatorNative {
    private CoordSysTranslatorNative() {
    }

    public native static boolean jni_Forward(double[] xs, double[] ys,
                                             long prjCoordSys);

    public native static boolean jni_Inverse(double[] xs, double[] ys,
                                             long prjCoordSys);

    

    public native static boolean jni_ConvertPoints(double[] xs, double[] ys,
            long sourcePrjCoordSys,
            long targetPrjCoordSys,
            long coordSysTransParameter,
            int coordSysTransMethod);
    
    public native static boolean jni_ConvertPoint3Ds(double[] xs, double[] ys, double[] zs,
            long sourcePrjCoordSys,
            long targetPrjCoordSys,
            long coordSysTransParameter,
            int coordSysTransMethod);
    
    public native static boolean jni_IsAvailableDatasetName(long targetDatasource, String name);
    
    public native static boolean jni_ConvertGeometry(long geometry,
            long sourcePrjCoordSys,
            long targetPrjCoordSys,
            long coordSysTransParameter,
            int coordSysTransMethod);
}
