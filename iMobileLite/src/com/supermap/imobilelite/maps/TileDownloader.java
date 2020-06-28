package com.supermap.imobilelite.maps;

abstract interface TileDownloader {
	public abstract void beginQueue();

	public abstract void queueTile(Tile paramTile);

	public abstract void endQueue();

	public abstract void clearQueue();

	public abstract void finishedDownload(Tile paramTile);

	public abstract void destroy();
}