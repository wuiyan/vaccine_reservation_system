package com.wuyan.vaccinereservation.service.impl;

import com.wuyan.vaccinereservation.service.VaccineInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VaccineInfoServiceImplTest {
    @Resource
    VaccineInfoService vaccineInfoService;

    @Test
    void deleteVaccineInfo() {
        this.vaccineInfoService.deleteVaccineInfo(5);
    }
}