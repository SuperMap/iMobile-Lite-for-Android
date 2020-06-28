package com.supermap.imobilelite.samples.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService {

	private Context context;
	
	public PreferencesService(Context context) {
		super();
		this.context = context;
	}
	
	public void saveBaseUrl(String fileName, String service, String instance){
		SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("service", service);
		editor.putString("instance", instance);
		editor.commit();
	}
	
	public Map<String, String> getBaseUrl(String fileName) {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		params.put("service", preferences.getString("service", ""));
		params.put("instance", preferences.getString("instance", ""));
		
		return params;
	}
	
	public void saveIServerUrl(String fileName, String iserverUrl) {
		SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("iserverurl", iserverUrl);
		editor.commit();
	}
	
	public Map<String, String> getIServerUrl(String fileName) {
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		params.put("iserverurl", preferences.getString("iserverurl", ""));
		
		return params;
	}
	
	/**
	 * 将阅读示范程序说明的偏好设置（不再提醒）保存到readme_config中
	 * @param demoName 指定的示范程序
	 * @param isEnable 
	 */
	public void saveReadmeEnable(String demoName, boolean isEnable) {
		SharedPreferences preferences = context.getSharedPreferences("readme_config", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(demoName, isEnable);
		editor.commit();
	}
	
	/**
	 * 从readme_config中获取阅读示范程序说明的偏好设置（不再提醒）
	 * @param demoName 指定的示范程序
	 * @return
	 */
	public Map<String, Boolean> getReadmeEnable(String demoName) {
		Map<String, Boolean> params = new HashMap<String, Boolean>();
		SharedPreferences preferences = context.getSharedPreferences("readme_config", Context.MODE_PRIVATE);
		params.put("readme", preferences.getBoolean(demoName, true));
		
		return params;
	}
}
