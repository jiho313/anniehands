package com.jiho.anniehands.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class SmsCertification {
    // 키 값 중복방지 상수
    private final String PREFIX = "sms:";
    private final int LIMIT_TIME = 3 * 60;
    private final StringRedisTemplate stringRedisTemplate;

    // 레디스 저장
    public void createSmsCertification(String tel, String certificationCode) {
        stringRedisTemplate.opsForValue()
                .set((PREFIX + tel), certificationCode, Duration.ofSeconds(LIMIT_TIME));
    }

    // 핸드폰 번호에 해당하는 인증번호 불러오기
    public String getSmsCertification(String tel) {
        return stringRedisTemplate.opsForValue().get(PREFIX + tel);
    }

    // 인증 완료 후 레디스에서 인증 번호 삭제
    public void deleteSmsCertification(String tel) {
        stringRedisTemplate.delete(PREFIX + tel);
    }

    // 레디스에 해당 핸드폰에 저장된 인증번호가 존재하는지 확인
    public boolean hasKey(String tel) {
        return stringRedisTemplate.hasKey(PREFIX + tel);
    }



}
