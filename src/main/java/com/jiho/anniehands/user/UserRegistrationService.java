package com.jiho.anniehands.user;

import com.jiho.anniehands.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserService userService;
    private final AddressService addressService;

    @Transactional
    public void registerUser(UserForm userForm) {
        User user = userService.saveUser(userForm);
        addressService.saveAddress(user, userForm);
    }
}
