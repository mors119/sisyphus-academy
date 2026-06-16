package com.sisyphus.backend;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@TestConfiguration
public class MockBeans {

    @MockitoBean
    JavaMailSender mailSender;

    @MockitoBean
    RedisTemplate<String, String> redisTemplate;
}
