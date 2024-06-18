package com.wuyan.vaccinereservation.controller;

import com.wuyan.vaccinereservation.common.ResponseBody;
import com.wuyan.vaccinereservation.common.ResponseCode;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.entity.Admin;
import com.wuyan.vaccinereservation.model.entity.User;
import com.wuyan.vaccinereservation.service.AdminService;
import com.wuyan.vaccinereservation.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AdminController {
    @Resource
    private AdminService adminService;
    @PostMapping("/adminLogin")
    public ResponseBody<Admin> adminLogin(@RequestBody Admin admin, HttpServletRequest httpServletRequest){
        if (StringUtils.isAllBlank(admin.getAccount(),admin.getPassword())){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        return ResultUtils.success(adminService.adminLogin(admin,httpServletRequest));
    }
    @PostMapping("/userLogin")
    public ResponseBody<User> userLogin(@RequestBody Map<String, String> requestData, HttpServletRequest httpServletRequest){
        String code = requestData.get("code");
        if (StringUtils.isBlank(code)){
            throw new BusinessException(ResponseCode.LOGIN_ERROR);
        }
        return ResultUtils.success(adminService.userLogin(code,httpServletRequest));
    }
}
