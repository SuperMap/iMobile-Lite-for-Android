package com.supermap.imobilelite.mapsamples;

import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.dialog.AddLayersDialog;
import com.supermap.imobilelite.mapsamples.dialog.ReadmeDialog;
import com.supermap.imobilelite.samples.service.PreferencesService;

/**
 * 地图叠加的示例类，默认有个china400的地图为底图
 * @author huangqinghua
 *
 */
public class AddLayersDemo extends SimpleDemo {

    private static final int ADDLAYER_MAP = 0;

    private static final int README_DIALOG = 9;

    private PreferencesService service;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("AddLayersDemo");
        boolean isReadmeEnable = params.get("readme");
        if (isReadmeEnable) {
            showDialog(README_DIALOG);
        }
        
        helpBtn.setVisibility(View.VISIBLE);
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
        super.onCreateOptionsMenu(menu);// 创建 图层叠加 菜单
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.addlayer_map);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击 图层叠加 菜单触发事件
        case 1:
            Intent intent = new Intent(AddLayersDemo.this, AddLayersDialog.class);
            startActivityForResult(intent, ADDLAYER_MAP);
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String mapUrl = data.getStringExtra("map_url");
            boolean isNonearth = data.getBooleanExtra("is_nonearth", false);
            Log.w("iserver", "isNonearth:" + isNonearth);
            if (isNonearth) {
                return;
            }

            LayerView layerView = new LayerView(this);
            // 设置访问地图的URL
            layerView.setURL(mapUrl);
            mapView.addLayer(layerView);

            // 设置访问地图的比例尺
            // int level = 0;
            // // 计算当前添加的图层的中心点
            // double centerX = layerView.getBounds().getLeft() + layerView.getBounds().getRight();
            // double centerY = layerView.getBounds().getBottom() + layerView.getBounds().getTop();
            // Point2D gp = new Point2D(centerX / 2, centerY / 2);
            // Log.d("iserver", "gp.getCenter:" + gp.getY() + "," + gp.getX());
            // // 计算当前添加的图层的最适合可见层级
            // level = mapView.getProjection().calculateZoomLevel(layerView.getBounds());
            // Log.d(Constants.ISERVER_TAG, "calculate to ZoomLevel by layer bounds:" + level);
            //
            // // 设置访问地图是否是平面坐标系
            // // layerView.setPCSNonEarth(data.getBooleanExtra("is_nonearth", false));
            // mapView.getController().setZoom(level);
            // // 设置中心点
            // mapView.getController().setCenter(gp);// 39.904491, 116.391468 0.0d, 0.0d
            mapView.invalidate();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "AddLayersDemo");
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
            readmeDialog.setReadmeText(getResources().getString(R.string.addlayerdemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }
}
