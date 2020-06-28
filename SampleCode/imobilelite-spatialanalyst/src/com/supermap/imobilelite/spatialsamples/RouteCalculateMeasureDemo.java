package com.supermap.imobilelite.spatialsamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PointOverlay;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.imobilelite.spatialAnalyst.RouteCalculateMeasureResult;
import com.supermap.imobilelite.spatialsamples.util.SpatialAnalystUtil;
import com.supermap.services.components.MapException;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.QueryParameter;
import com.supermap.services.components.commontypes.QueryParameterSet;
import com.supermap.services.components.commontypes.QueryResult;

/**
 * <p>
         * 点定里程Demo。用于展示基于路由对象计算指定点的 M 值
         * </p>
         * @author ${Author}
        * @version ${Version}
        *
        */
public class RouteCalculateMeasureDemo extends Activity {
    protected static String Log_TAG = "com.supermap.android.samples.RouteCalculateMeasureDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-changchun/rest/maps/长春市区图";
    protected static final String DEFAULT_ANALYST_URL = "/spatialanalyst-sample/restjsr/spatialanalyst";
    protected MapView mapView;
    private LayerView baseLayerView;
    private Bundle bundle;
    private String spatialUrl;
    private TouchOverlay touchOverlay;
    private Geometry routeObj;
    private Point2D gp;
    private EditText text;
    private LineOverlay lineOverlay;
    private PointOverlay pointOverlay;
    // 在屏幕上指定查询点,默认为true，平移时设为false
    private boolean addPoint = true;
    // 存放查询结果
    private List<QueryResult> queryResults = new ArrayList<QueryResult>();
    // README DIALOG，值统一定为9
    private static final int README_DIALOG = 9;
    private Button helpBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_calculate_measure_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        bundle = this.getIntent().getExtras();
        // 设置访问地图的URL
        String serviceUrl = bundle.getString("service_url");
        String mapUrl = "";
        if (serviceUrl == null || "".equals(serviceUrl)) {
            mapUrl = DEFAULT_URL + DEFAULT_MAP_URL;
            spatialUrl = DEFAULT_URL + DEFAULT_ANALYST_URL;
        } else {
            mapUrl = serviceUrl + DEFAULT_MAP_URL;
            spatialUrl = serviceUrl + DEFAULT_ANALYST_URL;
        }
        baseLayerView = new LayerView(this, mapUrl);
        mapView.addLayer(baseLayerView);
        mapView.getController().setZoom(1);
        // 设置中心点
        mapView.getController().setCenter(new Point2D(4503.6240321526, -3861.911472192499));
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        touchOverlay = new TouchOverlay();
        text = (EditText) this.findViewById(R.id.editText);
        lineOverlay = new LineOverlay(SpatialAnalystUtil.getLinePaintRed());
        pointOverlay = new PointOverlay(SpatialAnalystUtil.getPointPaint());
        routeObj = new Geometry();

        // Readme对话框
        PreferencesService service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("RouteCalculateMeasureDemo");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
        helpBtn =(Button)findViewById(R.id.btn_help);
        helpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(README_DIALOG);
            }
        });
        // 去除多余的键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 创建表面分析菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.queryRouteObj_text);
        menu.add(0, 2, 0, R.string.assignQueryPoint_text);
        menu.add(0, 3, 0, R.string.clear);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击表面分析 菜单触发事件
            case 1:// 查询路由对象
            	final ProgressDialog  progressDoalog = new ProgressDialog(this);
            	progressDoalog.setMessage("正在请求数据");
            	progressDoalog.setCanceledOnTouchOutside(false);
            	progressDoalog.show();
            	
            	new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 final com.supermap.services.components.commontypes.QueryResult queryResult =creatRouteObj();
						 runOnUiThread(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(progressDoalog!=null){
									progressDoalog.cancel();
								}
								if(queryResult!=null){
									showQueryResult(queryResult);
								} else{
									Toast.makeText(RouteCalculateMeasureDemo.this, "请求失败", Toast.LENGTH_LONG).show();
								}
							}
							 
						 });
					}
				}).start();
                break;
            case 2:// 指定查询点
                if (!mapView.getOverlays().contains(touchOverlay)) {
                    mapView.getOverlays().add(touchOverlay);
                }
                break;
            case 3:// 清除
                clear();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case README_DIALOG:
                Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "RouteCalculateMeasureDemo");
                return dialog;
            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case README_DIALOG:
                ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
                readmeDialog.setReadmeText(getResources().getString(R.string.routecalculatemeasure_readme));
                break;
            default:
                break;
        }
        super.onPrepareDialog(id, dialog);
    }

    // 通过SQL查询路由对象
    public QueryResult creatRouteObj() {
        if (!queryResults.isEmpty()) {
            return queryResults.get(0);
        }
        QueryResult queryResult = null;
        QueryParameterSet queryParameters = new QueryParameterSet();
        QueryParameter[] queryLayerParams = new QueryParameter[1];
        queryLayerParams[0] = new QueryParameter();
        // 指定要查询的图层名称
        queryLayerParams[0].name = "RouteDT_road@Changchun";
        queryLayerParams[0].attributeFilter = "RouteID=1690";
        queryParameters.queryParams = queryLayerParams;

        try {
            queryResult = mapView.getMap().queryBySQL("长春市区图", queryParameters);
            queryResults.add(queryResult);
        } catch (MapException e) {
            Log.w(Log_TAG, "Query error", e);
        }
        return queryResult;
    }

    // 将查询结果绘制在地图上
    public void showQueryResult(QueryResult queryResult) {
        if (queryResult == null || queryResult.recordsets == null || queryResult.recordsets[0].features == null) {
            Toast.makeText(this, "查询结果为空!", Toast.LENGTH_LONG).show();
            return;
        } else {
            Toast.makeText(this, "查询成功!", Toast.LENGTH_LONG).show();
        }
        // 存储查询记录的几何对象的点
        List<List<Point2D>> pointsLists = new ArrayList<List<Point2D>>();
        routeObj = queryResult.recordsets[0].features[0].geometry;
        List<Point2D> geoPoints = SpatialAnalystUtil.getPiontsFromGeometry(routeObj);
        if (routeObj.parts.length > 1) {
            int num = 0;
            for (int j = 0; j < routeObj.parts.length; j++) {
                int count = routeObj.parts[j];
                List<Point2D> partList = geoPoints.subList(num, num + count);
                pointsLists.add(partList);
                num = num + count;
            }
        } else {
            pointsLists.add(geoPoints);
        }

        // 把所有查询的几何对象都高亮显示
        for (int m = 0; m < pointsLists.size(); m++) {
            List<Point2D> geoPointList = pointsLists.get(m);
            mapView.getOverlays().add(lineOverlay);
            lineOverlay.setData(geoPointList);
            lineOverlay.setShowPoints(false);
        }
        this.mapView.getController().setCenter(SpatialAnalystUtil.calculateCenter(geoPoints));

        this.mapView.invalidate();
    }

    /**
     * <p>
     * 获取路由对象上指定点的M值z
     * </p>
     * @return
     */
    private void routeCaculateMeasure() {
        RouteCalculateMeasureResult result = null;
        if (routeObj == null || routeObj.points == null) {
            text.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "please get the RouteObject by query!", Toast.LENGTH_LONG).show();
            return;
        } else if (routeObj != null && gp != null) {
            result = SpatialAnalystUtil.RouteCalculateMeasure(spatialUrl, routeObj, gp);
            if (result == null) {
                Toast.makeText(this, "the result is null!", Toast.LENGTH_LONG).show();
            }
            if (result.succeed == true) {
                text.setText("查询获取的M值为：" + result.measure);

            } else {
                text.setText("计算指定点的M值失败，请确定点是否在路由对象上。");
            }
        }
    }

    /**
     * 选择地物触屏Overlay
     */
    class TouchOverlay extends Overlay {
        private float mTouchX;
        private float mTouchY;

        @Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (addPoint) {
                    // 记录点击位置
                    gp = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
                    mapView.getOverlays().add(pointOverlay);
                    pointOverlay.setData(gp);
                    mapView.invalidate();
                    text.setVisibility(View.VISIBLE);
                    text.setTextSize(15);
                    routeCaculateMeasure();
                }
                // 触碰抬起时，取消点不被添加状态
                addPoint = true;
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mTouchX = event.getX();
                mTouchY = event.getY();

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float x = Math.abs(event.getX() - mTouchX);
                float y = Math.abs(event.getY() - mTouchY);
                // move发生的位移容限为5个像素，小于5当做没有平移，因为手指的面积比较大
                if ((x * x + y * y) < 25) {
                    addPoint = true;
                } else {
                    addPoint = false;
                }
            }
            return false;
        }
    }


    /**
     * <p>
     * 清除所有对象
     * </p>
     */
    private void clear() {
        mapView.getOverlays().remove(touchOverlay);
        lineOverlay.setData(new ArrayList<Point2D>());
        pointOverlay.setData(new Point2D());
        routeObj = null;
        text.setVisibility(View.INVISIBLE);
        text.setText("");
        mapView.invalidate();
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
        queryResults.clear();
        super.onBackPressed();
    }

}
