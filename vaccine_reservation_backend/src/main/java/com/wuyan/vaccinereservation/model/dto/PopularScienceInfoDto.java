package com.wuyan.vaccinereservation.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 用于科普信息展示的Dto对象，用于展示信息
 */
@Data
public class PopularScienceInfoDto {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 创建日期
     */
    private Date createTime;

}
