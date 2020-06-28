package com.supermap.imobilelite.maps;

public interface DownloadTileListener {

	// 下载完成回调
		public void downloadTileFinished(int x, int y, int z, Boolean poi);
}
