package com.supermap.imobilelite.layerServices;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import com.supermap.services.components.commontypes.Layer;
import com.supermap.services.components.commontypes.LayerCollection;
import com.supermap.services.components.commontypes.LayerType;
import com.supermap.services.components.commontypes.Rectangle2D;
import com.supermap.services.components.commontypes.UGCChartLayer;
import com.supermap.services.components.commontypes.UGCGridLayer;
import com.supermap.services.components.commontypes.UGCImageLayer;
import com.supermap.services.components.commontypes.UGCLayerType;
import com.supermap.services.components.commontypes.UGCMapLayer;
import com.supermap.services.components.commontypes.UGCThemeLayer;
import com.supermap.services.components.commontypes.UGCVectorLayer;
import com.supermap.services.components.commontypes.WFSLayer;
import com.supermap.services.rest.util.JsonConverter;

public class ParseResultUtil {
    private static final JsonConverter jsConverer = new JsonConverter();

    public static List<Layer> parseLayersJson(String result) {
        return parseLayersJson(result, false);
    }

    public static Layer parseLayerJson(JSONObject result) {
        return parseLayerJson(result, false);
    }

    private static UGCMapLayer getUGCMapLayer(JSONObject result) {
        UGCMapLayer ugcMapLayer = new UGCMapLayer();// ugcMapLayer.type = LayerType.UGC;在构造函数里面
        try {
            if (result != null) {
                if (result.get("bounds") != null) {
                    ugcMapLayer.bounds = jsConverer.to(result.getString("bounds"), Rectangle2D.class);
                }
                if (result.get("queryable") != null) {
                    ugcMapLayer.queryable = result.getBoolean("queryable");
                }
                if (result.get("visible") != null) {
                    ugcMapLayer.visible = result.getBoolean("visible");
                }
                if (result.get("symbolScalable") != null) {
                    ugcMapLayer.symbolScalable = result.getBoolean("symbolScalable");
                }
                if (result.get("completeLineSymbolDisplayed") != null) {
                    ugcMapLayer.completeLineSymbolDisplayed = result.getBoolean("completeLineSymbolDisplayed");
                }
                if (result.get("minScale") != null) {
                    ugcMapLayer.minScale = result.getDouble("minScale");
                }
                if (result.get("maxScale") != null) {
                    ugcMapLayer.maxScale = result.getDouble("maxScale");
                }
                if (result.get("minVisibleGeometrySize") != null) {
                    ugcMapLayer.minVisibleGeometrySize = result.getDouble("minVisibleGeometrySize");
                }
                if (result.get("symbolScale") != null) {
                    ugcMapLayer.symbolScale = result.getDouble("symbolScale");
                }
                if (result.get("opaqueRate") != null) {
                    ugcMapLayer.opaqueRate = result.getInt("opaqueRate");
                }
                if (result.get("name") != null) {
                    ugcMapLayer.name = result.getString("name");
                }
                if (result.get("caption") != null) {
                    ugcMapLayer.caption = result.getString("caption");
                }
                if (result.get("description") != null) {
                    ugcMapLayer.description = result.getString("description");
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ugcMapLayer;
    }

    /**
     * <p>
     * 
     * </p>
     * @param result
     * @param simpleLayersInfo
     * @return
     * @since 7.0.0
     */
    public static List<Layer> parseLayersJson(String result, boolean simpleLayersInfo) {
        List<Layer> layerList = new ArrayList<Layer>();
        if (result != null) {
            try {
                JSONArray resultJA = new JSONArray(result);
                if (resultJA.length() > 0) {
                    for (int k = 0; k < resultJA.length(); k++) {
                        JSONObject superLayerJO = resultJA.getJSONObject(k);
                        Layer layer = parseLayerJson(superLayerJO, simpleLayersInfo);
                        if (layer != null) {
                            layerList.add(layer);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return layerList;
    }

    public static Layer parseLayerJson(JSONObject result, boolean simpleLayersInfo) {
        try {
            if (result != null) {
                if ("UGC".equals(result.getString("type"))) {
                    UGCMapLayer ugcMapLayer = null;
                    if (simpleLayersInfo) {
                        long s = System.currentTimeMillis();
                        ugcMapLayer = jsConverer.to(result.toString(), UGCMapLayer.class);
                        Log.d("com.supermap.imobilelite.layerServices.ParseResultUtil", "解析时间UGCMapLayer:" + (System.currentTimeMillis() - s) + "毫秒");
                    } else {
                        long s = System.currentTimeMillis();
                        ugcMapLayer = getUGCMapLayer(result);
                        JSONObject subLayersJO = result.getJSONObject("subLayers");
                        if (subLayersJO != null) {
                            JSONArray layersJO = subLayersJO.getJSONArray("layers");
                            if (layersJO != null && layersJO.length() > 0) {
                                if (ugcMapLayer.subLayers == null) {
                                    ugcMapLayer.subLayers = new LayerCollection();
                                } else {
                                    ugcMapLayer.subLayers.clear();
                                }
                                for (int i = 0; i < layersJO.length(); i++) {
                                    JSONObject layerJO = layersJO.getJSONObject(i);
                                    if (layerJO != null) {
                                        Layer layer = null;
                                        // UGCLayerType
                                        if ("CHART".equalsIgnoreCase(layerJO.getString("ugcLayerType"))) {
                                            layer = jsConverer.to(layerJO.toString(), UGCChartLayer.class);
                                        } else if ("GRID".equalsIgnoreCase(layerJO.getString("ugcLayerType"))) {
                                            layer = jsConverer.to(layerJO.toString(), UGCGridLayer.class);
                                        } else if ("IMAGE".equalsIgnoreCase(layerJO.getString("ugcLayerType"))) {
                                            layer = jsConverer.to(layerJO.toString(), UGCImageLayer.class);
                                        } else if ("THEME".equalsIgnoreCase(layerJO.getString("ugcLayerType"))) {
                                            layer = jsConverer.to(layerJO.toString(), UGCThemeLayer.class);
                                        } else if ("VECTOR".equalsIgnoreCase(layerJO.getString("ugcLayerType"))) {
                                            layer = jsConverer.to(layerJO.toString(), UGCVectorLayer.class);
                                        }
                                        if (layer != null) {
                                            ugcMapLayer.subLayers.add(layer);
                                        }
                                    }
                                }
                            }
                        }
                        Log.d("com.supermap.imobilelite.layerServices.ParseResultUtil", "解析时间subLayers:" + (System.currentTimeMillis() - s) + "毫秒");
                    }
                    return ugcMapLayer;
                } else if ("WMS".equals(result.getString("type"))) {
                    return jsConverer.to(result.toString(), WMSLayer.class);
                } else if ("WFS".equals(result.getString("type"))) {
                    return jsConverer.to(result.toString(), WFSLayer.class);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
