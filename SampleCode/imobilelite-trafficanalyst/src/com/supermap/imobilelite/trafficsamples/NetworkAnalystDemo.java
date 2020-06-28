package com.supermap.imobilelite.trafficsamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PointOverlay;
import com.supermap.imobilelite.networkAnalyst.SupplyCenter;
import com.supermap.imobilelite.networkAnalyst.SupplyCenterType;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.imobilelite.trafficsamples.R;
import com.supermap.imobilelite.trafficsamples.util.NetWorkAnalystUtil;

/**
 * <p>
 * 交通网络分析示例中显示地图的视图，该视图显示底图和提供网络分析方式菜单项及响应触发事件。
 * 事件的响应为 弹出支持拖动的窗口NetworkAnalystDialog, 展示进行交通网络分析操作提示信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class NetworkAnalystDemo extends Activity {
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-changchun/rest/maps/长春市区图";
    protected static final String DEFAULT_ANALYST_URL = "/transportationanalyst-sample/rest/networkanalyst/RoadNet@Changchun";
    private static final int NETWORKANALYST_DIALOG = 0;
    private static final int README_DIALOG = 9;
    private PreferencesService service;
    protected MapView mapView;
    private LayerView baseLayerView;
    // 绘制风格
    private Paint paint = new Paint(1);
    private Bundle bundle;
    private String networkAnalystUrl;
    private NetworkAnalystDialog networkAnalystDialog;
    private List<Point2D> geoPoints = new ArrayList<Point2D>();
    // 触屏Overlay,用来获取点坐标
    private TouchOverlay touchOverlay;
    // 触屏的x坐标
    private int touchX;
    // 触屏的y坐标
    private int touchY;
    private int touchDownX;
    private int touchDownY;
    // 是否添加选取的点，平移操作点击点不能加入
    boolean isAddPoint = true;
    // 分析方式
    private int drawStatic = 0;// 0代表最佳路径分析,1代表旅行商分析,2表示服务区分析,3代表最近设施分析,4代表选址分区分析,5代表多旅行商分析
    private List<Point2D> geoFacilities = new ArrayList<Point2D>();
    private List<Point2D> geoCenters = new ArrayList<Point2D>();
    protected int titleBarHeight;
    private Button helpBtn;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        bundle = this.getIntent().getExtras();
        // 设置访问地图的URL
        String serviceUrl = bundle.getString("service_url");
        String mapUrl = "";
        if (serviceUrl == null || "".equals(serviceUrl)) {
            mapUrl = DEFAULT_URL + DEFAULT_MAP_URL;
            networkAnalystUrl = DEFAULT_URL + DEFAULT_ANALYST_URL;
        } else {
            mapUrl = serviceUrl + DEFAULT_MAP_URL;
            networkAnalystUrl = serviceUrl + DEFAULT_ANALYST_URL;
        }

        baseLayerView = new LayerView(this, mapUrl);
        mapView.addLayer(baseLayerView);
        mapView.getController().setZoom(2); 
        // 设置中心点
        // mapView.getController().setCenter(new Point2D(116.391468, 39.904491));// 39.904491, 116.391468 0.0d, 0.0d
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        //获取标题栏高度
        mapView.post(new Runnable(){
        	public void run(){
        		titleBarHeight =initHeight();
        		
        	}
        });
        
        helpBtn =(Button)findViewById(R.id.button_help);
        helpBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 showDialog(README_DIALOG);
			}
		});
        // 创建交通网络分析操作提示窗口对象
        networkAnalystDialog = new NetworkAnalystDialog(this, R.style.dialogTheme);
        // 设置支持焦点不在窗口上时，父视图能够响应焦点事件
        networkAnalystDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
       
        // 设置绘制风格paint，默认风格
        paint.setColor(Color.argb(200, 0, 0, 250));
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        touchOverlay = new TouchOverlay();

        service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("networkAnalyst");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 创建 路线搜索菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.findpath_text);
        menu.add(0, 2, 0, R.string.findtsppath_text);
        menu.add(0, 3, 0, R.string.servicearea_text);
        menu.add(0, 4, 0, R.string.findclosesfacilities_text);
        menu.add(0, 5, 0, R.string.findlocation_text);
        menu.add(0, 6, 0, R.string.findmtsppath_text);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击 路线搜索 菜单触发事件
        case 1:// 最佳路径分析
            showDialog(NETWORKANALYST_DIALOG);
            networkAnalystDialog.setReadmeText(R.string.findpath_text);
            drawStatic = 0;
            clearOverlayer();
            break;
        case 2:// 旅行商分析
            showDialog(NETWORKANALYST_DIALOG);
            networkAnalystDialog.setReadmeText(R.string.findtsppath_text);
            drawStatic = 1;
            clearOverlayer();
            break;
        case 3:// 服务区分析
            showDialog(NETWORKANALYST_DIALOG);
            networkAnalystDialog.setReadmeText(R.string.servicearea_text);
            drawStatic = 2;
            clearOverlayer();
            break;
        case 4:// 最近设施分析
            showDialog(NETWORKANALYST_DIALOG);
            networkAnalystDialog.setReadmeText(R.string.findclosesfacilities_text);
            drawStatic = 3;
            clearOverlayer();
            break;
        case 5:// 选址分区分析
            showDialog(NETWORKANALYST_DIALOG);
            networkAnalystDialog.setReadmeText(R.string.findlocation_text);
            drawStatic = 4;
            mapView.getController().setCenter(new Point2D(4853.6240321526, -3861.911472192499));
            mapView.getController().setZoom(1);
            clearOverlayer();
            break;
        case 6:// 多旅行商分析
            showDialog(NETWORKANALYST_DIALOG);
            networkAnalystDialog.setReadmeText(R.string.findmtsppath_text);
            drawStatic = 5;
            clearOverlayer();
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <p>
     * 清空Overlay，地图刷新
     * </p>
     */
    public void clearOverlayer() {
        mapView.getOverlays().clear();
        geoPoints.clear();
        networkAnalystDialog.getSelectPointsBtn().setVisibility(View.VISIBLE);
        networkAnalystDialog.getSelectPointsBtn().setText(R.string.selectpoints_text);
        if (drawStatic == 3) {
            // 绘制设施点集合
            drawFacilitiesPoints();
            networkAnalystDialog.getSelectPointsBtn().setText(R.string.selecteventpoints_text);
        }
        if (drawStatic == 4) {
            // 绘制 供给中心点 集合
            drawSupplyCenters();
            networkAnalystDialog.getSelectPointsBtn().setVisibility(View.GONE);
        } else if (drawStatic == 5) {
            // 绘制配送中心点集合
            drawCentersPoints();
            networkAnalystDialog.getSelectPointsBtn().setText(R.string.selecttargetpoints_text);
        }
        mapView.invalidate();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case NETWORKANALYST_DIALOG:
            if (networkAnalystDialog != null) {
                return networkAnalystDialog;
            }
            break;
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "networkAnalyst");
            return dialog;
        default:
            break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
        case NETWORKANALYST_DIALOG:
            if (networkAnalystDialog != null) {
                Log.d("iserver", "NetworkAnalystDemo onPrepareDialog!");
            }
            break;
        case README_DIALOG:
            ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
            readmeDialog.setReadmeText(getResources().getString(R.string.networkanalyst_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

    /**
     * <p>
     * 在地图上选取分析点，并绘制在地图上
     * </p>
     */
    public void selectPoints() {
        if (!mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().add(touchOverlay);
        }
    }

    /**
     * <p>
     * 在地图上绘制分析结果路线
     * </p>
     */
    public void drawPointsLine() {
        // 执行最佳路径分析,将路线绘制在地图上
        if (!checkAndShowMessage()) {
            return;
        }
        List<List<Point2D>> pointLists = new ArrayList<List<Point2D>>();
        switch (drawStatic) {
        case 0:// 0代表最佳路径分析
            Log.d("iserver", "excutePathService");
            pointLists = NetWorkAnalystUtil.excutePathService(networkAnalystUrl, geoPoints);
            break;
        case 1:// 1代表旅行商分析
            Log.d("iserver", "excuteTSPPathsService");
            pointLists = NetWorkAnalystUtil.excuteTSPPathsService(networkAnalystUrl, geoPoints);
            break;
        case 2:// 2表示服务区分析
            Log.d("iserver", "excuteServiceAreasService");
            pointLists = NetWorkAnalystUtil.excuteServiceAreasService(networkAnalystUrl, geoPoints);
            break;
        case 3:// 3代表最近设施分析
            Log.d("iserver", "excuteFindClosesFacilitiesService");
            pointLists = NetWorkAnalystUtil.excuteFindClosesFacilitiesService(networkAnalystUrl, geoFacilities, geoPoints.get(0));
            break;
        case 4:// 4代表选址分区分析
            Log.d("iserver", "excuteFindLocationService");
            SupplyCenter[] supplyCenters = new SupplyCenter[5];
            int[] nodeIDs = { 1358, 2972, 663, 1161, 4337 };
            for (int i = 0; i < supplyCenters.length; i++) {
                SupplyCenter supplyCenter = new SupplyCenter(500, nodeIDs[i], 100, SupplyCenterType.OPTIONALCENTER);
                supplyCenters[i] = supplyCenter;
            }
            pointLists = NetWorkAnalystUtil.excuteFindLocationService(networkAnalystUrl, supplyCenters);
            break;
        case 5:// 5代表多旅行商分析
            Log.d("iserver", "excuteMTSPPathsService");
            pointLists = NetWorkAnalystUtil.excuteMTSPPathService(networkAnalystUrl, geoPoints, geoCenters);
        default:
            break;
        }
        if (pointLists == null) {
            return;
        }
        Log.d("iserver", "path count:" + pointLists.size());
        for (int i = 0; i < pointLists.size(); i++) {
            List<Point2D> geoPointList = pointLists.get(i);
            LineOverlay lineOverlay = new LineOverlay();
            lineOverlay.setLinePaint(paint);
            if(drawStatic != 4){
               lineOverlay.setShowPoints(false);
            }
            mapView.getOverlays().add(lineOverlay);
            lineOverlay.setData(geoPointList);
        }
        mapView.invalidate();
        geoPoints.clear();
        if (mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().remove(touchOverlay);
        }
    }

    /**
     * <p>
     * 绘制固定设施点
     * </p>
     */
    private void drawFacilitiesPoints() {
        if (geoFacilities.size() < 1) {
            geoFacilities.add(new Point2D(3803.0, -3218.0));
            geoFacilities.add(new Point2D(5099.0, -3879.0));
            geoFacilities.add(new Point2D(3733.0, -4819.0));
        }
        addPointOverlays(geoFacilities);
        mapView.invalidate();
    }

    /**
     * <p>
     * 绘制 供给中心点 集合
     * </p>
     */
    private void drawSupplyCenters() {
        List<Point2D> supplyCenters = new ArrayList<Point2D>();
        supplyCenters.add(new Point2D(2820.35101097629, -2358.0414663985171));// nodeID:1358
        supplyCenters.add(new Point2D(2909.4396668115278, -3647.0074300836109));// nodeID:2972
        supplyCenters.add(new Point2D(6940.6579024271468, -1627.6012900626256));// nodeID:663
        supplyCenters.add(new Point2D(6623.5972101719526, -2130.4887600981415));// nodeID:1161
        supplyCenters.add(new Point2D(5482.4979617984973, -4504.2328567816048));// nodeID:4337
        addPointOverlays(supplyCenters);
        mapView.invalidate();
    }

    /**
     * <p>
     * 绘制固定配送中心点
     * </p>
     */
    private void drawCentersPoints() {
        if (geoCenters.size() < 1) {
            geoCenters.add(new Point2D(3720, -4822.0));
            geoCenters.add(new Point2D(5089.0, -3865.0));
            geoCenters.add(new Point2D(3801.0, -3220.0));
        }
        addPointOverlays(geoCenters);
        mapView.invalidate();
    }

    private void addPointOverlays(List<Point2D> points) {
        if (points == null) {
            return;
        }
        Drawable drawable = getResources().getDrawable(R.drawable.point_marker);
        Bitmap pointBitMap = null;
        if (drawable instanceof BitmapDrawable) {
            pointBitMap = ((BitmapDrawable) drawable).getBitmap();
        }
        for (int i = 0; i < points.size(); i++) {
            PointOverlay pointOverlay = new PointOverlay(points.get(i), NetworkAnalystDemo.this);
            pointOverlay.setBitmap(pointBitMap);
            mapView.getOverlays().add(pointOverlay);
        }
    }

    /**
     * <p>
     * 判断参数是否合法，不合法展示提示信息
     * </p>
     * @return
     */
    boolean checkAndShowMessage() {
        if (drawStatic == 3) {
            if (geoPoints.size() != 1) {
                Toast.makeText(this, "EventPoint must be one point!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (drawStatic == 2 || drawStatic == 5) {
            if (geoPoints.size() < 1) {
                Toast.makeText(this, "the point at least has one!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (drawStatic != 4) {
            if (geoPoints.size() < 2) {
                Toast.makeText(this, "The numbers of point must be large to one!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    /**
     * 触屏Overlay
     */
    class TouchOverlay extends Overlay {
        @Override
        public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                isAddPoint = true;
                touchDownX = Math.round(event.getX());
                touchDownY = Math.round(event.getY());
                break;
            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                int x = Math.round(event.getX());
                int y = Math.round(event.getY());
                if (Math.abs(x - touchDownX) > 4 || Math.abs(y - touchDownY) > 4) {
                    isAddPoint = false;// 平移不加入该点
                }
                break;
            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                if (isAddPoint) {
                    touchX = Math.round(event.getX());
                    touchY = Math.round(event.getY());
                    // 记录点击位置
                    Point2D touchGeoPoint = mapView.getProjection().fromPixels(touchX, touchY);
                    mapView.getOverlays().add(new PointOverlay(touchGeoPoint, NetworkAnalystDemo.this));
                    mapView.invalidate();
                    geoPoints.add(touchGeoPoint);
                }
                isAddPoint = true;
                break;
            }
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        mapView.stopClearCacheTimer();// 停止和销毁 清除运行时服务器中缓存瓦片的定时器。
        mapView.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // 重写onBackPressed，将字段networkAnalystDialog必须置为null，以保证消除之前的引用
        networkAnalystDialog.dismiss();
        networkAnalystDialog = null;
        super.onBackPressed();
    }

}
