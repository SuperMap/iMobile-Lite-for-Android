package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class GeoCurve extends Geometry{
	
	private Point2Ds m_point2Ds = null;

	public GeoCurve() {
		long handle = GeoCurveNative.jni_New();
		this.setHandle(handle, true);
	}
	
	
	public GeoCurve(GeoCurve GeoCurve) {
		if(GeoCurve == null) {
			String message = InternalResource.loadString("GeoCurve",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long GeoCurveHandle = InternalHandleDisposable.getHandle(GeoCurve);
		if(GeoCurveHandle == 0) {
			String message = InternalResource.loadString("GeoCurve",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long cloneHandle = GeoCurveNative.jni_Clone(GeoCurveHandle);
		setHandle(cloneHandle, true);
		
		m_point2Ds = new Point2Ds(GeoCurve.getControlPoints(), this);
	}
	

	public GeoCurve(Point2Ds controlPoints) {
		int count = controlPoints.getCount();
        if (count < 6) {
            String message = InternalResource.loadString("controlPoints",
                    InternalResource.GeoCurveControlPointsLengthShouldNotLessThanSix,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        double[] xs = new double[count];
        double[] ys = new double[count];
        for (int i = 0; i < count; i++) {
            xs[i] = controlPoints.getItem(i).getX();
            ys[i] = controlPoints.getItem(i).getY();
        }
        long GeoCurveHandle = GeoCurveNative.jni_New2(xs, ys);
        this.setHandle(GeoCurveHandle, true);
        m_point2Ds = new Point2Ds(controlPoints, this);
	}
	
	GeoCurve(long handle) {
		this.setHandle(handle, false);
	}
	

	public double getLength() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getLength()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoCurveNative.jni_GetLength(this.getHandle());
	}
	

	public boolean isEmpty(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("isEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoCurveNative.jni_IsEmpty(getHandle());
	}

	public void setEmpty(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		m_point2Ds.clear();
		m_point2Ds = null;
		GeoCurveNative.jni_SetEmpty(getHandle());
	}
	
	

	public Point2Ds getControlPoints() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getControlPoints()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int count = GeoCurveNative.jni_GetPartPointCount(getHandle());
			
	    if (count >= 6) {
			double[] xs = new double[count];
			double[] ys = new double[count];
			GeoCurveNative.jni_GetControlPoints(this.getHandle(), xs, ys);
			Point2Ds pts = new Point2Ds();
			for (int i = 0; i < count; i++) {
				pts.add(new Point2D(xs[i], ys[i]));
			}

			if (m_point2Ds == null) {
				m_point2Ds = new Point2Ds(pts, this);
			} else {
				for (int i = 0; i < count; i++) {
					m_point2Ds.add(new Point2D(xs[i], ys[i]));
				}
				int newCount = m_point2Ds.getCount();
				m_point2Ds.removeRange(0, newCount - count);
			}
		}
		return this.m_point2Ds;
	}
	

	public void setControlPoints(Point2Ds controlPoints) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setControlPoints(Point2Ds controlPoints)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int count = controlPoints.getCount();
		if (count < 6) {
            String message = InternalResource.loadString("controlPoints",
                    InternalResource.GeoCurveControlPointsLengthShouldNotLessThanSix,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
		}
		double[] xs = new double[count];
		double[] ys = new double[count];
		for (int i = 0; i < ys.length; i++) {
            xs[i] = controlPoints.getItem(i).getX();
            ys[i] = controlPoints.getItem(i).getY();
		}
		GeoCurveNative.jni_SetControlPoints(this.getHandle(), xs, ys);
	}
	

	public GeoLine convertToLine(int pointCountPerSegment) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("convertToLine(int segmentCount)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (pointCountPerSegment < 2) {
			String message = InternalResource.loadString("pointCountPerSegment",
					InternalResource.GlobalArgumentShouldNotSmallerThanTwo,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long geoLineHandle = GeoCurveNative.jni_ConvertToLine(this.getHandle(), pointCountPerSegment);
		GeoLine geoLine = null;
		if(geoLineHandle != 0) {
            geoLine = new GeoLine(geoLineHandle);	
            geoLine.setIsDisposable(true);
        }
		return geoLine;
	}
	@Override
	public GeoCurve clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoCurve(this);
	}

	@Override
	public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
        	GeoCurveNative.jni_Delete(getHandle());
            this.setHandle(0);
            this.clearHandle();
        }	
	}
	protected void clearHandle() {
		super.clearHandle();
	}

	@Override
	public boolean fromJson(String json) {
		return false;
	}

	@Override
	public String toJson() {
		return null;
	}
}

