// 全局常量

// ----------- 网络请求相关常量 ----------
export const BASEURL = "http://localhost:8080/";

// ----------- 疫苗相关常量 -------------
// 疫苗类型
export const VACCINE_TYPE = {
    0:"流感疫苗",
    1:"HPV疫苗",
    2:"乙肝疫苗",
    3:"肺炎疫苗",
    4:"狂犬病疫苗",
    5:"甲肝疫苗",
    6:"水痘疫苗",
    7:"破伤风疫苗"
}

// ----------- 用户相关常量 -------------
// 用户性别
export const USER_GENDER = {
    0:"女",
    1:"男"
}
// 证件类型
export const CARD_TYPE = {
    0:"身份证",
    1:"普通护照"
}
// 用户状态
export const USER_STATUS = {
    0:"正常",
    1:"已禁用"
}

// ----------- 预约相关常量 -------------
export const RESERVATION_STATUS = {
    0:"已预约",
    // 已接种，有剩余剂次
    1:"已接种",
    // 已接种，无剩余剂次
    2:"已接种",
    3:"已取消"
}

// ----------- 订阅通知相关常量 -------------
// 预约提醒模板id
export const SUBSCRIPTION_TMPLIDS = ['oV06UCINvwAefbiyhUq_q7djW2rhIpteaOgsMm-Rd0M']

// 不使用默认导出，而使用普通export导出，导出时需知道确切的变量和函数，但对于常量来说更加直观
// export default {VACCINE_TYPE,USER_GENDER,USER_STATUS,CARD_TYPE};