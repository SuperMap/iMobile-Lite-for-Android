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
public class GeoDatum extends InternalHandleDisposable {

    private GeoSpheroid m_geoSpheroid = null;

    public GeoDatum() {
        long handle = GeoDatumNative.jni_New();
        this.setHandle(handle, true);
    }

    public GeoDatum(GeoDatumType type) {
        if (type == null) {
            String message = InternalResource.loadString("type",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        int ugcValue = Enum.internalGetUGCValue(type);
        long handle = GeoDatumNative.jni_New2(ugcValue);
        this.setHandle(handle, true);
    }

    public GeoDatum(GeoSpheroid geoSpheroid, String name) {
        if (geoSpheroid == null || geoSpheroid.getHandle() == 0) {
            String message = InternalResource.loadString("geoSpheroid",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        if(name == null || name.trim().length() == 0){
            String message = InternalResource.loadString("name",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        long handle = GeoDatumNative.jni_New3(geoSpheroid.getHandle(), name);
        this.setHandle(handle, true);
    }

    public GeoDatum(GeoDatum geoDatum) {
        if (geoDatum == null || geoDatum.getHandle() == 0) {
            String message = InternalResource.loadString("geoDatum",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoDatumNative.jni_Clone(geoDatum.getHandle());
        this.setHandle(handle, true);
    }

    GeoDatum(long handle, boolean disposable) {
        this.setHandle(handle, disposable);
    }


    public GeoDatum clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new GeoDatum(this);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            GeoDatumNative.jni_Delete(getHandle());
            this.setHandle(0);
            clearHandle();
        }
    }

    protected void clearHandle(){
        if(m_geoSpheroid != null){
            m_geoSpheroid.clearHandle();
            m_geoSpheroid = null;
        }
    }

    public String getName() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getName()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoDatumNative.jni_GetName(getHandle());
    }

    public void setName(String value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setName(String value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null || value.trim().length() == 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        GeoDatumNative.jni_SetName(getHandle(), value);
    }

    public GeoSpheroid getGeoSpheroid() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGeoSpheroid()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (m_geoSpheroid == null || m_geoSpheroid.getHandle() == 0) {
            long ugcHandle = GeoDatumNative.jni_GetGeoSpheroid(this.getHandle());
            if (ugcHandle != 0) {
                m_geoSpheroid = new GeoSpheroid(ugcHandle, false);
            }
        }
        return m_geoSpheroid;
    }

    public void setGeoSpheroid(GeoSpheroid value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setGeoSpheroid(GeoSpheroid value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.getType() != GeoDatumType.DATUM_USER_DEFINED) {
            String message = InternalResource.loadString(
                    "setGeoSpheroid(GeoSpheroid value)",
                    InternalResource.IfTypeNotUserDefinedCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (value == null || value.getHandle() == 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        GeoDatumNative.jni_SetGeoSpheroid(this.getHandle(),
                                          value.getHandle());
    }

    public GeoDatumType getType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getType()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = GeoDatumNative.jni_GetType(getHandle());
        return (GeoDatumType) Enum.parseUGCValue(
                GeoDatumType.class,
                ugcValue);
    }

    public void setType(GeoDatumType value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setType(GeoDatumType value)",
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
        GeoDatumNative.jni_SetType(getHandle(), ugcValue);
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
            result = GeoDatumNative.jni_FromXML(getHandle(), xml);
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
        return GeoDatumNative.jni_ToXML(getHandle());
    }


}
