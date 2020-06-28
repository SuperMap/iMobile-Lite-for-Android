package com.supermap.imobilelite.mapsamples.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.supermap.imobilelite.maps.Point2D;

import android.util.Log;

public final class JSONUtil {

	public static String getPrjCoordSysFromJSON(JSONObject prjcoordsys) {
		String type = Constants.DEFAULT_PRJCOORDSYS_TYPE;
		if (prjcoordsys != null) {
			try {
				type = (String) prjcoordsys.get("type");
			} catch (JSONException e) {
				Log.w(Constants.ISERVER_TAG, "Get prjcoordsys type error", e);
			}
		}
		return type;
	}
	
	public static double getEntireImageScale(JSONObject mapJSON) throws JSONException {
		double result = -1.0;
		JSONObject viewBounds = mapJSON.getJSONObject("viewBounds");
		double viewBoundsRight = viewBounds.getDouble("right");
		double viewBoundsLeft = viewBounds.getDouble("left");
		double viewBoundsWidth = Math.abs(viewBoundsRight - viewBoundsLeft);
		double viewBoundsHeight = viewBounds.getDouble("height");
		
		// JSONObject viewer = mapJSON.getJSONObject("viewer");
		// int viewerRight = viewer.getInt("right");
		// int viewerLeft = viewer.getInt("left");
		// double viewerWidth = Math.abs(viewerRight - viewerLeft);
		double scale = mapJSON.getDouble("scale");
		double boundsWidth = getBoundsWidth(mapJSON);
		double boundsHeight = getBoundsHeight(mapJSON);
		if (Double.compare(boundsWidth, boundsHeight) <= 0) {
			result = (viewBoundsHeight / boundsHeight) * scale;
		} else {
			result = (viewBoundsWidth / boundsWidth) * scale;
		}
		// return (viewBoundsWidth / boundsWidth) * (mapViewWidth / viewerWidth) * scale;
		return result;
	}
	
	private static double getBoundsWidth(JSONObject mapJSON) throws JSONException{
		double result = 0.0;
		result = Math.abs(getBoundsRight(mapJSON) - getBoundsLeft(mapJSON));
		return result;
	}
	
	private static double getBoundsHeight(JSONObject mapJSON) throws JSONException{
		double result = 0.0;
		result = Math.abs(getBoundsTop(mapJSON) - getBoundsBottom(mapJSON));
		return result;
	}
	
	public static double getBoundsLeft(JSONObject mapJSON) throws JSONException{
		double result = 0.0;
		boolean customEntireBoundsEnabled = mapJSON.getBoolean("customEntireBoundsEnabled");
		if (customEntireBoundsEnabled) {
			JSONObject customEntireBounds = mapJSON.getJSONObject("customEntireBounds");
			result = customEntireBounds.getDouble("left");
		}else{
			JSONObject bounds = mapJSON.getJSONObject("bounds");
			result = bounds.getDouble("left");
		}
		return result;
	}
	
	public static double getBoundsRight(JSONObject mapJSON) throws JSONException{
		double result = 0.0;
		boolean customEntireBoundsEnabled = mapJSON.getBoolean("customEntireBoundsEnabled");
		if (customEntireBoundsEnabled) {
			JSONObject customEntireBounds = mapJSON.getJSONObject("customEntireBounds");
			result = customEntireBounds.getDouble("right");
		}else{
			JSONObject bounds = mapJSON.getJSONObject("bounds");
			result = bounds.getDouble("right");
		}
		return result;
	}
	
	public static double getBoundsTop(JSONObject mapJSON) throws JSONException{
		double result = 0.0;
		boolean customEntireBoundsEnabled = mapJSON.getBoolean("customEntireBoundsEnabled");
		if (customEntireBoundsEnabled) {
			JSONObject customEntireBounds = mapJSON.getJSONObject("customEntireBounds");
			result = customEntireBounds.getDouble("top");
		}else{
			JSONObject bounds = mapJSON.getJSONObject("bounds");
			result = bounds.getDouble("top");
		}
		return result;
	}
	
	public static double getBoundsBottom(JSONObject mapJSON) throws JSONException{
		double result = 0.0;
		boolean customEntireBoundsEnabled = mapJSON.getBoolean("customEntireBoundsEnabled");
		if (customEntireBoundsEnabled) {
			JSONObject customEntireBounds = mapJSON.getJSONObject("customEntireBounds");
			result = customEntireBounds.getDouble("bottom");
		}else{
			JSONObject bounds = mapJSON.getJSONObject("bounds");
			result = bounds.getDouble("bottom");
		}
		return result;
	}

    public static boolean getIsMercatorProFromJSON(JSONObject prjcoordsysJSON) {
        if (prjcoordsysJSON != null) {
            try {
                JSONObject projection = prjcoordsysJSON.getJSONObject("projection");
                if (projection == null) {
                    return false;
                }
                String projectionName = projection.getString("name");
                String projectionType = (String) projection.get("type");
                if ("SPHERE_MERCATOR".equals(projectionName) && "PRJ_SPHERE_MERCATOR".equals(projectionType)) {
                    return true;
                } else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static Point2D getCenter(String url) throws JSONException {
        Point2D result = new Point2D(0.0d, 0.0d);
        url = url+"/entireImage";
        Log.d(Constants.ISERVER_TAG, "getCenter url:" + url);
        JSONObject mapJSON = HttpUtil.getMapJSON("PCS_NON_EARTH", url);
        if (mapJSON == null) {
            return result;
        }
        JSONObject mapParam = mapJSON.getJSONObject("mapParam");
        if(mapParam == null){
            return result;
        }
        JSONObject center = mapParam.getJSONObject("center");
        if (center != null) {
            double x = center.getDouble("x");
            double y = center.getDouble("y");
            Log.d(Constants.ISERVER_TAG, "mapJSON centerxy:" + x + "," + y);
            result = new Point2D(x, y);
        }
        return result;
    }

    // 墨卡托转经纬度
    public static Point2D Mercator2lonLat(double mx, double my) {
        double wx = mx / 20037508.34 * 180;
        double wy = my / 20037508.34 * 180;
        wy = 180 / Math.PI * (2 * Math.atan(Math.exp(wy * Math.PI / 180)) - Math.PI / 2);
        return new Point2D(wx, wy);
    }

    // 经纬度转墨卡托
    public static Point2D lonLat2Mercator(double wx, double wy) {
        double mx = wx * 20037508.34 / 180;
        double my = Math.log(Math.tan((90 + wy) * Math.PI / 360)) / (Math.PI / 180);
        my = my * 20037508.34 / 180;
        return new Point2D(mx, my);
    }

    public static double getEntireImageScale(String mapUrl) {
        JSONObject json = HttpUtil.getEntireImageJSON(mapUrl + "/entireImage.rjson?width=256&height=256&transparent=false");
        try {
            return json.getJSONObject("mapParam").getDouble("scale");
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
