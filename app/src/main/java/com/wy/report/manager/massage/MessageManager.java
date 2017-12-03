package com.wy.report.manager.massage;

import com.wy.report.manager.auth.UserManger;

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
}
