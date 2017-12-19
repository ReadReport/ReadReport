package com.wy.report.business.upload.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wy.report.R;
import com.wy.report.base.viewholder.BaseViewHolder;
import com.wy.report.business.upload.model.PictureModel;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-08 22:00
 */
public class ImageViewHolder extends BaseViewHolder<PictureModel> {

    @BindView(R.id.vh_select_image_image)
    ImageView selectedImage;

    @BindView(R.id.vh_select_image_delete)
    ImageView delete;

    public ImageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(PictureModel model) {
        super.onBindViewHolder(model);
        Glide.with(itemView.getContext())
             .load(model.getPath())
             .into(selectedImage);
    }
}
