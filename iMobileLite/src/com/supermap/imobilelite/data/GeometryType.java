package com.supermap.imobilelite.data;

import java.lang.*;
import java.util.ArrayList;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public final class GeometryType extends Enum {
	
	private static ArrayList<Enum> m_values = new ArrayList<Enum>();
	
	GeometryType(int value, int ugcValue) {
		super(value, ugcValue);
		super.setCustom(true);
	}

	public static final GeometryType GEOPOINT = new GeometryType(1, 1);

	public static final GeometryType GEOLINE = new GeometryType(3, 3);

	public static final GeometryType GEOREGION = new GeometryType(5, 5);

	public static final GeometryType GEOTEXT = new GeometryType(7, 7);
	
	public static final GeometryType GEOCOMPOUND = new GeometryType(1000, 1000);

	public static final GeometryType GEOROUNDRECTANGLE = new GeometryType(13,13);

	public static final GeometryType GEOCIRCLE = new GeometryType(15, 15);

	public static final GeometryType GEOELLIPSE = new GeometryType(20, 20);

	public static final GeometryType GEOPIE = new GeometryType(21, 21);

	public static final GeometryType GEOARC = new GeometryType(24, 24);

	public static final GeometryType GEOELLIPTICARC = new GeometryType(25, 25);

	public static final GeometryType GEOCARDINAL = new GeometryType(27, 27);

	public static final GeometryType GEOCURVE = new GeometryType(28, 28);

	public static final GeometryType GEOBSPLINE = new GeometryType(29, 29);
	
	public static final GeometryType GEOPOINT3D = new GeometryType(101, 101);

	public static final GeometryType GEOLINE3D = new GeometryType(103, 103);

	public static final GeometryType GEOREGION3D = new GeometryType(105, 105);

	public static final GeometryType GEOCHORD = new GeometryType(23, 23);
	
	public static final GeometryType GEORECTANGLE = new GeometryType(12, 12);
	
	public static final GeometryType GEOLINEM = new GeometryType(35, 35);
	
	public static final GeometryType GEOMODEL = new GeometryType(1201, 1201);
	public static final GeometryType GEOPLACEMARK = new GeometryType(108, 108);
	
	public static final GeometryType GEOGRAPHICOBJECT = new GeometryType(3000, 3000);

    public static GeometryType newInstance(int value) {
    	GeometryType geometryType = new GeometryType(value, value);
    	m_values.add(geometryType);
    	m_hashMap.put(GeometryType.class, m_values);
    	return geometryType;
    }
}
