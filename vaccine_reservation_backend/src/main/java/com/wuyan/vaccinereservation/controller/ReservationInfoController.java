package com.wuyan.vaccinereservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuyan.vaccinereservation.common.ResponseBody;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.entity.ReservationInfo;
import com.wuyan.vaccinereservation.model.vo.ReservationDetailVo;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoListShowVo;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoPersonShowVo;
import com.wuyan.vaccinereservation.service.ReservationInfoService;
import com.wuyan.vaccinereservation.utils.ResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.wuyan.vaccinereservation.common.ResponseCode.SYSTEM_ERROR;

@RestController
public class ReservationInfoController {
    @Resource
    private ReservationInfoService reservationInfoService;

    /**
     * 用于管理员用户获取预约管理页面的简要展示信息
     * @param pageNum
     * @param size
     * @return
     */
    @GetMapping("/reservations")
    public ResponseBody<IPage<ReservationInfoListShowVo>> getReservationInfoListPage(int pageNum, int size){
        return ResultUtils.success(reservationInfoService.getReservationInfoListShowVoIPage(pageNum,size));
    }

    /**
     * 通过姓名模糊查询预约展示信息
     */
    @GetMapping("/reservations/search")
    public ResponseBody<List<ReservationInfoListShowVo>> searchReservationInfoList(String username){
        return ResultUtils.success(reservationInfoService.searchReservationInfoList(username));
    }

    /**
     * 通过预约ID获取预约计划信息
     */
    @GetMapping("/reservations/id")
    public ResponseBody<ReservationDetailVo> getPlanInfoByReservationId(int reservationId){
        return ResultUtils.success(reservationInfoService.getReservationDetailVoByReservationId(reservationId));
    }
    /**
     * 用于管理员用户添加预约信息
     */
    @PostMapping("/reservations")
    public ResponseBody<Boolean> addReservationInfos(@RequestBody ReservationInfo reservationInfo){
        return ResultUtils.success(reservationInfoService.insertReservationInfo(reservationInfo));
    }

    /**
     * 用于管理员用户更新预约信息
     */
    @PutMapping("/reservations")
    public ResponseBody<Boolean> updateReservationInfos(@RequestBody ReservationInfo reservationInfo){
        boolean result = reservationInfoService.updateReservationInfo(reservationInfo);
        if (result){
            return ResultUtils.success(true);
        }else {
            throw new BusinessException(SYSTEM_ERROR);
        }
    }

    /**
     * 用于管理员用户删除指定预约信息
     */
    @DeleteMapping("/reservations")
    public ResponseBody<Boolean> deleteReservationInfo(int reservationId){
        return ResultUtils.success(reservationInfoService.deleteReservationInfo(reservationId));
    }


    /**
     * 用于普通用户获取自己的预约信息
     * @param userId 用户实体id
     * @return 预约信息列表
     */
    @GetMapping("/reservation")
    public ResponseBody<List<ReservationInfoPersonShowVo>> getReservationInfoList(int userId){
        return ResultUtils.success(reservationInfoService.getReservationInfoByUserId(userId));
    }
    @GetMapping("/reservation/id")
    public ResponseBody<ReservationDetailVo> getReservationInfo(int reservationId){
        return ResultUtils.success(reservationInfoService.getReservationDetailVoByReservationId(reservationId));
    }

    @PostMapping("/reservation")
    public ResponseBody<Boolean> addReservationInfo(@RequestBody ReservationInfo reservationInfo) {
        return ResultUtils.success(reservationInfoService.insertReservationInfo(reservationInfo));
    }
    @PutMapping("/reservation")
    public ResponseBody<Boolean> cancelReservation(int reservationId) {
        return ResultUtils.success(reservationInfoService.cancelReservation(reservationId));
    }
    @DeleteMapping("/reservation")
    public ResponseBody<Boolean> deleteReservation(int reservationId) {
        return ResultUtils.success(reservationInfoService.deleteReservationInfo(reservationId));
    }

}
