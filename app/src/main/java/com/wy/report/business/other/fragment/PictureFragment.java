package com.wy.report.business.other.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.ToolbarFragment;

import java.util.List;

import butterknife.BindView;

/*
 *
 * @author cantalou
 * @date 2017-12-09 13:56
 */
public class PictureFragment extends ToolbarFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<String> paths;

    private int index;

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        paths = bundle.getStringArrayList(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST);
        index = bundle.getInt(BundleKey.BUNDLE_KEY_PICTURE_PATH_LIST_INDEX);
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return paths.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getActivity());
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                container.addView(imageView, lp);
                Glide.with(getActivity())
                     .load(paths.get(position))
                     .into(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeViewAt(position);
            }
        });
        viewPager.setCurrentItem(index);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_picture;
    }
}
