package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

/**
 * <p>
 * 数据源连接信息类。
 * </p>
 * <p>
 * 该类包括了进行数据源连接的所有信息，如所要连接的服务器名称、数据库名称、用户名以及密码等。当保存为工作空间时,工作空间中的数据源的连接信息都将存储到工作空间文件中。对于不同类型的数据源，其连接信息有所区别。所以在使用该类所包含的成员时，请注意该成员所适用的 数据源类型。对于从数据源对象中返回的数据连接信息对象，只有connect方法可以被修改，其他内容是不可以被修改的。对于用户创建的数据源连接信息对象，其内容都可以修改。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class DatasourceConnectionInfo implements Serializable {
    private static final long serialVersionUID = 963882527928076282L;
    /**
     * <p>
     * 获取或设置数据源别名。
     * </p>
     */
    public String alias;
    /**
     * <p>
     * 获取或设置数据源连接的数据库名。
     * </p>
     */
    public String dataBase;
    /**
     * <p>
     * 获取或设置使用 ODBC(Open Database Connectivity，开放数据库互连)的数据库的驱动程序名。
     * 其中，对于SQL Server 数据库与 iServer 发布的 WMTS 服务，此为必设参数。对于SQL Server 数据库，它使用 ODBC 连接，所设置的驱动程序名为 "SQL Server" 或 "SQL Native Client"；
     * 对于 iServer 发布的 WMTS 服务，设置的驱动名称为 "WMTS"。
     * </p>
     */
    public String driver;
    /**
     * <p>
     * 获取或设置数据库服务器名、文件名或服务地址。 对于不同数据库服务器名、文件名或服务地址介绍如下：
     * 1.对于SDB和UDB文件，为其文件的绝对路径。注意：当绝对路径的长度超过UTF-8编码格式的260字节长度，该数据源无法打开。
     * 2.对于Oracle数据库，其服务器名为其TNS服务名称。
     * 3.对于SQL Server数据库，其服务器名为其系统的DSN(Database Source Name)名称。
     * 4.对于PostgreSQL数据库，其服务器名为“IP:端口号”，默认的端口号是 5432。
     * 5.对于DB2数据库，已经进行了编目，所以不需要进行服务器的设置。
     * 6.对于 Kingbase 数据库，其服务器名为其 IP 地址。
     * 7.对于GoogleMaps数据源，其服务器地址，默认设置为“http://maps.google.com”，且不可更改。
     * 8.对于SuperMapCould数据源，为其服务地址。
     * 9.对于MAPWORLD数据源，为其服务地址，默认设置为“http://www.tianditu.cn”，且不可更改。
     * 10.对于OGC和REST数据源，为其服务地址。
     * </p>
     */
    public String server;
    /**
     * <p>
     * 获取或设置登录数据库的用户名。
     * </p>
     */
    public String user;
    /**
     * <p>
     * 获取或设置登录数据源连接的数据库或文件的密码。
     * </p>
     */
    public String password;
    /**
     * <p>
     * 获取或设置数据源连接的引擎类型。
     * </p>
     */
    public EngineType engineType;
    /**
     * <p>
     * 获取或设置数据源是否自动连接数据。
     * </p>
     */
    public boolean connect;
    /**
     * <p>
     * 获取或设置是否以独占方式打开数据源。
     * </p>
     */
    public boolean exclusive;
    /**
     * <p>
     * 获取或设置是否把数据库中的其他非 SuperMap 数据表作为 LinkTable 打开。
     * </p>
     */
    public boolean openLinkTable;
    /**
     * <p>
     * 获取或设置是否以只读方式打开数据源。
     * </p>
     */
    public boolean readOnly;
    
    public DatasourceConnectionInfo() {
    }

    public DatasourceConnectionInfo(DatasourceConnectionInfo datasourceConnectionInfo) {
        if (datasourceConnectionInfo != null) {
            this.alias = datasourceConnectionInfo.alias;
            this.connect = datasourceConnectionInfo.connect;
            this.dataBase = datasourceConnectionInfo.dataBase;
            this.driver = datasourceConnectionInfo.driver;
            if (datasourceConnectionInfo.engineType != null) {
                this.engineType = datasourceConnectionInfo.engineType;
            }
            this.exclusive = datasourceConnectionInfo.exclusive;
            this.openLinkTable = datasourceConnectionInfo.openLinkTable;
            this.password = datasourceConnectionInfo.password;
            this.readOnly = datasourceConnectionInfo.readOnly;
            this.server = datasourceConnectionInfo.server;
            this.user = datasourceConnectionInfo.user;
        }
    }

    public static String toJson(DatasourceConnectionInfo connectionInfon) {
        String json = "";
        if ("".equals(connectionInfon.alias))
            json += "\"alias\":" + "\"" + connectionInfon.alias + "\",";
        if ("".equals(connectionInfon.dataBase))
            json += "\"dataBase\":" + "\"" + connectionInfon.dataBase + "\",";
        if ("".equals(connectionInfon.driver))
            json += "\"driver\":" + "\"" + connectionInfon.driver + "\",";
        if ("".equals(connectionInfon.server))
            json += "\"server\":" + "\"" + connectionInfon.server + "\",";
        if ("".equals(connectionInfon.user))
            json += "\"user\":" + "\"" + connectionInfon.user + "\",";
        if ("".equals(connectionInfon.password))
            json += "\"password\":" + "\"" + connectionInfon.password + "\",";
        if (connectionInfon.engineType != null)
            json += "\"engineType\":" + "\"" + connectionInfon.engineType + "\",";
        json += "\"connect\":" + connectionInfon.connect + ",";
        json += "\"exclusive\":" + connectionInfon.exclusive + ",";
        json += "\"openLinkTable\":" + connectionInfon.openLinkTable + ",";
        json += "\"readOnly\":" + connectionInfon.readOnly;

        if (json.length() > 0)
            json = "{" + json + "}";
        return json;
    }
}