package com.supermap.imobilelite.maps;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

/**
 * <p>
 * 图层视图，作为地图视图的子项添加到 {@link MapView} 中显示。
 * </p>
 * <p>
 * 图层视图用于显示 SuperMap iServer Java 6R 的 REST 地图服务提供的地图图层。必设属性为地图的 url。
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
public class LayerView extends AbstractTileLayerView {
    BoundingBox boundingBox=null;
    Point2D a = null;
//    Point2D LeftTop,RightBottom;
    String itop,iright,ileft,ibottom;
    private static final String LOG_TAG = "com.supermap.android.maps.layerview";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private static final float GUARANTEE_TILELOAD_PERCENTAGE = 0.5F;
    // 定义图层初始化状态码，与其他状态码区别
    private static final int INITIALIZED = 81;
    private static final int INITIALIZED_FAILED = 82;
    private static final int LAYER_REFRESH = 83;
    // private static final String LAYERSTATUS = "LayerStatus_";
    // private RestMapTileFactory tileFactory;
    private Bitmap loadingTile;
    private int topMargin = 0;
    private int bottomMargin = 0;
    private int rightMargin = 0;
    private int leftMargin = 0;
    // private Rect visibleRect = new Rect();//暂不支持
    private boolean transparent = true;
    private String curMapUrlEncoded = "";// 经过编码的url
    // private double minScale;
    private Timer time = null;
    // 图层的默认状态
    private JSONObject mapStatusParameter;
    private String prjCoordSysType = "";
    private Handler layerStatusChangeHandler = null;
    private boolean cacheEnabled = true;
    // 是否启用地图状态信息缓存
    // private boolean cacheMapStatusEnabled = false;
    // 子图层的id，用于构建专题图图层
    private String layersID;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     */
    public LayerView(Context context) {
        super(context);
        // initialize(context);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param attrs 属性信息。
     */
    public LayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // initialize(context);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param attrs 属性信息。
     * @param defStyle 风格标识。
     */
    public LayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // initialize(context);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param mapUrl 地图服务的 url。
     */
    public LayerView(Context context, String mapUrl) {
        super(context);
        // initialize(context);
        this.setURL(mapUrl);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param context Activity的上下文。
     * @param mapUrl 地图服务的 url。
     * @param epsgCode 设置动态投影的目标投影坐标系的 epsgCode。
     */
    public LayerView(Context context, String mapUrl, int epsgCode) {
        super(context);
        // initialize(context);
        if (epsgCode > 0) {
            this.crs = new CoordinateReferenceSystem();
            this.crs.wkid = epsgCode;
        }
        this.setURL(mapUrl);
    }

    // private void initialize(Context context) {
    // /*if (!(context instanceof MapActivity)) {
    // throw new IllegalArgumentException("MapView are restricted being created inside of a MapActivity class, you need to subclass MapAvtivity");
    // }*/
    // if (context == null) {
    // throw new IllegalArgumentException("context 为空。");
    // }
    // this.context = context;
    // this.defaultTilePaint.setDither(true);
    // this.defaultTilePaint.setFilterBitmap(true);
    // this.customTilePaint.setDither(true);
    // this.customTilePaint.setFilterBitmap(true);
    // }

    /**
     * 初始化 tileFactory 对象。ProjectionUtil 对象需要 mapView 的范围信息，所以添加图层后调用。
     */
    // void initTileFactory() {
    // // if (this.mapView != null && this.mapView.getTileCacher() != null) {
    // // this.tileCacher = this.mapView.getTileCacher();
    // // } else {
    // // this.tileCacher = new TileCacher(context);
    // // }
    // // this.tileProvider = new ThreadBasedTileDownloader(this, this.tileCacher);
    // // if(tileFactory==null){
    // // this.tileFactory = new RestMapTileFactory(this);// 必须先创建，设置setBaseUrl和setMinScale时需要
    // // }
    // this.tileFactory = new RestMapTileFactory(this);
    // this.tileFactory.setBaseUrl(this.curMapUrlEncoded);
    // this.tileFactory.setProjection(new ProjectionUtil(this));// 必须先初始化mapBounds和layerBounds后构造ProjectionUtil对象。--huangqh
    // this.projection = new Projection(this, this.tileFactory.getProjection());
    // }

    // private TileDownloader getTileProvider() {
    // return this.mapView.getTileProvider();
    // }

    //
    // public void setTileProvider(TileDownloader tProvider) {
    // tileProvider = tProvider;
    // }

    // private void initZoomlevel() {
    // if (this.mapView == null) {
    // return;
    // }
    // zoomLevel = this.mapView.getZoomLevel();
    // }

    // double getBoundsWidth() {
    // return Math.abs(this.boundsRight - this.boundsLeft);
    // }
    //
    // double getBoundsHeight() {
    // return Math.abs(this.boundsTop - this.boundsBottom);
    // }
    //
    // public double getBoundsLeft() {
    // return this.boundsLeft;
    // }
    //
    // public double getBoundsRight() {
    // return this.boundsRight;
    // }
    //
    // public double getBoundsTop() {
    // return this.boundsTop;
    // }
    //
    // public double getBoundsBottom() {
    // return this.boundsBottom;
    // }

    /**
     * <p>
     * 设置当前地图的 url，注意 url 不能进行任何编码。
     * </p>
     * @param mapUrl 地图服务的 url。
     */
    public void setURL(String mapUrl) {
        isLayerInited = false;
        this.curMapUrl = mapUrl;
        this.curMapUrlEncoded = this.getFormateMapURL(this.curMapUrl);
        if (isEmpty(this.curMapUrlEncoded)) {
            if (isEmpty(this.curMapUrl)) {
                Log.w(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_SETURL_URLILLEGAL));
            } else {
                this.curMapUrlEncoded = this.curMapUrl;
            }
        }
        // Log.d(LOG_TAG, "编码后的地图服务 url为:" + curMapUrlEncoded);
        /* String urlChecked = this.getFormateMapURL(mapUrl);
         this.curMapUrl = urlChecked;
         if (isEmpty(mapUrl)) {
             return;
         }*/
        /*// 初始化图层的名字标签
        if (urlChecked != null && urlChecked.contains("/")) {
            try {
                this.layerName = URLDecoder.decode(urlChecked.substring(urlChecked.lastIndexOf("/") + 1), "utf-8");
            } catch (UnsupportedEncodingException e) {
                this.layerName = urlChecked.substring(urlChecked.lastIndexOf("/") + 1);
            }
        }*/
        // todo 当没有setBounds时 使用
        // if (this.tileFactory instanceof RestMapTileFactory) {
        // RestMapTileFactory tmpFactory = (RestMapTileFactory) this.tileFactory;
        // tmpFactory.setProjection(new ProjectionUtil(this));
        // this.rotatableProjection = new RotatableProjection(this, tmpFactory.getProjection());
        // }
        // this.rotatableProjection = new RotatableProjection(this, this.tileFactory.getProjection());
        // 初始化地图默认状态参数
        // initMapStatusParameter(urlChecked);
        // if (isDynamicProjection) {//即使用了动态投影的话自定义比例尺数组也有，所以不需要重新初始化
        // 初始化地图是否设置了固定比例尺
        // initVisibleScales(url);//不考虑地图本身是否设置了固定比例尺，因为即使设置了也可以用其他比例尺正确出图
        // }
        // updateMapStatus(true, true);
        // new UpdateLayerViewStatuThread(false, true, false).start();

        if (this.mapView != null) {
            // 说明是后期更改了地图的服务地址,这里不判断是否baseLayer是因为要更新当前View的状态及一些基本的变量
            new UpdateLayerViewStatuThread(true, true).start();
        }
    }



    /**
     * <p>
     * 设置当前图层的坐标参考系。
     * </p>
     * @param crs 要显示的坐标参考系对象。
     */
    public void setCRS(CoordinateReferenceSystem crs) {
        if (crs == null) {
            return;
        }

        if (this.crs == null) {
            this.crs = crs;
            // isGCSLayer预处理
            this.isGCSLayer = Util.isGCSCoordSys(this.crs);
            // 理论上以下调用无效，因为不可能有了url但是没有crs
            if (this.mapView != null) {
                // 后期修改CRS
                if (this.mapView.getBaseLayer() == this) {
                    new UpdateLayerViewStatuThread(true, false).start();
                } else {
                    new UpdateLayerViewStatuThread(false, false).start();
                }
            }
        } else {
            if (this.crs.equals(crs)) {
                return;
            } else if (!("PCS_NON_EARTH".equalsIgnoreCase(this.prjCoordSysType))) {
                this.crs = crs;
                if (this.mapView != null) {
                    // 后期修改CRS
                    if (this.mapView.getBaseLayer() == this) {
                        new UpdateLayerViewStatuThread(true, false).start();
                    } else {
                        new UpdateLayerViewStatuThread(false, false).start();
                    }
                }
            }
        }
    }

    /**
     * <p>
     * 设置地图地理范围。
     * </p>
     * <p>
     * 设置地图地理范围后出图时将按照用户设置的范围进行出图，而不去请求服务器上的地图参数。
     * </p>
     * @param left 出图范围左侧坐标。
     * @param bottom 出图范围底部坐标。
     * @param right 出图范围右侧坐标。
     * @param top 出图范围顶部坐标。
     */
    public void setBounds(double left, double bottom, double right, double top) {
        // 动态图层可以不支持该接口，因为在发送请求后初始化bounds，设置了可能反而不正确
        if (left < right && bottom < top) {
            // 可能没有初始化SQLite缓存需要的比例尺、分辨率乘积信息
            this.layerBounds = new BoundingBox(new Point2D(left, top), new Point2D(right, bottom));
            // this.isLayerInited = true;
            if (this.crs == null) {
                if (!(left >= -181 && right <= 181 && bottom >= -91 && top <= 91)) {
                    isGCSLayer = false;
                }
            }
            if (mapView.getBaseLayer() == this) {
                updateMapStatus(true, false);
            }
        } else {
            this.isLayerInited = false;
            throw new IllegalArgumentException("参数不合法。");
        }
    }

    private void updateMapStatus(boolean clearBoundsInfo, boolean clearScalesInfo) {
        if (this.mapView != null) {
            if (mapView.isDetroy) {
                return;
            }
            if (this.mapView.getBaseLayer() == this) {
                // 如果已经添加到MapView,并且是baseLayer则更新地图状态
                this.mapView.updateMapStatus(this, clearBoundsInfo, clearScalesInfo);
            } else {
                this.mapView.updateMapStatus(this, false, clearScalesInfo);
            }
        }
    }

    /**
     * <p>
     * 获取是否使用服务器端缓存。
     * </p>
     * @return true表示使用，false表示不使用。
     */
    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    /**
     * <p>
     * 设置是否使用服务器端缓存。
     * </p>
     * @param cacheEnabled 是否使用服务器缓存。
     */
    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }


    /**
     * <p>
     * 获取当前图层的坐标参考系。
     * </p>
     * @return 当前图层的坐标参考系。
     */
    // public CoordinateReferenceSystem getCRS() {
    // /*if (!this.isLayerInited) {
    // throw new IllegalStateException("图层没有初始化。");
    // }*/
    // return this.crs;
    // }

    private static boolean isEmpty(String stringForCheck) {
        if (stringForCheck != null && stringForCheck.trim().length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * 初始化地图参数，不能在主线程中调用。
     * </p>
     * @param url
     */
    void initMapStatusParameter() {
        this.isLayerInited = false;
        if (this.context.getMainLooper().getThread() == Thread.currentThread()) {
            Log.w(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_USEWEB));
            // return;
        }
        if (isEmpty(this.curMapUrlEncoded)) {
            Message message = Message.obtain();
            message.what = INITIALIZED_FAILED;
            message.getData().putString("description", resource.getMessage(MapCommon.LAYERVIEW_URLILLEGAL));
            sendMessage(message);
            return;
        }
        try {
            String getMapStatusURL = this.curMapUrlEncoded + ".json";
            if (this.crs != null && this.crs.wkid > 0) {
                getMapStatusURL += "?prjCoordSys=%7B%22epsgCode%22%3A" + this.crs.wkid + "%7D";
                // isDynamicProjection = true;
            } /*else {
                // isDynamicProjection = false;
              }*/
            // setMapStatusParameter(Util.getJSON(getMapStatusURL));
            // 为了做到动态图层也能支持离线
            // if (this.isCacheMapStatusEnabled()) {
            // // 启用地图状态信息缓存，存在缓存无需发送请求获取地图状态信息，不存在还是发送请求获取
            // this.mapStatusParameter = getSavedMapStatus();
            // if (this.mapStatusParameter == null) {
            // this.mapStatusParameter = Util.getJSON(getMapStatusURL);
            // saveMapStatusToPreferences(this.mapStatusParameter);
            // }
            // } else {
            // // 默认模式，不启用地图状态信息缓存，发送请求获取地图状态信息并保存或更新地图缓存信息
            // this.mapStatusParameter = Util.getJSON(getMapStatusURL);
            // saveMapStatusToPreferences(this.mapStatusParameter);
            // }
            if (Credential.CREDENTIAL != null) {
                if (getMapStatusURL.contains("?prjCoordSys=")) {
                    getMapStatusURL = getMapStatusURL + "&" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
                } else {
                    getMapStatusURL = getMapStatusURL + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
                }
            }
            this.mapStatusParameter = Util.getJSON(getMapStatusURL);
            if (this.mapStatusParameter != null) {
                JSONObject prjCoordSys = this.mapStatusParameter.getJSONObject("prjCoordSys");
                if (this.crs == null) {
                    this.crs = new CoordinateReferenceSystem();
                }
                this.crs.wkid = prjCoordSys.getInt("epsgCode");
                this.crs.unit = prjCoordSys.getString("coordUnit");
                this.prjCoordSysType = prjCoordSys.getString("type");
                boolean isUseDefinedGCS = false;
                if (!"PCS_NON_EARTH".equals(prjCoordSysType)) {
                    // 不是平面坐标系则要初始化长半轴
                    try {
                        JSONObject coordSystem = prjCoordSys.getJSONObject("coordSystem");
                        if (coordSystem != null) {
                            Object projection = prjCoordSys.get("projection");
                            if (projection == null) {
                                isUseDefinedGCS = true;
                            }
                            JSONObject datum = coordSystem.getJSONObject("datum");
                            JSONObject spheroid = datum.getJSONObject("spheroid");
                            this.crs.datumAxis = spheroid.getDouble("axis");
                            this.crs.flatten = spheroid.getDouble("flatten");

                        }
                    } catch (Exception ex) {
                        Log.w(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_INITMAPSTATUSPARAMETER_GETPRJ_ERROR), ex);
                        this.crs.datumAxis = 6378137d;
                        this.crs.flatten = 1 / 298.257223563;
                    }
                }
                JSONObject viewBounds = this.mapStatusParameter.getJSONObject("viewBounds");
                double scale = this.mapStatusParameter.getDouble("scale");
                if (viewBounds != null && scale > 0) {
                    double viewBoundsRight = viewBounds.getDouble("right");
                    double viewBoundsLeft = viewBounds.getDouble("left");
                    double viewBoundsWidth = Math.abs(viewBoundsRight - viewBoundsLeft);
                    // TODO 如何确定是否为自定义投影坐标系或者自定义经纬度坐标系
                    if (Util.isGCSCoordSys(this.crs) || isUseDefinedGCS) {
                        isGCSLayer = true;
                        // 使用mbtiles缓存的时候无需把度转化成米，分辨率的单位跟地图单位保持一致
                        // double radius = this.crs.datumAxis > 1d ? this.crs.datumAxis : Constants.DEFAULT_AXIS;
                        // viewBoundsWidth = viewBoundsWidth * Math.PI * radius / 180.0;
                    } else {
                        isGCSLayer = false;
                    }
                    // 初始化dpi
                    double defaultResolution = 0d;
                    if (isGCSLayer) {
                        double radius = this.getCRS().datumAxis > 1d ? this.getCRS().datumAxis : 6378137d;
                        defaultResolution = viewBoundsWidth * Math.PI * radius / (180.0 * 256.0);
                    } else {
                        defaultResolution = viewBoundsWidth / 256.0;
                    }
                    dpi = defaultResolution * scale;
                    // 初始化完dpi就可以初始化scales和resolutions，保证resolutions被初始化
                    initResolutionsAndScales(true, true);
                    SqliteTileSourceFactory.getInstance().setLayerResolutionInfo(this.layerName, scale, viewBoundsWidth / 256.0);
                }

                double ml = getBoundsItem(this.mapStatusParameter, "left");
                double mb = getBoundsItem(this.mapStatusParameter, "bottom");
                double mr = getBoundsItem(this.mapStatusParameter, "right");
                double mt = getBoundsItem(this.mapStatusParameter, "top");
                if (layerBounds == null) {
                    this.layerBounds = new BoundingBox(new Point2D(ml, mt), new Point2D(mr, mb));
                }
                // initTileFactory();
                // 兼容低版本的iserver，如610版本地图状态没有visibleScalesEnabled和visibleScales字段，所以在获取之前先判断有再获取
                if (this.mapStatusParameter.has("visibleScalesEnabled")) {
                    boolean visibleScalesEnable = this.mapStatusParameter.getBoolean("visibleScalesEnabled");
                    if (visibleScalesEnable) {
                        JSONArray ja = this.mapStatusParameter.getJSONArray("visibleScales");
                        if (ja != null && ja.length() > 0) {
                            double scales[] = new double[ja.length()];
                            for (int i = 0; i < ja.length(); i++) {
                                scales[i] = ja.getDouble(i);
                            }
                            if (this.visibleScales == null || this.visibleScales.length < 1) {
                                this.setScales(scales);
                            }
                        }
                    }
                }

            } else {
                Log.i(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_MAPPARAM_NULL));
                Message message = Message.obtain();
                message.what = INITIALIZED_FAILED;
                message.getData().putString("description", resource.getMessage(MapCommon.LAYERVIEW_MAPPARAM_NULL));
                sendMessage(message);
                return;
            }
            if (!this.isLayerInited) {
                this.isLayerInited = true;
                Message message = Message.obtain();
                message.what = INITIALIZED;
                sendMessage(message);
            } else {
                // 调用setCRS后刷新地图状态。
                this.isLayerInited = true;
                Message message = Message.obtain();
                message.what = LAYER_REFRESH;
                sendMessage(message);
            }
        } catch (Exception e) {
            this.isLayerInited = false;
            Log.w(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_MAPPARAMINIT_FAIL));
            Log.d(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_MAPPARAMINIT_FAIL_DETAIL, e.getMessage()));
            Message message = Message.obtain();
            message.what = INITIALIZED_FAILED;
            message.getData().putString("description", e.getMessage());
            sendMessage(message);
        }
    }

    /**
     * <p>
     * 保存地图状态信息到SharedPreferences中去
     * </p>
     * @param mapStatusParameter
     */
    // private void saveMapStatusToPreferences(JSONObject mapStatusParameter) {
    // if (mapStatusParameter != null) {
    // SharedPreferences preferences = this.context.getSharedPreferences(LAYERSTATUS + layerName, Context.MODE_PRIVATE);
    // Editor editor = preferences.edit();
    // editor.putString(LAYERSTATUS + layerName, this.mapStatusParameter.toString());
    // editor.commit();
    // }
    // }

    /**
     * <p>
     * 获取地图状态信息缓存
     * </p>
     * @return
     */
    // private JSONObject getSavedMapStatus() {
    // String mapStatus = getMapStatusFromPreferences();
    // if (StringUtils.isEmpty(mapStatus)) {
    // return null;
    // } else {
    // try {
    // return new JSONObject(mapStatus);
    // } catch (JSONException e) {
    // return null;
    // }
    // }
    // }

    /**
     * <p>
     * 从SharedPreferences中获取保存地图状态信息
     * </p>
     * @return
     */
    // private String getMapStatusFromPreferences() {
    // SharedPreferences sharedPreferences = this.context.getSharedPreferences(LAYERSTATUS + layerName, Context.MODE_PRIVATE);
    // return sharedPreferences.getString(LAYERSTATUS + layerName, "");
    // }

    private void sendMessage(Message message) {
        if (this.layerStatusChangeHandler != null) {
            if (Util.checkIfSameThread(this.layerStatusChangeHandler)) {
                this.layerStatusChangeHandler.dispatchMessage(message);
            } else {
                this.layerStatusChangeHandler.sendMessage(message);
            }
        }
    }

    // String getLayerName() {
    // return this.layerName;
    // }

    private double getBoundsItem(JSONObject mapJSON, String itemName) throws JSONException {
        double result = 0.0;
        JSONObject bounds = mapJSON.getJSONObject("bounds");
        result = bounds.getDouble(itemName);
        return result;
    }

    /**
     * 获取MapView中当前图层编码后的 url 地址。
     * @return 返回当前图层编码后的 url 地址。
     */
    @Override
    public String getURL() {
        return this.curMapUrlEncoded;
    }

    JSONObject getMapStatusParameter() {
        return mapStatusParameter;
    }

    /**
     * <p>
     * 返回图层当前分辨率。
     * </p>
     * @return 图层当前分辨率。
     */
    // public double getResolution() {
    // return getMapView().getResolution();
    // }

    /**
     * <p>
     * 返回图层当前比例尺。
     * </p>
     * @return 图层当前比例尺。
     */
    // public double getScale() {
    // return getMapView().getScale();
    // }

    /**
     * <p>
     * 返回当前图层是否可见。
     * </p>
     * @return 当前图层是否可见。
     */
    // public boolean isVisible() {
    // return visible;
    // }

    /**
     * <p>
     * 设置当前图层是否可见。
     * </p>
     * @param visible 当前图层是否可见。
     */
    // public void setVisible(boolean visible) {
    // this.visible = visible;
    // }

    /*public Map getMap() {// 应该不需要了
        RestMapProviderSetting restMapSetting = new RestMapProviderSetting();
        restMapSetting.restServiceRootURL = getCurMapRestServiceURL();
        com.supermap.services.components.spi.MapProvider rmp = new RestMapProvider(restMapSetting);
        List<com.supermap.services.components.spi.MapProvider> sps = new ArrayList<com.supermap.services.components.spi.MapProvider>();
        sps.add(rmp);
        MapContext context = new MapContext();
        context.setProviders(sps);
        Map mapSC = new MapImpl(context);
        return mapSC;
    }
    
    private String getCurMapRestServiceURL() {// 应该不需要了
        if (!curMapUrl.equals("")) {
            int tmpIndex = this.curMapUrl.lastIndexOf("/");
            String tmpStr = this.curMapUrl.substring(0, tmpIndex);
            tmpIndex = tmpStr.lastIndexOf("/");
            return tmpStr.substring(0, tmpIndex);
        }
        return "";
    }*/

    private String getFormateMapURL(String tempMapURL) {
        if (tempMapURL != null && tempMapURL.trim().length() > 0) {
            tempMapURL = tempMapURL.trim();
            while (tempMapURL.endsWith("/")) {
                tempMapURL = tempMapURL.substring(0, tempMapURL.length() - 2);
            }
            if (tempMapURL.contains("/")) {
                String mapName = tempMapURL.substring(tempMapURL.lastIndexOf('/') + 1);
                this.layerName = mapName;
                try {
                    mapName = URLEncoder.encode(mapName, Constants.UTF8);// 对地图名进行编码
                    // Log.d(LOG_TAG, "编码后mapName:" + mapName);
                } catch (UnsupportedEncodingException e) {
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_GETFORMATEMAPURL_ENCODEERROR, e.getMessage()));
                }
                tempMapURL = tempMapURL.substring(0, tempMapURL.lastIndexOf('/') + 1);
                tempMapURL = tempMapURL + mapName;
            }
            return tempMapURL;
        }
        return "";
    }

    /**
     * <p>
     * 启动定时清除缓存定时器，仅清除客户端缓存，不清除服务器端的缓存。
     * </p>
     * @param minute 清除缓存的时间间隔，单位为分钟。
     */
    public void startClearCacheTimer(int minute) {
        // startClearCacheTimer(minute, true);
        // 第二个参数默认为false，即不清除服务器端的缓存
        startClearCacheTimer(minute, false);
    }

    /**
     * <p>
     * 启动定时清除缓存定时器，并根据 clearServerCache 的值判断是否定时清除服务器端的缓存。
     * </p>
     * @param minute 清除缓存的时间间隔，单位为分钟。
     * @param clearServerCache 是否定时清除服务器端的缓存。
     */
    public void startClearCacheTimer(int minute, boolean clearServerCache) {
        if (minute <= 0) {
            Log.w(LOG_TAG, "清除缓存的时间间隔必须大于0");
            return;
        }
        stopClearCacheTimer();
        time = new Timer("ClearCacheTimer", true);
        ClearCacheTask task = new ClearCacheTask(clearServerCache);
        time.schedule(task, 0, minute * 60000L);
    }

    private class ClearCacheTask extends TimerTask {
        private boolean clearServerCache = false;

        public ClearCacheTask(boolean isClearServerCache) {
            super();
            clearServerCache = isClearServerCache;
        }

        public void run() {
            clearCache(clearServerCache);
            // 清除缓存后刷新 added by zhouxu 2013/2/4
            postInvalidate();
        }
    }

    /**
     * <p>
     * 停止和销毁清除缓存的定时器。
     * </p>
     */
    public void stopClearCacheTimer() {
        if (time != null) {
            time.cancel();
            time = null;
        }
    }

    /**
     * <p>
     * 清除本地缓存以及根据 clearServerCache 的值判断是否清除服务器中的缓存，只有在必要的时候设置clearServerCache为true来清除服务器缓存，因为会影响服务器的缓存。
     * </p>
     * @param clearServerCache 是否定时清除服务器中的缓存。
     */
    public void clearCache(boolean clearServerCache) {
        clearTilesInMemory();
        clearTilesInDB();
        if (clearServerCache && !isEmpty(this.curMapUrlEncoded)) {
            if (getContext().getApplicationContext().getMainLooper().getThread() == Thread.currentThread()) {
                new Thread(new Runnable() {
                    public void run() {
                        Util.clearCache(LayerView.this.curMapUrlEncoded);
                    }
                }).start();
            } else {
                Util.clearCache(this.curMapUrlEncoded);
            }
        }
    }

    /**
     * <p>
     * 获取图层显示时的固定比例尺数组。
     * </p>
     * @return 图层显示时的固定比例尺数组。
     */
    // public double[] getScales() {
    // return visibleScales;
    // }

    /**
     * <p>
     * 设置图层显示时的固定比例尺数组。
     * </p>
     * <p>
     * 此设置的固定比例尺优先于地图本身的固定比例尺。若不设置，则以地图本身设置的固定比例尺为准;若地图无固定比例尺，则按照缩放级别进行地图的缩放。
     * </p>
     * @param visibleScales 图层显示时的固定比例尺数组。
     */
    public void setScales(double[] visibleScales) {
        if (visibleScales != null && visibleScales.length > 0) {
            this.visibleScales = Tool.getValibScales(visibleScales);
            initResolutionsAndScales(true, false);
            updateMapStatus(false, true);
        }
    }

    public double[] getScales() {
        if (visibleScales == null || visibleScales.length < 1) {
            initResolutionsAndScales(false, true);
        }
        return visibleScales;
    }

    /**
     * <p>
     * 获取子图层的id，用于构建专题图图层
     * </p>
     * @return
     */
    public String getLayersID() {
        return layersID;
    }

    /**
     * <p>
     * 设置子图层的id，用于构建专题图图层及临时图层等。
     * 获取进行切片的地图图层 ID，即指定进行地图切片的图层，可以是临时图层集，也可以是当前地图中图层的组合。如果此参数缺省则对全部图层进行切片。
     * </p>
     * <p>
     * layersID 可以是临时图层创建时 templayers 的 ID，如 layersID=382139acf0，也可以是当前地图中的某些图层的 ID 编号。
     * 其中，当前地图图层 ID 的定义规则如下：
     * 1. 各级图层按照图层顺序自上而下从0开始编号；
     * 2. 冒号（:）前为顶级图层；
     * 3. 英文句号（.）表示其他各级图层间的从属关系；
     * 4. 英文逗号（,）表示图层间的分隔。
     * </p>
     * <p>
     * 例如：
     * 其中，当前地图图层 ID 的定义规则如下：
     * 1. [0:0,1,2.0]表示顶级图层0下面的子图层：0、1及其下属所有子图层，和2下的子图层0；
     * 2. [1:1.2,2]表示顶级级图层1下面的子图层：1下的子图层2，和图层2及其下属所有子图层；
     * 3. 两个示例合并在一起则是：[0:0,1,2.0,1:1.2,2]
     * 此外，[0,1,2,3]表示顶级图层0下面的图层0、1、2、3及所有子图层，[0:,1:,2:]表示顶级图层0、1、2及其所有子图层。
     *</p>
     * @param layersID
     */
    public void setLayersID(String layersID) {
        this.layersID = layersID;
    }

    /**
     * <p>
     * 重写该接口，考虑子图层作为动态图层时，区别跟父图层的缓存不一样，考虑子图层id
     * </p>
     * @return
     */
    @Override
    String getLayerCacheFileName() {
        if (StringUtils.isEmpty(this.layerCacheFileName)) {
            if (StringUtils.isEmpty(this.layersID)) {
                return this.getLayerName();
            } else {
                return this.getLayerName() + "_" + this.layersID.hashCode();
            }
        } else {
            return this.layerCacheFileName;
        }
    }

    /**
     * <p>
     * 初始化resolutions和scales，该初始化依赖于dpi的初始化，如果图层设置了固定比例尺，必须保证初始化resolutions，因为需要依赖resolutions来出图。
     * </p>
     * @param isInitResolutions 分辨率是否初始化。
     * @param isInitScales 比例尺是否初始化。
     */
    protected void initResolutionsAndScales(boolean isInitResolutions, boolean isInitScales) {
        if (dpi == -1) {
            return;
        }
        if (isInitResolutions && visibleScales != null && visibleScales.length > 0) {
            resolutions = new double[visibleScales.length];
            for (int i = 0; i < visibleScales.length; i++) {
                resolutions[i] = dpi / visibleScales[i];
            }
        }
        if (isInitScales && resolutions != null && resolutions.length > 0) {
            visibleScales = new double[resolutions.length];
            for (int i = 0; i < resolutions.length; i++) {
                visibleScales[i] = dpi / resolutions[i];
            }
        }
    }

    /**
     * <p>
     * 设置图层的分辨率数组。
     * </p>
     * @param resolutions 图层的分辨率数组。
     */
    public void setResolutions(double[] resolutions) {
        if (resolutions != null && resolutions.length > 0) {
            this.resolutions = Tool.getResolutions(resolutions);
            initResolutionsAndScales(false, true);
            updateMapStatus(false, true);
        }
    }

    /**
     * <p>
     * 获取图层的分辨率数组。
     * </p>
     * @return 图层的分辨率数组。
     */
    public double[] getResolutions() {
        if (resolutions == null || resolutions.length < 1) {
            initResolutionsAndScales(true, false);
        }
        return this.resolutions;
    }

    // public boolean isVisibleScalesEnabled() {
    // return visibleScalesEnabled;
    // }
    //
    // public void setVisibleScalesEnabled(boolean visibleScalesEnabled) {
    // if(!isMapVScalesEnabled){
    // this.visibleScalesEnabled = visibleScalesEnabled;
    // }
    //
    // }
    //
    // /**
    // * 获取地图本身是否设置了固定比例尺
    // * @return
    // */
    // public boolean isMapVScalesEnabled() {
    // return isMapVScalesEnabled;
    // }
    //
    // /**
    // * 判断地图本身是否设置了固定比例尺
    // * @param isMapVScalesEnabled
    // */
    // private void setMapVScalesEnabled(boolean isMapVScalesEnabled) {
    // this.isMapVScalesEnabled = isMapVScalesEnabled;
    // }

    /**
     * 提供设置零级比例尺scale方法,例如0.000001形式
     * 
     * @param scale
     */
    /*public void setMinScale(double scale) {
        // this.tileFactory.setBaseScale(scale);
        this.minScale = scale;
    }
    
    public double getMinScale() {
        return this.minScale;
    }*/

    // /**
    // * 获取当前LayerView中是否是普通平面坐标系
    // * @return
    // */
    // private boolean isPCSNonEarth() {
    // return isPCSNonEarth;
    // }
    //
    // /**
    // * 设置当前LayerView是否是普通平面坐标系
    // * @param isPCSNonEarth
    // */
    // private void setPCSNonEarth(boolean isPCSNonEarth) {
    // this.isPCSNonEarth = isPCSNonEarth;
    // }
    //
    // private boolean isMercatorPro() {
    // return isMercatorPro;
    // }
    //
    // private void setMercatorPro(boolean isMercatorPro) {
    // this.isMercatorPro = isMercatorPro;
    // if (isMercatorPro) {
    // Log.d(LOG_TAG, "Current map is MercatorProjection!");
    // }
    // }
    //
    /**
     * <p>
     * 获取图层瓦片是否透明。
     * </p>
     * @return 图层瓦片是否透明。
     */
    public boolean isTransparent() {
        return transparent;
    }

    /**
     * <p>
     * 检查当前图层是否已经初始化完成。
     * </p>
     * @return 如果地图已经初始化则返回 true，否则返回 false。
     */
    // public boolean isInitialized() {
    // return this.isLayerInited;
    // }

    /**
     * <p>
     * 设置图层瓦片是否透明。
     * </p>
     * @param isTransparent 图层瓦片是否透明。
     */
    public void setTransparent(boolean isTransparent) {
//        if (this.mapView != null && mapView.getBaseLayer() == this) {
//            // 如果图层是baseLayer，默认为不透明
//            this.transparent = false;
//        }
        this.transparent = isTransparent;
    }

    /**
     * <p>
     * 获取是否启用地图状态信息缓存，默认不启用
     * </p>
     * @return 是否启用
     */
    // public boolean isCacheMapStatusEnabled() {
    // return cacheMapStatusEnabled;
    // }

    /**
     * <p>
     * 设置是否启用地图状态信息缓存
     * </p>
     * @param cacheMapStatusEnabled
     */
    // public void setCacheMapStatusEnabled(boolean cacheMapStatusEnabled) {
    // this.cacheMapStatusEnabled = cacheMapStatusEnabled;
    // }

    /*void setMapFactory(MapProvider provider) {
        if (provider == getMapProvider()) {
            return;
        }
    
        if (provider.equals(MapProvider.REST)) {
            this.tileFactory = new RestMapTileFactory(this);
        }
    
        // this.rotatableProjection = new RotatableProjection(this, this.tileFactory.getProjection());
    
        ITileCache cache = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY);
        if (cache != null) {
            cache.clear();
        }
        if ((getWidth() > 0) && (getHeight() > 0))
            postInvalidate();
    }*/

    // protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    // if ((w <= 0) && (h <= 0))
    // return;
    // if (this.mapView.getMapRotation()!= 0.0F) {
    // setMapRotation(this.mapView.getMapRotation());
    // } else {
    // this.width = getWidth();
    // this.height = getHeight();
    // }
    //
    // this.focalPoint.set(getWidth() >> 1, getHeight() >> 1);
    // this.mapView.scalePoint.x = this.focalPoint.x;
    // this.mapView.scalePoint.y = this.focalPoint.y;
    //
    // if (getTileCacher() != null)
    // getTileCacher().checkCacheSize(this.height, this.width);
    //
    // if (!this.firedMapLoaded) {
    // this.firedMapLoaded = true;
    // EventDispatcher.sendEmptyMessage(1);
    // }
    //
    // EventDispatcher.sendEmptyMessage(5);
    // preLoad();
    // }

    // private ZoomControls createZoomControls() {
    // if (this.zoomControls == null) {
    // this.zoomControls = new ZoomControls(this.context);
    // this.zoomControls.setZoomSpeed(2000L);
    // this.zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
    // public void onClick(View v) {
    // MapView.this.mapController.zoomIn();
    // }
    // });
    // this.zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
    // public void onClick(View v) {
    // MapView.this.mapController.zoomOut();
    // }
    // });
    // }
    // if (this.builtInZoomControls) {
    // this.zoomControls.setVisibility(4);
    // }
    // return this.zoomControls;
    // }

    // private ZoomButtonsController createZoomButtonsController() {
    // if (this.zoomButtonsController == null) {
    // this.zoomButtonsController = new ZoomButtonsController(this);
    // this.zoomButtonsController.setZoomSpeed(2000L);
    // this.zoomButtonsController.setOnZoomListener(new ZoomButtonsController.OnZoomListener() {
    // public void onZoom(boolean zoomIn) {
    // long currentTime = System.currentTimeMillis();
    // if (currentTime - zoomButtonsLastClickTime < ZOOMBUTTONS_DELAY) {
    // return;
    // }
    // zoomButtonsLastClickTime = currentTime;
    // if (zoomIn) {
    // MapView.this.mapController.zoomIn();
    // } else {
    // MapView.this.mapController.zoomOut();
    // }
    // }
    //
    // public void onVisibilityChanged(boolean visible) {
    // }
    // });
    // }
    // return this.zoomButtonsController;
    // }

    // protected void setMapCenter(GeoPoint geoPoint, int zoomLevel) {
    // synchronized (this) {
    // this.mapView.getZoomLevel() = zoomLevel;
    // this.centerGeoPoint = geoPoint;
    // }

    // updateZoomControls();

    // if (getWidth() == 0)
    // return;
    // postInvalidate();
    // }

    // private void updateZoomControls() {
    // boolean zoomInEnabled = this.zoomLevel < this.tileFactory.getMaxZoomLevel();
    // boolean zoomOutEnabled = this.zoomLevel > this.tileFactory.getMinZoomLevel();
    //
    // if (this.zoomControls != null) {
    // this.zoomControls.setIsZoomInEnabled(zoomInEnabled);
    // this.zoomControls.setIsZoomInEnabled(zoomOutEnabled);
    // }
    // if ((this.builtInZoomControls) && (this.zoomButtonsController != null)) {
    // ZoomControls zc = (ZoomControls) this.zoomButtonsController.getZoomControls();
    // zc.setIsZoomInEnabled(zoomInEnabled);
    // zc.setIsZoomOutEnabled(zoomOutEnabled);
    // }
    // }

    // public Point getFocalPoint() {
    // return new Point(this.mapView.focalPoint);
    // }

    // public void setFocalPoint(Point point) {
    // this.focalPoint.set(point.x, point.y);
    // this.rotatableProjection.setRotate(this.rotateDegrees, point.x, point.y);
    // moved();
    // EventDispatcher.sendEmptyMessage(23);
    // postInvalidate();
    // }

    // void addTile(Tile tile) {
    // if (this.tileFactory == null) {
    // return;
    // }
    // if (tile.getZoomLevel() != this.mapView.getZoomLevel()) {
    // return;
    // }
    // if (this.tileFactory.getTileType() == TileType.MAP) {
    // if (tile.getTileType() != TileType.MAP)
    // return;
    // } else if (tile.getTileType() == TileType.MAP) {
    // return;
    // }
    //
    // if ((tile.getBitmap() == null) || (tile.getBitmap().isRecycled())) {
    // this.getTileCacher().getCache(TileCacher.CacheType.MEMORY).removeTile(tile);
    // return;
    // }
    //
    // postInvalidate();
    // }

    // private void drawTile(Tile tile, Canvas canvas, boolean drawLoadingTile) {
    // if (this.tileFactory == null) {
    // return;
    // }
    // if (tile.getZoomLevel() != this.mapView.getZoomLevel()) {
    // return;
    // }
    // Tile t = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY).getTile(tile);
    //
    // this.totalTileCount += 1;
    //
    // Bitmap bitmap = null;
    // if (t != null) {
    // bitmap = t.getBitmap();
    //
    // if ((bitmap == null) || (bitmap.isRecycled())) {
    // this.getTileCacher().getCache(TileCacher.CacheType.MEMORY).removeTile(tile);
    // return;
    // }
    // this.tileCount += 1;
    // }
    // // else if ((drawLoadingTile)) {
    // // bitmap = getLoadingTile();
    // // }
    //
    // Rect tileRect = tile.getRect();
    // if (tileRect == null) {
    // Log.d(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_DRAWTILE_UNVISIBLE, tile.toString()));
    // return;
    // }
    //
    // if ((canvas != null) && (bitmap != null)) {
    // canvas.drawBitmap(bitmap, tileRect.left, tileRect.top, this.customTilePaint);
    // }
    // }

    // private int drawTiles(Canvas canvas, Point2D geoPoint, int zoom, boolean drawLoadingTile) {
    // if ((getWidth() == 0) || (getHeight() == 0))
    // return 0;
    // if ((this.getTileCacher() == null) || (this.tileFactory == null))
    // return 0;
    // if (zoom < 0) {
    // return 0;
    // }
    // int zoomLevel = this.mapView.getZoomLevel();
    // Point2D centerGeoPoint = this.mapView.centerGeoPoint;
    //
    // if ((zoom != this.mapView.getZoomLevel()) && (zoom >= 0)) {
    // this.mapView.setZoomLevel(zoom);
    // }
    // if (geoPoint != this.mapView.centerGeoPoint) {
    // this.mapView.centerGeoPoint = geoPoint;
    // }
    //
    // this.zoomLevel = this.mapView.getZoomLevel();
    //
    // this.tileCount = 0;
    // this.totalTileCount = 0;
    // try {
    // if (this.tileFactory.getTileType() == TileType.HYB) {
    // iterateTiles(this.zoomLevel, TileType.SAT, false, canvas, drawLoadingTile);
    // }
    // iterateTiles(this.zoomLevel, this.tileFactory.getTileType(), false, canvas, drawLoadingTile);
    // int i = this.tileCount;
    // return i;
    // } finally {
    // if (zoomLevel != this.mapView.getZoomLevel())
    // this.mapView.setZoomLevel(zoomLevel);
    // if (centerGeoPoint != this.mapView.centerGeoPoint)
    // this.mapView.centerGeoPoint = centerGeoPoint;
    // }
    // }

    // private void iterateTiles(int zoom, TileType type, boolean queueTile, Canvas canvas, boolean drawLoadingTile) {
    // if ((getWidth() == 0) || (getHeight() == 0)) {
    // return;
    // }
    // if ((this.getTileCacher() == null) || (this.tileFactory == null)) {
    // return;
    // }
    // if (zoom < 0 || getZoomLevel() < 0) {
    // return;
    // }
    //
    // int midY = this.mapView.focalPoint.y;
    // int midX = this.mapView.focalPoint.x;
    // int tileSize = this.tileFactory.getTileSize();
    //
    // boolean positiveYFinished = false;
    // boolean negativeYFinished = false;
    // Point offset = new Point();
    //
    // this.rotRect.set(0, 0, getWidth(), getHeight());
    // if (this.mapView.getMapRotation() != 0.0F) {
    // this.getProjection().rotateMapRect(this.rotRect);
    // }
    //
    // int i = 0;
    // for (int startY = midY; (!positiveYFinished) || (!negativeYFinished); i++) {
    // boolean positiveXFinished = false;
    // boolean negativeXFinished = false;
    // Tile t = null;
    // int i_sign = i % 2 == 0 ? -tileSize : tileSize;
    // startY = midY + (i / 2 + i % 2) * i_sign;
    //
    // int j = 0;
    // for (int startX = midX; (!positiveXFinished) || (!negativeXFinished); j++) {
    // int j_sign = j % 2 == 0 ? -tileSize : tileSize;
    // startX = midX + (j / 2 + j % 2) * j_sign;
    // // Log.d(LOG_TAG, "startX startY " + startX + "," + startY);
    // offset = this.getProjection().offsetFromFocalPoint(startX, startY, offset);
    // // 仅用于返回瓦片信息，没有对范围有效性进行控制
    // t = this.tileFactory.buildTile(offset.x, offset.y, zoom, type);
    // if (t == null) {
    // break;
    // }
    // this.getProjection().offsetToFocalPoint(t.getRect());
    // Rect imageSize = this.tileFactory.getProjection().getMapImageSize();
    // if (Rect.intersects(this.rotRect, t.getRect()) && imageSize != null) {
    // boolean needDraw = t.getPixelX() > imageSize.left - 256 && t.getPixelX() < imageSize.right && t.getPixelY() > imageSize.top - 256
    // && t.getPixelY() < imageSize.bottom;
    // // boolean noDraw = (t.getPixelX() < imageSize.left - 256) || (t.getPixelX() >= imageSize.right) || (t.getPixelY() <= imageSize.top - 256)
    // // || (t.getPixelY() >= imageSize.bottom);
    // if (imageSize != null && needDraw) {
    // if (queueTile) {
    // // Log.d(LOG_TAG, "queueTile开始");
    // queueTile(t);
    // } else {
    // // Log.d(LOG_TAG, "drawTile开始");
    // drawTile(t, canvas, drawLoadingTile);
    // }
    // }
    // }
    //
    // if (j % 2 != 0) {
    // if (negativeXFinished) {
    // j++;
    // }
    //
    // } else if (positiveXFinished) {
    // j++;
    // }
    //
    // if (t.getRect().left < this.rotRect.left) {
    // negativeXFinished = true;
    // }
    // if (t.getRect().right <= this.rotRect.right) {
    // continue;
    // }
    // positiveXFinished = true;
    // }
    //
    // if (t == null) {
    // if (i % 2 == 0)
    // negativeYFinished = true;
    // else
    // positiveYFinished = true;
    // } else {
    // if (t.getRect().top < this.rotRect.top)
    // negativeYFinished = true;
    // if (t.getRect().bottom > this.rotRect.bottom)
    // positiveYFinished = true;
    //
    // }
    //
    // if (i % 2 != 0) {
    // if (negativeYFinished) {
    // i++;
    // }
    //
    // } else if (positiveYFinished)
    // i++;
    // }
    // }

    // private void queueTile(Tile tile) {
    // this.totalTileCount += 1;
    // ITileCache mCache = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY);
    // if (mCache != null) {
    // Tile ct = mCache.getTile(tile);
    // if (ct == null) {
    // this.getTileProvider().queueTile(tile);
    // }
    // }
    // }

    // void preLoadDelayed(long delayMillis) {
    // this.mapEventCallback.sendEmptyMessageDelayed(PRELOAD, delayMillis);
    // }

    // void preLoad() {
    // if (!this.isLayerInited) {// 没有初始化完成，不去迭代计算需要下载的瓦片（因未初始化完会发生异常）
    // return;
    // }
    // if ((getMapWidth() == 0) || (getMapHeight() == 0))
    // return;
    // if ((this.getTileCacher() == null) || (this.tileFactory == null))
    // return;
    // if (getZoomLevel() < 0) {
    // return;
    // }
    //
    // this.getTileProvider().beginQueue();// 会清空下载队列
    // try {
    // if (this.tileFactory.getTileType() == TileType.HYB) {
    // if (this.tileFactory.isSupportedTileType(TileType.SAT)) {
    // iterateTiles(getZoomLevel(), TileType.SAT, true, null, false);
    // }
    // }
    // if (this.tileFactory.isSupportedTileType(this.tileFactory.getTileType())) {
    // iterateTiles(getZoomLevel(), this.tileFactory.getTileType(), true, null, false);
    // }
    // } finally {
    // this.getTileProvider().endQueue();
    // }
    // }

    private boolean isScaleValid(double currentScale) {// validateZoomLevel 函数中调用，但可能会影响性能，考虑缓存level的方式实现
        if (currentScale <= 0) {
            return false;
        }
        if (this.visibleScales == null || this.visibleScales.length == 0) {
            return true;
        } else {
            int length = this.visibleScales.length;
            if (Tool.isDoubleEqual(currentScale, this.visibleScales[0]) || Tool.isDoubleEqual(currentScale, this.visibleScales[length - 1])) {
                return true;
            }
            if (currentScale < this.visibleScales[0] || currentScale > this.visibleScales[length - 1]) {
                return false;
            }
            for (int index = 1; index < length; index++) {
                if (Tool.isDoubleEqual(currentScale, this.visibleScales[index])) {
                    return true;
                }
            }
        }

        return false;
    }

    // void setZoomLevel(int mapZoomLevel) {
    // if (mapZoomLevel > this.tileFactory.getMaxZoomLevel()) {
    // mapZoomLevel = this.tileFactory.getMaxZoomLevel();
    // }
    // if (mapZoomLevel < this.tileFactory.getMinZoomLevel()) {
    // mapZoomLevel = this.tileFactory.getMinZoomLevel();
    // }
    // setMapCenter(this.centerGeoPoint, mapZoomLevel);
    // }

    // void zoomToSpan(double latE6, double lngE6) {
    // double midlatE6 = latE6 / 2;
    // double midlngE6 = lngE6 / 2;
    // double lngE6Left = this.mapView.centerGeoPoint.getLongitude() - midlngE6;
    // double lngE6Right = this.mapView.centerGeoPoint.getLongitude() + midlngE6;
    // BoundingBox bbox = new BoundingBox(new GeoPoint(this.mapView.centerGeoPoint.getLatitude() + midlatE6, lngE6Left), new GeoPoint(
    // this.mapView.centerGeoPoint.getLatitude() - midlatE6, lngE6Right));
    //
    // zoomToSpan(bbox, false);
    // }
    //
    // void zoomToSpan(BoundingBox bbox, boolean shouldCenter) {
    // if (this.mapView.getZoomLevel() < 6)
    // this.mapView.setZoomLevel(6);
    //
    // Rect rect = visibleRegion();
    //
    // int zoom = getProjection().calculateZoomLevel(bbox, getWidth() - rect.width(), getHeight() - rect.height());
    //
    // if (validateZoomLevel(zoom)) {
    // EventDispatcher.sendEmptyMessage(11);
    // if (shouldCenter) {
    // EventDispatcher.sendEmptyMessage(21);
    // this.mapView.centerGeoPoint = bbox.getCenter();
    // EventDispatcher.sendEmptyMessage(23);
    // }
    // this.mapView.setZoomLevel(zoom);
    // if (shouldCenter) {
    // this.mapView.centerGeoPoint = bbox.getCenter();
    //
    // int centerX = this.mapView.focalPoint.x + (this.mapView.focalPoint.x - rect.centerX());
    // int centerY = this.mapView.focalPoint.y + (this.mapView.focalPoint.y - rect.centerY());
    // EventDispatcher.sendEmptyMessage(21);
    // this.mapView.centerGeoPoint = getProjection().fromPixels(centerX, centerY);
    // EventDispatcher.sendEmptyMessage(23);
    // }
    // EventDispatcher.sendEmptyMessage(12);
    // } else {
    // Log.w(LOG_TAG, "Invalid zoom calculated: " + zoom);
    //
    // if (shouldCenter) {
    // this.mapView.centerGeoPoint = bbox.getCenter();
    //
    // int centerX = this.mapView.focalPoint.x + (this.mapView.focalPoint.x - rect.centerX());
    // int centerY = this.mapView.focalPoint.y + (this.mapView.focalPoint.y - rect.centerY());
    // EventDispatcher.sendEmptyMessage(21);
    // this.mapView.centerGeoPoint = getProjection().fromPixels(centerX, centerY);
    // EventDispatcher.sendEmptyMessage(23);
    // }
    // }
    // }

    // public Overlay getOverlayByKey(String key) {
    // if (getOverlays() == null)
    // return null;
    //
    // for (Overlay o : getOverlays()) {
    // if (o.getKey().equals(key))
    // return o;
    // }
    // return null;
    // }

    // public void removeOverlayByKey(String key) {
    // Overlay o = getOverlayByKey(key);
    // if (o != null)
    // getOverlays().remove(o);
    // postInvalidate();
    // }
    //
    // MapProvider getMapProvider() {
    // if (this.tileFactory != null) {
    // return this.tileFactory.getMapProvider();
    // }
    // return null;
    // }

    // /**
    // * <p>
    // * 返回图层的地理范围。
    // * </p>
    // * @return 图层的地理范围。
    // */
    // public BoundingBox getBounds() {
    /*if (!this.isLayerInited) {
        throw new IllegalStateException("图层没有初始化。");
    }*/
    /*if (bbox == null)
        bbox = new BoundingBox();
    RotatableProjection projection = getProjection();
    if (projection == null)
        return null;

    int w = getWidth();
    int h = getHeight();

    if ((w == 0) && (h == 0)) {
        w = this.mapView.getMapWidth();
        h = this.mapView.getMapHeight();
    }
    
    GeoPoint ul = projection.fromPixels(0, 0);
    GeoPoint lr = projection.fromPixels(w, h);
    
    if (this.mapView.getMapRotation() != 0.0F) {
        GeoPoint ur = projection.fromPixels(w, 0);
        GeoPoint ll = projection.fromPixels(0, h);
    
        GeoPoint[] points = new GeoPoint[4];
        points[0] = ul;
        points[1] = ur;
        points[2] = ll;
        points[3] = lr;
        double l = 180.0D;
        double r = -180.0D;
        double t = -90.0D;
        double b = 90.0D;
        for (int i = 0; i < points.length; i++) {
            if (points[i].getLongitude() < l)
                l = points[i].getLongitude();
            if (points[i].getLongitude() > r)
                r = points[i].getLongitude();
            if (points[i].getLatitude() > t)
                t = points[i].getLatitude();
            if (points[i].getLatitude() < b)
                b = points[i].getLatitude();
        }
        bbox.leftTop = new GeoPoint(t, l);
        bbox.rightBottom = new GeoPoint(b, r);
        Log.d(LOG_TAG, ">>bbox: " + bbox + "; center: " + this.mapView.centerGeoPoint);
        return bbox;
    }
    bbox.leftTop = ul;
    bbox.rightBottom = lr;
    Log.d(LOG_TAG, "bbox: " + bbox + "; center: " + this.mapView.centerGeoPoint);
    return bbox;*/
    // return this.layerBounds != null ? new BoundingBox(this.layerBounds) : null;
    // }

    // public boolean canCoverCenter() {
    // int mid_x = getMapWidth() >> 1;
    // int mid_y = getMapHeight() >> 1;
    //
    // if (this.tileFactory.getTileType() == TileType.HYB) {
    // Tile hybTile = this.tileFactory.buildTile(mid_x, mid_y, getZoomLevel(), TileType.HYB);
    // hybTile = this.tileCacher.getCache(TileCacher.CacheType.MEMORY).getTile(hybTile);
    // Tile satTile = this.tileFactory.buildTile(mid_x, mid_y, getZoomLevel(), TileType.SAT);
    // satTile = this.tileCacher.getCache(TileCacher.CacheType.MEMORY).getTile(satTile);
    //
    // return (satTile != null) && (hybTile != null);
    // }
    //
    // Tile tile = this.tileFactory.buildTile(mid_x, mid_y, getZoomLevel(), this.tileFactory.getTileType());
    // tile = this.tileCacher.getCache(TileCacher.CacheType.MEMORY).getTile(tile);
    // return tile != null;
    // }

    // protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
    // return p instanceof LayoutParams;
    // }

    // public void displayZoomControls(boolean takeFocus) {
    // if (this.builtInZoomControls) {
    // this.zoomButtonsController = createZoomButtonsController();
    // if (takeFocus) {
    // this.zoomButtonsController.setFocusable(true);
    // this.zoomButtonsController.setVisible(true);
    // }
    // } else if ((this.zoomControls != null) && (takeFocus)) {
    // this.zoomControls.setFocusable(true);
    // this.zoomControls.show();
    // this.zoomControls.requestFocus();
    // }
    // }

    // protected LayoutParams generateDefaultLayoutParams() {
    // return new LayoutParams(-2, -2, new GeoPoint(0.0D, 0.0D), 3);
    // }
    //
    // public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
    // return new LayoutParams(getContext(), attrs);
    // }
    //
    // protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
    // return new LayoutParams(p);
    // }

    // public MapController getController() {
    // return this.mapController;
    // }

    // public int getLatitudeSpan() {
    // int top = getProjection().fromPixels(0, 0).getLatitudeE6();
    // int bottom = getProjection().fromPixels(0, getHeight()).getLatitudeE6();
    // return Math.abs(bottom - top);
    // }
    //
    // public int getLongitudeSpan() {
    // int left = getProjection().fromPixels(0, 0).getLongitudeE6();
    // int right = getProjection().fromPixels(getWidth(), 0).getLongitudeE6();
    // if (right < left) {
    // return Util.to1E6(360.0D) + right - left;
    // }
    // return Math.abs(right - left);
    // }

    // public List<Overlay> getOverlays() {
    // return this.overlayController.getOverlays();
    // }

    /*public GeoPoint getMapCenter() {
        return new GeoPoint(this.mapView.centerGeoPoint.getLatitude(), this.mapView.centerGeoPoint.getLongitude());
    }*/

    /*public int getMaxZoomLevel() {
        return this.tileFactory.getMaxZoomLevel();
    }*/

    // Projection getProjection() {
    // return this.projection;
    // }

    // private TileCacher getTileCacher() {
    // if (this.mapView != null) {
    // return this.mapView.getTileCacher();
    // }
    // return null;
    // }

    // private int getZoomLevel() {
    // if (mapView != null) {
    // return this.mapView.getZoomLevel();
    // }
    // return this.zoomLevel;
    // }

    // public View getZoomControls() {
    // if (this.zoomControls == null) {
    // this.zoomControls = createZoomControls();
    // }
    // if (this.builtInZoomControls) {
    // this.zoomControls.setVisibility(4);
    // }
    // return this.zoomControls;
    // }

    // public boolean isSatellite() {
    // return this.configuration.isSatellite();
    // }

    // public boolean isStreetView() {
    // return false;
    // }

    // public boolean isTraffic() {
    // return this.trafficManager == null ? false : this.trafficManager
    // .isEnabled();
    // }

    // protected void onAttachedToWindow() {
    // super.onAttachedToWindow();
    // if ((this.zoomButtonsController != null) && (this.builtInZoomControls))
    // this.zoomButtonsController.setVisible(true);
    // }

    // protected void onDetachedFromWindow() {
    // super.onDetachedFromWindow();
    // if (this.zoomButtonsController != null)
    // this.zoomButtonsController.setVisible(false);
    // }

    // public float getMapRotation() {
    // return this.rotateDegrees;
    // }

    // void setMapRotation(float rotateDegrees) {
    // if (this.mapView.getMapRotation() == rotateDegrees) {
    // return;
    // }
    // rotateDegrees = (rotateDegrees + 360.0F) % 360.0F;
    // this.rotateDegrees = rotateDegrees;
    // this.rotatableProjection.setRotate(rotateDegrees, this.focalPoint.x, this.focalPoint.y);
    // this.rotRect.set(0, 0, getWidth(), getHeight());
    // this.rotatableProjection.rotateMapRect(this.rotRect);
    // this.width = this.rotRect.width();
    // this.height = this.rotRect.height();
    // moved();
    // postInvalidate();
    // }

    // private int getMapWidth() {
    // return this.mapView.getMapWidth() == 0 ? getWidth() : this.mapView.getMapWidth();
    // }

    // private int getMapHeight() {
    // return this.mapView.getMapHeight() == 0 ? getHeight() : this.mapView.getMapHeight();
    // }

    // void setTileLayerPaint(Paint paint) {
    // if (paint == null)
    // paint = new Paint();
    //
    // paint.setDither(true);
    // paint.setFilterBitmap(true);
    // paint.setAntiAlias(true);
    // this.customTilePaint = paint;
    // }

    // @Override
    // protected void onDraw(Canvas canvas) {
    // if (!this.visible) {
    // return;
    // }
    // // 在canvasParam中绘制瓦片前，先新建一个以上一次的屏幕图片为底图的Canvas，在往上面绘制最新瓦片，最后把底图和瓦片一起保持于mLastScreen，用于下一次绘制
    // // Canvas canvas = new Canvas(mapView.mLastScreen);
    // // if (!mapView.zoomInChanged && this == mapView.getBaseLayer()) {
    // // canvas.drawColor(Color.WHITE);
    // // } else {
    // // canvas.drawBitmap(mapView.mLastScreen, 0, 0, null);
    // // }
    //
    // if (!this.isLayerInited) {
    // return;
    // }
    // // Log.d(LOG_TAG, "layerView onDraw!!!!");
    // if (getProjection() == null)
    // return;
    // if ((getHeight() == 0) || (getWidth() == 0)) {
    // return;
    // }
    //
    // if (canvas == null) {
    // return;
    // }
    //
    // // canvas.setBitmap(mapView.mLastScreen);
    // // canvas.drawBitmap(mapView.mLastScreen, 0,0, null);
    //
    // try {
    // // Rect bounds = canvas.getClipBounds();
    // //
    // // if ((bounds.width() <= 10) || (bounds.height() <= 10)) {
    // // return;
    // // }
    // // if (this.animators.size() > 0) {
    // // if ((bounds.width() == getWidth()) && (bounds.height() == getHeight())) {
    // // Animator animator = (Animator) this.animators.peek();
    // // if (!animator.animate()) {
    // // this.animators.poll();
    // // }
    // // if (this.animators.size() > 0) {
    // // invalidate();
    // // }
    // // } else {
    // // invalidate();
    // // }
    // // }
    //
    // // canvas.save();
    // // if (this.mapView.getMapRotation() != 0.0F) {
    // // Log.w(LOG_TAG, "layerView onDraw 1");
    // // boolean clipScreen = false;
    // //
    // // if ((bounds.width() != getWidth()) || (bounds.height() != getHeight())) {
    // // clipScreen = true;
    // // canvas.save(2);
    // // canvas.clipRect(0.0F, 0.0F, getWidth(), getHeight(), Op.REPLACE);
    // // }
    // //
    // // canvas.rotate(this.mapView.getMapRotation(), this.mapView.focalPoint.x, this.mapView.focalPoint.y);
    // //
    // // canvas.getClipBounds(bounds);
    // //
    // // if (clipScreen) {
    // // canvas.restore();
    // // }
    // //
    // // }
    //
    // if (this.mapView.currentScale != 1.0F) {
    // canvas.save();
    //
    // Point out = this.getProjection().mapPoint(this.mapView.scalePoint.x, this.mapView.scalePoint.y, null);
    // this.getProjection().offsetToFocalPoint(out.x, out.y, out);
    // canvas.scale(this.mapView.currentScale, this.mapView.currentScale, out.x, out.y);
    //
    // int zoom = this.mapView.scaling ? this.mapView.getZoomLevel() : (int) Math.round(this.mapView.getZoomLevel()
    // - Util.log2(this.mapView.currentScale));
    //
    // Point2D point = this.mapView.centerGeoPoint;
    // if ((this.mapView.scalePoint.x != this.mapView.focalPoint.x) || (this.mapView.scalePoint.y != this.mapView.focalPoint.y)) {
    // if (validateZoomLevel(zoom)) {
    // int tempZoomLevel = this.mapView.getZoomLevel();
    // Point2D tempCenterGeoPoint = this.mapView.centerGeoPoint;
    //
    // this.mapView.centerGeoPoint = getProjection().fromPixels(this.mapView.scalePoint.x, this.mapView.scalePoint.y);
    //
    // this.mapView.setZoomLevel(zoom);
    // int newCenterX = this.mapView.focalPoint.x + (this.mapView.focalPoint.x - this.mapView.scalePoint.x);
    // int newCenterY = this.mapView.focalPoint.y + (this.mapView.focalPoint.y - this.mapView.scalePoint.y);
    // point = getProjection().fromPixels(newCenterX, newCenterY);
    // this.mapView.setZoomLevel(tempZoomLevel);
    // this.mapView.centerGeoPoint = tempCenterGeoPoint;
    // }
    //
    // }
    // drawTiles(canvas, point, zoom, true);
    // canvas.restore();
    // }
    // // preLoad();
    // if ((!this.mapView.scaling) || (this.mapView.currentScale == 1.0F)) {
    // boolean drawLoadingTile = this.mapView.currentScale == 1.0F;
    // if (this.mapView.currentScale != 1.0F) {
    // preLoad();
    // }
    // // Log.d(LOG_TAG, "layerView onDraw drawTiles开始!!!,中心点为:\t" + this.mapView.centerGeoPoint.toString());
    // int tileCount = drawTiles(canvas, this.mapView.centerGeoPoint, this.mapView.getZoomLevel(), drawLoadingTile);
    // // Log.d(LOG_TAG, "layerView onDraw drawTiles结束!!!");
    // if ((tileCount >= GUARANTEE_TILELOAD_PERCENTAGE * this.totalTileCount) && (this.mapView.currentScale != 1.0F)) {
    // // Log.i(LOG_TAG, "layerView setScale!!!");
    // setScale(1.0F, 1.0F, this.mapView.focalPoint.x, this.mapView.focalPoint.y);
    // }
    //
    // }
    // // canvas.restore();
    // preLoad();
    // // if (this.reticalMode == ReticleDrawMode.DRAW_RETICLE_UNDER) {
    // // Reticle.draw(canvas, this, this.focalPoint);
    // // }
    //
    // // renderOverlays(canvas);
    //
    // // if (this.reticalMode == ReticleDrawMode.DRAW_RETICLE_OVER) {
    // // Reticle.draw(canvas, this, this.focalPoint);
    // // }
    //
    // // if (this.logo)
    // // this.logoDrawable.draw(canvas);
    // } catch (Exception ex) {
    // Log.e(LOG_TAG, resource.getMessage(MapCommon.LAYERVUEW_EXCEPTION, ex));
    // }
    // // 把最后的瓦片出图结果绘制在canvasParam中去
    // // canvasParam.drawBitmap(mapView.mLastScreen, 0, 0, null);
    // }

    // private void renderOverlays(Canvas canvas) {
    // try {
    // if ((this.currentScale != 1.0F) && (this.scaling)) {
    // canvas.save(1);
    // canvas.scale(this.currentScale, this.currentScale, this.scalePoint.x, this.scalePoint.y);
    // }
    //
    // if (this.trafficManager != null) {
    // this.trafficManager.draw(canvas, this);
    // }
    // this.overlayController.renderOverlays(canvas, this);
    //
    // if ((this.currentScale != 1.0F) && (this.scaling))
    // canvas.restore();
    // } finally {
    // if ((this.currentScale != 1.0F) && (this.scaling))
    // canvas.restore();
    // }
    // }

    // private Point scalePoint(int x, int y, Point point) {
    // if (this.mapView.scaling) {
    // Point scalePoint = this.mapView.scalePoint;
    // point.y = (int) (scalePoint.y + (y - scalePoint.y) * this.mapView.currentScale + GUARANTEE_TILELOAD_PERCENTAGE);
    // point.x = (int) (scalePoint.x + (x - scalePoint.x) * this.mapView.currentScale + GUARANTEE_TILELOAD_PERCENTAGE);
    // } else {
    // point.x = x;
    // point.y = y;
    // }
    // return point;
    // }

    // private void redoLayout(boolean changed, int l, int t, int r, int b) {
    // // int c = getChildCount();
    // Point point = new Point();
    // int height = getHeight();
    // int width = getWidth();
    // // for (int i = 0; i < c; i++) {
    //
    // View view = this;
    //
    // // if ((view.getVisibility() == 8) || (!(view.getLayoutParams() instanceof LayoutParams)))
    // // continue;
    // MapView.LayoutParams lp = (MapView.LayoutParams) view.getLayoutParams();
    // if (lp == null) {
    // Log.d(LOG_TAG, "LayerView redoLayout defauft!");
    // view.layout(l, t, r, b);
    // } else {
    // if (lp.mode == 0) {
    // if (lp.point == null) {
    // Log.e(LOG_TAG, "View instance mode is set to map but geopoint is not set");
    //
    // point.x = lp.x;
    // point.y = lp.y;
    // } else {
    // Log.d(LOG_TAG, "LayerView redoLayout getProjection!");
    // point = getProjection().toPixels(lp.point, point);
    // if (this.mapView.currentScale != 1.0F) {
    // point = scalePoint(point.x, point.y, point);
    // }
    // point.x += (lp.x != 2147483647 ? lp.x : 0);
    // point.y += (lp.y != 2147483647 ? lp.y : 0);
    // }
    // } else {
    // point.x = lp.x;
    // point.y = lp.y;
    // }
    //
    // int alignment = lp.alignment;
    // int cw = view.getMeasuredWidth();
    // int childHeight = view.getMeasuredHeight();
    //
    // int childLeft = point.x != 2147483647 ? point.x : width >> 1;
    // int childTop = point.y != 2147483647 ? point.y : height >> 1;
    // int childRight = childLeft + cw;
    // int childBottom = childTop + childHeight;
    // int x_padding = getPaddingLeft() - getPaddingRight();
    // int y_padding = getPaddingTop() - getPaddingRight();
    //
    // int count = 0;
    //
    // while ((alignment != 0) && (count++ < 3)) {
    // if ((alignment & 0x20) == 32) {
    // childBottom = point.y != 2147483647 ? childTop : height;
    // childTop = childBottom - childHeight;
    // alignment ^= 32;
    // continue;
    // }
    // if ((alignment & 0x10) == 16) {
    // childTop = point.y != 2147483647 ? childTop : 0;
    // childBottom = childTop + childHeight;
    // alignment ^= 16;
    // continue;
    // }
    // if ((alignment & 0x8) == 8) {
    // childRight = point.x != 2147483647 ? point.x : width;
    // childLeft = childRight - cw;
    // alignment ^= 8;
    // continue;
    // }
    // if ((alignment & 0x4) == 4) {
    // childLeft = point.x != 2147483647 ? point.x : 0;
    // childBottom = childLeft + cw;
    // alignment ^= 4;
    // continue;
    // }
    //
    // if ((alignment & 0x1) == 1) {
    // childLeft -= (cw >> 1);
    // childRight = childLeft + cw;
    // alignment ^= 1;
    // continue;
    // }
    // if ((alignment & 0x2) == 2) {
    // childTop -= (childHeight >> 1);
    // childBottom = childTop + childHeight;
    // alignment ^= 2;
    // }
    // }
    // Log.d(LOG_TAG, "LayerView redoLayout start!");
    // view.layout(childLeft + x_padding, childTop + y_padding, childRight + x_padding, childBottom + y_padding);
    // // }
    // }
    // }
    /**
     * 分配所有的子元素的大小和位置。
     */
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // redoLayout(changed, l, t, r, b);
    }

    // protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // // measureChildren(widthMeasureSpec, heightMeasureSpec);
    // int mw = resolveSize(getMeasuredWidth(), widthMeasureSpec);
    // int mh = resolveSize(getMeasuredHeight(), heightMeasureSpec);
    // setMeasuredDimension(mw, mh);
    // }
    /**
     * 改变地图的焦点。
     */
    @Override
    protected void onFocusChanged(boolean hasFocus, int direction, Rect unused) {
        super.onFocusChanged(hasFocus, direction, unused);
    }

    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    // if (this.overlayController.onKeyDown(keyCode, event, this))
    // return true;
    // if (keyCode == 19) {
    // getController().scrollBy(0, getHeight() / 4);
    // return true;
    // }
    // if (keyCode == 20) {
    // getController().scrollBy(0, -(getHeight() / 4));
    // return true;
    // }
    // if (keyCode == 22) {
    // getController().scrollBy(getWidth() / 4, 0);
    // return true;
    // }
    // if (keyCode == 21) {
    // getController().scrollBy(-(getWidth() / 4), 0);
    // return true;
    // }
    //
    // return false;
    // }

    // public boolean onKeyUp(int keyCode, KeyEvent event) {
    // return this.overlayController.onKeyUp(keyCode, event, this);
    // }

    // void onRestoreInstanceState(Bundle state) {
    // if ((state.containsKey("STATE_CENTER_LAT")) && (state.containsKey("STATE_CENTER_LNG"))) {
    // int latE6 = state.getInt("STATE_CENTER_LAT");
    // int longE6 = state.getInt("STATE_CENTER_LNG");
    // this.mapView.centerGeoPoint = new Point2D(longE6, latE6);
    // }
    //
    // if (state.containsKey("STATE_ZOOM_LEVEL")) {
    // int zoom = state.getInt("STATE_ZOOM_LEVEL");
    // if (validateZoomLevel(zoom))
    // this.mapView.setZoomLevel(zoom);
    // }
    // }
    //
    // void onSaveInstanceState(Bundle state) {
    // if (this.mapView.centerGeoPoint != null) {
    // state.putDouble("STATE_CENTER_LAT", this.mapView.centerGeoPoint.getY());
    // state.putDouble("STATE_CENTER_LNG", this.mapView.centerGeoPoint.getX());
    // }
    // state.putInt("STATE_ZOOM_LEVEL", this.mapView.getZoomLevel());
    // }

    // boolean handleOverlayEvent(MotionEvent event) {
    // return this.overlayController.onTouchEvent(event, this);
    // }

    // boolean onTap(GeoPoint gp) {
    // return this.overlayController.onTap(gp, this);
    // }

    // @Override
    // public boolean onTouchEvent(MotionEvent event) {
    // InputMethodManager imm = (InputMethodManager) this.context.getSystemService("input_method");
    //
    // imm.hideSoftInputFromWindow(getWindowToken(), 0);
    //
    // if ((isClickable()) && (isEnabled())) {
    // if ((this.builtInZoomControls) && (this.zoomButtonsController != null)) {
    // if (!this.zoomButtonsController.isVisible())
    // this.zoomButtonsController.setVisible(true);
    // } else if (this.zoomControls != null) {
    // this.zoomControls.show();
    // }
    // requestFocus();
    // if (handleOverlayEvent(event)) {
    // return true;
    // }
    // if ((this.eventHandler != null) && (this.eventHandler.handleTouchEvent(event))) {
    // return true;
    // }
    // }
    // return false;
    // }

    // public boolean onTrackballEvent(MotionEvent event) {
    // if (this.overlayController.onTrackballEvent(event, this)) {
    // return true;
    // }
    // if (this.trackBallHandler == null)
    // this.trackBallHandler = new DefaultTrackBallHandler(this);
    // return this.trackBallHandler.handleTrackballEvent(event);
    // }

    // public void setBuiltInZoomControls(boolean on) {
    // this.builtInZoomControls = on;
    // if (on) {
    // this.zoomButtonsController = createZoomButtonsController();
    // this.zoomButtonsController.setAutoDismissed(true);
    // if (this.zoomControls != null)
    // this.zoomControls.setVisibility(4);
    // } else {
    // if (this.zoomButtonsController != null) {
    // this.zoomButtonsController.setVisible(false);
    // }
    // if (this.zoomControls != null)
    // this.zoomControls.setVisibility(0);
    // }
    // }

    // public void setReticleDrawMode(ReticleDrawMode mode) {
    // this.reticalMode = mode;
    // }

    // public void setSatellite(boolean on) {
    // this.configuration.setSatellite(on);
    // }

    /*void setSatellite(boolean on, boolean label) {
        if ((this.tileFactory == null) && (this.tileProvider == null)
                && (this.tileCacher == null))
            return;
    
        TileType type = this.configuration.isSatellite() ? TileType.SAT
                : this.configuration.isSatelliteLabeled() ? TileType.HYB
                        : TileType.MAP;
        if ((this.provider == MapProvider.REST)
                && ((type == TileType.SATHYB) || (type == TileType.HYB))) {
            type = TileType.SAT;
        }
    
        if (this.tileFactory != null)
            this.tileFactory.setType(type);
        if (this.tileProvider != null)
            this.tileProvider.clearQueue();
    
        if ((getWidth() > 0) && (getHeight() > 0)) {
            preLoad();
            postInvalidate();
        }
    }*/

    // public void setStreetView(boolean on) {
    // }

    // public void setTraffic(boolean on) {
    // if (this.trafficManager == null) {
    // String baseUrl = getConfiguration().getTrafficURL();
    // String key = this.configuration.getPlatformApiKey();
    // this.trafficManager = new TrafficManager(this, baseUrl, key);
    // }
    // this.trafficManager.setTraffic(on);
    // }

    // private void setTileFactoryBaseURL(String url) {
    // this.tileFactory.setBaseUrl(url);
    // }

    // void setScale(float scaleX, float scaleY, float focusX, float focusY) {
    // this.mapView.currentScale = scaleX;
    //
    // this.mapView.scalePoint.x = (int) focusX;
    // this.mapView.scalePoint.y = (int) focusY;
    // mapView.moved();
    // }

    /**
     * <p>
     * 销毁当前 LayerView 对象，退出前调用。
     * </p>
     */
    public void destroy() {
        // if (this.trafficManager != null) {
        // this.trafficManager.destroy();
        // this.trafficManager = null;
        // }

        // if (this.mapController != null) {
        // this.mapController.destroy();
        // this.mapController = null;
        // }
        // if (this.tileProvider != null) {
        // this.tileProvider.destroy();
        // this.tileProvider = null;
        // }
        //
        // if (this.tileCacher != null) {
        // this.tileCacher.destroy();
        // this.tileCacher = null;
        // }

        // this.tileFactory = null;

        // this.overlayController.destroy();
        //
        // if (this.eventHandler != null) {
        // if ((this.eventHandler instanceof TouchEventHandler)) {
        // this.eventHandler.destroy();
        // }
        // this.eventHandler = null;
        // }

        // if (this.networkConnectivityListener != null) {
        // this.networkConnectivityListener.stopListening();
        // this.networkConnectivityListener = null;
        // }

        // if (this.zoomButtonsController != null) {
        // this.zoomButtonsController.setVisible(false);
        // this.zoomButtonsController = null;
        // this.builtInZoomControls = false;
        // }

        // this.termsView = null;

        // if (this.mapEventCallback != null) {
        // EventDispatcher.removeHandler(this.mapEventCallback);
        // this.mapEventCallback = null;
        // }

        // this.rotatableProjection = null;
        // this.zoomControls = null;

//        if (this.loadingTile != null) {
//            this.loadingTile.recycle();
//            this.loadingTile = null;
//        }

        destroyDrawingCache();

        // ((MapActivity) this.context).removeMapView(this);
    }

    private Bitmap getLoadingTile() {
        if (this.loadingTile == null) {
            String path = "/com/supermap/android/maps/loading.jpg";
            this.loadingTile = BitmapFactory.decodeStream(getClass().getResourceAsStream(path));

            if (this.loadingTile == null) {
                Log.d(LOG_TAG, resource.getMessage(MapCommon.LAYERVIEW_GETLOADINGTILE_NULL, path));
            }
        }
        return this.loadingTile;
    }

    void setLoadingTile(Bitmap loadingTile) {// 待考虑是否开出
        this.loadingTile = loadingTile;
    }

    // void moved() {
    // redoLayout(true, getLeft(), getTop(), getLeft() + getWidth(), getTop() + getHeight());
    // }

    // void zoomStart() {
    // this.mapView.scaling = true;
    // }
    //
    // void zoomEnd() {
    // this.mapView.scaling = false;
    // preLoad();
    // }

    /**
     * 清除运行时内存中缓存的瓦片
     */
    private void clearTilesInMemory() {
        if (this.getTileCacher() != null) {
            ITileCache mem = this.getTileCacher().getCache(TileCacher.CacheType.MEMORY);
            if (mem instanceof MemoryTileCache)
                ((MemoryTileCache) mem).removeTilesByName(this.getLayerCacheFileName());
        }
    }

    /**
     * 清除运行时手机端的SD卡中该图层的缓存瓦片
     */
    private void clearTilesInDB() {
        if (this.getTileCacher() != null) {
            ITileCache db = this.getTileCacher().getCache(TileCacher.CacheType.DB);
            if (db instanceof FSTileCache) {
                String providerName = "rest-map";
                // if (this.tileFactory instanceof RestMapTileFactory) {
                // RestMapTileFactory rmtf = (RestMapTileFactory) tileFactory;
                // providerName = rmtf.getProvider();
                // }
                FSTileCache fstc = (FSTileCache) db;
                fstc.clearByDirName(providerName + File.separator + this.getLayerCacheFileName());
            }
        }
    }

    // private Rect visibleRegion() {
    // this.visibleRect.set(this.leftMargin, this.topMargin, getWidth() - this.rightMargin, getHeight() - this.bottomMargin);
    //
    // return this.visibleRect;
    // }

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

    // public Configuration getConfiguration() {
    // return this.configuration;
    // }

    // protected void onResume() {
    // if (this.getTileProvider() == null) {
    // this.tileProvider = new ThreadBasedTileDownloader(this, this.tileCacher);
    // }
    // preLoad();
    // }

    // protected void onPause() {
    // if (this.tileProvider != null) {
    // this.tileProvider.destroy();
    // this.tileProvider = null;
    // }
    // }
    //
    // protected void onStop() {
    // if (this.tileCacher != null) {
    // ITileCache mem = this.tileCacher.getCache(TileCacher.CacheType.MEMORY);
    // if (mem != null)
    // mem.clear();
    // }
    // }

    // public void addMapViewEventListener(final MapViewEventListener eventListener) {
    // Looper looper = getContext().getApplicationContext().getMainLooper();
    // final MapView mapView = this;
    // Handler handler = new Handler(looper, new Handler.Callback() {
    // public boolean handleMessage(Message msg) {
    // switch (msg.what) {
    // case 21:
    // eventListener.moveStart(mapView);
    // break;
    // case 22:
    // eventListener.move(mapView);
    // break;
    // case 23:
    // eventListener.moveEnd(mapView);
    // break;
    // case 4:
    // eventListener.longTouch(mapView);
    // break;
    // case 11:
    // eventListener.zoomStart(mapView);
    // break;
    // case 12:
    // eventListener.zoomEnd(mapView);
    // break;
    // case 3:
    // eventListener.touch(mapView);
    // break;
    // case 1:
    // eventListener.mapLoaded(mapView);
    // case 2:
    // case 5:
    // case 6:
    // case 7:
    // case 8:
    // case 9:
    // case 10:
    // case 13:
    // case 14:
    // case 15:
    // case 16:
    // case 17:
    // case 18:
    // case 19:
    // case 20:
    // }
    // return false;
    // }
    // });
    // this.mapViewEventListeners.put(eventListener, handler);
    // EventDispatcher.registerHandler(handler);
    // }

    // public void removeMapViewEventListener(MapViewEventListener eventListener) {
    // if (this.mapViewEventListeners.containsKey(eventListener))
    // EventDispatcher.removeHandler((Handler) this.mapViewEventListeners.get(eventListener));
    // }

    // private static int getSdkVersion() {
    // return new Integer(Build.VERSION.SDK).intValue();
    // }

    // private TileFactory getTileFactory() {
    // return this.tileFactory;
    // }

    // /**
    // * 根据 范围参数 计算全幅显示该范围的比例尺级别
    // * @param bb
    // * @return
    // */
    // public int calculateZoomLevel(BoundingBox bb) {
    // if (getProjection() != null) {
    // return getProjection().calculateZoomLevel(bb);
    // }
    // return 0;
    // }

    // public static abstract interface MapViewEventListener {
    // public abstract void moveStart(MapView paramMapView);
    //
    // public abstract void move(MapView paramMapView);
    //
    // public abstract void moveEnd(MapView paramMapView);
    //
    // public abstract void touch(MapView paramMapView);
    //
    // public abstract void longTouch(MapView paramMapView);
    //
    // public abstract void zoomStart(MapView paramMapView);
    //
    // public abstract void zoomEnd(MapView paramMapView);
    //
    // public abstract void mapLoaded(MapView paramMapView);
    // }

    // private class AssetUpdater extends Thread {
    // private static final String LOG_TAG = "com.supermap.android.maps.assetupdater";
    // private final String cdnRoot = "http://content.mqcdn.com/mobile/android/";
    // private long staleDate;
    // private final String[] assets = { "logo_hdpi.png", "logo_mdpi.png", "navteqlogo_hdpi.png", "navteqlogo_mdpi.png", "osmlogo_hdpi.png",
    // "osmlogo_mdpi.png" };
    //
    // public AssetUpdater() {
    // Calendar c = Calendar.getInstance();
    // c.add(5, -14);
    //
    // this.staleDate = c.getTimeInMillis();
    // }
    //
    // public void run() {
    // try {
    // for (String asset : this.assets)
    // updateFile(asset);
    // } catch (Exception e) {
    // Log.w(LOG_TAG, "unable to update assets", e);
    // }
    // }

    // private void updateFile(String name) {
    // File asset = new File(MapView.this.getContext().getCacheDir().getAbsoluteFile() + File.separator + name);
    // if (needsUpdating(asset)) {
    // FileOutputStream fos = null;
    // InputStream is = null;
    // try {
    // fos = new FileOutputStream(asset);
    // is = HttpUtil.executeAsStream("http://content.mqcdn.com/mobile/android/" + name);
    //
    // byte[] buf = new byte[1024];
    // int length;
    // while ((length = is.read(buf)) > 0)
    // fos.write(buf, 0, length);
    // } catch (Exception e) {
    // Log.w(LOG_TAG, "unable to update assets", e);
    // } finally {
    // try {
    // if (fos != null)
    // fos.close();
    // } catch (Exception e) {
    // }
    // try {
    // if (is != null)
    // is.close();
    // } catch (Exception e) {
    // }
    // }
    // }
    // }

    // private boolean needsUpdating(File asset) {
    // return asset.lastModified() < this.staleDate;
    // }
    // }

    // private static class Reticle {
    // static Paint paint;
    //
    // public static void draw(Canvas canvas, View view, Point focalPoint) {
    // int radius = Math.min(view.getWidth(), view.getHeight()) / 10;
    // int lineSize = radius * 2 + 10;
    // int startX = focalPoint.x - (lineSize >> 1);
    // int startY = focalPoint.y - (lineSize >> 1);
    // canvas.drawCircle(focalPoint.x, focalPoint.y, radius, getPaint());
    // canvas.drawLine(focalPoint.x, startY, focalPoint.x, startY + lineSize, paint);
    //
    // canvas.drawLine(startX, focalPoint.y, startX + lineSize, focalPoint.y, paint);
    // }
    //
    // private static Paint getPaint() {
    // if (paint == null) {
    // paint = new Paint(1);
    // paint.setDither(true);
    // paint.setStyle(Paint.Style.STROKE);
    //
    // paint.setColor(-7829368);
    // paint.setStrokeWidth(1.0F);
    // DashPathEffect dashEffect = new DashPathEffect(new float[] { 5.0F, 5.0F }, 1.0F);
    //
    // paint.setPathEffect(dashEffect);
    // }
    //
    // return paint;
    // }
    // }

    // public static class LayoutParams extends ViewGroup.LayoutParams {
    // public static final int BOTTOM = 32;
    // public static final int CENTER_HORIZONTAL = 1;
    // public static final int CENTER_VERTICAL = 2;
    // public static final int LEFT = 4;
    // public static final int RIGHT = 8;
    // public static final int TOP = 16;
    // public static final int TOP_LEFT = 20;
    // public static final int CENTER = 3;
    // public static final int BOTTOM_CENTER = 33;
    // public static final int MODE_MAP = 0;
    // public static final int MODE_VIEW = 1;
    // public int alignment = 3;
    //
    // public int mode = 1;
    // public GeoPoint point;
    // public int x = 2147483647;
    //
    // public int y = 2147483647;
    //
    // public LayoutParams(Context context, AttributeSet attrs) {
    // super(context, attrs);
    // this.x = attrs.getAttributeIntValue("http://schemas.mapquest.com/apk/res/mapquest", "x", 2147483647);
    // this.y = attrs.getAttributeIntValue("http://schemas.mapquest.com/apk/res/mapquest", "x", 2147483647);
    // String geoPoint = attrs.getAttributeValue("http://schemas.mapquest.com/apk/res/mapquest", "geoPoint");
    //
    // if ((geoPoint != null) && (geoPoint.length() > 0)) {
    // String[] arr = geoPoint.split(",");
    // if (arr.length > 1)
    // try {
    // double latitude = Double.parseDouble(arr[0].trim());
    // double longitude = Double.parseDouble(arr[1].trim());
    // this.point = new GeoPoint(latitude, longitude);
    // this.mode = 0;
    // } catch (NumberFormatException nfe) {
    // Log.e("com.supermap.android.maps.assetupdater", "Invalid value for geoPoint attribute : " + geoPoint);
    // }
    // }
    // }
    //
    // public LayoutParams(ViewGroup.LayoutParams source) {
    // super(source);
    // if ((source instanceof LayoutParams)) {
    // LayoutParams lp = (LayoutParams) source;
    // this.x = lp.x;
    // this.y = lp.y;
    // this.point = lp.point;
    // this.mode = lp.mode;
    // this.alignment = lp.alignment;
    // }
    // }
    //
    // public LayoutParams(int width, int height, GeoPoint point, int alignment) {
    // super(width, height);
    // this.point = point;
    // this.alignment = alignment;
    // if (point != null)
    // this.mode = 0;
    // }
    //
    // public LayoutParams(int width, int height, GeoPoint point, int x, int y, int alignment) {
    // super(width, height);
    // this.point = point;
    // this.x = x;
    // this.y = y;
    // this.alignment = alignment;
    // if (point != null)
    // this.mode = 0;
    // }
    //
    // public LayoutParams(int width, int height, int x, int y, int alignment) {
    // super(width, height);
    // this.x = x;
    // this.y = y;
    // this.alignment = alignment;
    // }
    // }

    // public static enum ReticleDrawMode {
    // DRAW_RETICLE_NEVER,
    //
    // DRAW_RETICLE_OVER,
    //
    // DRAW_RETICLE_UNDER;
    // }
    //
    // private class MapEventHandler extends Handler {
    // private MapEventHandler() {
    // }
    //
    // public void handleMessage(Message msg) {
    // switch (msg.what) {
    // case 21:
    // case 22:
    // MapView.this.moved();
    // return;
    // case 23:
    // case 33:
    // MapView.this.preLoad();
    // MapView.this.moved();
    // return;
    // case 11:
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
    //
    // }
    //
    // }
    //
    // MapView.this.scaling = true;
    // return;
    // case 61:
    // MapView.this.preLoad();
    // MapView.this.postInvalidate();
    // return;
    // case PRELOAD:
    // MapView.this.preLoad();
    // MapView.this.postInvalidate();
    // }
    // }
    // }
    //

    void setLayerViewStatusChangeListener(final OnStatusChangedListener layerViewStatusChangeListener) {
        Looper looper = getContext().getApplicationContext().getMainLooper();
        final LayerView layerView = this;
        Handler handler = new Handler(looper, new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                if (mapView != null && mapView.isDetroy) {
                    return true;
                }
                switch (msg.what) {
                case INITIALIZED:
                    layerViewStatusChangeListener.onStatusChanged(layerView, OnStatusChangedListener.STATUS.INITIALIZED);
                    break;
                case INITIALIZED_FAILED:
                    OnStatusChangedListener.STATUS status = OnStatusChangedListener.STATUS.INITIALIZED_FAILED;
                    String description = msg.getData().getString("description");
                    if (!isEmpty(description)) {
                        status.setDescription(description);
                    }
                    layerViewStatusChangeListener.onStatusChanged(layerView, status);
                    break;
                case LAYER_REFRESH:
                    layerViewStatusChangeListener.onStatusChanged(layerView, OnStatusChangedListener.STATUS.LAYER_REFRESH);
                    break;
                }
                return true;
            }
        });
        this.layerStatusChangeHandler = handler;
    }

    class UpdateLayerViewStatuThread extends Thread {
        private boolean clearBoundsInfo;
        private boolean clearScalesInfo;

        UpdateLayerViewStatuThread(boolean clearBoundsInfo, boolean clearScalesInfo) {
            this.clearBoundsInfo = clearBoundsInfo;
            this.clearScalesInfo = clearScalesInfo;
        }

        public void run() {
            initMapStatusParameter();
            updateMapStatus(this.clearBoundsInfo, this.clearScalesInfo);
        }
    }

    /**
     * <p>
     * 初始化tile瓦片
     * </p>
     * @param tile
     */
    @Override
    public void initTileContext(Tile tile) {
        int index = getResolutionIndex();
        if (index == -1) {
            return;
        }
        // Log.i(LOG_TAG, "get layerView resolutionIndex:" + index);
        double scale = this.dpi / this.mapView.getRealResolution();
        double[] resolutions = getResolutions();
        if (resolutions != null && index < resolutions.length) {
            // 因为layer的resolution和map的resolution存在可允许的误差，但是计算出图比例尺使用layer的resolutio
            // n

            scale = this.dpi / resolutions[index];
        }
        String result = "";
        tile.setScale(scale);
        boolean transparent = this.isTransparent();
        if (this.mapView != null && mapView.getBaseLayer() == this && this.transparent) {
            // 如果图层是baseLayer，默认为不透明
            transparent = false;
        }
        tile.setTransparent(transparent);

        if (this.getCRS() != null && this.getCRS().wkid > 0) {
            tile.setEpsgCodes(this.getCRS().wkid);
        }
        result = getURL() + "/tileImage.png?width=256&height=256&transparent=" + String.valueOf(transparent) + "&cacheEnabled=" + cacheEnabled + "&scale="
                + scale + "&x=" + tile.getX() + "&y=" + tile.getY();
        // CoordinateReferenceSystem crs = this.getCRS();
        if (crs != null && crs.wkid > 0) {
            result += "&prjCoordSys=%7B%22epsgCode%22%3A" + crs.wkid + "%7D";
        }
        if (!StringUtils.isEmpty(this.layersID)) {
            result += "&layersID=" + this.layersID;
        }
        if (Credential.CREDENTIAL != null) {
            result = result + "&" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        // Log.d(LOG_TAG, "getTileURL:" + result);
        tile.setUrl(result);
        // return result;
    }
}
