package com.wy.report.manager.massage;

import com.hwangjr.rxbus.RxBus;
import com.wy.report.base.constant.RxKey;
import com.wy.report.base.model.ResponseModel;
import com.wy.report.business.home.model.MsgNumModel;
import com.wy.report.business.my.service.MyService;
import com.wy.report.helper.retrofit.RetrofitHelper;
import com.wy.report.manager.auth.UserManger;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/*
 *
 * @author cantalou
 * @date 2017-12-02 16:51
 */
public class MessageManager {

    private UserManger userManger;

    private int unreadMessageCount = 0;

    static final class InstanceHolder {
        static final MessageManager instance = new MessageManager();
    }

    private MessageManager() {
        userManger = UserManger.getInstance();
    }

    public boolean hasNewMessage() {
        return userManger.isLogin() && unreadMessageCount > 0;
    }

    public static MessageManager getInstance() {
        return InstanceHolder.instance;
    }

    public int getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
        RxBus.get().post(RxKey.RX_NEW_MESSAGE, new Object());
    }

    public void notifyAllMessageRead() {
        unreadMessageCount = 0;
        RxBus.get().post(RxKey.RX_NEW_MESSAGE, new Object());

    }

    public void getMessage() {
        MyService mMyService;
        if (UserManger.getInstance().isLogin()) {
            String uid = UserManger.getInstance().getLoginUser().getId();
            //获取消息数量
            mMyService = RetrofitHelper.getInstance().create(MyService.class);
            mMyService.getUnreadMsgNum(uid).subscribe(new Subscriber<ResponseModel<MsgNumModel>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ResponseModel<MsgNumModel> msgNumModelResponseModel) {
                    MessageManager.getInstance().setUnreadMessageCount(msgNumModelResponseModel.getData().getNum());
                }
            });
        }
    }

    public interface OnMessageChangeListener {
        void onAllMessageRead();

        void onNewUnreadMessageCount(int count);
    }
}
