package com.wuyan.vaccinereservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyan.vaccinereservation.model.dto.ReservationPlanDto;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.model.vo.ReservationPlanPromptVo;
import com.wuyan.vaccinereservation.model.vo.ReservationPlanShowVo;

import java.util.List;


/**
* @author wuyan
* @description 针对表【reservation_plan】的数据库操作Service
* @createDate 2024-02-27 09:10:57
*/
public interface ReservationPlanService extends IService<ReservationPlan> {
    /**
     * 获取ReservationPlanShowVo列表信息
     */
    IPage<ReservationPlanShowVo> getReservationPlanShowVoList(int pageNum, int size);

    /**
     * 通过预约计划id获取预约计划的Dto对象
     * @param planId
     * @return
     */
    ReservationPlanDto getReservationPlanDtoById(int planId);

    ReservationPlan getReservationPlanById(int id);
    /**
        通过疫苗ID查询预约计划信息
     */
    List<ReservationPlan> getReservationPlanByVaccineId(int vaccineId);

    /**
     * 通过疫苗ID查询ReservationPlanDto
     */
    ReservationPlanDto getPlanDtoByVaccineId(int vaccineId);

    /**
     * 通过疫苗编号查询预约计划信息
     */
    List<ReservationPlan> getReservationPlanByVaccineNumber(String vaccineNumber);

    /**
     * 通过疫苗名称进行模糊查询，获取预约计划信息
     */
    IPage<ReservationPlanPromptVo> searchListByVcName(String vcName, int pageNum, int size);

    // 模糊查询 通过标题
    List<ReservationPlanShowVo> searchReservationPlanShowVoList(String planName);

    /**
     * 更新ReservationPlan信息
     */
    boolean updateReservationPlan(ReservationPlan reservationPlan);
    // 插入一条ReservationPlan信息
    boolean insertReservationPlan(ReservationPlan reservationPlan);

    /**
     * 删除一条ReservationPlan信息
     */
    boolean deleteReservationPlan(int reservationPlanId);
}
