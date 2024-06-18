package com.wuyan.vaccinereservation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.dto.ReservationPlanDto;
import com.wuyan.vaccinereservation.model.pojo.Plan;
import com.wuyan.vaccinereservation.model.vo.ReservationPlanPromptVo;
import com.wuyan.vaccinereservation.model.vo.ReservationPlanShowVo;
import com.wuyan.vaccinereservation.service.ReservationPlanService;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.mapper.ReservationPlanMapper;
import com.wuyan.vaccinereservation.utils.ReservationPlanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuyan.vaccinereservation.common.ResponseCode.PARAMETER_ERROR;

/**
* @author wuyan
* @description 针对表【reservation_plan】的数据库操作Service实现
* @createDate 2024-02-27 09:10:57
*/
@Service
@Slf4j
public class ReservationPlanServiceImpl extends ServiceImpl<ReservationPlanMapper, ReservationPlan>
    implements ReservationPlanService {
    @Resource
    private ReservationPlanMapper reservationPlanMapper;

    @Override
    public IPage<ReservationPlanShowVo> getReservationPlanShowVoList(int pageNum, int size) {
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");
        }
        List<ReservationPlanDto> planDtoList = reservationPlanMapper.selectReservationPlanDto();
        List<ReservationPlanShowVo> planShowVos = planDtoList.stream().map(plan -> new ReservationPlanShowVo(plan.getId(), plan.getVaccineInfo().getVcName(), plan.getVaccineInfo().getPlace(), plan.getPlanName(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(plan.getCreateTime()))).collect(Collectors.toList());


        // 创建 Page 对象，手动设置总记录数
        Page<ReservationPlanShowVo> page = new Page<>(pageNum, size);
        page.setTotal(planShowVos.size());

        // 手动截取需要的数据作为当前页的数据
        int fromIndex = (pageNum - 1) * size;
        int toIndex = Math.min(fromIndex + size, planShowVos.size());
        page.setRecords(planShowVos.subList(fromIndex, toIndex));

        // 返回 Page 对象
        return page;
    }

    @Override
    public ReservationPlanDto getReservationPlanDtoById(int planId) {
        if (planId<=0){
            throw new BusinessException(PARAMETER_ERROR,"id大小不能小于零");
        }
        ReservationPlanDto reservationPlanDto = reservationPlanMapper.getReservationPlanDto(planId);
        return reservationPlanDto;
    }

    @Override
    public ReservationPlan getReservationPlanById(int id) {
        if (id<=0){
            throw new BusinessException(PARAMETER_ERROR,"id大小不能小于零");
        }
        return this.getById(id);
    }

    @Override
    public List<ReservationPlan> getReservationPlanByVaccineId(int vaccineId) {
        QueryWrapper<ReservationPlan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("vaccine_id", vaccineId);
        return this.list(planQueryWrapper);
    }

    @Override
    public ReservationPlanDto getPlanDtoByVaccineId(int vaccineId) {
        return reservationPlanMapper.getPlanDtoByVaccineId(vaccineId);
    }

    @Override
    public List<ReservationPlan> getReservationPlanByVaccineNumber(String vaccineNumber) {
        QueryWrapper<ReservationPlan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("vaccine_number", vaccineNumber);
        return this.list(planQueryWrapper);
    }

    @Override
    public IPage<ReservationPlanPromptVo> searchListByVcName(String vcName, int pageNum, int size) {
        // 两者皆为空，返回空集合
        if (StringUtils.isAllBlank(vcName)){
            return new Page<ReservationPlanPromptVo>();
        }
        // 信息校验，判断页数和页面大小不小于等于零
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");
        }
        List<ReservationPlanDto> planDtoList = reservationPlanMapper.selectReservationPlanDto();

        List<ReservationPlanDto> filterPlanDto = planDtoList.stream().filter(planDto -> planDto.getVaccineInfo().getVcName().contains(vcName)).collect(Collectors.toList());

        List<ReservationPlanPromptVo> planPromptVoList = filterPlanDto.stream().map(plan -> {
            List<String> rvDateTime = new ArrayList<>();
            String rvPlan = plan.getRvPlan();
            // 解析预约计划
            ObjectMapper objectMapper = new ObjectMapper();
            List<Plan> reservationPlans = null;
            try {
                reservationPlans = objectMapper.readValue(rvPlan, new TypeReference<List<Plan>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            // 循环遍历当前预约计划的所有时间段，确定预约的具体时段
            for (Plan reservationPlan : reservationPlans) {
                String date = reservationPlan.getDate();
                for (int j = 0; j < reservationPlan.getPlan().size(); j++) {
                    String timeStart = reservationPlan.getPlan().get(j).getStartTime();
                    String timeEnd = reservationPlan.getPlan().get(j).getEndTime();
                    rvDateTime.add(date + " " + timeStart + "~" + timeEnd);
                }
            }
            return new ReservationPlanPromptVo(plan.getId(), rvDateTime, plan.getVaccineInfo().getId(), plan.getVaccineNumber(), plan.getVaccineInfo().getVcName(), plan.getVaccineInfo().getPrice(),plan.getVaccineInfo().getPlace(), plan.getVaccineInfo().getDose());
        }).collect(Collectors.toList());


        // 创建 Page 对象，手动设置总记录数
        Page<ReservationPlanPromptVo> page = new Page<>(pageNum, size);
        page.setTotal(planPromptVoList.size());

        // 手动截取需要的数据作为当前页的数据
        int fromIndex = (pageNum - 1) * size;
        int toIndex = Math.min(fromIndex + size, planPromptVoList.size());
        page.setRecords(planPromptVoList.subList(fromIndex, toIndex));

        return page;

    }


    @Override
    public List<ReservationPlanShowVo> searchReservationPlanShowVoList(String planName) {
        // 两者皆为空，返回空集合
        if (StringUtils.isAllBlank(planName)){
            return new ArrayList<>();
        }

        List<ReservationPlanDto> planDtoList = reservationPlanMapper.selectReservationPlanDto();

        return planDtoList.stream().filter(plan->plan.getPlanName().contains(planName)).map(plan -> new ReservationPlanShowVo(plan.getId(), plan.getVaccineInfo().getVcName(), plan.getVaccineInfo().getPlace(), plan.getPlanName(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(plan.getCreateTime()))).collect(Collectors.toList());
    }

    @Override
    public boolean updateReservationPlan(ReservationPlan reservationPlan) {
        QueryWrapper<ReservationPlan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("id", reservationPlan.getId());
        ReservationPlan plan = this.getOne(planQueryWrapper);
        boolean checkPlanIsNull = ReservationPlanUtils.checkPlanIsNull(plan);
        if (!checkPlanIsNull){
            throw new BusinessException("已有人预约，无法更新当前预约计划！", 50000, "");
        }
        // 无人预约
        UpdateWrapper<ReservationPlan> planUpdateWrapper = new UpdateWrapper<>();
        planUpdateWrapper.eq("id",reservationPlan.getId());

        return this.update(reservationPlan,planUpdateWrapper);
    }

    @Override
    public boolean insertReservationPlan(ReservationPlan reservationPlan) {
        return this.save(reservationPlan);
    }

    @Override
    public boolean deleteReservationPlan(int reservationPlanId) {
        // 获取将要删除的信息，检查是否存在预约者
        QueryWrapper<ReservationPlan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("id", reservationPlanId);
        ReservationPlan plan = this.getOne(planQueryWrapper);
        // 解析预约计划
        ObjectMapper objectMapper = new ObjectMapper();
        List<Plan> reservations = null;
        try {
            reservations = objectMapper.readValue(plan.getRvPlan(), new TypeReference<List<Plan>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(reservations.toString());
        // 循环遍历当前预约计划的所有时间段，检测是否存在预约人
        for (Plan reservation : reservations) {
            for (int j = 0; j < reservation.getPlan().size(); j++) {
                Integer bookedNum = reservation.getPlan().get(j).getBookedNum();
                // 若预约计划中存在已被预约的数据，则不能删除此预约计划
                if (bookedNum > 0) {
                    throw new BusinessException("已有人预约，当前预约计划无法删除！", 50000, "");
                }
            }
        }
        return this.remove(planQueryWrapper);
    }
}




