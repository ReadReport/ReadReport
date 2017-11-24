package com.wy.report.helper.retrofit;

import android.util.SparseArray;

/**
 * @author cantalou
 * @date 2017年11月24日 9:04
 */
public final class ResponseCode {

    /**
     * 系统繁忙，请稍候再试
     */
    public static final int ERROR_CODE_40000 = 40000;
    /**
     * 获取token时secret错误
     */
    public static final int ERROR_CODE_40001 = 40001;
    /**
     * 不合法的凭证类型
     */
    public static final int ERROR_CODE_40002 = 40002;
    /**
     * 不合法的appid
     */
    public static final int ERROR_CODE_40003 = 40003;
    /**
     * 不合法的token
     */
    public static final int ERROR_CODE_40004 = 40004;
    /**
     * 不合法的参数
     */
    public static final int ERROR_CODE_40005 = 40005;
    /**
     * 缺少appid参数
     */
    public static final int ERROR_CODE_40006 = 40006;
    /**
     * 缺少secret参数
     */
    public static final int ERROR_CODE_40007 = 40007;
    /**
     * 缺少token参数
     */
    public static final int ERROR_CODE_40008 = 40008;
    /**
     * 帐号不合法
     */
    public static final int ERROR_CODE_40009 = 40009;
    /**
     * token超时，请检查token的有效期
     */
    public static final int ERROR_CODE_40010 = 40010;
    /**
     * POST的数据包为空
     */
    public static final int ERROR_CODE_40011 = 40011;
    /**
     * 解析JSON/XML内容错误
     */
    public static final int ERROR_CODE_40012 = 40012;
    /**
     * api功能未授权
     */
    public static final int ERROR_CODE_40013 = 40013;
    /**
     * 用户未授权该api
     */
    public static final int ERROR_CODE_40014 = 40014;
    /**
     * 用户受限
     */
    public static final int ERROR_CODE_40015 = 40015;
    /**
     * 参数错误
     */
    public static final int ERROR_CODE_40016 = 40016;
    /**
     * 系统错误
     */
    public static final int ERROR_CODE_40017 = 40017;
    /**
     * 缺少token参数
     */
    public static final int ERROR_CODE_40018 = 40018;
    /**
     * 缺少timestamp参数
     */
    public static final int ERROR_CODE_40019 = 40019;
    /**
     * POST数据参数不合法
     */
    public static final int ERROR_CODE_40020 = 40020;
    /**
     * 需要GET请求
     */
    public static final int ERROR_CODE_40021 = 40021;
    /**
     * 需要POST请求
     */
    public static final int ERROR_CODE_40022 = 40022;
    /**
     * 需要HTTPS请求
     */
    public static final int ERROR_CODE_40023 = 40023;
    /**
     * 远端服务不可用
     */
    public static final int ERROR_CODE_40024 = 40024;
    /**
     * 页面参数不合法
     */
    public static final int ERROR_CODE_40025 = 40025;
    /**
     * 日期格式错误
     */
    public static final int ERROR_CODE_50001 = 50001;
    /**
     * 时间区间不合法
     */
    public static final int ERROR_CODE_50002 = 50002;
    /**
     * 不合法的请求格式
     */
    public static final int ERROR_CODE_50003 = 50003;
    /**
     * 不合法的URL长度
     */
    public static final int ERROR_CODE_50004 = 50004;
    /**
     * 不合法的请求字符
     */
    public static final int ERROR_CODE_50005 = 50005;
    /**
     * 非法请求
     */
    public static final int ERROR_CODE_50006 = 50006;
    /**
     * 手机验证码不正确，请重新输入
     */
    public static final int ERROR_CODE_50007 = 50007;
    /**
     * 手机验证码已过期，请重新获取
     */
    public static final int ERROR_CODE_50008 = 50008;
    /**
     * 短信登录失败
     */
    public static final int ERROR_CODE_50009 = 50009;
    /**
     * 登录失败，用户名或者密码不正确
     */
    public static final int ERROR_CODE_50010 = 50010;
    /**
     * 一次删除页面ID数量不能超过10
     */
    public static final int ERROR_CODE_50011 = 50011;
    /**
     * 一次查询页面ID数量不能超过50
     */
    public static final int ERROR_CODE_50012 = 50012;
    /**
     * 创建上传目录失败
     */
    public static final int ERROR_CODE_50013 = 50013;
    /**
     * 文件上传失败！
     */
    public static final int ERROR_CODE_50014 = 50014;
    /**
     * 报告上传失败, 请稍后再试！
     */
    public static final int ERROR_CODE_50015 = 50015;
    /**
     * 对不起，系统正在解读您的报告中，暂时不能删除！
     */
    public static final int ERROR_CODE_50016 = 50016;
    /**
     * 对不起，您的报告系统已经解读，不能删除！
     */
    public static final int ERROR_CODE_50017 = 50017;
    /**
     * 对不起，您的报告系统已经解读，不能提交了！
     */
    public static final int ERROR_CODE_50018 = 50018;
    /**
     * 您的报告正在解读中，请耐心等待……
     */
    public static final int ERROR_CODE_50019 = 50019;
    /**
     * 请输入正确格式的手机号
     */
    public static final int ERROR_CODE_50020 = 50020;
    /**
     * 添加家庭成员失败
     */
    public static final int ERROR_CODE_50021 = 50021;
    /**
     * 会员问题提交失败
     */
    public static final int ERROR_CODE_50022 = 50022;
    /**
     * 会员批量删除问题失败
     */
    public static final int ERROR_CODE_50023 = 50023;
    /**
     * 会员反馈失败
     */
    public static final int ERROR_CODE_50024 = 50024;
    /**
     * 会员批量删除消息失败
     */
    public static final int ERROR_CODE_50025 = 50025;
    /**
     * 会员资料修改失败
     */
    public static final int ERROR_CODE_50026 = 50026;
    /**
     * 注册会员已存在
     */
    public static final int ERROR_CODE_50027 = 50027;
    /**
     * 会员注册失败
     */
    public static final int ERROR_CODE_50028 = 50028;
    /**
     * 验证用户不存在
     */
    public static final int ERROR_CODE_50029 = 50029;
    /**
     * 密码重置失败
     */
    public static final int ERROR_CODE_50030 = 50030;
    /**
     * 账户已存在
     */
    public static final int ERROR_CODE_50031 = 50031;
    /**
     * 重新绑定账户失败
     */
    public static final int ERROR_CODE_50032 = 50032;
    /**
     * 问题不存在
     */
    public static final int ERROR_CODE_50033 = 50033;
    /**
     * 点评失败
     */
    public static final int ERROR_CODE_50034 = 50034;
    /**
     * ios_token更新失败
     */
    public static final int ERROR_CODE_50035 = 50035;
    /**
     * 统一调用微信支付请求失败
     */
    public static final int ERROR_CODE_60001 = 60001;
    /**
     * 订单信息不存在
     */
    public static final int ERROR_CODE_60002 = 60002;
    /**
     * 该订单已经支付过了
     */
    public static final int ERROR_CODE_60003 = 60003;
    /**
     * 该订单实际支付金额与订单数据不符,请检查
     */
    public static final int ERROR_CODE_60004 = 60004;
    /**
     * 用户注册数动力用户失败
     */
    public static final int ERROR_CODE_70001 = 70001;
    /**
     * 用户数据上传数据库失败（系统忙）
     */
    public static final int ERROR_CODE_70002 = 70002;
    /**
     * 未开通该类型的服务
     */
    public static final int ERROR_CODE_70003 = 70003;
    /**
     * 数据上传数动力失败
     */
    public static final int ERROR_CODE_70004 = 70004;

    public static final SparseArray map = new SparseArray();

    static {
        map.put(ERROR_CODE_40000, "系统繁忙，请稍候再试");
        map.put(ERROR_CODE_40001, "获取token时secret错误");
        map.put(ERROR_CODE_40002, "不合法的凭证类型");
        map.put(ERROR_CODE_40003, "不合法的appid");
        map.put(ERROR_CODE_40004, "不合法的token");
        map.put(ERROR_CODE_40005, "不合法的参数");
        map.put(ERROR_CODE_40006, "缺少appid参数");
        map.put(ERROR_CODE_40007, "缺少secret参数");
        map.put(ERROR_CODE_40008, "缺少token参数");
        map.put(ERROR_CODE_40009, "帐号不合法");
        map.put(ERROR_CODE_40010, "token超时，请检查token的有效期");
        map.put(ERROR_CODE_40011, "POST的数据包为空");
        map.put(ERROR_CODE_40012, "解析JSON/XML内容错误");
        map.put(ERROR_CODE_40013, "api功能未授权");
        map.put(ERROR_CODE_40014, "用户未授权该api");
        map.put(ERROR_CODE_40015, "用户受限");
        map.put(ERROR_CODE_40016, "参数错误");
        map.put(ERROR_CODE_40017, "系统错误");
        map.put(ERROR_CODE_40018, "缺少token参数");
        map.put(ERROR_CODE_40019, "缺少timestamp参数");
        map.put(ERROR_CODE_40020, "POST数据参数不合法");
        map.put(ERROR_CODE_40021, "需要GET请求");
        map.put(ERROR_CODE_40022, "需要POST请求");
        map.put(ERROR_CODE_40023, "需要HTTPS请求");
        map.put(ERROR_CODE_40024, "远端服务不可用");
        map.put(ERROR_CODE_40025, "页面参数不合法");
        map.put(ERROR_CODE_50001, "日期格式错误");
        map.put(ERROR_CODE_50002, "时间区间不合法");
        map.put(ERROR_CODE_50003, "不合法的请求格式");
        map.put(ERROR_CODE_50004, "不合法的URL长度");
        map.put(ERROR_CODE_50005, "不合法的请求字符");
        map.put(ERROR_CODE_50006, "非法请求");
        map.put(ERROR_CODE_50007, "手机验证码不正确，请重新输入");
        map.put(ERROR_CODE_50008, "手机验证码已过期，请重新获取");
        map.put(ERROR_CODE_50009, "短信登录失败");
        map.put(ERROR_CODE_50010, "登录失败，用户名或者密码不正确");
        map.put(ERROR_CODE_50011, "一次删除页面ID数量不能超过10");
        map.put(ERROR_CODE_50012, "一次查询页面ID数量不能超过50");
        map.put(ERROR_CODE_50013, "创建上传目录失败");
        map.put(ERROR_CODE_50014, "文件上传失败！");
        map.put(ERROR_CODE_50015, "报告上传失败, 请稍后再试！");
        map.put(ERROR_CODE_50016, "对不起，系统正在解读您的报告中，暂时不能删除！");
        map.put(ERROR_CODE_50017, "对不起，您的报告系统已经解读，不能删除！");
        map.put(ERROR_CODE_50018, "对不起，您的报告系统已经解读，不能提交了！");
        map.put(ERROR_CODE_50019, "您的报告正在解读中，请耐心等待……");
        map.put(ERROR_CODE_50020, "请输入正确格式的手机号");
        map.put(ERROR_CODE_50021, "添加家庭成员失败");
        map.put(ERROR_CODE_50022, "会员问题提交失败");
        map.put(ERROR_CODE_50023, "会员批量删除问题失败");
        map.put(ERROR_CODE_50024, "会员反馈失败");
        map.put(ERROR_CODE_50025, "会员批量删除消息失败");
        map.put(ERROR_CODE_50026, "会员资料修改失败");
        map.put(ERROR_CODE_50027, "注册会员已存在");
        map.put(ERROR_CODE_50028, "会员注册失败");
        map.put(ERROR_CODE_50029, "验证用户不存在");
        map.put(ERROR_CODE_50030, "密码重置失败");
        map.put(ERROR_CODE_50031, "账户已存在");
        map.put(ERROR_CODE_50032, "重新绑定账户失败");
        map.put(ERROR_CODE_50033, "问题不存在");
        map.put(ERROR_CODE_50034, "点评失败");
        map.put(ERROR_CODE_50035, "ios_token更新失败");
        map.put(ERROR_CODE_60001, "统一调用微信支付请求失败");
        map.put(ERROR_CODE_60002, "订单信息不存在");
        map.put(ERROR_CODE_60003, "该订单已经支付过了");
        map.put(ERROR_CODE_60004, "该订单实际支付金额与订单数据不符, 请检查");
        map.put(ERROR_CODE_70001, "用户注册数动力用户失败");
        map.put(ERROR_CODE_70002, "用户数据上传数据库失败（系统忙）");
        map.put(ERROR_CODE_70003, "未开通该类型的服务");
        map.put(ERROR_CODE_70004, "数据上传数动力失败");

    }

}
