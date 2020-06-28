package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class GeoDatumType extends Enum {
    private GeoDatumType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final GeoDatumType DATUM_USER_DEFINED = new GeoDatumType( -1,
            -1);
    public static final GeoDatumType DATUM_AIRY_1830 = new GeoDatumType(6001,
            6001); /* Airy 1830                            */
    public static final GeoDatumType DATUM_AIRY_MOD = new GeoDatumType(6002,
            6002); /* Airy modified                        */
    public static final GeoDatumType DATUM_AUSTRALIAN = new GeoDatumType(6003,
            6003); /* Australian National                  */
    public static final GeoDatumType DATUM_BESSEL_1841 = new GeoDatumType(6004,
            6004); /* Bessel 1841                          */
    public static final GeoDatumType DATUM_BESSEL_MOD = new GeoDatumType(6005,
            6005); /* Bessel modified                      */
    public static final GeoDatumType DATUM_BESSEL_NAMIBIA = new GeoDatumType(
            6006, 6006); /* Bessel Namibia                       */
    public static final GeoDatumType DATUM_CLARKE_1858 = new GeoDatumType(6007,
            6007); /* Clarke 1858                          */
    public static final GeoDatumType DATUM_CLARKE_1866 = new GeoDatumType(6008,
            6008); /* Clarke 1866                          */
    public static final GeoDatumType DATUM_CLARKE_1866_MICH = new GeoDatumType(
            6009, 6009); /* Clarke 1866 Michigan                 */
    public static final GeoDatumType DATUM_CLARKE_1880 = new GeoDatumType(6034,
            6034); /* Clarke 1880                          */
    public static final GeoDatumType DATUM_CLARKE_1880_ARC = new GeoDatumType(
            6013, 6013); /* Clarke 1880 (Arc)                    */
    public static final GeoDatumType DATUM_CLARKE_1880_BENOIT = new
            GeoDatumType(6010, 6010);
            /* Clarke 1880 (Benoit)                 */
    public static final GeoDatumType DATUM_CLARKE_1880_IGN = new GeoDatumType(
            6011, 6011); /* Clarke 1880 (IGN)                    */
    public static final GeoDatumType DATUM_CLARKE_1880_RGS = new GeoDatumType(
            6012, 6012); /* Clarke 1880 (RGS)                    */
    public static final GeoDatumType DATUM_CLARKE_1880_SGA = new GeoDatumType(
            6014, 6014); /* Clarke 1880 (SGA)                    */
    public static final GeoDatumType DATUM_EVEREST_1830 = new GeoDatumType(6015,
            6015); /* Everest 1830                         */
    public static final GeoDatumType DATUM_EVEREST_DEF_1967 = new GeoDatumType(
            6016, 6016); /* Everest (definition 1967)            */
    public static final GeoDatumType DATUM_EVEREST_DEF_1975 = new GeoDatumType(
            6017, 6017); /* Everest (definition 1975)            */
    public static final GeoDatumType DATUM_EVEREST_MOD = new GeoDatumType(6018,
            6018); /* Everest modified                     */
    public static final GeoDatumType DATUM_GEM_10C = new GeoDatumType(6031,
            6031); /* GEM gravity potential model          */
    public static final GeoDatumType DATUM_GRS_1967 = new GeoDatumType(6036,
            6036); /* GRS 1967                             */
    public static final GeoDatumType DATUM_GRS_1980 = new GeoDatumType(6019,
            6019); /* GRS 1980                             */
    public static final GeoDatumType DATUM_HELMERT_1906 = new GeoDatumType(6020,
            6020); /* Helmert 1906                         */
    public static final GeoDatumType DATUM_INDONESIAN = new GeoDatumType(6021,
            6021); /* Indonesian National                  */
    public static final GeoDatumType DATUM_INTERNATIONAL_1924 = new
            GeoDatumType(6022, 6022);
            /* International 1927                   */
    public static final GeoDatumType DATUM_INTERNATIONAL_1967 = new
            GeoDatumType(6023, 6023);
            /* International 1967                   */
    public static final GeoDatumType DATUM_KRASOVSKY_1940 = new GeoDatumType(
            6024, 6024); /* Krasovsky 1940                       */
    public static final GeoDatumType DATUM_NWL_9D = new GeoDatumType(6025, 6025);
            /* Transit precise ephemeris            */
    public static final GeoDatumType DATUM_OSU_86F = new GeoDatumType(6032,
            6032); /* OSU 1986 geoidal model               */
    public static final GeoDatumType DATUM_OSU_91A = new GeoDatumType(6033,
            6033); /* OSU 1991 geoidal model               */
    public static final GeoDatumType DATUM_PLESSIS_1817 = new GeoDatumType(6027,
            6027); /* Plessis 1817                         */
    public static final GeoDatumType DATUM_SPHERE = new GeoDatumType(6035, 6035);
            /* Authalic sphere                      */
    public static final GeoDatumType DATUM_STRUVE_1860 = new GeoDatumType(6028,
            6028); /* Struve 1860                          */
    public static final GeoDatumType DATUM_WAR_OFFICE = new GeoDatumType(6029,
            6029); /* War Office                           */
    public static final GeoDatumType DATUM_WGS_1966 = new GeoDatumType((6001 +
            33000), (6001 + 33000)); /* WGS 1966                     */
    public static final GeoDatumType DATUM_FISCHER_1960 = new GeoDatumType((
            6002 + 33000), (6002 + 33000)); /* Fischer 1960                 */
    public static final GeoDatumType DATUM_FISCHER_1968 = new GeoDatumType((
            6003 + 33000), (6003 + 33000)); /* Fischer 1968                 */
    public static final GeoDatumType DATUM_FISCHER_MOD = new GeoDatumType((6004 +
            33000), (6004 + 33000)); /* Fischer modified             */
    public static final GeoDatumType DATUM_HOUGH_1960 = new GeoDatumType((6005 +
            33000), (6005 + 33000)); /* Hough 1960                   */
    public static final GeoDatumType DATUM_EVEREST_MOD_1969 = new GeoDatumType((
            6006 + 33000), (6006 + 33000)); /* Everest modified 1969        */
    public static final GeoDatumType DATUM_WALBECK = new GeoDatumType((6007 +
            33000), (6007 + 33000)); /* Walbeck                      */
    public static final GeoDatumType DATUM_SPHERE_AI = new GeoDatumType((6008 +
            33000), (6008 + 33000)); /* Authalic sphere (ARC/INFO)   */
//}}
    /*----------------------------------------------------------------------------*/
    /*                     H O R I Z O N T A L   D A T U M S                      */
    /*----------------------------------------------------------------------------*/
//{{
    public static final GeoDatumType DATUM_ADINDAN = new GeoDatumType(6201,
            6201); /* Adindan                              */
    public static final GeoDatumType DATUM_AFGOOYE = new GeoDatumType(6205,
            6205); /* Afgooye                              */
    public static final GeoDatumType DATUM_AGADEZ = new GeoDatumType(6206, 6206);
            /* Agadez                               */
    public static final GeoDatumType DATUM_AGD_1966 = new GeoDatumType(6202,
            6202); /* Australian Geodetic Datum 1966       */
    public static final GeoDatumType DATUM_AGD_1984 = new GeoDatumType(6203,
            6203); /* Australian Geodetic Datum 1984       */
    public static final GeoDatumType DATUM_AIN_EL_ABD_1970 = new GeoDatumType(
            6204, 6204); /* Ain el Abd 1970                      */
    public static final GeoDatumType DATUM_AMERSFOORT = new GeoDatumType(6289,
            6289); /* Amersfoort                           */
    public static final GeoDatumType DATUM_ARATU = new GeoDatumType(6208, 6208);
            /* Aratu                                */
    public static final GeoDatumType DATUM_ARC_1950 = new GeoDatumType(6209,
            6209); /* Arc 1950                             */
    public static final GeoDatumType DATUM_ARC_1960 = new GeoDatumType(6210,
            6210); /* Arc 1960                             */
    public static final GeoDatumType DATUM_ATF = new GeoDatumType(6901, 6901);
            /* Ancienne Triangulation Francaise     */
    public static final GeoDatumType DATUM_ATS_1977 = new GeoDatumType(6122,
            6122); /* Average Terrestrial System 1977      */
    public static final GeoDatumType DATUM_BARBADOS = new GeoDatumType(6212,
            6212); /* Barbados                             */
    public static final GeoDatumType DATUM_BATAVIA = new GeoDatumType(6211,
            6211); /* Batavia                              */
    public static final GeoDatumType DATUM_BEDUARAM = new GeoDatumType(6213,
            6213); /* Beduaram                             */
    public static final GeoDatumType DATUM_BEIJING_1954 = new GeoDatumType(6214,
            6214); /* Beijing 1954                         */
    public static final GeoDatumType DATUM_BELGE_1950 = new GeoDatumType(6215,
            6215); /* Reseau National Belge 1950           */
    public static final GeoDatumType DATUM_BELGE_1972 = new GeoDatumType(6313,
            6313); /* Reseau National Belge 1972           */
    public static final GeoDatumType DATUM_BERMUDA_1957 = new GeoDatumType(6216,
            6216); /* Bermuda 1957                         */
    public static final GeoDatumType DATUM_BERN_1898 = new GeoDatumType(6217,
            6217); /* Bern 1898                            */
    public static final GeoDatumType DATUM_BERN_1938 = new GeoDatumType(6306,
            6306); /* Bern 1938                            */
    public static final GeoDatumType DATUM_BOGOTA = new GeoDatumType(6218, 6218);
            /* Bogota                               */
    public static final GeoDatumType DATUM_BUKIT_RIMPAH = new GeoDatumType(6219,
            6219); /* Bukit Rimpah                         */
    public static final GeoDatumType DATUM_CAMACUPA = new GeoDatumType(6220,
            6220); /* Camacupa                             */
    public static final GeoDatumType DATUM_CAMPO_INCHAUSPE = new GeoDatumType(
            6221, 6221); /* Campo Inchauspe                      */
    public static final GeoDatumType DATUM_CAPE = new GeoDatumType(6222, 6222);
            /* Cape                                 */
    public static final GeoDatumType DATUM_CARTHAGE = new GeoDatumType(6223,
            6223); /* Carthage                             */
    public static final GeoDatumType DATUM_CHUA = new GeoDatumType(6224, 6224);
            /* Chua                                 */
    public static final GeoDatumType DATUM_CONAKRY_1905 = new GeoDatumType(6315,
            6315); /* Conakry 1905                         */
    public static final GeoDatumType DATUM_CORREGO_ALEGRE = new GeoDatumType(
            6225, 6225); /* Corrego Alegre                       */
    public static final GeoDatumType DATUM_COTE_D_IVOIRE = new GeoDatumType(
            6226, 6226); /* Cote d'Ivoire                        */
    public static final GeoDatumType DATUM_DATUM_73 = new GeoDatumType(6274,
            6274); /* Datum 73                             */
    public static final GeoDatumType DATUM_DEIR_EZ_ZOR = new GeoDatumType(6227,
            6227); /* Deir ez Zor                          */
    public static final GeoDatumType DATUM_DEALUL_PISCULUI_1933 = new
            GeoDatumType(6316, 6316); /* Dealul Piscului 1933				   */
    public static final GeoDatumType DATUM_DEALUL_PISCULUI_1970 = new
            GeoDatumType(6317, 6317); /* Dealul Piscului 1970				   */
    public static final GeoDatumType DATUM_DHDN = new GeoDatumType(6314, 6314);
            /* Deutsche Hauptdreiecksnetz           */
    public static final GeoDatumType DATUM_DOUALA = new GeoDatumType(6228, 6228);
            /* Douala                               */
    public static final GeoDatumType DATUM_ED_1950 = new GeoDatumType(6230,
            6230); /* European Datum 1950                  */
    public static final GeoDatumType DATUM_ED_1987 = new GeoDatumType(6231,
            6231); /* European Datum 1987                  */
    public static final GeoDatumType DATUM_EGYPT_1907 = new GeoDatumType(6229,
            6229); /* Egypt 1907                           */
    public static final GeoDatumType DATUM_ETRS_1989 = new GeoDatumType(6258,
            6258); /* European Terrestrial Ref. Sys. 1989  */
    public static final GeoDatumType DATUM_FAHUD = new GeoDatumType(6232, 6232);
            /* Fahud                                */
    public static final GeoDatumType DATUM_GANDAJIKA_1970 = new GeoDatumType(
            6233, 6233); /* Gandajika 1970                       */
    public static final GeoDatumType DATUM_GAROUA = new GeoDatumType(6234, 6234);
            /* Garoua                               */
    public static final GeoDatumType DATUM_GDA_1994 = new GeoDatumType(6283,
            6283); /* Geocentric Datum of Australia 1994   */
    public static final GeoDatumType DATUM_GGRS_1987 = new GeoDatumType(6121,
            6121); /* Greek Geodetic Reference System 1987 */
    public static final GeoDatumType DATUM_GREEK = new GeoDatumType(6120, 6120);
            /* Greek                                */
    public static final GeoDatumType DATUM_GUYANE_FRANCAISE = new GeoDatumType(
            6235, 6235); /* Guyane Francaise                     */
    public static final GeoDatumType DATUM_HERAT_NORTH = new GeoDatumType(6255,
            6255); /* Herat North                          */
    public static final GeoDatumType DATUM_HITO_XVIII_1963 = new GeoDatumType(
            6254, 6254); /* Hito XVIII 1963                      */
    public static final GeoDatumType DATUM_HU_TZU_SHAN = new GeoDatumType(6236,
            6236); /* Hu Tzu Shan                          */
    public static final GeoDatumType DATUM_HUNGARIAN_1972 = new GeoDatumType(
            6237, 6237); /* Hungarian Datum 1972                 */
    public static final GeoDatumType DATUM_INDIAN_1954 = new GeoDatumType(6239,
            6239); /* Indian 1954                          */
    public static final GeoDatumType DATUM_INDIAN_1975 = new GeoDatumType(6240,
            6240); /* Indian 1975                          */
    public static final GeoDatumType DATUM_INDONESIAN_1974 = new GeoDatumType(
            6238, 6238); /* Indonesian Datum 1974                */
    public static final GeoDatumType DATUM_JAMAICA_1875 = new GeoDatumType(6241,
            6241); /* Jamaica 1875                         */
    public static final GeoDatumType DATUM_JAMAICA_1969 = new GeoDatumType(6242,
            6242); /* Jamaica 1969                         */
    public static final GeoDatumType DATUM_KALIANPUR = new GeoDatumType(6243,
            6243); /* Kalianpur                            */
    public static final GeoDatumType DATUM_KANDAWALA = new GeoDatumType(6244,
            6244); /* Kandawala                            */
    public static final GeoDatumType DATUM_KERTAU = new GeoDatumType(6245, 6245);
            /* Kertau                               */
    public static final GeoDatumType DATUM_KKJ = new GeoDatumType(6123, 6123);
            /* Kartastokoordinaattijarjestelma      */
    public static final GeoDatumType DATUM_KOC = new GeoDatumType(6246, 6246);
            /* Kuwait Oil Company                   */
    public static final GeoDatumType DATUM_KUDAMS = new GeoDatumType(6319, 6319);
            /* Kuwait Utility                       */
    public static final GeoDatumType DATUM_LA_CANOA = new GeoDatumType(6247,
            6247); /* La Canoa                             */
    public static final GeoDatumType DATUM_LAKE = new GeoDatumType(6249, 6249);
            /* Lake                                 */
    public static final GeoDatumType DATUM_LEIGON = new GeoDatumType(6250, 6250);
            /* Leigon                               */
    public static final GeoDatumType DATUM_LIBERIA_1964 = new GeoDatumType(6251,
            6251); /* Liberia 1964                         */
    public static final GeoDatumType DATUM_LISBON = new GeoDatumType(6207, 6207);
            /* Lisbon                               */
    public static final GeoDatumType DATUM_LOMA_QUINTANA = new GeoDatumType(
            6288, 6288); /* Loma Quintana                        */
    public static final GeoDatumType DATUM_LOME = new GeoDatumType(6252, 6252);
            /* Lome                                 */
    public static final GeoDatumType DATUM_LUZON_1911 = new GeoDatumType(6253,
            6253); /* Luzon 1911                           */
    public static final GeoDatumType DATUM_MAHE_1971 = new GeoDatumType(6256,
            6256); /* Mahe 1971                            */
    public static final GeoDatumType DATUM_MAKASSAR = new GeoDatumType(6257,
            6257); /* Makassar                             */
    public static final GeoDatumType DATUM_MALONGO_1987 = new GeoDatumType(6259,
            6259); /* Malongo 1987                         */
    public static final GeoDatumType DATUM_MANOCA = new GeoDatumType(6260, 6260);
            /* Manoca                               */
    public static final GeoDatumType DATUM_MASSAWA = new GeoDatumType(6262,
            6262); /* Massawa                              */
    public static final GeoDatumType DATUM_MERCHICH = new GeoDatumType(6261,
            6261); /* Merchich                             */
    public static final GeoDatumType DATUM_MGI = new GeoDatumType(6312, 6312);
            /* Militar-Geographische Institut       */
    public static final GeoDatumType DATUM_MHAST = new GeoDatumType(6264, 6264);
            /* Mhast                                */
    public static final GeoDatumType DATUM_MINNA = new GeoDatumType(6263, 6263);
            /* Minna                                */
    public static final GeoDatumType DATUM_MONTE_MARIO = new GeoDatumType(6265,
            6265); /* Monte Mario                          */
    public static final GeoDatumType DATUM_MPORALOKO = new GeoDatumType(6266,
            6266); /* M'poraloko                           */
    public static final GeoDatumType DATUM_NAD_MICH = new GeoDatumType(6268,
            6268); /* NAD Michigan                         */
    public static final GeoDatumType DATUM_NAD_1927 = new GeoDatumType(6267,
            6267); /* North American Datum 1927            */
    public static final GeoDatumType DATUM_NAD_1983 = new GeoDatumType(6269,
            6269); /* North American Datum 1983            */
    public static final GeoDatumType DATUM_NAHRWAN_1967 = new GeoDatumType(6270,
            6270); /* Nahrwan 1967                         */
    public static final GeoDatumType DATUM_NAPARIMA_1972 = new GeoDatumType(
            6271, 6271); /* Naparima 1972                        */
    public static final GeoDatumType DATUM_NDG = new GeoDatumType(6902, 6902);
            /* Nord de Guerre                       */
    public static final GeoDatumType DATUM_NGN = new GeoDatumType(6318, 6318);
            /* National Geodetic Network (Kuwait)   */
    public static final GeoDatumType DATUM_NGO_1948 = new GeoDatumType(6273,
            6273); /* NGO 1948                             */
    public static final GeoDatumType DATUM_NORD_SAHARA_1959 = new GeoDatumType(
            6307, 6307); /* Nord Sahara 1959                     */
    public static final GeoDatumType DATUM_NSWC_9Z_2 = new GeoDatumType(6276,
            6276); /* NSWC 9Z-2                            */
    public static final GeoDatumType DATUM_NTF = new GeoDatumType(6275, 6275);
            /* Nouvelle Triangulation Francaise     */
    public static final GeoDatumType DATUM_NZGD_1949 = new GeoDatumType(6272,
            6272); /* New Zealand Geodetic Datum 1949      */
    public static final GeoDatumType DATUM_OS_SN_1980 = new GeoDatumType(6279,
            6279); /* OS (SN) 1980                         */
    public static final GeoDatumType DATUM_OSGB_1936 = new GeoDatumType(6277,
            6277); /* OSGB 1936                            */
    public static final GeoDatumType DATUM_OSGB_1970_SN = new GeoDatumType(6278,
            6278); /* OSGB 1970 (SN)                       */
    public static final GeoDatumType DATUM_PADANG_1884 = new GeoDatumType(6280,
            6280); /* Padang 1884                          */
    public static final GeoDatumType DATUM_PALESTINE_1923 = new GeoDatumType(
            6281, 6281); /* Palestine 1923                       */
    public static final GeoDatumType DATUM_POINTE_NOIRE = new GeoDatumType(6282,
            6282); /* Pointe Noire                         */
    public static final GeoDatumType DATUM_PSAD_1956 = new GeoDatumType(6248,
            6248); /* Provisional South Amer. Datum 1956   */
    public static final GeoDatumType DATUM_PULKOVO_1942 = new GeoDatumType(6284,
            6284); /* Pulkovo 1942                         */
    public static final GeoDatumType DATUM_PULKOVO_1995 = new GeoDatumType(6200,
            6200); /* Pulkovo 1995                         */
    public static final GeoDatumType DATUM_QATAR = new GeoDatumType(6285, 6285);
            /* Qatar                                */
    public static final GeoDatumType DATUM_QATAR_1948 = new GeoDatumType(6286,
            6286); /* Qatar 1948                           */
    public static final GeoDatumType DATUM_QORNOQ = new GeoDatumType(6287, 6287);
            /* Qornoq                               */
    public static final GeoDatumType DATUM_SAD_1969 = new GeoDatumType(6291,
            6291); /* South American Datum 1969            */
    public static final GeoDatumType DATUM_SAPPER_HILL_1943 = new GeoDatumType(
            6292, 6292); /* Sapper Hill 1943                     */
    public static final GeoDatumType DATUM_SCHWARZECK = new GeoDatumType(6293,
            6293); /* Schwarzeck                           */
    public static final GeoDatumType DATUM_SEGORA = new GeoDatumType(6294, 6294);
            /* Segora                               */
    public static final GeoDatumType DATUM_SERINDUNG = new GeoDatumType(6295,
            6295); /* Serindung                            */
    public static final GeoDatumType DATUM_STOCKHOLM_1938 = new GeoDatumType(
            6308, 6308); /* Stockholm 1938                       */
    public static final GeoDatumType DATUM_SUDAN = new GeoDatumType(6296, 6296);
            /* Sudan                                */
    public static final GeoDatumType DATUM_TANANARIVE_1925 = new GeoDatumType(
            6297, 6297); /* Tananarive 1925                      */
    public static final GeoDatumType DATUM_TIMBALAI_1948 = new GeoDatumType(
            6298, 6298); /* Timbalai 1948                        */
    public static final GeoDatumType DATUM_TM65 = new GeoDatumType(6299, 6299);
            /* TM65                                 */
    public static final GeoDatumType DATUM_TM75 = new GeoDatumType(6300, 6300);
            /* TM75                                 */
    public static final GeoDatumType DATUM_TOKYO = new GeoDatumType(6301, 6301);
            /* Tokyo                                */
    public static final GeoDatumType DATUM_TRINIDAD_1903 = new GeoDatumType(
            6302, 6302); /* Trinidad 1903                        */
    public static final GeoDatumType DATUM_TRUCIAL_COAST_1948 = new
            GeoDatumType(6303, 6303);
            /* Trucial Coast 1948                   */
    public static final GeoDatumType DATUM_VOIROL_1875 = new GeoDatumType(6304,
            6304); /* Voirol 1875                          */
    public static final GeoDatumType DATUM_VOIROL_UNIFIE_1960 = new
            GeoDatumType(6305, 6305);
            /* Voirol Unifie 1960                   */
    public static final GeoDatumType DATUM_WGS_1972 = new GeoDatumType(6322,
            6322); /* WGS 1972                             */
    public static final GeoDatumType DATUM_WGS_1972_BE = new GeoDatumType(6324,
            6324); /* WGS 1972 Transit Broadcast Ephemeris */
    public static final GeoDatumType DATUM_WGS_1984 = new GeoDatumType(6326,
            6326); /* WGS 1984                             */
    public static final GeoDatumType DATUM_YACARE = new GeoDatumType(6309, 6309);
            /* Yacare                               */
    public static final GeoDatumType DATUM_YOFF = new GeoDatumType(6310, 6310);
            /* Yoff                                 */
    public static final GeoDatumType DATUM_ZANDERIJ = new GeoDatumType(6311,
            6311); /* Zanderij                             */
    public static final GeoDatumType DATUM_EUROPEAN_1979 = new GeoDatumType((
            6201 + 33000), (6201 + 33000)); /* European 1979                */
    public static final GeoDatumType DATUM_EVEREST_BANGLADESH = new
            GeoDatumType((6202 + 33000), (6202 + 33000));
            /* Everest - Bangladesh         */
    public static final GeoDatumType DATUM_EVEREST_INDIA_NEPAL = new
            GeoDatumType((6203 + 33000), (6203 + 33000));
            /* Everest - India and Nepal    */
    public static final GeoDatumType DATUM_HJORSEY_1955 = new GeoDatumType((
            6204 + 33000), (6204 + 33000)); /* Hjorsey 1955                 */
    public static final GeoDatumType DATUM_HONG_KONG_1963 = new GeoDatumType((
            6205 + 33000), (6205 + 33000)); /* Hong Kong 1963               */
    public static final GeoDatumType DATUM_OMAN = new GeoDatumType((6206 +
            33000), (6206 + 33000)); /* Oman                         */
    public static final GeoDatumType DATUM_S_ASIA_SINGAPORE = new GeoDatumType((
            6207 + 33000), (6207 + 33000)); /* South Asia Singapore         */
    public static final GeoDatumType DATUM_AYABELLE = new GeoDatumType((6208 +
            33000), (6208 + 33000)); /* Ayabelle Lighthouse          */
    public static final GeoDatumType DATUM_BISSAU = new GeoDatumType((6209 +
            33000), (6209 + 33000)); /* Bissau                       */
    public static final GeoDatumType DATUM_DABOLA = new GeoDatumType((6210 +
            33000), (6210 + 33000)); /* Dabola                       */
    public static final GeoDatumType DATUM_POINT58 = new GeoDatumType((6211 +
            33000), (6211 + 33000)); /* Point 58                     */
    public static final GeoDatumType DATUM_BEACON_E_1945 = new GeoDatumType((
            6212 + 33000), (6212 + 33000)); /* Astro Beacon E 1945          */
    public static final GeoDatumType DATUM_TERN_ISLAND_1961 = new GeoDatumType((
            6213 + 33000), (6213 + 33000)); /* Tern Island Astro 1961       */
    public static final GeoDatumType DATUM_ASTRO_1952 = new GeoDatumType((6214 +
            33000), (6214 + 33000)); /* Astronomical Station 1952    */
    public static final GeoDatumType DATUM_BELLEVUE = new GeoDatumType((6215 +
            33000), (6215 + 33000)); /* Bellevue IGN                 */
    public static final GeoDatumType DATUM_CANTON_1966 = new GeoDatumType((6216 +
            33000), (6216 + 33000)); /* Canton Astro 1966            */
    public static final GeoDatumType DATUM_CHATHAM_ISLAND_1971 = new
            GeoDatumType((6217 + 33000), (6217 + 33000));
            /* Chatham Island Astro 1971    */
    public static final GeoDatumType DATUM_DOS_1968 = new GeoDatumType((6218 +
            33000), (6218 + 33000)); /* DOS 1968                     */
    public static final GeoDatumType DATUM_EASTER_ISLAND_1967 = new
            GeoDatumType((6219 + 33000), (6219 + 33000));
            /* Easter Island 1967           */
    public static final GeoDatumType DATUM_GUAM_1963 = new GeoDatumType((6220 +
            33000), (6220 + 33000)); /* Guam 1963                    */
    public static final GeoDatumType DATUM_GUX_1 = new GeoDatumType((6221 +
            33000), (6221 + 33000)); /* GUX 1 Astro                  */
    public static final GeoDatumType DATUM_JOHNSTON_ISLAND_1961 = new
            GeoDatumType((6222 + 33000), (6222 + 33000));
            /* Johnston Island 1961         */
    public static final GeoDatumType DATUM_KUSAIE_1951 = new GeoDatumType((6259 +
            33000), (6259 + 33000)); /* Kusaie Astro 1951            */
    public static final GeoDatumType DATUM_MIDWAY_1961 = new GeoDatumType((6224 +
            33000), (6224 + 33000)); /* Midway Astro 1961            */
    public static final GeoDatumType DATUM_OLD_HAWAIIAN = new GeoDatumType((
            6225 + 33000), (6225 + 33000)); /* Old Hawaiian                 */
    public static final GeoDatumType DATUM_PITCAIRN_1967 = new GeoDatumType((
            6226 + 33000), (6226 + 33000)); /* Pitcairn Astro 1967          */
    public static final GeoDatumType DATUM_SANTO_DOS_1965 = new GeoDatumType((
            6227 + 33000), (6227 + 33000)); /* Santo DOS 1965               */
    public static final GeoDatumType DATUM_VITI_LEVU_1916 = new GeoDatumType((
            6228 + 33000), (6228 + 33000)); /* Viti Levu 1916               */
    public static final GeoDatumType DATUM_WAKE_ENIWETOK_1960 = new
            GeoDatumType((6229 + 33000), (6229 + 33000));
            /* Wake-Eniwetok 1960           */
    public static final GeoDatumType DATUM_WAKE_ISLAND_1952 = new GeoDatumType((
            6230 + 33000), (6230 + 33000)); /* Wake Island Astro 1952       */
    public static final GeoDatumType DATUM_ANNA_1_1965 = new GeoDatumType((6231 +
            33000), (6231 + 33000)); /* Anna 1 Astro 1965            */
    public static final GeoDatumType DATUM_GAN_1970 = new GeoDatumType((6232 +
            33000), (6232 + 33000)); /* Gan 1970                     */
    public static final GeoDatumType DATUM_ISTS_073_1969 = new GeoDatumType((
            6233 + 33000), (6233 + 33000)); /* ISTS 073 Astro 1969          */
    public static final GeoDatumType DATUM_KERGUELEN_ISLAND_1949 = new
            GeoDatumType((6234 + 33000), (6234 + 33000));
            /* Kerguelen Island 1949        */
    public static final GeoDatumType DATUM_REUNION = new GeoDatumType((6235 +
            33000), (6235 + 33000)); /* Reunion                      */
    public static final GeoDatumType DATUM_ANTIGUA_ISLAND_1943 = new
            GeoDatumType((6236 + 33000), (6236 + 33000));
            /* Antigua Island Astro 1943    */
    public static final GeoDatumType DATUM_ASCENSION_ISLAND_1958 = new
            GeoDatumType((6237 + 33000), (6237 + 33000));
            /* Ascension Island 1958        */
    public static final GeoDatumType DATUM_DOS_71_4 = new GeoDatumType((6238 +
            33000), (6238 + 33000)); /* Astro DOS 71/4               */
    public static final GeoDatumType DATUM_CACANAVERAL = new GeoDatumType((6239 +
            33000), (6239 + 33000)); /* Cape Canaveral               */
    public static final GeoDatumType DATUM_FORT_THOMAS_1955 = new GeoDatumType((
            6240 + 33000), (6240 + 33000)); /* Fort Thomas 1955             */
    public static final GeoDatumType DATUM_GRACIOSA_1948 = new GeoDatumType((
            6241 + 33000), (6241 + 33000)); /* Graciosa Base SW 1948        */
    public static final GeoDatumType DATUM_ISTS_061_1968 = new GeoDatumType((
            6242 + 33000), (6242 + 33000)); /* ISTS 061 Astro 1968          */
    public static final GeoDatumType DATUM_LC5_1961 = new GeoDatumType((6243 +
            33000), (6243 + 33000)); /* L.C. 5 Astro 1961            */
    public static final GeoDatumType DATUM_MONTSERRAT_ISLAND_1958 = new
            GeoDatumType((6244 + 33000), (6244 + 33000));
            /* Montserrat Isl Astro 1958    */
    public static final GeoDatumType DATUM_OBSERV_METEOR_1939 = new
            GeoDatumType((6245 + 33000), (6245 + 33000));
            /* Observ. Meteorologico 1939   */
    public static final GeoDatumType DATUM_PICO_DE_LAS_NIEVES = new
            GeoDatumType((6246 + 33000), (6246 + 33000));
            /* Pico de Las Nieves           */
    public static final GeoDatumType DATUM_PORTO_SANTO_1936 = new GeoDatumType((
            6247 + 33000), (6247 + 33000)); /* Porto Santo 1936             */
    public static final GeoDatumType DATUM_PUERTO_RICO = new GeoDatumType((6248 +
            33000), (6248 + 33000)); /* Puerto Rico                  */
    public static final GeoDatumType DATUM_SAO_BRAZ = new GeoDatumType((6249 +
            33000), (6249 + 33000)); /* Sao Braz                     */
    public static final GeoDatumType DATUM_SELVAGEM_GRANDE_1938 = new
            GeoDatumType((6250 + 33000), (6250 + 33000));
            /* Selvagem Grande 1938         */
    public static final GeoDatumType DATUM_TRISTAN_1968 = new GeoDatumType((
            6251 + 33000), (6251 + 33000)); /* Tristan Astro 1968           */
    public static final GeoDatumType DATUM_SAMOA_1962 = new GeoDatumType((6252 +
            33000), (6252 + 33000)); /* American Samoa 1962          */
    public static final GeoDatumType DATUM_CAMP_AREA = new GeoDatumType((6253 +
            33000), (6253 + 33000)); /* Camp Area Astro              */
    public static final GeoDatumType DATUM_DECEPTION_ISLAND = new GeoDatumType((
            6254 + 33000), (6254 + 33000)); /* Deception Island             */
    public static final GeoDatumType DATUM_GUNUNG_SEGARA = new GeoDatumType((
            6255 + 33000), (6255 + 33000)); /* Gunung Segara                */
    public static final GeoDatumType DATUM_INDIAN_1960 = new GeoDatumType((6256 +
            33000), (6256 + 33000)); /* Indian 1960                  */
    public static final GeoDatumType DATUM_S42_HUNGARY = new GeoDatumType((6257 +
            33000), (6257 + 33000)); /* S-42 Hungary                 */
    public static final GeoDatumType DATUM_S_JTSK = new GeoDatumType((6258 +
            33000), (6258 + 33000)); /* S-JTSK                       */
    public static final GeoDatumType DATUM_ALASKAN_ISLANDS = new GeoDatumType((
            6260 + 33000), (6260 + 33000)); /* Alaskan Islands              */
    public static final GeoDatumType DATUM_JAPAN_2000 = new GeoDatumType((6301 +
            33000), (6301 + 33000)); /*Japan 2000 = ITRF */
    public static final GeoDatumType DATUM_XIAN_1980 = new GeoDatumType((6312 +
            33000), (6312 + 33000)); /* Xian 1980 */
    
    public static final GeoDatumType DATUM_CHINA_2000 = new GeoDatumType((6313 +
    		33000), (6313+33000)); /* China 2000 */
}
