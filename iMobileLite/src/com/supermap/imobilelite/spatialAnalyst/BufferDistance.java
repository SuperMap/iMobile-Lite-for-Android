package com.supermap.imobilelite.spatialAnalyst;

import java.io.Serializable;

/**
 * <p>
 * 缓冲区分析的缓冲距离类。
 * </p>
 * <p> 
 * 通过该类可以设置缓冲区分析的缓冲距离，距离可以是数值也可以是数值型的字段表达式。
 * </p>
 * @author ${Author}
 * @version ${Version}
 *
 */
public class BufferDistance implements Serializable {
    private static final long serialVersionUID = 4808894028601825350L;

    /**
     * <p>
     * 以数值型的字段表达式作为缓冲区分析的距离值。 
     * </p>
     */
    public String expression;

    /**
     * <p>
     * 以数值作为缓冲区分析的距离值。默认为 100，单位：米。 
     * </p>
     */
    public double value = 100.0;

    /**
     * <p>
     * 构造函数。
     * </p>
     */
    public BufferDistance() {
        super();
    }

}
