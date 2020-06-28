package com.supermap.imobilelite.mapsamples.dialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.supermap.imobilelite.mapsamples.R;
import com.supermap.imobilelite.mapsamples.util.Constants;
import com.supermap.imobilelite.samples.domain.MapResourceInfo;
import com.supermap.imobilelite.samples.service.PreferencesService;

public class BaseUrlDialog extends Dialog{

	private Context context;
	
	private EditText serviceUrlEditText;
	
	private EditText instanceEditText;
	
	private PreferencesService service;
	
	private static final String BASEURL_DIALOG = "baseurl_dialog";
	
	private MapManageDialog mapManageDialog;
	
	private String currentMapName = "";
	
	public BaseUrlDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	public BaseUrlDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.baseurl_dialog);
		
		mapManageDialog = (MapManageDialog) context;
		
		service = new PreferencesService(context);
		
		Button buttonConfirm = (Button) this.findViewById(R.id.bd_btn_confirm);
		buttonConfirm.setOnClickListener(new ConfirmButtonClick());
		
		Button buttonCancel = (Button) this.findViewById(R.id.bd_btn_cancel);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaseUrlDialog.this.dismiss();
			}
		});
		
		serviceUrlEditText = (EditText) this.findViewById(R.id.bd_service_url_et);
		instanceEditText = (EditText) this.findViewById(R.id.bd_instance_et);

	}
	
	public void refresh(MapResourceInfo mapResourceInfo) {
		if (mapResourceInfo != null) {
			serviceUrlEditText.setText(mapResourceInfo.getService());
			// 做一次解码处理，mapResourceInfo中存放的是编码后的uri，
			// 显示在对话框中用户会产生疑惑
			String instanceUri = mapResourceInfo.getInstance();
			// getLastPathSegment()会返回解码的地图名
			String mapName = Uri.parse(instanceUri).getLastPathSegment();
			String uri = instanceUri.substring(0, instanceUri.lastIndexOf('/')) + "/" + mapName;
			instanceEditText.setText(uri);
			currentMapName = mapResourceInfo.getMapName();
		}
	}
	
    /**
     * 列表项点击事件
     */
	class ConfirmButtonClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			String serviceUrl = serviceUrlEditText.getText().toString();
			String instanceUrl = instanceEditText.getText().toString();
			if (!serviceUrl.equals("") && !instanceUrl.equals("")) {
				service.saveBaseUrl(BASEURL_DIALOG, serviceUrl, instanceUrl);
				String mapName = Uri.parse(instanceUrl).getLastPathSegment();
				try {
					// 对地图名进行一次编码，解决中文地图名问题
					instanceUrl = instanceUrl.substring(0, instanceUrl.length()
							- mapName.length())
							+ URLEncoder.encode(mapName, Constants.UTF8);
				} catch (UnsupportedEncodingException e) {
					Log.w(Constants.ISERVER_TAG, "Encode map name error", e);
				}
				MapResourceInfo mapResourceInfo = new MapResourceInfo();
				mapResourceInfo.setMapName(currentMapName);
				mapResourceInfo.setService(serviceUrl);
				mapResourceInfo.setInstance(instanceUrl);
				mapManageDialog.updateMapResourceInfo(mapResourceInfo);
				BaseUrlDialog.this.dismiss();
			} else {
				Log.i(Constants.ISERVER_TAG, "Map url is needed");
			}
		}
	}
}
