package com.wuyan.vaccinereservation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyan.vaccinereservation.model.entity.User;
import org.apache.ibatis.annotations.Param;

/**
* @author wuyan
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-02-27 09:10:20
* @Entity com.wuyan.vaccinereservation.model.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    User selectByOpenid(@Param("openid") String openid);
    User selectUserById(@Param("id") int id);
}




