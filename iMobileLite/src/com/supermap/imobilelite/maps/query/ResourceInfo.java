package com.supermap.imobilelite.maps.query;

import java.io.Serializable;

import com.supermap.services.rest.PostResultType;

/**
 * <p>
 * 结果资源信息类。
 * </p>
 * <p>
 * 所有对象在服务器都是以资源的形式存储，每个资源会有一个唯一标识的 ID，同时还有一个该资源在服务器存储的位置，即 URL。用户可以设置REST接口的所有临时资源的存活时间，默认为7天。详情请见SuperMap iServer 7C帮助文档（iServer REST API > 临时资源的生命周期）。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public class ResourceInfo implements Serializable {
    private static final long serialVersionUID = -5693584101986057118L;
    /**
     * <p>
     * 获取或设置资源创建是否成功。
     * </p>
     */
    public Boolean succeed;
    /**
     * <p>
     * 获取或设置资源的 URL。
     * </p>
     */
    public String newResourceLocation;
    /**
     * <p>
     * 获取或设置资源的 ID 。
     * </p>
     */
    public String newResourceID;
    /**
     * <p>
     * POST 请求的结果类型。说明 POST 请求对目标资源的影响，即处理结果是什么样的。
     * </p>
     */
    public PostResultType postResultType;
}