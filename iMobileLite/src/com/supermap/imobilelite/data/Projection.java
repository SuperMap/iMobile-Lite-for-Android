package com.supermap.imobilelite.data;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: SuperMap GIS Technologies Inc.</p>
 *
 * @作者 not attributable
 * @version 2.0
 */
public class Projection extends InternalHandleDisposable {

    public Projection() {
        long handle = ProjectionNative.jni_New();
        this.setHandle(handle, true);
    }

    public Projection(ProjectionType type) {
        if (type == null) {
            String message = InternalResource.loadString("type",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        int ugcValue = Enum.internalGetUGCValue(type);
        long handle = ProjectionNative.jni_New2(ugcValue);
        this.setHandle(handle, true);

    }

    public Projection(Projection projection) {
        if (projection == null || projection.getHandle() == 0) {
            String message = InternalResource.loadString("projection",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        long handle = ProjectionNative.jni_Clone(projection.getHandle());
        this.setHandle(handle, true);
    }
    
    Projection(long handle, boolean disposable) {
        this.setHandle(handle, disposable);
    }

    public Projection clone() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "clone()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return new Projection(this);
    }

    public void dispose() {
        if (!this.getIsDisposable()) {
            String message = InternalResource.loadString("dispose()",
                    InternalResource.HandleUndisposableObject,
                    InternalResource.BundleName);
            throw new UnsupportedOperationException(message);
        } else if (this.getHandle() != 0) {
            ProjectionNative.jni_Delete(getHandle());
            this.setHandle(0);
            clearHandle();
        }
    }

    public String getName() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getName()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return ProjectionNative.jni_GetName(getHandle());
    }

    public ProjectionType getType() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "getType()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        int ugcValue = ProjectionNative.jni_GetType(getHandle());
        return (ProjectionType) Enum.parseUGCValue(
                ProjectionType.class,
                ugcValue);
    }

    public void setType(ProjectionType value) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "setType(ProjectionType value)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        if (value == null) {
            String message = InternalResource.loadString("value",
                    InternalResource.GlobalArgumentNull,
                    InternalResource.BundleName);
            throw new IllegalArgumentException(message);
        }
        int ugcValue = Enum.internalGetUGCValue(value);
        ProjectionNative.jni_SetType(getHandle(), ugcValue);
    }

    public boolean fromXML(String xml) {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "fromXML(String xml)",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }

        boolean result = false;
        if(xml != null && xml.trim().length() != 0){
            result = ProjectionNative.jni_FromXML(getHandle(), xml);
        }
        return result;
    }

    public String toXML() {
        if (this.getHandle() == 0) {
            String message = InternalResource.loadString(
                    "toXML()",
                    InternalResource.HandleObjectHasBeenDisposed,
                    InternalResource.BundleName);
            throw new IllegalStateException(message);
        }
        return ProjectionNative.jni_ToXML(getHandle());
    }
}
