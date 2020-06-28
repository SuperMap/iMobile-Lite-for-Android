package com.supermap.imobilelite.querysamples;

import java.util.Map;

import com.supermap.imobilelite.maps.query.FilterParameter;
import com.supermap.imobilelite.maps.query.QueryBySQLParameters;
import com.supermap.imobilelite.maps.query.QueryBySQLService;
import com.supermap.imobilelite.maps.query.QueryOption;
import com.supermap.imobilelite.querysamples.R;
import com.supermap.imobilelite.service.ReadmeDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * <p>
 * SQL查询的示例，包含查询参数的设置，查询结果的可视化处理在父类里面
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class SQLQueryDemo extends QueryDemo {
    private static final int README_DIALOG = 9;
    private static final String DEMONAME = "sqlQueryDemo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Map<String, Boolean> params = service.getReadmeEnable(DEMONAME);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, R.string.query);
        menu.add(0, 2, 0, R.string.clean);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 点击菜单触发事件
        case 1:
            sqlQuery();
            break;
        case 2:
            clean();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <p>
     * 设置SQL查询参数，并构建范围查询服务执行查询，查询完毕将回调查询监听器onQueryStatusChanged接口处理结果，该接口由用户实现
     * </p>
     */
    private void sqlQuery() {
        QueryBySQLParameters p = new QueryBySQLParameters();
        FilterParameter fp = new FilterParameter();
        // 属性过滤条件
        fp.attributeFilter = "SMID > 169 AND SMID < 174";// SMID > 169 AND SMID < 174
        fp.name = "Capitals@World.1";
        // fp.attributeFilter = "smid=247";// smid=247或Pop_1994>1000000000 and SmArea>900
        // fp.name = "Countries@World.1";// 必设，图层名称（图层名称格式：数据集名称@数据源别名）
        p.filterParameters = new FilterParameter[] { fp };
        p.expectCount = 20;// 期望返回的条数
        // p.queryOption = QueryOption.GEOMETRY;// 设置返回结果类型，默认是返回属性和地物，可以根据需要值返回其一
        QueryBySQLService qs = new QueryBySQLService(mapUrl);// totalCount:4,currentCount:4
        qs.process(p, new MyQueryEventListener());// 执行查询，必须设置 用户实现的查询监听器对象
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case README_DIALOG:
            Dialog dialog = new ReadmeDialog(this, R.style.readmeDialogTheme, DEMONAME);
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
            readmeDialog.setReadmeText(getResources().getString(R.string.sqlquerydemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

}
