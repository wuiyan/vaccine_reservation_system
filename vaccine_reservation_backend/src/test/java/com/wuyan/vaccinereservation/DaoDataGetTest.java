package com.wuyan.vaccinereservation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuyan.vaccinereservation.mapper.ReservationInfoMapper;
import com.wuyan.vaccinereservation.model.entity.ReservationInfo;
import com.wuyan.vaccinereservation.model.entity.User;
import com.wuyan.vaccinereservation.service.AdminService;
import com.wuyan.vaccinereservation.service.ReservationInfoService;
import com.wuyan.vaccinereservation.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class DaoDataGetTest {
    @Resource
    private AdminService adminService;
    @Resource
    private UserService userService;

    @Test
    void serviceTest(){

    }
    @Test
    void serviceUserTest(){
        IPage<User> adminList = userService.getUserList(1,1 );
        adminList.getRecords().forEach(System.out::println);
//        User user = adminList.get(0);
//        user.setOpenid("55555555");
//        boolean updateUser = userService.updateUser(user);
//        System.out.println(updateUser);
        User user = adminList.getRecords().get(0);
        user.setName("路人甲");
        user.setOpenid("66666666");
        user.setId(null);
        boolean insertUser = userService.insertUser(user);
        System.out.println(insertUser);
    }
    @Resource
    private ReservationInfoMapper reservationInfoMapper;
    @Resource
    private ReservationInfoService reservationInfoService;
    @Test
    void reservationInfoTest(){
        List<ReservationInfo> reservationInfo = reservationInfoMapper.selectReservationInfo();
        System.out.println(reservationInfo.toString());
        ReservationInfo reservationInfo1 = reservationInfo.get(0);
//        reservationInfo1.setRvPlace("地球村");

//        IPage<ReservationInfo> reservationInfoList = reservationInfoService.getReservationInfoDtoList(1, 2);
//        reservationInfoList.getRecords().forEach(System.out::println);


    }
    @Test
    void searchTest(){
        IPage<User> userIPage = userService.searchList("", "", 1, 5);
        System.out.println(userIPage.getTotal());
        userIPage.getRecords().forEach(System.out::println);

    }

    @Test
    void seaTest(){
        String md5Hex = DigestUtils.md5Hex("123456");


    }

}
