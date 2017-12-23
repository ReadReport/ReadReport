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
public abstract class PtrFragment extends NetworkFragment implements PtrHandler {

    protected PtrFrameLayout ptrFrameLayout;

    protected boolean hasLoadData = false;

    /**
     * 下拉刷新去掉toolbar
     */
    protected boolean ptrWithoutToolbar = false;

    @Nullable
    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //Clean the placeholder view
        ViewGroup prtContainer = (ViewGroup) inflater.inflate(ptrLayoutID(), container, false);
        prtContainer.removeView(prtContainer.findViewById(R.id.ptr_container_placeholder));
        ReflectUtil.set(prtContainer, "mContent", null);

        if (ptrWithoutToolbar) {
            //re init view
            ViewGroup root = (ViewGroup) super.createView(inflater, container, savedInstanceState);
            //remove toolbar content
            root.removeView(toolbarContentView);
            //ptr view add toolbar content
            prtContainer.addView(toolbarContentView);

            ReflectUtil.invoke(prtContainer, "onFinishInflate");
            //add ptr
            if ((TOOL_BAR_FLAG_OVERLAY & toolbarFlag()) == TOOL_BAR_FLAG_OVERLAY) {
                root.addView(prtContainer, 0);
            } else {
                root.addView(prtContainer);
            }
            return root;
        } else {
            //re init view
            ViewGroup contentView = (ViewGroup) super.createView(inflater, container, savedInstanceState);
            prtContainer.addView(contentView);
            ReflectUtil.invoke(prtContainer, "onFinishInflate");
            return prtContainer;
        }
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
        if (ptrWithoutToolbar) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, toolbarContentView, header);
        } else {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !hasLoadData) {
            loadData();
        }
    }

    public void performRefresh() {
        ptrFrameLayout.autoRefresh(true);
    }

    public void onPtrStart() {
    }

    public void onPtrError() {
        ptrFrameLayout.refreshComplete();
    }

    public void onPtrSuccess() {
        ptrFrameLayout.refreshComplete();
    }

    public PtrFrameLayout getPtrFrameLayout() {
        return ptrFrameLayout;
    }

    public void handlePtrStart() {
    }

    public void handlePtrError(Throwable t) {
        ptrFrameLayout.refreshComplete();
    }

    public void handlePtrSuccess(Object o) {
        ptrFrameLayout.refreshComplete();
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
                          handlePtrSuccess(null);
                      }
                  });
    }
}
