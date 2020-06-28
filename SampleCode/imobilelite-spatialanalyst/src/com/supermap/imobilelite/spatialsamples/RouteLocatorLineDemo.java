package com.supermap.imobilelite.spatialsamples;

import java.util.Map;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.supermap.imobilelite.samples.service.PreferencesService;

/**
 * <p>
 * 里程定线Demo，继承与里程定点。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class RouteLocatorLineDemo extends RouteLocatorPointDemo {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Readme对话框
        PreferencesService service = new PreferencesService(this);
        Map<String, Boolean> params = service.getReadmeEnable("RouteLocatorLineDemo");
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
        menu.add(0, 1, 0, R.string.routeLocatorLine_text);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击里程定点线菜单触发事件
        case 1:// 弹出对话框
            showDialog(ROUTELOCATOR_DIALOG);
            routeLocatorDialog.getStartPointTV().setText(R.string.startRoutePoint);
            routeLocatorDialog.getLocatorPointBtn().setText(R.string.locatorLine_text);
            break;
        default:
            break;
        }
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case ROUTELOCATOR_DIALOG:
            if (routeLocatorDialog != null) {
                return routeLocatorDialog;
            }
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, "RouteLocatorLineDemo");
            return dialog;
        default:
            return null;

        }

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
            readmeDialog.setReadmeText(getResources().getString(R.string.routelocatorline_readme));
            break;
        default:
            break;
        }
    }

}
