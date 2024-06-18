package com.wuyan.vaccinereservation.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wuyan.vaccinereservation.model.entity.VaccineInfo;
import lombok.Data;

import java.util.Date;

/**
 * 预约计划完整数据对象
 */
@Data
public class ReservationPlanDto {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 疫苗信息
     */
    private VaccineInfo vaccineInfo;
    /**
     * 疫苗编号
     */
    private String vaccineNumber;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 预约计划
     */
    private String rvPlan;

    /**
     * 创建日期
     */
    private Date createTime;
}
