package com.eyinfo.android_pure_utils.logs;

import android.content.Context;

import com.eyinfo.android_pure_utils.HandlerManager;
import com.eyinfo.android_pure_utils.events.RunnableParamsN;
import com.eyinfo.android_pure_utils.executor.CycleExecutor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.TimeoutException;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2015-7-1 下午6:53:18
 * Description: 捕捉异常处理
 * Modifier:
 * ModifyContent:
 */
public abstract class CrashHandler implements UncaughtExceptionHandler {

    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler additionHandler;
    //异常拦截类标识,用于区别在此异常类初始之后设置的第三方异常
    private String uncaughtTag = "e14ab7d12cde432fb81a9f92577976af";
    //检测默认handler次数
    private int defaultHandlerCount;

    //日志拦截监听
    protected abstract void onLogIntercept(Throwable throwable);

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     * <p>
     * param ctx
     */
    public void init(Context ctx) {
        defaultHandlerCount = 0;
        //如果mDefaultHandler不为null表示其它框架异常拦截在此之前注册
        additionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        //第三方异常类在之后初始化
        if (additionHandler == null) {
            HandlerManager.getInstance().postDelayed(new RunnableParamsN<Object>() {
                @Override
                public void run(Object... objects) {
                    UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
                    if (!(handler instanceof CrashHandler)) {
                        additionHandler = handler;
                    } else {
                        cycleExecutor.setPeriod(2000);
                        cycleExecutor.start();
                    }
                }
            }, 1500);
        }
    }

    private CycleExecutor cycleExecutor = new CycleExecutor() {
        @Override
        protected void onDoingExecutor(Object params) {
            defaultHandlerCount++;
            UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
            if (handler == null || (handler instanceof CrashHandler)) {
                if (defaultHandlerCount >= 5) {
                    cycleExecutor.stop();
                }
                return;
            }
            additionHandler = handler;
            cycleExecutor.stop();
        }
    };

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            onLogIntercept(ex);
            if (additionHandler != null) {
                // 如果用户没有处理则让系统默认的异常处理器来处理
                if (!(ex instanceof TimeoutException && "FinalizerWatchdogDaemon".equals(thread.getName()))) {
                    additionHandler.uncaughtException(thread, ex);
                }
            }
        } catch (Exception e) {
            // uncaughtException
        }
    }
}
