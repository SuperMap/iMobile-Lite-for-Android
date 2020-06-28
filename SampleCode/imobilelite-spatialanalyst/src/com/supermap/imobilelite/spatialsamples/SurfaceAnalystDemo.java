package com.supermap.imobilelite.spatialsamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
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
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.imobilelite.spatialAnalyst.SurfaceAnalystResult;
import com.supermap.imobilelite.spatialsamples.util.SpatialAnalystUtil;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 表面分析demo，用于展示提取等值线。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class SurfaceAnalystDemo extends Activity {
    protected static String Log_TAG = "com.supermap.android.samples.SurfaceAnalystDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-temperature/rest/maps/全国温度变化图";
    protected static final String DEFAULT_ANALYST_URL = "/spatialanalyst-sample/restjsr/spatialanalyst";
    protected MapView mapView;
    private LayerView baseLayerView;
    private Bundle bundle;
    private String spatialUrl;
    private List<LineOverlay> lineOverlays = new ArrayList<LineOverlay>();
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
        mapView.getController().setZoom(2);
        // 设置中心点
        mapView.getController().setCenter(new Point2D(531762, 3580330));
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);

        // Readme对话框
        PreferencesService service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("SurfaceAnalystDemo");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 创建表面分析菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.isoline_text);
        menu.add(0, 2, 0, R.string.clear);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击表面分析 菜单触发事件
        case 1:// 提取等值线
            clearOverlayer();
            showSpatialAnalystResult(SpatialAnalystUtil.excuteIsoline(spatialUrl));
            break;
        case 2:// 清除
            clearOverlayer();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "SurfaceAnalystDemo");
            return dialog;
        default:
            break;
        }
        return super.onCreateDialog(id);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
        case README_DIALOG:
            ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
            readmeDialog.setReadmeText(getResources().getString(R.string.isoline_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

    /**
     * 分析结果高亮显示
     */
    public void showSpatialAnalystResult(SurfaceAnalystResult result) {
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
            LineOverlay lineOverlay = new LineOverlay(SpatialAnalystUtil.getLinePaintBlue());
            mapView.getOverlays().add(lineOverlay);
            lineOverlays.add(lineOverlay);
            lineOverlay.setData(geoPointList);
            lineOverlay.setShowPoints(false);
        }
        this.mapView.invalidate();
    }

    /**
     * <p>
     * 清空Overlay，地图刷新
     * </p>
     */
    public void clearOverlayer() {
        if (lineOverlays.size() != 0) {
            mapView.getOverlays().remove(lineOverlays);
            lineOverlays.clear();
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
