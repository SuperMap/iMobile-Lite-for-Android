package com.supermap.imobilelite.maps.measure;

import com.supermap.services.components.commontypes.Unit;

/**
 * <p>
 * 量算结果类.
 * </P>
 * 该类用于存储从服务器端返回的距离/面积量算结果。
 *
 */
public class MeasureResult {
    
    /**
     * 获取面积量算结果。
     */
    public double area;
    
    /**
     * 获取距离量算结果。
     */
    public double distance;
    
    /**
     * 获取量算结果单位。
     */
    public Unit unit;
    
    /**
     * <p>
     * 构造函数。
     * </p> 
     */
    public MeasureResult(){
        super();
    }

}
