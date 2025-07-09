package com.demo.medicrecruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.demo.medicrecruitment", exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "com.demo.medicrecruitment.repository")
@SpringBootConfiguration
public class MedicRecruitmentDemoDddApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicRecruitmentDemoDddApplication.class, args);
    }

}
