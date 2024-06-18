package com.wuyan.vaccinereservation.model.vo;

import lombok.Data;

/**
 * 用于预约信息管理页的简要信息展示
 */
@Data
public class ReservationInfoListShowVo {
    /**
     * 预约信息ID
     */
    private Integer id;
    /**
     * 预约人姓名
     */
    private String person;
    /**
     * 预约疫苗名称
     */
    private String vaccine;
    /**
     * 预约日期-时间
     */
    private String dateTime;
    /**
     * 预约状态
     */
    private Integer status;

    public ReservationInfoListShowVo(Integer id, String person, String vaccine, String dateTime, Integer status) {
        this.id = id;
        this.person = person;
        this.vaccine = vaccine;
        this.dateTime = dateTime;
        this.status = status;
    }
}
