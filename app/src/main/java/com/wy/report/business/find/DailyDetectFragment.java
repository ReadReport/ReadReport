package com.wy.report.business.find;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wy.report.R;
import com.wy.report.base.fragment.ToolbarFragment;

/*
 *
 * @author cantalou
 * @date 2017-12-26 21:14
 */
public class DailyDetectFragment extends ToolbarFragment implements Toolbar.OnMenuItemClickListener {

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected int contentLayoutID() {
        return R.layout.fragment_find_daily_detect;
    }

    @Override
    protected int getMenuLayoutId() {
        return R.menu.menu_find_daily_detect;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find_daily_detect_more: {
                break;
            }
        }
        return false;
    }
}
