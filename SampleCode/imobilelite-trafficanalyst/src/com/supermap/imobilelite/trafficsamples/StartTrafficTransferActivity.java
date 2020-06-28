package com.supermap.imobilelite.trafficsamples;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.supermap.imobilelite.trafficTransferAnalyst.TransferTactic;
import com.supermap.imobilelite.trafficsamples.R;
import com.supermap.imobilelite.trafficsamples.util.TrafficTransferUtil;

/**
 * <p>
 * 交通换乘示例中 进行交通换乘分析视图，接收服务地址以及始发站点、目标站点和换乘类型参数进行查询。
 * 点击"换乘类型参数"按钮把结果发送给交通换乘示例中 展示交通换乘路线信息的视图ShowSolutionsActivity。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class StartTrafficTransferActivity extends Activity {
    private static final int TRAFFICTRANSFER_START = 0;
    private Button lessTimeBtn = null; // 最少时间搜索
    private Button minDistanceBtn = null; // 最短距离搜索
    private Button lessWalkBtn = null; // 少步行搜索
    private Button lessTransferBtn = null; // 少换乘搜索
    private String trafficTransferUrl;
    private EditText editSt;// 始发站点
    private EditText editEn;// 目的站点
    private ListView listView;// 站点输入时进行模糊查询站点名称集合的显示列表，提示友好

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.traffic_transfer);
        // 设定搜索按钮的响应
        lessTimeBtn = (Button) findViewById(R.id.lesstime);
        minDistanceBtn = (Button) findViewById(R.id.mindistance);
        lessWalkBtn = (Button) findViewById(R.id.lesswalk);
        lessTransferBtn = (Button) findViewById(R.id.lesstransfer);
        OnClickListener clickListener = new OnClickListener() {
            public void onClick(View v) {
                SearchButtonProcess(v);
            }
        };
        Bundle bundle = this.getIntent().getExtras();
        // 设置访问交通换乘服务的URL
        trafficTransferUrl = bundle.getString("url");
        lessTimeBtn.setOnClickListener(clickListener);
        minDistanceBtn.setOnClickListener(clickListener);
        lessWalkBtn.setOnClickListener(clickListener);
        lessTransferBtn.setOnClickListener(clickListener);

        editSt = (EditText) findViewById(R.id.start);
        editEn = (EditText) findViewById(R.id.end);
        listView = (ListView) StartTrafficTransferActivity.this.findViewById(R.id.stop_lv);
        editSt.addTextChangedListener(new MyTextWatcher(editSt));
        editEn.addTextChangedListener(new MyTextWatcher(editEn));
    }

    /**
     * <p>
     * 处理搜索按钮响应
     * </p>
     * @param v
     */
    void SearchButtonProcess(View v) {
        // 对起点终点的name进行赋值进行搜索
        String editStText = editSt.getText().toString();
        String editEnText = editEn.getText().toString();
        TransferTactic transferTactic = TransferTactic.LESS_TIME;
        if (lessTimeBtn.equals(v)) {
            Log.e("iserver", "transferTactic:LESS_TIME");
            transferTactic = TransferTactic.LESS_TIME;
        } else if (lessTransferBtn.equals(v)) {
            transferTactic = TransferTactic.LESS_TRANSFER;
        } else if (lessWalkBtn.equals(v)) {
            transferTactic = TransferTactic.LESS_WALK;
        } else {
            transferTactic = TransferTactic.MIN_DISTANCE;
        }
        // 进行交通换乘分析返回结果对象
        TrafficTransferUtil.SolutionsResult solutionsResult = (TrafficTransferUtil.SolutionsResult) TrafficTransferUtil.excuteTransferAnalyst(
                trafficTransferUrl, transferTactic, editStText, editEnText);
        Log.d("iserver", "editStText:" + editStText + ";editEnText:" + editEnText);
        Intent intent = new Intent(this, ShowSolutionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", solutionsResult);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            super();
            this.editText = editText;
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {
            String text = charSequence.toString();
            // 在起始和终点站点输入时进行站点名模糊查询，获取站点名集合的显示列表，供用户选择，用户友好
            final List<String> list = TrafficTransferUtil.queryStopNames(trafficTransferUrl, text);

            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new ArrayAdapter<String>(StartTrafficTransferActivity.this, android.R.layout.simple_list_item_1, list));
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String text = list.get(arg2);
                    editText.setText(text);
                    listView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("iserver", "StartTrafficTransferActivity onNewIntent!");
    }
}
