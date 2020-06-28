package com.supermap.imobilelite.maps;

import android.content.Context;
import android.view.Surface;

import com.supermap.imobilelite.data.Point2D;
import com.supermap.imobilelite.data.Rectangle2D;

/**
 * Created by wnmng on 2017/5/5.
 */

public class RMGLCanvasNative {
    private  RMGLCanvasNative(){
    }
    public static native long jni_New(RMGLCanvas canvas);
    public static native boolean jni_OnCreate(long instance);
    public static native boolean jni_OnDestroy(long instance);
    public static native void jni_InitSurface(Surface surface, long instance);
    public static native void jni_OnSize(long instance, int nWidth,int nHeight);
    public static native void jni_Render(long instance);
    public static native void jni_Refresh(long instance);
    public static native boolean jni_OnTouchEvent(long instance, int action,int ptCount,int index,int[] x,int[] y);
    public static native void jni_OpenGLVectorCache(long instance, String server);
    public static native void jni_OpenMVTVectorCache(long instance, String server);
    public static native void jni_JniLoading(Context context);
    public static native void jni_RegestBeforMapDrawCallback(RMGLCanvas canvas,long instance);
    public static native void jni_SetViewBounds(double left,double top,double right , double bottom,long instance);
    public static native void jni_SetCenter(long instance,double x, double y);
    public static native double jni_GetCenter(long instance,double[] buffer);
    public static native double jni_GetViewBounds(long instance, double[] params);
    public static native double jni_GetScale(long instance);
    public static native double jni_SetScale(long instance,double scale);
    public static native long jni_GetPrjCoorSys(long instance);
//    public static native void jni_SetSystemVersion();
    public static native void jni_SetScaledDisplayDensity(double value,double value1,long instance);
}
