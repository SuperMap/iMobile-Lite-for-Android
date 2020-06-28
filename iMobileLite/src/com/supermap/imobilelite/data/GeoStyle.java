package com.supermap.imobilelite.data;

import java.lang.*;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class GeoStyle extends InternalHandleDisposable {

	public GeoStyle() {
		super.setHandle(GeoStyleNative.jni_New(), true);
		reset();
	}


	public GeoStyle(GeoStyle style) {
		if (style.getHandle() == 0) {
			String message = InternalResource.loadString("style",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoStyleNative.jni_Clone(style.getHandle());
		super.setHandle(handle, true);
	}

	GeoStyle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		setHandle(handle, false);
	}
	public Color getFillBackColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Color color = new Color(GeoStyleNative
				.jni_GetFillBackColor(getHandle()));
		return color;
	}


	public void setFillBackColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetFillBackColor(getHandle(), value.getRGB());
	}

	
	public boolean getFillBackOpaque() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetIsBackTransparent(getHandle());
	}


	public void setFillBackOpaque(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetIsBackTransparent(getHandle(), value);
	}


	public Color getFillForeColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Color color = new Color(GeoStyleNative
				.jni_GetFillForeColor(getHandle()));
		return color;
	}


	public void setFillForeColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetFillForeColor(getHandle(), value.getRGB());
	}


	public double getFillGradientAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return GeoStyleNative.jni_GetFillAngle(getHandle());
	}


	public void setFillGradientAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeoStyleNative.jni_SetFillAngle(getHandle(), value);
	}

	
	public double getFillGradientOffsetRatioX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return GeoStyleNative.jni_GetFillCenterOffsetX(getHandle());
	}


	public void setFillGradientOffsetRatioX(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		GeoStyleNative.jni_SetFillCenterOffsetX(getHandle(), value);
	}


	public double getFillGradientOffsetRatioY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetFillCenterOffsetY(getHandle());
	}


	public void setFillGradientOffsetRatioY(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetFillCenterOffsetY(getHandle(), value);
	}


	public FillGradientMode getFillGradientMode() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int ugType = GeoStyleNative.jni_GetFillGradientType(getHandle());
		return (FillGradientMode) Enum.parseUGCValue(FillGradientMode.class,
				ugType);
	}

	
	public void setFillGradientMode(FillGradientMode fillGradientMode) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (fillGradientMode == null) {
			String message = InternalResource.loadString("fillGradientMode",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int ugType = fillGradientMode.getUGCValue();
		GeoStyleNative.jni_SetFillGradientType(getHandle(), ugType);
	}

	public int getFillOpaqueRate() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetFillOpaqueRate(getHandle());
	}


	public void setFillOpaqueRate(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		value = value < 0 ? 0 : value;
		value = value > 100 ? 100 : value;
		GeoStyleNative.jni_SetFillOpaqueRate(getHandle(), value);
	}


	public int getFillSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetFillStyle(getHandle());
	}


	public void setFillSymbolID(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyleTheValueOfSymbolIDShouldNotBeNegative,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyleNative.jni_SetFillStyle(getHandle(), value);
	}

	public Color getPointColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Color color = new Color(GeoStyleNative.jni_GetLineColor(getHandle()));
		return color;
	}


	public void setPointColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetLineColor(getHandle(), value.getRGB());
	}


	public Color getLineColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Color color = new Color(GeoStyleNative.jni_GetLineColor(getHandle()));
		return color;
	}


	public void setLineColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetLineColor(getHandle(), value.getRGB());
	}


	public int getLineSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetLineStyle(getHandle());
	}


	public void setLineSymbolID(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyleTheValueOfSymbolIDShouldNotBeNegative,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		GeoStyleNative.jni_SetLineStyle(getHandle(), value);
	}


	public double getLineWidth() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetLineWidth(getHandle());
	}


	public void setLineWidth(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyleArgumentOfLineWidthShouldBePositive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyleNative.jni_SetLineWidth(getHandle(), value);
	}


	public double getMarkerAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetMarkerAngle(getHandle());
	}


	public void setMarkerAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetMarkerAngle(getHandle(), value);
	}


	public Size2D getMarkerSize() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		double[] params = new double[2];
		GeoStyleNative.jni_GetMarkerSize(getHandle(), params);
		if (params[0] < 0 || params[1] < 0) {
			params[0] = 0;
			params[1] = 0;
		}
		return new Size2D(params[0], params[1]);
	}


	public void setMarkerSize(Size2D value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value.getWidth() < 0 && value.getHeight() < 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GeoStyleTheValueOfMarkerSizeIsNotValid,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoStyleNative.jni_SetMarkerSize(getHandle(), value.getWidth(), value
				.getHeight());
	}


	public int getMarkerSymbolID() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoStyleNative.jni_GetMarkerStyle(getHandle());
	}


	public void setMarkerSymbolID(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.GeoStyleTheValueOfSymbolIDShouldNotBeNegative,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoStyleNative.jni_SetMarkerStyle(getHandle(), value);
	}

	
	public GeoStyle clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoStyle(this);
	}

	
	public void dispose() {

		if (!super.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (super.getHandle() != 0) {
			GeoStyleNative.jni_Delete(super.getHandle());
			setHandle(0);
		}
	}

	public String toString() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("toString()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("{FillBackColor = ");
		buffer.append(this.getFillBackColor().toString());
		buffer.append(",FillForeColor = ");
		buffer.append(this.getFillForeColor().toString());
		buffer.append(",FillGradientAngle = ");
		buffer.append(this.getFillGradientAngle());
		buffer.append(",FillGradientOffsetRatioX = ");
		buffer.append(this.getFillGradientOffsetRatioX());
		buffer.append(",FillGradientOffsetRatioY = ");
		buffer.append(this.getFillGradientOffsetRatioY());
		buffer.append(",FillGradientMode = ");
		buffer.append(this.getFillGradientMode().name());
		buffer.append(",FillOpaqueRate = ");
		buffer.append(this.getFillOpaqueRate());
		buffer.append(",FillSymbolID = ");
		buffer.append(this.getFillSymbolID());
		buffer.append(",LineColor = ");
		buffer.append(this.getLineColor().toString());
		buffer.append(",LineSymbolID = ");
		buffer.append(this.getLineSymbolID());
		buffer.append(",LineWidth = ");
		buffer.append(this.getLineWidth());
		buffer.append(",MarkerAngle = ");
		buffer.append(this.getMarkerAngle());
		buffer.append(",MarkerSize = ");
		buffer.append(this.getMarkerSize());
		buffer.append(",MarkerSymbolID = ");
		buffer.append(this.getMarkerSymbolID());
		buffer.append("}\n");
		return buffer.toString();
	}
	
	public boolean fromXML(String xml){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSymbolFill()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		boolean result = false;		
		if (xml != null && xml.trim().length() != 0) {
			result = GeoStyleNative.jni_FromXML(this.getHandle(), xml);
		}
		
		return result;
	}
	
	public String toXML(){
		
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getSymbolFill()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}		
		
		String result = GeoStyleNative.jni_ToXML(this.getHandle());
		
		return result;
	}

	
	protected static GeoStyle createInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new GeoStyle(handle);
	}
	
	protected void clearHandle() {
		setHandle(0);
	}

	protected static void clearHandle(GeoStyle style) {
		style.clearHandle();
	}

	private void changeHandle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if (this.getHandle() != 0) {
			GeoStyleNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
	}

	protected static void changeHandle(GeoStyle style, long handle) {
		style.changeHandle(handle);
	}
	protected static void reset(GeoStyle style) {
		style.reset();
	}

	
	private void refreshHandle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.setHandle(handle, false);
	}
	
	protected static void refreshHandle(GeoStyle style, long handle) {
		style.refreshHandle(handle);
	}
	
	void reset() {
		if (getHandle() != 0) {
			GeoStyleNative.jni_Reset(getHandle());
		}
	}
	
	public String toJson(){
		return "";
	}
	
	public boolean fromJson(String json){
		return false;
	}
}
