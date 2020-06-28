package com.supermap.imobilelite.data;

import java.lang.*;

/**
 * <p>
 * imobile移植类
 * </p>
 */


public class Module extends Enum {
	protected Module(int value, int ugcValue) {
		super(value, ugcValue);
	}
	
	
	public static Module Core_Dev = new Module(0x01, 0x01);
	

	public static Module Core_Runtime = new Module(0x02, 0x02);
	

	public static Module Navigation_Dev = new Module(0x04, 0x04);
	

	public static Module Navigation_Runtime = new Module(0x08, 0x08);
	

	public static Module Realspace_Dev = new Module(0x10, 0x10);
	

	public static Module Realspace_Runtime = new Module(0x20, 0x20);
	

	public static Module Plot_Dev = new Module(0x40, 0x40);
	

	public static Module Plot_Runtime = new Module(0x80, 0x80);
	

	public static Module Industry_Navigation_Dev = new Module(0x100, 0x100);
	public static Module Industry_Navigation_Runtime = new Module(0x200, 0x200);
	

	public static Module Indoor_Navigation_Dev = new Module(0x400, 0x400);
	public static Module Indoor_Navigation_Runtime = new Module(0x800, 0x800);


	public static Module Plot3D_Dev = new Module(0x1000, 0x1000); 
	public static Module Plot3D_Runtime = new Module(0x2000, 0x2000);


	public static Module Realspace_Analyst_Dev = new Module(0x4000, 0x4000);
	public static Module Realspace_Analyst_Runtime = new Module(0x8000, 0x8000);

	
	public static Module Realspace_Effect_Dev = new Module(0x40000, 0x40000);
	public static Module Realspace_Effect_Runtime = new Module(0x80000, 0x80000);



	int getEnumValue(){
		return this.getUGCValue();
	}
}