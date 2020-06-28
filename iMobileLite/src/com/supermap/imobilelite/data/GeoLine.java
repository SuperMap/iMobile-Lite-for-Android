package com.supermap.imobilelite.data;

import java.util.ArrayList;
import android.util.Log;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class GeoLine extends Geometry {
    private ArrayList m_geoLineParts; 
 
    public GeoLine() {
        long handle = GeoLineNative.jni_New();
        this.setHandle(handle, true);
        m_geoLineParts = new ArrayList();
    }


    public GeoLine(GeoLine geoLine) {
        if (geoLine == null) {
            String message = InternalResource.loadString("geoLine",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (geoLine.getHandle() == 0) {
            String message = InternalResource.loadString("geoLine",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoLineNative.jni_Clone(geoLine.getHandle());
        this.setHandle(handle, true);
        m_geoLineParts = new ArrayList();
        for (int i = 0; i < geoLine.getPartsList().size(); i++) {

            Point2Ds point2Ds = (Point2Ds) geoLine.getPartsList().get(i);
            m_geoLineParts.add(point2Ds.clone());
        }
    }

 
    public GeoLine(Point2Ds points) {
        this();
        addPart(points);
    }


    GeoLine(long handle) {
        this.setHandle(handle, false);
        m_geoLineParts = new ArrayList();
        this.refrashPartsList();
    }


 
    public boolean isEmpty() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getIsEmpty()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return this.getPartCount() == 0;
    }


    public GeoLine clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new GeoLine(this);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            GeoLineNative.jni_Delete(getHandle());
            this.setHandle(0);

            clearHandle();
        }
    }

 
    public double getLength() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getLength()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoLineNative.jni_GetLength(getHandle());
    }


    public int getPartCount() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPartCount()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoLineNative.jni_GetPartCount(getHandle());
    }


    public int addPart(Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("addPart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int length = points.getCount();
        if (length < 2) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        Point2Ds newPoint2Ds = new Point2Ds(points, this);
        m_geoLineParts.add(newPoint2Ds);
        return GeoLineNative.jni_AddPart(getHandle(), xs, ys);
    }


    public boolean removePart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("removePart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        boolean bResult = GeoLineNative.jni_RemovePart(getHandle(), index);
        if (bResult == true) {
  
            m_geoLineParts.remove(index);
        }
        return bResult;
    }

 
    public Point2D findPointOnLineByDistance(double distance) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "findPointOnLineByDistance(double distance)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }


        if (distance < 0) {
            String message = InternalResource.loadString("distance",
                    InternalResource.GeoLineArgumentShouldNotBeNegative,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
  
        Point2D pt = new Point2D();
        if(getPartCount() > 0){
            double[] point = new double[2];
            GeoLineNative.jni_FindPointOnLineByDistance(getHandle(), distance,
                    point);
            pt.setX(point[0]);
            pt.setY(point[1]);
        }
        return pt;
    }

 
    public Point2Ds getPart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPart()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }


        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

     	if(m_geoLineParts.isEmpty()){
     			this.refrashPartsList();
     	}
        return (Point2Ds) m_geoLineParts.get(index);
    }



    @Override
    public boolean fromXML(String xml){
    	boolean isTrue=super.fromXML(xml);
    	this.refrashPartsList();
    	return isTrue;
    }
    

    public boolean insertPart(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "insertPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        int length = points.getCount();

        if (length < 2) {
            String message = InternalResource.loadString("Point2Ds",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }
        
        boolean bResult = false;
        int partCount = getPartCount();

        if (partCount == index) {
            int indexInsert = addPart(points);
            bResult = (indexInsert == index);
            if (bResult == true) {

                Point2Ds newPoint2Ds = new Point2Ds(points, this);
                m_geoLineParts.add(index, newPoint2Ds);
            }
            return bResult;
        }


        if (index < 0 || index > getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        
        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        bResult = GeoLineNative.jni_InsertPart(getHandle(), index, xs, ys);
        if (bResult == true) {

            Point2Ds newPoint2Ds = new Point2Ds(points, this);
            m_geoLineParts.add(index, newPoint2Ds);
        }
        return bResult;
    }
    

    public boolean setPart(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = points.getCount();

        if (length < 2) {
            String message = InternalResource.loadString("Point2Ds",
                    InternalResource.GeoLineInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);

        }

        double[] xs = new double[length];
        double[] ys = new double[length];
        for (int i = 0; i < length; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean bResult = GeoLineNative.jni_SetPart(getHandle(), index, xs, ys);
        if (bResult == true) {

            Point2Ds newPoint2Ds = new Point2Ds(points, this);
            m_geoLineParts.set(index, newPoint2Ds);
        }
        return bResult;
    }


    public void rotate(Point2D basePoint, double angle) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "rotate(Point2D basePoint, double angle)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        super.rotate(basePoint, angle);
        this.refreshFromUGC();
    }

    protected void clearHandle() {
        super.clearHandle();

        if (m_geoLineParts != null) {
            m_geoLineParts.clear();
            m_geoLineParts = null;
        }
    }

    ArrayList getPartsList() {
        return this.m_geoLineParts;
    }


    private Point2D[] getPartFromUGC(int index) {

        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        int length = GeoLineNative.jni_GetPartPointCount(getHandle(),
                index);
        if (length > 1) {
            Point2D[] point2Ds = new Point2D[length];
            double[] xs = new double[length];
            double[] ys = new double[length];
            GeoLineNative.jni_GetPart(getHandle(), index, xs, ys);
            for (int i = 0; i < length; i++) {
                point2Ds[i] = new Point2D(xs[i], ys[i]);
            }
            return point2Ds;
        } else {
            return null;
        }
    }


    private void refreshFromUGC() {

        int partCount = this.getPartCount();
        for (int i = 0; i < partCount; i++) {
            Point2Ds pts = (Point2Ds) m_geoLineParts.get(i);

            pts.setUserType(Point2Ds.UserType.NONE);
            pts.clear();
            pts.addRange(this.getPartFromUGC(i));

            pts.setUserType(Point2Ds.UserType.GEOLINE);
        }
    }

     void refrashPartsList() {

        int count = getPartCount();
        m_geoLineParts.clear();
        for (int i = 0; i < count; i++) {
 
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(i));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoLineParts.add(newPoint2Ds);
        }
    }

	@Override
	public boolean fromJson(SiJsonObject json) {
		if(super.fromJson(json)){
			
			SiJsonArray parts = json.getJsonArray("parts");
			SiJsonArray points = json.getJsonArray("points");
			
			int count = parts.getArraySize();
			int partCount = 0;
			int index = 0;
			for(int i=0;i<count;i++){
				partCount = parts.getInt(i);
				Point2Ds pts = new Point2Ds();
				for(int j=0;j<partCount;j++){
					Point2D pt = new Point2D();
					SiJsonObject ptJson = points.getJsonObject( index++ );
					if(!pt.fromJson(ptJson)){
						ptJson.dispose();
						continue;
					}
					ptJson.dispose();
					pts.add(pt);
				}
				this.addPart(pts);
			}
			parts.dispose();
			points.dispose();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean fromJson(String json) {
		SiJsonObject obj = new SiJsonObject(json);
		boolean ret = fromJson(obj);
		obj.dispose();
		return ret;
		
	}

	@Override
	public String toJson() {
		StringBuilder sb = new StringBuilder(super.toJson());
		sb.deleteCharAt(sb.length()-1);
		sb.append(",");
		
		String parts = "";
		int count  = this.getPartCount();
		for(int i=0;i<count;i++){
			parts += this.getPart(i).getCount();
			if(i != count-1){
				parts += ",";
			}
		}
		sb.append(" \"parts\": " + "["+ parts+"]" + ",");
		
		sb.append(" \"type\": " + "LINE" + ",");
		String points = "";
		int partPoints = 0;
		for(int i=0;i<count;i++){
			Point2Ds pts = this.getPart(i);
			partPoints = pts.getCount();
			for(int j=0;j<partPoints;j++){
				points += pts.getItem(j).toJson();
				if(i == count-1 && j == partPoints-1){

				}else{
					points += ",";
				}
			}
		}
		sb.append(" \"points\" :" + "[" + points + "]");
		
		sb.append("}");
		return sb.toString();
	}
	
	
	@Override
	public String toGeoJSON() {

		StringBuilder strBuilder = new StringBuilder();
		int count  = this.getPartCount();
		if (count > 1) {
			strBuilder.append("{\"type\": \"MultiLineString\",");
		} else {
			strBuilder.append("{\"type\": \"LineString\",");
		}
		
		strBuilder.append("\"coordinates\":[");
		
		getCoordinatesString(strBuilder, count);
		strBuilder.append("]}");
		return strBuilder.toString();

	}
	

	@Override
	public boolean fromGeoJSON(String geoJSON){
		if (geoJSON.contains("LineString")|| geoJSON.contains("MultiLineString")) {
			ArrayList<Point2Ds> ptsList = getPointsFromGeoJSON(geoJSON);
			for (int i = 0, size = ptsList.size(); i < size; i++) {
				this.addPart(ptsList.get(i));
			}
		} else {
			Log.e("GeoLine", "Not match the type of LineString or MultiLineString");
			return false;
		}

		return true;
	}

	private void getCoordinatesString(StringBuilder strBuilder, int count) {
		StringBuilder pointsBuilder = new StringBuilder();
		int ptsCount = 0;
		
		for(int i=0;i<count;i++){
			if (count > 1) {
				pointsBuilder.append("[");
			}
			
			Point2Ds pts = this.getPart(i);
			ptsCount = pts.getCount();
			for(int j = 0; j < ptsCount; j++){

				pointsBuilder.append("[");
				pointsBuilder.append(pts.getItem(j).getX());
				pointsBuilder.append(",");
				pointsBuilder.append(pts.getItem(j).getY());
				pointsBuilder.append("],");
				
	
				if (j > 0 && j % 1000 == 0 && j != ptsCount - 1) {
					strBuilder.append(pointsBuilder.toString());
					pointsBuilder.delete(0, pointsBuilder.length());
				}
			}

			if (ptsCount > 0) {
				pointsBuilder.deleteCharAt(pointsBuilder.length() - 1);
			}
			if (count > 1) {
				pointsBuilder.append("]");
			}
			

			pointsBuilder.append(",");
			
			strBuilder.append(pointsBuilder.toString());

			pointsBuilder.delete(0, pointsBuilder.length());
		}

		if (count > 0) {
			strBuilder = strBuilder.deleteCharAt(strBuilder.lastIndexOf(","));
		}
	}
}
