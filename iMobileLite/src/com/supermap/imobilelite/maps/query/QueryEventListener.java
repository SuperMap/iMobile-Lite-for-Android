package com.supermap.imobilelite.maps.query;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import com.supermap.imobilelite.commons.EventStatus;

/**
 * <p>
 * 处理查询结果的监听器抽象类。
 * </p>
 * <p>
 * 提供了等待 监听器执行完毕的接口。
 * </p>
 * @author ${Author}
 * @version ${Version}
 * 
 */
public abstract class QueryEventListener {
    private AtomicBoolean processed = new AtomicBoolean(false);
    private Future<?> future;

    /**
     * <p>
     * 用户必须自定义实现处理查询结果的接口。
     * </p>
     * @param sourceObject 查询结果。
     * @param status 查询结果的状态。
     */
    public abstract void onQueryStatusChanged(Object sourceObject, EventStatus status);

    private boolean isProcessed() {
        return processed.get();
    }

    /**
     * <p>
     * 设置异步操作处理。
     * </p>
     * @param future Future对象。
     */
    protected void setProcessFuture(Future<?> future) {
        this.future = future;
    }

    /**
     * <p>
     * 等待监听器执行完毕。
     * </p>
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void waitUntilProcessed() throws InterruptedException, ExecutionException {
        if (future == null) {
            return;
        }
        future.get();
    }
}