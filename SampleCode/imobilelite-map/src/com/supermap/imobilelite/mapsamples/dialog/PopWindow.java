package com.supermap.imobilelite.mapsamples.dialog;

import android.graphics.Point;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supermap.imobilelite.maps.MapView;
import com.supermap.imobilelite.maps.OverlayItem;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.mapsamples.R;

/**
 * 气泡窗口类，点击可获取用户位置的详细信息
 *
 */
public class PopWindow {
    private LinearLayout popupLinearLayout;
    private int titleBarHeight; 
    private View popView;
    // 定位弹出气泡的Point2D
    private Point2D popuPoint2D;

    public PopWindow(View popView,int titleBarHeight) {
        this.popView = popView;
        this.titleBarHeight =titleBarHeight;
        
    }

    /**
     * 实时更新弹出气泡窗口的位置
     */
    public void updatePopupWindow(MapView mapView) {
        if (popupLinearLayout != null) {
            Point point = mapView.getProjection().toPixels(popuPoint2D, null);
            // locatPopupLinearLayout.setX(point.x-locatPopupLinearLayout.getWidth()/2);
            // locatPopupLinearLayout.setY(point.y-locatPopupLinearLayout.getHeight()-15);
            AbsoluteLayout.LayoutParams geoLP = new AbsoluteLayout.LayoutParams(popupLinearLayout.getWidth(), popupLinearLayout.getHeight(), point.x
                    - popupLinearLayout.getWidth() / 2, point.y - popupLinearLayout.getHeight() + titleBarHeight);
            popupLinearLayout.setLayoutParams(geoLP);
            popupLinearLayout.invalidate();
            popView.requestLayout();
        }

    }

    /**
     * 弹出气泡窗口
     * @param mapView
     * @param item
     */
    public void showPopupWindow(MapView mapView, OverlayItem item) {
        if (item != null) {
            Point2D point = item.getPoint();
            popuPoint2D = point;
            popupLinearLayout = (LinearLayout) popView.findViewById(R.id.balloon_main_layout);
            TextView title = (TextView) popView.findViewById(R.id.balloon_item_title);
            title.setText(item.getTitle());
            Point loadPoint = mapView.getProjection().toPixels(point, null);
            ImageView imageView = (ImageView) popView.findViewById(R.id.map_bubbleImageOne);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closePopupWindow();
                }
            });
            AbsoluteLayout.LayoutParams geoLP = new AbsoluteLayout.LayoutParams(popupLinearLayout.getWidth(), popupLinearLayout.getHeight(), loadPoint.x
                    - popupLinearLayout.getWidth() / 2, loadPoint.y - popupLinearLayout.getHeight() + titleBarHeight);
            popupLinearLayout.setLayoutParams(geoLP);
            popupLinearLayout.invalidate();
            popView.requestLayout();
        }
    }

    /**
     * 关闭气泡窗口
     */
    public void closePopupWindow() {
        if (popupLinearLayout != null) {
            AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(popupLinearLayout.getWidth(), popupLinearLayout.getHeight(), -1000, -1000);
            popupLinearLayout.setLayoutParams(lp);
            popupLinearLayout.invalidate();
            popView.requestLayout();
            popuPoint2D = null;
            popupLinearLayout = null;
        }
    }
}
