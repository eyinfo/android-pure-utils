package com.eyinfo.android_pure_utils.executor;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2015-9-8 上午8:51:43
 * Description: 循环执行器(类似于定时器)
 * Modifier:
 * ModifyContent:
 */
public class CycleExecutor {

    private ScheduledThreadPoolExecutor sc = null;
    /**
     * 每次执行时间间隔 (以毫秒为单位)
     */
    private long period = 1000;

    private Object params;
    private boolean running = false;

    /**
     * param 设置每次执行时间间隔 (以毫秒为单位)
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * param 设置params
     */
    public void setParams(Object params) {
        this.params = params;
    }

    protected void onDoingWithMainLooper(Object params) {

    }

    protected void onDoingWithThread(Object params) {

    }

    public void start() {
        if (sc != null) {
            if (!sc.isShutdown()) {
                running = false;
                sc.shutdown();
            }
        }
        running = true;
        sc = new ScheduledThreadPoolExecutor(1);
        sc.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                running = true;
                onDoingWithThread(params);
                mhandler.obtainMessage().sendToTarget();
            }
        }, 0, period, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (sc != null && !sc.isShutdown()) {
            running = false;
            sc.shutdown();
        }
    }

    private Handler mhandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            onDoingWithMainLooper(params);
        }
    };

    //是否运行中
    public boolean isRunning() {
        if (sc != null && !sc.isShutdown()) {
            running = true;
        }
        return running;
    }
}
