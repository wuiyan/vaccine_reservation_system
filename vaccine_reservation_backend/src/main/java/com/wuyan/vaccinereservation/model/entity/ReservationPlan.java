package com.wuyan.vaccinereservation.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName reservation_plan
 */
@TableName(value ="reservation_plan")
@Data
public class ReservationPlan implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 疫苗id
     */
    private Integer vaccineId;

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
        ReservationPlan other = (ReservationPlan) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getVaccineId() == null ? other.getVaccineId() == null : this.getVaccineId().equals(other.getVaccineId()))
                && (this.getVaccineNumber() == null ? other.getVaccineNumber() == null : this.getVaccineNumber().equals(other.getVaccineNumber()))
                && (this.getPlanName() == null ? other.getPlanName() == null : this.getPlanName().equals(other.getPlanName()))
                && (this.getRvPlan() == null ? other.getRvPlan() == null : this.getRvPlan().equals(other.getRvPlan()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVaccineId() == null) ? 0 : getVaccineId().hashCode());
        result = prime * result + ((getVaccineNumber() == null) ? 0 : getVaccineNumber().hashCode());
        result = prime * result + ((getPlanName() == null) ? 0 : getPlanName().hashCode());
        result = prime * result + ((getRvPlan() == null) ? 0 : getRvPlan().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vaccineId=").append(vaccineId);
        sb.append(", vaccineNumber=").append(vaccineNumber);
        sb.append(", planName=").append(planName);
        sb.append(", rvPlan=").append(rvPlan);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}