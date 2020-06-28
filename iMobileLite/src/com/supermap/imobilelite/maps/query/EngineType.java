package com.supermap.imobilelite.maps.query;

/**
 * <p>
 * 数据源引擎类型枚举类。 
 * </p>
 * <p>
 * SuperMap SDX+ 是 SuperMap 的空间引擎技术，它提供了一种通用的访问机制（或模式）来访问存储在不同引擎里的数据。引擎类型包括数据库引擎、文件引擎和 Web 引擎。<br>
 * 1. 数据库引擎类型主要包括 Oracle 引擎（ORACLEPLUS）、SQL Server 引擎（SQLPLUS）;<br>
 * 2. 文件引擎主要包括 SDB 引擎（SDBPLUS）、UDB 引擎（UDB）、影像只读引擎（IMAGEPLUGINS）;<br>
 * 3. Web 引擎主要有 OGC 引擎（OGC）。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public enum EngineType {

    /**
     * <p>
     * 影像只读引擎类型，文件引擎，针对通用影像格式如 BMP，JPG，TIFF 以及超图自定义影像格式 SIT 等。
     * </p>
     */
    IMAGEPLUGINS,

    /**
     * <p>
     * OGC 引擎类型，针对于 Web 数据源，Web 引擎，目前支持的类型有 WMS，WFS，WCS。
     * </p>
     */
    OGC,

    /**
     * <p>
     * Oracle 引擎类型，针对 Oracle 数据源，数据库引擎。
     * </p>
     */
    ORACLEPLUS,

    /**
     * <p>
     * SDB 引擎类型，文件引擎，即 SDB 数据源。
     * </p>
     */
    SDBPLUS,

    /**
     * <p>
     * SQL Server 引擎类型，针对 SQL Server 数据源，数据库引擎。
     * </p>
     */
    SQLPLUS,

    /**
     * <p>
     *  UDB 引擎类型，文件引擎。
     * </p>
     */
    UDB
}