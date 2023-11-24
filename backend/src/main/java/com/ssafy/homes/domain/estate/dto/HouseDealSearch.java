package com.ssafy.homes.domain.estate.dto;

import com.ssafy.homes.domain.estate.domain.HouseDeal;
import com.ssafy.homes.domain.estate.domain.HouseInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@NoArgsConstructor
@ToString
public class HouseDealSearch {
    private long no;
    private HouseInfoSearch houseInfoSearch;

    private String dealAmount;
//    private int dealYear;
//    private int dealMonth;
//    private int dealDay;
    private String area;
    private String floor;
//    private String cancelDealType;

    @Builder
    private HouseDealSearch(long no, HouseInfoSearch houseInfoSearch, String dealAmount, int dealYear, int dealMonth, int dealDay, String area, String floor, String cancelDealType) {
        this.no = no;
        this.houseInfoSearch = houseInfoSearch;
        this.dealAmount = dealAmount;
//        this.dealYear = dealYear;
//        this.dealMonth = dealMonth;
//        this.dealDay = dealDay;
        this.area = area;
        this.floor = floor;
//        this.cancelDealType = cancelDealType;
    }

    public HouseDeal toEntity(HouseDealSearch dto) {
        return HouseDeal.builder()
                .no(dto.getNo())
                .houseInfo(new HouseInfoSearch().toEntity(dto.getHouseInfoSearch()))
                .dealAmount(dto.getDealAmount())
//                .dealYear(dto.getDealYear())
//                .dealMonth(dto.getDealMonth())
//                .dealDay(dto.getDealDay())
                .area(dto.getArea())
                .floor(dto.getFloor())
//                .cancelDealType(dto.getCancelDealType())
                .build();
    }

    public HouseDealSearch toDto(HouseDeal entity) {
        no = entity.getNo();
        houseInfoSearch = new HouseInfoSearch().toDto(entity.getHouseInfo());
        dealAmount = entity.getDealAmount();
//        dealYear = entity.getDealYear();
//        dealMonth = entity.getDealMonth();
//        dealDay = entity.getDealDay();
        area = entity.getArea();
        floor = entity.getFloor();
//        cancelDealType = entity.getCancelDealType();
        return this;
    }
}
