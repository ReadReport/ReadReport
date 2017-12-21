package com.wy.report.manager.auth;

import com.wy.report.business.auth.model.User;
import com.wy.report.manager.preferences.Key;
import com.wy.report.manager.preferences.PreferenceManager;

/**
 * @author cantalou
 * @date 2017年12月01日 16:55
 */
public class UserManger {

    private PreferenceManager preferenceManager;
    private User loginUser;

    private UserManger() {
        preferenceManager = PreferenceManager.getInstance();
        loadUser();
    }

    public static UserManger getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private void loadUser() {
        loginUser = preferenceManager.getValue(Key.LOGINED_USER_INFO, User.class);
    }

    public void updateUser(User user) {
        if (loginUser == null) {
            loginUser = user;
        } else {
            loginUser.setBirthday(user.getBirthday());
            loginUser.setHead(user.getHead());
            loginUser.setMobile(user.getMobile());
            loginUser.setName(user.getName());
            loginUser.setRelationship(user.getRelationship());
            loginUser.setSex(user.getSex());
        }
        preferenceManager.setValue(Key.LOGINED_USER_INFO, loginUser);
    }

    public boolean isLogin() {
        return loginUser != null;
    }

    public User getLoginUser() {
        return loginUser;
    }

    private static class InstanceHolder {
        static final UserManger INSTANCE = new UserManger();
    }

}