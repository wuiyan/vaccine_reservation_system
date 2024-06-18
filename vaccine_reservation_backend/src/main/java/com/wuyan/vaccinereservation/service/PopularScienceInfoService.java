package com.wuyan.vaccinereservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyan.vaccinereservation.model.dto.PopularScienceInfoDto;
import com.wuyan.vaccinereservation.model.entity.PopularScienceInfo;

import java.util.List;

/**
* @author wuyan
* @description 针对表【popular_science_info】的数据库操作Service
* @createDate 2024-02-27 09:09:40
*/
public interface PopularScienceInfoService extends IService<PopularScienceInfo> {
    /**
     * 获取PopularScienceInfoDto的列表信息
     *
     * @param pageNum 页数
     * @param size    页面大小
     * @return 科普信息分页列表
     */
    IPage<PopularScienceInfoDto> getPopularScienceInfoDtoList(int pageNum, int size);

    IPage<PopularScienceInfo> getPopularScienceInfoList(int pageNum, int size);
    // 通过id 精确查询
    PopularScienceInfo searchPopularScienceInfo(int popularScienceInfoId);

    /**
     *  用户模糊查询 通过标题
     */
    List<PopularScienceInfo> searchPopularScienceInfoList(String title);

    /**
     *  管理员模糊查询 通过标题
     */
    List<PopularScienceInfoDto> searchAdminPopularScienceInfoList(String title);

    /**
     * 更新PopularScienceInfo信息
     * @param popularScienceInfo 科普信息实体
     * @return 操作结果的布尔值
     */
    boolean updatePopularScienceInfo(PopularScienceInfo popularScienceInfo);
    // 插入一条PopularScienceInfo信息
    boolean insertPopularScienceInfo(PopularScienceInfo popularScienceInfo);

    /**
     * 删除一条PopularScienceInfo信息
     * @param popularScienceInfoId 科普信息ID
     * @return 操作结果
     */
    boolean deletePopularScienceInfo(int popularScienceInfoId);
}
