package com.jiho.anniehands.user;

import com.jiho.anniehands.exception.CustomErrorCode;
import com.jiho.anniehands.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

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
                .loginInfo(UserLoginInfo.ANNIEHANDS)
                .role(Role.ROLE_USER)
                .build();
    }


    // 아이디 혹은 이메일 중복 검사
    private void validateDuplicateUser(User user) {
        userRepository.findById(user.getId()).ifPresent(u -> {
            throw new UserException(CustomErrorCode.DUPLICATE_USERNAME);
        });
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserException(CustomErrorCode.DUPLICATE_EMAIL);
        });
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(CustomErrorCode.NO_MATCHING_MEMBER));
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(CustomErrorCode.NO_MATCHING_MEMBER.getMessage()));
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .map(CustomUserDetails::create)
                .orElseThrow(() -> new UsernameNotFoundException(id));
    }
}
