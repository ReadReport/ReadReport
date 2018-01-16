package com.wy.report.business.upload.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.upload.model.UnitModel;
import com.wy.report.business.upload.service.HospitalService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;

import java.util.List;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-10 23:02
 */
public class ChainUnitFragment extends PtrListFragment<UnitModel, BaseViewHolder> {

    private HospitalService hospitalService;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        hospitalService = RetrofitHelper.getInstance()
                                        .create(HospitalService.class);
        ptrWithoutToolbar = false;
    }

    @Override
    protected void loadData() {
        hospitalService.getChainUnits()
                       .subscribe(new PtrSubscriber<ResponseModel<List<UnitModel>>>(this) {
                           @Override
                           public void onNext(ResponseModel<List<UnitModel>> listResponseModel) {
                               super.onNext(listResponseModel);
                               quickAdapter.setNewData(listResponseModel.getData());
                           }
                       });
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_hospital_chain_unit;
    }

    @Override
    protected BaseQuickAdapter createAdapter() {
        return new HospitalAdapter(null);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        rxBus.post(RxKey.RX_HOSPITAL_UNIT_SELECT, quickAdapter.getItem(position));
        getActivity().finish();
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }

    private class HospitalAdapter extends BaseMultiItemQuickAdapter<UnitModel, BaseViewHolder> {

        public HospitalAdapter(List<UnitModel> data) {
            super(data);
            addItemType(UnitModel.TYPE_TITLE, R.layout.vh_hospital_unit);
            addItemType(UnitModel.TYPE_HOSPITAL, R.layout.vh_hospital_unit);
        }

        @Override
        protected void convert(BaseViewHolder helper, UnitModel item) {
            helper.setText(R.id.vh_hospital_title, item.getTitle());
        }
    }
}
