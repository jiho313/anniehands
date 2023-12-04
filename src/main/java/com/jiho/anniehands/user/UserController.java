package com.jiho.anniehands.user;


import com.jiho.anniehands.exception.CustomErrorCode;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @GetMapping("/singup")
    public String singupForm(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "page/user/singup";
    }

    @PostMapping("/singup")
    public String singup(@Valid UserForm userForm, BindingResult errors,  HttpSession session ) {
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", CustomErrorCode.MISMATCHED_PASSWORD.getCode(), CustomErrorCode.MISMATCHED_PASSWORD.getMessage() );
        }
        String sessionVerificationCode = (String) session.getAttribute("verificationCode");
        if (!userForm.getVerificationCode().equals(sessionVerificationCode) && !userForm.getVerificationCode().isBlank()) {
            errors.rejectValue("verificationCode", "Invalid.Verification.Code", "인증번호가 일치하지 않습니다.");
        }
        if (errors.hasErrors()) {
            return "page/user/singup";
        }
//        userService
        return "page/user/complete";
    }

}
