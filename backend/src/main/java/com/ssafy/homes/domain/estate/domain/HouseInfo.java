package com.ssafy.homes.domain.estate.domain;


import lombok.*;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;

@Entity
@Getter
@Setter // 나중에 삭제필요
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "houseinfo")
public class HouseInfo {

    @Id
    private long aptCode;

    /**
     * #dongcode entity 참조 1:1관계
     * 주 테이블에 외래키 적용 HouseInfo가 더 자주 조회에 쓰일거같으니 주 테이블로 적용
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dongCode")
    private DongCode dongCode;

    private int buildYear;
    private String roadName;
    private String roadNameBonbun;
    private String roadNameBubun;
    private String roadNameSeq;
    private String roadNameBasementCode;
    private String roadNameCode;
    private String dong;
    private String bonbun;
    private String bubun;
    private String sigunguCode;
    private String eubmyundongCode;
    private String landCode;
    private String apartmentName;
    private String jibun;
    private String lng;
    private String lat;

    @Builder
    private HouseInfo(long aptCode, DongCode dongCode, int buildYear, String roadName, String roadNameBonbun, String roadNameBubun
            , String roadNameSeq, String roadNameBasementCode, String roadNameCode, String dong, String bonbun, String bubun
            , String sigunguCode, String eubmyundongCode, String landCode, String apartmentName, String jibun, String lng, String lat) {
        this.aptCode = aptCode;
        this.dongCode = dongCode;
        this.buildYear = buildYear;
        this.roadName = roadName;
        this.roadNameBonbun = roadNameBonbun;
        this.roadNameBubun = roadNameBubun;
        this.roadNameSeq = roadNameSeq;
        this.roadNameBasementCode = roadNameBasementCode;
        this.roadNameCode = roadNameCode;
        this.dong = dong;
        this.bonbun = bonbun;
        this.bubun = bubun;
        this.sigunguCode = sigunguCode;
        this.eubmyundongCode = eubmyundongCode;
        this.landCode = landCode;
        this.apartmentName = apartmentName;
        this.jibun = jibun;
        this.lng = lng;
        this.lat = lat;
    }
}
