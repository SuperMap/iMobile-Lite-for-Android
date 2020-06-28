package com.supermap.imobilelite.mapsamples;

import java.util.Map;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.supermap.imobilelite.maps.DefaultItemizedOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.OverlayItem;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.dialog.MarkerConfigDialog;
import com.supermap.imobilelite.mapsamples.dialog.ReadmeDialog;
import com.supermap.imobilelite.samples.service.PreferencesService;

public class OverlayEventDemo extends SimpleDemo {

	private DefaultItemizedOverlay blueMarkerOverlay = null;

	private DefaultItemizedOverlay greenMarkerOverlay = null;

	private DefaultItemizedOverlay redMarkerOverlay = null;

	private DefaultItemizedOverlay yellowMarkerOverlay = null;

	private Point2D longTouchGeoPoint = null;

	private MarkerConfigDialog markerConfigDialog = null;

	private static final int MARKERCONFIG_DIALOG = 1;
	
	private static final int README_DIALOG = 9;
	
	// 触屏的x坐标
	private int touchX;
	
	// 触屏的y坐标
	private int touchY;
	
	private PreferencesService service;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Drawable drawableBlue = getResources().getDrawable(R.drawable.blue_pin);
		blueMarkerOverlay = new DefaultItemizedOverlay(drawableBlue);

		Drawable drawableGreen = getResources().getDrawable(
				R.drawable.green_pin);
		greenMarkerOverlay = new DefaultItemizedOverlay(drawableGreen);

		Drawable drawableRed = getResources().getDrawable(R.drawable.red_pin);
		redMarkerOverlay = new DefaultItemizedOverlay(drawableRed);

		Drawable drawableYellow = getResources().getDrawable(
				R.drawable.yellow_pin);
		yellowMarkerOverlay = new DefaultItemizedOverlay(drawableYellow);
		
        // 动态投影为4326的地图，防止投影坐标系设置中心点无效，图层叠加等设置经纬度坐标无效。
//        CoordinateReferenceSystem crs = new CoordinateReferenceSystem();
//        crs.wkid = 4326;
//        baseLayerView.setCRS(crs);

        mapView.getController().setZoom(6);
//        mapView.getController().setCenter(new Point2D(116.391468, 39.904491));
		mapView.setBuiltInZoomControls(false);
		mapView.addMapViewEventListener(new MapViewEventAdapter());

		TouchOverlay touchOverlay = new TouchOverlay();
		mapView.getOverlays().add(touchOverlay);

		mapView.getOverlays().add(blueMarkerOverlay);
		mapView.getOverlays().add(greenMarkerOverlay);
		mapView.getOverlays().add(redMarkerOverlay);
		mapView.getOverlays().add(yellowMarkerOverlay);

		// 构造一个markerConfigDialog，showDialog时即可直接返回
		markerConfigDialog = new MarkerConfigDialog(this, R.style.dialogTheme);
		
        service = new PreferencesService(this);
		Map<String, Boolean> params = service.getReadmeEnable("OverlayEventDemo");
		boolean isReadmeEnable = params.get("readme");
		if (isReadmeEnable) {
			showDialog(README_DIALOG);
		}
		helpBtn.setVisibility(View.VISIBLE);
	    helpBtn.setOnClickListener(new View.OnClickListener() {
				
		    @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(README_DIALOG);
		       }
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// group, item id, order, title
		menu.add(0, 1, 0, R.string.clearall);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			// 清空Overlay，地图刷新
			if (blueMarkerOverlay.size() != 0) {
				blueMarkerOverlay.clear();
				mapView.getOverlays().remove(blueMarkerOverlay);
			}
			if (greenMarkerOverlay.size() != 0) {
				greenMarkerOverlay.clear();
				mapView.getOverlays().remove(greenMarkerOverlay);
			}
			if (redMarkerOverlay.size() != 0) {
				redMarkerOverlay.clear();
				mapView.getOverlays().remove(redMarkerOverlay);
			}
			if (yellowMarkerOverlay.size() != 0) {
				yellowMarkerOverlay.clear();
				mapView.getOverlays().remove(yellowMarkerOverlay);
			}
			mapView.invalidate();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * 增加Overlay，刷新地图
	 * 
	 * @param id
	 */
	public void addOverlay(int id) {
		if (longTouchGeoPoint != null) {
			OverlayItem overlayItem = new OverlayItem(longTouchGeoPoint, "", "");
			switch (id) {
			case R.id.blue_pin_imgbtn:
				// 若markerOverlay已被移除则重新加入
				if (!mapView.getOverlays().contains(blueMarkerOverlay)) {
					mapView.getOverlays().add(blueMarkerOverlay);
				}
				if (!isOverlayItemExisted(blueMarkerOverlay, overlayItem)) {
					blueMarkerOverlay.addItem(overlayItem);
				}
				break;
			case R.id.green_pin_imgbtn:
				// 若markerOverlay已被移除则重新加入
				if (!mapView.getOverlays().contains(greenMarkerOverlay)) {
					mapView.getOverlays().add(greenMarkerOverlay);
				}
				if (!isOverlayItemExisted(greenMarkerOverlay, overlayItem)) {
					greenMarkerOverlay.addItem(overlayItem);
				}
				break;
			case R.id.red_pin_imgbtn:
				// 若markerOverlay已被移除则重新加入
				if (!mapView.getOverlays().contains(redMarkerOverlay)) {
					mapView.getOverlays().add(redMarkerOverlay);
				}
				if (!isOverlayItemExisted(redMarkerOverlay, overlayItem)) {
					redMarkerOverlay.addItem(overlayItem);
				}
				break;
			case R.id.yellow_pin_imgbtn:
				// 若markerOverlay已被移除则重新加入
				if (!mapView.getOverlays().contains(yellowMarkerOverlay)) {
					mapView.getOverlays().add(yellowMarkerOverlay);
				}
				if (!isOverlayItemExisted(yellowMarkerOverlay, overlayItem)) {
					yellowMarkerOverlay.addItem(overlayItem);
				}
				break;
			default:
				break;
			}
			mapView.invalidate();
		}
	}

	/**
	 * 根据触屏的位置判断是否已经加入了标注
	 * 
	 * @param overlayItem
	 * @return
	 */
	private boolean isOverlayItemExisted(DefaultItemizedOverlay overlay,
			OverlayItem overlayItem) {
		boolean bResult = false;
		for (int i = 0; i < overlay.size(); i++) {
			OverlayItem tmpItem = overlay.getItem(i);
			Point2D tmpPoint = tmpItem.getPoint();
			if (overlayItem.getPoint().equals(tmpPoint)) {
				bResult = true;
				break;
			}
		}
		return bResult;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// switch是android示范程序中的标准写法
		switch (id) {
		case MARKERCONFIG_DIALOG:
			if (markerConfigDialog != null && longTouchGeoPoint != null) {
				return markerConfigDialog;
			}
			break;
		case README_DIALOG:
			Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "OverlayEventDemo");
			return dialog;
		default:
			break;
		}
		return super.onCreateDialog(id);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case MARKERCONFIG_DIALOG:
			if (markerConfigDialog != null && longTouchGeoPoint != null) {
				Window window = markerConfigDialog.getWindow();
				WindowManager.LayoutParams lp = window.getAttributes();
				// 设置对话框初始位置为左上角
				lp.gravity = Gravity.LEFT | Gravity.TOP;
				// lp.x与lp.y表示相对于原始位置的偏移
				// (touchX, touchY)为触屏位置坐标，坐标系以屏幕左上角为(0, 0)
				lp.x = touchX;
				lp.y = touchY;
				// 重新设置对话框位置
				window.setAttributes(lp);
				markerConfigDialog.setLongTouchGeoPoint(longTouchGeoPoint);
			}
			break;
		case README_DIALOG:
			ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
			readmeDialog.setReadmeText(getResources().getString(R.string.overlayeventdemo_readme));
			break;
		default:
			break;
		}
		super.onPrepareDialog(id, dialog);
	}

	@Override
	public void onBackPressed() {
		// 重写onBackPressed，将字段markerConfigDialog必须置为null，以保证消除之前的引用
		markerConfigDialog.dismiss();
		markerConfigDialog = null;
		super.onBackPressed();
	}

	/**
	 * 触屏Overlay
	 */
	class TouchOverlay extends Overlay {
		@Override
		public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
			touchX =  Math.round(event.getX());
			touchY = Math.round(event.getY());
			// 记录点击位置
			longTouchGeoPoint = mapView.getProjection().fromPixels(touchX, touchY);
			return false;
		}
	}

	/**
	 * MapView事件处理适配器，提供长按事件的实现
	 */
	class MapViewEventAdapter implements MapView.MapViewEventListener {

		@Override
		public void moveStart(MapView paramMapView) {

		}

		@Override
		public void move(MapView paramMapView) {

		}

		@Override
		public void moveEnd(MapView paramMapView) {

		}

		@Override
		public void touch(MapView paramMapView) {

		}

		@Override
		public void longTouch(MapView paramMapView) {
			if (longTouchGeoPoint != null) {
				showDialog(MARKERCONFIG_DIALOG);
			}
		}

		@Override
		public void zoomStart(MapView paramMapView) {

		}

		@Override
		public void zoomEnd(MapView paramMapView) {

		}

		@Override
		public void mapLoaded(MapView paramMapView) {

		}
	}

}
