package com.wuyan.vaccinereservation.model.vo;

import lombok.Data;
@Data
public class ReservationInfoPersonShowVo {
    /**
     * 预约信息ID
     */
    private Integer id;
    /**
     * 预约人姓名
     */
    private String place;
    /**
     * 预约疫苗名称
     */
    private String vaccineName;
    /**
     * 预约日期-时间
     */
    private String dateTime;
    /**
     * 预约状态
     */
    private Integer status;

    public ReservationInfoPersonShowVo(Integer id, String place, String vaccine, String dateTime, Integer status) {
        this.id = id;
        this.place = place;
        this.vaccineName = vaccine;
        this.dateTime = dateTime;
        this.status = status;
    }
}
