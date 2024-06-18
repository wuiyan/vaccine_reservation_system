package com.wuyan.vaccinereservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuyan.vaccinereservation.model.entity.ReservationPlan;
import com.wuyan.vaccinereservation.model.pojo.Plan;
import com.wuyan.vaccinereservation.model.pojo.PlanInfo;
import com.wuyan.vaccinereservation.service.ReservationPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作JSON数据的测试类
 */
@SpringBootTest
public class JsonDataTest {
    @Resource
    private ReservationPlanService reservationPlanService;
    @Test
    public void JsonParse() throws JsonProcessingException {
        ReservationPlan planById = reservationPlanService.getReservationPlanById(1);
        System.out.println(planById);
        String jsonContent = planById.getRvPlan();
        System.out.println("输出数据库中的预约计划内容：");
        System.out.println(jsonContent);

        Plan planContent = new Plan();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Plan> reservations = objectMapper.readValue(jsonContent, new TypeReference<List<Plan>>() {});
        System.out.println(reservations);


//        planContent.setDate("2024-03-12");
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("startTime","08:00");
//        stringStringHashMap.put("endTime","10:00");
//        planContent.setPlan(new ArrayList<PlanInfo>(){{
//            add(new PlanInfo());
//        }});
//        System.out.println("直接输出planContent对象：");
//        System.out.println(planContent);

//        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(planContent);
//        System.out.println("将planContent对象格式化输出为JSON数据：");
//        System.out.println(jsonString);

    }

}
