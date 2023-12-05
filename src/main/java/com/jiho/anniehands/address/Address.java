package com.jiho.anniehands.address;

import com.jiho.anniehands.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"user"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    // 사용자 지정 주소 이름
    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "zipcode", nullable = false, length = 30)
    private String zipcode;

    @Column(name = "address_name", nullable = false, length = 45)
    private String addressName;

    @Column(name = "address_detail", nullable = false, length = 45)
    private String addressDetail;

    @Column(name = "req", length = 45)
    private String req;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    // 연관 관계 매핑 (User 엔티티가 있다고 가정)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public Address(Long no, String name, String zipcode, String addressName, String addressDetail, String req, boolean isDefault, User user) {
        this.no = no;
        this.name = name;
        this.zipcode = zipcode;
        this.addressName = addressName;
        this.addressDetail = addressDetail;
        this.req = req;
        this.isDefault = isDefault;
        this.user = user;
    }
}
