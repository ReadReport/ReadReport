package com.wy.report.business.splash.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.wy.report.R;
import com.wy.report.base.fragment.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.BindViews;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author cantalou
 * @date 2018年02月08日 15:41
 * <p>
 */
public class OnboardingFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindViews({R.id.activity_onboarding_indicator_0, R.id.activity_onboarding_indicator_1, R.id.activity_onboarding_indicator_2})
    ImageView[] indicator;

    @BindView(R.id.activity_onboarding_indicator_container)
    View indicatorContainer;

    private int position;

    private int[] backgroundIds = new int[]{R.drawable.default_one, R.drawable.default_two, R.drawable.default_three};

    @Override
    protected void initView(View contentView) {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return backgroundIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView iv = new ImageView(getActivity());
                iv.setImageResource(backgroundIds[position]);
                if (position == backgroundIds.length - 1) {
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            authRouterManager.openHome(getActivity());
                            Observable.timer(200, TimeUnit.MILLISECONDS)
                                      .subscribe(new Action1<Long>() {
                                          @Override
                                          public void call(Long aLong) {
                                              getActivity().finish();
                                          }
                                      });
                        }
                    });
                }
                container.addView(iv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                return iv;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if (object instanceof View) {
                    container.removeView((View) object);
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                OnboardingFragment.this.position = position;

                if (position == backgroundIds.length - 1) {
                    indicatorContainer.setVisibility(View.GONE);
                    return;
                }

                indicatorContainer.setVisibility(View.VISIBLE);

                for (int i = 0; i < position; i++) {
                    indicator[i].setImageResource(R.drawable.point_pass);
                }

                indicator[position].setImageResource(R.drawable.point_selected);

                for (int i = position + 1; i < backgroundIds.length; i++) {
                    indicator[i].setImageResource(R.drawable.point_normal);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_onboarding;
    }
}
