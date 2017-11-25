package com.wy.report.base.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.umeng.analytics.MobclickAgent;
import com.wy.report.base.activity.BaseActivity;

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
 * @date 2017-11-24 22:53
 */
public abstract class BaseFragment extends Fragment {

    protected Bus rxBus;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (this instanceof Toolbar.OnMenuItemClickListener && activity instanceof BaseActivity) {
            ((BaseActivity) activity).setMenuItemClickListener((Toolbar.OnMenuItemClickListener) this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxBus = RxBus.get();
        if (!rxBus.hasRegistered(this)) {
            rxBus.register(this);
        }
        initData();
    }

    @CallSuper
    protected abstract void initData();

    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(contentLayoutID(), container, false);
        initView();
        return contentView;
    }

    @Override
    public void onDestroy() {
        if (rxBus != null && rxBus.hasRegistered(this)) {
            rxBus.unregister(this);
        }
        super.onDestroy();
    }

    /**
     * Fragment 内容布局文件id
     *
     * @return
     */
    protected int contentLayoutID() {
        return 0;
    }
}
