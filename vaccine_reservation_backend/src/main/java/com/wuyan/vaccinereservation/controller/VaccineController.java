package com.wuyan.vaccinereservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wuyan.vaccinereservation.common.ResponseBody;
import com.wuyan.vaccinereservation.common.ResponseCode;
import com.wuyan.vaccinereservation.exception.BusinessException;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.model.entity.VaccineInfo;
import com.wuyan.vaccinereservation.service.ReservationPlanService;
import com.wuyan.vaccinereservation.service.VaccineInfoService;
import com.wuyan.vaccinereservation.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class VaccineController {
    @Resource
    private VaccineInfoService vaccineInfoService;

    /**
     * 获取疫苗列表信息
     */
    @GetMapping("/vaccines")
    public ResponseBody<IPage<VaccineInfo>> getVaccineInfoList(int pageNum, int size) {
        return ResultUtils.success(vaccineInfoService.getVaccineInfoList(pageNum, size));
    }

    /**
     * 通过type获取疫苗信息
     * @param type
     * @return
     */
    @GetMapping("/vaccines/type")
    public ResponseBody<List<VaccineInfo>> getVaccineInfoListByType(int type) {
        return ResultUtils.success(vaccineInfoService.getVaccineTypeList(type));
    }

    /**
     * 通过疫苗编号获取疫苗预约计划信息
     * @param vcNumber
     * @return
     */
    @GetMapping("/vaccines/vcNumber")
    public ResponseBody<List<VaccineInfo>> getReservationPlanListByVcNumber(String vcNumber) {
        return ResultUtils.success(vaccineInfoService.getVaccineVcNumberList(vcNumber));
    }

    /**
     * 通过疫苗名称获取疫苗信息
     * @param vcName
     * @return
     */
    @GetMapping("/vaccines/vcName")
    public ResponseBody<IPage<VaccineInfo>> getReservationPlanListByVcName(int pageNum,int size,String vcName) {
        return ResultUtils.success(vaccineInfoService.getVaccineInfoListByVcName(pageNum,size,vcName));
    }

    /**
     * 通过ID获取疫苗信息
     * @param id
     * @return
     */
    @GetMapping("/vaccines/id")
    public ResponseBody<VaccineInfo> getVaccineInfoListById(int id) {
        if (id<=0){
            throw new BusinessException(ResponseCode.PARAMETER_ERROR);
        }
        return ResultUtils.success(vaccineInfoService.getVaccineVcById(id));
    }

    /**
     * 通过疫苗名称模糊查询疫苗信息
     * @param vcName
     * @return
     */
    @GetMapping("/vaccines/search")
    public ResponseBody<List<VaccineInfo>> searchVaccineInfoByVcName(String vcName) {
        return ResultUtils.success(vaccineInfoService.searchVaccineInfoList(vcName));
    }

    /**
     * 新增一条疫苗信息
     */
    @PostMapping("/vaccines")
    public ResponseBody<Boolean> addVaccineInfo(@RequestBody VaccineInfo vaccineInfo) {
        System.out.println(vaccineInfo);
        if(vaccineInfo!=null){
            if(!StringUtils.isAllBlank(vaccineInfo.getVcNumber(),
                    vaccineInfo.getVcName(),
                    vaccineInfo.getPlace())){
                return ResultUtils.success(vaccineInfoService.insertVaccineInfo(vaccineInfo));
            }
        }
        throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
    }

    /**
     * 更新一条疫苗信息
     */
    @PutMapping("/vaccines")
    public ResponseBody<Boolean> updateVaccineInfo(@RequestBody VaccineInfo vaccineInfo) {
        System.out.println(vaccineInfo);
        if(vaccineInfo!=null){
            if(!StringUtils.isAllBlank(vaccineInfo.getVcNumber(),
                    vaccineInfo.getVcName(),
                    vaccineInfo.getPlace())){
                return ResultUtils.success(vaccineInfoService.updateVaccineInfo(vaccineInfo));
            }
        }
        throw new BusinessException(ResponseCode.PARAMETER_NULL_ERROR);
    }

    /**
     * 通过疫苗ID删除指定疫苗
     * @param vaccineId 待删除疫苗ID
     * @return 操作结果
     */
    @DeleteMapping("/vaccines")
    public ResponseBody<Boolean> deleteVaccineInfo(int vaccineId){
        if (vaccineId<=0){
            throw new BusinessException(ResponseCode.PARAMETER_ERROR);
        }

        return  ResultUtils.success(vaccineInfoService.deleteVaccineInfo(vaccineId));
    }

}
