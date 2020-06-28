package com.supermap.imobilelite.mapsamples.dialog;

import java.util.List;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.samples.adapter.MapResourceInfoAdapter;
import com.supermap.imobilelite.samples.domain.MapResourceInfo;

/**
 * 虽然是 Activity，但是功能性质上是个Dialog，处理地图叠加时 各个菜单事件，覆写了父类的一些处理，增加子类的处理
 * 
 * @author huangqinghua
 * 
 */
public class AddLayersDialog extends MapManageDialog {

    private List<MapResourceInfo> mapResourceInfos;

    private ListView listView;
    // 记录当前选择的列表项索引
    private static int mPosition = -1;

    private static final int ADD_MENUITEM = 0;

    private static final int ADD_MENU = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// 地图切换2

        // 初始化列表项
        listView = (ListView) this.findViewById(R.id.mapmanage_lv);
        mapResourceInfos = initMapResourceInfosFromXML();
        MapResourceInfoAdapter adapter = new MapResourceInfoAdapter(this, mapResourceInfos, R.layout.mapmanage_item_dialog);
        listView.setAdapter(adapter);

        // 长按列表项 触发 弹出菜单
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                listView.showContextMenu();
                return true;
            }
        });
        // 长按列表项弹出的菜单内容
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
                menu.setHeaderTitle(R.string.selectelayerdialog_menutitle);
                menu.add(0, ADD_MENUITEM, 0, R.string.selectelayerdialog_menuitem1);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 处理弹出菜单事件，先获取当前选择的列表项
        MapResourceInfo mapResourceInfo = (MapResourceInfo) listView.getAdapter().getItem(mPosition);// 切换当前
        switch (item.getItemId()) {
        case ADD_MENUITEM:
            showProgressDialog();
            // 处理getInstance()字符串，删除含有地图名称的字符串
            String[] strs = mapResourceInfo.getInstance().split("/");
            String splitinstance = "";
            for (int i = 1; i < strs.length - 1; i++) {
                splitinstance = splitinstance + "/" + strs[i];
            }
            // 地图名称未处理的url
            MapManageDialog.mapurl = mapResourceInfo.getService() + splitinstance + "/" + mapResourceInfo.getMapName();
            new Thread(new GetJSONRunnable(mapResourceInfo.getService() + mapResourceInfo.getInstance())).start();
            return true;
        default:
            break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // super.onCreateOptionsMenu(menu);//菜单2
        menu.add(0, ADD_MENU, 0, R.string.addlayerdialog_menuadd);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}