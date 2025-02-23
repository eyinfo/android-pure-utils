package com.eyinfo.android_pure_utils.logs.lart;

import android.text.TextUtils;
import android.util.Log;

/**
 * LogCat implementation for {@link LogStrategy}
 * <p>
 * This simply prints out all logs to Logcat by using standard {@link Log} class.
 */
public class LogcatLogStrategy implements LogStrategy {

    static final String DEFAULT_TAG = "NO_TAG";

    @Override
    public void log(int priority, String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (tag == null) {
            tag = DEFAULT_TAG;
        }
        Log.println(priority, tag, message);
    }
}
