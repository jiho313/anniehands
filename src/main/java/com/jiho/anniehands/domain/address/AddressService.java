package com.jiho.anniehands.domain.address;

import com.jiho.anniehands.domain.user.User;
import com.jiho.anniehands.domain.user.dto.UserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    // 주소 저장
    public void saveAddress(User user, UserForm userForm) {
        Address address = createAddress(user, userForm);
        addressRepository.save(address);
    }

    // 주소 생성
    public Address createAddress(User user, UserForm userForm) {
        return Address.builder()
                .user(user)
                .zipcode(userForm.getZipcode())
                .addressName(userForm.getAddress())
                .addressDetail(userForm.getDetailAddress())
                .build();
    }
}
