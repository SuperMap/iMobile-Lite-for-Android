package com.supermap.imobilelite.data;
/**
 * <p>
 * imobile移植类
 * </p>
 */

public class PrjCoordSysType extends Enum{
    private PrjCoordSysType(int value, int ugcValue) {
        super(value, ugcValue);
    }
    public static final PrjCoordSysType PCS_USER_DEFINED                  = new PrjCoordSysType(-1,-1);

    public static final PrjCoordSysType PCS_NON_EARTH = new PrjCoordSysType(0,
            0); 
    public static final PrjCoordSysType PCS_EARTH_LONGITUDE_LATITUDE = new
            PrjCoordSysType(1, 1);
    public static final PrjCoordSysType PCS_WORLD_PLATE_CARREE = new
            PrjCoordSysType(54001, 54001); /* Plate Carree           */
    public static final PrjCoordSysType PCS_WORLD_EQUIDISTANT_CYLINDRICAL = new
            PrjCoordSysType(54002, 54002); /* Equidistant Cyl.       */
    public static final PrjCoordSysType PCS_WORLD_MILLER_CYLINDRICAL = new
            PrjCoordSysType(54003, 54003); /* Miller Cylindrical     */
    public static final PrjCoordSysType PCS_WORLD_MERCATOR = new
            PrjCoordSysType(54004, 54004); /* Mercator               */
    public static final PrjCoordSysType PCS_WORLD_SINUSOIDAL = new
            PrjCoordSysType(54008, 54008); /* Sinusoidal             */
    public static final PrjCoordSysType PCS_WORLD_MOLLWEIDE = new
            PrjCoordSysType(54009, 54009); /* Mollweide              */
    public static final PrjCoordSysType PCS_WORLD_ECKERT_VI = new
            PrjCoordSysType(54010, 54010); /* Eckert VI              */
    public static final PrjCoordSysType PCS_WORLD_ECKERT_V = new
            PrjCoordSysType(54011, 54011); /* Eckert V               */
    public static final PrjCoordSysType PCS_WORLD_ECKERT_IV = new
            PrjCoordSysType(54012, 54012); /* Eckert IV              */
    public static final PrjCoordSysType PCS_WORLD_ECKERT_III = new
            PrjCoordSysType(54013, 54013); /* Eckert III             */
    public static final PrjCoordSysType PCS_WORLD_ECKERT_II = new
            PrjCoordSysType(54014, 54014); /* Eckert II              */
    public static final PrjCoordSysType PCS_WORLD_ECKERT_I = new
            PrjCoordSysType(54015, 54015); /* Eckert I               */
    public static final PrjCoordSysType PCS_WORLD_GALL_STEREOGRAPHIC = new
            PrjCoordSysType(54016, 54016); /* Gall Stereographic     */
    public static final PrjCoordSysType PCS_WORLD_BEHRMANN = new
            PrjCoordSysType(54017, 54017); /* Behrmann               */
    public static final PrjCoordSysType PCS_WORLD_WINKEL_I = new
            PrjCoordSysType(54018, 54018); /* Winkel I               */
    public static final PrjCoordSysType PCS_WORLD_WINKEL_II = new
            PrjCoordSysType(54019, 54019); /* Winkel II              */
    public static final PrjCoordSysType PCS_WORLD_POLYCONIC = new
            PrjCoordSysType(54021, 54021); /* Polyconic              */
    public static final PrjCoordSysType PCS_WORLD_QUARTIC_AUTHALIC = new
            PrjCoordSysType(54022, 54022); /* Quartic Authalic       */
    public static final PrjCoordSysType PCS_WORLD_LOXIMUTHAL = new
            PrjCoordSysType(54023, 54023); /* Loximuthal             */
    public static final PrjCoordSysType PCS_WORLD_BONNE = new PrjCoordSysType(
            54024, 54024); /* Bonne                  */
    public static final PrjCoordSysType PCS_WORLD_HOTINE = new PrjCoordSysType(
            54025, 54025); /* Hotine                 */
    public static final PrjCoordSysType PCS_WORLD_STEREOGRAPHIC = new
            PrjCoordSysType(54026, 54026); /* Stereographic          */
    public static final PrjCoordSysType PCS_WORLD_EQUIDISTANT_CONIC = new
            PrjCoordSysType(54027, 54027); /* Equidistant Conic      */
    public static final PrjCoordSysType PCS_WORLD_CASSINI = new PrjCoordSysType(
            54028, 54028); /* Cassini                */
    public static final PrjCoordSysType PCS_WORLD_VAN_DER_GRINTEN_I = new
            PrjCoordSysType(54029, 54029); /* Van der Grinten I      */
    public static final PrjCoordSysType PCS_WORLD_ROBINSON = new
            PrjCoordSysType(54030, 54030); /* Robinson               */
    public static final PrjCoordSysType PCS_WORLD_TWO_POINT_EQUIDISTANT = new
            PrjCoordSysType(54031, 54031); /* Two-Point Equidistant  */
    public static final PrjCoordSysType PCS_SPHERE_PLATE_CARREE = new
            PrjCoordSysType(53001, 53001); /* Plate Carree          */
    public static final PrjCoordSysType PCS_SPHERE_EQUIDISTANT_CYLINDRICAL = new
            PrjCoordSysType(53002, 53002); /* Equidistant Cyl.      */
    public static final PrjCoordSysType PCS_SPHERE_MILLER_CYLINDRICAL = new
            PrjCoordSysType(53003, 53003); /* Miller Cylindrical    */
    public static final PrjCoordSysType PCS_SPHERE_MERCATOR = new
            PrjCoordSysType(53004, 53004); /* Mercator              */
    public static final PrjCoordSysType PCS_SPHERE_SINUSOIDAL = new
            PrjCoordSysType(53008, 53008); /* Sinusoidal            */
    public static final PrjCoordSysType PCS_SPHERE_MOLLWEIDE = new
            PrjCoordSysType(53009, 53009); /* Mollweide             */
    public static final PrjCoordSysType PCS_SPHERE_ECKERT_VI = new
            PrjCoordSysType(53010, 53010); /* Eckert VI             */
    public static final PrjCoordSysType PCS_SPHERE_ECKERT_V = new
            PrjCoordSysType(53011, 53011); /* Eckert V              */
    public static final PrjCoordSysType PCS_SPHERE_ECKERT_IV = new
            PrjCoordSysType(53012, 53012); /* Eckert IV             */
    public static final PrjCoordSysType PCS_SPHERE_ECKERT_III = new
            PrjCoordSysType(53013, 53013); /* Eckert III            */
    public static final PrjCoordSysType PCS_SPHERE_ECKERT_II = new
            PrjCoordSysType(53014, 53014); /* Eckert II             */
    public static final PrjCoordSysType PCS_SPHERE_ECKERT_I = new
            PrjCoordSysType(53015, 53015); /* Eckert I              */
    public static final PrjCoordSysType PCS_SPHERE_GALL_STEREOGRAPHIC = new
            PrjCoordSysType(53016, 53016); /* Gall Stereographic    */
    public static final PrjCoordSysType PCS_SPHERE_BEHRMANN = new
            PrjCoordSysType(53017, 53017); /* Behrmann              */
    public static final PrjCoordSysType PCS_SPHERE_WINKEL_I = new
            PrjCoordSysType(53018, 53018); /* Winkel I              */
    public static final PrjCoordSysType PCS_SPHERE_WINKEL_II = new
            PrjCoordSysType(53019, 53019); /* Winkel II             */
    public static final PrjCoordSysType PCS_SPHERE_POLYCONIC = new
            PrjCoordSysType(53021, 53021); /* Polyconic             */
    public static final PrjCoordSysType PCS_SPHERE_QUARTIC_AUTHALIC = new
            PrjCoordSysType(53022, 53022); /* Quartic Authalic      */
    public static final PrjCoordSysType PCS_SPHERE_LOXIMUTHAL = new
            PrjCoordSysType(53023, 53023); /* Loximuthal            */
    public static final PrjCoordSysType PCS_SPHERE_BONNE = new PrjCoordSysType(
            53024, 53024); /* Bonne                 */
    /*public static final PrjCoordSysType PCS_SPHERE_HOTINE = new PrjCoordSysType(
            53025, 53025);  Hotine                */
    public static final PrjCoordSysType PCS_SPHERE_STEREOGRAPHIC = new
            PrjCoordSysType(53026, 53026); /* Stereographic         */
    public static final PrjCoordSysType PCS_SPHERE_EQUIDISTANT_CONIC = new
            PrjCoordSysType(53027, 53027); /* Equidistant Conic     */
    public static final PrjCoordSysType PCS_SPHERE_CASSINI = new
            PrjCoordSysType(53028, 53028); /* Cassini               */
    public static final PrjCoordSysType PCS_SPHERE_VAN_DER_GRINTEN_I = new
            PrjCoordSysType(53029, 53029); /* Van der Grinten I     */
    public static final PrjCoordSysType PCS_SPHERE_ROBINSON = new
            PrjCoordSysType(53030, 53030); /* Robinson              */
    public static final PrjCoordSysType PCS_SPHERE_TWO_POINT_EQUIDISTANT = new
            PrjCoordSysType(53031, 53031); /* Two-Point Equidistant */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_1N = new
            PrjCoordSysType(32601, 32601);
            /* WGS 1984 UTM Zone 1N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_2N = new
            PrjCoordSysType(32602, 32602);
            /* WGS 1984 UTM Zone 2N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_3N = new
            PrjCoordSysType(32603, 32603);
            /* WGS 1984 UTM Zone 3N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_4N = new
            PrjCoordSysType(32604, 32604);
            /* WGS 1984 UTM Zone 4N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_5N = new
            PrjCoordSysType(32605, 32605);
            /* WGS 1984 UTM Zone 5N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_6N = new
            PrjCoordSysType(32606, 32606);
            /* WGS 1984 UTM Zone 6N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_7N = new
            PrjCoordSysType(32607, 32607);
            /* WGS 1984 UTM Zone 7N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_8N = new
            PrjCoordSysType(32608, 32608);
            /* WGS 1984 UTM Zone 8N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_9N = new
            PrjCoordSysType(32609, 32609);
            /* WGS 1984 UTM Zone 9N               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_10N = new
            PrjCoordSysType(32610, 32610);
            /* WGS 1984 UTM Zone 10N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_11N = new
            PrjCoordSysType(32611, 32611);
            /* WGS 1984 UTM Zone 11N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_12N = new
            PrjCoordSysType(32612, 32612);
            /* WGS 1984 UTM Zone 12N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_13N = new
            PrjCoordSysType(32613, 32613);
            /* WGS 1984 UTM Zone 13N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_14N = new
            PrjCoordSysType(32614, 32614);
            /* WGS 1984 UTM Zone 14N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_15N = new
            PrjCoordSysType(32615, 32615);
            /* WGS 1984 UTM Zone 15N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_16N = new
            PrjCoordSysType(32616, 32616);
            /* WGS 1984 UTM Zone 16N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_17N = new
            PrjCoordSysType(32617, 32617);
            /* WGS 1984 UTM Zone 17N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_18N = new
            PrjCoordSysType(32618, 32618);
            /* WGS 1984 UTM Zone 18N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_19N = new
            PrjCoordSysType(32619, 32619);
            /* WGS 1984 UTM Zone 19N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_20N = new
            PrjCoordSysType(32620, 32620);
            /* WGS 1984 UTM Zone 20N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_21N = new
            PrjCoordSysType(32621, 32621);
            /* WGS 1984 UTM Zone 21N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_22N = new
            PrjCoordSysType(32622, 32622);
            /* WGS 1984 UTM Zone 22N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_23N = new
            PrjCoordSysType(32623, 32623);
            /* WGS 1984 UTM Zone=  23N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_24N = new
            PrjCoordSysType(32624, 32624);
            /* WGS 1984 UTM Zone 24N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_25N = new
            PrjCoordSysType(32625, 32625);
            /* WGS 1984 UTM Zone 25N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_26N = new
            PrjCoordSysType(32626, 32626);
            /* WGS 1984 UTM Zone 26N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_27N = new
            PrjCoordSysType(32627, 32627);
            /* WGS 1984 UTM Zone 27N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_28N = new
            PrjCoordSysType(32628, 32628);
            /* WGS 1984 UTM Zone=  28N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_29N = new
            PrjCoordSysType(32629, 32629);
            /* WGS 1984 UTM Zone=  29N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_30N = new
            PrjCoordSysType(32630, 32630);
            /* WGS 1984 UTM Zone 30N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_31N = new
            PrjCoordSysType(32631, 32631);
            /* WGS 1984 UTM Zone 31N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_32N = new
            PrjCoordSysType(32632, 32632);
            /* WGS 1984 UTM Zone= 32N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_33N = new
            PrjCoordSysType(32633, 32633);
            /* WGS 1984 UTM Zone 33N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_34N = new
            PrjCoordSysType(32634, 32634);
            /* WGS 1984 UTM Zone 34N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_35N = new
            PrjCoordSysType(32635, 32635);
            /* WGS 1984 UTM Zone 35N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_36N = new
            PrjCoordSysType(32636, 32636);
            /* WGS 1984 UTM Zone 36N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_37N = new
            PrjCoordSysType(32637, 32637);
            /* WGS 1984 UTM Zone 37N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_38N = new
            PrjCoordSysType(32638, 32638);
            /* WGS 1984 UTM Zone 38N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_39N = new
            PrjCoordSysType(32639, 32639);
            /* WGS 1984 UTM Zone 39N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_40N = new
            PrjCoordSysType(32640, 32640);
            /* WGS 1984 UTM Zone 40N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_41N = new
            PrjCoordSysType(32641, 32641);
            /* WGS 1984 UTM Zone 41N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_42N = new
            PrjCoordSysType(32642, 32642);
            /* WGS 1984 UTM Zone 42N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_43N = new
            PrjCoordSysType(32643, 32643);
            /* WGS 1984 UTM Zone 43N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_44N = new
            PrjCoordSysType(32644, 32644);
            /* WGS 1984 UTM Zone 44N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_45N = new
            PrjCoordSysType(32645, 32645);
            /* WGS 1984 UTM Zone 45N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_46N = new
            PrjCoordSysType(32646, 32646);
            /* WGS 1984 UTM Zone 46N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_47N = new
            PrjCoordSysType(32647, 32647);
            /* WGS 1984 UTM Zone 47N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_48N = new
            PrjCoordSysType(32648, 32648);
            /* WGS 1984 UTM Zone 48N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_49N = new
            PrjCoordSysType(32649, 32649);
            /* WGS 1984 UTM Zone 49N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_50N = new
            PrjCoordSysType(32650, 32650);
            /* WGS 1984 UTM Zone 50N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_51N = new
            PrjCoordSysType(32651, 32651);
            /* WGS 1984 UTM Zone 51N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_52N = new
            PrjCoordSysType(32652, 32652);
            /* WGS 1984 UTM Zone 52N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_53N = new
            PrjCoordSysType(32653, 32653);
            /* WGS 1984 UTM Zone= 53N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_54N = new
            PrjCoordSysType(32654, 32654);
            /* WGS 1984 UTM Zone= 54N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_55N = new
            PrjCoordSysType(32655, 32655);
            /* WGS 1984 UTM Zone 55N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_56N = new
            PrjCoordSysType(32656, 32656);
            /* WGS 1984 UTM Zone 56N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_57N = new
            PrjCoordSysType(32657, 32657);
            /* WGS 1984 UTM Zone 57N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_58N = new
            PrjCoordSysType(32658, 32658);
            /* WGS 1984 UTM Zone 58N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_59N = new
            PrjCoordSysType(32659, 32659);
            /* WGS 1984 UTM Zone 59N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_60N = new
            PrjCoordSysType(32660, 32660);
            /* WGS 1984 UTM Zone 60N              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_1S = new
            PrjCoordSysType(32701, 32701);
            /* WGS 1984 UTM Zone 1S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_2S = new
            PrjCoordSysType(32702, 32702);
            /* WGS 1984 UTM Zone 2S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_3S = new
            PrjCoordSysType(32703, 32703);
            /* WGS 1984 UTM Zone 3S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_4S = new
            PrjCoordSysType(32704, 32704);
            /* WGS 1984 UTM Zone 4S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_5S = new
            PrjCoordSysType(32705, 32705);
            /* WGS 1984 UTM Zone 5S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_6S = new
            PrjCoordSysType(32706, 32706);
            /* WGS 1984 UTM Zone 6S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_7S = new
            PrjCoordSysType(32707, 32707);
            /* WGS 1984 UTM Zone 7S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_8S = new
            PrjCoordSysType(32708, 32708);
            /* WGS 1984 UTM Zone 8S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_9S = new
            PrjCoordSysType(32709, 32709);
            /* WGS 1984 UTM Zone 9S               */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_10S = new
            PrjCoordSysType(32710, 32710);
            /* WGS 1984 UTM Zone 10S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_11S = new
            PrjCoordSysType(32711, 32711);
            /* WGS 1984 UTM Zone 11S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_12S = new
            PrjCoordSysType(32712, 32712);
            /* WGS 1984 UTM Zone 12S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_13S = new
            PrjCoordSysType(32713, 32713);
            /* WGS 1984 UTM Zone 13S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_14S = new
            PrjCoordSysType(32714, 32714);
            /* WGS 1984 UTM Zone 14S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_15S = new
            PrjCoordSysType(32715, 32715);
            /* WGS 1984 UTM Zone 15S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_16S = new
            PrjCoordSysType(32716, 32716);
            /* WGS 1984 UTM Zone 16S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_17S = new
            PrjCoordSysType(32717, 32717);
            /* WGS 1984 UTM Zone 17S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_18S = new
            PrjCoordSysType(32718, 32718);
            /* WGS 1984 UTM Zone 18S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_19S = new
            PrjCoordSysType(32719, 32719);
            /* WGS 1984 UTM Zone 19S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_20S = new
            PrjCoordSysType(32720, 32720);
            /* WGS 1984 UTM Zone 20S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_21S = new
            PrjCoordSysType(32721, 32721);
            /* WGS 1984 UTM Zone 21S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_22S = new
            PrjCoordSysType(32722, 32722);
            /* WGS 1984 UTM Zone 22S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_23S = new
            PrjCoordSysType(32723, 32723);
            /* WGS 1984 UTM Zone=  23S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_24S = new
            PrjCoordSysType(32724, 32724);
            /* WGS 1984 UTM Zone 24S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_25S = new
            PrjCoordSysType(32725, 32725);
            /* WGS 1984 UTM Zone 25S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_26S = new
            PrjCoordSysType(32726, 32726);
            /* WGS 1984 UTM Zone 26S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_27S = new
            PrjCoordSysType(32727, 32727);
            /* WGS 1984 UTM Zone 27S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_28S = new
            PrjCoordSysType(32728, 32728);
            /* WGS 1984 UTM Zone=  28S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_29S = new
            PrjCoordSysType(32729, 32729);
            /* WGS 1984 UTM Zone=  29S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_30S = new
            PrjCoordSysType(32730, 32730);
            /* WGS 1984 UTM Zone 30S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_31S = new
            PrjCoordSysType(32731, 32731);
            /* WGS 1984 UTM Zone 31S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_32S = new
            PrjCoordSysType(32732, 32732);
            /* WGS 1984 UTM Zone= 32S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_33S = new
            PrjCoordSysType(32733, 32733);
            /* WGS 1984 UTM Zone 33S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_34S = new
            PrjCoordSysType(32734, 32734);
            /* WGS 1984 UTM Zone 34S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_35S = new
            PrjCoordSysType(32735, 32735);
            /* WGS 1984 UTM Zone 35S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_36S = new
            PrjCoordSysType(32736, 32736);
            /* WGS 1984 UTM Zone 36S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_37S = new
            PrjCoordSysType(32737, 32737);
            /* WGS 1984 UTM Zone 37S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_38S = new
            PrjCoordSysType(32738, 32738);
            /* WGS 1984 UTM Zone 38S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_39S = new
            PrjCoordSysType(32739, 32739);
            /* WGS 1984 UTM Zone 39S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_40S = new
            PrjCoordSysType(32740, 32740);
            /* WGS 1984 UTM Zone 40S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_41S = new
            PrjCoordSysType(32741, 32741);
            /* WGS 1984 UTM Zone 41S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_42S = new
            PrjCoordSysType(32742, 32742);
            /* WGS 1984 UTM Zone 42S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_43S = new
            PrjCoordSysType(32743, 32743);
            /* WGS 1984 UTM Zone 43S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_44S = new
            PrjCoordSysType(32744, 32744);
            /* WGS 1984 UTM Zone 44S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_45S = new
            PrjCoordSysType(32745, 32745);
            /* WGS 1984 UTM Zone 45S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_46S = new
            PrjCoordSysType(32746, 32746);
            /* WGS 1984 UTM Zone 46S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_47S = new
            PrjCoordSysType(32747, 32747);
            /* WGS 1984 UTM Zone 47S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_48S = new
            PrjCoordSysType(32748, 32748);
            /* WGS 1984 UTM Zone 48S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_49S = new
            PrjCoordSysType(32749, 32749);
            /* WGS 1984 UTM Zone 49S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_50S = new
            PrjCoordSysType(32750, 32750);
            /* WGS 1984 UTM Zone 50S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_51S = new
            PrjCoordSysType(32751, 32751);
            /* WGS 1984 UTM Zone 51S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_52S = new
            PrjCoordSysType(32752, 32752);
            /* WGS 1984 UTM Zone 52S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_53S = new
            PrjCoordSysType(32753, 32753);
            /* WGS 1984 UTM Zone 53S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_54S = new
            PrjCoordSysType(32754, 32754);
            /* WGS 1984 UTM Zone= 54S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_55S = new
            PrjCoordSysType(32755, 32755);
            /* WGS 1984 UTM Zone 55S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_56S = new
            PrjCoordSysType(32756, 32756);
            /* WGS 1984 UTM Zone 56S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_57S = new
            PrjCoordSysType(32757, 32757);
            /* WGS 1984 UTM Zone 57S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_58S = new
            PrjCoordSysType(32758, 32758);
            /* WGS 1984 UTM Zone 58S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_59S = new
            PrjCoordSysType(32759, 32759);
            /* WGS 1984 UTM Zone 59S              */
    public static final PrjCoordSysType PCS_WGS_1984_UTM_60S = new
            PrjCoordSysType(32760, 32760);
            /* WGS 1984 UTM Zone 60S              */
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_I = new
            PrjCoordSysType(32761, 32761); //Japanese Zone I
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_II = new
            PrjCoordSysType(32762, 32762); //Japanese Zone II
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_III = new
            PrjCoordSysType(32763, 32763); //Japanese Zone III
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_IV = new
            PrjCoordSysType(32764, 32764); //Japanese Zone IV
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_V = new
            PrjCoordSysType(32765, 32765); //Japanese Zone V
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_VI = new
            PrjCoordSysType(32766, 32766); //Japanese Zone VI
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_VII = new
            PrjCoordSysType(32767, 32767); //Japanese Zone VII
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_VIII = new
            PrjCoordSysType(32768, 32768); //Japanese Zone VIII
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_IX = new
            PrjCoordSysType(32769, 32769); //Japanese Zone IX
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_X = new
            PrjCoordSysType(32770, 32770); //Japanese Zone X
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XI = new
            PrjCoordSysType(32771, 32771); //Japanese Zone XI
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XII = new
            PrjCoordSysType(32772, 32772); //Japanese Zone XII
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XIII = new
            PrjCoordSysType(32773, 32773); //Japanese Zone XIII
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XIV = new
            PrjCoordSysType(32774, 32774); //Japanese Zone XIV
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XV = new
            PrjCoordSysType(32775, 32775); //Japanese Zone XV
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XVI = new
            PrjCoordSysType(32776, 32776); //Japanese Zone XVI
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XVII = new
            PrjCoordSysType(32777, 32777); //Japanese Zone XVII
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XVIII = new
            PrjCoordSysType(32778, 32778); //Japanese Zone XVIII
    public static final PrjCoordSysType PCS_TOKYO_PLATE_ZONE_XIX = new
            PrjCoordSysType(32779, 32779); //Japanese Zone XIX
    public static final PrjCoordSysType PCS_TOKYO_UTM_51 = new PrjCoordSysType(
            32780, 32780);
    public static final PrjCoordSysType PCS_TOKYO_UTM_52 = new PrjCoordSysType(
            32781, 32781);
    public static final PrjCoordSysType PCS_TOKYO_UTM_53 = new PrjCoordSysType(
            32782, 32782);
    public static final PrjCoordSysType PCS_TOKYO_UTM_54 = new PrjCoordSysType(
            32783, 32783);
    public static final PrjCoordSysType PCS_TOKYO_UTM_55 = new PrjCoordSysType(
            32784, 32784);
    public static final PrjCoordSysType PCS_TOKYO_UTM_56 = new PrjCoordSysType(
            32785, 32785);
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_I = new
            PrjCoordSysType(32786, 32786); //Japanese Zone I
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_II = new
            PrjCoordSysType(32787, 32787); //Japanese Zone II
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_III = new
            PrjCoordSysType(32788, 32788); //Japanese Zone III
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_IV = new
            PrjCoordSysType(32789, 32789); //Japanese Zone IV
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_V = new
            PrjCoordSysType(32790, 32790); //Japanese Zone V
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_VI = new
            PrjCoordSysType(32791, 32791); //Japanese Zone VI
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_VII = new
            PrjCoordSysType(32792, 32792); //Japanese Zone VII
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_VIII = new
            PrjCoordSysType(32793, 32793); //Japanese Zone VIII
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_IX = new
            PrjCoordSysType(32794, 32794); //Japanese Zone IX
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_X = new
            PrjCoordSysType(32795, 32795); //Japanese Zone X
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XI = new
            PrjCoordSysType(32796, 32796); //Japanese Zone XI
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XII = new
            PrjCoordSysType(32797, 32797); //Japanese Zone XII
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XIII = new
            PrjCoordSysType(32798, 32798); //Japanese Zone XIII
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XIV = new
            PrjCoordSysType(32800, 32800); //Japanese Zone XIV
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XV = new
            PrjCoordSysType(32801, 32801); //Japanese Zone XV
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XVI = new
            PrjCoordSysType(32802, 32802); //Japanese Zone XVI
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XVII = new
            PrjCoordSysType(32803, 32803); //Japanese Zone XVII
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XVIII = new
            PrjCoordSysType(32804, 32804); //Japanese Zone XVIII
    public static final PrjCoordSysType PCS_JAPAN_PLATE_ZONE_XIX = new
            PrjCoordSysType(32805, 32805); //Japanese Zone XIX
    public static final PrjCoordSysType PCS_JAPAN_UTM_51 = new PrjCoordSysType(
            32806, 32806);
    public static final PrjCoordSysType PCS_JAPAN_UTM_52 = new PrjCoordSysType(
            32807, 32807);
    public static final PrjCoordSysType PCS_JAPAN_UTM_53 = new PrjCoordSysType(
            32808, 32808);
    public static final PrjCoordSysType PCS_JAPAN_UTM_54 = new PrjCoordSysType(
            32809, 32809);
    public static final PrjCoordSysType PCS_JAPAN_UTM_55 = new PrjCoordSysType(
            32810, 32810);
    public static final PrjCoordSysType PCS_JAPAN_UTM_56 = new PrjCoordSysType(
            32811, 32811);
    public static final PrjCoordSysType PCS_WGS_1972_UTM_1N = new
            PrjCoordSysType(32201, 32201);
            /* WGS 1972 UTM Zone 1N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_2N = new
            PrjCoordSysType(32202, 32202);
            /* WGS 1972 UTM Zone 2N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_3N = new
            PrjCoordSysType(32203, 32203);
            /* WGS 1972 UTM Zone 3N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_4N = new
            PrjCoordSysType(32204, 32204);
            /* WGS 1972 UTM Zone 4N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_5N = new
            PrjCoordSysType(32205, 32205);
            /* WGS 1972 UTM Zone 5N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_6N = new
            PrjCoordSysType(32206, 32206);
            /* WGS 1972 UTM Zone 6N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_7N = new
            PrjCoordSysType(32207, 32207);
            /* WGS 1972 UTM Zone 7N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_8N = new
            PrjCoordSysType(32208, 32208);
            /* WGS 1972 UTM Zone 8N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_9N = new
            PrjCoordSysType(32209, 32209);
            /* WGS 1972 UTM Zone 9N               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_10N = new
            PrjCoordSysType(32210, 32210);
            /* WGS 1972 UTM Zone 10N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_11N = new
            PrjCoordSysType(32211, 32211);
            /* WGS 1972 UTM Zone 11N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_12N = new
            PrjCoordSysType(32212, 32212);
            /* WGS 1972 UTM Zone 12N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_13N = new
            PrjCoordSysType(32213, 32213);
            /* WGS 1972 UTM Zone 13N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_14N = new
            PrjCoordSysType(32214, 32214);
            /* WGS 1972 UTM Zone 14N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_15N = new
            PrjCoordSysType(32215, 32215);
            /* WGS 1972 UTM Zone 15N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_16N = new
            PrjCoordSysType(32216, 32216);
            /* WGS 1972 UTM Zone 16N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_17N = new
            PrjCoordSysType(32217, 32217);
            /* WGS 1972 UTM Zone 17N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_18N = new
            PrjCoordSysType(32218, 32218);
            /* WGS 1972 UTM Zone 18N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_19N = new
            PrjCoordSysType(32219, 32219);
            /* WGS 1972 UTM Zone 19N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_20N = new
            PrjCoordSysType(32220, 32220);
            /* WGS 1972 UTM Zone 20N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_21N = new
            PrjCoordSysType(32221, 32221);
            /* WGS 1972 UTM Zone 21N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_22N = new
            PrjCoordSysType(32222, 32222);
            /* WGS 1972 UTM Zone 22N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_23N = new
            PrjCoordSysType(32223, 32223);
            /* WGS 1972 UTM Zone=  23N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_24N = new
            PrjCoordSysType(32224, 32224);
            /* WGS 1972 UTM Zone 24N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_25N = new
            PrjCoordSysType(32225, 32225);
            /* WGS 1972 UTM Zone 25N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_26N = new
            PrjCoordSysType(32226, 32226);
            /* WGS 1972 UTM Zone 26N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_27N = new
            PrjCoordSysType(32227, 32227);
            /* WGS 1972 UTM Zone 27N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_28N = new
            PrjCoordSysType(32228, 32228);
            /* WGS 1972 UTM Zone=  28N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_29N = new
            PrjCoordSysType(32229, 32229);
            /* WGS 1972 UTM Zone=  29N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_30N = new
            PrjCoordSysType(32230, 32230);
            /* WGS 1972 UTM Zone 30N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_31N = new
            PrjCoordSysType(32231, 32231);
            /* WGS 1972 UTM Zone 31N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_32N = new
            PrjCoordSysType(32232, 32232);
            /* WGS 1972 UTM Zone= 32N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_33N = new
            PrjCoordSysType(32233, 32233);
            /* WGS 1972 UTM Zone 33N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_34N = new
            PrjCoordSysType(32234, 32234);
            /* WGS 1972 UTM Zone 34N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_35N = new
            PrjCoordSysType(32235, 32235);
            /* WGS 1972 UTM Zone 35N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_36N = new
            PrjCoordSysType(32236, 32236);
            /* WGS 1972 UTM Zone 36N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_37N = new
            PrjCoordSysType(32237, 32237);
            /* WGS 1972 UTM Zone 37N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_38N = new
            PrjCoordSysType(32238, 32238);
            /* WGS 1972 UTM Zone 38N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_39N = new
            PrjCoordSysType(32239, 32239);
            /* WGS 1972 UTM Zone 39N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_40N = new
            PrjCoordSysType(32240, 32240);
            /* WGS 1972 UTM Zone 40N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_41N = new
            PrjCoordSysType(32241, 32241);
            /* WGS 1972 UTM Zone 41N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_42N = new
            PrjCoordSysType(32242, 32242);
            /* WGS 1972 UTM Zone 42N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_43N = new
            PrjCoordSysType(32243, 32243);
            /* WGS 1972 UTM Zone 43N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_44N = new
            PrjCoordSysType(32244, 32244);
            /* WGS 1972 UTM Zone 44N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_45N = new
            PrjCoordSysType(32245, 32245);
            /* WGS 1972 UTM Zone 45N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_46N = new
            PrjCoordSysType(32246, 32246);
            /* WGS 1972 UTM Zone 46N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_47N = new
            PrjCoordSysType(32247, 32247);
            /* WGS 1972 UTM Zone 47N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_48N = new
            PrjCoordSysType(32248, 32248);
            /* WGS 1972 UTM Zone 48N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_49N = new
            PrjCoordSysType(32249, 32249);
            /* WGS 1972 UTM Zone 49N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_50N = new
            PrjCoordSysType(32250, 32250);
            /* WGS 1972 UTM Zone 50N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_51N = new
            PrjCoordSysType(32251, 32251);
            /* WGS 1972 UTM Zone 51N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_52N = new
            PrjCoordSysType(32252, 32252);
            /* WGS 1972 UTM Zone 52N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_53N = new
            PrjCoordSysType(32253, 32253);
            /* WGS 1972 UTM Zone 53N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_54N = new
            PrjCoordSysType(32254, 32254);
            /* WGS 1972 UTM Zone= 54N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_55N = new
            PrjCoordSysType(32255, 32255);
            /* WGS 1972 UTM Zone 55N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_56N = new
            PrjCoordSysType(32256, 32256);
            /* WGS 1972 UTM Zone 56N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_57N = new
            PrjCoordSysType(32257, 32257);
            /* WGS 1972 UTM Zone 57N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_58N = new
            PrjCoordSysType(32258, 32258);
            /* WGS 1972 UTM Zone 58N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_59N = new
            PrjCoordSysType(32259, 32259);
            /* WGS 1972 UTM Zone 59N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_60N = new
            PrjCoordSysType(32260, 32260);
            /* WGS 1972 UTM Zone 60N              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_1S = new
            PrjCoordSysType(32301, 32301);
            /* WGS 1972 UTM Zone 1S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_2S = new
            PrjCoordSysType(32302, 32302);
            /* WGS 1972 UTM Zone 2S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_3S = new
            PrjCoordSysType(32303, 32303);
            /* WGS 1972 UTM Zone 3S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_4S = new
            PrjCoordSysType(32304, 32304);
            /* WGS 1972 UTM Zone 4S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_5S = new
            PrjCoordSysType(32305, 32305);
            /* WGS 1972 UTM Zone 5S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_6S = new
            PrjCoordSysType(32306, 32306);
            /* WGS 1972 UTM Zone 6S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_7S = new
            PrjCoordSysType(32307, 32307);
            /* WGS 1972 UTM Zone 7S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_8S = new
            PrjCoordSysType(32308, 32308);
            /* WGS 1972 UTM Zone 8S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_9S = new
            PrjCoordSysType(32309, 32309);
            /* WGS 1972 UTM Zone 9S               */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_10S = new
            PrjCoordSysType(32310, 32310);
            /* WGS 1972 UTM Zone 10S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_11S = new
            PrjCoordSysType(32311, 32311);
            /* WGS 1972 UTM Zone 11S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_12S = new
            PrjCoordSysType(32312, 32312);
            /* WGS 1972 UTM Zone 12S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_13S = new
            PrjCoordSysType(32313, 32313);
            /* WGS 1972 UTM Zone 13S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_14S = new
            PrjCoordSysType(32314, 32314);
            /* WGS 1972 UTM Zone 14S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_15S = new
            PrjCoordSysType(32315, 32315);
            /* WGS 1972 UTM Zone 15S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_16S = new
            PrjCoordSysType(32316, 32316);
            /* WGS 1972 UTM Zone 16S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_17S = new
            PrjCoordSysType(32317, 32317);
            /* WGS 1972 UTM Zone 17S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_18S = new
            PrjCoordSysType(32318, 32318);
            /* WGS 1972 UTM Zone 18S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_19S = new
            PrjCoordSysType(32319, 32319);
            /* WGS 1972 UTM Zone 19S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_20S = new
            PrjCoordSysType(32320, 32320);
            /* WGS 1972 UTM Zone 20S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_21S = new
            PrjCoordSysType(32321, 32321);
            /* WGS 1972 UTM Zone 21S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_22S = new
            PrjCoordSysType(32322, 32322);
            /* WGS 1972 UTM Zone 22S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_23S = new
            PrjCoordSysType(32323, 32323);
            /* WGS 1972 UTM Zone 23S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_24S = new
            PrjCoordSysType(32324, 32324);
            /* WGS 1972 UTM Zone 24S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_25S = new
            PrjCoordSysType(32325, 32325);
            /* WGS 1972 UTM Zone 25S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_26S = new
            PrjCoordSysType(32326, 32326);
            /* WGS 1972 UTM Zone 26S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_27S = new
            PrjCoordSysType(32327, 32327);
            /* WGS 1972 UTM Zone 27S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_28S = new
            PrjCoordSysType(32328, 32328);
            /* WGS 1972 UTM Zone=  28S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_29S = new
            PrjCoordSysType(32329, 32329);
            /* WGS 1972 UTM Zone=  29S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_30S = new
            PrjCoordSysType(32330, 32330);
            /* WGS 1972 UTM Zone 30S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_31S = new
            PrjCoordSysType(32331, 32331);
            /* WGS 1972 UTM Zone 31S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_32S = new
            PrjCoordSysType(32332, 32332);
            /* WGS 1972 UTM Zone= 32S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_33S = new
            PrjCoordSysType(32333, 32333);
            /* WGS 1972 UTM Zone 33S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_34S = new
            PrjCoordSysType(32334, 32334);
            /* WGS 1972 UTM Zone 34S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_35S = new
            PrjCoordSysType(32335, 32335);
            /* WGS 1972 UTM Zone 35S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_36S = new
            PrjCoordSysType(32336, 32336);
            /* WGS 1972 UTM Zone 36S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_37S = new
            PrjCoordSysType(32337, 32337);
            /* WGS 1972 UTM Zone 37S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_38S = new
            PrjCoordSysType(32338, 32338);
            /* WGS 1972 UTM Zone 38S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_39S = new
            PrjCoordSysType(32339, 32339);
            /* WGS 1972 UTM Zone 39S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_40S = new
            PrjCoordSysType(32340, 32340);
            /* WGS 1972 UTM Zone 40S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_41S = new
            PrjCoordSysType(32341, 32341);
            /* WGS 1972 UTM Zone 41S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_42S = new
            PrjCoordSysType(32342, 32342);
            /* WGS 1972 UTM Zone 42S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_43S = new
            PrjCoordSysType(32343, 32343);
            /* WGS 1972 UTM Zone 43S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_44S = new
            PrjCoordSysType(32344, 32344);
            /* WGS 1972 UTM Zone 44S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_45S = new
            PrjCoordSysType(32345, 32345);
            /* WGS 1972 UTM Zone 45S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_46S = new
            PrjCoordSysType(32346, 32346);
            /* WGS 1972 UTM Zone 46S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_47S = new
            PrjCoordSysType(32347, 32347);
            /* WGS 1972 UTM Zone 47S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_48S = new
            PrjCoordSysType(32348, 32348);
            /* WGS 1972 UTM Zone 48S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_49S = new
            PrjCoordSysType(32349, 32349);
            /* WGS 1972 UTM Zone 49S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_50S = new
            PrjCoordSysType(32350, 32350);
            /* WGS 1972 UTM Zone 50S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_51S = new
            PrjCoordSysType(32351, 32351);
            /* WGS 1972 UTM Zone 51S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_52S = new
            PrjCoordSysType(32352, 32352);
            /* WGS 1972 UTM Zone 52S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_53S = new
            PrjCoordSysType(32353, 32353);
            /* WGS 1972 UTM Zone 53S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_54S = new
            PrjCoordSysType(32354, 32354);
            /* WGS 1972 UTM Zone 54S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_55S = new
            PrjCoordSysType(32355, 32355);
            /* WGS 1972 UTM Zone 55S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_56S = new
            PrjCoordSysType(32356, 32356);
            /* WGS 1972 UTM Zone 56S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_57S = new
            PrjCoordSysType(32357, 32357);
            /* WGS 1972 UTM Zone 57S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_58S = new
            PrjCoordSysType(32358, 32358);
            /* WGS 1972 UTM Zone 58S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_59S = new
            PrjCoordSysType(32359, 32359);
            /* WGS 1972 UTM Zone 59S              */
    public static final PrjCoordSysType PCS_WGS_1972_UTM_60S = new
            PrjCoordSysType(32360, 32360);
            /* WGS 1972 UTM Zone 60S              */
    public static final PrjCoordSysType PCS_NAD_1927_BLM_14N = new
            PrjCoordSysType(32074, 32074);
            /* NAD 1927 BLM Zone 14N           */
    public static final PrjCoordSysType PCS_NAD_1927_BLM_15N = new
            PrjCoordSysType(32075, 32075);
            /* NAD 1927 BLM Zone 15N           */
    public static final PrjCoordSysType PCS_NAD_1927_BLM_16N = new
            PrjCoordSysType(32076, 32076);
            /* NAD 1927 BLM Zone 16N           */
    public static final PrjCoordSysType PCS_NAD_1927_BLM_17N = new
            PrjCoordSysType(32077, 32077);
            /* NAD 1927 BLM Zone 17N           */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_3N = new
            PrjCoordSysType(26703, 26703);
            /* NAD 1927 UTM Zone 3N               */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_4N = new
            PrjCoordSysType(26704, 26704);
            /* NAD 1927 UTM Zone 4N               */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_5N = new
            PrjCoordSysType(26705, 26705);
            /* NAD 1927 UTM Zone 5N               */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_6N = new
            PrjCoordSysType(26706, 26706);
            /* NAD 1927 UTM Zone 6N               */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_7N = new
            PrjCoordSysType(26707, 26707);
            /* NAD 1927 UTM Zone 7N               */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_8N = new
            PrjCoordSysType(26708, 26708);
            /* NAD 1927 UTM Zone 8N               */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_9N = new
            PrjCoordSysType(26709, 26709);
            /* NAD 1927 UTM Zone 9N               */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_10N = new
            PrjCoordSysType(26710, 26710);
            /* NAD 1927 UTM Zone 10N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_11N = new
            PrjCoordSysType(26711, 26711);
            /* NAD 1927 UTM Zone 11N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_12N = new
            PrjCoordSysType(26712, 26712);
            /* NAD 1927 UTM Zone 12N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_13N = new
            PrjCoordSysType(26713, 26713);
            /* NAD 1927 UTM Zone 13N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_14N = new
            PrjCoordSysType(26714, 26714);
            /* NAD 1927 UTM Zone 14N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_15N = new
            PrjCoordSysType(26715, 26715);
            /* NAD 1927 UTM Zone 15N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_16N = new
            PrjCoordSysType(26716, 26716);
            /* NAD 1927 UTM Zone 16N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_17N = new
            PrjCoordSysType(26717, 26717);
            /* NAD 1927 UTM Zone 17N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_18N = new
            PrjCoordSysType(26718, 26718);
            /* NAD 1927 UTM Zone 18N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_19N = new
            PrjCoordSysType(26719, 26719);
            /* NAD 1927 UTM Zone 19N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_20N = new
            PrjCoordSysType(26720, 26720);
            /* NAD 1927 UTM Zone 20N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_21N = new
            PrjCoordSysType(26721, 26721);
            /* NAD 1927 UTM Zone 21N              */
    public static final PrjCoordSysType PCS_NAD_1927_UTM_22N = new
            PrjCoordSysType(26722, 26722);
            /* NAD 1927 UTM Zone 22N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_3N = new
            PrjCoordSysType(26903, 26903);
            /* NAD 1983 UTM Zone 3N               */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_4N = new
            PrjCoordSysType(26904, 26904);
            /* NAD 1983 UTM Zone 4N               */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_5N = new
            PrjCoordSysType(26905, 26905);
            /* NAD 1983 UTM Zone 5N               */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_6N = new
            PrjCoordSysType(26906, 26906);
            /* NAD 1983 UTM Zone 6N               */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_7N = new
            PrjCoordSysType(26907, 26907);
            /* NAD 1983 UTM Zone 7N               */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_8N = new
            PrjCoordSysType(26908, 26908);
            /* NAD 1983 UTM Zone 8N               */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_9N = new
            PrjCoordSysType(26909, 26909);
            /* NAD 1983 UTM Zone 9N               */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_10N = new
            PrjCoordSysType(26910, 26910);
            /* NAD 1983 UTM Zone 10N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_11N = new
            PrjCoordSysType(26911, 26911);
            /* NAD 1983 UTM Zone 11N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_12N = new
            PrjCoordSysType(26912, 26912);
            /* NAD 1983 UTM Zone 12N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_13N = new
            PrjCoordSysType(26913, 26913);
            /* NAD 1983 UTM Zone 13N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_14N = new
            PrjCoordSysType(26914, 26914);
            /* NAD 1983 UTM Zone 14N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_15N = new
            PrjCoordSysType(26915, 26915);
            /* NAD 1983 UTM Zone 15N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_16N = new
            PrjCoordSysType(26916, 26916);
            /* NAD 1983 UTM Zone 16N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_17N = new
            PrjCoordSysType(26917, 26917);
            /* NAD 1983 UTM Zone 17N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_18N = new
            PrjCoordSysType(26918, 26918);
            /* NAD 1983 UTM Zone 18N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_19N = new
            PrjCoordSysType(26919, 26919);
            /* NAD 1983 UTM Zone 19N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_20N = new
            PrjCoordSysType(26920, 26920);
            /* NAD 1983 UTM Zone 20N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_21N = new
            PrjCoordSysType(26921, 26921);
            /* NAD 1983 UTM Zone 21N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_22N = new
            PrjCoordSysType(26922, 26922);
            /* NAD 1983 UTM Zone 22N              */
    public static final PrjCoordSysType PCS_NAD_1983_UTM_23N = new
            PrjCoordSysType(26923, 26923);
            /* NAD 1983 UTM Zone 23N              */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_28N = new
            PrjCoordSysType(25828, 25828);
            /* ETRS 1989 UTM Zone 28N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_29N = new
            PrjCoordSysType(25829, 25829);
            /* ETRS 1989 UTM Zone 29N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_30N = new
            PrjCoordSysType(25830, 25830);
            /* ETRS 1989 UTM Zone 30N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_31N = new
            PrjCoordSysType(25831, 25831);
            /* ETRS 1989 UTM Zone 31N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_32N = new
            PrjCoordSysType(25832, 25832);
            /* ETRS 1989 UTM Zone 32N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_33N = new
            PrjCoordSysType(25833, 25833);
            /* ETRS 1989 UTM Zone 33N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_34N = new
            PrjCoordSysType(25834, 25834);
            /* ETRS 1989 UTM Zone 34N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_35N = new
            PrjCoordSysType(25835, 25835);
            /* ETRS 1989 UTM Zone 35N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_36N = new
            PrjCoordSysType(25836, 25836);
            /* ETRS 1989 UTM Zone 36N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_37N = new
            PrjCoordSysType(25837, 25837);
            /* ETRS 1989 UTM Zone 37N            */
    public static final PrjCoordSysType PCS_ETRS_1989_UTM_38N = new
            PrjCoordSysType(25838, 25838);
            /* ETRS 1989 UTM Zone 38N            */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_4 = new
            PrjCoordSysType(28404, 28404);
            /* Pulkovo 1942 GK Zone 4          */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_5 = new
            PrjCoordSysType(28405, 28405);
            /* Pulkovo 1942 GK Zone 5          */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_6 = new
            PrjCoordSysType(28406, 28406);
            /* Pulkovo 1942 GK Zone 6          */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_7 = new
            PrjCoordSysType(28407, 28407);
            /* Pulkovo 1942 GK Zone 7          */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_8 = new
            PrjCoordSysType(28408, 28408);
            /* Pulkovo 1942 GK Zone 8          */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_9 = new
            PrjCoordSysType(28409, 28409);
            /* Pulkovo 1942 GK Zone 9          */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_10 = new
            PrjCoordSysType(28410, 28410);
            /* Pulkovo 1942 GK Zone 10         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_11 = new
            PrjCoordSysType(28411, 28411);
            /* Pulkovo 1942 GK Zone 11         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_12 = new
            PrjCoordSysType(28412, 28412);
            /* Pulkovo 1942 GK Zone 12         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_13 = new
            PrjCoordSysType(28413, 28413);
            /* Pulkovo 1942 GK Zone 13         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_14 = new
            PrjCoordSysType(28414, 28414);
            /* Pulkovo 1942 GK Zone 14         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_15 = new
            PrjCoordSysType(28415, 28415);
            /* Pulkovo 1942 GK Zone 15         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_16 = new
            PrjCoordSysType(28416, 28416);
            /* Pulkovo 1942 GK Zone 16         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_17 = new
            PrjCoordSysType(28417, 28417);
            /* Pulkovo 1942 GK Zone 17         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_18 = new
            PrjCoordSysType(28418, 28418);
            /* Pulkovo 1942 GK Zone 18         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_19 = new
            PrjCoordSysType(28419, 28419);
            /* Pulkovo 1942 GK Zone 19         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_20 = new
            PrjCoordSysType(28420, 28420);
            /* Pulkovo 1942 GK Zone 20         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_21 = new
            PrjCoordSysType(28421, 28421);
            /* Pulkovo 1942 GK Zone 21         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_22 = new
            PrjCoordSysType(28422, 28422);
            /* Pulkovo 1942 GK Zone 22         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_23 = new
            PrjCoordSysType(28423, 28423);
            /* Pulkovo 1942 GK Zone 23         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_24 = new
            PrjCoordSysType(28424, 28424);
            /* Pulkovo 1942 GK Zone 24         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_25 = new
            PrjCoordSysType(28425, 28425);
            /* Pulkovo 1942 GK Zone 25         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_26 = new
            PrjCoordSysType(28426, 28426);
            /* Pulkovo 1942 GK Zone= 26         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_27 = new
            PrjCoordSysType(28427, 28427);
            /* Pulkovo 1942 GK Zone 27         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_28 = new
            PrjCoordSysType(28428, 28428);
            /* Pulkovo 1942 GK Zone=  28         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_29 = new
            PrjCoordSysType(28429, 28429);
            /* Pulkovo 1942 GK Zone=  29         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_30 = new
            PrjCoordSysType(28430, 28430);
            /* Pulkovo 1942 GK Zone 30         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_31 = new
            PrjCoordSysType(28431, 28431);
            /* Pulkovo 1942 GK Zone 31         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_32 = new
            PrjCoordSysType(28432, 28432);
            /* Pulkovo 1942 GK Zone= 32         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_4N = new
            PrjCoordSysType(28464, 28464);
            /* Pulkovo 1942 GK Zone 4N         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_5N = new
            PrjCoordSysType(28465, 28465);
            /* Pulkovo 1942 GK Zone 5N         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_6N = new
            PrjCoordSysType(28466, 28466);
            /* Pulkovo 1942 GK Zone 6N         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_7N = new
            PrjCoordSysType(28467, 28467);
            /* Pulkovo 1942 GK Zone 7N         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_8N = new
            PrjCoordSysType(28468, 28468);
            /* Pulkovo 1942 GK Zone 8N         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_9N = new
            PrjCoordSysType(28469, 28469);
            /* Pulkovo 1942 GK Zone 9N         */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_10N = new
            PrjCoordSysType(28470, 28470);
            /* Pulkovo 1942 GK Zone 10N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_11N = new
            PrjCoordSysType(28471, 28471);
            /* Pulkovo 1942 GK Zone 11N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_12N = new
            PrjCoordSysType(28472, 28472);
            /* Pulkovo 1942 GK Zone 12N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_13N = new
            PrjCoordSysType(28473, 28473);
            /* Pulkovo 1942 GK Zone 13N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_14N = new
            PrjCoordSysType(28474, 28474);
            /* Pulkovo 1942 GK Zone 14N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_15N = new
            PrjCoordSysType(28475, 28475);
            /* Pulkovo 1942 GK Zone 15N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_16N = new
            PrjCoordSysType(28476, 28476);
            /* Pulkovo 1942 GK Zone 16N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_17N = new
            PrjCoordSysType(28477, 28477);
            /* Pulkovo 1942 GK Zone 17N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_18N = new
            PrjCoordSysType(28478, 28478);
            /* Pulkovo 1942 GK Zone 18N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_19N = new
            PrjCoordSysType(28479, 28479);
            /* Pulkovo 1942 GK Zone 19N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_20N = new
            PrjCoordSysType(28480, 28480);
            /* Pulkovo 1942 GK Zone 20N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_21N = new
            PrjCoordSysType(28481, 28481);
            /* Pulkovo 1942 GK Zone 21N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_22N = new
            PrjCoordSysType(28482, 28482);
            /* Pulkovo 1942 GK Zone 22N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_23N = new
            PrjCoordSysType(28483, 28483);
            /* Pulkovo 1942 GK Zone 23N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_24N = new
            PrjCoordSysType(28484, 28484);
            /* Pulkovo 1942 GK Zone 24N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_25N = new
            PrjCoordSysType(28485, 28485);
            /* Pulkovo 1942 GK Zone 25N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_26N = new
            PrjCoordSysType(28486, 28486);
            /* Pulkovo 1942 GK Zone= 26N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_27N = new
            PrjCoordSysType(28487, 28487);
            /* Pulkovo 1942 GK Zone 27N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_28N = new
            PrjCoordSysType(28488, 28488);
            /* Pulkovo 1942 GK Zone=  28N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_29N = new
            PrjCoordSysType(28489, 28489);
            /* Pulkovo 1942 GK Zone=  29N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_30N = new
            PrjCoordSysType(28490, 28490);
            /* Pulkovo 1942 GK Zone 30N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_31N = new
            PrjCoordSysType(28491, 28491);
            /* Pulkovo 1942 GK Zone 31N        */
    public static final PrjCoordSysType PCS_PULKOVO_1942_GK_32N = new
            PrjCoordSysType(28492, 28492);
            /* Pulkovo 1942 GK Zone= 32N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_4 = new
            PrjCoordSysType(20004, 20004);
            /* Pulkovo 1995 GK Zone 4          */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_5 = new
            PrjCoordSysType(20005, 20005);
            /* Pulkovo 1995 GK Zone 5          */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_6 = new
            PrjCoordSysType(20006, 20006);
            /* Pulkovo 1995 GK Zone 6          */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_7 = new
            PrjCoordSysType(20007, 20007);
            /* Pulkovo 1995 GK Zone 7          */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_8 = new
            PrjCoordSysType(20008, 20008);
            /* Pulkovo 1995 GK Zone 8          */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_9 = new
            PrjCoordSysType(20009, 20009);
            /* Pulkovo 1995 GK Zone 9          */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_10 = new
            PrjCoordSysType(20010, 20010);
            /* Pulkovo 1995 GK Zone 10         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_11 = new
            PrjCoordSysType(20011, 20011);
            /* Pulkovo 1995 GK Zone 11         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_12 = new
            PrjCoordSysType(20012, 20012);
            /* Pulkovo 1995 GK Zone 12         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_13 = new
            PrjCoordSysType(20013, 20013);
            /* Pulkovo 1995 GK Zone 13         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_14 = new
            PrjCoordSysType(20014, 20014);
            /* Pulkovo 1995 GK Zone 14         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_15 = new
            PrjCoordSysType(20015, 20015);
            /* Pulkovo 1995 GK Zone 15         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_16 = new
            PrjCoordSysType(20016, 20016);
            /* Pulkovo 1995 GK Zone 16         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_17 = new
            PrjCoordSysType(20017, 20017);
            /* Pulkovo 1995 GK Zone 17         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_18 = new
            PrjCoordSysType(20018, 20018);
            /* Pulkovo 1995 GK Zone 18         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_19 = new
            PrjCoordSysType(20019, 20019);
            /* Pulkovo 1995 GK Zone 19         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_20 = new
            PrjCoordSysType(20020, 20020);
            /* Pulkovo 1995 GK Zone= 20         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_21 = new
            PrjCoordSysType(20021, 20021);
            /* Pulkovo 1995 GK Zone 21         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_22 = new
            PrjCoordSysType(20022, 20022);
            /* Pulkovo 1995 GK Zone 22         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_23 = new
            PrjCoordSysType(20023, 20023);
            /* Pulkovo 1995 GK Zone 23         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_24 = new
            PrjCoordSysType(20024, 20024);
            /* Pulkovo 1995 GK Zone 24         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_25 = new
            PrjCoordSysType(20025, 20025);
            /* Pulkovo 1995 GK Zone 25         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_26 = new
            PrjCoordSysType(20026, 20026);
            /* Pulkovo 1995 GK Zone= 26         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_27 = new
            PrjCoordSysType(20027, 20027);
            /* Pulkovo 1995 GK Zone 27         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_28 = new
            PrjCoordSysType(20028, 20028);
            /* Pulkovo 1995 GK Zone=  28         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_29 = new
            PrjCoordSysType(20029, 20029);
            /* Pulkovo 1995 GK Zone=  29         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_30 = new
            PrjCoordSysType(20030, 20030);
            /* Pulkovo 1995 GK Zone 30         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_31 = new
            PrjCoordSysType(20031, 20031);
            /* Pulkovo 1995 GK Zone 31         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_32 = new
            PrjCoordSysType(20032, 20032);
            /* Pulkovo 1995 GK Zone= 32         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_4N = new
            PrjCoordSysType(20064, 20064);
            /* Pulkovo 1995 GK Zone 4N         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_5N = new
            PrjCoordSysType(20065, 20065);
            /* Pulkovo 1995 GK Zone 5N         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_6N = new
            PrjCoordSysType(20066, 20066);
            /* Pulkovo 1995 GK Zone 6N         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_7N = new
            PrjCoordSysType(20067, 20067);
            /* Pulkovo 1995 GK Zone 7N         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_8N = new
            PrjCoordSysType(20068, 20068);
            /* Pulkovo 1995 GK Zone 8N         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_9N = new
            PrjCoordSysType(20069, 20069);
            /* Pulkovo 1995 GK Zone 9N         */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_10N = new
            PrjCoordSysType(20070, 20070);
            /* Pulkovo 1995 GK Zone 10N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_11N = new
            PrjCoordSysType(20071, 20071);
            /* Pulkovo 1995 GK Zone 11N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_12N = new
            PrjCoordSysType(20072, 20072);
            /* Pulkovo 1995 GK Zone 12N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_13N = new
            PrjCoordSysType(20073, 20073);
            /* Pulkovo 1995 GK Zone 13N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_14N = new
            PrjCoordSysType(20074, 20074);
            /* Pulkovo 1995 GK Zone 14N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_15N = new
            PrjCoordSysType(20075, 20075);
            /* Pulkovo 1995 GK Zone 15N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_16N = new
            PrjCoordSysType(20076, 20076);
            /* Pulkovo 1995 GK Zone 16N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_17N = new
            PrjCoordSysType(20077, 20077);
            /* Pulkovo 1995 GK Zone 17N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_18N = new
            PrjCoordSysType(20078, 20078);
            /* Pulkovo 1995 GK Zone 18N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_19N = new
            PrjCoordSysType(20079, 20079);
            /* Pulkovo 1995 GK Zone 19N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_20N = new
            PrjCoordSysType(20080, 20080);
            /* Pulkovo 1995 GK Zone= 20N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_21N = new
            PrjCoordSysType(20081, 20081);
            /* Pulkovo 1995 GK Zone 21N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_22N = new
            PrjCoordSysType(20082, 20082);
            /* Pulkovo 1995 GK Zone 22N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_23N = new
            PrjCoordSysType(20083, 20083);
            /* Pulkovo 1995 GK Zone 23N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_24N = new
            PrjCoordSysType(20084, 20084);
            /* Pulkovo 1995 GK Zone 24N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_25N = new
            PrjCoordSysType(20085, 20085);
            /* Pulkovo 1995 GK Zone 25N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_26N = new
            PrjCoordSysType(20086, 20086);
            /* Pulkovo 1995 GK Zone 26N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_27N = new
            PrjCoordSysType(20087, 20087);
            /* Pulkovo 1995 GK Zone 27N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_28N = new
            PrjCoordSysType(20088, 20088);
            /* Pulkovo 1995 GK Zone 28N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_29N = new
            PrjCoordSysType(20089, 20089);
            /* Pulkovo 1995 GK Zone=  29N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_30N = new
            PrjCoordSysType(20090, 20090);
            /* Pulkovo 1995 GK Zone 30N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_31N = new
            PrjCoordSysType(20091, 20091);
            /* Pulkovo 1995 GK Zone 31N        */
    public static final PrjCoordSysType PCS_PULKOVO_1995_GK_32N = new
            PrjCoordSysType(20092, 20092);
            /* Pulkovo 1995 GK Zone= 32N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_13 = new
            PrjCoordSysType(21413, 21413);
            /* Beijing 1954 GK Zone 13         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_14 = new
            PrjCoordSysType(21414, 21414);
            /* Beijing 1954 GK Zone 14         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_15 = new
            PrjCoordSysType(21415, 21415);
            /* Beijing 1954 GK Zone 15         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_16 = new
            PrjCoordSysType(21416, 21416);
            /* Beijing 1954 GK Zone 16         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_17 = new
            PrjCoordSysType(21417, 21417);
            /* Beijing 1954 GK Zone 17         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_18 = new
            PrjCoordSysType(21418, 21418);
            /* Beijing 1954 GK Zone 18         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_19 = new
            PrjCoordSysType(21419, 21419);
            /* Beijing 1954 GK Zone 19         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_20 = new
            PrjCoordSysType(21420, 21420);
            /* Beijing 1954 GK Zone= 20         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_21 = new
            PrjCoordSysType(21421, 21421);
            /* Beijing 1954 GK Zone= 21         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_22 = new
            PrjCoordSysType(21422, 21422);
            /* Beijing 1954 GK Zone 22         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_23 = new
            PrjCoordSysType(21423, 21423);
            /* Beijing 1954 GK Zone 23         */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_13N = new
            PrjCoordSysType(21473, 21473);
            /* Beijing 1954 GK Zone 13N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_14N = new
            PrjCoordSysType(21474, 21474);
            /* Beijing 1954 GK Zone 14N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_15N = new
            PrjCoordSysType(21475, 21475);
            /* Beijing 1954 GK Zone 15N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_16N = new
            PrjCoordSysType(21476, 21476);
            /* Beijing 1954 GK Zone 16N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_17N = new
            PrjCoordSysType(21477, 21477);
            /* Beijing 1954 GK Zone 17N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_18N = new
            PrjCoordSysType(21478, 21478);
            /* Beijing 1954 GK Zone 18N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_19N = new
            PrjCoordSysType(21479, 21479);
            /* Beijing 1954 GK Zone 19N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_20N = new
            PrjCoordSysType(21480, 21480);
            /* Beijing 1954 GK Zone 20N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_21N = new
            PrjCoordSysType(21481, 21481);
            /* Beijing 1954 GK Zone= 21N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_22N = new
            PrjCoordSysType(21482, 21482);
            /* Beijing 1954 GK Zone 22N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_GK_23N = new
            PrjCoordSysType(21483, 21483);
            /* Beijing 1954 GK Zone=  23N        */
    public static final PrjCoordSysType PCS_ED_1950_UTM_28N = new
            PrjCoordSysType(23028, 23028);
            /* European Datum 1950 UTM Zone 28N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_29N = new
            PrjCoordSysType(23029, 23029);
            /* European Datum 1950 UTM Zone=  29N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_30N = new
            PrjCoordSysType(23030, 23030);
            /* European Datum 1950 UTM Zone 30N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_31N = new
            PrjCoordSysType(23031, 23031);
            /* European Datum 1950 UTM Zone 31N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_32N = new
            PrjCoordSysType(23032, 23032);
            /* European Datum 1950 UTM Zone= 32N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_33N = new
            PrjCoordSysType(23033, 23033);
            /* European Datum 1950 UTM Zone 33N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_34N = new
            PrjCoordSysType(23034, 23034);
            /* European Datum 1950 UTM Zone 34N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_35N = new
            PrjCoordSysType(23035, 23035);
            /* European Datum 1950 UTM Zone 35N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_36N = new
            PrjCoordSysType(23036, 23036);
            /* European Datum 1950 UTM Zone 36N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_37N = new
            PrjCoordSysType(23037, 23037);
            /* European Datum 1950 UTM Zone 37N    */
    public static final PrjCoordSysType PCS_ED_1950_UTM_38N = new
            PrjCoordSysType(23038, 23038);
            /* European Datum 1950 UTM Zone 38N    */
    public static final PrjCoordSysType PCS_ATS_1977_UTM_19N = new PrjCoordSysType(2219,2219); /* ATS 1977 UTM Zone 19N              */
    public static final PrjCoordSysType PCS_ATS_1977_UTM_20N = new PrjCoordSysType(2220,2220); /* ATS 1977 UTM Zone 20N              */
    public static final PrjCoordSysType PCS_KKJ_FINLAND_1 = new PrjCoordSysType(
            2391, 2391); /* Finland Zone 1                      */
    public static final PrjCoordSysType PCS_KKJ_FINLAND_2 = new PrjCoordSysType(
            2392, 2392); /* Finland Zone 2                      */
    public static final PrjCoordSysType PCS_KKJ_FINLAND_3 = new PrjCoordSysType(
            2393, 2393); /* Finland Zone 3                      */
    public static final PrjCoordSysType PCS_KKJ_FINLAND_4 = new PrjCoordSysType(
            2394, 2394); /* Finland Zone 4                      */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_18N = new
            PrjCoordSysType(29118, 29118);
            /* South American 1969 UTM Zone 18N   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_19N = new
            PrjCoordSysType(29119, 29119);
            /* South American 1969 UTM Zone 19N   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_20N = new
            PrjCoordSysType(29120, 29120);
            /* South American 1969 UTM Zone 20N   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_21N = new
            PrjCoordSysType(29121, 29121);
            /* South American 1969 UTM Zone= 21N   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_22N = new
            PrjCoordSysType(29122, 29122);
            /* South American 1969 UTM Zone 22N   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_17S = new
            PrjCoordSysType(29177, 29177);
            /* South American 1969 UTM Zone 17S   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_18S = new
            PrjCoordSysType(29178, 29178);
            /* South American 1969 UTM Zone 18S   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_19S = new
            PrjCoordSysType(29179, 29179);
            /* South American 1969 UTM Zone 19S   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_20S = new
            PrjCoordSysType(29180, 29180);
            /* South American 1969 UTM Zone 20S   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_21S = new
            PrjCoordSysType(29181, 29181);
            /* South American 1969 UTM Zone 21S   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_22S = new
            PrjCoordSysType(29182, 29182);
            /* South American 1969 UTM Zone 22S   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_23S = new
            PrjCoordSysType(29183, 29183);
            /* South American 1969 UTM Zone=  23S   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_24S = new
            PrjCoordSysType(29184, 29184);
            /* South American 1969 UTM Zone 24S   */
    public static final PrjCoordSysType PCS_SAD_1969_UTM_25S = new
            PrjCoordSysType(29185, 29185);
            /* South American 1969 UTM Zone 25S   */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_48 = new
            PrjCoordSysType(20248, 20248);
            /* AGD 1966 AMG Zone 48                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_49 = new
            PrjCoordSysType(20249, 20249);
            /* AGD 1966 AMG Zone 49                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_50 = new
            PrjCoordSysType(20250, 20250);
            /* AGD 1966 AMG Zone 50                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_51 = new
            PrjCoordSysType(20251, 20251);
            /* AGD 1966 AMG Zone 51                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_52 = new
            PrjCoordSysType(20252, 20252);
            /* AGD 1966 AMG Zone 52                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_53 = new
            PrjCoordSysType(20253, 20253);
            /* AGD 1966 AMG Zone 53                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_54 = new
            PrjCoordSysType(20254, 20254);
            /* AGD 1966 AMG Zone 54                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_55 = new
            PrjCoordSysType(20255, 20255);
            /* AGD 1966 AMG Zone 55                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_56 = new
            PrjCoordSysType(20256, 20256);
            /* AGD 1966 AMG Zone 56                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_57 = new
            PrjCoordSysType(20257, 20257);
            /* AGD 1966 AMG Zone 57                */
    public static final PrjCoordSysType PCS_AGD_1966_AMG_58 = new
            PrjCoordSysType(20258, 20258);
            /* AGD 1966 AMG Zone 58                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_48 = new
            PrjCoordSysType(20348, 20348);
            /* AGD 1984 AMG Zone 48                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_49 = new
            PrjCoordSysType(20349, 20349);
            /* AGD 1984 AMG Zone 49                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_50 = new
            PrjCoordSysType(20350, 20350);
            /* AGD 1984 AMG Zone 50                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_51 = new
            PrjCoordSysType(20351, 20351);
            /* AGD 1984 AMG Zone 51                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_52 = new
            PrjCoordSysType(20352, 20352);
            /* AGD 1984 AMG Zone 52                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_53 = new
            PrjCoordSysType(20353, 20353);
            /* AGD 1984 AMG Zone 53                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_54 = new
            PrjCoordSysType(20354, 20354);
            /* AGD 1984 AMG Zone 54                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_55 = new
            PrjCoordSysType(20355, 20355);
            /* AGD 1984 AMG Zone 55                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_56 = new
            PrjCoordSysType(20356, 20356);
            /* AGD 1984 AMG Zone 56                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_57 = new
            PrjCoordSysType(20357, 20357);
            /* AGD 1984 AMG Zone 57                */
    public static final PrjCoordSysType PCS_AGD_1984_AMG_58 = new
            PrjCoordSysType(20358, 20358);
            /* AGD 1984 AMG Zone 58                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_48 = new
            PrjCoordSysType(28348, 28348);
            /* GDA 1994 MGA Zone 48                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_49 = new
            PrjCoordSysType(28349, 28349);
            /* GDA 1994 MGA Zone 49                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_50 = new
            PrjCoordSysType(28350, 28350);
            /* GDA 1994 MGA Zone 50                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_51 = new
            PrjCoordSysType(28351, 28351);
            /* GDA 1994 MGA Zone 51                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_52 = new
            PrjCoordSysType(28352, 28352);
            /* GDA 1994 MGA Zone 52                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_53 = new
            PrjCoordSysType(28353, 28353);
            /* GDA 1994 MGA Zone 53                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_54 = new
            PrjCoordSysType(28354, 28354);
            /* GDA 1994 MGA Zone 54                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_55 = new
            PrjCoordSysType(28355, 28355);
            /* GDA 1994 MGA Zone 55                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_56 = new
            PrjCoordSysType(28356, 28356);
            /* GDA 1994 MGA Zone 56                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_57 = new
            PrjCoordSysType(28357, 28357);
            /* GDA 1994 MGA Zone 57                */
    public static final PrjCoordSysType PCS_GDA_1994_MGA_58 = new
            PrjCoordSysType(28358, 28358);
            /* GDA 1994 MGA Zone 58                */
    public static final PrjCoordSysType PCS_NAD_1927_AL_E = new PrjCoordSysType(
            26729, 26729); /* NAD 1927 SPCS Zone Alabama East      */
    public static final PrjCoordSysType PCS_NAD_1927_AL_W = new PrjCoordSysType(
            26730, 26730); /* NAD 1927 SPCS Zone Alabama West      */
    public static final PrjCoordSysType PCS_NAD_1927_AK_1 = new PrjCoordSysType(
            26731, 26731); /* NAD 1927 SPCS Zone Alaska 1          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_2 = new PrjCoordSysType(
            26732, 26732); /* NAD 1927 SPCS Zone Alaska 2          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_3 = new PrjCoordSysType(
            26733, 26733); /* NAD 1927 SPCS Zone Alaska 3          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_4 = new PrjCoordSysType(
            26734, 26734); /* NAD 1927 SPCS Zone Alaska 4          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_5 = new PrjCoordSysType(
            26735, 26735); /* NAD 1927 SPCS Zone Alaska 5          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_6 = new PrjCoordSysType(
            26736, 26736); /* NAD 1927 SPCS Zone Alaska 6          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_7 = new PrjCoordSysType(
            26737, 26737); /* NAD 1927 SPCS Zone Alaska 7          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_8 = new PrjCoordSysType(
            26738, 26738); /* NAD 1927 SPCS Zone Alaska 8          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_9 = new PrjCoordSysType(
            26739, 26739); /* NAD 1927 SPCS Zone Alaska 9          */
    public static final PrjCoordSysType PCS_NAD_1927_AK_10 = new
            PrjCoordSysType(26740, 26740);
            /* NAD 1927 SPCS Zone Alaska 10         */
    public static final PrjCoordSysType PCS_NAD_1927_AZ_E = new PrjCoordSysType(
            26748, 26748); /* NAD 1927 SPCS Zone Arizona East      */
    public static final PrjCoordSysType PCS_NAD_1927_AZ_C = new PrjCoordSysType(
            26749, 26749); /* NAD 1927 SPCS Zone Arizona Central   */
    public static final PrjCoordSysType PCS_NAD_1927_AZ_W = new PrjCoordSysType(
            26750, 26750); /* NAD 1927 SPCS Zone Arizona West      */
    public static final PrjCoordSysType PCS_NAD_1927_AR_N = new PrjCoordSysType(
            26751, 26751); /* NAD 1927 SPCS Zone Arkansas North    */
    public static final PrjCoordSysType PCS_NAD_1927_AR_S = new PrjCoordSysType(
            26752, 26752); /* NAD 1927 SPCS Zone Arkansas South    */
    public static final PrjCoordSysType PCS_NAD_1927_CA_I = new PrjCoordSysType(
            26741, 26741); /* NAD 1927 SPCS Zone California I      */
    public static final PrjCoordSysType PCS_NAD_1927_CA_II = new
            PrjCoordSysType(26742, 26742);
            /* NAD 1927 SPCS Zone California II     */
    public static final PrjCoordSysType PCS_NAD_1927_CA_III = new
            PrjCoordSysType(26743, 26743);
            /* NAD 1927 SPCS Zone California II     */
    public static final PrjCoordSysType PCS_NAD_1927_CA_IV = new
            PrjCoordSysType(26744, 26744);
            /* NAD 1927 SPCS Zone California IV     */
    public static final PrjCoordSysType PCS_NAD_1927_CA_V = new PrjCoordSysType(
            26745, 26745); /* NAD 1927 SPCS Zone California V      */
    public static final PrjCoordSysType PCS_NAD_1927_CA_VI = new
            PrjCoordSysType(26746, 26746);
            /* NAD 1927 SPCS Zone California VI     */
    public static final PrjCoordSysType PCS_NAD_1927_CA_VII = new
            PrjCoordSysType(26747, 26747);
            /* NAD 1927 SPCS Zone California VII    */
    public static final PrjCoordSysType PCS_NAD_1927_CO_N = new PrjCoordSysType(
            26753, 26753); /* NAD 1927 SPCS Zone Colorado North    */
    public static final PrjCoordSysType PCS_NAD_1927_CO_C = new PrjCoordSysType(
            26754, 26754); /* NAD 1927 SPCS Zone Colorado Central  */
    public static final PrjCoordSysType PCS_NAD_1927_CO_S = new PrjCoordSysType(
            26755, 26755); /* NAD 1927 SPCS Zone Colorado South    */
    public static final PrjCoordSysType PCS_NAD_1927_CT = new PrjCoordSysType(
            26756, 26756); /* NAD 1927 SPCS Zone Connecticut       */
    public static final PrjCoordSysType PCS_NAD_1927_DE = new PrjCoordSysType(
            26757, 26757); /* NAD 1927 SPCS Zone Delaware          */
    public static final PrjCoordSysType PCS_NAD_1927_FL_E = new PrjCoordSysType(
            26758, 26758); /* NAD 1927 SPCS Zone Florida East      */
    public static final PrjCoordSysType PCS_NAD_1927_FL_W = new PrjCoordSysType(
            26759, 26759); /* NAD 1927 SPCS Zone Florida West      */
    public static final PrjCoordSysType PCS_NAD_1927_FL_N = new PrjCoordSysType(
            26760, 26760); /* NAD 1927 SPCS Zone Florida North     */
    public static final PrjCoordSysType PCS_NAD_1927_GA_E = new PrjCoordSysType(
            26766, 26766); /* NAD 1927 SPCS Zone Georgia East      */
    public static final PrjCoordSysType PCS_NAD_1927_GA_W = new PrjCoordSysType(
            26767, 26767); /* NAD 1927 SPCS Zone Georgia West      */
    public static final PrjCoordSysType PCS_NAD_1927_HI_1 = new PrjCoordSysType(
            26761, 26761); /* NAD 1927 SPCS Zone Hawaii 1          */
    public static final PrjCoordSysType PCS_NAD_1927_HI_2 = new PrjCoordSysType(
            26762, 26762); /* NAD 1927 SPCS Zone Hawaii 2          */
    public static final PrjCoordSysType PCS_NAD_1927_HI_3 = new PrjCoordSysType(
            26763, 26763); /* NAD 1927 SPCS Zone Hawaii 3          */
    public static final PrjCoordSysType PCS_NAD_1927_HI_4 = new PrjCoordSysType(
            26764, 26764); /* NAD 1927 SPCS Zone Hawaii 4          */
    public static final PrjCoordSysType PCS_NAD_1927_HI_5 = new PrjCoordSysType(
            26765, 26765); /* NAD 1927 SPCS Zone Hawaii 5          */
    public static final PrjCoordSysType PCS_NAD_1927_ID_E = new PrjCoordSysType(
            26768, 26768); /* NAD 1927 SPCS Zone Idaho East        */
    public static final PrjCoordSysType PCS_NAD_1927_ID_C = new PrjCoordSysType(
            26769, 26769); /* NAD 1927 SPCS Zone Idaho Central     */
    public static final PrjCoordSysType PCS_NAD_1927_ID_W = new PrjCoordSysType(
            26770, 26770); /* NAD 1927 SPCS Zone Idaho West        */
    public static final PrjCoordSysType PCS_NAD_1927_IL_E = new PrjCoordSysType(
            26771, 26771); /* NAD 1927 SPCS Zone Illinois East     */
    public static final PrjCoordSysType PCS_NAD_1927_IL_W = new PrjCoordSysType(
            26772, 26772); /* NAD 1927 SPCS Zone Illinois West     */
    public static final PrjCoordSysType PCS_NAD_1927_IN_E = new PrjCoordSysType(
            26773, 26773); /* NAD 1927 SPCS Zone Indiana East      */
    public static final PrjCoordSysType PCS_NAD_1927_IN_W = new PrjCoordSysType(
            26774, 26774); /* NAD 1927 SPCS Zone Indiana West      */
    public static final PrjCoordSysType PCS_NAD_1927_IA_N = new PrjCoordSysType(
            26775, 26775); /* NAD 1927 SPCS Zone Iowa North        */
    public static final PrjCoordSysType PCS_NAD_1927_IA_S = new PrjCoordSysType(
            26776, 26776); /* NAD 1927 SPCS Zone Iowa South        */
    public static final PrjCoordSysType PCS_NAD_1927_KS_N = new PrjCoordSysType(
            26777, 26777); /* NAD 1927 SPCS Zone Kansas North      */
    public static final PrjCoordSysType PCS_NAD_1927_KS_S = new PrjCoordSysType(
            26778, 26778); /* NAD 1927 SPCS Zone Kansas South      */
    public static final PrjCoordSysType PCS_NAD_1927_KY_N = new PrjCoordSysType(
            26779, 26779); /* NAD 1927 SPCS Zone Kentucky North    */
    public static final PrjCoordSysType PCS_NAD_1927_KY_S = new PrjCoordSysType(
            26780, 26780); /* NAD 1927 SPCS Zone Kentucky South    */
    public static final PrjCoordSysType PCS_NAD_1927_LA_N = new PrjCoordSysType(
            26781, 26781); /* NAD 1927 SPCS Zone Louisiana North   */
    public static final PrjCoordSysType PCS_NAD_1927_LA_S = new PrjCoordSysType(
            26782, 26782); /* NAD 1927 SPCS Zone Louisiana South   */
    public static final PrjCoordSysType PCS_NAD_1927_ME_E = new PrjCoordSysType(
            26783, 26783); /* NAD 1927 SPCS Zone Maine East        */
    public static final PrjCoordSysType PCS_NAD_1927_ME_W = new PrjCoordSysType(
            26784, 26784); /* NAD 1927 SPCS Zone Maine West        */
    public static final PrjCoordSysType PCS_NAD_1927_MD = new PrjCoordSysType(
            26785, 26785); /* NAD 1927 SPCS Zone Maryland          */
    public static final PrjCoordSysType PCS_NAD_1927_MA_M = new PrjCoordSysType(
            26786, 26786); /* NAD 1927 SPCS Zone Mass. Mainland    */
    public static final PrjCoordSysType PCS_NAD_1927_MA_I = new PrjCoordSysType(
            26787, 26787); /* NAD 1927 SPCS Zone Mass. Island      */
    public static final PrjCoordSysType PCS_NAD_1927_MI_N = new PrjCoordSysType(
            26788, 26788); /* NAD 1927 SPCS Zone Michigan North    */
    public static final PrjCoordSysType PCS_NAD_1927_MI_C = new PrjCoordSysType(
            26789, 26789); /* NAD 1927 SPCS Zone Michigan Central  */
    public static final PrjCoordSysType PCS_NAD_1927_MI_S = new PrjCoordSysType(
            26790, 26790); /* NAD 1927 SPCS Zone Michigan South    */
    public static final PrjCoordSysType PCS_NAD_1927_MN_N = new PrjCoordSysType(
            26791, 26791); /* NAD 1927 SPCS Zone Minnesota North   */
    public static final PrjCoordSysType PCS_NAD_1927_MN_C = new PrjCoordSysType(
            26792, 26792); /* NAD 1927 SPCS Zone Minnesota Central */
    public static final PrjCoordSysType PCS_NAD_1927_MN_S = new PrjCoordSysType(
            26793, 26793); /* NAD 1927 SPCS Zone Minnesota South   */
    public static final PrjCoordSysType PCS_NAD_1927_MS_E = new PrjCoordSysType(
            26794, 26794); /* NAD 1927 SPCS Zone Mississippi East  */
    public static final PrjCoordSysType PCS_NAD_1927_MS_W = new PrjCoordSysType(
            26795, 26795); /* NAD 1927 SPCS Zone Mississippi West  */
    public static final PrjCoordSysType PCS_NAD_1927_MO_E = new PrjCoordSysType(
            26796, 26796); /* NAD 1927 SPCS Zone Missouri East     */
    public static final PrjCoordSysType PCS_NAD_1927_MO_C = new PrjCoordSysType(
            26797, 26797); /* NAD 1927 SPCS Zone Missouri Central  */
    public static final PrjCoordSysType PCS_NAD_1927_MO_W = new PrjCoordSysType(
            26798, 26798); /* NAD 1927 SPCS Zone Missouri West     */
    public static final PrjCoordSysType PCS_NAD_1927_MT_N = new PrjCoordSysType(
            32001, 32001); /* NAD 1927 SPCS Zone Montana North     */
    public static final PrjCoordSysType PCS_NAD_1927_MT_C = new PrjCoordSysType(
            32002, 32002); /* NAD 1927 SPCS Zone Montana Central   */
    public static final PrjCoordSysType PCS_NAD_1927_MT_S = new PrjCoordSysType(
            32003, 32003); /* NAD 1927 SPCS Zone Montana South     */
    public static final PrjCoordSysType PCS_NAD_1927_NE_N = new PrjCoordSysType(
            32005, 32005); /* NAD 1927 SPCS Zone Nebraska North    */
    public static final PrjCoordSysType PCS_NAD_1927_NE_S = new PrjCoordSysType(
            32006, 32006); /* NAD 1927 SPCS Zone Nebraska South    */
    public static final PrjCoordSysType PCS_NAD_1927_NV_E = new PrjCoordSysType(
            32007, 32007); /* NAD 1927 SPCS Zone Nevada East       */
    public static final PrjCoordSysType PCS_NAD_1927_NV_C = new PrjCoordSysType(
            32008, 32008); /* NAD 1927 SPCS Zone Nevada Central    */
    public static final PrjCoordSysType PCS_NAD_1927_NV_W = new PrjCoordSysType(
            32009, 32009); /* NAD 1927 SPCS Zone Nevada West       */
    public static final PrjCoordSysType PCS_NAD_1927_NH = new PrjCoordSysType(
            32010, 32010); /* NAD 1927 SPCS Zone New Hampshire     */
    public static final PrjCoordSysType PCS_NAD_1927_NJ = new PrjCoordSysType(
            32011, 32011); /* NAD 1927 SPCS Zone New Jersey        */
    public static final PrjCoordSysType PCS_NAD_1927_NM_E = new PrjCoordSysType(
            32012, 32012); /* NAD 1927 SPCS Zone New Mexico East   */
    public static final PrjCoordSysType PCS_NAD_1927_NM_C = new PrjCoordSysType(
            32013, 32013); /* NAD 1927 SPCS Zone New Mexico Cent.  */
    public static final PrjCoordSysType PCS_NAD_1927_NM_W = new PrjCoordSysType(
            32014, 32014); /* NAD 1927 SPCS Zone New Mexico West   */
    public static final PrjCoordSysType PCS_NAD_1927_NY_E = new PrjCoordSysType(
            32015, 32015); /* NAD 1927 SPCS Zone New York East     */
    public static final PrjCoordSysType PCS_NAD_1927_NY_C = new PrjCoordSysType(
            32016, 32016); /* NAD 1927 SPCS Zone New York Central  */
    public static final PrjCoordSysType PCS_NAD_1927_NY_W = new PrjCoordSysType(
            32017, 32017); /* NAD 1927 SPCS Zone New York West     */
    public static final PrjCoordSysType PCS_NAD_1927_NY_LI = new
            PrjCoordSysType(32018, 32018);
            /* NAD 1927 SPCS Zone NY Long Island    */
    public static final PrjCoordSysType PCS_NAD_1927_NC = new PrjCoordSysType(
            32019, 32019); /* NAD 1927 SPCS Zone North Carolina    */
    public static final PrjCoordSysType PCS_NAD_1927_ND_N = new PrjCoordSysType(
            32020, 32020); /* NAD 1927 SPCS Zone North Dakota N    */
    public static final PrjCoordSysType PCS_NAD_1927_ND_S = new PrjCoordSysType(
            32021, 32021); /* NAD 1927 SPCS Zone North Dakota S    */
    public static final PrjCoordSysType PCS_NAD_1927_OH_N = new PrjCoordSysType(
            32022, 32022); /* NAD 1927 SPCS Zone Ohio North        */
    public static final PrjCoordSysType PCS_NAD_1927_OH_S = new PrjCoordSysType(
            32023, 32023); /* NAD 1927 SPCS Zone Ohio South        */
    public static final PrjCoordSysType PCS_NAD_1927_OK_N = new PrjCoordSysType(
            32024, 32024); /* NAD 1927 SPCS Zone Oklahoma North    */
    public static final PrjCoordSysType PCS_NAD_1927_OK_S = new PrjCoordSysType(
            32025, 32025); /* NAD 1927 SPCS Zone Oklahoma South    */
    public static final PrjCoordSysType PCS_NAD_1927_OR_N = new PrjCoordSysType(
            32026, 32026); /* NAD 1927 SPCS Zone Oregon North      */
    public static final PrjCoordSysType PCS_NAD_1927_OR_S = new PrjCoordSysType(
            32027, 32027); /* NAD 1927 SPCS Zone Oregon South      */
    public static final PrjCoordSysType PCS_NAD_1927_PA_N = new PrjCoordSysType(
            32028, 32028); /* NAD 1927 SPCS Zone Pennsylvania N    */
    public static final PrjCoordSysType PCS_NAD_1927_PA_S = new PrjCoordSysType(
            32029, 32029); /* NAD 1927 SPCS Zone Pennsylvania S    */
    public static final PrjCoordSysType PCS_NAD_1927_RI = new PrjCoordSysType(
            32030, 32030); /* NAD 1927 SPCS Zone Rhode Island      */
    public static final PrjCoordSysType PCS_NAD_1927_SC_N = new PrjCoordSysType(
            32031, 32031); /* NAD 1927 SPCS Zone South Carolina N  */
    public static final PrjCoordSysType PCS_NAD_1927_SC_S = new PrjCoordSysType(
            32033, 32033); /* NAD 1927 SPCS Zone South Carolina S  */
    public static final PrjCoordSysType PCS_NAD_1927_SD_N = new PrjCoordSysType(
            32034, 32034); /* NAD 1927 SPCS Zone South Dakota N    */
    public static final PrjCoordSysType PCS_NAD_1927_SD_S = new PrjCoordSysType(
            32035, 32035); /* NAD 1927 SPCS Zone South Dakota S    */
    public static final PrjCoordSysType PCS_NAD_1927_TN = new PrjCoordSysType(
            32036, 32036); /* NAD 1927 SPCS Zone Tennessee         */
    public static final PrjCoordSysType PCS_NAD_1927_TX_N = new PrjCoordSysType(
            32037, 32037); /* NAD 1927 SPCS Zone Texas North       */
    public static final PrjCoordSysType PCS_NAD_1927_TX_NC = new
            PrjCoordSysType(32038, 32038);
            /* NAD 1927 SPCS Zone Texas North Cent. */
    public static final PrjCoordSysType PCS_NAD_1927_TX_C = new PrjCoordSysType(
            32039, 32039); /* NAD 1927 SPCS Zone Texas Central     */
    public static final PrjCoordSysType PCS_NAD_1927_TX_SC = new
            PrjCoordSysType(32040, 32040);
            /* NAD 1927 SPCS Zone Texas South Cent. */
    public static final PrjCoordSysType PCS_NAD_1927_TX_S = new PrjCoordSysType(
            32041, 32041); /* NAD 1927 SPCS Zone Texas South       */
    public static final PrjCoordSysType PCS_NAD_1927_UT_N = new PrjCoordSysType(
            32042, 32042); /* NAD 1927 SPCS Zone Utah North        */
    public static final PrjCoordSysType PCS_NAD_1927_UT_C = new PrjCoordSysType(
            32043, 32043); /* NAD 1927 SPCS Zone Utah Central      */
    public static final PrjCoordSysType PCS_NAD_1927_UT_S = new PrjCoordSysType(
            32044, 32044); /* NAD 1927 SPCS Zone Utah South        */
    public static final PrjCoordSysType PCS_NAD_1927_VT = new PrjCoordSysType(
            32045, 32045); /* NAD 1927 SPCS Zone Vermont           */
    public static final PrjCoordSysType PCS_NAD_1927_VA_N = new PrjCoordSysType(
            32046, 32046); /* NAD 1927 SPCS Zone Virginia North    */
    public static final PrjCoordSysType PCS_NAD_1927_VA_S = new PrjCoordSysType(
            32047, 32047); /* NAD 1927 SPCS Zone Virginia South    */
    public static final PrjCoordSysType PCS_NAD_1927_WA_N = new PrjCoordSysType(
            32048, 32048); /* NAD 1927 SPCS Zone Washington North  */
    public static final PrjCoordSysType PCS_NAD_1927_WA_S = new PrjCoordSysType(
            32049, 32049); /* NAD 1927 SPCS Zone Washington South  */
    public static final PrjCoordSysType PCS_NAD_1927_WV_N = new PrjCoordSysType(
            32050, 32050); /* NAD 1927 SPCS Zone West Virginia N   */
    public static final PrjCoordSysType PCS_NAD_1927_WV_S = new PrjCoordSysType(
            32051, 32051); /* NAD 1927 SPCS Zone West Virginia S   */
    public static final PrjCoordSysType PCS_NAD_1927_WI_N = new PrjCoordSysType(
            32052, 32052); /* NAD 1927 SPCS Zone Wisconsin North   */
    public static final PrjCoordSysType PCS_NAD_1927_WI_C = new PrjCoordSysType(
            32053, 32053); /* NAD 1927 SPCS Zone Wisconsin Central */
    public static final PrjCoordSysType PCS_NAD_1927_WI_S = new PrjCoordSysType(
            32054, 32054); /* NAD 1927 SPCS Zone Wisconsin South   */
    public static final PrjCoordSysType PCS_NAD_1927_WY_E = new PrjCoordSysType(
            32055, 32055); /* NAD 1927 SPCS Zone Wyoming I East    */
    public static final PrjCoordSysType PCS_NAD_1927_WY_EC = new
            PrjCoordSysType(32056, 32056);
            /* NAD 1927 SPCS Zone Wyoming II EC     */
    public static final PrjCoordSysType PCS_NAD_1927_WY_WC = new
            PrjCoordSysType(32057, 32057);
            /* NAD 1927 SPCS Zone Wyoming III WC    */
    public static final PrjCoordSysType PCS_NAD_1927_WY_W = new PrjCoordSysType(
            32058, 32058); /* NAD 1927 SPCS Zone Wyoming IV West   */
    public static final PrjCoordSysType PCS_NAD_1927_PR = new PrjCoordSysType(
            32059, 32059); /* NAD 1927 SPCS Zone Puerto Rico       */
    public static final PrjCoordSysType PCS_NAD_1927_VI = new PrjCoordSysType(
            32060, 32060); /* NAD 1927 SPCS Zone St. Croix         */
    public static final PrjCoordSysType PCS_NAD_1927_GU = new PrjCoordSysType((32061 + 33000),(32061 + 33000)); /* NAD 1927 SPCS Zone Guam      */
    public static final PrjCoordSysType PCS_NAD_1983_AL_E = new PrjCoordSysType(
            26929, 26929); /* NAD 1983 SPCS Zone Alabama East      */
    public static final PrjCoordSysType PCS_NAD_1983_AL_W = new PrjCoordSysType(
            26930, 26930); /* NAD 1983 SPCS Zone Alabama West      */
    public static final PrjCoordSysType PCS_NAD_1983_AK_1 = new PrjCoordSysType(
            26931, 26931); /* NAD 1983 SPCS Zone Alaska 1          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_2 = new PrjCoordSysType(
            26932, 26932); /* NAD 1983 SPCS Zone Alaska 2          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_3 = new PrjCoordSysType(
            26933, 26933); /* NAD 1983 SPCS Zone Alaska 3          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_4 = new PrjCoordSysType(
            26934, 26934); /* NAD 1983 SPCS Zone Alaska 4          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_5 = new PrjCoordSysType(
            26935, 26935); /* NAD 1983 SPCS Zone Alaska 5          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_6 = new PrjCoordSysType(
            26936, 26936); /* NAD 1983 SPCS Zone Alaska 6          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_7 = new PrjCoordSysType(
            26937, 26937); /* NAD 1983 SPCS Zone Alaska 7          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_8 = new PrjCoordSysType(
            26938, 26938); /* NAD 1983 SPCS Zone Alaska 8          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_9 = new PrjCoordSysType(
            26939, 26939); /* NAD 1983 SPCS Zone Alaska 9          */
    public static final PrjCoordSysType PCS_NAD_1983_AK_10 = new
            PrjCoordSysType(26940, 26940);
            /* NAD 1983 SPCS Zone Alaska 10         */
    public static final PrjCoordSysType PCS_NAD_1983_AZ_E = new PrjCoordSysType(
            26948, 26948); /* NAD 1983 SPCS Zone Arizona East      */
    public static final PrjCoordSysType PCS_NAD_1983_AZ_C = new PrjCoordSysType(
            26949, 26949); /* NAD 1983 SPCS Zone Arizona Central   */
    public static final PrjCoordSysType PCS_NAD_1983_AZ_W = new PrjCoordSysType(
            26950, 26950); /* NAD 1983 SPCS Zone Arizona West      */
    public static final PrjCoordSysType PCS_NAD_1983_AR_N = new PrjCoordSysType(
            26951, 26951); /* NAD 1983 SPCS Zone Arkansas North    */
    public static final PrjCoordSysType PCS_NAD_1983_AR_S = new PrjCoordSysType(
            26952, 26952); /* NAD 1983 SPCS Zone Arkansas South    */
    public static final PrjCoordSysType PCS_NAD_1983_CA_I = new PrjCoordSysType(
            26941, 26941); /* NAD 1983 SPCS Zone California I      */
    public static final PrjCoordSysType PCS_NAD_1983_CA_II = new
            PrjCoordSysType(26942, 26942);
            /* NAD 1983 SPCS Zone California II     */
    public static final PrjCoordSysType PCS_NAD_1983_CA_III = new
            PrjCoordSysType(26943, 26943);
            /* NAD 1983 SPCS Zone California III    */
    public static final PrjCoordSysType PCS_NAD_1983_CA_IV = new
            PrjCoordSysType(26944, 26944);
            /* NAD 1983 SPCS Zone California IV     */
    public static final PrjCoordSysType PCS_NAD_1983_CA_V = new PrjCoordSysType(
            26945, 26945); /* NAD 1983 SPCS Zone California V      */
    public static final PrjCoordSysType PCS_NAD_1983_CA_VI = new
            PrjCoordSysType(26946, 26946);
            /* NAD 1983 SPCS Zone California VI     */
    public static final PrjCoordSysType PCS_NAD_1983_CO_N = new PrjCoordSysType(
            26953, 26953); /* NAD 1983 SPCS Zone Colorado North    */
    public static final PrjCoordSysType PCS_NAD_1983_CO_C = new PrjCoordSysType(
            26954, 26954); /* NAD 1983 SPCS Zone Colorado Central  */
    public static final PrjCoordSysType PCS_NAD_1983_CO_S = new PrjCoordSysType(
            26955, 26955); /* NAD 1983 SPCS Zone Colorado South    */
    public static final PrjCoordSysType PCS_NAD_1983_CT = new PrjCoordSysType(
            26956, 26956); /* NAD 1983 SPCS Zone Connecticut       */
    public static final PrjCoordSysType PCS_NAD_1983_DE = new PrjCoordSysType(
            26957, 26957); /* NAD 1983 SPCS Zone Delaware          */
    public static final PrjCoordSysType PCS_NAD_1983_FL_E = new PrjCoordSysType(
            26958, 26958); /* NAD 1983 SPCS Zone Florida East      */
    public static final PrjCoordSysType PCS_NAD_1983_FL_W = new PrjCoordSysType(
            26959, 26959); /* NAD 1983 SPCS Zone Florida West      */
    public static final PrjCoordSysType PCS_NAD_1983_FL_N = new PrjCoordSysType(
            26960, 26960); /* NAD 1983 SPCS Zone Florida North     */
    public static final PrjCoordSysType PCS_NAD_1983_GA_E = new PrjCoordSysType(
            26966, 26966); /* NAD 1983 SPCS Zone Georgia East      */
    public static final PrjCoordSysType PCS_NAD_1983_GA_W = new PrjCoordSysType(
            26967, 26967); /* NAD 1983 SPCS Zone Georgia West      */
    public static final PrjCoordSysType PCS_NAD_1983_HI_1 = new PrjCoordSysType(
            26961, 26961); /* NAD 1983 SPCS Zone Hawaii Zone 1     */
    public static final PrjCoordSysType PCS_NAD_1983_HI_2 = new PrjCoordSysType(
            26962, 26962); /* NAD 1983 SPCS Zone Hawaii Zone 2     */
    public static final PrjCoordSysType PCS_NAD_1983_HI_3 = new PrjCoordSysType(
            26963, 26963); /* NAD 1983 SPCS Zone Hawaii Zone 3     */
    public static final PrjCoordSysType PCS_NAD_1983_HI_4 = new PrjCoordSysType(
            26964, 26964); /* NAD 1983 SPCS Zone Hawaii Zone 4     */
    public static final PrjCoordSysType PCS_NAD_1983_HI_5 = new PrjCoordSysType(
            26965, 26965); /* NAD 1983 SPCS Zone Hawaii Zone 5     */
    public static final PrjCoordSysType PCS_NAD_1983_ID_E = new PrjCoordSysType(
            26968, 26968); /* NAD 1983 SPCS Zone Idaho East        */
    public static final PrjCoordSysType PCS_NAD_1983_ID_C = new PrjCoordSysType(
            26969, 26969); /* NAD 1983 SPCS Zone Idaho Central     */
    public static final PrjCoordSysType PCS_NAD_1983_ID_W = new PrjCoordSysType(
            26970, 26970); /* NAD 1983 SPCS Zone Idaho West        */
    public static final PrjCoordSysType PCS_NAD_1983_IL_E = new PrjCoordSysType(
            26971, 26971); /* NAD 1983 SPCS Zone Illinois East     */
    public static final PrjCoordSysType PCS_NAD_1983_IL_W = new PrjCoordSysType(
            26972, 26972); /* NAD 1983 SPCS Zone Illinois West     */
    public static final PrjCoordSysType PCS_NAD_1983_IN_E = new PrjCoordSysType(
            26973, 26973); /* NAD 1983 SPCS Zone Indiana East      */
    public static final PrjCoordSysType PCS_NAD_1983_IN_W = new PrjCoordSysType(
            26974, 26974); /* NAD 1983 SPCS Zone Indiana West      */
    public static final PrjCoordSysType PCS_NAD_1983_IA_N = new PrjCoordSysType(
            26975, 26975); /* NAD 1983 SPCS Zone Iowa North        */
    public static final PrjCoordSysType PCS_NAD_1983_IA_S = new PrjCoordSysType(
            26976, 26976); /* NAD 1983 SPCS Zone Iowa South        */
    public static final PrjCoordSysType PCS_NAD_1983_KS_N = new PrjCoordSysType(
            26977, 26977); /* NAD 1983 SPCS Zone Kansas North      */
    public static final PrjCoordSysType PCS_NAD_1983_KS_S = new PrjCoordSysType(
            26978, 26978); /* NAD 1983 SPCS Zone Kansas South      */
    public static final PrjCoordSysType PCS_NAD_1983_KY_N = new PrjCoordSysType(
            26979, 26979); /* NAD 1983 SPCS Zone Kentucky North    */
    public static final PrjCoordSysType PCS_NAD_1983_KY_S = new PrjCoordSysType(
            26980, 26980); /* NAD 1983 SPCS Zone Kentucky South    */
    public static final PrjCoordSysType PCS_NAD_1983_LA_N = new PrjCoordSysType(
            26981, 26981); /* NAD 1983 SPCS Zone Louisiana North   */
    public static final PrjCoordSysType PCS_NAD_1983_LA_S = new PrjCoordSysType(
            26982, 26982); /* NAD 1983 SPCS Zone Louisiana South   */
    public static final PrjCoordSysType PCS_NAD_1983_ME_E = new PrjCoordSysType(
            26983, 26983); /* NAD 1983 SPCS Zone Maine East        */
    public static final PrjCoordSysType PCS_NAD_1983_ME_W = new PrjCoordSysType(
            26984, 26984); /* NAD 1983 SPCS Zone Maine West        */
    public static final PrjCoordSysType PCS_NAD_1983_MD = new PrjCoordSysType(
            26985, 26985); /* NAD 1983 SPCS Zone Maryland          */
    public static final PrjCoordSysType PCS_NAD_1983_MA_M = new PrjCoordSysType(
            26986, 26986); /* NAD 1983 SPCS Zone Mass. Mainland    */
    public static final PrjCoordSysType PCS_NAD_1983_MA_I = new PrjCoordSysType(
            26987, 26987); /* NAD 1983 SPCS Zone Mass. Island      */
    public static final PrjCoordSysType PCS_NAD_1983_MI_N = new PrjCoordSysType(
            26988, 26988); /* NAD 1983 SPCS Zone Michigan North    */
    public static final PrjCoordSysType PCS_NAD_1983_MI_C = new PrjCoordSysType(
            26989, 26989); /* NAD 1983 SPCS Zone Michigan Central  */
    public static final PrjCoordSysType PCS_NAD_1983_MI_S = new PrjCoordSysType(
            26990, 26990); /* NAD 1983 SPCS Zone Michigan South    */
    public static final PrjCoordSysType PCS_NAD_1983_MN_N = new PrjCoordSysType(
            26991, 26991); /* NAD 1983 SPCS Zone Minnesota North   */
    public static final PrjCoordSysType PCS_NAD_1983_MN_C = new PrjCoordSysType(
            26992, 26992); /* NAD 1983 SPCS Zone Minnesota Central */
    public static final PrjCoordSysType PCS_NAD_1983_MN_S = new PrjCoordSysType(
            26993, 26993); /* NAD 1983 SPCS Zone Minnesota South   */
    public static final PrjCoordSysType PCS_NAD_1983_MS_E = new PrjCoordSysType(
            26994, 26994); /* NAD 1983 SPCS Zone Mississippi East  */
    public static final PrjCoordSysType PCS_NAD_1983_MS_W = new PrjCoordSysType(
            26995, 26995); /* NAD 1983 SPCS Zone Mississippi West  */
    public static final PrjCoordSysType PCS_NAD_1983_MO_E = new PrjCoordSysType(
            26996, 26996); /* NAD 1983 SPCS Zone Missouri East     */
    public static final PrjCoordSysType PCS_NAD_1983_MO_C = new PrjCoordSysType(
            26997, 26997); /* NAD 1983 SPCS Zone Missouri Central  */
    public static final PrjCoordSysType PCS_NAD_1983_MO_W = new PrjCoordSysType(
            26998, 26998); /* NAD 1983 SPCS Zone Missouri West     */
    public static final PrjCoordSysType PCS_NAD_1983_MT = new PrjCoordSysType(
            32100, 32100); /* NAD 1983 SPCS Zone Montana           */
    public static final PrjCoordSysType PCS_NAD_1983_NE = new PrjCoordSysType(
            32104, 32104); /* NAD 1983 SPCS Zone Nebraska          */
    public static final PrjCoordSysType PCS_NAD_1983_NV_E = new PrjCoordSysType(
            32107, 32107); /* NAD 1983 SPCS Zone Nevada East       */
    public static final PrjCoordSysType PCS_NAD_1983_NV_C = new PrjCoordSysType(
            32108, 32108); /* NAD 1983 SPCS Zone Nevada Central    */
    public static final PrjCoordSysType PCS_NAD_1983_NV_W = new PrjCoordSysType(
            32109, 32109); /* NAD 1983 SPCS Zone Nevada West       */
    public static final PrjCoordSysType PCS_NAD_1983_NH = new PrjCoordSysType(
            32110, 32110); /* NAD 1983 SPCS Zone New Hampshire     */
    public static final PrjCoordSysType PCS_NAD_1983_NJ = new PrjCoordSysType(
            32111, 32111); /* NAD 1983 SPCS Zone New Jersey        */
    public static final PrjCoordSysType PCS_NAD_1983_NM_E = new PrjCoordSysType(
            32112, 32112); /* NAD 1983 SPCS Zone New Mexico East   */
    public static final PrjCoordSysType PCS_NAD_1983_NM_C = new PrjCoordSysType(
            32113, 32113); /* NAD 1983 SPCS Zone New Mexico Cent.  */
    public static final PrjCoordSysType PCS_NAD_1983_NM_W = new PrjCoordSysType(
            32114, 32114); /* NAD 1983 SPCS Zone New Mexico West   */
    public static final PrjCoordSysType PCS_NAD_1983_NY_E = new PrjCoordSysType(
            32115, 32115); /* NAD 1983 SPCS Zone New York East     */
    public static final PrjCoordSysType PCS_NAD_1983_NY_C = new PrjCoordSysType(
            32116, 32116); /* NAD 1983 SPCS Zone New York Central  */
    public static final PrjCoordSysType PCS_NAD_1983_NY_W = new PrjCoordSysType(
            32117, 32117); /* NAD 1983 SPCS Zone New York West     */
    public static final PrjCoordSysType PCS_NAD_1983_NY_LI = new
            PrjCoordSysType(32118, 32118);
            /* NAD 1983 SPCS Zone NY Long Island    */
    public static final PrjCoordSysType PCS_NAD_1983_NC = new PrjCoordSysType(
            32119, 32119); /* NAD 1983 SPCS Zone North Carolina    */
    public static final PrjCoordSysType PCS_NAD_1983_ND_N = new PrjCoordSysType(
            32120, 32120); /* NAD 1983 SPCS Zone North Dakota N    */
    public static final PrjCoordSysType PCS_NAD_1983_ND_S = new PrjCoordSysType(
            32121, 32121); /* NAD 1983 SPCS Zone North Dakota S    */
    public static final PrjCoordSysType PCS_NAD_1983_OH_N = new PrjCoordSysType(
            32122, 32122); /* NAD 1983 SPCS Zone Ohio North        */
    public static final PrjCoordSysType PCS_NAD_1983_OH_S = new PrjCoordSysType(
            32123, 32123); /* NAD 1983 SPCS Zone Ohio South        */
    public static final PrjCoordSysType PCS_NAD_1983_OK_N = new PrjCoordSysType(
            32124, 32124); /* NAD 1983 SPCS Zone Oklahoma North    */
    public static final PrjCoordSysType PCS_NAD_1983_OK_S = new PrjCoordSysType(
            32125, 32125); /* NAD 1983 SPCS Zone Oklahoma South    */
    public static final PrjCoordSysType PCS_NAD_1983_OR_N = new PrjCoordSysType(
            32126, 32126); /* NAD 1983 SPCS Zone Oregon North      */
    public static final PrjCoordSysType PCS_NAD_1983_OR_S = new PrjCoordSysType(
            32127, 32127); /* NAD 1983 SPCS Zone Oregon South      */
    public static final PrjCoordSysType PCS_NAD_1983_PA_N = new PrjCoordSysType(
            32128, 32128); /* NAD 1983 SPCS Zone Pennsylvania N    */
    public static final PrjCoordSysType PCS_NAD_1983_PA_S = new PrjCoordSysType(
            32129, 32129); /* NAD 1983 SPCS Zone Pennsylvania S    */
    public static final PrjCoordSysType PCS_NAD_1983_RI = new PrjCoordSysType(
            32130, 32130); /* NAD 1983 SPCS Zone Rhode Island      */
    public static final PrjCoordSysType PCS_NAD_1983_SC = new PrjCoordSysType(
            32133, 32133); /* NAD 1983 SPCS Zone South Carolina    */
    public static final PrjCoordSysType PCS_NAD_1983_SD_N = new PrjCoordSysType(
            32134, 32134); /* NAD 1983 SPCS Zone South Dakota N    */
    public static final PrjCoordSysType PCS_NAD_1983_SD_S = new PrjCoordSysType(
            32135, 32135); /* NAD 1983 SPCS Zone South Dakota S    */
    public static final PrjCoordSysType PCS_NAD_1983_TN = new PrjCoordSysType(
            32136, 32136); /* NAD 1983 SPCS Zone Tennessee         */
    public static final PrjCoordSysType PCS_NAD_1983_TX_N = new PrjCoordSysType(
            32137, 32137); /* NAD 1983 SPCS Zone Texas North       */
    public static final PrjCoordSysType PCS_NAD_1983_TX_NC = new
            PrjCoordSysType(32138, 32138);
            /* NAD 1983 SPCS Zone Texas North Cent. */
    public static final PrjCoordSysType PCS_NAD_1983_TX_C = new PrjCoordSysType(
            32139, 32139); /* NAD 1983 SPCS Zone Texas Central     */
    public static final PrjCoordSysType PCS_NAD_1983_TX_SC = new
            PrjCoordSysType(32140, 32140);
            /* NAD 1983 SPCS Zone Texas South Cent. */
    public static final PrjCoordSysType PCS_NAD_1983_TX_S = new PrjCoordSysType(
            32141, 32141); /* NAD 1983 SPCS Zone Texas South       */
    public static final PrjCoordSysType PCS_NAD_1983_UT_N = new PrjCoordSysType(
            32142, 32142); /* NAD 1983 SPCS Zone Utah North        */
    public static final PrjCoordSysType PCS_NAD_1983_UT_C = new PrjCoordSysType(
            32143, 32143); /* NAD 1983 SPCS Zone Utah Central      */
    public static final PrjCoordSysType PCS_NAD_1983_UT_S = new PrjCoordSysType(
            32144, 32144); /* NAD 1983 SPCS Zone Utah South        */
    public static final PrjCoordSysType PCS_NAD_1983_VT = new PrjCoordSysType(
            32145, 32145); /* NAD 1983 SPCS Zone Vermont           */
    public static final PrjCoordSysType PCS_NAD_1983_VA_N = new PrjCoordSysType(
            32146, 32146); /* NAD 1983 SPCS Zone Virginia North    */
    public static final PrjCoordSysType PCS_NAD_1983_VA_S = new PrjCoordSysType(
            32147, 32147); /* NAD 1983 SPCS Zone Virginia South    */
    public static final PrjCoordSysType PCS_NAD_1983_WA_N = new PrjCoordSysType(
            32148, 32148); /* NAD 1983 SPCS Zone Washington North  */
    public static final PrjCoordSysType PCS_NAD_1983_WA_S = new PrjCoordSysType(
            32149, 32149); /* NAD 1983 SPCS Zone Washington South  */
    public static final PrjCoordSysType PCS_NAD_1983_WV_N = new PrjCoordSysType(
            32150, 32150); /* NAD 1983 SPCS Zone West Virginia N   */
    public static final PrjCoordSysType PCS_NAD_1983_WV_S = new PrjCoordSysType(
            32151, 32151); /* NAD 1983 SPCS Zone West Virginia S   */
    public static final PrjCoordSysType PCS_NAD_1983_WI_N = new PrjCoordSysType(
            32152, 32152); /* NAD 1983 SPCS Zone Wisconsin North   */
    public static final PrjCoordSysType PCS_NAD_1983_WI_C = new PrjCoordSysType(
            32153, 32153); /* NAD 1983 SPCS Zone Wisconsin Central */
    public static final PrjCoordSysType PCS_NAD_1983_WI_S = new PrjCoordSysType(
            32154, 32154); /* NAD 1983 SPCS Zone Wisconsin South   */
    public static final PrjCoordSysType PCS_NAD_1983_WY_E = new PrjCoordSysType(
            32155, 32155); /* NAD 1983 SPCS Zone Wyoming I East    */
    public static final PrjCoordSysType PCS_NAD_1983_WY_EC = new
            PrjCoordSysType(32156, 32156);
            /* NAD 1983 SPCS Zone Wyoming II EC     */
    public static final PrjCoordSysType PCS_NAD_1983_WY_WC = new
            PrjCoordSysType(32157, 32157);
            /* NAD 1983 SPCS Zone Wyoming III WC    */
    public static final PrjCoordSysType PCS_NAD_1983_WY_W = new PrjCoordSysType(
            32158, 32158); /* NAD 1983 SPCS Zone Wyoming IV West   */
    public static final PrjCoordSysType PCS_NAD_1983_PR_VI = new
            PrjCoordSysType(32161, 32161);
            /* NAD 1983 SPCS Zone PR & St. Croix    */
    public static final PrjCoordSysType PCS_NAD_1983_GU = new PrjCoordSysType((32161 + 33000),(32161 + 33000)); /* NAD 1983 SPCS Zone Guam      */
    public static final PrjCoordSysType PCS_ADINDAN_UTM_37N = new
            PrjCoordSysType(20137, 20137); /* Adindan UTM Zone 37N          */
    public static final PrjCoordSysType PCS_ADINDAN_UTM_38N = new
            PrjCoordSysType(20138, 20138); /* Adindan UTM Zone 38N          */
    public static final PrjCoordSysType PCS_AFGOOYE_UTM_38N = new
            PrjCoordSysType(20538, 20538); /* Afgooye UTM Zone 38N          */
    public static final PrjCoordSysType PCS_AFGOOYE_UTM_39N = new
            PrjCoordSysType(20539, 20539); /* Afgooye UTM Zone 39N          */
    public static final PrjCoordSysType PCS_AIN_EL_ABD_UTM_37N = new
            PrjCoordSysType(20437, 20437); /* Ain el Abd 1970 UTM Zone 37N  */
    public static final PrjCoordSysType PCS_AIN_EL_ABD_UTM_38N = new
            PrjCoordSysType(20438, 20438); /* Ain el Abd 1970 UTM Zone 38N  */
    public static final PrjCoordSysType PCS_AIN_EL_ABD_UTM_39N = new
            PrjCoordSysType(20439, 20439); /* Ain el Abd 1970 UTM Zone 39N  */
    public static final PrjCoordSysType PCS_ARATU_UTM_22S = new PrjCoordSysType(
            20822, 20822); /* Aratu UTM Zone 22S            */
    public static final PrjCoordSysType PCS_ARATU_UTM_23S = new PrjCoordSysType(
            20823, 20823); /* Aratu UTM Zone=  23S            */
    public static final PrjCoordSysType PCS_ARATU_UTM_24S = new PrjCoordSysType(
            20824, 20824); /* Aratu UTM Zone 24S            */
    public static final PrjCoordSysType PCS_BATAVIA_UTM_48S = new
            PrjCoordSysType(21148, 21148); /* Batavia UTM Zone 48S          */
    public static final PrjCoordSysType PCS_BATAVIA_UTM_49S = new
            PrjCoordSysType(21149, 21149); /* Batavia UTM Zone 49S          */
    public static final PrjCoordSysType PCS_BATAVIA_UTM_50S = new
            PrjCoordSysType(21150, 21150); /* Batavia UTM Zone 50S          */
    public static final PrjCoordSysType PCS_BOGOTA_UTM_17N = new
            PrjCoordSysType(21817, 21817); /* Bogota UTM Zone 17N           */
    public static final PrjCoordSysType PCS_BOGOTA_UTM_18N = new
            PrjCoordSysType(21818, 21818); /* Bogota UTM Zone 18N           */
    public static final PrjCoordSysType PCS_CAMACUPA_UTM_32S = new
            PrjCoordSysType(22032, 22032); /* Camacupa UTM Zone= 32S         */
    public static final PrjCoordSysType PCS_CAMACUPA_UTM_33S = new
            PrjCoordSysType(22033, 22033); /* Camacupa UTM Zone 33S         */
    public static final PrjCoordSysType PCS_CARTHAGE_UTM_32N = new
            PrjCoordSysType(22332, 22332); /* Carthage UTM Zone= 32N         */
    public static final PrjCoordSysType PCS_CORREGO_ALEGRE_UTM_23S = new
            PrjCoordSysType(22523, 22523);
            /* Corrego Alegre UTM Zone=  23S   */
    public static final PrjCoordSysType PCS_CORREGO_ALEGRE_UTM_24S = new
            PrjCoordSysType(22524, 22524); /* Corrego Alegre UTM Zone 24S   */
    public static final PrjCoordSysType PCS_DATUM_73_UTM_ZONE_29N = new
            PrjCoordSysType(27429, 27429);
            /* Datum 73 UTM Zone=  29N         */
    public static final PrjCoordSysType PCS_DOUALA_UTM_32N = new
            PrjCoordSysType(22832, 22832); /* Douala UTM Zone= 32N           */
    public static final PrjCoordSysType PCS_FAHUD_UTM_39N = new PrjCoordSysType(
            23239, 23239); /* Fahud UTM Zone 39N            */
    public static final PrjCoordSysType PCS_FAHUD_UTM_40N = new PrjCoordSysType(
            23240, 23240); /* Fahud UTM Zone 40N            */
    public static final PrjCoordSysType PCS_GAROUA_UTM_33N = new
            PrjCoordSysType(23433, 23433); /* Garoua UTM Zone 33N           */
    public static final PrjCoordSysType PCS_GGRS_1987_GREEK_GRID = new PrjCoordSysType(2100, 2100);/* Greek Grid                    */
    public static final PrjCoordSysType PCS_ID_1974_UTM_46N = new
            PrjCoordSysType(23846, 23846); /* Indonesia 1974 UTM Zone 46N   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_47N = new
            PrjCoordSysType(23847, 23847); /* Indonesia 1974 UTM Zone 47N   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_48N = new
            PrjCoordSysType(23848, 23848); /* Indonesia 1974 UTM Zone 48N   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_49N = new
            PrjCoordSysType(23849, 23849); /* Indonesia 1974 UTM Zone 49N   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_50N = new
            PrjCoordSysType(23850, 23850); /* Indonesia 1974 UTM Zone 50N   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_51N = new
            PrjCoordSysType(23851, 23851); /* Indonesia 1974 UTM Zone 51N   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_52N = new
            PrjCoordSysType(23852, 23852); /* Indonesia 1974 UTM Zone 52N   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_53N = new
            PrjCoordSysType(23853, 23853); /* Indonesia 1974 UTM Zone 53N   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_46S = new
            PrjCoordSysType(23886, 23886); /* Indonesia 1974 UTM Zone 46S   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_47S = new
            PrjCoordSysType(23887, 23887); /* Indonesia 1974 UTM Zone 47S   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_48S = new
            PrjCoordSysType(23888, 23888); /* Indonesia 1974 UTM Zone 48S   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_49S = new
            PrjCoordSysType(23889, 23889); /* Indonesia 1974 UTM Zone 49S   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_50S = new
            PrjCoordSysType(23890, 23890); /* Indonesia 1974 UTM Zone 50S   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_51S = new
            PrjCoordSysType(23891, 23891); /* Indonesia 1974 UTM Zone 51S   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_52S = new
            PrjCoordSysType(23892, 23892); /* Indonesia 1974 UTM Zone 52S   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_53S = new
            PrjCoordSysType(23893, 23893); /* Indonesia 1974 UTM Zone 53S   */
    public static final PrjCoordSysType PCS_ID_1974_UTM_54S = new
            PrjCoordSysType(23894, 23894); /* Indonesia 1974 UTM Zone 54S   */
    public static final PrjCoordSysType PCS_INDIAN_1954_UTM_47N = new
            PrjCoordSysType(23947, 23947); /* Indian 1954 UTM Zone 47N      */
    public static final PrjCoordSysType PCS_INDIAN_1954_UTM_48N = new
            PrjCoordSysType(23948, 23948); /* Indian 1954 UTM Zone 48N      */
    public static final PrjCoordSysType PCS_INDIAN_1975_UTM_47N = new
            PrjCoordSysType(24047, 24047); /* Indian 1975 UTM Zone 47N      */
    public static final PrjCoordSysType PCS_INDIAN_1975_UTM_48N = new
            PrjCoordSysType(24048, 24048); /* Indian 1975 UTM Zone 48N      */
    public static final PrjCoordSysType PCS_KERTAU_UTM_47N = new
            PrjCoordSysType(24547, 24547); /* Kertau UTM Zone 47N           */
    public static final PrjCoordSysType PCS_KERTAU_UTM_48N = new
            PrjCoordSysType(24548, 24548); /* Kertau UTM Zone 48N           */
    public static final PrjCoordSysType PCS_LA_CANOA_UTM_20N = new
            PrjCoordSysType(24720, 24720);
            /* La Canoa UTM Zone=  20N         */
    public static final PrjCoordSysType PCS_LA_CANOA_UTM_21N = new
            PrjCoordSysType(24721, 24721); /* La Canoa UTM Zone 21N         */
    public static final PrjCoordSysType PCS_LOME_UTM_31N = new PrjCoordSysType(
            25231, 25231); /* Lome UTM Zone 31N             */
    public static final PrjCoordSysType PCS_MPORALOKO_UTM_32N = new
            PrjCoordSysType(26632, 26632); /* M'poraloko UTM Zone= 32N       */
    public static final PrjCoordSysType PCS_MPORALOKO_UTM_32S = new
            PrjCoordSysType(26692, 26692); /* M'poraloko UTM Zone= 32S       */
    public static final PrjCoordSysType PCS_MALONGO_1987_UTM_32S = new
            PrjCoordSysType(25932, 25932); /* Malongo 1987 UTM Zone= 32S     */
    public static final PrjCoordSysType PCS_MASSAWA_UTM_37N = new
            PrjCoordSysType(26237, 26237); /* Massawa UTM Zone 37N          */
    public static final PrjCoordSysType PCS_MHAST_UTM_32S = new PrjCoordSysType(
            26432, 26432); /* Mhast UTM Zone= 32S            */
    public static final PrjCoordSysType PCS_MINNA_UTM_31N = new PrjCoordSysType(
            26331, 26331); /* Minna UTM Zone 31N            */
    public static final PrjCoordSysType PCS_MINNA_UTM_32N = new PrjCoordSysType(
            26332, 26332); /* Minna UTM Zone 32N            */
    public static final PrjCoordSysType PCS_NAHRWAN_1967_UTM_38N = new
            PrjCoordSysType(27038, 27038); /* Nahrwan 1967 UTM Zone 38N     */
    public static final PrjCoordSysType PCS_NAHRWAN_1967_UTM_39N = new
            PrjCoordSysType(27039, 27039); /* Nahrwan 1967 UTM Zone 39N     */
    public static final PrjCoordSysType PCS_NAHRWAN_1967_UTM_40N = new
            PrjCoordSysType(27040, 27040); /* Nahrwan 1967 UTM Zone 40N     */
    public static final PrjCoordSysType PCS_NGN_UTM_38N = new PrjCoordSysType(
            31838, 31838); /* NGN UTM Zone 38N              */
    public static final PrjCoordSysType PCS_NGN_UTM_39N = new PrjCoordSysType(
            31839, 31839); /* NGN UTM Zone 39N              */
    public static final PrjCoordSysType PCS_NORD_SAHARA_UTM_29N = new
            PrjCoordSysType(30729, 30729);
            /* Nord Sahara 1959 UTM Zone=  29N */
    public static final PrjCoordSysType PCS_NORD_SAHARA_UTM_30N = new
            PrjCoordSysType(30730, 30730); /* Nord Sahara 1959 UTM Zone 30N */
    public static final PrjCoordSysType PCS_NORD_SAHARA_UTM_31N = new
            PrjCoordSysType(30731, 30731); /* Nord Sahara 1959 UTM Zone 31N */
    public static final PrjCoordSysType PCS_NORD_SAHARA_UTM_32N = new
            PrjCoordSysType(30732, 30732); /* Nord Sahara 1959 UTM Zone 32N */
    public static final PrjCoordSysType PCS_NAPARIMA_1972_UTM_20N = new
            PrjCoordSysType(27120, 27120); /* Naparima 1972 UTM Zone 20N    */
    public static final PrjCoordSysType PCS_POINTE_NOIRE_UTM_32S = new
            PrjCoordSysType(28232, 28232); /* Pointe Noire UTM Zone 32S     */

    public static final PrjCoordSysType PCS_PSAD_1956_UTM_18N = new
            PrjCoordSysType(24818, 24818);
            /* Prov. S. Amer. Datum UTM Zone 18N */
    public static final PrjCoordSysType PCS_PSAD_1956_UTM_19N = new
            PrjCoordSysType(24819, 24819);
            /* Prov. S. Amer. Datum UTM Zone 19N */
    public static final PrjCoordSysType PCS_PSAD_1956_UTM_20N = new
            PrjCoordSysType(24820, 24820);
            /* Prov. S. Amer. Datum UTM Zone 20N */
    public static final PrjCoordSysType PCS_PSAD_1956_UTM_21N = new
            PrjCoordSysType(24821, 24821);
            /* Prov. S. Amer. Datum UTM Zone 21N */
    public static final PrjCoordSysType PCS_PSAD_1956_UTM_17S = new
            PrjCoordSysType(24877, 24877);
            /* Prov. S. Amer. Datum UTM Zone 17S */
    public static final PrjCoordSysType PCS_PSAD_1956_UTM_18S = new
            PrjCoordSysType(24878, 24878);
            /* Prov. S. Amer. Datum UTM Zone 18S */
    public static final PrjCoordSysType PCS_PSAD_1956_UTM_19S = new
            PrjCoordSysType(24879, 24879);
            /* Prov. S. Amer. Datum UTM Zone 19S */
    public static final PrjCoordSysType PCS_PSAD_1956_UTM_20S = new
            PrjCoordSysType(24880, 24880);
            /* Prov. S. Amer. Datum UTM Zone 20S */

    public static final PrjCoordSysType PCS_SAPPER_HILL_UTM_20S = new
            PrjCoordSysType(29220, 29220); /* Sapper Hill 1943 UTM Zone 20S */
    public static final PrjCoordSysType PCS_SAPPER_HILL_UTM_21S = new
            PrjCoordSysType(29221, 29221); /* Sapper Hill 1943 UTM Zone 21S */
    public static final PrjCoordSysType PCS_SCHWARZECK_UTM_33S = new
            PrjCoordSysType(29333, 29333); /* Schwarzeck UTM Zone 33S       */
    public static final PrjCoordSysType PCS_SUDAN_UTM_35N = new PrjCoordSysType(
            29635, 29635); /* Sudan UTM Zone 35N            */
    public static final PrjCoordSysType PCS_SUDAN_UTM_36N = new PrjCoordSysType(
            29636, 29636); /* Sudan UTM Zone 36N            */
    public static final PrjCoordSysType PCS_TANANARIVE_UTM_38S = new
            PrjCoordSysType(29738, 29738); /* Tananarive 1925 UTM Zone 38S  */
    public static final PrjCoordSysType PCS_TANANARIVE_UTM_39S = new
            PrjCoordSysType(29739, 29739); /* Tananarive 1925 UTM Zone 39S  */
    public static final PrjCoordSysType PCS_TC_1948_UTM_39N = new
            PrjCoordSysType(30339, 30339);
            /* Trucial Coast 1948 UTM Zone 39N */
    public static final PrjCoordSysType PCS_TC_1948_UTM_40N = new
            PrjCoordSysType(30340, 30340);
            /* Trucial Coast 1948 UTM Zone 40N */
    public static final PrjCoordSysType PCS_TIMBALAI_1948_UTM_49N = new
            PrjCoordSysType(29849, 29849); /* Timbalai 1948 UTM Zone 49N    */
    public static final PrjCoordSysType PCS_TIMBALAI_1948_UTM_50N = new
            PrjCoordSysType(29850, 29850); /* Timbalai 1948 UTM Zone 50N    */
    public static final PrjCoordSysType PCS_YOFF_1972_UTM_28N = new
            PrjCoordSysType(31028, 31028);
            /* Yoff 1972 UTM Zone=  28N        */
    public static final PrjCoordSysType PCS_ZANDERIJ_1972_UTM_21N = new
            PrjCoordSysType(31121, 31121); /* Zanderij 1972 UTM Zone 21N    */
    public static final PrjCoordSysType PCS_KUDAMS_KTM = new PrjCoordSysType(
            31900, 31900); /* Kuwait Utility KTM         */

    public static final PrjCoordSysType PCS_LUZON_PHILIPPINES_I = new
            PrjCoordSysType(25391, 25391); /* Philippines Zone I         */
    public static final PrjCoordSysType PCS_LUZON_PHILIPPINES_II = new
            PrjCoordSysType(25392, 25392); /* Philippines Zone II        */
    public static final PrjCoordSysType PCS_LUZON_PHILIPPINES_III = new
            PrjCoordSysType(25393, 25393); /* Philippines Zone III       */
    public static final PrjCoordSysType PCS_LUZON_PHILIPPINES_IV = new
            PrjCoordSysType(25394, 25394); /* Philippines Zone IV        */
    public static final PrjCoordSysType PCS_LUZON_PHILIPPINES_V = new
            PrjCoordSysType(25395, 25395); /* Philippines Zone V         */

    public static final PrjCoordSysType PCS_MGI_FERRO_AUSTRIA_WEST = new
            PrjCoordSysType(31291, 31291); /* Austria (Ferro) West Zone  */
    public static final PrjCoordSysType PCS_MGI_FERRO_AUSTRIA_CENTRAL = new
            PrjCoordSysType(31292, 31292); /* Austria (Ferro) Cent. Zone */
    public static final PrjCoordSysType PCS_MGI_FERRO_AUSTRIA_EAST = new
            PrjCoordSysType(31293, 31293); /* Austria (Ferro) East Zone  */
    public static final PrjCoordSysType PCS_MONTE_MARIO_ROME_ITALY_1 = new
            PrjCoordSysType(26591, 26591); /* Monte Mario (Rome) Italy 1 */
    public static final PrjCoordSysType PCS_MONTE_MARIO_ROME_ITALY_2 = new
            PrjCoordSysType(26592, 26592); /* Monte Mario (Rome) Italy 2 */

    public static final PrjCoordSysType PCS_C_INCHAUSARGENTINA_1 = new
            PrjCoordSysType(22191, 22191); /* Argentina Zone 1           */
    public static final PrjCoordSysType PCS_C_INCHAUSARGENTINA_2 = new
            PrjCoordSysType(22192, 22192); /* Argentina Zone 2           */
    public static final PrjCoordSysType PCS_C_INCHAUSARGENTINA_3 = new
            PrjCoordSysType(22193, 22193); /* Argentina Zone 3           */
    public static final PrjCoordSysType PCS_C_INCHAUSARGENTINA_4 = new
            PrjCoordSysType(22194, 22194); /* Argentina Zone 4           */
    public static final PrjCoordSysType PCS_C_INCHAUSARGENTINA_5 = new
            PrjCoordSysType(22195, 22195); /* Argentina Zone 5           */
    public static final PrjCoordSysType PCS_C_INCHAUSARGENTINA_6 = new
            PrjCoordSysType(22196, 22196); /* Argentina Zone 6           */
    public static final PrjCoordSysType PCS_C_INCHAUSARGENTINA_7 = new
            PrjCoordSysType(22197, 22197); /* Argentina Zone 7           */

    public static final PrjCoordSysType PCS_DHDN_GERMANY_1 = new
            PrjCoordSysType(31491, 31491); /* Germany Zone 1             */
    public static final PrjCoordSysType PCS_DHDN_GERMANY_2 = new
            PrjCoordSysType(31492, 31492); /* Germany Zone 2             */
    public static final PrjCoordSysType PCS_DHDN_GERMANY_3 = new
            PrjCoordSysType(31493, 31493); /* Germany Zone 3             */
    public static final PrjCoordSysType PCS_DHDN_GERMANY_4 = new
            PrjCoordSysType(31494, 31494); /* Germany Zone 4             */
    public static final PrjCoordSysType PCS_DHDN_GERMANY_5 = new
            PrjCoordSysType(31495, 31495); /* Germany Zone 5             */

    public static final PrjCoordSysType PCS_AIN_EL_ABD_BAHRAIN_GRID = new
            PrjCoordSysType(20499, 20499); /* Bahrain State Grid         */

    public static final PrjCoordSysType PCS_BOGOTA_COLOMBIA_WEST = new
            PrjCoordSysType(21891, 21891); /* Colombia West Zone         */
    public static final PrjCoordSysType PCS_BOGOTA_COLOMBIA_BOGOTA = new
            PrjCoordSysType(21892, 21892); /* Colombia Bogota Zone       */
    public static final PrjCoordSysType PCS_BOGOTA_COLOMBIA_E_CENTRAL = new
            PrjCoordSysType(21893, 21893); /* Colombia E Central Zone    */
    public static final PrjCoordSysType PCS_BOGOTA_COLOMBIA_EAST = new
            PrjCoordSysType(21894, 21894); /* Colombia East Zone         */

    public static final PrjCoordSysType PCS_EGYPT_RED_BELT = new
            PrjCoordSysType(22992, 22992); /* Egypt Red Belt             */
    public static final PrjCoordSysType PCS_EGYPT_PURPLE_BELT = new
            PrjCoordSysType(22993, 22993); /* Egypt Purple Belt          */
    public static final PrjCoordSysType PCS_EGYPT_EXT_PURPLE_BELT = new
            PrjCoordSysType(22994, 22994); /* Egypt Extended Purple Belt */

    public static final PrjCoordSysType PCS_LEIGON_GHANA_GRID = new
            PrjCoordSysType(25000, 25000); /* Ghana Metre Grid           */

    public static final PrjCoordSysType PCS_TM65_IRISH_GRID = new
            PrjCoordSysType(29900, 29900); /* Irish National Grid        */

    public static final PrjCoordSysType PCS_NZGD_1949_NORTH_ISLAND = new
            PrjCoordSysType(27291, 27291); /* New Zealand North Island   */
    public static final PrjCoordSysType PCS_NZGD_1949_SOUTH_ISLAND = new
            PrjCoordSysType(27292, 27292); /* New Zealand South Island   */

    public static final PrjCoordSysType PCS_MINNA_NIGERIA_WEST_BELT = new
            PrjCoordSysType(26391, 26391); /* Nigeria West Belt          */
    public static final PrjCoordSysType PCS_MINNA_NIGERIA_MID_BELT = new
            PrjCoordSysType(26392, 26392); /* Nigeria Mid Belt           */
    public static final PrjCoordSysType PCS_MINNA_NIGERIA_EAST_BELT = new
            PrjCoordSysType(26393, 26393); /* Nigeria East Belt          */

    public static final PrjCoordSysType PCS_PSAD_1956_PERU_WEST = new
            PrjCoordSysType(24891, 24891); /* Peru West Zone             */
    public static final PrjCoordSysType PCS_PSAD_1956_PERU_CENTRAL = new
            PrjCoordSysType(24892, 24892); /* Peru Central Zone          */
    public static final PrjCoordSysType PCS_PSAD_1956_PERU_EAST = new
            PrjCoordSysType(24893, 24893); /* Peru East Zone             */

    public static final PrjCoordSysType PCS_LISBON_PORTUGUESE_GRID = new
            PrjCoordSysType(20700, 20700); /* Portuguese National Grid   */

    public static final PrjCoordSysType PCS_QATAR_GRID = new PrjCoordSysType(
            28600, 28600); /* Qatar National Grid        */

    public static final PrjCoordSysType PCS_OSGB_1936_BRITISH_GRID = new
            PrjCoordSysType(27700, 27700); /* British National Grid      */
    public static final PrjCoordSysType PCS_RT38_STOCKHOLM_SWEDISH_GRID = new
            PrjCoordSysType(30800, 30800); /* Swedish National Grid    */
    public static final PrjCoordSysType PCS_VOIROL_N_ALGERIE_ANCIENNE = new
            PrjCoordSysType(30491, 30491); /* Nord Algerie ancienne      */
    public static final PrjCoordSysType PCS_VOIROL_S_ALGERIE_ANCIENNE = new
            PrjCoordSysType(30492, 30492); /* Nord Algerie ancienne      */
    public static final PrjCoordSysType PCS_VOIROL_UNIFIE_N_ALGERIE = new
            PrjCoordSysType(30591, 30591); /* Nord Algerie               */
    public static final PrjCoordSysType PCS_VOIROL_UNIFIE_S_ALGERIE = new
            PrjCoordSysType(30592, 30592); /* Nord Algerie               */
    public static final PrjCoordSysType PCS_ATF_NORD_DE_GUERRE = new
            PrjCoordSysType(27500, 27500); /* Nord de Guerre             */

    public static final PrjCoordSysType PCS_NTF_FRANCE_I = new PrjCoordSysType(
            27581, 27581); /* France I                   */
    public static final PrjCoordSysType PCS_NTF_FRANCE_II = new PrjCoordSysType(
            27582, 27582); /* France II                  */
    public static final PrjCoordSysType PCS_NTF_FRANCE_III = new
            PrjCoordSysType(27583, 27583); /* France III                 */
    public static final PrjCoordSysType PCS_NTF_FRANCE_IV = new PrjCoordSysType(
            27584, 27584); /* France IV                  */
    public static final PrjCoordSysType PCS_NTF_NORD_FRANCE = new
            PrjCoordSysType(27591, 27591); /* Nord France                */
    public static final PrjCoordSysType PCS_NTF_CENTRE_FRANCE = new
            PrjCoordSysType(27592, 27592); /* Centre France              */
    public static final PrjCoordSysType PCS_NTF_SUD_FRANCE = new
            PrjCoordSysType(27593, 27593); /* Sud France                 */

    public static final PrjCoordSysType PCS_NTF_CORSE = new PrjCoordSysType(
            27594, 27594); /* Corse                      */

    public static final PrjCoordSysType PCS_KALIANPUR_INDIA_0 = new
            PrjCoordSysType(24370, 24370); /* India Zone 0               */
    public static final PrjCoordSysType PCS_KALIANPUR_INDIA_I = new
            PrjCoordSysType(24371, 24371); /* India Zone I               */
    public static final PrjCoordSysType PCS_KALIANPUR_INDIA_IIA = new
            PrjCoordSysType(24372, 24372); /* India Zone IIa             */
    public static final PrjCoordSysType PCS_KALIANPUR_INDIA_IIB = new
            PrjCoordSysType(24382, 24382); /* India Zone IIb             */
    public static final PrjCoordSysType PCS_KALIANPUR_INDIA_IIIA = new
            PrjCoordSysType(24373, 24373); /* India Zone IIIa            */
    public static final PrjCoordSysType PCS_KALIANPUR_INDIA_IIIB = new
            PrjCoordSysType(24383, 24383); /* India Zone IIIb            */
    public static final PrjCoordSysType PCS_KALIANPUR_INDIA_IVA = new
            PrjCoordSysType(24374, 24374); /* India Zone IVa             */
    public static final PrjCoordSysType PCS_KALIANPUR_INDIA_IVB = new
            PrjCoordSysType(24384, 24384); /* India Zone IVb             */

    public static final PrjCoordSysType PCS_JAMAICA_1875_OLD_GRID = new
            PrjCoordSysType(24100, 24100); /* Jamaica 1875 Old Grid      */
    public static final PrjCoordSysType PCS_JAD_1969_JAMAICA_GRID = new
            PrjCoordSysType(24200, 24200); /* Jamaica Grid               */

    public static final PrjCoordSysType PCS_MERCHICH_NORD_MAROC = new
            PrjCoordSysType(26191, 26191); /* Nord Maroc                 */
    public static final PrjCoordSysType PCS_MERCHICH_SUD_MAROC = new
            PrjCoordSysType(26192, 26192); /* Sud Maroc                  */
    public static final PrjCoordSysType PCS_MERCHICH_SAHARA = new
            PrjCoordSysType(26193, 26193); /* Sahara                     */
    public static final PrjCoordSysType PCS_CARTHAGE_NORD_TUNISIE = new
            PrjCoordSysType(22391, 22391); /* Nord Tunisie               */
    public static final PrjCoordSysType PCS_CARTHAGE_SUD_TUNISIE = new
            PrjCoordSysType(22392, 22392); /* Sud Tunisie                */
    public static final PrjCoordSysType PCS_KOC_LAMBERT = new PrjCoordSysType(
            24600, 24600); /* Kuwait Oil Co - Lambert    */
    public static final PrjCoordSysType PCS_BELGE_LAMBERT_1950 = new
            PrjCoordSysType(21500, 21500); /* Belge Lambert 1950         */
    public static final PrjCoordSysType PCS_DEALUL_PISCULUI_1933_STEREO_33 = new
            PrjCoordSysType(31600, 31600); /* Stereo 1933           */
    public static final PrjCoordSysType
            PCS_DEALUL_PISCULUI_1970_STEREO_EALUL_PISCULUI_1970_STEREO_70 = new
            PrjCoordSysType(31700, 31700); /* Stereo 1970           */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_25   = new PrjCoordSysType( 2401,2401);			 /* Beijing 1954 3 degree GK zone 25         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_26   = new PrjCoordSysType( 2402,2402);			 /* Beijing 1954 3 degree GK zone 26         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_27   = new PrjCoordSysType( 2403,2403);			 /* Beijing 1954 3 degree GK zone 27         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_28   = new PrjCoordSysType( 2404,2404);			 /* Beijing 1954 3 degree GK zone 28         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_29   = new PrjCoordSysType( 2405,2405);			 /* Beijing 1954 3 degree GK zone 29         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_30   = new PrjCoordSysType( 2406,2406);			 /* Beijing 1954 3 degree GK zone 30         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_31   = new PrjCoordSysType( 2407,2407);			 /* Beijing 1954 3 degree GK zone 31         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_32   = new PrjCoordSysType( 2408,2408);			 /* Beijing 1954 3 degree GK zone 32       */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_33   = new PrjCoordSysType( 2409,2409);			 /* Beijing 1954 3 degree GK zone 33         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_34   = new PrjCoordSysType( 2410,2410);			 /* Beijing 1954 3 degree GK zone 34         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_35   = new PrjCoordSysType( 2411,2411);			 /* Beijing 1954 3 degree GK zone 35         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_36   = new PrjCoordSysType( 2412,2412);			 /* Beijing 1954 3 degree GK zone 36         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_37   = new PrjCoordSysType( 2413,2413);			 /* Beijing 1954 3 degree GK zone 37        */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_38   = new PrjCoordSysType( 2414,2414);			 /* Beijing 1954 3 degree GK zone 38         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_39   = new PrjCoordSysType( 2415,2415);			 /* Beijing 1954 3 degree GK zone 39         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_40   = new PrjCoordSysType( 2416,2416);			 /* Beijing 1954 3 degree GK zone 40         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_41   = new PrjCoordSysType( 2417,2417);			 /* Beijing 1954 3 degree GK zone 41      */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_42   = new PrjCoordSysType( 2418,2418);			 /* Beijing 1954 3 degree GK zone 42         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_43   = new PrjCoordSysType( 2419,2419);			 /* Beijing 1954 3 degree GK zone 43         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_44   = new PrjCoordSysType( 2420,2420);			 /* Beijing 1954 3 degree GK zone 44        */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_45   = new PrjCoordSysType( 2421,2421);			 /* Beijing 1954 3 degree GK zone 45         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_25N   = new PrjCoordSysType( 2422,2422);			/* Beijing 1954 3 degree GK zone 25N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_26N   = new PrjCoordSysType( 2423,2423);			/* Beijing 1954 3 degree GK zone 26N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_27N   = new PrjCoordSysType( 2424,2424);			/* Beijing 1954 3 degree GK zone 27N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_28N   = new PrjCoordSysType( 2425,2425);			/* Beijing 1954 3 degree GK zone 28N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_29N   = new PrjCoordSysType( 2426,2426);			/* Beijing 1954 3 degree GK zone 29N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_30N   = new PrjCoordSysType( 2427,2427);			/* Beijing 1954 3 degree GK zone 30N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_31N   = new PrjCoordSysType( 2428,2428);			/* Beijing 1954 3 degree GK zone 31N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_32N   = new PrjCoordSysType( 2429,2429);			/* Beijing 1954 3 degree GK zone 32N       */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_33N   = new PrjCoordSysType( 2430,2430);			/* Beijing 1954 3 degree GK zone 33N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_34N   = new PrjCoordSysType( 2431,2431);			/* Beijing 1954 3 degree GK zone 34N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_35N   = new PrjCoordSysType( 2432,2432);			/* Beijing 1954 3 degree GK zone 35N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_36N   = new PrjCoordSysType( 2433,2433);			/* Beijing 1954 3 degree GK zone 36N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_37N   = new PrjCoordSysType( 2434,2434);			/* Beijing 1954 3 degree GK zone 37N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_38N   = new PrjCoordSysType( 2435,2435);			/* Beijing 1954 3 degree GK zone 38N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_39N   = new PrjCoordSysType( 2436,2436);			/* Beijing 1954 3 degree GK zone 39N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_40N   = new PrjCoordSysType( 2437,2437);			/* Beijing 1954 3 degree GK zone 40N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_41N   = new PrjCoordSysType( 2438,2438);			/* Beijing 1954 3 degree GK zone 41N      */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_42N   = new PrjCoordSysType( 2439,2439);			/* Beijing 1954 3 degree GK zone 42N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_43N   = new PrjCoordSysType( 2440,2440);			/* Beijing 1954 3 degree GK zone 43N         */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_44N   = new PrjCoordSysType( 2441,2441);			/* Beijing 1954 3 degree GK zone 44N        */
    public static final PrjCoordSysType PCS_BEIJING_1954_3_DEGREE_GK_45N   = new PrjCoordSysType( 2442,2442);                  /* Beijing 1954 3 degree GK zone 45N         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_13   = new PrjCoordSysType( 21513,21513);						/* China 2000 GK Zone 13         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_14   = new PrjCoordSysType( 21514,21514);						/* China 2000 GK Zone 14         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_15   = new PrjCoordSysType( 21515,21515);						/* China 2000 GK Zone 15         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_16   = new PrjCoordSysType( 21516,21516);						/* China 2000 GK Zone 16         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_17   = new PrjCoordSysType( 21517,21517);						/* China 2000 GK Zone 17         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_18   = new PrjCoordSysType( 21518,21518);						/* China 2000 GK Zone 18         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_19   = new PrjCoordSysType( 21519,21519);						/* China 2000 GK Zone 19         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_20   = new PrjCoordSysType( 21520,21520);						/* China 2000 GK Zone= new PrjCoordSysType( 20         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_21   = new PrjCoordSysType( 21521,21521);						/* China 2000 GK Zone= new PrjCoordSysType( 21         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_22   = new PrjCoordSysType( 21522,21522);						/* China 2000 GK Zone 22         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_23   = new PrjCoordSysType( 21523,21523);						/* China 2000 GK Zone 23         */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_13N  = new PrjCoordSysType( 21573,21573);						/* China 2000 GK Zone 13N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_14N  = new PrjCoordSysType( 21574,21574);						/* China 2000 GK Zone 14N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_15N  = new PrjCoordSysType( 21575,21575);						/* China 2000 GK Zone 15N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_16N  = new PrjCoordSysType( 21576,21576);						/* China 2000 GK Zone 16N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_17N  = new PrjCoordSysType( 21577,21577);						/* China 2000 GK Zone 17N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_18N  = new PrjCoordSysType( 21578,21578);						/* China 2000 GK Zone 18N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_19N  = new PrjCoordSysType( 21579,21579);						/* China 2000 GK Zone 19N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_20N  = new PrjCoordSysType( 21580,21580);						/* China 2000 GK Zone 20N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_21N  = new PrjCoordSysType( 21581,21581);						/* China 2000 GK Zone= new PrjCoordSysType( 21N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_22N  = new PrjCoordSysType( 21582,21582);						/* China 2000 GK Zone 22N        */
    public static final PrjCoordSysType PCS_CHINA_2000_GK_23N  = new PrjCoordSysType( 21583,21583);						/* China 2000 GK Zone= new PrjCoordSysType(  23N        */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_25   = new PrjCoordSysType( 21625,21625);     /* China 2000 3 degree GK zone 25         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_26   = new PrjCoordSysType( 21626,21626);     /* China 2000 3 degree GK zone 26         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_27   = new PrjCoordSysType( 21627,21627);     /* China 2000 3 degree GK zone 27         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_28   = new PrjCoordSysType( 21628,21628);     /* China 2000 3 degree GK zone 28         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_29   = new PrjCoordSysType( 21629,21629);     /* China 2000 3 degree GK zone 29         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_30   = new PrjCoordSysType( 21630,21630);     /* China 2000 3 degree GK zone 30         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_31   = new PrjCoordSysType( 21631,21631);     /* China 2000 3 degree GK zone 31         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_32   = new PrjCoordSysType( 21632,21632);     /* China 2000 3 degree GK zone 32       */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_33   = new PrjCoordSysType( 21633,21633);     /* China 2000 3 degree GK zone 33         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_34   = new PrjCoordSysType( 21634,21634);     /* China 2000 3 degree GK zone 34         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_35   = new PrjCoordSysType( 21635,21635);     /* China 2000 3 degree GK zone 35         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_36   = new PrjCoordSysType( 21636,21636);     /* China 2000 3 degree GK zone 36         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_37   = new PrjCoordSysType( 21637,21637);     /* China 2000 3 degree GK zone 37        */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_38   = new PrjCoordSysType( 21638,21638);     /* China 2000 3 degree GK zone 38         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_39   = new PrjCoordSysType( 21639,21639);     /* China 2000 3 degree GK zone 39         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_40   = new PrjCoordSysType( 21640,21640);     /* China 2000 3 degree GK zone 40         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_41   = new PrjCoordSysType( 21641,21641);     /* China 2000 3 degree GK zone 41      */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_42   = new PrjCoordSysType( 21642,21642);     /* China 2000 3 degree GK zone 42         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_43   = new PrjCoordSysType( 21643,21643);     /* China 2000 3 degree GK zone 43         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_44   = new PrjCoordSysType( 21644,21644);     /* China 2000 3 degree GK zone 44        */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_45   = new PrjCoordSysType( 21645,21645);     /* China 2000 3 degree GK zone 45         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_25N   = new PrjCoordSysType( 21675,21675);     /* China 2000 3 degree GK zone 25N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_26N   = new PrjCoordSysType( 21676,21676);     /* China 2000 3 degree GK zone 26N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_27N   = new PrjCoordSysType( 21677,21677);     /* China 2000 3 degree GK zone 27N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_28N   = new PrjCoordSysType( 21678,21678);     /* China 2000 3 degree GK zone 28N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_29N   = new PrjCoordSysType( 21679,21679);     /* China 2000 3 degree GK zone 29N        */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_30N   = new PrjCoordSysType( 21680,21680);     /* China 2000 3 degree GK zone 30N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_31N   = new PrjCoordSysType( 21681,21681);     /* China 2000 3 degree GK zone 31N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_32N   = new PrjCoordSysType( 21682,21682);     /* China 2000 3 degree GK zone 32N       */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_33N   = new PrjCoordSysType( 21683,21683);     /* China 2000 3 degree GK zone 33N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_34N   = new PrjCoordSysType( 21684,21684);     /* China 2000 3 degree GK zone 34N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_35N   = new PrjCoordSysType( 21685,21685);     /* China 2000 3 degree GK zone 35N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_36N   = new PrjCoordSysType( 21686,21686);     /* China 2000 3 degree GK zone 36N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_37N   = new PrjCoordSysType( 21687,21687);     /* China 2000 3 degree GK zone 37N        */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_38N   = new PrjCoordSysType( 21688,21688);     /* China 2000 3 degree GK zone 38N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_39N   = new PrjCoordSysType( 21689,21689);     /* China 2000 3 degree GK zone 39N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_40N   = new PrjCoordSysType( 21690,21690);     /* China 2000 3 degree GK zone 40N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_41N   = new PrjCoordSysType( 21691,21691);     /* China 2000 3 degree GK zone 41N      */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_42N   = new PrjCoordSysType( 21692,21692);     /* China 2000 3 degree GK zone 42N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_43N   = new PrjCoordSysType( 21693,21693);     /* China 2000 3 degree GK zone 43N         */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_44N   = new PrjCoordSysType( 21694,21694);     /* China 2000 3 degree GK zone 44N        */
    public static final PrjCoordSysType PCS_CHINA_2000_3_DEGREE_GK_45N   = new PrjCoordSysType( 21695,21695);     /* China 2000 3 degree GK zone 45N         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_13   = new PrjCoordSysType( 2327,2327);     /* Xian 1980 GK Zone 13         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_14   = new PrjCoordSysType( 2328,2328);     /* Xian 1980 GK Zone 14         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_15   = new PrjCoordSysType( 2329,2329);     /* Xian 1980 GK Zone 15         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_16   = new PrjCoordSysType( 2330,2330);     /* Xian 1980 GK Zone 16         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_17   = new PrjCoordSysType( 2331,2331);     /* Xian 1980 GK Zone 17         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_18   = new PrjCoordSysType( 2332,2332);     /* Xian 1980 GK Zone 18         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_19   = new PrjCoordSysType( 2333,2333);     /* Xian 1980 GK Zone 19         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_20   = new PrjCoordSysType( 2334,2334);     /* Xian 1980 GK Zone= new PrjCoordSysType( 20         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_21   = new PrjCoordSysType( 2335,2335);     /* Xian 1980 GK Zone= new PrjCoordSysType( 21         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_22   = new PrjCoordSysType( 2336,2336);     /* Xian 1980 GK Zone 22         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_23   = new PrjCoordSysType( 2337,2337);     /* Xian 1980 GK Zone 23         */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_13N  = new PrjCoordSysType( 2338,2338);     /* Xian 1980 GK Zone 13N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_14N  = new PrjCoordSysType( 2339,2339);     /* Xian 1980 GK Zone 14N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_15N  = new PrjCoordSysType( 2340,2340);     /* Xian 1980 GK Zone 15N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_16N  = new PrjCoordSysType( 2341,2341);     /* Xian 1980 GK Zone 16N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_17N  = new PrjCoordSysType( 2342,2342);     /* Xian 1980 GK Zone 17N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_18N  = new PrjCoordSysType( 2343,2343);     /* Xian 1980 GK Zone 18N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_19N  = new PrjCoordSysType( 2344,2344);     /* Xian 1980 GK Zone 19N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_20N  = new PrjCoordSysType( 2345,2345);     /* Xian 1980 GK Zone 20N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_21N  = new PrjCoordSysType( 2346,2346);     /* Xian 1980 GK Zone= new PrjCoordSysType( 21N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_22N  = new PrjCoordSysType( 2347,2347);     /* Xian 1980 GK Zone 22N        */
    public static final PrjCoordSysType PCS_XIAN_1980_GK_23N  = new PrjCoordSysType( 2348,2348);     /* Xian 1980 GK Zone= new PrjCoordSysType(  23N        */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_25   = new PrjCoordSysType( 2349,2349);     /* Xian 1980 3 degree GK zone 25         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_26   = new PrjCoordSysType( 2350,2350);     /* Xian 1980 3 degree GK zone 26         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_27   = new PrjCoordSysType( 2351,2351);     /* Xian 1980 3 degree GK zone 27         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_28   = new PrjCoordSysType( 2352,2352);     /* Xian 1980 3 degree GK zone 28         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_29   = new PrjCoordSysType( 2353,2353);     /* Xian 1980 3 degree GK zone 29         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_30   = new PrjCoordSysType( 2354,2354);     /* Xian 1980 3 degree GK zone 30         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_31   = new PrjCoordSysType( 2355,2355);     /* Xian 1980 3 degree GK zone 31         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_32   = new PrjCoordSysType( 2356,2356);     /* Xian 1980 3 degree GK zone 32       */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_33   = new PrjCoordSysType( 2357,2357);     /* Xian 1980 3 degree GK zone 33         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_34   = new PrjCoordSysType( 2358,2358);     /* Xian 1980 3 degree GK zone 34         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_35   = new PrjCoordSysType( 2359,2359);     /* Xian 1980 3 degree GK zone 35         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_36   = new PrjCoordSysType( 2360,2360);     /* Xian 1980 3 degree GK zone 36         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_37   = new PrjCoordSysType( 2361,2361);     /* Xian 1980 3 degree GK zone 37        */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_38   = new PrjCoordSysType( 2362,2362);     /* Xian 1980 3 degree GK zone 38         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_39   = new PrjCoordSysType( 2363,2363);     /* Xian 1980 3 degree GK zone 39         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_40   = new PrjCoordSysType( 2364,2364);     /* Xian 1980 3 degree GK zone 40         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_41   = new PrjCoordSysType( 2365,2365);     /* Xian 1980 3 degree GK zone 41      */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_42   = new PrjCoordSysType( 2366,2366);     /* Xian 1980 3 degree GK zone 42         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_43   = new PrjCoordSysType( 2367,2367);     /* Xian 1980 3 degree GK zone 43         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_44   = new PrjCoordSysType( 2368,2368);     /* Xian 1980 3 degree GK zone 44        */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_45   = new PrjCoordSysType( 2369,2369);     /* Xian 1980 3 degree GK zone 45         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_25N   = new PrjCoordSysType( 2370,2370);     /* Xian 1980 3 degree GK zone 25N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_26N   = new PrjCoordSysType( 2371,2371);     /* Xian 1980 3 degree GK zone 26N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_27N   = new PrjCoordSysType( 2372,2372);     /* Xian 1980 3 degree GK zone 27N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_28N   = new PrjCoordSysType( 2373,2373);     /* Xian 1980 3 degree GK zone 28N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_29N   = new PrjCoordSysType( 2374,2374);     /* Xian 1980 3 degree GK zone 29N        */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_30N   = new PrjCoordSysType( 2375,2375);     /* Xian 1980 3 degree GK zone 30N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_31N   = new PrjCoordSysType( 2376,2376);     /* Xian 1980 3 degree GK zone 31N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_32N   = new PrjCoordSysType( 2377,2377);     /* Xian 1980 3 degree GK zone 32N       */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_33N   = new PrjCoordSysType( 2378,2378);     /* Xian 1980 3 degree GK zone 33N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_34N   = new PrjCoordSysType( 2379,2379);     /* Xian 1980 3 degree GK zone 34N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_35N   = new PrjCoordSysType( 2380,2380);     /* Xian 1980 3 degree GK zone 35N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_36N   = new PrjCoordSysType( 2381,2381);     /* Xian 1980 3 degree GK zone 36N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_37N   = new PrjCoordSysType( 2382,2382);     /* Xian 1980 3 degree GK zone 37N        */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_38N   = new PrjCoordSysType( 2383,2383);     /* Xian 1980 3 degree GK zone 38N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_39N   = new PrjCoordSysType( 2384,2384);     /* Xian 1980 3 degree GK zone 39N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_40N   = new PrjCoordSysType( 2385,2385);     /* Xian 1980 3 degree GK zone 40N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_41N   = new PrjCoordSysType( 2386,2386);     /* Xian 1980 3 degree GK zone 41N      */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_42N   = new PrjCoordSysType( 2387,2387);     /* Xian 1980 3 degree GK zone 42N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_43N   = new PrjCoordSysType( 2388,2388);     /* Xian 1980 3 degree GK zone 43N         */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_44N   = new PrjCoordSysType( 2389,2389);     /* Xian 1980 3 degree GK zone 44N        */
    public static final PrjCoordSysType PCS_XIAN_1980_3_DEGREE_GK_45N   = new PrjCoordSysType( 2390,2390);     /* Xian 1980 3 degree GK zone 45N         */
    public static final PrjCoordSysType PCS_KERTAU_MALAYA_METERS =  new PrjCoordSysType( 23110,23110);
    public static final PrjCoordSysType  PCS_TIMBALAI_1948_RSO_BORNEO =  new PrjCoordSysType(23130,23130);
}
