package com.supermap.imobilelite.data;

import java.lang.*;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class Unit extends Enum {
	protected Unit(int value, int ugcValue) {
		super(value, ugcValue);
	}


	public static final Unit MILIMETER = new Unit(10, 10);

	public static final Unit CENTIMETER = new Unit(100, 100);

	public static final Unit DECIMETER = new Unit(1000, 1000);

	public static final Unit METER = new Unit(10000, 10000);

	public static final Unit KILOMETER = new Unit(10000000, 10000000);

	public static final Unit INCH = new Unit(254, 254);

	public static final Unit FOOT = new Unit(3048, 3048);

	public static final Unit YARD = new Unit(9144, 9144);

	public static final Unit MILE = new Unit(16090000, 16090000);

	public static final Unit SECOND = new Unit(485 + 1000000000,
			485 + 1000000000);

	public static final Unit MINUTE = new Unit(29089 + 1000000000,
			29089 + 1000000000);

	public static final Unit DEGREE = new Unit(1745329 + 1000000000,
			1745329 + 1000000000);

	public static final Unit RADIAN = new Unit(100000000 + 1000000000,
			100000000 + 1000000000);

	protected static boolean isCoordUnit(Unit unit) {
		boolean isCoordUnit = false;

		if (unit == Unit.MILIMETER) {
			isCoordUnit = true;
		} else if (unit == Unit.CENTIMETER) {
			isCoordUnit = true;
		} else if (unit == Unit.DECIMETER) {
			isCoordUnit = true;
		} else if (unit == Unit.METER) {
			isCoordUnit = true;
		} else if (unit == Unit.KILOMETER) {
			isCoordUnit = true;
		} else if (unit == Unit.INCH) {
			isCoordUnit = true;
		} else if (unit == Unit.FOOT) {
			isCoordUnit = true;
		} else if (unit == Unit.YARD) {
			isCoordUnit = true;
		} else if (unit == Unit.MILE) {
			isCoordUnit = true;
		} else if (unit == Unit.SECOND) {
			isCoordUnit = true;
		} else if (unit == Unit.MINUTE) {
			isCoordUnit = true;
		} else if (unit == Unit.DEGREE) {
			isCoordUnit = true;
		} else if (unit == Unit.RADIAN) {
			isCoordUnit = true;
		}
		return isCoordUnit;
	}

	protected static boolean isDistanceUnit(Unit unit) {
		boolean isDistanceUnit = false;

		if (unit == Unit.MILIMETER) {
			isDistanceUnit = true;
		} else if (unit == Unit.CENTIMETER) {
			isDistanceUnit = true;
		} else if (unit == Unit.DECIMETER) {
			isDistanceUnit = true;
		} else if (unit == Unit.METER) {
			isDistanceUnit = true;
		} else if (unit == Unit.KILOMETER) {
			isDistanceUnit = true;
		} else if (unit == Unit.INCH) {
			isDistanceUnit = true;
		} else if (unit == Unit.FOOT) {
			isDistanceUnit = true;
		} else if (unit == Unit.YARD) {
			isDistanceUnit = true;
		} else if (unit == Unit.MILE) {
			isDistanceUnit = true;
		}
		return isDistanceUnit;
	}


	@Override
	public String toString() {
		String result = "";

		if (this == Unit.MILIMETER) {
			result = InternalResource.loadString("",
					InternalResource.MILIMETER, InternalResource.BundleName);
		} else if (this == Unit.CENTIMETER) {
			result = InternalResource.loadString("",
					InternalResource.CENTIMETER, InternalResource.BundleName);
		} else if (this == Unit.DECIMETER) {
			result = InternalResource.loadString("",
					InternalResource.DECIMETER, InternalResource.BundleName);
		} else if (this == Unit.METER) {
			result = InternalResource.loadString("",
					InternalResource.METER, InternalResource.BundleName);
		} else if (this == Unit.KILOMETER) {
			result = InternalResource.loadString("",
					InternalResource.KILOMETER, InternalResource.BundleName);
		} else if (this == Unit.INCH) {
			result = InternalResource.loadString("",
					InternalResource.INCH, InternalResource.BundleName);
		} else if (this == Unit.FOOT) {
			result = InternalResource.loadString("",
					InternalResource.FOOT, InternalResource.BundleName);
		} else if (this == Unit.YARD) {
			result = InternalResource.loadString("",
					InternalResource.YARD, InternalResource.BundleName);
		} else if (this == Unit.MILE) {
			result = InternalResource.loadString("",
					InternalResource.MILE, InternalResource.BundleName);
		} else if (this == Unit.SECOND) {
			result = InternalResource.loadString("",
					InternalResource.SECOND, InternalResource.BundleName);
		} else if (this == Unit.MINUTE) {
			result = InternalResource.loadString("",
					InternalResource.MINUTE, InternalResource.BundleName);
		} else if (this == Unit.DEGREE) {
			result = InternalResource.loadString("",
					InternalResource.DEGREE, InternalResource.BundleName);
		} else if (this == Unit.RADIAN) {
			result = InternalResource.loadString("",
					InternalResource.RADIAN, InternalResource.BundleName);
		}
		return result;
	}
}
