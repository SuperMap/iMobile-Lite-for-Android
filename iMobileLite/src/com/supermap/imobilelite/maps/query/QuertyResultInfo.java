package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

/**
 * <p>
 * 查询结果信息类。
 * </p>
 * <p>
 * 该类用于存储服务端返回的查询结果，其中包含了查询结果记录集（Recordset）等相关信息。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class QuertyResultInfo implements Serializable {
    private static final long serialVersionUID = 551408590890327941L;

    /**
     * <p>
     * 获取符合查询条件的记录总数。
     * </p>
     */
    public int totalCount;
    /**
     * <p>
     * 获取当次查询返回的记录数
     * </p>
     */
    public int currentCount;
    /**
     * <p>
     * 获取自定义操作处理的结果。
     * </p>
     */
    public String customResponse;
    /**
     * <p>
     * 获取查询结果记录集（Recordset）数组.
     * </p>
     */
    public Recordset[] recordsets;
}
