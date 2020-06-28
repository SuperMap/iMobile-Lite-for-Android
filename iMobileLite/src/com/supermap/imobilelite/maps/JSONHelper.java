package com.supermap.imobilelite.maps;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.supermap.imobilelite.resources.MapCommon;
import com.supermap.services.util.ResourceManager;

class JSONHelper {
    public static final String LOG_TAG = "com.supermap.android.maps.jsonhelper";
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    private boolean debug = false;

    void setDebug(boolean debug) {
        this.debug = debug;
    }

    public JSONObject getJSONObject(String name, JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                return jsonObject.getJSONObject(name);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return null;
    }

    public JSONArray getJSONArray(String name, JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                return jsonObject.getJSONArray(name);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return new JSONArray();
    }

    public JSONArray getJSONArray(int index, JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                return jsonArray.optJSONArray(index);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return new JSONArray();
    }

    public int getInt(String name, JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                return jsonObject.getInt(name);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return -1;
    }

    public String getString(String name, JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                return jsonObject.getString(name);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return "";
    }

    public Double getDouble(String name, JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                return Double.valueOf(jsonObject.getDouble(name));
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return Double.valueOf(-1.0D);
    }

    public boolean getBoolean(String name, JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                return jsonObject.getBoolean(name);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return false;
    }

    public String getString(int index, JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                return jsonArray.getString(index);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return "";
    }

    public int getInt(int index, JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                return jsonArray.getInt(index);
            } catch (Exception e) {
                Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return -1;
    }

    public double getDouble(int index, JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                return jsonArray.getDouble(index);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return -1.0D;
    }

    public JSONObject getJSONObject(int index, JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                return jsonArray.getJSONObject(index);
            } catch (Exception e) {
                if (this.debug)
                    Log.w(LOG_TAG, resource.getMessage(MapCommon.JSONHELPER_PARSE_EXCEPTION, e.getMessage()));
            }
        }
        return null;
    }
}