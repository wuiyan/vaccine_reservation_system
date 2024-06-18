package com.wuyan.vaccinereservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuyan.vaccinereservation.common.ResponseBody;
import com.wuyan.vaccinereservation.common.ResponseCode;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.dto.ReservationPlanDto;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoListShowVo;
import com.wuyan.vaccinereservation.model.vo.ReservationPlanPromptVo;
import com.wuyan.vaccinereservation.model.vo.ReservationPlanShowVo;
import com.wuyan.vaccinereservation.service.ReservationPlanService;
import com.wuyan.vaccinereservation.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ReservationPlanController {
    @Resource
    private ReservationPlanService reservationPlanService;

    /**
     * 获取预约计划管理页面展示信息
     */
    @GetMapping("/plan")
    public ResponseBody<IPage<ReservationPlanShowVo>> getReservationPlanShowVoListPage(int pageNum, int size){
        return ResultUtils.success(reservationPlanService.getReservationPlanShowVoList(pageNum,size));
    }
    /**
     * 通过疫苗名称搜索预约计划
     */
    @GetMapping("/plan/vcName")
    public ResponseBody<IPage<ReservationPlanPromptVo>> getPlanByVcName(String vcName, int pageNum, int size){
        return ResultUtils.success(reservationPlanService.searchListByVcName(vcName,pageNum,size));
    }

    /**
     * 通过疫苗名称模糊查询
     */
    @GetMapping("/plan/search")
    public ResponseBody<List<ReservationPlanShowVo>> searchPlanByPlanName(String planName){
        return ResultUtils.success(reservationPlanService.searchReservationPlanShowVoList(planName));
    }

    /**
     * 通过预约id获取预约计划dto对象
     */
    @GetMapping("/plan/id")
    public ResponseBody<ReservationPlanDto> getPlanDtoById(int planId){
        return ResultUtils.success(reservationPlanService.getReservationPlanDtoById(planId));
    }

    /**
     * 通过疫苗id获取预约计划dto对象
     */
    @GetMapping("/plan/vaccineId")
    public ResponseBody<ReservationPlanDto> getPlanDtoByVaccineId(int vaccineId){
        return ResultUtils.success(reservationPlanService.getPlanDtoByVaccineId(vaccineId));
    }

    @PostMapping("/plan")
    public ResponseBody<Boolean> addReservationPlan(@RequestBody ReservationPlan reservationPlan) {
        if(reservationPlan!=null){
            if(!StringUtils.isAllBlank(reservationPlan.getRvPlan(),
                    reservationPlan.getPlanName(),
                    reservationPlan.getVaccineNumber())
                    &&  reservationPlan.getVaccineId()>0){
                return ResultUtils.success(reservationPlanService.insertReservationPlan(reservationPlan));
            }
        }
        throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
    }

    /**
     * 更新预约计划
     * @param reservationPlan
     * @return
     */
    @PutMapping("/plan")
    public ResponseBody<Boolean> updateReservationPlan(@RequestBody ReservationPlan reservationPlan) {
        if(reservationPlan!=null){
            if(!StringUtils.isAllBlank(reservationPlan.getRvPlan(),
                    reservationPlan.getPlanName(),
                    reservationPlan.getVaccineNumber())
                    &&  reservationPlan.getVaccineId()>0){
                return ResultUtils.success(reservationPlanService.updateReservationPlan(reservationPlan));
            }
        }
        throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
    }

    /**
     * 删除预约计划
     */
    @DeleteMapping("/plan")
    public ResponseBody<Boolean> deleteReservationPlan( int planId) {
        if(planId>0){
            return ResultUtils.success(reservationPlanService.deleteReservationPlan(planId));
        }
        throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
    }
}
