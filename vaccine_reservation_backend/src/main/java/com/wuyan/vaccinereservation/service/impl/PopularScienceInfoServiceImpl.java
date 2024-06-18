package com.wuyan.vaccinereservation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.dto.PopularScienceInfoDto;
import com.wuyan.vaccinereservation.service.PopularScienceInfoService;
import com.wuyan.vaccinereservation.model.entity.PopularScienceInfo;
import com.wuyan.vaccinereservation.mapper.PopularScienceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuyan.vaccinereservation.common.ResponseCode.PARAMETER_ERROR;

/**
* @author wuyan
* @description 针对表【popular_science_info】的数据库操作Service实现
* @createDate 2024-02-27 09:09:40
*/
@Service
public class PopularScienceInfoServiceImpl extends ServiceImpl<PopularScienceInfoMapper, PopularScienceInfo>
    implements PopularScienceInfoService {
    @Resource
    private PopularScienceInfoMapper popularScienceInfoMapper;

    @Override
    public IPage<PopularScienceInfoDto> getPopularScienceInfoDtoList(int pageNum, int size) {
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");

        }
        List<PopularScienceInfoDto> scienceInfoDtoList = popularScienceInfoMapper.getPopularScienceInfoDtoList();

        // 创建 Page 对象，手动设置总记录数
        Page<PopularScienceInfoDto> page = new Page<>(pageNum, size);
        page.setTotal(scienceInfoDtoList.size());

        // 手动截取需要的数据作为当前页的数据
        int fromIndex = (pageNum - 1) * size;
        int toIndex = Math.min(fromIndex + size, scienceInfoDtoList.size());
        page.setRecords(scienceInfoDtoList.subList(fromIndex, toIndex));

        // 返回 Page 对象
        return page;
    }

    @Override
    public IPage<PopularScienceInfo> getPopularScienceInfoList(int pageNum, int size) {
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");

        }

        Page<PopularScienceInfo> page = new Page<>(pageNum,size);
        return this.page(page);

    }

    @Override
    public PopularScienceInfo searchPopularScienceInfo(int popularScienceInfoId) {
        QueryWrapper<PopularScienceInfo> scienceInfoQueryWrapper = new QueryWrapper<>();
        scienceInfoQueryWrapper.eq("id", popularScienceInfoId);
        return this.getOne(scienceInfoQueryWrapper);
    }

    @Override
    public List<PopularScienceInfo> searchPopularScienceInfoList(String title) {
        if (StringUtils.isAllBlank(title)){
            return new ArrayList<>();
        }

        // 有一个不为空则将不为空的条件进行查询
        QueryWrapper<PopularScienceInfo> scienceInfoQueryWrapper = new QueryWrapper<>();
        scienceInfoQueryWrapper.like("title",title);
        return this.list(scienceInfoQueryWrapper);
    }

    @Override
    public List<PopularScienceInfoDto> searchAdminPopularScienceInfoList(String title) {
        if (StringUtils.isBlank(title)) {
            return new ArrayList<>();
        }
        List<PopularScienceInfoDto> scienceInfoDtoList = popularScienceInfoMapper.getPopularScienceInfoDtoList();
        return scienceInfoDtoList.stream().filter(science -> science.getTitle().contains(title)).collect(Collectors.toList());

    }

    @Override
    public boolean updatePopularScienceInfo(PopularScienceInfo popularScienceInfo) {
        UpdateWrapper<PopularScienceInfo> scienceInfoUpdateWrapper = new UpdateWrapper<>();
        scienceInfoUpdateWrapper.eq("id",popularScienceInfo.getId());

        return this.update(popularScienceInfo,scienceInfoUpdateWrapper);
    }

    @Override
    public boolean insertPopularScienceInfo(PopularScienceInfo popularScienceInfo) {
        return this.save(popularScienceInfo);
    }

    @Override
    public boolean deletePopularScienceInfo(int popularScienceInfoId) {
        if (popularScienceInfoId<=0){
            throw new BusinessException(PARAMETER_ERROR);
        }
        QueryWrapper<PopularScienceInfo> scienceInfoQueryWrapper = new QueryWrapper<>();
        scienceInfoQueryWrapper.eq("id", popularScienceInfoId);
        return this.remove(scienceInfoQueryWrapper);
    }
}




