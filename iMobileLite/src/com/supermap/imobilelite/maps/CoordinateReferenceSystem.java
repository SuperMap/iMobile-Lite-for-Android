package com.supermap.imobilelite.maps;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * 坐标参考系类。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class CoordinateReferenceSystem {
    /**
     * <p>
     * 坐标参考系对应地球椭球体长半轴半径。
     * </p>
     */
    public double datumAxis;
    /**
     * <p>
     * 坐标参考系对应地球椭球体扁率。
     * </p>
     */
    public double flatten;
    /**
     * <p>
     * 坐标参考系对应的坐标单位。
     * </p>
     */
    public String unit;
    /**
     * <p>
     * 当前坐标参考系对应的的 Well Konwn ID 值（简称 WKID）。
     * </p>
     */
    public int wkid;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public CoordinateReferenceSystem() {
        this.wkid = -1000;
        this.unit = "meter";
    }

    /**
     * <p>
     * 比较指定对象与当前坐标参考系对象是否相等。
     * </p>
     * @param obj 待比较的对象。
     * @return true表示相等，false表示不相等。
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof CoordinateReferenceSystem)) {
            return false;
        }
        CoordinateReferenceSystem rhs = (CoordinateReferenceSystem) obj;
        if (rhs.wkid == this.wkid) {
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(55, 57).append(datumAxis).append(flatten).append(unit).append(wkid).toHashCode();
    }
}
