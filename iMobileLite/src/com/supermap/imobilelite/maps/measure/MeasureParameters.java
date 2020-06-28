package com.supermap.imobilelite.maps.measure;

import com.supermap.services.components.commontypes.Point2D;
import com.supermap.services.components.commontypes.PrjCoordSys;
import com.supermap.services.components.commontypes.Unit;

/**
 * <p>
 * 量算参数类。
 * </p>
 * 客户端要量算的地物间的距离或某个区域的面积是一个Point2D[]类型的几何对象（线或者面），它将与指定的量算单位一起作为量算参数传递到服务端。最终服务端将以指定单位返回得到的距离或面积。
 */
public class MeasureParameters {
    
    /**
     * 获取或设置客户端要量算的几何对象（线或面），必设属性。
     */
    public Point2D[]  point2Ds;
    
    /**
     * 获取或设置客户端的量算单位。默认单位：米，即量算结果以米为单位。
     */
    public Unit unit =Unit.METER;
    
    
    /**
     * <p>
     * 请求的地图的坐标参考系统。
     * </p>
     * 当此参数设置的坐标系统不同于地图的原有坐标系统时， 系统会进行投影转换，并返回转换后的，即目标投影坐标系下的地图上的距离。 
     * 参数使用时，需按照PrjCoordSys中的字段结构来构建，同时也支持通过只传递epsgCode的方式传入坐标参考系，如：  prjCoordSys={"epsgCode":3857}。                            
     */
    public PrjCoordSys prjCoordSys;
    
 
    /**
     *  <p>
     * 构造函数。
     * </p>
     */
    public MeasureParameters(){
        super();
    }
    
    

}
