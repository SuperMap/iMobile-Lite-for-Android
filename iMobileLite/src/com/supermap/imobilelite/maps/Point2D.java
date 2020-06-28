package com.supermap.imobilelite.maps;

import java.io.Serializable;

/**
 * <p>
 * 二维点对象。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class Point2D implements Serializable {
    private static final long serialVersionUID = -1043960411441517653L;
    /**
     * <p>
     * 点对象的y坐标。
     * </p>
     */
    public double y;
    /**
     * <p>
     * 点对象的x坐标。
     * </p>
     */
    public double x;

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param point2D 二维点对象。
     */
    public Point2D(Point2D point2D) {
        if (point2D == null) {
            throw new IllegalArgumentException("geoPoint parameter is null.");
        }
        this.x = point2D.getX();
        this.y = point2D.getY();
    }

    /**
     * <p>
     * 根据 x,y 构造 Point2D 对象。
     * </p>
     * @param x x 坐标。
     * @param y y 坐标。
     */
    public Point2D(double x, double y) {
        // setLatitudeE6(new Double(latitude * 1000000.0D).intValue());
        // setLongitudeE6(new Double(longitude * 1000000.0D).intValue());
        this.x = x;
        this.y = y;
    }

    // public double getLatitudeE6() {
    // return this.latitude;
    // }

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public Point2D() {
        super();
    }

    /**
     * <p>
     * 获取 y 坐标值。
     * </p>
     * @return 返回 y 坐标。
     */
    public double getY() {
        return this.y;
    }

    // private void setLatitudeE6(int latitude) {
    // this.latitude = latitude;
    // }

    // public double getLongitudeE6() {
    // return this.longitude;
    // }

    /**
     * <p>
     * 获取 x 坐标值。
     * </p>
     * @return 返回 x 坐标。
     */
    public double getX() {
        return this.x;
    }

    // private void setLongitudeE6(int longitude) {
    // while (longitude > 180000000.0D) {
    // longitude = (int) (longitude - 360000000.0D);
    // }
    // while (longitude < -180000000.0D) {
    // longitude = (int) (longitude + 360000000.0D);
    // }

    // this.longitude = longitude;
    // }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[" + this.x + "," + this.y + "]";
    }

    /**
     * <p>
     * 返回当前二维点对象的哈希码。
     * </p>
     * @return 当前二维点对象的哈希码。
     */
    public int hashCode() {
        String result = new Double(this.y).toString() + new Double(this.x).toString();
        return result.hashCode();
    }

    /**
     * <p>
     * 判断指定对象跟当前 {@link Point2D} 对象是否相等。
     * </p>
     * @param obj 跟本对象进行比较的 Java 对象。
     * @return 指定对象跟本对象相等，则返回 true，否则，返回 false。
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point2D other = (Point2D) obj;
        if (this.y != other.y) {
            return false;
        }
        return this.x == other.x;
    }
}