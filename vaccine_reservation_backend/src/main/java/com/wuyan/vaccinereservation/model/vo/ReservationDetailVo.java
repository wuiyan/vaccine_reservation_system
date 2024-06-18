package com.wuyan.vaccinereservation.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预约详情展示对象
 */
@Data
public class ReservationDetailVo {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /*
      ---------------- 用户信息 ---------------
     */

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄 0-女，1-男
     */
    private Integer gender;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 证件类型 0-身份证
     */
    private Integer cardType;

    /**
     * 证件号
     */
    private String card;

    /*
      ---------------- 疫苗信息 ----------------
     */
    /**
     * 疫苗ID
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
     * 参考价格
     */
    private BigDecimal price;
    /**
     * 预约地点
     */
    private String place;

    /*
      --------------- 预约信息 --------------
     */

    /**
     * 预约计划ID
     */
    private int planId;

    /**
     * 当前剂次
     */
    private Integer nowDose;

    /**
     * 用户预约申请时间
     */
    private String nowTime;

    /**
     * 预约时间
     */
    private String rvDateTime;


    /**
     * 预约状态
     */
    private Integer rvStatus;

    public ReservationDetailVo(Integer id, String name, Integer gender, String phone, Integer cardType, String card, int vaccineId, String vcNumber, String vcName, BigDecimal price, String place, int planId, Integer nowDose, String nowTime, String rvDateTime, Integer rvStatus) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.cardType = cardType;
        this.card = card;
        this.vaccineId = vaccineId;
        this.vcNumber = vcNumber;
        this.vcName = vcName;
        this.price = price;
        this.place = place;
        this.planId = planId;
        this.nowDose = nowDose;
        this.nowTime = nowTime;
        this.rvDateTime = rvDateTime;
        this.rvStatus = rvStatus;
    }
}
