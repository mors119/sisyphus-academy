package com.sisyphus.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(MockBeans.class)
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
