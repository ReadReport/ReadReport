package com.wy.report.manager.auth;

import com.hwangjr.rxbus.RxBus;
import com.wy.report.ReportApplication;
import com.wy.report.base.constant.RxKey;
import com.wy.report.business.auth.model.User;
import com.wy.report.manager.preferences.Key;
import com.wy.report.manager.preferences.PreferenceManager;
import com.wy.report.manager.router.AuthRouterManager;

import static com.wy.report.manager.router.AuthRouterManager.ROUTER_LOGIN;

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

    public static String getUid() {
        return getInstance().isLogin() ? getInstance().getLoginUser()
                                                      .getId() : "";
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

    public void logout() {
        preferenceManager.delete(Key.LOGINED_USER_INFO);
        loginUser = null;
        RxBus.get()
             .post(RxKey.RX_LOGOUT, new Object());
    }

    private static class InstanceHolder {
        static final UserManger INSTANCE = new UserManger();
    }

    /**
     * 检测当前
     *
     * @return
     */
    public static boolean checkLogin() {

        if (getInstance().isLogin()) {
            return true;
        }

        AuthRouterManager.getInstance()
                         .getRouter()
                         .open(ReportApplication.getCurrentActivity(), ROUTER_LOGIN);
        return false;
    }
}
