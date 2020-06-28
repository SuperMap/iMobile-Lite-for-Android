package com.supermap.imobilelite.trafficsamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.supermap.imobilelite.maps.DefaultItemizedOverlay;
import com.supermap.imobilelite.maps.ItemizedOverlay;
import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.LineOverlay;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.OverlayItem;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.samples.service.PreferencesService;
import com.supermap.imobilelite.trafficsamples.R;

/**
 * <p>
 * 交通换乘示例中显示地图的视图，该视图显示底图和提供路线搜索菜单项及触发事件。
 * 事件的响应为 把用户输入地址发送给交通换乘示例中 进行交通换乘分析视图StartTrafficTransferActivity。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class TrafficTransferAnalystDemo extends Activity {
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-changchun/rest/maps/长春市区图";
    protected static final String DEFAULT_ANALYST_URL = "/traffictransferanalyst-sample/restjsr/traffictransferanalyst/Traffic-Changchun";
    private static final int TRAFFICTRANSFER_ANALYST = 0;
    private static final int README_DIALOG = 9;
    private PreferencesService service;
    protected MapView mapView;
    private LayerView baseLayerView;
    protected LineOverlay lineOverlay = new LineOverlay();
    // 绘制风格
    private Paint paint = new Paint(1);
    private Bundle bundle;
    private DefaultItemizedOverlay overlayStart;
    private DefaultItemizedOverlay overlayEnd;
    private Drawable drawableStart;
    private Drawable drawableEnd;
    private boolean isOnNewIntent;
    private Button helpBtn;
    private String analystUrl;

    // private ClickedOverlay clickedOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demo);
        mapView = (MapView) this.findViewById(R.id.mapview);
        bundle = this.getIntent().getExtras();
        isOnNewIntent = false;
        // 设置访问地图的URL
        String serviceUrl = bundle.getString("service_url");
        String mapUrl = "";
        if (serviceUrl == null || "".equals(serviceUrl)) {
            mapUrl = DEFAULT_URL + DEFAULT_MAP_URL;
            analystUrl = DEFAULT_URL + DEFAULT_ANALYST_URL;
        } else {
            mapUrl = serviceUrl + DEFAULT_MAP_URL;
            analystUrl = serviceUrl + DEFAULT_ANALYST_URL;
        }
        baseLayerView = new LayerView(this, mapUrl);

        mapView.addLayer(baseLayerView);
        mapView.getController().setZoom(2);
        // 设置中心点
        // mapView.getController().setCenter(new Point2D(116.391468, 39.904491));// 39.904491, 116.391468 0.0d, 0.0d
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);

        // 设置绘制风格paint，默认风格
        paint.setColor(Color.argb(200, 0, 0, 250));
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        lineOverlay.setLinePaint(paint);
        drawableStart = getResources().getDrawable(R.drawable.start_sign);
        overlayStart = new DefaultItemizedOverlay(drawableStart);

        drawableEnd = getResources().getDrawable(R.drawable.end_sign);
        overlayEnd = new DefaultItemizedOverlay(drawableEnd);

        service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("TrafficTransferAnalyst");
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
        super.onCreateOptionsMenu(menu);// 创建 路线搜索菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.traffic_transfer_analyst);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击 路线搜索 菜单触发事件
        case 1:
            startNextActivity();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <p>
     * 点击 路线搜索 菜单 ，保存用户输入地址并把地址推送到下一个视图
     * </p>
     */
    void startNextActivity() {
        Intent intent = new Intent(this, StartTrafficTransferActivity.class);
        Bundle sendBundle = new Bundle();
        // 设置访问交通换乘实例的URL
        sendBundle.putString("url", analystUrl);
        intent.putExtras(sendBundle);
        // startActivityForResult(intent, TRAFFICTRANSFER_ANALYST);
        startActivity(intent);
    }

    /**
     * Overlay焦点获取事件
     */
    class ClickedOverlay implements ItemizedOverlay.OnFocusChangeListener {

        @Override
        public void onFocusChanged(ItemizedOverlay overlay, OverlayItem item) {
            Toast.makeText(mapView.getContext().getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "TrafficTransferAnalyst");
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
            readmeDialog.setReadmeText(getResources().getString(R.string.traffictransferanalyst_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

    /**
     * <p>
     * 当该Activity对象已经存在任务堆栈中时，再次调用该Activity对象不会去重新创建，而是回调onNewIntent
     * 重写该接口修改视图的展示内容
     * </p>
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("iserver", "TrafficTransferAnalyst onNewIntent!");
        isOnNewIntent = true;
        Bundle newBundle = intent.getExtras();
        List<Point2D> geoPointList = new ArrayList<Point2D>();
        geoPointList = (List<Point2D>) newBundle.get("points");
        Log.d("iserver", "points:" + geoPointList.size());
        mapView.getOverlays().add(lineOverlay);
        if (geoPointList.size() >= 2) {
            if (mapView.getOverlays().contains(overlayStart)) {
                mapView.getOverlays().remove(overlayStart);
            }
            if (mapView.getOverlays().contains(overlayEnd)) {
                mapView.getOverlays().remove(overlayEnd);
            }
            overlayStart = new DefaultItemizedOverlay(drawableStart);
            OverlayItem overlayItemStart = new OverlayItem(geoPointList.get(0), "起点", "起点");
            overlayStart.addItem(overlayItemStart);
            overlayEnd = new DefaultItemizedOverlay(drawableEnd);
            OverlayItem overlayItemEnd = new OverlayItem(geoPointList.get(geoPointList.size() - 1), "终点", "终点");
            overlayEnd.addItem(overlayItemEnd);
            mapView.getOverlays().add(overlayStart);
            mapView.getOverlays().add(overlayEnd);
        }
        lineOverlay.setData(geoPointList);
        lineOverlay.setShowPoints(true);
        mapView.invalidate();
    }

    @Override
    protected void onDestroy() {
        mapView.stopClearCacheTimer();// 停止和销毁 清除运行时服务器中缓存瓦片的定时器。
        mapView.destroy();
        super.onDestroy();
    }

    /**
     * <p>
     * 重新回退接口，实现更好的交互
     * </p>
     */
    @Override
    public void onBackPressed() {
        if (isOnNewIntent) {
            mapView.getOverlays().remove(overlayStart);
            mapView.getOverlays().remove(overlayEnd);
            lineOverlay.setData(new ArrayList<Point2D>());
            lineOverlay.setShowPoints(false);
            mapView.invalidate();
            Intent intent = new Intent(this, ShowSolutionsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(intent);
            isOnNewIntent = false;
        } else {
            super.onBackPressed();
        }
    }
}
