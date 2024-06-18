package com.wuyan.vaccinereservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyan.vaccinereservation.model.dto.ReservationInfoDto;
import com.wuyan.vaccinereservation.model.entity.ReservationInfo;
import com.wuyan.vaccinereservation.model.vo.ReservationDetailVo;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoListShowVo;
import com.wuyan.vaccinereservation.model.vo.ReservationInfoPersonShowVo;

import java.util.List;

/**
* @author wuyan
* @description 针对表【reservation_info】的数据库操作Service
* @createDate 2024-02-27 09:09:53
*/
public interface ReservationInfoService extends IService<ReservationInfo> {
    /**
     * 获取ReservationInfoListShowVo的分页信息
     */
    IPage<ReservationInfoListShowVo> getReservationInfoListShowVoIPage(int pageNum, int size);

    /**
     * 获取 ReservationInfoDto 的全部列表信息
     */
    List<ReservationInfoDto> getReservationInfoDtoList();

    /**
     * 获取 ReservationInfoShowVo 的全部列表信息
     */
    List<ReservationInfoPersonShowVo> getReservationInfoByUserId(int userId);

    /**
     * 通过预约id获取预约展示信息
     */
    ReservationDetailVo getReservationDetailVoByReservationId(int reservationId);

    /**
     * 获取对应 id 的 ReservationInfoDto 对象信息
     */
    ReservationInfoDto getReservationInfoDto(int reservationInfoId);

    /**
     * 通过疫苗id来查询状态为正在使用中的疫苗预约信息记录
     */
    List<ReservationInfo> getReservationInfoByVaccineId(int vaccineId);


    // 模糊查询 通过姓名和电话号码
    List<ReservationInfoListShowVo> searchReservationInfoList(String username);




    /**
     * 更新ReservationInfo信息
     */
    boolean updateReservationInfo(ReservationInfo reservationInfo);
    /**
     * 插入一条ReservationInfo信息
     */
    boolean insertReservationInfo(ReservationInfo reservationInfo);
    /**
     *  删除一条ReservationInfo信息
     */
    boolean deleteReservationInfo(int reservationInfoId);

    /**
     * 取消用户预约
     */
    boolean cancelReservation(int reservationInfoId);


    /**
     * 疫苗接种实现，检验疫苗是否符合接种状态，并返回相应结果
     */
    ReservationDetailVo checkVaccination(String code);
    /**
     * 确认疫苗接种
     */
    boolean confirmVaccination(int reservationInfoId);
}
