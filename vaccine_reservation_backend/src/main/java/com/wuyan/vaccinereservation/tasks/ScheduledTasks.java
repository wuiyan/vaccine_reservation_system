package com.wuyan.vaccinereservation.tasks;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.wuyan.vaccinereservation.model.dto.ReservationInfoDto;
import com.wuyan.vaccinereservation.service.ReservationInfoService;
import com.wuyan.vaccinereservation.utils.DateTimeUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wuyan.vaccinereservation.common.GlobalConstant.SUBSCRIPTION_TMPLIDS;

public class ScheduledTasks {
    @Resource
    private ReservationInfoService reservationInfoService;

    @Resource
    private WxMaService wxMaService;

    @Scheduled(fixedRate = 3600000)
    public void VaccineReminder(){
        LocalDateTime now = LocalDateTime.now();
        // 获取预约信息列表
        List<ReservationInfoDto> reservationInfos = reservationInfoService.getReservationInfoDtoList();
        for (ReservationInfoDto info : reservationInfos) {
            // 当预约状态为已预约且用户提醒开关打开时进行下一步检查
            if (info.getRvStatus() == 0 && info.getUser().getRemind()>0) {
                LocalDateTime rvStartDatetime = dateToLocalDateTime(info.getRvStartDatetime());
                long hoursUntilAppointment = ChronoUnit.HOURS.between(now, rvStartDatetime);
                // 如果预约时间与当前时间差在一天之内则发送提醒
                if (hoursUntilAppointment > 0 && hoursUntilAppointment <= 24) {
                    sendWeChatNotification(info.getUser().getOpenid(),info);
                }
            }
        }
    }

    private LocalDateTime dateToLocalDateTime(Date date){
        // 将一个 Date 转为 LocalDateTime
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

    }
    private void sendWeChatNotification(String openId,ReservationInfoDto infoDto){
        WxMaMsgService msgService = wxMaService.getMsgService();
        String date = DateTimeUtils.DateToYearMonthDay(infoDto.getRvStartDatetime());
        String timeStart = DateTimeUtils.DateToTime(infoDto.getRvStartDatetime());
        String timeEnd = DateTimeUtils.DateToTime(infoDto.getRvEndDatetime());
        String DateTimes = date + " " + timeStart + "~" + timeEnd;
        List<WxMaSubscribeMessage.MsgData> msgData = new ArrayList<>();
        msgData.add(new WxMaSubscribeMessage.MsgData("thing9",infoDto.getUser().getName()));
        msgData.add(new WxMaSubscribeMessage.MsgData("date6",DateTimes));
        msgData.add(new WxMaSubscribeMessage.MsgData("thing15",infoDto.getVaccineInfo().getPlace()));
        msgData.add(new WxMaSubscribeMessage.MsgData("thing7","预约提醒：您的疫苗预约时间快到了！"));

        WxMaSubscribeMessage message = WxMaSubscribeMessage.builder()
                // 推送用户的openid
                .toUser(openId)
                .templateId(SUBSCRIPTION_TMPLIDS)
                .data(msgData)
                .page("/pages/user/index/index")
                .build();
        try {
            msgService.sendSubscribeMsg(message);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }
}
