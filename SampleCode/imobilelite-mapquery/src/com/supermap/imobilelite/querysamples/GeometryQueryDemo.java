package com.supermap.imobilelite.querysamples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.supermap.imobilelite.maps.PolygonOverlay;
import com.supermap.imobilelite.maps.query.FilterParameter;
import com.supermap.imobilelite.maps.query.QueryByGeometryParameters;
import com.supermap.imobilelite.maps.query.QueryByGeometryService;
import com.supermap.imobilelite.maps.query.SpatialQueryMode;
import com.supermap.imobilelite.querysamples.R;
import com.supermap.imobilelite.service.ReadmeDialog;
import com.supermap.services.components.commontypes.Geometry;
import com.supermap.services.components.commontypes.GeometryType;
import com.supermap.services.components.commontypes.Point2D;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * <p>
 * 几何查询的示例，包含查询参数的设置，查询结果的可视化处理在父类里面
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class GeometryQueryDemo extends QueryDemo {
    private static final int README_DIALOG = 9;
    private static final String DEMONAME = "geometryQueryDemo";
    private double boundsLeft = 113;
    private double boundsBottom = 38;
    private double boundsRight = 119;
    private double boundsTop = 42;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mapView.getController().setCenter(new com.supermap.android.maps.Point2D(116.391468, 39.904491));
        // 绘制一个多边形的Geometry
        PolygonOverlay polygonOverlay = new PolygonOverlay(getDefPolygonPaint());
        polygonOverlay.setData(getDefPoints());
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
     * 生成组成多边形Geometry的5个点
     * </p>
     * @return
     */
    private List<com.supermap.imobilelite.maps.Point2D> getDefPoints() {
        List<com.supermap.imobilelite.maps.Point2D> geoPointList = new ArrayList<com.supermap.imobilelite.maps.Point2D>();
        geoPointList.add(new com.supermap.imobilelite.maps.Point2D(boundsLeft, boundsTop));
        geoPointList.add(new com.supermap.imobilelite.maps.Point2D(boundsRight, boundsTop));
        geoPointList.add(new com.supermap.imobilelite.maps.Point2D((boundsRight + boundsLeft) / 2, boundsBottom));
        geoPointList.add(new com.supermap.imobilelite.maps.Point2D(boundsLeft, boundsTop));
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
            geometryQuery();
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
     * 设置几何查询参数，并构建范围查询服务执行查询，查询完毕将回调查询监听器onQueryStatusChanged接口处理结果，该接口由用户实现
     * </p>
     */
    private void geometryQuery() {
        QueryByGeometryParameters p = new QueryByGeometryParameters();
        p.spatialQueryMode = SpatialQueryMode.INTERSECT;// 必设，空间查询模式，默认相交
        // 构建查询几何对象
        Geometry g = new Geometry();
        g.type = GeometryType.REGION;
        g.points = new Point2D[] { new Point2D(boundsLeft, boundsTop), new Point2D(boundsRight, boundsTop),
                new Point2D((boundsRight + boundsLeft) / 2, boundsBottom), new Point2D(boundsLeft, boundsTop) };
        g.parts = new int[] { 4 };
        p.geometry = g;
        // p.expectCount = 3;
        FilterParameter fp = new FilterParameter();
        // fp.attributeFilter = "SMID > 0";
        fp.name = "Capitals@World.1";// 必设参数，图层名称格式：数据集名称@数据源别名
        p.filterParameters = new FilterParameter[] { fp };
        QueryByGeometryService qs = new QueryByGeometryService(mapUrl);
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
            readmeDialog.setReadmeText(getResources().getString(R.string.geometryquerydemo_readme));
            break;
        default:
            break;
        }
        super.onPrepareDialog(id, dialog);
    }

}
