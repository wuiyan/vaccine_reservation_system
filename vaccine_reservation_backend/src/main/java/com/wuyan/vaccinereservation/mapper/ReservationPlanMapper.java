package com.wuyan.vaccinereservation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyan.vaccinereservation.model.dto.ReservationPlanDto;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoListShowVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @author 86155
* @description 针对表【reservation_plan】的数据库操作Mapper
* @createDate 2024-02-27 16:41:51
* @Entity generator.domain.ReservationPlan
*/
public interface ReservationPlanMapper extends BaseMapper<ReservationPlan> {
    ReservationPlan selectByPlanId(@Param("id") int id);

    List<ReservationPlanDto> selectReservationPlanDto();

    /**
     * 通过预约id获取预约Dto信息
     * @param id
     * @return
     */
    ReservationPlanDto getReservationPlanDto(@Param("id") int id);
    /**
     * 通过疫苗id获取预约Dto信息
     */
    ReservationPlanDto getPlanDtoByVaccineId(@Param("vaccineId") int vaccineId);
}




