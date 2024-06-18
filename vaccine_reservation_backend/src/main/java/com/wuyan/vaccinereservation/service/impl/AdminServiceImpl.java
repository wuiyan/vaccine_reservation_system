package com.wuyan.vaccinereservation.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyan.vaccinereservation.common.ResponseCode;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.entity.User;
import com.wuyan.vaccinereservation.service.AdminService;
import com.wuyan.vaccinereservation.model.entity.Admin;
import com.wuyan.vaccinereservation.mapper.AdminMapper;
import com.wuyan.vaccinereservation.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.wuyan.vaccinereservation.common.GlobalConstant.LOGIN_STATUS;
import static com.wuyan.vaccinereservation.common.ResponseCode.DISABLED_STATE;

/**
* @author wuyan
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2024-02-27 09:07:52
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {
    @Resource
    private WxMaService wxMaService;
    @Resource
    private UserService userService;

    @Override
    public Admin adminLogin(Admin admin ,HttpServletRequest request) {
        String md5Hex = DigestUtils.md5Hex(admin.getPassword());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",admin.getAccount());
        queryWrapper.eq("password",md5Hex);
        Admin adminInfo = this.getOne(queryWrapper);
        if (adminInfo == null){
            throw new BusinessException(ResponseCode.LOGIN_ERROR);
        }
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_STATUS, adminInfo);
        adminInfo.setPassword(null);

        return adminInfo;
    }

    @Override
    public User userLogin(String code, HttpServletRequest httpServletRequest) {
        WxMaUserService userServiceInfo = wxMaService.getUserService();
        WxMaJscode2SessionResult sessionInfo = null;
        try {
            sessionInfo = userServiceInfo.getSessionInfo(code);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
        //获取openid
        String openid = sessionInfo.getOpenid();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("openid",openid);
        User user = userService.getOne(userQueryWrapper);
        // 如果用户不存在则注册此用户
        if (user == null){
            User userInfo = new User();
            userInfo.setOpenid(openid);
            userService.insertUser(userInfo);
            user = userService.getOne(userQueryWrapper);
        }
        if (user.getUserStatus()==1){
            throw new BusinessException(DISABLED_STATE);
        }
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(LOGIN_STATUS, user);
        return user;
    }
}




