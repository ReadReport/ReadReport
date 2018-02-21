package com.wy.report.base.fragment;

import android.support.annotation.CallSuper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;


import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

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
 * @date 2017-11-24 22:54
 */
public abstract class PtrListFragment<T, K extends BaseViewHolder> extends PtrFragment implements PtrHandler,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recycle_view)
    protected RecyclerView recyclerView;

    protected BaseQuickAdapter<T, K> quickAdapter;

    protected LinearLayoutManager layoutManager;

    @Override
    @CallSuper
    protected void initView(View contentView) {
        super.initView(contentView);
        initRecycleView();
    }

    protected void initRecycleView() {
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        quickAdapter = createAdapter();
        quickAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(quickAdapter);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler2.checkContentCanBePulledDown(frame, recyclerView, header);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_ptr_recycleview;
    }

    protected abstract BaseQuickAdapter<T, K> createAdapter();

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
