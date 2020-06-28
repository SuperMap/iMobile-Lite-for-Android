package com.supermap.imobilelite.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @作者 not attributable
 * @version 2.0
 */
public class GeoPrimeMeridianType extends Enum {
    private GeoPrimeMeridianType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final GeoPrimeMeridianType PRIMEMERIDIAN_USER_DEFINED = new
            GeoPrimeMeridianType( -1, -1);
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_GREENWICH = new
            GeoPrimeMeridianType(8901, 8901);
            /*   0~00~00"     E                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_LISBON = new
            GeoPrimeMeridianType(8902, 8902);
            /*   9~07'54".862 W                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_PARIS = new
            GeoPrimeMeridianType(8903, 8903);
            /*   2~20'14".025 E                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_BOGOTA = new
            GeoPrimeMeridianType(8904, 8904);
            /*  74~04'51".3   W                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_MADRID = new
            GeoPrimeMeridianType(8905, 8905);
            /*   3~41'16".58  W                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_ROME = new
            GeoPrimeMeridianType(8906, 8906);
            /*  12~27'08".4   E                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_BERN = new
            GeoPrimeMeridianType(8907, 8907);
            /*   7~26'22".5   E                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_JAKARTA = new
            GeoPrimeMeridianType(8908, 8908);
            /* 106~48'27".79  E                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_FERRO = new
            GeoPrimeMeridianType(8909, 8909);
            /*  17~40'00"     W                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_BRUSSELS = new
            GeoPrimeMeridianType(8910, 8910);
            /*  =4~22'04".71  E                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_STOCKHOLM = new
            GeoPrimeMeridianType(8911, 8911);
            /*  18~03'29".8   E                             */
    public static final GeoPrimeMeridianType PRIMEMERIDIAN_ATHENS = new
            GeoPrimeMeridianType(8912, 8912);
            /* =  23~42'58".815 E                             */
}
