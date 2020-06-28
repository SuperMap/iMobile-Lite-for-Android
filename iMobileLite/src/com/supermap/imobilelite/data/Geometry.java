package com.supermap.imobilelite.data;

import java.lang.*;
import java.util.ArrayList;
import java.util.Vector;


/**
 * <p>
 * imobile移植类
 * </p>
 */

public abstract class Geometry extends InternalHandleDisposable {
	private GeoStyle m_style = null;
	private boolean m_setStyle = false;
	transient static Vector m_customGeometryCreateListeners;


	protected Geometry() {
	}


	public Rectangle2D getBounds() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBounds()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		double[] bounds = new double[4];
		GeometryNative.jni_GetBounds(getHandle(), bounds);

		Rectangle2D rcBounds = new Rectangle2D(bounds[0], bounds[1], bounds[2],
				bounds[3]);
		return rcBounds;
	}

	
	public Point2D getInnerPoint() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getInnerPoint()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		double[] params = new double[2];
		GeometryNative.jni_GetInnerPoint(getHandle(), params);
		Point2D point = new Point2D(params[0], params[1]);
		return point;
	}


	public Point2D getHandles(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getInnerPoint()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		double[] params = new double[2];
		GeometryNative.jni_GetHandles(getHandle(), index, params);
		Point2D point = new Point2D(params[0], params[1]);
		return point;
	}
	

	public int getHandleCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getInnerPoint()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		int handleCount = GeometryNative.jni_GetHandleCount(getHandle());
		return handleCount;
	}
	
	
	public int getID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getID()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return GeometryNative.jni_GetID(getHandle());
	}

	
	public boolean isEmpty() {
		return false;
	}


	public GeoStyle getStyle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getStyle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.m_style == null) {
			long stylePtr = GeometryNative.jni_GetStyle(getHandle());
			if (stylePtr != 0) {
				this.m_style = GeoStyle.createInstance(stylePtr);
			}
		}
		return this.m_style;
	}


	public void setStyle(GeoStyle value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setStyle(GeoStyle value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeometryType type = getType();

		if (type == GeometryType.GEOTEXT) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoTextUnsupprotStyle,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}

		if (value == null) {
			if (m_style != null) {
				m_style.clearHandle();
				m_style = null;
			}
			GeometryNative.jni_SetStyle(getHandle(), 0);
		} else {
			if (value.getHandle() == 0) {
				String message = InternalResource.loadString("value",
						InternalResource.GlobalArgumentObjectHasBeenDisposed,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}

			GeoStyle style = (GeoStyle) value.clone();
			GeometryNative.jni_SetStyle(getHandle(), style.getHandle());
			style.dispose();
		}
		this.m_setStyle = true;
	}


	public GeometryType getType() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getType()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		int ugcType = GeometryNative.jni_GetType(getHandle());
		try {
			return (GeometryType) Enum.parseUGCValue(GeometryType.class, ugcType);
		} catch (Exception e) {
			return new GeometryType(ugcType, ugcType);
		}
	}


	public abstract Geometry clone();


	public boolean hitTest(Point2D point, double tolerance) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"hitTest(Point2D point, double tolerance)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (tolerance < 0) {
			String message = InternalResource.loadString("tolerance",
					InternalResource.GeometryInvalidTolerance,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return GeometryNative.jni_HitTest(getHandle(), point.getX(), point
				.getY(), tolerance);
	}


	public void offset(double dx, double dy) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"offset(double dx, double dy)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeometryNative.jni_Offset(getHandle(), dx, dy);
	}


	public void resize(Rectangle2D bounds) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"resize(Rectangle2D bounds)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (this.getType() == GeometryType.GEOREGION) {
			if (bounds.getWidth() == 0) {
				String message = InternalResource.loadString("bounds",
						InternalResource.GeometryResizeBoundsWidthIsZero,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
			if (bounds.getHeight() == 0) {
				String message = InternalResource.loadString("bounds",
						InternalResource.GeometryResizeBoundsHeightIsZero,
						InternalResource.BundleName);
				throw new IllegalArgumentException(message);
			}
		}
		GeometryNative.jni_Resize(getHandle(), bounds.getLeft(), bounds
				.getTop(), bounds.getRight(), bounds.getBottom());
	
		if (this.getType() == GeometryType.GEOTEXT) {
			GeoText geoText = (GeoText) this;
			double width = geoText.getTextStyle().getFontWidth();
			double height = geoText.getTextStyle().getFontHeight();
			if (width < 0) {
				geoText.getTextStyle().setFontWidth(Math.abs(width));
			}
			if (height < 0) {
				geoText.getTextStyle().setFontHeight(Math.abs(height));
			}
		}
		else if (this.getType() == GeometryType.GEOREGION)
		{
			GeoRegion geoRegion = (GeoRegion) this;
			geoRegion.refrashPartsList();
		}
	}


	public void rotate(Point2D basePoint, double angle) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"rotate(Point2D basePoint, double angle)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeometryNative.jni_Rotate(getHandle(), basePoint.getX(), basePoint
				.getY(), angle);
	}


	private void setEmpty() {

	}

	static final Geometry createInstance(long geoHandle) {
		if (geoHandle == 0) {
			return null;
		}
		Geometry geo = null;
		int ugcType = GeometryNative.jni_GetType(geoHandle);
		int[] values = Enum.getValues(GeometryType.class);
		boolean isOtherType = true;
		for (int i = 0; i < values.length; i++) {
			if (ugcType == values[i]) {
				isOtherType = false;
				break;
			}
		}

		if (geo != null) {
		
			geo.setIsDisposable(true);
		}
		return geo;
	}
	

	public abstract void dispose();

	protected void clearHandle() {
		if (m_style != null) {
			m_style.clearHandle();
			m_style = null;
		}
		this.setHandle(0);
	}

	
	protected static void clearHandle(Geometry geometry) {
		geometry.clearHandle();
	}

	static final Geometry createInstance(GeometryType geoType) {
		Geometry geometry = null;
		
		if (geoType.equals(GeometryType.GEOPOINT)) {
		} else if (geoType.equals(GeometryType.GEOLINE)) {
			geometry = new GeoLine();
		} else if (geoType.equals(GeometryType.GEOREGION)) {
			geometry = new GeoRegion();
		} else if (geoType.equals(GeometryType.GEOTEXT)) {
			geometry = new GeoText();
		} else {
			throw new RuntimeException(".");
		}
		
		
		geometry.setIsDisposable(true);
		return geometry;
	}

	
	
	public boolean fromXML(String xml) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("fromXML(String xml)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean result = false;
		if (xml != null && xml.trim().length() != 0) {
			result = GeometryNative.jni_FromXML(getHandle(), xml);
		}
		return result;
	}

	public String toXML() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("toXML()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeometryNative.jni_ToXML(getHandle());
	}
	
	protected static final Geometry internalCreateInstance(long geoHandle) {
		return Geometry.createInstance(geoHandle);
	}
	
	public String toJson(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		sb.append(" \"id\" : " + this.getID() + ",");
		sb.append(" \"center\" :{ " + "\"x\" :" + this.getInnerPoint().getX() + ", \"y\" : "+ this.getInnerPoint().getY() + "}" + ",");
		GeoStyle style = this.getStyle();
		String strStyle = "null";
		if(style != null){
			strStyle = style.toJson();
		}
		sb.append(" \"style\" :" + strStyle);
		
		sb.append("}");
		return sb.toString();
	}
	
	public boolean fromJson(String json){
		SiJsonObject jsonObj = new SiJsonObject(json);
		
		boolean ret =  fromJson(jsonObj);
		jsonObj.dispose();
		return ret;
	}
	
	public boolean fromJson(SiJsonObject jsonObj){ 
		return true;
	}
	

	public String toGeoJSON(){
		return null;
	}
	
	
	public boolean fromGeoJSON(String geoJSON){
		return false;
	}
	
	
	protected  ArrayList<Point2Ds> getPointsFromGeoJSON(final String geoJSON) {

		final String coordinates = geoJSON.substring(geoJSON.indexOf("["), geoJSON.lastIndexOf("]") + 1).replace(" ", "");
		char c = 0;
		ArrayList<Point2Ds> ptsList = new ArrayList<Point2Ds>();
		Point2Ds pts = new Point2Ds();
		Point2D pt = new Point2D(0,0);
		StringBuilder value = new StringBuilder();
		
		String markerStr = null; 
		for (int i = 0, length = coordinates.length(); i < length; i++) {
			c = coordinates.charAt(i);

			switch (c) {
			case ' ':     
				break;
			case ',':    
				if (value.length() > 0) {
					pt.setX(Double.parseDouble(value.toString()));
					value.delete(0, value.length());
				}
				markerStr += ",";
				if(checkLineEnd(markerStr)){
					ptsList.add(pts);                        
					pts = new Point2Ds();                    
				}
				markerStr = "";                              
				break;
			case '[':    
				break;
			case ']':     
				if (value.length() > 0) {
					pt.setY(Double.parseDouble(value.toString()));
					value.delete(0, value.length());         
					pts.add(pt);                            
					pt.setX(0);
					pt.setY(0);
				}
				markerStr += "]";
				break;

			default:
				value.append(c);
				break;
			}

		}
		ptsList.add(pts);                                   
		return ptsList;
	}
	
	
	private boolean checkLineEnd(String markerStr) {
		if(markerStr != null){
			if(markerStr.equals("]],")){
				return true;
			}else{
				return false;
			}
		}else {
			return false;
		}
	}
}
