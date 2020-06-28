package com.supermap.imobilelite.data;


/**
 * <p>
 * imobile移植类
 * </p>
 */
 
public final class FillGradientMode extends Enum {
    private FillGradientMode(int value, int ugcValue) {
        super(value, ugcValue);
    }

    public static final FillGradientMode NONE = new FillGradientMode(0, 0);
    public static final FillGradientMode LINEAR = new FillGradientMode(1, 1);
    public static final FillGradientMode RADIAL = new FillGradientMode(2, 2);
    public static final FillGradientMode CONICAL = new FillGradientMode(3, 3);
    public static final FillGradientMode SQUARE = new FillGradientMode(4, 4);
}
