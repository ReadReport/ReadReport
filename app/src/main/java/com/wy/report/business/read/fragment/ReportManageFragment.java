package com.wy.report.business.read.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.read.mode.ReportListMode;
import com.wy.report.business.read.mode.ReportItemMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.business.read.view.ManagePopMenu;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.ToastUtils;
import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;


public class ReportManageFragment extends PtrListFragment<ReportItemMode, BaseViewHolder> {


    private ManagePopMenu mPopMenu;

    private boolean isPop;

    private ReadService readService;

    @BindView(R.id.toolbar_pop)
    TextView toolBarPop;
    private String uid;
    private int    pageConut;
    private int    page;


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ptrWithoutToolbar = true;
        readService = RetrofitHelper.getInstance()
                                    .create(ReadService.class);
        uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());

    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        initPopMenu();
        toolBarPop.setText("全部");
        toolBarPop.setClickable(true);
        toolBarPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPop) {
                    mPopMenu.dismiss();
                } else {
                    mPopMenu.showAtAnchorView(toolbar, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT, 0, 0);
                }
            }
        });
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        ptrFrameLayout.autoRefresh();
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        setTitle(R.string.report_manage);
    }

    @Override
    protected int toolbarLayoutID() {
        return R.layout.view_read_manage_toolbar;
    }


    @Override
    protected BaseQuickAdapter<ReportItemMode, BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<ReportItemMode, BaseViewHolder>(R.layout.view_report_item) {
            @Override
            protected void convert(BaseViewHolder reportItemModeBaseViewHolder, final ReportItemMode reportItemMode) {
                setItem(reportItemModeBaseViewHolder, reportItemMode);
                reportItemModeBaseViewHolder.setOnClickListener(R.id.report_manage_submit_to_hospital, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reportItemMode.getParseState() == ReportItemMode.READ_STATE_UNSUBMIT) {
                            submit2Doctor(reportItemMode);
                        } else if (reportItemMode.getParseState() == ReportItemMode.READ_STATE_GETFAILED) {
                            getSingleReport(reportItemMode);
                        }
                    }
                });
            }
        };
    }


    @Override
    protected void loadData() {
        page = 0;
        readService.getReportList(uid, page, 0).subscribe(new PtrSubscriber<ResponseModel<ReportListMode>>(this) {
            @Override
            public void onNext(ResponseModel<ReportListMode> listResponseModel) {
                super.onNext(listResponseModel);
                quickAdapter.setNewData(listResponseModel.getData().getData());
                pageConut = listResponseModel.getData().getCount();
            }
        });
    }

    private void initPopMenu() {
        mPopMenu = new ManagePopMenu(getActivity());
        mPopMenu.createPopup();
        mPopMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toolBarPop.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_read_manage_nav_up, 0);
                isPop = false;
            }
        });
        mPopMenu.setOnAttachedWindowListener(new EasyPopup.OnAttachedWindowListener() {
            @Override
            public void onAttachedWindow(int i, int i1, EasyPopup easyPopup) {
                toolBarPop.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_read_manage_nav_down, 0);
                isPop = true;
            }
        });
    }

    private String generateName(String name, String relationShip) {
        return name + " (" + relationShip + ")";
    }

    private String generateUploadTime(String uploadTime) {
        return String.format(getString(R.string.report_manage_upload_time), uploadTime);
    }

    @Override
    protected void loadNext() {
        page++;
        if (page > pageConut - 1) {
            ToastUtils.showLong(getString(R.string.report_not_more_data));
            return;
        }
        readService.getReportList(uid, page, 0).subscribe(new PtrSubscriber<ResponseModel<ReportListMode>>(this) {
            @Override
            public void onNext(ResponseModel<ReportListMode> listResponseModel) {
                super.onNext(listResponseModel);
                quickAdapter.addData(listResponseModel.getData().getData());
            }
        });
    }

    private void setItem(BaseViewHolder reportItemModeBaseViewHolder, ReportItemMode reportItemMode) {
        reportItemModeBaseViewHolder.setText(R.id.report_manage_name, generateName(reportItemMode.getName(), reportItemMode.getRelationship()));
        reportItemModeBaseViewHolder.setText(R.id.report_manage_time, generateUploadTime(reportItemMode.getUploadTime()));
        reportItemModeBaseViewHolder.setText(R.id.report_manage_hospital, reportItemMode.getHospital());

        //状态图标
        ImageView stateIcon = reportItemModeBaseViewHolder.getView(R.id.report_mange_state_icon);
        //状态标题
        TextView stateTitle = reportItemModeBaseViewHolder.getView(R.id.report_manage_state);
        //状态提示
        TextView stateTip = reportItemModeBaseViewHolder.getView(R.id.report_manage_state_tip);
        //提交摁钮
        TextView sub2Doctor = reportItemModeBaseViewHolder.getView(R.id.report_manage_submit_to_hospital);
        //消息数量
        TextView messageNum = reportItemModeBaseViewHolder.getView(R.id.report_manage_message_num);
        switch (reportItemMode.getParseState()) {
            case ReportItemMode.READ_STATE_UNSUBMIT:
                stateIcon.setImageResource(R.drawable.list_unsubmitted);

                stateTitle.setText(getText(R.string.report_manage_state_uncommit));
                stateTitle.setTextColor(getResources().getColor(R.color.hong_f84d4d));

                stateTip.setVisibility(View.INVISIBLE);

                messageNum.setVisibility(View.INVISIBLE);

                sub2Doctor.setVisibility(View.VISIBLE);
                sub2Doctor.setText(getString(R.string.report_manage_submit_to_doctor));
                break;
            case ReportItemMode.READ_STATE_UNREAD:
                stateIcon.setImageResource(R.drawable.list_notread);

                stateTitle.setText(getText(R.string.report_manage_state_unread));
                stateTitle.setTextColor(getResources().getColor(R.color.hong_f84d4d));

                stateTip.setVisibility(View.VISIBLE);
                stateTip.setText(getString(R.string.report_manage_waiting_tip));

                messageNum.setVisibility(View.INVISIBLE);

                sub2Doctor.setVisibility(View.INVISIBLE);
                break;
            case ReportItemMode.READ_STATE_UNGTE:
                stateIcon.setImageResource(R.drawable.list_getin);

                stateTitle.setText(getText(R.string.report_manage_state_getting));
                stateTitle.setTextColor(getResources().getColor(R.color.hong_f84d4d));

                stateTip.setVisibility(View.VISIBLE);
                stateTip.setText(getString(R.string.report_manage_waiting_tip));

                messageNum.setVisibility(View.INVISIBLE);

                sub2Doctor.setVisibility(View.INVISIBLE);
                break;
            case ReportItemMode.READ_STATE_GETFAILED:
                stateIcon.setImageResource(R.drawable.list_failed);

                stateTitle.setText(getText(R.string.report_manage_state_get_failed));
                stateTitle.setTextColor(getResources().getColor(R.color.hong_f84d4d));

                stateTip.setVisibility(View.INVISIBLE);

                messageNum.setVisibility(View.INVISIBLE);

                sub2Doctor.setVisibility(View.VISIBLE);
                sub2Doctor.setText(getString(R.string.report_manage_reget));
                break;
            case ReportItemMode.READ_STATE_READED:
                stateIcon.setImageResource(R.drawable.list_interpreted);

                stateTitle.setText(getText(R.string.report_manage_state_readed));
                stateTitle.setTextColor(getResources().getColor(R.color.lv_00bf4e));

                stateTip.setVisibility(View.VISIBLE);
                stateTip.setText(String.format(getString(R.string.report_manage_score), reportItemMode.getScore()));

                messageNum.setVisibility(View.VISIBLE);
                messageNum.setText(String.valueOf(reportItemMode.getUnReadMsgNum()));

                sub2Doctor.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    private void submit2Doctor(final ReportItemMode mode) {
        readService.sumbitReport2Doctor(mode.getId()).subscribe(new PtrSubscriber<ResponseModel>(this) {
            @Override
            public void onNext(ResponseModel responseModel) {
                super.onNext(responseModel);
                ToastUtils.showLong("已提交");
                for (ReportItemMode mode1 : quickAdapter.getData()) {
                    if (mode1.getId().equals(mode.getId())) {
                        mode1.setParseState(ReportItemMode.READ_STATE_UNREAD);
                        quickAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });

    }

    private void getSingleReport(ReportItemMode mode) {
        readService.getReportList(mode.getMemberId(), 0, 0).subscribe(new PtrSubscriber<ResponseModel<ReportListMode>>(this) {
            @Override
            public void onNext(ResponseModel<ReportListMode> listResponseModel) {
                super.onNext(listResponseModel);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        ReportItemMode item = (ReportItemMode) adapter.getItem(position);

        switch (item.getParseState()) {
            case ReportItemMode.READ_STATE_GETFAILED:
                ToastUtils.showLong(R.string.report_manage_get_failed_tips);
                break;
            case ReportItemMode.READ_STATE_UNGTE:
                ToastUtils.showLong(R.string.report_manage_getting_tips);
                break;
            case ReportItemMode.READ_STATE_UNSUBMIT:
            case ReportItemMode.READ_STATE_UNREAD:
            case ReportItemMode.READ_STATE_READED:
                Bundle bundle = new Bundle();
                bundle.putString(BundleKey.BUNDLE_KEY_REPORT_ID, item.getId());
                AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_REPORT_DETAIL,bundle);
                break;
            default:
                break;
        }
    }
}
