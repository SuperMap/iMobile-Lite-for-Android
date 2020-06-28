package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class GeoSpheroid extends InternalHandleDisposable {
    public GeoSpheroid() {
        long handle = GeoSpheroidNative.jni_New();
        this.setHandle(handle, true);
    }

    public GeoSpheroid(GeoSpheroidType type) {
        if (type == null) {
            String message = InternalResource.loadString("type",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        int ugcValue = Enum.internalGetUGCValue(type);
        long handle = GeoSpheroidNative.jni_New2(ugcValue);
        this.setHandle(handle, true);
    }

    public GeoSpheroid(double axis,double flatten,String name){

        if (name == null || name.trim().length() == 0) {
            String message = InternalResource.loadString("name",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        long handle = GeoSpheroidNative.jni_New3(axis,flatten,name);
        this.setHandle(handle, true);
    }

    public GeoSpheroid(GeoSpheroid geoSpheroid) {
        if (geoSpheroid == null) {
            String message = InternalResource.loadString("geoSpheroid",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (geoSpheroid.getHandle() == 0) {
            String message = InternalResource.loadString("geoSpheroid",
                    InternalResource.GlobalArgumentObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoSpheroidNative.jni_Clone(geoSpheroid.getHandle());
        this.setHandle(handle, true);
    }

    GeoSpheroid(long handle, boolean disposable) {
        this.setHandle(handle, disposable);
    }
    public GeoSpheroid clone(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new GeoSpheroid(this);
    }
    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            GeoSpheroidNative.jni_Delete(getHandle());
            this.setHandle(0);
            clearHandle();
        }
    }

    public double getAxis() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getAxis()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        double axis = GeoSpheroidNative.jni_GetAxis(getHandle());
        return axis;
    }

    public void setAxis(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setAxis(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(this.getType() != GeoSpheroidType.SPHEROID_USER_DEFINED){
            String message = InternalResource.loadString(
                    "setAxis(double value)",
                    InternalResource.IfTypeNotUserDefinedCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        GeoSpheroidNative.jni_SetAxis(getHandle(), value);
    }

    public double getFlatten() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getFlatten()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        double flatten = GeoSpheroidNative.jni_GetFlatten(getHandle());
        return flatten;
    }

    public void setFlatten(double value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setFlatten(double value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(this.getType() != GeoSpheroidType.SPHEROID_USER_DEFINED){
            String message = InternalResource.loadString(
                    "setFlatten(double value)",
                    InternalResource.IfTypeNotUserDefinedCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        GeoSpheroidNative.jni_SetFlatten(getHandle(), value);
    }

    public String getName() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getName()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoSpheroidNative.jni_GetName(getHandle());
    }

    public void setName(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setName(String value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(value == null || value.trim().length() == 0){
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        GeoSpheroidNative.jni_SetName(getHandle(), value);
    }

    public GeoSpheroidType getType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getType()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = GeoSpheroidNative.jni_GetType(getHandle());
        return (GeoSpheroidType) Enum.parseUGCValue(
                GeoSpheroidType.class,
                ugcValue);
    }

    public void setType(GeoSpheroidType value){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setType(GeoSpheroidType value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
           String message = InternalResource.loadString("value",
                   InternalResource.GlobalArgumentNull,
                   InternalResource.BundleName);
           throw new IllegalArgumentException(message);
       }
       int ugcValue = Enum.internalGetUGCValue(value);
       GeoSpheroidNative.jni_SetType(getHandle(), ugcValue);
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
            result = GeoSpheroidNative.jni_FromXML(getHandle(), xml);
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
        return GeoSpheroidNative.jni_ToXML(getHandle());
    }
}
