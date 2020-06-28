package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class PrjFileVersion extends Enum {
	private PrjFileVersion(int value, int ugcValue) {
		super(value, ugcValue);
	}
	
	public static final PrjFileVersion SFC60 = new PrjFileVersion(1, 1);
	
	public static final PrjFileVersion UGC60 = new PrjFileVersion(2, 2); 

}
