package com.supermap.imobilelite.commons;

/**
 * <p>
 * SuperMap的安全证书类，其中包括token等安全验证信息。 
 * </p>
 * <p>
 * 当服务器开启安全认证之后，所有有权限的用户可以通过自己的用户名以及密码在服务列表界面申请自己唯一的安全锁，通过安全锁信息创建一个安全证书对象，将其存放于Credential的静态字段CREDENTIAL里，
 * 当用户发送服务请求时，会自动通过CREDENTIAL字段获取相应的信息组合之后访问服务器，如果不设置有效的安全证书将无法访问受安全限制的服务。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * @since 7.1.0
 * 
 
 */
public class Credential {
    /**
     * <p>
     * 用于存储安全证书对象，如果用户的服务器开启了安全认证，用户必须设置此属性才能访问相关的服务。目前支持的服务包括：地图服务、数据服务、专题图、量算、查询、公交换乘、空间分析、网络分析。
     * </p>
     * <p>
     * 需要使用用户名和密码在：”http://localhost:8090/iserver/services/security/tokens”下申请value
     * 获得形如：”2OMwGmcNlrP2ixqv1Mk4BuQMybOGfLOrljruX6VcYMDQKc58Sl9nMHsqQaqeBx44jRvKSjkmpZKK1L596y7skQ..”的value
     * </p>
     * @since 7.1.0
     */
    public static volatile Credential CREDENTIAL;
    /**
     * <p>
     * 验证信息前缀，name=value部分的name部分，默认为“token”。
     * </p>
     * @since 7.1.0
     */
    public String name = "token";
    /**
     * <p>
     * 访问受安全限制的服务时用于通过安全认证的验证信息。
     * </p>
     * @since 7.1.0
     */
    public String value;
    
    /**
     * <p>
     * 构造函数。
     * </p>
     * @param name 验证信息前缀
     * @param value 验证信息
     */
    public Credential(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }
    
    /**
     * <p>
     * 构造函数。
     * </p>
     * @param value 验证信息
     */
    public Credential(String value) {
        this("token", value);
    }
    
    public void destroy(){
        CREDENTIAL = null;
    }
}
