package com.eyinfo.android_pure_utils.observable.call;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019-06-11
 * Description:RxJava完成回调
 * Modifier:
 * ModifyContent:
 */
public interface OnObservableComplete<NextParam> {

    /**
     * rxjava complete call
     *
     * @param param init param
     */
    public void onComplete(NextParam param);
}
