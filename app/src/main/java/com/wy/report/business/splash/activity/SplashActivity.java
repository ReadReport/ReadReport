package com.wy.report.business.splash.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wy.report.R;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.auth.model.User;
import com.wy.report.business.auth.service.AuthService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.AuthManager;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Observable.timer(2, TimeUnit.SECONDS)
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          AuthRouterManager.getInstance()
                                           .openHome(SplashActivity.this);
                      }
                  });
        Observable.timer(3, TimeUnit.SECONDS)
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          finish();
                      }
                  });
        RetrofitHelper.getInstance()
                      .create(AuthService.class)
                      .login("18046042250", "111111")
                      .subscribe(new NetworkSubscriber<ResponseModel<User>>(null) {
                          @Override
                          public void onNext(ResponseModel<User> userResponseModel) {
                              super.onNext(userResponseModel);
                              UserManger.getInstance()
                                        .updateUser(userResponseModel.getData());
                          }
                      });
    }
}

