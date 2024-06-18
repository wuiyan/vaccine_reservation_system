package com.wuyan.vaccinereservation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyan.vaccinereservation.model.entity.VaccineInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wuyan
* @description 针对表【vaccine_info】的数据库操作Mapper
* @createDate 2024-02-27 09:10:28
* @Entity com.wuyan.vaccinereservation.model.domain.VaccineInfo
*/
public interface VaccineInfoMapper extends BaseMapper<VaccineInfo> {
    List<VaccineInfo> selectByNumber(@Param("vcNumber") String vcNumber);

    VaccineInfo selectVaccineById(@Param("id") Integer id);

    List<VaccineInfo> selectByType(@Param("type") int type);
}




