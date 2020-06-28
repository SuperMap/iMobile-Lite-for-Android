package com.supermap.imobilelite.maps;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 多面对象封装类
 * </p>
 * @author ${huangqh}
 * @version ${Version}
 * @since 7.0.0
 * 
 */
public class MultiPolygon {
    private int[] parts;// 多面中每个面点的个数
    private List<Point2D> points;// 组成多面的所有点集合

    public MultiPolygon(int[] parts, List<Point2D> points) {
        super();
        setParts(parts);
        this.points = points;
    }

    public MultiPolygon() {
        super();
    }

    public int[] getParts() {
        return parts;
    }

    public void setParts(int[] parts) {
        if (parts != null && parts.length > 0) {
            // this.parts = new int[parts.length];
            // for (int i = 0; i < parts.length; i++) {
            // this.parts[i] = parts[i];
            // }
            this.parts = Arrays.copyOf(parts, parts.length);
        }
    }

    public List<Point2D> getPoints() {
        return points;
    }

    public void setPoints(List<Point2D> points) {
        this.points = points;
    }

}
