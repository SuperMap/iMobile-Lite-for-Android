package com.supermap.imobilelite.data;


import java.lang.*;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class TextStyle extends InternalHandleDisposable {

	
	public TextStyle() {
		long handle = TextStyleNative.jni_New();
		this.setHandle(handle, true);
		reset();
	}

	

	public TextStyle(TextStyle textStyle) {
		if (textStyle.getHandle() == 0) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = TextStyleNative.jni_Clone(textStyle.getHandle());
		this.setHandle(handle, true);
	}


	TextStyle(long handle) {
		this.setHandle(handle, false);
	}


	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			TextStyleNative.jni_Delete(getHandle());
			this.setHandle(0);
		}
	}



	public Color getBackColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBackColor()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int rgb = TextStyleNative.jni_GetBackColor(getHandle());
		return new Color(rgb);
	}


	public void setBackColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setBackColor(Color value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetBackColor(getHandle(), value.getRGB());
	}

	public boolean isBold() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBold()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetBold(getHandle());
	}

	public void setBold(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setBold(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetBold(getHandle(), value);
	}


	public Color getForeColor() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getForeColor()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int rgb = TextStyleNative.jni_GetForeColor(getHandle());
		return new Color(rgb);
	}


	public void setForeColor(Color value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setForeColor(Color value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetForeColor(getHandle(), value.getRGB());
	}


	public boolean isSizeFixed() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getIsSizeFixed()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetIsSizeFixed(getHandle());
	}


	public void setSizeFixed(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setIsSizeFixed(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetSizeFixed(getHandle(), value);
	}


	public double getFontHeight() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getFontHeight()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetFontHeight(getHandle());
	}


	public void setFontHeight(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setFontHeight(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value <= 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.TextStyleTheValueOfFontHeightShouldBePositive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		TextStyleNative.jni_SetFontHeight(getHandle(), value);
	}


	public double getFontWidth() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getFontWidth()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetFontWidth(getHandle());
	}


	public void setFontWidth(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setFontWidth(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value < 0) {
			String message = InternalResource
					.loadString(
							"value",
							InternalResource.TextStyleTheValueOfFontWidthShouldBePositive,
							InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		TextStyleNative.jni_SetFontWidth(getHandle(), value);
	}


	public String getFontName() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getFontName()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetFontName(getHandle());
	}


	public void setFontName(String value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setFontName(String value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (value == null) {
			value = "";
		}
		TextStyleNative.jni_SetFontName(getHandle(), value);
	}


	public boolean getItalic() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getItalic()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetItalic(getHandle());
	}


	public void setItalic(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setItalic(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetItalic(getHandle(), value);
	}


	public double getItalicAngle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getItalicAngle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetItalicAngle(getHandle());
	}


	public void setItalicAngle(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setItalicAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetItalicAngle(getHandle(), value);
	}
	

	public void setBackTransparency(int value){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setItalicAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
			
		}
		if(value >=0 && value<=255){
			TextStyleNative.jni_SetBackTransparency(getHandle(), value);
		}
	}
	
	
	public int getBackTransparency(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setItalicAngle(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetBackTransparency(getHandle());
	}

	
	public boolean getOutline() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getOutline()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetOutline(getHandle());
	}


	public TextStyle clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new TextStyle(this);
	}
	
	
	public void setOutline(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setOutline(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetOutline(getHandle(), value);
	}


	public double getRotation() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRotation()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetRotation(getHandle());
	}

	
	public void setRotation(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setRotation(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetRotation(getHandle(), value);
	}

	
	public boolean getShadow() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getShadow()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetShadow(getHandle());
	}


	public void setShadow(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setShadow(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetShadow(getHandle(), value);
	}


	public boolean getStrikeout() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getStrikeout()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetStrikeout(getHandle());
	}

	
	public void setStrikeout(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setStrikeout(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetStrikeout(getHandle(), value);
	}


	public boolean isBackOpaque() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getBackOpaque()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetBackOpaque(getHandle());
	}


	public void setBackOpaque(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setBackOpaque(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetBackOpaque(getHandle(), value);
	}


	public boolean getUnderline() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getUnderline()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetUnderLine(getHandle());
	}


	public void setUnderline(boolean value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setUnderline(boolean value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetUnderLine(getHandle(), value);
	}


	@Deprecated
	public int getWeight() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getWeight()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetWeight(getHandle());
	}


	@Deprecated
	public void setWeight(int value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setWeight(int value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_setWeight(getHandle(), value);
	}
	
  

	public String toString() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("toString()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("{Alignment = ");
		buffer.append(",BackColor = ");
		buffer.append(this.getBackColor().toString());
		buffer.append(",BackOpaque = ");
		buffer.append(this.isBackOpaque());
		buffer.append(",Bold = ");
		buffer.append(this.isBold());
		buffer.append(",FontName = ");
		buffer.append(this.getFontName());
		buffer.append(",FontHeight = ");
		buffer.append(this.getFontHeight());
		buffer.append(",FontWidth = ");
		buffer.append(this.getFontWidth());
		buffer.append(",FontColor = ");
		buffer.append(this.getForeColor().toString());
		buffer.append(",IsSizeFixed = ");
		buffer.append(this.isSizeFixed());
		buffer.append(",Italic = ");
		buffer.append(this.getItalic());
		buffer.append(",Outline = ");
		buffer.append(this.getOutline());
		buffer.append(",Rotation = ");
		buffer.append(this.getRotation());
		buffer.append(",Shadow = ");
		buffer.append(this.getShadow());
		buffer.append(",Strikeout = ");
		buffer.append(this.getStrikeout());
		buffer.append(",Underline = ");
		buffer.append(this.getUnderline());
		buffer.append(",Weight = ");
		buffer.append(this.getWeight());
		buffer.append("}\n");
		return buffer.toString();
	}


	protected static TextStyle createInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new TextStyle(handle);
	}

	protected void clearHandle() {
		setHandle(0);
	}

	
	protected static void changeHandle(TextStyle style, long handle) {
		style.changeHandle(handle);
	}

	protected static void clearHandle(TextStyle style) {
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
			TextStyleNative.jni_Delete(getHandle());
		}
		this.setHandle(handle, false);
	}

	protected static void refreshHandle(TextStyle style, long handle) {
		style.refreshHandle(handle);
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

	void reset() {
		TextStyleNative.jni_Reset(getHandle());
	}
	
	
	public double getFontScale() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getFontScale()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return TextStyleNative.jni_GetFontScale(getHandle());
	}

	
	public void setFontScale(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setFontScale(double value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		TextStyleNative.jni_SetFontScale(getHandle(), value);
	}
	
}