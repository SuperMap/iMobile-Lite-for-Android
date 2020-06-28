package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

import com.supermap.services.components.commontypes.Rectangle2D;

/**
 * <p>
 * 海图查询参数类。
 * </p>
 * <p>
 * 该类用于设置海图查询时的相关参数。海图查询分为海图属性查询和海图范围查询两类，通过属性queryMode指定查询模式。必设属性有：queryMode、chartLayerNames、chartQueryFilterParameters。当进行海图范围查询时，必设属性还包括bounds。<br>
 * 注意：在海图查询中，对单个图层查询物标的点、线或面，会返回对应于物标的点线面的三个结果记录集Recordset。所以在多图层查询中（即设置chartLayerNames数组元素个数为多个，对应于每个图层设置一个过滤参数ChartQueryFilterParameter进行查询，图层数组chartLayerNames与结果数组QueryResult.recordsets不再是一一对应的关系。这种情况下，用户需要通过结果数据集名RecordSet.datasetName来确定对应的查询图层。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class ChartQueryParameters implements Serializable {
    private static final long serialVersionUID = -3247251337184830660L;
    /**
     * <p>
     * 海图查询模式，有两种：海图属性查询（ChartAttributeQuery）和海图范围查询（ChartBoundsQuery）。
     * </p>
     */
    public ChartQueryMode queryMode = ChartQueryMode.ChartAttributeQuery;
    /**
     * <p>
     * 指定的查询范围，当查询模式为 ChartBoundsQuery 时必选。
     * </p>
     */
    public Rectangle2D bounds;

    /**
     * <p>
     * 查询的海图图层的名称。
     * </p>
     */
    public String[] chartLayerNames;
    /**
     * <p>
     * 海图查询过滤参数。包括：物标代码、物标可应用对象的选择（是否查询点、线或面）、属性字段过滤条件。
     * </p>
     */
    public ChartQueryFilterParameter[] chartQueryFilterParameters;
    /**
     * <p>
     * 是否立即返回新创建资源的表述还是返回新资源的 URI。
     * 如果为 true，则直接返回新创建资源，即查询结果的表述。如果为 false，则返回的是查询结果资源的 URI。默认为 true。
     * 获取或设置是返回查询结果记录集 recordsets，还是返回查询结果的资源 resourceInfo。默认为 true，表示返回 recordsets。Recordsets 和 ResourceInfo 都存储在查询结果类 QueryResult 中。
     * 当 ReturnContent = true，表示返回查询记录集，这时查询结果存储在 QueryResult.quertyResultInfo 中，而 QueryResult.resourceInfo 为空；
     * 当 ReturnContent = false 时，表示返回查询结果资源，这时查询结果存储在 QueryResult.resourceInfo 中，而 QueryResult.quertyResultInfo 为空。
     * </p>
     */
    public boolean returnContent = true;
    /**
     * <p>
     * 查询起始记录位置，默认为0
     * </p>
     */
    public int startRecord = 0;
    /**
     * <p>
     * 期望查询结果返回的记录数，该值大于0。
     * </p>
     */
    public int expectCount = 1;

    public ChartQueryParameters() {
    }
}
