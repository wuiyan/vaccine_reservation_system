package com.wuyan.vaccinereservation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyan.vaccinereservation.model.dto.PopularScienceInfoDto;
import com.wuyan.vaccinereservation.model.entity.PopularScienceInfo;

import java.util.List;


/**
* @author wuyan
* @description 针对表【popular_science_info】的数据库操作Mapper
* @createDate 2024-02-27 09:09:40
* @Entity com.wuyan.vaccinereservation.model.domain.PopularScienceInfo
*/
public interface PopularScienceInfoMapper extends BaseMapper<PopularScienceInfo> {
    List<PopularScienceInfoDto> getPopularScienceInfoDtoList();
}




