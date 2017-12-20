package com.wy.report.helper.retrofit.subscriber;

import android.support.annotation.CallSuper;

import com.wy.report.base.fragment.PtrFragment;

import java.lang.ref.SoftReference;

/*
 *
 * @author cantalou
 * @date 2017-11-28 22:21
 */
public class PtrSubscriber<T> extends NetworkSubscriber<T> {

    public PtrSubscriber(PtrFragment handler) {
        super(handler);
    }

}
