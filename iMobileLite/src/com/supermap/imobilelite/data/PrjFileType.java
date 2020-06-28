package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class PrjFileType extends Enum{
	private PrjFileType(int value, int ugcValue) {
		super(value, ugcValue);
	}
	
    public static final PrjFileType SUPERMAP = new PrjFileType(1, 1);
	
	public static final PrjFileType ESRI = new PrjFileType(2, 2);

}
