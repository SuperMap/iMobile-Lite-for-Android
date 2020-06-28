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
public class GeoCoordSys extends InternalHandleDisposable {

    private GeoDatum m_geoDatum = null;
    private GeoPrimeMeridian m_geoPrimeMeridian = null;

    public GeoCoordSys() {
        long handle = GeoCoordSysNative.jni_New();
        this.setHandle(handle, true);
        // ����Ĭ��ֵ
        GeoCoordSysNative.jni_Reset(this.getHandle());
    }


    public GeoCoordSys(GeoCoordSysType type, GeoSpatialRefType spatialRefType) {
        if (type == null) {
            String message = InternalResource.loadString("type",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (spatialRefType == null) {
            String message = InternalResource.loadString("spatialRefType",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        int ugcValue = Enum.internalGetUGCValue(type);
        int ugcRefTypeValue = Enum.internalGetUGCValue(spatialRefType);
        long handle = GeoCoordSysNative.jni_New2(ugcValue, ugcRefTypeValue);
        this.setHandle(handle, true);
    }

    public GeoCoordSys(GeoDatum geoDatum, GeoPrimeMeridian geoPrimeMeridian,
                       GeoSpatialRefType spatialRefType, Unit unit, String name) {
        if (geoDatum == null || geoDatum.getHandle() == 0) {
            String message = InternalResource.loadString("geoDatum",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (geoPrimeMeridian == null || geoPrimeMeridian.getHandle() == 0) {
            String message = InternalResource.loadString("geoPrimeMeridian",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (spatialRefType == null) {
            String message = InternalResource.loadString("spatialRefType",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (unit == null) {
            String message = InternalResource.loadString("unit",
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
        long handle = GeoCoordSysNative.jni_New3(geoDatum.getHandle(),
                                                 geoPrimeMeridian.getHandle(),
                                                 spatialRefType.getUGCValue(),
                                                 unit.getUGCValue(), name);
        this.setHandle(handle, true);
    }

    public GeoCoordSys(GeoCoordSys geoCoordSys) {
        if (geoCoordSys == null || geoCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("geoCoordSys",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoCoordSysNative.jni_Clone(geoCoordSys.getHandle());
        this.setHandle(handle, true);
    }

    public GeoCoordSys(long handle, boolean disposable) {
        this.setHandle(handle, disposable);
    }

    public GeoCoordSys clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new GeoCoordSys(this);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            GeoCoordSysNative.jni_Delete(getHandle());
            this.setHandle(0);
            clearHandle();
        }
    }

    protected void clearHandle(){
        if(m_geoDatum != null){
            m_geoDatum.clearHandle();
            m_geoDatum = null;
        }
        if(m_geoPrimeMeridian != null){
            m_geoPrimeMeridian.clearHandle();
            m_geoPrimeMeridian = null;
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
        return GeoCoordSysNative.jni_GetName(getHandle());
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
        GeoCoordSysNative.jni_SetName(getHandle(), value);
    }

    public GeoCoordSysType getType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getType()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = GeoCoordSysNative.jni_GetType(getHandle());
        return (GeoCoordSysType) Enum.parseUGCValue(
                GeoCoordSysType.class,
                ugcValue);
    }

    public void setType(GeoCoordSysType value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setType(GeoCoordSysType value) ",
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
        GeoCoordSysNative.jni_SetType(getHandle(), ugcValue);
    }

    public GeoSpatialRefType getGeoSpatialRefType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGeoSpatialRefType()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = GeoCoordSysNative.jni_GetGeoSpatialRefType(getHandle());
        return (GeoSpatialRefType) Enum.parseUGCValue(
                GeoSpatialRefType.class,
                ugcValue);
    }

    public void setGeoSpatialRefType(GeoSpatialRefType value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setType(GeoSpatialRefType value) ",
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
        int ugcValue = value.getUGCValue();
        GeoCoordSysNative.jni_SetGeoSpatialRefType(getHandle(), ugcValue);
    }

    public GeoDatum getGeoDatum() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGeoDatum()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(this.getGeoSpatialRefType() == GeoSpatialRefType.SPATIALREF_NONEARTH){
            String message = InternalResource.loadString(
                    "getGeoPrimeMeridian()",
                    InternalResource.ThisPropertyNotSupportedIfSpatialRefTypeNoneEarth,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (m_geoDatum == null || m_geoDatum.getHandle() == 0) {
            long ugcHandle = GeoCoordSysNative.jni_GetGeoDatum(getHandle());
            if (ugcHandle != 0) {
                m_geoDatum = new GeoDatum(ugcHandle, false);
            }
        }
        return m_geoDatum;
    }

    public void setGeoDatum(GeoDatum value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setGeoDatum(GeoDatum value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.getType() != GeoCoordSysType.GCS_USER_DEFINE) {
            String message = InternalResource.loadString(
                    "setGeoDatum(GeoDatum value)",
                    InternalResource.IfTypeNotUserDefinedCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if(this.getGeoSpatialRefType() == GeoSpatialRefType.SPATIALREF_NONEARTH){
            String message = InternalResource.loadString(
                    "setGeoDatum(GeoDatum value)",
                    InternalResource.ThisPropertyNotSupportedIfSpatialRefTypeNoneEarth,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (value == null || value.getHandle() == 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        GeoCoordSysNative.jni_SetGeoDatum(getHandle(), value.getHandle());
    }

    public GeoPrimeMeridian getGeoPrimeMeridian() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGeoPrimeMeridian()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if(this.getGeoSpatialRefType() == GeoSpatialRefType.SPATIALREF_NONEARTH){
            String message = InternalResource.loadString(
                    "getGeoPrimeMeridian()",
                    InternalResource.ThisPropertyNotSupportedIfSpatialRefTypeNoneEarth,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (m_geoPrimeMeridian == null || m_geoPrimeMeridian.getHandle() == 0) {
            long ugcHandle = GeoCoordSysNative.jni_GetGeoPrimeMeridian(
                    getHandle());
            if (ugcHandle != 0) {
                m_geoPrimeMeridian = new GeoPrimeMeridian(ugcHandle, false);
            }
        }
        return m_geoPrimeMeridian;
    }

    public void setGeoPrimeMeridian(GeoPrimeMeridian value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setGeoDatum(GeoPrimeMeridian value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.getType() != GeoCoordSysType.GCS_USER_DEFINE) {
            String message = InternalResource.loadString(
                    "setGeoPrimeMeridian(GeoPrimeMeridian value)",
                    InternalResource.IfTypeNotUserDefinedCantSetProperty,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if(this.getGeoSpatialRefType() == GeoSpatialRefType.SPATIALREF_NONEARTH){
            String message = InternalResource.loadString(
                    "getGeoPrimeMeridian()",
                    InternalResource.ThisPropertyNotSupportedIfSpatialRefTypeNoneEarth,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        if (value == null || value.getHandle() == 0) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        GeoCoordSysNative.jni_SetGeoPrimeMeridian(getHandle(), value.getHandle());
    }

    public Unit getCoordUnit() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getCoordUnit()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = GeoCoordSysNative.jni_GetCoordUnit(getHandle());
        return (Unit) Enum.parseUGCValue(
                Unit.class,
                ugcValue);
    }

    public void setCoordUnit(Unit value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setCoordUnit(Unit value)",
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
        GeoCoordSysNative.jni_SetCoordUnit(getHandle(), value.getUGCValue());
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
            result = GeoCoordSysNative.jni_FromXML(getHandle(), xml);
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
        return GeoCoordSysNative.jni_ToXML(getHandle());
    }
}
