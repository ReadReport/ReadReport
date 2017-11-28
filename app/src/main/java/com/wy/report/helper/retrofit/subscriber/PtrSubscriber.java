package com.wy.report.helper.retrofit.subscriber;

import com.wy.report.base.fragment.PtrFragment;

/*
 *
 * @author cantalou
 * @date 2017-11-28 22:21
 */
public class PtrSubscriber<T> extends NetworkSubscriber<T> {

    protected PtrFragment handler;

    public PtrSubscriber(PtrFragment handler) {
        this.handler = handler;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        handler.getPtrFrameLayout()
               .refreshComplete();
    }
}
