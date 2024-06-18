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
import com.wuyan.vaccinereservation.model.dto.ReservationInfoDto;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.model.entity.User;
import com.wuyan.vaccinereservation.model.entity.VaccineInfo;
import com.wuyan.vaccinereservation.model.pojo.Plan;
import com.wuyan.vaccinereservation.model.vo.ReservationDetailVo;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoListShowVo;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoPersonShowVo;
import com.wuyan.vaccinereservation.service.ReservationInfoService;
import com.wuyan.vaccinereservation.model.entity.ReservationInfo;
import com.wuyan.vaccinereservation.mapper.ReservationInfoMapper;
import com.wuyan.vaccinereservation.service.ReservationPlanService;
import com.wuyan.vaccinereservation.service.UserService;
import com.wuyan.vaccinereservation.utils.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static com.wuyan.vaccinereservation.common.GlobalConstant.*;
import static com.wuyan.vaccinereservation.common.ResponseCode.*;

/**
* @author wuyan
* @description 针对表【reservation_info】的数据库操作Service实现
* @createDate 2024-02-27 09:09:53
*/
@Service
public class ReservationInfoServiceImpl extends ServiceImpl<ReservationInfoMapper, ReservationInfo>
    implements ReservationInfoService {
    @Resource
    private UserService userService;
    @Resource
    private ReservationPlanService reservationPlanService;
    @Resource
    private ReservationInfoMapper reservationInfoMapper;


    @Override
    public IPage<ReservationInfoListShowVo> getReservationInfoListShowVoIPage(int pageNum, int size) {
        if (pageNum <= 0 || size <= 0){
            throw new BusinessException(PARAMETER_ERROR,"页数和页面大小不能小于零");
        }
        // 获取总的数据集合
        List<ReservationInfoDto> allInfo = reservationInfoMapper.selectReservationInfoDto();

        // 提取展示信息
        List<ReservationInfoListShowVo> reservationInfoListShowVos = allInfo.stream().map(reservationInfoDto -> {
            String startDate = DateTimeUtils.DateToYearMonthDay(reservationInfoDto.getRvStartDatetime());
            String startTime = DateTimeUtils.DateToTime(reservationInfoDto.getRvStartDatetime());
            String endTime = DateTimeUtils.DateToTime(reservationInfoDto.getRvEndDatetime());
            return new ReservationInfoListShowVo(
                    reservationInfoDto.getId(),
                    reservationInfoDto.getUser().getName(),
                    reservationInfoDto.getVaccineInfo().getVcName(),
                    startDate + " "+startTime +"~"+endTime,
                    reservationInfoDto.getRvStatus());
        }).collect(Collectors.toList());

        // 创建 Page 对象，手动设置总记录数
        Page<ReservationInfoListShowVo> page = new Page<>(pageNum, size);
        page.setTotal(reservationInfoListShowVos.size());

        // 手动截取需要的数据作为当前页的数据
        int fromIndex = (pageNum - 1) * size;
        int toIndex = Math.min(fromIndex + size, reservationInfoListShowVos.size());
        page.setRecords(reservationInfoListShowVos.subList(fromIndex, toIndex));

        // 返回 Page 对象
        return page;

    }

    @Override
    public List<ReservationInfoDto> getReservationInfoDtoList() {
        return reservationInfoMapper.selectReservationInfoDto();
    }

    @Override
    public List<ReservationInfoPersonShowVo> getReservationInfoByUserId(int userId) {
        if (userId<=0){
            throw new BusinessException(PARAMETER_ERROR);
        }
        List<ReservationInfoDto> allInfo = reservationInfoMapper.selectReservationInfoDto();
        List<ReservationInfoDto> infos = allInfo.stream().filter(info -> info.getUser().getId() == userId).collect(Collectors.toList());
        return infos.stream().map(i -> {
            String startDate = DateTimeUtils.DateToYearMonthDay(i.getRvStartDatetime());
            String startTime = DateTimeUtils.DateToTime(i.getRvStartDatetime());
            String endTime = DateTimeUtils.DateToTime(i.getRvEndDatetime());
            return new ReservationInfoPersonShowVo(
                    i.getId(),
                    i.getVaccineInfo().getPlace(),
                    i.getVaccineInfo().getVcName(),
                    startDate + " " + startTime + "~" + endTime,
                    i.getRvStatus());
        }).collect(Collectors.toList());
    }

    public ReservationDetailVo getReservationDetailVoByReservationId(int reservationId) {
        ReservationInfoDto infoDto = this.getReservationInfoDto(reservationId);
        User user = infoDto.getUser();
        VaccineInfo vaccineInfo = infoDto.getVaccineInfo();

        // 预约时间
        String date = DateTimeUtils.DateToYearMonthDay(infoDto.getRvStartDatetime());
        String timeStart = DateTimeUtils.DateToTime(infoDto.getRvStartDatetime());
        String timeEnd = DateTimeUtils.DateToTime(infoDto.getRvEndDatetime());
        String rvDateTime = date + " " + timeStart + "~" + timeEnd;

        return new ReservationDetailVo(
                infoDto.getId(),
                user.getName() ,user.getGender(), user.getPhone(), user.getCardType(), user.getCard(),
                vaccineInfo.getId(),vaccineInfo.getVcNumber(),vaccineInfo.getVcName(),vaccineInfo.getPrice(),vaccineInfo.getPlace(),
                infoDto.getPlan().getId(),infoDto.getNowDose(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(infoDto.getNowTime()),rvDateTime,infoDto.getRvStatus()
        );
    }

    @Override
    public ReservationInfoDto getReservationInfoDto(int reservationInfoId) {
        if (reservationInfoId<=0){
            throw new BusinessException(PARAMETER_ERROR);
        }
        return reservationInfoMapper.getReservationInfoDto(reservationInfoId);
    }

    @Override
    public List<ReservationInfo> getReservationInfoByVaccineId(int vaccineId) {
        QueryWrapper<ReservationInfo> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("vaccine_id", vaccineId)
                .lt("rv_status", STATUS_VACCINATED_NO_REMAINDER);
        return this.list(planQueryWrapper);
    }


    @Override
    public List<ReservationInfoListShowVo> searchReservationInfoList(String username) {
        // 两者皆为空，返回空集合
        if (StringUtils.isAllBlank(username)){
            return new ArrayList<>();
        }

        // 获取总的数据集合
        List<ReservationInfoDto> allInfo = reservationInfoMapper.selectReservationInfoDto();

        // 提取展示信息
        List<ReservationInfoListShowVo> reservationInfoListShowVos = allInfo.stream().filter(info-> info.getUser().getName().contains(username)).map(reservationInfoDto -> {
            String startDate = DateTimeUtils.DateToYearMonthDay(reservationInfoDto.getRvStartDatetime());
            String startTime = DateTimeUtils.DateToTime(reservationInfoDto.getRvStartDatetime());
            String endTime = DateTimeUtils.DateToTime(reservationInfoDto.getRvEndDatetime());
            return new ReservationInfoListShowVo(
                    reservationInfoDto.getId(),
                    reservationInfoDto.getUser().getName(),
                    reservationInfoDto.getVaccineInfo().getVcName(),
                    startDate + " "+startTime +"~"+endTime,
                    reservationInfoDto.getRvStatus());
        }).collect(Collectors.toList());
        return  reservationInfoListShowVos;
    }

    @Override
    @Transactional
    public boolean updateReservationInfo(ReservationInfo reservationInfo) {
        // 先通过逻辑删除方式去除预约计划中的相关信息
        boolean delResult =  this.deleteReservationInfo(reservationInfo.getId());
        // 真实删除这条记录
        int delInfo = reservationInfoMapper.deleteReservationInfo(reservationInfo.getId());
        // 插入更新后的记录，实现更新
        boolean insertResult = this.insertReservationInfo(reservationInfo);

        return delResult && insertResult && delInfo>0;
    }

    @Override
    @Transactional
    public boolean insertReservationInfo(ReservationInfo reservationInfo) {
        // 如果新增预约信息状态为已预约，才从预约计划中进行相应修改，其余只需操作预约信息即可
        if(reservationInfo.getRvStatus()==STATUS_RESERVED){
        // 获取预约计划信息
        QueryWrapper<ReservationPlan> reservationPlanQueryWrapper = new QueryWrapper<>();
        reservationPlanQueryWrapper.eq("id", reservationInfo.getPlanId());
        ReservationPlan reservationPlan = reservationPlanService.getOne(reservationPlanQueryWrapper);
        if (reservationPlan == null){
            throw new BusinessException(PARAMETER_ERROR);
        }

        // 从预约计划中添加被占用的数量
        // 解析预约计划
        ObjectMapper objectMapper = new ObjectMapper();
        List<Plan> reservations = null;
        try {
            reservations = objectMapper.readValue(reservationPlan.getRvPlan(), new TypeReference<List<Plan>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 预约时段信息
        String startDate = DateTimeUtils.DateToYearMonthDay(reservationInfo.getRvStartDatetime());
        String startTime = DateTimeUtils.DateToTime(reservationInfo.getRvStartDatetime());
        String endTime = DateTimeUtils.DateToTime(reservationInfo.getRvEndDatetime());
        // 循环遍历当前预约计划的所有时间段，确定预约的具体时段
        for (Plan reservation : reservations) {
            for (int j = 0; j < reservation.getPlan().size(); j++) {
                String date = reservation.getDate();
                if (date.equals(startDate)){
                    String timeStart = reservation.getPlan().get(j).getStartTime();
                    String timeEnd = reservation.getPlan().get(j).getEndTime();
                    if(timeStart.equals(startTime) && timeEnd.equals(endTime)){
                        // 找到对应时段后，为预约数减一
                        Integer bookedNum = reservation.getPlan().get(j).getBookedNum();
                        Integer planNum = reservation.getPlan().get(j).getPlanNum();
                        if (bookedNum<planNum){
                            reservation.getPlan().get(j).setBookedNum(bookedNum + 1);
                        }else {
                            throw new BusinessException(RESERVATION_IS_FULL);
                        }
                    }
                }
            }
        }
        // 设置更新条件和更新内容
        UpdateWrapper<ReservationPlan> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",reservationInfo.getPlanId());
        try {
            updateWrapper.set("rv_plan",objectMapper.writeValueAsString(reservations));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        reservationPlanService.update(updateWrapper);
    }


        return this.save(reservationInfo);
    }

    @Override
    @Transactional
    public boolean deleteReservationInfo(int reservationInfoId) {
        // 获取预约记录信息
        ReservationInfoDto reservationInfoDto = this.getReservationInfoDto(reservationInfoId);
        if (reservationInfoDto == null){
            throw new BusinessException(PARAMETER_ERROR);
        }
        // 如果要删除的记录状态为已预约，需从计划表中还原对应数据
        if(reservationInfoDto.getRvStatus()==STATUS_RESERVED){
            // 从预约计划中恢复被占用的数量
            // 解析预约计划
            ObjectMapper objectMapper = new ObjectMapper();
            List<Plan> reservations = null;
            try {
                reservations = objectMapper.readValue(reservationInfoDto.getPlan().getRvPlan(), new TypeReference<List<Plan>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            // 预约时段信息
            String startDate = DateTimeUtils.DateToYearMonthDay(reservationInfoDto.getRvStartDatetime());
            String startTime = DateTimeUtils.DateToTime(reservationInfoDto.getRvStartDatetime());
            String endTime = DateTimeUtils.DateToTime(reservationInfoDto.getRvEndDatetime());
            // 循环遍历当前预约计划的所有时间段，确定预约的具体时段
            for (Plan reservation : reservations) {
                for (int j = 0; j < reservation.getPlan().size(); j++) {
                    String date = reservation.getDate();
                    if (date.equals(startDate)){
                        String timeStart = reservation.getPlan().get(j).getStartTime();
                        String timeEnd = reservation.getPlan().get(j).getEndTime();
                        if(timeStart.equals(startTime) && timeEnd.equals(endTime)){
                            // 找到对应时段后，为预约数减一
                            Integer bookedNum = reservation.getPlan().get(j).getBookedNum();
                            if (bookedNum>0){
                                reservation.getPlan().get(j).setBookedNum(bookedNum - 1);
                            }
                        }
                    }
                }
            }
            // 设置更新条件和更新内容
            UpdateWrapper<ReservationPlan> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",reservationInfoDto.getPlan().getId());
            try {
                updateWrapper.set("rv_plan",objectMapper.writeValueAsString(reservations));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            reservationPlanService.update(updateWrapper);
        }

        QueryWrapper<ReservationInfo> reservationInfoQueryWrapper = new QueryWrapper<>();
        reservationInfoQueryWrapper.eq("id",reservationInfoId);
        // 其他预约状态直接移除数据信息即可
        return this.remove(reservationInfoQueryWrapper);
    }

    @Override
    public boolean cancelReservation(int reservationInfoId) {
        QueryWrapper<ReservationInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",reservationInfoId);
        ReservationInfo reservationInfo = this.getOne(queryWrapper);
        reservationInfo.setRvStatus(STATUS_CANCELLED);
        return updateReservationInfo(reservationInfo);
    }


    @Override
    public ReservationDetailVo checkVaccination(String code) {
        byte[] decodedBytes = Base64.getDecoder().decode(code);
        String decodedString = new String(decodedBytes);
        String[] strings = decodedString.split("\\+\\+");
        int reservationId = Integer.parseInt(strings[0]);
        ReservationDetailVo detailVo = this.getReservationDetailVoByReservationId(reservationId);
        if (detailVo.getRvStatus() != 0){
            throw new BusinessException(RESERVATION_VACCINATED);
        }
        return detailVo;

    }

    @Override
    public boolean confirmVaccination(int reservationInfoId) {
        QueryWrapper<ReservationInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",reservationInfoId);
        ReservationInfo reservationInfo = this.getOne(queryWrapper);

        ReservationInfoDto reservationInfoDto = this.getReservationInfoDto(reservationInfoId);
        if (reservationInfoDto.getVaccineInfo().getDose() > reservationInfoDto.getNowDose()){
            reservationInfo.setRvStatus(STATUS_VACCINATED_WITH_REMAINDER);
        }else {
            reservationInfo.setRvStatus(STATUS_VACCINATED_NO_REMAINDER);
        }
        return updateReservationInfo(reservationInfo);
    }
}




