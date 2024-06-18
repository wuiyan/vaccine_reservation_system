package com.wuyan.vaccinereservation.common;

/**
 * 全局常量定义类
 */
public class GlobalConstant {
    // 用户预约信息状态码
    public static final int STATUS_RESERVED = 0; // 已预约
    public static final int STATUS_VACCINATED_WITH_REMAINDER = 1; // 已接种，有剩余剂次
    public static final int STATUS_VACCINATED_NO_REMAINDER = 2; // 已接种，无剩余剂次
    public static final int STATUS_CANCELLED = 3; // 已取消

    // 用户登录状态
    public static final String LOGIN_STATUS = "LOGIN_STATUS";

    // 推送模板id
    public static final String SUBSCRIPTION_TMPLIDS = "oV06UCINvwAefbiyhUq_q7djW2rhIpteaOgsMm-Rd0M";

}
