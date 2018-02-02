package com.wy.report.business.read.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;
import com.wy.report.widget.clip.view.ClipViewLayout;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-2-2 下午7:48
 * @description: ReadReport
 */
public class ClipImageFragment extends ToolbarFragment implements View.OnClickListener {

    private ClipViewLayout clipViewLayout1;
    private ClipViewLayout clipViewLayout2;
    private ImageView      back;
    private TextView       btnCancel;
    private TextView       btnOk;
    //类别 1: qq, 2: weixin
    private int            type;

    private Activity mActivity;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        type = getActivity().getIntent().getIntExtra("type", 2);
        mActivity = getActivity();
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        initView();
    }

    /**
     * 初始化组件
     */
    public void initView() {
        clipViewLayout1 = (ClipViewLayout) contentView.findViewById(R.id.clipViewLayout1);
        clipViewLayout2 = (ClipViewLayout) contentView.findViewById(R.id.clipViewLayout2);
        back = (ImageView) contentView.findViewById(R.id.iv_back);
        btnCancel = (TextView) contentView.findViewById(R.id.btn_cancel);
        btnOk = (TextView) contentView.findViewById(R.id.bt_ok);
        //设置点击事件监听器
        back.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (type == 1) {
            clipViewLayout1.setVisibility(View.VISIBLE);
            clipViewLayout2.setVisibility(View.GONE);
            //设置图片资源
            clipViewLayout1.setImageSrc(mActivity.getIntent().getData());
        } else {
            clipViewLayout2.setVisibility(View.VISIBLE);
            clipViewLayout1.setVisibility(View.GONE);
            //设置图片资源
            clipViewLayout2.setImageSrc(mActivity.getIntent().getData());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mActivity.finish();
                break;
            case R.id.btn_cancel:
                mActivity.finish();
                break;
            case R.id.bt_ok:
                generateUriAndReturn();
                break;
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle("选取头像");
    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        if (type == 1) {
            zoomedCropBitmap = clipViewLayout1.clip();
        } else {
            zoomedCropBitmap = clipViewLayout2.clip();
        }
        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(mActivity.getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = mActivity.getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            mActivity.setResult(RESULT_OK, intent);
            mActivity.finish();
        }
    }
    @Override
    protected int contentLayoutID() {
        return R.layout.activity_clip_image;
    }
}
