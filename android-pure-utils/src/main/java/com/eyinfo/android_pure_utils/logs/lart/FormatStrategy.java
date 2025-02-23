package com.eyinfo.android_pure_utils.logs.lart;

/**
 * Used to determine how messages should be printed or saved.
 *
 * @see PrettyFormatStrategy
 * @see CsvFormatStrategy
 */
public interface FormatStrategy {

    void log(int priority, String tag, String message);
}
