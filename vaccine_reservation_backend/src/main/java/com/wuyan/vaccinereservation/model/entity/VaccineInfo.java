package com.wuyan.vaccinereservation.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName vaccine_info
 */
@TableName(value ="vaccine_info")
@Data
public class VaccineInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 疫苗编号
     */
    private String vcNumber;

    /**
     * 疫苗名称
     */
    private String vcName;

    /**
     * 剂次
     */
    private Integer dose;

    /**
     * 间隔时间 - 天
     */
    private Integer spaceTime;

    /**
     * 参考价格
     */
    private BigDecimal price;

    /**
     * 疫苗分裂
     */
    private Integer type;

    /**
     * 预约地点
     */
    private String place;

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
        VaccineInfo other = (VaccineInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVcNumber() == null ? other.getVcNumber() == null : this.getVcNumber().equals(other.getVcNumber()))
            && (this.getVcName() == null ? other.getVcName() == null : this.getVcName().equals(other.getVcName()))
            && (this.getDose() == null ? other.getDose() == null : this.getDose().equals(other.getDose()))
            && (this.getSpaceTime() == null ? other.getSpaceTime() == null : this.getSpaceTime().equals(other.getSpaceTime()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPlace() == null ? other.getPlace() == null : this.getPlace().equals(other.getPlace()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVcNumber() == null) ? 0 : getVcNumber().hashCode());
        result = prime * result + ((getVcName() == null) ? 0 : getVcName().hashCode());
        result = prime * result + ((getDose() == null) ? 0 : getDose().hashCode());
        result = prime * result + ((getSpaceTime() == null) ? 0 : getSpaceTime().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPlace() == null) ? 0 : getPlace().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vcNumber=").append(vcNumber);
        sb.append(", vcName=").append(vcName);
        sb.append(", dose=").append(dose);
        sb.append(", spaceTime=").append(spaceTime);
        sb.append(", price=").append(price);
        sb.append(", type=").append(type);
        sb.append(", place=").append(place);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}