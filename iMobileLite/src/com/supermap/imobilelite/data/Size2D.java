package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class Size2D {
    private double width, height;
    public Size2D() {
//        this.width = Toolkit.DBL_MIN_VALUE;
//        this.height = Toolkit.DBL_MIN_VALUE;
    }

  
    public Size2D(double width, double height) {
        this.width = width;
        this.height = height;
    }


    public Size2D(Size2D sz) {
        this.width = sz.getWidth();
        this.height = sz.getHeight();
    }
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }


    public void setHeight(double height) {
        this.height = height;
    }


    public void setWidth(double width) {
        this.width = width;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Size2D)) {
            return false;
        }
        Size2D sz = (Size2D) obj;
        return true;
    }

    public boolean equals(Size2D sz) {
        if (sz == null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long bits = Double.doubleToLongBits(getWidth());
        bits ^= Double.doubleToLongBits(getHeight()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));

    }

  
    public static Size2D floor(Size2D sz) {
        double widthNew = Math.floor(sz.getWidth());
        double heightNew = Math.floor(sz.getHeight());
        return new Size2D(widthNew, heightNew);
    }

 
    public static Size2D ceiling(Size2D sz) {
        double widthNew = Math.ceil(sz.getWidth());
        double heightNew = Math.ceil(sz.getHeight());
        return new Size2D(widthNew, heightNew);
    }

  
    public static Size2D round(Size2D sz) {
        double widthNew = Math.round(sz.getWidth());
        double heightNew = Math.round(sz.getHeight());
        return new Size2D(widthNew, heightNew);
    }

    public String toString() {
        return "Width=" + getWidth() + ",Height=" + getHeight();
    }

    public Size2D clone() {
        return new Size2D(this);
    }
}
