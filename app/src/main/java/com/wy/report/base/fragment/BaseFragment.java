package com.wy.report.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cantalou.android.util.ReflectUtil;
import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;
import com.wy.report.helper.rxbus.EnhanceFinder;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.manager.router.Router;

import butterknife.ButterKnife;

/*
 *
 * @author cantalou
 * @date 2017-11-24 22:53
 */
public abstract class BaseFragment extends Fragment {

    protected static Bus rxBus;

    protected View contentView;

    protected Router router;

    protected AuthRouterManager authRouterManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rxBus == null) {
            rxBus = RxBus.get();
            ReflectUtil.set(rxBus, "finder", EnhanceFinder.ANNOTATED);
        }
        if (!rxBus.hasRegistered(this)) {
            rxBus.register(this);
        }
    }

    @CallSuper
    protected void initData(Bundle savedInstanceState) {
        authRouterManager = AuthRouterManager.getInstance();
        router = authRouterManager.getRouter();
    }

    protected abstract void initView(View contentView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        contentView = createView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, contentView);
        initView(contentView);
        return contentView;
    }

    @Nullable
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return bindView(inflater, container, savedInstanceState);
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

    /**
     * bindView
     *
     * @return
     */
    protected View bindView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(contentLayoutID(), container, false);
    }

    public boolean onBackPressed() {
        return false;
    }

    public int getColor(int color) {
        return getResources().getColor(color);
    }

}
