package com.wy.report.business.upload.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.business.upload.model.UnitModel;
import com.wy.report.business.upload.service.HospitalService;
import com.wy.report.helper.retrofit.RetrofitHelper;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-10 23:02
 */
public class ChainUnitFragment extends PtrListFragment {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    private HospitalService hospitalService;

    @Override
    protected void initData(Bundle savedInstanceState) {
        hospitalService = RetrofitHelper.getInstance()
                                        .create(HospitalService.class);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_hospital_chain_unit;
    }

    @Override
    protected BaseQuickAdapter createAdapter() {
        return new BaseQuickAdapter<UnitModel, BaseViewHolder>(R.layout.vh_hospital_unit) {
            @Override
            protected void convert(BaseViewHolder helper, UnitModel item) {

            }
        };
    }
}
