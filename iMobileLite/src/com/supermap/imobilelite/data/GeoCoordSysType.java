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
 * @author not attributable
 * @version 2.0
 */
public class GeoCoordSysType extends Enum {
    private GeoCoordSysType(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final GeoCoordSysType GCS_USER_DEFINE = new GeoCoordSysType( -
            1, -1); /*�û��Զ���ĵ�������ϵ */
//{{
    public static final GeoCoordSysType GCS_AIRY_1830 = new GeoCoordSysType(
            4001, 4001); /* Airy 1830                          */
    public static final GeoCoordSysType GCS_AIRY_MOD = new GeoCoordSysType(4002,
            4002); /* Airy modified                      */
    public static final GeoCoordSysType GCS_AUSTRALIAN = new GeoCoordSysType(
            4003, 4003); /* Australian National                */
    public static final GeoCoordSysType GCS_BESSEL_1841 = new GeoCoordSysType(
            4004, 4004); /* Bessel 1841                        */
    public static final GeoCoordSysType GCS_BESSEL_MOD = new GeoCoordSysType(
            4005, 4005); /* Bessel modified                    */
    public static final GeoCoordSysType GCS_BESSEL_NAMIBIA = new
            GeoCoordSysType(4006, 4006);
    /* Bessel Namibia                     */
    public static final GeoCoordSysType GCS_CLARKE_1858 = new GeoCoordSysType(
            4007, 4007); /* Clarke 1858                        */
    public static final GeoCoordSysType GCS_CLARKE_1866 = new GeoCoordSysType(
            4008, 4008); /* Clarke 1866                        */
    public static final GeoCoordSysType GCS_CLARKE_1866_MICH = new
            GeoCoordSysType(4009, 4009);
    /* Clarke 1866 Michigan               */
    public static final GeoCoordSysType GCS_CLARKE_1880_BENOIT = new
            GeoCoordSysType(4010, 4010);
    /* Clarke 1880 (Benoit)               */
    public static final GeoCoordSysType GCS_CLARKE_1880_IGN = new
            GeoCoordSysType(4011, 4011);
    /* Clarke 1880 (IGN)                  */
    public static final GeoCoordSysType GCS_CLARKE_1880_RGS = new
            GeoCoordSysType(4012, 4012);
    /* Clarke 1880 (RGS)                  */
    public static final GeoCoordSysType GCS_CLARKE_1880_ARC = new
            GeoCoordSysType(4013, 4013);
    /* Clarke 1880 (Arc)                  */
    public static final GeoCoordSysType GCS_CLARKE_1880_SGA = new
            GeoCoordSysType(4014, 4014);
    /* Clarke 1880 (SGA)                  */
    public static final GeoCoordSysType GCS_EVEREST_1830 = new GeoCoordSysType(
            4015, 4015); /* Everest 1830                       */
    public static final GeoCoordSysType GCS_EVEREST_DEF_1967 = new
            GeoCoordSysType(4016, 4016);
    /* Everest (definition 1967)          */
    public static final GeoCoordSysType GCS_EVEREST_DEF_1975 = new
            GeoCoordSysType(4017, 4017);
    /* Everest (definition 1975)          */
    public static final GeoCoordSysType GCS_EVEREST_MOD = new GeoCoordSysType(
            4018, 4018); /* Everest modified                   */
    public static final GeoCoordSysType GCS_GRS_1980 = new GeoCoordSysType(4019,
            4019); /* GRS 1980                           */
    public static final GeoCoordSysType GCS_HELMERT_1906 = new GeoCoordSysType(
            4020, 4020); /* Helmert 1906                       */
    public static final GeoCoordSysType GCS_INDONESIAN = new GeoCoordSysType(
            4021, 4021); /* Indonesian National                */
    public static final GeoCoordSysType GCS_INTERNATIONAL_1924 = new
            GeoCoordSysType(4022, 4022);
    /* International 1927                 */
    public static final GeoCoordSysType GCS_INTERNATIONAL_1967 = new
            GeoCoordSysType(4023, 4023);
    /* International 1967                 */
    public static final GeoCoordSysType GCS_KRASOVSKY_1940 = new
            GeoCoordSysType(4024, 4024);
    /* Krasovsky 1940                     */
    public static final GeoCoordSysType GCS_NWL_9D = new GeoCoordSysType(4025,
            4025); /* Transit precise ephemeris          */
    public static final GeoCoordSysType GCS_PLESSIS_1817 = new GeoCoordSysType(
            4027, 4027); /* Plessis 1817                       */
    public static final GeoCoordSysType GCS_STRUVE_1860 = new GeoCoordSysType(
            4028, 4028); /* Struve 1860                        */
    public static final GeoCoordSysType GCS_WAR_OFFICE = new GeoCoordSysType(
            4029, 4029); /* War Office                         */

    public static final GeoCoordSysType GCS_GEM_10C = new GeoCoordSysType(4031,
            4031); /* GEM gravity potential model        */
    public static final GeoCoordSysType GCS_OSU_86F = new GeoCoordSysType(4032,
            4032); /* OSU 1986 geoidal model             */
    public static final GeoCoordSysType GCS_OSU_91A = new GeoCoordSysType(4033,
            4033); /* OSU 1991 geoidal model             */
    public static final GeoCoordSysType GCS_CLARKE_1880 = new GeoCoordSysType(
            4034, 4034); /* Clarke 1880                        */
    public static final GeoCoordSysType GCS_SPHERE = new GeoCoordSysType(4035,
            4035); /* Authalic sphere                    */
    public static final GeoCoordSysType GCS_GRS_1967 = new GeoCoordSysType(4036,
            4036); /* GRS 1967                           */

    public static final GeoCoordSysType GCS_WGS_1966 = new GeoCoordSysType((
            4001 + 33000), (4001 + 33000)); /* WGS 1966                   */
    public static final GeoCoordSysType GCS_FISCHER_1960 = new GeoCoordSysType((
            4002 + 33000), (4002 + 33000)); /* Fischer 1960               */
    public static final GeoCoordSysType GCS_FISCHER_1968 = new GeoCoordSysType((
            4003 + 33000), (4003 + 33000)); /* Fischer 1968               */
    public static final GeoCoordSysType GCS_FISCHER_MOD = new GeoCoordSysType((
            4004 + 33000), (4004 + 33000)); /* Fischer modified           */
    public static final GeoCoordSysType GCS_HOUGH_1960 = new GeoCoordSysType((
            4005 + 33000), (4005 + 33000)); /* Hough 1960                 */
    public static final GeoCoordSysType GCS_EVEREST_MOD_1969 = new
            GeoCoordSysType((4006 + 33000), (4006 + 33000));
    /* Everest modified 1969      */
    public static final GeoCoordSysType GCS_WALBECK = new GeoCoordSysType((4007 +
            33000), (4007 + 33000)); /* Walbeck                    */
    public static final GeoCoordSysType GCS_SPHERE_AI = new GeoCoordSysType((
            4008 + 33000), (4008 + 33000)); /* Authalic sphere (ARC/INFO) */

    public static final GeoCoordSysType GCS_GREEK = new GeoCoordSysType(4120,
            4120); /* Greek                              */
    public static final GeoCoordSysType GCS_GGRS_1987 = new GeoCoordSysType(
            4121, 4121); /* Greek Geodetic Ref. System 1987    */
    public static final GeoCoordSysType GCS_ATS_1977 = new GeoCoordSysType(4122,
            4122); /* Average Terrestrial System 1977     */
    public static final GeoCoordSysType GCS_KKJ = new GeoCoordSysType(4123,
            4123); /* Kartastokoordinaattijarjestelma    */
//}}
    /*----------------------------------------------------------------------------*/
    /*         G E O G R A P H I C   C O O R D I N A T E   S Y S T E M S          */
    /*----------------------------------------------------------------------------*/
//{{
    public static final GeoCoordSysType GCS_PULKOVO_1995 = new GeoCoordSysType(
            4200, 4200); /* Pulkovo 1995                       */
    public static final GeoCoordSysType GCS_ADINDAN = new GeoCoordSysType(4201,
            4201); /* Adindan                            */
    public static final GeoCoordSysType GCS_AGD_1966 = new GeoCoordSysType(4202,
            4202); /* Australian Geodetic Datum 1966     */
    public static final GeoCoordSysType GCS_AGD_1984 = new GeoCoordSysType(4203,
            4203); /* Australian Geodetic Datum 1984     */
    public static final GeoCoordSysType GCS_AIN_EL_ABD_1970 = new
            GeoCoordSysType(4204, 4204);
    /* Ain el Abd 1970                    */
    public static final GeoCoordSysType GCS_AFGOOYE = new GeoCoordSysType(4205,
            4205); /* Afgooye                            */
    public static final GeoCoordSysType GCS_AGADEZ = new GeoCoordSysType(4206,
            4206); /* Agadez                             */
    public static final GeoCoordSysType GCS_LISBON = new GeoCoordSysType(4207,
            4207); /* Lisbon                             */
    public static final GeoCoordSysType GCS_ARATU = new GeoCoordSysType(4208,
            4208); /* Aratu                              */
    public static final GeoCoordSysType GCS_ARC_1950 = new GeoCoordSysType(4209,
            4209); /* Arc 1950                           */
    public static final GeoCoordSysType GCS_ARC_1960 = new GeoCoordSysType(4210,
            4210); /* Arc 1960                           */
    public static final GeoCoordSysType GCS_BATAVIA = new GeoCoordSysType(4211,
            4211); /* Batavia                            */
    public static final GeoCoordSysType GCS_BARBADOS = new GeoCoordSysType(4212,
            4212); /* Barbados                           */
    public static final GeoCoordSysType GCS_BEDUARAM = new GeoCoordSysType(4213,
            4213); /* Beduaram                           */
    public static final GeoCoordSysType GCS_BEIJING_1954 = new GeoCoordSysType(
            4214, 4214); /* Beijing 1954                       */
    public static final GeoCoordSysType GCS_BELGE_1950 = new GeoCoordSysType(
            4215, 4215); /* Reseau National Belge 1950         */
    public static final GeoCoordSysType GCS_BERMUDA_1957 = new GeoCoordSysType(
            4216, 4216); /* Bermuda 1957                       */
    public static final GeoCoordSysType GCS_BERN_1898 = new GeoCoordSysType(
            4217, 4217); /* Bern 1898                          */
    public static final GeoCoordSysType GCS_BOGOTA = new GeoCoordSysType(4218,
            4218); /* Bogota                             */
    public static final GeoCoordSysType GCS_BUKIT_RIMPAH = new GeoCoordSysType(
            4219, 4219); /* Bukit Rimpah                       */
    public static final GeoCoordSysType GCS_CAMACUPA = new GeoCoordSysType(4220,
            4220); /* Camacupa                           */
    public static final GeoCoordSysType GCS_CAMPO_INCHAUSPE = new
            GeoCoordSysType(4221, 4221);
    /* Campo Inchauspe                    */
    public static final GeoCoordSysType GCS_CAPE = new GeoCoordSysType(4222,
            4222); /* Cape                               */
    public static final GeoCoordSysType GCS_CARTHAGE = new GeoCoordSysType(4223,
            4223); /* Carthage                           */
    public static final GeoCoordSysType GCS_CHUA = new GeoCoordSysType(4224,
            4224); /* Chua                               */
    public static final GeoCoordSysType GCS_CORREGO_ALEGRE = new
            GeoCoordSysType(4225, 4225);
    /* Corrego Alegre                     */
    public static final GeoCoordSysType GCS_COTE_D_IVOIRE = new GeoCoordSysType(
            4226, 4226); /* Cote d'Ivoire                      */
    public static final GeoCoordSysType GCS_DEIR_EZ_ZOR = new GeoCoordSysType(
            4227, 4227); /*Deir ez Zor                        */
    public static final GeoCoordSysType GCS_DOUALA = new GeoCoordSysType(4228,
            4228); /* Douala                             */
    public static final GeoCoordSysType GCS_EGYPT_1907 = new GeoCoordSysType(
            4229, 4229); /* Egypt 1907                         */
    public static final GeoCoordSysType GCS_ED_1950 = new GeoCoordSysType(4230,
            4230); /* European Datum 1950                */
    public static final GeoCoordSysType GCS_ED_1987 = new GeoCoordSysType(4231,
            4231); /* European Datum 1987                */
    public static final GeoCoordSysType GCS_FAHUD = new GeoCoordSysType(4232,
            4232); /* Fahud                              */
    public static final GeoCoordSysType GCS_GANDAJIKA_1970 = new
            GeoCoordSysType(4233, 4233);
    /* Gandajika 1970                     */
    public static final GeoCoordSysType GCS_GAROUA = new GeoCoordSysType(4234,
            4234); /* Garoua                             */
    public static final GeoCoordSysType GCS_GUYANE_FRANCAISE = new
            GeoCoordSysType(4235, 4235);
    /* Guyane Francaise                   */
    public static final GeoCoordSysType GCS_HU_TZU_SHAN = new GeoCoordSysType(
            4236, 4236); /* Hu Tzu Shan                        */
    public static final GeoCoordSysType GCS_HUNGARIAN_1972 = new
            GeoCoordSysType(4237, 4237);
    /* Hungarian Datum 1972               */
    public static final GeoCoordSysType GCS_INDONESIAN_1974 = new
            GeoCoordSysType(4238, 4238);
    /* Indonesian Datum 1974              */
    public static final GeoCoordSysType GCS_INDIAN_1954 = new GeoCoordSysType(
            4239, 4239); /* Indian 1954                        */
    public static final GeoCoordSysType GCS_INDIAN_1975 = new GeoCoordSysType(
            4240, 4240); /* Indian 1975                        */
    public static final GeoCoordSysType GCS_JAMAICA_1875 = new GeoCoordSysType(
            4241, 4241); /* Jamaica 1875                       */
    public static final GeoCoordSysType GCS_JAMAICA_1969 = new GeoCoordSysType(
            4242, 4242); /* Jamaica 1969                       */
    public static final GeoCoordSysType GCS_KALIANPUR = new GeoCoordSysType(
            4243, 4243); /* Kalianpur                          */
    public static final GeoCoordSysType GCS_KANDAWALA = new GeoCoordSysType(
            4244, 4244); /* Kandawala                          */
    public static final GeoCoordSysType GCS_KERTAU = new GeoCoordSysType(4245,
            4245); /* Kertau                             */
    public static final GeoCoordSysType GCS_KOC_ = new GeoCoordSysType(4246,
            4246); /* Kuwait Oil Company                 */
    public static final GeoCoordSysType GCS_LA_CANOA = new GeoCoordSysType(4247,
            4247); /* La Canoa                           */
    public static final GeoCoordSysType GCS_PSAD_1956 = new GeoCoordSysType(
            4248, 4248); /* Provisional South Amer. Datum 1956 */
    public static final GeoCoordSysType GCS_LAKE = new GeoCoordSysType(4249,
            4249); /* Lake                               */
    public static final GeoCoordSysType GCS_LEIGON = new GeoCoordSysType(4250,
            4250); /* Leigon                             */
    public static final GeoCoordSysType GCS_LIBERIA_1964 = new GeoCoordSysType(
            4251, 4251); /* Liberia 1964                       */
    public static final GeoCoordSysType GCS_LOME = new GeoCoordSysType(4252,
            4252); /* Lome                               */
    public static final GeoCoordSysType GCS_LUZON_1911 = new GeoCoordSysType(
            4253, 4253); /* Luzon 1911                         */
    public static final GeoCoordSysType GCS_HITO_XVIII_1963 = new
            GeoCoordSysType(4254, 4254);
    /* Hito XVIII 1963                    */
    public static final GeoCoordSysType GCS_HERAT_NORTH = new GeoCoordSysType(
            4255, 4255); /* Herat North                        */
    public static final GeoCoordSysType GCS_MAHE_1971 = new GeoCoordSysType(
            4256, 4256); /* Mahe 1971                          */
    public static final GeoCoordSysType GCS_MAKASSAR = new GeoCoordSysType(4257,
            4257); /* Makassar                           */
    public static final GeoCoordSysType GCS_ETRS_1989 = new GeoCoordSysType(
            4258, 4258); /* European Terrestrial Ref. Sys. 1989*/
    public static final GeoCoordSysType GCS_MALONGO_1987 = new GeoCoordSysType(
            4259, 4259); /* Malongo 1987                       */
    public static final GeoCoordSysType GCS_MANOCA = new GeoCoordSysType(4260,
            4260); /* Manoca                             */
    public static final GeoCoordSysType GCS_MERCHICH = new GeoCoordSysType(4261,
            4261); /* Merchich                           */
    public static final GeoCoordSysType GCS_MASSAWA = new GeoCoordSysType(4262,
            4262); /* Massawa                            */
    public static final GeoCoordSysType GCS_MINNA = new GeoCoordSysType(4263,
            4263); /* Minna                              */
    public static final GeoCoordSysType GCS_MHAST = new GeoCoordSysType(4264,
            4264); /* Mhast                              */
    public static final GeoCoordSysType GCS_MONTE_MARIO = new GeoCoordSysType(
            4265, 4265); /* Monte Mario                        */
    public static final GeoCoordSysType GCS_MPORALOKO = new GeoCoordSysType(
            4266, 4266); /* M'poraloko                         */
    public static final GeoCoordSysType GCS_NAD_1927 = new GeoCoordSysType(4267,
            4267); /* North American Datum 1927          */
    public static final GeoCoordSysType GCS_NAD_MICH = new GeoCoordSysType(4268,
            4268); /* NAD Michigan                       */
    public static final GeoCoordSysType GCS_NAD_1983 = new GeoCoordSysType(4269,
            4269); /* North American Datum 1983          */
    public static final GeoCoordSysType GCS_NAHRWAN_1967 = new GeoCoordSysType(
            4270, 4270); /* Nahrwan 1967                       */
    public static final GeoCoordSysType GCS_NAPARIMA_1972 = new GeoCoordSysType(
            4271, 4271); /* Naparima 1972                      */
    public static final GeoCoordSysType GCS_NZGD_1949 = new GeoCoordSysType(
            4272, 4272); /* New Zealand Geodetic Datum 1949    */
    public static final GeoCoordSysType GCS_NGO_1948_ = new GeoCoordSysType(
            4273, 4273); /* NGO 1948                           */
    public static final GeoCoordSysType GCS_DATUM_73 = new GeoCoordSysType(4274,
            4274); /* Datum 73                           */
    public static final GeoCoordSysType GCS_NTF_ = new GeoCoordSysType(4275,
            4275); /* Nouvelle Triangulation Francaise   */
    public static final GeoCoordSysType GCS_NSWC_9Z_2_ = new GeoCoordSysType(
            4276, 4276); /* NSWC 9Z-2                          */
    public static final GeoCoordSysType GCS_OSGB_1936 = new GeoCoordSysType(
            4277, 4277); /* OSGB 1936                          */
    public static final GeoCoordSysType GCS_OSGB_1970_SN = new GeoCoordSysType(
            4278, 4278); /* OSGB 1970 (SN)                     */
    public static final GeoCoordSysType GCS_OS_SN_1980 = new GeoCoordSysType(
            4279, 4279); /* OS (SN) 1980                       */
    public static final GeoCoordSysType GCS_PADANG_1884 = new GeoCoordSysType(
            4280, 4280); /* Padang 1884                        */
    public static final GeoCoordSysType GCS_PALESTINE_1923 = new
            GeoCoordSysType(4281, 4281);
    /* Palestine 1923                     */
    public static final GeoCoordSysType GCS_POINTE_NOIRE = new GeoCoordSysType(
            4282, 4282); /* Pointe Noire                       */
    public static final GeoCoordSysType GCS_GDA_1994 = new GeoCoordSysType(4283,
            4283); /* Geocentric Datum of Australia 1994 */
    public static final GeoCoordSysType GCS_PULKOVO_1942 = new GeoCoordSysType(
            4284, 4284); /* Pulkovo 1942                       */
    public static final GeoCoordSysType GCS_QATAR = new GeoCoordSysType(4285,
            4285); /* Qatar                              */
    public static final GeoCoordSysType GCS_QATAR_1948 = new GeoCoordSysType(
            4286, 4286); /* Qatar 1948                         */
    public static final GeoCoordSysType GCS_QORNOQ = new GeoCoordSysType(4287,
            4287); /* Qornoq                             */
    public static final GeoCoordSysType GCS_LOMA_QUINTANA = new GeoCoordSysType(
            4288, 4288); /* Loma Quintana                      */
    public static final GeoCoordSysType GCS_AMERSFOORT = new GeoCoordSysType(
            4289, 4289); /* Amersfoort                         */

    public static final GeoCoordSysType GCS_SAD_1969 = new GeoCoordSysType(4291,
            4291); /* South American Datum 1969          */
    public static final GeoCoordSysType GCS_SAPPER_HILL_1943 = new
            GeoCoordSysType(4292, 4292);
    /* Sapper Hill 1943                   */
    public static final GeoCoordSysType GCS_SCHWARZECK = new GeoCoordSysType(
            4293, 4293); /* Schwarzeck                         */
    public static final GeoCoordSysType GCS_SEGORA = new GeoCoordSysType(4294,
            4294); /* Segora                             */
    public static final GeoCoordSysType GCS_SERINDUNG = new GeoCoordSysType(
            4295, 4295); /* Serindung                          */
    public static final GeoCoordSysType GCS_SUDAN = new GeoCoordSysType(4296,
            4296); /* Sudan                              */
    public static final GeoCoordSysType GCS_TANANARIVE_1925 = new
            GeoCoordSysType(4297, 4297);
    /* Tananarive 1925                    */
    public static final GeoCoordSysType GCS_TIMBALAI_1948 = new GeoCoordSysType(
            4298, 4298); /* Timbalai 1948                      */
    public static final GeoCoordSysType GCS_TM65 = new GeoCoordSysType(4299,
            4299); /* TM65                               */

    public static final GeoCoordSysType GCS_TM75 = new GeoCoordSysType(4300,
            4300); /* TM75                               */
    public static final GeoCoordSysType GCS_TOKYO = new GeoCoordSysType(4301,
            4301); /* Tokyo                              */
    public static final GeoCoordSysType GCS_TRINIDAD_1903 = new GeoCoordSysType(
            4302, 4302); /* Trinidad 1903                      */
    public static final GeoCoordSysType GCS_TRUCIAL_COAST_1948 = new
            GeoCoordSysType(4303, 4303);
    /* Trucial Coast 1948                 */
    public static final GeoCoordSysType GCS_VOIROL_1875 = new GeoCoordSysType(
            4304, 4304); /* Voirol 1875                        */
    public static final GeoCoordSysType GCS_VOIROL_UNIFIE_1960 = new
            GeoCoordSysType(4305, 4305);
    /* Voirol Unifie 1960                 */
    public static final GeoCoordSysType GCS_BERN_1938 = new GeoCoordSysType(
            4306, 4306); /* Bern 1938                          */
    public static final GeoCoordSysType GCS_NORD_SAHARA_1959 = new
            GeoCoordSysType(4307, 4307);
    /* Nord Sahara 1959                   */
    public static final GeoCoordSysType GCS_RT38_ = new GeoCoordSysType(4308,
            4308); /* RT38                               */
    public static final GeoCoordSysType GCS_YACARE = new GeoCoordSysType(4309,
            4309); /* Yacare                             */
    public static final GeoCoordSysType GCS_YOFF = new GeoCoordSysType(4310,
            4310); /* Yoff                               */
    public static final GeoCoordSysType GCS_ZANDERIJ = new GeoCoordSysType(4311,
            4311); /* Zanderij                           */
    public static final GeoCoordSysType GCS_MGI_ = new GeoCoordSysType(4312,
            4312); /* Militar-Geographische Institut     */
    public static final GeoCoordSysType GCS_BELGE_1972 = new GeoCoordSysType(
            4313, 4313); /* Reseau National Belge 1972         */
    public static final GeoCoordSysType GCS_DHDNB = new GeoCoordSysType(4314,
            4314); /* Deutsche Hauptdreiecksnetz         */
    public static final GeoCoordSysType GCS_CONAKRY_1905 = new GeoCoordSysType(
            4315, 4315); /* Conakry 1905                       */
    public static final GeoCoordSysType GCS_DEALUL_PISCULUI_1933 = new
            GeoCoordSysType(4316, 4316); /* Dealul Piscului 1933 (Romania)   */
    public static final GeoCoordSysType GCS_DEALUL_PISCULUI_1970 = new
            GeoCoordSysType(4317, 4317); /* Dealul Piscului 1970 (Romania)   */
    public static final GeoCoordSysType GCS_NGN = new GeoCoordSysType(4318,
            4318); /* National Geodetic Network (Kuwait) */
    public static final GeoCoordSysType GCS_KUDAMS = new GeoCoordSysType(4319,
            4319); /* Kuwait Utility                     */

    public static final GeoCoordSysType GCS_WGS_1972 = new GeoCoordSysType(4322,
            4322); /* WGS 1972                           */

    public static final GeoCoordSysType GCS_WGS_1972_BE = new GeoCoordSysType(
            4324, 4324); /* WGS 1972 Transit Broadcast Ephemer.*/

    public static final GeoCoordSysType GCS_WGS_1984 = new GeoCoordSysType(4326,
            4326); /* WGS 1984                           */

    public static final GeoCoordSysType GCS_BERN_1898_BERN = new
            GeoCoordSysType(4801, 4801);
    /* Bern 1898 (Bern)                   */
    public static final GeoCoordSysType GCS_BOGOTA_BOGOTA = new GeoCoordSysType(
            4802, 4802); /* Bogota (Bogota)                    */
    public static final GeoCoordSysType GCS_LISBON_LISBON = new GeoCoordSysType(
            4803, 4803); /* Lisbon (Lisbon)                    */
    public static final GeoCoordSysType GCS_MAKASSAR_JAKARTA = new
            GeoCoordSysType(4804, 4804);
    /* Makassar (Jakarta)                 */
    public static final GeoCoordSysType GCS_MGI_FERRO = new GeoCoordSysType(
            4805, 4805); /* MGI (Ferro)                        */
    public static final GeoCoordSysType GCS_MONTE_MARIO_ROME = new
            GeoCoordSysType(4806, 4806);
    /* Monte Mario (Rome)                 */
    public static final GeoCoordSysType GCS_NTF_PARIS = new GeoCoordSysType(
            4807, 4807); /* NTF (Paris)                        */
    public static final GeoCoordSysType GCS_PADANG_1884_JAKARTA = new
            GeoCoordSysType(4808, 4808);
    /* Padang 1884 (Jakarta)              */
    public static final GeoCoordSysType GCS_BELGE_1950_BRUSSELS = new
            GeoCoordSysType(4809, 4809);
    /* Belge 1950 (Brussels)              */
    public static final GeoCoordSysType GCS_TANANARIVE_1925_PARIS = new
            GeoCoordSysType(4810, 4810); /* Tananarive 1925 (Paris)          */
    public static final GeoCoordSysType GCS_VOIROL_1875_PARIS = new
            GeoCoordSysType(4811, 4811);
    /* Voirol 1875 (Paris)                */
    public static final GeoCoordSysType GCS_VOIROL_UNIFIE_1960_PARIS = new
            GeoCoordSysType(4812, 4812); /* Voirol Unifie 1960 (Paris)    */
    public static final GeoCoordSysType GCS_BATAVIA_JAKARTA = new
            GeoCoordSysType(4813, 4813);
    /* Batavia (Jakarta)                  */
    public static final GeoCoordSysType GCS_RT38_STOCKHOLM = new
            GeoCoordSysType(4814, 4814);
    /* RT38 (Stockholm)                   */
    public static final GeoCoordSysType GCS_GREEK_ATHENS = new GeoCoordSysType(
            4815, 4815); /* Greek (Athens)                     */

    public static final GeoCoordSysType GCS_ATF_PARIS = new GeoCoordSysType(
            4901, 4901); /* ATF (Paris)                        */
    public static final GeoCoordSysType GCS_NDG_PARIS = new GeoCoordSysType(
            4902, 4902); /* Nord de Guerre (Paris)             */

    public static final GeoCoordSysType GCS_EUROPEAN_1979 = new GeoCoordSysType((
            4201 + 33000), (4201 + 33000)); /* European 1979              */
    public static final GeoCoordSysType GCS_EVEREST_BANGLADESH = new
            GeoCoordSysType((4202 + 33000), (4202 + 33000));
            /* Everest - Bangladesh       */
    public static final GeoCoordSysType GCS_EVEREST_INDIA_NEPAL = new
            GeoCoordSysType((4203 + 33000), (4203 + 33000));
            /* Everest - India and Nepal  */
    public static final GeoCoordSysType GCS_HJORSEY_1955 = new GeoCoordSysType((
            4204 + 33000), (4204 + 33000)); /* Hjorsey 1955               */
    public static final GeoCoordSysType GCS_HONG_KONG_1963 = new
            GeoCoordSysType((4205 + 33000), (4205 + 33000));
            /* Hong Kong 1963             */
    public static final GeoCoordSysType GCS_OMAN = new GeoCoordSysType((4206 +
            33000), (4206 + 33000)); /* Oman                       */
    public static final GeoCoordSysType GCS_S_ASIA_SINGAPORE = new
            GeoCoordSysType((4207 + 33000), (4207 + 33000));
            /* South Asia Singapore       */
    public static final GeoCoordSysType GCS_AYABELLE = new GeoCoordSysType((
            4208 + 33000), (4208 + 33000)); /* Ayabelle Lighthouse        */
    public static final GeoCoordSysType GCS_BISSAU = new GeoCoordSysType((4209 +
            33000), (4209 + 33000)); /* Bissau                     */
    public static final GeoCoordSysType GCS_DABOLA = new GeoCoordSysType((4210 +
            33000), (4210 + 33000)); /* Dabola                     */
    public static final GeoCoordSysType GCS_POINT58 = new GeoCoordSysType((4211 +
            33000), (4211 + 33000)); /* Point 58                   */
    public static final GeoCoordSysType GCS_BEACON_E_1945 = new GeoCoordSysType((
            4212 + 33000), (4212 + 33000)); /* Astro Beacon E 1945        */
    public static final GeoCoordSysType GCS_TERN_ISLAND_1961 = new
            GeoCoordSysType((4213 + 33000), (4213 + 33000));
            /* Tern Island Astro 1961     */
    public static final GeoCoordSysType GCS_ASTRO_1952 = new GeoCoordSysType((
            4214 + 33000), (4214 + 33000)); /* Astronomical Station 1952  */
    public static final GeoCoordSysType GCS_BELLEVUE = new GeoCoordSysType((
            4215 + 33000), (4215 + 33000)); /* Bellevue IGN               */
    public static final GeoCoordSysType GCS_CANTON_1966 = new GeoCoordSysType((
            4216 + 33000), (4216 + 33000)); /* Canton Astro 1966          */
    public static final GeoCoordSysType GCS_CHATHAM_ISLAND_1971 = new
            GeoCoordSysType((4217 + 33000), (4217 + 33000));
            /* Chatham Island Astro 1971  */
    public static final GeoCoordSysType GCS_DOS_1968 = new GeoCoordSysType((
            4218 + 33000), (4218 + 33000)); /* DOS 1968                   */
    public static final GeoCoordSysType GCS_EASTER_ISLAND_1967 = new
            GeoCoordSysType((4219 + 33000), (4219 + 33000));
            /* Easter Island 1967         */
    public static final GeoCoordSysType GCS_GUAM_1963 = new GeoCoordSysType((
            4220 + 33000), (4220 + 33000)); /* Guam 1963                  */
    public static final GeoCoordSysType GCS_GUX_1 = new GeoCoordSysType((4221 +
            33000), (4221 + 33000)); /* GUX 1 Astro                */
    public static final GeoCoordSysType GCS_JOHNSTON_ISLAND_1961 = new
            GeoCoordSysType((4222 + 33000), (4222 + 33000));
            /* Johnston Island 1961      */
    public static final GeoCoordSysType GCS_CARTHAGE_DEGREE = new
            GeoCoordSysType((4223 + 33000), (4223 + 33000));
            /* Carthage (degrees)         */

    public static final GeoCoordSysType GCS_MIDWAY_1961 = new GeoCoordSysType((
            4224 + 33000), (4224 + 33000)); /* Midway Astro 1961          */
    public static final GeoCoordSysType GCS_OLD_HAWAIIAN = new GeoCoordSysType((
            4225 + 33000), (4225 + 33000)); /* Old Hawaiian               */
    public static final GeoCoordSysType GCS_PITCAIRN_1967 = new GeoCoordSysType((
            4226 + 33000), (4226 + 33000)); /* Pitcairn Astro 1967        */
    public static final GeoCoordSysType GCS_SANTO_DOS_1965 = new
            GeoCoordSysType((4227 + 33000), (4227 + 33000));
            /* Santo DOS 1965             */
    public static final GeoCoordSysType GCS_VITI_LEVU_1916 = new
            GeoCoordSysType((4228 + 33000), (4228 + 33000));
            /* Viti Levu 1916             */
    public static final GeoCoordSysType GCS_WAKE_ENIWETOK_1960 = new
            GeoCoordSysType((4229 + 33000), (4229 + 33000));
            /* Wake-Eniwetok 1960         */
    public static final GeoCoordSysType GCS_WAKE_ISLAND_1952 = new
            GeoCoordSysType((4230 + 33000), (4230 + 33000));
            /* Wake Island Astro 1952     */
    public static final GeoCoordSysType GCS_ANNA_1_1965 = new GeoCoordSysType((
            4231 + 33000), (4231 + 33000)); /* Anna 1 Astro 1965          */
    public static final GeoCoordSysType GCS_GAN_1970 = new GeoCoordSysType((
            4232 + 33000), (4232 + 33000)); /* Gan 1970                   */
    public static final GeoCoordSysType GCS_ISTS_073_1969 = new GeoCoordSysType((
            4233 + 33000), (4233 + 33000)); /* ISTS 073 Astro 1969        */
    public static final GeoCoordSysType GCS_KERGUELEN_ISLAND_1949 = new
            GeoCoordSysType((4234 + 33000), (4234 + 33000));
            /* Kerguelen Island 1949    */
    public static final GeoCoordSysType GCS_REUNION = new GeoCoordSysType((4235 +
            33000), (4235 + 33000)); /* Reunion                    */
    public static final GeoCoordSysType GCS_ANTIGUA_ISLAND_1943 = new
            GeoCoordSysType((4236 + 33000), (4236 + 33000));
            /* Antigua Island Astro 1943  */
    public static final GeoCoordSysType GCS_ASCENSION_ISLAND_1958 = new
            GeoCoordSysType((4237 + 33000), (4237 + 33000));
            /* Ascension Island 1958    */
    public static final GeoCoordSysType GCS_DOS_71_4 = new GeoCoordSysType((
            4238 + 33000), (4238 + 33000)); /* Astro DOS 71/4              */
    public static final GeoCoordSysType GCS_CACANAVERAL = new GeoCoordSysType((
            4239 + 33000), (4239 + 33000)); /* Cape Canaveral              */
    public static final GeoCoordSysType GCS_FORT_THOMAS_1955 = new
            GeoCoordSysType((4240 + 33000), (4240 + 33000));
            /* Fort Thomas 1955            */
    public static final GeoCoordSysType GCS_GRACIOSA_1948 = new GeoCoordSysType((
            4241 + 33000), (4241 + 33000)); /* Graciosa Base SW 1948       */
    public static final GeoCoordSysType GCS_ISTS_061_1968 = new GeoCoordSysType((
            4242 + 33000), (4242 + 33000)); /* ISTS 061 Astro 1968         */
    public static final GeoCoordSysType GCS_LC5_1961 = new GeoCoordSysType((
            4243 + 33000), (4243 + 33000)); /* L.C. 5 Astro 1961           */
    public static final GeoCoordSysType GCS_MONTSERRAT_ISLAND_1958 = new
            GeoCoordSysType((4244 + 33000), (4244 + 33000));
            /* Montserrat Astro 1958   */
    public static final GeoCoordSysType GCS_OBSERV_METEOR_1939 = new
            GeoCoordSysType((4245 + 33000), (4245 + 33000));
            /* Observ. Meteorologico 1939  */
    public static final GeoCoordSysType GCS_PICO_DE_LAS_NIEVES = new
            GeoCoordSysType((4246 + 33000), (4246 + 33000));
            /* Pico de Las Nieves          */
    public static final GeoCoordSysType GCS_PORTO_SANTO_1936 = new
            GeoCoordSysType((4247 + 33000), (4247 + 33000));
            /* Porto Santo 1936            */
    public static final GeoCoordSysType GCS_PUERTO_RICO = new GeoCoordSysType((
            4248 + 33000), (4248 + 33000)); /* Puerto Rico                 */
    public static final GeoCoordSysType GCS_SAO_BRAZ = new GeoCoordSysType((
            4249 + 33000), (4249 + 33000)); /* Sao Braz                    */
    public static final GeoCoordSysType GCS_SELVAGEM_GRANDE_1938 = new
            GeoCoordSysType((4250 + 33000), (4250 + 33000));
            /* Selvagem Grande 1938      */
    public static final GeoCoordSysType GCS_TRISTAN_1968 = new GeoCoordSysType((
            4251 + 33000), (4251 + 33000)); /* Tristan Astro 1968          */
    public static final GeoCoordSysType GCS_SAMOA_1962 = new GeoCoordSysType((
            4252 + 33000), (4252 + 33000)); /* American Samoa 1962         */
    public static final GeoCoordSysType GCS_CAMP_AREA = new GeoCoordSysType((
            4253 + 33000), (4253 + 33000)); /* Camp Area Astro             */
    public static final GeoCoordSysType GCS_DECEPTION_ISLAND = new
            GeoCoordSysType((4254 + 33000), (4254 + 33000));
            /* Deception Island            */
    public static final GeoCoordSysType GCS_GUNUNG_SEGARA = new GeoCoordSysType((
            4255 + 33000), (4255 + 33000)); /* Gunung Segara               */
    public static final GeoCoordSysType GCS_INDIAN_1960 = new GeoCoordSysType((
            4256 + 33000), (4256 + 33000)); /* Indian 1960                 */
    public static final GeoCoordSysType GCS_S42_HUNGARY = new GeoCoordSysType((
            4257 + 33000), (4257 + 33000)); /* S-42 Hungary                */
    public static final GeoCoordSysType GCS_S_JTSK = new GeoCoordSysType((4258 +
            33000), (4258 + 33000)); /* S-JTSK                      */
    public static final GeoCoordSysType GCS_KUSAIE_1951 = new GeoCoordSysType((
            4259 + 33000), (4259 + 33000)); /* Kusaie Astro 1951          */
    public static final GeoCoordSysType GCS_ALASKAN_ISLANDS = new
            GeoCoordSysType((4260 + 33000), (4260 + 33000));
            /* Alaskan Islands             */

    public static final GeoCoordSysType GCS_JAPAN_2000 = new GeoCoordSysType((
            4301 + 33000), (4301 + 33000));
    public static final GeoCoordSysType GCS_XIAN_1980 = new GeoCoordSysType((
            4312 + 33000), (4312 + 33000)); /* Xian 1980*/
    
    // ����Ϊ6.0����
    public static final GeoCoordSysType GCS_CHINA_2000 = new GeoCoordSysType((
    		4313+33000),(4313+33000));  /* China 2000 */

}
