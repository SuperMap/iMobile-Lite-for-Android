package com.supermap.imobilelite.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/**
 * <p>
 * imobile移植类
 * </p>
 */

public class GeoRegion extends Geometry {
    private ArrayList m_geoRegionParts; 
   
    public GeoRegion() {
        super.setHandle(GeoRegionNative.jni_New(), true);
        this.m_geoRegionParts = new ArrayList();
    }

 
    public GeoRegion(GeoRegion region) {
        if (region == null) {
            String message = InternalResource.loadString("region",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (region.getHandle() == 0) {
            String message = InternalResource.loadString("region",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoRegionNative.jni_Clone(region.getHandle());
        super.setHandle(handle, true);
        m_geoRegionParts = new ArrayList(region.getPartsList().size());
        for (int i = 0; i < region.getPartsList().size(); i++) {
  
            Point2Ds point2Ds = (Point2Ds) region.getPartsList().get(i);
            m_geoRegionParts.add(point2Ds.clone());
        }
    }

 
    public GeoRegion(Point2Ds points) {
        this();
       if (points.getCount()<3) {
    	   String message = InternalResource.loadString("convertToRegion",
                   InternalResource.GeoRegionInvalidPointsLength,
                   InternalResource.BundleName);
           throw new IllegalArgumentException(message);
       }
        addPart(points);
    }

    
    
    
    

    public int addPart(Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "addPart(Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int len = points.getCount();
        if (len < 3) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoRegionInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[len];
        double[] ys = new double[len];
        int i;
        for (i = 0; i < len; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        int index = GeoRegionNative.jni_AddPart(getHandle(), xs, ys);
        Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
        Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
        m_geoRegionParts.add(newPoint2Ds);
        return index;
    }
    
    

    public GeoLine convertToLine() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("convertToLine()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        long ptr = GeoRegionNative.jni_ConvertToLine(getHandle());
        if (ptr != 0) {
            GeoLine geoLine = new GeoLine(ptr);
            geoLine.setIsDisposable(true);
            return geoLine;
        } else {
            return null;
        }
    }
    

    public Point2Ds getPart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPart(int index)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (index < 0 || index >= this.getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        return (Point2Ds)  m_geoRegionParts.get(index);
    }
    
    

    public boolean insertPart(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "insertPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        
        int len = points.getCount();
        if (len < 3) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoRegionInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        
        if (index < 0 || index > getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }
        double[] xs = new double[len];
        double[] ys = new double[len];
        int i;
        for (i = 0; i < len; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        if (index == getPartCount()) {
            int newIndex = GeoRegionNative.jni_AddPart(getHandle(), xs, ys);

            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(newIndex));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.add(index, newPoint2Ds);
            return (newIndex != -1);
        } else {

            boolean bResult = GeoRegionNative.jni_InsertPart(getHandle(), index,
                    xs, ys);
            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.add(index, newPoint2Ds);
            return bResult;
        }
    }

    public boolean removePart(int index) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "removePart(int index)",
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
        
        boolean bResult = GeoRegionNative.jni_RemovePart(getHandle(), index);
        if (bResult == true) {

            m_geoRegionParts.remove(index);
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
        
   
        if (points.getCount() < 3) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoRegionInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

      
        int len = points.getCount();
        double[] xs = new double[len];
        double[] ys = new double[len];
        int i;
        for (i = 0; i < len; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean bResult = GeoRegionNative.jni_SetPart(getHandle(), index, xs,
                ys);
        if (bResult == true) {

            Point2Ds point2Ds = new Point2Ds(this.getPartFromUGC(index));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            m_geoRegionParts.set(index, newPoint2Ds);
        }
        return bResult;
    }
    
    
  
    boolean setPartJustToUGC(int index, Point2Ds points) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setPart(int index, Point2Ds points)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        

        if (points.getCount() < 3) {
            String message = InternalResource.loadString("points",
                    InternalResource.GeoRegionInvalidPointsLength,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        if (index < 0 || index >= getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

      
        int len = points.getCount();
        double[] xs = new double[len];
        double[] ys = new double[len];
        int i;
        for (i = 0; i < len; i++) {
            xs[i] = points.getItem(i).getX();
            ys[i] = points.getItem(i).getY();
        }
        boolean bResult = GeoRegionNative.jni_SetPart(getHandle(), index, xs,
                ys);
        return bResult;
    }
    

    public double getArea() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getArea()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoRegionNative.jni_GetArea(getHandle());
    }

    public int getPartCount() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPartCount()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoRegionNative.jni_GetPartCount(getHandle());
    }


    public double getPerimeter() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getPerimeter()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return GeoRegionNative.jni_GetPerimeter(getHandle());
    }

    protected void clearHandle() {
        super.clearHandle();

        if (m_geoRegionParts != null) {
            m_geoRegionParts.clear();
            m_geoRegionParts = null;
        }
        this.setHandle(0);
    }

    protected static void clearHandle(GeoRegion region) {
        region.clearHandle();
    }
    
    protected void changeHandle(long regionHandle){
    	 if (regionHandle == 0) {
             String message = InternalResource.loadString("regionHandle",
                     InternalResource.GlobalInvalidConstructorArgument,
                     InternalResource.BundleName);
             throw new IllegalArgumentException(message);
         }

    	 this.setIsDisposable(true);
    	 this.dispose();
    	 this.setHandle(regionHandle,false);  	
    }
    
    protected static void changeHandle(GeoRegion region,long regionHandle){
    	region.changeHandle(regionHandle);
    }

    ArrayList getPartsList() {
        return this.m_geoRegionParts;
    }


    private Point2D[] getPartFromUGC(int index) {

        if (index < 0 || index >= this.getPartCount()) {
            String message = InternalResource.loadString("index",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IndexOutOfBoundsException(message);
        }

        int length = GeoRegionNative.jni_GetPartPointCount(getHandle(),
                index);
        if (length > 2) {
            Point2D point2Ds[] = new Point2D[length];
            double[] xs = new double[length];
            double[] ys = new double[length];
            GeoRegionNative.jni_GetPart(getHandle(), index, xs, ys);
            for (int i = 0; i < length; i++) {
                point2Ds[i] = (new Point2D(xs[i], ys[i]));
            }
            return point2Ds;
        } else {
            return null;
        }
    }


    private void refreshFromUGC() {

        int partCount = this.getPartCount();
        for (int i = 0; i < partCount; i++) {
            Point2Ds pts = (Point2Ds) m_geoRegionParts.get(i);

            pts.setUserType(Point2Ds.UserType.NONE);
            pts.clear();
            pts.addRange(this.getPartFromUGC(i));

            pts.setUserType(Point2Ds.UserType.GEOREGION);
        }
    }


     void refrashPartsList() {

        int count = getPartCount();
        m_geoRegionParts.clear();
        for (int i = 0; i < count; i++) {

            Point2Ds point2Ds = new Point2Ds(this.
                                             getPartFromUGC(i));
            Point2Ds newPoint2Ds = new Point2Ds(point2Ds, this);
            this.m_geoRegionParts.add(newPoint2Ds);
        }
    }
	

	@Override
	public GeoRegion clone() {
		
		if (this.getHandle() == 0) {
            String message = InternalResource.loadString("clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new GeoRegion(this);
	}

	@Override
	public void dispose() {
	        if (!this.getIsDisposable()) {
	            String message = InternalResource.loadString("dispose()",
	                    InternalResource.HandleUndisposableObject,
	                    InternalResource.BundleName);
	            throw new UnsupportedOperationException(message);
	        } else if (this.getHandle() != 0) {
	            GeoRegionNative.jni_Delete(getHandle());
	            setHandle(0);

	            clearHandle();
	        }
	}
	
    public boolean isEmpty() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getIsEmpty()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return (getPartCount() == 0);
    }
	

    GeoRegion(long handle) {
        this.setHandle(handle, false);
        m_geoRegionParts = new ArrayList();
        this.refrashPartsList();
    }
	protected void refreshHandle(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		this.setHandle(handle, false);
	}
	
	protected static void refreshHandle(GeoRegion geoRegion, long handle) {
		geoRegion.refreshHandle(handle);
	}

	@Override
	public boolean fromJson(String json) {
		SiJsonObject obj = new SiJsonObject(json);
		boolean ret = fromJson(obj);
		obj.dispose();
		return ret;
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
		
		sb.append(" \"type\": " + "\"REGION\"" + ",");
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
	public String toGeoJSON(){

		StringBuilder strBuilder = new StringBuilder();
		int count  = this.getPartCount();

		strBuilder.append("{\"type\": \"Polygon\",");
		
		strBuilder.append("\"coordinates\":[");
		
		getCoordinatesString(strBuilder, count);
		strBuilder.append("]}");
		return strBuilder.toString();
	}


	@Override
	public boolean fromGeoJSON(String geoJSON){
		if (!geoJSON.contains("Polygon")) {
			Log.e("GeoRegion", "Not match the type of Polygon");
			return false;
		}

		ArrayList<Point2Ds> ptsList = getPointsFromGeoJSON(geoJSON);
		for (int i = 0, size = ptsList.size(); i < size; i++) {
			this.addPart(ptsList.get(i));
		}
		return true;
	}

	private void getCoordinatesString(StringBuilder strBuilder, int count) {
		StringBuilder pointsBuilder = new StringBuilder();
		int ptsCount = 0;
		
		for(int i = 0;i < count; i++){
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
				
		
				if (j>0 && j% 1000 == 0 && j != ptsCount - 1) {
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
			
			strBuilder.append(pointsBuilder);

			pointsBuilder.delete(0, pointsBuilder.length());
		}

		if (count > 0) {
			strBuilder = strBuilder.deleteCharAt(strBuilder.lastIndexOf(","));
		}
	}
}
