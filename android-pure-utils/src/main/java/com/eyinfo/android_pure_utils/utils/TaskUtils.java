package com.eyinfo.android_pure_utils.utils;

import com.eyinfo.android_pure_utils.executor.CustomThreadPool;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TaskUtils {
    private static volatile TaskUtils instance;

    public static TaskUtils getInstance() {
        if (instance == null) {
            synchronized (TaskUtils.class) {
                if (instance == null) {
                    instance = new TaskUtils();
                }
            }
        }
        return instance;
    }

    private CustomThreadPool customThreadPool = new CustomThreadPool();
    private Deque<Runnable> deque = new ConcurrentLinkedDeque<>();
    private boolean isQueuing = false;

    public <R extends Runnable> void execute(R runnable) {
        deque.add(runnable);
        operation();
    }

    private void operation() {
        if (isQueuing || deque.isEmpty()) {
            return;
        }
        Runnable runnable = deque.poll();
        if (runnable == null) {
            isQueuing = false;
            return;
        }
        isQueuing = true;
        customThreadPool.execute(runnable);
        isQueuing = false;
        operation();
    }

    public void cancel() {
        customThreadPool.shutdown();
    }
}
