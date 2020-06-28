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
public class PrjCoordSys extends InternalHandleDisposable {

    private GeoCoordSys m_geoCoordSys = null;
    private Projection m_projection = null;
    private PrjParameter m_prjParameter = null;

    public PrjCoordSys() {
        long handle = PrjCoordSysNative.jni_New();
        this.setHandle(handle, true);
        PrjCoordSysNative.jni_Reset(this.getHandle());
    }

    public PrjCoordSys(PrjCoordSysType type){
        if (type == null) {
           String message = InternalResource.loadString("type",
                   InternalResource.GlobalArgumentNull,
                   InternalResource.BundleName);
           throw new IllegalArgumentException(message);
       }
       long handle = PrjCoordSysNative.jni_New2(type.getUGCValue());
       this.setHandle(handle, true);
    }

    public PrjCoordSys(GeoCoordSys geoCoordSys, Projection projection,
                       PrjParameter prjParameter,
                       String name) {
        if (geoCoordSys == null || geoCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("geoCoordSys",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (projection == null || projection.getHandle() == 0) {
            String message = InternalResource.loadString("projection",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (prjParameter == null || prjParameter.getHandle() == 0) {
            String message = InternalResource.loadString("prjParameter",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (name == null || name.trim().length() == 0) {
            String message = InternalResource.loadString("name",
                    InternalResource.GlobalStringIsNullOrEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        long handle = PrjCoordSysNative.jni_New3(geoCoordSys.getHandle(),
                                                 projection.getHandle(),
                                                 prjParameter.getHandle(), name);
        this.setHandle(handle, true);
    }

    public PrjCoordSys(PrjCoordSys prjCoordSys){
        if (prjCoordSys == null || prjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("prjCoordSys",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = PrjCoordSysNative.jni_Clone(prjCoordSys.getHandle());
        this.setHandle(handle, true);
    }

    public PrjCoordSys(long handle, boolean disposable) {
        this.setHandle(handle, disposable);
    }

    public static PrjCoordSys createInstance(long handle,boolean disposable){
        return new PrjCoordSys(handle,disposable);
    }

    public PrjCoordSys clone(){
        if (this.getHandle() == 0) {
           String message = InternalResource.loadString(
                   "clone()",
                   InternalResource.HandleObjectHasBeenDisposed,
                   InternalResource.BundleName);
           throw new IllegalStateException(message);
       }
       return new PrjCoordSys(this);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            PrjCoordSysNative.jni_Delete(getHandle());
            this.setHandle(0);
            clearHandle();
        }
    }

    public void clearHandle(){
        if(m_geoCoordSys != null){
            m_geoCoordSys.clearHandle();
            m_geoCoordSys = null;
        }
        if(m_projection != null){
            m_projection.clearHandle();
            m_projection = null;
        }
        if(m_prjParameter != null){
            m_prjParameter.clearHandle();
            m_prjParameter = null;
        }
        this.setHandle(0);
    }

    protected static void clearHandle(PrjCoordSys prjCoordSys){
        prjCoordSys.clearHandle();
    }


    public String getName() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getName()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return PrjCoordSysNative.jni_GetName(getHandle());
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
        PrjCoordSysNative.jni_SetName(getHandle(), value);
    }

    public PrjCoordSysType getType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getType()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = PrjCoordSysNative.jni_GetType(getHandle());
        return (PrjCoordSysType) Enum.parseUGCValue(
                PrjCoordSysType.class,
                ugcValue);
    }

    public void setType(PrjCoordSysType value) {
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
        PrjCoordSysNative.jni_SetType(getHandle(), value.getUGCValue());
    }

    public GeoCoordSys getGeoCoordSys() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getGeoCoordSys()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (m_geoCoordSys == null || m_geoCoordSys.getHandle() == 0) {
            long ugcHandle = PrjCoordSysNative.jni_GetGeoCoordSys(getHandle());
            if (ugcHandle != 0) {
                m_geoCoordSys = new GeoCoordSys(ugcHandle, false);
            }
        }
        // qianjn
        GeoCoordSys result = null;
        if (this.getType() != PrjCoordSysType.PCS_NON_EARTH)
        {
        	result = m_geoCoordSys;
        }
        return result;
    }

    public void setGeoCoordSys(GeoCoordSys value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setGeoCoordSys(GeoCoordSys value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.getType() != PrjCoordSysType.PCS_USER_DEFINED
        	&& this.getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE) {
            String message = InternalResource.loadString(
                    "setGeoCoordSys(GeoCoordSys value)",
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
        PrjCoordSysNative.jni_SetGeoCoordSys(getHandle(), value.getHandle());
    }
    public Projection getProjection() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getProjection()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (m_projection == null || m_projection.getHandle() == 0) {
            long ugcHandle = PrjCoordSysNative.jni_GetProjection(getHandle());
            if (ugcHandle != 0) {
                m_projection = new Projection(ugcHandle, false);
            }
        }
        
        // qianjn
//        Projection result = null;
//        if (this.getType() != PrjCoordSysType.PCS_NON_EARTH 
//        		&& this.getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE)
//        {
//        	result = m_projection;
//        }
        return m_projection;
    }

    public void setProjection(Projection value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setProjection(Projection value) ",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.getType() != PrjCoordSysType.PCS_USER_DEFINED) {
            String message = InternalResource.loadString(
                    "setProjection(Projection value) ",
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
        PrjCoordSysNative.jni_SetProjection(getHandle(), value.getHandle());
    }

    public PrjParameter getPrjParameter() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getPrjParameter()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (m_prjParameter == null || m_prjParameter.getHandle() == 0) {
            long ugcHandle = PrjCoordSysNative.jni_GetPrjParameter(getHandle());
            if (ugcHandle != 0) {
                m_prjParameter = new PrjParameter(ugcHandle, false);
            }
        }
        
        // qianjn
//        PrjParameter result = null;
//        if (this.getType() != PrjCoordSysType.PCS_NON_EARTH 
//        		&& this.getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE)
//        {
//        	result = m_prjParameter;
//        }
        return m_prjParameter;
    }

    public void setPrjParameter(PrjParameter value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPrjParameter(PrjParameter value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.getType() != PrjCoordSysType.PCS_USER_DEFINED) {
            String message = InternalResource.loadString(
                    "setPrjParameter(PrjParameter value)",
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
        PrjCoordSysNative.jni_SetPrjParameter(getHandle(), value.getHandle());
    }

    public Unit getCoordUnit() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getCoordUnit()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = PrjCoordSysNative.jni_GetCoordUnit(getHandle());
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
        int ugcValue = Enum.internalGetUGCValue(value);
        PrjCoordSysNative.jni_SetCoordUnit(getHandle(), ugcValue);
    }

    public Unit getDistanceUnit() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getDistUnit()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = PrjCoordSysNative.jni_GetDistUnit(getHandle());
        return (Unit) Enum.parseUGCValue(
                Unit.class,
                ugcValue);
    }

    public void setDistanceUnit(Unit value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setDistUnit(Unit value)",
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
        PrjCoordSysNative.jni_SetDistUnit(getHandle(), value.getUGCValue());
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
            result = PrjCoordSysNative.jni_FromXML(getHandle(), xml);
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
        return PrjCoordSysNative.jni_ToXML(getHandle());
    }
    
//	  �ݲ�֧��eps ��  supermap ͶӰ֮���ת��
//    public int toEPSGCode() {
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "toEPSGCode()",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        return PrjCoordSysNative.jni_ToEPSGCode(getHandle());
//    }
//    
//    public boolean fromEPSGCode(int value) {
//        if (this.getHandle() == 0) {
//            String message = InternalResource.loadString(
//                    "fromEPSGCode()",
//                    InternalResource.HandleObjectHasBeenDisposed,
//                    InternalResource.BundleName);
//            throw new IllegalStateException(message);
//        }
//        return PrjCoordSysNative.jni_FromEPSGCode(getHandle(), value);
//    }
    
    public boolean toFile(String path, PrjFileVersion value){
    	if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "toFile(String path, PrjFileVersion value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
    	if (path == null || path.trim().length() == 0) {
			String message = InternalResource.loadString("fileName",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
    	int ugcType = value.getUGCValue();
        return PrjCoordSysNative.jni_ToFile(getHandle(), path, ugcType);
    }
    
    public boolean fromFile(String path, PrjFileType value){
    	if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "fromFile(String path, PrjFileVersion value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
		if (path == null || path.trim().length() == 0) {
			String message = InternalResource.loadString("fileName",
					InternalResource.GlobalStringIsNullOrEmpty,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		int ugcType = value.getUGCValue();
        return PrjCoordSysNative.jni_FromFile(getHandle(), path, ugcType);
    }
    
    
}
