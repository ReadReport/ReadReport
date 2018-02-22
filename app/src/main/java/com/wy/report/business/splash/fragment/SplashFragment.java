package com.wy.report.business.splash.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * @author cantalou
 * @date 2018年02月08日 15:45
 */
public class SplashFragment extends BaseFragment {

    @Override
    protected void initView(View contentView) {
        final Activity activity = getActivity();
        Observable.timer(2, TimeUnit.SECONDS)
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          authRouterManager.openHome(activity);
                      }
                  });
        Observable.timer(3, TimeUnit.SECONDS)
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          activity.finish();
                      }
                  });
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_splash;
    }
}
