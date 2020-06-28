package com.supermap.imobilelite.mapsamples;

import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.supermap.imobilelite.maps.LayerView;
import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.dialog.MapManageDialog;
import com.supermap.imobilelite.mapsamples.dialog.ReadmeDialog;
import com.supermap.imobilelite.samples.service.PreferencesService;

public class MapSwitchDemo extends SimpleDemo {

    private static final int SWITCH_MAP = 0;

    private static final int README_DIALOG = 9;
    private static final double MINSCALE = 0.0000000016901636;
    private PreferencesService service;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("MapSwitchDemo");
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
        super.onCreateOptionsMenu(menu);// 菜单1
        // group, item id, order, title
        menu.add(0, 1, 0, R.string.switch_map);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 地图切换
        case 1:
            Intent intent = new Intent(MapSwitchDemo.this, MapManageDialog.class);
            startActivityForResult(intent, SWITCH_MAP);
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {// 切换当前6
            String mapUrl = data.getStringExtra("map_url");
            // 先清空map中已有的图层后，再添加新的图层
            mapView.removeAllLayers();
            LayerView layerView = new LayerView(this);

            // 设置访问地图的URL
            layerView.setURL(mapUrl);
            mapView.addLayer(layerView);
            mapView.invalidate();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "MapSwitchDemo");
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
            readmeDialog.setReadmeText(getResources().getString(R.string.mapswitchdemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }
}