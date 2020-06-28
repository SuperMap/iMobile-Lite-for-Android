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
 * @author zhangjinan
 * @version 2.0
 */
public class CoordSysTransParameter extends InternalHandleDisposable {
    public CoordSysTransParameter() {
        long handle = CoordSysTransParameterNative.jni_New();
        this.setHandle(handle, true);
    }

    public CoordSysTransParameter(CoordSysTransParameter coordSysTransParameter) {
        if (coordSysTransParameter == null ||
            coordSysTransParameter.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "coordSysTransParameter",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = CoordSysTransParameterNative.jni_Clone(
                coordSysTransParameter.getHandle());
        this.setHandle(handle, true);
    }

    /**
     * SuperMap �ڲ�ʹ��
     * @param handle
     * @param disposable
     */
    public CoordSysTransParameter(long handle, boolean disposable){
    	this.setHandle(handle, disposable);
    }
    
    public CoordSysTransParameter clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new CoordSysTransParameter(this);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            CoordSysTransParameterNative.jni_Delete(getHandle());
            this.setHandle(0);
            clearHandle();
        }
    }

    public double getTranslateX() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getTranslateX()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return CoordSysTransParameterNative.jni_GetTranslateX(getHandle());
    }

    public void setTranslateX(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setTranslateX(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        CoordSysTransParameterNative.jni_SetTranslateX(getHandle(), value);
    }

    public double getTranslateY() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getTranslateY()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return CoordSysTransParameterNative.jni_GetTranslateY(getHandle());
    }

    public void setTranslateY(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setTranslateY(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        CoordSysTransParameterNative.jni_SetTranslateY(getHandle(), value);
    }

    public double getTranslateZ() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getTranslateZ()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return CoordSysTransParameterNative.jni_GetTranslateZ(getHandle());
    }

    public void setTranslateZ(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setTranslateZ(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        CoordSysTransParameterNative.jni_SetTranslateZ(getHandle(), value);
    }

    public double getRotateX() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getRotateX()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return CoordSysTransParameterNative.jni_GetRotateX(getHandle());
    }

    public void setRotateX(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setRotateX(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        CoordSysTransParameterNative.jni_SetRotateX(getHandle(), value);
    }

    public double getRotateY() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getRotateY()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return CoordSysTransParameterNative.jni_GetRotateY(getHandle());
    }

    public void setRotateY(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setRotateY(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        CoordSysTransParameterNative.jni_SetRotateY(getHandle(), value);
    }

    public double getRotateZ() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getRotateZ()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return CoordSysTransParameterNative.jni_GetRotateZ(getHandle());
    }

    public void setRotateZ(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setRotateZ(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        CoordSysTransParameterNative.jni_SetRotateZ(getHandle(), value);
    }
    
    public double getScaleDifference() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getScaleDifference()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return CoordSysTransParameterNative.jni_GetScaleDifference(getHandle());
    }

    public void setScaleDifference(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setScaleDifference(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        CoordSysTransParameterNative.jni_SetScaleDifference(getHandle(), value);
    }
    public boolean fromXML(String xml) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "fromXML(String xml)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        boolean result = false;
        if(xml != null && xml.trim().length() != 0){
            result = CoordSysTransParameterNative.jni_FromXML(getHandle(), xml);
        }
        return result;
    }

    public String toXML() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "toXML()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return CoordSysTransParameterNative.jni_ToXML(getHandle());
    }
}
