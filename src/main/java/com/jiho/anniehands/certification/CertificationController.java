package com.jiho.anniehands.certification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/certification")
@Slf4j
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping("/send-certification-code")
    public String sendSmsCertificationCode(@RequestParam String tel) {
        certificationService.sendOne(tel);
        return "SMS 인증번호가 발송되었습니다.";
    }
}
