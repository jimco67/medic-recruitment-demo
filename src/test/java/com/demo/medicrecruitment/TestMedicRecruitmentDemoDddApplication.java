package com.demo.medicrecruitment;

import org.springframework.boot.SpringApplication;

public class TestMedicRecruitmentDemoDddApplication {

    public static void main(String[] args) {
        SpringApplication.from(MedicRecruitmentDemoDddApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
