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
public class PrjParameter extends InternalHandleDisposable {
    public PrjParameter() {
        long handle = PrjParameterNative.jni_New();
        this.setHandle(handle, true);
    }

    public PrjParameter(PrjParameter prjParameter) {
        if (prjParameter == null || prjParameter.getHandle() == 0) {
            String message = InternalResource.loadString("prjParameter",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = PrjParameterNative.jni_Clone(prjParameter.getHandle());
        this.setHandle(handle, true);
    }

    PrjParameter(long handle, boolean disposable) {
        this.setHandle(handle, disposable);
    }

    public PrjParameter clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new PrjParameter(this);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            PrjParameterNative.jni_Delete(getHandle());
            this.setHandle(0);
            clearHandle();
        }
    }

    public double getFalseEasting() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getFalseEasting()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetFalseEasting(getHandle());
    }

    public void setFalseEasting(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setFalseEasting(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetFalseEasting(getHandle(), value);
    }

    public double getFalseNorthing() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getFalseNorthing()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetFalseNorthing(getHandle());
    }

    public void setFalseNorthing(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setFalseNorthing(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetFalseNorthing(getHandle(), value);
    }

    public double getCentralMeridian() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getCentralMeridian()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetCentralMeridian(getHandle());
    }

    public void setCentralMeridian(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setCentralMeridian(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetCentralMeridian(getHandle(), value);
    }

    public double getCentralParallel() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getCentralParallel()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetCentralParallel(getHandle());
    }

    public void setCentralParallel(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setCentralParallel(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetCentralParallel(getHandle(), value);
    }

    public double getStandardParallel1() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getStandardParallel1()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetStandardParallel1(getHandle());
    }

    public void setStandardParallel1(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setStandardParallel1(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetStandardParallel1(getHandle(), value);
    }

    public double getStandardParallel2() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getStandardParallel2()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetStandardParallel2(getHandle());
    }

    public void setStandardParallel2(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setStandardParallel2(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetStandardParallel2(getHandle(), value);
    }

    public double getScaleFactor() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getScaleFactor()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetScaleFactor(getHandle());
    }

    public void setScaleFactor(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setScaleFactor(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetScaleFactor(getHandle(), value);
    }

    public double getAzimuth() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getAzimuth()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetAzimuth(getHandle());
    }

    public void setAzimuth(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setAzimuth(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetAzimuth(getHandle(), value);
    }

    public double getFirstPointLongitude() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getFirstPointLongitude()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetFirstPointLongitude(getHandle());
    }

    public void setFirstPointLongitude(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setFirstPointLongitude(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetFirstPointLongitude(getHandle(), value);
    }

    public double getSecondPointLongitude() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getSecondPointLongitude()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjParameterNative.jni_GetSecondPointLongitude(getHandle());
    }

    public void setSecondPointLongitude(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setSecondPointLongitude(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        PrjParameterNative.jni_SetSecondPointLongitude(getHandle(), value);
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
            result = PrjParameterNative.jni_FromXML(getHandle(), xml);
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
        return PrjParameterNative.jni_ToXML(getHandle());
    }
}
