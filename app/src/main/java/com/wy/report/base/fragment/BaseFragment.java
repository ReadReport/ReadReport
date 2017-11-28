package com.wy.report.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.wy.report.base.activity.BaseActivity;

/*
 *
 * @author cantalou
 * @date 2017-11-24 22:53
 */
public abstract class BaseFragment extends Fragment {

    protected Bus rxBus;

    protected Toolbar toolbar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
    protected abstract int contentLayoutID();

    protected boolean isSupportToolbar() {
        return true;
    }

    protected boolean isToolbarOverlap() {
        return false;
    }
}
