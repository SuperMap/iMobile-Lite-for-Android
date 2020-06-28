package com.supermap.imobilelite.mapsamples.util;

import com.supermap.imobilelite.commons.EventStatus;
import com.supermap.imobilelite.maps.measure.MeasureMode;
import com.supermap.imobilelite.maps.measure.MeasureParameters;
import com.supermap.imobilelite.maps.measure.MeasureResult;
import com.supermap.imobilelite.maps.measure.MeasureService;
import com.supermap.imobilelite.maps.measure.MeasureService.MeasureEventListener;
import com.supermap.services.components.commontypes.Point2D;
import com.supermap.services.components.commontypes.Unit;

/**
 * <p>
 * 地图量算工具类
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class MeasureUtil {

    /**
     * <p>
     * 地图距离量算
     * </p>
     * @param measureUrl 地图服务地址
     * @param pts 量算点集合
     * @return
     */
    public static MeasureResult distanceMeasure(String measureUrl, Point2D[] pts) {
        // 构造查询参数
        MeasureParameters parameters = new MeasureParameters();
        parameters.point2Ds = pts;
        MeasureService service = new MeasureService(measureUrl);
        MyMeasureEventListener listener = new MyMeasureEventListener();
        service.process(parameters, listener, MeasureMode.DISTANCE);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listener.getResult();

    }

    /**
     * <p>
     * 地图面积量算
     * </p>
     * @param measureUrl 地图服务地址
     * @param pts 量算点集合
     * @return
     */
    public static MeasureResult areaMeasure(String measureUrl, Point2D[] pts) {
        // 构造查询参数
        MeasureParameters parameters = new MeasureParameters();
        parameters.point2Ds = pts;
        parameters.unit = Unit.KILOMETER;// 使用平方米为单位，默认是米
        MeasureService service = new MeasureService(measureUrl);
        MyMeasureEventListener listener = new MyMeasureEventListener();
        service.process(parameters, listener, MeasureMode.AREA);
        try {
            listener.waitUntilProcessed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listener.getResult();

    }

    /**
     * <p>
     * 监控器类
     * </p>
     * @author ${Author}
     * @version ${Version}
     * 
     */
    static class MyMeasureEventListener extends MeasureEventListener {
        private MeasureResult result;

        @Override
        public void onMeasureStatusChanged(Object sourceObject, EventStatus status) {
            // 分析结果
            if (sourceObject instanceof MeasureResult) {
                result = (MeasureResult) sourceObject;
            }
        }

        public MeasureResult getResult() {
            return result;
        }
    }

}
