package com.wuyan.vaccinereservation.model.pojo;

import lombok.Data;

/**
 * 用于存储预约计划的单个时间段信息
 */
@Data
public class PlanInfo {
    private String startTime;
    private String endTime;
    private Integer planNum;
    private Integer bookedNum;
}
