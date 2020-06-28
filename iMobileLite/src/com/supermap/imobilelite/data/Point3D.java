package com.supermap.imobilelite.data;


/**
 * <p>
 * imobile移植类
 * </p>
 */

public class Point3D {
	private double x;

	private double y;

	private double z;

	public Point3D() {
	}


	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3D(Point3D pt) {
		this(pt.getX(), pt.getY(), pt.getZ());
	}

	public double getX() {
		return x;
	}

	public void setX(double value) {
		x = value;
	}

	public double getY() {
		return y;
	}

	public void setY(double value) {
		y = value;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double value) {
		z = value;
	}

	public Point3D clone() {
		return new Point3D(this);
	}

    public String toString() {
        return "{X=" + x + ",Y=" + y + ",Z=" + z +"}";
    }
	
    public String toJson(){
    	return "{ \"x\" :" + x + ",\"y\" :" + y + ",\"z\" :" + z +"}";
    }
    
    public boolean fromJson(String json){
    	return fromJson(new SiJsonObject(json));
    }
    
    public boolean fromJson(SiJsonObject json){
			double x = json.getDouble("x");
			double y = json.getDouble("y");
			double z = json.getDouble("z");
			
			this.setX(x);
			this.setY(y);
			this.setZ(z);
			return true;
    }
}
