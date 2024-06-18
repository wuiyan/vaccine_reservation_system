package com.wuyan.vaccinereservation.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 预约计划简要展示信息对象
 */
@Data
@AllArgsConstructor
public class ReservationPlanShowVo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 疫苗编号
     */
    private String vaccineName;

    /**
     * 接种地点
     */
    private String place;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 创建日期
     */
    private String createTime;
}
