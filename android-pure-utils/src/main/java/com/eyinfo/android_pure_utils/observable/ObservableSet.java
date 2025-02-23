package com.eyinfo.android_pure_utils.observable;

import com.eyinfo.android_pure_utils.observable.call.OnDisposableListener;
import com.eyinfo.android_pure_utils.observable.call.OnSubscribeListener;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019/2/22
 * Description:rxjava订阅集;后期相关订阅在当前类实现;
 * Modifier:
 * ModifyContent:
 */
public class ObservableSet {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 结构rxjava简单任务处理(只有next或accept[成功]回调)
     *
     * @param source   耗时任务处理监听(在io线程)
     * @param nextCall 结果任务处理线程(在mainThread线程)
     * @param <R>      source.next()数据返回类型
     */
    public <R> void buildEasyTask(final OnSubscribeListener<R> source, final OnDisposableListener<R> nextCall) {
        if (nextCall == null) {
            //如果没有回调处理请使用ThreadPoolUtils
            return;
        }
        Observable<R> observable = Observable.create(new ObservableOnSubscribe<R>() {

            @Override
            public void subscribe(ObservableEmitter<R> e) throws Exception {
                if (source != null) {
                    e.onNext(source.next());
                }
                e.onComplete();
            }
        });
        DisposableSimpleObserver<R> disposableSimpleObserver = new DisposableSimpleObserver<R>() {
            @Override
            public void onNext(R param) {
                if (nextCall == null) {
                    return;
                }
                nextCall.accept(param);
            }
        };
        observable.subscribeOn(
                        Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(disposableSimpleObserver);
        mCompositeDisposable.add(disposableSimpleObserver);
    }
}
