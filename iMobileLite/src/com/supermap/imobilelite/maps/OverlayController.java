package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

class OverlayController {
    private static final String LOG_TAG = "com.supermap.android.maps.overlayController";
    public List<Overlay> overlays = null;
    private MapView mapView;

    public OverlayController(MapView mapView) {
        this.mapView = mapView;
        this.overlays = Collections.synchronizedList(new OverlayArrayList());
    }

    public List<Overlay> getOverlays() {
        return this.overlays;
    }

    void setBackedList(List<Overlay> list) {
        this.overlays = list;
    }

    public void renderOverlays(Canvas canvas, MapView mapView) {
        if (this.overlays.size() > 0)
            synchronized (this.overlays) {
                // for (Overlay overlay : this.overlays) {
                // try {
                // overlay.draw(canvas, mapView, true, mapView.getDrawingTime());
                // } catch (Exception e) {
                // Log.d(LOG_TAG, e.toString(), e);
                // }
                // }
                // 不绘制阴影
                for (Overlay overlay : this.overlays) {
                    try {
                        overlay.draw(canvas, mapView, false, mapView.getDrawingTime());
                    } catch (Exception ex) {
                        Log.d(LOG_TAG, ex.toString(), ex);
                    }
                }
            }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event, MapView mapView) {
        if (this.overlays.size() > 0) {
            synchronized (this.overlays) {
                for (Overlay overlay : this.overlays) {
                    if (overlay.onKeyDown(keyCode, event, mapView)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event, MapView mapView) {
        if (this.overlays.size() > 0) {
            synchronized (this.overlays) {
                for (Overlay overlay : this.overlays) {
                    if (overlay.onKeyUp(keyCode, event, mapView)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean onTap(Point2D gp, MapView mapView) {
        if (this.overlays.size() > 0) {
            synchronized (this.overlays) {
                for (Overlay overlay : this.overlays) {
                    if (overlay.onTap(gp, mapView)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
        if (this.overlays.size() > 0) {
            synchronized (this.overlays) {
                for (int i = 0; i < this.overlays.size(); i++) {
                    Overlay overlay = this.overlays.get(i);
                    if (overlay.onTouchEvent(event, mapView)) {
                        return true;
                    }
                }
                // 换成以上的迭代遍历不会有数组越界异常，绘制LineOverlay出现这个异常，而做的修改
                // for (Overlay overlay : this.overlays) {
                // if (overlay.onTouchEvent(event, mapView)) {
                // return true;
                // }
                // }
            }
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent event, MapView mapView) {
        if (this.overlays.size() > 0) {
            synchronized (this.overlays) {
                for (Overlay overlay : this.overlays) {
                    if (overlay.onTrackballEvent(event, mapView)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void destroy() {
        for (Overlay overlay : this.overlays) {
            overlay.destroy();
        }
        this.overlays.clear();
    }

    private class OverlayArrayList extends ArrayList<Overlay> {
        private static final long serialVersionUID = -1622579671240580437L;

        private OverlayArrayList() {
        }

        public void clear() {
            for (Overlay o : this) {
                o.destroy();
            }
            super.clear();
        }

        public Overlay remove(int index) {
            Overlay o = (Overlay) super.remove(index);
            o.destroy();
            return o;
        }

        public boolean remove(Object object) {
            if ((object instanceof List)) {
                List<Overlay> list = (List) object;
                for (Overlay o : list)
                    o.destroy();
            } else if ((object instanceof Overlay)) {
                ((Overlay) object).destroy();
            }
            return super.remove(object);
        }

        protected void removeRange(int fromIndex, int toIndex) {
            for (int i = fromIndex; i <= toIndex; i++) {
                ((Overlay) get(i)).destroy();
            }
            super.removeRange(fromIndex, toIndex);
        }

        public void add(int index, Overlay overlay) {
            checkOverlayAdd(overlay);
            super.add(index, overlay);
            sort();
        }

        public boolean add(Overlay overlay) {
            checkOverlayAdd(overlay);
            boolean add = super.add(overlay);
            sort();
            return add;
        }

        public boolean addAll(Collection<? extends Overlay> collection) {
            boolean add = super.addAll(collection);
            sort();
            return add;
        }

        public boolean addAll(int index, Collection<? extends Overlay> collection) {
            checkOverlays(collection);
            boolean add = super.addAll(index, collection);
            sort();
            return add;
        }

        private void sort() {
            Collections.sort(this, new Comparator() {
                public int compare(Object x, Object xx) {
                    int one = ((Overlay) x).getZIndex();
                    int two = ((Overlay) xx).getZIndex();
                    if (one == two)
                        return 0;
                    return one < two ? -1 : 1;
                }
            });
            mapView.getEventDispatcher().sendEmptyMessage(41);
        }

        private void checkOverlays(Collection<? extends Overlay> collection) {
            for (Overlay oo : collection)
                checkOverlayAdd(oo);
        }

        private void checkOverlayAdd(Overlay overlay) {
            if ((overlay.getKey() == null) || (overlay.getKey().length() == 0)) {
                return;
            }
            Overlay o = getOverlayByKey(overlay.getKey());
            if (o != null)
                remove(o);
        }

        public Overlay getOverlayByKey(String key) {
            for (Overlay o : this) {
                if (o.getKey().equals(key))
                    return o;
            }
            return null;
        }
    }
}