package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;

import com.wy.report.R;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;

/**
 * 编辑成员
 *
 * @author cantalou
 * @date 2017-11-26 23:04
 */
public class EditFamilyFragment extends NetworkFragment {


    private MyService myService;

    private String uid;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        setTitle(getString(R.string.edit_family_title));
    }

    @Override
    protected int contentLayoutID() {
        return 0;
    }




    @Override
    protected int toolbarFlag() {
        return TOOL_BAR_FLAG_SHOW;
    }


}
