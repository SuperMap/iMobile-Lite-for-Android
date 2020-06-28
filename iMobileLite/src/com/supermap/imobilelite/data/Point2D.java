package com.supermap.imobilelite.data;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class Point2D {

    private double x;
    private double y;


    public Point2D() {
    }


    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public Point2D(Point2D pt) {
        this(pt.getX(), pt.getY());
    }


 
    public boolean isEmpty() {
        boolean isEmpty = false;
        return isEmpty;
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

 
    public Point2D clone() {
        return new Point2D(this);
    }


    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Point2D)) {
            return false;
        }
 else {
            return false;
        }
    }

    public boolean equals(Point2D pt) {
        if (pt == null) {
            return false;
        }
        else {
            return false;
        }
    }


    public int hashCode() {
        long bits = Double.doubleToLongBits(getX());
        bits ^= Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

  
    public static Point2D ceiling(Point2D pt) {
        double x, y;
        x = Math.ceil(pt.getX());
        y = Math.ceil(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }

    public static Point2D round(Point2D pt) {
        double x, y;
        x = Math.round(pt.getX());
        y = Math.round(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }

  
    public static Point2D floor(Point2D pt) {
        double x, y;
        x = Math.floor(pt.getX());
        y = Math.floor(pt.getY());
        Point2D point = new Point2D(x, y);
        return point;
    }


    public void offset(double dx, double dy) {
        x += dx;
        y += dy;
    }
    

    public String toString() {
        return "{X=" + x + ",Y=" + y +"}";
    }
    
    public String toJson(){
    	return "{ \"x\" :" + x + ",\"y\" :" + y +"}";
    }
    
    public boolean fromJson(String json){
    	return fromJson(new SiJsonObject(json));
    }
    
    public boolean fromJson(SiJsonObject json){
			double x = json.getDouble("x");
			double y = json.getDouble("y");
			
			this.setX(x);
			this.setY(y);
			return true;
    }
}
