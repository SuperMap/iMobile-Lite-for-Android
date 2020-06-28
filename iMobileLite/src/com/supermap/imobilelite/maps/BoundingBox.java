package com.supermap.imobilelite.maps;

import java.util.List;

import com.supermap.services.util.ResourceManager;

/**
 * <p>
 * 二维矩形区域类。
 * </p>
 * <p>
 * 该类主要用来描述地图的地理坐标范围。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class BoundingBox {
    private static ResourceManager resource = new ResourceManager("com.supermap.android.MapCommon");
    /**
     * <p>
     * 左上角坐标
     * </p>
     */
    public Point2D leftTop;
    /**
     * <p>
     * 右下角坐标
     * </p>
     */
    public Point2D rightBottom;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public BoundingBox() {
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param boundingBox 二维矩形区域对象。
     */
    public BoundingBox(BoundingBox boundingBox) {
        if (boundingBox == null) {
            // throw new IllegalArgumentException(resource.getMessage(MapCommon.BOUNDINGBOX_NULL));
            return;
        }
        if (boundingBox.leftTop == null || boundingBox.rightBottom == null) {
            return;
            // throw new IllegalArgumentException(resource.getMessage(MapCommon.BOUNDINGBOX_ILLEGAL));
        }

        this.leftTop = new Point2D(boundingBox.leftTop);
        this.rightBottom = new Point2D(boundingBox.rightBottom);
    }

    /**
     * <p>
     * 构造函数。
     * </p>
     * @param leftTop 二维矩形区域左上角点。
     * @param rightBottom 二维矩形区域右下角点。
     */
    public BoundingBox(Point2D leftTop, Point2D rightBottom) {
        if (leftTop == null) {
            return;
            // throw new IllegalArgumentException(resource.getMessage(MapCommon.BOUNDINGBOX_LEFTTOP_NULL));
        }
        if (rightBottom == null) {
            return;
            // throw new IllegalArgumentException(resource.getMessage(MapCommon.BOUNDINGBOX_RIGHTBOTTOM_NULL));
        }
        this.leftTop = new Point2D(leftTop);
        this.rightBottom = new Point2D(rightBottom);
    }

    /**
     * <p>
     * 获取二维矩形区域左上角点的 x 坐标。
     * </p>
     * @return
     */
    public double getLeft() {
        if (this.leftTop != null) {
            return this.leftTop.getX();
        } else {
            return 0;
            // throw new IllegalStateException(resource.getMessage(MapCommon.BOUNDINGBOX_GETLEFT_LEFTTOP_NOINITIALIZED));
        }
    }

    /**
     * <p>
     * 获取二维矩形区域右下角点的 x 坐标。
     * </p>
     * @return
     */
    public double getRight() {
        if (this.rightBottom != null) {
            return this.rightBottom.getX();
        } else {
            return 0;
            // throw new IllegalStateException(resource.getMessage(MapCommon.BOUNDINGBOX_GETRIGHT_RIGHTBOTTOM_NOINITIALIZED));
        }
    }

    /**
     * <p>
     * 获取二维矩形区域左上角点的 y 坐标。
     * </p>
     * @return
     */
    public double getTop() {
        if (this.leftTop != null) {
            return this.leftTop.getY();
        } else {
            return 0;
            // throw new IllegalStateException(resource.getMessage(MapCommon.BOUNDINGBOX_GETTOP_LEFTTOP_NOINITIALIZED));
        }
    }

    /**
     * <p>
     * 获取二维矩形区域右下角点的 y 坐标。
     * </p>
     * @return
     */
    public double getBottom() {
        if (this.rightBottom != null) {
            return this.rightBottom.getY();
        } else {
            return 0;
            // throw new IllegalStateException(resource.getMessage(MapCommon.BOUNDINGBOX_GETBOTTOM_RIGHTBOTTOM_NOINITIALIZED));
        }
    }

    /**
     * <p>
     * 获取矩形区域的中心点。
     * </p>
     * @return
     */
    public Point2D getCenter() {
        if ((this.leftTop == null) || (this.rightBottom == null))
            return null;
        return new Point2D((this.leftTop.getX() + this.rightBottom.getX()) / 2, (this.leftTop.getY() + this.rightBottom.getY()) / 2);
    }

    /**
     * <p>
     * 判断指定的矩形区域是否包含在当前矩形区域内。
     * </p>
     * @param boundingBox 指定的矩形区域对象。
     * @return
     */
    public boolean contains(BoundingBox boundingBox) {
        if ((boundingBox == null) || (boundingBox.leftTop == null) || (boundingBox.rightBottom == null)) {
            return false;
        }
        return (this.leftTop.getY() >= boundingBox.leftTop.getY()) && (this.leftTop.getX() <= boundingBox.leftTop.getX())
                && (this.rightBottom.getY() <= boundingBox.rightBottom.getY()) && (this.rightBottom.getX() >= boundingBox.rightBottom.getX());
    }

    /**
     * <p>
     * 判定指定的点是否包含在当前矩形区域内。
     * </p>
     * @param point2D 指定的二维点对象。
     * @return 如果指定的点在此矩形区域内部和边界上时，此方法将返回 true；否则将返回 false。
     */
    public boolean contains(Point2D point2D) {
        if (leftTop == null || rightBottom == null) {
            return false;
        }
        return (point2D.getY() <= this.leftTop.getY()) && (point2D.getY() >= this.rightBottom.getY()) && (point2D.getX() <= this.rightBottom.getX())
                && (point2D.getX() >= this.leftTop.getX());
    }

    private String toString(boolean latFirst) {
        StringBuilder buffer = new StringBuilder();
        if (leftTop == null || rightBottom == null) {
            return "BoundingBox is unvalid";
        }
        if (latFirst) {
            buffer.append(this.leftTop.getY()).append(",").append(this.leftTop.getX()).append(",").append(this.rightBottom.getY()).append(",")
                    .append(this.rightBottom.getX());
        } else {
            buffer.append(this.leftTop.getX()).append(",").append(this.leftTop.getY()).append(",").append(this.rightBottom.getX()).append(",")
                    .append(this.rightBottom.getY());
        }

        return buffer.toString();
    }

    /**
     * <p>
     * 将 BoundingBox 对象的左上角和右下角的坐标转换成 String 形式。
     * </p>
     * @return BoundingBox 对象的左上角和右下角的坐标的 String 形式。
     */
    public String toString() {
        return toString(false);
    }

    /**
     * <p>
     * 判断两个矩形区域是否相交。
     * </p>
     * @param boundingBox1 矩形区域1。
     * @param boundingBox2 矩形区域2。
     * @return 返回矩形区域1和矩形区域2是否相交。如果相交，返回 true；否则，返回 false。
     */
    public static boolean intersect(BoundingBox boundingBox1, BoundingBox boundingBox2) {
        if (boundingBox1 == null || boundingBox2 == null || boundingBox1.leftTop == null || boundingBox1.rightBottom == null || boundingBox2.leftTop == null
                || boundingBox2.rightBottom == null) {
            return false;
        }
        return (boundingBox2.leftTop.getY() >= boundingBox1.rightBottom.getY()) && (boundingBox2.rightBottom.getY() <= boundingBox1.leftTop.getY())
                && (boundingBox2.leftTop.getX() <= boundingBox1.rightBottom.getX()) && (boundingBox2.rightBottom.getX() >= boundingBox1.leftTop.getX());
    }

    /**
     * <p>
     * 计算当前点串的最小外接矩形。
     * </p>
     * @param point2Ds 点串数组。
     * @return 返回当前点串数组的最小外接矩形区域对象。
     */
    public static BoundingBox calculateBoundingBoxGeoPoint(List<Point2D> point2Ds) {
        BoundingBox bbox = null;
        double minLat = 0;
        double maxLat = 0;
        double minLng = 0;
        double maxLng = 0;

        if ((point2Ds != null) && (point2Ds.size() > 0)) {
            Point2D first = (Point2D) point2Ds.get(0);
            minLat = maxLat = first.getY();
            minLng = maxLng = first.getX();
            for (Point2D gp : point2Ds) {
                if (gp.getY() > maxLat)
                    maxLat = gp.getY();
                else if (gp.getY() < minLat) {
                    minLat = gp.getY();
                }
                if (gp.getX() < minLng)
                    minLng = gp.getX();
                else if (gp.getX() > maxLng) {
                    maxLng = gp.getX();
                }
            }
        } else {
            return null;
        }
        bbox = new BoundingBox(new Point2D(minLng, maxLat), new Point2D(maxLng, minLat));
        return bbox;
    }

    /**
     * <p>
     * 获取矩形区域的宽度。
     * </p>
     * @return 返回矩形区域的宽度。
     */
    public double getWidth() {
        if ((this.leftTop == null) || (this.rightBottom == null))
            return 0;
        return Math.abs(rightBottom.getX() - leftTop.getX());
    }

    /**
     * <p>
     * 获取矩形区域的高度。
     * </p>
     * @return 返回矩形区域的高度。
     */
    public double getHeight() {
        if ((this.leftTop == null) || (this.rightBottom == null))
            return 0;
        return Math.abs(leftTop.getY() - rightBottom.getY());
    }

    /**
     * <p>
     * 获取矩形区域的左上角坐标。
     * </p>
     * @return 返回矩形区域的左上角坐标。
     */
    public Point2D getLeftTop() {
        return this.leftTop;
    }

    /**
     * <p>
     * 获取矩形区域的右下角坐标。
     * </p>
     * @return 返回矩形区域的右下角坐标。
     */
    public Point2D getRightBottom() {
        return this.rightBottom;
    }

    /**
     * <p>
     * 判断所获取的矩形区域宽度和高度是否合法。
     * </p>
     * @return 当获取的矩形区域宽度或高度小于或等于零时，返回 false ；否则，返回 true 。
     */
    public boolean isValid() {
        boolean flag = true;
        if (getLeft() >= getRight() || getBottom() >= getTop()) {
            flag = false;
        }
        return flag;
    }

    /**
     * <p>
     * 对 两个矩形区域对象进行并集计算，获取并集的最小外接矩形。
     * </p>
     * @param boundingBox1 矩形区域1。
     * @param boundingBox2 矩形区域2。
     * @return 两个矩形区域并集的最小外接矩形。
     */
    public static BoundingBox union(BoundingBox boundingBox1, BoundingBox boundingBox2) {
        if (boundingBox1 == null && boundingBox2 == null) {
            return null;
        }
        if (boundingBox1 == null) {
            return new BoundingBox(boundingBox2);
        }
        if (boundingBox2 == null) {
            return new BoundingBox(boundingBox1);
        }

        double minLat = boundingBox1.getBottom();
        double maxLng = boundingBox1.getRight();
        double minLng = boundingBox1.getLeft();
        double maxLat = boundingBox1.getTop();

        if (boundingBox2.leftTop != null) {
            if (minLng > boundingBox2.getLeft()) {
                minLng = boundingBox2.getLeft();
            }
            if (maxLat < boundingBox2.getTop()) {
                maxLat = boundingBox2.getTop();
            }
        }
        if (boundingBox2.rightBottom != null) {
            if (minLat > boundingBox2.getBottom()) {
                minLat = boundingBox2.getBottom();
            }
            if (maxLng < boundingBox2.getRight()) {
                maxLng = boundingBox2.getRight();
            }
        }
        return new BoundingBox(new Point2D(minLng, maxLat), new Point2D(maxLng, minLat));
    }
}