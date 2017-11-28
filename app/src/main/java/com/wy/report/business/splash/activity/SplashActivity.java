package com.wy.report.business.splash.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wy.report.R;
import com.wy.report.manager.auth.AuthManager;
import com.wy.report.manager.router.AuthRouterManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AuthManager.getInstance()
                   .refreshToken();

        Observable.timer(2, TimeUnit.MILLISECONDS)
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          AuthRouterManager.getInstance()
                                           .openHome(SplashActivity.this);
                          finish();
                      }
                  });
    }
}

