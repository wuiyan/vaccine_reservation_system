package com.wuyan.vaccinereservation.controller;

import com.wuyan.vaccinereservation.common.ResponseBody;
import com.wuyan.vaccinereservation.common.ResponseCode;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.vo.ReservationDetailVo;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoPersonShowVo;
import com.wuyan.vaccinereservation.service.ReservationInfoService;
import com.wuyan.vaccinereservation.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class VaccinationController {
    @Resource
    private ReservationInfoService reservationInfoService;

    @GetMapping("/vaccination")
    public ResponseBody<ReservationDetailVo> checkInfo(String code){
        if (StringUtils.isBlank(code)) {
            throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
        }
        return ResultUtils.success(reservationInfoService.checkVaccination(code));
    }
    @PutMapping("/vaccination")
    public ResponseBody<Boolean> checkInfo(int reservationId){
        if (reservationId<=0) {
            throw new BusinessException(ResponseCode.PARAMETER_ERROR);
        }
        return ResultUtils.success(reservationInfoService.confirmVaccination(reservationId));
    }
}
