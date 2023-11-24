package com.ssafy.homes.domain.estate.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@ToString
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Table(name = "dongcode")
public class DongCode {

    @Id
    private String dongCode;

    private String sidoName;
    private String gugunName;
    private String dongName;

    @Builder
    private DongCode(String dongCode, String sidoName, String gugunName, String dongName) {
        this.dongCode = dongCode;
        this.sidoName = sidoName;
        this.gugunName = gugunName;
        this.dongName = dongName;
    }
}
