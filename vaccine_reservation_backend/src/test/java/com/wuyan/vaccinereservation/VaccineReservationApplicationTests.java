package com.wuyan.vaccinereservation;

import com.wuyan.vaccinereservation.model.entity.Admin;
import com.wuyan.vaccinereservation.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VaccineReservationApplicationTests {

    @Test
    void contextLoads() {
        AdminServiceImpl adminService = new AdminServiceImpl();


    }

}
