package com.supermap.imobilelite.maps;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class DownloadConfige {
	
	private DownloadConfigeListener mDownloadConfigeListener = null;
	private String m_URI = null;
	private String m_CachePath = null;
	
	public DownloadConfige(String URI, String cachePath){
		m_URI = URI;
		m_CachePath = cachePath;		
	}

	public void setDownloadConfigeListener(DownloadConfigeListener listener){
		mDownloadConfigeListener = listener;
	}
	
	// 开始GL下载，下载成功后回调
	public void downloadConfige(){
		new Thread(new Runnable() {
			public void run() {
				try {
					Boolean bSuccess = true;
					Tools.getTools().downloadFile(m_URI+"/Confige.GLData?type=1", m_CachePath+"VectorCache.xml");
					Tools.getTools().downloadFile(m_URI+"/Confige.GLData?type=2", m_CachePath+"VectorCache.index");
					Tools.getTools().downloadFile(m_URI+"/Confige.GLData?type=3", m_CachePath+"mark_symbol.idx");
					Tools.getTools().downloadFile(m_URI+"/Confige.GLData?type=4", m_CachePath+"mark_symbol.dat");
					Tools.getTools().downloadFile(m_URI+"/Confige.GLData?type=5", m_CachePath+"fill_symbol.idx");
					Tools.getTools().downloadFile(m_URI+"/Confige.GLData?type=6", m_CachePath+"fill_symbol.dat");
					if (mDownloadConfigeListener != null && bSuccess) {
						mDownloadConfigeListener.downloadConfigeFinished(m_URI, m_CachePath);
					}
				}
//				catch (ClientProtocolException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
				 catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			}).start();

	}
	// 开始MVT下载，下载成功后回调
	public void downloadMVTConfige(){
		Thread thread=new Thread(new Runnable() {
			public void run() {

				try {
					Boolean bSuccess = true;
					Tools.getTools().downloadFile(m_URI+"/tilefeature/mvtsprites/sprite.json", m_CachePath+"sprites/"+"sprite.json");
					Tools.getTools().downloadFile(m_URI+"/tilefeature/mvtsprites/sprite.png", m_CachePath+"sprites/"+"sprite.png");
					Tools.getTools().downloadFile(m_URI+"/tilefeature/mvtsprites/sprite@2x.json", m_CachePath+"sprites/"+"sprite@2x.json");
					Tools.getTools().downloadFile(m_URI+"/tilefeature/mvtsprites/sprite@2x.png", m_CachePath+"sprites/"+"sprite@2x.png");
					Tools.getTools().downloadFile(m_URI+"/tileFeature/vectorstyles.json?type=MapBox_GL&styleonly=true", m_CachePath+"styles/"+"style.json");
					Tools.getTools().downloadFile(m_URI+".json", m_CachePath+"styles/"+"sci.json");
					if (mDownloadConfigeListener != null && bSuccess) {
						mDownloadConfigeListener.downloadMVTConfigeFinished(m_URI+"/tileFeature.mvt", m_CachePath);
					}
				}
//				catch (ClientProtocolException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	// 开始MVT下载，下载成功后回调
	public void downloadMVTConfige2(){
		Thread thread=new Thread(new Runnable() {
			public void run() {
				try {
					Boolean bSuccess = true;
					Tools.getTools().downloadFile(m_URI, m_CachePath+"styles/"+"style.json");

						downloadConfige(m_CachePath+"styles/"+"style.json");

					if (mDownloadConfigeListener != null && bSuccess) {
//                        String info=ReadStyle(m_CachePath+"styles/"+"style.json");
//                        JSONObject jsonObject= JSON.parseObject(info);
//                        JSONObject json_source=jsonObject.getJSONObject("sources");
//                        String name= jsonObject.getString("name");
//                        JSONObject json_tile=json_source.getJSONObject(name);
//                        String tile= json_tile.getString("tiles");
//						int b=tile.lastIndexOf("\"");
//						String tileservice=tile.substring(2,b);
						mDownloadConfigeListener.downloadMVTConfigeFinished(tileservice+"/tileFeature.mvt", m_CachePath);
					}
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	private String ReadstyleFile(String path){
		File file=new File(path);
			try {
				FileReader fileReader=new FileReader(file);
				Reader reader=new InputStreamReader(new FileInputStream(file),"utf-8");
				int ch=0;
				StringBuffer sb=new StringBuffer();
				while ((ch=reader.read())!=-1){
					sb.append((char)ch);
				}
				fileReader.close();
				reader.close();
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	private String tileservice=null;
	private void downloadConfige(String path){
		String info=ReadstyleFile(path);
		JSONObject jsonObject= JSON.parseObject(info);
		JSONObject json_source=jsonObject.getJSONObject("sources");
		String name= jsonObject.getString("name");
		JSONObject json_tile=json_source.getJSONObject(name);
		String tile= json_tile.getString("tiles");
		int b=tile.lastIndexOf("\"");
		tileservice=tile.substring(2,b);
		String sprite=jsonObject.getString("sprite");
		try {
			Tools.getTools().downloadFile(sprite+"/sprite.json", m_CachePath+"sprites/"+"sprite.json");
			Tools.getTools().downloadFile(sprite+"/sprite.png", m_CachePath+"sprites/"+"sprite.png");
			Tools.getTools().downloadFile(sprite+"/sprite@2x.json", m_CachePath+"sprites/"+"sprite@2x.json");
			Tools.getTools().downloadFile(sprite+"/sprite@2x.png", m_CachePath+"sprites/"+"sprite@2x.png");
			Tools.getTools().downloadFile(tileservice+".json", m_CachePath+"styles/"+"sci.json");
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
