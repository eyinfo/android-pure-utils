package com.eyinfo.android_pure_utils.events;

import android.view.View;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2018/8/14
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class OnClickListenerProxy implements View.OnClickListener {
    private View.OnClickListener object;

    public OnClickListenerProxy(View.OnClickListener object) {
        this.object = object;
    }

    @Override
    public void onClick(View v) {
        try {
            onPreClickProxy(v);
            if (object != null) {
                object.onClick(v);
            }
            onAfterClickProxy(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPreClickProxy(View v) {

    }

    protected void onAfterClickProxy(View v) {

    }
}
