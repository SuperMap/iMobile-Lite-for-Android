package com.supermap.imobilelite.mapsamples;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.supermap.imobilelite.maps.CoordinateReferenceSystem;
import com.supermap.imobilelite.maps.DefaultItemizedOverlay;
import com.supermap.imobilelite.maps.ItemizedOverlay;
import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.OverlayItem;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.dialog.PopWindow;

public class OverlayDemo extends Activity{
	private static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services/map-china400/rest/maps/China";
    private MapView mapView;
    private LayerView baseLayerView;
    private String mapUrl;
    private Point2D beijing = new Point2D(116.391468, 39.904491);
    private PopWindow popWindow;
    private int titleBarHeight;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        View popView = inflater.inflate(R.layout.simple_demo, null);
        setContentView(popView);  
        mapView = (MapView) this.findViewById(R.id.mapview);
        baseLayerView = new LayerView(this);
        Bundle bundle = this.getIntent().getExtras();
        // 设置访问地图的URL
        mapUrl = bundle.getString("map_url");
        if (mapUrl != null && mapUrl.equals("")) {
            baseLayerView.setURL(DEFAULT_URL);
        } else {
            baseLayerView.setURL(mapUrl);
        }
        CoordinateReferenceSystem crs = new CoordinateReferenceSystem();
        crs.wkid = 4326;
        baseLayerView.setCRS(crs);
        mapView.addLayer(baseLayerView);
        mapView.getController().setCenter(new Point2D(116.391468, 39.904491));// 39.904491, 116.391468 0.0d, 0.0d

        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mapView.getController().setZoom(6);
        mapView.post(new Runnable(){
        	public void run(){
        		titleBarHeight =initHeight();
        		Log.d("com.supermap.imobilelite.mapsamples", "titleBarHeight:"+titleBarHeight);
        	}
        });
        popWindow = new PopWindow(popView,titleBarHeight);
        // 增加监听器
        mapView.addMapViewEventListener(new MapViewEventAdapter());
//        mapView.getController().setCenter(new Point2D(116.391468, 39.904491));
        showOverlay();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
	}
	
    /**
     * 计算标题栏的高度
     * @return
     */
    private int initHeight(){
    	Rect rect =new Rect();
    	Window window =getWindow();
    	mapView.getWindowVisibleDisplayFrame(rect);
    	//状态栏的高度
    	int statusBarHight =rect.top;
    	//标题栏跟状态栏的总体高度
    	int contentViewTop =window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
    	//标题栏的高度
    	int titleBarHeight =contentViewTop -statusBarHight;
    	return titleBarHeight;
    }
	/**
	 * 添加标注并显示
	 */
	private void showOverlay() {
        // 为首都及省会城市增加标注
        Drawable drawableBlue = getResources().getDrawable(R.drawable.blue_pin);
        DefaultItemizedOverlay overlay = new DefaultItemizedOverlay(drawableBlue);
        OverlayItem overlayItem1 = new OverlayItem(beijing, "北京", "北京");
        OverlayItem overlayItem2 = new OverlayItem(new Point2D(119.29781, 26.07859), "福州", "福州");
        OverlayItem overlayItem3 = new OverlayItem(new Point2D(117.2757, 31.863255), "合肥", "合肥");
        OverlayItem overlayItem4 = new OverlayItem(new Point2D(116.9671014491427, 36.65554103344072), "济南", "济南");
        OverlayItem overlayItem5 = new OverlayItem(new Point2D(115.89992, 28.675991), "南昌", "南昌");
        OverlayItem overlayItem6 = new OverlayItem(new Point2D(120.1650797808366, 30.25258003455749), "杭州", "杭州");
        OverlayItem overlayItem7 = new OverlayItem(new Point2D(118.7980507758158, 32.0853446326497), "南京", "南京");
        OverlayItem overlayItem8 = new OverlayItem(new Point2D(121.4700004384941, 31.22999934324168), "上海", "上海");
        OverlayItem overlayItem9 = new OverlayItem(new Point2D(121.5066991712687, 25.03508867790034), "台北", "台北");
        OverlayItem overlayItem10 = new OverlayItem(new Point2D(87.58649898245132, 43.78259334309395), "乌鲁木齐", "乌鲁木齐");
        OverlayItem overlayItem11 = new OverlayItem(new Point2D(101.7779873119019, 36.62124650477163), "西宁", "西宁");
        OverlayItem overlayItem12 = new OverlayItem(new Point2D(103.7508901732588, 36.06854443671507), "兰州", "兰州");
        OverlayItem overlayItem13 = new OverlayItem(new Point2D(106.2623667781083, 38.46798038796805), "银川", "银川");
        OverlayItem overlayItem14 = new OverlayItem(new Point2D(108.8831890384891, 34.2659121817652), "西安", "西安");
        OverlayItem overlayItem15 = new OverlayItem(new Point2D(91.13208630424707, 29.65070055880591), "拉萨", "拉萨");
        OverlayItem overlayItem16 = new OverlayItem(new Point2D(106.7003035105738, 26.57193679104831), "贵阳", "贵阳");
        OverlayItem overlayItem17 = new OverlayItem(new Point2D(102.7021004028558, 25.05102907134705), "昆明", "昆明");
        OverlayItem overlayItem18 = new OverlayItem(new Point2D(104.0713202656362, 30.66999375220116), "成都", "成都");
        OverlayItem overlayItem19 = new OverlayItem(new Point2D(106.522701649771, 29.54407808674531), "重庆", "重庆");
        OverlayItem overlayItem20 = new OverlayItem(new Point2D(110.3433603839145, 20.03920587078526), "海口", "海口");
        OverlayItem overlayItem21 = new OverlayItem(new Point2D(113.26143, 23.118912), "广州", "广州");
        OverlayItem overlayItem22 = new OverlayItem(new Point2D(108.31177, 22.806543), "南宁", "南宁");
        OverlayItem overlayItem23 = new OverlayItem(new Point2D(112.9333175489201, 28.23056241236), "长沙", "长沙");
        OverlayItem overlayItem24 = new OverlayItem(new Point2D(114.279240418456, 30.57248904311948), "武汉", "武汉");
        OverlayItem overlayItem25 = new OverlayItem(new Point2D(113.6548802282204, 34.7646717478449), "郑州", "郑州");
        OverlayItem overlayItem26 = new OverlayItem(new Point2D(126.6225811335438, 45.7552141651009), "哈尔滨", "哈尔滨");
        OverlayItem overlayItem27 = new OverlayItem(new Point2D(123.38339136237, 41.80199341192142), "沈阳", "沈阳");
        OverlayItem overlayItem28 = new OverlayItem(new Point2D(125.3500003369777, 43.86666944859481), "长春", "长春");
        OverlayItem overlayItem29 = new OverlayItem(new Point2D(112.5516960082677, 37.89305014631092), "太原", "太原");
        OverlayItem overlayItem30 = new OverlayItem(new Point2D(111.6486551308885, 40.81439382596415), "呼和浩特", "呼和浩特");
        OverlayItem overlayItem31 = new OverlayItem(new Point2D(114.4985924758457, 38.04194230466739), "石家庄", "石家庄");
        OverlayItem overlayItem32 = new OverlayItem(new Point2D(117.1999887366723, 39.13000573437018), "天津", "天津");
        OverlayItem overlayItem33 = new OverlayItem(new Point2D(113.5442042992459, 22.20187632747567), "澳门", "澳门");
        OverlayItem overlayItem34 = new OverlayItem(new Point2D(113.9866673970331, 22.43891874998456), "香港", "香港");
        overlay.addItem(overlayItem1);
        overlay.addItem(overlayItem2);
        overlay.addItem(overlayItem3);
		overlay.addItem(overlayItem4);
		overlay.addItem(overlayItem5);
		overlay.addItem(overlayItem6);
		overlay.addItem(overlayItem7);
		overlay.addItem(overlayItem8);
		overlay.addItem(overlayItem9);
		overlay.addItem(overlayItem10);
		overlay.addItem(overlayItem11);
		overlay.addItem(overlayItem12);
		overlay.addItem(overlayItem13);
		overlay.addItem(overlayItem14);
		overlay.addItem(overlayItem15);
		overlay.addItem(overlayItem16);
		overlay.addItem(overlayItem17);
		overlay.addItem(overlayItem18);
		overlay.addItem(overlayItem19);
		overlay.addItem(overlayItem20);
		overlay.addItem(overlayItem21);
		overlay.addItem(overlayItem22);
		overlay.addItem(overlayItem23);
		overlay.addItem(overlayItem24);
		overlay.addItem(overlayItem25);
		overlay.addItem(overlayItem26);
		overlay.addItem(overlayItem27);
		overlay.addItem(overlayItem28);
		overlay.addItem(overlayItem29);
		overlay.addItem(overlayItem30);
		overlay.addItem(overlayItem31);
		overlay.addItem(overlayItem32);
		overlay.addItem(overlayItem33);
		overlay.addItem(overlayItem34);
		
		overlay.setOnFocusChangeListener(new SelectedOverlay());
		mapView.getOverlays().add(new CustomOverlay());
		mapView.getOverlays().add(overlay);
		
		// 重新onDraw一次
		mapView.invalidate();
	}
	
	/**
	 * 自定义Overlay 
	 */
	class CustomOverlay extends Overlay {
		
        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            super.draw(canvas, mapView, shadow);
            Paint paint = new Paint();
            Point point = mapView.getProjection().toPixels(new Point2D(116.500396, 39.977909), null);
            paint.setTextSize(24);
            paint.setStrokeWidth(0.8f);
            paint.setARGB(255, 255, 0, 0);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawText("北京超图", point.x, point.y, paint);
        }
	}
	
	/**
	 * Overlay焦点获取事件 
	 */
	class SelectedOverlay implements ItemizedOverlay.OnFocusChangeListener {

		@Override
		public void onFocusChanged(ItemizedOverlay overlay, OverlayItem item) {
//			// 地图中心漫游至当前OverlayItem
//			mapView.getController().animateTo(item.getPoint());
//			Toast.makeText(mapView.getContext().getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
			 // 弹出气泡展示消息
			popWindow.showPopupWindow(mapView, item);
		}
		
	}
	

    /**
     * 更新气泡窗口
     */
    public void updatePopupWindow() {
        popWindow.updatePopupWindow(mapView);
    }
    
    
    /**
     * 给MapView添加监听事件
     * */
    class MapViewEventAdapter implements MapView.MapViewEventListener {

        @Override
        public void longTouch(MapView paramMapView) {
            popWindow.closePopupWindow();
        }

        @Override
        public void mapLoaded(MapView paramMapView) {
        }

        @Override
        public void move(MapView paramMapView) {
            updatePopupWindow();
        }

        @Override
        public void moveEnd(MapView paramMapView) {
            updatePopupWindow();
        }

        @Override
        public void moveStart(MapView paramMapView) {
            updatePopupWindow();
        }

        @Override
        public void touch(MapView paramMapView) {
        }

        @Override
        public void zoomEnd(MapView paramMapView) {
            updatePopupWindow();
            // popWindow.closePopupWindow();
        }

        @Override
        public void zoomStart(MapView paramMapView) {
        }
    }
   
    @Override
    protected void onDestroy() {
        // LocationUtil.disposeLoction();
        if (mapView != null) {
            mapView.destroy();
        }
        super.onDestroy();
    }

}
