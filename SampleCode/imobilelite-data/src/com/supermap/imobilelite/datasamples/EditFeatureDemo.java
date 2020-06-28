package com.supermap.imobilelite.datasamples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

import com.supermap.imobilelite.data.EditFeatureAction;
import com.supermap.imobilelite.data.EditFeaturesResult;
import com.supermap.imobilelite.data.GetFeaturesResult;
import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Overlay;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.datasamples.R;
import com.supermap.imobilelite.datasamples.util.DataUtil;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.services.components.commontypes.Feature;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.GeometryType;

/**
 * <p>
 * 地物编辑示例中显示地图的视图，该视图显示底图和提供地物编辑菜单项及响应触发事件。
 * 事件的响应为 弹出支持拖动的窗口EditFeatureDialog, 展示进行地物编辑操作提示信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class EditFeatureDemo extends Activity {
    protected static final String TAG = "com.supermap.android.samples.edit.EditFeatureDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-jingjin/rest/maps/京津地区土地利用现状图";
    protected static final String DEFAULT_EDIT_URL = "/data-jingjin/rest/data/datasources/Jingjin/datasets/Landuse_R";
    protected static final String DEFAULT_QUERY_URL = "/data-jingjin/rest/data";
    private static final int EDITFEATURE_DIALOG = 0;
    private static final int README_DIALOG = 9;
    protected MapView mapView;
    private LayerView baseLayerView;
    private PreferencesService service;
    private String editServiceUrl;
    private String queryServiceUrl;
    private EditFeatureDialog editFeatureDialog;
    private Bundle bundle;
    // 存放绘制线或面的点集合
    private List<Point2D> geoPoints = new ArrayList<Point2D>();
    // 默认的绘制面对象
    private PolygonOverlay polygonOverlay;
    private List<PolygonOverlay> polygonOverlays = new ArrayList<PolygonOverlay>();
    // 增加地物触屏Overlay,用来获取点坐标
    private AddTouchOverlay touchOverlay;
    // 选择地物触屏Overlay，用来获取点坐标
    private SelectedTouchOverlay selectedtouchOverlay;
    // 触屏的x坐标
    private int touchX;
    // 触屏的y坐标
    private int touchY;
    // 存放要删除或修改的矢量要素 ID 号集合
    private int[] ids;
    private int id = -1;
    private Feature[] selectedFeatures;
    private int editStatus = -1;// 0代表添加一个地物，1代表更新一个要素地物，2代表删除一个要素，3代表更新一个要素属性，-1代表未编辑一个地物
    // 存放所有选中地物的点集合
    private Map<String, List<Point2D>> featureMap = new HashMap<String, List<Point2D>>();
    // 存放所有选中地物相应的 编辑控件 集合
    private List<EditFeatureAction> actionList = new ArrayList<EditFeatureAction>();
    private Button helpBtn;
    protected int titleBarHeight;
    private EditAndDelTouchOverlay editAndDelTouchOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        bundle = this.getIntent().getExtras();
        // 设置访问地图的URL和数据编辑的URL
        String serviceUrl = bundle.getString("service_url");
        String mapUrl = "";
        if (serviceUrl == null || "".equals(serviceUrl)) {
            mapUrl = DEFAULT_URL + DEFAULT_MAP_URL;
            editServiceUrl = DEFAULT_URL + DEFAULT_EDIT_URL;
            queryServiceUrl = DEFAULT_URL + DEFAULT_QUERY_URL;
        } else {
            mapUrl = serviceUrl + DEFAULT_MAP_URL;
            editServiceUrl = serviceUrl + DEFAULT_EDIT_URL;
            queryServiceUrl = serviceUrl + DEFAULT_QUERY_URL;
        }
        baseLayerView = new LayerView(this, mapUrl);
        baseLayerView.setCacheEnabled(false);
        baseLayerView.clearCache(false);

        mapView.addLayer(baseLayerView);
        mapView.getController().setZoom(2);
        // 设置中心点
        // mapView.getController().setCenter(new Point2D(116.391468, 39.904491));// 39.904491, 116.391468 0.0d, 0.0d
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        // 获取标题栏的高度
        mapView.post(new Runnable() {
            public void run() {
                titleBarHeight = initHeight();

            }
        });
        helpBtn = (Button) findViewById(R.id.button_help);
        helpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(README_DIALOG);
            }
        });

        // 创建地图编辑操作提示窗口对象
        editFeatureDialog = new EditFeatureDialog(this, R.style.dialogTheme);
        editFeatureDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        polygonOverlay = new PolygonOverlay(getPolygonPaint());
        mapView.getOverlays().add(polygonOverlay);
        touchOverlay = new AddTouchOverlay();
        selectedtouchOverlay = new SelectedTouchOverlay();
        editAndDelTouchOverlay = new EditAndDelTouchOverlay();
        service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("editFeature");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
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

    // 增加地物
    public void addGeometry() {
        editStatus = 0;
        polygonOverlay.setData(new ArrayList<Point2D>());
        clearPolygonOverlays();
        clearEditAction();
        // 添加PolygonOverlay绘面，地图刷新
        geoPoints.clear();
        mapView.setUseScrollEvent(false);
        ids = null;
        this.selectedFeatures = null;

        if (!mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().add(touchOverlay);
        }
        if (mapView.getOverlays().contains(selectedtouchOverlay)) {
            mapView.getOverlays().remove(selectedtouchOverlay);
        }
        mapView.invalidate();
    }

    // 结束编辑地物事件，向服务端提交请求
    public void commit() {
        // 提交增加地物事件请求
        if (editStatus == 0) {
            mapView.setUseScrollEvent(true);
            if (geoPoints.size() > 2) {
                geoPoints.add(geoPoints.get(0));
                com.supermap.services.components.commontypes.Point2D[] pts = new com.supermap.services.components.commontypes.Point2D[geoPoints.size()];
                for (int j = 0; j < geoPoints.size(); j++) {
                    pts[j] = new com.supermap.services.components.commontypes.Point2D(geoPoints.get(j).x, geoPoints.get(j).y);
                }
                Geometry geometry = new Geometry();
                geometry.points = pts;
                geometry.parts = new int[] { pts.length };
                geometry.type = GeometryType.REGION;
                Feature feature = new Feature();
                feature.geometry = geometry;
                Feature[] features = { feature };

                EditFeaturesResult addResult = DataUtil.excute_geoAdd(editServiceUrl, features);
                geoPoints.clear();
                polygonOverlay.setData(new ArrayList<Point2D>());
                if (addResult.succeed) {
                    Toast.makeText(this, "添加地物成功!", Toast.LENGTH_LONG).show();
                    mapView.getOverlays().remove(touchOverlay);
                    clearCache();
                } else {
                    mapView.invalidate();
                    Toast.makeText(this, "添加地物失败!", Toast.LENGTH_LONG).show();
                }
                editStatus = -1;
            } else {
                Toast.makeText(this, "添加的地物至少包含三个点!", Toast.LENGTH_LONG).show();
            }
        } else if (editStatus == 1) {
            // 提交更新地物事件请求
            if (featureMap != null && featureMap.size() > 0 && id != -1) {
                Feature tarFeature = null;
                for (int i = 0; i < selectedFeatures.length; i++) {
                    Feature feature = selectedFeatures[i];
                    if (feature != null && feature.getID() == id && feature.geometry != null && feature.geometry.points != null) {
                        // 需要更新geometry的点和parts
                        DataUtil.resetGeometry(feature, featureMap);
                        tarFeature = feature;
                    }
                }
                EditFeaturesResult delResult = DataUtil.excute_geoEdit(editServiceUrl, new Feature[] { tarFeature }, new int[] { id });
                ids = null;
                id = -1;
                this.selectedFeatures = null;
                clearPolygonOverlays();
                polygonOverlay.setData(new ArrayList<Point2D>());
                if (delResult.succeed) {
                    // todo清除缓存后刷新
                    Toast.makeText(this, "更新地物成功!", Toast.LENGTH_LONG).show();
                    clearCache();
                } else {
                    mapView.invalidate();
                    Toast.makeText(this, "更新地物失败!", Toast.LENGTH_LONG).show();
                }
                clearEditAction();
            } else {
                Toast.makeText(this, "请先选择地物!", Toast.LENGTH_LONG).show();
            }
            editStatus = -1;
        }
    }

    /**
     * <p>
     * 结束编辑状态，并清空编辑控件
     * </p>
     */
    private void clearEditAction() {
        for (int i = 0; i < actionList.size(); i++) {
            EditFeatureAction editFeatureAction = actionList.get(i);
            editFeatureAction.stopEditFeature();
        }
        featureMap.clear();
        actionList.clear();
    }

    /**
     * <p>
     * 结束编辑状态，并清空处理触碰事件Overlay
     * </p>
     */
    public void clearTouchOverlay() {
        mapView.setUseScrollEvent(true);
        mapView.getOverlays().remove(selectedtouchOverlay);
        mapView.getOverlays().remove(touchOverlay);
    }

    // 选择地物
    public void selectGeometry() {
        if (!mapView.getOverlays().contains(selectedtouchOverlay)) {
            mapView.getOverlays().add(selectedtouchOverlay);
        }
        if (mapView.getOverlays().contains(touchOverlay)) {
            mapView.getOverlays().remove(touchOverlay);
        }
        polygonOverlay.setData(new ArrayList<Point2D>());
        clearPolygonOverlays();
        ids = null;
        this.selectedFeatures = null;
        clearEditAction();
        editStatus = -1;
        mapView.setUseScrollEvent(true);
        geoPoints.clear();
        mapView.invalidate();
    }

    /**
     * <p>
     * 清除缓存，获取更新后的地图瓦片
     * </p>
     */
    private void clearCache() {
        baseLayerView.clearCache(false);
        try {
            Thread.currentThread().sleep(1500L);
        } catch (InterruptedException e) {
            Log.d(TAG, "currentThread sleep exception");
        }

        mapView.invalidate();
    }

    private void clearPolygonOverlays() {
        mapView.getOverlays().removeAll(polygonOverlays);
        polygonOverlays.clear();
    }

    // 删除地物
    public void deleteGeometry() {
        editStatus = 2;
        clearEditAction();
        mapView.setUseScrollEvent(true);
        clearPolygonOverlays();
        polygonOverlay.setData(new ArrayList<Point2D>());
        if (ids != null) {
            EditFeaturesResult delResult = DataUtil.excute_geoDel(editServiceUrl, ids);
            ids = null;
            this.selectedFeatures = null;
            editStatus = -1;
            if (delResult.succeed) {
                // 清除缓存后刷新
                Toast.makeText(this, "删除地物成功!", Toast.LENGTH_LONG).show();
                clearCache();
            } else {
                mapView.invalidate();
                Toast.makeText(this, "删除地物失败!", Toast.LENGTH_LONG).show();
            }
        } else {
            editStatus = -1;
            Toast.makeText(this, "请先选择地物!", Toast.LENGTH_LONG).show();
        }
    }

    // 编辑地物
    public void editGeometry() {
        editStatus = 1;
        clearEditAction();
        mapView.setUseScrollEvent(true);
        if (ids != null && this.selectedFeatures != null) {
            // for (int i = 0; i < selectedFeatures.length; i++) {
            // Feature feature = selectedFeatures[i];
            // if (feature != null && feature.geometry != null && feature.geometry.points != null) {
            // List<Point2D> geoPoints = DataUtil.getPiontsFromGeometry(feature.geometry);
            // featureMap.put(String.valueOf(feature.getID()), geoPoints);
            // EditFeatureAction editFeatureAction = new EditFeatureAction(mapView);
            // editFeatureAction.doEditFeature(geoPoints);
            // actionList.add(editFeatureAction);
            // }
            // }
            if (mapView.getOverlays().contains(touchOverlay)) {
                mapView.getOverlays().remove(touchOverlay);
            }
            if (mapView.getOverlays().contains(selectedtouchOverlay)) {
                mapView.getOverlays().remove(selectedtouchOverlay);
            }
            if (!mapView.getOverlays().contains(editAndDelTouchOverlay)) {
                mapView.getOverlays().add(editAndDelTouchOverlay);
            }
        } else {
            Toast.makeText(this, "请先选择地物!", Toast.LENGTH_LONG).show();
        }
    }

    // 属性编辑
    public void editField() {
        editStatus = 3;
        clearEditAction();
        mapView.setUseScrollEvent(true);

        if (ids != null && this.selectedFeatures != null && selectedFeatures.length > 0) {
            Feature feature = selectedFeatures[0];
            String[] fieldNames = feature.fieldNames;
            String[] fieldValues = feature.fieldValues;
            ids = null;
            this.selectedFeatures = null;
            // 清除高亮显示
            polygonOverlay.setData(new ArrayList<Point2D>());
            clearPolygonOverlays();
            editStatus = -1;
            if (fieldNames != null && fieldValues != null && fieldNames.length == fieldValues.length) {
                Intent intent = new Intent(this, ShowEditFieldActivity.class);
                Bundle sendBundle = new Bundle();
                sendBundle.putStringArray("fieldNames", fieldNames);
                sendBundle.putStringArray("fieldValues", fieldValues);
                sendBundle.putSerializable("feature", feature);
                sendBundle.putString("dataServiceUrl", editServiceUrl);
                intent.putExtras(sendBundle);
                startActivity(intent);
            }
        } else {
            editStatus = -1;
            Toast.makeText(this, "请先选择地物!", Toast.LENGTH_LONG).show();
        }
    }

    // 将选中的地物高亮显示
    public void showPolygonOverlay(GetFeaturesResult result) {
        if (result == null || result.features == null || result.features.length == 0) {
            Toast.makeText(this, "选择地物失败!", Toast.LENGTH_LONG).show();
            return;
        }
        selectedFeatures = result.features;
        ids = new int[result.features.length];
        // 存储查询记录的几何对象的点
        List<List<Point2D>> pointsLists = new ArrayList<List<Point2D>>();
        for (int i = 0; i < result.features.length; i++) {
            Feature feature = result.features[i];
            ids[i] = feature.getID();
            Log.d("ids", String.valueOf(ids[i]));
            Geometry geometry = feature.geometry;
            List<Point2D> geoPoints = DataUtil.getPiontsFromGeometry(geometry);
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
        if (pointsLists.size() > 0) {
            if (polygonOverlays == null) {
                polygonOverlays = new ArrayList<PolygonOverlay>();
            }
            for (int m = 0; m < pointsLists.size(); m++) {
                List<Point2D> geoPointList = pointsLists.get(m);
                PolygonOverlay polygonOverlay = new PolygonOverlay(getPolygonPaint());
                polygonOverlay.setData(geoPointList);
                mapView.getOverlays().add(polygonOverlay);
                polygonOverlays.add(polygonOverlay);
            }
            this.mapView.getOverlays().remove(selectedtouchOverlay);
            this.mapView.invalidate();
        } else {
            Toast.makeText(this, "选择地物失败!", Toast.LENGTH_LONG).show();
        }
    }

    // 绘面风格
    private Paint getPolygonPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setAlpha(50);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);

        return paint;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 创建 地图编辑 菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.editfeature_text);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击 地物编辑 菜单触发事件
        case 1:
            clearTouchOverlay();
            showDialog(EDITFEATURE_DIALOG);
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 给Overlay设置点集合，开始绘制对象，并刷新地图
     * 
     * @param overlay
     * @param gps
     */
    private void setOverlayData(Overlay overlay, List<Point2D> gps) {
        if (overlay == null) {
            return;
        }
        List<Point2D> geoPointList = new ArrayList<Point2D>();
        if (gps != null && gps.size() > 0) {
            copyList(gps, geoPointList);
        } else if (geoPoints.size() > 0) {
            copyList(geoPoints, geoPointList);
        }
        if (geoPointList.size() > 0) {
            if (overlay instanceof LineOverlay) {
                ((LineOverlay) overlay).setData(geoPointList);
            } else if (overlay instanceof PolygonOverlay) {
                ((PolygonOverlay) overlay).setData(geoPointList);
            }
            mapView.invalidate();
        }
    }

    private void copyList(List<Point2D> sourcegps, List<Point2D> targetgps) {
        for (int i = 0; i < sourcegps.size(); i++) {
            targetgps.add(new Point2D(sourcegps.get(i)));
        }
    }

    private void setOverlayData(Overlay overlay) {
        setOverlayData(overlay, null);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case EDITFEATURE_DIALOG:
            if (editFeatureDialog != null) {
                return editFeatureDialog;
            }
            break;
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "editFeature");
            return dialog;
        default:
            break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
        case EDITFEATURE_DIALOG:
            if (editFeatureDialog != null) {
                Log.d("iserver", "EditFeatureDemo onPrepareDialog!");
            }
            break;
        case README_DIALOG:
            ReadmeDialog readmeDialog = (ReadmeDialog) dialog;
            readmeDialog.setReadmeText(getResources().getString(R.string.editfeaturedemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

    @Override
    protected void onDestroy() {
        mapView.stopClearCacheTimer();// 停止和销毁 清除运行时服务器中缓存瓦片的定时器。
        geoPoints.clear();
        selectedFeatures = null;
        clearEditAction();
        mapView.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // 重写onBackPressed，将字段editFeatureDialog必须置为null，以保证消除之前的引用
        editFeatureDialog.dismiss();
        editFeatureDialog = null;
        geoPoints.clear();
        selectedFeatures = null;
        clearEditAction();
        super.onBackPressed();
    }

    /**
     * 选择地物触屏Overlay
     */
    class SelectedTouchOverlay extends Overlay {
        @Override
        public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
            if (editStatus < 0 && event.getAction() == MotionEvent.ACTION_UP) {
                touchX = Math.round(event.getX());
                touchY = Math.round(event.getY());
                // 记录点击位置
                Point2D touchPoint = mapView.getProjection().fromPixels(touchX, touchY);
                showPolygonOverlay(DataUtil.excute_geoSel(queryServiceUrl, touchPoint));
            }
            return false;
        }
    }

    /**
     * 编辑或删除地物触屏Overlay
     */
    class EditAndDelTouchOverlay extends Overlay {
        @Override
        public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
            if (selectedFeatures != null && event.getAction() == MotionEvent.ACTION_UP) {
                if (editStatus < 1) {// 选择和添加地物的时候不能触发，这样可能可以不用把mapView中的自己删除
                    return false;
                }
                touchX = Math.round(event.getX());
                touchY = Math.round(event.getY());
                // 记录点击位置
                Point2D touchPoint = mapView.getProjection().fromPixels(touchX, touchY);
                // 从查询结果地物中选择一个进行编辑，先选出包含 触碰点 的所有地物，再选择面积最小的高亮（保证被其他地物包含的地物可以被选中）
                List<List<Point2D>> list = findSelectGeometry(touchPoint, polygonOverlays);
                if (list != null && list.size() > 0) {
                    int index = findSmallPolygonIndex(list);
                    if (index < 0) {
                        return false;
                    }
                    Feature feature = findTarFeature(list.get(index));
                    if (editStatus == 1) {// 编辑地物
                        if (feature != null && feature.geometry != null && feature.geometry.points != null) {
                            List<Point2D> geoPoints = DataUtil.getPiontsFromGeometry(feature.geometry);
                            if (feature.geometry.parts.length > 1) {
                                int num = 0;
                                for (int j = 0; j < feature.geometry.parts.length; j++) {
                                    int count = feature.geometry.parts[j];
                                    List<Point2D> part = geoPoints.subList(num, num + count);
                                    // 拷贝一份很重要，这样才不会出现java.util.ConcurrentModificationException异常
                                    List<Point2D> partList = DataUtil.copyList(part);
                                    featureMap.put(String.valueOf(feature.getID()) + j, partList);
                                    EditFeatureAction editFeatureAction = new EditFeatureAction(mapView);
                                    editFeatureAction.doEditFeature(partList);
                                    actionList.add(editFeatureAction);
                                    num = num + count;
                                }
                            } else {
                                featureMap.put(String.valueOf(feature.getID()), geoPoints);
                                EditFeatureAction editFeatureAction = new EditFeatureAction(mapView);
                                editFeatureAction.doEditFeature(geoPoints);
                                actionList.add(editFeatureAction);
                            }
                        }
                    } else if (editStatus == 2) {// 用于处理只删除一个地物，目前会删除所有选中的地物，备用分支

                    } else if (editStatus == 3) {// 用于处理编辑想要编辑的要素属性，目前会编辑所有选中地物中的最后一个要素属性，备用分支

                    }
                    // 点选完，删除自己
                    mapView.getOverlays().remove(editAndDelTouchOverlay);
                } else {// 没有点选到查询出来的地物

                }
            }
            return true;
        }

        /**
         * <p>
         * 从选中的所有要素中找到包含list中所有点的要素，并初始化找到的要素id
         * </p>
         * @param list
         * @return
         */
        private Feature findTarFeature(List<Point2D> list) {
            if (selectedFeatures != null && list != null) {
                for (int i = 0; i < selectedFeatures.length; i++) {
                    Feature feature = selectedFeatures[i];
                    List<Point2D> geoPoints = DataUtil.getPiontsFromGeometry(feature.geometry);
                    if (geoPoints.containsAll(list)) {
                        id = feature.getID();
                        return feature;
                    }
                }
            }
            return null;
        }

        /**
         * <p>
         * 找到每个点数组构成的多边形中最小的
         * </p>
         * @param list
         * @return
         */
        private int findSmallPolygonIndex(List<List<Point2D>> list) {
            if (list != null && list.size() > 0) {
                if (list.size() == 1) {
                    return 0;
                }
                double MinArea = DataUtil.getArea(list.get(0));
                int index = 0;
                for (int i = 1; i < list.size(); i++) {
                    double area = DataUtil.getArea(list.get(i));
                    if (MinArea > area) {
                        MinArea = area;
                        index = i;
                    }
                }
                return index;
            }
            return -1;
        }

        /**
         * <p>
         * 找到包含 触碰点gp的所有多边形
         * </p>
         * @param gp
         * @param pls
         * @return
         */
        private List<List<Point2D>> findSelectGeometry(Point2D gp, List<PolygonOverlay> pls) {
            if (gp != null && pls != null) {
                List<List<Point2D>> lists = new ArrayList<List<Point2D>>();
                for (int i = 0; i < pls.size(); i++) {
                    List<Point2D> list = pls.get(i).getData();
                    if (DataUtil.contians(gp, list)) {
                        lists.add(list);
                    }
                }
                return lists;
            }
            return null;
        }
    }

    /**
     * 增加地物触屏Overlay
     */
    class AddTouchOverlay extends Overlay {
        @Override
        public boolean onTouchEvent(MotionEvent event, final MapView mapView) {
            if (editStatus == 0) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    touchX = Math.round(event.getX());
                    touchY = Math.round(event.getY());
                    // 记录点击位置
                    Point2D touchGeoPoint = mapView.getProjection().fromPixels(touchX, touchY);
                    if (!geoPoints.contains(touchGeoPoint)) {
                        geoPoints.add(touchGeoPoint);
                    }
                    setOverlayData(polygonOverlay);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {

                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // 给当前绘制面对象设置点集合 数据
                    List<Point2D> geoPointList = new ArrayList<Point2D>();
                    if (geoPoints.size() > 0) {
                        copyList(geoPoints, geoPointList);
                    }
                    int x = Math.round(event.getX());
                    int y = Math.round(event.getY());
                    Point2D touchGeoPoint = mapView.getProjection().fromPixels(x, y);
                    updatePoint(geoPointList, touchGeoPoint);
                    setOverlayData(polygonOverlay, geoPointList);
                    // mapView.invalidate();
                }
                return true;
            }
            return false;
        }

        private void updatePoint(List<Point2D> geoPointList, Point2D touchGeoPoint) {
            if (geoPointList.size() == geoPoints.size()) {
                geoPointList.add(touchGeoPoint);
            } else if (geoPointList.size() > geoPoints.size()) {
                geoPointList.remove(geoPointList.size() - 1);
                geoPointList.add(touchGeoPoint);
            }
        }
    }

}
