package com.supermap.imobilelite.mapsamples;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.supermap.imobilelite.mapsamples.dialog.QueryConfigDialog;
import com.supermap.imobilelite.mapsamples.dialog.ReadmeDialog;
import com.supermap.imobilelite.mapsamples.util.HttpUtil;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.imobilelite.maps.DefaultItemizedOverlay;
import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.OverlayItem;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.QueryResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DistanceQueryDemo extends SimpleDemo {

	private static final int QUERYCONFIG_DIALOG = 0;
	
	// README DIALOG，值统一定为9
	private static final int README_DIALOG = 9;

	private QueryConfigDialog queryConfigDialog;

	// 长按地理位置
	private Point2D longTouchGeoPoint = null;

	private JSONObject layersObject = null;

	// 触屏的x坐标
	private int touchX;


	// 触屏的y坐标
	private int touchY;

	private TouchOverlay touchOverlay;
	
	private int selectedLayerPosition = - 1;
	
	private static Drawable drawableBlue;
	
	private DefaultItemizedOverlay defaultItemizedOverlay;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapView.removeAllLayers();
		LayerView layerView = new LayerView(this);
		layerView.setURL(mapUrl);
		mapView.addLayer(layerView);
		 // 设置缩放级别
        mapView.getController().setZoom(2);
        // 设置中心点
        // Point2D gp = new Point2D(0.0d, 0.0d);
        mapView.getController().setCenter(new Point2D(11858134.82004900, 4265797.67453910));// 11858134.82004900, 4265797.67453910
		mapView.addMapViewEventListener(new MapViewEventAdapter());

		touchOverlay = new TouchOverlay();
		mapView.getOverlays().add(touchOverlay);


        // 蓝色的大头针图片，用一个分辨率低一些的，节约内存，提高速度
        drawableBlue = getResources().getDrawable(R.drawable.min_blue_pin);
        defaultItemizedOverlay = new DefaultItemizedOverlay(drawableBlue);

		// 构造一个markerConfigDialog，showDialog时即可直接返回
		queryConfigDialog = new QueryConfigDialog(this, R.style.dialogTheme);
		
        PreferencesService service = new PreferencesService(this);
		Map<String, Boolean> params = service.getReadmeEnable("DistanceQueryDemo");
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
	
	/**
	 * 获取MapView
	 * @return 
	 */
	public MapView getMapView() {
		return mapView;
	}
	
	/**
	 * 设置当前选择的LayerPosition
	 * @param position
	 */
	public void setSelectedLayerPosition(int position) {
		selectedLayerPosition = position;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// group, item id, order, title
		menu.add(0, 1, 0, R.string.clear_query_result);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			// 清除查询结果
			if (defaultItemizedOverlay.size() != 0) {
				defaultItemizedOverlay.destroy();
				mapView.getOverlays().clear();
				mapView.invalidate();
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case QUERYCONFIG_DIALOG:
			if (queryConfigDialog != null && longTouchGeoPoint != null) {
				return queryConfigDialog;
			}
			break;
		case README_DIALOG:
			Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "DistanceQueryDemo");
			return dialog;
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case QUERYCONFIG_DIALOG:
				if (queryConfigDialog != null && longTouchGeoPoint != null) {
					queryConfigDialog.refreshLongTouchGeoPoint(longTouchGeoPoint);
					queryConfigDialog.refreshSpinner(getLayerNames(), selectedLayerPosition);
				}
				break;
		case README_DIALOG:
			ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
			readmeDialog.setReadmeText(getResources().getString(R.string.distancequerydemo_readme));
			break;
		default:
			break;
		}
		super.onPrepareDialog(id, dialog);
	}

	@Override
	public void onBackPressed() {
		// 重写onBackPressed，将字段queryConfigDialog必须置为null，以保证消除之前的引用
		queryConfigDialog.dismiss();
		queryConfigDialog = null;
		super.onBackPressed();
	}

	/**
	 * 获取当前地图的所有图层名字，只考虑点图层
	 * @return
	 */
	private String[] getLayerNames() {
		ArrayList<String> values = new ArrayList<String>();
		try {
				JSONObject subLayers = layersObject.getJSONObject("subLayers");
				JSONArray layersArray = subLayers.getJSONArray("layers");
				for (int i = 0; i < layersArray.length(); i++) {
					String layerName = "";
					JSONObject layerJsonObject = layersArray.getJSONObject(i);
					JSONObject datasetInfo = layerJsonObject.getJSONObject("datasetInfo");
					String type = datasetInfo.getString("type");
					// DEMO中查询只考虑点图层
					if (type.equals("POINT")) {
						layerName = layerJsonObject.getString("name");
						values.add(layerName);
					}
				}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String[] result = new String[values.size()];
		return values.toArray(result);
	}

	/**
	 * 查询结果显示成Overlay
	 * @param queryResult
	 */
	public void showDefaultItemizedOverlay(QueryResult queryResult) {
		for (int i = 0; i < queryResult.recordsets[0].features.length; i++) {
			Feature feature = queryResult.recordsets[0].features[i];
			com.supermap.services.components.commontypes.Point2D pt = feature.geometry.points[0];
			com.supermap.imobilelite.maps.Point2D geoPoint = new com.supermap.imobilelite.maps.Point2D(pt.x,pt.y);
			OverlayItem overlayItem = new OverlayItem(geoPoint, "", "");
			defaultItemizedOverlay.addItem(overlayItem);
		}
		if (!mapView.getOverlays().contains(defaultItemizedOverlay)) {
			mapView.getOverlays().add(defaultItemizedOverlay);
		}
		// circleOverlay.setTouchEventListener(new
		// CircleOverlay.OverlayTouchEventListener() {
		// @Override
		// public void onTouch(MotionEvent evt, MapView mapView) {
		// Toast.makeText(getApplicationContext(), "Meters Circle Touch!",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		this.mapView.invalidate();
		queryResult = null;
		System.gc();
		Runtime.getRuntime().gc();
	}

	public void showLineOverlay(QueryResult queryResult) {
		for (int i = 0; i < queryResult.recordsets[0].features.length; i++) {
			Feature feature = queryResult.recordsets[0].features[i];
			Geometry geometry = feature.geometry;
			List<Point2D> geoPoints = getPiontsFromGeometry(geometry);
			LineOverlay lineOverlay = new LineOverlay(getLinePaint());
			lineOverlay.setData(geoPoints);
			this.mapView.getOverlays().add(lineOverlay);
		}


		// lineOverlay.setTouchEventListener(new
		// LineOverlay.OverlayTouchEventListener() {
		// @Override
		// public void onTouch(MotionEvent evt, MapView mapView) {
		// Toast.makeText(getApplicationContext(), "Line Touch!",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		this.mapView.invalidate();
	}

	public void showPolygonOverlay(QueryResult queryResult) {
		for (int i = 0; i < queryResult.recordsets[0].features.length; i++) {
			Feature feature = queryResult.recordsets[0].features[i];
			Geometry geometry = feature.geometry;
			List<Point2D> geoPoints = getPiontsFromGeometry(geometry);
			PolygonOverlay polygonOverlay = new PolygonOverlay(getPolygonPaint());
			polygonOverlay.setData(geoPoints);
			this.mapView.getOverlays().add(polygonOverlay);
		}

		// polygonOverlay.setTapListener(new PolygonOverlay.OverlayTapListener()
		// {
		// @Override
		// public void onTap(GeoPoint gp, MapView mapView) {
		// Toast.makeText(getApplicationContext(), "Cherry Creek State Park",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		this.mapView.invalidate();
	}
	
	private List<com.supermap.imobilelite.maps.Point2D> getPiontsFromGeometry(Geometry geometry){
        List< com.supermap.imobilelite.maps.Point2D> geoPoints = new ArrayList<com.supermap.imobilelite.maps.Point2D>();
        com.supermap.services.components.commontypes.Point2D[] points = geometry.points;
        for(com.supermap.services.components.commontypes.Point2D point : points){
            com.supermap.imobilelite.maps.Point2D geoPoint = new com.supermap.imobilelite.maps.Point2D(point.x,point.y);
            geoPoints.add(geoPoint);
        }
        return geoPoints;
    }

	private Paint getLinePaint() {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.rgb(72, 61, 139));
		paint.setAlpha(80);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(3);

		return paint;
	}

	private Paint getPolygonPaint() {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLUE);
		paint.setAlpha(50);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(2);

		return paint;
	}

	/**
	 * 触屏Overlay
	 */
	class TouchOverlay extends Overlay {
		@Override
		public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
			touchX = Math.round(event.getX());
			touchY = Math.round(event.getY());
			// 记录点击位置
			longTouchGeoPoint = mapView.getProjection().fromPixels(touchX,
					touchY);
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
			if (mapView.getOverlays().contains(defaultItemizedOverlay)) {
				Toast.makeText(DistanceQueryDemo.this, R.string.have_got_queryresult, Toast.LENGTH_SHORT).show();
			} else {
				if (longTouchGeoPoint != null) {
					
					final ProgressDialog  progressDoalog = new ProgressDialog(DistanceQueryDemo.this);
	            	progressDoalog.setMessage("正在请求数据");
	            	progressDoalog.setCanceledOnTouchOutside(false);
	            	progressDoalog.show(); 
					
	            	new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							layersObject = HttpUtil.getLayersJSONObject(mapView.getBaseLayer().getURL());
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									if(progressDoalog!=null){
										progressDoalog.cancel();
									}
									if(layersObject!=null){
										showDialog(QUERYCONFIG_DIALOG);
									}else{
										Toast.makeText(DistanceQueryDemo.this, "请求失败", Toast.LENGTH_LONG).show();
									}
								}
							});	
						}
					}).start();
					
					
					
				}
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
