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
public class GeoPrimeMeridian extends InternalHandleDisposable {

    public GeoPrimeMeridian() {
        long handle = GeoPrimeMeridianNative.jni_New();
        this.setHandle(handle, true);
    }

    public GeoPrimeMeridian(GeoPrimeMeridianType type) {
        if (type == null) {
            String message = InternalResource.loadString("type",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        int ugcValue = Enum.internalGetUGCValue(type);
        long handle = GeoPrimeMeridianNative.jni_New2(ugcValue);
        this.setHandle(handle, true);
    }

    public GeoPrimeMeridian(double longitudeValue, String name) {
        if(name == null || name.trim().length() == 0){
            String message = InternalResource.loadString("name",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoPrimeMeridianNative.jni_New3(longitudeValue, name);
        this.setHandle(handle, true);
    }

    public GeoPrimeMeridian(GeoPrimeMeridian geoPrimeMeridian) {
        if (geoPrimeMeridian == null) {
            String message = InternalResource.loadString("geoPrimeMeridian",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (geoPrimeMeridian.getHandle() == 0) {
            String message = InternalResource.loadString("geoPrimeMeridian",
                    InternalResource.GlobalArgumentObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoPrimeMeridianNative.jni_Clone(geoPrimeMeridian.
                getHandle());
        this.setHandle(handle, true);
    }

    GeoPrimeMeridian(long handle, boolean disposable) {
        this.setHandle(handle, disposable);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            GeoPrimeMeridianNative.jni_Delete(getHandle());
            this.setHandle(0);
            clearHandle();
        }
    }

    public GeoPrimeMeridian clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new GeoPrimeMeridian(this);
    }

    public double getLongitudeValue(){
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getLongitudeValue()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoPrimeMeridianNative.jni_GetLongitudeValue(getHandle());
    }

    public void setLongitudeValue(double value){
        if (this.getHandle() == 0) {
           String message = InternalResource.loadString(
                   "setLongitudeValue(double value)",
                   InternalResource.HandleObjectHasBeenDisposed,
                   InternalResource.BundleName);
           throw new IllegalStateException(message);
       }
       if(this.getType() != GeoPrimeMeridianType.PRIMEMERIDIAN_USER_DEFINED){
           String message = InternalResource.loadString(
                   "setLongitudeValue(double value)",
                   InternalResource.IfTypeNotUserDefinedCantSetProperty,
                   InternalResource.BundleName);
           throw new UnsupportedOperationException(message);
        }
       GeoPrimeMeridianNative.jni_SetLongitudeValue(getHandle(),value);
    }

    public String getName() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getName()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoPrimeMeridianNative.jni_GetName(getHandle());
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

        GeoPrimeMeridianNative.jni_SetName(getHandle(), value);
    }

    public GeoPrimeMeridianType getType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getType()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = GeoPrimeMeridianNative.jni_GetType(getHandle());
        return (GeoPrimeMeridianType) Enum.parseUGCValue(
                GeoPrimeMeridianType.class,
                ugcValue);
    }

    public void setType(GeoPrimeMeridianType value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setType(GeoPrimeMeridianType value)",
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
        GeoPrimeMeridianNative.jni_SetType(getHandle(), ugcValue);
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
            result = GeoPrimeMeridianNative.jni_FromXML(getHandle(), xml);
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
        return GeoPrimeMeridianNative.jni_ToXML(getHandle());
    }
}
