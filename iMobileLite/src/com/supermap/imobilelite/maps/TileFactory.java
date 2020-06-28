package com.supermap.imobilelite.maps;

abstract interface TileFactory {
    public abstract int getTileSize();

    public abstract ProjectionUtil getProjection();

    /*public abstract int getMaxZoomLevel();
    
    public abstract void setMaxZoomLevel(int maxZoomLevel);
    
    public abstract int getMinZoomLevel();*/

    public abstract Tile buildTile(Tile paramTile, int paramInt, TileType paramTileType);

    public abstract Tile buildTile(Point2D paramGeoPoint, int paramInt, TileType paramTileType);

    public abstract Tile buildTile(int paramInt1, int paramInt2, int paramInt3, TileType paramTileType);

    public abstract void setType(TileType paramTileType);

    public abstract TileType getTileType();

    public abstract MapProvider getMapProvider();

    public abstract boolean isSupportedTileType(TileType paramTileType);

    public abstract void setBaseUrl(String baseUrl);

    // public abstract void setBaseScale(double scale);

}