package com.wy.report.business.splash.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.wy.report.business.splash.fragment.OnboardingFragment;
import com.wy.report.business.splash.fragment.SplashFragment;
import com.wy.report.manager.preferences.Key;
import com.wy.report.manager.preferences.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Fragment fragment;

        PreferenceManager preferenceManager = PreferenceManager.getInstance();
        if (preferenceManager.getValue(Key.FIRST_LAUNCH) == null) {

            fragment = new OnboardingFragment();
            preferenceManager.setValue(Key.FIRST_LAUNCH, true);
        } else {
            fragment = new SplashFragment();
        }
        getSupportFragmentManager().beginTransaction()
                                   .replace(android.R.id.content, fragment)
                                   .commitAllowingStateLoss();
    }
}

