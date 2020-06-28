package com.supermap.imobilelite.mapsamples.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.util.Constants;
import com.supermap.imobilelite.mapsamples.util.HttpUtil;
import com.supermap.imobilelite.samples.domain.MapResourceInfo;
import com.supermap.imobilelite.samples.service.PreferencesService;

public class IServerUrlDialog extends Dialog{

	private Context context;
	
	private EditText iserverUrlEditText;
	
	private PreferencesService service;
	
	private static final String ISERVERURL_DIALOG = "iserverurl_dialog";
	
	private MapManageDialog mapManageDialog;
	
	private static final int ADD_SUCCESS = 1;
	
	private static final int FAILED = 2;
	
	private Handler handler;
	
	private Dialog progressDialog;
	
	public IServerUrlDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	public IServerUrlDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.iserverurl_dialog);
		
		mapManageDialog = (MapManageDialog) context;
		handler = new GetJSONFinished();
		service = new PreferencesService(context);
		
		Button buttonConfirm = (Button) this.findViewById(R.id.iserverurl_dialog_confirm_btn);
		buttonConfirm.setOnClickListener(new ConfirmButtonClick());
		
		Button buttonCancel = (Button) this.findViewById(R.id.iserverurl_dialog_cancel_btn);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IServerUrlDialog.this.dismiss();
			}
		});
		
		iserverUrlEditText = (EditText) this.findViewById(R.id.iserverurl_dialog_url_et);
		
		// 当是编辑时就用选中的第一个CheckBox对应的MapResourceInfo来为文本框赋值
		Map<String, String> params = service.getIServerUrl(ISERVERURL_DIALOG);
		String preferenceService = params.get("iserverurl");
		if (!preferenceService.trim().equals("")) {
			iserverUrlEditText.setText(preferenceService);
		}
	}
	
    void showProgressDialog() {
    	progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.treating), 
    			context.getResources().getString(R.string.iserverurldialog_getmaps), true);
    }
	
    /**
     * 列表项点击事件
     */
	class ConfirmButtonClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			String iserverUrl = iserverUrlEditText.getText().toString();
			if (!iserverUrl.equals("")) {
				service.saveIServerUrl(ISERVERURL_DIALOG, iserverUrl);
				showProgressDialog();
				new Thread(new GetJSONRunnable(iserverUrl)).start();
			} else {
				Log.i(Constants.ISERVER_TAG, "IServer url is needed");
			}
		}
	}
	
	/**
	 * 从服务中获取全幅地图的JSON数据 
	 *
	 */
	class GetJSONRunnable implements Runnable {
		
		private String iserverUrl;
		
		public GetJSONRunnable(String iserverUrl) {
			this.iserverUrl = iserverUrl;
		}
		
		@Override
		public void run() {
			JSONArray jsonArray = HttpUtil.getServicesJSONArray(iserverUrl); 
			ArrayList<MapResourceInfo> mapResourceInfos = new ArrayList<MapResourceInfo>();
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						if (jsonObject.getString("interfaceType").equals("com.supermap.services.rest.RestServlet") &&
								jsonObject.getString("componentType").equals("com.supermap.services.components.impl.MapImpl")) {
							String restUrl = jsonObject.getString("url");
							String mapsUrl = restUrl + "/maps.json";
							JSONArray mapsArray = HttpUtil.getMapsJSONArray(mapsUrl);
							for (int j = 0; j < mapsArray.length(); j++) {
								JSONObject mapJsonObject = mapsArray.getJSONObject(j); 
								MapResourceInfo mapResourceInfo = new MapResourceInfo();
								mapResourceInfo.setMapName(mapJsonObject.getString("name"));
								String service = iserverUrl + "/services";
								mapResourceInfo.setService(service);
								String instance = mapJsonObject.getString("path").substring(service.length());
								mapResourceInfo.setInstance(instance);
								if (!mapResourceInfos.contains(mapResourceInfo)) {
									mapResourceInfos.add(mapResourceInfo);
								}
							}
						}
					} catch (JSONException e) {
						Log.w(Constants.ISERVER_TAG, "Get object from json error");
					}
				}
			}
			
			Message msg = new Message();
			if (mapResourceInfos.size() != 0) {
				msg.what = ADD_SUCCESS;
				msg.obj = mapResourceInfos;
			} else {
				Log.w(Constants.ISERVER_TAG, "Get entire image map scale error");
				msg.what = FAILED;
			}
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 获取全幅数据后启动界面 
	 *
	 */
	class GetJSONFinished extends Handler {
		@Override
    	public void handleMessage(Message msg) {
			List<MapResourceInfo> mapResourceInfos = null;
			if (msg.obj != null) {
				mapResourceInfos =  (List<MapResourceInfo>) msg.obj;
			}
			progressDialog.dismiss();
			switch (msg.what) {
			case ADD_SUCCESS:
				mapManageDialog.addMapResourceInfos(mapResourceInfos);
				IServerUrlDialog.this.dismiss();
	    		super.handleMessage(msg);
				break;
			case FAILED:
				Toast.makeText(context, R.string.get_map_param_failed, Toast.LENGTH_SHORT).show();
				break;
			default:
				IServerUrlDialog.this.dismiss();
				break;
			}
    	}
	}
}
