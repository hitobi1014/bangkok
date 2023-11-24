package com.ssafy.homes.domain.estate.dto;

import lombok.Data;

@Data
public class RequestSelectSearchToSales {
    private String estateSalesType;
    private String transactionType;
    private Long salesPrice;
    private Double area;
    private String year;

    // 남서, 북동 좌표
    private Double[] swLatLng;
    private Double[] neLatLng;

}
