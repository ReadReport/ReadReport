package com.wy.report.business.upload.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wy.report.business.upload.model.PictureModel;
import com.wy.report.business.upload.viewholder.ImageViewHolder;

/*
 *
 * @author cantalou
 * @date 2017-12-08 22:31
 */
public class SelectImageAdapter extends BaseQuickAdapter<PictureModel, ImageViewHolder> {

    public SelectImageAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

   @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    protected void convert(ImageViewHolder helper, PictureModel item) {
        helper.onBindViewHolder(item);
    }
}
