package com.eyinfo.android_pure_utils.observable.call;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019-06-12
 * Description:onNext回调处理
 * Modifier:
 * ModifyContent:
 */
public abstract class OnNextConsumer<T, T1> {

    public abstract void accept(T t, T1 t1, Object[] extras);
}
