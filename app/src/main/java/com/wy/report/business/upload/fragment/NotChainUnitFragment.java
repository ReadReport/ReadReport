package com.wy.report.business.upload.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-10 23:02
 */
public class NotChainUnitFragment extends PtrFragment {


    @BindView(R.id.recycle_view_left)
    RecyclerView recycleViewLeft;

    @BindView(R.id.recycle_view_right)
    RecyclerView recycleViewRight;

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        recycleViewLeft.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewRight.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_hospital_not_chain_unit;
    }
}
