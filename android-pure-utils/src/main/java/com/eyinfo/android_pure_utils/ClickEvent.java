package com.eyinfo.android_pure_utils;

import android.view.View;

import androidx.annotation.IdRes;

import com.eyinfo.android_pure_utils.events.OnClickTriggerListener;
import com.eyinfo.android_pure_utils.logs.Logger;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019/3/8
 * Description:防止频繁点击
 * Modifier:
 * ModifyContent:
 */
public class ClickEvent {
    private static long lastClickTime = 0;
    private static int viewId = 0;

    /**
     * 是否快速点击
     *
     * @param resid view id
     * @return true-快速点击;false-正常;
     */
    public static boolean isFastClick(@IdRes int resId) {
        try {
            if (resId != 0) {
                if (viewId != resId) {
                    viewId = resId;
                    lastClickTime = 0;
                }
            }
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            int intervalTime = 400;
            if (0 < timeD && timeD < intervalTime) {
                return true;
            }
            lastClickTime = time;
        } catch (Exception e) {
            Logger.error(e);
        }
        return false;
    }

    /**
     * 是否快速点击
     *
     * @return true-快速点击;false-正常;
     */
    public static boolean isFastClick() {
        return isFastClick(0);
    }

    /**
     * 连续触发次数回调
     *
     * @param view                 触发视图
     * @param triggerCount         连续触发triggerCount次{@link OnClickTriggerListener}中的reverse true,
     *                             再连续触发triggerCount次reverse false,依次类推;
     * @param clickTriggerListener 回调监听
     */
    public static void continuousTrigger(View view, int triggerCount, OnClickTriggerListener clickTriggerListener) {
        if (view == null || triggerCount <= 0 || clickTriggerListener == null) {
            return;
        }
        view.setOnClickListener(new ContinuousTriggerListener(triggerCount, clickTriggerListener));
    }

    private static class ContinuousTriggerListener implements View.OnClickListener {

        private int triggerCount;
        private OnClickTriggerListener clickTriggerListener;
        private long time = 0;
        private int count;
        private boolean flag = false;

        public ContinuousTriggerListener(int triggerCount, OnClickTriggerListener clickTriggerListener) {
            this.triggerCount = triggerCount;
            this.clickTriggerListener = clickTriggerListener;
        }

        @Override
        public void onClick(View v) {
            long timeMillis = System.currentTimeMillis();
            long diff = timeMillis - time;
            int interval = 600;
            if (diff > interval) {
                if (flag) {
                    count = triggerCount - 1;
                } else {
                    count = 1;
                }
            }
            if (flag) {
                if (count == 0) {
                    time = 0;
                    flag = false;
                    clickTriggerListener.onClickTrigger(flag);
                }
                count--;
            } else {
                if (count == triggerCount) {
                    time = 0;
                    flag = true;
                    clickTriggerListener.onClickTrigger(flag);
                }
                count++;
            }
            time = timeMillis;
        }
    }
}
