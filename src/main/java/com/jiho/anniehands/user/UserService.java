package com.jiho.anniehands.user;

import com.jiho.anniehands.address.Address;
import com.jiho.anniehands.address.AddressRepository;
import com.jiho.anniehands.exception.CustomErrorCode;
import com.jiho.anniehands.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User saveUser(UserForm userForm) {
        String encryptedPassword = passwordEncoder.encode(userForm.getPassword());
        User user = User.builder()
                .id(userForm.getId())
                .name(userForm.getName())
                .email(userForm.getEmail())
                .password(encryptedPassword)
                .tel(userForm.getTel())
                .birthdate(userForm.getBirth())
                .loginInfo(LoginInfo.ANNIEHANDS)
                .role(Role.ROLE_USER)
                .build();
        validateDuplicateUser(user);
        userRepository.save(user);

        Address address = Address.builder()
                .user(user)
                .zipcode(userForm.getZipcode())
                .addressName(userForm.getAddress())
                .addressDetail(userForm.getDetailAddress())
                .build();
        addressRepository.save(address);
        return user;
    }

    // 아이디 혹은 이메일 중복 검사
    private void validateDuplicateUser(User user) {
        Optional<User> optionalUserById = userRepository.findById(user.getId());
        if (optionalUserById.isPresent()) {
            throw new MemberException(CustomErrorCode.DUPLICATE_USERNAME);
        }

        Optional<User> optionalUserByEmail = userRepository.findByEmail(user.getEmail());
        if (optionalUserByEmail.isPresent()) {
            throw new MemberException(CustomErrorCode.DUPLICATE_EMAIL);
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(CustomErrorCode.NO_MATCHING_MEMBER));
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new MemberException(CustomErrorCode.NO_MATCHING_MEMBER.getMessage()));
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            User user = findById(id);
            return UserDetailsImpl.create(user);
        } catch (MemberException e) {
            throw new UsernameNotFoundException(id);
        }
    }
}
