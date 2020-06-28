package com.supermap.imobilelite.maps;

import com.supermap.services.util.ResourceManager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

/**
 * <p>
 * 云图层视图，作为地图视图的子项添加到 {@link MapView} 中显示。
 * 云图层视图用于显示 SuperMap云服务提供的地图图层，常作为底图使用。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
public class CloudLayerView extends AbstractTileLayerView {
    private static final String LOG_TAG = "com.supermap.android.maps.cloudLayerView";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private String cacheUrl = "http://t1.supermapcloud.com/FileService/image";
    private String cloudMapName = "quanguo";
    private String type = "web";
    // private double defaultResolutions[] = { 156605.46875, 78302.734375, 39151.3671875, 19575.68359375, 9787.841796875, 4893.9208984375, 2446.96044921875,
    // 1223.48022460937, 611.740112304687, 305.870056152344, 152.935028076172, 76.4675140380859, 38.233757019043, 19.1168785095215, 9.55843925476074,
    // 4.77921962738037, 2.38960981369019, 1.19480490684509, 0.597402453422546 };
    private double defaultResolutions[] = { 156543.033928041, 78271.5169640203, 39135.7584820102, 19567.8792410051, 9783.93962050254, 4891.96981025127,
            2445.98490512563, 1222.99245256282, 611.496226281409, 305.748113140704, 152.874056570352, 76.4370282851761, 38.218514142588, 19.109257071294,
            9.55462853564701, 4.77731426782351, 2.38865713391175, 1.19432856695588, 0.597164283477938 };// 第19级分辨率为0.298817952474，但由于绝大部分城市和地区在此级别都无图，所以暂不增加
    private boolean debug;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     */
    public CloudLayerView(Context context) {
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
    public CloudLayerView(Context context, AttributeSet attrs) {
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
    public CloudLayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        layerName = "SuperMapCloud"+"-1";// 清除sd卡缓存需要，瓦片地址更换为t1，瓦片不一样，所以缓存文件名更改一下
        curMapUrl = "http://t.supermapcloud.com/FileService/SuperMapCloud";// sd卡缓存需要SuperMapCloud这个名称
        isGCSLayer = false;// 是3857
        this.layerBounds = new BoundingBox(new Point2D(-2.00375083427892E7, 2.00375083427892E7), new Point2D(2.00375083427892E7, -2.00375083427892E7));
        this.crs = new CoordinateReferenceSystem();
        this.crs.wkid = 3857;
        this.resolutions = defaultResolutions;
        isLayerInited = true;// 不用发送请求，因为初始化了layerBounds、resolutions就可以了
        // 如果没有设置固定分辨率，而只是设置固定比例尺，那么需要设置dpi，用于通过固定比例尺计算出相应的分辨率，是根据分辨率出图的，此处可有可无
        this.dpi = 0.0254 / 96.0;
        // this.projection = new Projection(this);
    }

    /**
     * <p>
     * 初始化tile瓦片。
     * </p>
     * @param tile 瓦片。
     * @return 瓦片的url。
     */
    @Override
    public void initTileContext(Tile tile) {
        // 获取真正的出图层级，因为mapview的层级数是所有图层固定比例尺数组合并的长度
        int index = getResolutionIndex();
        if (index == -1) {
            return;
        }
        // Log.i(LOG_TAG, "get layerView resolutionIndex:" + index);
        String result = cacheUrl + "?map=" + this.cloudMapName + "&type=" + this.type + "&x=" + tile.getX() + "&y=" + tile.getY() + "&z=" + index;
        if(debug){
            Log.d(LOG_TAG, "getTileURL:" + result);
        }
        tile.setUrl(result);
        // return result;
    }

    /**
     * <p>
     * 设置云图层瓦片的根路径地址，如"http://t1.supermapcloud.com/FileService/image"
     * </p>
     * @param url 根路径地址
     * @since 7.0.0
     */
    public void setBaseUrl(String url) {
        if (url == null || "".equals(url)) {
            return;
        }
        this.cacheUrl = url;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
}
