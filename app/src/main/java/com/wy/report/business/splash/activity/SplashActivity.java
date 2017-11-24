package com.wy.report.business.splash.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wy.report.R;
import com.wy.report.manager.auth.AuthManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AuthManager.getInstance().refreshToken();
    }
}
