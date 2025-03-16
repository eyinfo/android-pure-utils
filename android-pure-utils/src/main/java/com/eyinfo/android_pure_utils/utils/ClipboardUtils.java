package com.eyinfo.android_pure_utils.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtils {

    /**
     * 复制文本到剪切板
     *
     * @param context Context
     * @param text    待复制的文本
     */
    public static void copy(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("tel:", text);
        clipboard.setPrimaryClip(clip);
    }
}
