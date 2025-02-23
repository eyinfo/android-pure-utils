package com.eyinfo.android_pure_utils.logs;

import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2015-7-11 上午10:49:22
 * Description: 缓存异常信息至文件
 * Modifier:
 * ModifyContent:
 */
class CrashFileTask {

    private final String STACK_TRACE = "STACK_TRACE";
    /**
     * 错误报告文件的扩展名
     */
    private final String CRASH_REPORTER_EXTENSION = "txt";

    public void writeLog(Throwable throwable, File targetDir) {
        try {
            if (throwable == null) {
                return;
            }
            String crashInfo = CrashUtils.getCrashInfo(throwable);
            if (TextUtils.isEmpty(crashInfo)) {
                return;
            }
            Properties mDeviceCrashInfo = new Properties();
            saveToFile(crashInfo, mDeviceCrashInfo, targetDir);
        } catch (Exception e) {
            // write log error
        }
    }

    /**
     * 保存日志
     *
     * @param crashInfo        错误信息
     * @param mDeviceCrashInfo 设置相关信息
     * @param targetDir        目标目录
     */
    private void saveToFile(String crashInfo, Properties mDeviceCrashInfo, File targetDir) {
        try {
            if (targetDir == null) {
                return;
            }
            final String contentperx = crashInfo.length() > 20 ? crashInfo.substring(0, 20) : crashInfo.toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String date = sdf.format(System.currentTimeMillis());
            String fileName = String.format("%s_%s.%s", date, contentperx, CRASH_REPORTER_EXTENSION);
            File[] filelst = targetDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (TextUtils.isEmpty(filename)) {
                        return false;
                    } else if (filename.contains(contentperx)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            if (filelst != null && filelst.length > 0) {
                for (File file : filelst) {
                    file.delete();
                }
            }
            mDeviceCrashInfo.put(STACK_TRACE, crashInfo);
            File mfile = new File(targetDir, fileName);
            if (!mfile.exists()) {
                mfile.createNewFile();
            }
            // 保存文件
            FileOutputStream trace = new FileOutputStream(mfile, true);
            mDeviceCrashInfo.store(trace, "");
            trace.flush();
            trace.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
