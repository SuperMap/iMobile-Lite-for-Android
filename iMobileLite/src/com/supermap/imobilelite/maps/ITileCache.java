package com.supermap.imobilelite.maps;

abstract interface ITileCache {
	public abstract Tile getTile(Tile paramTile);

	public abstract void addTile(Tile paramTile);

	public abstract void removeTile(Tile paramTile);

	public abstract boolean contains(Tile paramTile);

	public abstract void clear();

	public abstract int size();

	public abstract void destroy();
}