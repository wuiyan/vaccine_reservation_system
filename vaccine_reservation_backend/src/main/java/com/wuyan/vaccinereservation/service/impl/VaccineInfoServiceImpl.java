package com.wuyan.vaccinereservation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.entity.ReservationInfo;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.service.ReservationInfoService;
import com.wuyan.vaccinereservation.service.ReservationPlanService;
import com.wuyan.vaccinereservation.service.VaccineInfoService;
import com.wuyan.vaccinereservation.model.entity.VaccineInfo;
import com.wuyan.vaccinereservation.mapper.VaccineInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.wuyan.vaccinereservation.common.ResponseCode.PARAMETER_ERROR;

/**
* @author wuyan
* @description 针对表【vaccine_info】的数据库操作Service实现
* @createDate 2024-02-27 09:10:28
*/
@Service
public class VaccineInfoServiceImpl extends ServiceImpl<VaccineInfoMapper, VaccineInfo>
    implements VaccineInfoService {
    @Resource
    private VaccineInfoMapper vaccineInfoMapper;
    @Resource
    private ReservationPlanService reservationPlanService;
    @Resource
    private ReservationInfoService reservationInfoService;

    @Override
    public IPage<VaccineInfo> getVaccineInfoList(int pageNum, int size) {
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");
        }
        QueryWrapper<VaccineInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true,false,"id");
        Page<VaccineInfo> page = new Page<>(pageNum,size);
        return this.page(page,queryWrapper);
    }

    @Override
    public List<VaccineInfo> getVaccineTypeList(int type) {
        if (type<0){
            throw new BusinessException(PARAMETER_ERROR,"类型大小不能小于零");
        }

        // 返回 Page 对象
        return vaccineInfoMapper.selectByType(type);
    }

    @Override
    public List<VaccineInfo> getVaccineVcNumberList(String vcNumber) {
        List<VaccineInfo> vaccineInfos = vaccineInfoMapper.selectByNumber(vcNumber);
        List<ReservationPlan> planByVaccineNumber = reservationPlanService.getReservationPlanByVaccineNumber(vcNumber);
        List<VaccineInfo> vaccinfoList = new ArrayList<>();
        for (int i = 0; i < vaccineInfos.size(); i++) {
            for (int j = 0; j < planByVaccineNumber.size(); j++) {
                if (Objects.equals(vaccineInfos.get(i).getId(), planByVaccineNumber.get(j).getVaccineId())){
                    vaccinfoList.add(vaccineInfos.get(i));
                }
            }
        }
        return vaccinfoList;
    }

    @Override
    public IPage<VaccineInfo> getVaccineInfoListByVcName(int pageNum, int size, String vcName) {
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");
        }
        if (StringUtils.isBlank(vcName)){
            return new Page<>();
        }
        QueryWrapper<VaccineInfo> vaccineInfoQueryWrapper = new QueryWrapper<>();
        vaccineInfoQueryWrapper.like("vc_name", vcName);
        Page<VaccineInfo> page = new Page<>(pageNum,size);
        return this.page(page, vaccineInfoQueryWrapper);
    }

    @Override
    public VaccineInfo getVaccineVcById(int vaccineInfoId) {
        QueryWrapper<VaccineInfo> vaccineInfoQueryWrapper = new QueryWrapper<>();
        vaccineInfoQueryWrapper.eq("id", vaccineInfoId);
        return this.getOne(vaccineInfoQueryWrapper);
    }

    @Override
    public List<VaccineInfo> searchVaccineInfoList(String vcName) {
        // 两者皆为空，返回空集合
        if (StringUtils.isAllBlank(vcName)){
            return new ArrayList<>();
        }

        // 有一个不为空则将不为空的条件进行查询
        QueryWrapper<VaccineInfo> vaccineInfoQueryWrapper = new QueryWrapper<>();
        vaccineInfoQueryWrapper.like(StringUtils.isNotBlank(vcName),"vc_name",vcName);
        return this.list(vaccineInfoQueryWrapper);
    }

    @Override
    public boolean updateVaccineInfo(VaccineInfo vaccineInfo) {
        UpdateWrapper<VaccineInfo> vaccineInfoUpdateWrapper = new UpdateWrapper<>();
        vaccineInfoUpdateWrapper.eq("id",vaccineInfo.getId());

        return this.update(vaccineInfo,vaccineInfoUpdateWrapper);
    }

    @Override
    public boolean insertVaccineInfo(VaccineInfo vaccineInfo) {
        return this.save(vaccineInfo);
    }

    @Override
    @Transactional
    public boolean deleteVaccineInfo(int vaccineInfoId) {
        // 检查预约信息表中有无使用中的疫苗记录
        List<ReservationInfo> reservationInfo = reservationInfoService.getReservationInfoByVaccineId(vaccineInfoId);
        if (reservationInfo.size()>0){
            throw new BusinessException("操作失败，存在正在使用中的预约信息！",50000,"");
        }

        // 从预约计划表中删除含有此疫苗信息的记录
        List<ReservationPlan> planByVaccine = reservationPlanService.getReservationPlanByVaccineId(vaccineInfoId);
        for (ReservationPlan reservationPlan : planByVaccine) {
            boolean b = reservationPlanService.deleteReservationPlan(reservationPlan.getId());
        }

        // 从疫苗信息表中删除疫苗信息
        QueryWrapper<VaccineInfo> vaccineInfoQueryWrapper = new QueryWrapper<>();
        vaccineInfoQueryWrapper.eq("id", vaccineInfoId);
        Boolean result = this.remove(vaccineInfoQueryWrapper);
        System.out.println(result);

        return result;
    }
}




