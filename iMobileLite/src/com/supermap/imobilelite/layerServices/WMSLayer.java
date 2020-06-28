package com.supermap.imobilelite.layerServices;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import com.supermap.services.components.commontypes.BoundsWithCRS;
import com.supermap.services.components.commontypes.Layer;
import com.supermap.services.components.commontypes.Rectangle2D;

public class WMSLayer extends Layer {
    private static final long serialVersionUID = 8796631280893275495L;
    public String url;
    public String version;
    public String service;
    public String format;
    public String styles;
    /**
     * 图层风格信息。
     * */
    // public UserStyleElement[] layerStyles;//属于第三方的封装类，这里暂时不封装
    public List layerStyles;
    public String request;

    public String exceptions;

    public String title;
    public Rectangle2D latLonBoundingBox;
    public BoundsWithCRS[] boundsWithCRSs;

    public WMSLayer() {

    }

    public WMSLayer(WMSLayer wmsLayer) {
        super(wmsLayer);
        this.url = wmsLayer.url;
        this.version = wmsLayer.version;
        this.service = wmsLayer.service;
        this.format = wmsLayer.format;
        this.styles = wmsLayer.styles;
        this.request = wmsLayer.request;
        this.exceptions = wmsLayer.exceptions;
        this.title = wmsLayer.title;
        if (wmsLayer.latLonBoundingBox != null) {
            this.latLonBoundingBox = new Rectangle2D(wmsLayer.latLonBoundingBox);
        }
        if (wmsLayer.boundsWithCRSs != null) {
            this.boundsWithCRSs = new BoundsWithCRS[wmsLayer.boundsWithCRSs.length];
            for (int i = 0; i < boundsWithCRSs.length; i++) {
                if (wmsLayer.boundsWithCRSs[i] != null) {
                    this.boundsWithCRSs[i] = new BoundsWithCRS(wmsLayer.boundsWithCRSs[i]);
                }
            }
        }
        // if (wmsLayer.layerStyles != null && wmsLayer.layerStyles.length > 0) {
        // this.layerStyles = new UserStyleElement[wmsLayer.layerStyles.length];
        // for (int i = 0; i < layerStyles.length; i++) {
        // if (wmsLayer.layerStyles[i] != null) {
        // this.layerStyles[i] = new UserStyleElement();
        // this.layerStyles[i].setName(wmsLayer.layerStyles[i].getName());
        // this.layerStyles[i].setTitle(wmsLayer.layerStyles[i].getTitle());
        // this.layerStyles[i].setAbstract(wmsLayer.layerStyles[i].getAbstract());
        // }
        // }
        // }

    }

    public Layer copy() {
        return new WMSLayer(this);
    }

    /**
     * <p>
     * 获取 WMS图层对象的哈希码值。
     * </p>
     * @return 哈希码值。
     */
    public int hashCode() {
        return new HashCodeBuilder(281, 283).append(super.hashCode()).append(url).append(version).append(service).append(format).append(styles).append(request)
                .append(layerStyles).append(exceptions).append(title).append(boundsWithCRSs).append(latLonBoundingBox).toHashCode();
    }

    /**
     * <p>
     * 比较指定对象与当前 {@link WMSLayer} 对象是否相等。
     * </p>
     * @param obj 与当前 {@link WMSLayer} 对象进行比较的对象。
     * @return 如果指定对象跟 WMSLayer 对象相等，则返回 true，否则，返回 false。
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WMSLayer)) {
            return false;
        }
        WMSLayer rhs = (WMSLayer) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(url, rhs.url).append(version, rhs.version).append(service, rhs.service)
                .append(format, rhs.format).append(styles, rhs.styles).append(layerStyles, rhs.layerStyles).append(request, rhs.request)
                .append(exceptions, rhs.exceptions).append(title, rhs.title).append(boundsWithCRSs, rhs.boundsWithCRSs)
                .append(latLonBoundingBox, rhs.latLonBoundingBox).isEquals();
    }
}