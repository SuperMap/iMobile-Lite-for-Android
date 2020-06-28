package com.supermap.imobilelite.maps;

import org.apache.commons.lang3.StringUtils;

import com.supermap.imobilelite.commons.Credential;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

/**
 * <p>
 * WMS图层视图，作为地图视图的子项添加到 {@link MapView} 中显示。
 * WMS图层视图用于显示 标准OGC服务 的 WMS 地图服务。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class WMSLayerView extends AbstractTileLayerView {
    private static final String LOG_TAG = "WMSLayerView";
    private String version = "1.1.1";
    private String layers = "0";
    private String styles = "";
    private String crsKey = "SRS";// 1.1.1是:SRS;1.3.0是:CRS
    private String crsStr = "EPSG:4326";
    private String format = "image/png";
    private boolean transparent = false;
    private String bgColor = "0xFFFFFF";
    // 考虑属性参数影响瓦片的内容，所以此属性记录属性参数们对应的hashcode并生成相关的字符串作为缓存文件的一部分
    private String paramsToName = "";

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     */
    public WMSLayerView(Context context) {
        super(context);
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param attrs 属性信息。
     */
    public WMSLayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param attrs 属性信息。
     * @param defStyle 风格标识。
     */
    public WMSLayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param url wms地图服务的 url
     * @param version wms的版本号，1.1.1和1.3.0
     * @param layers 地图图层列表。地图图层之间以半角英文逗号进行分隔，如0.1,0.2，写0代表地图全部图层。
     * 最左边的图层在最底，下一个图层放到前一个的上面，依次类推。图层名称是服务元数据中的<Layer><Name>元素的字符数据内容。
     */
    public WMSLayerView(Context context, String url, String version, String layers) {
        super(context);
        this.curMapUrl = url;
        if (!StringUtils.isEmpty(version)) {
            this.version = version;
        }
        if (!StringUtils.isEmpty(layers)) {
            this.layers = layers;
        }
        if ("1.3.0".equals(version)) {
            crsKey = "CRS";
        }
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param url wms地图服务的 url
     * @param version wms的版本号，1.1.1和1.3.0
     * @param layers 地图图层列表。地图图层之间以半角英文逗号进行分隔，如0.1,0.2，写0代表地图全部图层。
     * 最左边的图层在最底，下一个图层放到前一个的上面，依次类推。图层名称是服务元数据中的<Layer><Name>元素的字符数据内容。
     * @param crs 坐标参考系统字符串,如EPSG:4326；该参数值应该是 GetCapabilities 操作中服务器声明的 CRS。
     * @param boundingBox wms地图服务的地理范围，其中必须left<right,bottom<top
     */
    public WMSLayerView(Context context, String url, String version, String layers, String crs, BoundingBox boundingBox) {
        this(context, url, version, layers);
        setCRS(crs);
        setBounds(boundingBox);
    }

    private void initialize() {
        layerName = "WMS";// 缓存名称需要
        if (!StringUtils.isEmpty(curMapUrl) && curMapUrl.contains("/")) {
            String mapName = curMapUrl.substring(curMapUrl.lastIndexOf('/') + 1);
            layerName += mapName;
        }
        // this.layerBounds = new BoundingBox(new Point2D(-2.00375083427892E7, 2.00375083427892E7), new Point2D(2.00375083427892E7, -2.00375083427892E7));
        this.layerBounds = new BoundingBox(new Point2D(-180.0, 90.0), new Point2D(180.0, -90.0));
        // if ("1.3.0".equals(version)) {
        // this.layerBounds = new BoundingBox(new Point2D(-90.0, 180.0), new Point2D(90.0, -180.0));
        // }
        this.crs = new CoordinateReferenceSystem();
        this.crs.wkid = 4326;
        isGCSLayer = true;// 4326
        // this.resolutions = defaultResolutions;
        isLayerInited = true;// 不用发送请求，因为初始化了layerBounds、resolutions(任意)就可以了
        // 如果没有设置固定分辨率，而只是设置固定比例尺，那么需要设置dpi，用于通过固定比例尺计算出相应的分辨率，是根据分辨率出图的，此处可有可无
        // this.dpi = 0.0254 / 96.0;
        layerParamsToName();
    }

    /**
     * <p>
     * 设置坐标参考系统。该参数值应该是 GetCapabilities 操作中服务器声明的 CRS。
     * 如果请求包含了一个服务器不支持的 CRS，服务器将抛出一个服务异常（code = “InvalidCRS”）。
     * </p>
     * @param crs 坐标参考系统字符串,如EPSG:4326(默认),EPSG:3857等
     * @since 7.0.0
     */
    public void setCRS(String crs) {
        if (!StringUtils.isEmpty(crs)) {
            crsStr = crs;
            int code = 4326;
            if (crs.contains(":")) {
                String codeStr = crs.substring(crs.lastIndexOf(':') + 1);
                // 判断是数字todo
                if (codeStr.matches("[0-9]+")) {
                    code = Integer.valueOf(codeStr);
                }
            }
            this.crs = new CoordinateReferenceSystem();
            this.crs.wkid = code;
            isGCSLayer = Util.isGCSCoordSys(this.crs);
            layerParamsToName();
        }
    }

    /**
     * <p>
     * 设置wms地图服务的地理范围，其中必须left小于right,bottom小于top.
     * </p>
     * @param left 地理范围左
     * @param bottom 地理范围下
     * @param right 地理范围右
     * @param top 地理范围上
     * @since 7.0.0
     */
    public void setBounds(double left, double bottom, double right, double top) {
        if (left >= right || bottom >= top) {
            Log.w(LOG_TAG, "The value of bounds isn't valid");
            return;
        }
        // 判断如果是版本1.3.0且EPSG:4326时，如果用户设置的是x和y方向对调的bounds，这里需要修改回来成正规的
//        if ("1.3.0".equals(version) && "EPSG:4326".equalsIgnoreCase(crsStr)) {
//            // todo
//            this.layerBounds = new BoundingBox(new Point2D(left, top), new Point2D(right, bottom));
//        } else {
            this.layerBounds = new BoundingBox(new Point2D(left, top), new Point2D(right, bottom));
//        }
        layerParamsToName();
    }

    /**
     * <p>
     * 设置wms地图服务的地理范围，其中必须left小于right,bottom小于top.
     * </p>
     * @param boundingBox 地理范围
     * @since 7.0.0
     */
    public void setBounds(BoundingBox boundingBox) {
        if (boundingBox != null) {
            setBounds(boundingBox.getLeft(), boundingBox.getBottom(), boundingBox.getRight(), boundingBox.getTop());
        }
    }

    /**
     * <p>
     * 地图的输出格式。WMS 服务器的 capabilities 文档中声明的 GetMap 中的 Format 之一。
     * 如<Format>image/png</Format>,<Format>image/bmp</Format>,<Format>image/jpeg</Format>,<Format>image/gif</Format>
     * </p>
     * @param format 输出格式，如image/png
     * @since 7.0.0
     */
    public void setFormat(String format) {
        if (!StringUtils.isEmpty(format)) {
            this.format = format;
            layerParamsToName();
        }
    }

    /**
     * <p>
     * 设置背景色，其为十六进制红绿蓝颜色值（默认为 0xFFFFFF）。
     * </p>
     * @param color
     * @since 7.0.0
     */
    public void setBgcolor(String colorStr) {
        if (!StringUtils.isEmpty(colorStr)) {
            this.bgColor = colorStr;
            layerParamsToName();
        }
    }

    /**
     * <p>
     * 设置地图的背景是否透明（默认为 false）。
     * </p>
     * @param transparent 是否透明
     * @since 7.0.0
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
        layerParamsToName();
    }

    /**
     * <p>
     * 设置请求图层的样式列表，图层样式之间以英文逗号分隔。
     * STYLES 值与 LAYERS 参数值是一一对应的。图层名称是服务元数据中定义的<Style><Name>元素值。
     * 如果请求不存在的 Style，服务器将返回一个 服务异常（code=StyleNotDefined）。SuperMap iServer 目前还没有实现，该值应设为空。
     * </p>
     * @param styles 请求图层的样式列表字符串
     * @since 7.0.0
     */
    public void setStyles(String styles) {
        if (!StringUtils.isEmpty(styles)) {
            this.styles = styles;
            layerParamsToName();
        }
    }

    @Override
    String getLayerName() {
        if (StringUtils.isEmpty(paramsToName)) {
            layerParamsToName();
        }
        return this.layerName + paramsToName;
    }

    /**
     * <p>
     * 根据图层属性参数生成hashcode作为缓存目录的组成部分
     * </p>
     * @since 7.0.0
     */
    private void layerParamsToName() {
        // todo 考虑属性字段
        StringBuilder sb = new StringBuilder();
        // 可能还需要考虑layerBounds，因为layerBounds不一样那么层号和列号都不一样，缓存就不能共用，后续在考虑吧
        sb.append(version).append(layers).append(crsStr).append(format).append(bgColor).append(styles).append(layerBounds.toString());
        paramsToName = "_" + sb.toString().hashCode();
        if (transparent) {
            paramsToName += "_t";
        }
    }

    @Override
    public void initTileContext(Tile tile) {
        // http://192.168.120.9:8090/iserver/services/maps/wms111/世界地图_Day?VERSION=1.1.1&REQUEST=GetMap&layers=0.12&STYLES=World&SRS=EPSG:4326&BBOX=-180.0,-90.0,180.0,90.0&WIDTH=800&HEIGHT=400&FORMAT=image/png
        // http://192.168.120.9:8090/iserver/services/map-world/wms130/世界地图_Day?VERSION=1.3.0&REQUEST=GetMap&LAYERS=0.11&STYLES=&CRS=CRS:84&BBOX=-180,-90,180,90&WIDTH=800&HEIGHT=400&FORMAT=image/png。
        Rect tileRect = tile.getRect();
        // Point leftTop = new Point(tileRect.left,tileRect.top);
        // Point rightBottom = new Point(tileRect.right,tileRect.bottom);
        Point2D leftTop = this.mapView.getProjection().fromPixels(tileRect.left, tileRect.top);
        Point2D rightBottom = this.mapView.getProjection().fromPixels(tileRect.right, tileRect.bottom);
        // 如果要做范围控制的话，对比当前瓦片的bounds是否跟图层的bounds有交集，没有则不设置无效的请求url直接返回，先注释
        // BoundingBox tileBounds = new BoundingBox(leftTop, rightBottom);
        // if (!BoundingBox.intersect(tileBounds, layerBounds)) {
        // return;
        // }

        // StringBuilder sb = new StringBuilder();
        String bbox = "";
        // 如果是版本1.3.0且EPSG:4326时，bbox的x和y方向需要对调请求，特殊
        if ("1.3.0".equals(version) && "EPSG:4326".equalsIgnoreCase(crsStr)) {
            // sb.append(rightBottom.y).append(",").append(leftTop.x).append(",").append(leftTop.y).append(",").append(rightBottom.x);
            bbox = rightBottom.y + "," + leftTop.x + "," + leftTop.y + "," + rightBottom.x;
        } else {
            // sb.append(leftTop.x).append(",").append(rightBottom.y).append(",").append(rightBottom.x).append(",").append(leftTop.y);
            bbox = leftTop.x + "," + rightBottom.y + "," + rightBottom.x + "," + leftTop.y;
        }
        String tileUrl = curMapUrl + "?REQUEST=GetMap&VERSION=" + version + "&layers=" + layers + "&" + crsKey + "=" + crsStr + "&BBOX=" + bbox
                + "&WIDTH=256&HEIGHT=256&FORMAT=" + format;
        if (!StringUtils.isEmpty(styles)) {
            tileUrl += "&STYLES=" + styles;
        } else {// 可以没有这个参数
            tileUrl += "&STYLES=";
        }
        if (!"0xFFFFFF".equalsIgnoreCase(bgColor)) {// 不是默认的0xFFFFFF则带上参数
            tileUrl += "&BGCOLOR=" + bgColor;
        }
        if (transparent) {// 不是默认false则带上参数
            tileUrl += "&TRANSPARENT=" + transparent;
        }
        if (Credential.CREDENTIAL != null) {
            tileUrl = tileUrl + "&" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        tile.setUrl(tileUrl);
        // Log.d(LOG_TAG, "tileUrl:" + tileUrl);
    }

}
