package com.supermap.imobilelite.thirdpartlayers;

import android.content.Context;
import android.util.AttributeSet;

import com.supermap.imobilelite.maps.AbstractTileLayerView;
import com.supermap.imobilelite.maps.BoundingBox;
import com.supermap.imobilelite.maps.CoordinateReferenceSystem;
import com.supermap.imobilelite.maps.Point2D;
import com.supermap.imobilelite.maps.Tile;

public class TDTLayerView extends AbstractTileLayerView {
    // 记录日志时类特定的标签
    private static final String LOG_TAG = "com.supermap.imobilelite.thirdpartlayers.TDTLayerView";
    // "http://t${num}.tianditu.com/DataServer?T=${type}_${proj}&x=${x}&y=${y}&l=${z}"
    // private String url = "http://tile7.tianditu.com/DataServer?T=vec_c";// (c:EPSG:4326,w:其他坐标系) (vec:矢量图层，cva:矢量标签图层，img:影像图层,cia:影像标签图层，ter:地形,cta:地形标签图层)
    // private double defaultResolutions[] = { 0.703125, 0.35156249999999994, 0.17578124999999997, 0.08789062500000014, 0.04394531250000007, 0.021972656250000007,
    // 0.01098632812500002, 0.00549316406250001, 0.0027465820312500017, 0.0013732910156250008, 0.000686645507812499, 0.0003433227539062495,
    // 0.00017166137695312503, 0.00008583068847656251, 0.000042915344238281406, 0.000021457672119140645, 0.000010728836059570307, 0.00000536441802978516 };

    // 图层类型(vec:矢量图层，cva:矢量标签图层，img:影像图层,cia:影像标签图层，ter:地形,cta:地形标签图层)
    private String layerType = "vec";
    // 是否是标签图层
    private boolean isLabel = false;
    // 图片url中z值偏移量
    private int zOffset = 1;
    // epsgcode编码，只包含数字部分("4326"或"900913")，默认为"4326"
    private String epsgcode = "4326";

    public TDTLayerView(Context context) {
        super(context);
        initialize();
    }

    public TDTLayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDTLayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文
     * @param layerType 图层类型，只有这个三个值(vec:矢量图层，img:影像图层，ter:地形图层)，默认vec
     * @param isLabel 是否是标签图层 ，默认是false
     * @param epsgcode epsgcode编码，只包含数字部分("4326"或"900913")，默认为"4326"
     */
    public TDTLayerView(Context context, String layerType, boolean isLabel, String epsgcode) {
        super(context);
        if ("vec".equalsIgnoreCase(layerType) || "img".equalsIgnoreCase(layerType) || "ter".equalsIgnoreCase(layerType)) {
            this.layerType = layerType;
        }
        this.isLabel = isLabel;
        if (epsgcode != null && !"".equals(epsgcode)) {
            this.epsgcode = epsgcode;
        }
        initialize();
    }

    private void initialize() {
        layerName = "TianDiTu_" + getlayerType() + "_" + getProj();// 必设，指明地图名，以便区分和做缓存需要，可以自定义
        // 初始化必设参数
        initParam();
        // 标识地图是否初始化完成，必设
        isLayerInited = true;// 不用发送请求，因为初始化了layerBounds、resolutions就可以了
    }

    private String getlayerType() {
        String lt = this.layerType;
        if (this.isLabel) {
            if ("vec".equals(lt))
                lt = "cva";
            if ("img".equals(lt))
                lt = "cia";
            if ("ter".equals(lt))
                lt = "cta";
        }
        return lt;
    }

    private String getProj() {
        String proj = "c";
        if (!"4326".equals(this.epsgcode)) {
            proj = "w";
        }
        return proj;
    }

    private void initParam() {
        // 必设，坐标参考系
        this.crs = new CoordinateReferenceSystem();
        int resLen = 18;
        int resStart = 0;
        if ("vec".equals(layerType)) {
            resLen = 18;
            resStart = 0;
            zOffset = 1;
        } else if ("img".equals(layerType)) {
            resLen = 17;
            resStart = 1;
            this.zOffset = 2;
        } else if ("ter".equals(layerType)) {
            resLen = 14;
            resStart = 0;
            this.zOffset = 1;
        }
        double minX = -180;
        double minY = -90;
        double maxX = 180;
        double maxY = 90;
        double[] rs = new double[resLen];
        // 分辨率统一以米为单位
        for (int i = 0; i < resLen; i++) {
            rs[i] = 156543.0339 / 2 / (Math.pow(2, i + resStart));
        }
        if ("4326".equals(this.epsgcode)) {
            minX = -180;
            minY = -90;
            maxX = 180;
            maxY = 90;
            // var maxResolution = 156543.0339;
            // var minResolution = 0.5971642833709717;
            // 分辨率统一以米为单位，地图投影为4326那么resolutions则是以度为单位，此处把度转换成米
            // for (int i = resStart; i < resLen; i++) {
            // rs[i] = 1.40625 / 2 / (Math.pow(2, i));
            // }
            this.crs.unit = "degree";
            this.crs.wkid = 4326;
            isGCSLayer = true;// 必设，是否为地理坐标系，必须指明，4326所以true，如果epsgCode > 4000 && epsgCode < 5000返回true，自定义地理坐标系的话需要注意
        } else {
            minX = -20037508.3392;
            minY = -20037508.3392;
            maxX = 20037508.3392;
            maxY = 20037508.3392;
            // var maxResolution = 156543.0339;
            // var minResolution = 0.5971642833709717;
            // for (int i = resStart; i < resLen; i++) {
            // rs[i] = 156543.0339 / 2 / (Math.pow(2, i));
            // }
            // this.numZoomLevels = 18;
            this.crs.unit = "m";
            this.crs.wkid = 900913;// "EPSG:900913"
            isGCSLayer = false;
        }
        // 地图的bounds，必设
        this.layerBounds = new BoundingBox(new Point2D(minX, maxY), new Point2D(maxX, minY));
        // 必设，固定分辨率
        this.resolutions = rs;
    }

    @Override
    public void initTileContext(Tile tile) {
        // 获取真正的出图层级，因为mapview的层级数是所有图层固定比例尺数组合并的长度
        int index = getResolutionIndex();
        if (index == -1) {
            return;
        }
        index += this.zOffset;
        int x = tile.getX();
        int y = tile.getY();
        int num = Math.abs((x + y) % 8);// tile0...7
        String lt = getlayerType();
        String proj = getProj();
//        String result = "http://tile" + num + ".tianditu.com/DataServer?T=" + lt + "_" + proj + "&X=" + x + "&Y=" + y + "&L=" + index;
        String result = "http://t" + num + ".tianditu.com/DataServer?T=" + lt + "_" + proj + "&X=" + 
        					x + "&Y=" + y + "&L=" + index + "&tk=48b48acac18f39d428efe921effdd310";//tk在天地图官网申请并替换，申请时选择应用类型为"服务器"
        // &X=0&Y=0&L=2
        // String result = url + "&X=" + x + "&Y=" + y + "&L=" + index;
        // Log.d(LOG_TAG, "getTileURL:" + result);
        tile.setUrl(result);
    }

}
