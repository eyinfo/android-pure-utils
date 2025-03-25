package com.eyinfo.android_pure_utils.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class StartUtils {

    private static volatile StartUtils instance;

    public static StartUtils getInstance() {
        if (instance == null) {
            synchronized (StartUtils.class) {
                if (instance == null) {
                    instance = new StartUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 此方式会跳转到系统拨号界面并填充号码，无需申请权限，用户需手动点击拨号按钮。
     *
     * @param tel 电话号码
     */
    public void startTel(Context context, String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(String.format("tel:%s", tel)));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            try {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
