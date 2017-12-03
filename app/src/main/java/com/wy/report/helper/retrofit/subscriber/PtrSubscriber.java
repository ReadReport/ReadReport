package com.wy.report.helper.retrofit.subscriber;

import android.support.annotation.CallSuper;

import com.wy.report.base.fragment.PtrFragment;

/*
 *
 * @author cantalou
 * @date 2017-11-28 22:21
 */
public class PtrSubscriber<T> extends NetworkSubscriber<T> {

    protected PtrFragment handler;

    public PtrSubscriber(PtrFragment handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    @CallSuper
    public void onNext(T t) {
        super.onNext(t);
        handler.getPtrFrameLayout()
                .refreshComplete();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();

    }
}
