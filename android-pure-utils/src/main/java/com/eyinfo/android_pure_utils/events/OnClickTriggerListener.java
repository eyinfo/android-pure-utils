package com.eyinfo.android_pure_utils.events;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019-05-20
 * Description:单击触发监听
 * Modifier:
 * ModifyContent:
 */
public interface OnClickTriggerListener {
    /**
     * 单击触发
     *
     * @param reverse 触发triggerCount次为true再触发triggerCount次为false,依次类推;
     */
    public void onClickTrigger(boolean reverse);
}
