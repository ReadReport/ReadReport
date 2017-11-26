package com.wy.report.manager.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.text.format.DateUtils;

import com.wy.report.ReportApplication;
import com.wy.report.base.model.BaseModel;
import com.wy.report.business.auth.model.TokenModel;
import com.wy.report.business.auth.service.AuthService;
import com.wy.report.helper.retrofit.ReportException;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.preferences.Key;
import com.wy.report.manager.preferences.PreferenceManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 授权信息管理类
 *
 * @author cantalou
 * @date 2017年11月23日 11:28
 */
public class AuthManager {

    public static final String APP_ID = "dbg_ios_app";

    public static final String APP_SECRET = "6F2TL4tX3QhKCSJC";

    /**
     * Token失效时间<br/>
     * 60秒用于防止接近失效时间节点时,本地验证通过但服务端验证失败
     */
    private static final long EXPIRE_TIME = DateUtils.HOUR_IN_MILLIS * 2 - DateUtils.MINUTE_IN_MILLIS;

    /**
     * 启动自动检测, 每隔60分钟重新获取
     */
    private static final long REFRESH_INTERVAL = DateUtils.HOUR_IN_MILLIS;

    private TokenModel tokenModel = new TokenModel();

    private PreferenceManager preferenceManager;

    /**
     * 上一次刷新token的时间
     */
    private long lastRefreshTime;

    private static class InstanceHolder {
        static final AuthManager instance = new AuthManager();
    }

    private AuthManager() {
        preferenceManager = PreferenceManager.getInstance();
        tokenModel = preferenceManager.getValue(Key.AUTH_TOKEN_INFO, TokenModel.class);
        Observable.interval(REFRESH_INTERVAL, REFRESH_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          refreshToken();
                      }
                  });
        registerNetworkChange();
    }


    private void registerNetworkChange() {
        Context context = ReportApplication.getGlobalContext();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshToken();
            }
        }, filter);
    }


    public static final AuthManager getInstance() {
        return InstanceHolder.instance;
    }

    public void refreshToken() {
        if (SystemClock.elapsedRealtime() - lastRefreshTime < REFRESH_INTERVAL) {
            return;
        }
        RetrofitHelper.getRetrofit()
                      .create(AuthService.class)
                      .getToken(APP_ID, APP_SECRET)
                      .subscribe(new NetworkSubscriber<BaseModel<TokenModel>>() {
                          @Override
                          public void onNext(BaseModel<TokenModel> model) {
                              AuthManager.this.tokenModel = model.getData();
                              preferenceManager.setValue(Key.AUTH_TOKEN_INFO, tokenModel);
                              lastRefreshTime = SystemClock.elapsedRealtime();
                          }
                      });

    }

    public TokenModel getTokenModel() {
        return tokenModel;
    }
}
