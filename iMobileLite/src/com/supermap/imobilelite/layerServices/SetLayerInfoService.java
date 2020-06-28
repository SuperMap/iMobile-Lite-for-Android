package com.supermap.imobilelite.layerServices;

import java.util.List;

import org.apache.http.entity.StringEntity;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.maps.Util;
import com.supermap.services.components.commontypes.Layer;

/**
 * <p>
 * 创建地图的临时图层服务
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class SetLayerInfoService {
    private String baseUrl;
    // 用于修改临时图层所需参数isTempLayers和resourceID
    // private boolean isTempLayers;
    private String resourceID;
    private int timeout = -1; // 代表使用默认超时时间，5秒
    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 地图服务的根地址
     */
    public SetLayerInfoService(String url) {
        super();
        this.baseUrl = url;
    }
    
    /**
     * <p>
     * 用户自定义超时时间。
     * </p>
     * @param timeout 用户自定义超时时间。若用户不设置，则使用默认超时间为5秒。0代表无限，即代表不设置超时限制。单位默认为秒。
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * <p>
     * 创建临时图层
     * </p>
     * @param listener 创建临时图层监听器
     * @param layer 创建临时图层的图层信息
     * @since 7.0.0
     */
    public void processTempLayersAdd(SetLayersInfoEventListener listener, Layer layer) {
        if (baseUrl == null || "".equals(baseUrl) || layer == null) {
            return;
        }
        String url = baseUrl + "/tempLayersSet.json";
        new SetLayersInfoThread(url, new SetLayersInfoHandler(listener), layer, "POST").start();
    }

    /**
     * <p>
     * 更新指定的临时图层
     * </p>
     * @param listener 更新临时图层监听器
     * @param layer 更新临时图层的图层信息
     * @param resourceID 指定临时图层的id
     * @since 7.0.0
     */
    public void processTempLayersUpdate(SetLayersInfoEventListener listener, Layer layer, String resourceID) {
        if (baseUrl == null || "".equals(baseUrl) || layer == null) {
            return;
        }
        if (resourceID == null || "".equals(resourceID)) {
            return;
        }
        String url = baseUrl + "/tempLayersSet/" + resourceID + ".json";
        if (Credential.CREDENTIAL != null) {
            url = url + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        new SetLayersInfoThread(url, new SetLayersInfoHandler(listener), layer, "PUT").start();
    }

    class SetLayersInfoThread extends Thread {
        private String url;
        private Handler handler;
        private Layer layer;
        private String method;

        public SetLayersInfoThread(String url, Handler handler, Layer layer, String method) {
            super();
            this.url = url;
            this.handler = handler;
            this.layer = layer;
            this.method = method;
        }

        @Override
        public void run() {
            try {
                String result = null;
                String entityStr = JSON.toJSONString(layer);
                StringEntity entity = new StringEntity("[" + entityStr + "]", "UTF-8");// 数组的形式;
                if ("PUT".equalsIgnoreCase(method)) {
//                    entity = new StringEntity(entityStr, "UTF-8");
                    result = Util.put(url, entity);
                } else if ("POST".equalsIgnoreCase(method)) {
//                    entity = new StringEntity("[" + entityStr + "]", "UTF-8");// 数组的形式
                    result = Util.post(url, entity, timeout);
                }
                SetLayerResult slResult = JSON.parseObject(result, SetLayerResult.class);
                Message msg = new Message();
                msg.what = 200;
                msg.obj = slResult;
                handler.sendMessage(msg);
            } catch (Exception e) {
                Message msg = new Message();
                msg.what = 300;
                msg.obj = e.getMessage();
                handler.sendMessage(msg);
            }
        }
    }

    class SetLayersInfoHandler extends Handler {
        private SetLayersInfoEventListener listener;

        public SetLayersInfoHandler(SetLayersInfoEventListener listener) {
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                listener.onSetLayersInfoStatus(msg.obj, EventStatus.PROCESS_COMPLETE);
            } else {
                listener.onSetLayersInfoStatus(msg.obj, EventStatus.PROCESS_FAILED);
            }
            super.handleMessage(msg);
        }
    }

    public static abstract class SetLayersInfoEventListener {
        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果。
         * @param status 分析结果的状态。
         */
        public abstract void onSetLayersInfoStatus(Object sourceObject, EventStatus status);
    }
}
