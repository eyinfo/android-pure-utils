package com.eyinfo.android_pure_utils.observable;

import com.eyinfo.android_pure_utils.observable.call.OnNextConsumer;
import com.eyinfo.android_pure_utils.observable.call.OnObservableComplete;
import com.eyinfo.android_pure_utils.observable.call.OnSubscribeConsumer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2019-06-11
 * Description:
 * Modifier:
 * ModifyContent:
 */
public class BaseObservable {

    private static class CusObservableOnSubscribe<T, T1> implements ObservableOnSubscribe<T> {

        private T param;
        private T1 t1;
        private OnSubscribeConsumer<T, T1> subscribeConsumer;

        public CusObservableOnSubscribe(T param, T1 t1, OnSubscribeConsumer<T, T1> subscribeConsumer) {
            this.param = param;
            this.t1 = t1;
            this.subscribeConsumer = subscribeConsumer;
        }

        @Override
        public void subscribe(ObservableEmitter<T> emitter) throws Exception {
            if (subscribeConsumer != null) {
                subscribeConsumer.onSubscribe(param, t1);
            }
            emitter.onNext(param);
            emitter.onComplete();
        }
    }

    private static class CusObserver<T, T1> implements Observer<T> {

        private Disposable disposable;
        private OnNextConsumer<T, T1> onNext;
        private Consumer<Throwable> onError;
        private OnObservableComplete<T> complete;
        private T param;
        private T1 t1;
        private Object extras;

        public CusObserver(T param, T1 t1, Object extras, OnNextConsumer<T, T1> onNext, Consumer<Throwable> onError, OnObservableComplete<T> complete) {
            this.onNext = onNext;
            this.onError = onError;
            this.complete = complete;
            this.param = param;
            this.t1 = t1;
            this.extras = extras;
        }

        @Override
        public void onSubscribe(Disposable d) {
            disposable = d;
        }

        @Override
        public void onNext(T param) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            if (onNext == null) {
                return;
            }
            try {
                onNext.accept(param, t1, (extras instanceof Object[]) ? (Object[]) extras : new Object[]{extras});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            if (onError == null) {
                return;
            }
            try {
                onError.accept(e);
            } catch (Throwable e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void onComplete() {
            if (complete == null) {
                return;
            }
            complete.onComplete(param);
        }
    }

    /**
     * subscribe
     *
     * @param param             input param
     * @param t1                input param2
     * @param subscribeConsumer 订阅消费者
     * @param onNext            处理成功回调
     * @param onError           处理失败回调
     * @param complete          处理完成
     * @param extras            扩展数据
     * @param <NextParam>       参数类型
     * @param <T1>              参数2类型
     */
    protected <NextParam, T1> void buildSubscribe(NextParam param, T1 t1, OnSubscribeConsumer<NextParam, T1> subscribeConsumer, OnNextConsumer<NextParam, T1> onNext, Consumer<Throwable> onError, OnObservableComplete<NextParam> complete, Object extras) {
        Observable<NextParam> observable = Observable.<NextParam>create(new CusObservableOnSubscribe<NextParam, T1>(param, t1, subscribeConsumer));
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusObserver<NextParam, T1>(param, t1, extras, onNext, onError, complete));
    }

    /**
     * subscribe
     *
     * @param param             input param
     * @param t1                input param2
     * @param subscribeConsumer 订阅消费者
     * @param onNext            处理成功回调
     * @param extras            扩展数据
     * @param <NextParam>       参数类型
     * @param <T1>              参数2类型
     */
    protected <NextParam, T1> void buildSubscribe(NextParam param, T1 t1, OnSubscribeConsumer<NextParam, T1> subscribeConsumer, OnNextConsumer<NextParam, T1> onNext, Object extras) {
        buildSubscribe(param, t1, subscribeConsumer, onNext, null, null, extras);
    }

    /**
     * subscribe
     *
     * @param param             input param
     * @param subscribeConsumer 订阅消费者
     * @param onNext            处理成功回调
     * @param extras            扩展数据
     * @param <NextParam>       参数类型
     */
    protected <NextParam> void buildSubscribe(NextParam param, OnSubscribeConsumer<NextParam, Object> subscribeConsumer, OnNextConsumer<NextParam, Object> onNext, Object extras) {
        buildSubscribe(param, null, subscribeConsumer, onNext, extras);
    }
}
