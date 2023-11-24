package com.ssafy.homes.domain.estate.dto;

import com.ssafy.homes.domain.estate.domain.DongCode;
import com.ssafy.homes.domain.estate.domain.HouseInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class HouseInfoSearch {
    private long aptCode;
    private DongSearch dongSearch;

    private int buildYear;
    private String roadName;
//    private String roadNameBonbun;
//    private String roadNameBubun;
//    private String roadNameSeq;
//    private String roadNameBasementCode;
//    private String roadNameCode;
    private String dong;
//    private String bonbun;
//    private String bubun;
//    private String sigunguCode;
//    private String eubmyundongCode;
//    private String landCode;
    private String apartmentName;
//    private String jibun;
    private String lng;
    private String lat;

    @Builder
    private HouseInfoSearch(long aptCode, DongSearch dongSearch, int buildYear, String roadName, String roadNameBonbun, String roadNameBubun
            , String roadNameSeq, String roadNameBasementCode, String roadNameCode, String dong, String bonbun, String bubun
            , String sigunguCode, String eubmyundongCode, String landCode, String apartmentName, String jibun, String lng, String lat) {
        this.aptCode = aptCode;
        this.dongSearch = dongSearch;
        this.buildYear = buildYear;
        this.roadName = roadName;
//        this.roadNameBonbun = roadNameBonbun;
//        this.roadNameBubun = roadNameBubun;
//        this.roadNameSeq = roadNameSeq;
//        this.roadNameBasementCode = roadNameBasementCode;
//        this.roadNameCode = roadNameCode;
        this.dong = dong;
//        this.bonbun = bonbun;
//        this.bubun = bubun;
//        this.sigunguCode = sigunguCode;
//        this.eubmyundongCode = eubmyundongCode;
//        this.landCode = landCode;
        this.apartmentName = apartmentName;
//        this.jibun = jibun;
        this.lng = lng;
        this.lat = lat;
    }

    public HouseInfo toEntity(HouseInfoSearch dto) {
        return HouseInfo.builder()
                .aptCode(dto.getAptCode())
                .dongCode(new DongSearch().toEntity(dto.getDongSearch()))
                .buildYear(dto.getBuildYear())
                .roadName(dto.getRoadName())
//                .roadNameBonbun(dto.getRoadNameBonbun())
//                .roadNameBubun(dto.getRoadNameBubun())
//                .roadNameSeq(dto.getRoadNameSeq())
//                .roadNameBasementCode(dto.getRoadNameBasementCode())
//                .roadNameCode(dto.getRoadNameCode())
                .dong(dto.getDong())
//                .bonbun(dto.getBonbun())
//                .bubun(dto.getBubun())
//                .sigunguCode(dto.getSigunguCode())
//                .eubmyundongCode(dto.getEubmyundongCode())
//                .landCode(dto.getLandCode())
                .apartmentName(dto.getApartmentName())
//                .jibun(dto.getJibun())
                .lng(dto.getLng())
                .lat(dto.getLat())
                .build();
    }

    public HouseInfoSearch toDto(HouseInfo entity) {
        aptCode = entity.getAptCode();
        dongSearch = new DongSearch().toDto(entity.getDongCode());
        buildYear = entity.getBuildYear();
        roadName = entity.getRoadName();
//        roadNameBonbun = entity.getRoadNameBonbun();
//        roadNameBubun = entity.getRoadNameBubun();
//        roadNameSeq = entity.getRoadNameSeq();
//        roadNameBasementCode = entity.getRoadNameBasementCode();
//        roadNameCode = entity.getRoadNameCode();
        dong = entity.getDong();
//        bonbun = entity.getBonbun();
//        bubun = entity.getBubun();
//        sigunguCode = entity.getSigunguCode();
//        eubmyundongCode = entity.getEubmyundongCode();
//        landCode = entity.getLandCode();
        apartmentName = entity.getApartmentName();
//        jibun = entity.getJibun();
        lng = entity.getLng();
        lat = entity.getLat();

        return this;
    }
}
