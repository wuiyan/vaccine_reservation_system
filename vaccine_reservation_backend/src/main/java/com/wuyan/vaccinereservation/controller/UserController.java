package com.wuyan.vaccinereservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuyan.vaccinereservation.common.ResponseBody;
import com.wuyan.vaccinereservation.common.ResponseCode;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.entity.Admin;
import com.wuyan.vaccinereservation.model.entity.User;
import com.wuyan.vaccinereservation.model.vo.UserStatusVo;
import com.wuyan.vaccinereservation.service.UserService;
import com.wuyan.vaccinereservation.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserService userService;
    /**
     * 获取用户列表
     */
    @GetMapping("/users")
    public ResponseBody<IPage<User>> getUserList(int pageNum, int size){
        if (pageNum <=0 || size <=0){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        return ResultUtils.success(userService.getUserList(pageNum,size));
    }

    /**
     * 通过用户id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    @GetMapping("/users/id")
    public ResponseBody<User> getUserById(int userId){
        if (userId<=0){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        return ResultUtils.success(userService.getUserById(userId));
    }

    /**
     * 通过姓名获取用户信息
     */
    @GetMapping("/users/search")
    public ResponseBody<List<User>> searchUserByName(String name){
        return ResultUtils.success(userService.searchListByName(name));
    }
    /**
     * 新增一个用户
     */
    @PostMapping("/users")
    public ResponseBody<Boolean> addUserInfo(@RequestBody User user){
        if (user == null){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        if (StringUtils.isAllBlank(user.getName(),user.getOpenid(),user.getPhone(),user.getCard())){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        return ResultUtils.success(userService.insertUser(user));
    }
    /**
     * 修改用户信息
     */
    @PutMapping("/users")
    public ResponseBody<Boolean> updateUserInfos(@RequestBody User user){
        if (user == null){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        if (StringUtils.isAllBlank(user.getName(),user.getOpenid(),user.getPhone(),user.getCard())){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        return ResultUtils.success(userService.updateUser(user));
    }
    /**
     * 删除指定用户
     */
    @DeleteMapping("/users")
    public ResponseBody<Boolean> deleteUserInfo(int userId){
        if (userId<=0){
            throw new BusinessException(ResponseCode.PARAMETER_ERROR);
        }
        return ResultUtils.success(userService.deleteUser(userId));
    }

    /**
     * 更新指定用户状态
     * @param userStatusVo 用户状态接收对象
     * @return boolean
     */
    @PutMapping("/users/status")
    public ResponseBody<Boolean> changeUserStatus(@RequestBody UserStatusVo userStatusVo){
        if (userStatusVo.getId()<=0){
            throw new BusinessException(ResponseCode.PARAMETER_ERROR);
        }
        return ResultUtils.success(userService.changeUserStatus(userStatusVo));
    }

    // ------------------------ 普通用户接口 ---------------
    /**
     * 修改用户信息
     */
    @PutMapping("/user")
    public ResponseBody<Boolean> updateUserInfo(@RequestBody User user){
        if (user == null){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        if (StringUtils.isAllBlank(user.getName(),user.getOpenid(),user.getPhone(),user.getCard())){
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        return ResultUtils.success(userService.updateUser(user));
    }
    /**
     * 订阅消息
     */
    @PutMapping("/user/remind")
    public ResponseBody<Boolean> updateUserRemindInfo(int userId){
        if (userId <= 0){
            throw new BusinessException(ResponseCode.PARAMETER_ERROR);
        }
        return ResultUtils.success(userService.addRemindCount(userId));
    }
}

