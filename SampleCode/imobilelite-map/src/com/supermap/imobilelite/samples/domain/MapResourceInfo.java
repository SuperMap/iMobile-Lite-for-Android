package com.supermap.imobilelite.samples.domain;

public class MapResourceInfo {

	// 地图名
	private String mapName;

	// 服务地址
	private String service;

	// 实例地址
	private String instance;

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof MapResourceInfo) {
			MapResourceInfo that = (MapResourceInfo) o;
			return this.mapName.equals(that.mapName)
					&& this.service.equals(that.service);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result;
		int hashCode1 = (mapName != null ? mapName.hashCode() : 0);
		int hashCode2 = (service != null ? service.hashCode() : 0);
		int hashCode3 = (instance != null ? instance.hashCode() : 0);
		result = 31 * hashCode1 + 32 * hashCode2 + 33 * hashCode3;
		return result;
	}
}
