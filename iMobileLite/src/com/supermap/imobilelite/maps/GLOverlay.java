package com.supermap.imobilelite.maps;
import com.supermap.imobilelite.data.*;
import com.supermap.imobilelite.data.Point2Ds;
import com.supermap.imobilelite.data.Point2D;

/**
 * Created by JK on 2017/12/4.
 */

public class GLOverlay {
    RMGLCanvas mcanvas = null ;
    MapView mMapview = null ;
    double a,b,c,d,Left,Top,Right,Bottom;

    public GLOverlay(RMGLCanvas canvas, MapView mapView) {
        mcanvas=canvas;
        mMapview=mapView;
        com.supermap.imobilelite.maps.Point2D leftTop = new com.supermap.imobilelite.maps.Point2D(mcanvas.getViewBounds().getLeft(),mcanvas.getViewBounds().getTop());
        com.supermap.imobilelite.maps.Point2D rightBottom = new com.supermap.imobilelite.maps.Point2D(mcanvas.getViewBounds().getRight(),mcanvas.getViewBounds().getBottom());
        mMapview.setViewBounds(new BoundingBox(leftTop,rightBottom));
        mcanvas.setOnTouchEvent(true);
    }

    public void SuperPosition(){
        mMapview.addMapViewEventListener(new MapView.MapViewEventListener() {
            @Override
            public void moveStart(MapView paramMapView) {
                Left=mMapview.getViewBounds().getLeft();
                Top=mMapview.getViewBounds().getTop();
                Right=mMapview.getViewBounds().getRight();
                Bottom=mMapview.getViewBounds().getBottom();
                Rectangle2D rectangle2D = new Rectangle2D(Left,Bottom,Right,Top);
                mcanvas.setViewBounds(rectangle2D);
                mcanvas.refreshDrawableState();
                mcanvas.Refresh();
            }
            @Override
            public void move(MapView paramMapView) {
                Left=mMapview.getViewBounds().getLeft();
                Top=mMapview.getViewBounds().getTop();
                Right=mMapview.getViewBounds().getRight();
                Bottom=mMapview.getViewBounds().getBottom();
                Rectangle2D rectangle2D = new Rectangle2D(Left,Bottom,Right,Top);
                mcanvas.setViewBounds(rectangle2D);
                mcanvas.refreshDrawableState();
                mcanvas.Refresh();
            }

            @Override
            public void moveEnd(MapView paramMapView) {
                Left=mMapview.getViewBounds().getLeft();
                Top=mMapview.getViewBounds().getTop();
                Right=mMapview.getViewBounds().getRight();
                Bottom=mMapview.getViewBounds().getBottom();
                Rectangle2D rectangle2D = new Rectangle2D(Left,Bottom,Right,Top);
                mcanvas.setViewBounds(rectangle2D);
                mcanvas.refreshDrawableState();
                mcanvas.Refresh();
            }

            @Override
            public void touch(MapView paramMapView) {
                Left=mMapview.getViewBounds().getLeft();
                Top=mMapview.getViewBounds().getTop();
                Right=mMapview.getViewBounds().getRight();
                Bottom=mMapview.getViewBounds().getBottom();
                Rectangle2D rectangle2D = new Rectangle2D(Left,Bottom,Right,Top);
                mcanvas.setViewBounds(rectangle2D);
                mcanvas.refreshDrawableState();
                mcanvas.Refresh();
            }

            @Override
            public void longTouch(MapView paramMapView) {

            }

            @Override
            public void zoomStart(MapView paramMapView) {

                Left=mMapview.getViewBounds().getLeft();
                Top=mMapview.getViewBounds().getTop();
                Right=mMapview.getViewBounds().getRight();
                Bottom=mMapview.getViewBounds().getBottom();
                Rectangle2D rectangle2D = new Rectangle2D(Left,Bottom,Right,Top);
                mcanvas.setViewBounds(rectangle2D);
                mcanvas.refreshDrawableState();
                mcanvas.Refresh();
            }

            @Override
            public void zoomEnd(MapView paramMapView) {

                Left=mMapview.getViewBounds().getLeft();
                Top=mMapview.getViewBounds().getTop();
                Right=mMapview.getViewBounds().getRight();
                Bottom=mMapview.getViewBounds().getBottom();
                Rectangle2D rectangle2D = new Rectangle2D(Left,Bottom,Right,Top);
                mcanvas.setViewBounds(rectangle2D);
                mcanvas.refreshDrawableState();
                mcanvas.Refresh();
            }

            @Override
            public void mapLoaded(MapView paramMapView) {

            }
        });
    }
}
