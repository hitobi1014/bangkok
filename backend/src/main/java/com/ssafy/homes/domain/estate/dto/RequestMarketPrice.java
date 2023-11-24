package com.ssafy.homes.domain.estate.dto;

import lombok.Data;

@Data
public class RequestMarketPrice {
    private double[] lat;
    private double[] lng;
}
