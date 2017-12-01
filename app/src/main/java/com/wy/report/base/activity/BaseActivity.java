package com.wy.report.base.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.umeng.analytics.MobclickAgent;
import com.wy.report.R;

import butterknife.ButterKnife;

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * @author cantalou
 * @date 2017-11-23 22:22
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Bus rxBus;

    protected Toolbar.OnMenuItemClickListener menuItemClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (isTranslucentStatusBar()) {
            Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }

        savedInstanceState.remove("android:support:fragments");
        super.onCreate(savedInstanceState);
        rxBus = RxBus.get();
        if (!rxBus.hasRegistered(this)) {
            rxBus.register(this);
        }
        initData(savedInstanceState);
        int contentLayoutID = contentLayoutID();
        if (contentLayoutID > 0) {
            setContentView(contentLayoutID);
            ButterKnife.bind(this);
            initView();
        }
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected void initView() {

    }

    protected int contentLayoutID() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (rxBus != null && rxBus.hasRegistered(this)) {
            rxBus.unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.navigtor_pop_right_in, R.anim.navigtor_pop_right_out);
    }

    public boolean isTranslucentStatusBar() {
        return false;
    }

}