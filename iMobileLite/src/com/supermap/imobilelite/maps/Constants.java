package com.supermap.imobilelite.maps;

/**
 * <p>
 * 公用的常量类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public final class Constants {

    // 日志标签
    // 暂不用，先不公开
    private static final String ISERVER_TAG = "iserver";

    /**
     * <p>
     * 字符串编码UTF-8常量。
     * </p>
     */
    public static final String UTF8 = "utf-8";

    // 经纬坐标系
    static final String DEFAULT_PRJCOORDSYS_TYPE = "PCS_EARTH_LONGITUDE_LATITUDE";

    // 普通平面坐标系
    static final String PCS_NON_EARTH = "PCS_NON_EARTH";

    // 记录初始地图服务的配置文件
    static final String INIT_CONFIG_FILE_NAME = "init_mapresourceinfo.xml";

    // 记录添加的地图服务的配置文件
    static final String CONFIG_FILE_NAME = "mapresourceinfos.xml";

    /**
     * <p>
     * 地球的赤道半径常量值，值为6378137。
     * </p>
     */
    public static final double DEFAULT_AXIS = 6378137d;

    /**
     * <p>
     * 地图缩放级别常量值，值为18。
     * </p>
     */
    final static int DEFAULT_RESOLUTION_SIZE = 18;
    // 下载图片时网络超时时间
    final static int NETWORK_TIME_OUT = 5000;
}
