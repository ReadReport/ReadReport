package com.wy.report.helper.retrofit;

import com.wy.report.manager.auth.AuthManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * @author cantalou
 * @date 2017年11月27日 13:57
 */
public class RetryWhenException implements Func1<Observable<? extends Throwable>, Observable<?>> {

    /**
     * retry次数
     */

    private static final int DEFAULT_COUNT = 3;

    /**
     * 延迟
     */
    private static final long DEFAULT_DELAY = 3000;

    /**
     * 叠加延迟
     */
    private static final long DEFAULT_INCREASE_DELAY = 3000;

    /**
     * retry次数
     */

    private int count;

    /**
     * 延迟
     */
    private long delay;

    /**
     * 叠加延迟
     */
    private long increaseDelay;

    private AuthManager authManager;

    public RetryWhenException() {
        this(DEFAULT_COUNT, DEFAULT_DELAY);
    }

    public RetryWhenException(int count, long delay) {
        this(count, delay, DEFAULT_INCREASE_DELAY);
    }

    public RetryWhenException(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
        this.authManager = AuthManager.getInstance();
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                if (throwable instanceof ReportException && ResponseCode.ERROR_CODE_40004.equals(((ReportException) throwable).getCode())) {
                    authManager.makeExpired();
                    authManager.syncRefreshToken();
                    return Observable.just(null);
                } else {
                    return Observable.just(throwable)
                                     .zipWith(Observable.range(1, count + 1), new Func2<Throwable, Integer, Wrapper>() {
                                         @Override
                                         public Wrapper call(Throwable throwable, Integer integer) {
                                             return new Wrapper(throwable, integer);
                                         }
                                     })
                                     .flatMap(new Func1<Wrapper, Observable<?>>() {
                                         @Override
                                         public Observable<?> call(Wrapper wrapper) {
                                             if (wrapper.throwable instanceof ConnectException
                                                     || wrapper.throwable instanceof SocketTimeoutException
                                                     || wrapper.throwable instanceof TimeoutException) {
                                                 //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                                                 if (wrapper.index < count + 1) {
                                                     return Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);
                                                 } else {
                                                     return Observable.error(ResponseCode.convert2Exception(ResponseCode.ERROR_CODE_2, ""));
                                                 }
                                             }
                                             return Observable.error(wrapper.throwable);
                                         }
                                     });
                }
            }
        });


    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
