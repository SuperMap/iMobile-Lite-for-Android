package com.supermap.imobilelite.querysamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.maps.query.FilterParameter;
import com.supermap.imobilelite.maps.query.QueryByBoundsParameters;
import com.supermap.imobilelite.maps.query.QueryByBoundsService;
import com.supermap.imobilelite.querysamples.R;
import com.supermap.imobilelite.service.ReadmeDialog;
import com.supermap.services.components.commontypes.Rectangle2D;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * <p>
 * 范围查询的示例，包含查询参数的设置，查询结果的可视化处理在父类里面
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class BoundsQueryDemo extends QueryDemo {
    private static final int README_DIALOG = 9;
    private static final String DEMONAME = "boundsQueryDemo";
    // 范围的左下右上的值，单位是地理单位
    private double boundsLeft = 113;
    private double boundsBottom = 38;
    private double boundsRight = 119;
    private double boundsTop = 42;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mapView.getController().setCenter(new Point2D(116.391468, 39.904491));
        // 绘制bounds范围的矩形
        List<Point2D> geoPointList = getDefPoints();
        PolygonOverlay polygonOverlay = new PolygonOverlay(getDefPolygonPaint());
        polygonOverlay.setData(geoPointList);
        mapView.getOverlays().add(polygonOverlay);

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

    /**
     * <p>
     * 生成组成bounds范围矩形的5个点
     * </p>
     * @return
     */
    private List<Point2D> getDefPoints() {
        List<Point2D> geoPointList = new ArrayList<Point2D>();
        geoPointList.add(new Point2D(boundsLeft, boundsTop));
        geoPointList.add(new Point2D(boundsRight, boundsTop));
        geoPointList.add(new Point2D(boundsRight, boundsBottom));
        geoPointList.add(new Point2D(boundsLeft, boundsBottom));
        geoPointList.add(new Point2D(boundsLeft, boundsTop));
        return geoPointList;
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
            boundsQuery();
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
     * 设置范围查询参数，并构建范围查询服务执行查询，查询完毕将回调查询监听器onQueryStatusChanged接口处理结果，该接口由用户实现
     * </p>
     */
    private void boundsQuery() {
        QueryByBoundsParameters p = new QueryByBoundsParameters();
        p.bounds = new Rectangle2D(boundsLeft, boundsBottom, boundsRight, boundsTop);// left, bottom, right, top 必设范围
        p.expectCount = 2;// 期望返回的条数
        FilterParameter fp = new FilterParameter();
        // fp.attributeFilter = "SMID > 0";
        fp.name = "Capitals@World.1";// 必设参数，图层名称格式：数据集名称@数据源别名
        p.filterParameters = new FilterParameter[] { fp };
        QueryByBoundsService qs = new QueryByBoundsService(mapUrl);
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
            readmeDialog.setReadmeText(getResources().getString(R.string.boundquerydemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }
}
