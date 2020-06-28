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
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.imobilelite.spatialAnalyst.DatasetOverlayAnalystResult;
import com.supermap.imobilelite.spatialAnalyst.GeometryOverlayAnalystResult;
import com.supermap.imobilelite.spatialsamples.util.SpatialAnalystUtil;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;

/**
 * <p>
 * 叠加分析Demo
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class OverlayAnalystDemo extends Activity {
    protected static String Log_TAG = "com.supermap.android.samples.OverlayAnalystDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-jingjin/rest/maps/京津地区地图";
    protected static final String DEFAULT_ANALYST_URL = "/spatialanalyst-sample/restjsr/spatialanalyst";
    protected MapView mapView;
    private LayerView baseLayerView;
    private Bundle bundle;
    private String spatialUrl;
    private List<PolygonOverlay> polygonOverlays = new ArrayList<PolygonOverlay>();
    private List<PolygonOverlay> geoOverlays = new ArrayList<PolygonOverlay>();
    // README DIALOG，值统一定为9
    private static final int README_DIALOG = 9;
    private boolean isanalyst = true; // true执行分析，false执行几何对象复原
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
        mapView.getController().setZoom(1);
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);

        // Readme对话框
        PreferencesService service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("OverlayAnalystDemo");
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
        // 给待叠加的几何面对象绘制风格
        geoOverlay1();
        geoOverlay2();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 创建叠加分析菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.geooverlay_text);
        // menu.add(0, 2, 0, R.string.overlay_text);
        return true;
    }

    public void isAnalyst(MenuItem item) {
        if (isanalyst) {
            clearOverlayer();
            cleargeoOverlayer();
            showGeoOverlayAnalystResult(SpatialAnalystUtil.excuteGeoOverlay(spatialUrl));
            // 更改菜单名称为几何对象复原
            isanalyst = false;
            item.setTitle(R.string.geooverlay_text1);
        } else {
            clearOverlayer();
            // 给待叠加的几何面对象绘制风格
            geoOverlay1();
            geoOverlay2();
            // 更改菜单名称为几何对象求交
            isanalyst = true;
            item.setTitle(R.string.geooverlay_text);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击菜单触发事件
        case 1:// 几何对象叠加
            isAnalyst(item);
            break;
        // case 2: //数据集叠加
        // clearOverlayer();
        // showOverlayAnalystResult(SpatialAnalystUtil.excuteOverlay(spatialUrl));
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "OverlayAnalystDemo");
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
            readmeDialog.setReadmeText(getResources().getString(R.string.overlay_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

    /**
     * 数据集叠加分析结果高亮显示
     */
    public void showOverlayAnalystResult(DatasetOverlayAnalystResult result) {
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
            PolygonOverlay polygonOverlay = new PolygonOverlay(SpatialAnalystUtil.getPolygonPaintRed());
            mapView.getOverlays().add(polygonOverlay);
            polygonOverlays.add(polygonOverlay);
            polygonOverlay.setData(geoPointList);
        }
        this.mapView.invalidate();
    }

    /**
     * <p>
     * 待叠加分析几何对象1绘制面风格
     * </p>
     */
    public void geoOverlay1() {
        // 构造几何对象
        List<Point2D> points = new ArrayList<Point2D>();
        points.add(new Point2D(117.0, 40.01));
        points.add(new Point2D(117.61, 40.06));
        points.add(new Point2D(117.66, 39.54));
        points.add(new Point2D(116.9, 39.57));
        PolygonOverlay overlay = new PolygonOverlay(SpatialAnalystUtil.getPolygonPaintBlue());
        mapView.getOverlays().add(overlay);
        geoOverlays.add(overlay);
        overlay.setData(points);
        overlay.setShowPoints(false);
    }

    /**
     * <p>
     * 待叠加分析几何对象2绘制面风格
     * </p>
     */
    public void geoOverlay2() {
        // 构造几何对象
        List<Point2D> points = new ArrayList<Point2D>();
        points.add(new Point2D(117.34, 39.84));
        points.add(new Point2D(117.9, 39.81));
        points.add(new Point2D(117.81, 39.26));
        points.add(new Point2D(117.22, 39.24));
        PolygonOverlay overlay = new PolygonOverlay(SpatialAnalystUtil.getPolygonPaintBlue());
        mapView.getOverlays().add(overlay);
        geoOverlays.add(overlay);
        overlay.setData(points);
        overlay.setShowPoints(false);
    }

    /**
     * 几何对象叠加分析结果高亮显示
     */
    public void showGeoOverlayAnalystResult(GeometryOverlayAnalystResult result) {
        if (result == null || result.resultGeometry == null) {
            Toast.makeText(this, "分析结果为空!", Toast.LENGTH_LONG).show();
            return;
        }
        // 存储查询记录的几何对象的点
        List<List<Point2D>> pointsLists = new ArrayList<List<Point2D>>();
        Geometry geometry = result.resultGeometry;
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

        // 把所有查询的几何对象都高亮显示
        for (int m = 0; m < pointsLists.size(); m++) {
            List<Point2D> geoPointList = pointsLists.get(m);
            PolygonOverlay polygonOverlay = new PolygonOverlay(SpatialAnalystUtil.getPolygonPaintRed());
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

    /**
     * <p>
     * 清空几何对象的Overlay，地图刷新
     * </p>
     */
    public void cleargeoOverlayer() {
        if (geoOverlays.size() != 0) {
            mapView.getOverlays().remove(geoOverlays);
            geoOverlays.clear();
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
        // 重写onBackPressed，将字段networkAnalystDialog必须置为null，以保证消除之前的引用
        super.onBackPressed();
    }

}
