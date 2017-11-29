package com.wy.report.base.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cantalou.android.util.ReflectUtil;
import com.wy.report.R;

import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
 * License for the specific language governing permis sions and limitations under
 * the License.
 *
 * @author cantalou
 * @date 2017-11-24 23:54
 */
public abstract class PtrFragment extends ToolbarFragment implements PtrHandler {

    protected PtrFrameLayout ptrFrameLayout;

    @Nullable
    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Clean the placeholder view
        ViewGroup prtContainer = (ViewGroup) inflater.inflate(ptrLayoutID(), container, false);
        prtContainer.removeView(prtContainer.findViewById(R.id.ptr_container_placeholder));
        ReflectUtil.set(prtContainer, "mContent", null);

        //re init view
        ViewGroup contentView = (ViewGroup) super.createView(inflater, container, savedInstanceState);
        prtContainer.addView(contentView);
        ReflectUtil.invoke(prtContainer, "onFinishInflate");
        return prtContainer;
    }

    @Override
    @CallSuper
    protected void initView(View contentView) {
        super.initView(contentView);
        ptrFrameLayout = (PtrFrameLayout) contentView.findViewById(R.id.ptr_layout);
        ptrFrameLayout.setPtrHandler(this);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        loadData();
    }

    public void performRefresh() {
        ptrFrameLayout.autoRefresh(true);
    }

    public void onLoadingStart() {

    }

    public void onLoadEnd() {
        ptrFrameLayout.refreshComplete();
    }


    public PtrFrameLayout getPtrFrameLayout() {
        return ptrFrameLayout;
    }

    protected int ptrLayoutID() {
        return R.layout.fragment_ptr;
    }

    protected void loadData() {
        Observable.timer(2, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          onLoadEnd();
                      }
                  });
    }
}
