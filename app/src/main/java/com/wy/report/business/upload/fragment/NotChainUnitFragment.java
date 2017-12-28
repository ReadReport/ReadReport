package com.wy.report.business.upload.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.PtrFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.upload.model.HospitalCityModel;
import com.wy.report.business.upload.model.HospitalProvinceModel;
import com.wy.report.business.upload.model.UnitModel;
import com.wy.report.business.upload.service.HospitalService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

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

    private BaseQuickAdapter<HospitalProvinceModel, BaseViewHolder> adapterLeft;

    private BaseQuickAdapter<UnitModel, BaseViewHolder> adapterRight;

    private HospitalService hospitalService;

    private int selectedProvince = 0;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        hospitalService = RetrofitHelper.getInstance()
                                        .create(HospitalService.class);
        ptrWithoutToolbar = false;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        adapterLeft = new BaseQuickAdapter<HospitalProvinceModel, BaseViewHolder>(R.layout.vh_hospital_province) {
            @Override
            protected void convert(BaseViewHolder helper, HospitalProvinceModel item) {
                TextView province = helper.getView(R.id.vh_hospital_province);
                province.setText(item.getProvince());
                Resources res = getResources();
                province.setTextColor(item.isSelected() ? res.getColor(R.color.lan_30acff) : res.getColor(R.color.hei_333333));
            }
        };
        adapterLeft.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showProvinceUnitData(position);
            }
        });
        recycleViewLeft.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewLeft.setAdapter(adapterLeft);

        adapterRight = new BaseQuickAdapter<UnitModel, BaseViewHolder>(R.layout.vh_hospital_unit) {
            @Override
            protected void convert(BaseViewHolder helper, UnitModel item) {
                helper.setText(R.id.vh_hospital_title, item.getTitle());
            }
        };
        adapterRight.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                rxBus.post(RxKey.RX_HOSPITAL_UNIT_SELECT, adapterRight.getItem(position));
                getActivity().finish();
            }
        });
        recycleViewRight.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewRight.setAdapter(adapterRight);
    }


    @Override
    protected void loadData() {
        hospitalService.getNotChainUnits()
                       .subscribe(new PtrSubscriber<ResponseModel<List<HospitalProvinceModel>>>(this) {
                           @Override
                           public void onNext(ResponseModel<List<HospitalProvinceModel>> listResponseModel) {
                               super.onNext(listResponseModel);
                               adapterLeft.setNewData(listResponseModel.getData());
                               showProvinceUnitData(0);
                           }
                       });
    }

    private void showProvinceUnitData(int position) {

        HospitalProvinceModel provinceModel = adapterLeft.getItem(selectedProvince);
        if (provinceModel != null) {
            provinceModel.setSelected(false);
            adapterLeft.notifyItemChanged(selectedProvince);
        }

        provinceModel = adapterLeft.getItem(position);
        provinceModel.setSelected(true);
        adapterLeft.notifyItemChanged(position);
        List<UnitModel> allUnits = new ArrayList<>();
        for (HospitalCityModel cityModel : provinceModel.getCity()) {
            allUnits.addAll(cityModel.getUnits());
        }
        adapterRight.setNewData(allUnits);
        selectedProvince = position;
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_hospital_not_chain_unit;
    }

    @Override
    protected int toolbarFlag() {
        return 0;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return super.checkCanDoRefresh(frame, content, header) && PtrDefaultHandler.checkContentCanBePulledDown(frame, recycleViewLeft, header)
                && PtrDefaultHandler.checkContentCanBePulledDown(frame, recycleViewRight, header);
    }
}
