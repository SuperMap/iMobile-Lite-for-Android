package com.supermap.imobilelite.maps;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @author zhangjinan
 * @version 2.0
 */
public class GLCacheFile {
    private long mHandle = 0;
    private boolean mdisposable = false;

    private void setHandle(long handle,boolean disposable){
        mHandle = handle;
        mdisposable=disposable;
    }
    private long getHandle(){
        return  mHandle ;
    }

    private boolean getIsDisposable(){
        return mdisposable;
    }

    private void clearHandle(){
        mHandle=0;
        mdisposable=false;
    }

    public GLCacheFile() {
        long handle = GLCacheFileNative.jni_New();
        this.setHandle(handle, true);
    }

    GLCacheFile(long handle, boolean disposable) {
        this.setHandle(handle, disposable);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = "cant disposable";
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
        	GLCacheFileNative.jni_Delete(getHandle());
            this.setHandle(0,false);
            clearHandle();
        }
    }


    public void fromCacheFile(String path){
        if (this.getHandle() == 0) {
            String message = "no handle";
            throw new IllegalStateException(message);
        }
        GLCacheFileNative.jni_FromConfig(getHandle(), path);
    }

    public String getTilePath(int level, int row, int col) {
        if (this.getHandle() == 0) {
            String message = "no handle";
            throw new IllegalStateException(message);
        }
        return GLCacheFileNative.jni_GetTilePath(getHandle(), level, row, col);
    }

    public String getMVTTilePath(int level, int row, int col) {
        if (this.getHandle() == 0) {
            String message = "no handle";
            throw new IllegalStateException(message);
        }
        return GLCacheFileNative.jni_GetMVTTilePath(getHandle(), level, row, col);
    }
}
