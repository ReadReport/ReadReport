package com.wy.report.business.picture.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wy.report.R;
import com.wy.report.base.constant.ActivityRequestCode;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.fragment.BaseFragment;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.util.PhotoUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author cantalu
 * @date 2018年02月13日 15:50
 * <p>
 */
public class CameraPreviewFragment extends BaseFragment {

    @BindView(R.id.fragment_camera_preview_picture)
    ImageView content;

    @BindView(R.id.preview_content_container)
    View contentContainer;

    private String path ;

    private boolean fromActivityResult = true;

    @Override
    protected void initData(Bundle savedInstanceState) {
        getActivity().getWindow()
                     .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.initData(savedInstanceState);
        PhotoUtil.openCamera(this);
    }

    @Override
    protected void initView(View contentView) {
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_camera_preview;
    }

    @OnClick(R.id.fragment_camera_preview_recapture)
    public void reCapture() {
        PhotoUtil.openCamera(this);
    }

    @OnClick(R.id.fragment_camera_preview_use)
    public void usePicture() {
        rxBus.post(RxKey.RX_PICTURE_CHOOSE_CAMERA_RESULT, new PictureModel(path));
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ActivityRequestCode.CODE_OPEN_CAMERA: {
                path = PhotoUtil.onActivityResult(this, requestCode, resultCode, data);
                if (TextUtils.isEmpty(path)) {
                    getActivity().finish();
                    return;
                }
                contentContainer.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                     .load(path)
                     .into(content);
                break;
            }
        }
        fromActivityResult = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!fromActivityResult){
            getActivity().finish();
        }
        fromActivityResult = false;
    }
}
