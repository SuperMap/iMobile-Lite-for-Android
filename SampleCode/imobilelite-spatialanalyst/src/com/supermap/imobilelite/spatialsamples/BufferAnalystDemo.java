package com.supermap.imobilelite.spatialsamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.spatialAnalyst.DatasetBufferAnalystResult;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.imobilelite.spatialsamples.R;
import com.supermap.imobilelite.spatialsamples.util.SpatialAnalystUtil;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 缓冲区分析Demo
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class BufferAnalystDemo extends Activity {
    protected static String Log_TAG = "com.supermap.android.samples.BufferAnalystDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-changchun/rest/maps/长春市区图";
    protected static final String DEFAULT_ANALYST_URL = "/spatialanalyst-changchun/restjsr/spatialanalyst";
    protected MapView mapView;
    private LayerView baseLayerView;
    private Bundle bundle;
    private String spatialUrl;
    private List<PolygonOverlay> polygonOverlays = new ArrayList<PolygonOverlay>();
    // README DIALOG，值统一定为9
    private static final int README_DIALOG = 9;
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
            spatialUrl = DEFAULT_URL + DEFAULT_ANALYST_URL;
        } else {
            mapUrl = serviceUrl + DEFAULT_MAP_URL;
            spatialUrl = serviceUrl + DEFAULT_ANALYST_URL;
        }
        baseLayerView = new LayerView(this, mapUrl);
        mapView.addLayer(baseLayerView);
        mapView.getController().setZoom(4);
        // 设置中心点
        Point2D point = new Point2D(5106.319287022352, -3337.3849141502124);
        mapView.getController().setCenter(point);
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        // Readme对话框
        PreferencesService service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("BufferAnalystDemo");
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

        // 等待一段时间让地图初始化完成
        SystemClock.sleep(2000);
        // 给待缓冲的线绘制风格
        geoLineOverlay();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 创建缓冲区分析菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.buffer_text);
        menu.add(0, 2, 0, R.string.clear);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击缓冲区分析 菜单触发事件
        case 1:// 缓冲区分析
            clearOverlayer();
            showBufferAnalystResult(SpatialAnalystUtil.excuteBuffer(spatialUrl));
            break;
        case 2:// 清除
            clearOverlayer();
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
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "BufferAnalystDemo");
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
            readmeDialog.setReadmeText(getResources().getString(R.string.buffer_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

    /**
     * <p>
     * 需要缓冲的线绘制风格
     * </p>
     */
    public void geoLineOverlay() {
        // 构造几何对象
        List<Point2D> points = new ArrayList<Point2D>();
        points.add(new Point2D(4933.319287022352, -3337.3849141502124));
        points.add(new Point2D(4960.9674060199022, -3349.3316322355736));
        points.add(new Point2D(5006.0235999418364, -3358.8890067038628));
        points.add(new Point2D(5075.3145648369318, -3378.0037556404409));
        points.add(new Point2D(5305.19551436013, -3376.9669111768926));
        LineOverlay lineOverlay = new LineOverlay(SpatialAnalystUtil.getLinePaintBlue());
        mapView.getOverlays().add(lineOverlay);
        lineOverlay.setData(points);
        lineOverlay.setShowPoints(false);
    }

    /**
     * 分析结果高亮显示
     */
    public void showBufferAnalystResult(DatasetBufferAnalystResult result) {
        if (result == null || result.recordset == null || result.recordset.features == null) {
            Toast.makeText(this, "分析结果为空!", Toast.LENGTH_LONG).show();
            return;
        }
        // 存储查询记录的几何对象的点
        List<List<Point2D>> pointsLists = new ArrayList<List<Point2D>>();
        for (int i = 0; i < result.recordset.features.length; i++) {
            Feature feature = result.recordset.features[i];
            Geometry geometry = feature.geometry;
            Log.d("Test info ", "feature.geometry points =" + geometry.points.length);
            List<Point2D> geoPoints = SpatialAnalystUtil.getPiontsFromGeometry(geometry);
            if (geometry.parts.length > 1) {
                int num = 0;
                for (int j = 0; j < geometry.parts.length; j++) {
                    int count = geometry.parts[j];
                    List<Point2D> partList = geoPoints.subList(num, num + count);
                    pointsLists.add(partList);
                    num = num + count;
                }
            } else {
                pointsLists.add(geoPoints);
            }
        }
        // 把所有查询的几何对象都高亮显示
        for (int m = 0; m < pointsLists.size(); m++) {
            List<Point2D> geoPointList = pointsLists.get(m);
            PolygonOverlay polygonOverlay = new PolygonOverlay(SpatialAnalystUtil.getPolygonPaintBlue());
            mapView.getOverlays().add(polygonOverlay);
            polygonOverlays.add(polygonOverlay);
            polygonOverlay.setData(geoPointList);
            polygonOverlay.setShowPoints(false);
        }
        this.mapView.invalidate();
    }

    /**
     * <p>
     * 清空Overlay，地图刷新
     * </p>
     */
    public void clearOverlayer() {
        if (polygonOverlays.size() != 0) {
            mapView.getOverlays().remove(polygonOverlays);
            polygonOverlays.clear();
        }
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
        super.onBackPressed();
    }

}
