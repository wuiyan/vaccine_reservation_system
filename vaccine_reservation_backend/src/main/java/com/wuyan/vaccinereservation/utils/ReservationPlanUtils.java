package com.wuyan.vaccinereservation.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.model.pojo.Plan;

import java.util.List;

/**
 * 预约计划工具类
 */
public class ReservationPlanUtils {
    /**
     * 检测预约计划是否为空
     * @return false 不为空，true 为空
     */
    public static boolean checkPlanIsNull(ReservationPlan plan){
        // 解析预约计划
        ObjectMapper objectMapper = new ObjectMapper();
        List<Plan> reservations = null;
        try {
            reservations = objectMapper.readValue(plan.getRvPlan(), new TypeReference<List<Plan>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 循环遍历当前预约计划的所有时间段，检测是否存在预约人
        for (Plan reservation : reservations) {
            for (int j = 0; j < reservation.getPlan().size(); j++) {
                Integer bookedNum = reservation.getPlan().get(j).getBookedNum();
                // 若预约计划中存在已被预约的数据，则不能删除此预约计划
                if (bookedNum > 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
