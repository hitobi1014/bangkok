package com.ssafy.homes.domain.estate.application;

import com.ssafy.homes.domain.estate.domain.HouseDeal;
import com.ssafy.homes.domain.estate.dto.RequestMarketPrice;
import com.ssafy.homes.domain.estate.dto.ResponseMarketPriceDetail;
import com.ssafy.homes.domain.estate.dto.ResponseMarketToMap;

import java.util.List;
import java.util.Map;

public interface MarketPriceHistoryService {
    List<ResponseMarketToMap> getHouseDeal(RequestMarketPrice param);

    List<ResponseMarketPriceDetail> getHouseDealDetail(Double lat, Double lng);
}
