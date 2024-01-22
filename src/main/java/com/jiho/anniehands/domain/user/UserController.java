package com.jiho.anniehands.domain.user;


import com.jiho.anniehands.certification.CertificationService;
import com.jiho.anniehands.common.exception.CustomErrorCode;
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

    private final UserService userService;
    private final UserRegistrationService userRegistrationService;
    private final CertificationService certificationService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "page/user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserForm userForm, BindingResult errors) {
        validateUserForm(userForm, errors);
        if (errors.hasErrors()) {
            return "page/user/signup";
        }
        userRegistrationService.registerUser(userForm);
        return "page/user/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "page/user/login";
    }

    private void validateUserForm(UserForm userForm, BindingResult errors) {
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", CustomErrorCode.MISMATCHED_PASSWORD.getCode(), CustomErrorCode.MISMATCHED_PASSWORD.getMessage());
        }
        if (!certificationService.verifySms(userForm.getTel(), userForm.getCertificationCode())) {
            errors.rejectValue("certificationCode", CustomErrorCode.INVALID_VERIFICATION_CODE.getCode(), CustomErrorCode.INVALID_VERIFICATION_CODE.getMessage());
        }
    }

}
