package com.supermap.imobilelite.maps.query;

/**
 * <p>
 * 查询结果类型枚举类。
 * </p>
 * <p>
 * 该类描述查询结果返回类型，包括只返回属性、只返回几何实体以及返回属性和几何实体。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public enum QueryOption {

    /**
     * <p>
     * 属性类型，只返回地物的属性信息。
     * </p>
     */
    ATTRIBUTE,

    /**
     * <p>
     * 空间几何类型，只返回地物的空间几何信息。
     * </p>
     */
    GEOMETRY,

    /**
     * <p>
     * 属性和空间几何类型，返回地物的属性信息和空间几何信息。
     * </p>
     */
    ATTRIBUTEANDGEOMETRY;
}