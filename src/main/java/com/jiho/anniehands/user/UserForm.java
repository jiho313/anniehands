package com.jiho.anniehands.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserForm {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Size(max = 16, min = 4, message = "아이디는 4글자 이상 16이하여야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(max = 16, min = 4, message = "비밀번호는 4글자 이상 16이하여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 확인은 필수 입력 값입니다.")
    private String passwordConfirm;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 45)
    private String name;

    private String zipcode;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;

    private String detailAddress;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String tel;

    @NotBlank(message = "인증번호를 입력해주세요.")
    private String verificationCode;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Size(max = 255)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
}
