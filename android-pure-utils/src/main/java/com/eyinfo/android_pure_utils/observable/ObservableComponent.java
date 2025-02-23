package com.eyinfo.android_pure_utils.observable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2018/9/16
 * Description:
 * Modifier:
 * ModifyContent:
 */
public class ObservableComponent<Param, Params> {
    private static CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Params[] params = null;
    private String key = "";
    private Object extras = null;

    /**
     * subscribe
     *
     * @return
     */
    protected Param subscribeWith(Params... params) throws Exception {
        return null;
    }

    /**
     * onNext
     *
     * @param param
     * @param params
     */
    protected void nextWith(Param param, Params... params) {

    }

    /**
     * onNext
     *
     * @param param
     * @param key
     * @param params
     */
    protected void nextWith(Param param, String key, Params... params) {

    }

    /**
     * complete
     *
     * @param isError   true-已回调onError方法；false-已正常处理完成;
     * @param throwable
     * @param params
     */
    protected void completeWith(boolean isError, Throwable throwable, Params... params) {

    }

    /**
     * 回调时返回,用于区分
     *
     * @param key 请求标识
     */
    public void setKey(String key) {
        this.key = key;
    }

    public <T> T getExtras() {
        return (T) extras;
    }

    public void setExtras(Object extras) {
        this.extras = extras;
    }

    class EmitterObject {
        public Param returnValue;

        public EmitterObject(Param returnValue) {
            this.returnValue = returnValue;
        }
    }

    /**
     * 构建并执行observable
     *
     * @param params 输入参数在subscribeWith参数中返回
     */
    public void build(Params... params) {
        internalBuild(Schedulers.io(), params);
    }

    /**
     * 构建并执行observable，提供一个不切换线程的调用方式
     *
     * @param params
     */
    public void buildWithoutScheduler(Params... params) {
        internalBuild(null, params);

    }

    public void internalBuild(Scheduler scheduler, Params... params) {
        this.params = params;
        try {
            Observable<EmitterObject> observable = Observable.create(new ObservableOnSubscribe<EmitterObject>() {

                @Override
                public void subscribe(ObservableEmitter<EmitterObject> e) throws Exception {
                    e.onNext(new EmitterObject(subscribeWith(ObservableComponent.this.params)));
                    e.onComplete();
                }
            });
            final DisposableObserver<EmitterObject> disposableObserver = new DisposableObserver<EmitterObject>() {
                @Override
                public void onNext(EmitterObject emitter) {
                    nextWith(emitter.returnValue, ObservableComponent.this.params);
                    nextWith(emitter.returnValue, key, ObservableComponent.this.params);
                }

                @Override
                public void onError(Throwable e) {
                    completeWith(true, e, ObservableComponent.this.params);
                    disposable(this);
                }

                @Override
                public void onComplete() {
                    completeWith(false, null, ObservableComponent.this.params);
                    disposable(this);
                }
            };
            if (scheduler != null) {
                observable = observable.subscribeOn(scheduler);
            }
            observable.
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(disposableObserver);
            mCompositeDisposable.add(disposableObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disposable(Disposable disposable) {
        try {
            if (disposable == null) {
                return;
            }
            if (disposable.isDisposed()) {
                mCompositeDisposable.delete(disposable);
            } else {
                mCompositeDisposable.remove(disposable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
