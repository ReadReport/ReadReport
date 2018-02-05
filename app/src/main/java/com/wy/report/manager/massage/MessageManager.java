package com.wy.report.manager.massage;

import com.wy.report.manager.auth.UserManger;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @author cantalou
 * @date 2017-12-02 16:51
 */
public class MessageManager {

    private UserManger userManger;

    private int unreadMessageCount = 0;

    private List<OnMessageChangeListener> mListeners;

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
        notifyNewUnreadMessageCount();
    }

    public void notifyAllMessageRead()
    {
        if(mListeners!=null)
        {
            for(OnMessageChangeListener listener:mListeners)
            {
                listener.onAllMessageRead();
            }
        }
    }

    public void notifyNewUnreadMessageCount()
    {
        if(mListeners!=null)
        {
            for(OnMessageChangeListener listener:mListeners)
            {
                listener.onNewUnreadMessageCount(unreadMessageCount);
            }
        }
    }

    public void addOnAllMessageReadedListener(OnMessageChangeListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        mListeners.add(listener);
    }

    public void removeAllMessageReadedListener(OnMessageChangeListener listener) {
        if (mListeners != null) {
            mListeners.remove(listener);
        }
    }

    public interface OnMessageChangeListener {
        void onAllMessageRead();

        void onNewUnreadMessageCount(int count);
    }
}
