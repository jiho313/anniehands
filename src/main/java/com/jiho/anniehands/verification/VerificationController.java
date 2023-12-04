package com.jiho.anniehands.verification;

import com.jiho.anniehands.util.SmsUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/verification")
@Slf4j
public class VerificationController {

    private final SmsUtil smsUtil;

    @PostMapping("/send-verification-code")
    @ResponseBody
    public String sendVerificationCode(@RequestParam String tel, HttpSession session) {
        String verificationCode = generateVerificationCode();
        smsUtil.sendOne(tel, verificationCode);
        session.setAttribute("verificationCode", verificationCode);
        return "인증번호가 발송되었습니다.";
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%05d", random.nextInt(10000));
    }
}
