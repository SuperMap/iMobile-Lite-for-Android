package com.supermap.imobilelite.maps.ogc.wmts;

import com.supermap.imobilelite.maps.BoundingBox;

/**
 * <p>
 * WMTS图层地理范围和相应的坐标参考系crs
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
class BoundsWithCRS {
    public BoundingBox bounds;
    public String crs;

    public BoundsWithCRS() {
        super();
    }

    public BoundsWithCRS(BoundingBox bounds, String crs) {
        super();
        this.bounds = bounds;
        this.crs = crs;
    }

}
