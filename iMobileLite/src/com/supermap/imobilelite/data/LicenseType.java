package com.supermap.imobilelite.data;

import java.lang.*;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class LicenseType extends Enum {

	protected LicenseType(int value, int ugcValue) {
		super(value, ugcValue);
	}
	public static final LicenseType DEVICEID = new LicenseType(0,0); 
	public static final LicenseType UUID = new LicenseType(1,1); 
}
