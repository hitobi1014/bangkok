package com.ssafy.homes.domain.estate.dto;

import com.ssafy.homes.domain.estate.domain.HouseDeal;
import lombok.Data;

@Data
public class ResponseMarketToMap {

    private long no;
    private String dealAmount;
    private int dealYear;
    private String area;
    private long aptCode;
    private String lat;
    private String lng;


    public ResponseMarketToMap toDto(HouseDeal houseDeal) {
        this.no = houseDeal.getNo();
        this.dealAmount = houseDeal.getDealAmount();
        this.dealYear = houseDeal.getDealYear();
        this.area = houseDeal.getArea();
        this.aptCode = houseDeal.getHouseInfo().getAptCode();
        this.lat = houseDeal.getHouseInfo().getLat();
        this.lng = houseDeal.getHouseInfo().getLng();

        return this;
    }

}
