package com.wy.report.business.my.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wy.report.R;
import com.wy.report.base.fragment.NetworkFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.NetworkSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.util.StringUtils;
import com.wy.report.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-1-8 下午6:21
 * @description: ReadReport
 */
public class FeedbackFragment extends NetworkFragment {

    @BindView(R.id.feedback_content)
    EditText content;

    MyService myService;

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        setTitle(R.string.feedback_title);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        myService = RetrofitHelper.getInstance().create(MyService.class);
    }

    @OnClick(R.id.feedback_commit)
    public void modifyPwd() {
        if (StringUtils.isNotBlank(content)) {
            String id  = String.valueOf(UserManger.getInstance().getLoginUser().getId());
            String con = content.getText().toString();
            myService.feedback(id, con).subscribe(new NetworkSubscriber<ResponseModel>(this) {
                @Override
                public void onNext(ResponseModel responseModel) {
                    super.onNext(responseModel);
                    ToastUtils.showLong(R.string.feedback_success_tip);
                }
            });
        } else {
            ToastUtils.showLong(R.string.feedback_empty_tip);
        }
    }
}
