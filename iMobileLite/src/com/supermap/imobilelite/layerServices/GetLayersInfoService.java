package com.supermap.imobilelite.layerServices;

import java.util.List;
import android.os.Handler;
import android.os.Message;

import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.maps.Util;
import com.supermap.services.components.commontypes.Layer;

public class GetLayersInfoService {
    private String baseUrl;
    private boolean simpleLayersInfo;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 地图服务的根地址
     */
    public GetLayersInfoService(String url) {
        super();
        this.baseUrl = url;
    }

    public void process(GetLayersInfoEventListener listener) {
        if (baseUrl == null || "".equals(baseUrl)) {
            return;
        }
        String getUrl = baseUrl + "/layers.json";
        if (Credential.CREDENTIAL != null) {
            getUrl = getUrl + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
        new GetLayersInfoThread(getUrl, new GetLayersInfoHandler(listener)).start();
    }

    public void setSimpleLayersInfo(boolean simpleLayersInfo) {
        this.simpleLayersInfo = simpleLayersInfo;
    }

    class GetLayersInfoThread extends Thread {
        private String url;
        private Handler handler;

        public GetLayersInfoThread(String url, Handler handler) {
            super();
            this.url = url;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                String result = Util.getJsonStr(url);// 待完善
                List<Layer> layerList = ParseResultUtil.parseLayersJson(result, simpleLayersInfo);
                Message msg = new Message();
                msg.what = 200;
                msg.obj = layerList;
                handler.sendMessage(msg);
            } catch (Exception e) {
                Message msg = new Message();
                msg.what = 300;
                msg.obj = e.getMessage();
                handler.sendMessage(msg);
            }
        }
    }

    class GetLayersInfoHandler extends Handler {
        private GetLayersInfoEventListener listener;

        public GetLayersInfoHandler(GetLayersInfoEventListener listener) {
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                listener.onGetLayersInfoStatus(msg.obj, EventStatus.PROCESS_COMPLETE);
            } else {
                listener.onGetLayersInfoStatus(msg.obj, EventStatus.PROCESS_FAILED);
            }
            super.handleMessage(msg);
        }
    }

    public static abstract class GetLayersInfoEventListener {
        /**
         * <p>
         * 用户必须自定义实现处理分析结果的接口。
         * </p>
         * @param sourceObject 分析结果,即图层结果集合。
         * @param status 分析结果的状态。
         */
        public abstract void onGetLayersInfoStatus(Object sourceObject, EventStatus status);
    }
}
