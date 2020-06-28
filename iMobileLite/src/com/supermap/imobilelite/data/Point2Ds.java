package com.supermap.imobilelite.data;

import java.lang.*;
import java.util.ArrayList;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class Point2Ds {
    private ArrayList m_point2Ds = null;
    private GeoLine m_geoLine = null;
    private GeoRegion m_geoRegion = null;
    private UserType m_userType = UserType.NONE;
    private GeoCardinal m_geoCardinal = null;
    private GeoBSpline m_geoBSpline = null;
    private GeoCurve m_geoCurve = null;
    
    public Point2Ds() {
        m_point2Ds = new ArrayList();
        this.m_userType = UserType.NONE;
    }

    public Point2Ds(Point2D[] points) {
        this();
        this.addRange(points);
    }

    public Point2Ds(Point2Ds points) {
        this();
        int size = points.getCount();
        for (int i = 0; i < size; i++) {
            m_point2Ds.add(points.getItem(i).clone());
        }
    }

    Point2Ds(Point2Ds points, GeoLine geoLine) {
        this(points);
        this.m_geoLine = geoLine;
        this.m_userType = UserType.GEOLINE;
    }

    Point2Ds(Point2Ds points, GeoRegion geoRegion) {
        this(points);
        this.m_geoRegion = geoRegion;
        this.m_userType = UserType.GEOREGION;
    }
    
    Point2Ds(Point2Ds points, GeoCardinal geoCardinal) {
        this(points);
        this.m_geoCardinal = geoCardinal;
        this.m_userType = UserType.GEOCARDINAL;
    }
    
    Point2Ds(Point2Ds points, GeoBSpline geoBSpline) {
        this(points);
        this.m_geoBSpline = geoBSpline;
        this.m_userType = UserType.GEOBSPLINE;
    }
    Point2Ds(Point2Ds points, GeoCurve geoCurve) {
        this(points);
        this.m_geoCurve = geoCurve;
        this.m_userType = UserType.GEOCURVE;
    }

    public int getCount() {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);


            if (indexLine == -1) {

                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

 
            if (indexRegion == -1) {

                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } 
        
        return m_point2Ds.size();
    }


    public Point2D getItem(int index) {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);


            if (indexLine == -1) {

                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);


            if (indexRegion == -1) {

                String message = InternalResource.loadString("getItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } 

        Point2D pt = (Point2D) m_point2Ds.get(index);
        return (Point2D) pt.clone();
    }
    public void setItem(int index, Point2D point2D) {
    	if(point2D.isEmpty()){
    		return;                            
    	}
        if (this.m_userType.value() == UserType.NONE.value()) {
            m_point2Ds.set(index, point2D.clone());
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);
 
            if (indexLine == -1) {
    
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            m_point2Ds.set(index, point2D);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);


            if (indexRegion == -1) {
  
                String message = InternalResource.loadString("setItem()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            if (index == 0) {

                m_point2Ds.remove(m_point2Ds.size() - 1);
            } else if (index == m_point2Ds.size() - 1) {

                m_point2Ds.set(0, point2D);
            }
            m_point2Ds.set(index, point2D);
            m_geoRegion.setPartJustToUGC(indexRegion, this);

        } 
    }


    public Point2Ds clone() {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

 
            if (indexLine == -1) {
    
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);


            if (indexRegion == -1) {
   
                String message = InternalResource.loadString("clone()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } 

        return new Point2Ds(this);

    }


    public int add(Point2D pt) {
        int indexAdded = -1;
        int indexRemove = -1;
        boolean bSuccess = false;

        if(pt.isEmpty()){
        	
        	return indexAdded;
        }
        if (this.m_userType.value() == UserType.NONE.value()) {
            indexRemove = m_point2Ds.size() - 1; 
            bSuccess = m_point2Ds.add(pt.clone());
            if (bSuccess) {
                indexAdded = m_point2Ds.size() - 1;
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);


            if (indexLine == -1) {

                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            indexRemove = m_point2Ds.size() - 1; 
            bSuccess = m_point2Ds.add(pt.clone());
            if (bSuccess) {
                indexAdded = m_point2Ds.size() - 1;
            }

            int index = m_geoLine.getPartsList().indexOf(this);
            m_geoLine.setPart(index, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);


            if (indexRegion == -1) {

                String message = InternalResource.loadString("add()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            indexRemove = m_point2Ds.size() - 1; 

            bSuccess = m_point2Ds.add(pt.clone());
            if (bSuccess) {
                indexAdded = m_point2Ds.size() - 1;
            }


            m_point2Ds.remove(indexRemove);

            m_geoRegion.setPart(indexRegion, this);
        } 

        return indexAdded;
    }

   

    public int addRange(Point2D[] points) {
        int length = points.length;
        int countInvalid = 0;                    
        int indexRemove = m_point2Ds.size() - 1; 
        if (this.m_userType.value() == UserType.NONE.value()) {
			for (int i = 0; i < length; i++) {
				if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					m_point2Ds.add(points[i].clone());
				}
			}
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);


            if (indexLine == -1) {
      
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

			for (int i = 0; i < length; i++) {
				if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					m_point2Ds.add(points[i].clone());
				}
			}

            m_geoLine.setPart(indexLine, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

 
            if (indexRegion == -1) {

                String message = InternalResource.loadString("addRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
			for (int i = 0; i < length; i++) {
				if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					m_point2Ds.add(points[i].clone());
				}
			}

            m_point2Ds.remove(indexRemove);
            m_geoRegion.setPart(indexRegion, this);
        } 
       
        return length - countInvalid;
    }


    public boolean insert(int index, Point2D pt) {
        if(index<0 || index>this.getCount()){
           String message = InternalResource.loadString("insert(int index, Point2D pt)",
                   InternalResource.GlobalIndexOutOfBounds,
                   InternalResource.BundleName);
               throw new IllegalArgumentException(message);
       }

        boolean bSuccess = false;

        if(pt.isEmpty()){
        	return bSuccess;
        }
        int indexRemove = m_point2Ds.size() - 1; 
        int size = m_point2Ds.size();
        if (this.m_userType.value() == UserType.NONE.value()) {

            m_point2Ds.add(index, pt.clone());
            if (m_point2Ds.size() == size + 1) {
                bSuccess = true;
            } else {
                bSuccess = false;
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("insert()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);

   
            if (indexLine == -1) {
      
                String message = InternalResource.loadString("insert()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            m_point2Ds.add(index, pt.clone());
            if (m_point2Ds.size() == size + 1) {
                bSuccess = true;
            } else {
                bSuccess = false;
            }

            m_geoLine.setPart(indexLine, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {

            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("insert()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);


            if (indexRegion == -1) {
 
                String message = InternalResource.loadString("insert()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            m_point2Ds.add(index, pt.clone());
            if (m_point2Ds.size() == size + 1) {
                bSuccess = true;
            } else {
                bSuccess = false;
            }

   
            if (index == 0) {

                m_point2Ds.remove(indexRemove + 1);
            }

    
            if (index == m_point2Ds.size()) {
                m_point2Ds.remove(indexRemove);
            }
            m_geoRegion.setPart(indexRegion, this);
        } 
        
        return bSuccess;
    }
    

    public int insertRange(int index, Point2D[] points) {
        if(index<0 || index>this.getCount()){
            String message = InternalResource.loadString("insertRange(int index, Point2D[] points)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
                throw new IllegalArgumentException(message);
        }
        int indexRemove = m_point2Ds.size() - 1;
        
        int length = points.length;
        int countInvalid = 0;
        if (this.m_userType.value() == UserType.NONE.value()) {
            for (int i = 0; i < length; i++) {
            	if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					int realIndex = index + i - countInvalid;
	                m_point2Ds.add(realIndex, points[i].clone());
				}
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexLine = m_geoLine.getPartsList().indexOf(this);


            if (indexLine == -1) {
     
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            for (int i = 0; i < length; i++) {
            	if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					int realIndex = index + i - countInvalid;
	                m_point2Ds.add(realIndex, points[i].clone());
				}
            }
            m_geoLine.setPart(indexLine, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);


            if (indexRegion == -1) {
     
                String message = InternalResource.loadString("insertRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            for (int i = 0; i < length; i++) {
            	if(points[i].isEmpty()){
					countInvalid ++;
				}else{
					int realIndex = index + i - countInvalid;
	                m_point2Ds.add(realIndex, points[i].clone());
				}
            }

       
            if (index == 0) {
                m_point2Ds.remove(indexRemove + length - countInvalid);
            }


            if (index == m_point2Ds.size()) {
                m_point2Ds.remove(indexRemove);
            }
            m_geoRegion.setPart(indexRegion, this);
        } 
        
        return length - countInvalid;
    }

    public boolean remove(int index) {
        if (index < 0 || index >= this.getCount()) {
            String message = InternalResource.loadString(
                    "remove(int index)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        Point2D oldValue = null;
        Point2D removeValue = null;
        boolean bResult = false;
        if (this.m_userType.value() == UserType.NONE.value()) {
            oldValue = (Point2D) m_point2Ds.get(index);
            removeValue = (Point2D) m_point2Ds.remove(index);
            if (oldValue.equals(removeValue)) {
                bResult = true;
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);


            if (indexLine == -1) {
        
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

           
            if (this.getCount() < 2) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.Point2DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }
            oldValue = (Point2D) m_point2Ds.get(index);
            removeValue = (Point2D) m_point2Ds.remove(index);
            if (oldValue.equals(removeValue)) {
                m_geoLine.setPart(indexLine, this);
                bResult = true;
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);


            if (indexRegion == -1) {
   
                String message = InternalResource.loadString("remove()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            
            if (this.getCount() < 4) {
                String message = InternalResource.loadString("remove()",
                        InternalResource.Point2DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }

            oldValue = (Point2D) m_point2Ds.get(index);
            removeValue = (Point2D) m_point2Ds.remove(index);

            if (oldValue.equals(removeValue)) {

                if (index == 0) {
                   
                    m_point2Ds.remove(m_point2Ds.size() - 1);
                }
                if (index == m_point2Ds.size()) {
                    
                    m_point2Ds.remove(0);
                }
                m_geoRegion.setPart(indexRegion, this);
                bResult = true;
            }
        }
        return bResult;
    }

    public int removeRange(int index, int count) {
        if (index < 0 || index >= this.getCount()) {
            String message = InternalResource.loadString(
                    "removeRange(int index, int count)",
                    InternalResource.GlobalIndexOutOfBounds,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }

        int countBeforeRemove = this.getCount();
        int countAfterRemove = countBeforeRemove - count;
        if (this.m_userType.value() == UserType.NONE.value()) {
            for (int i = index + count - 1; i >= index; i--) {
                m_point2Ds.remove(i);
            }
        } else if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

            if (indexLine == -1) {
                
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            
            if (countAfterRemove < 2) {
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.Point2DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }

            for (int i = index + count - 1; i >= index; i--) {
                m_point2Ds.remove(i);
            }
            m_geoLine.setPart(indexLine, this);
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

           
            if (indexRegion == -1) {
                
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            
            if (countAfterRemove < 4) {
                String message = InternalResource.loadString("removeRange()",
                        InternalResource.Point2DsInvalidPointLength,
                        InternalResource.BundleName);
                throw new UnsupportedOperationException(message);
            }
            for (int i = index + count - 1; i >= index; i--) {
                m_point2Ds.remove(i);
            }
            if (index == 0) {
               
                m_point2Ds.remove(m_point2Ds.size() - 1);
            }
            if (index + count == countBeforeRemove) {
                
                m_point2Ds.remove(0);
            }
            m_geoRegion.setPart(indexRegion, this);
        } 
       
        return count;
    }

    public void clear() {
        
        if (this.m_userType.equals(UserType.GEOLINE) ||
            this.m_userType.equals(UserType.GEOREGION) ) {
            String message = InternalResource.loadString("clear()",
                    InternalResource.Point2DsCannotDoClearOperation,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }
        m_point2Ds.clear();
    }

    public Point2D[] toArray() {
        if (this.m_userType.value() == UserType.GEOLINE.value()) {
            if (this.m_geoLine.getHandle() == 0) {
                String message = InternalResource.loadString("toArray()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            int indexLine = m_geoLine.getPartsList().indexOf(this);

          
            if (indexLine == -1) {
               
                String message = InternalResource.loadString("toArray()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        } else if (this.m_userType.value() == UserType.GEOREGION.value()) {
            if (this.m_geoRegion.getHandle() == 0) {
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            int indexRegion = m_geoRegion.getPartsList().indexOf(this);

            
            if (indexRegion == -1) {
               
                String message = InternalResource.loadString("getCount()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
        }
        
        int count = m_point2Ds.size();
        Point2D[] point2Ds = new Point2D[count];
        for(int i=0;i<count;i++){
            point2Ds[i] = (Point2D)m_point2Ds.get(i);
        }
        return point2Ds;
    }

    static class UserType extends Enum {
        private UserType(int value, int ugcValue) {
            super(value, ugcValue);
        }

        public static final UserType NONE = new UserType(1, 1);
        public static final UserType GEOLINE = new UserType(2, 2);
        public static final UserType GEOREGION = new UserType(3, 3);
        public static final UserType GEOCARDINAL = new UserType(4, 4);
        public static final UserType GEOBSPLINE = new UserType(5, 5);
        public static final UserType GEOCURVE = new UserType(6, 6);
    }


    UserType getUserType() {
        return this.m_userType;
    }

    void setUserType(UserType userType) {
        this.m_userType = userType;
    }
}
