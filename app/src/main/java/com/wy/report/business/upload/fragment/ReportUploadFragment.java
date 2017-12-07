package com.wy.report.business.upload.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *
 * @author cantalou
 * @date 2017-12-05 21:20
 */
public class ReportUploadFragment extends ToolbarFragment {

    @BindView(R.id.report_upload_medical_examiner_name)
    TextView name;

    @BindView(R.id.report_upload_examine_time_name)
    TextView time;

    @BindView(R.id.report_upload_examine_hospital_name)
    TextView hospital;

    @BindView(R.id.report_upload_remark_name)
    EditText remark;

    @BindView(R.id.report_upload_image_rv)
    RecyclerView imageRecycleView;


    @Override
    protected void initData() {

    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_report_upload;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_upload);
    }

    @OnClick({R.id.report_upload_medical_examiner_name, R.id.report_upload_medical_examiner_more})
    public void nameClick() {

    }

    @OnClick({R.id.report_upload_examine_time_name, R.id.report_upload_examine_time_more})
    public void timeClick() {

    }

    @OnClick({R.id.report_upload_examine_hospital_name, R.id.report_upload_examine_hospital_more})
    public void hospitalClick() {

    }
}
