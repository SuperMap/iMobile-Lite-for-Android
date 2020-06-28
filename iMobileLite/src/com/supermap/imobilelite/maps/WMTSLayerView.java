package com.supermap.imobilelite.maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.maps.ogc.wmts.GetWMTSCapabilities;
import com.supermap.imobilelite.maps.ogc.wmts.RequestEncoding;
import com.supermap.imobilelite.maps.ogc.wmts.TileMatrix;
import com.supermap.imobilelite.maps.ogc.wmts.WMTSCapabilitiesResult;
import com.supermap.imobilelite.maps.ogc.wmts.WMTSLayerInfo;
import com.supermap.imobilelite.maps.ogc.wmts.WMTSTileMatrixSetInfo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;

/**
 * <p>
 * WMTS 图层视图，作为地图视图的子项添加到 {@link MapView} 中显示。
 * </p>
 * <p>
 * WMTS 是 OGC 提出的首个支持 REST 的服务标准，目前为 1.0.0 版本，该服务支持三种请求模式：HTTP KVP（Key-Value Pair）方式、SOAP 方式、REST 方式，SuperMap iClient 7C for Ansroid 支持 HTTP KVP（Key-Value Pair）方式和 REST 方式两种；支持三种功能GetCapabilities（获取服务的元信息）、GetTile（获取切片）、GetFeatureInfo（可选，获取点选的要素信息在 WMTSLayerView 中，默认首先通过 GetCapabilities 获取图层信息，其次通过 GetTile 请求实际瓦片。若想通过 GetTile 直接获取瓦片出图，可将 WMTSLayerView中enableGetCapabilities 属性设置为 false，此时有如下属性为必设：tileMatrixIdentifiers、resolutions、layer、tileMatrixSet、url、 bounds 属性。若使用默认出图方式，即 WMTSLayerView中enableGetCapabilities 属性设置为 true，则layer、tileMatrixSet、url、bounds为必设。
 * </p>
 * <p>
 * WMTSLayerView 还支持自定义 WMTS 服务，即未（完全）遵循 OGC WMTS 标准比例尺/分辨率数组的 WMTS 服务，如 SuperMap iServer Java 7C 支持使用用户自定义的比例尺集发布 WMTS 服务。同样的，使用自定义 WMTS 服务时，当  WMTSLayerView中enableGetCapabilities 属性设置为 false，tileMatrixIdentifiers、resolutions、layer、tileMatrixSet、url、bounds为必设。当 WMTSLayerView中enableGetCapabilities 属性为 true，layerName、tileMatrixSet、url、bounds、resolutions为必设。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class WMTSLayerView extends AbstractTileLayerView {
    private static final String LOG_TAG = "WMTSLayerView";
    private String version = "1.0.0";
    private String layer;
    private String style;
    private String defStyle = "default";
    private String format;
    private String defFormat = "image/png";
    private boolean enableGetCapabilities = false;
    private String tileMatrixSet;
    private List<String> tileMatrixIds;
    private double[] tileMetrixScales;
    private String requestEncoding = RequestEncoding.REST;
    /**
     * <p>
     * 标准比例尺集名称，用于指定 WMTS 服务使用哪种标准。可选值有如下几种：GlobalCRS84Scale、GlobalCRS84Pixel、GoogleCRS84Quad、GoogleMapsCompatible，这四种为 OGC WMTS 标准，
     * 若 wellKownScaleSet 值不属于上述四种，则按自定义 WMTS 处理. 当 enableGetCapabilities 属性为 true 时，默认情况下首先会根据 tileMatrixSet 属性自动读取 WMTS 描述文件中的 WellKnownScaleSet 标识。
     * 若用户指定了 wellKownScaleSet 值，则以用户为准。若用户未设置，且 WMTS 描述文件中不存在 WellKnownScaleSet 标识，则按自定义 WMTS 处理。
     * 当 enableGetCapabilities 属性为 false 时，wellKownScaleSet 为必设，否则按自定义 WMTS 处理。
     * </p>
     * @since 7.0.0
     */
    private String wellKnownScaleSet = "GlobalCRS84Scale";// 4326 GoogleMapsCompatible:3857

    // WMTS图层时需要依照服务端出图的标准比例尺集( GlobalCRS84Scale、 GlobalCRS84Pixel、 GoogleCRS84Quad、 GoogleMapsCompatible)设置此属性，
    // 否则在出图时可能会出现地图偏差等问题。 用户自定义缩放级别时须同时设置resolutions和matrixIds信息，并保证两者信息一一对应。 各个标准比例尺集对应的分辨率数组如下：
    private static final double[] GlobalCRS84ScaleResolutions = new double[] { 1.25764139776733, 0.628820698883665, 0.251528279553466, 0.125764139776733,
            0.0628820698883665, 0.0251528279553466, 0.0125764139776733, 0.00628820698883665, 0.00251528279553466, 0.00125764139776733, 0.000628820698883665,
            0.000251528279553466, 0.000125764139776733, 0.0000628820698883665, 0.0000251528279553466, 0.0000125764139776733, 0.00000628820698883665,
            0.00000251528279553466, 0.00000125764139776733, 0.000000628820698883665, 0.00000025152827955346 };// 级别:21
    // private static final double[] GlobalCRS84ScaleScales = new double[] { 500000000, 250000000, 100000000, 50000000, 25000000, 10000000, 5000000, 2500000,
    // 1000000, 500000, 250000, 100000, 50000, 25000, 10000, 5000, 2500, 1000, 500, 250, 100 };

    private static final double[] GoogleCRS84QuadResolutions = new double[] { 1.40625000000000, 0.703125000000000, 0.351562500000000, 0.175781250000000,
            0.0878906250000000, 0.0439453125000000, 0.0219726562500000, 0.0109863281250000, 0.00549316406250000, 0.00274658203125000, 0.00137329101562500,
            0.000686645507812500, 0.000343322753906250, 0.000171661376953125, 0.0000858306884765625, 0.0000429153442382812, 0.0000214576721191406,
            0.0000107288360595703, 0.00000536441802978516 };// 级别:19
    // private static final double[] GoogleCRS84QuadScales = new double[] { 559082264.0287178, 279541132.0143589, 139770566.0071794, 69885283.00358972,
    // 34942641.50179486, 17471320.75089743, 8735660.375448715, 4367830.187724357, 2183915.093862179, 1091957.546931089, 545978.7734655447,
    // 272989.3867327723, 136494.6933663862, 68247.34668319309, 34123.67334159654, 17061.83667079827, 8530.918335399136, 4265.459167699568,
    // 2132.729583849784 };

    private static final double[] GlobalCRS84PixelResolutions = new double[] { 240000, 120000, 60000, 40000, 20000, 10000, 4000, 2000, 1000, 500, 166, 100, 33,
            16, 10, 3, 1, 0.33 };// 级别:18
    // private static final double[] preDefResPixelScales = new double[] { 795139219.9519541, 397569609.9759771, 198784804.9879885, 132523203.3253257,
    // 66261601.66266284, 33130800.83133142, 13252320.33253257, 6626160.166266284, 3313080.083133142, 1656540.041566571, 552180.0138555236,
    // 331308.0083133142, 110436.0027711047, 55218.00138555237, 33130.80083133142, 11043.60027711047, 3313.080083133142, 1104.360027711047 };

    private static final double[] GoogleMapsCompatibleResolutions = new double[] { 156543.0339280410, 78271.51696402048, 39135.75848201023, 19567.87924100512,
            9783.939620502561, 4891.969810251280, 2445.984905125640, 1222.992452562820, 611.4962262814100, 305.7481131407048, 152.8740565703525,
            76.43702828517624, 38.21851414258813, 19.10925707129406, 9.554628535647032, 4.777314267823516, 2.388657133911758, 1.194328566955879,
            0.5971642834779395 };// 级别:19
    // private static final double[] preDefGoogleMapsCompatibleScales = new double[] { 559082264.0287178, 279541132.0143589, 139770566.0071794, 69885283.00358972,
    // 34942641.50179486, 17471320.75089743, 8735660.375448715, 4367830.187724357, 2183915.093862179, 1091957.546931089, 545978.7734655447,
    // 272989.3867327723, 136494.6933663862, 68247.34668319309, 34123.67334159654, 17061.83667079827, 8530.918335399136, 4265.459167699568,
    // 2132.729583849784 };

    // private static final double[] chinaWmtsScales = new double[] { 295829355.45, 147914677.73, 73957338.86, 36978669.43, 18489334.72, 9244667.36, 4622333.68,
    // 2311166.84, 1155583.42, 577791.71, 288895.85, 144447.93, 72223.96, 36111.98, 18055.99, 9028.00, 4514.00, 2257.00, 1128.50, 564.25 };
    // private static final double[] chinaWmtsResolutionsInMeter = new double[] { 78271.52, 39135.76, 19567.88, 9783.94, 4891.97, 2445.98, 1222.99, 611.50,
    // 305.75, 152.87, 76.44, 38.22, 19.11, 9.55, 4.78, 2.39, 1.19, 0.60, 0.2986, 0.1493 };
    private static final double[] chinaWmtsResolutionsInDegree = new double[] { 1.406249999978297, 0.7031249999891485, 0.35156249999999994,
            0.17578124999999997, 0.08789062500000014, 0.04394531250000007, 0.021972656250000007, 0.01098632812500002, 0.00549316406250001,
            0.0027465820312500017, 0.0013732910156250009, 0.000686645507812499, 0.0003433227539062495, 0.00017166137695312503, 0.00008583068847656251,
            0.000042915344238281406, 0.000021457672119140645, 0.000010728836059570307, 0.000005364418029785169, 0.000002682210361715995,
            0.0000013411051808579975 };
    // 考虑属性参数影响瓦片的内容，所以此属性记录属性参数们对应的hashcode并生成相关的字符串作为缓存文件的一部分
    private String paramsToName = "";
    private OnStatusChangedListener listener;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     */
    public WMTSLayerView(Context context) {
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
    public WMTSLayerView(Context context, AttributeSet attrs) {
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
    public WMTSLayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param url wmts地图服务的 url
     * @param layer 请求的 WMTS 服务的图层名称
     * @param tileMatrixSet 瓦片矩阵集的唯一标识符，矩阵集
     * @param enableGetCapabilities 是否执行 GetCapabilities 操作，默认为false(建议设置为false，必要时设置为true)
     * @param tileMatrixIds 显示级别名称集合
     * @param resolutions 分辨率数组
     */
    public WMTSLayerView(Context context, String url, String layer, String tileMatrixSet, boolean enableGetCapabilities, double[] resolutions,
            List<String> tileMatrixIds) {
        this(context, url, layer, tileMatrixSet, enableGetCapabilities, resolutions);
        setTileMatrixIds(tileMatrixIds);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param url wmts地图服务的 url
     * @param layer 请求的 WMTS 服务的图层名称
     * @param tileMatrixSet 瓦片矩阵集的唯一标识符，矩阵集。
     * @param enableGetCapabilities 是否执行 GetCapabilities 操作，默认为false(建议设置为false，必要时设置为true)
     * @param resolutions 分辨率数组
     */
    public WMTSLayerView(Context context, String url, String layer, String tileMatrixSet, boolean enableGetCapabilities, double[] resolutions) {
        this(context, url, layer, tileMatrixSet, enableGetCapabilities);
        setResolutions(resolutions);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。 
     * @param url wmts地图服务的 url
     * @param layer 请求的 WMTS 服务的图层名称
     * @param tileMatrixSet 瓦片矩阵集的唯一标识符，矩阵集
     * @param enableGetCapabilities 是否执行 GetCapabilities 操作，默认为false(建议设置为false，必要时设置为true)
     */
    public WMTSLayerView(Context context, String url, String layer, String tileMatrixSet, boolean enableGetCapabilities) {
        super(context);
        this.curMapUrl = url;
        this.layer = layer;
        setTileMatrixSet(tileMatrixSet);
        setEnableGetCapabilities(enableGetCapabilities);
        initialize();
        // http://192.168.120.9:8090/iserver/services/map-world/wmts100/1.0.0/WMTSCapabilities.xml
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。 
     * @param url wmts地图服务的 url
     * @param layer 请求的 WMTS 服务的图层名称
     * @param tileMatrixSet 瓦片矩阵集的唯一标识符，矩阵集
     */
    public WMTSLayerView(Context context, String url, String layer, String tileMatrixSet) {
        this(context, url, layer, tileMatrixSet, false);
    }

    private void initialize() {
        layerName = "WMTS_";// 缓存名称需要
        if (!StringUtils.isEmpty(layer)) {
            layerName += this.layer;
        }
        // this.layerBounds = new BoundingBox(new Point2D(-2.00375083427892E7, 2.00375083427892E7), new Point2D(2.00375083427892E7, -2.00375083427892E7));
        if (!this.enableGetCapabilities) {
            initDefaultStatus();
        }
        // else {
        // this.layerBounds = new BoundingBox(new Point2D(-180.0, 90.0), new Point2D(180.0, -90.0));
        // this.crs = new CoordinateReferenceSystem();
        // this.crs.wkid = 4326;
        // isGCSLayer = true;// 4326
        // }

        // this.resolutions = defaultResolutions;
        // isLayerInited = true;// 不用发送请求，因为初始化了layerBounds、resolutions(任意)就可以了
        // 如果没有设置固定分辨率，而只是设置固定比例尺，那么需要设置dpi，用于通过固定比例尺计算出相应的分辨率，是根据分辨率出图的，此处可有可无
        // this.dpi = 0.0254 / 96.0;
        layerParamsToName();
    }

    private void initDefaultStatus() {
        if (!StringUtils.isEmpty(tileMatrixSet)) {
            initBoundsAndCrs();
            initResolutions();
            // 不是标准的使用默认
            if (resolutions == null) {
                resolutions = changeResolutions(chinaWmtsResolutionsInDegree.clone());
            }
            initTileMatrixIds(resolutions.length);
            checkLayerInited();
        }
    }

    private void initBoundsAndCrs() {
        if (tileMatrixSet.contains("GlobalCRS84Pixel") || tileMatrixSet.contains("GoogleMapsCompatible")) {
            this.layerBounds = new BoundingBox(new Point2D(-2.00375083427892E7, 2.00375083427892E7), new Point2D(2.00375083427892E7, -2.00375083427892E7));
            this.crs = new CoordinateReferenceSystem();
            this.crs.wkid = 3857;
            isGCSLayer = false;// 3857
        } 
//        else if (tileMatrixSet.contains("GoogleCRS84Quad") || tileMatrixSet.contains("GlobalCRS84Scale")) {
//            this.layerBounds = new BoundingBox(new Point2D(-180.0, 90.0), new Point2D(180.0, -90.0));
//            this.crs = new CoordinateReferenceSystem();
//            this.crs.wkid = 4326;
//            isGCSLayer = true;// 4326
//        } 
        else {// 不是标准的使用默认
            this.layerBounds = new BoundingBox(new Point2D(-180.0, 90.0), new Point2D(180.0, -90.0));
            this.crs = new CoordinateReferenceSystem();
            this.crs.wkid = 4326;
            isGCSLayer = true;// 4326
        }
    }

    private void initResolutions() {
        if (tileMatrixSet.contains("GlobalCRS84Pixel")) {
            this.resolutions = GlobalCRS84PixelResolutions.clone();
        } else if (tileMatrixSet.contains("GoogleCRS84Quad")) {
            this.resolutions = changeResolutions(GoogleCRS84QuadResolutions.clone());
        } else if (tileMatrixSet.contains("GoogleMapsCompatible")) {
            this.resolutions = GoogleMapsCompatibleResolutions.clone();
        } else if (tileMatrixSet.contains("GlobalCRS84Scale")) {
            this.resolutions = changeResolutions(GlobalCRS84ScaleResolutions.clone());
        }
    }

    private double[] changeResolutions(double[] rs) {
        for (int i = 0; i < rs.length; i++) {
            rs[i] = rs[i] * Math.PI * 6378137.0 / 180.0;
        }
        return rs;
    }

    private boolean checkLayerInited() {
        if (layerBounds.isValid() && crs != null && resolutions != null && resolutions.length > 0) {
            isLayerInited = true;
            return true;
        }
        return false;
    }

    /**
     * <p>
     * 设置图层名
     * </p>
     * @param layer
     * @since 7.0.0
     */
    public void setLayer(String layer) {
        if (!StringUtils.isEmpty(layer)) {
            this.layer = layer;
            layerName = "WMTS_" + this.layer;
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
        this.layerBounds = new BoundingBox(new Point2D(left, top), new Point2D(right, bottom));
        layerParamsToName();
        checkLayerInited();
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
     * 设置请求图层的样式列表，图层样式之间以英文逗号分隔。
     * STYLES 值与 LAYERS 参数值是一一对应的。图层名称是服务元数据中定义的<Style><Name>元素值。
     * 如果请求不存在的 Style，服务器将返回一个 服务异常（code=StyleNotDefined）。SuperMap iServer 目前还没有实现，该值应设为空。
     * </p>
     * @param styles 请求图层的样式列表字符串
     * @since 7.0.0
     */
    public void setStyle(String style) {
        if (!StringUtils.isEmpty(style)) {
            this.style = style;
            layerParamsToName();
        }
    }

    private void setEnableGetCapabilities(boolean enableGetCapabilities) {
        this.enableGetCapabilities = enableGetCapabilities;
        if (this.enableGetCapabilities) {
            // 发送请求
            GetCapabilitiesTask getCapabilitiesTask = new GetCapabilitiesTask();
            getCapabilitiesTask.execute(this.curMapUrl);
        }
    }

    /**
     * <p>
     * 设置服务请求模式
     * </p>
     * @param requestEncoding
     * @since 7.0.0
     */
    public void setRequestEncoding(String requestEncoding) {
        if (!StringUtils.isEmpty(requestEncoding)) {
            if (requestEncoding.equalsIgnoreCase("KVP")) {
                this.requestEncoding = RequestEncoding.KVP;
            } else if (requestEncoding.equalsIgnoreCase("REST")) {
                this.requestEncoding = RequestEncoding.REST;
            }
        }
    }

    private void setTileMatrixIds(List<String> tileMatrixIds) {
        if (tileMatrixIds != null && tileMatrixIds.size() > 0) {

            this.tileMatrixIds = new ArrayList<String>();
            for (int i = 0; i < tileMatrixIds.size(); i++) {
                this.tileMatrixIds.add(tileMatrixIds.get(i));
            }
            layerParamsToName();
        }
    }

    /**
     * <p>
     * 设置瓦片矩阵集的唯一标识符，矩阵集名称
     * </p>
     * @param tileMatrixSet
     * @since 7.0.0
     */
    public void setTileMatrixSet(String tileMatrixSet) {
        if (!StringUtils.isEmpty(tileMatrixSet)) {
            this.tileMatrixSet = tileMatrixSet;
            layerParamsToName();
        } else {
            // 提示必设参数
            this.tileMatrixSet = "";
        }
    }

    private void setResolutions(double[] rs) {
        if (rs != null && rs.length > 0) {
            double[] rsOrder = Tool.getResolutions(rs);
            if (tileMatrixSet.contains("GoogleCRS84Quad") || tileMatrixSet.contains("GlobalCRS84Scale")) {
                this.resolutions = changeResolutions(rsOrder);
            } else {
                this.resolutions = rsOrder;
            }
            layerParamsToName();
            checkLayerInited();
            if (this.tileMatrixIds == null) {
                this.tileMatrixIds = new ArrayList<String>();
            }
            for (int i = 0; i < resolutions.length; i++) {
                this.tileMatrixIds.add(String.valueOf(i));
            }
//            initTileMatrixIds(resolutions.length);
        }
    }

    public double[] getWMTSResolutions() {
        double[] rs = resolutions.clone(); 
        if (tileMatrixSet.contains("GoogleCRS84Quad") || tileMatrixSet.contains("GlobalCRS84Scale")) {
            for (int i = 0; i < rs.length; i++) {
                rs[i] = rs[i] / Math.PI / 6378137.0 * 180.0;
            }
        } 
        return rs;
    }

    private void initTileMatrixIds(int length) {
        if (this.tileMatrixIds != null && this.tileMatrixIds.size() > 0) {
            return;
        }
        if (this.tileMatrixIds == null) {
            this.tileMatrixIds = new ArrayList<String>();
        }
        for (int i = 0; i < length; i++) {
            this.tileMatrixIds.add(String.valueOf(i));
        }
    }

    @Override
    public void setCRS(CoordinateReferenceSystem crs) {
        if (crs != null) {
            this.crs = crs;
            checkLayerInited();
            this.isGCSLayer = Util.isGCSCoordSys(crs);
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
        String tempFormat = StringUtils.isEmpty(format) ? defFormat : format;
        String tempStyle = StringUtils.isEmpty(style) ? defStyle : style;
        sb.append(tempFormat).append(tempStyle).append(tileMatrixSet).append(layerBounds);
        if (tileMatrixIds != null && tileMatrixIds.size() > 0) {
            for (int i = 0; i < tileMatrixIds.size(); i++) {
                sb.append(tileMatrixIds.get(i));
            }
        }
        if (this.resolutions != null && resolutions.length > 0) {
            for (int i = 0; i < resolutions.length; i++) {
                sb.append(this.resolutions[i]);
            }
        }
        paramsToName = "_" + sb.toString().hashCode();
    }

    @Override
    public void initTileContext(Tile tile) {
        // KVP: <ServiceRoot>?SERVICE=WMTS&REQUEST=GetTile&VERSION=version&Layer=&Style=&Format=&TileMatrixSet=&TileMatrix=&TileRow=&TileCol=
        // http://192.168.120.9:8090/iserver/services/map-world/wmts100?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&Layer=World+Map&Style=default&Format=image/png&TileMatrixSet=GlobalCRS84Scale_World+Map&TileMatrix=2&TileRow=0&TileCol=0
        // REST: <ServiceRoot>/{layer}/{style}/{TileMatrixSet}/{TileMatrix}/{TileRow}/{TileCol}[.Format]
        // <ServiceRoot>/{layer}/{TileMatrixSet}/{TileMatrix}/{TileRow}/{TileCol}[.Format]
        // http://192.168.120.9:8090/iserver/services/map-world/wmts100/World+Map/default/GlobalCRS84Scale_World+Map/2/0/0.png
        // Rect tileRect = tile.getRect();
        // Point leftTop = new Point(tileRect.left,tileRect.top);
        // Point rightBottom = new Point(tileRect.right,tileRect.bottom);
        // Point2D leftTop = this.mapView.getProjection().fromPixels(tileRect.left, tileRect.top);
        // Point2D rightBottom = this.mapView.getProjection().fromPixels(tileRect.right, tileRect.bottom);
        // 如果要做范围控制的话，对比当前瓦片的bounds是否跟图层的bounds有交集，没有则不设置无效的请求url直接返回，先注释
        // BoundingBox tileBounds = new BoundingBox(leftTop, rightBottom);
        // if (!BoundingBox.intersect(tileBounds, layerBounds)) {
        // return;
        // }
        int index = getResolutionIndex();
        if (index == -1) {
            return;
        }
        String tileMatrix = "";
        if (this.tileMatrixIds != null && index < this.tileMatrixIds.size()) {
            tileMatrix = tileMatrixIds.get(index);
        } else {
            return;
        }
        // todo url中layer和tileMatrixSet需要编码
        // http://192.168.120.9:8090/iserver/services/map-world/wmts100/World+Map/default/GlobalCRS84Scale_World+Map/{TileMatrix}/{TileRow}/{TileCol}.png
        String layerEncode = this.layer;
        String tileMatrixSetEncode = this.tileMatrixSet;
        try {
            layerEncode = URLEncoder.encode(this.layer, Constants.UTF8);// 对地图名进行编码
            tileMatrixSetEncode = URLEncoder.encode(this.tileMatrixSet, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            Log.w(LOG_TAG, "layer and tileMatrixSet 编码失败：" + e.getMessage());
        }
        String format = ".png";
        if (!StringUtils.isEmpty(this.format)) {
            format = "." + this.format.substring(this.format.lastIndexOf('/') + 1);
        }
        // format和style在不发送GetCapabilities和用户没有设置值时，使用默认值
        if (StringUtils.isEmpty(this.format)) {
            this.format = this.defFormat;
        }
        if (StringUtils.isEmpty(this.style)) {
            this.style = this.defStyle;
        }
        String tileUrl = curMapUrl + "/" + layerEncode + "/" + this.style + "/" + tileMatrixSetEncode + "/" + tileMatrix + "/" + tile.getY() + "/"
                + tile.getX() + format;
        Log.e("++++++++",tileUrl+"");
        if(curMapUrl.contains("?")&&curMapUrl.contains("/")){
            int li= curMapUrl.lastIndexOf("/");
            int li1 = curMapUrl.lastIndexOf("?");
            if (li1 > li) {// 问号在url的后面作为参数的拼接符
                tileUrl = curMapUrl.substring(0, li1) + "/" + layerEncode + "/" + this.style + "/" + tileMatrixSetEncode + "/" + tileMatrix + "/" + tile.getY()
                        + "/" + tile.getX() + format + curMapUrl.substring(li1 - 1, curMapUrl.length());
            }
        }
        // ?SERVICE=WMTS&REQUEST=GetTile&VERSION=version&Layer=&Style=&Format=&TileMatrixSet=&TileMatrix=&TileRow=&TileCol=
        if (RequestEncoding.KVP.equals(requestEncoding)) {
            tileUrl = curMapUrl + "?SERVICE=WMTS&REQUEST=GetTile&VERSION=" + version + "&Layer=" + layerEncode + "&Style=" + this.style + "&Format="
                    + this.format + "&TileM" +
                    "atrixSet=" + this.tileMatrixSet + "&TileMatrix=" + tileMatrix + "&TileRow=" + tile.getY() + "&TileCol="
                    + tile.getX();
            if(curMapUrl.contains("?")&&curMapUrl.contains("/")){
                int li= curMapUrl.lastIndexOf("/");
                int li1 = curMapUrl.lastIndexOf("?");
                if (li1 > li) {// 问号在url的后面作为参数的拼接符
                    tileUrl = curMapUrl + "&SERVICE=WMTS&REQUEST=GetTile&VERSION=" + version + "&Layer=" + layerEncode + "&Style=" + this.style + "&Format="
                            + this.format + "&TileMatrixSet=" + this.tileMatrixSet + "&TileMatrix=" + tileMatrix + "&TileRow=" + tile.getY() + "&TileCol="
                            + tile.getX();
                }
            }
        }
        if (Credential.CREDENTIAL != null) {
            tileUrl = tileUrl + "&" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        tile.setUrl(tileUrl);
        Log.d(LOG_TAG, "tileUrl:" + tileUrl);
    }

    public void setGCSLayer(boolean isGCSLayer) {
        this.isGCSLayer = isGCSLayer;
    }
    
    /**
     * <p>
     * 发送获取WMTS服务元数据信息的异步任务，获取完成并解析完成后读取相应的初始化信息后通知mapview
     * </p>
     * @author ${huangqh}
     * @version ${Version}
     * @since 7.0.0
     * 
     */
    class GetCapabilitiesTask extends AsyncTask<String, Integer, WMTSCapabilitiesResult> {
        /**
         * <p>
         * 用于在执行后台任务前做一些UI操作
         * </p>
         * @since 7.0.0
         */
        @Override
        protected void onPreExecute() {
        };

        /**
         * <p>
         * 内部执行后台任务，不可在此方法中修改UI
         * </p>
         * @param params
         * @return
         * @since 7.0.0
         */
        @Override
        protected WMTSCapabilitiesResult doInBackground(String... params) {
            if (isLayerInited) {// 已经初始化完成就无需再次发送请求
                return null;
            }
            GetWMTSCapabilities getWMTSCapabilities = new GetWMTSCapabilities(params[0]);
            return getWMTSCapabilities.getCapabilities();
        }

        /**
         * <p>
         * 用于更新进度信息
         * </p>
         * @param values
         * @since 7.0.0
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        /**
         * <p>
         * 用于在执行完后台任务后更新UI显示结果
         * </p>
         * @param result
         * @since 7.0.0
         */
        @Override
        protected void onPostExecute(WMTSCapabilitiesResult result) {
            // TODO 解析结果，初始化bounds、crs、isGCSLayer和resolutions，最后checkLayerInited()成功，通知mapview更新状态
            // super.onPostExecute(result);
            if (result != null) {
                WMTSLayerInfo layerInfo = null;
                WMTSTileMatrixSetInfo tileMatrixSetInfo = null;
                if (!StringUtils.isEmpty(layer) && result.layerInfos != null && result.layerInfos.size() > 0) {
                    for (int i = 0; i < result.layerInfos.size(); i++) {
                        layerInfo = result.layerInfos.get(i);
                        if (layerInfo != null && layer.equals(layerInfo.name)) {
                            if (!StringUtils.isEmpty(layerInfo.style) && StringUtils.isEmpty(style)) {
                                style = layerInfo.style;// 用户设置优先，没有用户设置，那么就自己初始化
                            }
                            if (!StringUtils.isEmpty(layerInfo.imageFormat) && StringUtils.isEmpty(format)) {
                                format = layerInfo.imageFormat;// 用户设置优先，没有用户设置，那么就自己初始化
                            }
                        }
                    }
                }
                if (!StringUtils.isEmpty(tileMatrixSet) && result.tileMatrixSetInfos != null && result.tileMatrixSetInfos.size() > 0) {
                    for (int i = 0; i < result.layerInfos.size(); i++) {
                        tileMatrixSetInfo = result.tileMatrixSetInfos.get(i);
                        if (tileMatrixSetInfo != null && tileMatrixSet.equals(tileMatrixSetInfo.name)) {
                            // String supportedCRS = tileMatrixSetInfo.supportedCRS;
                            if (tileMatrixSetInfo.tileMatrixs != null && tileMatrixSetInfo.tileMatrixs.size() > 0) {
                                // 判断用户是否已经设置tileMatrixIds
                                boolean isTileMatrixIdsInited = (tileMatrixIds != null && tileMatrixIds.size() > 0);
                                int count = tileMatrixSetInfo.tileMatrixs.size();
                                tileMetrixScales = new double[count];
                                for (int j = 0; j < count; j++) {
                                    TileMatrix tileMatrix = tileMatrixSetInfo.tileMatrixs.get(j);
                                    if (tileMatrix != null && !isTileMatrixIdsInited) {// 用户设置优先，没有用户设置，那么就自己初始化
                                        if (tileMatrixIds == null) {
                                            tileMatrixIds = new ArrayList<String>();
                                        }
                                        tileMatrixIds.add(tileMatrix.id);
                                        tileMetrixScales[j] = tileMatrix.scaleDenominator;
                                    }
                                }
                            }
                            // if (layerInfo != null && layerInfo.boundingBoxes != null&&layerInfo.boundingBoxes.size()>0) {
                            // for (int j = 0; j < layerInfo.boundingBoxes.size(); j++) {
                            // BoundsWithCRS boundsWithCRS = layerInfo.boundingBoxes.get(j);
                            // if(boundsWithCRS!=null&&supportedCRS.equalsIgnoreCase(boundsWithCRS.crs)){
                            //
                            // }
                            // }
                            // }
                        }
                    }
                }
                // 初始化bounds、crs、isGCSLayer和resolutions，最后checkLayerInited()成功，通知mapview更新状态
                if (!StringUtils.isEmpty(tileMatrixSet)) {
                    if (layerBounds == null || !layerBounds.isValid()) {// 用户设置优先，没有用户设置，那么就自己初始化
                        initBoundsAndCrs();
                    }
                    if (resolutions == null) {// 用户设置优先，没有用户设置，那么就自己初始化
                        initResolutions();
                    }
                    // 如果不是标准的wellKnownScaleSet，采用默认值，但是可以考虑使用tileMetrixScales计算出resolutions
                    if (resolutions == null) {
//                        if (tileMetrixScales != null && tileMetrixScales.length > 0) {
//                            // 使用tileMetrixScales计算出resolutions
//                        }
                        resolutions = changeResolutions(chinaWmtsResolutionsInDegree.clone());
                    }
                    checkLayerInited();
                }
                layerParamsToName();
                if (isLayerInited && mapView != null && listener != null) {// 通知mapview更新状态，如果此处图层还没有被add到mapview中去mapview为空
                    listener.onStatusChanged(WMTSLayerView.this, OnStatusChangedListener.STATUS.INITIALIZED);
                }
            }
        }

        /**
         * <p>
         * 用于在取消执行中的任务时更新UI
         * </p>
         * @since 7.0.0
         */
        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }
    }

    /**
     * <p>
     * 设置图层状态发送变化的监听器
     * </p>
     * @param listener
     * @since 7.0.0
     */
    void setLayerViewStatusChangeListener(OnStatusChangedListener listener) {
        this.listener = listener;
    }

}
