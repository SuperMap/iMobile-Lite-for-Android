package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */
public class Color {
	private int value;
	public Color(int rgb){
		this.value = 0xff000000 | rgb;
	}
	public Color(int r, int g, int b){
		value = ((0xff << 24) | (r<<16) | (g<<8) | b);
	}
	
	public Color(int r, int g, int b,int a){
		value = ((a << 24) | (r<<16) | (g<<8) | b);
	}
	

	public int getR(){
		return (value>>16) & 0xff;
	}
	

	public int getG(){
		return (value>>8) & 0xff;
	}
	

	public int getB(){
		return value & 0xff;
	}
	   
	
	public int getA(){
		return (value>>24) & 0xff;
	}
	
	public int getRGB(){
		return value;
	}
	

	public int getRGBA(){
		return  value;
	}

	public String toColorString(){
		StringBuilder builder = new StringBuilder();
		builder.append("#");
		builder.append(Integer.toHexString(value));
		return builder.toString();
	}
}
