package com.sisyphus.backend.integration;

import com.sisyphus.backend.BackendApplication;
import com.sisyphus.backend.support.MockBeans;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = BackendApplication.class)
@ActiveProfiles("test")
@Import(MockBeans.class)
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }
}
