package com.wuyan.vaccinereservation.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName reservation_info
 */
@TableName(value ="reservation_info")
@Data
public class ReservationInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 疫苗id
     */
    private Integer vaccineId;

    /**
     * 预约计划id
     */
    private Integer planId;

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

    /**
     * 逻辑删除 0-正常，1-删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ReservationInfo other = (ReservationInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getVaccineId() == null ? other.getVaccineId() == null : this.getVaccineId().equals(other.getVaccineId()))
                && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
                && (this.getNowDose() == null ? other.getNowDose() == null : this.getNowDose().equals(other.getNowDose()))
                && (this.getNowTime() == null ? other.getNowTime() == null : this.getNowTime().equals(other.getNowTime()))
                && (this.getRvStartDatetime() == null ? other.getRvStartDatetime() == null : this.getRvStartDatetime().equals(other.getRvStartDatetime()))
                && (this.getRvEndDatetime() == null ? other.getRvEndDatetime() == null : this.getRvEndDatetime().equals(other.getRvEndDatetime()))
                && (this.getRvStatus() == null ? other.getRvStatus() == null : this.getRvStatus().equals(other.getRvStatus()))
                && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getVaccineId() == null) ? 0 : getVaccineId().hashCode());
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getNowDose() == null) ? 0 : getNowDose().hashCode());
        result = prime * result + ((getNowTime() == null) ? 0 : getNowTime().hashCode());
        result = prime * result + ((getRvStartDatetime() == null) ? 0 : getRvStartDatetime().hashCode());
        result = prime * result + ((getRvEndDatetime() == null) ? 0 : getRvEndDatetime().hashCode());
        result = prime * result + ((getRvStatus() == null) ? 0 : getRvStatus().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", vaccineId=").append(vaccineId);
        sb.append(", planId=").append(planId);
        sb.append(", nowDose=").append(nowDose);
        sb.append(", nowTime=").append(nowTime);
        sb.append(", rvStartDatetime=").append(rvStartDatetime);
        sb.append(", rvEndDatetime=").append(rvEndDatetime);
        sb.append(", status=").append(rvStatus);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}