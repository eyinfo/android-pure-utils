package com.eyinfo.android_pure_utils.logs.lart;

/**
 * A proxy interface to enable additional operations.
 * Contains all possible Log message usages.
 */
public interface Printer {

    void addAdapter(LogAdapter adapter);

    Printer t(String tag);

    void d(String message, Object... args);

    void d(Object object);

    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void w(String message, Object... args);

    void i(String message, Object... args);

    void v(String message, Object... args);

    void wtf(String message, Object... args);

    /**
     * Formats the given json content and print it
     */
    void json(String json);

    /**
     * Formats the given xml content and print it
     */
    void xml(String xml);

    void log(int priority, String tag, String message, Throwable throwable);

    void clearLogAdapters();
}
