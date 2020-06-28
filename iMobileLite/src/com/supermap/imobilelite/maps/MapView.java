package com.supermap.imobilelite.maps;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomButtonsController;
import android.widget.ZoomControls;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.components.Map;
import com.supermap.services.components.MapContext;
import com.supermap.services.components.impl.MapImpl;
import com.supermap.services.providers.RestMapProvider;
import com.supermap.services.providers.RestMapProviderSetting;
import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 显示地图的视图。
 * </p>
 * <p>
 * 一个显示地图的视图，当被焦点选中时，它能捕获按键事件和触摸手势去完成地图的基本操作，如平移、缩放等。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 *
 */
public class MapView extends RelativeLayout{
    //地图比例尺单位是否为米
    private boolean isMeter=true;
    private static final String LOG_TAG = "MapView";
    private static ResourceManager resource = new ResourceManager("com.supermap.imobilelite.MapCommon");
    private static final int MAP_VIEW_BACKGROUND = Color.rgb(238, 238, 238);
    private static final int PRELOAD = 31459;
    private static final int[] SCALE = { 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000,
            20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000, 5000000 };
    private Context context;
    private MapController mapController;
    private boolean builtInZoomControls = true;
    // 是否启用滑动事件触发平移地图，绘制点线面时不启用--huangqh
    private boolean useScrollEvent = true;
    private boolean useDoubleTapEvent = true;
    private ZoomControls zoomControls;
    private ZoomButtonsController zoomButtonsController;
    // private boolean logo = false;
    private int zoomLevel = 0;
    // 保存上一次屏幕的图片，解决缩放过程存在清屏的现象
    Bitmap mLastScreen = null;
    // 标志是否是放大地图
    boolean zoomInChanged = false;
    /**
     * 屏幕中心点对应的地理坐标点
     */
    // GeoPoint centerGeoPoint = new GeoPoint(Util.to1E6(39.904491), Util.to1E6(116.391468));
    Point2D centerGeoPoint = null;
    // 做瓦片的android端缓存
    private TileCacher tileCacher;
    // private TrafficManager trafficManager;
    TouchEventHandler eventHandler;
    private DefaultTrackBallHandler trackBallHandler;
    private Paint defaultTilePaint = new Paint(1);
    private Paint customTilePaint = new Paint(1);
    private boolean firedMapLoaded = false;
    // 是否缩放比例尺
    boolean scaling = false;
    // 比例尺缩放多少
    float currentScale = 1.0F;
    // 记录每次多点触碰时比例尺真实缩放多少
    float realScale = 1.0F;
    // 比例尺缩放时对应屏幕的点
    Point scalePoint = new Point();

    private float rotateDegrees = 0.0F;
    private int height;
    private int width;
    private Projection projection;
    Queue<Animator> animators = new LinkedBlockingQueue<Animator>();
    private ReticleDrawMode reticalMode = ReticleDrawMode.DRAW_RETICLE_NEVER;
    private OverlayController overlayController;
    private NetworkConnectivityListener networkConnectivityListener;
    private Handler mapEventCallback = null;

    // private BitmapDrawable logoDrawable = null;
    private BitmapDrawable locationDrawable = null;
    // private Configuration configuration;
    private int topMargin = 0;
    private int bottomMargin = 0;
    private int rightMargin = 0;
    private int leftMargin = 0;

    private HashMap<MapViewEventListener, Handler> mapViewEventListeners = new HashMap<MapViewEventListener, Handler>();
    // 当前锁定屏幕的像素坐标点，默认是屏幕中心点
    Point focalPoint = new Point();
    private Rect rotRect = new Rect();
    private Rect visibleRect = new Rect();
    // zoomButtonsController控件上次点击时间
    private long zoomButtonsLastClickTime;
    // zoomButtonsController控件延时，防止用户过快点击缩放控件
    private static final long ZOOMBUTTONS_DELAY = 200;
    ArrayList<AbstractTileLayerView> layerViewList = new ArrayList<AbstractTileLayerView>();
    // 存储添加进来的所有尚未初始化的layerView，保证清除图层的接口能够生效，解决异步添加layerView惹的祸
    ArrayList<AbstractTileLayerView> unInitedLayerViewList = new ArrayList<AbstractTileLayerView>();
    private AbstractTileLayerView baseLayer = null;
    private int maxLevel = Constants.DEFAULT_RESOLUTION_SIZE - 1;
    private BoundingBox mapIndexBounds = null;// 主要是用于计算，实际上不一定是地图的范围
    private double[] resolutions;
    private double[] scales;// 实际出图的比例尺数组，与level一一对应
    // private double[] visibleScales = null;// 自定义比例尺数组，取所有Layer的并集，如果都没有设置的话就默认18级比例尺显示

    private TileDownloader tileProvider;
    // private ProjectionUtil projectionUtil;
    // private final static int DEFAULT_RESOLUTION_SIZE = 18;

    private CoordinateReferenceSystem crs;
    private boolean isGCS = false;// 是否地理坐标系
    private int defWidth = -1;
    // 是否发生多点触碰缩放
    boolean isMultiTouchScale;
    // 展示比例尺控件的布局
    private RelativeLayout scaleView;
    // 展示比例尺控件上的数值
    private TextView scaleTextView;
    // 展示比例尺控件上的那个图片
    private ImageView scaleImageView;
    // 比例尺控件在屏幕上的位置X
    private int scaleViewX;
    // 比例尺控件在屏幕上的位置Y
    private int scaleViewY;
    // 设置比例尺控件是否可见
    private boolean showScale;
    // 保存用户设置的viewBounds
    private BoundingBox viewbounds;
    private float density = 1.0f;
    EventDispatcher ed;
    public boolean isDetroy = false;
    // 判断是否发生缩放的动画
    boolean isZoomScale = false;
    // 缩放的动画过程的缩放因子
    private float zoomScale = 1.0f;
    /**
     * <p>
     * 是否开启固定比例尺可视化效果（即是否关闭任意比例尺缩放）
     * true代表开启固定比例尺可视化效果，false代表任意比例尺缩放效果，默认为false
     * </p>
     * @since 8.0.0
     */
    public boolean fixedLevelsEnabled = false;
    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context 一个 Activity 对象。
     */
    public MapView(Context context) {
        super(context);
        initialize(context);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context 一个 Activity 对象。
     * @param attrs 属性信息。
     */
    public MapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context 一个 Activity 对象。
     * @param attrs 属性信息。
     * @param defStyle 风格标识。
     */
    public MapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        /*if (!(context instanceof MapActivity)) {
            throw new IllegalArgumentException("MapView are restricted being created inside of a MapActivity class, you need to subclass MapAvtivity");
        }

        ((MapActivity) context).addMapView(this);*/
        if (context == null) {
            throw new IllegalArgumentException("context 为空。");
        }
        this.context = context;
        // this.configuration = new Configuration(this);
        this.mapController = new MapController(this);

        this.mapEventCallback = new MapEventHandler();
        getEventDispatcher().registerHandler(this.mapEventCallback);
        this.tileCacher = new TileCacher(context);
        this.tileProvider = new ThreadBasedTileDownloader(this, this.tileCacher);

        this.defaultTilePaint.setDither(true);
        this.defaultTilePaint.setFilterBitmap(true);
        this.customTilePaint.setDither(true);
        this.customTilePaint.setFilterBitmap(true);
        setFocusable(true);
        setBackgroundColor(MAP_VIEW_BACKGROUND);
        this.eventHandler = new TouchEventHandler(this);
        // this.tileFactory = new RestMapTileFactory(this);// 必须先创建，设置setBaseUrl和setMinScale时需要
        // this.projectionUtil = new ProjectionUtil(this);
        // this.rotatableProjection = new RotatableProjection(this,projectionUtil);

        setMapCenter(this.centerGeoPoint, this.zoomLevel);
        this.overlayController = new OverlayController(this);
        // this.logoDrawable = Util.getDrawable(context, "logo");// logo图片
        locationDrawable = Util.getDrawable(context, "location_marker_green");// location图片,点击定位所在位置

        this.networkConnectivityListener = new NetworkConnectivityListener(context,this);
        this.networkConnectivityListener.startListening();

        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        this.width = display.getWidth();
        this.defWidth = display.getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        density = dm.density;
        Log.i(LOG_TAG, "dm.density:" + dm.density);
        this.height = (display.getHeight() - (int) (25.0D * dm.density + 0.5D));
        // 初始化记录上一次屏幕的图片
        if (mLastScreen != null) {
            mLastScreen.recycle();
            mLastScreen = null;
        }
        mLastScreen = Bitmap.createBitmap(this.width, this.height, Config.ARGB_8888);
        // Log.i(LOG_TAG, "Bitmap.getDensity():" + mLastScreen.getDensity());
        // 初始化比例尺控件
        initScaleBar(context);
    }

    EventDispatcher getEventDispatcher() {
        if (ed == null) {
            ed = new EventDispatcher();
        }
        return ed;
    }

    TileDownloader getTileProvider() {
        return tileProvider;
    }

    /**
     * <p>
     * 获取 Map 对象，可以通过它对当前地图进行距离查询、SQL查询、地图量算等操作，这些操作均使用iServer的接口。
     * </p>
     * @return 当前地图对应的 Map 对象。
     */
    public Map getMap() {
        List<String> mapURLs = getCurMapRestServiceURL();
        List<com.supermap.services.components.spi.MapProvider> sps = new ArrayList<com.supermap.services.components.spi.MapProvider>();
        if (mapURLs.size() <= 0) {
            return null;
        }
        for (String mapURL : mapURLs) {
            RestMapProviderSetting restMapSetting = new RestMapProviderSetting();
            restMapSetting.useCache = false;
            restMapSetting.restServiceRootURL = mapURL;
            com.supermap.services.components.spi.MapProvider rmp = new RestMapProvider(restMapSetting);
            sps.add(rmp);
        }
        MapContext context = new MapContext();
        context.setProviders(sps);
        Map mapSC = new MapImpl(context);
        return mapSC;
    }

    /**
     * 获取当前地图所属rest地图服务的URL，供RESTMapProvider初始化使用
     * @param
     * @return
     */
    private List<String> getCurMapRestServiceURL() {
        List<String> mapURLs = new ArrayList<String>();
        AbstractTileLayerView[] layers = this.getLayers();
        String curMapUrl = "";
        if (layers != null && layers.length > 0) {
            for (AbstractTileLayerView layer : layers) {
                curMapUrl = layer.getURL();
                if (!curMapUrl.equals("")) {
                    int tmpIndex = curMapUrl.lastIndexOf("/");
                    String tmpStr = curMapUrl.substring(0, tmpIndex);
                    tmpIndex = tmpStr.lastIndexOf("/");
                    mapURLs.add(tmpStr.substring(0, tmpIndex));
                }
            }
        }
        return mapURLs;
    }

    /**
     * <p>
     * 返回地图坐标参考系。
     * </p>
     * @return
     */
    public CoordinateReferenceSystem getCRS() {
        return this.crs;
    }

    /**
     * <p>
     * 返回地图中心点坐标。
     * </p>
     * @return 地图中心点坐标。
     */
    public Point2D getCenter() {
        return this.centerGeoPoint == null ? null : new Point2D(this.centerGeoPoint);
    }

    /**
     * <p>
     * 返回地图当前视窗的分辨率。
     * </p>
     * @return 地图当前分辨率。
     */
    public double getResolution() {
        return getRealResolution() / this.currentScale;
    }

    /**
     * <p>
     * 返回地图当前真实的分辨率。
     * </p>
     * @return 地图当前分辨率。
     */
    public double getRealResolution() {
        if (resolutions != null && resolutions.length > 0) {
            if (zoomLevel < resolutions.length) {
                return this.resolutions[zoomLevel];
            } else {
                return this.resolutions[resolutions.length - 1];
            }
        } else {
            double firstResolution = getDefResolution();
            return zoomLevel <= maxLevel ? firstResolution / (Math.pow(2, zoomLevel)) : firstResolution / (Math.pow(2, maxLevel));
        }
    }

    /**
     * <p>
     * 返回全幅显示的分辨率
     * </p>
     * @return
     */
    public double getDefResolution() {
        if (getIndexBounds() != null && getIndexBounds().rightBottom != null && getIndexBounds().leftTop != null) {
            double geoWidth = getIndexBounds().rightBottom.getX() - getIndexBounds().leftTop.getX();
            if (isGCS || (baseLayer != null && baseLayer.isGCSLayer())) {
                // 考虑坐标系
                double radius = Constants.DEFAULT_AXIS;
                if (baseLayer.getCRS() != null) {
                    radius = baseLayer.getCRS().datumAxis > 1d ? baseLayer.getCRS().datumAxis : Constants.DEFAULT_AXIS;
                }
                geoWidth = geoWidth * Math.PI * radius / 180.0;
            }
            int width = this.defWidth != -1 ? this.defWidth : this.getMapWidth();
            return geoWidth / (width * 1.0);
        } else {
            return -1.0d;
        }
    }

    /**
     * <p>
     * 返回地图当前比例尺。
     * </p>
     * @return 地图当前比例尺。
     */
    public double getScale() {
        double resolution = getResolution();
        double dpi = -1;
        if (baseLayer != null && baseLayer.dpi != -1) {
            dpi = baseLayer.dpi;
        } else {
            for (int i = 0; i < layerViewList.size(); i++) {
                if (layerViewList.get(i).dpi != -1) {
                    dpi = layerViewList.get(i).dpi;
                    break;
                }
            }
        }
        if (resolution > 0 && dpi != -1) {
            return dpi / resolution;
        } else {
            return 0;
        }
    }

    BoundingBox getIndexBounds() {
        if (this.mapIndexBounds == null) {
            // Log.i(LOG_TAG, resource.getMessage(MapCommon.MAPVIEW_MAPINDEXBOUNDS_NULL));
            if (this.baseLayer != null && this.baseLayer.getBounds() != null) {
                BoundingBox tmpBoundingBox = baseLayer.getBounds();
                adjustMapBounds(tmpBoundingBox);
                return tmpBoundingBox;
            } else {// baseLayer为空或是没有初始化成功则无法初始化mapIndexBounds，返回宽高为0的BoundingBox
                return new BoundingBox();
            }
        }
        return this.mapIndexBounds;
    }

    /**
     * <p>
     * 返回地图地理范围。该属性等于 MapView 中加载的所有图层的地理范围的并集。
     * </p>
     * @return 地图地理范围。
     */
    public BoundingBox getBounds() {
        BoundingBox mapBounds = null;
        for (AbstractTileLayerView layerView : this.layerViewList) {
            mapBounds = BoundingBox.union(mapBounds, layerView.getBounds());
        }
        return mapBounds;
    }

    /**
     * <p>
     * 返回地图的分辨率数组。该属性与比例尺数组 scales 属性只需设置其一即可，系统内部会自动进行转换(map包含非动态图层就可能不等同)。
     * </p>
     * @return 地图的分辨率数组。
     */
    public double[] getResolutions() {
        return resolutions;
    }

    /**
     * <p>
     * 返回地图的比例尺数组。该属性与分辨率数组 resolutions 属性只需设置其一即可(map包含非动态图层就可能不等同)。
     * </p>
     * @return 地图的比例尺数组。
     */
    public double[] getScales() {
        return scales;
    }

    /**
     * 将屏幕坐标转化为对应的地理坐标。如果没有添加任何地图时则返回空。
     * @param point 屏幕坐标。
     * @return 返回屏幕坐标对应的地理坐标。
     */
    public Point2D toMapPoint(Point point) {
        if (point != null) {
            return this.toMapPoint(point.x, point.y);
        }
        return null;
    }

    /**
     * <p>
     * 将屏幕上的像素点坐标转化为对应的地理坐标。如果没有添加任何地图时则返回空。
     * </p>
     * @param screenX 当前点离屏幕左侧的坐标的长度。
     * @param screenY 当前点离屏幕顶部的坐标的长度。
     * @return 返回屏幕坐标对应的地理坐标。
     */
    public Point2D toMapPoint(int screenX, int screenY) {
        Projection projection = this.getProjection();
        if (projection != null) {
            return projection.fromPixels(screenX, screenY);
        }
        return null;
    }

    /**
     * <p>
     * 将一个地理点坐标转化为屏幕点坐标。
     * </p>
     * @param point2D 地理点坐标。
     * @return 屏幕点坐标。
     */
    public Point toScreenPoint(Point2D point2D) {
        Projection projection = this.getProjection();
        if (projection != null && point2D != null) {
            return projection.toPixels(point2D, null);
        }
        return null;
    }

    /**
     * <p>
     * 设置缓存大小，单位是张，指明最多缓存多少瓦片，每张约占256KB内存计算
     * </p>
     * @param size 缓存张数
     * @since 7.0.0
     */
    public void setCacheSize(int size) {
        if (this.tileCacher != null) {
            this.tileCacher.setCacheSize(size);
        }
    }

    /**
     * <p>
     * 获取屏幕范围所对应的地理范围（视图范围）
     * </p>
     * @return
     */
    public BoundingBox getViewBounds() {
        // Log.d(LOG_TAG, "getViewBounds:" + getLeft() + "," + getTop() + "," + getRight() + "," + getBottom());
        Point2D leftTop = this.toMapPoint(getLeft(), getTop());
        Point2D rightBottom = this.toMapPoint(getRight(), getBottom());
        return new BoundingBox(leftTop, rightBottom);
    }

    /**
     * <p>
     * 设置当前屏幕范围所展示的地理范围，其中必须left<right,bottom<top.
     * </p>
     * @param boundingBox 地理范围左上右下
     * @since 7.0.0
     */
    public void setViewBounds(BoundingBox boundingBox) {
        if (boundingBox == null || !boundingBox.isValid()) {
            Log.w(LOG_TAG, "The value of viewBounds isn't valid");
            return;
        }
        viewbounds = new BoundingBox(boundingBox);
        showByViewBounds();
    }

    private void showByViewBounds() {
        // 保证底图存在并初始化完成，setViewBounds才真正去生效，不然没有参考的地图范围
        if (baseLayer != null && baseLayer.isLayerInited && baseLayer.getBounds() != null && viewbounds != null) {
            // 1.修正适配可视范围
            adjustMapBounds(viewbounds);
            // Log.d(LOG_TAG, "setViewBounds中适配可视范围后viewbounds l:" + viewbounds.getLeft() + ",t" + viewbounds.getTop() + ",r" + viewbounds.getRight() + ",b"
            // + viewbounds.getBottom());
            // 2.可视范围的中心点设为出图的中心点
            this.centerGeoPoint = viewbounds.getCenter();
            // 3.计算可视范围当前的真实分辨率
            double geoWidth = viewbounds.getWidth();
            // Log.d(LOG_TAG, "viewbounds.getWidth:" + geoWidth);
            if (isGCS || (baseLayer != null && baseLayer.isGCSLayer())) {
                // 考虑坐标系
                double radius = Constants.DEFAULT_AXIS;
                if (baseLayer.getCRS() != null) {
                    radius = baseLayer.getCRS().datumAxis > 1d ? baseLayer.getCRS().datumAxis : Constants.DEFAULT_AXIS;
                }
                geoWidth = geoWidth * Math.PI * radius / 180.0;
                // Log.d(LOG_TAG, "geoWidth:" + geoWidth);
            }
            int width = this.defWidth != -1 ? this.defWidth : this.getMapWidth();
            // Log.d(LOG_TAG, "width:" + width + ",this.defWidth:" + this.defWidth + ",this.getMapWidth()" + this.getMapWidth());
            double curResolution = geoWidth / (width * 1.0) * getDensity();
            // Log.d(LOG_TAG, "curResolution:" + curResolution);
            // 4.根据真实分辨率找到接近的固定分辨率，即找到缩放的层级
            int endLevel = this.zoomLevel;
            double nearResolution = curResolution;
            // 设置了固定比例尺
            if (resolutions != null && resolutions.length > 0) {
                double minDetaR = Math.abs(resolutions[0] - curResolution);
                endLevel = 0;
                for (int i = 1; i < resolutions.length; i++) {
                    if (Math.abs(resolutions[i] - curResolution) < minDetaR) {
                        minDetaR = Math.abs(resolutions[i] - curResolution);
                        // 找到最靠近的分辨率的下标作为最终的缩放层级
                        endLevel = i;
                    }
                }
                nearResolution = resolutions[endLevel];
            } else {// 没有设置固定比例尺的情况
                double firstResolution = getDefResolution();
                // Log.d(LOG_TAG, "firstResolution:" + firstResolution);
                if (firstResolution != -1.0d) {
                    double scaleFactor = firstResolution / curResolution;
                    // Log.d(LOG_TAG, "scaleFactor:" + scaleFactor);
                    if (scaleFactor > 0) {
                        if (scaleFactor <= 1) {
                            nearResolution = firstResolution;
                            endLevel = 0;
                        } else {
                            int level = (int) Util.log2(scaleFactor);
                            // Log.d(LOG_TAG, "level:" + level + ",Util.log2(scaleFactor):" + Util.log2(scaleFactor));
                            double detaR1 = Math.abs(firstResolution / (Math.pow(2, level)) - curResolution);
                            double detaR2 = Math.abs(firstResolution / (Math.pow(2, level + 1)) - curResolution);
                            // Log.d(LOG_TAG, "detaR1:" + detaR1 + ",detaR2:" + detaR2);
                            if (detaR1 <= detaR2) {
                                nearResolution = firstResolution / (Math.pow(2, level));
                                endLevel = level;
                            } else {
                                nearResolution = firstResolution / (Math.pow(2, level + 1));
                                endLevel = level + 1;
                            }
                        }
                    }
                }
            }
            // Log.d(LOG_TAG, "nearResolution:" + nearResolution);
            // 5.计算接近的固定分辨率与真实分辨率的缩放比例因子
            double sacle = nearResolution / curResolution;
            Log.d(LOG_TAG, "setViewBounds后zoomlevle:" + endLevel + " currentScale:" + sacle);
            // 6.设置出图层级和缩放比例因子，后重新出图
            if (this.validateZoomLevel(endLevel) && sacle > 0) {// 保证endLevel合法，才设置缩放比例因子，因为因子是跟层级相关的
                this.currentScale = (float) sacle;// this.setScale((float)sacle, (float)sacle, this.scalePoint.x, this.scalePoint.y);
                if (endLevel == maxLevel && currentScale > 2.0f) {// 解决viewbounds过小超出了最大缩放级别很多的时候，不再支持无限放大
                    this.currentScale = 1.5f;
                }
                if (this.currentScale != 1.0f) {
                    zoomStart();
                }
            }
            setZoomLevel(endLevel);
            viewbounds = null;// 设置为null，保证其值生效一次即可
        }
    }

    void queueAnimator(Animator animator) {
        this.animators.add(animator);
        postInvalidate();
//        invalidate();
    }

    // void setLogoShown(boolean show) {
    // this.logo = show;
    // }
    /**
     * <p>
     * 重新调整地图对象的尺寸。如果覆盖该方法，一定要通过调用super.onSizeChanged()。
     * </p>
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if ((w <= 0) && (h <= 0))
            return;
        if (this.rotateDegrees != 0.0F) {
            setMapRotation(this.rotateDegrees);
        } else {
            this.width = getWidth();
            this.height = getHeight();
        }
        // 初始化记录上一次屏幕的图片
        if (mLastScreen != null) {
            mLastScreen.recycle();
            mLastScreen = null;
        }
        mLastScreen = Bitmap.createBitmap(this.width, this.height, Config.ARGB_8888);

        this.focalPoint.set(getWidth() >> 1, getHeight() >> 1);
        this.scalePoint.x = this.focalPoint.x;
        this.scalePoint.y = this.focalPoint.y;

        if (getTileCacher() != null)
            getTileCacher().checkCacheSize(this.height, this.width);

        if (!this.firedMapLoaded) {
            this.firedMapLoaded = true;
            getEventDispatcher().sendEmptyMessage(1);
        }
        getEventDispatcher().sendEmptyMessage(5);
        preLoad();
    }

    private ZoomControls createZoomControls() {
        if (this.zoomControls == null) {
            this.zoomControls = new ZoomControls(this.context);
            this.zoomControls.setZoomSpeed(20L);
            this.zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MapView.this.mapController.zoomIn();
                }
            });
            this.zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MapView.this.mapController.zoomOut();
                }
            });
        }
        if (this.builtInZoomControls) {
            this.zoomControls.setVisibility(INVISIBLE);
        }
        return this.zoomControls;
    }

    private ZoomButtonsController createZoomButtonsController() {
        if (this.zoomButtonsController == null) {
            this.zoomButtonsController = new ZoomButtonsController(this);
            this.zoomButtonsController.setZoomSpeed(200L);//2000
            this.zoomButtonsController.setOnZoomListener(new ZoomButtonsController.OnZoomListener() {
                public void onZoom(boolean zoomIn) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - zoomButtonsLastClickTime < ZOOMBUTTONS_DELAY) {
                        return;
                    }
                    zoomButtonsLastClickTime = currentTime;
                    if (zoomIn) {
                        MapView.this.mapController.zoomIn();
                    } else {
                        MapView.this.mapController.zoomOut();
                    }
                }
                public void onVisibilityChanged(boolean visible) {
                }
            });
        }
        return this.zoomButtonsController;
    }

    void setMapCenter(Point2D geoPoint, int zoomLevel) {
        synchronized (this) {
            this.zoomLevel = zoomLevel;
            this.centerGeoPoint = geoPoint;
        }
//        this.currentScale = 1.0f;// 直接放大或缩小时，置缩放比为1
        updateZoomControls();

        if (getWidth() == 0)
            return;
        postInvalidate();
    }

    private void updateZoomControls() {
        boolean zoomInEnabled = this.zoomLevel < maxLevel;
        boolean zoomOutEnabled = this.zoomLevel > 0;

        if (this.zoomControls != null) {
            this.zoomControls.setIsZoomInEnabled(zoomInEnabled);
            this.zoomControls.setIsZoomOutEnabled(zoomOutEnabled);// 可能有问题setIsZoomOutEnabled
        }
        if ((this.builtInZoomControls) && (this.zoomButtonsController != null)) {
            ZoomControls zc = (ZoomControls) this.zoomButtonsController.getZoomControls();
            zc.setIsZoomInEnabled(zoomInEnabled);
            zc.setIsZoomOutEnabled(zoomOutEnabled);
            // 把缩放按钮置于右下角
            FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams) zc.getLayoutParams();
            fl.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            // fl.width =-2;
            // fl.height = -2;
            zc.setLayoutParams(fl);
            // zc.setGravity(Gravity.RIGHT|Gravity.BOTTOM);
        }
    }

    Point getFocalPoint() {
        return new Point(this.focalPoint);
    }

    // public void setFocalPoint(Point point) {
    // this.focalPoint.set(point.x, point.y);
    // this.rotatableProjection.setRotate(this.rotateDegrees, point.x, point.y);
    // moved();
    // EventDispatcher.sendEmptyMessage(23);
    // postInvalidate();
    // }

    void addTile(Tile tile) {
        // if (this.tileFactory == null) {
        // return;
        // }
        if (tile.getZoomLevel() != this.getZoomLevel()) {
            return;
        }
        // if (this.tileFactory.getTileType() == TileType.MAP) {
        // if (tile.getTileType() != TileType.MAP)
        // return;
        // } else if (tile.getTileType() == TileType.MAP) {
        // return;
        // }

        if ((tile.getBitmap() == null) || (tile.getBitmap().isRecycled())) {
            this.tileCacher.getCache(TileCacher.CacheType.MEMORY).removeTile(tile);
            return;
        }
        postInvalidate();
    }

    void preLoadDelayed(long delayMillis) {
        this.mapEventCallback.sendEmptyMessageDelayed(PRELOAD, delayMillis);
    }

    private void preLoad() {// 可能用来调用layerView的设置及绘图
        if ((getWidth() == 0) || (getHeight() == 0))
            return;
        if (getZoomLevel() < 0) {
            return;
        }
        // 如果baseLayer未初始化完成，其他图层初始化了也不预处理下载图片，因为需要baseLayer的bounds
        if (baseLayer == null || !baseLayer.isInitialized()) {
            return;
        }
        if (this.tileProvider == null) {
            this.tileProvider = new ThreadBasedTileDownloader(this, getTileCacher());
        }
        for (AbstractTileLayerView layerView : layerViewList) {
            layerView.preLoad();
        }
    }

    boolean validateZoomLevel(int zoom) {
        return (zoom <= maxLevel) && (zoom >= 0);
    }

    /**
     * <p>
     * 地图放大。
     * </p>
     */
    public void zoomIn() {
        // 如果已缩放到最大级别且存在缩放比，此时再放大不做处理保持现状
        if (this.currentScale != 1.0f && zoomLevel == maxLevel) {
            return;
        }
        this.currentScale = 1.0f;// 直接放大或缩小时，置缩放比为1
        setZoomLevel(this.zoomLevel + 1);
        getEventDispatcher().sendEmptyMessage(12);// 通知监听器MapViewEventListener的zoomEnd接口生效
    }

    /**
     * <p>
     * 地图缩小。
     * </p>
     */
    public void zoomOut() {
        // 如果已缩放到最小级别且存在缩放比，此时再缩小不做处理保持现状
        if (this.currentScale != 1.0f && zoomLevel == 0) {
            return;
        }
        this.currentScale = 1.0f;// 直接放大或缩小时，置缩放比为1
        setZoomLevel(this.zoomLevel - 1);
        getEventDispatcher().sendEmptyMessage(12);// 通知监听器MapViewEventListener的zoomEnd接口生效
    }

    void setZoomLevel(int mapZoomLevel) {
        if (mapZoomLevel > maxLevel) {
            mapZoomLevel = maxLevel;
        }
        if (mapZoomLevel < 0) {
            mapZoomLevel = 0;
        }
        setMapCenter(this.centerGeoPoint, mapZoomLevel);
    }

    void zoomToSpan(double latE6, double lngE6) {
        double midlatE6 = latE6 / 2;
        double midlngE6 = lngE6 / 2;
        double lngE6Left = this.centerGeoPoint.getX() - midlngE6;
        double lngE6Right = this.centerGeoPoint.getX() + midlngE6;
        BoundingBox bbox = new BoundingBox(new Point2D(lngE6Left, this.centerGeoPoint.getY() + midlatE6), new Point2D(lngE6Right, this.centerGeoPoint.getY()
                - midlatE6));

        zoomToSpan(bbox, false);
    }

    void zoomToSpan(BoundingBox bbox, boolean shouldCenter) {
        // TODO 待确认zoomLevel的值
        if (this.zoomLevel < 6)
            this.zoomLevel = 6;

        Rect rect = visibleRegion();

        int zoom = getProjection().calculateZoomLevel(bbox, getWidth() - rect.width(), getHeight() - rect.height());

        if (validateZoomLevel(zoom)) {
            getEventDispatcher().sendEmptyMessage(11);
            if (shouldCenter) {
                getEventDispatcher().sendEmptyMessage(21);
                this.centerGeoPoint = bbox.getCenter();
                getEventDispatcher().sendEmptyMessage(23);
            }
            this.zoomLevel = zoom;
            if (shouldCenter) {
                this.centerGeoPoint = bbox.getCenter();

                int centerX = this.focalPoint.x + (this.focalPoint.x - rect.centerX());
                int centerY = this.focalPoint.y + (this.focalPoint.y - rect.centerY());
                getEventDispatcher().sendEmptyMessage(21);
                this.centerGeoPoint = getProjection().fromPixels(centerX, centerY);
                getEventDispatcher().sendEmptyMessage(23);
            }
            getEventDispatcher().sendEmptyMessage(12);
        } else {
            Log.w(LOG_TAG, resource.getMessage(MapCommon.MAPVIEW_ZOOM_INVALID, String.valueOf(zoom)));
            if (shouldCenter) {
                this.centerGeoPoint = bbox.getCenter();

                int centerX = this.focalPoint.x + (this.focalPoint.x - rect.centerX());
                int centerY = this.focalPoint.y + (this.focalPoint.y - rect.centerY());
                getEventDispatcher().sendEmptyMessage(21);
                this.centerGeoPoint = getProjection().fromPixels(centerX, centerY);
                getEventDispatcher().sendEmptyMessage(23);
            }
        }
    }

    /**
     * 通过 Overlay 对象的 key 获取该 Overlay 对象。
     * @param key Overlay 对象的 key。
     * @return key 对应的 Overlay 对象。
     */
    public Overlay getOverlayByKey(String key) {
        if (getOverlays() == null)
            return null;

        for (Overlay o : getOverlays()) {
            if (o.getKey().equals(key))
                return o;
        }
        return null;
    }

    /**
     * 通过Overlay名称删除该Overlay对象
     * @param key
     */
    public void removeOverlayByKey(String key) {
        Overlay o = getOverlayByKey(key);
        if (o != null)
            getOverlays().remove(o);
        postInvalidate();
    }

    // MapProvider getMapProvider() {// 暂时只有TrafficManager调用，但是TrafficManager可能不需要了
    // if (this.getBaseLayer() != null) {
    // return this.baseLayer.getMapProvider();
    // }
    // return null;
    // }

    /**
     * <p>
     * 检查布局参数。
     * </p>
     * @param p 布局参数。
     * @return true为合法，false为不合法。
     */
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * <p>
     * 设置是否显示缩放的控件按钮。
     * </p>
     * @param takeFocus 是否显示缩放的控件按钮。
     */
    public void displayZoomControls(boolean takeFocus) {
        if (this.builtInZoomControls) {
            this.zoomButtonsController = createZoomButtonsController();
            if (takeFocus) {
                this.zoomButtonsController.setFocusable(true);
                this.zoomButtonsController.setVisible(true);
            }
        } else if ((this.zoomControls != null) && (takeFocus)) {
            this.zoomControls.setFocusable(true);
            this.zoomControls.show();
            this.zoomControls.requestFocus();
        }
    }

    /**
     * <p>
     * 获取一个Layout的默认参数的集合。
     * </p>
     * @return Layout的默认参数的集合。
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2, new Point2D(0.0D, 0.0D), 3);
    }

    /**
     * <p>
     * 获取一个Layout参数的集合，其中参数带有ViewGroup.LayoutParams.WRAP_CONTENT的宽度， ViewGroup.LayoutParams.WRAP_CONTENT的高度和坐标(0,0).
     * </p>
     * @param attrs
     * @return Layout参数的集合。
     */
    @Override
    public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /**
     * <p>
     * 获取一个Layout参数的集合。
     * </p>
     * @param p Layout参数的集合。
     * @return Layout参数的集合。
     */
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /**
     * <p>
     * 返回地图的 MapController对象，可用于控制和驱动平移和缩放。
     * </p>
     * @return 地图的 MapController对象。
     */
    public MapController getController() {
        return this.mapController;
    }

    /**
     * <p>
     * 返回 Overlay列表。
     * </p>
     * @return Overlay列表。
     */
    public List<Overlay> getOverlays() {
        return this.overlayController.getOverlays();
    }

    /**
     * <p>
     * 返回地图的最大缩放级别。
     * </p>
     * @return 地图的最大缩放级别。
     */
    public int getMaxZoomLevel() {
        return maxLevel;
    }

    /**
     * 设置地图的最大缩放级别。
     * @return
     */
    void setMaxZoomLevel(int maxZoomLevel) {
        if (maxZoomLevel < Constants.DEFAULT_RESOLUTION_SIZE) {
            this.maxLevel = maxZoomLevel;
        }
    }

    /**
     * <p>
     * 返回地图视图对应的投影工具类对象。
     * </p>
     * @return 地图视图对应的投影工具类对象。
     */
    public Projection getProjection() {
        if (projection == null) {
            projection = this.getBaseLayer() != null ? this.getBaseLayer().getProjection() : null;
            // if (projection instanceof RotatableProjection) {
            // rotatableProjection = (RotatableProjection) projection;
            // }
        }
        return projection;
    }

    /**
     * <p>
     * 返回当前的缩放级别
     * </p>
     * @return
     */
    public int getZoomLevel() {
        return this.zoomLevel;
    }

    View getZoomControls() {
        if (this.zoomControls == null) {
            this.zoomControls = createZoomControls();
        }
        if (this.builtInZoomControls) {
            this.zoomControls.setVisibility(INVISIBLE);
        }
        return this.zoomControls;
    }

    /**
     * <p>
     * 将地图加载到当前屏幕。
     * </p>
     */
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if ((this.zoomButtonsController != null) && (this.builtInZoomControls))
            this.zoomButtonsController.setVisible(true);
    }

    /**
     * <p>
     * 当视图从窗口分离时调用，用于清除缩放控件。
     * </p>
     */
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.zoomButtonsController != null)
            this.zoomButtonsController.setVisible(false);
    }

    float getMapRotation() {
        return this.rotateDegrees;
    }

    void setMapRotation(float rotateDegrees) {
        if (Math.abs(this.rotateDegrees - rotateDegrees) < 0.00001) {
            return;
        }
        rotateDegrees = (rotateDegrees + 360.0F) % 360.0F;
        this.rotateDegrees = rotateDegrees;
        this.projection.setRotate(rotateDegrees, this.focalPoint.x, this.focalPoint.y);
        this.rotRect.set(0, 0, getWidth(), getHeight());
        this.projection.rotateMapRect(this.rotRect);
        this.width = this.rotRect.width();
        this.height = this.rotRect.height();
        moved();
        postInvalidate();
    }

    int getMapWidth() {
        return this.width == 0 ? getWidth() : this.width;
    }

    int getMapHeight() {
        return this.height == 0 ? getHeight() : this.height;
    }

    /**
     * 设置绘制瓦片的画笔
     * @param paint
     */
    void setTileLayerPaint(Paint paint) {// 可以后续考虑开不开出来
        if (paint == null)
            paint = new Paint();

        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        this.customTilePaint = paint;
    }

    // 待重写，还可能去掉
    // protected final void onDraw(Canvas canvas) {
    // super.onDraw(canvas);
    // }

    /**
     * <p>
     * 绘制动画、旋转，再绘制所有图层的瓦片，最后绘制Overlays和logo
     * </p>
     * @param canvas
     * @since 7.0.0
     */
    private void drawMapView(Canvas canvas) {
        if (getProjection() == null)
            return;
        if ((getHeight() == 0) || (getWidth() == 0)) {
            return;
        }
        if (canvas == null) {
            return;
        }
        try {
            Rect bounds = canvas.getClipBounds();

            if ((bounds.width() <= 10) || (bounds.height() <= 10)) {
                return;
            }
            // 绘制动画
            if (this.animators.size() > 0) {
                if ((bounds.width() == getWidth()) && (bounds.height() == getHeight())) {
                    Animator animator = (Animator) this.animators.peek();
                    if (!animator.animate()) {// 地图移动时调用animate()
                        this.animators.poll();
                    }
                    if (this.animators.size() > 0) {
                        invalidate();
                    }
                } else {
                    invalidate();
                }
            }
            canvas.save();
            // 裁剪旋转
            if (this.rotateDegrees != 0.0F) {
                boolean clipScreen = false;

                if ((bounds.width() != getWidth()) || (bounds.height() != getHeight())) {
                    clipScreen = true;
                    canvas.save(2);
                    canvas.clipRect(0.0F, 0.0F, getWidth(), getHeight(), Op.REPLACE);
                }

                canvas.rotate(this.rotateDegrees, this.focalPoint.x, this.focalPoint.y);

                canvas.getClipBounds(bounds);

                if (clipScreen) {
                    canvas.restore();
                }
            }

            // 绘制所有图层的瓦片,把最后的瓦片出图结果绘制在canvasParam中去
            canvas.drawBitmap(mLastScreen, 0, 0, null);
            // if ((!this.scaling) || (this.currentScale == 1.0F)) {
            // if (this.currentScale != 1.0F) {
            // preLoad();
            // }
            // }
            canvas.restore();
            if (this.reticalMode == ReticleDrawMode.DRAW_RETICLE_UNDER) {
                Reticle.draw(canvas, this, this.focalPoint);
            }
            // 绘制Overlays
            renderOverlays(canvas);

            if (this.reticalMode == ReticleDrawMode.DRAW_RETICLE_OVER) {
                Reticle.draw(canvas, this, this.focalPoint);
            }

            // if (this.logo)
            // this.logoDrawable.draw(canvas);
        } catch (Exception ex) {
            Log.d(LOG_TAG, resource.getMessage(MapCommon.MAPVIEW_EXCEPTION, ex.getMessage()));
        }
    }

    /**
     * 分发给子组件进行绘制。
     * @param canvasParam 画布
     */
    @Override
    protected void dispatchDraw(Canvas canvasParam) {
        super.dispatchDraw(canvasParam);

        // 在baseLayer初始化完成的前提下，其他图层初始化了才开始绘制，虽然其他图层没有初始化成功也不会绘制，但是有些判断是否绘制的接口需要用到baseLayer初始化完成的参数，不然就会崩溃
        if (this.layerViewList.size() > 0 && baseLayer != null && baseLayer.isInitialized()) {
            // 在canvasParam中绘制瓦片前，先新建一个以上一次的屏幕图片为底图的Canvas，在往上面绘制最新瓦片，最后把底图和瓦片一起保持于mLastScreen，用于下一次绘制
            Canvas canvas = new Canvas(mLastScreen);
            Bitmap bm = null;
            boolean isSaved = false;
            // 如果发生多点触碰缩放，那么先缩放上一次的背景图片，后再调用dispatchDraw来通知layer绘制最新图片，经典的想法实现--huangqh
            if (isMultiTouchScale && realScale != 1.0) {
                bm = Bitmap.createBitmap(mLastScreen);
                canvas.drawColor(Color.WHITE);
                canvas.save();
                // Point out = this.getProjection().mapPoint(this.mapView.scalePoint.x, this.mapView.scalePoint.y, null);
                // this.getProjection().offsetToFocalPoint(out.x, out.y, out);
                canvas.scale(realScale, realScale, scalePoint.x, scalePoint.y);
                if (bm != null) {
                    canvas.drawBitmap(bm, 0, 0, defaultTilePaint);
                }
                canvas.restore();
            } else if (isZoomScale && zoomScale != 1.0f) {
                // 缩放动画过程使用画板缩放后绘图替换（以前使用真实的缩放瓦片即修改currentScale值）
                // 解决快速点击屏幕触发很多次双击放大而产生多个放大的动画zoomAnimate对象，导致中心点计算错误出白图
                if (zoomScale < 1.0f) {// 为了去掉缩小动画过程看到一层一层缩小图叠加的效果，为了美观
                    canvas.drawColor(Color.WHITE);
                }
                canvas.save();
                isSaved = true;
                canvas.scale(zoomScale, zoomScale, scalePoint.x, scalePoint.y);
            } else {
                if (!zoomInChanged) {
                    canvas.drawColor(Color.WHITE);
                } else {
                    canvas.drawBitmap(mLastScreen, 0, 0, null);
                }
            }
            // 临时Bitmap对象及时回收，比较有必要
            if ((bm != null) && (!bm.isRecycled())) {
                bm.recycle();
            }

            super.dispatchDraw(canvas);
            if (isZoomScale && zoomScale != 1.0f) {
                // 缩放动画过程使用画板缩放后绘图替换（以前使用真实的缩放瓦片即修改currentScale值）
                // 解决快速点击屏幕触发很多次双击放大而产生多个放大的动画zoomAnimate对象，导致中心点计算错误出白图
                if(isSaved){
                    canvas.restore();// restore必须和save成对出现
                    isSaved = false;
                }
                isZoomScale = false;
            }
            // 所有子图层绘制瓦片到canvas后，把结果绘制到mapview的绘图容器canvasParam中去
            drawMapView(canvasParam);
            // // 把最后的瓦片出图结果绘制在canvasParam中去
            // canvasParam.drawBitmap(mLastScreen, 0, 0, null);
            // drawMapView(canvasParam);// 可否放到前面进行绘制，单独把log拿出来
        }
    }

    private void renderOverlays(Canvas canvas) {
//        try {
        // 去掉拉伸和压缩时overlayer也拉伸和压缩
        // if ((this.currentScale != 1.0F) && (this.scaling)) {
        // canvas.save(1);
        // canvas.scale(this.currentScale, this.currentScale, this.scalePoint.x, this.scalePoint.y);
        // }

        // if (this.trafficManager != null) {
        // this.trafficManager.draw(canvas, this);
        // }
        this.overlayController.renderOverlays(canvas, this);

        // if ((this.currentScale != 1.0F) && (this.scaling))
        // canvas.restore();
//        } finally {
//            // if ((this.currentScale != 1.0F) && (this.scaling))
//            // canvas.restore();
//        }
    }

    private Point scalePoint(int x, int y, Point point) {
        if (this.scaling) {
            Point scalePoint = this.scalePoint;
            point.y = (int) (scalePoint.y + (y - scalePoint.y) * this.currentScale + 0.5F);
            point.x = (int) (scalePoint.x + (x - scalePoint.x) * this.currentScale + 0.5F);
        } else {
            point.x = x;
            point.y = y;
        }
        return point;
    }

    private void redoLayout(boolean changed, int l, int t, int r, int b) {
        int c = getChildCount();
        Point point = new Point();
        int height = getHeight();
        int width = getWidth();
        for (int i = 0; i < c; i++) {
            View view = getChildAt(i);
            // 过滤比例尺控件的重新布局，它单独布局，不在这里处理
            if (view instanceof RelativeLayout && view.equals(scaleView) ) {
                continue;
            }
            if (view instanceof AbstractTileLayerView) {
                // 子视图是AbstractLayerView,直接布局
                View viewParent=(View) view.getParent();

                int paddingLeft=viewParent.getPaddingLeft();
                int paddingRight=viewParent.getPaddingRight();
                int paddingTop=viewParent.getPaddingTop();
                int paddingBottom=viewParent.getPaddingBottom();

                int leftMargin=0;
                int rightMargin=0;
                int topMargin=0;
                int bottomMargin=0;
                if(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
                    ViewGroup.MarginLayoutParams params=(MarginLayoutParams) view.getLayoutParams();
                    leftMargin=params.leftMargin;
                    rightMargin=params.rightMargin;
                    topMargin=params.topMargin;
                    bottomMargin=params.bottomMargin;
                }

                view.layout(paddingLeft+leftMargin, paddingTop+topMargin, r-paddingRight-rightMargin, b-paddingBottom-bottomMargin);
            } else if (view.getVisibility() == View.VISIBLE && view instanceof RMGLCanvas){
                // GL图层
//                View viewParent=(View) view.getParent();
//
//                int paddingLeft=viewParent.getPaddingLeft();
//                int paddingRight=viewParent.getPaddingRight();
//                int paddingTop=viewParent.getPaddingTop();
//                int paddingBottom=viewParent.getPaddingBottom();
//
//                int leftMargin=0;
//                int rightMargin=0;
//                int topMargin=0;
//                int bottomMargin=0;
//                if(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
//                    ViewGroup.MarginLayoutParams params=(MarginLayoutParams) view.getLayoutParams();
//                    leftMargin=params.leftMargin;
//                    rightMargin=params.rightMargin;
//                    topMargin=params.topMargin;
//                    bottomMargin=params.bottomMargin;
//                }
//
//                view.layout(paddingLeft+leftMargin, paddingTop+topMargin, r-paddingRight-rightMargin, b-paddingBottom-bottomMargin);
                view.layout(0,0,r-l,b-t);
            } else {
                // 子视图不是AbstractLayerView的布局跟原本一样
                if ((view.getVisibility() == GONE) || (!(view.getLayoutParams() instanceof LayoutParams)))
                    continue;
                LayoutParams lp = (LayoutParams) view.getLayoutParams();

                if (lp.mode == 0) {
                    if (lp.point == null) {
                        // Log.e(LOG_TAG, resource.getMessage(MapCommon.MAPVIEW_MODE_POINT));
                        point.x = lp.x;
                        point.y = lp.y;
                    } else {
                        point = getProjection().toPixels(lp.point, point);
                        // if (this.currentScale != 1.0F) {
                        // point = scalePoint(point.x, point.y, point);
                        // }
                        point.x += (lp.x != 2147483647 ? lp.x : 0);
                        point.y += (lp.y != 2147483647 ? lp.y : 0);
                    }
                } else {
                    point.x = lp.x;
                    point.y = lp.y;
                }
                int alignment = lp.alignment;
                int cw = view.getMeasuredWidth();
                int childHeight = view.getMeasuredHeight();
                int childLeft = point.x != 2147483647 ? point.x : width >> 1;
                int childTop = point.y != 2147483647 ? point.y : height >> 1;
                int childRight = childLeft + cw;
                int childBottom = childTop + childHeight;
                int x_padding = getPaddingLeft() - getPaddingRight();
                int y_padding = getPaddingTop() - getPaddingRight();

                int count = 0;
                while ((alignment != 0) && (count++ < 3)) {
                    if ((alignment & 0x20) == 32) {
                        childBottom = point.y != 2147483647 ? childTop : height;
                        childTop = childBottom - childHeight;
                        alignment ^= 32;
                        continue;
                    }
                    if ((alignment & 0x10) == 16) {
                        childTop = point.y != 2147483647 ? childTop : 0;
                        childBottom = childTop + childHeight;
                        alignment ^= 16;
                        continue;
                    }
                    if ((alignment & 0x8) == 8) {
                        childRight = point.x != 2147483647 ? point.x : width;
                        childLeft = childRight - cw;
                        alignment ^= 8;
                        continue;
                    }
                    if ((alignment & 0x4) == 4) {
                        childLeft = point.x != 2147483647 ? point.x : 0;
                        childBottom = childLeft + cw;
                        alignment ^= 4;
                        continue;
                    }

                    if ((alignment & 0x1) == 1) {
                        childLeft -= (cw >> 1);
                        childRight = childLeft + cw;
                        alignment ^= 1;
                        continue;
                    }
                    if ((alignment & 0x2) == 2) {
                        childTop -= (childHeight >> 1);
                        childBottom = childTop + childHeight;
                        alignment ^= 2;
                    }
                }

                view.layout(childLeft + x_padding, childTop + y_padding, childRight + x_padding, childBottom + y_padding);
            }
        }
    }

    /**
     * 分配所有的子元素的大小和位置。
     */
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        redoLayout(changed, l, t, r, b);
        //布局比例尺控件
        onLayoutScaleBar(changed, l, t, r, b);

    }

    /**
     * 确定所有子元素的大小。
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int mw = resolveSize(getMeasuredWidth(), widthMeasureSpec);
        int mh = resolveSize(getMeasuredHeight(), heightMeasureSpec);

        setMeasuredDimension(mw, mh);

        // if (this.logoDrawable != null) {
        // int h = this.logoDrawable.getIntrinsicHeight();
        // int w = this.logoDrawable.getIntrinsicWidth();
        // int padding = 5;
        // int b = mh - padding;
        // int t = b - h;
        // int l = padding;
        // int r = l + w;
        // this.logoDrawable.setBounds(l, t, r, b);
        // }
        if (this.locationDrawable != null) {
            int h = this.locationDrawable.getIntrinsicHeight();
            int w = this.locationDrawable.getIntrinsicWidth();
            int padding = 5;
            int b = mh - padding;
            int t = b - h;
            int l = padding;
            int r = l + w;
            this.locationDrawable.setBounds(l, t, r, b);
        }
    }

    /**
     * 改变地图的焦点
     */
    public void onFocusChanged(boolean hasFocus, int direction, Rect unused) {
        super.onFocusChanged(hasFocus, direction, unused);
    }

    /**
     * 相应按键按下事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.overlayController.onKeyDown(keyCode, event, this))
            return true;
        if (keyCode == 19) {
            getController().scrollBy(0, getHeight() / 4);
            return true;
        }
        if (keyCode == 20) {
            getController().scrollBy(0, -(getHeight() / 4));
            return true;
        }
        if (keyCode == 22) {
            getController().scrollBy(getWidth() / 4, 0);
            return true;
        }
        if (keyCode == 21) {
            getController().scrollBy(-(getWidth() / 4), 0);
            return true;
        }

        return false;
    }

    /**
     * 相应按键抬起事件
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.overlayController.onKeyUp(keyCode, event, this);
    }

    // public void onRestoreInstanceState(Bundle state) {
    //
    // if ((state.containsKey("STATE_CENTER_LAT")) && (state.containsKey("STATE_CENTER_LNG"))) {
    // int latE6 = state.getInt("STATE_CENTER_LAT");
    // int longE6 = state.getInt("STATE_CENTER_LNG");
    // this.centerGeoPoint = new GeoPoint(latE6, longE6);
    // }
    //
    // if (state.containsKey("STATE_ZOOM_LEVEL")) {
    // int zoom = state.getInt("STATE_ZOOM_LEVEL");
    // if (validateZoomLevel(zoom))
    // this.zoomLevel = zoom;
    // }
    // }
    //
    // public void onSaveInstanceState(Bundle state) {
    // if (this.centerGeoPoint != null) {
    // state.putInt("STATE_CENTER_LAT", this.centerGeoPoint.getLatitudeE6());
    // state.putInt("STATE_CENTER_LNG", this.centerGeoPoint.getLongitudeE6());
    // }
    // state.putInt("STATE_ZOOM_LEVEL", this.zoomLevel);
    // }

    boolean handleOverlayEvent(MotionEvent event) {
        return this.overlayController.onTouchEvent(event, this);
    }

    boolean onTap(Point2D gp) {
        return this.overlayController.onTap(gp, this);
    }

    /**
     * <p>
     * 响应所有触屏事件。
     * </p>
     * @param event 事件。
     * @return true表示响应，false表示不响应。
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) this.context.getSystemService("input_method");

        imm.hideSoftInputFromWindow(getWindowToken(), 0);

        if ((isClickable()) && (isEnabled())) {
            if ((this.builtInZoomControls) && (this.zoomButtonsController != null)) {
                if (!this.zoomButtonsController.isVisible())
                    this.zoomButtonsController.setVisible(true);
            } else if (this.zoomControls != null) {
                this.zoomControls.show();
            }
            requestFocus();
            if (this.getProjection() == null) {
                return false;
            }
            if (handleOverlayEvent(event)) {
                return true;
            }
            if ((this.eventHandler != null) && (this.eventHandler.handleTouchEvent(event))) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * 响应跟踪球事件。
     * </p>
     * @param event 事件。
     * @return true表示响应，false表示不响应。
     */
    public boolean onTrackballEvent(MotionEvent event) {
        if (this.overlayController.onTrackballEvent(event, this)) {
            return true;
        }
        if (this.trackBallHandler == null)
            this.trackBallHandler = new DefaultTrackBallHandler(this);
        return this.trackBallHandler.handleTrackballEvent(event);
    }

    /**
     * <p>
     * 设置是否启用内置的缩放控件。
     * </p>
     * @param on 是否启用内置的缩放控件。
     */
    public void setBuiltInZoomControls(boolean on) {
        this.builtInZoomControls = on;
        if (on) {
            this.zoomButtonsController = createZoomButtonsController();
            this.zoomButtonsController.setAutoDismissed(true);
            if (this.zoomControls != null) {
                this.zoomControls.setVisibility(INVISIBLE);
            }
            updateZoomControls();
        } else {
            if (this.zoomButtonsController != null) {
                this.zoomButtonsController.setVisible(false);
            }
            if (this.zoomControls != null) {
                this.zoomControls.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * <p>
     * 设置是否开启响应平移事件，绘制点线面的时候需要关闭平移事件才能绘制成功。
     * </p>
     * @param on 是否开启响应平移事件。
     */
    public void setUseScrollEvent(boolean on) {
        this.useScrollEvent = on;
    }

    /**
     * <p>
     * 获取是否开启响应平移事件，绘制点线面的时候可以灵活使用
     * </p>
     * @return
     */
    boolean isUseScrollEvent() {
        return this.useScrollEvent;
    }

    /**
     * <p>
     * 获取是否开启响应双击事件，交互的时候可以灵活使用
     * </p>
     * @return
     */
    boolean isUseDoubleTapEvent() {
        return useDoubleTapEvent;
    }

    /**
     * <p>
     * 设置是否开启响应双击事件，交互的时候可以灵活使用
     * </p>
     * @param useDoubleTapEvent 是否开启响应双击事件
     */
    public void setUseDoubleTapEvent(boolean useDoubleTapEvent) {
        this.useDoubleTapEvent = useDoubleTapEvent;
    }

    void setReticleDrawMode(ReticleDrawMode mode) {
        this.reticalMode = mode;
    }

    void setScale(float scaleX, float scaleY, float focusX, float focusY) {
        this.currentScale = scaleX;

        this.scalePoint.x = (int) focusX;
        this.scalePoint.y = (int) focusY;
        moved();
    }

    /**
     * <p>
     * 销毁 MapView 对象，退出前调用。
     * </p>
     */
    public void destroy() {
        for (AbstractTileLayerView layerView : this.layerViewList) {
            layerView.destroy();
        }
        clearMapStatues();
        // if (this.trafficManager != null) {
        // this.trafficManager.destroy();
        // this.trafficManager = null;
        // }

        if (this.mapController != null) {
            this.mapController.destroy();
            this.mapController = null;
        }

        if (this.tileCacher != null) {
            this.tileCacher.destroy();
            this.tileCacher = null;
        }

        if (this.tileProvider != null) {
            this.tileProvider.destroy();
            this.tileProvider = null;
        }

        this.overlayController.destroy();

        if (this.eventHandler != null) {
            // if ((this.eventHandler instanceof TouchEventHandler)) {
            this.eventHandler.destroy();
            // }
            this.eventHandler = null;
        }

        if (this.networkConnectivityListener != null) {
            this.networkConnectivityListener.stopListening();
            this.networkConnectivityListener = null;
        }

        if (this.zoomButtonsController != null) {
            this.zoomButtonsController.setVisible(false);
            this.zoomButtonsController = null;
            this.builtInZoomControls = false;
        }

        if (this.mapEventCallback != null) {
            getEventDispatcher().removeHandler(this.mapEventCallback);
            this.mapEventCallback = null;
        }
        getEventDispatcher().removeAllHandlers();

        this.zoomControls = null;
        if (mapViewEventListeners != null) {
//            if (mapViewEventListeners.size() > 0) {
//                for (Handler h : mapViewEventListeners.values()) {
//                    EventDispatcher.removeHandler(h);
//                    h = null;
//                }
//            }
            mapViewEventListeners.clear();
            mapViewEventListeners = null;
        }
        if (animators != null) {
            animators.clear();
            animators = null;
        }
        if (this.layerViewList != null) {
            layerViewList.clear();
            layerViewList = null;
        }
        if (unInitedLayerViewList != null) {
            unInitedLayerViewList.clear();
            unInitedLayerViewList = null;
        }
        if (mLastScreen != null) {
            mLastScreen.recycle();
            mLastScreen = null;
        }
        isDetroy = true;
        destroyDrawingCache();
        // ((MapActivity) this.context).removeMapView(this);
        SqliteTileSourceFactory.getInstance().dispose();
        System.gc();
    }

    void moved() {
        redoLayout(true, getLeft(), getTop(), getLeft() + getWidth(), getTop() + getHeight());
    }

    void zoomStart() {
        this.scaling = true;
    }

    void zoomEnd() {
        this.scaling = false;
        // 双击和缩放按钮的缩放结束时，会设置scaling为false和currentScale为2或是0.5，此处修正currentScale为1，这样才不会出现用下一级别的瓦片再进行拉伸的现象。
        // this.currentScale = 1.0f;
        // preLoad();
        moved();// 使mapview里面其他子布局能够在动画缩放完成后实现重新布局，保证随着地图而动
    }

    /**
     * <p>
     * 清除运行时内存中缓存瓦片。
     * </p>
     */
    public void clearTilesInMemory() {
        if (this.tileCacher != null) {
            ITileCache mem = this.tileCacher.getCache(TileCacher.CacheType.MEMORY);
            if (mem != null)
                mem.clear();
        }
    }

    /**
     * <p>
     * 清除运行时手机端的SD卡中所有图层的缓存瓦片。
     * </p>
     */
    public void clearTilesInDB() {
        if (this.tileCacher != null) {
            ITileCache db = this.tileCacher.getCache(TileCacher.CacheType.DB);
            if (db != null)
                db.clear();
        }
    }

    /**
     * <p>
     * 启动定时清除缓存定时器，清楚缓存时也清除服务器中的缓存。
     * </p>
     * @param minute 清除缓存的时间间隔，单位为分钟。
     */
    public void startClearCacheTimer(int minute) {
        for (AbstractTileLayerView layerView : layerViewList) {
            if (layerView instanceof LayerView) {
                ((LayerView) layerView).startClearCacheTimer(minute);
            }
            // layerView.startClearCacheTimer(minute);
        }
    }

    /**
     * <p>
     * 启动定时清除缓存定时器，并根据 clearServerCache 的值判断是否定时清除服务器中的缓存。
     * </p>
     * @param minute 清除缓存的时间间隔，单位为分钟。
     * @param clearServerCache 是否定时清除服务器中的缓存。
     */
    public void startClearCacheTimer(int minute, boolean clearServerCache) {
        for (AbstractTileLayerView layerView : layerViewList) {
            if (layerView instanceof LayerView) {
                ((LayerView) layerView).startClearCacheTimer(minute, clearServerCache);
            }
            // layerView.startClearCacheTimer(minute, clearServerCache);
        }
    }

    /**
     * <p>
     * 停止和销毁清除缓存的定时器。
     * </p>
     */
    public void stopClearCacheTimer() {
        for (AbstractTileLayerView layerView : layerViewList) {
            if (layerView instanceof LayerView) {
                ((LayerView) layerView).stopClearCacheTimer();
            }
            // layerView.stopClearCacheTimer();
        }
    }

    Rect visibleRegion() {
        this.visibleRect.set(this.leftMargin, this.topMargin, getWidth() - this.rightMargin, getHeight() - this.bottomMargin);

        return this.visibleRect;
    }

    int getTopMargin() {
        return this.topMargin;
    }

    void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    int getBottomMargin() {
        return this.bottomMargin;
    }

    void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    int getRightMargin() {
        return this.rightMargin;
    }

    void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }

    int getLeftMargin() {
        return this.leftMargin;
    }

    void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    // Configuration getConfiguration() {
    // return this.configuration;
    // }

    TileCacher getTileCacher() {
        return this.tileCacher;
    }

    void onResume() {
        /*if (this.getTileProvider() == null) {
            this.tileProvider = new ThreadBasedTileDownloader(this, this.tileCacher);
        }*/// preLoad中进行判断
        preLoad();
    }

    void onPause() {
        if (this.tileProvider != null) {
            this.tileProvider.destroy();
            this.tileProvider = null;
        }
    }

    void onStop() {
        if (this.tileCacher != null) {
            ITileCache mem = this.tileCacher.getCache(TileCacher.CacheType.MEMORY);
            if (mem != null)
                mem.clear();
        }
    }

    /**
     * <p>
     * 添加一个 MapView 事件监听器。
     * </p>
     * @param eventListener MapView 事件的监听器对象。
     */
    public void addMapViewEventListener(final MapViewEventListener eventListener) {
        Looper looper = getContext().getApplicationContext().getMainLooper();
        final MapView mapView = this;
        Handler handler = new Handler(looper, new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 21:
                        eventListener.moveStart(mapView);
                        break;
                    case 22:
                        eventListener.move(mapView);
                        break;
                    case 23:
                        eventListener.moveEnd(mapView);
                        break;
                    case 4:
                        eventListener.longTouch(mapView);
                        break;
                    case 11:
                        eventListener.zoomStart(mapView);
                        break;
                    case 12:
                        eventListener.zoomEnd(mapView);
                        break;
                    case 3:
                        eventListener.touch(mapView);
                        break;
                    case 1:
                        eventListener.mapLoaded(mapView);
                        break;
                    case 2:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                }
                return false;
            }
        });
        this.mapViewEventListeners.put(eventListener, handler);
        getEventDispatcher().registerHandler(handler);
    }

    /**
     * <p>
     * 移除指定的 MapView 事件监听器。
     * </p>
     * @param eventListener 要移除的 MapView 事件监听器。
     */
    public void removeMapViewEventListener(MapViewEventListener eventListener) {
        if (this.mapViewEventListeners.containsKey(eventListener)) {
            getEventDispatcher().removeHandler((Handler) this.mapViewEventListeners.get(eventListener));
            this.mapViewEventListeners.remove(eventListener);
        }
    }

    /**
     * <p>
     * MapView 事件的监听器接口。用户可自行实现各事件。
     * </p>
     * @author ${Author}
     * @version ${Version}
     */
    public static abstract interface MapViewEventListener {
        /**
         * <p>
         * 地图开始移动后调用此方法。
         * </p>
         * @param paramMapView 地图视图对象。
         */
        public abstract void moveStart(MapView paramMapView);

        /**
         * <p>
         * 地图移动时调用此方法。
         * </p>
         * @param paramMapView 地图视图对象。
         */
        public abstract void move(MapView paramMapView);

        /**
         * <p>
         * 地图结束移动时调用此方法。
         * </p>
         * @param paramMapView 地图视图对象。
         */
        public abstract void moveEnd(MapView paramMapView);

        /**
         * <p>
         * 触摸后调用此方法。
         * </p>
         * @param paramMapView 地图视图对象。
         */
        public abstract void touch(MapView paramMapView);

        /**
         * <p>
         * 长时间触摸后调用此方法。
         * </p>
         * @param paramMapView 地图视图对象。
         */
        public abstract void longTouch(MapView paramMapView);

        /**
         * <p>
         * 地图开始缩放后调用此方法。
         * </p>
         * @param paramMapView 地图视图对象。
         */
        public abstract void zoomStart(MapView paramMapView);

        /**
         * <p>
         * 地图缩放结束后调用此方法。
         * </p>
         * @param paramMapView 地图视图对象。
         */
        public abstract void zoomEnd(MapView paramMapView);

        /**
         * <p>
         * 加载地图后调用此方法。
         * </p>
         * @param paramMapView 地图视图对象。
         */
        public abstract void mapLoaded(MapView paramMapView);
    }

    private static class Reticle {
        static Paint paint;

        public static void draw(Canvas canvas, View view, Point focalPoint) {
            int radius = Math.min(view.getWidth(), view.getHeight()) / 10;
            int lineSize = radius * 2 + 10;
            int startX = focalPoint.x - (lineSize >> 1);
            int startY = focalPoint.y - (lineSize >> 1);
            canvas.drawCircle(focalPoint.x, focalPoint.y, radius, getPaint());
            canvas.drawLine(focalPoint.x, startY, focalPoint.x, startY + lineSize, paint);

            canvas.drawLine(startX, focalPoint.y, startX + lineSize, focalPoint.y, paint);
        }

        private static synchronized Paint getPaint() {
            if (paint == null) {
                paint = new Paint(1);
                paint.setDither(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(-7829368);
                paint.setStrokeWidth(1.0F);
                DashPathEffect dashEffect = new DashPathEffect(new float[] { 5.0F, 5.0F }, 1.0F);
                paint.setPathEffect(dashEffect);
            }
            return paint;
        }
    }

    /**
     * <p>
     * 每个子视图与MapView关联的布局信息。
     * </p>
     * @author ${Author}
     * @version ${Version}
     *
     */
    public static class LayoutParams extends RelativeLayout.LayoutParams {

        /**
         * <p>
         * 水平对齐方式：底端对齐。
         * </p>
         */
        public static final int BOTTOM = 32;

        /**
         * <p>
         * 水平对齐方式：水平方向向上居中对齐。
         * </p>
         */
        public static final int CENTER_HORIZONTAL = 1;

        /**
         * <p>
         * 垂直对齐方式：垂直方向上居中对齐。
         * </p>
         */
        public static final int CENTER_VERTICAL = 2;

        /**
         * <p>
         * 水平对齐方式：左对齐。
         * </p>
         */
        public static final int LEFT = 4;

        /**
         * <p>
         * 水平对齐方式：右对齐。
         * </p>
         */
        public static final int RIGHT = 8;

        /**
         * <p>
         * 垂直对齐方式：上对齐。
         * </p>
         */
        public static final int TOP = 16;

        /**
         * <p>
         * 对齐方式：左上方对齐。
         * </p>
         */
        public static final int TOP_LEFT = 20;

        /**
         * <p>
         * 对齐方式：水平和垂直方向上都是居中对齐。
         * </p>
         */
        public static final int CENTER = 3;

        /**
         * <p>
         * 对齐方式：垂直方向上底端对齐，水平方向上居中对齐。
         * </p>
         */
        public static final int BOTTOM_CENTER = 33;
        /**
         * <p>
         * 布局模式：相对于地图。当地图滚动或缩放时，子视图的位置就会改变。
         * </p>
         */
        public static final int MODE_MAP = 0;
        /**
         * <p>
         * 布局模式：相对于地图视图。子视图的位置相对于父视图保持不变，当地图滚动或缩放时不会移动。
         * </p>
         */
        public static final int MODE_VIEW = 1;

        /**
         * <p>
         * 子视图放置的对齐方式。
         * </p>
         */
        public int alignment = 3;

        /**
         * <p>
         * 布局模式
         * </p>
         */
        public int mode = 1;
        /**
         * <p>
         * 子视图在地图上的位置。当模式为MODE_MAP时使用。
         * </p>
         */
        public Point2D point;

        /**
         * <p>
         * 子视图相对于地图视图的x坐标位置。
         * </p>
         */
        public int x = 2147483647;

        /**
         * <p>
         * 子视图相对于地图视图的y坐标位置。
         * </p>
         */
        public int y = 2147483647;

        /**
         * <p>
         * 构造函数。
         * </p>
         * @param context
         * @param attrs
         */
        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.x = attrs.getAttributeIntValue("http://schemas.mapquest.com/apk/res/mapquest", "x", 2147483647);
            this.y = attrs.getAttributeIntValue("http://schemas.mapquest.com/apk/res/mapquest", "x", 2147483647);
            String geoPoint = attrs.getAttributeValue("http://schemas.mapquest.com/apk/res/mapquest", "geoPoint");

            if ((geoPoint != null) && (geoPoint.length() > 0)) {
                String[] arr = geoPoint.split(",");
                if (arr.length > 1)
                    try {
                        double latitude = Double.parseDouble(arr[0].trim());
                        double longitude = Double.parseDouble(arr[1].trim());
                        this.point = new Point2D(longitude, latitude);
                        this.mode = 0;
                    } catch (NumberFormatException nfe) {
                        Log.e(LOG_TAG, resource.getMessage(MapCommon.MAPVIEW_GEOPOINT_INVALID, geoPoint));
                    }
            }
        }

        /**
         * <p>
         * 构造函数。
         * </p>
         * @param source
         */
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            if ((source instanceof LayoutParams)) {
                LayoutParams lp = (LayoutParams) source;
                this.x = lp.x;
                this.y = lp.y;
                this.point = lp.point;
                this.mode = lp.mode;
                this.alignment = lp.alignment;
            }
        }

        /**
         * <p>
         * 构造函数。
         * </p>
         * @param width
         * @param height
         * @param point
         * @param alignment
         */
        public LayoutParams(int width, int height, Point2D point, int alignment) {
            super(width, height);
            this.point = point;
            this.alignment = alignment;
            if (point != null)
                this.mode = 0;
        }

        /**
         * <p>
         * 构造函数。
         * </p>
         * @param width
         * @param height
         * @param point
         * @param x
         * @param y
         * @param alignment
         */
        public LayoutParams(int width, int height, Point2D point, int x, int y, int alignment) {
            super(width, height);
            this.point = point;
            this.x = x;
            this.y = y;
            this.alignment = alignment;
            if (point != null)
                this.mode = 0;
        }

        /**
         * <p>
         * 构造函数。
         * </p>
         * @param width
         * @param height
         * @param x
         * @param y
         * @param alignment
         */
        public LayoutParams(int width, int height, int x, int y, int alignment) {
            super(width, height);
            this.x = x;
            this.y = y;
            this.alignment = alignment;
        }
    }

    private static enum ReticleDrawMode {
        DRAW_RETICLE_NEVER,

        DRAW_RETICLE_OVER,

        DRAW_RETICLE_UNDER;
    }

    private class MapEventHandler extends Handler {
        private MapEventHandler() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 21:
                case 22:
                    MapView.this.moved();
                    return;
                case 23:
                case 33:
                    MapView.this.preLoad();
                    MapView.this.moved();
                    return;
                case 11:
                    // if (MapView.this.currentScale != 1.0F) {
                    // int zoom = !MapView.this.scaling ? MapView.this.zoomLevel : (int) Math.round(MapView.this.zoomLevel + Util.log2(MapView.this.currentScale));
                    //
                    // if (MapView.this.validateZoomLevel(zoom)) {
                    // int tempZoomLevel = MapView.this.zoomLevel;
                    //
                    // if ((MapView.this.scalePoint.x != MapView.this.focalPoint.x) || (MapView.this.scalePoint.y != MapView.this.focalPoint.y)) {
                    // MapView.this.setZoomLevel(zoom);
                    //
                    // MapView.this.centerGeoPoint = MapView.this.getProjection().fromPixels(MapView.this.scalePoint.x, MapView.this.scalePoint.y);
                    //
                    // int newCenterX = MapView.this.focalPoint.x + (MapView.this.focalPoint.x - MapView.this.scalePoint.x);
                    // int newCenterY = MapView.this.focalPoint.y + (MapView.this.focalPoint.y - MapView.this.scalePoint.y);
                    // MapView.this.centerGeoPoint = MapView.this.getProjection().fromPixels(newCenterX, newCenterY);
                    // }
                    //
                    // if (!MapView.this.scaling) {
                    // MapView.this.setZoomLevel((int) Math.round(MapView.this.zoomLevel - Util.log2(MapView.this.currentScale)));
                    // } else {
                    // MapView.this.setZoomLevel(tempZoomLevel);
                    // }
                    // }
                    // }
                    // Log.d(LOG_TAG, "MapEventHandler 11 scaling=true");
                    MapView.this.scaling = true;
                    return;
                case 61:
                    MapView.this.preLoad();
                    MapView.this.postInvalidate();
                    return;
                case PRELOAD:
                    MapView.this.preLoad();
                    MapView.this.postInvalidate();
                    break;
            }
        }
    }

    /**
     * <p>
     * 向 MapView 视图中添加一组图层视图子项.
     * </p>
     * @param layerViews 图层视图数组。
     */
    public void addLayers(AbstractTileLayerView[] layerViews) {
        if (layerViews != null) {
            for (AbstractTileLayerView layView : layerViews) {
                this.addLayer(layView);
            }
        }
    }

    // GL图层
    private RMGLCanvas mGLLayer = null;

    /**
     * <p>
     * 向MapView视图中添加GL图层视图子项,只作为底图
     * <p>
     * @param rmglCanvas 要添加的图层视图
     */
    public void addGLLayer(RMGLCanvas rmglCanvas){
        if (rmglCanvas == null) {
            Log.d("MapView: " , "GLLayer is null!");
            return;
        }
        if (mGLLayer == rmglCanvas) {
            Log.d("MapView: " , "Add the same GLLayer!");
            return;
        }
        if (mGLLayer != null) {
            mGLLayer.dispose();
            mGLLayer = null;
        }
        mGLLayer = rmglCanvas;
        if (mGLLayer.getParent() == null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            this.addView(mGLLayer,params);
        }

    }

    /**
     * <p>
     * 向 MapView 视图中添加一个图层视图子项。
     * </p>
     * @param layerView 要添加的图层视图。
     */
    public void addLayer(AbstractTileLayerView layerView) {
        this.addLayer(layerView, -1);
    }

    /**
     * <p>
     * 向 MapView 视图的指定位置添加一个图层视图子项.
     * </p>
     * @param layerView 要添加的图层视图
     * @param index 图层添加的位置索引。
     */
    public void addLayer(AbstractTileLayerView layerView, int index) {
        this.addLayerInner(layerView, index);
    }

    private void addLayerInner(AbstractTileLayerView layerView, int index) {
        if (layerView == null) {
            return;
        }
        // 存储添加进来的所有尚未初始化的layerView，保证清除图层的接口能够生效
        if (index < 0 || index > unInitedLayerViewList.size()) {
            this.unInitedLayerViewList.add(layerView);
            // this.addView(layerView);
        } else {
            // this.addView(layerView, index);
            this.unInitedLayerViewList.add(index, layerView);
        }
        // if (this.baseLayer == null || index == 0) {
        // // 先假设这个类url参数正确，可以被正确初始化，没有baseLayer就认为这个layer是baseLayer
        // this.baseLayer = layerView;
        // } else {
        // // 判断底图是否初始化完成
        // layerView.setCRS(this.crs);
        // }
        if (this.baseLayer != null && index != 0) {
            // 判断底图是否初始化完成
            layerView.setCRS(this.crs);
        }
        layerView.mapView = this;// 解决在图层未初始化完 能够清除已有的sdcard缓存，以前是未初始化完是不能清除的，因为layerView.mapView为null
        int layerSize = this.layerViewList.size();
        if (layerView.getParent() == null && !layerViewList.contains(layerView)) {
            if (index < 0 || index > layerSize) {
                Log.d(LOG_TAG, "context thread:" + (Thread.currentThread()));
                Log.d(LOG_TAG, "currentThread thread:" + (this.context.getMainLooper().getThread()));
                Log.d(LOG_TAG, "thread:" + (this.context.getMainLooper().getThread() == Thread.currentThread()));
                this.addView(layerView);
                this.layerViewList.add(layerView);
            } else {
                this.addView(layerView, index);// 不严谨，中间可能有其他类型的View
                this.layerViewList.add(index, layerView);
            }
        }
        if (layerView.isInitialized()) {
            if (this.baseLayer == null || index == 0) {
                // 先假设这个类url参数正确，可以被正确初始化，没有baseLayer就认为这个layer是baseLayer
                if (unInitedLayerViewList.size() > 0 && index != 0) {// 保证第一个加进来的图层是底图。
                    this.baseLayer = unInitedLayerViewList.get(0);
                } else {
                    this.baseLayer = layerView;
                }
            } else {
                // 判断底图是否初始化完成
                layerView.setCRS(this.baseLayer.getCRS());
            }
            updateMapStatus(layerView);
            layerView.initProjection();
            if (layerView == this.baseLayer) {
                this.isGCS = layerView.isGCSLayer();
                this.crs = layerView.getCRS();
                // 底图初始化成功后，试图再次使设置可视范围生效，保证必须执行一次
                showByViewBounds();
                reInitProjectionToLayers();// 切换底图的时候要重新初始化其他底图的Projection，不然计算瓦片位置出错
            }
            // 更新比例尺控件
            updateScaleBar();
            // layerView.mapView = this;
            // int layerSize = this.layerViewList.size();
            // if (layerView.getParent() == null && !layerViewList.contains(layerView)) {
            //
            // if (index < 0 || index > layerSize) {
            // this.addView(layerView);
            // this.layerViewList.add(layerView);
            // } else {
            // this.addView(layerView, index);// 不严谨，中间可能有其他类型的View
            // this.layerViewList.add(index, layerView);
            // }
            // }
            // 当layerView在未初始化完成就被删除了但是此时已初始化并被添加到map中了，此时重新从map中清除掉
            // if (!unInitedLayerViewList.contains(layerView)) {
            // removeLayer(layerView);
            // }

        } else if (layerView.getURL() != null && layerView.getURL().trim().length() > 0 && layerView instanceof LayerView) {
            InnerOnStatusChangedListener listener = new InnerOnStatusChangedListener(index);
            LayerView lv = ((LayerView) layerView);
            lv.setLayerViewStatusChangeListener(listener);
            lv.new UpdateLayerViewStatuThread(false, false).start();
        } else if (layerView.getURL() != null && layerView.getURL().trim().length() > 0 && layerView instanceof WMTSLayerView) {
            InnerOnWMTSLayerStatusChangedListener listener = new InnerOnWMTSLayerStatusChangedListener(index);
            WMTSLayerView lv = ((WMTSLayerView) layerView);
            lv.setLayerViewStatusChangeListener(listener);
        } else {
            layerView.mapView = this;
            /*if (layerView.layerBounds == null || !layerView.layerBounds.isValid()) {
                if (layerView.isGCSLayer()) {
                    layerView.layerBounds = new BoundingBox(new Point2D(-180, 90), new Point2D(180, -90));
                } else {
                    layerView.layerBounds = new BoundingBox(new Point2D(-20037508.34, 20037508.34), new Point2D(20037508.34, -20037508.34));
                }
            }*/
            if (this.baseLayer == layerView) {
                this.isGCS = layerView.isGCSLayer();
                if (this.isGCS) {
                    this.crs = new CoordinateReferenceSystem();
                    this.crs.wkid = 4326;
                }
            }
        }
    }

    // 切换底图的时候要重新初始化其他底图的Projection，不然计算瓦片位置出错
    private void reInitProjectionToLayers() {
        if (this.layerViewList != null && this.layerViewList.size() > 1) {
            for (int i = 0; i < layerViewList.size(); i++) {
                AbstractTileLayerView lv = layerViewList.get(i);
                if (this.baseLayer != lv) {
                    lv.initProjection();
                }
            }
        }
    }

    private class InnerOnStatusChangedListener implements OnStatusChangedListener {
        private int index;
        private int times = 0;

        InnerOnStatusChangedListener(int index) {
            this.index = index;
        }

        @Override
        public void onStatusChanged(Object sourceObject, STATUS status) {
            if (!(sourceObject instanceof LayerView)) {
                Log.w(LOG_TAG, resource.getMessage(MapCommon.MAPVIEW_SOURCEOBJECT, sourceObject.getClass().getCanonicalName()));
                return;
            }
            LayerView layerView = (LayerView) sourceObject;
            // layerView已被移除直接返回，不执行初始化后的添加
            if (unInitedLayerViewList == null || !unInitedLayerViewList.contains(layerView)) {
                return;
            }
            if (MapView.this.baseLayer == null || index == 0) {
                // 先假设这个类url参数正确，可以被正确初始化，没有baseLayer就认为这个layer是baseLayer
                if (unInitedLayerViewList.size() > 0 && index != 0) {// 保证第一个加进来的图层是底图。
                    MapView.this.baseLayer = unInitedLayerViewList.get(0);
                } else {
                    MapView.this.baseLayer = layerView;
                }
            } else {
                // 判断底图是否初始化完成
                layerView.setCRS(MapView.this.baseLayer.getCRS());
            }
            // layerView.mapView = MapView.this;
            if (MapView.this.baseLayer == layerView) {
                MapView.this.isGCS = layerView.isGCSLayer();
                MapView.this.crs = layerView.getCRS();
            }
            if (status.equals(STATUS.INITIALIZED)) {
                updateMapStatus(layerView);
                layerView.initProjection();
                // 底图初始化成功后，试图再次使设置可视范围生效，保证必须执行一次
                if (MapView.this.baseLayer == layerView) {
                    showByViewBounds();
                    reInitProjectionToLayers();// 切换底图的时候要重新初始化其他底图的Projection，不然计算瓦片位置出错
                }
                // 地图初始化完成后，刷新地图本身重新预处理
                layerView.preLoad();
                // 更新比例尺控件
                updateScaleBar();
                // int layerSize = MapView.this.layerViewList.size();
                // if (layerView.getParent() == null && !layerViewList.contains(layerView)) {
                // // int layerViewIndex = findLayerViewIndex(layerView);
                // if (index < 0 || index > layerSize) {
                // MapView.this.addView(layerView);
                // MapView.this.layerViewList.add(layerView);
                // } else {
                // MapView.this.addView(layerView, index);// 不严谨，中间可能有其他类型的View
                // MapView.this.layerViewList.add(index, layerView);
                // }
                // Log.i(LOG_TAG, "LayView:" + layerView.getLayerName() + " 初始化成功。");
                // // 当layerView在未初始化完成就被删除了但是此时已初始化并被添加到map中了，此时重新从map中清除掉
                // // if (!unInitedLayerViewList.contains(layerView)) {
                // // removeLayer(layerView);
                // // }
                // }
            } else {
                if (++times < 3) {
                    Log.i(LOG_TAG, "LayView:" + layerView.getLayerName() + " 第 " + times + " 次初始化地图参数失败，错误消息为：" + status.toString());
                    layerView.new UpdateLayerViewStatuThread(false, false).start();
                } else {
                    if (layerView == MapView.this.baseLayer) {
                        Log.w(LOG_TAG, "LayView:" + layerView.getLayerName() + "(" + layerView.getURL() + ")  第 3 次初始化地图参数失败，错误消息为：" + status.toString()
                                + "， 将使用默认参数出图。");
                        MapView.this.crs = layerView.getCRS();
                        MapView.this.isGCS = layerView.isGCSLayer();
                        /*if (MapView.this.crs == null) {
                            if (MapView.this.isGCS) {
                                MapView.this.crs = new CoordinateReferenceSystem();
                                MapView.this.crs.wkid = 4326;
                            }
                        }
                        if (layerView.layerBounds == null || !layerView.layerBounds.isValid()) {
                            if (layerView.isGCSLayer()) {
                                layerView.layerBounds = new BoundingBox(new Point2D(-180, 90), new Point2D(180, -90));
                            } else {
                                layerView.layerBounds = new BoundingBox(new Point2D(-20037508.34, 20037508.34), new Point2D(20037508.34, -20037508.34));
                            }
                        }
                        MapView.this.centerGeoPoint = new Point2D(layerView.layerBounds.getCenter());*/
                    } else {
                        Log.w(LOG_TAG, "LayView:" + layerView.getLayerName() + "(" + layerView.getURL() + ") 初始化地图参数失败，错误消息为：" + status.toString());
                    }
                }
            }
        }
    }

    private class InnerOnWMTSLayerStatusChangedListener implements OnStatusChangedListener {
        private int index;

        public InnerOnWMTSLayerStatusChangedListener(int index) {
            this.index = index;
        }

        @Override
        public void onStatusChanged(Object sourceObject, STATUS status) {
            if (!(sourceObject instanceof WMTSLayerView)) {
                Log.w(LOG_TAG, resource.getMessage(MapCommon.MAPVIEW_SOURCEOBJECT, sourceObject.getClass().getCanonicalName()));
                return;
            }
            WMTSLayerView wmtsLayerView = (WMTSLayerView) sourceObject;
            // layerView已被移除直接返回，不执行初始化后的添加
            if (unInitedLayerViewList == null || !unInitedLayerViewList.contains(wmtsLayerView)) {
                return;
            }
            if (MapView.this.baseLayer == null || index == 0) {
                // 先假设这个类url参数正确，可以被正确初始化，没有baseLayer就认为这个layer是baseLayer
                if (unInitedLayerViewList.size() > 0 && index != 0) {// 保证第一个加进来的图层是底图。
                    MapView.this.baseLayer = unInitedLayerViewList.get(0);
                } else {
                    MapView.this.baseLayer = wmtsLayerView;
                }
            } else {
                // 判断底图是否初始化完成
                wmtsLayerView.setCRS(MapView.this.baseLayer.getCRS());
            }
            // layerView.mapView = MapView.this;
            if (MapView.this.baseLayer == wmtsLayerView) {
                MapView.this.isGCS = wmtsLayerView.isGCSLayer();
                MapView.this.crs = wmtsLayerView.getCRS();
            }
            if (status.equals(STATUS.INITIALIZED)) {
                updateMapStatus(wmtsLayerView);
                // 底图初始化成功后，试图再次使设置可视范围生效，保证必须执行一次
                if (MapView.this.baseLayer == wmtsLayerView) {
                    showByViewBounds();
                }
                wmtsLayerView.initProjection();
                // 地图初始化完成后，刷新地图本身重新预处理
                wmtsLayerView.preLoad();
                // 更新比例尺控件
                updateScaleBar();
            }
        }
    }

    /**
     * 更新必要的地图状态，相当于 重新渲染：需要加锁么？具体图层更新时调用--huangqh
     * @param layView
     * @param updateBoundsInfo
     */
    void updateMapStatus(AbstractTileLayerView layView, boolean updateBoundsInfo, boolean updateScalesInfo) {
        // 更新地图状态时不应该绘制图片
        if (this.baseLayer == layView) {
            if (this.crs != null && !this.crs.equals(layView.getCRS())) {
                // 需要更新坐标信息,并重新初始化地图状态，
                this.crs = layView.getCRS();
                this.isGCS = layView.isGCSLayer();
            }
            resetMapStatus(updateBoundsInfo, updateScalesInfo);
        } else {
            resetMapStatus(false, updateScalesInfo);
        }

    }

    // 只考虑baseLayer的范围，--huangqh
    /**
     * <p>
     * 重新设置地图信息（比例尺数组，地图范围，地图坐标系）
     * 更新图层比例尺数组、删除图层和刷新地图时调用该接口
     * </p>
     * @param updateBoundsInfo
     * @param updateScalesInfo
     */
    private void resetMapStatus(boolean updateBoundsInfo, boolean updateScalesInfo) {
        if (!updateBoundsInfo && !updateScalesInfo) {
            return;
        }
        if (updateBoundsInfo) {
            this.mapIndexBounds = null;// 确认会不会在其他线程中调用出图，计算的时候出现空指针异常。
            this.centerGeoPoint = null;
            if (this.resolutions == null || this.resolutions.length == 0) {
                updateScalesInfo = true;
            }
        }
        if (updateScalesInfo) {
            this.scales = null;
            this.resolutions = null;
        }
        if (baseLayer != null && this.baseLayer.isInitialized()) {
            initScalesAndResolutions(this.baseLayer, updateBoundsInfo, updateScalesInfo);
        }
        for (int index = 1; index < this.layerViewList.size(); index++) {
            AbstractTileLayerView layerView = this.layerViewList.get(index);
            if (layerView.isInitialized()) {
                initScalesAndResolutions(layerView, false, updateScalesInfo);
            }
        }
    }

    /**
     * 图层初始化完成后更新地图信息（比例尺数组，地图范围，如果是第一次添加的话则还会设置地图坐标系）
     * 一个图层存在调用多次该接口，所以存在多次合并该图层的比例尺数组到地图中去，唯有该图层的比例尺数组保持不变才能保证不会出错，
     * 因为一样的比例尺数组添加多次也只会加一次，当onStatusChanged接收到图层初始化成功的信息就会调用该接口一次，
     * 好在初始化地图状态成功后不会修改图层的比例尺数组
     * @param layerView layView为baseLayer时才应该调用这个函数更新地图
     */
    private void updateMapStatus(AbstractTileLayerView layerView) {
        if (layerView == this.baseLayer || (baseLayer != null && !baseLayer.isInitialized() && this.mapIndexBounds == null)) {
            this.crs = layerView.getCRS();
            this.isGCS = layerView.isGCSLayer();
            initScalesAndResolutions(layerView, true, true);
        } else {
            initScalesAndResolutions(layerView, false, true);
        }
    }

    /**
     * <p>
     * 使用base图层的bound修改map的bound和合并所有图层的固定比例尺于map中，并用resolutions的数目修改层级数
     * </p>
     * @param layView
     * @param updateBoundsInfo 是否更新map的bound
     * @param updateScalesInfo 是否合并所有图层的固定比例尺于map中
     */
    private strictfp void initScalesAndResolutions(AbstractTileLayerView layView, boolean updateBoundsInfo, boolean updateScalesInfo) {
        if (!updateBoundsInfo && !updateScalesInfo) {
            return;
        }
        BoundingBox tempMapBounds = layView.getBounds();
        if (layView.getBounds() == null || !layView.getBounds().isValid()) {
            return;
        }

        if (updateBoundsInfo) {
            adjustMapBounds(tempMapBounds);
            this.mapIndexBounds = tempMapBounds;// 只考虑base图层的bounds，用来出图位置的计算用的
            if (this.centerGeoPoint == null) {
                this.centerGeoPoint = new Point2D((tempMapBounds.getLeft() + tempMapBounds.getRight()) / 2.0,
                        (tempMapBounds.getTop() + tempMapBounds.getBottom()) / 2.0);
            }
        }

        if (!updateScalesInfo) {
            return;
        }

        double[] tempResolutions = this.resolutions;
        double[] currentResolutions = layView.getResolutions();
        if (currentResolutions != null && currentResolutions.length > 0) {
            // 重新设置固定比例尺数组
            if (tempResolutions == null || tempResolutions.length == 0) {
                // this.visibleScales = Arrays.copyOf(tempScales, tempScales.length);
                tempResolutions = new double[currentResolutions.length];
                System.arraycopy(currentResolutions, 0, tempResolutions, 0, currentResolutions.length);
                tempResolutions = Tool.getResolutions(tempResolutions);
            } else {
                // 融合两个比例尺数组，先添加，再排序，再抽取
                double[] visibleresolutionsArray = new double[tempResolutions.length + currentResolutions.length];
                // visibleScalesArray = Arrays.copyOf(this.visibleScales, this.visibleScales.length);
                System.arraycopy(tempResolutions, 0, visibleresolutionsArray, 0, tempResolutions.length);
                for (int index = 0; index < currentResolutions.length; index++) {
                    visibleresolutionsArray[index + tempResolutions.length] = currentResolutions[index];
                }
                tempResolutions = Tool.getResolutions(visibleresolutionsArray);
            }
            if (tempResolutions != null && tempResolutions.length > 0) {
                int resolutionsCount = tempResolutions.length;
                if (resolutionsCount - 1 != maxLevel) {
                    updateZoomControls();// 更新缩放按钮的状态
                }
                this.maxLevel = resolutionsCount - 1;// 层级数以resolutions为基准
                this.resolutions = new double[resolutionsCount];
                Log.d(LOG_TAG, "resolutionsCount:" + resolutionsCount);
                System.arraycopy(tempResolutions, 0, this.resolutions, 0, tempResolutions.length);
            }
        }

        double[] tempVisibleScales = this.scales;
        double[] currentLayerVisibleScales = layView.getScales();
        if (currentLayerVisibleScales != null && currentLayerVisibleScales.length > 0) {
            // 重新设置固定比例尺数组
            if (tempVisibleScales == null || tempVisibleScales.length == 0) {
                // this.visibleScales = Arrays.copyOf(tempScales, tempScales.length);
                tempVisibleScales = new double[currentLayerVisibleScales.length];
                System.arraycopy(currentLayerVisibleScales, 0, tempVisibleScales, 0, currentLayerVisibleScales.length);
                tempVisibleScales = Tool.getValibScales(tempVisibleScales);
            } else {
                // 融合两个比例尺数组，先添加，再排序，再抽取
                double[] visibleScalesArray = new double[tempVisibleScales.length + currentLayerVisibleScales.length];
                // visibleScalesArray = Arrays.copyOf(this.visibleScales, this.visibleScales.length);
                System.arraycopy(tempVisibleScales, 0, visibleScalesArray, 0, tempVisibleScales.length);
                for (int index = 0; index < currentLayerVisibleScales.length; index++) {
                    visibleScalesArray[index + tempVisibleScales.length] = currentLayerVisibleScales[index];
                }
                tempVisibleScales = Tool.getValibScales(visibleScalesArray);
            }
            if (tempVisibleScales != null && tempVisibleScales.length > 0) {
                int scalesCount = tempVisibleScales.length;
                this.scales = new double[scalesCount];
                Log.d(LOG_TAG, "scalesCount:" + scalesCount);
                System.arraycopy(tempVisibleScales, 0, this.scales, 0, tempVisibleScales.length);
            }
        }

        // if (tempVisibleScales == null || tempVisibleScales.length == 0) {
        // // 计算地图合适的比例尺数组（所有图层全副显示）
        // int width = this.getMapWidth();
        // double geoWidth = tempMapBounds.rightBottom.getX() - tempMapBounds.leftTop.getX();
        // if (isGCS) {
        // // 考虑坐标系
        // double radius = Constants.DEFAULT_AXIS;
        // if (baseLayer.getCRS() != null) {
        // radius = baseLayer.getCRS().datumAxis > 1d ? baseLayer.getCRS().datumAxis : Constants.DEFAULT_AXIS;
        // }
        // geoWidth = geoWidth * Math.PI * radius / 180.0;
        // }
        // double firstResolution = geoWidth / (width * 1.0);
        // double firstScale = tempRatio / firstResolution;// defaultResolution * defaultScale / firstResolution;
        //
        // this.scales = new double[Constants.DEFAULT_RESOLUTION_SIZE];
        // this.resolutions = new double[Constants.DEFAULT_RESOLUTION_SIZE];
        // this.maxLevel = Constants.DEFAULT_RESOLUTION_SIZE - 1;
        // for (int index = 0; index < Constants.DEFAULT_RESOLUTION_SIZE; index++) {
        // this.resolutions[index] = firstResolution / (Math.pow(2, index));// 2 << index
        // this.scales[index] = firstScale * (Math.pow(2, index));// 2 << index
        // }
        // } else {
        // int scalesCount = tempVisibleScales.length;
        // this.maxLevel = scalesCount - 1;
        // // this.visibleScales = new double[scalesCount];
        // // System.arraycopy(tempVisibleScales, 0, this.visibleScales, 0, tempVisibleScales.length);
        // // this.scales = Arrays.copyOf(this.visibleScales, scalesCount);
        // this.scales = new double[scalesCount];
        // System.arraycopy(tempVisibleScales, 0, this.scales, 0, tempVisibleScales.length);
        // this.resolutions = new double[scalesCount];
        // for (int index = 0; index < scalesCount; index++) {
        // // this.resolutions[index] = defaultScale * defaultResolution / this.scales[index];
        // this.resolutions[index] = tempRatio / this.scales[index];
        // }
        // }
    }

    /**
     * 调整当前地图的Bounds，使其与屏幕大小符合。
     * @param
     */
    private strictfp void adjustMapBounds(BoundingBox bounds) {
        int width = this.getMapWidth();
        int height = this.getMapHeight();

        double geoWidth = bounds.rightBottom.getX() - bounds.leftTop.getX();
        double geoHeight = bounds.leftTop.getY() - bounds.rightBottom.getY();
        double geoAspect = geoWidth / geoHeight;
        double aspect = width / (height * 1.0);
        double offsetWidth = 0d;
        double offsetHeight = 0d;
        if (width > height) {
            if (geoWidth > geoHeight) {
                if (aspect > geoAspect) {
                    offsetWidth = geoHeight * aspect - geoWidth;
                } else {
                    offsetHeight = geoWidth / aspect - geoHeight;
                }
            } else {
                offsetWidth = geoHeight * aspect - geoWidth;
            }
        } else if (width < height) {
            if (geoWidth < geoHeight) {
                if (aspect > geoAspect) {
                    offsetWidth = geoHeight * aspect - geoWidth;
                } else {
                    offsetHeight = geoWidth / aspect - geoHeight;
                }
            } else {
                offsetHeight = geoWidth / aspect - geoHeight;
            }
        } else if (geoWidth < geoHeight) {
            offsetWidth = geoHeight - geoWidth;
        } else if (geoWidth > geoHeight) {
            offsetHeight = geoWidth / aspect - geoHeight;
        }
        double left = bounds.leftTop.getX();
        double right = bounds.rightBottom.getX();
        double top = bounds.leftTop.getY();
        double bottom = bounds.rightBottom.getY();
        if (Math.abs(offsetWidth) > 1.0e-10) {
            left = bounds.leftTop.getX() - offsetWidth * 0.5;
            right = bounds.rightBottom.getX() + offsetWidth * 0.5;
        }

        if (Math.abs(offsetHeight) > 1.0e-10) {
            top = bounds.leftTop.getY() + offsetHeight * 0.5;
            bottom = bounds.rightBottom.getY() - offsetHeight * 0.5;
        }
        // Log.d(LOG_TAG, "offsetWidth:"+offsetWidth+",offsetHeight:"+offsetHeight);
        bounds.leftTop = new Point2D(left, top);
        bounds.rightBottom = new Point2D(right, bottom);
        // bounds = new BoundingBox(new Point2D(left, top), new Point2D(right, bottom));
    }

    /**
     * <p>
     * 返回地图视图中指定的图层视图。
     * </p>
     * @param resourceId 图层标识。
     * @return 地图视图中指定的图层视图。
     */
    public AbstractTileLayerView getLayerById(int resourceId) {
        // View localView = findViewById(resourceId);
        if (resourceId >= this.unInitedLayerViewList.size()) {
            return null;
        }
        View localView = this.unInitedLayerViewList.get(resourceId);
        return (localView != null) && ((localView instanceof AbstractTileLayerView)) ? (AbstractTileLayerView) localView : null;
    }

    /**
     * <p>
     * 返回地图视图中的所有图层视图数组。
     * </p>
     * @return 地图视图中的所有图层视图数组。
     */
    public AbstractTileLayerView[] getLayers() {
        if (unInitedLayerViewList == null) {
            return null;
        }
        int count = this.unInitedLayerViewList.size();
        List<AbstractTileLayerView> layerViews = new ArrayList<AbstractTileLayerView>();
        for (int i = 0; i < count; i++) {
            View localView = unInitedLayerViewList.get(i);
            if (!(localView instanceof AbstractTileLayerView))
                continue;
            layerViews.add((AbstractTileLayerView) localView);
        }
        return (AbstractTileLayerView[]) layerViews.toArray(new AbstractTileLayerView[layerViews.size()]);
    }

    /**
     * <p>
     * 移除地图视图中的所有图层视图。
     * </p>
     */
    public void removeAllLayers() {
        stopClearCacheTimer();
        if (this.layerViewList.size() > 0) {
            this.removeAllViews();
        }
        clearMapStatues();
    }

    /**
     * <p>
     * 移除指定的图层视图。
     * </p>
     * @param layerView 要移除的图层视图。
     */
    public void removeLayer(AbstractTileLayerView layerView) {
        if (layerView == null || (!this.unInitedLayerViewList.contains(layerView) && !this.layerViewList.contains(layerView))) {
            return;
        }
        // layerView未初始化完成并尚未添加到map中，此时执行删除时只能删除unInitedLayerViewList中的layerView，保证layerView初始化完成不会被添加到map中（即确实删除了）
        if (this.unInitedLayerViewList.contains(layerView) && !this.layerViewList.contains(layerView)) {
            unInitedLayerViewList.remove(layerView);
            return;
        }
        // 只要layerViewList包含了layerView就执行清除的工作，不论unInitedLayerViewList是否包含layerView
        if (layerView == this.baseLayer) {
            if (layerViewList.size() == 1) {
                // stopClearCacheTimer();
                // this.removeView(layerView);
                // clearMapStatues();
                removeAllLayers();
            } else {
                this.baseLayer = layerViewList.get(1);
                this.crs = this.baseLayer.getCRS();
                this.isGCS = this.baseLayer.isGCSLayer();
                if (layerView instanceof LayerView) {
                    ((LayerView) layerView).stopClearCacheTimer();
                }

                this.layerViewList.remove(layerView);
                this.unInitedLayerViewList.remove(layerView);
                resetMapStatus(true, true);
                this.removeView(layerView);
                if(this.baseLayer.isLayerInited){
                    this.baseLayer.initProjection();
                }
            }
        } else {
            if (layerView instanceof LayerView) {
                ((LayerView) layerView).stopClearCacheTimer();
            }
            // layerView.stopClearCacheTimer();
            this.removeView(layerView);
            this.layerViewList.remove(layerView);
            this.unInitedLayerViewList.remove(layerView);
            resetMapStatus(false, true);// 注释掉，解决清除图层后速度慢，而且中心点发生变化的问题——huangqh
        }
    }

    /**
     * 移除指定的图层视图
     * @param index
     */
    public void removeLayer(int index) {
        if (index >= unInitedLayerViewList.size()) {
            return;
        }
        removeLayer(unInitedLayerViewList.get(index));
    }

    /**
     * <p>
     * 刷新地图窗口。
     * </p>
     */
    public void refresh() {
        resetMapStatus(true, true);
        // 有没有判断条件，是否可以开始绘制
        postInvalidate();
    }

//    /**
//     * <p>
//     * 添加子视图。
//     * </p>
//     * @param parent 父视图。
//     * @param child 子视图。
//     */
//    @Override
//    public void onChildViewAdded(View parent, View child) {
//        if ((child instanceof AbstractTileLayerView)) {
//            this.addLayer((AbstractTileLayerView) child);
//        }
//    }
//
//    /**
//     * <p>
//     * 删除子视图。
//     * </p>
//     * @param parent 父视图。
//     * @param child 子视图。
//     */
//    @Override
//    public void onChildViewRemoved(View parent, View child) {
//        if ((child instanceof AbstractTileLayerView)) {
//            removeLayer((AbstractTileLayerView) child);
//        }
//    }

    /**
     * <p>
     * 返回地图视图的第一个子项图层视图。
     * </p>
     * @return 地图视图的第一个子项图层视图。
     */
    public AbstractTileLayerView getBaseLayer() {
        if (this.getLayers() != null && this.getLayers().length > 0 && baseLayer == null) {
            baseLayer = this.getLayers()[0];
            // resetMapProjectionAndURL(baseLayer);
        }
        return baseLayer;
    }

    private void clearMapStatues() {
        this.isGCS = false;
        this.crs = null;
        this.layerViewList.clear();
        this.unInitedLayerViewList.clear();
        this.baseLayer = null;
        this.centerGeoPoint = null;
        // this.visibleScales = null;
        this.scales = null;
        this.resolutions = null;
        this.projection = null;
        this.maxLevel = Constants.DEFAULT_RESOLUTION_SIZE - 1;
    }

    /**
     * <p>
     * 设置是否显示比例尺控件
     * </p>
     * @param show
     */
    public void showScaleControl(boolean show) {
        if ((show) && (!this.showScale)) {
            addView(this.scaleView);
            this.showScale = show;
        } else if ((!show) && (this.showScale)) {
            removeView(this.scaleView);
            this.showScale = show;
        }
    }

    /**
     * <p>
     * 初始化比例尺控件的布局和组成元素
     * </p>
     * @param context
     */
    private void initScaleBar(Context context) {
        this.scaleView = new RelativeLayout(context);
        ViewGroup.LayoutParams scaleViewLayoutParams = new ViewGroup.LayoutParams(-2, -2);
        this.scaleView.setLayoutParams(scaleViewLayoutParams);
        this.scaleTextView = new TextView(context);
        RelativeLayout.LayoutParams scaleTextViewLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        scaleTextViewLayoutParams.addRule(14);
        this.scaleTextView.setTextColor(Color.parseColor("#000000"));
        this.scaleTextView.setTextSize(2, 11.0F);
        this.scaleTextView.setTypeface(this.scaleTextView.getTypeface(), 1);
        this.scaleTextView.setLayoutParams(scaleTextViewLayoutParams);
        this.scaleTextView.setId(2147483647);
        this.scaleView.addView(this.scaleTextView);
        // this.v = new TextView(context);
        // RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        // localLayoutParams2.width = -2;
        // localLayoutParams2.height = -2;
        // localLayoutParams2.addRule(14);
        // this.v.setTextColor(Color.parseColor("#000000"));
        // this.v.setTextSize(2, 11.0F);
        // this.v.setLayoutParams(localLayoutParams2);
        // this.t.addView(this.v);
        this.scaleImageView = new ImageView(context);
        RelativeLayout.LayoutParams scaleImageViewLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        scaleImageViewLayoutParams.width = -2;
        scaleImageViewLayoutParams.height = -2;
        scaleImageViewLayoutParams.addRule(14);
        scaleImageViewLayoutParams.addRule(3, this.scaleTextView.getId());
        this.scaleImageView.setLayoutParams(scaleImageViewLayoutParams);
        // AssetManager localAssetManager = context.getAssets();
        Bitmap localBitmap = null;
        try {
            // localBitmap = BitmapFactory.decodeStream(localAssetManager.open("icon_scale.9.png"));
            InputStream is = context.getClass().getResourceAsStream("/com/supermap/imobilelite/maps/icon_scale.9.png");
            localBitmap = BitmapFactory.decodeStream(is);
            if (localBitmap != null) {
                byte[] arrayOfByte = localBitmap.getNinePatchChunk();
                // boolean bool = NinePatch.isNinePatchChunk(arrayOfByte);
                NinePatchDrawable localNinePatchDrawable = new NinePatchDrawable(localBitmap, arrayOfByte, new Rect(), null);
                this.scaleImageView.setBackgroundDrawable(localNinePatchDrawable);
            } else {
                Log.i(LOG_TAG, "icon_scale.9.png is not existent");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.scaleView.addView(this.scaleImageView);
        // initScaleControlPosition();
    }

    /**
     * <p>
     * 用户自定义比例尺控件在屏幕上的布局位置
     * </p>
     * @param positionX 屏幕位置x
     * @param positionY 屏幕位置y
     */
    public void setScaleControlPosition(int positionX, int positionY) {
        this.scaleViewX = positionX;
        this.scaleViewY = positionY;
        initScaleControlPosition();
    }

    /**
     * <p>
     * 初始化比例尺控件在屏幕上的位置
     * </p>
     */
    private void initScaleControlPosition() {
        if (!this.showScale) {
            return;
        }
        int i1 = getMapWidth();
        int i2 = getMapHeight() - 30;
        // Log.d(LOG_TAG, "initScaleControlPosition w:" + i1 + ",h:" + i2);
        if (scaleViewY > i2 || scaleViewY < 1) {
            scaleViewY = i2;
        }
        if (scaleViewX > i1 || scaleViewX < 1) {
            scaleViewX = 5;
        }
        if ((scaleViewY >= 0) && (scaleViewY <= i2) && (scaleViewX >= 0) && (scaleViewX <= i1)) {
            checkView(this.scaleView);
            int i3 = this.scaleView.getMeasuredWidth();
            int i4 = this.scaleView.getMeasuredHeight();
            // Log.d(LOG_TAG, "scaleView getMeasuredWidth:" + i3 + ",getMeasuredHeight:" + i4);
            this.scaleView.layout(scaleViewX, scaleViewY, scaleViewX + i3, scaleViewY + i4);
        }
    }

    private void checkView(View paramView) {
        ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
        if (localLayoutParams == null)
            localLayoutParams = new ViewGroup.LayoutParams(-2, -2);
        int i1 = localLayoutParams.width;
        int i2;
        if (i1 > 0)
            i2 = View.MeasureSpec.makeMeasureSpec(i1, 1073741824);
        else
            i2 = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int i3 = localLayoutParams.height;
        int i4;
        if (i3 > 0)
            i4 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        else
            i4 = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        paramView.measure(i2, i4);
    }

    /**
     * <p>
     * 重新布局比例尺控件
     * </p>
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    private void onLayoutScaleBar(boolean changed, int l, int t, int r, int b) {
        // Log.d(LOG_TAG, "onLayoutScaleBar b:" + b + ",t:" + t);
        if ((this.showScale) && (this.scaleView.getParent() != null)) {
            checkView(this.scaleView);
            int w = this.scaleView.getMeasuredWidth();
            int h = this.scaleView.getMeasuredHeight();
            if ((this.scaleViewX == -2147483648) || (this.scaleViewY == -2147483648)) {
                this.scaleView.layout(5, b - t - h - 56, 5 + w, b - t - 56);
            } else {
                int i1 = getMapWidth();
                int i2 = getMapHeight() - 30;
                if (scaleViewY > i2 || scaleViewY < 1) {
                    scaleViewY = i2;
                }
                if (scaleViewX > i1 || scaleViewX < 1) {
                    scaleViewX = 5;
                }
                // Log.d(LOG_TAG, "onLayoutScaleBar scaleViewX:" + scaleViewX + ",scaleViewY:" + scaleViewY);
                this.scaleView.layout(this.scaleViewX, this.scaleViewY, this.scaleViewX + w, this.scaleViewY + h);
            }
        }
    }


    //不同单位长度的两种计算当前地图分辨率方式，默认为米true
    public void isMeter(boolean ism){
        isMeter=ism;
    }


    /**
     * <p>
     * 修改比例尺控件的信息和长度
     * </p>
     */
    void updateScaleBar() {
        if (this.showScale) {
            int mScaleMaxWidth = getMapWidth() >> 2;// mScaleMaxWidth = 默认屏幕宽度四分之一
            // Point fromPoint = getProjection().getProjectionUtil().getGlobalFromScreen(5, 5, null);
            // Point toPoint = getProjection().getProjectionUtil().getGlobalFromScreen(5 + mScaleMaxWidth, 5, null);
            // 通过getDistance函数得出两点间的真实距离
            // double distance = getDistance();
            // 通过getResolutionByLat函数得出当前分辨率，再求出屏幕四分之一宽度所对应的真实距离
            double distance = (getMapWidth() >> 2) * getResolutionByLat(isMeter);
            // Log.d(LOG_TAG, "updateScaleBar distance:" + distance+",getDistance():"+getDistance());
            String discripition = null;
            int dis = 0;
            int width = 20;
            // 真实距离和数组中相近的两个值循环比较，以小值为准，得出规定好的比例尺数值赋值给dis
            for (int j = 1; j < SCALE.length; j++) {
                if (SCALE[j - 1] <= distance && distance < SCALE[j]) {
                    dis = SCALE[j - 1];
                    break;
                }
            }
            if (distance > SCALE[SCALE.length - 1]) {
                dis = SCALE[SCALE.length - 1];
            }
            if (distance < SCALE[0]) {
                dis = SCALE[0];
            }
            // 比例尺黑条的宽度 =（dis*默认屏幕宽度四分之一）/真实的距离
            width = (int) (dis * mScaleMaxWidth / distance);
            if (width>400)
                width=180;

            // 如果比例尺数值大于1000，则discripition = 2500 km（公里），否则 为 900 m（米）
            if (dis >= 1000) {
                discripition = dis / 1000 + "km";
            } else {
                discripition = dis + "m";
            }
            // Log.d(LOG_TAG, "updateScaleBar scaleImageView width:" + width + ",scaleTextView text:" + discripition);
            // 设置TextView组件要显示的比例尺数值
            scaleTextView.setText(discripition);
            // 设置比例尺黑条ImageView的的长度
            android.view.ViewGroup.LayoutParams lp = scaleImageView.getLayoutParams();
            lp.width = width;
            scaleImageView.setLayoutParams(lp);
        }
    }

    /**
     * <p>
     * 获取当前分辨率下屏幕四分之一宽度所对应的真实距离，没有考虑纬度不同，半径不同，以赤道半径的大小计算
     * </p>
     * @return
     */
    private double getDistance() {
        double res = getRealResolution();
        if (isMultiTouchScale) {
            res = getResolution();
        }
        // double lon = this.centerGeoPoint.x - (getMapWidth() >> 2) * res / prjCoordSysRadio;
        // Point2D p2d = new Point2D(lon, centerGeoPoint.y);
        return (getMapWidth() >> 2) * res / this.getDensity();
    }

    /**
     * <p>
     * 获取当前分辨率，考虑纬度不同，半径不同，当前分辨率也不同，上下平移分辨率发生变化，往南北两极分辨率逐渐变小
     * </p>
     * @param isMeter
     * @return
     * @since 6.1.3
     */
    private double getResolutionByLat(boolean isMeter) {
        double res = getRealResolution();
        if (isMultiTouchScale) {
            res = getResolution();
        }
        res = res / this.getDensity();
        double toRadians = 0.017453292519943295769236907684886;
        double earthRadius = 6378137;
        double mercatorLatitudeLimit = 85.051128;
        if (isMeter) {
            double lat = this.centerGeoPoint.y / (earthRadius * Math.PI) * 180.0;
            lat = 180 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180.0)) - Math.PI / 2);
            double radian = Math.min(mercatorLatitudeLimit, Math.abs(lat)) * Math.PI / 180;
            double curRadius = Math.cos(radian) * earthRadius;
            return (curRadius * Math.PI * 2) * (res / (Math.PI * 2 * earthRadius));
        } else {
            double degreeDist = earthRadius * toRadians;
            double y = Math.min(Math.abs(this.centerGeoPoint.y), mercatorLatitudeLimit);
            return Math.cos(y * toRadians) * res * degreeDist;
        }
    }

    float getDensity() {
        return density;
    }

    /**
     * <p>
     * 专门为ZoomAnimator进行缩放动画时每一步动画时设置相应的缩放大小和缩放中心点
     * </p>
     * @param zoomScale 缩放大小
     * @param focusX 缩放中心点x值
     * @param focusY 缩放中心点y值
     * @since 7.0.0
     */
    protected void setScaleByZoom(float zoomScale, int focusX, int focusY) {
        isZoomScale = true;
        this.zoomScale = zoomScale;
        this.scalePoint.x = (int) focusX;
        this.scalePoint.y = (int) focusY;
//        moved();
    }

    /*public void setBaseLayer(LayerView baseLayer) {
        this.baseLayer = baseLayer;
        resetMapProjectionAndURL(baseLayer);
    }*/

    /*private void resetMapProjectionAndURL(LayerView lv) {
        if (lv != null) {
            this.curMapUrl = lv.getCurrentMapURL();
    //            if (lv.getProjection() instanceof RotatableProjection) {
    //                this.rotatableProjection = (RotatableProjection) lv.getProjection();
    //            }
        }
    }*/
}