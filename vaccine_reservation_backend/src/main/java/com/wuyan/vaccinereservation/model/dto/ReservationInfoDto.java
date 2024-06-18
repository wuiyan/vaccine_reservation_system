package com.wuyan.vaccinereservation.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.model.entity.User;
import com.wuyan.vaccinereservation.model.entity.VaccineInfo;
import lombok.Data;

import java.util.Date;

/**
    预约信息完整数据对象
 */
@Data
public class ReservationInfoDto {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户信息
     */
    private User user;

    /**
     * 疫苗信息
     */
    private VaccineInfo vaccineInfo;

    /**
     * 预约计划信息
     */
    private ReservationPlan plan;

    /**
     * 当前剂次
     */
    private Integer nowDose;

    /**
     * 用户预约申请时间
     */
    private Date nowTime;

    /**
     * 预约开始时间
     */
    private Date rvStartDatetime;

    /**
     * 预约结束时间
     */
    private Date rvEndDatetime;

    /**
     * 预约状态
     */
    private Integer rvStatus;

}
