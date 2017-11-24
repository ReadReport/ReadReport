package com.wy.report.manager.auth;

import android.text.format.DateUtils;

import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.manager.preferences.Key;
import com.wy.report.manager.preferences.PreferenceManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 授权信息管理类
 *
 * @author cantalou
 * @date 2017年11月23日 11:28
 */
public class AuthManager {

    public static final String APP_ID = "appid";

    public static final String APP_SECRET = "secret";

    /**
     * Token失效时间<br/>
     * 60秒用于防止接近失效时间节点时,本地验证通过但服务端验证失败
     */
    private static final long EXPIRE_TIME = DateUtils.HOUR_IN_MILLIS * 2 - DateUtils.MINUTE_IN_MILLIS;

    /**
     * 启动自动检测, 每隔60分钟重新获取
     */
    private static final long REFRESH_INTERVAL = DateUtils.HOUR_IN_MILLIS;

    private TokenInfo tokenInfo = new TokenInfo();

    private PreferenceManager preferenceManager;

    private static class InstanceHolder {
        static final AuthManager instance = new AuthManager();
    }

    private AuthManager() {
        preferenceManager = PreferenceManager.getInstance();
        tokenInfo = preferenceManager.getValue(Key.AUTH_TOKEN_INFO, TokenInfo.class);
        Observable.interval(0L, REFRESH_INTERVAL, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          refreshToken();
                      }
                  });
    }

    public static final AuthManager getInstance() {
        return InstanceHolder.instance;
    }

    public void refreshToken() {
        RetrofitHelper.getRetrofit()
                      .create(AuthService.class)
                      .getToken(APP_ID, APP_SECRET)
                      .subscribe(new Subscriber<TokenInfo>() {
                          @Override
                          public void onCompleted() {
                          }

                          @Override
                          public void onError(Throwable e) {
                          }

                          @Override
                          public void onNext(TokenInfo tokenInfo) {
                              AuthManager.this.tokenInfo = tokenInfo;
                              preferenceManager.setValue(Key.AUTH_TOKEN_INFO, tokenInfo);
                          }
                      });

    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }
}
