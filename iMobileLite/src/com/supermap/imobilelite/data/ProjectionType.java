package com.supermap.imobilelite.data;

import java.util.ArrayList;


/**
 * <p>
 * imobile移植类
 * </p>
 */

public class ProjectionType extends Enum {
	private static ArrayList<Enum> m_values = new ArrayList<Enum>();

	private	ProjectionType(int value, int ugcValue) {
		super(value, ugcValue);
		super.setCustom(true);
	}

    public static final ProjectionType PRJ_NONPROJECTION = new ProjectionType(
            43000, 43000); 
    public static final ProjectionType PRJ_PLATE_CARREE = new ProjectionType(
            43001, 43001); /* Plate Carree                */
    public static final ProjectionType PRJ_EQUIDISTANT_CYLINDRICAL = new
            ProjectionType(43002, 43002); /* Equidistant Cylindrical      */
    public static final ProjectionType PRJ_MILLER_CYLINDRICAL = new
            ProjectionType(43003, 43003); /* Miller Cylindrical           */
    public static final ProjectionType PRJ_MERCATOR = new ProjectionType(43004,
            43004); /* Mercator                     */
    public static final ProjectionType PRJ_GAUSS_KRUGER = new ProjectionType(
            43005, 43005); /* Gauss-Kruger                 */
    public static final ProjectionType PRJ_TRANSVERSE_MERCATOR = new
            ProjectionType(43006, 43006); /* Transverse Mercator ==43005  */
    public static final ProjectionType PRJ_ALBERS = new ProjectionType(43007,
            43007); /* Albers                       */
    public static final ProjectionType PRJ_SINUSOIDAL = new ProjectionType(
            43008, 43008); /* Sinusoidal                   */
    public static final ProjectionType PRJ_MOLLWEIDE = new ProjectionType(43009,
            43009); /* Mollweide                    */
    public static final ProjectionType PRJ_ECKERT_VI = new ProjectionType(43010,
            43010); /* Eckert VI                    */
    public static final ProjectionType PRJ_ECKERT_V = new ProjectionType(43011,
            43011); /* Eckert V                     */
    public static final ProjectionType PRJ_ECKERT_IV = new ProjectionType(43012,
            43012); /* Eckert IV                    */
    public static final ProjectionType PRJ_ECKERT_III = new ProjectionType(
            43013, 43013); /* Eckert III                   */
    public static final ProjectionType PRJ_ECKERT_II = new ProjectionType(43014,
            43014); /* Eckert II                    */
    public static final ProjectionType PRJ_ECKERT_I = new ProjectionType(43015,
            43015); /* Eckert I                     */
    public static final ProjectionType PRJ_GALL_STEREOGRAPHIC = new
            ProjectionType(43016, 43016); /* Gall Stereographic           */
    public static final ProjectionType PRJ_BEHRMANN = new ProjectionType(43017,
            43017); /* Behrmann                     */
    public static final ProjectionType PRJ_WINKEL_I = new ProjectionType(43018,
            43018); /* Winkel I                     */
    public static final ProjectionType PRJ_WINKEL_II = new ProjectionType(43019,
            43019); /* Winkel II                    */
    public static final ProjectionType PRJ_LAMBERT_CONFORMAL_CONIC = new
            ProjectionType(43020, 43020); /* Lambert Conformal Conic      */
    public static final ProjectionType PRJ_POLYCONIC = new ProjectionType(43021,
            43021); /* Polyconic                    */
    public static final ProjectionType PRJ_QUARTIC_AUTHALIC = new
            ProjectionType(43022, 43022); /* Quartic Authalic             */
    public static final ProjectionType PRJ_LOXIMUTHAL = new ProjectionType(
            43023, 43023); /* Loximuthal                   */
    public static final ProjectionType PRJ_BONNE = new ProjectionType(43024,
            43024); /* Bonne                        */
    public static final ProjectionType PRJ_HOTINE = new ProjectionType(43025,
            43025); /* Hotine                       */
    public static final ProjectionType PRJ_STEREOGRAPHIC = new ProjectionType(
            43026, 43026); /* Stereographic                */
    public static final ProjectionType PRJ_EQUIDISTANT_CONIC = new
            ProjectionType(43027, 43027); /* Equidistant Conic            */
    public static final ProjectionType PRJ_CASSINI = new ProjectionType(43028,
            43028); /* Cassini                      */
    public static final ProjectionType PRJ_VAN_DER_GRINTEN_I = new
            ProjectionType(43029, 43029); /* Van der Grinten I            */
    public static final ProjectionType PRJ_ROBINSON = new ProjectionType(43030,
            43030); /* Robinson                     */
    public static final ProjectionType PRJ_TWO_POINT_EQUIDISTANT = new
            ProjectionType(43031, 43031); /* Two-Point Equidistant        */
    public static final ProjectionType PRJ_EQUIDISTANT_AZIMUTHAL = new
            ProjectionType(43032, 43032); /* Equidistant Azimuthal        */
    public static final ProjectionType PRJ_LAMBERT_AZIMUTHAL_EQUAL_AREA = new
            ProjectionType(43033, 43033); /* Lambert Azimuthal Equal Area*/
    public static final ProjectionType PRJ_CONFORMAL_AZIMUTHAL = new
            ProjectionType(43034, 43034); /* Conformal Azimuthal*/
    public static final ProjectionType PRJ_ORTHO_GRAPHIC = new ProjectionType(
            43035, 43035);
    public static final ProjectionType PRJ_GNOMONIC = new ProjectionType(43036,
            43036);
    public static final ProjectionType PRJ_CHINA_AZIMUTHAL = new ProjectionType(
            43037, 43037);
    public static final ProjectionType PRJ_SANSON = new ProjectionType(43040,
            43040);
    public static final ProjectionType PRJ_EQUALAREA_CYLINDRICAL = new
            ProjectionType(43041, 43041); /* EqualArea Cylindrical        */
    public static final ProjectionType PRJ_HOTINE_AZIMUTH_NATORIGIN = new
            ProjectionType(43042, 43042);
    
    public static final ProjectionType PRJ_OBLIQUE_MERCATOR = new
    ProjectionType(43043, 43043);
    
    public static final ProjectionType PRJ_HOTINE_OBLIQUE_MERCATOR = new
    ProjectionType(43044, 43044);
    
    public static final ProjectionType PRJ_SPHERE_MERCATOR = new
    ProjectionType(43045, 43045);
    
    public static final ProjectionType PRJ_BONNE_SOUTH_ORIENTATED = new ProjectionType(43046, 43046);
   
    public static final ProjectionType PRJ_OBLIQUE_STEREOGRAPHIC = new ProjectionType(43047, 43047);
    

    public static ProjectionType newInstance(int value) {
    	if(value >= 43000 && value < 44000 )
    	{
            String message = InternalResource.loadString(
                    "value",
                    InternalResource.InvalidArgumentValue,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
    	}

    	ProjectionType type = new ProjectionType(value, value);
    	m_values.add(type);
    	m_hashMap.put(ProjectionType.class, m_values);
    	return type;
    }
  }
