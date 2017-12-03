package com.wy.report.widget.indicator;

/*
 *
 * @author cantalou
 * @date 2017-12-02 18:26
 */
public interface IPagerNavigator {

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);

    void onAttachToMagicIndicator();


    void onDetachFromMagicIndicator();

    void notifyDataSetChanged();
}
