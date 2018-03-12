package com.wy.report.business.read.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wy.report.R;
import com.wy.report.base.constant.BundleKey;
import com.wy.report.base.fragment.PtrListFragment;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.my.model.FamilyItemMode;
import com.wy.report.business.my.service.MyService;
import com.wy.report.business.read.mode.ReportItemMode;
import com.wy.report.business.read.mode.ReportListMode;
import com.wy.report.business.read.service.ReadService;
import com.wy.report.business.read.view.ManagePopMenu;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.helper.retrofit.subscriber.PtrSubscriber;
import com.wy.report.manager.auth.UserManger;
import com.wy.report.manager.router.AuthRouterManager;
import com.wy.report.util.DensityUtils;
import com.wy.report.util.ToastUtils;
import com.wy.report.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.Subscriber;


public class ReportManageFragment extends PtrListFragment<ReportItemMode, BaseViewHolder> {


    private ManagePopMenu mPopMenu;

    private boolean isPop;

    private ReadService readService;
    private MyService   myService;

    @BindView(R.id.toolbar_pop)
    TextView toolBarPop;
    private String uid;
    private int    pageConut;
    private int    page;
    //0单个 1所有
    private int    ifAll;
    private final int ALL = 1;
    private final int ONE = 0;


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ptrWithoutToolbar = true;
        readService = RetrofitHelper.getInstance()
                                    .create(ReadService.class);
        myService = RetrofitHelper.getInstance()
                                  .create(MyService.class);
        uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        ifAll = ALL;
    }

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        initPopMenu();
        toolBarPop.setText("全部");
        toolBarPop.setClickable(true);
        mPopMenu.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopMenu.setOutsideTouchable(false);
        mPopMenu.setFocusable(true);
        mPopMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toolBarPop.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_read_manage_nav_up, 0);
                isPop = false;
            }
        });
        toolBarPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPop) {
                    mPopMenu.showAsDropDown(toolbar, toolbar.getMeasuredWidth() - mPopMenu.getWidth()
                            , 0);
                    toolBarPop.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_read_manage_nav_down, 0);
                    isPop = true;
                } else {
                    mPopMenu.dismiss();
                    toolBarPop.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.selector_read_manage_nav_up, 0);
                    isPop = false;
                }
            }
        });
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        ptrFrameLayout.autoRefresh();
        quickAdapter.bindToRecyclerView(recyclerView);

        recyclerView.setPadding(0,DensityUtils.dip2px(getActivity(), 10),0,0);

        recyclerView.setBackgroundColor(getResources().getColor(R.color.hui_f9f9f9));
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, DensityUtils.dip2px(getActivity(), 10), getResources().getColor(R.color.hui_f9f9f9)));
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
        toolBarPop.setText("全部");
        page = 0;
        ifAll = ALL;
        uid = String.valueOf(UserManger.getInstance().getLoginUser().getId());
        getList(uid, page, ifAll);
        myService.getFamily(uid).subscribe(new Subscriber<ResponseModel<List<FamilyItemMode>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseModel<List<FamilyItemMode>> listResponseModel) {
                FamilyItemMode familyItemMode = new FamilyItemMode();
                familyItemMode.setId("-1");
                familyItemMode.setName("全部");
                List<FamilyItemMode> newData = new ArrayList<>();
                newData.add(familyItemMode);
                newData.addAll(listResponseModel.getData());
                mPopMenu.setNewData(newData);
            }
        });
    }

    private void initPopMenu() {
        mPopMenu = new ManagePopMenu(getActivity());
        mPopMenu.setOnPopItemClick(new ManagePopMenu.OnPopItemClick() {
            @Override
            public void onPopItemClick(FamilyItemMode item) {
                toolBarPop.setText(item.getName());
                //所有
                if (item.getId().equals("-1")) {
                    loadData();
                } else {
                    //单个
                    uid = item.getId();
                    page = 0;
                    ifAll = ONE;
                    getList(uid, page, ifAll);
                }
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
        if (page > pageConut) {
            ToastUtils.showLong(getString(R.string.report_not_more_data));
            ptrFrameLayout.refreshComplete();
            return;
        }
        readService.getReportList(uid, page, ifAll).subscribe(new PtrSubscriber<ResponseModel<ReportListMode>>(this) {
            @Override
            public void onNext(ResponseModel<ReportListMode> listResponseModel) {
                super.onNext(listResponseModel);
                quickAdapter.addData(listResponseModel.getData().getData());
            }
        });
    }

    @Override
    public void handlePtrError(Throwable t) {
        super.handlePtrError(t);
    }

    @Override
    public void handlePtrSuccess(Object o) {
        super.handlePtrSuccess(o);
        initRecyleEmptyView();
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
        TextView     messageNum   = reportItemModeBaseViewHolder.getView(R.id.report_manage_message_num);
        LinearLayout messageNumLl = reportItemModeBaseViewHolder.getView(R.id.report_manage_message_num_ll);
        switch (reportItemMode.getParseState()) {
            case ReportItemMode.READ_STATE_UNSUBMIT:
                stateIcon.setImageResource(R.drawable.list_unsubmitted);

                stateTitle.setText(getText(R.string.report_manage_state_uncommit));
                stateTitle.setTextColor(getResources().getColor(R.color.hong_f84d4d));

                stateTip.setVisibility(View.INVISIBLE);

                messageNumLl.setVisibility(View.INVISIBLE);

                sub2Doctor.setVisibility(View.VISIBLE);
                sub2Doctor.setText(getString(R.string.report_manage_submit_to_doctor));
                break;
            case ReportItemMode.READ_STATE_UNREAD:
                stateIcon.setImageResource(R.drawable.list_notread);

                stateTitle.setText(getText(R.string.report_manage_state_unread));
                stateTitle.setTextColor(getResources().getColor(R.color.hong_f84d4d));

                stateTip.setVisibility(View.VISIBLE);
                stateTip.setText(getString(R.string.report_manage_waiting_tip));

                messageNumLl.setVisibility(View.INVISIBLE);

                sub2Doctor.setVisibility(View.INVISIBLE);
                break;
            case ReportItemMode.READ_STATE_UNGTE:
                stateIcon.setImageResource(R.drawable.list_getin);

                stateTitle.setText(getText(R.string.report_manage_state_getting));
                stateTitle.setTextColor(getResources().getColor(R.color.hong_f84d4d));

                stateTip.setVisibility(View.VISIBLE);
                stateTip.setText(getString(R.string.report_manage_waiting_tip));

                messageNumLl.setVisibility(View.INVISIBLE);

                sub2Doctor.setVisibility(View.INVISIBLE);
                break;
            case ReportItemMode.READ_STATE_GETFAILED:
                stateIcon.setImageResource(R.drawable.list_failed);

                stateTitle.setText(getText(R.string.report_manage_state_get_failed));
                stateTitle.setTextColor(getResources().getColor(R.color.hong_f84d4d));

                stateTip.setVisibility(View.INVISIBLE);

                messageNumLl.setVisibility(View.INVISIBLE);

                sub2Doctor.setVisibility(View.VISIBLE);
                sub2Doctor.setText(getString(R.string.report_manage_reget));
                break;
            case ReportItemMode.READ_STATE_READED:
                stateIcon.setImageResource(R.drawable.list_interpreted);

                stateTitle.setText(getText(R.string.report_manage_state_readed));
                stateTitle.setTextColor(getResources().getColor(R.color.lv_00bf4e));

                stateTip.setVisibility(View.VISIBLE);
                stateTip.setText(String.format(getString(R.string.report_manage_score), reportItemMode.getScore()));

                if (reportItemMode.getUnReadMsgNum() > 0) {
                    messageNumLl.setVisibility(View.VISIBLE);
                    messageNum.setText(String.valueOf(reportItemMode.getUnReadMsgNum()));
                } else {
                    messageNumLl.setVisibility(View.GONE);
                }

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
                ToastUtils.showCenter(R.string.report_manage_get_failed_tips);
                break;
            case ReportItemMode.READ_STATE_UNGTE:
                ToastUtils.showCenter(R.string.report_manage_getting_tips);
                break;
            case ReportItemMode.READ_STATE_UNSUBMIT:
            case ReportItemMode.READ_STATE_UNREAD:
            case ReportItemMode.READ_STATE_READED:
                Bundle bundle = new Bundle();
                bundle.putString(BundleKey.BUNDLE_KEY_REPORT_ID, item.getId());
                AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_REPORT_DETAIL, bundle);
                break;
            default:
                break;
        }
    }


    /**
     * @param uid
     * @param page
     * @param ifAll 0单个成员 1所有成员
     */
    private void getList(String uid, int page, int ifAll) {
        readService.getReportList(uid, page, ifAll).subscribe(new PtrSubscriber<ResponseModel<ReportListMode>>(this) {
            @Override
            public void onNext(ResponseModel<ReportListMode> listResponseModel) {
                super.onNext(listResponseModel);
                quickAdapter.setNewData(listResponseModel.getData().getData());
                pageConut = Math.round(listResponseModel.getData().getCount() / 10);
            }
        });
    }


    private void initRecyleEmptyView() {

        quickAdapter.setEmptyView(R.layout.view_report_manage_empty);
        quickAdapter.getEmptyView().findViewById(R.id.report_manage_empty_get_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_REPORT_QUERY);
            }
        });
        quickAdapter.getEmptyView().findViewById(R.id.report_manage_empty_upload_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_REPORT_UPLOAD);
            }
        });
        quickAdapter.getEmptyView().findViewById(R.id.top_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(BundleKey.BUNDLE_KEY_REPORT_ID, "1");
                AuthRouterManager.getInstance().getRouter().open(getActivity(), AuthRouterManager.ROUTER_REPORT_DETAIL, bundle);
            }
        });
    }
}
