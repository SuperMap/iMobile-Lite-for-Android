package com.supermap.imobilelite.mapsamples.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.mapsamples.OverlayEventDemo;
import com.supermap.imobilelite.mapsamples.R;

public class MarkerConfigDialog extends Dialog{

	private Context context;
	
	private TextView latitudeTextView;
	
	private TextView longitudeTextView;
	
	private Point2D longTouchGeoPoint;

	public MarkerConfigDialog(Context context) {
		super(context);
		this.context = context;
	}

	public MarkerConfigDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}
	
	public MarkerConfigDialog(Context context, int theme, Point2D longTouchGeoPoint) {
		super(context, theme);
		this.context = context;
		this.longTouchGeoPoint = longTouchGeoPoint;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.markerconfig_dialog);
		
		ImageButton blueImageButton = (ImageButton) this.findViewById(R.id.blue_pin_imgbtn); 
		blueImageButton.setOnClickListener(new ImageButtonClick());
		ImageButton greenImageButton = (ImageButton) this.findViewById(R.id.green_pin_imgbtn); 
		greenImageButton.setOnClickListener(new ImageButtonClick());
		ImageButton redImageButton = (ImageButton) this.findViewById(R.id.red_pin_imgbtn); 
		redImageButton.setOnClickListener(new ImageButtonClick());
		ImageButton yellowImageButton = (ImageButton) this.findViewById(R.id.yellow_pin_imgbtn); 
		yellowImageButton.setOnClickListener(new ImageButtonClick());
		
		Button cancelBtn = (Button) this.findViewById(R.id.markerconfig_cancel);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		latitudeTextView = (TextView) this.findViewById(R.id.latitude_tv);
		longitudeTextView = (TextView) this.findViewById(R.id.longitude_tv);
		if (longTouchGeoPoint != null) {
			latitudeTextView.setText("latitude: " + String.valueOf(longTouchGeoPoint.getY()));
			longitudeTextView.setText("longitude: " + String.valueOf(longTouchGeoPoint.getX()));
		}
	}
	
	/**
	 * 设置长按坐标点
	 * @param geoPoint
	 */
	public void setLongTouchGeoPoint(Point2D geoPoint) {
		longTouchGeoPoint = geoPoint;
		if (latitudeTextView != null && longitudeTextView != null) {
			latitudeTextView.setText("latitude: " + String.valueOf(longTouchGeoPoint.getY()));
			longitudeTextView.setText("longitude: " + String.valueOf(longTouchGeoPoint.getX()));
		}
	}

	class ImageButtonClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (context instanceof OverlayEventDemo) {
				OverlayEventDemo overlayEventDemo = (OverlayEventDemo) context;
				overlayEventDemo.addOverlay(v.getId());
			}
			MarkerConfigDialog.this.dismiss();
		}
	}

}
