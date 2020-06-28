package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

/**
 * <p>
 * 查询结果类。
 * </p>
 * <p>
 * 该类用于存储服务端返回的查询结果，其中包含了查询结果记录集（Recordset）或查询结果资源（ResourceInfo）等相关信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class QueryResult implements Serializable {
    private static final long serialVersionUID = -6619417984953236683L;
    // public int totalCount;
    // public int currentCount;
    // public String customResponse;
    // public Recordset []recordsets;
    /**
     * <p>
     * 获取查询结果记录集（Recordset）数组等结果信息对象
     * </p>
     */
    public QuertyResultInfo quertyResultInfo;
    /**
     * <p>
     * 获取查询结果资源 ResourceInfo。
     * </p>
     */
    public ResourceInfo resourceInfo;

}
