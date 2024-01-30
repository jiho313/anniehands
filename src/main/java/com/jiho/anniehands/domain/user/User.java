package com.jiho.anniehands.domain.user;

import com.jiho.anniehands.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@DynamicInsert
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(nullable = false, length = 50, unique = true)
    private String id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(length = 150)
    private String password;

    @Column(nullable = false, length = 20)
    private String tel;

    @Column(nullable = false)
    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UserLoginType loginInfo;

    @Column
    private LocalDate birthdate;

    @Builder
    public User(Long no, String id, String name, String email, String password, String tel,
                Boolean isEnabled, Role role, LocalDate birthdate, UserLoginType loginInfo) {
        this.no = no;
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.isEnabled = isEnabled;
        this.role = role;
        this.birthdate = birthdate;
        this.loginInfo = loginInfo;
    }

}
