package com.supermap.imobilelite.maps;

public interface DownloadConfigeListener {

	// 下载完成回调
	public void downloadConfigeFinished(String URL, String Path);
	public void downloadMVTConfigeFinished(String URL, String Path);
}
