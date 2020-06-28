package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class TextPart extends InternalHandleDisposable {
    private Point2D m_anchorPoint = null;
    private GeoText m_geoText = null; 


    public TextPart() {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset();
    }

    public TextPart(TextPart part) {
        if (part.getHandle() == 0) {
            String message = InternalResource.loadString("part",
                    InternalResource.GlobalArgumentObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long partHandle = 0;
        if (part.m_geoText != null) {
            int index = part.m_geoText.getTextPartsList().indexOf(part);

            if (index == -1) {
                String message = InternalResource.loadString(
                        "TextPart(TextPart part)",
                        InternalResource.GlobalArgumentObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalArgumentException(message);
            }
            partHandle = GeoTextNative.jni_GetSubHandle(part.m_geoText.
                    getHandle(), index);
        } else {
            partHandle = part.getHandle();
        }
        long handle = TextPartNative.jni_Clone(partHandle);
        this.setHandle(handle, true);
        this.setAnchorPoint(part.getAnchorPoint());
    }

 
    public TextPart(String text, Point2D anchorPoint) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset(anchorPoint, 0, text);
    }

  
    public TextPart(String text, Point2D anchorPoint, double rotation) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        reset(anchorPoint, rotation, text);
    }

 
    public TextPart(String text, double x, double y, double rotation) {
        long handle = TextPartNative.jni_New();
        this.setHandle(handle, true);
        Point2D anchorPoint = new Point2D(x, y);
        reset(anchorPoint, rotation, text);
    }


    TextPart(GeoText geoText, int index) {
        if (geoText.getHandle() == 0) {
            String message = InternalResource.loadString("geoText",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = GeoTextNative.jni_GetSubHandle(geoText.getHandle(), index);
        this.setHandle(handle, false);
        this.m_geoText = geoText;
    }

   
    public void dispose() {
      
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            TextPartNative.jni_Delete(getHandle());
            this.setHandle(0);
        }
    }

  
    public double getRotation() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getRotation()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
          
            if (index == -1) {
                String message = InternalResource.loadString("getRotation()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            
            return GeoTextNative.jni_GetSubRotation(m_geoText.getHandle(),
                    index);
        } else {
            return TextPartNative.jni_GetRotation(getHandle());
        }
    }

   
    public void setRotation(double rotation) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setRotation(double rotation)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
          
            if (index == -1) {

                String message = InternalResource.loadString("setRotation()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            GeoTextNative.jni_SetSubRotation(m_geoText.getHandle(), rotation,
                                             index);
        } else {
            TextPartNative.jni_SetRotation(getHandle(), rotation);
        }
    }

   
    public String getText() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getText()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
           
            if (index == -1) {
                String message = InternalResource.loadString("getText()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            return GeoTextNative.jni_GetSubText(m_geoText.getHandle(), index);
        } else {
            return TextPartNative.jni_GetText(getHandle());
        }
    }


    public void setText(String text) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setText(String text)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        if (text == null) {
            text = "";
        }

        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
           
            if (index == -1) {
                String message = InternalResource.loadString("setText()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            GeoTextNative.jni_SetSubText(m_geoText.getHandle(), text, index);
        } else {
            TextPartNative.jni_SetText(getHandle(), text);
        }
    }


    public Point2D getAnchorPoint() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getAnchorPoint()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            double pt[] = new double[2];
           
            TextPartNative.jni_GetSubAnchor(m_geoText.getHandle(), pt,
                                            index);
            return new Point2D(pt[0], pt[1]);
        } else {
            return m_anchorPoint;
        }
    }

  
    public void setAnchorPoint(Point2D anchorPoint) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setAnchorPoint(Point2D anchorPoint)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
           
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            
            TextPartNative.jni_SetSubAnchor(m_geoText.getHandle(),
                                            anchorPoint.getX(),
                                            anchorPoint.getY(), index);
        } else {
            this.m_anchorPoint = (Point2D) anchorPoint.clone();
        }
    }

 
    public double getX() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getX()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);
            
            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }
            double pt[] = new double[2];
           
            TextPartNative.jni_GetSubAnchor(m_geoText.getHandle(), pt,
                                            index);
            return pt[0];
        } else {
            return m_anchorPoint.getX();
        }

    }

   
    public double getY() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString("getY()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (this.m_geoText != null) {
            int index = m_geoText.getTextPartsList().indexOf(this);

            if (index == -1) {
                String message = InternalResource.loadString(
                        "getAnchorPoint()",
                        InternalResource.HandleObjectHasBeenDisposed,
                        InternalResource.BundleName);
                throw new IllegalStateException(message);
            }

            double pt[] = new double[2];

            TextPartNative.jni_GetSubAnchor(m_geoText.getHandle(), pt,
                                            index);
            return pt[1];
        } else {
            return m_anchorPoint.getY();
        }
    }

    void reset() {
        reset(new Point2D(0, 0), 0, "");
    }
    void reset(Point2D anchorPoint, double rotation, String text) {

        this.setAnchorPoint(anchorPoint);
        this.setRotation(rotation);
        this.setText(text);
    }
}

