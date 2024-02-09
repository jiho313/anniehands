package com.jiho.anniehands.domain.options;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Options {

    @Id
    @Column(name = "no")
    private Integer no;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "value", nullable = false, length = 50)
    private String value;

    @Builder
    public Options(Integer no, String name, String value) {
        this.no = no;
        this.name = name;
        this.value = value;
    }
}
