package com.demo.medicrecruitment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class MedicRecruitmentDemoDddApplicationTests {

    @Test
    void contextLoads() {
    }

}
