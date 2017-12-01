package com.wy.report.manager.auth;

import com.wy.report.business.auth.model.User;
import com.wy.report.manager.preferences.Key;
import com.wy.report.manager.preferences.PreferenceManager;

/**
 * @author cantalou
 * @date 2017年12月01日 16:55
 */
public class UserManger {

    private static class InstanceHolder {
        static final UserManger INSTANCE = new UserManger();
    }

    private PreferenceManager preferenceManager;

    private User loginedUser;

    private UserManger() {
        preferenceManager = PreferenceManager.getInstance();
        loadUser();
    }

    private void loadUser() {
        loginedUser = preferenceManager.getValue(Key.LOGINED_USER_INFO, User.class);
    }

    public void updateUser(User user) {
        if (loginedUser == null) {
            loginedUser = user;
        } else {
            loginedUser.setBirthday(user.getBirthday());
            loginedUser.setHead(user.getHead());
            loginedUser.setMobile(user.getMobile());
            loginedUser.setName(user.getName());
            loginedUser.setRelationship(user.getRelationship());
            loginedUser.setSex(user.getSex());
        }
        preferenceManager.setValue(Key.LOGINED_USER_INFO, loginedUser);
    }

    public boolean isLogin() {
        return loginedUser != null;
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    public static UserManger getInstance() {
        return InstanceHolder.INSTANCE;
    }

}
