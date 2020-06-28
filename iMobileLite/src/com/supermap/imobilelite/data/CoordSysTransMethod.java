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

public class CoordSysTransMethod extends Enum {
    private CoordSysTransMethod(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final CoordSysTransMethod MTH_GEOCENTRIC_TRANSLATION = new
            CoordSysTransMethod(9603, 9603);
            /* Geocentric Translation (3-par.)*/
    public static final CoordSysTransMethod MTH_MOLODENSKY = new
            CoordSysTransMethod(9604, 9604);
            /* Molodensky                     */
    public static final CoordSysTransMethod MTH_MOLODENSKY_ABRIDGED = new
            CoordSysTransMethod(9605, 9605);
            /* Abridged Molodensky            */
    public static final CoordSysTransMethod MTH_POSITION_VECTOR = new
            CoordSysTransMethod(9606, 9606);
            /* Position Vector (7-par.)       */
    public static final CoordSysTransMethod MTH_COORDINATE_FRAME = new
            CoordSysTransMethod(9607, 9607);
            /* Coordinate Frame (7-par.)      */
    public static final CoordSysTransMethod MTH_BURSA_WOLF = new
            CoordSysTransMethod((9607 + 33000), (9607 + 33000));
            /* Bursa-Wolf             */
 }
