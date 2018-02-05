package com.wy.report.business.read.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import com.wy.report.R;
import com.wy.report.base.activity.StandardActivity;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.business.read.fragment.AskFragment;
import com.wy.report.util.SoftHideKeyBoardUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-2-3 下午8:32
 * @description: ReadReport
 */
public class AskActivity extends StandardActivity {


    @BindView(R.id.input_content)
    EditText mContent;


    @Override
    protected void initView() {
        super.initView();
        SoftHideKeyBoardUtil.assistActivity(this);
        Intent intent            = getIntent();
        String fragmentClassName = intent.getStringExtra(BundleKey.BUNDLE_KEY_FRAGMENT_CLASS_NAME);
        try {
            fragment = (Fragment) Class.forName(fragmentClassName)
                                       .newInstance();
            fragment.setArguments(intent.getExtras());
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.ask_fragment_content, fragment)
                                       .commitAllowingStateLoss();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int contentLayoutID() {
        return R.layout.activity_ask;
    }


    @OnClick(R.id.input_submit)
    public void submit() {
        ((AskFragment)fragment).submitContent(mContent);
    }
}
