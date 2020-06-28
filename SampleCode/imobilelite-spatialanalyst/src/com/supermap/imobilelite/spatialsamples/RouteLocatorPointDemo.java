package com.supermap.imobilelite.spatialsamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PointOverlay;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.imobilelite.spatialAnalyst.RouteLocatorResult;
import com.supermap.imobilelite.spatialsamples.util.SpatialAnalystUtil;
import com.supermap.services.components.MapException;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.QueryParameter;
import com.supermap.services.components.commontypes.QueryParameterSet;
import com.supermap.services.components.commontypes.QueryResult;

/**
 * <p>
 * 里程定点Demo
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class RouteLocatorPointDemo extends Activity {
    protected static String Log_TAG = "com.supermap.android.samples.RouteLocatorPointDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-changchun/rest/maps/长春市区图";
    protected static final String DEFAULT_ANALYST_URL = "/spatialanalyst-sample/restjsr/spatialanalyst";
    protected MapView mapView;
    protected LayerView baseLayerView;
    protected Bundle bundle;
    protected String spatialUrl;
    protected Geometry routeObj;
    protected LineOverlay lineOverlay;
    protected PointOverlay pointOverlay;
    protected static final int ROUTELOCATOR_DIALOG = 0;
    // README DIALOG，值统一定为9
    protected static final int README_DIALOG = 9;
    protected RouteLocatorDialog routeLocatorDialog;
    // 存放查询结果
    protected List<QueryResult> queryResults = new ArrayList<QueryResult>();
    protected LineOverlay locatorLineOverLay;
    protected int titleBarHeight;
    protected Button helpBtn;
    protected double maxM;

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
        mapView.post(new Runnable() {
            public void run() {
                titleBarHeight = initHeight();

            }
        });
        lineOverlay = new LineOverlay(SpatialAnalystUtil.getLinePaintRed());
        pointOverlay = new PointOverlay(SpatialAnalystUtil.getPointPaint());
        locatorLineOverLay = new LineOverlay(SpatialAnalystUtil.getLinePaintBlue());
        // 创建里程定点分析操作提示窗口对象
        routeLocatorDialog = new RouteLocatorDialog(this, R.style.dialogTheme);
        // 设置支持焦点不在窗口上时，父视图能够响应焦点事件
        routeLocatorDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        // Readme对话框
        PreferencesService service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("RouteLocatorPointDemo");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
        helpBtn = (Button) findViewById(R.id.button_help);
        helpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(README_DIALOG);
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 计算标题栏的高度
     * @return
     */
    private int initHeight() {
        Rect rect = new Rect();
        Window window = getWindow();
        mapView.getWindowVisibleDisplayFrame(rect);
        // 状态栏的高度
        int statusBarHight = rect.top;
        // 标题栏跟状态栏的总体高度
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // 标题栏的高度
        int titleBarHeight = contentViewTop - statusBarHight;
        return titleBarHeight;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 创建里程定点菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.routeLocatorPoint_text);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击里程定点 菜单触发事件
            case 1:// 弹出对话框
                showDialog(ROUTELOCATOR_DIALOG);
                routeLocatorDialog.getEndPointET().setVisibility(View.GONE);
                routeLocatorDialog.getEndPointTV().setVisibility(View.GONE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ROUTELOCATOR_DIALOG:
                if (routeLocatorDialog != null) {
                    return routeLocatorDialog;
                }
                break;
            case README_DIALOG:
                Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "RouteLocatorPointDemo");
                return dialog;
            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case ROUTELOCATOR_DIALOG:
                if (routeLocatorDialog != null) {
                    Log.d(Log_TAG, "routelocatorDialog onPrepareDialog!");
                }
                break;
            case README_DIALOG:
                ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
                readmeDialog.setReadmeText(getResources().getString(R.string.routelocatorpoint_readme));
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
            Log.w(Log_TAG, "Query error！", e);
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
        // 查询结果中的geometry是一个route对象，其中的Point2D是PointWithMeasure对象，包含x、y和measure三个值
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
        maxM = SpatialAnalystUtil.calculatemaxM(geoPoints);
        routeLocatorDialog.getStartPointET().setEnabled(true);
        routeLocatorDialog.getEndPointET().setEnabled(true);
        this.mapView.invalidate();
    }

    // 定位点
    public void LocatorPoint() {
        double measure = routeLocatorDialog.getMeasure();
        if (routeObj == null || routeObj.points == null) {
            Toast.makeText(this, "请通过查询获取路由对象!", Toast.LENGTH_LONG).show();
            return;
        }
        RouteLocatorResult result = SpatialAnalystUtil.RouteLocatorPoint(spatialUrl, routeObj, measure);
        if (result == null || result.resultGeometry == null || result.resultGeometry.points == null) {
            Toast.makeText(this, "里程定位点失败!", Toast.LENGTH_LONG).show();
            return;
        }
        Point2D point = new Point2D(result.resultGeometry.points[0].x, result.resultGeometry.points[0].y);
        mapView.getOverlays().add(pointOverlay);
        pointOverlay.setData(point);
        mapView.invalidate();

    }

    // 定位线
    public void LocatorLine() {
        double startMeasure = routeLocatorDialog.getMeasure();
        double endMeasure = routeLocatorDialog.getEndMeasure();
        if (routeObj == null || routeObj.points == null) {
            Toast.makeText(this, "请通过查询获取路由对象!", Toast.LENGTH_LONG).show();
            return;
        }
        RouteLocatorResult result = SpatialAnalystUtil.RouteLocatorLine(spatialUrl, routeObj, startMeasure, endMeasure);
        if (result == null || result.resultGeometry == null || result.resultGeometry.points == null) {
            Toast.makeText(this, "里程定位线失败!", Toast.LENGTH_LONG).show();
            return;
        }
        List<Point2D> geoPoints = SpatialAnalystUtil.getPiontsFromGeometry(result.resultGeometry);
        mapView.getOverlays().add(locatorLineOverLay);
        locatorLineOverLay.setData(geoPoints);
        locatorLineOverLay.setShowPoints(false);
        mapView.invalidate();

    }

    // 清除
    public void clear() {
        lineOverlay.setData(new ArrayList<Point2D>());
        pointOverlay.setData(new Point2D());
        locatorLineOverLay.setData(new ArrayList<Point2D>());
        routeObj = null;
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
        // 重写onBackPressed，将字段routelocatorDialog必须置为null，以保证消除之前的引用
        queryResults.clear();
        routeLocatorDialog.dismiss();
        routeLocatorDialog = null;
        super.onBackPressed();
    }

}
