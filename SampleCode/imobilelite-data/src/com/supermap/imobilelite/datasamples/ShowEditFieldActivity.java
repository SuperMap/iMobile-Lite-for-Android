package com.supermap.imobilelite.datasamples;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.supermap.imobilelite.data.EditFeaturesResult;
import com.supermap.imobilelite.datasamples.R;
import com.supermap.imobilelite.datasamples.util.DataUtil;
import com.supermap.services.components.commontypes.Feature;

public class ShowEditFieldActivity extends Activity {
    private ListView listView;
    // 记录当前选择的列表项索引
    private static int mPosition = -1;
    // 数组
    private SimpleAdapter listItemAdapter;
    private ArrayList<HashMap<String, Object>> listItem = null;
    private Bundle bundle;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_editfield);
        listView = (ListView) findViewById(R.id.show_result);
        bundle = this.getIntent().getExtras();
        // 初始化数据
        init();
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
                menu.setHeaderTitle(R.string.editfield_menutitle);
                menu.add(0, 0, 0, R.string.updatefield_menuitem);
            }
        });
        Button confirmBtn = (Button) this.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new ConfirmButtonClicked());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 处理弹出菜单事件，先获取当前选择的列表项
        switch (item.getItemId()) {
        case 0:
            updateField();
            return true;
        default:
            break;
        }
        return false;
    }

    private void updateField() {
        LayoutInflater inflater = (LayoutInflater) ShowEditFieldActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.update_field, null);
        HashMap<String, Object> item = (HashMap<String, Object>) listItem.get(mPosition);
        final TextView fieldkeyET = (TextView) layout.findViewById(R.id.fieldkey_et);
        String fieldkey = (String) item.get("field_key");
        fieldkeyET.setText(fieldkey);
        if (fieldkey.toLowerCase().contains("sm")) {
            Toast.makeText(ShowEditFieldActivity.this, "系统字段" + fieldkey + "不能被修改！", Toast.LENGTH_SHORT).show();
            return;
        }
        final EditText fieldvalueET = (EditText) layout.findViewById(R.id.fieldvalue_et);
        fieldvalueET.setText((String) item.get("field_value"));

        // 弹出的对话框
        new AlertDialog.Builder(ShowEditFieldActivity.this)
        /* 弹出窗口的最上头文字 */
        .setTitle("修改一条属性数据")
        /* 设置弹出窗口的图式 */
        .setIcon(android.R.drawable.ic_dialog_info)
        /* 设置弹出窗口的信息 */
        .setMessage("请输入修改的内容").setView(layout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                String fieldValue = fieldvalueET.getText().toString();
                String fieldKey = fieldkeyET.getText().toString();
                if (fieldValue == null || fieldValue.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入要修改的内容", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("field_key", fieldKey);
                    map.put("field_value", fieldValue);
                    listItem.set(mPosition, map);
                    listItemAdapter.notifyDataSetChanged();
                    Toast.makeText(ShowEditFieldActivity.this, "字段" + fieldKey + "值修改为:" + fieldValue + "", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() { /* 设置跳出窗口的返回事件 */
            public void onClick(DialogInterface dialoginterface, int i) {
                Toast.makeText(ShowEditFieldActivity.this, "取消了修改数据", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    private void updateFields() {
        Feature feature = (Feature) bundle.getSerializable("feature");
        String dataServiceUrl = bundle.getString("dataServiceUrl");
        if (feature == null) {
            Toast.makeText(ShowEditFieldActivity.this, "地物为空，无法更新字段！", Toast.LENGTH_SHORT).show();
            return;
        }
        int size = listItem.size();
        String[] fieldNames = new String[size];
        String[] fieldValues = new String[size];
        for (int i = 0; i < size; i++) {
            String fieldName = (String) listItem.get(i).get("field_key");
            fieldNames[i] = fieldName.substring(0, fieldName.lastIndexOf(":"));
            fieldValues[i] = (String) listItem.get(i).get("field_value");
        }
        feature.fieldNames = fieldNames;
        feature.fieldValues = fieldValues;
        // todo 发送更新的请求
        EditFeaturesResult delResult = DataUtil.excute_geoEdit(dataServiceUrl, new Feature[] { feature }, new int[] { feature.getID() });
        if (delResult != null && delResult.succeed) {
            Toast.makeText(ShowEditFieldActivity.this, "更新字段成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ShowEditFieldActivity.this, "更新字段失败！", Toast.LENGTH_SHORT).show();
        }
        this.onBackPressed();
    }

    // 初始化数据
    private void init() {
        String[] fieldNames = bundle.getStringArray("fieldNames");
        String[] fieldValues = bundle.getStringArray("fieldValues");
        if (fieldNames == null || fieldValues == null) {
            return;
        }
        if (fieldNames.length != fieldValues.length) {
            return;
        }
        listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < fieldNames.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("field_key", fieldNames[i] + ":");
            map.put("field_value", fieldValues[i]);
            listItem.add(map);
        }
        listItemAdapter = new SimpleAdapter(getApplicationContext(), listItem,// 数据源
                R.layout.editfield_items, new String[] { "field_key", "field_value" }, new int[] { R.id.field_key, R.id.field_value });
        listView.setAdapter(listItemAdapter);
    }

    class ConfirmButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            updateFields();
        }
    }
}
