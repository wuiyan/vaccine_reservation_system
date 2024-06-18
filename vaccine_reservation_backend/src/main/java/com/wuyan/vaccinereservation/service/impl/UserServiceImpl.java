package com.wuyan.vaccinereservation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.vo.UserStatusVo;
import com.wuyan.vaccinereservation.service.UserService;
import com.wuyan.vaccinereservation.model.entity.User;
import com.wuyan.vaccinereservation.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.wuyan.vaccinereservation.common.ResponseCode.PARAMETER_ERROR;
import static com.wuyan.vaccinereservation.common.ResponseCode.PARAMETER_NULL_ERROR;

/**
* @author wuyan
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-02-27 09:10:20
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public IPage<User> getUserList(int pageNum, int size) {
        // 1. 信息校验，判断页数和页面大小不小于等于零
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");
        }
        // 2. 获取用户列表
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true,false,"id");
        Page<User> page = new Page<>(pageNum,size);
        IPage<User> userIPage = this.page(page,queryWrapper);
        return userIPage;

    }

    @Override
    public User getUserById(int userId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",userId);
        return this.getOne(userQueryWrapper);
    }

    @Override
    public List<User> searchListByName(String name) {
        // 为空，返回空集合
        if (StringUtils.isBlank(name)){
            return new ArrayList<>();
        }

        // 有一个不为空则将不为空的条件进行查询
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name",name);
        return this.list(userQueryWrapper);
    }

    @Override
    public IPage<User> searchList(String name, String phone, int pageNum, int size) {
        // 两者皆为空，返回空集合
        if (StringUtils.isAllBlank(name,phone)){
            return new Page<User>();
        }
        // 信息校验，判断页数和页面大小不小于等于零
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");
        }

        Page<User> page = new Page<>(pageNum,size);

        // 有一个不为空则将不为空的条件进行查询
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotBlank(name),"name",name);
        userQueryWrapper.or();
        userQueryWrapper.like(StringUtils.isNotBlank(phone),"phone",phone);
        return this.page(page,userQueryWrapper);
    }

    @Override
    public boolean updateUser(User user) {
        if (user ==null){
            throw new BusinessException(PARAMETER_NULL_ERROR);
        }
        if (user.getId()<0){
            throw new BusinessException(PARAMETER_ERROR);
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id",user.getId());

        return this.update(user,userUpdateWrapper);
    }

    @Override
    public boolean insertUser(User user) {
        if (user == null){
            throw new BusinessException(PARAMETER_NULL_ERROR);
        }
        if (user.getRemind() == null){
            user.setRemind(0);
        }
        if (user.getUserStatus() == null){
            user.setUserStatus(0);
        }
        return this.save(user);
    }

    @Override
    public boolean deleteUser(int userId) {
        if (userId<0){
            throw new BusinessException(PARAMETER_ERROR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", userId);
        return this.remove(userQueryWrapper);
    }

    @Override
    public boolean changeUserStatus(UserStatusVo userStatusVo) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id",userStatusVo.getId());
        userUpdateWrapper.set("user_status",userStatusVo.getUserStatus());

        return this.update(userUpdateWrapper);
    }

    @Override
    public boolean addRemindCount(int userId) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id",userId);
        userUpdateWrapper.setSql("remind = remind + 1");
        return this.update(userUpdateWrapper);
    }
}




