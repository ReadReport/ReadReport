package com.wy.report.helper.retrofit.subscriber;

import android.support.annotation.CallSuper;
import android.widget.Toast;

import com.cantalou.android.util.Log;
import com.cantalou.android.util.NetworkUtils;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.helper.retrofit.ReportException;

import java.io.IOException;
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

    public void handleStart() {
        PtrFragment fragment = (PtrFragment)handler.get();
        if (fragment != null) {
            fragment.handlePtrStart();
        }
    }

    public void handleError(Throwable t) {
        PtrFragment fragment = (PtrFragment)handler.get();
        if (fragment != null) {
            fragment.handlePtrError(t);
        }
    }

    public void handleSuccess(T t) {
        PtrFragment fragment = (PtrFragment)handler.get();
        if (fragment != null) {
            fragment.handlePtrSuccess(t);
        }
    }
}
