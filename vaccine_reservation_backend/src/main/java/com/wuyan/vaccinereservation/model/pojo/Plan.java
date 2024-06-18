package com.wuyan.vaccinereservation.model.pojo;

import lombok.Data;

import java.util.List;

/**
 * 用于存储预约计划的一天信息
 */
@Data
public class Plan {
    private String date;
    private List<PlanInfo> plan;
}
