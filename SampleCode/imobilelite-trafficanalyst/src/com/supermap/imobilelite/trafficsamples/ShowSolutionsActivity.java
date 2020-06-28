package com.supermap.imobilelite.trafficsamples;

import java.util.ArrayList;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supermap.imobilelite.trafficsamples.R;
import com.supermap.imobilelite.trafficsamples.util.TrafficTransferUtil.PathsResult;
import com.supermap.imobilelite.trafficsamples.util.TrafficTransferUtil.SolutionsResult;

/**
 * <p>
 * 交通换乘示例中 展示交通换乘路线信息视图，提供每条路线方案绘制到底图的"查看地图"按钮。
 * 点击按钮后把该路线方案的导引项坐标集合发送给 交通换乘示例中显示地图的视图，展示路线图。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ShowSolutionsActivity extends ExpandableListActivity {
    List<String> group; // 组列表
    List<List<String>> child; // 子列表
    MyShowSolutionInfoAdapter adapter; // 数据适配器
    SolutionsResult solutionsResult;// 封装路线方案集合信息对象

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.transfer_solution);     
        adapter = new MyShowSolutionInfoAdapter();
        initializeData();
        ExpandableListView expandableListView = getExpandableListView();
        expandableListView.setAdapter(adapter);
        expandableListView.setCacheColorHint(0); // 设置拖动列表的时候防止出现黑色背景
    }

    /**
     * 初始化组、子列表数据
     */
    private void initializeData() {
        group = new ArrayList<String>();
        child = new ArrayList<List<String>>();
        Bundle bundle = this.getIntent().getExtras();
        solutionsResult = (SolutionsResult) bundle.getSerializable("result");
        addInfo(solutionsResult);
    }

    /**
     * <p>
     * 给组、子列表添加数据
     * </p>
     * @param solutionsResult
     */
    private void addInfo(SolutionsResult solutionsResult) {
        if (solutionsResult == null) {
            return;
        }
        TextView starttext = (TextView) this.findViewById(R.id.starttext);
        starttext.setTextColor(Color.rgb(80, 80, 80));
        starttext.setText(solutionsResult.startStopName);
        TextView endtext = (TextView) this.findViewById(R.id.endtext);
        endtext.setTextColor(Color.rgb(80, 80, 80));
        endtext.setText(solutionsResult.endStopName);
        // group和child数目必须一样多
        if (solutionsResult.groupNames.size() != solutionsResult.pathsResults.size()) {
            return;
        }
        group = solutionsResult.groupNames;
        for (int i = 0; i < solutionsResult.groupNames.size(); i++) {
            PathsResult pathsResult = solutionsResult.pathsResults.get(solutionsResult.groupNames.get(i));
            if (pathsResult == null) {
                List<String> childitem = new ArrayList<String>();
                child.add(childitem);
            } else {
                List childitem = pathsResult.paths;
                childitem.add("btn");// 增加查看地图按钮设计的，增加一个子项
                child.add(childitem);
            }
        }

    }

    class MyShowSolutionInfoAdapter extends BaseExpandableListAdapter {
        // 设置子视图的图片
        int[] logos = new int[] { R.drawable.bus, R.drawable.step };

        // 自己定义一个获得文字信息的方法，暂时不用
        TextView getTextView() {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
            TextView textView = new TextView(ShowSolutionsActivity.this);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(36, 0, 0, 0);
            textView.setTextSize(20);
            textView.setTextColor(Color.BLACK);
            return textView;
        }

        // -----------------Child----------------//
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return child.get(groupPosition).size();
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String groupName = group.get(groupPosition);
            String childName = child.get(groupPosition).get(childPosition);
            return getGenericView(groupName, childName);
        }

        // ----------------Group----------------//
        @Override
        public Object getGroup(int groupPosition) {
            return group.get(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount() {
            return group.size();
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String string = group.get(groupPosition);
            return getGenericGroupView(string);
        }

        // 创建组视图
        public TextView getGenericGroupView(String s) {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 50);
            TextView text = new TextView(ShowSolutionsActivity.this);
            text.setLayoutParams(lp);
            // Center the text vertically
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            text.setPadding(55, 0, 0, 0);
            text.setTextColor(Color.BLACK);
            // text.setTextSize(20);
            text.setText(s);
            return text;
        }

        // 创建子视图
        public View getGenericView(final String groupName, String childName) {
            // Layout parameters for the ExpandableListView
            if ("btn".equals(childName)) {// 显示"查看地图"按钮子项
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 50);
                Button showMapBtn = new Button(ShowSolutionsActivity.this);
                showMapBtn.setLayoutParams(params);
                // showMapBtn.setHeight(20);
                // showMapBtn.setWidth(20);
                showMapBtn.setTextSize(13);
                showMapBtn.setGravity(Gravity.CENTER);
                showMapBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        startNextActivity(groupName);              
                        // setResult(RESULT_OK, data);
                        // finish();
                    }
                });
                showMapBtn.setText("查看地图");
                return showMapBtn;
            } else {
                // 解析一个方案中每个分段的路线信息，该信息包含是否步行信息以及路线描述信息
                String childItemName = childName;
                boolean isWalk = false;
                String[] infos = childName.split("&");
                if (infos.length >= 2) {
                    // 是否步行信息
                    if (infos[0].contains("true")) {
                        isWalk = true;
                    }
                    // 路线描述信息
                    if (childName.contains("path=")) {
                        childItemName = childName.substring(childName.indexOf("path=") + 5);
                    }
                }
                LinearLayout ll = new LinearLayout(ShowSolutionsActivity.this);
                ll.setOrientation(0);

                ImageView generallogo = new ImageView(ShowSolutionsActivity.this);
                if (isWalk) {
                    // 步行使用步行图标
                    generallogo.setImageResource(logos[1]);
                } else {
                    // 公交使用公交图标
                    generallogo.setImageResource(logos[0]);
                }
                AbsListView.LayoutParams param = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50);
                generallogo.setLayoutParams(param);
                generallogo.setScaleType(ScaleType.CENTER);
                ll.addView(generallogo);

                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 50);
                TextView text = new TextView(ShowSolutionsActivity.this);
                text.setLayoutParams(lp);
                // Center the text vertically
                text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                // Set the text starting position
                text.setPadding(25, 0, 0, 0);
                // text.setTextSize(15);
                text.setText(childItemName);
                ll.addView(text);
                return ll;
            }
        }

        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }

    }

    void startNextActivity(String groupName) {
        Intent data = new Intent(this, TrafficTransferAnalystDemo.class);
        data.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        // data.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        Bundle bundle = new Bundle();
        ArrayList points = new ArrayList();
        if (solutionsResult != null && solutionsResult.pathsResults.get(groupName) != null) {
            points = (ArrayList) solutionsResult.pathsResults.get(groupName).points;
        }
        bundle.putSerializable("points", points);
        data.putExtras(bundle);
        startActivity(data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("iserver", "ShowSolutionsActivity onNewIntent!");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartTrafficTransferActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
