package com.ssafy.homes.domain.estate.dto;

import com.ssafy.homes.domain.estate.domain.HouseDeal;
import lombok.Data;

@Data
public class ResponseMarketPriceDetail {

    // 화면에 보여질 정보
    private long no;
    private String dealAmount;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String area;
    private String floor;
    private String cancelDealType;

    private HouseInfoSearch houseInfo;
    // 동 정보
//    private String sidoName;
//    private String gugunName;
//    private String dongName;

    public ResponseMarketPriceDetail toDto(HouseDeal houseDeal) {
        this.no = houseDeal.getNo();
        this.dealAmount = houseDeal.getDealAmount();
        this.dealYear = houseDeal.getDealYear();
        this.dealMonth = houseDeal.getDealMonth();
        this.dealDay = houseDeal.getDealDay();
        this.area = houseDeal.getArea();
        this.floor = houseDeal.getFloor();
        this.cancelDealType = houseDeal.getCancelDealType();
        this.houseInfo = new HouseInfoSearch().toDto(houseDeal.getHouseInfo());

        // 동 정보
//        this.sidoName = houseDeal.getHouseInfo().getDongCode().getSidoName();
//        this.gugunName = houseDeal.getHouseInfo().getDongCode().getGugunName();
//        this.dongName = houseDeal.getHouseInfo().getDongCode().getDongName();

        return this;
    }




//    private long no;
//    private HouseInfo houseInfo;
//    private String dealAmount;
//    private int dealYear;
//    private int dealMonth;
//    private int dealDay;
//    private String area;
//    private String floor;
//    private String cancelDealType;



}
