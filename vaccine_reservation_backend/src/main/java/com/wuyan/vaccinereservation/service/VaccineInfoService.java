package com.wuyan.vaccinereservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyan.vaccinereservation.model.entity.VaccineInfo;

import java.util.List;

/**
* @author wuyan
* @description 针对表【vaccine_info】的数据库操作Service
* @createDate 2024-02-27 09:10:28
*/
public interface VaccineInfoService extends IService<VaccineInfo> {
    /**
     * 获取VaccineInfo列表信息
     * @param pageNum 页数
     * @param size 页面大小
     * @return 疫苗信息列表
     */
    IPage<VaccineInfo> getVaccineInfoList(int pageNum, int size);
    // 通过type获取疫苗种类信息
    List<VaccineInfo> getVaccineTypeList(int type);

    /**
     * 通过vcNumber获取存在预约计划的疫苗信息
     */
    List<VaccineInfo> getVaccineVcNumberList(String vcNumber);

    /**
     * 通过vcName获取疫苗信息
     */
    IPage<VaccineInfo> getVaccineInfoListByVcName(int pageNum, int size,String vcName);
    // 通过id获取疫苗信息
    VaccineInfo getVaccineVcById(int vaccineInfoId);

    // 模糊查询 通过编号和名称
    List<VaccineInfo> searchVaccineInfoList(String vcName);

    // 更新VaccineInfo信息
    boolean updateVaccineInfo(VaccineInfo vaccineInfo);
    // 插入一条VaccineInfo信息
    boolean insertVaccineInfo(VaccineInfo vaccineInfo);
    /**
     *  删除一条VaccineInfo信息
     */
    boolean deleteVaccineInfo(int vaccineInfoId);
}
