package com.supermap.imobilelite.maps;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * MBTiles元信息封装类，用于初始化MBTiles图层的状态
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * 
 */
class MBTilesMetadata {
    public BoundingBox bounds;
    public String name = "";
    public String type = "baselayer";
    public String version = "1.0.0";
    public String description = "";
    public String format = "png";
    public boolean compatible;
    public boolean transparent;
    // public String tag = "MOBAC";
    public double[] resolutions;
    public double[] scales;
    public int crs_wkid = -1;
    public String unit = "degree";
    public int tileSize = 256;
    public int minzoom = 0;
    public int maxzoom = 0;
    public Point2D axis_origin;
    // 以下属性暂时没有使用
    // private String legend = "";
    // private String resolutionsStr = "";

    /**
     * <p>
     * 解析bouds字符串为相应的BoundingBox对象
     * </p>
     * @param bounds bouds字符串
     * @return
     */
    public BoundingBox getLayerBounds(String bounds) {
        if (StringUtils.isEmpty(bounds)) {
            return null;
        }
        String[] boundsArr = bounds.split(",");
        if (boundsArr != null && boundsArr.length == 4) {
            return new BoundingBox(new Point2D(Double.valueOf(boundsArr[0]), Double.valueOf(boundsArr[3])), new Point2D(Double.valueOf(boundsArr[2]),
                    Double.valueOf(boundsArr[1])));
        } else {
            return null;
        }
    }

    /**
     * <p>
     * 解析多个double组成的字符串为double数组
     * </p>
     * @param str 多个double组成的字符串
     * @return
     */
    protected Double[] getDoubleArray(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String[] stringArr = str.split(",");
        List<Double> stringList = new ArrayList<Double>();
        for (int i = 0; i < stringArr.length; i++) {
            String item = stringArr[i];
            // 判断字符串是否是数字，包括整型和浮点型
            if (!item.matches("^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE][-+]?\\d+)?[dD]?$")) {
                continue;
            }
            stringList.add(Double.valueOf(item));
        }
        Double[] result = new Double[stringList.size()];
        return stringList.toArray(result);
    }

    /**
     * <p>
     * 解析多个scale组成的字符串为scales数组
     * </p>
     * @param str 多个scale组成的字符串
     * @return
     */
    public double[] getScalesFromStr(String str) {
        Double[] scales = getDoubleArray(str);
        double[] result = new double[scales.length];
        for (int i = 0; i < scales.length; i++) {
            result[i] = 1.0 / scales[i];
        }
        return result;
    }

    /**
     * <p>
     * 解析多个resolution组成的字符串为resolutions数组
     * </p>
     * @param str 多个resolution组成的字符串
     * @return
     */
    public double[] getResolutionsFromStr(String str) {
        Double[] resolutions = getDoubleArray(str);
        double[] result = new double[resolutions.length];
        for (int i = 0; i < resolutions.length; i++) {
            result[i] = resolutions[i];
        }
        return result;
    }
}
