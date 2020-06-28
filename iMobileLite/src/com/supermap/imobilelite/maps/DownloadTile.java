package com.supermap.imobilelite.maps;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadTile {
	
	class TilePos{
		private int mX;
		private int mY;
		private int mZ;
		public TilePos(int x, int y, int z){
			mX = x;
			mY = y;
			mZ = z;
		}
		
		public int getX(){
			return mX;
		}
		public int getY(){
			return mY;
		}
		public int getZ(){
			return mZ;
		}
	}
	private boolean m_ismvt;
	private DownloadTileListener mDownloadTileListener = null;
	private String m_URI = null;
	private String m_CachePath = null;
	private List<TilePos> mTilePos = new ArrayList<TilePos>();
	private ReentrantLock mReentrantLock = new ReentrantLock();
	
	
	public DownloadTile(String URI, String cachePath, boolean ismvt){
		m_ismvt=ismvt;
		m_URI = URI;
		m_CachePath = cachePath;
		if(ismvt){
		File file = new File(m_CachePath+"tiles/tileFeature.mvt");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		}else {
		File file = new File(m_CachePath+"Data/a.dat");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		}
	}
	
	public void setDownloadTileListener(DownloadTileListener listener){
		mDownloadTileListener = listener;
	}

	// 开始下载GL，下载成功后回调
	public void downloadTile(final int x, final int y, final int z){
		new Thread(new Runnable() {
			public void run() {
				try {
					String strURI = m_URI+"?z="+z+"&x="+x+"&y="+y;
					
					// 先请求POI数据
					String filePath = m_CachePath+"Data/POI-"+z+"-"+x+"-"+y+".dat";
					Boolean bSuccessPoi = Tools.getTools().downloadFile(strURI+"&poi=true", filePath);
					if (mDownloadTileListener != null && bSuccessPoi) {
						synchronized (this) {
							mDownloadTileListener.downloadTileFinished(x, y, z, true);
						}
					}
					
					// 再请求Back数据
					filePath = m_CachePath+"Data/"+z+"-"+x+"-"+y+".dat";
					Boolean bSuccessBack = Tools.getTools().downloadFile(strURI+"&poi=false", filePath);
					if (mDownloadTileListener != null && bSuccessBack) {
						synchronized (this) {
							mDownloadTileListener.downloadTileFinished(x, y, z, false);
						}
					}

					// 下载完成移除当前任务标记。
					mReentrantLock.lock();
					for (int i = 0; i < mTilePos.size(); i++) {
						if (mTilePos.get(i).getX()==x && mTilePos.get(i).getY()==y && mTilePos.get(i).getZ()==z) {
							mTilePos.remove(i);
							break;
						}
					}
					mReentrantLock.unlock();
					
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			}).start();

	}

	// 开始下载MVT，下载成功后回调
	public void downloadMVTTile(final int x, final int y, final int z){
		new Thread(new Runnable() {
			public void run() {
				try {
					String strURI = m_URI+"?returnAttributes=true&width=256&height=256"+"&x="+x+"&y="+y+"&z="+z;
					String filePath = m_CachePath+"tiles/"+z+"/"+x+"/"+y+".mvt";
					Tools.getTools().downloadFile(strURI, filePath);

					// 下载完成移除当前任务标记。
					mReentrantLock.lock();
					for (int i = 0; i < mTilePos.size(); i++) {
						if (mTilePos.get(i).getX()==x && mTilePos.get(i).getY()==y && mTilePos.get(i).getZ()==z) {
							mTilePos.remove(i);
							break;
						}
					}
					mReentrantLock.unlock();

				}

				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}
	
}
