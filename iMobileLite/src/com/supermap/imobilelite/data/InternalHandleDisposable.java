package com.supermap.imobilelite.data;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public abstract class InternalHandleDisposable extends InternalHandle implements
        IDisposable {
    protected InternalHandleDisposable() {
    }

    
    protected void setHandle(long handle) {
        if (handle != 0) {
            String message = InternalResource.loadString("setHandle()",
                    InternalResource.HandleDisposableCantCreate,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        }

        super.setHandle(0);
    }

    
    protected void setHandle(long handle, boolean disposable) {
        if (this.getIsDisposable() && !(getHandle() == 0)) {
            String message = InternalResource.loadString("setHandle()",
                    InternalResource.HandleOriginalObjectHasNotBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        this.setIsDisposable(disposable);
        super.setHandle(handle);
    }

 
    protected boolean getIsDisposable() {
        return this.m_disposable;
    }

    protected void finalize() {
        if (this.getIsDisposable() && !(getHandle() == 0)) {
        }
    }

 
    protected void setIsDisposable(boolean disposable) {
        this.m_disposable = disposable;
    }


    protected static void setIsDisposable(InternalHandleDisposable obj,
                                          boolean disposable) {
        obj.setIsDisposable(disposable);
    }

    private boolean m_disposable = true;
}
