package com.jiho.anniehands.domain.user;

import com.jiho.anniehands.common.exception.CustomErrorCode;
import com.jiho.anniehands.common.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 저장
    public User saveUser(UserForm userForm) {
        User user = createUser(userForm);
        validateDuplicateUser(user);
        userRepository.save(user);
        return user;
    }

    // 사용자 생성
    private User createUser(UserForm userForm) {
        String encryptedPassword = passwordEncoder.encode(userForm.getPassword());
        return User.builder()
                .id(userForm.getId())
                .name(userForm.getName())
                .email(userForm.getEmail())
                .password(encryptedPassword)
                .tel(userForm.getTel())
                .birthdate(userForm.getBirth())
                .loginInfo(UserLoginType.ANNIEHANDS)
                .role(Role.ROLE_USER)
                .build();
    }

    // 아이디 혹은 이메일 중복 검사
    private void validateDuplicateUser(User user) {
        userRepository.findById(user.getId()).ifPresent(u -> {
            throw new UserException(CustomErrorCode.DUPLICATE_USERNAME, "/user/signup");
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserException(CustomErrorCode.DUPLICATE_EMAIL, "/user/signup");
        });
    }

}
