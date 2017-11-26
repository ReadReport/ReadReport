package com.wy.report.base.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    protected Bus rxBus;

    protected Toolbar.OnMenuItemClickListener menuItemClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxBus = RxBus.get();
        if (!rxBus.hasRegistered(this)) {
            rxBus.register(this);
        }
        initData();
        setContentView(contentLayoutID());
        ButterKnife.bind(this);
        initView();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int contentLayoutID();

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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (menuItemClickListener != null) {
            return menuItemClickListener.onMenuItemClick(item);
        }
        return false;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(baseLayoutID());
        ViewGroup baseLayout = (ViewGroup) findViewById(R.id.base_layout);
        baseLayout.addView(view);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(baseLayoutID());
        ViewGroup baseLayout = (ViewGroup) findViewById(R.id.base_layout);
        LayoutInflater.from(this)
                      .inflate(layoutResID, baseLayout);
    }

    protected int baseLayoutID() {
        return R.layout.activity_base;
    }

    public void setMenuItemClickListener(Toolbar.OnMenuItemClickListener menuItemClickListener) {
        this.menuItemClickListener = menuItemClickListener;
    }
}
