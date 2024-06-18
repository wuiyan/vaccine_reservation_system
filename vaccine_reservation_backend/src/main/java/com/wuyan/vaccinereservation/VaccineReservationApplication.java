package com.wuyan.vaccinereservation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("com/wuyan/vaccinereservation/mapper")
@EnableTransactionManagement
public class VaccineReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccineReservationApplication.class, args);
    }

}
