package com.supermap.imobilelite.data;
import java.lang.*;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class DeviceIDType extends Enum {

	protected DeviceIDType(int value, int ugcValue) {
		super(value, ugcValue);
	}
	public static final DeviceIDType MAC = new DeviceIDType(0,0);
	public static final DeviceIDType IMEI = new DeviceIDType(1,1);
	public static final DeviceIDType UUID = new DeviceIDType(2,2);
	public static final DeviceIDType TMIMEI = new DeviceIDType(3,3);
}
