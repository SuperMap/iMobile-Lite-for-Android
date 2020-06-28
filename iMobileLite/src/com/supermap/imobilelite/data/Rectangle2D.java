package com.supermap.imobilelite.data;

/**
 * <p>Title:二维矩形类 </p>
 *
 * <p>Description:存储一组整数，共四个，表示一个矩形的位置和大小。该类采用平面坐标而非屏幕坐标系统 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author 李云锦
 * @version 2.0
 */
public class Rectangle2D {
    private double left;
    private double right;
    private double top;
    private double bottom;

    public Rectangle2D() {
//        left = Toolkit.DBL_MIN_VALUE;
//        right = Toolkit.DBL_MIN_VALUE;
//        top = Toolkit.DBL_MIN_VALUE;
//        bottom = Toolkit.DBL_MIN_VALUE;
    }

    /**
     * 从 rect 初始化 Rectangle2D结构的新实例
     * @param rect Rectangle2D
     */
    public Rectangle2D(Rectangle2D rect) {
        this(rect.getLeft(), rect.getBottom(), rect.getRight(), rect.getTop());
    }

    /**
     * 用指定的左下角坐标、宽度与高度初始化矩形的新实例
     * @param topLeft Point2D
     * @param width double
     * @param height double
     */
    public Rectangle2D(Point2D leftBottom, double width, double height) {
        this.left = leftBottom.getX();
        this.bottom = leftBottom.getY();
        this.right = this.left + width;
        this.top = this.bottom + height;
    }

    /**
     *
     * @param left double
     * @param bottom double
     * @param right double
     * @param top double
     */
    public Rectangle2D(Point2D leftBottom, Point2D rightTop) {
        this.left = leftBottom.getX();
        this.bottom = leftBottom.getY();
        this.right = rightTop.getX();
        this.top = rightTop.getY();
    }

    /**
     *  构造函数，利用四角坐标
     * @param left double
     * @param right double
     * @param bottom double
     * @param top double
     */
    public Rectangle2D(double left, double bottom, double right, double top) {
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }
    public Rectangle2D(Point2D center,Size2D size){
    	if(size.getWidth()<0||size.getHeight()<0){
    		String message = InternalResource.loadString("size",
					InternalResource.Rectangle2DWidthAndHeightShouldMoreThanZero,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
    	}
    	double left = center.getX()-size.getWidth()/2;
    	double bottom = center.getY()-size.getHeight()/2;
    	double right = center.getX()+size.getWidth()/2;
    	double top =  center.getY()+size.getHeight()/2;
    	this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }

//    public final static Rectangle2D getEMPTY() {
//        return new Rectangle2D(Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE,
//                               Toolkit.DBL_MIN_VALUE, Toolkit.DBL_MIN_VALUE);
//    }

    /**
     * 矩形是否为空
     * @return boolean
     */
//    public boolean isEmpty() {
//        if (Toolkit.isZero(left - Toolkit.DBL_MIN_VALUE,
//                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
//                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
//            Toolkit.isZero(top - Toolkit.DBL_MIN_VALUE,
//                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
//                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
//            Toolkit.isZero(right - Toolkit.DBL_MIN_VALUE,
//                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
//                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION) &&
//            Toolkit.isZero(bottom - Toolkit.DBL_MIN_VALUE,
//                           Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
//                           Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * ���ؾ��ε����ĵ�
     * @return Point2D
     */
    public Point2D getCenter() {
        Point2D pt = new Point2D();
        pt.setX((left + right) / 2);
        pt.setY((top + bottom) / 2);
        return pt;
    }

    /**
     * 返回矩形的中心点
     * @return Point2D
     */
    public double getTop() {
        return top;
    }

    public void setTop(double value) {
        top = value;
    }

    /**
     * 获取此 Rectangle 结构上边缘的 y 坐标
     * @return double
     */
    public double getBottom() {
        return bottom;
    }

    public void setBottom(double value) {
        bottom = value;
    }

    /**
     * 获取 y 坐标，该坐标是此 Rectangle 结构的 Y 与 Height 属性值之和
     * @return double
     */
    public double getLeft() {
        return left;
    }

    /**
     * 获取此 Rectangle 结构左边缘的 x 坐标
     * @return double
     */
    public void setLeft(double value) {
        left = value;
    }

    /**
     * 获取 x 坐标，该坐标是此 Rectangle 结构的 X 与 Width 属性值之和
     * @return double
     */
    public double getRight() {
        return right;
    }

    /**
     *
     * @param value double
     */
    public void setRight(double value) {
        right = value;
    }

    /**
     * 获取此 Rectangle 结构的宽度
     * @return double
     */
    public double getWidth() {
        return Math.abs(right - left);
    }


    /**
     * 获取此 Rectangle 结构的高度
     * @return double
     */
    public double getHeight() {
        return Math.abs(top - bottom);
    }


    /**
     * 克隆对象
     * @return Rectangle2D
     */
    public Rectangle2D clone() {
        return new Rectangle2D(this);
    }

    /**
     * 通过将 rect 的X,Y,Width,Height舍入到与其接近的较大整数值，并以整数值构造一个新的Rectagnle2D返回
     * @param rect Rectangle2D
     * @return Rectangle2D
     */
    public static Rectangle2D ceiling(Rectangle2D rect) {
        Rectangle2D r = new Rectangle2D(Math.ceil(rect.getLeft()),
                                        Math.ceil(rect.getBottom()),
                                        Math.ceil(rect.getRight()),
                                        Math.ceil(rect.getTop()));

        return r;
    }

    /**
     * 通过将 rect 的X,Y,Width,Height值截断为小于或者等于它的最大整数值，并以整数值构造一个新的Rectangle2D返回
     * @param rect Rectangle2D
     * @return Rectangle2D
     */
    public static Rectangle2D floor(Rectangle2D rect) {
        Rectangle2D r = new Rectangle2D(Math.floor(rect.getLeft()),
                                        Math.floor(rect.getBottom()),
                                        Math.floor(rect.getRight()),
                                        Math.floor(rect.getTop()));

        return r;
    }

    /**
     * 通过将 rect 的X,Y,Width,Height舍入到最接近的整数值，并以整数值构造一个新的Rectangle2D返回
     * @param rect Rectangle2D
     * @return Rectangle2D
     */
    public static Rectangle2D round(Rectangle2D rect) {
        Rectangle2D r = new Rectangle2D(Math.round(rect.getLeft()),
                                        Math.round(rect.getBottom()),
                                        Math.round(rect.getRight()),
                                        Math.round(rect.getTop()));

        return r;
    }

    /**
     * 判断是否包含点
     * @param pt Point2D
     * @return boolean
     */
    public boolean contains(Point2D pt) {
        return (pt.getX() >= left) && (pt.getX() <= right) && (pt.getY() <= top) &&
                (pt.getY() >= bottom);
    }

    /**
     * 判断是否包含矩形
     * @param rect Rectangle2D
     * @return boolean
     */
//    public boolean contains(Rectangle2D rect) {
//        return (rect.left > left || Toolkit.isZero(rect.left - left)) &&
//                (rect.top < top || Toolkit.isZero(rect.top - top)) &&
//                (rect.right < right || Toolkit.isZero(rect.right - right)) &&
//                (rect.bottom > bottom || Toolkit.isZero(rect.bottom - bottom));
//    }

    /**
     * 判断是否包含点，点用X、Y坐标表示
     * @param x double
     * @param y double
     * @return boolean
     */
    public boolean contains(double x, double y) {
        Point2D point = new Point2D(x, y);
        return contains(point);
    }


    /**
     * 重写基类的equals方法
     * @param obj Object
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Rectangle2D)) {
            return false;
        }

//        Rectangle2D rect = (Rectangle2D) obj;
//        if (Toolkit.isZero(left - rect.getLeft()) &&
//            Toolkit.isZero(right - rect.getRight()) &&
//            Toolkit.isZero(top - rect.getTop()) &&
//            Toolkit.isZero(bottom - rect.getBottom())) {
//            return true;
//        }
        else {
            return false;
        }
    }

    public boolean equals(Rectangle2D rect) {
        if (rect == null) {
            return false;
        }
//        if (Toolkit.isZero(left - rect.getLeft()) &&
//            Toolkit.isZero(right - rect.getRight()) &&
//            Toolkit.isZero(top - rect.getTop()) &&
//            Toolkit.isZero(bottom - rect.getBottom())) {
//            return true;
//        }
        else {
            return false;
        }
    }


    /**
     * 因为重写了基类的equals方法，因此要重写hashcode，否则在key-value中contains方法
     * 会发生错误
     * @return int
     */
    public int hashCode() {
        long bits = Double.doubleToLongBits(getLeft());
        bits += Double.doubleToLongBits(getBottom()) * 37;
        bits += Double.doubleToLongBits(getRight()) * 43;
        bits += Double.doubleToLongBits(getTop()) * 47;
        return (((int) bits) ^ ((int) (bits >> 32)));

    }

    /**
     * 膨胀
     * @param x double
     * @param y double
     */
    public void inflate(double x, double y) {
        left -= x;
        right += x;
        top += y;
        bottom -= y;
    }

    /**
     * 取相交矩形
     * 会改变当前矩形
     * @param rect Rectangle2D
     */
    public void intersect(Rectangle2D rect) {
        if (hasIntersection(rect)) {
            left = Math.max(left, rect.left);
            top = Math.min(top, rect.top);
            right = Math.min(right, rect.right);
            bottom = Math.max(bottom, rect.bottom);

        } else {
            setEmpty();
        }
    }

    /**
     * 判断是否与目标矩形相交
     * @param rect Rectangle2D
     * @return boolean
     * modified:IntersectsWith改名为IsIntersect by liyj 07.5.17
     * modified:IsIntersect改名为hasIntersection by liyj 07.5.17
     */
    public boolean hasIntersection(Rectangle2D rect) {
        return right >= rect.left && left <= rect.right && top >= rect.bottom &&
                bottom <= rect.top;
    }

    /**
     * 平移矩形
     * @param dx double
     * @param dy double
     */
    public void offset(double dx, double dy) {
        left += dx;
        right += dx;
        top += dy;
        bottom += dy;
    }

    /**
     * 获取矩形的字符串表示
     * @return String
     */
    public String toString() {
        return "Left=" + left + ",Bottom=" + bottom + ",Right=" + right +
                ",Top=" + top;
    }

    /**
     * 合并矩形
     * @param rect Rectangle2D
     */
    public void union(Rectangle2D rect) {
        left = Math.min(left, rect.getLeft());
        top = Math.max(top, rect.getTop());
        right = Math.max(right, rect.getRight());
        bottom = Math.min(bottom, rect.getBottom());
    }

    private void setEmpty() {
//        left = Toolkit.DBL_MIN_VALUE;
//        right = Toolkit.DBL_MIN_VALUE;
//        top = Toolkit.DBL_MIN_VALUE;
//        bottom = Toolkit.DBL_MIN_VALUE;
    }


    public String toJson(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("{");

    	sb.append(" \"leftBottom\" :{" +
    			" \"x\" : " + this.left +"," +
    			" \"y\" : " + this.bottom + "}," );

    	sb.append(" \"rightTop\" : { "+
    			" \"x\" : " + this.right +"," +
    			" \"y\" : " + this.top + "}" );

    	sb.append("}");
    	return sb.toString();
    }

}

//    /**
//     * 合并矩形
//     * @param a Rectangle2D
//     * @param b Rectangle2D
//     * @return Rectangle2D
//     */
//    public static Rectangle2D union(Rectangle2D a, Rectangle2D b) {
//        Rectangle2D rect = new Rectangle2D(a);
//        rect.union(b);
//        return rect;
//    }
//    /**
//     * 计算相交矩形
//     * 不改变现有矩形
//     * @param a Rectangle2D
//     * @param b Rectangle2D
//     * @return Rectangle2D
//     */
//    public static Rectangle2D intersect(Rectangle2D a, Rectangle2D b) {
//        Rectangle2D rect = new Rectangle2D(a);
//        rect.intersect(b);
//        return rect;
//    }
//    /**
//     * 膨胀
//     * @param rect Rectangle2D
//     * @param x double
//     * @param y double
//     * @return Rectangle2D
//     */
//    public static Rectangle2D inflate(Rectangle2D rect, double x, double y) {
//        Rectangle2D r = new Rectangle2D(rect);
//        r.inflate(x, y);
//        return r;
//    }
