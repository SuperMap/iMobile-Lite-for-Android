package com.supermap.imobilelite.querysamples;

import java.util.Map;

import com.supermap.imobilelite.maps.PointOverlay;
import com.supermap.imobilelite.maps.query.FilterParameter;
import com.supermap.imobilelite.maps.query.QueryByDistanceParameters;
import com.supermap.imobilelite.maps.query.QueryByDistanceService;
import com.supermap.imobilelite.querysamples.R;
import com.supermap.imobilelite.service.ReadmeDialog;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.GeometryType;
import com.supermap.services.components.commontypes.Point2D;

import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * <p>
 * 距离查询的示例，包含查询参数的设置，查询结果的可视化处理在父类里面
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class DistanceQueryDemo extends QueryDemo {
    private static final int README_DIALOG = 9;
    private static final String DEMONAME = "distanceQueryDemo";
    // 距离查询参考中心点的坐标
    private double distancePointX = 121;
    private double distancePointY = 31;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绘制距离查询中心点的点图标
        PointOverlay po = new PointOverlay();
        Drawable drawableRed = getResources().getDrawable(R.drawable.red_pin);
        po.setBitmap(((BitmapDrawable) drawableRed).getBitmap());
        po.setData(new com.supermap.imobilelite.maps.Point2D(distancePointX, distancePointY));// 客户端自己的Point2D
        mapView.getOverlays().add(po);

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
            distanceQuery();
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
     * 设置距离查询参数，并构建范围查询服务执行查询，查询完毕将回调查询监听器onQueryStatusChanged接口处理结果，该接口由用户实现
     * </p>
     */
    private void distanceQuery() {
        QueryByDistanceParameters p = new QueryByDistanceParameters();
        p.distance = 30;// 必设，查询距离，单位为地理单位
        Geometry g = new Geometry();
        // 构建点地物，必设
        g.type = GeometryType.POINT;
        g.points = new Point2D[] { new Point2D(distancePointX, distancePointY) };// iserver端的Point2D
        g.parts = new int[] { 1 };//
        p.geometry = g;
        // p.expectCount = 2;
        FilterParameter fp = new FilterParameter();
        // fp.attributeFilter = "SMID > 0";
        fp.name = "Capitals@World.1";// 必设，图层名称（图层名称格式：数据集名称@数据源别名）
        p.filterParameters = new FilterParameter[] { fp };
        QueryByDistanceService qs = new QueryByDistanceService(mapUrl);
        qs.process(p, new MyQueryEventListener());
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
            readmeDialog.setReadmeText(getResources().getString(R.string.distancequerydemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }
}
