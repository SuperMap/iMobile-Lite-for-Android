package com.supermap.imobilelite.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class GeoText extends Geometry {
	private ArrayList m_textParts = null;

	private TextStyle m_textStyle = null;

	public GeoText() {
		long handle = GeoTextNative.jni_New();
		this.setHandle(handle, true);
		reset();
		m_textParts = new ArrayList();
	}


	public GeoText(GeoText geoText) {
		if (geoText == null) {
			String message = InternalResource.loadString("geoText",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (geoText.getHandle() == 0) {
			String message = InternalResource.loadString("geoText",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		long handle = GeoTextNative.jni_Clone(geoText.getHandle());
		this.setHandle(handle, true);
		m_textParts = new ArrayList();
		int size = geoText.getTextPartsList().size();
		for (int i = 0; i < size; i++) {
			TextPart newPart = new TextPart(this, i);
			m_textParts.add(newPart);
		}
	}


	public GeoText(TextPart part) {
		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoTextNative.jni_New();
		this.setHandle(handle, true);
		reset();
		m_textParts = new ArrayList();


		addPart(part);
	}


	public GeoText(TextPart part, TextStyle textStyle) {
		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (textStyle == null) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (textStyle.getHandle() == 0) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoTextNative.jni_New();
		this.setHandle(handle, true);
		m_textParts = new ArrayList();

		addPart(part);
		setTextStyle(textStyle);
	}

	public boolean isEmpty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getIsEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoTextNative.jni_GetPartCount(getHandle()) == 0;
	}
	

	GeoText(long handle) {
		this.setHandle(handle, false);


		m_textParts = new ArrayList();
		this.refreshTextPartsList();
	}

	protected static GeoText creatInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new GeoText(handle);

	}

	
	public String getText() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getText()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoTextNative.jni_GetContent(getHandle());
	}

	public int getPartCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPartCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoTextNative.jni_GetPartCount(getHandle());
	}


	public TextStyle getTextStyle() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getTextStyle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_textStyle == null) {

			long textStylePtr = GeoTextNative.jni_GetTextStyle(getHandle());
			if (textStylePtr != 0) {
				this.m_textStyle = TextStyle.createInstance(textStylePtr);
			}
		}
		return this.m_textStyle;
	}


	public void setTextStyle(TextStyle textStyle) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setTextStyle(TextStyle textStyle)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (textStyle.getHandle() == 0) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		TextStyle newStyle = (TextStyle) textStyle.clone();
		
		GeoTextNative.jni_SetTextStyle(getHandle(), newStyle.getHandle());

	}


	public int addPart(TextPart part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"addPart(TextPart part)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		int index = GeoTextNative.jni_AddPart(getHandle(), part.getHandle(),
				part.getX(), part.getY());


		TextPart newPart = new TextPart(this, index);


		this.m_textParts.add(newPart);
		return index;
	}

	
	public TextPart getPart(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getPart(int index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		TextPart part = (TextPart) m_textParts.get(index);
		return part;
	}

	
	public boolean insertPart(int index, TextPart part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"insertPart(int index, TextPart part)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		
		if (index < 0 || index > getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}

		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean bResult = false;
		bResult = GeoTextNative.jni_InsertPart(getHandle(), index, part
				.getHandle(), part.getX(), part.getY());
		if (bResult) {
			TextPart newPart = new TextPart(this, index);

			
			m_textParts.add(index, newPart);
		}
		return bResult;
	}


	public boolean removePart(int index) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"removePart(int index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		boolean bResult = GeoTextNative.jni_RemovePart(getHandle(), index);
		if (bResult) {

			m_textParts.remove(index);
		}
		return bResult;
	}


	public boolean setPart(int index, TextPart part) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setPart(int index, TextPart part)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (part == null) {
			String message = InternalResource.loadString("part",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (index < 0 || index >= getPartCount()) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalIndexOutOfBounds,
					InternalResource.BundleName);
			throw new IndexOutOfBoundsException(message);
		}
		if (part.getHandle() == 0) {
			String message = InternalResource.loadString("part",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		boolean bResult = GeoTextNative.jni_SetPart(getHandle(), index, part
				.getHandle(), part.getX(), part.getY());
		if (bResult) {
			TextPart newPart = new TextPart(this, index);

			m_textParts.set(index, newPart);
		}
		return bResult;
	}



	public GeoText clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		return new GeoText(this);
	}


	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoTextNative.jni_Delete(getHandle());
			this.setHandle(0);

			clearHandle();
		}
	}

	protected void clearHandle() {
		super.clearHandle();
		if (m_textParts != null) {
			m_textParts.clear();
			m_textParts = null;
		}
		if (m_textStyle != null) {
			m_textStyle.clearHandle();
			m_textStyle = null;
		}
	}

	ArrayList getTextPartsList() {
		return this.m_textParts;
	}

	void reset() {


		this.getTextStyle().reset();
	}


	private void refreshTextPartsList() {
		m_textParts.clear();
		int count = this.getPartCount();
		for (int i = 0; i < count; i++) {
			TextPart part = new TextPart(this, i);
			m_textParts.add(part);
		}
	}

	@Override
	public boolean fromJson(String json) {
		SiJsonObject obj = new SiJsonObject(json);
		boolean ret = fromJson(obj);
		obj.dispose();
		return ret;
	}
	
	@Override
	public boolean fromJson(SiJsonObject json) {
		if(super.fromJson(json)){
			SiJsonArray points = json.getJsonArray("points");
			SiJsonArray texts = json.getJsonArray("texts");
			
			int count = points.getArraySize();
			for(int i=0;i<count;i++){
				TextPart tp = new TextPart();
				Point2D pt = new Point2D();
				SiJsonObject ptJson = points.getJsonObject(i);
				if(pt.fromJson(ptJson)){
					tp.setAnchorPoint(pt);
					tp.setText(texts.getString(i));
				}
				this.addPart(tp);
				ptJson.dispose();
			}
			points.dispose();
			texts.dispose();
			return true;
		}
		return false;
	}

	@Override
	public String toJson() {
		StringBuilder sb = new StringBuilder(super.toJson());
		sb.deleteCharAt(sb.length()-1);
		sb.append(",");
		
		String parts = "";
		sb.append(" \"parts\": " + "["+ parts+"]" + ",");
		
		sb.append(" \"type\": " + "\"TEXT\"" + ",");
		String points = "";
		int count = this.getPartCount();
		for(int i=0;i<count;i++){
			points += this.getPart(i).getAnchorPoint().toJson();
			if(i != count-1){
				points += ",";
			}
		}
		sb.append(" \"points\" :" + "[" + points + "]" + ",");
		
		String text = "";
		for(int i=0;i<count;i++){
			text += this.getPart(i).getText();
			if(i != count-1){
				points += ",";
			}
		}
		sb.append(" \"texts\" :" + "[" + text + "]");
		
		
		sb.append("}");
		return sb.toString();
	}

}

