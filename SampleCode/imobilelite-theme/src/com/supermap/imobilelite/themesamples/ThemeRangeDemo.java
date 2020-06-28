package com.supermap.imobilelite.themesamples;

import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.theme.RemoveThemeResult;
import com.supermap.imobilelite.theme.ThemeResult;
import com.supermap.imobilelite.service.PreferencesService;
import com.supermap.imobilelite.themesamples.R;
import com.supermap.imobilelite.util.ThemeUtil;

public class ThemeRangeDemo extends Activity {
    protected static String Log_TAG = "com.supermap.imobilelite.themesamples.ThemeUniqueDemo";
    protected static final String DEFAULT_URL = "http://support.supermap.com.cn:8090/iserver/services";
    protected static final String DEFAULT_MAP_URL = "/map-china400/rest/maps/China";
    protected MapView mapView;
    private LayerView baseLayerView;
    private String mapUrl;
    // private String layerID;
    private LayerView themeLayer;
    private Bundle bundle;
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
        if (serviceUrl == null || "".equals(serviceUrl)) {
            mapUrl = DEFAULT_URL + DEFAULT_MAP_URL;
        } else {
            mapUrl = serviceUrl + DEFAULT_MAP_URL;
        }
        baseLayerView = new LayerView(this, mapUrl);
        mapView.addLayer(baseLayerView);
        mapView.getController().setZoom(3);
        // 设置中心点
        Point2D point = new Point2D(12080677, 4591416);
        mapView.getController().setCenter(point);
        // 启用内置放大缩小控件
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        // Readme对话框
        PreferencesService service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("ThemeRangeDemo");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
        
        helpBtn =(Button)findViewById(R.id.button_help);
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
        super.onCreateOptionsMenu(menu);// 创建单值专题图菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.creat_theme);
        menu.add(0, 2, 0, R.string.remove_theme);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击专题图 菜单触发事件
        case 1:// 创建专题图
            if (themeLayer == null) {
                displayTheme(ThemeUtil.createThemeRange(mapUrl));
            }
            break;
        case 2:// 移除专题图
            if (themeLayer != null) {
                removeTheme(ThemeUtil.removeTheme(mapUrl));
            }
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayTheme(ThemeResult result) {
        if (result == null || result.resourceInfo == null || result.resourceInfo.newResourceID == null) {
            return;
        }
        themeLayer = new LayerView(this, mapUrl);
        themeLayer.setTransparent(true);
        themeLayer.setLayersID(result.resourceInfo.newResourceID);
        // layerID = result.resourceInfo.newResourceID;
        mapView.addLayer(themeLayer);

    }

    private void removeTheme(RemoveThemeResult removeResult) {
        if (removeResult.succeed == true && themeLayer != null) {
            mapView.removeLayer(themeLayer);
            themeLayer = null;
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "ThemeRangeDemo");
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
            readmeDialog.setReadmeText(getResources().getString(R.string.themerange_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
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
