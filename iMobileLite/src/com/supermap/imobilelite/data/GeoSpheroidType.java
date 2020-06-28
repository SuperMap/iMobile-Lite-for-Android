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
public class GeoSpheroidType extends Enum {
    private GeoSpheroidType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final GeoSpheroidType SPHEROID_USER_DEFINED = new
            GeoSpheroidType( -1, -1);
    public static final GeoSpheroidType SPHEROID_AIRY_1830 = new
            GeoSpheroidType(7001, 7001); // Airy 1830
    public static final GeoSpheroidType SPHEROID_AIRY_MOD = new GeoSpheroidType(
            7002, 7002); // Airy modified
    public static final GeoSpheroidType SPHEROID_ATS_1977 = new GeoSpheroidType(
            7041, 7041); // Average Terrestrial System 1977
    public static final GeoSpheroidType SPHEROID_AUSTRALIAN = new
            GeoSpheroidType(7003, 7003); // Australian National
    public static final GeoSpheroidType SPHEROID_BESSEL_1841 = new
            GeoSpheroidType(7004, 7004); // Bessel 1841
    public static final GeoSpheroidType SPHEROID_BESSEL_MOD = new
            GeoSpheroidType(7005, 7005); // Bessel modified
    public static final GeoSpheroidType SPHEROID_BESSEL_NAMIBIA = new
            GeoSpheroidType(7006, 7006); // Bessel Namibia
    public static final GeoSpheroidType SPHEROID_CLARKE_1858 = new
            GeoSpheroidType(7007, 7007); // Clarke 1858
    public static final GeoSpheroidType SPHEROID_CLARKE_1866 = new
            GeoSpheroidType(7008, 7008); // Clarke 1866
    public static final GeoSpheroidType SPHEROID_CLARKE_1866_MICH = new
            GeoSpheroidType(7009, 7009); // Clarke 1866 Michigan
    public static final GeoSpheroidType SPHEROID_CLARKE_1880 = new
            GeoSpheroidType(7034, 7034); // Clarke 1880
    public static final GeoSpheroidType SPHEROID_CLARKE_1880_ARC = new
            GeoSpheroidType(7013, 7013); // Clarke 1880 (Arc)
    public static final GeoSpheroidType SPHEROID_CLARKE_1880_BENOIT = new
            GeoSpheroidType(7010, 7010); // Clarke 1880 (Benoit)
    public static final GeoSpheroidType SPHEROID_CLARKE_1880_IGN = new
            GeoSpheroidType(7011, 7011); // Clarke 1880 (IGN)
    public static final GeoSpheroidType SPHEROID_CLARKE_1880_RGS = new
            GeoSpheroidType(7012, 7012); // Clarke 1880 (RGS)
    public static final GeoSpheroidType SPHEROID_CLARKE_1880_SGA = new
            GeoSpheroidType(7014, 7014); // Clarke 1880 (SGA)
    public static final GeoSpheroidType SPHEROID_EVEREST_1830 = new
            GeoSpheroidType(7015, 7015); // Everest 1830
    public static final GeoSpheroidType SPHEROID_EVEREST_DEF_1967 = new
            GeoSpheroidType(7016, 7016); // Everest (definition 19  = 67)
    public static final GeoSpheroidType SPHEROID_EVEREST_DEF_1975 = new
            GeoSpheroidType(7017, 7017); // Everest (definition 1975)
    public static final GeoSpheroidType SPHEROID_EVEREST_MOD = new
            GeoSpheroidType(7018, 7018); // Everest modified
    public static final GeoSpheroidType SPHEROID_GEM_10C = new GeoSpheroidType(
            7031, 7031); // GEM gravity potential model
    public static final GeoSpheroidType SPHEROID_GRS_1967 = new GeoSpheroidType(
            7036, 7036); // GRS 1967 = International 1967
    public static final GeoSpheroidType SPHEROID_GRS_1980 = new GeoSpheroidType(
            7019, 7019); // GRS 1980
    public static final GeoSpheroidType SPHEROID_HELMERT_1906 = new
            GeoSpheroidType(7020, 7020); // Helmert 1906
    public static final GeoSpheroidType SPHEROID_INDONESIAN = new
            GeoSpheroidType(7021, 7021); // Indonesian National
    public static final GeoSpheroidType SPHEROID_INTERNATIONAL_1924 = new
            GeoSpheroidType(7022, 7022); // International 1924
    public static final GeoSpheroidType SPHEROID_INTERNATIONAL_1967 = new
            GeoSpheroidType(7023, 7023); // International 1967
    public static final GeoSpheroidType SPHEROID_KRASOVSKY_1940 = new
            GeoSpheroidType(7024, 7024); // Krasovsky 1940
    public static final GeoSpheroidType SPHEROID_NWL_9D = new GeoSpheroidType(
            7025, 7025); // Transit precise ephemeris
    public static final GeoSpheroidType SPHEROID_OSU_86F = new GeoSpheroidType(
            7032, 7032); // OSU 1986 geoidal model
    public static final GeoSpheroidType SPHEROID_OSU_91A = new GeoSpheroidType(
            7033, 7033); // OSU 1991 geoidal model
    public static final GeoSpheroidType SPHEROID_PLESSIS_1817 = new
            GeoSpheroidType(7027, 7027); // Plessis 1817
    public static final GeoSpheroidType SPHEROID_SPHERE = new GeoSpheroidType(
            7035, 7035); // Authalic sphere
    public static final GeoSpheroidType SPHEROID_STRUVE_1860 = new
            GeoSpheroidType(7028, 7028); // Struve 1860
    public static final GeoSpheroidType SPHEROID_WAR_OFFICE = new
            GeoSpheroidType(7029, 7029); // War Office
    public static final GeoSpheroidType SPHEROID_NWL_10D = new GeoSpheroidType(
            7026, 7026); // NWL_10D
    public static final GeoSpheroidType SPHEROID_WGS_1972 = new GeoSpheroidType(
            7043, 7043); // WGS 1972
    public static final GeoSpheroidType SPHEROID_WGS_1984 = new GeoSpheroidType(
            7030, 7030); // WGS 1984
    public static final GeoSpheroidType SPHEROID_WGS_1966 = new GeoSpheroidType((
            7001 + 33000), (7001 + 33000)); // WGS 1966
    public static final GeoSpheroidType SPHEROID_FISCHER_1960 = new
            GeoSpheroidType((7002 + 33000), (7002 + 33000)); // Fischer 1960
    public static final GeoSpheroidType SPHEROID_FISCHER_1968 = new
            GeoSpheroidType((7003 + 33000), (7003 + 33000)); // Fischer 1968
    public static final GeoSpheroidType SPHEROID_FISCHER_MOD = new
            GeoSpheroidType((7004 + 33000), (7004 + 33000)); // Fischer modified
    public static final GeoSpheroidType SPHEROID_HOUGH_1960 = new
            GeoSpheroidType((7005 + 33000), (7005 + 33000)); // Hough 1960
    public static final GeoSpheroidType SPHEROID_EVEREST_MOD_1969 = new
            GeoSpheroidType((7006 + 33000), (7006 + 33000)); // Everest modified 1969
    public static final GeoSpheroidType SPHEROID_WALBECK = new GeoSpheroidType((
            7007 + 33000), (7007 + 33000)); // Walbeck
    public static final GeoSpheroidType SPHEROID_SPHERE_AI = new
            GeoSpheroidType((7008 + 33000), (7008 + 33000)); // Authalic sphere (ARC/INFO)
    public static final GeoSpheroidType SPHEROID_INTERNATIONAL_1975 = new
            GeoSpheroidType((7023 + 33000), (7023 + 33000)); //International 1975,Used By China Xian-1980
    
//  ����Ϊ6.0����
    public static final GeoSpheroidType SPHEROID_CHINA_2000 = new
    GeoSpheroidType(7044, 7044); //International 1975,Used By China Xian-1980
}
