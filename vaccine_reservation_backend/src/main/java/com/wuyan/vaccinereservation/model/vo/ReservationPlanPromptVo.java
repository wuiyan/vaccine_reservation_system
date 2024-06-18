package com.wuyan.vaccinereservation.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预约计划的提示信息对象
 */
@Data
public class ReservationPlanPromptVo {
    /**
     * 预约计划ID
     */
    private int planId;
    /**
     * 预约计划的所有可预约时段
     */
    private List<String> rvDateTime;

    /**
     * 疫苗id
     */
    private int vaccineId;
    /**
     * 疫苗编号
     */
    private String vcNumber;
    /**
     * 疫苗名称
     */
    private String vcName;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 预约地点
     */
    private String place;
    /**
     * 剂次
     */
    private int dose;

    public ReservationPlanPromptVo(int planId, List<String> rvDateTime, int vaccineId, String vcNumber, String vcName, BigDecimal price, String place, int dose) {
        this.planId = planId;
        this.rvDateTime = rvDateTime;
        this.vaccineId = vaccineId;
        this.vcNumber = vcNumber;
        this.vcName = vcName;
        this.price = price;
        this.place = place;
        this.dose = dose;
    }
}
