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

    /*
    messageService 필드는 NurigoApp인스턴스를 사용하여 초기화되며, 이 과정에서 apiKey와 apiSecretKey 같은 외부 설정 값이 필요하다.
    이 때 스프링은 빈(Bean) 객체 생성 후, 빈 간에 의존성 주입이 완료된 이후에만 이런 외부 설정 값을 사용할 수 있기 때문에 @PostConstruct 어노테이션을 이용하여
    초기화 콜백 시점으로 미뤄 초기화되어야 한다.
    final 키워드는 필드가 선언 시 또는 생성자를 통해 생성 시점에 초기화를 강제하며, 한 번 할당된 후에는 값이 변경될 수 없음을 의미한다.
    따라서, messageService 필드는 final 키워드를 사용할 수 없다.
    */
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
