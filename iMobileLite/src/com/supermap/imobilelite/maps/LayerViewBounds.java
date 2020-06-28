package com.supermap.imobilelite.maps;

import android.os.Message;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jk on 2017/12/7.
 */

public class LayerViewBounds extends ViewBoundsBase {

    private String url;
    public LayerViewBounds(String Url){
        url=Url;
        this.run();
    }

    public void run(){
     new Thread(new Runnable() {
        @Override
        public void run() {
            String myString = null;
            try {
                // 定义获取文件内容的URL
                URL myURL = new URL(url+".json");
                // 打开URL链接
                URLConnection ucon = myURL.openConnection();
                // 使用InputStream，从URLConnection读取数据
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                // 用ByteArrayBuffer缓存
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }
                // 将缓存的内容转化为String,用UTF-8编码
                myString = EncodingUtils.getString(baf.toByteArray(), "UTF-8");
                JSONObject jsonObject = new JSONObject(myString);
                String viewBounds = jsonObject.getString("viewBounds");
                JSONObject jsonObject1 = null;
                jsonObject1 = new JSONObject(viewBounds);
                String itop = jsonObject1.getString("top");
                double top = Double.parseDouble(itop);
                String iright = jsonObject1.getString("right");
                double right = Double.parseDouble(iright);
                String ileft = jsonObject1.getString("left");
                double left = Double.parseDouble(ileft);
                String ibottom = jsonObject1.getString("bottom");
                double bottom = Double.parseDouble(ibottom);
                Point2D LeftTop = new Point2D(left,top);
                Point2D RightBottom = new Point2D(right,bottom);
                BoundingBox boundingBox = new BoundingBox(LeftTop,RightBottom);
                Point2D center=boundingBox.getCenter();
                Message msgAddFeature = mHandle.obtainMessage(1);
                msgAddFeature.obj = center;
                mHandle.sendMessage(msgAddFeature);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
    }
}
