package com.supermap.imobilelite.mapsamples.dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.util.Constants;
import com.supermap.imobilelite.mapsamples.util.HttpUtil;
import com.supermap.imobilelite.mapsamples.util.JSONUtil;
import com.supermap.imobilelite.samples.adapter.MapResourceInfoAdapter;
import com.supermap.imobilelite.samples.domain.MapResourceInfo;
import com.supermap.imobilelite.samples.service.MapResourceService;

/**
 * 虽然是 Activity，但是功能性质上是个Dialog
 */
public class MapManageDialog extends Activity {

    private List<MapResourceInfo> mapResourceInfos;

    private ListView listView;

    private static final int ISERVERURL_DIALOG = 0;

    private static final int BASEURL_DIALOG = 1;

    // 记录当前选择的列表项索引
    private static int mPosition = -1;

    private static final int SWITCH_MENUITEM = 0;
    private static final int EDIT_MENUITEM = 1;
    private static final int DELETE_MENUITEM = 2;

    private static final int ADD_MENU = 0;

    private static final int GET_MAPJSON_SUCCESS = 1;

    private static final int GET_MAPJSON_FAILED = 2;

    private Dialog progressDialog;

    private Handler handler;
    // 存储原始的url，如地图名称为中文的，未进行处理过的
    public static String mapurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// 地图切换2
        this.setContentView(R.layout.mapmanage_dialog);

        handler = new GetJSONFinished();

        // 初始化列表项
        listView = (ListView) this.findViewById(R.id.mapmanage_lv);
        mapResourceInfos = initMapResourceInfosFromXML();
        MapResourceInfoAdapter adapter = new MapResourceInfoAdapter(this, mapResourceInfos, R.layout.mapmanage_item_dialog);
        listView.setAdapter(adapter);

        // 长按列表项弹出菜单
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                listView.showContextMenu();
                return true;
            }
        });

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
                menu.setHeaderTitle(R.string.mapmanagedialog_menutitle);
                menu.add(0, SWITCH_MENUITEM, 0, R.string.mapmanagedialog_menuitem1);
                menu.add(0, EDIT_MENUITEM, 1, R.string.mapmanagedialog_menuitem2);
                menu.add(0, DELETE_MENUITEM, 2, R.string.mapmanagedialog_menuitem3);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 处理弹出菜单事件，先获取当前选择的列表项
        MapResourceInfo mapResourceInfo = (MapResourceInfo) listView.getAdapter().getItem(mPosition);// 切换当前
        switch (item.getItemId()) {
        case SWITCH_MENUITEM:
            showProgressDialog();
            saveMapsToXML();
            // 处理getInstance()字符串，删除含有地图名称的字符串
            String[] strs = mapResourceInfo.getInstance().split("/");
            String splitinstance = "";
            for (int i = 1; i < strs.length - 1; i++) {
                splitinstance = splitinstance + "/" + strs[i];
            }
            // 地图名称未处理的url
            mapurl = mapResourceInfo.getService() + splitinstance + "/" + mapResourceInfo.getMapName();
            new Thread(new GetJSONRunnable(mapResourceInfo.getService() + mapResourceInfo.getInstance())).start();
            return true;
        case EDIT_MENUITEM:
            showDialog(BASEURL_DIALOG);
            return true;
        case DELETE_MENUITEM:
            // 若列表项仅有一项，即为当前正在使用的地图服务，不允许删除
            if (mapResourceInfos.size() == 1) {
                Toast.makeText(MapManageDialog.this, R.string.mapmanagedialog_shouldnotdelete, Toast.LENGTH_SHORT).show();
            } else {
                mapResourceInfos.remove(mapResourceInfo);
                refreshListView();
            }
            return true;

        default:
            break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);// 菜单2
        // group, item id, order, title
        menu.add(0, ADD_MENU, 0, R.string.mapmanagedialog_menuadd);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 添加新服务
        case ADD_MENU:
            showDialog(ISERVERURL_DIALOG);
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addMapResourceInfo(MapResourceInfo mapResourceInfo) {
        mapResourceInfos.add(mapResourceInfo);
        refreshListView();
    }

    public void addMapResourceInfos(List<MapResourceInfo> infos) {
        this.mapResourceInfos.addAll(infos);// 确定
        refreshListView();
    }

    public void updateMapResourceInfo(MapResourceInfo mapResourceInfo) {
        mapResourceInfos.set(mPosition, mapResourceInfo);
        refreshListView();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {// 添加新服务2
        case ISERVERURL_DIALOG:
            return new IServerUrlDialog(MapManageDialog.this, R.style.dialogTheme);
        case BASEURL_DIALOG:
            return new BaseUrlDialog(MapManageDialog.this, R.style.dialogTheme);
        default:
            break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {// 添加新服务3
        case BASEURL_DIALOG:
            if (mPosition != -1) {
                MapResourceInfo mapResourceInfo = (MapResourceInfo) listView.getAdapter().getItem(mPosition);
                BaseUrlDialog baseUrlDialog = (BaseUrlDialog) dialog;
                baseUrlDialog.refresh(mapResourceInfo);
            }
            break;

        default:
            break;
        }
    }

    @Override
    public void onBackPressed() {
        saveMapsToXML();
        super.onBackPressed();
    }

    void showProgressDialog() {// 切换当前2
        progressDialog = ProgressDialog.show(this, this.getResources().getString(R.string.treating), this.getResources().getString(R.string.getting_map_param),
                true);
    }

    private void saveMapsToXML() {// 切换当前3
        File xmlFile = new File(MapManageDialog.this.getFilesDir(), Constants.CONFIG_FILE_NAME);
        try {
            MapResourceService.save(mapResourceInfos, new FileOutputStream(xmlFile));
        } catch (FileNotFoundException e) {
            Log.w(Constants.ISERVER_TAG, "Failed to save file " + Constants.CONFIG_FILE_NAME, e);
        }
    }

    List<MapResourceInfo> initMapResourceInfosFromXML() {
        List<MapResourceInfo> result = null;// 地图切换3
        try {
            File tmp = new File(this.getFilesDir(), Constants.INIT_CONFIG_FILE_NAME);
            InputStream xml = null;
            if (tmp.exists()) {
                xml = new FileInputStream(tmp);
            } else {
                xml = this.getClass().getClassLoader().getResourceAsStream(Constants.CONFIG_FILE_NAME);
            }
            result = MapResourceService.getMapResourceInfos(xml);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 刷新列表
     */
    private void refreshListView() {
        MapResourceInfoAdapter adapter = new MapResourceInfoAdapter(this, mapResourceInfos, R.layout.mapmanage_item_dialog);
        listView.setAdapter(adapter);
    }

    /**
     * 从服务中获取地图的JSON数据
     * 
     */
    class GetJSONRunnable implements Runnable {

        private String baseUrl;

        public GetJSONRunnable(String url) {
            this.baseUrl = url;
        }

        @Override
        public void run() {// 切换当前4
            JSONObject prjcoordsysJSON = HttpUtil.getPrjCoordSysJSON(baseUrl);
            boolean isMercatorPro = JSONUtil.getIsMercatorProFromJSON(prjcoordsysJSON);
            String type = JSONUtil.getPrjCoordSysFromJSON(prjcoordsysJSON);
            JSONObject mapJSON = HttpUtil.getMapJSON(type, baseUrl);

            Message msg = new Message();
            if (mapJSON != null) {
                MessageObj obj = new MessageObj();
                // obj.mapUrl = baseUrl;
                obj.mapUrl = mapurl; // 地图名称未做处理的url
                obj.mapJSON = mapJSON.toString();
                if (type.equals(Constants.PCS_NON_EARTH)) {
                    obj.isNonEarth = true;
                } else {
                    obj.isNonEarth = false;
                }
                obj.isMercatorPro = isMercatorPro;
                msg.obj = obj;
                msg.what = GET_MAPJSON_SUCCESS;
            } else {
                msg.what = GET_MAPJSON_FAILED;
            }
            handler.sendMessage(msg);
        }
    }

    /**
     * 获取数据后进行数据传递
     */
    class GetJSONFinished extends Handler {
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();// 切换当前5
            switch (msg.what) {
            case GET_MAPJSON_SUCCESS:
                MessageObj messageObj = (MessageObj) msg.obj;
                Intent data = new Intent();
                data.putExtra("map_url", messageObj.mapUrl);
                data.putExtra("map_json", messageObj.mapJSON);
                data.putExtra("is_nonearth", messageObj.isNonEarth);
                data.putExtra("is_mercatorpro", messageObj.isMercatorPro);
                setResult(RESULT_OK, data);
                finish();
                super.handleMessage(msg);
                break;
            case GET_MAPJSON_FAILED:
                Toast.makeText(MapManageDialog.this, R.string.get_map_param_failed, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
            }
        }
    }

    /**
     * 确定按钮点击响应事件
     */
    class ConfirmButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            File xmlFile = new File(MapManageDialog.this.getFilesDir(), Constants.CONFIG_FILE_NAME);
            try {
                MapResourceService.save(mapResourceInfos, new FileOutputStream(xmlFile));
            } catch (FileNotFoundException e) {
                Log.w(Constants.ISERVER_TAG, xmlFile.getPath() + "not found", e);
            }
            finish();
        }
    }

    static class MessageObj {
        public String mapUrl;
        public String mapJSON;
        public boolean isNonEarth;
        public boolean isMercatorPro;
    }
}
