package com.wy.report.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wy.report.base.constant.BundleKey;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.util.PhotoUtil;

import static com.wy.report.base.constant.ActivityRequestCode.CODE_OPEN_ALBUM;

/**
 * @author cantalou
 * @date 2017年12月01日 17:35
 */
public class StandardActivity extends BaseActivity {

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String fragmentClassName = intent.getStringExtra(BundleKey.BUNDLE_KEY_FRAGMENT_CLASS_NAME);
        try {
            Fragment fragment = (Fragment) Class.forName(fragmentClassName)
                                                .newInstance();
            getSupportFragmentManager().beginTransaction()
                                       .replace(android.R.id.content, fragment)
                                       .commitAllowingStateLoss();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isTranslucentStatusBar() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
