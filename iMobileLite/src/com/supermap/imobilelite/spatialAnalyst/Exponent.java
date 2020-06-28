package com.supermap.imobilelite.spatialAnalyst;

/**
 * <p>
 * 泛克吕金（UniversalKriging）插值时样点数据中趋势面方程的阶数枚举类。
 * </p>
 * <p>
 * 该类定义了泛克吕金（UniversalKriging）插值时样点数据中趋势面方程的阶数的类型常量。样点数据集中样点之间固有的某种趋势，可以通过函数或者多项式的拟合呈现。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum Exponent {

    /**
     * <p>
     * 阶数为1。
     * </p>
     */
    EXP1,

    /**
     * <p>
     * 阶数为2。
     * </p>
     */
    EXP2;

}
