package com.wuyan.vaccinereservation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyan.vaccinereservation.model.entity.Admin;
import com.wuyan.vaccinereservation.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author wuyan
* @description 针对表【admin】的数据库操作Service
* @createDate 2024-02-27 09:07:52
*/
public interface AdminService extends IService<Admin> {
    Admin adminLogin(Admin admin , HttpServletRequest request);
    /**
     * 用户登录
     */
    User userLogin(String code, HttpServletRequest httpServletRequest);
}
