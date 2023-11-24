package com.ssafy.homes.domain.estate.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "housedeal")
public class HouseDeal {

    @Id
    private long no;

    /**
     * HouseInfo 1:1 관계 적용
     * HouseDeal => 주 테이블로 적용, 외래키
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aptCode")
    private HouseInfo houseInfo;
//    private long aptCode;


    private String dealAmount;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String area;
    private String floor;
    private String cancelDealType;

    @Builder
    private HouseDeal(long no, HouseInfo houseInfo, String dealAmount, int dealYear, int dealMonth, int dealDay, String area, String floor, String cancelDealType) {
        this.no = no;
        this.houseInfo = houseInfo;
        this.dealAmount = dealAmount;
        this.dealYear = dealYear;
        this.dealMonth = dealMonth;
        this.dealDay = dealDay;
        this.area = area;
        this.floor = floor;
        this.cancelDealType = cancelDealType;
    }
}
