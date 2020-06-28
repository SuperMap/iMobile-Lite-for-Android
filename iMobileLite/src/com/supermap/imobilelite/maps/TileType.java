package com.supermap.imobilelite.maps;

class TileType {
	public static TileType MAP = new TileType("map");
	public static TileType SAT = new TileType("sat");
	public static TileType HYB = new TileType("hyb");
	public static TileType SATHYB = new TileType("sathyb");
	public static TileType TRAFFIC = new TileType("traffic");
	final String value;

	public TileType(String value) {
		this.value = value;
	}

	public static TileType valueOf(String type) {
		if (MAP.value.equalsIgnoreCase(type))
			return MAP;
		if (SAT.value.equalsIgnoreCase(type))
			return SAT;
		if (HYB.value.equalsIgnoreCase(type))
			return HYB;
		if (SATHYB.value.equalsIgnoreCase(type))
			return SATHYB;
		if (TRAFFIC.value.equalsIgnoreCase(type))
			return TRAFFIC;
		return null;
	}

	public String toString() {
		return this.value;
	}

	public String value() {
		return this.value;
	}
}