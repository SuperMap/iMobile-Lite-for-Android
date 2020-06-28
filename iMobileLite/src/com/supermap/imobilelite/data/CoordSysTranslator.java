package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */
 
public final class CoordSysTranslator {
    public static boolean forward(Point2Ds points, PrjCoordSys prjCoordSys) {
        if (points == null) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        Point2Ds.UserType ptsType = points.getUserType();
        points.setUserType(Point2Ds.UserType.NONE);
        int length = points.getCount();
        if (length < 1) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    Point2DsIsEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (prjCoordSys == null || prjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("prjCoordSys",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
            if (xs[i] > 180 || xs[i] < -180 || ys[i] > 90 || ys[i] < -90) {
                String message = InternalResource.loadString("points",
                        InternalResource.
                        InvalidLongitudeLatitudeCoord,
                        InternalResource.BundleName);
                throw new IllegalArgumentException(message);
            }
        }
        boolean result = CoordSysTranslatorNative.jni_Forward(xs, ys,
                prjCoordSys.getHandle());
        for (int i = 0; i < length; i++) {
            points.setItem(i,new Point2D(xs[i],ys[i]));
        }
        points.setUserType(ptsType);
        return result;
    }

    public static boolean inverse(Point2Ds points, PrjCoordSys prjCoordSys) {
        if (points == null) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        Point2Ds.UserType ptsType = points.getUserType();
        points.setUserType(Point2Ds.UserType.NONE);
        int length = points.getCount();
        if (length < 1) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    Point2DsIsEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (prjCoordSys == null || prjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("prjCoordSys",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean result = CoordSysTranslatorNative.jni_Inverse(xs, ys,
                prjCoordSys.getHandle());
        for (int i = 0; i < length; i++) {
            points.setItem(i,new Point2D(xs[i],ys[i]));
        }
        points.setUserType(ptsType);
        return result;
    }

    public static boolean convert(Point2Ds points,
                                  PrjCoordSys srcPrjCoordSys,
                                  PrjCoordSys desPrjCoordSys,
                                  CoordSysTransParameter coordSysTransParameter,
                                  CoordSysTransMethod coordSysTransMethod) {
        if (points == null) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        Point2Ds.UserType ptsType = points.getUserType();
        points.setUserType(Point2Ds.UserType.NONE);
        int length = points.getCount();
        if (length < 1) {
            String message = InternalResource.loadString("points",
                    InternalResource.
                    Point2DsIsEmpty,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (srcPrjCoordSys == null || srcPrjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("srcPrjCoordSys",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (desPrjCoordSys == null || desPrjCoordSys.getHandle() == 0) {
            String message = InternalResource.loadString("targetPrjCoordSys",
                    InternalResource.
                    GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (coordSysTransParameter == null ||
            coordSysTransParameter.getHandle() == 0) {
            coordSysTransParameter = new CoordSysTransParameter();
        }
        if (coordSysTransMethod == null) {
            String message = InternalResource.loadString("coordSysTransMethod",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean result = CoordSysTranslatorNative.jni_ConvertPoints(
                xs, ys,
                srcPrjCoordSys.getHandle(),
                desPrjCoordSys.getHandle(),
                coordSysTransParameter.getHandle(),
                coordSysTransMethod.getUGCValue());
        for (int i = 0; i < length; i++) {
            points.setItem(i,new Point2D(xs[i],ys[i]));
        }
        points.setUserType(ptsType);
        return result;
    }
    public static boolean convert(Geometry geometry,
            PrjCoordSys srcPrjCoordSys,
            PrjCoordSys desPrjCoordSys,
            CoordSysTransParameter coordSysTransParameter,
            CoordSysTransMethod coordSysTransMethod) {
		if (geometry == null || geometry.getHandle() == 0) {
			String message = InternalResource.loadString("geometry",
			InternalResource.
			GlobalArgumentNull,
			InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if (srcPrjCoordSys == null || srcPrjCoordSys.getHandle() == 0) {
			String message = InternalResource.loadString("srcPrjCoordSys",
			InternalResource.
			GlobalArgumentNull,
			InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (desPrjCoordSys == null || desPrjCoordSys.getHandle() == 0) {
			String message = InternalResource.loadString("desPrjCoordSys",
			InternalResource.
			GlobalArgumentNull,
			InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		if (coordSysTransParameter == null ||
			coordSysTransParameter.getHandle() == 0) {
			coordSysTransParameter = new CoordSysTransParameter();
		}
		
		if (coordSysTransMethod == null) {
			String message = InternalResource.loadString("coordSysTransMethod",
			InternalResource.GlobalArgumentNull,
			InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		
		boolean result = CoordSysTranslatorNative.jni_ConvertGeometry(
			geometry.getHandle(),
			srcPrjCoordSys.getHandle(),
			desPrjCoordSys.getHandle(),
			coordSysTransParameter.getHandle(),
			coordSysTransMethod.getUGCValue());
		if(geometry.getType() == GeometryType.GEOREGION)
		{
			((GeoRegion)geometry).refrashPartsList();
		}
		return result;
	}
 
}
