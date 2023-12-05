package com.jiho.anniehands.certification;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificationService {

    @Value("${spring.coolsms.api.key}")
    private String apiKey;
    @Value("${spring.coolsms.api.secret}")
    private String apiSecretKey;
    @Value("${spring.coolsms.senderNumber}")
    private String senderNumber;

    private DefaultMessageService messageService;
    private final SmsCertification smsCertification;

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 예제
    public SingleMessageSentResponse sendOne(String tel) {
        Message message = new Message();
        String certificationCode = createCertificationCode();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(senderNumber);
        message.setTo(tel);
        message.setText("[AnnieHands!] 아래의 인증번호를 입력해주세요!\n" + certificationCode);

        smsCertification.createSmsCertification(tel, certificationCode);

        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    // 인증 번호 검증
    public boolean verifySms(String tel, String certificationCode) {
        boolean isVerified = isVerify(tel, certificationCode);
        if (isVerified) {
            // 인증 성공시 인증번호 삭제
            smsCertification.deleteSmsCertification(tel);
        }
        return isVerified;
    }

    private boolean isVerify(String tel, String certificationCode) {
        log.info(smsCertification.getSmsCertification(tel));
        return (smsCertification.hasKey(tel) && smsCertification.getSmsCertification(tel)
                        .equals(certificationCode));
    }

    private String createCertificationCode() {
        Random random = new Random();
        return String.format("%05d", random.nextInt(10000));
    }
}
