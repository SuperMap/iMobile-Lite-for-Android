package com.supermap.imobilelite.layerServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.StringEntity;

import com.alibaba.fastjson.JSON;
import com.supermap.imobilelite.commons.Credential;
import com.supermap.imobilelite.maps.Util;
import com.supermap.services.components.commontypes.Layer;
import com.supermap.services.components.commontypes.LayerCollection;
import com.supermap.services.components.commontypes.UGCMapLayer;
import com.supermap.services.components.commontypes.UGCVectorLayer;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/*
 * 
 * @author Xingjun
 * <p>2016.08.31</p>
 *
 */
/**
 * <pre>
 * 根据图层显示过滤条件，创建或更新临时图层的类
 * 创建或更新的结果将通过监听器的回调函数{@link LayersFilterListener}返回
 * 
 * 设置某些图层可见，可参考{@link com.supermap.imobilelite.maps.LayerView#setLayersID()},通过其设置的图层就可以显示，其他图层不显示
 * </pre>
 *
 */
public class LayersFilterService {
    private String baseUrl;
    private boolean simpleLayersInfo;
    
	private Map<Integer, String> mFilterMap;
	private String tag;

	private LayersFilterListener mFilterListener;

	private CreateTempLayerHandler mHandler;

	private String mUpdateResouceID;
	 
	private int CREATED = 200;
	private int FAILED = 300;
	private int UPDATED = 201;
	
    /**
     * <p>
     * 构造函数。
     * </p>
     * @param url 地图服务的根地址
     */
    public LayersFilterService(String url) {
        super();
        this.baseUrl = url;
        tag = this.getClass().getSimpleName();
        
        mHandler = new CreateTempLayerHandler();
    }
    
    /**
     * 设置图层显示过滤监听器, 通过设置的listener获得服务创建或更新临时图层的结果
     * @param listener  图层显示过滤监听器
     */
    public void setLayersFilterListener(LayersFilterListener listener){
    	mFilterListener = listener;
    	mHandler.setListener(listener);
    }

    /**
     * 根据显示过滤条件, 创建一个临时图层; 显示过滤条件是有地图中的图层索引和过滤条件的键值对
     * @param filterMap 图层索引和过滤条件的键值对
     */
    public void createTempLayer(Map<Integer, String> filterMap) {
    	
    	
        if (baseUrl == null || "".equals(baseUrl) || filterMap == null ) {
        	Log.d(tag, "url or other paras are null");
            return;
        }
        if(filterMap.size() == 0){
        	Log.d(tag, "FilterMap is empty.");
        	return;
        }
        
        mFilterMap = filterMap;
       
      
        new CreateTempLayerThread(baseUrl, mHandler, "POST").start();
    }
    
    /**
     * 根据显示过滤条件, 更新指定ID的临时图层
     * @param resourceID  临时图层ID
     * @param filterMap   图层索引和过滤条件的键值对
     */
    public void updateTempLayer(String resourceID, Map<Integer, String> filterMap){
    	if (baseUrl == null || filterMap == null || resourceID == null) {
    		Log.d(tag, "url or other paras are null");
            return;
        }
    	
        if (resourceID == null || "".equals(resourceID)) {
            return;
        }
        mUpdateResouceID = resourceID;
        mFilterMap = filterMap;
        
        new CreateTempLayerThread(baseUrl, mHandler, "PUT").start();
    }

//    /**
//     * 设置是否使用简略图层信息
//     * @param simpleLayersInfo
//     */
//    public void setSimpleLayersInfo(boolean simpleLayersInfo) {
//        this.simpleLayersInfo = simpleLayersInfo;
//    }

    /*********************************** CreateTempLayerThread ****************************************/
    
    class CreateTempLayerThread extends Thread {
        private String url;
        private Handler handler;
		private String method;
		
		public CreateTempLayerThread(String url){
			 super();
	         this.url = url;
		}

        public CreateTempLayerThread(String url, Handler handler, String method) {
            super();
            this.url = url;
            this.handler = handler;
            this.method = method;
        }

        @Override
        public void run() {
        	
        	// 1. 获取图层列表
        	Message msg = new Message();
        	List<Layer> layerList =  getLayers(msg, url);
        	if(layerList == null){
        		handler.sendMessage(msg);
        		return;
        	}
        	
        	// 2. 向服务器请求创建临时图层
        	ArrayList<String> layerIDs = new ArrayList<String>();
        	
        	for (int i = 0; i < layerList.size(); i++) {
				
				if (layerList.get(i) instanceof UGCMapLayer) {
					
					UGCMapLayer ugcMapLayer = (UGCMapLayer) layerList.get(i);
					
					if (ugcMapLayer != null && ugcMapLayer.subLayers != null) {
						// 得到图层集合
						LayerCollection layerCol = ugcMapLayer.subLayers;
						// 定义一个临时图层集合
						LayerCollection tempLayerCol = new LayerCollection();
						if (layerCol.size() > 0) {
							
							String filter = null;
							for (int j = 0; j < layerCol.size(); j++) {
								
								filter = mFilterMap.get(j);
								if(filter == null)
									continue;
								
								Layer layer1 = layerCol.get(j);
								
								// 得到该矢量图层，并设置其可显示和过滤显示条件
								UGCVectorLayer ugcVectorLayer1 = (UGCVectorLayer) layer1;
								
								ugcVectorLayer1.visible = true;
								ugcVectorLayer1.displayFilter = filter;
								tempLayerCol.add(ugcVectorLayer1);
							}

							UGCMapLayer tempUGCMapLayer = new UGCMapLayer();
							tempUGCMapLayer.subLayers = tempLayerCol;
							
							String layerID = createTempLayer(msg, url, tempUGCMapLayer, method);
							layerIDs.add(layerID);
						}
					}
				}
        	}
        	
        	// 处理结果
        	if(layerIDs.size() < 0){
        		msg.what = FAILED;
        		if(msg.obj == null){
					if (method.equals("POST"))
						msg.obj = "Failed to create temp layer.";
					
					if (method.equals("PUT"))
						msg.obj = "Failed to update temp layer.";
        		}
        		
        		handler.sendMessage(msg);
        	}else {
        		
				if (method.equals("POST")){
					msg.what = CREATED;

					msg.obj = layerIDs;
				}
					
				if (method.equals("PUT")){
					if (layerIDs.get(0).contains("true")){
						msg.what = UPDATED;
					}else {
						msg.what = FAILED;
						msg.obj = layerIDs.get(0);
					}
				}
				
				
				handler.sendMessage(msg);
			}
        	
        }

    }
    /*********************************** CreateTempLayerThread End ****************************************/

    
    
    /*********************************** UpdateTempLayerThread ****************************************/

    class UpdateTempLayerThread extends Thread {
        private String url;
        private Handler handler;

        public UpdateTempLayerThread(String url, Handler handler) {
            super();
            this.url = url;
            this.handler = handler;
        }

        @Override
        public void run() {
        	
        }
    }

    /*********************************** UpdateTempLayerThread End****************************************/
    
    
    
    /**
     * 获取图层列表
     * @param msg 回传消息,如错误信息
     * @param url 地图服务地址
     * @return    图层列表
     */
	private List<Layer> getLayers(Message msg, String url) {
		
		String layersUrl = url + "/layers.json";
		if (Credential.CREDENTIAL != null) {
			layersUrl = layersUrl + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
        }
		List<Layer> layerList = null;
		try {
        	
        	// 获取图层
            String result = Util.getJsonStr(layersUrl);
			if (result != null) {
				layerList = ParseResultUtil.parseLayersJson(result, simpleLayersInfo);
			}else {
				msg.what = FAILED;
				msg.obj = "Failed to get layers.";
				return null;
			}
            
            msg.what = CREATED;
        } catch (Exception e) {
            msg.what = FAILED;
            msg.obj = e.getMessage();
        }
		
		return layerList;
	}
	
	/**
	 * 创建或更新临时图层
	 * @param msg
	 * @param url
	 * @param layer
	 * @param method
	 * @return
	 */
    private String createTempLayer(Message msg, String url, Layer layer, String method){
    	try {
            String result = null;
            String tempLayerCreateUrl =null;
            
            
            String entityStr = JSON.toJSONString(layer);
            
            StringEntity entity = new StringEntity("[" + entityStr + "]", "UTF-8");// 数组的形式;
//            String method = "POST";
            int timeout = -1;
            
			if ("PUT".equalsIgnoreCase(method )) {
				tempLayerCreateUrl = baseUrl + "/tempLayersSet/" + mUpdateResouceID + ".json";
		        if (Credential.CREDENTIAL != null) {
		        	tempLayerCreateUrl = tempLayerCreateUrl + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
		        }
                result = Util.put(tempLayerCreateUrl, entity);
            } else if ("POST".equalsIgnoreCase(method)) {
            	tempLayerCreateUrl = baseUrl + "/tempLayersSet.json";
                if (Credential.CREDENTIAL != null) {
                	tempLayerCreateUrl = tempLayerCreateUrl + "?" + Credential.CREDENTIAL.name + "=" + Credential.CREDENTIAL.value;
                }
                result = Util.post(tempLayerCreateUrl, entity, timeout);
            }
            
            
            // 创建
            if (method.equals("POST")){
            	SetLayerResult slResult = JSON.parseObject(result, SetLayerResult.class);
				if (result != null) {
					
					result = slResult.newResourceID;
				}
            }
			return result;

        } catch (Exception e) {
            msg.what = FAILED;
            msg.obj = e.getMessage();
            
        }
    	
    	return null;
    }
    
    
    /************************** CreateTempLayerHandler ****************************************/
    class CreateTempLayerHandler extends Handler {
    	
        private LayersFilterListener listener;

        public CreateTempLayerHandler() {
            
        }
        
       public void setListener(LayersFilterListener listener) {
			this.listener = listener;
		}

        @SuppressWarnings("unchecked")
		@Override
        public void handleMessage(Message msg) {
        	
			if (listener != null) {

				if (msg.what == CREATED) {
					listener.onCreated((List<String>)msg.obj);
				} else if (msg.what == FAILED) {
					listener.onFailed(msg.obj.toString());
				}else if(msg.what == UPDATED) {
					listener.onUpdated(mUpdateResouceID);
				}
			}
            super.handleMessage(msg);
        }
    }
    /************************** CreateTempLayerHandler End ****************************************/
    
    
    /************************** LayersFilterListener ****************************************/
    
    /*
     * 根据图层过滤条件创建或更新临时图层的监听器
     * @author XingJun
     * 2016.08.31
     */
    /**
     * 根据图层过滤条件创建或更新临时图层的监听器
     *
     */
    public static abstract class LayersFilterListener {
        
        /**
         * 当请求服务器创建临时图层成功时调用, 并返回创建的临时图层的图层ID集合,通常一次只创建一个临时图层.
         * <p>根据获得的图层ID, 可以创一个新的LayerView来显示这个图层。如:</p>
         * <pre>
         *     String layerID = layerIDs.get(0);
         *     LayerView layer = new LayerView();
         *     layer.setURL(url);
         *     layer.setLayersID(layerID);
         *     
         *     mapView.addLayer(layer);
         *     mapView.refresh();
         * 
         * </pre>
         * @param layerIDs 临时图层ID集合
         */
        public abstract void onCreated(List<String> layerIDs);
        
        /**
         * 当创建或更新临时图层失败时调用,并返回错误信息
         * @param failMsg 错误信息
         */
        public abstract void onFailed(String failMsg);
        
        /**
         * 当更新临时图层成功时调用, 并返回被更新的临时图层的ID
         * <p>在回调中清除缓存{@link com.supermap.imobilelite.maps.LayerView#clearCache(false)}和刷新地图{@link com.supermap.imobilelite.maps.MapView#refresh()}以便更新当前显示</p>
         * @param layerID 临时图层ID
         */
        public abstract void onUpdated(String layerID);
        
        /************************** LayersFilterListener END ****************************************/
    }
}
