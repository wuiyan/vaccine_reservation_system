package com.wuyan.vaccinereservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyan.vaccinereservation.model.entity.User;
import com.wuyan.vaccinereservation.model.vo.UserStatusVo;

import java.util.List;

/**
* @author wuyan
* @description 针对表【user】的数据库操作Service
* @createDate 2024-02-27 09:10:20
*/
public interface UserService extends IService<User> {
    /**
     * 获取user分页列表信息
     */
    IPage<User> getUserList(int pageNum, int size);

    /**
     * 通过id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    User getUserById(int userId);

    /**
     * 通过姓名进行模糊查询
     */
    List<User> searchListByName(String name);
    // 模糊查询 姓名和电话号码
    IPage<User> searchList(String name,String phone,int pageNum, int size);

    /**
     * 更新user信息
     * @param user 用户实体对象
     * @return boolean
     */
    boolean updateUser(User user);
    /**
     * 插入一条user信息
     * @param user 用户实体对象
     * @return boolean
     */
    boolean insertUser(User user);
    /**
     * 删除一条user信息
     * @param userId 用户实体ID
     * @return boolean
     */
    boolean deleteUser(int userId);

    /**
     * 更改用户状态
     * @param userStatusVo 用户状态更改对象
     * @return
     */
    boolean changeUserStatus(UserStatusVo userStatusVo);

    /**
     * 增加订阅提醒次数
     */
    boolean addRemindCount(int userId);

}
