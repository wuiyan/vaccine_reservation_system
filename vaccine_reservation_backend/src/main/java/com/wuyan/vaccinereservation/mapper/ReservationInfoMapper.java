package com.wuyan.vaccinereservation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyan.vaccinereservation.model.dto.ReservationInfoDto;
import com.wuyan.vaccinereservation.model.entity.ReservationInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wuyan
* @description 针对表【reservation_info】的数据库操作Mapper
* @createDate 2024-02-27 09:09:53
* @Entity com.wuyan.vaccinereservation.model.domain.ReservationInfo
*/
public interface ReservationInfoMapper extends BaseMapper<ReservationInfo> {
    /**
     * 获取预约信息实体列表
     */
    List<ReservationInfo> selectReservationInfo();

    /**
     *  获取预约信息 ReservationInfoDto 列表信息
     */
    List<ReservationInfoDto> selectReservationInfoDto();

    /**
     * 通过ID获取单个 ReservationInfoDto 信息
     */
    ReservationInfoDto getReservationInfoDto(@Param("id") int id);

    int deleteReservationInfo(@Param("id") int id);


}




