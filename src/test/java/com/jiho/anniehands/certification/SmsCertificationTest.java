package com.jiho.anniehands.certification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class SmsCertificationTest {

    @Mock
    private StringRedisTemplate mockStringRedisTemplate;
    @Mock
    private ValueOperations<String, String> valueOperations;

    private SmsCertification smsCertification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockStringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        smsCertification = new SmsCertification(mockStringRedisTemplate);
    }

    @Test
    public void testCreateSmsCertification() {
        String tel = "01012345678";
        String certificationCode = "12345";

        smsCertification.createSmsCertification(tel, certificationCode);

        verify(valueOperations, times(1)).set("sms: " + tel, certificationCode, Duration.ofSeconds(3 * 60));
    }

    @Test
    public void testGetSmsCertification() {
        String tel = "01012345678";
        when(valueOperations.get("sms:" + tel)).thenReturn("12345");

        String returnedCode = smsCertification.getSmsCertification(tel);

        assertEquals("12345", returnedCode);
    }

    @Test
    public void testDeleteSmsCertification() {
        String tel = "01012345678";

        smsCertification.deleteSmsCertification(tel);

        verify(mockStringRedisTemplate, times(1)).delete("sms: " + tel);
    }

    @Test
    public void testHasKey() {
        String tel = "01012345678";
        when(mockStringRedisTemplate.hasKey("sms: " + tel)).thenReturn(true);

        boolean exists = smsCertification.hasKey(tel);

        assertTrue(exists);
    }
}