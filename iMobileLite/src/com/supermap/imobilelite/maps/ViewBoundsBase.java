package com.supermap.imobilelite.maps;

import android.os.Handler;

/**
 * Created by jk on 2017/12/8.
 */

public class ViewBoundsBase {
    ComputeCenterCallBack cb = null;
    protected Handler mHandle = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    cb.ComputeCenter((Point2D)msg.obj);
                    break;
            }
        };
    };

    public boolean setResponseCallback(ComputeCenterCallBack resposeCallback){
        if(resposeCallback != null){
            cb = resposeCallback;
            return true;
        }
        else{
            return false;
        }
    }
}
