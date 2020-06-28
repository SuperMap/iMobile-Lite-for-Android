package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */
 
public abstract class InternalHandle {
    static {
    }

    protected InternalHandle() {

    }

    public long getHandle() {
        return m_handle;
    }

    protected void setHandle(long handle) {
        this.m_handle = handle;
    }

    public static long getHandle(InternalHandle obj) {
        return obj.getHandle();
    }

    protected static void setHandle(InternalHandle obj, long handle) {
        obj.setHandle(handle);
    }

  
    protected void clearHandle(){
        setHandle(0);
    }

    private long m_handle;

}
