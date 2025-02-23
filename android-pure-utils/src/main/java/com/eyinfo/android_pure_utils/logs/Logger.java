package com.eyinfo.android_pure_utils.logs;

import com.eyinfo.android_pure_utils.logs.enums.LogLevel;
import com.eyinfo.android_pure_utils.logs.lart.AndroidLogAdapter;
import com.eyinfo.android_pure_utils.logs.lart.FormatStrategy;
import com.eyinfo.android_pure_utils.logs.lart.PrettyFormatStrategy;
import com.eyinfo.android_pure_utils.logs.lart.Printer;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019/2/27
 * Description:基于orhanobut的日志处理
 * Modifier:
 * ModifyContent:
 */
public class Logger {

    private static FormatStrategy formatStrategy;

    private static FormatStrategy getFormatStrategy() {
        if (formatStrategy == null) {
            PrettyFormatStrategy.Builder builder = PrettyFormatStrategy.newBuilder();
            formatStrategy = builder.showThreadInfo(true)
                    .methodCount(5)
                    .methodOffset(0)
                    .build();
        }
        return formatStrategy;
    }

    private static Printer printer(String tag) {
        com.eyinfo.android_pure_utils.logs.lart.Logger.clearLogAdapters();
        FormatStrategy formatStrategy = getFormatStrategy();
        Printer printer = com.eyinfo.android_pure_utils.logs.lart.Logger.t(tag);
        printer.addAdapter(new AndroidLogAdapter(formatStrategy));
        return printer;
    }

    private static boolean logIntercept(String tag, LogLevel level, String message, Throwable throwable, Object... args) {
        return false;
    }

//    /**
//     * debug日志
//     *
//     * @param tag    当前日志标签；示例[全局标签-tag]
//     * @param object 基础数据结构对象
//     */
//    public static void debug(String tag, Object object) {
//        printer(tag).d(object);
//    }

//    /**
//     * debug日志
//     *
//     * @param object 基础数据结构对象
//     */
//    public static void debug( Object object) {
//        debug("", object);
//    }

    /**
     * debug日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void debug(String tag, String message, Object... args) {
        if (logIntercept(tag, LogLevel.debug, message, null, args)) {
            return;
        }
        printer(tag).d(message, args);
    }

    /**
     * 上报debug日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportDebug(String tag, String message, Object... args) {
        if (logIntercept(tag, LogLevel.reportDebug, message, null, args)) {
            return;
        }
        printer(tag).d(message, args);
    }

    /**
     * debug日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void debug(String message, Object... args) {
        if (logIntercept("", LogLevel.debug, message, null, args)) {
            return;
        }
        debug("", message, args);
    }

    /**
     * 上报debug日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportDebug(String message, Object... args) {
        if (logIntercept("", LogLevel.reportDebug, message, null, args)) {
            return;
        }
        debug("", message, args);
    }

    /**
     * 错误日志
     *
     * @param tag       当前日志标签；示例[全局标签-tag]
     * @param throwable 栈信息
     * @param message   消息
     * @param args      基础数据结构对象
     */
    public static void error(String tag, Throwable throwable, String message, Object... args) {
        if (logIntercept(tag, LogLevel.error, message, throwable, args)) {
            return;
        }
        printer(tag).e(throwable, message, args);
    }

    /**
     * 上报错误日志
     *
     * @param tag       当前日志标签；示例[全局标签-tag]
     * @param throwable 栈信息
     * @param message   消息
     * @param args      基础数据结构对象
     */
    public static void reportError(String tag, Throwable throwable, String message, Object... args) {
        if (logIntercept(tag, LogLevel.reportError, message, throwable, args)) {
            return;
        }
        printer(tag).e(throwable, message, args);
    }

    /**
     * 错误日志
     *
     * @param throwable 栈信息
     * @param message   消息
     * @param args      基础数据结构对象
     */
    public static void error(Throwable throwable, String message, Object... args) {
        error("", throwable, message, args);
    }

    /**
     * 上报错误日志
     *
     * @param throwable 栈信息
     * @param message   消息
     * @param args      基础数据结构对象
     */
    public static void reportError(Throwable throwable, String message, Object... args) {
        reportError("", throwable, message, args);
    }

    /**
     * 错误日志
     *
     * @param throwable 栈信息
     */
    public static void error(Throwable throwable) {
        error(throwable, "");
    }

    /**
     * 上报错误日志
     *
     * @param throwable 栈信息
     */
    public static void reportError(Throwable throwable) {
        reportError(throwable, "");
    }

    /**
     * 错误日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void error(String tag, String message, Object... args) {
        error(tag, null, message, args);
    }

    /**
     * 上报错误日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportError(String tag, String message, Object... args) {
        reportError(tag, null, message, args);
    }

    /**
     * 错误日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void error(String message, Object... args) {
        error("", message, args);
    }

    /**
     * 上报错误日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportError(String message, Object... args) {
        reportError("", message, args);
    }

    /**
     * 信息日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void info(String tag, String message, Object... args) {
        if (logIntercept(tag, LogLevel.info, message, null, args)) {
            return;
        }
        printer(tag).i(message, args);
    }

    /**
     * 上报信息日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportInfo(String tag, String message, Object... args) {
        if (logIntercept(tag, LogLevel.reportInfo, message, null, args)) {
            return;
        }
        printer(tag).i(message, args);
    }

    /**
     * 信息日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void info(String message, Object... args) {
        info("", message, args);
    }

    /**
     * 上报信息日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportInfo(String message, Object... args) {
        reportInfo("", message, args);
    }

    /**
     * 版本记录日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void version(String tag, String message, Object... args) {
        if (logIntercept(tag, LogLevel.version, message, null, args)) {
            return;
        }
        printer(tag).v(message, args);
    }

    /**
     * 上报版本记录日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportVersion(String tag, String message, Object... args) {
        if (logIntercept(tag, LogLevel.reportVersion, message, null, args)) {
            return;
        }
        printer(tag).v(message, args);
    }

    /**
     * 版本记录日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void version(String message, Object... args) {
        version("", message, args);
    }

    /**
     * 上报版本记录日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportVersion(String message, Object... args) {
        reportVersion("", message, args);
    }

    /**
     * 警告类日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void warn(String tag, String message, Object... args) {
        if (logIntercept(tag, LogLevel.warn, message, null, args)) {
            return;
        }
        printer(tag).w(message, args);
    }

    /**
     * 上报警告类日志
     *
     * @param tag     当前日志标签；示例[全局标签-tag]
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportWarn(String tag, String message, Object... args) {
        if (logIntercept(tag, LogLevel.reportWarn, message, null, args)) {
            return;
        }
        printer(tag).w(message, args);
    }

    /**
     * 警告类日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void warn(String message, Object... args) {
        warn("", message, args);
    }

    /**
     * 上报警告类日志
     *
     * @param message 消息
     * @param args    基础数据结构对象
     */
    public static void reportWarn(String message, Object... args) {
        reportWarn("", message, args);
    }

    /**
     * 记录json日志
     *
     * @param tag  当前日志标签；示例[全局标签-tag]
     * @param json json内容
     */
    public static void json(String tag, String json) {
        if (logIntercept(tag, LogLevel.json, json, null, (Object) null)) {
            return;
        }
        printer(tag).json(json);
    }

    /**
     * 记录json日志
     *
     * @param json json内容
     */
    public static void json(String json) {
        json("", json);
    }

    /**
     * 记录xml日志
     *
     * @param tag 当前日志标签；示例[全局标签-tag]
     * @param xml xml内容
     */
    public static void xml(String tag, String xml) {
        if (logIntercept(tag, LogLevel.xml, xml, null, (Object) null)) {
            return;
        }
        printer(tag).xml(xml);
    }

    /**
     * 记录xml日志
     *
     * @param xml xml内容
     */
    public static void xml(String xml) {
        xml("", xml);
    }
}
